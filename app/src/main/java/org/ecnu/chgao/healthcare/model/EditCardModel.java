package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.bean.EditCardItemData;
import org.ecnu.chgao.healthcare.present.EditCardPresent;
import org.ecnu.chgao.healthcare.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardModel extends BaseModel {
    List<EditCardItemData> mCards;
    SharedPreferencesUtils sharedPreferencesUtils;
    private EditCardPresent mPresent;

    public EditCardModel(EditCardPresent present) {
        mPresent = present;
        mCards = new ArrayList<>();
    }

    public List<EditCardItemData> getAllCard() {
        if (mCards.size() != 0) {
            return mCards;
        }
        sharedPreferencesUtils = new SharedPreferencesUtils(mPresent.getContext().getApplicationContext());
        int i = 0;
        for (CardType cardType : CardType.values()) {
            mCards.add(new EditCardItemData(i++, cardType.getValue(), (Boolean) sharedPreferencesUtils.getParam(cardType.getValue(), false)));
        }
        return mCards;
    }

    public void toggleEnable(int index) {
        if (index < 0 || index > mCards.size() - 1) {
            throw new IllegalArgumentException("the card you choose is illegal");
        }
        EditCardItemData data = mCards.get(index);
        data.setEnable(!data.isEnable());
    }

    public void update() {
        for (EditCardItemData data : mCards) {
            sharedPreferencesUtils.setParam(data.getName(), data.isEnable());
        }
    }

    public enum CardType {
        STEP("每日运动"), LOCATION("运动轨迹"), FALL_DOWN("跌倒检测"), NOTIFICATION("每日提醒"), EVETY_DAY_HEALTH("健康小提示"), EDIT_CARD("卡片管理");
        String value;

        CardType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
