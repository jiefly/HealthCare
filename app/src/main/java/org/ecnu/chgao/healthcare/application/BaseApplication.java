package org.ecnu.chgao.healthcare.application;

import android.app.Application;

import org.ecnu.chgao.healthcare.util.SharedPreferencesUtils;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.sms.SMSSDK;

/**
 * Created by chgao on 17-5-26.
 */

public class BaseApplication extends Application {
    public SharedPreferencesUtils sharedPreferencesUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        SMSSDK.getInstance().initSdk(this);
        sharedPreferencesUtils = new SharedPreferencesUtils(this);
    }

    public SharedPreferencesUtils getSharedPreferencesUtils() {
        return sharedPreferencesUtils;
    }
}
