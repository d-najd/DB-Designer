package com.umldesigner.api.controller.uml_activity.table;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.umldesigner.infrastructure.uml.logic.api.ASettings;
import com.umldesigner.infrastructure.uml.logic.api.ApiMethodCodes;
import com.umldesigner.infrastructure.uml.logic.api.BaseAPIControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.submodules.UmlDesignerShared.schema.table.dto.STablePojo;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class STableController extends BaseAPIControllerTemplate {
    public STableController(Context context, ReceiverInterface receiver){
        super(context, receiver);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.TABLE;
    }
    
    public void getAllTables(){
        Log.d("Execute", "getAllTables: ");
        
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        List<STablePojo> sTablePojos = new ArrayList<>();
    
                        for(int i = 0; i < response.length(); i++){
                            STablePojo curObject = gson.fromJson(response.get(0).toString(), STablePojo.class);
                            sTablePojos.add(curObject);
                        }
    
                        receiverInterface.receiveData(sTablePojos, this, ApiMethodCodes.getAll);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                aSettings.getErrorListener()
        );
        
        ASettings.getInstance(context).addRequest(request);
    }
}
