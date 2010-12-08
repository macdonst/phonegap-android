/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap.sysinfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CpuMonitor {
	private static final String LOG_TAG = "CPU Usage Query";

	private float totalUsed = 0.0f;
	private float totalIdle = 0.0f;

	public CpuMonitor() {
	}

	public JSONObject getCpuUsage() throws JSONException {
		JSONObject obj = new JSONObject();
		float usage = 0.0f;
		/*
		 * There is no Android API for getting the CPU usage so we will need to do it the old 
		 * fashioned UNIX way by parsing /proc/stat
		 */
	    try {
	        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1024);
	        // We just need the first line as it has the aggregated CPU statistics.
	        String cpuInfo = input.readLine();
	        Log.d(LOG_TAG, cpuInfo);
	        input.close();

	        // Split up the first line into tokens
	        StringTokenizer tokenizer = new StringTokenizer(cpuInfo, " ");
	        
	        // Throw away the first token as it is always 'cpu'
	        tokenizer.nextToken();
	        
	        // Add the next three tokens user, nice and system cpu usage
	        float used = Float.parseFloat(tokenizer.nextToken()) + Float.parseFloat(tokenizer.nextToken()) + 
	        	Float.parseFloat(tokenizer.nextToken());
	        
	        // Then the next token is the system idle time
	        float idle = Float.parseFloat(tokenizer.nextToken());

	        Log.d(LOG_TAG, "used = " + used + " idle " + idle);

	        usage = ((used - this.totalUsed) * 100.0f) / ((used - this.totalUsed) + (idle - this.totalIdle));
	        this.totalUsed = used;
	        this.totalIdle = idle;
	    }
	    catch( IOException ex )
	    {
	        ex.printStackTrace();           
	    }		
		
	    obj.put("usage", usage);
		return obj;
	}
}