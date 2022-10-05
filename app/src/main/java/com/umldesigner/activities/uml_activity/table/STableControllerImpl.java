package com.umldesigner.activities.uml_activity.table;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import java.util.List;

public class STableControllerImpl extends AbstractApiController<STablePojo> implements ApiController<STablePojo> {

    public STableControllerImpl(ViewGroup container) {
        super(container);
    }

    @Override
    protected Endpoints setEndpoint() {
        return Endpoints.table;
    }

    @Override
    protected RequestHandler<STablePojo> setRequestHandler() {
        return STableRequestHandler.getInstance();
    }

    @Override
    protected String setPutUrl(STablePojo o) {
        return "/" + o.getUuid();
    }

    //TODO this is hard coded, fix it
    @Override
    protected String setPostUrl(STablePojo o) {
        return Endpoints.Raw.projectRaw + "/54badac5-70ef-4a31-b04b-6633ad68257c";
    }

    @Override
    public void post(STablePojo o) {
        super.post(o);
/*
        ApiController<SFKPojo> controller = new SFKControllerImpl(container);
        for(SItemPojo item : o.getItems()){
            try{
                controller.post(item.getItemInfo().getForeignKey());
            } catch (Exception ignored){ }
        }

 */

    }

    @Override
    protected STablePojo objectPrep(STablePojo o) {
        o.setX(o.getX() / SSettings.getInstance().getSpacing());
        o.setY(o.getY() / SSettings.getInstance().getSpacing());

         // ? extends SItemPojo is return type of o.getItems
        @SuppressWarnings("unchecked")
        List<SItemPojo> itemPojos = (List<SItemPojo>) o.getItems();

        for (SItemPojo itemPojo : itemPojos){
            itemPojo.setTable(null);
            itemPojo.setTableUuid_(null);
        }
        return o;
    }
}