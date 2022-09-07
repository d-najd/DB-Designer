package com.umldesigner.activities.uml_activity.SFK;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.schema.foreign_key.dto.SFKPojo;

public class SFKControllerImpl extends AbstractApiController<SFKPojo> implements ApiController<SFKPojo> {
    public SFKControllerImpl(ViewGroup container) {
        super(container);
    }
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM_FK;
    }

    @Override
    protected RequestHandler<SFKPojo> setRequestHandler() {
        return new SFKRequestHandler();
    }

}
