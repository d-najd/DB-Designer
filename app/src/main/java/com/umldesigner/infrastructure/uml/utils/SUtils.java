package com.umldesigner.infrastructure.uml.utils;

import android.util.Log;

import com.umldesigner.MainActivityListeners;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.activities.uml_activity.STable.data.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
/**
 * utils for the schema related stuff
 * @see com.umldesigner.infrastructure.uml.logic.app.SSettings
 */
public class SUtils {
    private static SUtils instance;
    
    private final SDragListeners sDragListeners;
    
    //region Data Storage Related
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     * Integer is used to prevent unnecessary casting to int
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
    private final HashSet<STableData> allTables;
    
    //endregion
    
    public Integer getNextId() {
        return appIdCounter++;
    }
    
    private SUtils() {
        Log.d("Execute", "Create Schema Settings Singleton");
        
        allViews = new HashMap<>();
        allTables = new HashSet<>();
        appIdCounter = 1;
    
        sDragListeners = MainActivityListeners.sDragListeners;
    }
    
    public static SUtils getInstance() {
        if (instance == null){
            instance = new SUtils();
        }
        return instance;
    }
    
    /**
     * puts a view to the ViewTags with given id
     */
    public void allViewTagsPut(Integer id, SObject umlObject){
        //TODO implement proper tostring method since this one doesnt seem to work
        //Log.d("Execute", "Put View in Schema Settings Singleton with parameters" + id + ", " + umlObject.toString());

        allViews.put(id, umlObject);
        if(umlObject.getData() instanceof STableData){
            allTables.add((STableData) umlObject.getData());
        }
    }
    
    public void clearViews(){
        for(SObject sObject : allViews.values()){
            sObject.destroy();
        }
        allViews.clear();
        allTables.clear();
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
