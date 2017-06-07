package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.util.AttributeSet;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.step.bean.StepData;

/**
 * Created by chgao on 17-5-25.
 */

public class StepHistoryItemView extends NormalHistoryItemView<StepData> {
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
    public void fillData(StepData stepHistoryData) {
        mTimeTv.setText(stepHistoryData.getToday());
        mValueTv.setText(stepHistoryData.getStep());
        mValueUnitTv.setText("步");
    }


}
