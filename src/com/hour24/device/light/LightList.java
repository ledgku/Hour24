package com.hour24.device.light;

import java.util.ArrayList;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class LightList {
	public static ArrayList<LightPinObj> aLightList = new ArrayList<LightPinObj>();
	
	// 핀 등록
	public static void setList(){
		aLightList.add(new LightPinObj("1", "light-01"));
		aLightList.add(new LightPinObj("2", "light-02"));
		aLightList.add(new LightPinObj("2", "light-03"));
		aLightList.add(new LightPinObj("4", "light-04"));
	}
	
	public String searchPin(String sPinName){
		for(LightPinObj f : aLightList){	// LightList에서 기기명에 해당하는 Pin 번호 찾기
			if(f.getPinName() != null && f.getPinName().contains(sPinName)){
				return f.getPin();
			}
		}
		return null;
	}
}

/*class LightPinObj{
	private Pin pin;
	private String pinName;
	
	public LightPinObj(Pin pin, String pinName){
		this.pin = pin;
		this.pinName = pinName;
	}
	
	public Pin getPin(){return pin;}
	public String getPinName(){return pinName;}
}*/