package org.ecnu.chgao.healthcare.view;

import org.ecnu.chgao.healthcare.step.bean.StepData;

import java.util.List;

/**
 * Created by chgao on 17-5-29.
 */

public interface StepViewer extends BaseViewer {
    void fillData(List<StepData> datas);
}
