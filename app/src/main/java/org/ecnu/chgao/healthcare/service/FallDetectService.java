package org.ecnu.chgao.healthcare.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.application.BaseApplication;
import org.ecnu.chgao.healthcare.bean.AccountInfo;
import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.model.LoginModel;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.DateUtilKt;
import org.ecnu.chgao.healthcare.util.DbUtils;
import org.ecnu.chgao.healthcare.view.MainActivity;
import org.json.JSONException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by chgao on 17-6-1.
 */

public class FallDetectService extends Service implements FallDetectListener.OnFallListener {
    public static final String TAG = FallDetectService.class.getSimpleName();
    public static final int FOREGROUND_NOTIFICATION_ID = 0x10010;
    public static final int FALL_DOWN_ACTION_NOTIFICATION_ID = 0x01101;
    public static final String CANCEL_FALL_DOWN_ACTION = "cancel_fall_down_action";
    public static final int FALL_DOWN_CANCEL_TIME = 20;
    private SensorManager mSensorManager;
    private FallDetectListener mListener;
    private AtomicBoolean mShowFallDownNotification;
    private Timer mTimer;
    private CancelFallDownReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initReceiver();
        //initAndStartForeground();
        mShowFallDownNotification = new AtomicBoolean(false);
        mListener = new FallDetectListener(this).registerListener(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                onFall();
            }
        }, 10000);
    }

    private void initReceiver() {
        mReceiver = new CancelFallDownReceiver();
        IntentFilter intentFilter = new IntentFilter("org.ecnu.jiefly.CancelFallDown");
        registerReceiver(mReceiver, intentFilter);
    }

    public void onCancelFallDownAction() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(FALL_DOWN_ACTION_NOTIFICATION_ID);
        Log.w(TAG, "onCancelFallDownAction");
    }

    private void initAndStartForeground() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("跌倒检测服务")
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentText("运行中...");
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        Notification notification = mBuilder.build();
        notification.defaults = Notification.DEFAULT_SOUND;
        //前台 service
        startForeground(FOREGROUND_NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBooleanExtra(CANCEL_FALL_DOWN_ACTION, false)) {
            onCancelFallDownAction();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mListener != null) {
            mSensorManager.unregisterListener(mListener);
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onFall() {
        //if we show notification,ignore the same fall down action which happen during showing
        if (mShowFallDownNotification.get()) {
            return;
        }
        mShowFallDownNotification.set(true);
        //if we find user fall down,we need show a notification and give user
        //a chance to cancel this fall down action.if user did't canceled this
        //action in time,we should upload this action to our server
        final long fallTime = System.currentTimeMillis();
        Intent intent = new Intent(this, CancelFallDownReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("检测到您摔倒")
                .setContentText("如果您不希望上传摔倒事件，请在一分钟内点击此通知。")
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{0, 300, 500, 700})
                .setSmallIcon(R.drawable.logo_icon);
        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID, builder.build());
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - fallTime > FALL_DOWN_CANCEL_TIME * 1000) {
                    realFall();
                    mTimer.cancel();
                    builder.setContentText("由于您未在20s内取消该事件，我们将帮你通知您的亲属。");
                    nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID, builder.build());
                    return;
                }
                builder.setContentText(String.format("如果您不希望上传摔倒事件，请在 %s s 内点击次通知。", FALL_DOWN_CANCEL_TIME - (System.currentTimeMillis() - fallTime) / 1000));
                nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID, builder.build());
            }
        }, 1000, 1000);
    }

    private void realFall() {
        DbUtils.createDb(this, StepService.DB_NAME);
        List<LocationUploadBean> today = DbUtils.getQueryByWhere(LocationUploadBean.class, "today", new String[]{DateUtilKt.getTodayDate()}, StepService.DB_NAME);
        LocationUploadBean closer;
        if (today.size() == 0) {
            closer = new LocationUploadBean(System.currentTimeMillis());
        } else {
            closer = today.get(0);
        }
        for (LocationUploadBean location : today) {
            if (closer.getmHappendTime() < location.getmHappendTime()) {
                closer = location;
            }
        }
        try {
            new AccountInfo(this).onFallDown((String) (((BaseApplication) getApplication()).getSharedPreferencesUtils().getParam(LoginModel.ACCOUNT_KEY, "")), closer, new NetworkCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.i(this.getClass().getSimpleName(), "upload fall down action success");
                }

                @Override
                public void onFail(String reason) {
                    Log.i(this.getClass().getSimpleName(), "upload fall down action fail,reason:" + reason);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
