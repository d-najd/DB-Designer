package com.umldesigner.activities.uml_activity.SItem.dialog;

import android.util.Log;
import android.widget.TextView;

import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.infrastructure.uml.custom.spinner.AbstractCustomSpinner;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

import java.util.List;

class EditSItemDialogRTableCustomSpinner extends AbstractCustomSpinner<STableData> {
    SItemDialog dialog;
    
    /**
     * a spinner used for the dialog for editing the SItem's, specifically for the Ref.Table field
     * @param data list of the table names
     * @param v the TextView that was pressed, textview specifically is needed because we want to
     *          change the name of the textview after a table has been selected
     * @implNote String of the name and not id for the field is used because having a table with
     * the same name or having a field with the same name inside a table is impossible so there
     * isn't a need for the id nor any other field inside the table or the table itself
     */
    public EditSItemDialogRTableCustomSpinner(List<STableData> data, TextView v, SItemDialog dialog) {
        super(data, v);
        this.dialog = dialog;
    }
    
    @Override
    protected Integer getWidth() {
        return Math.round(SSettings.getInstance().getDp() * 150);
    }
    
    @Override
    public void pressed(TextView item, int itemPosition) {
        Log.d("Debug", "pressed: " + item.toString() + itemPosition);
        
        TextView parentView = (TextView) getParentView();
        parentView.setText(item.getText().toString());
        parentView.setTag(getListData().get(itemPosition).getId());
        
        dialog.notifyTableChanged();
    }

    @Override
    protected String getTitle(STableData o) {
        return o.getTitle();
    }
}
