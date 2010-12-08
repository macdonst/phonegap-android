/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap.sysinfo;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AmbientSensor implements SensorEventListener {
	private static final String LOG_TAG = "Sensor Query";
	public static int STOPPED = 0;
	public static int STARTING = 1;
    public static int RUNNING = 2;
    public static int ERROR_FAILED_TO_START = 3;
	
    protected float value;
    protected float min;
    protected float max;
    protected float normalizedValue;
    protected int status;						// status of listener
    protected SensorManager sensorManager; 		// Sensor manager
    protected Sensor sensor;					// sensor returned by sensor manager
    protected int sensorType;					// type of sensor to monitor
	
	public AmbientSensor(SensorManager sensorManager, int sensorType) {
		this.sensorManager = sensorManager;
		this.sensorType = sensorType;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != sensorType) {
            return;
        }
        
        Log.d(LOG_TAG, "Number of values = " + event.values.length);
        for (int i=0; i<event.values.length; i++) {
            Log.d(LOG_TAG, "values = " + event.values[i]);	
        }
        
		this.value = 0;
		this.min = 0;
		this.max = 0;
		this.normalizedValue = 0;
	}

	public JSONObject getSensorValue() throws JSONException {
		JSONObject obj = new JSONObject();
		
		Log.d(LOG_TAG, "We are in AmbientSensor getSensorValue()");
		
		obj.put("value", value);
		obj.put("min", min);
		obj.put("max", max);
		obj.put("normalizedValue", normalizedValue);
		
		return obj;
	}

	/**
     * Start listening for pressure sensor.
     * 
     * @return 			status of listener
     */
    public int start() {
		// If already starting or running, then just return
        if ((this.status == AmbientSensor.RUNNING) || (this.status == AmbientSensor.STARTING)) {
        	return this.status;
        }

        // Get pressure sensor from sensor manager
        List<Sensor> list = this.sensorManager.getSensorList(sensorType);

        Log.d(LOG_TAG, "We have size = " + list.size() + " of sensor type = " + sensorType);
        
        // If found, then register as listener
        if ((list != null) && (list.size() > 0)) {
        	Log.d(LOG_TAG, "We are registering a type=" + sensorType + " listener");
            this.sensor = list.get(0);
            this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_FASTEST);
            this.setStatus(AmbientSensor.STARTING);
        }
       
        // If error, then set status to error
        else {
            this.setStatus(AmbientSensor.ERROR_FAILED_TO_START);
        }
        
        return this.status;
    }

    /**
     * Stop listening to the sensor.
     */
    public void stop() {
        if (this.status != AmbientSensor.STOPPED) {
        	this.sensorManager.unregisterListener(this);
        }
        this.setStatus(AmbientSensor.STOPPED);
    }
    
    /**
     * Get status of the sensor.
     * 
     * @return			status
     */
	public int getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status.
	 * @param status
	 */
	protected void setStatus(int status) {
		this.status = status;
	}
    
    /**
     * Does this device have the type of sensor we are looking for?
     * 
     * @return	has a sensor
     */
    public boolean hasSensor() {
        // Get light sensor from sensor manager
        List<Sensor> list = this.sensorManager.getSensorList(sensorType);
        
        if (list == null) {
        	return false;
        }
    	
    	return (list.size() > 0) ? true : false;
    }
}
