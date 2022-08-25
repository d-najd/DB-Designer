package com.umldesigner.activities.uml_activity.SItem.controller;

import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.SItem.receiver.SItemReceiver;
import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

public class SItemController extends ApiController<SItemPojo> {
    public SItemController(ViewGroup container) {
        super(container);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM;
    }
    @Override
    protected ReceiverInterface setReceiver() {
        return SItemReceiver.getInstance();
    }

    @Override
    protected String getPostUrl(SItemPojo o) {
       return "/" + o.getTable().getUuid();
    }

}
