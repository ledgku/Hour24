package com.hour24.device.doorlock;

import com.hour24.raspi.ExecuteCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class DoorlockContorl {
	private volatile static DoorlockContorl DOORLOCK_CONTROL_INSTANCE = null;
	private GpioController gpio = null;
	private static GpioPinDigitalOutput Doorlock = null;
	private ExecuteCommand ec = null;
	
	private DoorlockContorl() {
		gpio = GpioFactory.getInstance();
		ec = new ExecuteCommand();
	}
	
	public static DoorlockContorl getDoorlockControl() {
		if (DOORLOCK_CONTROL_INSTANCE == null) {
			synchronized (DoorlockContorl.class) {
				DOORLOCK_CONTROL_INSTANCE = new DoorlockContorl();
			}
		}
		return DOORLOCK_CONTROL_INSTANCE;
	}
	
	public boolean Open(){
		ec.executeCommand("python /home/pi/DoorLock.py d1ddd");
		return true;
	}
	
	public boolean Close(){
		ec.executeCommand("python /home/pi/DoorLock.py d0ddd");
		return true;
	}
}
