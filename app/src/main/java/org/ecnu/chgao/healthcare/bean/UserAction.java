package org.ecnu.chgao.healthcare.bean;
/**
 * @author MingLei Jia
 */

import android.content.Context;
import android.support.annotation.NonNull;

import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.json.JSONException;

public class UserAction {


    private Context context = null;

    public UserAction(Context context) {
        this.context = context;
    }

    public void login(String phoneMd5, String passwordMd5, int action,
                      @NonNull final NetworkCallback callback) throws JSONException {
        new AccountInfo(context).login(phoneMd5, passwordMd5, callback);
    }

    public void getSmsCode(String phone, @NonNull NetworkCallback callback) throws JSONException {
        new AccountInfo(context).getSmsCode(phone, callback);
    }

    public void findPwd(String phone, String pwd, String code, @NonNull NetworkCallback callback) throws JSONException {
        new AccountInfo(context).findPwd(phone, pwd, code, callback);
    }

    public void register(String phone, String passwordMd5, String smsCode,
                         @NonNull NetworkCallback callback) throws JSONException {

        new AccountInfo(context).register(phone, passwordMd5, smsCode, callback);
    }
}
