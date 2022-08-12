package com.umldesigner.activities.uml_activity.dialogs.item.edit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.umldesigner.R;
import com.umldesigner.activities.uml_activity.listeners.item.dialog.EditSItemDialogListeners;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;

/**
 * the dialog used that pops up to when you create new table
 */
public class EditSItemDialog extends Dialog {
    SItemData sItemData;
    
    public EditSItemDialog(Context context, SItemData sItemData) {
        super(context);
        this.sItemData = sItemData;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_s_item_edit);
        
        Button okBtn = findViewById(R.id.okBtn);
        Button cancelBtn = findViewById(R.id.cancelBtn);

        okBtn.setOnClickListener(view -> dismiss());
        cancelBtn.setOnClickListener(view -> dismiss());
        
        setupFields();
    }
    
    private void setupFields(){
        EditSItemDialogListeners itemListener = new EditSItemDialogListeners();
    
        TextView refTable = findViewById(R.id.refTable);
        TextView refField = findViewById(R.id.refField);
        TextView onUpdate = findViewById(R.id.onUpdate);
        TextView onDelete = findViewById(R.id.onDelete);
        
        refTable.setOnClickListener(itemListener);
        refField.setOnClickListener(itemListener);
        onUpdate.setOnClickListener(itemListener);
        onDelete.setOnClickListener(itemListener);
    }
}
