package com.firoj.shabnaz.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

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

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        mContext = context;
        if(status.equals("Not connected to Internet"))
        {
            MainActivity.isOnline=false;
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        }
        else
        {
            SessionManager session = new SessionManager(context);
            HashMap<String, String> Radious = session.getCurrentId();
            if(Radious!= null)
            {
                CurrentId = Radious.get(SessionManager.KEY_CurrentId);
            }
            else {
                CurrentId = "0";
            }

            MainActivity.isOnline=true;
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
            repoObject = MainActivity.getRepo();
            allWorkOrders = repoObject.rShabnaz.getAllWorkOrders(Integer.parseInt(CurrentId));



            //Toast.makeText(context, allWorkOrders.size(), Toast.LENGTH_LONG).show();
            try {
                getMailInfo();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            session.createCurrentId("0");
        }

    }

    private void getMailInfo() throws JSONException {

        String url = "http://firoj.website/FirSha/gcm_chat/v1/user/test";

        JsonObjectRequest jsObjRequest;
        jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,
                getParams(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                String UserID = null;
//                SessionManager session = new SessionManager(getBaseContext());
//                session.createDeviceID(UserID);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,
                        "False", Toast.LENGTH_LONG).show();
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
        for(int i =0; i<allWorkOrders.size(); i++)
        {
            JSONObject imParam = new JSONObject();
            Shabnaz ShabnazObj = allWorkOrders.get(i);
            String a = ShabnazObj.getMessage();
            imParam.put("message", ShabnazObj.getMessage());
            imParam.put("no", ShabnazObj.getSenderNum());
            params.put("dataTest" , imParam);
        }
        return params;
    }

}




