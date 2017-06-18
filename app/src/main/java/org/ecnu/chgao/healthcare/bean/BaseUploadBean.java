package org.ecnu.chgao.healthcare.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by chgao on 17-6-15.
 */

public class BaseUploadBean implements Serializable {
    @Column("time")
    @SerializedName("time")
    protected long mHappendTime;
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Expose(deserialize = false)
    protected int id;

    public BaseUploadBean(long time) {
        mHappendTime = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
