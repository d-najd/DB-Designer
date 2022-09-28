package com.umldesigner.activities.uml_activity.user_project;

import android.view.ViewGroup;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.AbstractApiController;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.schema.user_project.UserProjectPojo;

public class UserProjectControllerImpl extends AbstractApiController<UserProjectPojo> implements ApiController<UserProjectPojo> {
    public UserProjectControllerImpl(ViewGroup container){
        super(container);
    }

    @Override
    protected String setEndpoint() {
        return Endpoints.project.toString();
    }

    @Override
    protected RequestHandler<UserProjectPojo> setRequestHandler() {
        return null;
    }


}