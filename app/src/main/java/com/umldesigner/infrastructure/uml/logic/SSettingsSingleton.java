package com.umldesigner.infrastructure.uml.logic;

import android.util.Log;

import com.umldesigner.MainActivity;
import com.umldesigner.activities.uml_activity.SListeners;
import com.umldesigner.infrastructure.uml.data.STable.STableData;
import com.umldesigner.infrastructure.uml.entities.SObject;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * a singleton which holds the schema settings and schema related stuff
 */
@Getter
public class SSettingsSingleton {
    private static SSettingsSingleton instance;

    public final float TABLE_ELEVATION = 0.5f;
    public final float ARROW_HEAD_ELEVATION = 0.12f;
    public final float ARROW_BACK_ELEVATION = 0.11f;
    public final float ARROW_BODY_ELEVATION = 0.10f;
    private final float spacing;
    /**
     * uuid counter used EXCLUSIVELY for the android app, (the server has a separate infrastructure)
     */
    @Getter(AccessLevel.NONE)
    private Integer appIdCounter;
    
    /**
     * the tags of all existing views/constraint layouts
     */
    
    private final HashMap<Integer, SObject> allUmlObjects;
    
    /**
     * holds the data inside the uml tables
     */
    
    private final ArrayList<STableData> umlTablesData;
    private final SListeners sListeners;
   
    private final float dp;
    
    public Integer getNextId() {
        return appIdCounter++;
    }
    
    private SSettingsSingleton() {
        Log.d("Execute", "Create Schema Settings Singleton");
       
        allUmlObjects = new HashMap<>();
        umlTablesData = new ArrayList<>();
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
        
        allUmlObjects.put(id, umlObject);
    }
}
