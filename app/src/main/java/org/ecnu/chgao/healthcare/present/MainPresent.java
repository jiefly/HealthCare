package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.model.MainModel;
import org.ecnu.chgao.healthcare.view.MainViewer;

/**
 * Created by chgao on 17-5-29.
 */

public class MainPresent extends BasePresent {
    public MainPresent(MainViewer viewer, MainModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public MainPresent(MainViewer viewer) {

        this.mViewer = viewer;
    }
}
