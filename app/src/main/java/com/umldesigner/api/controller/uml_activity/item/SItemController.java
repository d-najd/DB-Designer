package com.umldesigner.api.controller.uml_activity.item;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.umldesigner.infrastructure.uml.utils.ApiUtils;
import com.umldesigner.infrastructure.uml.logic.api.BaseAPIControllerTemplate;
import com.umldesigner.infrastructure.uml.logic.api.Endpoints;
import com.umldesigner.infrastructure.uml.logic.api.ReceiverInterface;
import com.umldesigner.submodules.UmlDesignerShared.schema.table_item.dto.SItemPojo;

import org.json.JSONException;

import java.util.ArrayList;

public class SItemController extends BaseAPIControllerTemplate {
    public SItemController(Context context, ReceiverInterface receiver) {
        super(context, receiver);
    }
    
    @Override
    protected String setEndpoint() {
        return Endpoints.ITEM;
    }
    
    public void getItem(){
        Log.d("Execute", "getItem with parameter");
        
        String newUrl = url + "/e85fd0d0-2f76-4a7b-b670-920eee5bce59";
        
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                newUrl,
                null,
                response -> {
                    Gson gson = new Gson();
                    SItemPojo itemPojo = gson.fromJson(response.toString(), SItemPojo.class);
                    itemPojo = itemPojo;
                },
                apiUtils.getErrorListener()
        );
    
        ApiUtils.getInstance(context).addRequest(request);
    }
    
    public void getAllItems(){
        Log.d("Execute", "getAllitems with parameter");
        
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        ArrayList<SItemPojo> itemPojos = new ArrayList<>();
                        for(int i = 0; i < response.length(); i++){
                            SItemPojo itemPojo = gson.fromJson(response.getJSONObject(i).toString(), SItemPojo.class);
                            
                            itemPojos.add(itemPojo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                apiUtils.getErrorListener()
        );
    
        ApiUtils.getInstance(context).addRequest(request);
    }
}
