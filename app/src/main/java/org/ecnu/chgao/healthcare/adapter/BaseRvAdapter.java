package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.ecnu.chgao.healthcare.bean.BaseItemData;
import org.ecnu.chgao.healthcare.view.item.BaseItemView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public abstract class BaseRvAdapter extends RecyclerView.Adapter<BaseRvAdapter.BaseVH> {
    protected final PublishSubject<BaseItemData> onClickSubject = PublishSubject.create();
    protected final PublishSubject<BaseItemData> onLongClickSubject = PublishSubject.create();

    protected Context mContext;
    protected final List<BaseItemData> datas;

    public BaseRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    public void removeData(int index) {
        if (index > -1 && index < datas.size()) {
            datas.remove(index);
            notifyItemRemoved(index);
            refreshDatas();
        }
    }

    private void refreshDatas() {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setIndex(i);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(BaseItemData data) {
        if (data != null) {
            datas.add(data);
        }
    }

    public List<BaseItemData> getAllDatas() {
        return datas;
    }

    public void addDatas(List<BaseItemData> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
        }
    }

    public Observable<? extends BaseItemData> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    public Observable<? extends BaseItemData> getPositionLongClicks() {
        return onLongClickSubject.asObservable();
    }

    class BaseVH extends RecyclerView.ViewHolder {
        BaseItemView itemView;
        BaseItemData data;

        public BaseVH(View itemView) {
            super(itemView);
            this.itemView = (BaseItemView) itemView;
        }

        public BaseItemView getItemView() {
            return itemView;
        }

        public BaseVH setOnClickListener(final PublishSubject<BaseItemData> onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onNext(data);
                }
            });

            return this;
        }

        public BaseVH setOnLongClickListener(final PublishSubject<BaseItemData> onClickListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onNext(data);
                    return true;
                }
            });

            return this;
        }

        public void resetView(BaseItemData data) {
            this.data = data;
            itemView.fillData(data);
        }
    }
}