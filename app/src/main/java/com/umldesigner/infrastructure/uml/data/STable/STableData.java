package com.umldesigner.infrastructure.uml.data.STable;

import androidx.recyclerview.widget.RecyclerView;

import com.umldesigner.activities.uml_activity.views.sfk.SFKView;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.data.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import java.util.ArrayList;
import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;

/**
 * data field used exclusively for android
 */
public class STableData extends STablePojo implements BaseDataInterface, BaseObservable {
    
    /**
     * the one who is the primary key (making the connections) holds the key, the other item doesn't
     * hold anything
     */
    @Getter
    private HashSet<SFKView> foreignKeys = new HashSet<>();
    
    @Getter
    private Integer id;
   
    @Getter
    @Setter
    private RecyclerView recyclerView; //this is used for getting stuff for the sfk
    
    public STableData(Integer id, Float x, Float y, String title, ArrayList<SItemData> items) {
        this.id = id;
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
        foreignKeys.add((SFKView) o);
    }
    
    @Override
    public void removeObserver(BaseObserver o) {
        foreignKeys.remove((SFKView) o);
        ((SFKView) o).destroy();
    }
    
    @Override
    public void notifyObservers() {
        for(SFKView sfk : foreignKeys){
            sfk.updateObserver(this, null);
        }
    }
}
