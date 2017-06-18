package org.ecnu.chgao.healthcare.present;

import android.util.Log;

import com.google.gson.Gson;

import org.ecnu.chgao.healthcare.application.BaseApplication;
import org.ecnu.chgao.healthcare.bean.AccountInfo;
import org.ecnu.chgao.healthcare.bean.UserInfo;
import org.ecnu.chgao.healthcare.connection.http.HttpMethod;
import org.ecnu.chgao.healthcare.connection.http.NetConnection;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.model.LoginModel;
import org.ecnu.chgao.healthcare.model.UserInfoModel;
import org.ecnu.chgao.healthcare.util.ApiStores;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.view.UserInfoViewer;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chgao on 17-6-16.
 */

public class UserInfoPresent extends BasePresent<UserInfoViewer, UserInfoModel> {
    public static final String TAG = "UserInfoPresent";

    public UserInfoPresent(UserInfoViewer viewer) {
        mViewer = viewer;
        mModel = new UserInfoModel(this);
        mViewer.setUserInfo(mModel.getUserInfo());
    }

    public void saveUserInfo(@NotNull String userName, int userSex, @NotNull String emergencyName, @NotNull String emergencyPhone, int emergencySex) {
        UserInfo userInfo = new UserInfo(userName, userSex, emergencyName, emergencyPhone, emergencySex);
        mModel.saveUserInfo(userInfo);
        JSONObject object = new JSONObject();
        try {
            object.put(AccountInfo.USER, ((BaseApplication) getApplicationContext()).getSharedPreferencesUtils().getParam(LoginModel.ACCOUNT_KEY, ""));
            object.put(AccountInfo.HEADER, Config.HEADER_USER_INFO);
            object.put(AccountInfo.ACTION, Config.ACTION_UPLOAD);
            object.put(Config.HEADER_USER_INFO, new Gson().toJson(userInfo));
            Log.i(TAG, object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewer.showProgress("保存数据中");
        new NetConnection(mViewer.getContext(), ApiStores.API_SERVER_URL, HttpMethod.POST, new NetworkCallback() {
            @Override
            public void onSuccess(String result) {
                mViewer.dismissProgress();
                mViewer.finish();
                Log.i(TAG, "upload packages success");
            }

            @Override
            public void onFail(String reason) {
                mViewer.dismissProgress();
                mViewer.showAlertDialog("保存数据", "上传数据失败", null, null);
                Log.e(TAG, "upload packages failed");
            }
        }, object.toString());
    }
}
