package com.firoj.shabnaz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkChangeReciever extends BroadcastReceiver {

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





        }

        // Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}




