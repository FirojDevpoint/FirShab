package com.firoj.shabnaz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.database.model.Shabnaz;

import java.util.List;


public class IncomingSms extends BroadcastReceiver {
	
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	
	public void onReceive(Context context, Intent intent) {
	
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();


//		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
//			Toast.makeText(context, "SMS_RECEIVED", Toast.LENGTH_LONG).show();
//		}else  if(intent.getAction().equals("android.provider.Telephony.SMS_DELIVER")){
//			Toast.makeText(context, "SMS_DELIVER", Toast.LENGTH_LONG).show();
//		}else  if(intent.getAction().equals("android.provider.Telephony.sms_sent")){
//			Toast.makeText(context, "sms_sent", Toast.LENGTH_LONG).show();
//		}

		try {
			
			if (bundle != null) {
				
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				
				for (int i = 0; i < pdusObj.length; i++) {
					
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					
			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();

			        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

					Repo repoObject;
					repoObject = MainActivity.getRepo();
					Shabnaz mShabnaz;
					mShabnaz = new Shabnaz();
					mShabnaz.setMessage(message);
					mShabnaz.setSenderNum(senderNum);
					mShabnaz.setShabnazID(0);
					//mShabnaz.setLastId(0);
					repoObject.rShabnaz.save(mShabnaz);
					List<Shabnaz> aa = repoObject.rShabnaz.getAllWorkOrders(0);
					
				} // end for loop
              } // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			
		}
	}

	
	
}