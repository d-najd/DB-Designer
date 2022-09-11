package com.umldesigner.activities.uml_activity.STable;

import android.util.Log;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class STableRequestHandler implements RequestHandler<STablePojo> {
    private static STableRequestHandler instance;
    public static STableRequestHandler getInstance(){
        if (instance == null){
            instance = new STableRequestHandler();
        }
        return instance;
    }

    @Override
    public void receiveData(List<STablePojo> requestedData, ApiController<STablePojo> controller, ApiRequest request) {
        Log.d("Execute", "receiveData with request: " + request.toString() + " and received data count "
                + Objects.requireNonNullElse(requestedData, new ArrayList<>()).size());

        if (controller.getEndpoint().equals(Endpoints.TABLE)){
            switch (request){
                case getAll:
                    SUtils.getInstance().clearViews();
                    for(STablePojo pojo : requestedData){

                        List<SItemData> items = new ArrayList<>();

                        /*
                            the received always contains pojo's
                         */
                        @SuppressWarnings("unchecked")
                        List<SItemPojo> itemPojos = (List<SItemPojo>) pojo.getItems();
                        for(SItemPojo itemPojo : itemPojos){
                            items.add(SItemData.from(itemPojo));
                        }

                        new STableBuilder(pojo.getUuid(), controller.getContainer(), pojo.getTitle(),
                                pojo.getX(), pojo.getY())
                                .addItems(items)
                                .build();
                    }
                    break;
                case post:
                case put:
                    break;
                default:
                    throw new IllegalStateException("the current receiver is unable to handle the current state");
            }
        } else {
            throw new IllegalStateException("the current receiver is unable to handle the current state");
        }
    }
}
