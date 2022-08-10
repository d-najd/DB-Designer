package com.umldesigner.activities.uml_activity.grid;

import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.umldesigner.activities.uml_activity.views.SBackground;
import com.umldesigner.infrastructure.uml.utils.MoveViewUtils;

/**
 * handles the schema listeners
 */
public class SDragListeners implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener {
    public SBackground sBackground;

    public SDragListeners(SBackground sBackground){
        this.sBackground = sBackground;
    }

    @Override
    public boolean onLongClick(View v) {
        startDrag(v);
        return false;
    }

    private void startDrag(View v){
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(v);
        ClipData.Item item = new ClipData.Item(v.getId() + "");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(v.getId() + "", mimeTypes, item);

        v.startDragAndDrop(data, mShadow, null, 0);
    }

    @Override
    public boolean onDrag(View v, DragEvent event)
    {
        Log.d("Execute", "OnDrag with parameters " + v.toString() + ", " + event.toString());
        String clipData;
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;
            case DragEvent.ACTION_DROP:
                clipData = event.getClipDescription().getLabel().toString();
                Integer int_clipData = Integer.parseInt(clipData);
                //moving the UmlObject
                MoveViewUtils.moveViewAbsolute(int_clipData, event.getX(), event.getY());
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public void onClick(View view) {
        startDrag(view);
    }
}
