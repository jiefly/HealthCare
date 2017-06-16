package org.ecnu.chgao.healthcare.bean;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chgao on 17-6-15.
 */

public class StepUploadBean extends BaseUploadBean implements Serializable {
    //6 bean/Hour
    @SerializedName("frequency")
    public static final int FREQUENCY = 10 * 60 * 1000;//ms
    @SerializedName("step")
    private int mStep;
    @SerializedName("task")
    private int mTask;


    public StepUploadBean(long time) {
        super(time);
    }

    public int getmStep() {
        return mStep;
    }

    public void setmStep(int mStep) {
        this.mStep = mStep;
    }

    public int getmTask() {
        return mTask;
    }

    public void setmTask(int mTask) {
        this.mTask = mTask;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("id:").append(id).append("\n")
                .append("time:").append(new Date(mHappendTime).toGMTString()).append("\n")
                .append("task:").append(mTask).append("/n")
                .append("current:").append(mStep);
        return sb.toString();
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("time", mHappendTime);
            jsonObject.put("current", mStep);
            jsonObject.put("task", mTask);
            jsonObject.put("frequency", FREQUENCY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
