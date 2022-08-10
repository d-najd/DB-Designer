package com.umldesigner.activities.uml_activity.dialogs.item.edit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.umldesigner.R;
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
    }
}
