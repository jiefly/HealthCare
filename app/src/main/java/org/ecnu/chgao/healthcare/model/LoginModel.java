package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.application.BaseApplication;
import org.ecnu.chgao.healthcare.present.LoginPresent;
import org.ecnu.chgao.healthcare.util.SharedPreferencesUtils;

/**
 * Created by chgao on 17-5-26.
 */

public class LoginModel extends BaseModel {
    public static final String ACCOUNT_KEY = "account_key";
    public static final String PWD_KEY = "pwd_key";
    private LoginPresent mPresent;
    private SharedPreferencesUtils sharedPreferencesUtils;

    public LoginModel(LoginPresent present) {
        mPresent = present;
        sharedPreferencesUtils = ((BaseApplication) mPresent.getContext().getApplicationContext()).getSharedPreferencesUtils();

    }

    public String getUserAccount() {
        return (String) sharedPreferencesUtils.getParam(ACCOUNT_KEY, "");
    }

    public String getPwd() {
        return (String) sharedPreferencesUtils.getParam(PWD_KEY, "");
    }

    public void updateAccount(String account) {
        sharedPreferencesUtils.setParam(ACCOUNT_KEY, account);
    }

    public void updatePwd(String pwd) {
        sharedPreferencesUtils.setParam(PWD_KEY, pwd);
    }
}
