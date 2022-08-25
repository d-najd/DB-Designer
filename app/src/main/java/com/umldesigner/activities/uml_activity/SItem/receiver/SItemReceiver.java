package com.umldesigner.activities.uml_activity.SItem.receiver;


import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;

import java.util.List;

public class SItemReceiver implements ReceiverInterface {
    private static SItemReceiver instance;
    public static SItemReceiver getInstance(){
        if (instance == null){
            instance = new SItemReceiver();
        }
        return instance;
    }


    @Override
    public void receiveData(List<?> requestedData, ApiController controller, ApiRequest request) {

    }
}
