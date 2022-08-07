package com.umldesigner.infrastructure.uml.logic;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.umldesigner.activities.uml_activity.SDragListeners;
import com.umldesigner.activities.uml_activity.views.arrow_old.SArrowPart;
import com.umldesigner.activities.uml_activity.views.arrow_old.SArrowViewOld;
import com.umldesigner.activities.uml_activity.views.table.STableView;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * rework this so it uses builder and uses enum instead of String type
 * @deprecated
 */
public class SObjectFactory {
    private final SDragListeners sDragListeners;
    private final ViewGroup container;
   
    public SDragListeners getUmlListeners(){
        return sDragListeners;
    }
    
    public SObjectFactory(ViewGroup container, SDragListeners sDragListeners){
        this.sDragListeners = sDragListeners;
        this.container = container;
    }
    
    public SObject create(String type, float[] positions){
        return create(type, null, positions, null, null);
    }
    
    public SObject create(String type, String title, float[] positions){
        return create(type, title, positions, null, null);
    }
    
    public SObject create(String type, String title, float[] positions, ArrayList<Object> objects){
        return create(type, title, positions, null,  objects);
    }
    
    public SObject create(String type, float[] positions, View[] views){
        return create(type, null, positions, views, null);
    }
    
    @SuppressLint("ClickableViewAccessibility")
    public SObject create(String type, String title, float[] positions, View[] views, ArrayList<Object> objects){
        switch (type.toLowerCase(Locale.ROOT)){
            case "arrow":
                return createUmlArrow(positions, views);
            case "umltable":
                return createUmlTable(title, positions, objects);
            default:
                    throw new IllegalStateException("invalid type: " + type);
        }
    }
    
    @NonNull
    private SArrowViewOld createUmlArrow(float[] positions, View[] views) {
        if(views != null && views.length > 2)
            throw new IllegalStateException("creating arrows requires at least 1 and no more than 3 views in");
        if(positions.length != 4)
            throw new IllegalStateException("creating arrows requires 4 positions, xStart, xEnd, yStart, yEnd in");
        if(sDragListeners == null)
            throw new IllegalStateException("creating arrows requires umlListeners");
        View view1 = views != null && views.length > 0 ? views[0] : null;
        View view2 = view1 != null && views.length > 1 ? views[1] : null;
        return new SArrowViewOld(container, positions[0], positions[1],
                positions[2], positions[3], (SArrowPart) view1, (SArrowPart) view2,
                sDragListeners);
    }
    
    @NonNull
    private STableView createUmlTable(String title, float[] positions, ArrayList<Object> objects) {
        return null;
    }
}
