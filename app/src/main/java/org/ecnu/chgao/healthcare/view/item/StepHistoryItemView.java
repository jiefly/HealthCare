package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import org.ecnu.chgao.healthcare.bean.StepHistoryData;

import org.ecnu.chgao.healthcare.R;

/**
 * Created by chgao on 17-5-25.
 */

public class StepHistoryItemView extends NormalHistoryItemView<StepHistoryData> {
    public StepHistoryItemView(Context context) {
        super(context);
    }

    public StepHistoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StepHistoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.step_history_item;
    }

    @Override
    public void fillData(StepHistoryData stepHistoryData) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTimeTv.setText(stepHistoryData.dateDetail(false, true, true, true, true, false));
        }
        mValueTv.setText(stepHistoryData.getValue() + "");
        mValueUnitTv.setText("步");
    }


}
