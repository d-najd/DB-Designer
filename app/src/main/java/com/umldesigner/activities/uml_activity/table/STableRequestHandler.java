package com.umldesigner.activities.uml_activity.table;

import com.umldesigner.infrastructure.uml.logic.api.AbstractRequestHandler;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

class STableRequestHandler extends AbstractRequestHandler<STablePojo> {
    private static STableRequestHandler instance;

    public static STableRequestHandler getInstance() {
        if (instance == null) {
            instance = new STableRequestHandler();
        }
        return instance;
    }
}
