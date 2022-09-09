package com.umldesigner.activities.uml_activity.SItem;

import com.umldesigner.activities.uml_activity.STable.STableData;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

/**
 * data field used exclusively for android
 *
 * @apiNote considered changing this to a decorator, but I think it will be more painful to maintain
 * that way
 */
@NoArgsConstructor
public class SItemData implements BaseDataInterface<SItemPojo>{
    private int id;

    //I have no idea why I can't set this to final
    @Getter
    private SItemPojo pojo;

    @Getter
    private STableData table;

    private SItemData(String uuid, @NonNull String value, @NonNull String type, int size,
                        boolean isPrimaryKey, STableData table){
        this.id = SUtils.getInstance().getNextId();

        String tableUuid = null;
        if(table != null){
            tableUuid = table.getPojo().getUuid();
        }
        STablePojo tablePojo = null;
        if (table != null){
            tablePojo = table.getPojo();
        }
        this.table = table;
        this.pojo = new SItemPojo(uuid, type, value, size, tableUuid, tablePojo, isPrimaryKey);
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
    public static SItemData newInstance(String uuid, @NonNull String value, @NonNull String type, int size,
                                        boolean isPrimaryKey, STableData table){
        return new SItemData(
                uuid,
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
                 itemPojo.getUuid(),
                 itemPojo.getValue(),
                 itemPojo.getType(),
                 itemPojo.getSize() != null ? itemPojo.getSize() : 0,
                 itemPojo.getIsPrimaryKey(),
                 STableData.from(itemPojo.getTable())
        );
    }

    /**
     * returns basic instance for testing purposes, should not be used for production because it doesn't reference a table
     * which can cause problems while trying to send the code to the backend
     */
    public static SItemData newNoTableInstance(String value, String type){
        return new SItemData(
                UUID.randomUUID().toString(),
                value,
                type,
                0,
                false,
                null
        );
    }

    @Override
    public Object getId() {
        return id;
    }

    public String getUuid() {
        return pojo.getUuid();
    }

    public String getType() {
        return pojo.getType();
    }

    public String getValue() {
        return pojo.getValue();
    }

    public void setTable(STableData table) {
        pojo.setTable(table.getPojo());
    }

    public STableData getTable(){
        return table;
    }
}
