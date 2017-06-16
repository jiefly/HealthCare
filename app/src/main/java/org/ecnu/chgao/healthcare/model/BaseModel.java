package org.ecnu.chgao.healthcare.model;

import org.ecnu.chgao.healthcare.present.BasePresent;
import org.ecnu.chgao.healthcare.step.service.StepService;
import org.ecnu.chgao.healthcare.util.DbUtils;

/**
 * Created by chgao on 17-5-26.
 */

public class BaseModel<T extends BasePresent> {
    public final String TAG = this.getClass().getSimpleName();
    T mPresent;

    public BaseModel(T t) {
        mPresent = t;
        initDb();
    }

    private void initDb() {
        DbUtils.createDb(mPresent.getApplicationContext(), getDbName());
    }

    protected String getDbName() {
        return StepService.DB_NAME;
    }

}
