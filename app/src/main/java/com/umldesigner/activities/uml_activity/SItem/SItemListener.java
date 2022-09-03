package com.umldesigner.activities.uml_activity.SItem;


import android.util.Log;
import android.view.View;

import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

/**
 * gets called when SItem is pressed, the class may need a name change?
 */
class SItemListeners implements View.OnClickListener{
    SItemPojo data;
    public SItemListeners(SItemPojo data){
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        Log.d("Debug", "onClick: " + v.toString());
        
        //SItemDialog dialog = new SItemDialog(v.getContext(), sItemData);
        //dialog.show();
    }
}
