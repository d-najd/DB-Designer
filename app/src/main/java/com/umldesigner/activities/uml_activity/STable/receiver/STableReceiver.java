package com.umldesigner.activities.uml_activity.STable.receiver;

import android.util.Log;
import com.umldesigner.activities.uml_activity.SItem.data.SItemData;
import com.umldesigner.activities.uml_activity.STable.builder.STableBuilder;
import com.umldesigner.activities.uml_activity.STable.view.STableView;
import com.umldesigner.infrastructure.uml.logic.api.ApiController;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.ArrayList;
import java.util.List;

public class STableReceiver implements ReceiverInterface {
    private static STableReceiver instance;
    public static STableReceiver getInstance(){
        if (instance == null){
            instance = new STableReceiver();
        }
        return instance;
    }

    @Override
    public void receiveData(List<?> requestedData, ApiController controller, ApiRequest code) {
        Log.d("Execute", "receiveData: " + requestedData == null ? "no data" : requestedData.toString()
                + controller.toString() + code.toString());

        if (controller.getEndpoint().equals(Endpoints.TABLE)){
            switch (code){
                case getAll:
                    SUtils.getInstance().clearViews();

                    List<STablePojo> tables = (List<STablePojo>) requestedData;
                    for(STablePojo pojo : tables){

                        ArrayList<SItemData> items = new ArrayList<>();
                        for(SItemPojo itemPojo : pojo.getItems()){
                            items.add(new SItemData(itemPojo));
                        }

                        new STableBuilder(pojo.getUuid(), controller.getContainer(), pojo.getTitle(),
                                pojo.getX(), pojo.getY())
                                .addItems(items)
                                .build();
                    }
                    break;
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
