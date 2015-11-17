package com.hour24.device.light;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class LightSensor {
	private static int nLIGHT_LEVEL = 0;
	private static final int MAX_LEVEL = 1000;

	private static GpioPinDigitalOutput LightSensor = null;
	private static GpioController gpio = GpioFactory.getInstance();

	public int LuxValue() {
		if (LightSensor == null) {
			LightSensor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14);
			LightSensor.export(PinMode.DIGITAL_OUTPUT);
			LightSensor.setState(PinState.LOW);
			LightSensor.export(PinMode.DIGITAL_INPUT);
			return SensorOn();
		} else {
			System.out.println("이미 조도센서 ON");
			return SensorOn();
		}
	}

	private static int SensorOn() {
		if (LightSensor != null) {
			LightSensor.export(PinMode.DIGITAL_OUTPUT);
			LightSensor.setState(PinState.LOW);
			LightSensor.export(PinMode.DIGITAL_INPUT);
			nLIGHT_LEVEL = 0;
			while ((LightSensor.getState() == PinState.LOW)
					&& (nLIGHT_LEVEL < MAX_LEVEL)) {
				nLIGHT_LEVEL++;
			}
			System.out.println("LIGHT_LEVEL: " + nLIGHT_LEVEL);
			SensorOff();
			return nLIGHT_LEVEL;
		} else {
			return 0;
		}
	}

	private static void SensorOff() {
		if (LightSensor != null) {
			gpio.shutdown();
			gpio.unprovisionPin(LightSensor);
			LightSensor = null;
		}
	}

	/*public void SensorTestOn() throws InterruptedException {
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalInput ssensor = gpio.provisionDigitalInputPin(
				RaspiPin.GPIO_06, PinPullResistance.PULL_DOWN);

		System.out.println("LIGHT SENSOR START");

		ssensor.addListener(new GpioPinListenerDigital() {

			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				// TODO Auto-generated method stub
				if (event.getState().isHigh()) {
					System.out.println("LED ON");
				}

				if (event.getState().isLow()) {
					System.out.println("LED OFF");
				}

				System.out.println(event.getState().getValue() + "");

				System.out.println("-------------------------------------------------");
			}
		});
		
		int f = 0;
		try {
			// keep program running until user aborts
			for (;;) {
				Thread.sleep(500);
				f++;
				if (f == 20) {
					break;
				}
			}

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
		
		gpio.shutdown();
		gpio.unprovisionPin(ssensor);
	}*/
}
