package org.ecnu.chgao.healthcare.view;

import org.ecnu.chgao.healthcare.bean.NormalRemindItemData;
import org.ecnu.chgao.healthcare.present.ToDoPresent;

import java.util.List;

/**
 * Created by chgao on 17-6-12.
 */

public interface ToDoViewer extends Viewer<ToDoPresent> {
    void onItemAdded(NormalRemindItemData data);

    void onRangeItemAdded(List<NormalRemindItemData> datas);

    void onItemChanged(int index, NormalRemindItemData data);
}
