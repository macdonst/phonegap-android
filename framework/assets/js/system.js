/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 * 
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */

/**
 * This represents the mobile device, and provides access to system properties
 * @constructor
 */
var SystemInfo = function() {
};

/**
 * Get the system property
 *
 * @param {DOMString} propertyId The name of the property being requested.
 * @param {Function} successCallback The function to call when the property is available
 * @param {Function} errorCallback The function to call when there is an error getting the property. (OPTIONAL)
 * @param {Options} options An object containing the various options when fetching the property. (OPTIONAL)
 */
SystemInfo.prototype.get = function(propertyId, successCallback, errorCallback, options) {
    // Get info
    PhoneGap.exec(successCallback, errorCallback, "System", "get", [propertyId, options]);
};

/**
 * Monitors the system property
 *
 * @param {DOMString} propertyId The name of the property being requested.
 * @param {Function} successCallback The function to call when the property is available
 * @param {Function} errorCallback The function to call when there is an error getting the property. (OPTIONAL)
 * @param {Options} options An object containing the various options when fetching the property. (OPTIONAL)
 */
SystemInfo.prototype.monitor = function(propertyId, successCallback, errorCallback, options) {
    // Get info
    PhoneGap.exec(successCallback, errorCallback, "System", "monitor", [propertyId, options]);
};

/**
 * Determines if the device supports the system property
 *
 * @param {DOMString} propertyId The name of the property being requested.
 */
SystemInfo.prototype.has = function(propertyId) {
    // Get info
    return PhoneGap.exec(null, null, "System", "has", [propertyId]);
};

/**
 *  SystemInfoError
 *  An error code assigned by an implementation when an error has occurred
 */
var SystemInfoError = function() {
    this.code=null;
};

SystemInfoError.PERMISSION_DENIED = 1;
SystemInfoError.INFORMATION_UNAVAILABLE = 2;
SystemInfoError.INVALID_VALUE = 3;
SystemInfoError.TIMEOUT = 4;

/**
 * SystemInfoOptions
 * 
 * An object containing the various options for fetching the property requested.
 * 
 * @param {double} highThreshold On a call to monitor the success callback is only triggered if the value of the property meets or exceeds this number
 * @param {double} lowThreshold On a call to monitor the success callback is only triggered if the value of the property meets or is lower than this number
 * @param {DOMString} thresholdTarget The name of an attribute of the property that is marked a threshold target
 * @param {long} timeout The maximum length of time between a call to monitor and the success callback being envoked
 * @param {DOMString} id That indicates the request targets instance
 */
var SystemInfoOptions = function(highThreshold, lowThreshold, thresholdTarget, timeout, id) {
	this.highThreshold = (highThreshold != 'undefined') ? highThreshold : null;
	this.lowThreshold = (lowThreshold != 'undefined') ? lowThreshold : null;
	this.thresholdTarget = thresholdTarget || null;
	this.timeout = (timeout != 'undefined') ? timeout : null;
	this.id = id || null;
};

/**
 * PowerAttributes
 * 
 * Reflects the state of the devices power usage
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {float} level Specifies how much of the internal power source remains (0-100)
 * @param {long} timeRemaining Estimated time remaining in seconds before the battery will be depleted
 * @param {boolean} isBattery Indicates if the current power source is battery
 * @param {boolean} isCharging Indicates if the the battery is being charged 
 */
var PowerAttributes = function(info, id, level, timeRemaining, isBattery, isCharging) {
	this.info = info || null;
	this.id = id || null;	
	this.level = (level != 'undefined') ? level : null;
	this.timeRemaining = (timeRemaining != 'undefined') ? timeRemaining : null;
	this.isBattery = (isBattery != 'undefined') ? isBattery : null;
	this.isCharging = (isCharging != 'undefined') ? isCharging : null;
};

/**
 * CPUAttributes
 * 
 * Exposes the devices CPU information.
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {float} usage The current CPU usage (0.0-1.0)
 */
var CPUAttributes = function(info, id, usage) {
	this.info = info || null;
	this.id = id || null;	
	this.usage = (usage != 'undefined') ? usage : null;
};

/**
 * ThermalAttributes
 * 
 * Exposes the devices internal temperature
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {float} state Indicates the current thermal status (0.0-1.0) 
 */
var ThermalAttributes = function(info, id, state) {
	this.info = info || null;
	this.id = id || null;	
	this.state = (state != 'undefined') ? state : null;
};

/**
 * NetworkAttributes
 * 
 * Used to determine the current state of the devices network connections
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {ConnectionAttributes[]} activeConnections An array of active device connections.
 */
