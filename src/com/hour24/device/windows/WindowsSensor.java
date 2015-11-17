package com.hour24.device.windows;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;

public class WindowsSensor {
	private WindowsInfo wi = null;

	public WindowsSensor() {
		wi = WindowsInfo.getWindowsInfo();
	}

	public String SensorValue(Pin pin, String sPinName) {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalInput sensor = null;
		String sState = null;
		for (WindowsObj v : wi.aWindowsArr) {
			if (v.getPinName() != null && v.getPinName().contains(sPinName)) {
				v.setPin(gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_DOWN));
				sensor = v.getPin();
				sState = sensor.getState().toString();
				gpio.shutdown();
				gpio.unprovisionPin(sensor);
				break;
			}
		}
		return sState;
		

	}

}
