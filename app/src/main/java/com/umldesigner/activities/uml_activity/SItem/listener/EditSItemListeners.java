package com.umldesigner.activities.uml_activity.SItem.listener;


import android.util.Log;
import android.view.View;

import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

public class EditSItemListeners implements View.OnClickListener{
    SItemPojo data;
    public EditSItemListeners(SItemPojo data){
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        Log.d("Debug", "onClick: " + v.toString());
        
        //SItemDialog dialog = new SItemDialog(v.getContext(), sItemData);
        //dialog.show();
    }
}
