package org.ecnu.chgao.healthcare.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chgao on 17-6-12.
 */
@Table("remind")
public class RemindData {
    private static final long HOURS = 60 * 60 * 1000;//ms
    private static final long DAY = 24 * HOURS;//ms
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("type")
    private Type mRemindType;
    @Column("create_date")
    private Date mCreateDate;
    @Column("remind_date")
    //start
    private Date mRemindDate;
    @Column("show_remind_time")
    private String mShowRemindTime;
    @Column("name")
    private String mRemindName;
    @Column("addition")
    private String mRemindAddition;
    @Column("extend")
    private Map<Integer, String> extend = new HashMap<>();
    private Map<Integer, Long> remindTime = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Long> getRemindTime() {
        return remindTime;
    }

    public void setExtendByItemData(List<MedicalNotificationEditItemData> extendList) throws Exception {
        if (mRemindDate == null || mCreateDate == null) {
            throw new Exception("should set remind data first");
        }
        for (MedicalNotificationEditItemData data : extendList) {
            extend.put(data.getmType().value, data.getValue());
        }
        //start in next day of mCreateDate
        while (mRemindDate.before(mCreateDate)) {
            mRemindDate.setTime(mRemindDate.getTime() + DAY);
        }
        long startTime = mRemindDate.getTime();
        //1.duration(12 days)
        int days = Integer.parseInt(extend.get(MedicalNotificationEditItemData.Type.DOURATION.value));
        int times = Integer.parseInt(extend.get(MedicalNotificationEditItemData.Type.TIMES_PERDAY.value));
        float inteval = Float.parseFloat(extend.get(MedicalNotificationEditItemData.Type.INTEVAL.value));
        //2.times per day(3 times/day)
        //
        for (int i = 0; i < days; i++) {
            startTime += DAY;
            for (int j = 0; j < times; j++) {
                remindTime.put((int) (mCreateDate.getTime() + i * 24 + j), (long) (startTime + j * HOURS * inteval));
            }
        }
    }

    public Map<Integer, String> getExtend() {
        return extend;
    }

    public void setExtend(Map<Integer, String> extend) {
        this.extend = extend;
    }

    public Type getmRemindType() {
        return mRemindType;
    }

    public void setmRemindType(Type mRemindType) {
        this.mRemindType = mRemindType;
    }

    public Date getmCreateDate() {
        return mCreateDate;
    }

    public void setmCreateDate(Date mCreateDate) {
        this.mCreateDate = mCreateDate;
    }

    public Date getmRemindDate() {
        return mRemindDate;
    }

    public void setmRemindDate(Date mRemindDate) {
        this.mRemindDate = mRemindDate;
    }

    public String getmShowRemindTime() {
        return mShowRemindTime;
    }

    public void setmShowRemindTime(String mShowRemindTime) {
        this.mShowRemindTime = mShowRemindTime;
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

    @Override
    public String toString() {
        return "id:" + id
                + "\ntype:" + mRemindType
                + "\nname:" + mRemindName
                + "\naddition:" + mRemindAddition;
    }

    public List<MedicalNotificationEditItemData> getmMedicalNotificationData() {
        List<MedicalNotificationEditItemData> result = new ArrayList<>();
        for (MedicalNotificationEditItemData.Type type : MedicalNotificationEditItemData.Type.values()) {
            result.add((MedicalNotificationEditItemData) new MedicalNotificationEditItemData(type).setValue(extend.get(type.value)));
        }
        return result;
    }

    public enum Type {
        MEDICAL(0), WALK(1);
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
