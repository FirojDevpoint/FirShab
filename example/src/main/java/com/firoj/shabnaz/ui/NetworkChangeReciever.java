package com.firoj.shabnaz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.database.model.Shabnaz;


import java.util.HashMap;
import java.util.List;

public class NetworkChangeReciever extends BroadcastReceiver {

    private Repo repoObject;
    private String CurrentId;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

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
            List<Shabnaz> allWorkOrders = repoObject.rShabnaz.getAllWorkOrders(Integer.parseInt(CurrentId));
            for(int i =0; i<allWorkOrders.size(); i++)
            {
                Shabnaz ShabnazObj = allWorkOrders.get(i);
                String a = ShabnazObj.getMessage();

            }





            session.createCurrentId("0");
        }

        // Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}




