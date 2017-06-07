package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.bean.StepHistoryData;

import org.ecnu.chgao.healthcare.step.bean.StepData;
import org.ecnu.chgao.healthcare.view.item.StepHistoryItemView;

import java.util.ArrayList;
import java.util.List;

public class StepRvAdapter extends RecyclerView.Adapter<StepRvAdapter.HeartRateVH> {
    private Context mContext;
    private List<StepData> datas;

    public StepRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public HeartRateVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeartRateVH(new StepHistoryItemView(mContext));
    }

    @Override
    public void onBindViewHolder(HeartRateVH holder, int position) {
        holder.getItemView().fillData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(StepData data) {
        if (data != null) {
            datas.add(data);
        }
    }

    public void addDatas(List<StepData> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
        }
    }

    class HeartRateVH extends RecyclerView.ViewHolder {
        StepHistoryItemView itemView;

        public HeartRateVH(View itemView) {
            super(itemView);
            this.itemView = (StepHistoryItemView) itemView;
        }

        public StepHistoryItemView getItemView() {
            return itemView;
        }
    }
}