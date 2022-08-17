package com.umldesigner.infrastructure.uml.logic.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.umldesigner.infrastructure.uml.error.api.ApiErrorListener;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * singleton which holds api related things
 */
public class ASettings {
    @Getter(AccessLevel.NONE)
    private static ASettings instance;
    /**
     * ip of the api
     */
    public static final String IP = "http://192.168.1.200:8080";
    
    /**
     * request queue for the api calls
     */
    private final RequestQueue requestQueue;
    
    /**
     * error listener for the api calls
     */
    @Getter
    private Response.ErrorListener errorListener;
    
    private ASettings(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        errorListener = new ApiErrorListener(context);
    }
    
    public static ASettings getInstance(Context context) {
        if(instance == null){
            instance = new ASettings(context);
        }
        return instance;
    }
    
    /**
     * adds a request to the queue
     * @param request the request that needs to be added to the queue
     */
    public void addRequest(Request<?> request){
       requestQueue.add(request);
    }
}
