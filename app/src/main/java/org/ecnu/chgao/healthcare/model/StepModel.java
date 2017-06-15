package org.ecnu.chgao.healthcare.model;

import android.content.Context;

import org.ecnu.chgao.healthcare.bean.LocationUploadBean;
import org.ecnu.chgao.healthcare.bean.StepUploadBean;
import org.ecnu.chgao.healthcare.bean.UploadPackage;
import org.ecnu.chgao.healthcare.step.bean.StepData;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chgao on 17-5-29.
 */

public class StepModel extends BaseModel {
    private static final String TAG = "StepModel";

    public List<StepData> getAllData(Context context) {
        if (DbUtils.getLiteOrm(StepService.DB_NAME) == null) {
            DbUtils.createDb(context, StepService.DB_NAME);
        }
        List<StepData> result = new ArrayList<>();
        result.addAll(DbUtils.getQueryAll(StepData.class, StepService.DB_NAME));
        return result;
    }

    public void storeStepUploadBean(StepUploadBean stepUploadBean) {
        generateAndStoreUploadPackage(stepUploadBean);
    }

    private void generateAndStoreUploadPackage(StepUploadBean stepUploadBean) {
        List<LocationUploadBean> locations = DbUtils.getQueryAll(LocationUploadBean.class, StepService.DB_NAME);
        //remove locations in db
        DbUtils.deleteAll(LocationUploadBean.class, StepService.DB_NAME);
        UploadPackage uploadPackage = new UploadPackage(System.currentTimeMillis());
        uploadPackage.setStep(stepUploadBean, locations);
        DbUtils.insert(uploadPackage, StepService.DB_NAME);
    }

}
