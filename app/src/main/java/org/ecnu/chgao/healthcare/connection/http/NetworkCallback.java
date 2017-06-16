package org.ecnu.chgao.healthcare.connection.http;

/**
 * Created by chgao on 17-6-16.
 */

public interface NetworkCallback {
    void onSuccess(String result);

    void onFail(String reason);
}
