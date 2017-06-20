package org.ecnu.chgao.healthcare.bean;

import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chgao on 17-6-15.
 */

public class LocationUploadBean extends BaseUploadBean implements Serializable {
    @SerializedName("frequency")
    public static final int FREQUENCY = 60 * 1000;//ms
    @SerializedName("latitude")
    private double mLatitude;
    @SerializedName("longitude")
    private double mLongitude;
    @SerializedName("accuracy")
    private float mAccuracy;
    @SerializedName("address")
    private String mAddress;
    @Column("used")
    private boolean mUsed;


    public LocationUploadBean(long time) {
        super(time);
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public float getmAccuracy() {
        return mAccuracy;
    }

    public void setmAccuracy(float mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public boolean ismUsed() {
        return mUsed;
    }

    public void setmUsed(boolean mUsed) {
        this.mUsed = mUsed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("id:").append(id).append("\n")
                .append("time:").append(new Date(mHappendTime)).append("\n")
                .append("address:").append(mAddress);
        return sb.toString();
    }
}
