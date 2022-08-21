package com.umldesigner.infrastructure.uml.utils;

import androidx.annotation.NonNull;

import com.umldesigner.infrastructure.uml.entities.Movable;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;

public class MovableUtils {
    
    // Noninstantiable utility class
    private MovableUtils(){
        // Suppress default constructor for noninstantiablity
        throw new AssertionError();
    }
    
    /**
     * moves uml view to given position using grid spaces
     * @param movable the object that we want to move
     * @param x position in grid spaces, not absolute
     * @param y position in grid spaces, not absolute
     * @implNote notifying that an object has been moved should be done in the view itself
     * @see #moveViewAbsolute(Movable, float, float)
     */
    public static void moveView(@NonNull Movable movable, float x, float y){
        float spacedX = x * SSettings.getInstance().getSpacing();
        float spacedY = y * SSettings.getInstance().getSpacing();
        
        movable.move(spacedX, spacedY);
    }
    
    /**
     * moves uml view to a given absolute position
     * @param movable the object that we want to move
     * @param x absolute position
     * @param y absolute position
     * @implNote notifying that an object has been moved should be done in the view itself
     * @see #moveView(Movable, float, float)
     */
    public static void moveViewAbsolute(@NonNull Movable movable, float x, float y){
        movable.move(x, y);
    }
}
