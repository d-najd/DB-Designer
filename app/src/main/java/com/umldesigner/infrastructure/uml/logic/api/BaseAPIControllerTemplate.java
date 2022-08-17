package com.umldesigner.infrastructure.uml.logic.api;

import android.content.Context;

import lombok.Getter;


public abstract class BaseAPIControllerTemplate {
    protected final Context context;
    protected final ASettings aSettings;
    protected final ReceiverInterface receiverInterface;
    protected final String url;
    @Getter
    protected final String endpoint;
    
    public BaseAPIControllerTemplate(Context context, ReceiverInterface receiverInterface) {
        this.context = context;
        this.aSettings = ASettings.getInstance(context);
        this.receiverInterface = receiverInterface;
        
        if (setEndpoint() == null) {
            throw new IllegalStateException("method setEndpoint should define some url, it is pointless" +
                    "to point to the root of the site since it will not contain anything");
        }
        
        this.url = ASettings.IP + setEndpoint();
        this.endpoint = setEndpoint();
    }
    
    /**
     * sets the part of the url after the "http://xx.xx:xx/", has similar functionality to the
     * request controller that is located above the class declaration in spring boot controllers
     */
    protected abstract String setEndpoint();
}
    
