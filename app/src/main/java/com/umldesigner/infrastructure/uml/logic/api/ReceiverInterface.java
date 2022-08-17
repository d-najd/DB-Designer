package com.umldesigner.infrastructure.uml.logic.api;

import java.util.List;

public interface ReceiverInterface {
    /**
     * send method call back to the activity where it was called from once the request has been received
     * @param controller used for getting the endpoint of the controller thus letting us to know which
     *                   controller got called
     * @param requestedData the data requested from the server
     * @param code of the method that got called, like update, get etc
     */
    public void receiveData(List<?> requestedData, BaseAPIControllerTemplate controller, ApiMethodCodes code);
}
