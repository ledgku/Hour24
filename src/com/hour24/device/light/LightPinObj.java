package com.hour24.device.light;

public class LightPinObj {
	private String sPin;
	private String sPinName;
	
	public LightPinObj(String sPin, String sPinName){
		this.sPin = sPin;
		this.sPinName = sPinName;
	}
	
	public String getPin(){return sPin;}
	public String getPinName(){return sPinName;}
}
