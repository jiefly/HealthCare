package org.ecnu.chgao.healthcare.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import org.ecnu.chgao.healthcare.util.DateUtilKt;

import java.io.Serializable;

/**
 * Created by chgao on 17-6-15.
 */

public class BaseUploadBean implements Serializable {
    @SerializedName("time")
    protected long mHappendTime;
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Expose(deserialize = false)
    protected int id;
    @Expose(deserialize = false)
    @Column("today")
    private String mToday;

    public BaseUploadBean(long time) {
        mHappendTime = time;
        mToday = DateUtilKt.time2DateString(time);
    }

    public long getmHappendTime() {
        return mHappendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmToday() {
        return mToday;
    }

    public void setmToday(String mToday) {
        this.mToday = mToday;
    }
}
