package com.umldesigner.activities.uml_activity.SItem;


import android.util.Log;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.List;

class SItemRequestHandler implements RequestHandler<SItemPojo> {
    private static SItemRequestHandler instance;
    public static SItemRequestHandler getInstance(){
        if (instance == null){
            instance = new SItemRequestHandler();
        }
        return instance;
    }

    @Override
    public void receiveData(List<SItemPojo> requestedData, ApiController<SItemPojo> controller, ApiRequest request) {
        Log.d("Execute", "receiveData with request: " + request.toString() + " and received data count " + requestedData.size());

    }
}
