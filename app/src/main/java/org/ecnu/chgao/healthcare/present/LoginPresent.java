package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.bean.UserAction;
import org.ecnu.chgao.healthcare.model.LoginModel;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.view.LoginViewer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chgao on 17-5-26.
 */

public class LoginPresent extends BasePresent<LoginViewer, LoginModel> {

    public LoginPresent(LoginViewer viewer, LoginModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public LoginPresent(LoginViewer viewer) {
        this.mViewer = viewer;
        mModel = new LoginModel(this);
        mViewer.setAccountInfo(mModel.getUserAccount(), mModel.getPwd());
    }

    public Context getContext() {
        return mViewer.getContext();
    }

    public void onLoginClick(String account, String pwd) {
        mModel.updateAccount(account);
        mModel.updatePwd(pwd);
        mViewer.showProgress("登陆中...");
        UserAction action = new UserAction(mViewer.getContext());
        try {
            action.login(account, pwd, Config.ACTION_LOGIN,
                    new UserAction.SuccessCallback() {
                        @Override
                        public void onSuccess(String jsonResult) {
                            try {
                                JSONObject result = new JSONObject(jsonResult);
                                if ("success".equals(result.getString("result"))) {
                                    mViewer.dismissProgress();
                                    mViewer.loginSuccess();
                                    // TODO:save user account && pwd
                                } else {
                                    mViewer.dismissProgress();
                                    mViewer.loginFailed(result.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new UserAction.FailCallback() {
                        @Override
                        public void onFail(int status, int reason) {
                            mViewer.dismissProgress();
                            mViewer.loginFailed("登录失败");
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onRegisterClick() {
        mViewer.jumpToRegister();
    }

    public void onForgotPdwClick() {
        mViewer.jumpToFindPwd();
    }
}