var NetworkAttributes = function(info, id, activeConnections) {
	this.info = info || null;
	this.id = id || null;	
	this.activeConnections = activeConnections || null;  // ConnectionAttributes[]
};

/**
 * ConnectionAttributes
 * 
 * The properties of a single network connection
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {DOMString} type One of the possible ConnectionTypes
 * @param {long} currentDownloadBandwidth The current real-time download bandwidth, in Kbits/s
 * @param {long} currentUploadBandwidth The current real-time upload bandwidth, in Kbits/s
 * @param {long} maxDownloadBandwidth This property represents the maximum download bandwidth offered by this network connection, measured in Kbits/s.
 * @param {long} maxUploadBandwidth This property represents the maximum upload bandwidth offered by this network connection, measured in Kbits/s.
 * @param {float} currentSignalStrength The connections signal strength (0-1)
 * @param {boolean} roaming Indicates if the connection is roaming
 * @returns
 */
var ConnectionAttributes = function(info, id, type, currentDownloadBandwidth, currentUploadBandwidth, maxDownloadBandwidth, maxUploadBandwidth, currentSignalStrength, roaming) {
	this.info = info || null;
	this.id = id || null;	
    this.type = (type != 'undefined') ? type : null;
    this.currentDownloadBandwidth = (currentDownloadBandwidth != 'undefined') ? currentDownloadBandwidth : null;
    this.currentUploadBandwidth = (currentUploadBandwidth != 'undefined') ? currentUploadBandwidth : null;
    this.maxDownloadBandwidth = (maxDownloadBandwidth != 'undefined') ? maxDownloadBandwidth : null;
    this.maxUploadBandwidth = (maxUploadBandwidth != 'undefined') ? maxUploadBandwidth : null;
    this.currentSignalStrength = (currentSignalStrength != 'undefined') ? currentSignalStrength : null;
    this.roaming = (roaming != 'undefined') ? roaming : null;	
};

/**
 * ConnectionType
 * 
 * An enumeraton of the possible values for ConnectionAttributes.type
 */
var ConnectionType = function() {};

ConnectionType.UNKNOWN = "unknown";
ConnectionType.ETHERNET = "ethernet";
ConnectionType.USB = "usb";
ConnectionType.WIFI = "wifi";
ConnectionType.WIMAX = "wimax";
ConnectionType.TYPE_2G = "2g";
ConnectionType.TYPE_3G = "3g";
ConnectionType.TYPE_4G = "4g";

/*
 * AmbientLight
 * AmbientNoise
 * AmbientTemperature
 * AmbientAtmosphericPressure
 * Proximity
 */
/**
 * SensorAttributes
 * 
 * An interface to the external sensors
 * 
 * @param {float} value The value of the sensor at time of query 
 * @param {float} min The minimum value possible for the sensor
 * @param {float} max The maximum value possible for the sensor
 * @param {float} normalizedValue The numerical value of the sensor normalized between 0 to 1  
 */
var SensorAttributes = function(value, min, max, normalizedValue) {
	this.value = (value != 'undefined') ? value : null;
	this.min = (min != 'undefined') ? min : null;
	this.max = (max != 'undefined') ? max : null;
	this.normalizedValue = (normalizedValue != 'undefined') ? normalizedValue : null;
};

/**
 * AVCodecsAttributes
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {AudioCodecAttributes[]} audioCodecs An array of the devices audio codecs
 * @param {VideoCodecAttributes[]} videoCodecs An array of the devices video codecs
 */
var AVCodecsAttributes = function(info, id, audioCodecs, videoCodecs) {
	this.info = info || null;
	this.id = id || null;	
	this.audioCodecs = audioCodecs || null; // AudioCodecs[]
	this.videoCodecs = videoCodecs || null; // VideoCodecs[]
};

/**
 * AudioCodecAttributes
 * 
 * Information on the audio codec
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {DOMString} compFormats Space separated list of mime types 
 * @param {boolean} encode Does the device support encoding
 * @param {boolean} decode Does the device support decoding
 */
var AudioCodecAttributes = function (info, id, compFormats, encode, decode) {
	this.info = info || null;
	this.id = id || null;	
	this.compFormats = compFormats || null;
	this.encode = (encode != 'undefined') ? encode : null;
	this.decode = (decode != 'undefined') ? decode : null;
};

