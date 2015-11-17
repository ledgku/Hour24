package com.hour24.raspi;

import java.util.ArrayList;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class CPUList {
	public static ArrayList<CPUPinObj> aCPUList = new ArrayList<CPUPinObj>();

	// 핀 등록
	public static void setList() {
		aCPUList.add(new CPUPinObj(RaspiPin.GPIO_27, "fan-01"));
	}

	public Pin searchPin(String pinName) {
		for (CPUPinObj f : aCPUList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
			if (f.getPinName() != null && f.getPinName().contains(pinName)) {
				return f.getPin();
			}
		}
		return null;
	}
}

class CPUPinObj{
	private Pin pin;
	private String sPinName;
	
	public CPUPinObj(Pin pin, String sPinName){
		this.pin = pin;
		this.sPinName = sPinName;
	}
	
	public Pin getPin(){return pin;}
	public String getPinName(){return sPinName;}
}
