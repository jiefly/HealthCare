package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.model.BaseModel;
import org.ecnu.chgao.healthcare.view.Viewer;

/**
 * Created by chgao on 17-5-26.
 */

public class BasePresent<V extends Viewer, M extends BaseModel> {
    protected static boolean DEBUG = false;
    protected V mViewer;
    protected M mModel;

    public Context getApplicationContext() {
        return mViewer.getContext().getApplicationContext();
    }
}
