package com.firoj.shabnaz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.firoj.shabnaz.database.Repo;

public class AlarmReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        Repo repoObject;

        repoObject = MainActivity.getRepo();
        repoObject.rShabnaz.delAll();

        String phoneNumberReciver="9291590151";// phone number to which SMS to be send
        String message="Hi Janu Love you Shabnaz Uummaa Munna good night Begam sweet dreams Sweat heart.";// message to send
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);

    }

}