package com.umldesigner.api.controller.uml_activity.table;

import android.content.Context;

import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

public class STableController extends ApiController<STablePojo> {
    public STableController(Context context, ReceiverInterface receiver) {
        super(context, receiver);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.TABLE;
    }
    
    @Override
    protected String getUpdateUrl(STablePojo o) {
        return "/" + o.getUuid();
    }
}