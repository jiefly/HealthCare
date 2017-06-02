package org.ecnu.chgao.healthcare.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.view.MainActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by chgao on 17-6-1.
 */

public class FallDeteachService extends Service implements FallListener.OnFallListener {
    public static final String TAG = FallDeteachService.class.getSimpleName();
    public static final int FOREGROUND_NOTIFICATION_ID = 0x10010;
    public static final int FALL_DOWN_ACTION_NOTIFICATION_ID = 0x01101;
    private SensorManager mSensorManager;
    private FallListener mListener;
    private AtomicBoolean mShowFalldownNotification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAndStartForeground();
        mShowFalldownNotification = new AtomicBoolean(false);
        mListener = new FallListener(this).registerListener(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                onFall();
            }
        }, 10000);
    }

    private void initAndStartForeground() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("跌倒检测服务")
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        if (mListener != null) {
            mSensorManager.unregisterListener(mListener);
        }
        super.onDestroy();
    }

    @Override
    public void onFall() {
        //if we show notification,ignore the same fall down action which happen during showing
        if (mShowFalldownNotification.get()) {
            return;
        }
        mShowFalldownNotification.set(true);
        //if we find user fall down,we need show a notification and give user
        //a chance to cancel this fall down action.if user did't canceled this
        //action in time,we should upload this action to our server
        final long fallTime = System.currentTimeMillis();
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("检测到您摔倒")
                .setContentText("如果您不希望上传摔倒事件，请在一分钟内点击此通知。")
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVibrate(new long[]{0, 300, 500, 700})
                .setSmallIcon(R.mipmap.ic_launcher);
        final NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID, builder.build());
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - fallTime > 60 * 1000){
                    realFall();
                    timer.cancel();
                    builder.setContentText("由于您未在60s内取消该事件，我们将帮你通知您的亲属。");
                    nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID,builder.build());
                    return;
                }
                builder.setContentText(String.format("如果您不希望上传摔倒事件，请在 %s s 内点击次通知。", 60 - (System.currentTimeMillis() - fallTime) / 1000));
                nm.notify(FALL_DOWN_ACTION_NOTIFICATION_ID,builder.build());
            }
        }, 1000, 1000);
    }

    private void realFall() {
        // TODO: 17-6-1 handle real fall down action
    }

}
