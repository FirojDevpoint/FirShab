package com.firoj.shabnaz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.database.model.Shabnaz;


import java.util.List;

public class NetworkChangeReciever extends BroadcastReceiver {

    private Repo repoObject;

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
            MainActivity.isOnline=true;
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
            repoObject = MainActivity.getRepo();
            List<Shabnaz> allWorkOrders = repoObject.rShabnaz.getAllByAssetsID(0);
            for(int i =0; i<allWorkOrders.size(); i++)
            {
                Shabnaz ShabnazObj = allWorkOrders.get(i);
                String a = ShabnazObj.getMessage();

            }
        }

        // Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}




