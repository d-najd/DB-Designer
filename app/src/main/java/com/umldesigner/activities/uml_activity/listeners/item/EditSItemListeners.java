package com.umldesigner.activities.uml_activity.listeners.item;


import android.util.Log;
import android.view.View;

import com.umldesigner.activities.uml_activity.dialogs.item.edit.EditSItemDialog;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;

public class EditSItemListeners implements View.OnClickListener{
    SItemData sItemData;
    public EditSItemListeners(SItemData sItemData){
        this.sItemData = sItemData;
    }
    
    @Override
    public void onClick(View v) {
        Log.d("Debug", "onClick: " + v.toString());
        
        EditSItemDialog dialog = new EditSItemDialog(v.getContext(), sItemData);
        dialog.show();
    }
}
