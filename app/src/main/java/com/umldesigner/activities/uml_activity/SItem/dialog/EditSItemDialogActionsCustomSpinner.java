package com.umldesigner.activities.uml_activity.SItem.dialog;


import android.util.Log;
import android.widget.TextView;
import com.umldesigner.infrastructure.uml.custom.spinner.AbstractCustomSpinner;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

import java.util.List;

class EditSItemDialogActionsCustomSpinner extends AbstractCustomSpinner<String> {
    /**
     * a spinner used for the dialog for editing the actions on the OnDelete and OnUpdate fields
     * @param data list of the table names
     * @param v the TextView that was pressed, textview specifically is needed because we want to
     *          change the name of the textview after a table has been selected
     */
    public EditSItemDialogActionsCustomSpinner(List<String> data, TextView v) {
        super(data, v);
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
    }
}
