package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.R;
import org.ecnu.chgao.healthcare.alarmmanager.AlarmManagerUtil;
import org.ecnu.chgao.healthcare.bean.MedicalNotificationEditItemData;
import org.ecnu.chgao.healthcare.bean.NormalRemindItemData;
import org.ecnu.chgao.healthcare.bean.RemindData;
import org.ecnu.chgao.healthcare.model.TodoModel;
import org.ecnu.chgao.healthcare.view.ToDoViewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chgao on 17-6-12.
 */

public class ToDoPresent extends BasePresent<ToDoViewer, TodoModel> {
    private RemindData currentData;
    private int currentIndex;

    public ToDoPresent(ToDoViewer viewer) {
        this.mViewer = viewer;
        this.mModel = new TodoModel(this);
        mViewer.onRangeItemAdded(rangeRemindData2RemindItemData(mModel.getAllRemind()));
    }

    public Context getContext() {
        return mViewer.getContext();
    }

    public void handleItemClick(NormalRemindItemData data) {
        currentData = data.getRemindData();
        currentIndex = data.getIndex();
    }

    public void handleItemRemove(NormalRemindItemData data) {
        cancelAlarm(data.getRemindData());
        mModel.deleteRemind(data.getRemindData());
    }

    private void cancelAlarm(RemindData remindData) {

    }

    public void addItem(RemindData remindData) {
        setAlarm(remindData);
        addDataToDB(remindData);
        mViewer.onItemAdded(remindDate2RemindItemData(remindData, -1));
    }

    private void setAlarm(RemindData remindData) {
        Map<Integer, Long> times = remindData.getRemindTime();
        for (int key : times.keySet()) {
            AlarmManagerUtil.setAlarmByDetailTime(mViewer.getContext(), "吃药提醒", "请服用" + remindData.getmRemindAddition() + remindData.getmRemindName(), times.get(key), key, 2);
        }
    }

    public void changeItem(RemindData dstData) {
        //cancel data before change
        cancelAlarm(currentData);
        //reset data changed
        changeAlarm(dstData);
        dstData.setId(currentData.getId());
        mModel.updateRemind(dstData);
        mModel.updateRemind(dstData);
        mViewer.onItemChanged(currentIndex, remindDate2RemindItemData(dstData, currentIndex));
    }

    private void changeAlarm(RemindData dstData) {

    }

    private void addDataToDB(RemindData remindData) {
        mModel.addData(remindData);
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
        result.setTime(data.getmShowRemindTime());
        for (MedicalNotificationEditItemData medicalNotificationEditItemData : data.getmMedicalNotificationData()) {
            if (medicalNotificationEditItemData.getmType() == MedicalNotificationEditItemData.Type.INTEVAL) {
                result.setExtention("每" + mViewer.getContext().getString(R.string.hour_format, medicalNotificationEditItemData.getValue()) + "服用一次");
                break;
            }
        }
        result.setRemindData(data);
        return result;
    }

    public RemindData generateRemindData(List<MedicalNotificationEditItemData> datas) {
        RemindData result = new RemindData();
        result.setmCreateDate(new Date());
        for (MedicalNotificationEditItemData data : datas) {
            switch (data.getmType()) {
                case MEDCIAL_NAME:
                    result.setmRemindName(data.getValue());
                    break;
                case PILL_PERTIME:
                    result.setmRemindAddition(mViewer.getContext().getString(R.string.pill_format, Integer.parseInt(data.getValue())));
                    break;
                case START_TIME:
                    result.setmShowRemindTime(data.getValue());
                    String[] time = data.getValue().split(":");
                    Date date = new Date();
                    date.setHours(Integer.parseInt(time[0]));
                    date.setMinutes(Integer.parseInt(time[1]));
                    result.setmRemindDate(date);
                    break;
            }
        }
        try {
            result.setExtendByItemData(datas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void onResume() {
    }

    public void onDestroy() {
        mModel.onDestroy();
    }
}
