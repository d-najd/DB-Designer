package com.umldesigner.api.controller.uml_activity.item;

import android.content.Context;

import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;

public class SItemController extends ApiController {
    public SItemController(Context context, ReceiverInterface receiver) {
        super(context, receiver);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM;
    }
}
