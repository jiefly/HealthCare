package org.ecnu.chgao.healthcare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chgao on 17-6-21.
 */

public class UploadBean<T> {
    @SerializedName("user")
    private String mUser;
    @SerializedName("action")
    private int mAction;
    @SerializedName("header")
    private String mHeader;
    @SerializedName("packages")
    private T mUploadBeans;

    public UploadBean(String mUser, int mAction, String mHeader, T mUploadBeans) {
        this.mUser = mUser;
        this.mAction = mAction;
        this.mHeader = mHeader;
        this.mUploadBeans = mUploadBeans;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public int getmAction() {
        return mAction;
    }

    public void setmAction(int mAction) {
        this.mAction = mAction;
    }

    public String getmHeader() {
        return mHeader;
    }

    public void setmHeader(String mHeader) {
        this.mHeader = mHeader;
    }

    public T getmUploadBeans() {
        return mUploadBeans;
    }

    public void setmUploadBeans(T mUploadBeans) {
        this.mUploadBeans = mUploadBeans;
    }
}
