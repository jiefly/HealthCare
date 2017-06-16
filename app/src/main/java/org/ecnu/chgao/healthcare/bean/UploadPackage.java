package org.ecnu.chgao.healthcare.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chgao on 17-6-15.
 */

public class UploadPackage extends BaseUploadBean {
    @SerializedName("step_package")
    private StepUploadBean mStepBean;
    @SerializedName("location_package")
    private List<LocationUploadBean> mLocationBean;

    public UploadPackage(long time) {
        super(time);
        mLocationBean = new ArrayList<>();
    }

    public void setStep(StepUploadBean step, List<LocationUploadBean> locations) {
        mStepBean = step;
        mLocationBean.addAll(locations);
    }
}
