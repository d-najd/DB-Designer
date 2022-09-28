package com.umldesigner.activities.uml_activity.table_item;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.table_item.dialog.SItemDialog;
import com.umldesigner.infrastructure.uml.utils.DialogType;

/**
 * gets called when SItem is pressed, the class may need a name change?
 */
class SItemListener implements View.OnClickListener{
    private final ViewGroup container;
    SItemData data;
    public SItemListener(ViewGroup container, SItemData data){
        this.data = data;
        this.container = container;
    }

    @Override
    public void onClick(View v) {
        Log.d("Execute", "onClick: " + v.toString());

        SItemDialog dialog = new SItemDialog(container, data, data.getTable(), DialogType.Edit);
        dialog.show();
    }
}
