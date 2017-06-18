package org.ecnu.chgao.healthcare.bean;

/**
 * Created by chgao on 17-6-18.
 */

public class NormalSettingItemData extends EditCardItemData {
    public NormalSettingItemData(int index, String name, boolean mChecked) {
        super(index, name, mChecked);
        mEnable = true;
    }
}
