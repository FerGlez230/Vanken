package com.example.vanken.Modelos;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public final class VolleySingleton {
        private static VolleySingleton singleton;
        private RequestQueue requestQueue;
        private static Context context;
        private JsonObjectRequest jsonObjectRequest;
        public VolleySingleton(Context context)
        {
            VolleySingleton.context = context;
            requestQueue = getRequestQueue();
        }
        /**
         * Retorna la unica instancia del singleton
         * @param context contexto donde se ejecutarán las peticiones
         * @return instancia
         */
        public static  synchronized  VolleySingleton getInstance(Context context)
        {
            if(singleton==null)
            {
                singleton=new VolleySingleton(context.getApplicationContext());
            }
            return singleton;
        }
        /**
         * Obtiene la instancia de la cola de peticiones
         * @return cola de peticiones
         */
        public RequestQueue getRequestQueue() {
            if(requestQueue==null)
            {
                requestQueue= Volley.newRequestQueue(context.getApplicationContext());
            }
            return requestQueue;
        }
        /**
         * Añade la peticion a la cola
         * @param req peticion
         * @param <T> resultado final de tipo <T>
         */
        public <T> void  addToRequestQueue(Request<T> req)
        {
            getRequestQueue().add(req);
        }
}