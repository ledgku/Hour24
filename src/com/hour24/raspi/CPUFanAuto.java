package com.hour24.raspi;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.hour24.log.ControlLog;
import com.hour24.user.PushSend;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.system.SystemInfo;

public class CPUFanAuto {
	private volatile static CPUFanAuto CPU_FAN_INSTANCE = null;
	private FanScheduleStart fss = null;
	private Timer FanTimer = null;
	private ControlLog conlog = null;
	private CPUFanControl cfc = CPUFanControl.getCPUFanControl();
	private CPUInfo ci = CPUInfo.getCPUInfo();
	private PushSend ps = null;
	private String sToken = null;

	private CPUFanAuto() {
		conlog = new ControlLog();
		ps = new PushSend();
	}

	public void getCPUTempStart(String sToken) {
		this.sToken = sToken;
		if (fss == null) {
			fss = new FanScheduleStart();
		}
		if (FanTimer == null) {
			FanTimer = new Timer();
			FanTimer.schedule(fss, 100, 1000 * 60);

			ci.setAuto(true);
			ps.SendAllPush("cpufan", false, sToken, null);
			conlog.TraceLog("CPU", null, "true", "auto");
			System.out.println("[설정] CPU FAN 자동 ON");
		}
	}

	public void getCPUTempStop(String sToken) {
		this.sToken = sToken;
		if (FanTimer != null && fss != null) {
			FanTimer.cancel();
			FanTimer = null;
			fss = null;

			ci.setAuto(false);
			ps.SendAllPush("cpufan", false, sToken, null);
			conlog.TraceLog("CPU", null, "false", "auto");
			System.out.println("[설정] CPU FAN 자동 OFF");
		}
	}

	public void Auto() {
		float fTemp = getCpuTemp();
		if (fTemp > 35.0f) {
			if (!ci.getTurn()) {
				cfc.CPUFanOn();
				ps.SendAllPush("cpufan", false, null, null);
				System.out.println("[동작] 온도:" + fTemp + " CPU FAN ON");
			}
		} else {
			if (ci.getTurn()) {
				cfc.CPUFanOff();
				ps.SendAllPush("cpufan", false, null, null);
				System.out.println("[동작] 온도:" + fTemp + " CPU FAN OFF");
			}
		}
	}

	public static CPUFanAuto getCPUFanAuto() {
		if (CPU_FAN_INSTANCE == null) {
			synchronized (CPUFanAuto.class) {
				CPU_FAN_INSTANCE = new CPUFanAuto();
			}
		}
		return CPU_FAN_INSTANCE;
	}

	private float getCpuTemp() {
		try {
			conlog.TraceLog("CPU", null, SystemInfo.getCpuTemperature() + "", "temp");
			return SystemInfo.getCpuTemperature();
		} catch (NumberFormatException | IOException | InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}
}

class FanScheduleStart extends TimerTask {
	public void run() {
		CPUFanAuto cpufan = CPUFanAuto.getCPUFanAuto();
		cpufan.Auto();
	}
}