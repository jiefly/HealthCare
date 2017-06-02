package org.ecnu.chgao.healthcare.present;

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
    }

    public void onLoginClick(String account, String pwd) {
        UserAction action = new UserAction(mViewer.getContext());
        try {
            action.login(account, pwd, Config.ACTION_LOGIN,
                    new UserAction.SuccessCallback() {
                        @Override
                        public void onSuccess(String jsonResult) {
                            try {
                                JSONObject result = new JSONObject(jsonResult);
                                if ("success".equals(result.getString("result"))){
                                    mViewer.login();
                                    // TODO:save user account && pwd
                                }else {
                                    mViewer.showToast("用户名或者密码错误");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new UserAction.FailCallback() {
                        @Override
                        public void onFail(int status, int reason) {
                            mViewer.showAlertDialog("Tips","登录失败",null,null);
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
