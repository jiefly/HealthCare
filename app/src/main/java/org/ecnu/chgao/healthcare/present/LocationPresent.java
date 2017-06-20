package org.ecnu.chgao.healthcare.present;

import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.model.LocationModel;
import org.ecnu.chgao.healthcare.view.LocationViewer;

import java.util.List;
import java.util.Map;

/**
 * Created by chgao on 17-6-20.
 */

public class LocationPresent extends BasePresent<LocationViewer, LocationModel> {
    public LocationPresent(LocationViewer viewer) {
        this.mViewer = viewer;
        this.mModel = new LocationModel(this);
    }

    public Map<String, List<LocationUploadBean>> getAllDatas() {
        return mModel.getAllLocations();
    }

    public List<LocationUploadBean> getTodayLocations() {
        return mModel.getTodayLocation();
    }

    public List<LocationUploadBean> getLocationsByTime(long time) {
        return mModel.getLocationsByTime(time);
    }
}
