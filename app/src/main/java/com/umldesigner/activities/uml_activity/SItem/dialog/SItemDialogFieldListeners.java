package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.dialog.spinners.EditSItemDialogActionsSpinner;
import com.umldesigner.activities.uml_activity.SItem.dialog.spinners.EditSItemDialogRItemSpinner;
import com.umldesigner.activities.uml_activity.SItem.dialog.spinners.EditSItemDialogRTableSpinner;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * listener for the fields of the dialog
 */
public class SItemDialogFieldListeners implements View.OnClickListener {
    private final SItemDialog dialog;
    public SItemDialogFieldListeners(SItemDialog dialog){
        this.dialog = dialog;
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Log.d("Debug", "onClick: " + view.toString());
        
        Context c = view.getContext();
        switch (view.getId()){
            case R.id.refTable:
                List<String> tableTitles = SUtils.getInstance().getTables().stream()
                        .map(STablePojo::getTitle).collect(Collectors.toList());
    
                new EditSItemDialogRTableSpinner(tableTitles, (TextView)view, dialog);
                break;
            case R.id.refField:
                List<String> itemTitles = new ArrayList<>();
                dialog.getSelectedTableData().getItems().forEach(o -> itemTitles.add(o.getValue()));
                
                new EditSItemDialogRItemSpinner(itemTitles, (TextView)view, dialog);
                break;
            case R.id.onUpdate:
            case R.id.onDelete:
                new EditSItemDialogActionsSpinner(SItemDialog.getAllActions(view.getContext()), (TextView)view);
                break;
            default:
                Message.defErrMessage(c);
                break;
        }
    }
}
