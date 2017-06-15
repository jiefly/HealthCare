package org.ecnu.chgao.healthcare.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chgao on 17-6-15.
 */

public class LocationUploadBean extends BaseUploadBean implements Serializable {
    public static final int FREQUENCY = 60 * 1000;//ms
    private double mLatitude;
    private double mLongitude;
    private float mAccuracy;
    private String mAddress;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("id:").append(id).append("\n")
                .append("time:").append(new Date(mHappendTime).toGMTString()).append("\n")
                .append("address:").append(mAddress);
        return sb.toString();
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", id);
            object.put("time", mHappendTime);
            object.put("latitude", mLatitude);
            object.put("longitude", mLongitude);
            object.put("accuracy", mAccuracy);
            object.put("address", mAddress);
            object.put("frequency", FREQUENCY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
