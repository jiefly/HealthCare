package org.ecnu.chgao.healthcare.view;

/**
 * Created by chgao on 17-6-2.
 */

public interface RegisterViewer extends BaseViewer {
    void onRegisterFailed(String msg);
    void onRegisterSuccess();
}
