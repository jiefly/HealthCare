package org.ecnu.chgao.healthcare.model;

/**
 * Created by chgao on 17-5-26.
 */

public class BaseModel {
    public String getDbName() {
        return this.getClass().getSimpleName();
    }

}
