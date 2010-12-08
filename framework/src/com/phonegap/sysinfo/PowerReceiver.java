/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap.sysinfo;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class PowerReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "Power Query";
	private int level = 0;
	private long timeRemaining = 0;
	private boolean isBattery = false;
	private boolean isCharging = false;
	private int temp = 0;
	
	public JSONObject getPowerStatus() throws JSONException {
		JSONObject obj = new JSONObject();
		
		Log.d(LOG_TAG, "We are in getPowerStatus()");
		
		obj.put("level", level);
		obj.put("timeRemaining", timeRemaining);
		obj.put("isBattery", isBattery);
		obj.put("isCharging", isCharging);
		
		return obj;
	}
	public JSONObject getThermalStatus() throws JSONException {
		JSONObject obj = new JSONObject();
		
		Log.d(LOG_TAG, "We are in getThermalStatus()");
		
		obj.put("state", temp);
		
		return obj;
	}

	@Override
    public void onReceive(Context arg0, Intent intent) {
		level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
		isBattery = plugged == 0 ? true : false;
		isCharging = plugged > 0 ? true : false;
		Log.d(LOG_TAG, "Power level is = " + level);
		Log.d(LOG_TAG, "Plugged in = " + isCharging);
		Log.d(LOG_TAG, "Temp is = " + temp);
	}
}
