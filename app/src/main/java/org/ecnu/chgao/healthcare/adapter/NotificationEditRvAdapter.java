package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.bean.NotificationEditItemData;
import org.ecnu.chgao.healthcare.view.item.NotificationEditItemView;

/**
 * Created by chgao on 17-6-13.
 */

public class NotificationEditRvAdapter extends BaseRvAdapter {
    public NotificationEditRvAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseVH(new NotificationEditItemView(mContext)).setOnClickListener(onClickSubject).setOnLongClickListener(onLongClickSubject);
    }

    @Override
    public void onBindViewHolder(BaseVH holder, int position) {
        datas.get(position).setIndex(position);
        holder.resetView(datas.get(position));
    }

    public void onItemChange(int index, NotificationEditItemData data) {

    }

}
