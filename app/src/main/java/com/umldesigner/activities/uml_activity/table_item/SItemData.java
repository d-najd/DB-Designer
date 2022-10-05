package com.umldesigner.activities.uml_activity.table_item;

import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.activities.uml_activity.table_item.item_info.SItemInfoData;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import lombok.NonNull;

import java.util.UUID;

/**
 * @apiNote considered changing this to a decorator, but it because of difficulties to maintain it I decided not to
 */
public class SItemData extends SItemPojo implements BaseDataInterface<SItemPojo> {
    private final int id;

    private SItemData(String uuid, @NonNull String value, @NonNull String type, int size,
                      SItemInfoData itemInfo, STableData table){
        this.id = SUtils.getInstance().getNextId();
        this.uuid = uuid;
        this.value = value;
        this.type = type;
        this.size = size;
        this.table = table;
        this.itemInfo = itemInfo;
    }

    /**
     * constructs proper instance which can be used for backend stuff
     * @param value the value field in the item
     * @param type the type of the field (ex varchar, int)
     * @param size whether the field type has size and how much it is (ex varchar(50))
     * @param table tells us which table the item belongs to
     * @return SItemData instance
     */
    public static SItemData newInstance(String uuid, @NonNull String value, @NonNull String type, int size,
                                        SItemInfoData itemInfoData, STableData table){
        return new SItemData(
                uuid,
                value,
                type,
                size,
                itemInfoData,
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
                 SItemInfoData.from(itemPojo.getItemInfo()),
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
                null,
                null
        );
    }

    @Override
    public Object getId() {
        return id;
    }

    /**
     * preventing unnecessary casting in client code
     * @return S*Data of type of the table field
     */
    public STableData getTable(){
        return (STableData) super.getTable();
    }
}
