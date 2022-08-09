package com.umldesigner.activities.uml_activity.views.table;

import android.view.View;

import com.umldesigner.activities.uml_activity.dialogs.table.edit.EditSTableDialog;
import com.umldesigner.infrastructure.uml.data.STable.STableData;

public class STableListeners implements View.OnClickListener {
    STableData sTableData;
    public STableListeners(STableData sTableData){
        this.sTableData = sTableData;
    }
    
    @Override
    public void onClick(View view) {
        EditSTableDialog dialog = new EditSTableDialog(view.getContext(), sTableData);
        dialog.show();
    }
}
