package com.umldesigner.infrastructure.uml.logic.api;

import android.util.Log;
import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.utils.MyCloneable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRequestHandler<T extends MyCloneable<T>> implements RequestHandler<T> {
    public void receiveData(List<T> requestedData, ApiController<T> controller, RequestTypes request) {
        Log.d("Execute", "receiveData with request: " + request.toString() + " and received data count "
                + Objects.requireNonNullElse(requestedData, new ArrayList<>()).size());
        switch (request){
            case getUuid:
                receiveGetByUuid(requestedData, controller, request);
                break;
            case getAll:
                receiveGetAllData(requestedData, controller, request);
                break;
            case post:
                receivePostData(requestedData, controller, request);
                break;
            case put:
                receivePutData(requestedData, controller, request);
                break;
        }
    }

    /**
     * @implSpec if left unoverridden will throw illegal state exception when called
     */
    public void receiveGetByUuid(List<T> requestedData, ApiController<T> controller, RequestTypes request) {
        throw new IllegalStateException("should add request handler for the get all method in class: "
                + getClass().getSimpleName());
    }

    /**
     * @implSpec if left unoverridden will throw illegal state exception when called
     */
    public void receiveGetAllData(List<T> requestedData, ApiController<T> controller, RequestTypes request) {
        throw new IllegalStateException("should add request handler for the get all method in class: "
                + getClass().getSimpleName());
    }

    public void receivePostData(List<T> requestedData, ApiController<T> controller, RequestTypes request) { }

    public void receivePutData(List<T> requestedData, ApiController<T> controller, RequestTypes request) { }
}
