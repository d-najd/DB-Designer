package com.umldesigner.activities.uml_activity.SFK;

import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.identities.BaseMIdentityPojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;
import lombok.NonNull;

public class SFKData extends SFKPojo implements BaseDataInterface {

    private SFKData(
            @NonNull String fUuid,
            @NonNull String sUuid,
            String onDelete,
            String onUpdate){
        this.identity = new BaseMIdentityPojo(fUuid, sUuid);
        this.onDelete = onDelete != null ? onDelete : "ca";
        this.onUpdate = onUpdate != null ? onUpdate : "ca";
    }

    public static SFKData newInstance(
            @NonNull String fUuid,
            @NonNull String sUuid,
            String onDelete,
            String onUpdate){
        return new SFKData(
            fUuid,
            sUuid,
            onDelete,
            onUpdate
        );
    }

    public static SFKData newInstance(
            @NonNull String fUuid,
            @NonNull String sUuid){
        return new SFKData(
                fUuid,
                sUuid,
                null,
                null
        );
    }


    /**
     * @return new instance of the identity object to prevent mutability
     */
    @Override
    public Object getId() {
        return new BaseMIdentityPojo(getIdentity().getFirstUuid(), getIdentity().getSecondUuid());
    }
}
