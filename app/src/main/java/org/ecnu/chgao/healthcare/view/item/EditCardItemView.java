package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.ecnu.chgao.healthcare.R;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardItemView extends RelativeLayout {
    private TextView mNameTV;
    private Switch mEnableSw;

    public EditCardItemView(Context context) {
        super(context);
        init(context);
    }


    public EditCardItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditCardItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditCardItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.title_switch_item, this);
        mNameTV = (TextView) findViewById(R.id.id_edit_card_name);
        mEnableSw = (Switch) findViewById(R.id.id_edit_card_enable);
    }

    public void enableSw(boolean enable) {
        mEnableSw.setEnabled(enable);
    }

    public void fillData(String name, boolean enable) {
        mNameTV.setText(name);
        mEnableSw.setChecked(enable);
    }


    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mEnableSw.setOnClickListener(l);
    }
}
