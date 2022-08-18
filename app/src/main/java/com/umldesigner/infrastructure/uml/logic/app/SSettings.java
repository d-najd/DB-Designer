package com.umldesigner.infrastructure.uml.logic.app;

import android.util.Log;

import com.umldesigner.MainActivity;

import lombok.Getter;

/**
 * singleton which holds stuff like sizes of the tables, elevations etc
 * singleton because we need to get dp from the main activity to calculate sizes
 * @see com.umldesigner.infrastructure.uml.utils.SUtils
 */
@Getter
public class SSettings {
    private static SSettings instance;
   
    private final float dp;
    private float spacing;
    
    //region elevations
    public static final float TABLE_ELEVATION = 0.5f;
    public static final float ARROW_HEAD_ELEVATION = 0.12f;
    public static final float ARROW_BACK_ELEVATION = 0.11f;
    public static final float ARROW_BODY_ELEVATION = 0.10f;
    
    //endregion
    
    //region sizes
    @Getter
    public float tableWidth = spacing * 9;
    
    //endregion
    private SSettings() {
        Log.d("Execute", "Create Schema Settings Singleton");
    
        spacing = MainActivity.spacing;
        dp = MainActivity.dp;
    }
    
    public static SSettings getInstance() {
        if (instance == null){
            instance = new SSettings();
        }
        return instance;
    }
}
