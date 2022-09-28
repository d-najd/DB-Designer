package com.umldesigner.activities.uml_activity.table;

import android.view.View;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.utils.DialogType;

class STableListener implements View.OnClickListener {
    STableData sTableData;
    ViewGroup container;
    public STableListener(STableData sTableData, ViewGroup container){
        this.sTableData = sTableData;
        this.container = container;
    }
    
    @Override
    public void onClick(View view) {
    STableDialog dialog = new STableDialog(container, sTableData, DialogType.Edit);
        dialog.show();
    }
}
