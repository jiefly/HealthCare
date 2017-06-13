package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.bean.NormalRemindItemData;

/**
 * Created by chgao on 17-6-10.
 */

public class RemindItemView extends RelativeLayout {
    private ImageView mLeftIcon;
    private ImageView mRightIcon;
    private TextView mRemindName;
    private TextView mRemindDetail;
    private TextView mRemindTime;

    public RemindItemView(Context context) {
        super(context);
        init(context);
    }


    public RemindItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RemindItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RemindItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.medical_remind_item, this);
        mLeftIcon = (ImageView) findViewById(R.id.id_notification_edit_title);
        mRightIcon = (ImageView) findViewById(R.id.id_notification_edit_more);
        mRemindName = (TextView) findViewById(R.id.id_notification_edit_value);
        mRemindDetail = (TextView) findViewById(R.id.id_notification_edit_value);
        mRemindTime = (TextView) findViewById(R.id.id_medical_remind_time);
    }

    public void fillData(NormalRemindItemData data) {
        mRemindName.setText(data.getName());
        mRemindTime.setText(data.getTime());
        mRemindDetail.setText(data.getDetail());
    }

}
