package org.ecnu.chgao.healthcare.bean;

/**
 * Created by chgao on 17-6-13.
 */

public class NotificationEditItemData extends BaseItemData {
    protected String name;
    protected String value;

    public String getName() {
        return name;
    }

    public NotificationEditItemData setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public NotificationEditItemData setValue(String value) {
        this.value = value;
        return this;
    }

}
