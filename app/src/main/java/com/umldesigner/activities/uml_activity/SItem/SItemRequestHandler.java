package com.umldesigner.activities.uml_activity.SItem;


import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;

import java.util.List;

class SItemRequestHandler implements RequestHandler {
    private static SItemRequestHandler instance;
    public static SItemRequestHandler getInstance(){
        if (instance == null){
            instance = new SItemRequestHandler();
        }
        return instance;
    }


    @Override
    public void receiveData(List<?> requestedData, AbstractApiController controller, ApiRequest request) {

    }
}
