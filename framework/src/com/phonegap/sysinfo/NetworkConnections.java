/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap.sysinfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.phonegap.DroidGap;
import com.phonegap.api.PhonegapActivity;

public class NetworkConnections {
	private static final String LOG_TAG = "NetworkConnections Query";
	
	public static final String UNKNOWN = "unknown";
	public static final String ETHERNET = "ethernet";
	public static final String USB = "usb";
	public static final String WIFI = "wifi";
	public static final String WIMAX = "wimax";
	// mobile
	public static final String MOBILE = "mobile";
	public static final String XMOBILE = "x-mobile";
	// 2G network types
	public static final String TYPE_2G = "2g";
	public static final String GSM = "gsm";
	public static final String GPRS = "gprs";
	public static final String EDGE = "edge";
	// 3G network types
	public static final String TYPE_3G = "3g";
	public static final String CDMA = "cdma";
	public static final String UMTS = "umts";
	// 4G network types
	public static final String TYPE_4G = "4g";
	public static final String LTE = "lte";
	public static final String UMB = "umb";

	
	private ConnectivityManager netMgr;
	private WifiManager wifiMgr;
	
	/**
	 * Constructor.
	 */
	public NetworkConnections(PhonegapActivity ctx) {
		this.netMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		this.wifiMgr = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
	}

	public JSONArray getActiveConnections() {
		JSONArray connections = new JSONArray();
		
		NetworkInfo info = netMgr.getActiveNetworkInfo();

		if (info != null) {
			JSONObject connection = new JSONObject();

			try {
				connection.put("type", getType(info));
				connection.put("roaming", info.isRoaming());

				if (info.getTypeName().toLowerCase().equals(WIFI)) {
					WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

					connection.put("currentDownloadBandwidth", wifiInfo.getLinkSpeed());
					connection.put("currentUploadBandwidth", 0);
					connection.put("maxDownloadBandwidth", 0);
					connection.put("maxUploadBandwidth", 0);
					connection.put("currentSignalStrength", wifiInfo.getRssi());
				}
				else {				
					connection.put("currentDownloadBandwidth", 0);
					connection.put("currentUploadBandwidth", 0);
					connection.put("maxDownloadBandwidth", 0);
					connection.put("maxUploadBandwidth", 0);
					connection.put("currentSignalStrength", 0);
				}
			} catch (JSONException e) {
				Log.e(LOG_TAG, e.getMessage(), e);
			}
		
			connections.put(connection);
		}
		
		return connections;
	}

	/**
	 * Determine the type of connection
	 * 
	 * @param info the network info so we can determine connection type.
	 * @return
	 */
	private String getType(NetworkInfo info) {
		String type = info.getTypeName();	
			
		if (type.toLowerCase().equals(WIFI)) {
			return WIFI;
		}
		else if (type.toLowerCase().equals(MOBILE)) {
			type = info.getSubtypeName();
			if (type.toLowerCase().equals(GSM) || 
					type.toLowerCase().equals(GPRS) ||
					type.toLowerCase().equals(EDGE)) {
				return TYPE_2G;
			}
			else if (type.toLowerCase().equals(CDMA) || 
					type.toLowerCase().equals(UMTS)) {
				return TYPE_3G;
			}
			else if (type.toLowerCase().equals(LTE) || 
					type.toLowerCase().equals(UMB)) {
				return TYPE_4G;
			}
			else {
				return XMOBILE;
			}
		}
		return UNKNOWN;
	}
}
