package org.ecnu.chgao.healthcare.bean;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardItemData {
    private String name;
    private boolean enable;
    private int mIndex;

    public EditCardItemData(int index, String name, boolean enable) {
        mIndex = index;
        this.name = name;
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }
}
