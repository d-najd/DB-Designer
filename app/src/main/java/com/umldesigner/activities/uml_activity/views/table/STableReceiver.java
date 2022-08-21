package com.umldesigner.activities.uml_activity.views.table;

import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;

import java.util.List;

public class STableReceiver implements ReceiverInterface {
    private STableView sTableView;
    
    public STableReceiver(STableView sTableView){
        this.sTableView = sTableView;
    }
    
    @Override
    public void receiveData(List<?> requestedData, ApiController controller, ApiRequest request) {
        List<?> req = requestedData;
        ApiController con = controller;
        ApiRequest requ = request;
        requ = requ;
    }
}
