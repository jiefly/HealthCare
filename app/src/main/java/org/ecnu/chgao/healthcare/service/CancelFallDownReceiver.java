package org.ecnu.chgao.healthcare.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CancelFallDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && context != null) {
            Intent i = new Intent(context, FallDeteachService.class);
            i.putExtra(FallDeteachService.CANCEL_FALL_DOWN_ACTION, true);
            context.startService(i);
        }
    }
}
