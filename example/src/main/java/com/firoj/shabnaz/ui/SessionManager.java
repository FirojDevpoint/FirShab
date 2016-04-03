package com.firoj.shabnaz.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
	//SharedPreferences pref;
	private SharedPreferences _sharedPreference;
	private Editor editor;
	private Context _context;
	private int PRIVATE_MODE = 0;
	private static final String PREF_NAME = "ShabPref";

	public static final String KEY_DeviceId = "DeviceId";
	public static final String KEY_LastID = "LastID";
	public static final String KEY_CurrentId = "CurrentId";

	public SessionManager(Context context) {
		if(context!=null)
		{
			this._context = context;
			_sharedPreference = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
			editor = _sharedPreference.edit();

		}
	}
	public HashMap<String, String> getCurrentId() {
		try {
			HashMap<String, String> user = new HashMap<>();
			user.put(KEY_CurrentId, _sharedPreference.getString(KEY_CurrentId, null));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public HashMap<String, String> getDeviceID() {
		try {
			HashMap<String, String> user = new HashMap<>();
			user.put(KEY_DeviceId, _sharedPreference.getString(KEY_DeviceId, "0"));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public HashMap<String, String> getLastID() {
		try {
			HashMap<String, String> user = new HashMap<>();
			user.put(KEY_LastID, _sharedPreference.getString(KEY_LastID, null));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void createLastID(String i) {
		try {
			editor.putString(KEY_LastID, i);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createCurrentId(String i) {
		try {
			editor.putString(KEY_CurrentId, i);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void createDeviceID(String i) {
		try {
			editor.putString(KEY_DeviceId, i);
			editor.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
