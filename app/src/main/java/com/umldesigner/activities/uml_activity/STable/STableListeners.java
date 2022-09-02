package com.umldesigner.activities.uml_activity.STable;

import android.view.View;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.utils.DialogType;
import com.umldesigner.activities.uml_activity.STable.STableDialog;
import com.umldesigner.activities.uml_activity.STable.STableData;

class STableListeners implements View.OnClickListener {
    STableData sTableData;
    ViewGroup container;
    public STableListeners(STableData sTableData, ViewGroup container){
        this.sTableData = sTableData;
        this.container = container;
    }
    
    @Override
    public void onClick(View view) {
    STableDialog dialog = new STableDialog(container, sTableData, DialogType.Edit);
        dialog.show();
    }
}
