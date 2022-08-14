package com.umldesigner.activities.uml_activity.dialogs.item.edit.spinners;


import android.widget.TextView;

import com.umldesigner.infrastructure.uml.custom.spinner.CustomSpinnerTemplate;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

import java.util.List;

public class EditSItemDialogActionsSpinner extends CustomSpinnerTemplate<String> {
    /**
     * a spinner used for the dialog for editing the actions on the OnDelete and OnUpdate fields
     * @param data list of the table names
     * @param v the TextView that was pressed, textview specifically is needed because we want to
     *          change the name of the textview after a table has been selected
     */
    public EditSItemDialogActionsSpinner(List<String> data, TextView v) {
        super(data, v);
    }
    
    @Override
    protected Integer getWidth() {
        return (Integer) Math.round(SSettings.getInstance().getDp() * 150);
    }
    
    @Override
    public void pressed(TextView item, int itemPosition) {
        TextView parentView = (TextView) getView();
        parentView.setText(item.getText().toString());
    }
}
