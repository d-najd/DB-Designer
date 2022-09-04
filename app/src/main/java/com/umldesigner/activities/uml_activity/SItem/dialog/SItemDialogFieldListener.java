package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * listener for the fields of the dialog
 */
class SItemDialogFieldListener implements View.OnClickListener {
    private final SItemDialog dialog;
    public SItemDialogFieldListener(SItemDialog dialog){
        this.dialog = dialog;
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Log.d("Debug", "onClick: " + view.toString());
        
        Context c = view.getContext();
        switch (view.getId()){
            case R.id.refTable:
                List<STableData> tables = new ArrayList<>();
                for (Iterator<Map.Entry<Integer, STableData>> it = SUtils.getInstance().getTableIterator(); it.hasNext(); ) {
                    STableData t = it.next().getValue();
                    tables.add(t);
                }
                new EditSItemDialogRTableCustomSpinner(tables, (TextView)view, dialog);
                break;
            case R.id.refField:
                List<String> itemTitles = new ArrayList<>();
                STableData re = (STableData) dialog.getTableData();
                List<SItemData> items = (List<SItemData>) dialog.getTableData().getItems();

                new EditSItemDialogRItemCustomSpinner(items, (TextView)view, dialog);
                break;
            case R.id.onUpdate:
            case R.id.onDelete:
                new EditSItemDialogActionsCustomSpinner(SItemDialog.getAllActions(view.getContext()), (TextView)view);
                break;
            default:
                Message.defErrMessage(c);
                break;
        }
    }
}
