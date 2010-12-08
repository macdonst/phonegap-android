/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.phonegap.api.PhonegapActivity;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import com.phonegap.sysinfo.AVCodecs;
import com.phonegap.sysinfo.AmbientSensor;
import com.phonegap.sysinfo.CpuMonitor;
import com.phonegap.sysinfo.NetworkConnections;
import com.phonegap.sysinfo.PowerReceiver;

public class SystemInfoManager extends Plugin {
	private static final String AMBIENT_ATMOSPHERIC_PRESSURE = "ambientatmosphericpressure";
	private static final String AMBIENT_TEMPERATURE = "ambienttemperature";
	private static final String AMBIENT_NOISE = "ambientnoise";
	private static final String AMBIENT_LIGHT = "ambientlight";
	private static final String AVCODECS = "avcodecs";
	private static final String CPU = "cpu";
	private static final String NETWORK = "network";
	private static final String POWER = "power";
    private static final String PROXIMITY = "proximity";
	private static final String STORAGE = "storage";
	private static final String THERMAL = "thermal";

	private SensorManager sensorManager; // Sensor manager
    private PowerReceiver power;
    private CpuMonitor cpu;
    private AmbientSensor light;
    private AmbientSensor pressure;
    private Sensor temperature;
    private AmbientSensor proximity;
    private NetworkConnections network;

	private static final String LOG_TAG = "SystemInfo Query";
    private static final Map<String, Boolean> propMap = new HashMap<String, Boolean>();
    static {
    	propMap.put(POWER, true);
    	propMap.put(CPU, true);
    	propMap.put(THERMAL, true);
    	propMap.put(NETWORK, true);
    	propMap.put("connection", false);
    	propMap.put(AMBIENT_LIGHT, true);
    	propMap.put(AMBIENT_NOISE, true);
    	propMap.put(AMBIENT_TEMPERATURE, true);
    	propMap.put(AMBIENT_ATMOSPHERIC_PRESSURE, true);
    	propMap.put(PROXIMITY, true);
    	propMap.put(AVCODECS, true);
    	propMap.put("audiocodec", false);
    	propMap.put("videocodec", false);
    	propMap.put(STORAGE, true);
    	propMap.put("storageunit", false);
    	propMap.put("outputdevices", false);
    	propMap.put("inputdevices", false);
    }
	
	/**
	 * Constructor.
	 */
	public SystemInfoManager()	{
	}
	
	/**
	 * Sets the context of the Command. This can then be used to do things like
	 * get file paths associated with the Activity.
	 * 
	 * @param ctx The context of the main Activity.
	 */
	public void setContext(PhonegapActivity ctx) {
		super.setContext(ctx);
		this.sensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
	}
	/**
	 * Identifies if action to be executed returns a value and should be run synchronously.
	 * 
	 * @param action	The action to execute
	 * @return			T=returns value
	 */
	public boolean isSynch(String action) {
		if (action.equals("has")) {
			return true;
		}
		return false;
	}

	@Override
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		PluginResult.Status status = PluginResult.Status.OK;
		String result = "";		
		
		try {
			String propertyId = args.getString(0);
			if (action.equals("get")) {
				//JSONObject options = args.getJSONObject(1);
				if (CPU.equals(propertyId.toLowerCase())) {
					if (cpu==null) {
						cpu = new CpuMonitor();
					}
					return new PluginResult(status, cpu.getCpuUsage());
				}
				else if (POWER.equals(propertyId.toLowerCase())) {
					if (power==null) {
						power = new PowerReceiver();
						ctx.registerReceiver(power, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
					}
					return new PluginResult(status, power.getPowerStatus());
				}
				else if (THERMAL.equals(propertyId.toLowerCase())) {
					if (power==null) {
						power = new PowerReceiver();
						ctx.registerReceiver(power, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
					}
					return new PluginResult(status, power.getThermalStatus());
				}
				else if (AMBIENT_LIGHT.equals(propertyId.toLowerCase())) {
					if (light==null) {
						light = new AmbientSensor(sensorManager, Sensor.TYPE_LIGHT);
						light.start();
					}
					return new PluginResult(status, light.getSensorValue());
				}
				else if (AMBIENT_NOISE.equals(propertyId.toLowerCase())) {
				}
//				else if (AMBIENT_TEMPERATURE.equals(propertyId.toLowerCase())) {
//					if (temperature==null) {
//						temperature = new AmbientSensor(sensorManager, Sensor.TYPE_TEMPERATURE);
//						temperature.start();
//					}
//					return new PluginResult(status, pressure.getSensorValue());
//				}
				else if (AMBIENT_ATMOSPHERIC_PRESSURE.equals(propertyId.toLowerCase())) {
					if (pressure==null) {
						pressure = new AmbientSensor(sensorManager, Sensor.TYPE_PRESSURE);
						pressure.start();
					}
					return new PluginResult(status, pressure.getSensorValue());
				}
				else if (PROXIMITY.equals(propertyId.toLowerCase())) {
					if (proximity==null) {
						proximity = new AmbientSensor(sensorManager, Sensor.TYPE_PROXIMITY);
						proximity.start();
					}
					return new PluginResult(status, proximity.getSensorValue());
				}
				else if (STORAGE.equals(propertyId.toLowerCase())) {
					return new PluginResult(status, DirectoryManager.getStorageUnits());
				}
				else if (AVCODECS.equals(propertyId.toLowerCase())) {
					return new PluginResult(status, AVCodecs.getCodecs());
				}
				else if (NETWORK.equals(propertyId.toLowerCase())) {
					if (network==null) {
						network = new NetworkConnections(ctx);
					}
					return new PluginResult(status, network.getActiveConnections());
				}
			}
			else if (action.equals("monitor")) {
				// TODO Coming soon!			
				//JSONObject options = args.getJSONObject(1);
			}
			else if (action.equals("has")) {
				 if (AMBIENT_LIGHT.equals(propertyId.toLowerCase())) {
					if (light==null) {
						light = new AmbientSensor(sensorManager, Sensor.TYPE_LIGHT);
					}
					return new PluginResult(status, light.hasSensor());
				}
				else if (AMBIENT_TEMPERATURE.equals(propertyId.toLowerCase())) {
					if (temperature==null) {
						List<Sensor> list = this.sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
						if (list.size() > 0) {
							temperature = list.get(0);
						}
						return new PluginResult(status, true);
					}
					return new PluginResult(status, false);
				}
				else if (AMBIENT_ATMOSPHERIC_PRESSURE.equals(propertyId.toLowerCase())) {
					if (pressure==null) {
						pressure = new AmbientSensor(sensorManager, Sensor.TYPE_PRESSURE);
					}
					return new PluginResult(status, pressure.hasSensor());
				}
				else if (PROXIMITY.equals(propertyId.toLowerCase())) {
					if (proximity==null) {
						proximity = new AmbientSensor(sensorManager, Sensor.TYPE_PROXIMITY);
					}
					return new PluginResult(status, proximity.hasSensor());
				}
				// If not one of the sensors default to looking at property map 
				return new PluginResult(status, propMap.get(propertyId.toLowerCase()).booleanValue());			
			}
			return new PluginResult(status, result);
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}
}
