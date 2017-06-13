package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.bean.NormalRemindItemData;
import org.ecnu.chgao.healthcare.bean.RemindData;
import org.ecnu.chgao.healthcare.model.TodoModel;
import org.ecnu.chgao.healthcare.util.DateUtilKt;
import org.ecnu.chgao.healthcare.view.ToDoViewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chgao on 17-6-12.
 */

public class ToDoPresent extends BasePresent<ToDoViewer, TodoModel> {
    public ToDoPresent(ToDoViewer viewer) {
        this.mViewer = viewer;
        this.mModel = new TodoModel(this);
        mViewer.onRangeItemAdded(rangeRemindData2RemindItemData(mModel.getAllRemind()));
    }

    public Context getContext() {
        return mViewer.getContext();
    }

    public void handleItemClick(NormalRemindItemData data) {

    }

    public void handleAddClick() {
        RemindData remindData = new RemindData();
        remindData.setmRemindAddition("1 片");
        remindData.setmRemindName("阿姆西林");
        remindData.setmRemindDate(new Date());
        remindData.setmRemindType(RemindData.Type.MEDCIAL);
        mModel.addData(remindData);
        mViewer.onItemAdded(new NormalRemindItemData(remindData.getmRemindName(), remindData.getmRemindAddition(), DateUtilKt.dateDetail(remindData.getmRemindDate(), false, false, true, true, true, false)));
    }

    public void handleItemRemove(NormalRemindItemData data) {
        mModel.deleteRemind(data.getRemindData());
    }

    private List<NormalRemindItemData> rangeRemindData2RemindItemData(List<RemindData> datas) {
        List<NormalRemindItemData> result = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            result.add(remindDate2RemindItemData(datas.get(i), i));
        }
        return result;
    }

    private NormalRemindItemData remindDate2RemindItemData(RemindData data, int index) {
        NormalRemindItemData result = new NormalRemindItemData("", "", "");
        result.setIndex(index);
        if (data == null) {
            return result;
        }
        result.setName(data.getmRemindName());
        result.setDetail(data.getmRemindAddition());
        result.setTime(DateUtilKt.dateDetail(data.getmRemindDate(), false, false, true, true, true, false));
        result.setRemindData(data);
        return result;
    }

    public void onResume() {
    }

    public void onDestroy() {
        mModel.onDestroy();
    }
}
