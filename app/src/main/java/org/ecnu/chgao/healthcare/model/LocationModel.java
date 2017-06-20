package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.present.LocationPresent;
import org.ecnu.chgao.healthcare.present.LoginPresent;
import org.ecnu.chgao.healthcare.util.DateUtilKt;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chgao on 17-6-20.
 */

public class LocationModel extends BaseModel<LocationPresent> {
    public LocationModel(LocationPresent present) {
        super(present);
        DbUtils.createDb(present.getApplicationContext(), getDbName());
    }

    public Map<String, List<LocationUploadBean>> getAllLocations() {
        List<LocationUploadBean> all = DbUtils.getQueryAll(LocationUploadBean.class, getDbName());
        Map<String, List<LocationUploadBean>> result = new HashMap<>();
        String date;
        for (LocationUploadBean bean : all) {
            date = DateUtilKt.time2DateString(bean.getmHappendTime());
            if (!result.containsKey(date)) {
                result.put(date, new ArrayList<LocationUploadBean>());
            }
            result.get(date).add(bean);
        }
        return result;
    }

    public List<LocationUploadBean> getTodayLocation() {
        return getLocationsByTime(System.currentTimeMillis());
    }

    public List<LocationUploadBean> getLocationsByTime(long time) {
        String date = DateUtilKt.time2DateString(time);
        List<LocationUploadBean> result = DbUtils.getQueryByWhere(LocationUploadBean.class, "today", new String[]{date}, getDbName());
        Collections.sort(result, new Comparator<LocationUploadBean>() {
            @Override
            public int compare(LocationUploadBean o1, LocationUploadBean o2) {
                return (int) (o1.getmHappendTime() - o2.getmHappendTime());
            }
        });
        return result;
    }
}
