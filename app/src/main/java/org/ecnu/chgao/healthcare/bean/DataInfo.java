package org.ecnu.chgao.healthcare.bean;

import android.content.Context;
import android.support.annotation.NonNull;

import org.ecnu.chgao.healthcare.connection.http.HttpMethod;
import org.ecnu.chgao.healthcare.connection.http.NetConnection;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.util.Config;
import org.json.JSONObject;

public class DataInfo {

    private Context context = null;

    public DataInfo(Context context) {
        this.context = context;
    }

    public void uploadData(JSONObject json, int action, @NonNull NetworkCallback callback) {
        new NetConnection(context, Config.GATE_URL, HttpMethod.POST,
                callback, action, json.toString());
    }
}
