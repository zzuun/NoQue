package com.example.zunnorain.noque.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequest {

    private static SingletonRequest singletonRequest;
    private RequestQueue requestQueue;
    private static Context context;

    private SingletonRequest(Context contxt) {
        context = contxt;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized SingletonRequest getInstance(Context context) {
        if (singletonRequest == null) {
            singletonRequest = new SingletonRequest(context);
        }
        return singletonRequest;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