/**
 * VideoCodecAttributes
 * 
 * Information on the video codec
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {DOMString[]} compFormats Space-separated list of MIME types for supported compression formats.
 * @param {DOMString[]} containerFormats Supported container format names.
 * @param {boolean[]} hwAccel Includes hardware acceleration support?
 * @param {DOMString[]} profiles The list of profiles available for this codec.
 * @param {DOMString[]} frameTypes The list of frame types supported by the codec.
 * @param {DOMString[]} rateTypes The list of rate control options supported by the codec.
 */
var VideoCodecAttributes = function (info, id, compFormats, containerFormats, hwAccel, profiles, frameTypes, rateTypes) {
	this.info = info || null;
	this.id = id || null;	
	this.compFormats = compFormats || null; // DOMString[]
	this.containerFormats = containerFormats || null; // DOMString[]
	this.hwAccel = hwAccel || null; // boolean[]
	this.profiles = profiles || null; // DOMString[]
	this.frameTypes = frameTypes || null; // DOMString[]
	this.rateTypes = rateTypes || null; // DOMString[]
};

/**
 * StorageUnitAttributes
 * 
 * Describes the devices storage options.
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {short} type One of the StorageUnitAttribute types.
 * @param {boolean} isWritable If the storage is writable.
 * @param {long} capacity The total number of bytes that the storage can hold.
 * @param {long} availableCapacity The total number of free bytes on this storage.
 * @param {boolean} isRemoveable If the storage is removable.
 */
var StorageUnitAttributes = function (info, id, type, isWritable, capacity, availableCapacity, isRemoveable) {
	this.info = info || null;
	this.id = id || null;	
	this.type = (type != 'undefined') ? type : null;
	this.isWritable = (isWritable != 'undefined') ? isWritable : null;
	this.capacity = (capacity != 'undefined') ? capacity : null;
	this.availableCapacity = (availableCapacity != 'undefined') ? availableCapacity : null;
	this.isRemoveable = (isRemoveable != 'undefined') ? isRemoveable : null;
};

StorageUnitAttributes.TYPE_UNKNOWN = 0;
StorageUnitAttributes.TYPE_HARDDISK = 1;
StorageUnitAttributes.TYPE_FLOPPYDISK = 2;
StorageUnitAttributes.TYPE_OPTICAL = 3;
StorageUnitAttributes.TYPE_FLASH = 4;

/**
 * OutputDevicesAttributes
 * 
 * Describes the devices output options.
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {DisplayDeviceAttributes[]} displayDevices An array of display devices.
 * @param {DisplayDeviceAttributes[]} activeDisplayDevices An array of active display devices.
 * @param {PrintingDeviceAttributes[]} printingDevices An array of printing devices.
 * @param {PrintingDeviceAttributes} activePrintingDevice The active printing device.
 * @param {BrailleDeviceAttributes[]} brailleDevices An array of braille devices.
 * @param {BrailleDeviceAttributes} activeBrailleDevice The active braille device.
 * @param {AudioDeviceAttributes[]} audioDevices An array of audio devices.
 * @param {AudioDeviceAttributes[]} activeAudioDevices An array of active audio devices.
 */
var OutputDevicesAttributes = function(info, id, displayDevices, activeDisplayDevices, printingDevices, activePrintingDevice, 
		brailleDevices, activeBrailleDevice, audioDevices, activeAudioDevices) {
	this.info = info || null;
	this.id = id || null;	
	this.displayDevices = displayDevices || null; // DisplayDeviceAttributes[]
    this.activeDisplayDevices = activeDisplayDevices || null; // DisplayDeviceAttributes[]
    this.printingDevices = printingDevices || null; // PrintingDeviceAttributes[]
    this.activePrintingDevice = activePrintingDevice || null; // PrintingDeviceAttributes
    this.brailleDevices = brailleDevices || null; // BrailleDeviceAttributes[]
    this.activeBrailleDevice = activeBrailleDevice || null; // BrailleDeviceAttributes
    this.audioDevices = audioDevices || null; // AudioDeviceAttributes[]
    this.activeAudioDevices = activeAudioDevices || null; // AudioDeviceAttributes[]
};

/**
 * DisplayDevicesAttributes
 * 
 * Describes the devices display
 * 
 * @param {short} orientation The display orientation from the constants listed in this object.
 * @param {float} brightness The current brightness (0-1)
 * @param {float} contrast The current contrast (0-1)
 * @param {boolean} blanked If the devices screen is blanked out.
 * @param {long} dotsPerInchW Resolution along the width in DPI
 * @param {long} dotsPerInchH Resolution along the height in DPI
 * @param {float} physicalWidth Physical width in cm
 * @param {float} physicalHeight Physical height in cm
 * @param {DOMString} info text description
 */
