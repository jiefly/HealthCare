package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.bean.NormalRemindItemData;
import org.ecnu.chgao.healthcare.view.item.RemindItemView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public class TodoRvAdapter extends RecyclerView.Adapter<TodoRvAdapter.TodoVH> {
    private final PublishSubject<NormalRemindItemData> onClickSubject = PublishSubject.create();
    private final PublishSubject<NormalRemindItemData> onLongClickSubject = PublishSubject.create();

    private Context mContext;
    private List<NormalRemindItemData> datas;

    public TodoRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public TodoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoVH(new RemindItemView(mContext)).setOnClickListener(onClickSubject).setOnLongClickListener(onLongClickSubject);
    }

    @Override
    public void onBindViewHolder(TodoVH holder, int position) {
        datas.get(position).setIndex(position);
        holder.resetView(datas.get(position));
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

    public void addData(NormalRemindItemData data) {
        if (data != null) {
            datas.add(data);
        }
    }

    public void addDatas(List<NormalRemindItemData> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
        }
    }

    public Observable<? extends NormalRemindItemData> getPositionClicks() {
        return onClickSubject.asObservable();
    }

    public Observable<? extends NormalRemindItemData> getPositionLongClicks() {
        return onLongClickSubject.asObservable();
    }

    class TodoVH extends RecyclerView.ViewHolder {
        RemindItemView itemView;
        NormalRemindItemData data;

        public TodoVH(View itemView) {
            super(itemView);
            this.itemView = (RemindItemView) itemView;
        }

        public RemindItemView getItemView() {
            return itemView;
        }

        public TodoVH setOnClickListener(final PublishSubject<NormalRemindItemData> onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onNext(data);
                }
            });

            return this;
        }

        public TodoVH setOnLongClickListener(final PublishSubject<NormalRemindItemData> onClickListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onNext(data);
                    return true;
                }
            });

            return this;
        }

        public void resetView(NormalRemindItemData data) {
            this.data = data;
            itemView.fillData(data);
        }
    }
}