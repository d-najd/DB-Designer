package com.umldesigner.activities.uml_activity.SItem.listener;


import android.util.Log;
import android.view.View;

import com.umldesigner.activities.uml_activity.SItem.data.SItemData;

public class EditSItemListeners implements View.OnClickListener{
    SItemData sItemData;
    public EditSItemListeners(SItemData sItemData){
        this.sItemData = sItemData;
    }
    
    @Override
    public void onClick(View v) {
        Log.d("Debug", "onClick: " + v.toString());
        
        //SItemDialog dialog = new SItemDialog(v.getContext(), sItemData);
        //dialog.show();
    }
}
