package org.ecnu.chgao.healthcare.bean;

import org.ecnu.chgao.healthcare.bean.NormalMainItemData;

/**
 * Created by chgao on 17-6-5.
 */

public class MainMenuClickEvent extends NormalMainItemData {
    @Override
    public ItemType getmItemType() {
        return ItemType.STEP;
    }
}
