package com.umldesigner.activities.uml_activity.STable.controller;

import android.view.ViewGroup;
import com.umldesigner.activities.uml_activity.STable.receiver.STableRequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.app.SSettings;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

public class STableController extends ApiControllerTemplate<STablePojo> {
    public STableController(ViewGroup container) {
        super(container);
    }

    @Override
    protected String setEndpoint() {
        return Endpoints.TABLE;
    }

    @Override
    protected RequestHandler setRequestHandler() {
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

        for (SItemPojo itemPojo : o.getItems()){
            itemPojo.setTable(null);
            itemPojo.setTableUuid_(null);
        }
        return o;
    }
}