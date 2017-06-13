package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chgao on 17-6-13.
 */

public abstract class BaseItemView<D> extends RelativeLayout {
    protected ImageView mLeftIcon;
    protected ImageView mRightIcon;
    protected TextView mName;
    protected TextView mValue;

    public BaseItemView(Context context) {
        super(context);
        init(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BaseItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    public abstract void fillData(D d);

    protected void init(Context context) {
        inflate(context, getLayoutRes(), this);
    }

    protected abstract int getLayoutRes();
}
