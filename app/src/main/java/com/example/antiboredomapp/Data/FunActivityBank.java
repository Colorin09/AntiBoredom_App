package com.example.antiboredomapp.Data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.antiboredomapp.Controller.MySingleton;
import com.example.antiboredomapp.Model.Accessibility;
import com.example.antiboredomapp.Model.FunActivity;
import com.example.antiboredomapp.Model.Price;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FunActivityBank {

    private RequestQueue requestQueue;

    private static String url;
    private static Context cxt;

    FunActivity funActivity;
    Price p;
    Accessibility access;

    RequestQueue queue = MySingleton.getInstance(cxt).getRequestQueue();

    public static final String TAG = FunActivityBank.class.getSimpleName();

    public FunActivity getFunActivity(final AnwserAsyncResponse callback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            p = new Price(response.getDouble("price"));
                            access = new Accessibility(response.getDouble("accessibility"));
                            funActivity = new FunActivity(response.getString("key"), response.getString("activity"),
                                    response.getString("type"), Integer.parseInt(response.getString("participants")),
                                    response.getString("link"), p, access);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(null != callback)
                            callback.processFinished(funActivity);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Problem","It did not work " + error.toString());
                funActivity = null;
            }
        });

        //adding request queue
        MySingleton.getInstance(cxt).addToRequestQueue(jsonObjectRequest);

        return funActivity;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String sent_url) {
        url = sent_url;
    }

    public static Context getCxt() {
        return cxt;
    }

    public static void setCxt(Context cnt) {
        cxt = cnt;
    }

}