var DisplayDeviceAttributes = function(orientation, brightness, contrast, blanked, dotsPerInchW, 
		dotsPerInchH, physicalWidth, physicalHeight, info) {
    this.orientation = (orientation != 'undefined') ? orientation : null;
    this.brightness = (brightness != 'undefined') ? brightness : null;
    this.contrast = (contrast != 'undefined') ? contrast : null;
    this.blanked = (blanked != 'undefined') ? blanked : null;
    this.dotsPerInchW = (dotsPerInchW != 'undefined') ? dotsPerInchW : null;
    this.dotsPerInchH = (dotsPerInchH != 'undefined') ? dotsPerInchH : null;
    this.physicalWidth = (physicalWidth != 'undefined') ? physicalWidth : null;
    this.physicalHeight = (physicalHeight != 'undefined') ? physicalHeight : null;
    this.info = info || null;
};

DisplayDeviceAttributes.ORIENTATION_UNKNOWN = 0;
DisplayDeviceAttributes.ORIENTATION_LANDSCAPE = 1;
DisplayDeviceAttributes.ORIENTATION_PORTRAIT = 2;
DisplayDeviceAttributes.ORIENTATION_INVERTED_LANDSCAPE = 3;
DisplayDeviceAttributes.ORIENTATION_INVERTED_PORTRAIT = 4;

/**
 * AudioDeviceAttributes
 * 
 * Describes the devices audio options
 * 
 * @param {short} type One of the AudioDeviceAttribute types 
 * @param {long} freqRangeLow Frequency range, low value, in Hz.
 * @param {long} freqRangeHigh Frequency range, high value, in Hz.
 * @param {short} volumeLevel 
 * @param {DOMString} info text description
 */
var AudioDeviceAttributes = function(type, freqRangeLow, freqRangeHigh, volumeLevel, info) {
	this.type = (type != 'undefined') ? type : null;
	this.freqRangeLow = (freqRangeLow != 'undefined') ? freqRangeLow : null;
    this.freqRangeHigh = (freqRangeHigh != 'undefined') ? freqRangeHigh : null;
    this.volumeLevel = (volumeLevel != 'undefined') ? volumeLevel : null;
    this.info = info || null;
};

AudioDeviceAttributes.TYPE_UNKNOWN = 0;
AudioDeviceAttributes.TYPE_SPEAKER = 1;
AudioDeviceAttributes.TYPE_HEADPHONES = 2;

/**
 * PrintingDeviceAttributes
 * 
 * The devices printing options
 * 
 * @param {short} type On of the PrintingDeviceAttribute types
 * @param {long} resolution in DPI
 * @param {boolean} color Can print in color 
 * @param {DOMString} info text description
 */
var PrintingDeviceAttributes = function(type, resolution, color, info) {
	this.type = (type != 'undefined') ? type : null;
	this.resolution = (resolution != 'undefined') ? resolution : null;
    this.color = (color != 'undefined') ? color : null;
    this.info = info || null;
};

PrintingDeviceAttributes.TYPE_UNKNOWN = 0;
PrintingDeviceAttributes.TYPE_INKJET = 0;
PrintingDeviceAttributes.TYPE_LASER = 1;
PrintingDeviceAttributes.TYPE_EMBOSSED = 2;
PrintingDeviceAttributes.TYPE_PLOTTER = 3;

/**
 * BrailleDeviceAttributes
 * 
 * @param {long} nbCells the number of cells on this device
 * @param {DOMString} info text description
 */
var BrailleDeviceAttributes = function(nbCells, info) {
    this.nbCells = (nbCells != 'undefined') ? nbCells : null;
    this.info = info || null;
};

/** 
 * InputDevicesAttributes
 * 
 * Describes the devices input options
 * 
 * @param {DOMString} info A free-form string describing an instance of a system property.
 * @param {DOMString} id A free-form string identifying an instance of a system property.
 * @param {PointerAttributes[]} pointingDevices An array of pointing devices
 * @param {PointerAttributes[]} activePointingDevices An array of active pointing devices
 * @param {KeyboardAttributes[]} keyboards An array of keyboard devices
 * @param {KeyboardAttributes[]} activeKeyboards An array of active keyboard devices
 * @param {CameraAttributes[]} cameras An array of camera devices
 * @param {CameraAttributes[]} activeCameras An array of active camera devices
 * @param {MicrophoneAttributes[]} microphones An array of microphone devices
 * @param {MicrophoneAttributes[]} activeMicrophones An array of active microphone devices
 */
