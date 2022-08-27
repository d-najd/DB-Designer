package com.umldesigner.activities.uml_activity.SItem.receiver;


import com.umldesigner.infrastructure.uml.logic.api.controller.ApiControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;

import java.util.List;

public class sItemRequestHandler implements RequestHandler {
    private static sItemRequestHandler instance;
    public static sItemRequestHandler getInstance(){
        if (instance == null){
            instance = new sItemRequestHandler();
        }
        return instance;
    }


    @Override
    public void receiveData(List<?> requestedData, ApiControllerTemplate controller, ApiRequest request) {

    }
}
