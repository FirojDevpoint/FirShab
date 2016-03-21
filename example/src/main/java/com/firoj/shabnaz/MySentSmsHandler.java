package com.firoj.shabnaz;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.database.model.Shabnaz;
import com.firoj.shabnaz.ui.MainActivity;

import java.util.Date;
import java.util.List;

/**
 * Created by user11 on 3/17/2016.
 */
public class MySentSmsHandler extends ContentObserver {
    private Context mContext;
    public MySentSmsHandler(Context applicationContext){
        super(null);
        mContext = applicationContext;
    }
    public void onChange(boolean selfChange){
        Cursor cursor = mContext.getContentResolver().query(
                Uri.parse("content://sms/sent"), null, null, null, null);
        assert cursor != null;
        if (cursor.moveToNext()) {
            String protocol = cursor.getString(cursor.getColumnIndex("protocol"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            // Only processing outgoing sms event & only when it
            // is sent successfully (available in SENT box).
            if (protocol != null || type != 2) {
                return;
            }
            int dateColumn = cursor.getColumnIndex("date");
            int bodyColumn = cursor.getColumnIndex("body");
            int addressColumn = cursor.getColumnIndex("address");
            String from = "9603988968";
            String message1 = cursor.getString(bodyColumn);
           // Toast.makeText(mContext, message1, Toast.LENGTH_LONG).show();
            String to = cursor.getString(addressColumn);
            Date now = new Date(cursor.getLong(dateColumn));
            String message = cursor.getString(bodyColumn);

            Repo repoObject;
            repoObject = MainActivity.getRepo();

            Shabnaz mShabnaz = null;

            mShabnaz = new Shabnaz();

            mShabnaz.setMessage(message);
            mShabnaz.setSenderNum(to);
            mShabnaz.setShabnazID(0);
            repoObject.rShabnaz.save(mShabnaz);

            List<Shabnaz> aa =  repoObject.rShabnaz.getAllWorkOrders(0);
            List<Shabnaz> bb = repoObject.rShabnaz.getAllByAssetsID(1);
        }
    }
}