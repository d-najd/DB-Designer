package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.umldesigner.Message;
import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.infrastructure.uml.error.ErrorTags;
import com.umldesigner.infrastructure.uml.utils.SUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * listener for the fields of the dialog
 */
class SItemDialogFieldListener implements View.OnClickListener {
    private final SItemDialog dialog;
    public SItemDialogFieldListener(SItemDialog dialog){
        this.dialog = dialog;
    }

    @Override
    public void onClick(View view) {
        Log.d("Debug", "onClick: " + view.toString());
        
        Context c = view.getContext();
        int id = view.getId();
        if(id == R.id.refTable){
            List<STableData> tables = new ArrayList<>();
            for (Iterator<STableData> it = SUtils.getInstance().getTableIterator(); it.hasNext(); ) {
                STableData t = it.next();
                tables.add(t);
            }
            new EditSItemDialogRTableCustomSpinner(tables, (TextView)view, dialog);
        } else if (id == R.id.refField){

            /*
                this cast should not fail because for the android side of things there should be used SItemData
                all around, altho it may be preferable to force this in the future
             */
            List<SItemData> items = dialog.getTableData().getItems();

            new EditSItemDialogRItemCustomSpinner(items, (TextView)view, dialog);
        } else if (id == R.id.onUpdate || id == R.id.onDelete) {
            new EditSItemDialogActionsCustomSpinner(SItemDialog.getAllActions(view.getContext()), (TextView) view);
        } else {
            Message.defErrMessage(c);
            Log.e(ErrorTags.APP_ERROR, "invalid pressed view id");
        }
    }
}
