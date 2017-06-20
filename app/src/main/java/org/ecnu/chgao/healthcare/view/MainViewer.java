package org.ecnu.chgao.healthcare.view;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.ecnu.chgao.healthcare.present.MainPresent;

/**
 * Created by chgao on 17-5-29.
 */

public interface MainViewer extends BaseViewer<MainPresent> {
    void setStepCount(int totalCount, int currentCount);

    void startLocation(AMapLocationClientOption option, AMapLocationListener listener);

    void stopLocation();
    void bindStepService();

    void jumpToLogin();
}
