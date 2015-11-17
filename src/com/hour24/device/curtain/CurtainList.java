package com.hour24.device.curtain;

import java.util.ArrayList;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class CurtainList {
//	public static ArrayList<CurtainPinObj> aCurtainList = new ArrayList<CurtainPinObj>();

	// 핀 등록
//	public static void setList() {
//		aCurtainList.add(new CurtainPinObj("curtain-01"));
//		aCurtainList.add(new CurtainPinObj("curtain-02"));
//	}
//
//	// Step핀 검색
//	public Pin searchStepPin(String sPinName) {
//		for (CurtainPinObj f : aCurtainList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
//			if (f.getPinName() != null && f.getPinName().contains(sPinName)) {
//				return f.getStepPin();
//			}
//		}
//		return null;
//	}
//
//	// Dir핀 검색
//	public Pin searchDirPin(String sPinName) {
//		for (CurtainPinObj f : aCurtainList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
//			if (f.getPinName() != null && f.getPinName().contains(sPinName)) {
//				return f.getDirPin();
//			}
//		}
//		return null;
//	}
//
//	// Ena핀 검색
//	public Pin searchEnaPin(String sPinName) {
//		for (CurtainPinObj f : aCurtainList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
//			if (f.getPinName() != null && f.getPinName().contains(sPinName)) {
//				return f.getEnaPin();
//			}
//		}
//		return null;
//	}
}

 /*class CurtainPinObj{
	private Pin pin_step;
	private Pin pin_dir;
	private Pin pin_ena;
	private String pinName;
	
	public CurtainPinObj(Pin pin_step, Pin pin_dir, Pin pin_ena, String pinName){
		this.pin_step = pin_step;
		this.pin_dir = pin_dir;
		this.pin_ena = pin_ena;
		this.pinName = pinName;
	}
	
	public Pin getStepPin(){return pin_step;}
	public Pin getDirPin(){return pin_dir;}
	public Pin getEnaPin(){return pin_ena;}
	public String getPinName(){return pinName;}
}*/