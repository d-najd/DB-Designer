package com.umldesigner.activities.uml_activity.user_project;


import com.umldesigner.activities.uml_activity.foreign_key.SFKBuilder;
import com.umldesigner.activities.uml_activity.table.STableBuilder;
import com.umldesigner.activities.uml_activity.table.STableData;
import com.umldesigner.activities.uml_activity.table_item.SItemData;
import com.umldesigner.infrastructure.uml.logic.api.AbstractRequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.RequestTypes;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.utils.SUtils;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.user_project.UserProjectPojo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class UserProjectRequestHandlerImpl extends AbstractRequestHandler<UserProjectPojo> {
    private static UserProjectRequestHandlerImpl instance;
    public static UserProjectRequestHandlerImpl getInstance(){
        if (instance == null){
            instance = new UserProjectRequestHandlerImpl();
        }
        return instance;
    }

    @Override
    public void receiveGetByUuid(List<UserProjectPojo> requestedData, ApiController<UserProjectPojo> controller, RequestTypes request) {
        SUtils.getInstance().clearViews();
        UserProjectPojo projectPojo = requestedData.get(0);
        if(projectPojo == null){
            return;
        }

        for(STablePojo pojo : projectPojo.getTables()){
            List<SItemData> items = new ArrayList<>();

            // the received always contains pojo's
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
    }

    //TODO this is for testing so remove it later
    @Override
    public void receiveGetAllData(List<UserProjectPojo> requestedData, ApiController<UserProjectPojo> controller, RequestTypes request) {
        SUtils.getInstance().clearViews();
        UserProjectPojo projectPojo = requestedData.get(0);
        if(projectPojo == null){
            return;
        }

        for(STablePojo pojo : projectPojo.getTables()){
            List<SItemData> items = new ArrayList<>();

            // the received always contains pojo's
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

        Iterator<STableData> iterator = SUtils.getInstance().getTableIterator();
        while(iterator.hasNext()){
            STableData tableData = iterator.next();

            for(int i = 0; i < tableData.getItems().size(); i++){
                SItemData itemData = (SItemData) tableData.getItems().get(i);
                STableData sTableData = SUtils.getInstance().getTableByUuid(itemData
                        .getItemInfo().getForeignKey().getReferencedTableUuid());

                new SFKBuilder(
                        controller.getContainer(),
                        tableData,
                        i,
                        sTableData,
                        sTableData.getItemPosition(itemData.getItemInfo().getForeignKey().getReferencedUuid())
                ).build();

            }
        }
    }


}
