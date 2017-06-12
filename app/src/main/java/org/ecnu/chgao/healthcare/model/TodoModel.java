package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.bean.RemindData;
import org.ecnu.chgao.healthcare.present.ToDoPresent;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.List;

/**
 * Created by chgao on 17-6-12.
 */

public class TodoModel extends BaseModel {
    private ToDoPresent mPresent;

    public TodoModel(ToDoPresent present) {
        mPresent = present;
    }

    public List<RemindData> getAllRemind() {
        if (DbUtils.getLiteOrm() == null) {
            DbUtils.createDb(mPresent.getContext(), "Remind");
        }
        return DbUtils.getQueryAll(RemindData.class);
    }

    public void updateRemind(RemindData data) {
        DbUtils
    }

    public void onDestroy() {
        DbUtils.closeDb();
    }
}
