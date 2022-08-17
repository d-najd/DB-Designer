package com.umldesigner.infrastructure.uml.logic.api;

import android.content.Context;

public abstract class BaseAPIControllerTemplate {
    protected Context context;
    protected ASettings aSettings;
    protected String url;
    
    public BaseAPIControllerTemplate(Context context){
        this.context = context;
        this.aSettings = ASettings.getInstance(context);
        
        if (setUrl() == null){
            throw new IllegalStateException("method setUrl should define some url, it is pointless" +
                    "to point to the root of the site since it will not contain anything");
        }
        
        this.url = ASettings.IP + setUrl();
    }
    
    /**
     * sets the part of the url after the "http://xx.xx:xx/", has similar functionality to the
     * request controller that is located above the class declaration in spring boot controllers
     */
    protected abstract String setUrl();
}
