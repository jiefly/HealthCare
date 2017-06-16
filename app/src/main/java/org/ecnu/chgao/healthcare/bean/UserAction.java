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
        new AccountInfo(context).login(phoneMd5, passwordMd5, action, callback);
    }

    public void getSmsCode(String phone, int action, @NonNull NetworkCallback callback) throws JSONException {
        AccountInfo accountInfo = new AccountInfo(context);
        accountInfo.getSmsCode(phone, action, callback);
    }

    public void findPwd(String phone, String pwd, String code, int action, @NonNull NetworkCallback callback) throws JSONException {
        AccountInfo accountInfo = new AccountInfo(context);
        accountInfo.findPwd(phone, pwd, code, action, callback);
    }

    public void register(String phone, String passwordMd5, String smsCode, int action,
                         @NonNull NetworkCallback callback) throws JSONException {

        new AccountInfo(context).register(phone, passwordMd5, smsCode, action, callback);
    }
}
