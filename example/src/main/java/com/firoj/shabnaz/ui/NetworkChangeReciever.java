package com.firoj.shabnaz.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.database.model.Shabnaz;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import volley.AppController;

public class NetworkChangeReciever extends BroadcastReceiver {

    private Repo repoObject;
    private String CurrentId;
    private Context mContext;
    private List<Shabnaz> allWorkOrders;
    private SessionManager session;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        mContext = context;
        if(status.equals("Not connected to Internet"))
        {
            MainActivity.isOnline=false;

        }
        else
        {
            session = new SessionManager(context);
            HashMap<String, String> Radious = session.getCurrentId();
            if(Radious!= null)
            {
                CurrentId = Radious.get(SessionManager.KEY_CurrentId);
            }
            else {
                CurrentId = "0";
            }

            MainActivity.isOnline=true;
            repoObject = MainActivity.getRepo();
            allWorkOrders = repoObject.rShabnaz.getAllWorkOrders(Integer.parseInt(CurrentId));



            try {

                getMailInfo();

            } catch (JSONException e) {
                e.printStackTrace();
            }

           
        }

    }

    private void getMailInfo() throws JSONException {

        String url = "http://firoj.website/FirSha/gcm_chat/v1/user/test";

        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,
                getParams(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                repoObject.rShabnaz.delAll();
                String UserID = null;
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    private JSONObject getParams() throws JSONException {


        JSONObject params = new JSONObject();
        JSONArray imParam = new JSONArray();
        for(int i =0; i<allWorkOrders.size(); i++)
        {


            JSONObject test = new JSONObject();
            Shabnaz ShabnazObj = allWorkOrders.get(i);
            //String a = ShabnazObj.getMessage();

            test.put("message", ShabnazObj.getMessage());
            test.put("no", ShabnazObj.getSenderNum());
            imParam.put(test);
        }
        params.put("dataTest" , imParam);
        return params;
    }

}




