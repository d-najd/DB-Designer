package com.umldesigner.infrastructure.uml.logic;

import android.util.Log;

import com.umldesigner.MainActivity;
import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * a singleton which holds the schema settings and schema related stuff
 */
@Getter
public class SSettingsSingleton {
    private static SSettingsSingleton instance;
    
    //region general values
    private final float dp;
    private final float spacing;
    //endregion
   
    //region elevations
    public static final float TABLE_ELEVATION = 0.5f;
    public static final float ARROW_HEAD_ELEVATION = 0.12f;
    public static final float ARROW_BACK_ELEVATION = 0.11f;
    public static final float ARROW_BODY_ELEVATION = 0.10f;
    
    //endregion
    
    //region sizes
    public static float TABLE_WIDTH = getInstance().spacing * 9;
    
    //endregion
    
    //region Data Storage Related
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     */
    @Getter(AccessLevel.NONE)
    private Integer appIdCounter;
    /**
     * the tags of all existing views/constraint layouts including their data's
     */
    private final HashMap<Integer, SObject> allViews;
    
    /**
     * holds the data inside the uml tables
     */
    
    private final SListeners sListeners;
    //endregion
    
    public Integer getNextId() {
        return appIdCounter++;
    }
    
    private SSettingsSingleton() {
        Log.d("Execute", "Create Schema Settings Singleton");
       
        allViews = new HashMap<>();
        appIdCounter = 1;
       
        spacing = MainActivity.spacing;
        dp = MainActivity.dp;
        
        sListeners = MainActivity.listeners;
    }
    
    public static SSettingsSingleton getInstance() {
        if (instance == null){
            instance = new SSettingsSingleton();
        }
        return instance;
    }
    
    /**
     * puts a view to the ViewTags with given id
     */
    public void allViewTagsPut(Integer id, SObject umlObject){
        Log.d("Execute", "Put View in Schema Settings Singleton with parameters" + id + ", " + umlObject.toString());
        
        allViews.put(id, umlObject);
    }
    
    /**
     * gets a field from {@link #allViews} with a given id
     * @param id the given id
     * @return Key and Value of the given id if it exists null if it doesn't
     */
     public SObject getViewById(Integer id){
        return allViews.get(id);
     }
}
