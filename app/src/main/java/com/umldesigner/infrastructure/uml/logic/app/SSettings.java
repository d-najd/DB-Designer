package com.umldesigner.infrastructure.uml.logic.app;

import android.util.Log;

import com.umldesigner.MainActivity;
import com.umldesigner.MainActivityListeners;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * a singleton which holds the schema settings and schema related stuff for the app side of things
 */
@Getter
public class SSettings {
    private static SSettings instance;
    
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
     * @apiNote use {@link #allViewTagsPut(Integer, SObject)} for putting data in the hashmap
     */
    
    private final HashMap<Integer, SObject> allViews;
    
    /**
     * holds set of all tables
     * @see #allViewTagsPut(Integer, SObject)
     */
    @Getter
    private final HashSet<STableData> allTables;
    
    /**
     * holds the data inside the uml tables
     */
    
    private final SDragListeners sDragListeners;
    //endregion
    
    public Integer getNextId() {
        return appIdCounter++;
    }
    
    private SSettings() {
        Log.d("Execute", "Create Schema Settings Singleton");
       
        allViews = new HashMap<>();
        allTables = new HashSet<>();
        appIdCounter = 1;
       
        spacing = MainActivity.spacing;
        dp = MainActivity.dp;
        
        sDragListeners = MainActivityListeners.sDragListeners;
    
        if(sDragListeners == null || spacing == 0 || dp == 0){
            Log.wtf("ERROR", "failed to instantiate SSettings, one or more of " +
                    "the fields failed to instantiate in its creation");
        }
    }
    
    public static SSettings getInstance() {
        if (instance == null){
            instance = new SSettings();
        }
        return instance;
    }
    
    /**
     * puts a view to the ViewTags with given id
     */
    public void allViewTagsPut(Integer id, SObject umlObject){
        Log.d("Execute", "Put View in Schema Settings Singleton with parameters" + id + ", " + umlObject.toString());
        
        allViews.put(id, umlObject);
        if(umlObject.getData() instanceof STableData){
            allTables.add((STableData) umlObject.getData());
        }
    }
    
    public Iterator<Map.Entry<Integer, SObject>> getViewsIterator(){
        return allViews.entrySet().iterator();
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
