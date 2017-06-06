package org.ecnu.chgao.healthcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.model.NormalMainItemData;
import org.ecnu.chgao.healthcare.view.customview.StepArcView;
import org.ecnu.chgao.healthcare.view.item.NormalMainItemView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.NormalItemVH> {
    public static final int ITEM_HEADER = 0x00101;
    public static final int ITEM_NORMAL = 0x11010;
    public static final int ITEM_FOTTER = 0x11111;
    private final PublishSubject<NormalMainItemData> onClickSubject = PublishSubject.create();
    private Context mContext;
    private List<NormalMainItemData> datas;

    public MainRvAdapter(Context context) {
        mContext = context;
        datas = new ArrayList<>();
    }

    @Override
    public NormalItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HEADER:
                return new MainHeaderItemVH(LayoutInflater.from(mContext).inflate(R.layout.main_item_header_item_view, parent, false)).setOnClickListener(onClickSubject);
        }
        return new MainItemVH(new NormalMainItemView(mContext)).setOnClickListener(onClickSubject);
    }

    @Override
    public void onBindViewHolder(NormalItemVH holder, int position) {
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

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_HEADER : ITEM_NORMAL;
    }

    public void setStep(int totalStep, int currentStep) {
        datas.get(0).setmCurrentStep(currentStep);
        datas.get(0).setmTotalStep(totalStep);
        notifyItemChanged(0);
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

    class MainHeaderItemVH extends NormalItemVH {
        public View itemView;
        public StepArcView mArcView;
        public NormalMainItemData itemData;


        public MainHeaderItemVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mArcView = (StepArcView) itemView.findViewById(R.id.id_main_step_arc_view);
        }

        public MainHeaderItemVH setOnClickListener(final PublishSubject<NormalMainItemData> onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onNext(itemData);
                }
            });

            return this;
        }

        @Override
        public void resetView(NormalMainItemData data) {
            itemData = data;
            setStepValue(data.getmTotalStep(), data.getmCurrentStep());
        }

        public void setStepValue(final int totalStep, int currentStep) {
            Observable.just(currentStep).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    if (mArcView != null) {
                        mArcView.setCurrentCount(totalStep, integer);
                    }
                }
            });
        }
    }

    class MainItemVH extends NormalItemVH {
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

        @Override
        public void resetView(NormalMainItemData data) {
            itemData = data;
            itemView.fillData(itemData);
        }
    }

    public abstract class NormalItemVH extends RecyclerView.ViewHolder {
        public NormalItemVH(View itemView) {
            super(itemView);
        }

        public abstract void resetView(NormalMainItemData data);
    }
}