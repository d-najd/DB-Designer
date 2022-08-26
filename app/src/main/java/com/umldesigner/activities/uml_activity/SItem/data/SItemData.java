package com.umldesigner.activities.uml_activity.SItem.data;

import com.umldesigner.activities.uml_activity.STable.data.STableData;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * data field used exclusively for android
 *
 * @apiNote considered changing this to a decorator but I think it will be more painful to maintain
 * that way
 */
@NoArgsConstructor
public class SItemData extends SItemPojo {

    private SItemData(@NonNull String value, @NonNull String type, int size,
                        boolean isPrimaryKey, STablePojo table){
        this.value = value;
        this.type = type;
        this.size = size;
        this.isPrimaryKey = isPrimaryKey;
        this.table = table;
    }

    /**
     * constructs proper instance which can be used for backend stuff
     * @param value the value field in the item
     * @param type the type of the field (ex varchar, int)
     * @param size whether the field type has size and how much it is (ex varchar(50))
     * @param isPrimaryKey whether the given field is a primary key or a normal instance
     * @param table tells us which table the item belongs to
     * @return SItemData instance
     */
    public static SItemData newInstance(@NonNull String value, @NonNull String type, int size,
                                        boolean isPrimaryKey,  STablePojo table){
        return new SItemData(
                value,
                type,
                size,
                isPrimaryKey,
                table
        );
    }

    /**
     * returns a SItemData instance from SItemPojo
     * @param itemPojo the input pojo
     * @return SItemData instance of the given pojo
     */
    public static SItemData from(SItemPojo itemPojo){
         return new SItemData(
                itemPojo.getValue(),
                itemPojo.getType(),
                itemPojo.getSize() != null ? itemPojo.getSize() : 0,
                itemPojo.getIsPrimaryKey(),
                itemPojo.getTable()
        );
    }

    /**
     * returns basic instance for testing purposes, should not be used for production because it doesn't reference a table
     * which can cause problems while trying to send the code to the backend
     */
    public static SItemData newTestingInstance(String value, String type){
        return new SItemData(
                value,
                type,
                0,
                false,
                null
        );
    }

}
