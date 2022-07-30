package com.umldesigner.activities.uml_activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.umldesigner.activities.uml_activity.views.SBackground;
import com.umldesigner.infrastructure.uml.utils.MoveViewUtils;

/**
 * handles the schema listeners
 */
public class SListeners implements View.OnTouchListener, View.OnLongClickListener, View.OnDragListener {
    public SBackground sBackground;

    public SListeners(SBackground sBackground){
        this.sBackground = sBackground;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        startDrag(v);
        return false;
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
        Log.d("Execute:", "OnDrag with parameters " + v.toString() + ", " + event.toString());
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
}
