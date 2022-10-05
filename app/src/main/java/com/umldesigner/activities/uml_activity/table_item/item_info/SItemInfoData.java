package com.umldesigner.activities.uml_activity.table_item.item_info;

import com.umldesigner.activities.uml_activity.foreign_key.SFKData;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.item_info.SItemInfoPojo;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

public class SItemInfoData extends SItemInfoPojo implements BaseDataInterface<SItemInfoPojo> {
    @Getter
    private final Integer id;

    private SItemInfoData(String uuid, Boolean primaryKey, Boolean allowNull, Boolean uniqueKey, Boolean autoIncrement, Boolean unsigned,
                          SFKData foreignKey, Set<SFKData> referencedForeignKeys){
        this.id = SUtils.getInstance().getNextId();
        this.uuid = uuid;
        this.primaryKey = primaryKey  != null ? primaryKey : false;
        this.allowNull = allowNull != null ? allowNull : false;
        this.uniqueKey = uniqueKey != null ? uniqueKey : false;
        this.autoIncrement = autoIncrement != null ? autoIncrement : false;
        this.unsigned = unsigned != null ? unsigned : false;
        this.foreignKey = foreignKey;
        this.referencedForeignKeys = referencedForeignKeys;
    }

    public static SItemInfoData newInstance(String uuid, SFKData foreignKey, Set<SFKData> referencedForeignKeys){
        return new SItemInfoData(
                uuid,
                null,
                null,
                null,
                null,
                null,
                foreignKey,
                referencedForeignKeys
        );
    }

    public static SItemInfoData newInstance(String uuid, Boolean primaryKey, Boolean allowNull, Boolean uniqueKey, Boolean autoIncrement, Boolean unsigned,
                          SFKData foreignKey, Set<SFKData> referencedForeignKeys){
        return new SItemInfoData(
                uuid,
                primaryKey,
                allowNull,
                uniqueKey,
                autoIncrement,
                unsigned,
                foreignKey,
                referencedForeignKeys
        );
    }

    public static SItemInfoData from(SItemInfoPojo pojo){
        if(pojo == null){
            return null;
        }

        /*
        Set<SFKData> referencedForeignKeys = new HashSet<>();
        for(SFKPojo sfkPojo : pojo.getReferencedForeignKeys()){
             referencedForeignKeys.add(SFKData.from(sfkPojo));
        }

         */

        //TODO check to make sure this works and doesn't do some weird reference magic
        Set<SFKData> referencedForeignKeys = pojo.getReferencedForeignKeys().parallelStream()
                .map(SFKData::from).collect(Collectors.toSet());

        return new SItemInfoData(
                pojo.getUuid(),
                pojo.getPrimaryKey(),
                pojo.getAllowNull(),
                pojo.getUniqueKey(),
                pojo.getAutoIncrement(),
                pojo.getUnsigned(),
                SFKData.from(pojo.getForeignKey()),
                referencedForeignKeys
        );
    }
}
