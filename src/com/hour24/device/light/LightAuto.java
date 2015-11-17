package com.hour24.device.light;

import java.util.Timer;
import java.util.TimerTask;

import com.hour24.http.EndPoint;
import com.hour24.http.HttpCommunication;
import com.hour24.log.ControlLog;
import com.hour24.user.PushSend;
import com.pi4j.io.gpio.Pin;

public class LightAuto {
	private volatile static LightAuto LIGHT_AUTO_INSTANCE = null;
	private LightControl lc = null;
	private LightInfo li = null;
	private LightXML lx = null;
	private PushSend ps = null;
	private ControlLog conlog = null;
	private LightScheduleStart lss = null;
	private Timer LightTimer = null;
	private String sMyPhone = null;
	private LightSensor ls = null;
	private LightList ll = null;

	private LightAuto() {
		lc = LightControl.getLightControl();
		li = LightInfo.getLightInfo();
		lx = LightXML.getLightXML();
		ls = new LightSensor();
		ll = new LightList();
		ps = new PushSend();
		conlog = new ControlLog();
	}

	public static LightAuto getLightAuto() {
		if (LIGHT_AUTO_INSTANCE == null) {
			synchronized (LightAuto.class) {
				LIGHT_AUTO_INSTANCE = new LightAuto();
			}
		}
		return LIGHT_AUTO_INSTANCE;
	}

	// 전등 자동 시작
	public void LightAutoOn(String phone) {
		li.setAuto(true);
		if (lss == null) {
			lss = new LightScheduleStart();
		}
		if (LightTimer == null) {
			LightTimer = new Timer();
			LightTimer.schedule(lss, 100, 1000 * 10);

			ps.SendAllPush("Light", false, phone, null);
			lx.setXMLAutoValue("true");
			conlog.TraceLog("전등", null, "true", "light-auto");
			System.out.println("[설정] 조도센서 ON");
		}

	}

	// 전등 자동 종료
	public void LightAutoOff(String phone) {
		li.setAuto(false);
		if (LightTimer != null && lss != null) {
			LightTimer.cancel();
			LightTimer = null;
			lss = null;

			ps.SendAllPush("Light", false, phone, null);
			lx.setXMLAutoValue("false");
			conlog.TraceLog("전등", null, "false", "light-auto");
			System.out.println("[설정] 조도센서 OFF");
		}

	}

	public void Auto() {
		Boolean qTurn = false;
		Boolean qBeforeTurn = li.isqAllTurn();
		qTurn = CalcLux(ls.LuxValue());
		
		if (qTurn) {
			if(!qBeforeTurn){
				lc.LightOn(null, "all", sMyPhone, "Auto");
				System.out.println("[동작] 모든 전등 ON");
			}
		} else {
			if(qBeforeTurn){
				lc.LightOff(null, "all", sMyPhone, "Auto");
				System.out.println("[동작] 모든 전등 OFF");
			}
		}
		ps.SendAllPush("Light", false, sMyPhone, null);
	}

	private static boolean CalcLux(int luxValue) {
		if (luxValue < 30) {
			return true;
		} else {
			return false;
		}
	}
}

class LightScheduleStart extends TimerTask {

	public void run() {
		LightAuto la = LightAuto.getLightAuto();
		la.Auto();
	}
}
