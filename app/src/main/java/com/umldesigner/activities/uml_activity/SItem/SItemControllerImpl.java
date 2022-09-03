package com.umldesigner.activities.uml_activity.SItem;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

public class SItemControllerImplAbstract extends AbstractApiController<SItemPojo> {
    public SItemControllerImplAbstract(ViewGroup container) {
        super(container);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM;
    }
    @Override
    protected RequestHandler setRequestHandler() {
        return SItemRequestHandler.getInstance();
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
