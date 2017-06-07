package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.model.StepModel;
import org.ecnu.chgao.healthcare.view.StepViewer;

/**
 * Created by chgao on 17-5-29.
 */

public class StepPresent extends BasePresent<StepViewer, StepModel> {
    public StepPresent(StepViewer viewer) {
        this.mViewer = viewer;
        this.mModel = new StepModel();
    }

    public void fetchData() {
        mViewer.fillData(mModel.getAllData(mViewer.getContext()));
    }

}
