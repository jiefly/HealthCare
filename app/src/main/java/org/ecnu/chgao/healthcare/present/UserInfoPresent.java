package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.bean.UserInfo;
import org.ecnu.chgao.healthcare.model.UserInfoModel;
import org.ecnu.chgao.healthcare.view.UserInfoViewer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by chgao on 17-6-16.
 */

public class UserInfoPresent extends BasePresent<UserInfoViewer, UserInfoModel> {
    public UserInfoPresent(UserInfoViewer viewer) {
        mViewer = viewer;
        mModel = new UserInfoModel(this);
        mViewer.setUserInfo(mModel.getUserInfo());
    }

    public void saveUserInfo(@NotNull String userName, int userSex, @NotNull String emergencyName, @NotNull String emergencyPhone, int emergencySex) {
        mModel.saveUserInfo(new UserInfo(userName, userSex, emergencyName, emergencyPhone, emergencySex));
        mViewer.finish();
    }
}
