package com.umldesigner.activities.uml_activity.grid;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.umldesigner.activities.uml_activity.views.SBackground;

public class SGridCreate {
    SDragListeners sDragListeners;
    
    /**
     * creates Schema Grid and sets listeners for it, the listeners are used for dragging Schema
     * objects around
     * @param container the ViewGroup that the container is located at
     */
    public SGridCreate(ViewGroup container) {
        Log.d("Execute", "Create Uml Grid");
        
        SBackground sBackground = new SBackground(container.getContext());
        sBackground.setMinimumWidth(50000);
        sBackground.setMinimumHeight(50000);
        container.addView(sBackground);
        
        sDragListeners = new SDragListeners(sBackground);
        
        ImageButton gridColliders = new ImageButton(container.getContext());
        gridColliders.setMinimumWidth(50000);
        gridColliders.setMinimumHeight(50000);
        gridColliders.setBackgroundColor(Color.parseColor("#00000000"));
        gridColliders.setTag("gridColliders");
        gridColliders.setOnDragListener(sDragListeners);
        gridColliders.setPadding(150, 150, 0, 0);
        container.addView(gridColliders);
    }
    
    public SDragListeners getListeners() {
        return sDragListeners;
    }
}