package com.umldesigner.infrastructure.uml.logic.api;

import com.umldesigner.infrastructure.uml.logic.api.controller.ApiController;
import com.umldesigner.submodules.UmlDesignerShared.infrastructure.pojo.utils.MyCloneable;

import java.util.List;

/**
 * @implSpec the request handler should be made singleton because there should not be reason for more than one handler
 * to exist inside an application,
 */
public interface RequestHandler<T extends MyCloneable<T>> {
    /**
     * method for receiving the data from the server and forwarding it to
     * @param controller used for getting the endpoint of the controller thus letting us to know which
     *                   controller got called
     * @param requestedData the data requested from the server
     * @param request of the method that got called, like update, get etc
     * @param success true if the request was success false if it wasn't
     */
    void receiveData(List<T> requestedData, ApiController<T> controller, RequestTypes request);
    void receiveGetByUuid(List<T> requestedData, ApiController<T> controller, RequestTypes request);
    void receiveGetAllData(List<T> requestedData, ApiController<T> controller, RequestTypes request);
    void receivePostData(List<T> requestedData, ApiController<T> controller, RequestTypes request);
    void receivePutData(List<T> requestedData, ApiController<T> controller, RequestTypes request);
}
