package org.ecnu.chgao.healthcare.bean;
/**
 * @author MingLei Jia
 */

import android.content.Context;
import android.support.annotation.NonNull;

import org.ecnu.chgao.healthcare.connection.http.HttpMethod;
import org.ecnu.chgao.healthcare.connection.http.NetConnection;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.util.ApiStores;
import org.ecnu.chgao.healthcare.util.Config;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountInfo {

    public static final String HEADER = "header";
    public static final String PHONE = "phone";
    public static final String SMS_CODE = "code";
    //	public static final String NAME = "userName";
    public static final String PASSWORD_MD5 = "passwordMd5";
    public static final String ACTION = "action";
    public static final String USER = "user";
    public static final String STATE = "state";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String LOCATION = "location";
    public static final int STATUS_LOGIN_SUCCESS = 1;
    public static final int STATUS_LOGIN_FAIL = 0;
    public static final int FAIL_REASON_ACCOUNT_FAIL = 1;
    public static final int FAIL_REASON_OTHER = 0;

    public static final int STATUS_REGISTER_SUCCESS = 1;
    public static final int STATUS_REGISTER_FAIL = 0;
    public static final int FAIL_REASON_MSG_CODE_NOT_CORRECT = -1;
    public static final int FAIL_REASON_PHONE_REGISTERED = 1;

    private Context context = null;

    public AccountInfo(Context context) {
        this.context = context;
    }


    public void register(String phone, String passwordMd5, String smsCode,
                         final NetworkCallback callback) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AccountInfo.PHONE, phone);
        jsonObject.put(AccountInfo.PASSWORD_MD5, passwordMd5);
        jsonObject.put(AccountInfo.ACTION, Config.ACTION_REGISTER);
        jsonObject.put(AccountInfo.SMS_CODE, smsCode);
        System.out.println("JSON: " + jsonObject.toString());
        new NetConnection(context, ApiStores.API_SERVER_URL, HttpMethod.POST,
                callback, jsonObject.toString());
    }


    public void login(String phone, String passwordMd5,
                      @NonNull NetworkCallback callback) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AccountInfo.PHONE, phone);
        jsonObject.put(AccountInfo.PASSWORD_MD5, passwordMd5);
        jsonObject.put(AccountInfo.ACTION, Config.ACTION_LOGIN);
        new NetConnection(context, ApiStores.API_SERVER_URL, HttpMethod.POST,
                callback, jsonObject.toString());

    }

    public void findPwd(String phone, String passwordMd5, String smsCode,
                        @NonNull NetworkCallback callback) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AccountInfo.PHONE, phone);
        jsonObject.put(AccountInfo.PASSWORD_MD5, passwordMd5);
        jsonObject.put(AccountInfo.SMS_CODE, smsCode);
        jsonObject.put(AccountInfo.ACTION, Config.ACTION_REGISTER);


        new NetConnection(context, ApiStores.API_SERVER_URL, HttpMethod.POST, callback, jsonObject.toString());

    }

    public void getSmsCode(String phone,
                           @NonNull NetworkCallback callback) throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(AccountInfo.PHONE, phone);
        jsonObject.put(AccountInfo.ACTION, Config.ACTION_GET_SMS_CODE);


        new NetConnection(context, ApiStores.API_SERVER_URL, HttpMethod.POST,
                callback, jsonObject.toString());

    }

}
