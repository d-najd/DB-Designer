package com.umldesigner.activities.uml_activity.listeners.item.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.EditSItemDialog;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners.EditSItemDialogActionsSpinner;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners.EditSItemDialogRItemSpinner;
import com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners.EditSItemDialogRTableSpinner;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditSItemDialogListeners implements View.OnClickListener {
    private EditSItemDialog dialog;
    public EditSItemDialogListeners(EditSItemDialog dialog){
        this.dialog = dialog;
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Context c = view.getContext();
        switch (view.getId()){
            case R.id.refTable:
                List<String> tableTitles = SSettings.getInstance().getAllTables().stream()
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
                new EditSItemDialogActionsSpinner(EditSItemDialog.getAllActions(view.getContext()), (TextView)view);
                break;
            default:
                Message.defErrMessage(c);
                break;
        }
    }
}
