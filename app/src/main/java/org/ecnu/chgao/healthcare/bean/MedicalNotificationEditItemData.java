package org.ecnu.chgao.healthcare.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by chgao on 17-6-13.
 */

public class MedicalNotificationEditItemData extends NotificationEditItemData implements Parcelable, Serializable {
    public static final Creator<MedicalNotificationEditItemData> CREATOR = new Creator<MedicalNotificationEditItemData>() {
        @Override
        public MedicalNotificationEditItemData createFromParcel(Parcel in) {
            return new MedicalNotificationEditItemData(in);
        }

        @Override
        public MedicalNotificationEditItemData[] newArray(int size) {
            return new MedicalNotificationEditItemData[size];
        }
    };
    protected Type mType = Type.MEDCIAL_NAME;

    public MedicalNotificationEditItemData(Type type) {
        mType = type;
    }

    protected MedicalNotificationEditItemData(Parcel in) {
        mType = Type.getTypeByValue(in.readInt());
        name = in.readString();
        value = in.readString();
        mIndex = in.readInt();
    }

    public Type getmType() {
        return mType;
    }

    public MedicalNotificationEditItemData setmType(Type mType) {
        this.mType = mType;
        return this;
    }

    @Override
    public String getName() {
        return mType.getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mType.value);
        dest.writeString(name);
        dest.writeString(value);
        dest.writeInt(mIndex);
    }

    public enum Type {
        MEDCIAL_NAME(0), PILL_PERTIME(1), TIMES_PERDAY(2), START_TIME(3), INTEVAL(4)/*unit is minute*/, REMIND_WAY(5), DOURATION(6);
        int value;

        Type(int value) {
            this.value = value;
        }

        public static Type getTypeByValue(int value) {
            for (Type t : Type.values()) {
                if (t.value == value) {
                    return t;
                }
            }
            return null;
        }

        public String getName() {
            switch (value) {
                case 0:
                    return "药物名称";
                case 1:
                    return "每次服用";
                case 2:
                    return "每日服用";
                case 3:
                    return "开始服用时间";
                case 4:
                    return "服用间隔";
                case 5:
                    return "提醒方式";
                case 6:
                    return "服用天数";
            }
            return "";
        }
    }
}
