package com.umldesigner.activities.uml_activity.SFK;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.identities.BaseMIdentityPojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;
import lombok.Getter;
import lombok.NonNull;

public class SFKData implements BaseDataInterface<SFKPojo> {
    @Getter
    private final SFKPojo pojo;
    private SFKData(
            @NonNull String fUuid,
            @NonNull String sUuid,
            @NonNull String fTUuid,
            @NonNull String sTUuid,
            String onDelete,
            String onUpdate){
        this.pojo = new SFKPojo(
                new BaseMIdentityPojo(fUuid, sUuid),
                fTUuid, sTUuid,
                onDelete != null ? onDelete : "ca",
                onUpdate != null ? onUpdate : "ca"
                );
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
    public SFKPojo getId() {
        return pojo;
    }
}