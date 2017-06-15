package org.ecnu.chgao.healthcare.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chgao on 17-6-15.
 */

public class UploadPackage extends BaseUploadBean {
    private StepUploadBean mStepBean;
    private List<LocationUploadBean> mLocationBean;

    public UploadPackage(long time) {
        super(time);
        mLocationBean = new ArrayList<>();
    }

    public void setStep(StepUploadBean step, List<LocationUploadBean> locations) {
        mStepBean = step;
        mLocationBean.addAll(locations);
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("Step", mStepBean);
            object.put("location", mLocationBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
