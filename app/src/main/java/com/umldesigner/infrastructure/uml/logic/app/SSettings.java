package com.umldesigner.infrastructure.uml.logic.app;

import android.util.Log;

import com.umldesigner.MainActivity;
import com.umldesigner.infrastructure.uml.error.ErrorTags;

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
    public final float tableWidth;
    
    //endregion
    private SSettings() {
        Log.d("Execute", "Create Schema Settings Singleton");
    
        spacing = MainActivity.spacing;
        dp = MainActivity.dp;
        
        tableWidth = spacing * 9;
        
        if(spacing == 0 || dp  == 0|| tableWidth ==0){
            Log.e(ErrorTags.APP_ERROR, "Invalid spacings set, make sure that the setup() method" +
                    "inside main activity is called before SSettings gets called");
        }
    }
    
    public static SSettings getInstance() {
        if (instance == null){
            instance = new SSettings();
        }
        return instance;
    }
}
