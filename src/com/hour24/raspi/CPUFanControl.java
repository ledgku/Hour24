package com.hour24.raspi;

import com.hour24.log.ControlLog;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class CPUFanControl {
	private volatile static CPUFanControl CPU_FAN_CONTROL_INSTANCE = null;
	private static GpioPinDigitalOutput Fan = null;
	private static GpioController gpio = null;
	private ControlLog conlog = null;
	private CPUInfo ci = null;

	private CPUFanControl() {
		conlog = new ControlLog();
		ci = CPUInfo.getCPUInfo();
		gpio = GpioFactory.getInstance();
	}

	public static CPUFanControl getCPUFanControl() {
		if (CPU_FAN_CONTROL_INSTANCE == null) {
			synchronized (CPUFanControl.class) {
				CPU_FAN_CONTROL_INSTANCE = new CPUFanControl();
			}
		}
		return CPU_FAN_CONTROL_INSTANCE;
	}

	public void CPUFanOn() {
		if (Fan == null) {
			Fan = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "CPU FAN", PinState.HIGH);
		} else {
			Fan.high();
		}
		ci.setTurn(true);
		conlog.TraceLog("CPU", null, "true", "turn");
	}

	public void CPUFanOff() {
		if (Fan != null) {
			Fan.low();
			gpio.shutdown();
			gpio.unprovisionPin(Fan);
			Fan = null;

			ci.setTurn(false);
			conlog.TraceLog("CPU", null, "false", "turn");
		}
	}
}
