package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.model.MainModel;
import org.ecnu.chgao.healthcare.step.UpdateUiCallBack;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.view.MainViewer;

/**
 * Created by chgao on 17-5-29.
 */

public class MainPresent extends BasePresent<MainViewer, MainModel> implements UpdateUiCallBack {
    public MainPresent(MainViewer viewer, MainModel model) {
        this.mModel = model;
        this.mViewer = viewer;
    }

    public MainPresent(MainViewer viewer) {
        this.mViewer = viewer;
        mModel = new MainModel(mViewer.getContext());
    }

    public void onStepServiceConnected(StepService service) {
        service.registerCallback(this);
    }

    public void onStepServiceDisconnected() {
    }

    public void updateTask(int step) {
        mModel.updateTodayTask(step);
    }

    public void updateCurrentStep(int step) {
        mModel.updateCurrentStep(step);
    }

    public int getCurrentStep() {
        return mModel.getCurrentStep();
    }

    public int getTaskStep() {
        return mModel.getTodayTask();
    }

    @Override
    public void updateUi(int stepCount) {
        updateCurrentStep(stepCount);
        mViewer.setStepCount(mModel.getTodayTask(), stepCount);
    }
}
