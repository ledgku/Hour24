package com.hour24.device.motion;

import java.util.Timer;
import java.util.TimerTask;

import com.hour24.config.Config;
import com.hour24.device.alert.AlertControl;
import com.hour24.user.PushSend;

public class MotionAuto {
	private volatile static MotionAuto MOTION_AUTO_INSTANCE = null;
	private PushSend ps = null;
	private AlertControl ac = null;
	private MotionScheduleStart mss = null;
	public Timer MotionTimer = null;
	private MotionSensor ms = null;
	private Config conf = null;
	private MotionInfo mi = null;

	private MotionAuto() {
		ms = new MotionSensor();
		ac = new AlertControl();
		conf = Config.getConfig();
		mi = MotionInfo.getMotionInfo();
		ps = new PushSend();
	}

	public static MotionAuto getMotionAuto() {
		if (MOTION_AUTO_INSTANCE == null) {
			synchronized (MotionAuto.class) {
				MOTION_AUTO_INSTANCE = new MotionAuto();
			}
		}
		return MOTION_AUTO_INSTANCE;
	}

	public void MotionSensorOn(String sToken) {

		if (mss == null) {
			mss = new MotionScheduleStart();
		}
		if (MotionTimer == null) {
			MotionTimer = new Timer();
			MotionTimer.schedule(mss, 100, 1000 * 1);
			mi.setsAuto("true");
			ps.SendAllPush("config", false, sToken, null);
			System.out.println("[설정] 모션센서 ON");
		}
		
	}

	public void MotionSensorOff(String sToken) {

		if (MotionTimer != null && mss != null) {
			MotionTimer.cancel();
			MotionTimer = null;
			mss = null;
			mi.setsAuto("false");
			ps.SendAllPush("config", false, sToken, null);
			System.out.println("[설정] 모션센서 OFF");
		}

	}

	public void Auto() {
		String sVal = null;
		String tmp = mi.getsTurn();
		sVal = ms.SensorValue();
		mi.setsTurn(sVal);

		if ("HIGH".equals(sVal)) {
			if (conf.isWarning() && "LOW".equals(tmp)) {
				ac.WarningOn(true, null, "움직임이 감지되었습니다.");
				System.out.println("[감지/경고] 모션센서 감지");
			} else if ("LOW".equals(tmp)) {
				//ps.SendAllPush("config", false, null, null);
				System.out.println("[감지] 모션센서 감지");
			}
		} else if ("LOW".equals(sVal)) {
//			if ("HIGH".equals(sVal)) {
//				ps.SendAllPush("config", false, null, null);
//			}
		}

	}
}

class MotionScheduleStart extends TimerTask {

	public void run() {
		MotionAuto ma = MotionAuto.getMotionAuto();
		ma.Auto();
	}
}