package com.umldesigner.activities.uml_activity.user_project;

import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.infrastructure.uml.data.BaseDataInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.user_project.UserProjectPojo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserProjectData extends UserProjectPojo implements BaseDataInterface<UserProjectPojo> {
    private final int id;

    private UserProjectData(String title, Set<STableData> tables) {
        this.id = SUtils.getInstance().getNextId();
        this.title = title;
        this.tables = (Set<STablePojo>) Objects.requireNonNullElseGet(tables, () -> new HashSet<>());
    }

    @Override
    public Object getId() {
        return id;
    }


}
