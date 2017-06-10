package org.ecnu.chgao.healthcare.present;

import android.content.Context;

import org.ecnu.chgao.healthcare.bean.EditCardItemData;
import org.ecnu.chgao.healthcare.model.EditCardModel;
import org.ecnu.chgao.healthcare.view.EditCardViewer;

import java.util.List;

/**
 * Created by chgao on 17-6-10.
 */

public class EditCardPresent extends BasePresent<EditCardViewer, EditCardModel> {
    public EditCardPresent(EditCardViewer viewer) {
        this.mViewer = viewer;
        mModel = new EditCardModel(this);
    }

    public List<EditCardItemData> getAllCard() {
        return mModel.getAllCard();
    }

    public void updateCardState() {
        mModel.update();
    }

    public void toogleEnable(int index) {
        mModel.toggleEnable(index);
    }

    public Context getContext() {
        return mViewer.getContext();
    }
}
