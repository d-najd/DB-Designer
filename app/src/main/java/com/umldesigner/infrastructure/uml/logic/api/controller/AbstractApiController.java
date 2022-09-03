package com.umldesigner.infrastructure.uml.logic.api.controller;

import android.content.Context;
import android.util.Log;

import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.umldesigner.infrastructure.uml.error.ErrorTags;
import com.umldesigner.infrastructure.uml.logic.api.ApiRequest;
import com.umldesigner.infrastructure.uml.logic.api.RequestHandler;
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

/**
 * controller for the backend
 *
 * @implNote not able to make this static because context causes static memory leak
 * @param <T> the type of object being sent and received from the server, the pojo should be stored in the submodule
 *           UmlDesignerShared
 */

public abstract class AbstractApiController<T> implements ApiController<T> {
    @Getter
    protected final Context context;
    protected final ApiUtils apiUtils;
    @Getter
    protected final RequestHandler requestHandler;
    @Getter
    protected final String url;
    @Getter
    protected final String endpoint;
    
    private final Class<T> tClass;

    @Getter
    protected final ViewGroup container;

    public AbstractApiController(ViewGroup container) {
        this.context = container.getContext();
        this.apiUtils = ApiUtils.getInstance(context);
        this.requestHandler = setRequestHandler();
        this.container = container;
        
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
     * use this to define a request handler for the controller
     * @return the request handler
     */
    protected abstract RequestHandler setRequestHandler();

    /**
     * hook for cleaning up the object before it is converted to json.
     * example use for this can be preventing recursion hell like the example below
     *
     * <pre>
     *     class MainObj{
     *         SubObj sub;
     *         ...
     *     }
     *     class SubObj {
     *         MainObj main;
     *         ...
     *     }
     * </pre>
     *
     * @implNote make sure that the final address is assembled before calling this method since methods like
     * {{@link #getPostUrl(Object)}} may depend on it
     * @param o the given object
     * @return cleaned up object
     */
    protected T objectPrep(T o){
        return o;
    }

    /**
     * @implSpec sends a json array request to the backend, after the request is received the received json objects are
     * converted into java objects and then sent to the receiver along with the current controller and getAll as the
     * request type, if the request fails then {{@link ApiUtils#getErrorListener()}} is used to handle the error
     */
    final public void getAll() {
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
                        
                        requestHandler.receiveData(pojos, this, ApiRequest.getAll);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                apiUtils.getErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return AbstractApiController.getHeaders();
            }
        };
        
        ApiUtils.getInstance(context).addRequest(request);
    }

    /**
     * @see {@link RequestHandler}
     */
    public void getByUuid(Object id){
        throw new UnsupportedOperationException("the method does not have a default implementation");
    }

    /**
     * returns the last part of the url, the uuid of the object that needs to be updated
     * xx.xx.xx:xx/{uuid}
     * 
     * @implSpec if not overridden "" will be returned as to not cause any problems by returning null
     * @see #post(Object)
     */
    protected String getPostUrl(T o){
        return "";
    }
    /**
     * @param o the data parameter contained within the view should be passed here
     *          
     * @implSpec first the given object is being used to in {{@link #getPostUrl(Object)}} to get custom post url if 
     * specified, then the object is converted to json but before that {@link #objectPrep(Object)} is called from
     * inside the objectToJson. after that
     * StringRequest is sent to the backend, if the request is received the json object is
     * converted into java object and then sent to the receiver along with the current controller and post as the
     * request type, if the request fails then {{@link ApiUtils#getErrorListener()}} is used to handle the error
     *
     * @see #getPostUrl(Object) for specifying a custom post method url
     * @see #objectToJSON(Object)
     * @see #objectPrep(Object) 
     */
    public void post(T o){
        Log.d("Execute", "update on object " + o.getClass() + ", with class " + this.getClass().getSimpleName());

        //cleaning up the object
        if(o == null){
            Log.e(ErrorTags.API_ERROR, "Attempting to use update method with no object attached");
        }

        String curUrl = url + getPostUrl(o);
        JSONObject jsonObject = objectToJSON(o);

        StringRequest request = new StringRequest(
                Request.Method.POST,
                curUrl,
                response -> {
                    requestHandler.receiveData(null, this, ApiRequest.post);
                },
                apiUtils.getErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return AbstractApiController.getHeaders();
            }

            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
        };

        ApiUtils.getInstance(context).addRequest(request);
    }
    
    /**
     * returns the last part of the url, the uuid of the object that needs to be updated
     * xx.xx.xx:xx/{uuid}
     * @see #put(Object)
     */
    protected String getPutUrl(T o){
        throw new IllegalStateException("when using default put method override getPutUrl(T)");
    }

    /**
     * @implNote {@link #getPutUrl(Object)} must be overridden if this method is used otherwise IllegalStateException
     * will be thrown
     *
     * @implSpec first the given object is being used to in {{@link #getPutUrl(Object)}} to get the custom url
     * which <h1>is not optional</h1>, then the object is converted to json
     * but before that {@link #objectPrep(Object)} is called from inside the objectToJson,
     * after that a StringRequest to the backend, if the request is successful the json object is
     * converted into java object and then sent to the receiver along with the current controller and post as the
     * request type, if the request fails then {{@link ApiUtils#getErrorListener()}} is used to handle the error
     *
     * @see #getPostUrl(Object) for specifying a custom post method url
     * @see #objectToJSON(Object)
     * @see #objectPrep(Object) 
     */
     final public void put(T o){
        Log.d("Execute", "update on object " + o.getClass() + ", with class" + this.getClass().getSimpleName());

        //cleaning up the object
        if(o == null){
            Log.e(ErrorTags.API_ERROR, "Attempting to use update method with no object attached");
        }

        String curUrl = url + getPutUrl(o);
        JSONObject jsonObject = objectToJSON(o);

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                curUrl,
                response -> {
                    Gson gson = new Gson();
                    List<T> pojos = new ArrayList<>();
    
                    pojos.add(
                            gson.fromJson(response, tClass));
    
                    requestHandler.receiveData(pojos, this, ApiRequest.put);
                },
                apiUtils.getErrorListener()
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return AbstractApiController.getHeaders();
            }
        
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
        };
        
        ApiUtils.getInstance(context).addRequest(request);
    }

    /**
     * converts java objects to json objects
     *
     * @implSpec {@link #objectPrep(Object)} is called before the object is converted to json
     *
     * @param o the input java object
     * @return a json object
     * @see #objectPrep(Object)
     */
    private JSONObject objectToJSON(T o) {
        JSONObject convertedJObject = null;
        try {
            String jsonInString = new Gson().toJson(objectPrep(o), tClass);
            convertedJObject = new JSONObject(jsonInString);
        } catch (JSONException e) {
            Log.e(ErrorTags.APP_ERROR, "Error converting " + o.getClass().getSimpleName() + " to json object");
            Log.e(ErrorTags.APP_ERROR, "Make sure that the pojo is being passes and not the data");
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