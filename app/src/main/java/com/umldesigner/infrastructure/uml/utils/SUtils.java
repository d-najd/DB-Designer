package com.umldesigner.infrastructure.uml.utils;

import android.util.Log;
import com.umldesigner.MainActivity;
import com.umldesigner.activities.uml_activity.grid.SDragListeners;
import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.entities.SObject;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.*;

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

    private final Map<String, BaseDataInterface<?>> dataByUuid;

    /**
     * holds set of all tables located in the schema activity, items are added through the
     * {@link #viewsPut(Integer, SObject)} method
     * @see #views map containing all the views in the schema table
     */
    private final Map<Integer, STableData> tables;

    private final Map<String, STableData> tablesByUuid;

    @Getter
    private final SDragListeners dragListeners;

    //endregion

    public Integer getNextId() {
        return appIdCounter++;
    }

    private SUtils() {
        Log.d("Execute", "Create Schema Settings Singleton");

        views = new HashMap<>();
        tables = new HashMap<>();
        tablesByUuid = new HashMap<>();
        dataByUuid = new HashMap<>();
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
     * @return modifiable table map, mapped by their uuid
     */
    public Map<String, STableData> getTablesByUuid(){
        return Collections.unmodifiableMap(tablesByUuid);
    }

    /**
     * @return iterator for all table values
     */
    public Iterator<STableData> getTableIterator(){
        return tables.values().iterator();
    }

    /**
     * gets a field from {@link #views} with a given id
     * @param id the given id
     * @return Key and Value of the given id if it exists null if it doesn't
     */
    public SObject getViewById(Integer id){
        return views.get(id);
    }

    /**
     * puts a view in the {@link #views} with a given id, if the object is instance of {@link STableData} them the
     * object will also be put in {@link #tables}
     */
    public void viewsPut(Integer id, SObject umlObject){
        views.put(id, umlObject);
        if(umlObject.getData() instanceof BaseDataInterface<?>) {
            if (umlObject.getData() instanceof STableData) {
                addTable(id, (STableData) umlObject.getData());
            }
        }
    }

    /**
     * @param id id of the table
     * @return the table data if the object exists null of it does not
     */
    public STableData getTable(Integer id){
        return tables.get(id);
    }

    public STableData getTableByUuid(String id){
        return tablesByUuid.get(id);
    }

    public <T extends BaseDataInterface<?>> T getDataByUuid(String uuid, Class<T> className) {
        if (className.isInstance(dataByUuid.get(uuid))) {
            //line above checks
            @SuppressWarnings("unchecked")
            T data = (T) dataByUuid.get(uuid);
            return data;
        } else {
            throw new IllegalArgumentException("invalid class type specified");
        }
    }

    /**
     * @implNote this may cause memory leak if the table is not removed, maybe add warning if table remains for longer
     * than 10 minutes?
     * @param id id of the table
     * @param table the table itself
     */
    public void addTable(Integer id, STableData table){
        tables.put(id, table);
        tablesByUuid.put(table.getUuid(), table);
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
     * first attempts to call destroy on object if it exists in views, then remove it from views and then from tables
     * @param id given id
     */
    public void removeById(Integer id){
        if(views.get(id) != null){
            Objects.requireNonNull(views.get(id)).destroy();
        }
        views.remove(id);
        tables.remove(id);
    }
}
