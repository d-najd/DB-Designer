package com.umldesigner.activities.uml_activity.foreign_key;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;
import lombok.NonNull;

public class SFKData extends SFKPojo implements BaseDataInterface<SFKPojo> {
    private final Integer id;

    private SFKData(
            @NonNull String uuid,
            @NonNull String referencedUuid,
            @NonNull String referencedTableUuid_,
            String onDelete,
            String onUpdate) {
        this.id = SUtils.getInstance().getNextId();
        this.uuid = uuid;
        this.referencedUuid = referencedUuid;
        this.referencedTableUuid_ = referencedTableUuid_;
        this.onDelete = onDelete != null ? onDelete : "ca";
        this.onUpdate = onUpdate != null ? onUpdate : "ca";
    }

    public static SFKData from(SFKPojo pojo) {
        if(pojo == null)
            return null;

        return new SFKData(
                pojo.getUuid(),
                pojo.getReferencedUuid(),
                pojo.getReferencedTableUuid_(),
                pojo.getOnDelete(),
                pojo.getOnUpdate()
        );
    }

    @Override
    public Integer getId() {
        return id;
    }
}