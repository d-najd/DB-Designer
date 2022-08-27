package com.umldesigner.activities.uml_activity.SItem.controller;

import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.SItem.receiver.sItemRequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

public class SItemController extends ApiControllerTemplate<SItemPojo> {
    public SItemController(ViewGroup container) {
        super(container);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM;
    }
    @Override
    protected RequestHandler setRequestHandler() {
        return sItemRequestHandler.getInstance();
    }

    @Override
    protected SItemPojo objectPrep(SItemPojo o) {
        o.setTable(null);
        return o;
    }

    @Override
    protected String getPutUrl(SItemPojo o) {
        return "/" + o.getUuid();
    }

    @Override
    protected String getPostUrl(SItemPojo o) {
       return Endpoints.TABLE_RAW  + "/" + o.getTable().getUuid();
    }
}
