package org.ecnu.chgao.healthcare.connection.http;
/**
 * @author MingLei Jia
 */

import android.content.Context;

public class Netconnection {

    public Netconnection(Context context, final String url, final HttpMethod httpMethod,
                         final SuccessCallback successCallback,
                         final FailCallback failCallback, final int action,
                         final String jsonParams) {

        //���ӷ�����
        new BaseNetConnection(context, url, httpMethod, new BaseNetConnection.SuccessCallback() {

            @Override
            public void onSuccess(String result) {

                if (result != null) {
                    if (successCallback != null) {
                        successCallback.onSuccess(result);
                    }
                } else {
                    if (failCallback != null) {
                        failCallback.onFail(0, 0);
                    }
                }
            }
        }, new BaseNetConnection.FailCallback() {

            @Override
            public void onFail(int status, int reason) {

                if (failCallback != null) {
                    failCallback.onFail(status, reason);
                }
            }
        }, action, jsonParams);


    }


    //�ɹ��ص�����
    public interface SuccessCallback {

        void onSuccess(String result);
    }


    //ʧ�ܻص�����
    public interface FailCallback {
        void onFail(int status, int reason);
    }

}
