package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.bean.EditCardItemData;
import org.ecnu.chgao.healthcare.view.item.EditCardItemView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardRvAdapter extends RecyclerView.Adapter<EditCardRvAdapter.EditCardVH> {
    private final PublishSubject<EditCardItemData> onClickSubject = PublishSubject.create();
    private List<EditCardItemData> datas;
    private Context mContext;

    public EditCardRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public EditCardVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditCardVH(new EditCardItemView(mContext)).setOnClickListener(onClickSubject);
    }

    @Override
    public void onBindViewHolder(EditCardVH holder, int position) {
        holder.resetView(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public EditCardRvAdapter addData(EditCardItemData data) {
        if (data != null) {
            datas.add(data);
        }
        return this;
    }

    public EditCardRvAdapter addDatas(List<EditCardItemData> newDatas) {
        if (newDatas != null && newDatas.size() != 0) {
            datas.addAll(newDatas);
        }
        return this;
    }

    public Observable<? extends EditCardItemData> getPositionClicks() {
        return onClickSubject.asObservable();
    }


    class EditCardVH extends RecyclerView.ViewHolder {
        private EditCardItemView itemView;
        private EditCardItemData data;

        public EditCardVH(View itemView) {
            super(itemView);
            this.itemView = (EditCardItemView) itemView;
        }

        public EditCardVH setOnClickListener(final PublishSubject<EditCardItemData> onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onNext(data);
                }
            });

            return this;
        }

        public void resetView(EditCardItemData data) {
            this.data = data;
            itemView.fillData(data.getName(), data.isEnable());
        }
    }
}
