package com.umldesigner.infrastructure.uml.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.umldesigner.infrastructure.uml.error.api.ApiErrorListener
import lombok.AccessLevel
import com.umldesigner.infrastructure.uml.utils.ApiUtils
import lombok.Getter

/**
 * singleton which holds api related things
 */
class ApiUtils private constructor(context: Context) {
    /**
     * request queue for the api calls
     */
    private val requestQueue: RequestQueue

    /**
     * error listener for the api calls
     */

    val errorListener: Response.ErrorListener

    init {
        requestQueue = Volley.newRequestQueue(context)
        errorListener = ApiErrorListener(context)
    }

    /**
     * adds a request to the queue
     * @param request the request that needs to be added to the queue
     */
    fun addRequest(request: Request<*>?) {
        requestQueue.add(request)
    }

    companion object {
        private var instance: ApiUtils? = null

        /**
         * ip of the api
         */
        const val IP = "http://192.168.1.150:8080"
        @JvmStatic
        fun getInstance(context: Context): ApiUtils? {
            if (instance == null) {
                instance = ApiUtils(context)
            }
            return instance
        }
    }
}