package org.ecnu.chgao.healthcare.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.Date;

/**
 * Created by chgao on 17-6-12.
 */
@Table("remind")
public class RemindData {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("type")
    private Type mRemindType;
    @Column("date")
    private Date mRemindDate;
    @Column("name")
    private String mRemindName;
    @Column("addition")
    private String mRemindAddition;

    public Type getmRemindType() {
        return mRemindType;
    }

    public void setmRemindType(Type mRemindType) {
        this.mRemindType = mRemindType;
    }

    public Date getmRemindDate() {
        return mRemindDate;
    }

    public void setmRemindDate(Date mRemindDate) {
        this.mRemindDate = mRemindDate;
    }

    public String getmRemindName() {
        return mRemindName;
    }

    public void setmRemindName(String mRemindName) {
        this.mRemindName = mRemindName;
    }

    public String getmRemindAddition() {
        return mRemindAddition;
    }

    public void setmRemindAddition(String mRemindAddition) {
        this.mRemindAddition = mRemindAddition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id:" + id
                + "\ntype:" + mRemindType
                + "\nname:" + mRemindName
                + "\naddition:" + mRemindAddition
                + "\ndate:" + mRemindDate.toGMTString();
    }

    public enum Type {
        MEDCIAL(0), WAKL(1);
        int value;

        Type(int value) {
            this.value = value;
        }

        public static Type generateTypeByValue(int value) {
            for (Type t : Type.values()) {
                if (t.value == value) {
                    return t;
                }
            }
            return null;
        }
    }
}
