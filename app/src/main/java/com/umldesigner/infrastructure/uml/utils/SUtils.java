package com.umldesigner.infrastructure.uml.utils;

import android.util.Log;

import com.umldesigner.MainActivity;
import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.*;

import lombok.AccessLevel;
import lombok.Getter;

@Getter
/**
 * utils for the schema related stuff
 * @see com.umldesigner.infrastructure.uml.logic.app.SSettings
 */
public class SUtils {
    private static SUtils instance;


    //region Data Storage Related
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     * Integer is used to prevent unnecessary casting to int
     */
    @Getter(AccessLevel.NONE)
    private Integer appIdCounter;
    /**
     * holds map of all the views located in the schema activity
     * @see #viewsPut(Integer, SObject)  for putting data in the map
     * @see #views set containing all the tables
     */

    private final Map<Integer, SObject> views;

    /**
     * holds set of all tables located in the schema activity, items are added through the
     * {@link #viewsPut(Integer, SObject)} method
     * @see #views map containing all the views in the schema table
     */
    private final Set<STableData> tables;

    private SDragListeners dragListeners;

    //endregion

    public Integer getNextId() {
        return appIdCounter++;
    }

    private SUtils() {
        Log.d("Execute", "Create Schema Settings Singleton");

        views = new HashMap<>();
        tables = new HashSet<>();
        appIdCounter = 1;

        dragListeners = MainActivity.getDragListeners();
    }

    /**
     * can't make the utils static because views cant be stored as a static
     * @return instance of sUtils
     */
    public static SUtils getInstance() {
        if (instance == null){
            instance = new SUtils();
        }
        return instance;
    }

    /**
     * puts a view in the {@link #views} with a given id, if the object is instance of {@link STableData} them the
     * object will also be put in {@link #tables}
     * @see #getViews()
     * @see #getTables()
     */
    public void viewsPut(Integer id, SObject umlObject){
        views.put(id, umlObject);
        if(umlObject.getData() instanceof STableData){
            tables.add((STableData) umlObject.getData());
        }
    }

    /**
     * clears all of the {@link #views} and {@link #tables} and runs the {@link SObject#destroy()} method on all objects
     * within the views collection
     */
    public void clearViews(){
        for(SObject sObject : views.values()){
            sObject.destroy();
        }
        views.clear();
        tables.clear();
    }

    /**
     * @return unmodifiable set of all the tables
     * @see #getViews() for collection containing all views in the activity sorted by their id
     * @see #viewsPut(Integer, SObject) for more info
     */
    public Set<STableData> getTables() {
        return Collections.unmodifiableSet(tables);
    }

    /**
     * returns unmodifiable map of all the views in the uml activity
     * @see
     * @return
     */
    public Map<Integer, SObject> getViews() {
        return Collections.unmodifiableMap(views);
    }

    /**
     * gets a field from {@link #views} with a given id
     * @param id the given id
     * @return Key and Value of the given id if it exists null if it doesn't
     */
    public SObject getViewById(Integer id){
        return views.get(id);
    }
}
