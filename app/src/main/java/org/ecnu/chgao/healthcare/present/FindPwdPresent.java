package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.bean.UserAction;
import org.ecnu.chgao.healthcare.connection.http.NetworkCallback;
import org.ecnu.chgao.healthcare.view.RegisterViewer;
import org.json.JSONException;

/**
 * Created by chgao on 17-6-7.
 */

public class FindPwdPresent extends RegisterPresent {

    public FindPwdPresent(RegisterViewer viewer) {
        super(viewer);
    }

    @Override
    public void onRegisterClick(String phone, String smsCode, String pwd) {
        if (DEBUG) {
            mViewer.showProgress("重置中...");
            UserAction ua = new UserAction(mViewer.getContext());
            try {
                ua.findPwd(phone, pwd, smsCode, new NetworkCallback() {
                    @Override
                    public void onSuccess(String result) {
                        mViewer.dismissProgress();
                        mViewer.onRegisterSuccess();
                    }

                    @Override
                    public void onFail(String reason) {
                        mViewer.dismissProgress();
                        mViewer.onRegisterFailed(reason);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
