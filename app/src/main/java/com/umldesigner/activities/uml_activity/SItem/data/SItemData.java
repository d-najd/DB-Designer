package com.umldesigner.activities.uml_activity.SItem.data;

import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import lombok.NoArgsConstructor;

/**
 * data field used exclusively for android
 *
 * @apiNote considered changing this to a decorator but I think it will be more painful to maintain
 * that way
 */
@NoArgsConstructor
public class SItemData extends SItemPojo {
    
    public SItemData(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public SItemData(String value, String type, Integer size){
        this.value = value;
        this.type = type;
        this.size = size;
    }
    
    /**
     * todo find a better way to do this
     * creates data from the pojo
     */
    public SItemData (SItemPojo sItemPojo){
        this.value = sItemPojo.getValue();
        this.type = sItemPojo.getType();
        this.isPrimaryKey = sItemPojo.getIsPrimaryKey();
        this.table = sItemPojo.getTable();
        this.uuid = sItemPojo.getUuid();
    }
    
    public SItemData(String value, String type, Boolean isPrimaryKey){
        this.value = value;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }
}
