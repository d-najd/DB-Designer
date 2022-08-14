package com.umldesigner.infrastructure.uml.logic.api.controller.schema.table;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.logic.api.ASettings;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;

public class STableController {
    Context context;
    ASettings aSettings;
    
    public STableController(Context context){
        this.context = context;
        this.aSettings = ASettings.getInstance(context);
    }
    
    public void getAllTables(){
        String URL = ASettings.getIP() + Endpoints.TABLE;
    
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    Message.message(context, "received all tables");
                    Log.d("test", response.toString());
                },
                aSettings.getErrorListener()
        );
        
        aSettings.addRequest(jsonArrayRequest);
    }
}
