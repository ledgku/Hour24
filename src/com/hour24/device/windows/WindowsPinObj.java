package com.hour24.device.windows;

import com.pi4j.io.gpio.Pin;

public class WindowsPinObj {
	private Pin pin;
	private String sPinName;

	public WindowsPinObj(Pin pin, String sPinName) {
		this.pin = pin;
		this.sPinName = sPinName;
	}
	
	public Pin getPin(){return pin;}
	public String getPinName(){return sPinName;}
}
