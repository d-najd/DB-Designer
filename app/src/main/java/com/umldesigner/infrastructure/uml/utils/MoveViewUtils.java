package com.umldesigner.infrastructure.uml.utils;

import android.util.Log;

import com.umldesigner.infrastructure.uml.logic.SSettingsSingleton;

import java.util.Objects;

public class MoveViewUtils {
    
    
    /**
     * moves uml view to given position using grid spaces
     * @param id of the view
     * @param x position in grid spaces, not absolute
     * @param y position in grid spaces, not absolute
     * @see #moveViewAbsolute(int, float, float)
     */
    public static void moveView(int id, float x, float y){
        float spacedX = x * SSettingsSingleton.getInstance().getSpacing();
        float spacedY = y * SSettingsSingleton.getInstance().getSpacing();
    
        try {
            Objects.requireNonNull(SSettingsSingleton.getInstance().getViewById(id)).move(spacedX, spacedY);
        } catch (NullPointerException e){
            Log.e("ERROR", "Unable to move object with id " + id);
        }
    }
    
    /**
     * moves uml view to a given absolute position
     * @param id of the view
     * @param x absolute position
     * @param y absolute position
     * @see #moveView(int, float, float)
     */
    public static void moveViewAbsolute(int id, float x, float y){
        try {
            Objects.requireNonNull(SSettingsSingleton.getInstance().getViewById(id)).move(x, y);
        } catch (NullPointerException e){
            Log.e("ERROR", "Unable to move object with id " + id);
        }
    }
}
