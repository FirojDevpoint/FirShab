package com.jpardogo.android.listbuddies.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.jpardogo.android.listbuddies.database.Repo;
import com.jpardogo.android.listbuddies.database.model.Water;

import java.util.List;


public class IncomingSms extends BroadcastReceiver {
	
	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();
	
	public void onReceive(Context context, Intent intent) {
	
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras();

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

					Water workOrder = null;

					//workOrder = repoObject.rWater.getByWorkOrderID();
					workOrder.setMessage("");
					workOrder.setSenderNum("");
					workOrder.setWaterID(0);
					repoObject.rWater.save(workOrder);




				int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
					toast.show();
					
				} // end for loop
              } // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
			
		}
	}

	
	
}