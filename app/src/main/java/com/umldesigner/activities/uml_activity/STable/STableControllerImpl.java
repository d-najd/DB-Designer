package com.umldesigner.activities.uml_activity.STable;

import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.SItem.SItemData;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.ArrayList;
import java.util.List;

public class STableControllerImpl extends AbstractApiController<STablePojo> implements ApiController<STablePojo> {

    public STableControllerImpl(ViewGroup container) {
        super(container);
    }

    @Override
    protected String setEndpoint() {
        return Endpoints.TABLE;
    }

    @Override
    protected RequestHandler<STablePojo> setRequestHandler() {
        return STableRequestHandler.getInstance();
    }

    @Override
    protected String getPutUrl(STablePojo o) {
        return "/" + o.getUuid();
    }

    @Override
    protected STablePojo objectPrep(STablePojo o) {
        o.setX(o.getX() / SSettings.getInstance().getSpacing());
        o.setY(o.getY() / SSettings.getInstance().getSpacing());

        List<SItemPojo> itemPojos = new ArrayList<>();
        try{
            List<SItemPojo> itemPojoTemp = (List<SItemPojo>) o.getItems();
            itemPojos = itemPojoTemp;
        } catch (ClassCastException e){
            try{
                List<SItemData> itemPojoTemp = (List<SItemData>) o.getItems();

                for(SItemData itemData : itemPojoTemp){
                    itemPojos.add(itemData.getPojo());
                }
            } catch (ClassCastException e2){
                e2.printStackTrace();
                throw new AssertionError();
            }
        }

        for (SItemPojo itemPojo : itemPojos){
            itemPojo.setTable(null);
            itemPojo.setTableUuid_(null);
        }
        return o;
    }
}