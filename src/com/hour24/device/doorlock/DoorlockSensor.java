package com.hour24.device.doorlock;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

public class DoorlockSensor {
	public DoorlockSensor() {
		// TODO Auto-generated constructor stub
	}

	public String SensorValue() {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalInput sensor = null;
		String sState = null;

		sensor = gpio.provisionDigitalInputPin(RaspiPin.GPIO_26, PinPullResistance.PULL_DOWN);
		sState = sensor.getState().toString();
		gpio.shutdown();
		gpio.unprovisionPin(sensor);
		return sState;
	}
}
