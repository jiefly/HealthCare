package org.ecnu.chgao.healthcare.connection.http;
/**
 * @author MingLei Jia
 */

import android.content.Context;
import android.support.annotation.NonNull;

public class NetConnection {
    public static final String TAG = "NetConnection";

    public NetConnection(Context context, final String url, final HttpMethod httpMethod,
                         @NonNull final NetworkCallback callback,
                         final String jsonParams) {
        new BaseNetConnection(context, url, httpMethod, callback, jsonParams);
    }
}
