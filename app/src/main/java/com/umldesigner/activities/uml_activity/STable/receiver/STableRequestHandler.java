package com.umldesigner.activities.uml_activity.STable.receiver;

import android.util.Log;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.activities.uml_activity.STable.builder.STableBuilder;
import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.ArrayList;
import java.util.List;

public class STableRequestHandler implements RequestHandler {
    private static STableRequestHandler instance;
    public static STableRequestHandler getInstance(){
        if (instance == null){
            instance = new STableRequestHandler();
        }
        return instance;
    }

    @Override
    public void receiveData(List<?> requestedData, ApiController controller, ApiRequest code) {
        Log.d("Execute", "Requested data" + requestedData == null ? requestedData.toString() : "no data"
                        + controller.toString() + code.toString());

        if (controller.getEndpoint().equals(Endpoints.TABLE)){
            switch (code){
                case getAll:
                    SUtils.getInstance().clearViews();

                    List<STablePojo> tables = (List<STablePojo>) requestedData;
                    for(STablePojo pojo : tables){

                        ArrayList<SItemData> items = new ArrayList<>();
                        for(SItemPojo itemPojo : pojo.getItems()){
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
