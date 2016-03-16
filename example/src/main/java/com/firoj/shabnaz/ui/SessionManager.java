package com.firoj.shabnaz.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
	SharedPreferences pref;
	Editor editor;

	public static final String KEY_DeviceId = "DeviceId";




	public HashMap<String, String> getDeviceID() {
		try {
			HashMap<String, String> user = new HashMap<>();
			user.put(KEY_DeviceId, pref.getString(KEY_DeviceId, null));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
