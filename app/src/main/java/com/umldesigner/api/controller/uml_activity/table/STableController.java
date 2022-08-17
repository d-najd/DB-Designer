package com.umldesigner.api.controller.uml_activity.table;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.umldesigner.infrastructure.uml.logic.api.ASettings;
import com.umldesigner.infrastructure.uml.logic.api.BaseAPIControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;

public class STableController extends BaseAPIControllerTemplate {
    public STableController(Context context){
        super(context);
    }
    
    @Override
    protected String setUrl() {
        return Endpoints.TABLE;
    }
    
    public void getAllTables(){
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    response.
                },
                aSettings.getErrorListener()
        );
        
        ASettings.getInstance(context).addRequest(request);
    }
}
