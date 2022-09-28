package com.umldesigner.activities.uml_activity.user_project;


import android.util.Log;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.schema.user_project.UserProjectPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class UserProjectRequestHandler implements RequestHandler<UserProjectPojo> {
    private static UserProjectRequestHandler instance;
    public static UserProjectRequestHandler getInstance(){
        if (instance == null){
            instance = new UserProjectRequestHandler();
        }
        return instance;
    }

    @Override
    public void receiveData(List<UserProjectPojo> requestedData, ApiController<UserProjectPojo> controller, ApiRequest request) {
        Log.d("Execute", "receiveData with request: " + request.toString() + " and received data count "
                + Objects.requireNonNullElse(requestedData, new ArrayList<>()).size());



    }
}
