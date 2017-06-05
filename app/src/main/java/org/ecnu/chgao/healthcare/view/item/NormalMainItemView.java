package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.ecnu.chgao.healthcare.R;

/**
 * Created by chgao on 17-6-5.
 */

public class NormalMainItemView<D> extends RelativeLayout {
    private static final int INVALID = -1;
    private Context mContext;
    private int mLayoutResId;
    private CardView mCardView;

    public NormalMainItemView(Context context) {
        super(context);
        init(context);
    }

    public NormalMainItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NormalMainItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context) {
        mContext = context;
        if (getLayoutRes() == INVALID) {
            mLayoutResId = R.layout.step_history_item;
        } else {
            mLayoutResId = getLayoutRes();
        }
        inflate(context, mLayoutResId, this);
        initView();
    }

    private int getLayoutRes() {
        return R.layout.normal_main_item_view;
    }

    public void initView() {
        mCardView = (CardView) findViewById(R.id.id_normal_main_item_card);
    }

    public CardView getmCardView() {
        return mCardView;
    }

    public void fillData(D d) {
    }
}
