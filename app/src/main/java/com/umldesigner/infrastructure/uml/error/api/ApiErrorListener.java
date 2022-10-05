package com.umldesigner.infrastructure.uml.error.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.umldesigner.Message;
import com.umldesigner.infrastructure.uml.error.ErrorTags;


public class ApiErrorListener implements Response.ErrorListener {
    Context context;
    public ApiErrorListener(Context context){
        this.context = context;
    }
    
    /**
     * todo make sure that this works
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            switch (error.networkResponse.statusCode) {
                case 400:
                    Message.message(context, "Bad request");
                    Log.e(ErrorTags.API_ERROR, "Bad request error: " + new String(error.networkResponse.data));
                case 418:
                    Message.message(context, "The server refuses to brew coffee because it is a teapot");
                    Log.e(ErrorTags.API_ERROR, "server says it is a teapot, error: " + new String(error.networkResponse.data));
                    break;
                case 500:
                    Message.message(context, "Internal server error");
                    Log.e(ErrorTags.API_ERROR, "Internal server error: " + new String(error.networkResponse.data));
                    break;
                case 503:
                    Message.message(context, "Service unavailable");
                    Log.e(ErrorTags.API_ERROR, "Service unavailable, error: " + new String(error.networkResponse.data));
                    break;
                default:
                    Message.defErrMessage(context);
                    Log.e(ErrorTags.API_ERROR, "Unhandled server error code, error: " + new String(error.networkResponse.data));
                    error.printStackTrace();
                    break;
            }
        } catch (NullPointerException e){
            Message.message(context, "server seems to be offline");
            Log.e(ErrorTags.API_ERROR, "server seems to be offline");
            e.printStackTrace();
        }
    }
}
