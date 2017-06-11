package org.ecnu.chgao.healthcare.bean;

import android.support.annotation.DrawableRes;

import org.ecnu.chgao.healthcare.model.EditCardModel;

/**
 * Created by chgao on 17-6-5.
 */

public class NormalMainItemData {
    @DrawableRes
    private int mIconRes;
    @DrawableRes
    private int mMoreRes;
    private String mItemTitle;
    private String mContent;
    private int mIndex;
    private int mTotalStep;
    private int mCurrentStep;
    private ItemType mItemType;

    public int getmIndex() {
        return mIndex;
    }

    public NormalMainItemData setmIndex(int mIndex) {
        this.mIndex = mIndex;
        return this;
    }

    public ItemType getmItemType() {
        return mItemType;
    }

    public NormalMainItemData setmItemType(ItemType mItemType) {
        this.mItemType = mItemType;
        return this;
    }

    public int getmTotalStep() {
        return mTotalStep;
    }

    public NormalMainItemData setmTotalStep(int mTotalStep) {
        this.mTotalStep = mTotalStep;
        return this;
    }

    public int getmCurrentStep() {
        return mCurrentStep;
    }

    public NormalMainItemData setmCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
        return this;
    }

    public int getmIconRes() {
        return mIconRes;
    }

    public NormalMainItemData setmIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
        return this;
    }

    public String getmItemTitle() {
        return mItemTitle;
    }

    public NormalMainItemData setmItemTitle(String mItemTitle) {
        this.mItemTitle = mItemTitle;
        return this;
    }

    public String getmContent() {
        return mContent;
    }

    public NormalMainItemData setmContent(String mContent) {
        this.mContent = mContent;
        return this;
    }

    public int getmMoreRes() {
        return mMoreRes;
    }

    public NormalMainItemData setmMoreRes(int mMoreRes) {
        this.mMoreRes = mMoreRes;
        return this;
    }

    public enum ItemType {
        UNKNOW(0), STEP(1), LOCATION(2), FALL_DOWN(3), NOTIFICATION(4), EVETY_DAY_HEALTH(5), FOTTER(6);
        int value;

        ItemType(int value) {
            this.value = value;
        }

        public static ItemType getTypeByValue(int value) {
            for (ItemType itemType : ItemType.values()) {
                if (value == itemType.value) {
                    return itemType;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }
}
