package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.bean.RemindData;
import org.ecnu.chgao.healthcare.present.ToDoPresent;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.List;

/**
 * Created by chgao on 17-6-12.
 */

public class TodoModel extends BaseModel<ToDoPresent> {

    public TodoModel(ToDoPresent present) {
        super(present);
        DbUtils.createDb(mPresent.getContext(), getDbName());
    }

    public List<RemindData> getAllRemind() {
        if (DbUtils.getLiteOrm(getDbName()) == null) {
            DbUtils.createDb(mPresent.getContext(), getDbName());
        }
        return DbUtils.getQueryAll(RemindData.class, getDbName());
    }

    public void addAll(List<RemindData> datas) {
        DbUtils.insertAll(datas, getDbName());
    }

    public void addData(RemindData data) {
        DbUtils.insert(data, getDbName());
    }

    public void updateRemind(RemindData data) {
        DbUtils.update(data, getDbName());
    }

    public void deleteRemind(RemindData data) {
        DbUtils.delete(data, getDbName());
    }

    public void onDestroy() {
        DbUtils.closeDb(getDbName());
    }
}
