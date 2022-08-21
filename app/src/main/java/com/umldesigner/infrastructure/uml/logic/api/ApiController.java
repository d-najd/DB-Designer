package com.umldesigner.infrastructure.uml.logic.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.umldesigner.infrastructure.uml.error.ErrorTags;
import com.umldesigner.infrastructure.uml.utils.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NonNull;

public abstract class ApiController<T> {
    @Getter
    protected final Context context;
    protected final ApiUtils apiUtils;
    @Getter
    protected final ReceiverInterface receiverInterface;
    @Getter
    protected final String url;
    @Getter
    protected final String endpoint;
    
    private Class<T> tClass;
    
    public ApiController(Context context, ReceiverInterface receiverInterface) {
        this.context = context;
        this.apiUtils = ApiUtils.getInstance(context);
        this.receiverInterface = receiverInterface;
        
        if (setEndpoint() == null) {
            throw new IllegalStateException("method setEndpoint should define some url, it is pointless" +
                    "to point to the root of the site since it will not contain anything");
        }
        
        this.url = ApiUtils.IP + setEndpoint();
        this.endpoint = setEndpoint();
        this.tClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    /**
     * sets the part of the url after the "http://xx.xx:xx/", has similar functionality to the
     * request controller that is located above the class declaration in spring boot controllers
     */
    protected abstract String setEndpoint();
    
    /**
     * default implementation for the getAll method
     */
    public void getAll() {
        Log.d("Execute", "getAll on class, " + this.getClass().getSimpleName());
        
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Gson gson = new Gson();
                        List<T> pojos = new ArrayList<>();
                        
                        for(int i = 0; i < response.length(); i++){
                            T curObject = gson.fromJson(response.get(i).toString(), tClass);
                            
                            pojos.add(curObject);
                        }
                        
                        receiverInterface.receiveData(pojos, this, ApiRequest.getAll);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                apiUtils.getErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return ApiController.getHeaders();
            }
        };
        
        ApiUtils.getInstance(context).addRequest(request);
    }
    
    /**
     * @see {@link ReceiverInterface}
     */
    public void getByUuid(Object id){
        throw new UnsupportedOperationException("the method is not implemented");
    }
    
    /**
     * @param o the data parameter contained within the view should be passed here
     */
    public void post(T o){
        throw new UnsupportedOperationException("the method is not implemented");
    }
    
    /**
     * @see #update(Object) 
     */
    protected String getUpdateUrl(T o){
        throw new IllegalStateException("when using default update method define getUpdateUrl");
    }
    
    /**
     * default implementation for the update method
     * @implNote {@link #getUpdateUrl(Object)} must be overridden if this method is used
     */
    public void update(T o){
        Log.d("Execute", "update on class, " + this.getClass().getSimpleName());
        
        String curUrl = url + getUpdateUrl(o);
        JSONObject jsonObject = objectToJSON(o);
        
        if (jsonObject == null){
            return;
        }
    
        StringRequest request = new StringRequest(
                Request.Method.POST,
                curUrl,
                response -> {
                    Gson gson = new Gson();
                    List<T> pojos = new ArrayList<>();
    
                    pojos.add(
                            gson.fromJson(response, tClass));
    
                    receiverInterface.receiveData(pojos, this, ApiRequest.post);
                },
                apiUtils.getErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return ApiController.getHeaders();
            }
        
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
        };
        
        ApiUtils.getInstance(context).addRequest(request);
    }
    
    
    private JSONObject objectToJSON(T o) {
        JSONObject convertedJObject = null;
        String jsonInString = new Gson().toJson(o);
        try {
            convertedJObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            Log.e(ErrorTags.APP_ERROR, "Error converting " + o.getClass().getSimpleName() + " to json object");
            e.printStackTrace();
        }
        return convertedJObject;
    }
    
    
    /**
     * method for getting the headers for the requests, stuff like authentication and content type.
     */
    @NonNull
    private static HashMap<String, String> getHeaders() {
        HashMap<String, String> params = new HashMap<>();
        
        //adding param for json data
        params.put("Content-Type", "application/json; charset=utf-8");
       
        /*
        //for authentication
        String userData = UserData.getLastUserRaw(context);
        
        if (userData == null) {
            Log.wtf("DEBUG", "getting server data without a user");
            return params;
        }
        
        //String creds = String.format("%s:%s",username,password);
        //String auth = "Basic " + Base64.encodeToString(userData, Base64.NO_WRAP);
        String auth = "Basic " + userData;
        params.put("Authorization", auth);
         */
        
        return params;
    }
}