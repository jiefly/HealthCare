package org.ecnu.chgao.healthcare.bean;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardItemData {
    private String name;
    private boolean mChecked;
    private boolean mEnable;
    private int mIndex;

    public EditCardItemData(int index, String name, boolean mChecked) {
        mIndex = index;
        this.name = name;
        this.mChecked = mChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean ismChecked() {
        return mChecked;
    }

    public void setmChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    public boolean ismEnable() {
        return mEnable;
    }

    public void setmEnable(boolean mEnable) {
        this.mEnable = mEnable;
    }
}
