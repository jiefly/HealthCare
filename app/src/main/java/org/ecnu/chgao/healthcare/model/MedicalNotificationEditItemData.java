package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.bean.NotificationEditItemData;

/**
 * Created by chgao on 17-6-13.
 */

public class MedicalNotificationEditItemData extends NotificationEditItemData {
    protected Type mType;

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

    public enum Type {
        MEDCIAL_NAME(0), PILL_PERTIME(1), TIMES_PERDAY(2), START_TIME(3), INTEVAL(4)/*unit is minute*/, REMIND_WAY(5), DOURATION(6);
        int value;

        Type(int value) {
            this.value = value;
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
