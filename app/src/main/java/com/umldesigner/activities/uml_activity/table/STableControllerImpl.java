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
    protected String setEndpoint() {
        return Endpoints.table.toString();
    }

    @Override
    protected RequestHandler<STablePojo> setRequestHandler() {
        return STableRequestHandler.getInstance();
    }

    @Override
    protected String setPutUrl(STablePojo o) {
        return "/" + o.getUuid();
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