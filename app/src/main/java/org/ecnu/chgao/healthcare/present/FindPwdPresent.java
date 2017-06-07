package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.bean.UserAction;
import org.ecnu.chgao.healthcare.model.RegisterModel;
import org.ecnu.chgao.healthcare.util.Config;
import org.ecnu.chgao.healthcare.view.RegisterViewer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chgao on 17-6-7.
 */

public class FindPwdPresent extends RegisterPresent {
    public FindPwdPresent(RegisterViewer viewer, RegisterModel model) {
        super(viewer, model);
    }

    public FindPwdPresent(RegisterViewer viewer) {
        super(viewer);
    }

    @Override
    public void onRegisterClick(String phone, String smsCode, String pwd) {
        if (DEBUG) {
            UserAction ua = new UserAction(mViewer.getContext());
            try {
                ua.register(phone, pwd, smsCode, Config.ACTION_CHANGE_PASSWORD, new UserAction.SuccessCallback() {
                    @Override
                    public void onSuccess(String jsonResult) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonResult);
                            if ("success".equals(jsonObject.getString("result"))) {
                                //if register success,loginSuccess and jump to main activity
                                mViewer.onRegisterSuccess();
                            } else {
                                mViewer.onRegisterFailed(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new UserAction.FailCallback() {

                    @Override
                    public void onFail(int status, int reason) {
                        //tell user register failed
                        mViewer.onRegisterFailed("重置密码失败");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}
