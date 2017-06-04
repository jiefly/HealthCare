package org.ecnu.chgao.healthcare.view;

import org.ecnu.chgao.healthcare.present.MainPresent;

/**
 * Created by chgao on 17-5-29.
 */

public interface MainViewer extends Viewer<MainPresent> {
    void setStepCount(int totalCount, int currentCount);

    void bindStepService();
}
