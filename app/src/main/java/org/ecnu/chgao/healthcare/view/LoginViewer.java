package org.ecnu.chgao.healthcare.view;

import org.ecnu.chgao.healthcare.present.LoginPresent;

/**
 * Created by chgao on 17-5-26.
 */

public interface LoginViewer extends Viewer<LoginPresent> {
    void login();

    void jumpToRegister();

    void jumpToFindPwd();
}
