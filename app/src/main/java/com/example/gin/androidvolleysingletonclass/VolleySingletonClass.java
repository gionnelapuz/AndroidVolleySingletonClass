package com.example.gin.androidvolleysingletonclass;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Gin on 10/30/2017.
 */

public class VolleySingletonClass {

    private static VolleySingletonClass volleySingletonClass;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleySingletonClass(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingletonClass getInstance(Context context) {
        if (volleySingletonClass == null) {
            volleySingletonClass = new VolleySingletonClass(context);
        }
        return volleySingletonClass;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        mRequestQueue.add(request);
    }

    public <T> void cancelPendingRequests(Request<T> request) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(request);
        }
    }

}
