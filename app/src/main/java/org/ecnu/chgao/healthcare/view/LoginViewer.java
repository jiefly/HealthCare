package org.ecnu.chgao.healthcare.view;

import org.ecnu.chgao.healthcare.present.LoginPresent;

/**
 * Created by chgao on 17-5-26.
 */

public interface LoginViewer extends BaseViewer<LoginPresent> {
    void loginSuccess();

    void loginFailed(String msg);

    void jumpToRegister();

    void jumpToFindPwd();

    void setAccountInfo(String account, String pwd);
}
