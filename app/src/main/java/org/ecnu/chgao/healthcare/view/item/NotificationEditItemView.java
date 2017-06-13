package org.ecnu.chgao.healthcare.view.item;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.bean.NotificationEditItemData;

/**
 * Created by chgao on 17-6-13.
 */

public class NotificationEditItemView extends BaseItemView<NotificationEditItemData> {


    public NotificationEditItemView(Context context) {
        super(context);
    }

    public NotificationEditItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotificationEditItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NotificationEditItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mRightIcon = (ImageView) findViewById(R.id.id_notification_edit_more);
        mName = (TextView) findViewById(R.id.id_notification_edit_title);
        mValue = (TextView) findViewById(R.id.id_notification_edit_value);
    }

    @Override
    public void fillData(NotificationEditItemData notificationEditItemData) {
        mName.setText(notificationEditItemData.getName());
        mValue.setText(notificationEditItemData.getValue());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.notification_edit_item;
    }
}
