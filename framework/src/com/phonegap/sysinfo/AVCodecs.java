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

import android.util.Log;

public class AVCodecs {
	private static final String LOG_TAG = "AVCodecs Query";

	public static JSONObject getCodecs() {
		JSONObject codecs = new JSONObject();
		
		try{
			codecs.put("audioCodecs", getAudioCodecs());
			codecs.put("videoCodecs", getVideoCodecs());
		}
		catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		
		return codecs;
	}

	private static JSONArray getVideoCodecs() {
		JSONArray video = new JSONArray();

		try {
			video.put(new JSONObject("{\"compFormats\": \"video/H263\", \"containerFormats\": [\"3GPP\",\"MPEG-4\"], \"hwAccel\": \"true\", \"profiles\": \"\", \"frameTypes\": \"\", \"rateTypes\": \"\"}"));
			video.put(new JSONObject("{\"compFormats\": \"video/h264\", \"containerFormats\": [\"3GPP\",\"MPEG-4\"], \"hwAccel\": \"true\", \"profiles\": \"\", \"frameTypes\": \"\", \"rateTypes\": \"\"}"));
			video.put(new JSONObject("{\"compFormats\": \"video/mp4\", \"containerFormats\": [\"3GPP\"], \"hwAccel\": \"true\", \"profiles\": \"\", \"frameTypes\": \"\", \"rateTypes\": \"\"}"));
		}
		catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		
		return video;
	}

	private static JSONArray getAudioCodecs() {
		JSONArray audio = new JSONArray();
		
		try {
			audio.put(new JSONObject("{\"compFormats\": \"AAC LC/LTP\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"HE-AACv1 (AAC+)\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"HE-AACv2 (enhanced AAC+)\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"AMR-NB\", \"encode\": \"true\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"AMR-WB\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"MP3\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"MIDI\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"Ogg Vorbis\", \"encode\": \"false\", \"decode\": \"true\"}"));
			audio.put(new JSONObject("{\"compFormats\": \"PCM/WAVE\", \"encode\": \"false\", \"decode\": \"true\"}"));
		}
		catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage(), e);
		}
		
		return audio;
	}

}
