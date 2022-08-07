package com.umldesigner.activities.uml_activity.views.table;

import android.view.View;

import com.umldesigner.activities.uml_activity.dialogs.table.EditSTableDialog;

public class STableListeners implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        EditSTableDialog dialog = new EditSTableDialog(view.getContext());
        dialog.show();
    }
}
