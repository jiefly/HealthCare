package org.ecnu.chgao.healthcare.model;

import android.content.Context;

import org.ecnu.chgao.healthcare.step.bean.StepData;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.DbUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chgao on 17-5-29.
 */

public class StepModel extends BaseModel {
    public List<StepData> getAllData(Context context) {
        if (DbUtils.getLiteOrm(StepService.DB_NAME) == null) {
            DbUtils.createDb(context, "jingzhi");
        }
        List<StepData> result = new ArrayList<>();
        result.addAll(DbUtils.getQueryAll(StepData.class, StepService.DB_NAME));
        return result;
    }
}
