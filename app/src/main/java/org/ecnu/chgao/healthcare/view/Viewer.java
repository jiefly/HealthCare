package org.ecnu.chgao.healthcare.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by chgao on 17-5-26.
 */

public interface Viewer<P> {
    //void setPresent(P present);
    Context getContext();

    void showToast(String message);

    void showSnack(View view, String message);

    void showProgress(String title);

    void dismissProgress();

    void finish();

    void showAlertDialog(String title, String message, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative);
}
