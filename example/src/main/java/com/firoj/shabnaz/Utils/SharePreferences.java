package com.firoj.shabnaz.Utils;

import android.content.SharedPreferences;

import com.firoj.shabnaz.ListBuddies;
import com.firoj.shabnaz.provider.SharedPrefFiles;
import com.firoj.shabnaz.provider.SharedPrefKeys;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;

import java.security.spec.ECField;

/**
 * Created by jpardogo on 23/02/2014.
 */
public class SharePreferences {

    public static void saveCustomization(SharedPrefKeys prefKey, int progress) {
        try
        {
            SharedPreferences customize_pref = getCustomizePref();
            SharedPreferences.Editor editor = customize_pref.edit();
            editor.putInt(prefKey.toString(), progress);
            editor.commit();
        }
        catch (Exception Ex)
        {

        }

    }

    public static int getValue(SharedPrefKeys prefKey) {
        SharedPreferences customize_pref = getCustomizePref();
        int defaultValue = getDefaultValue(prefKey);
        return customize_pref.getInt(prefKey.toString(), defaultValue);
    }

    private static SharedPreferences getCustomizePref() {
        //int defaultValue;
        SharedPreferences defaultValue = null;
        try
        {
            defaultValue  = ListBuddies.getAppContext().getSharedPreferences(SharedPrefFiles.CUSTOMIZE_SETTINGS.toString(), 0);
        }
        catch(Exception Ex)
        {

        }

        return defaultValue;
    }

    private static int getDefaultValue(SharedPrefKeys prefKey) {
        int defaultValue = 0;
        switch (prefKey) {
            case GAP_PROGRESS:
                try {
                    defaultValue = ListBuddies.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                }
                catch (Exception Ex)
                {

                }

                break;
            case SPEED_PROGRESS:
                defaultValue = ListBuddiesLayout.DEFAULT_SPEED;
                break;
            case DIV_HEIGHT_PROGRESS:
                try {
                    defaultValue = ListBuddies.getAppContext().getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
                }
                catch (Exception Ex)
                {

                }

                break;
        }
        return defaultValue;
    }

    public static void reset() {
        for (SharedPrefKeys key : SharedPrefKeys.values()) {
            saveCustomization(key, getDefaultValue(key));
        }
    }
}