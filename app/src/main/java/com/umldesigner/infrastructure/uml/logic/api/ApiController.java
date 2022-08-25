package com.umldesigner.infrastructure.uml.logic.api;

import android.content.Context;
import android.util.Log;

import android.view.ViewGroup;
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
    
    private final Class<T> tClass;

    @Getter
    protected final ViewGroup container;

    public ApiController(ViewGroup container) {
        this.context = container.getContext();
        this.apiUtils = ApiUtils.getInstance(context);
        this.receiverInterface = setReceiver();
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
     * used for defining a concrete receiver
     * @return the receiver
     */
    protected abstract ReceiverInterface setReceiver();

    /**
     * hook for cleaning up the object before it is converted to json.
     * example use for this can be preventing recursion hell like the example below
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
     *
     * @param o the given object
     * @return cleaned up object
     */
    protected T objectPrep(T o){
        return o;
    };

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
     * returns the last part of the url, the uuid of the object that needs to be updated
     * xx.xx.xx:xx/{uuid}
     * @see #update(Object)
     */
    protected String getPostUrl(T o){
        return "";
    }
    /**
     * @param o the data parameter contained within the view should be passed here
     */
    public void post(T o){
        Log.d("Execute", "update on class, " + this.getClass().getSimpleName());

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
                    receiverInterface.receiveData(null, this, ApiRequest.put);
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
    
    /**
     * returns the last part of the url, the uuid of the object that needs to be updated
     * xx.xx.xx:xx/{uuid}
     * @see #update(Object)
     */
    protected String getUpdateUrl(T o){
        throw new IllegalStateException("when using default update method define getUpdateUrl");
    }

    /**
     * default implementation for the update method
     * @implNote {@link #getUpdateUrl(Object)} must be overridden if this method is used
     * @see #objectPrep(Object) for doing cleanup on the given object before it is converted to json, see method for more info
     */
    public void update(T o){
        Log.d("Execute", "update on class, " + this.getClass().getSimpleName());

        //cleaning up the object
        if(o == null){
            Log.e(ErrorTags.API_ERROR, "Attempting to use update method with no object attached");
        }

        String curUrl = url + getUpdateUrl(o);
        JSONObject jsonObject = objectToJSON(o);

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                curUrl,
                response -> {
                    Gson gson = new Gson();
                    List<T> pojos = new ArrayList<>();
    
                    pojos.add(
                            gson.fromJson(response, tClass));
    
                    receiverInterface.receiveData(pojos, this, ApiRequest.put);
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

    /**
     * converts java objects to json objects
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