var InputDevicesAttributes = function(info, id, pointingDevices, activePointingDevices, keyboards, activeKeyboards, 
		cameras, activeCameras, microphones, activeMicrophones) {
	this.info = info || null;
	this.id = id || null;	
    this.pointingDevices = pointingDevices || null; // PointerAttributes[]
    this.activePointingDevices = activePointingDevices || null; // PointerAttributes[]
    this.keyboards = keyboards || null; // KeyboardAttributes[]
    this.activeKeyboards = activeKeyboards || null; // KeyboardAttributes[]
    this.cameras = cameras || null; // CameraAttributes[]
    this.activeCameras = activeCameras || null; // CameraAttributes[]
    this.microphones = microphones || null; // MicrophoneAttributes[]
    this.activeMicrophones = activeMicrophones || null; // MicrophoneAttributes[]
};

/**
 * PointerAttributes
 * 
 * Describes the pointing device
 * 
 * @param {short} type One of the PointerDevicesAttributes types
 * @param {boolean} supportsMultiTouch Does the device support multi touch
 * @param {DOMString} info text description
 */
var PointerAttributes = function(type, supportsMultiTouch, info) {
    this.type = (type != 'undefined') ? type : null;
    this.supportsMultiTouch = (supportsMultiTouch != 'undefined') ? supportsMultiTouch : null;
    this.info = info || null;
};

PointerAttributes.TYPE_UNKNOWN = 0;
PointerAttributes.TYPE_MOUSE = 1;
PointerAttributes.TYPE_TOUCHSCREEN = 2;
PointerAttributes.TYPE_LIGHTPEN = 3;
PointerAttributes.TYPE_GESTURE = 4;
PointerAttributes.TYPE_TABLET = 5;

/**
 * KeyboardAttributes
 * 
 * Describes the keyboard
 * 
 * @param {short} type One of the KeyboardAttribute types
 * @param {boolean} isHardware is this a hardware keyboard?
 * @param {DOMString} info text description
 */
var KeyboardAttributes = function(type, isHardware, info) {
    this.type = (type != 'undefined') ? type : null;
    this.isHardware = (isHardware != 'undefined') ? isHardware : null;
    this.info = info || null;
};

KeyboardAttributes.TYPE_UNKNOWN = 0;
KeyboardAttributes.TYPE_KEYBOARD = 1;
KeyboardAttributes.TYPE_KEYPAD = 2;

/**
 * CameraAttributes
 * 
 * Describes the camera
 * 
 * @param {boolean} supportsVideo Does the camera support video?
 * @param {boolean} hasFlash Does the camera had a flash?
 * @param {long} sensorPixels The number of picture elements (pixels) of this camera.
 * @param {float} maxZoomFactor The maximum zoom factor of this camera. 
 */
var CameraAttributes = function(supportsVideo, hasFlash, sensorPixels, maxZoomFactor) {
	this.supportsVideo = (supportsVideo != 'undefined') ? supportsVideo : null;
	this.hasFlash = (hasFlash != 'undefined') ? hasFlash : null;
    this.sensorPixels = (sensorPixels != 'undefined') ? sensorPixels : null;
    this.maxZoomFactor = (maxZoomFactor != 'undefined') ? maxZoomFactor : null;
};

/**
 * MicrophoneAttributes
 * 
 * Describes the microphone
 * 
 * @param {short} type One of the MicrophoneAttributes types.
 * @param {long} freqRangeLow Frequency range, low value, in Hz.
 * @param {long} freqRangeHigh Frequency range, high value, in Hz.
 * @param {DOMString} info text description
 * @param {DOMString} name Name of the microphone.
 * @param {DOMString} types Space-separated list of available MIME types.
 */
var MicrophoneAttributes = function(type, freqRangeLow, freqRangeHigh, info, name, types){
    this.type = (type != 'undefined') ? type : null;
    this.freqRangeLow = (freqRangeLow != 'undefined') ? freqRangeLow : null;
    this.freqRangeHigh = (freqRangeHigh != 'undefined') ? freqRangeHigh : null;
    this.info = info || null;
    this.name = name || null;
    this.types = types || null; // DOMString[]
};

MicrophoneAttributes.TYPE_UNKNOWN = 0;
MicrophoneAttributes.TYPE_MICROPHONE = 1;
MicrophoneAttributes.TYPE_LINEIN = 2;


PhoneGap.addConstructor(function() {
    navigator.system = window.system = new SystemInfo();
});
