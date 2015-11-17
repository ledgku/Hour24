package com.hour24.device.doorlock;

import java.util.Timer;
import java.util.TimerTask;

import com.hour24.config.Config;
import com.hour24.device.alert.AlertControl;
import com.hour24.user.PushSend;

public class DoorlockAuto {
	private volatile static DoorlockAuto DOORLOCK_AUTO_INSTANCE = null;
	private PushSend ps = null;
	private AlertControl ac = null;
	private DoorlockScheduleStart dss = null;
	public Timer DoorlockTimer = null;
	private DoorlockSensor ds = null;
	private Config conf = null;
	private DoorlockInfo di = null;

	private DoorlockAuto() {
		ds = new DoorlockSensor();
		ac = new AlertControl();
		conf = Config.getConfig();
		di = DoorlockInfo.getDoorlockInfo();
		ps = new PushSend();
	}

	public static DoorlockAuto getDoorlockAuto() {
		if (DOORLOCK_AUTO_INSTANCE == null) {
			synchronized (DoorlockAuto.class) {
				DOORLOCK_AUTO_INSTANCE = new DoorlockAuto();
			}
		}
		return DOORLOCK_AUTO_INSTANCE;
	}

	public void DoorlockSensorOn(String phone) {

		if (dss == null) {
			dss = new DoorlockScheduleStart();
		}
		if (DoorlockTimer == null) {
			DoorlockTimer = new Timer();
			DoorlockTimer.schedule(dss, 100, 1000 * 1);
			di.setsAuto("true");
			ps.SendAllPush("config", false, phone, null);
		}
	}

	public void DoorlockSensorOff(String phone) {

		if (DoorlockTimer != null && dss != null) {
			DoorlockTimer.cancel();
			DoorlockTimer = null;
			dss = null;
			di.setsAuto("false");
			ps.SendAllPush("config", false, phone, null);
		}

	}

	public void Auto() {
		String sVal = null;
		String tmp = di.getsTurn();
		sVal = ds.SensorValue();
		di.setsTurn(sVal);

		if ("HIGH".equals(sVal)) {
			if (conf.isWarning() && "LOW".equals(tmp)) {
				ac.WarningOn(true, null, "도어락이 열렸습니다.");
				
				System.out.println("[동작/경고] 도어락 열림");
			} else if("LOW".equals(tmp)){
				ps.SendAllPush("config", false, null, null);
				
				System.out.println("[동작] 도어락 열림");
			}
		} else if ("LOW".equals(sVal)) {
			if("HIGH".equals(tmp)){
				ps.SendAllPush("config", false, null, null);
				
				System.out.println("[동작] 도어락 닫힘");
			}
		}

	}
}

class DoorlockScheduleStart extends TimerTask {

	public void run() {
		DoorlockAuto da = DoorlockAuto.getDoorlockAuto();
		da.Auto();
	}
}