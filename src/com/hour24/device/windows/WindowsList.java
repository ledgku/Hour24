package com.hour24.device.windows;

import java.util.ArrayList;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

public class WindowsList {
	public static ArrayList<WindowsPinObj> aWindowsList = null;

	public static void setList() {
		aWindowsList = new ArrayList<WindowsPinObj>();
		aWindowsList.add(new WindowsPinObj(RaspiPin.GPIO_10, "windows-01"));
		aWindowsList.add(new WindowsPinObj(RaspiPin.GPIO_11, "windows-02"));
	}

	public Pin searchPin(String sPinName) {
		for (WindowsPinObj f : aWindowsList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
			if (f.getPinName() != null && f.getPinName().contains(sPinName)) {
				return f.getPin();
			}
		}
		return null;
	}
}

/*class WindowsPinObj{
	private Pin pin;
	private String pinName;

	public WindowsPinObj(Pin pin, String pinName) {
		this.pin = pin;
		this.pinName = pinName;
	}
	
	public Pin getPin(){return pin;}
	public String getPinName(){return pinName;}
}*/