package com.umldesigner.infrastructure.uml.data.SItem;

import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

/**
 * data field used exclusively for android
 *
 * @apiNote considered changing this to a decorator but I think it will be more painful to maintain
 * that way
 */
public class SItemData extends SItemPojo {
    
    public SItemData(String value, String type) {
        this.value = value;
        this.type = type;
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
