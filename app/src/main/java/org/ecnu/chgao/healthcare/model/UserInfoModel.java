package org.ecnu.chgao.healthcare.model;

import android.util.Log;

import org.ecnu.chgao.healthcare.bean.UserInfo;
import org.ecnu.chgao.healthcare.present.UserInfoPresent;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.List;

/**
 * Created by chgao on 17-6-16.
 */

public class UserInfoModel extends BaseModel<UserInfoPresent> {
    public UserInfoModel(UserInfoPresent userInfoPresent) {
        super(userInfoPresent);
    }

    public void saveUserInfo(UserInfo userInfo) {
        DbUtils.deleteAll(UserInfo.class, getDbName());
        DbUtils.insert(userInfo, getDbName());
    }

    public UserInfo getUserInfo() {
        List<UserInfo> userInfos = DbUtils.getQueryAll(UserInfo.class, getDbName());
        if (userInfos == null || userInfos.size() == 0) {
            Log.w(TAG, "no user info data,will return an empty data");
            return new UserInfo("", 0, "", "", 0);
        } else if (userInfos.size() >= 1) {
            Log.w(TAG, "more than one user info data,will return the first one");
        }
        return userInfos.get(0);
    }
}
