package org.ecnu.chgao.healthcare.view;

/**
 * Created by chgao on 17-6-7.
 */

public interface FindPwdViewer extends RegisterViewer {
    void onFindPwdFailed(String msg);

    void onFindPwdSuccess();
}
