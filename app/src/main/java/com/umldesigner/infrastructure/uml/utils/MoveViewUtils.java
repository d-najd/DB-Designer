package com.umldesigner.infrastructure.uml.utils;

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
        x = x * SSettingsSingleton.getInstance().getSpacing();
        y = y * SSettingsSingleton.getInstance().getSpacing();
        
        Objects.requireNonNull(SSettingsSingleton.getInstance().getAllUmlObjects().get(id)).move(x, y);
    }
    
    /**
     * moves uml view to a given absolute position
     * @param id of the view
     * @param x absolute position
     * @param y absolute position
     * @see #moveView(int, float, float)
     */
    public static void moveViewAbsolute(int id, float x, float y){
        Objects.requireNonNull(SSettingsSingleton.getInstance().getAllUmlObjects().get(id)).move(x, y);
    }
}
