package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.model.TodoModel;
import org.ecnu.chgao.healthcare.view.ToDoViewer;

/**
 * Created by chgao on 17-6-12.
 */

public class ToDoPresent extends BasePresent<ToDoViewer, TodoModel> {
    public Context getContext() {
        return mViewer.getContext();
    }
}
