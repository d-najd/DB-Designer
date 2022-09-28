package com.umldesigner.activities.uml_activity.foreign_key;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;
import lombok.NonNull;

public class SFKData extends SFKPojo implements BaseDataInterface<SFKPojo> {
    private final Integer id;
    private SFKData(
            @NonNull String fUuid,
            @NonNull String sUuid,
            @NonNull String fTUuid,
            @NonNull String sTUuid,
            String onDelete,
            String onUpdate){
        this.id = SUtils.getInstance().getNextId();
        this.onDelete = onDelete != null ? onDelete : "ca";
        this.onUpdate = onUpdate != null ? onUpdate : "ca";
    }

    public static SFKData newInstance(
            @NonNull String fUuid,
            @NonNull String sUuid,
            @NonNull String fTUuid,
            @NonNull String sTUuid,
            String onDelete,
            String onUpdate){
        return new SFKData(
            fUuid,
            sUuid,
            fTUuid,
            sTUuid,
            onDelete,
            onUpdate
        );
    }

    public static SFKData newInstance(
            @NonNull String fUuid,
            @NonNull String sUuid,
            @NonNull String fTUuid,
            @NonNull String sTUuid){
        return new SFKData(
                fUuid,
                sUuid,
                fTUuid,
                sTUuid,
                null,
                null
        );
    }

    @Override
    public Integer getId() {
        return id;
    }
}