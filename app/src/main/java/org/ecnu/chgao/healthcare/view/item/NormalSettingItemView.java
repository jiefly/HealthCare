package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

/**
 * Created by chgao on 17-6-18.
 */

public class NormalSettingItemView extends EditCardItemView {
    public NormalSettingItemView(Context context) {
        super(context);
    }

    public NormalSettingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalSettingItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NormalSettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
