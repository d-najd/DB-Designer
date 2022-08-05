package com.umldesigner.infrastructure.uml.data.SItem;

import com.umldesigner.activities.uml_activity.views.sfk.SFKView;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObservable;
import com.umldesigner.infrastructure.uml.logic.observer.BaseObserver;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.HashSet;

import lombok.Getter;

/**
 * data field used exclusively for android
 *
 * @apiNote considered changing this to a decorator but I think it will be more painful to maintain
 * that way
 */
public class SItemData extends SItemPojo implements BaseDataInterface, BaseObservable{
    @Getter
    private Integer id;
   
    /**
     * the one who is the primary key (making the connections) holds the key, the other item doesn't
     * hold anything
     */
    @Getter
    private HashSet<SFKView> foreignKeys = new HashSet<>();
    
    public SItemData(String value, String type) {
        this.value = value;
        this.type = type;
    }
    
    public SItemData(String value, String type, Boolean isPrimaryKey){
        this.value = value;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
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
