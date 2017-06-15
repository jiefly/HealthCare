package org.ecnu.chgao.healthcare.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.ecnu.chgao.healthcare.alarmmanager.AlarmManagerUtil;
import org.ecnu.chgao.healthcare.service.PackageUploadService;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.DbUtils;

public class PackageUploadDataReceiver extends BroadcastReceiver {

    private static final String TAG = "PackageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DbUtils.getLiteOrm(StepService.DB_NAME) == null) {
            DbUtils.createDb(context, StepService.DB_NAME);
        }
        context.startService(new Intent(context, PackageUploadService.class));
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
    }


}
