package com.umldesigner.activities.uml_activity.STable.data;

import android.util.Log;
import android.util.Pair;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.activities.uml_activity.SFK.view.SFKView;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.infrastructure.uml.logic.app.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.app.observer.BaseObserver;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.ArrayList;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;

/**
 * data field used exclusively for android
 */
public class STableData extends STablePojo implements BaseDataInterface, BaseObservable {
    @Getter
    private final HashSet<SFKView> foreignKeys = new HashSet<>();
    
    @Getter
    private final Integer id;
   
    @Getter
    @Setter
    private RecyclerView recyclerView; //this is used for getting stuff for the sfk
    
    public STableData(Integer id, String uuid, Float x, Float y, String title, ArrayList<SItemData> items) {
        this.id = id;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.title = title;
        this.items = items;
    }
    
    public STableData(Integer id, Float x, Float y, String title){
        this.id = id;
        this.x = x;
        this.y = y;
        this.title = title;
    }
    
    /**
     * sets the x position in the data and updates the itemData n stuff
     * @param x
     */
    
    @Override
    public void setX(Float x) {
        super.setX(x);
    }
    
    @Override
    public void setY(Float y) {
        super.setY(y);
    }
    
    @Override
    public void registerObserver(BaseObserver o) {
        Log.d("Execute", "registerObserver: ");
        foreignKeys.add((SFKView) o);
    }
    
    @Override
    public void removeObserver(BaseObserver o) {
        Log.d("Execute", "removeObserver: " + o.toString());
        foreignKeys.remove((SFKView) o);
    }
    
    @Override
    public void notifyObservers() {
        Log.d("Execute", "notifyObservers: ");
        STableDataBuffer sTableDataBuffer = new STableDataBuffer();
        for(SFKView sfk : foreignKeys){
            sfk.updateObserver(this, sTableDataBuffer);
        }
        
        for(int i = 0; i < sTableDataBuffer.getCount(); i++){
            Pair<SFKView, SFKView> curValue = sTableDataBuffer.getBuffer().get(i);
            
            //destroying the observer, NOTE removeObserver is called from inside the destroy method
            assert curValue != null;
            curValue.first.destroy();
    
            //saving the new key's
            curValue.first.getSTableData().registerObserver(curValue.second);
            curValue.first.getFTableData().registerObserver(curValue.second);
        }
    }
}
