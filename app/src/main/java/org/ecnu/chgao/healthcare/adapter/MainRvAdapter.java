package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.model.NormalMainItemData;
import org.ecnu.chgao.healthcare.view.item.NormalMainItemView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MainItemVH> {
    private final PublishSubject<NormalMainItemData> onClickSubject = PublishSubject.create();
    private Context mContext;
    private List<NormalMainItemData> datas;

    public MainRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public MainItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainItemVH(new NormalMainItemView(mContext)).setOnClickListener(onClickSubject);
    }

    @Override
    public void onBindViewHolder(MainItemVH holder, int position) {
        NormalMainItemData data = datas.get(position);
        if (data != null) {
            data.setmIndex(position);
            holder.resetView(data);
        } else {
            throw new NullPointerException("item mData is null!!!");
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public Observable<? extends NormalMainItemData> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    public void addData(NormalMainItemData data) {
        if (data != null) {
            datas.add(data);
        }
    }

    public void addDatas(List<NormalMainItemData> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
        }
    }

    class MainItemVH extends RecyclerView.ViewHolder {
        private NormalMainItemView itemView;
        private NormalMainItemData itemData;

        public MainItemVH(View itemView) {
            super(itemView);
            this.itemView = (NormalMainItemView) itemView;
        }

        public NormalMainItemView getItemView() {
            return itemView;
        }

        public MainItemVH setOnClickListener(final PublishSubject<NormalMainItemData> onClickListener) {
            itemView.getmCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onNext(itemData);
                }
            });

            return this;
        }

        public void resetView(NormalMainItemData data) {
            itemData = data;
            itemView.fillData(itemData);
        }
    }
}