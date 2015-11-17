package com.hour24.device.windows;

import java.util.Timer;
import java.util.TimerTask;

import com.hour24.config.Config;
import com.hour24.device.alert.AlertControl;
import com.hour24.log.ControlLog;
import com.hour24.user.PushSend;

public class WindowsAuto {
	private volatile static WindowsAuto WINDOWS_AUTO_INSTANCE = null;

	private WindowsScheduleStart wss = null;
	public Timer WindowsTimer = null;
	private WindowsSensor ws = null;
	private PushSend ps = null;
	private AlertControl ac = null;
	private WindowsInfo wi = null;
	private WindowsList wl = null;
	private WindowsXML wx = null;
	private ControlLog conlog = null;
	private Config conf = null;

	private WindowsAuto() {
		wi = WindowsInfo.getWindowsInfo();
		ps = new PushSend();
		ac = new AlertControl();
		wl = new WindowsList();
		ws = new WindowsSensor();
		wx = WindowsXML.getWindowsXML();
		conlog = new ControlLog();
		conf = Config.getConfig();
	}

	public static WindowsAuto getWindowsAuto() {
		if (WINDOWS_AUTO_INSTANCE == null) {
			synchronized (WindowsAuto.class) {
				WINDOWS_AUTO_INSTANCE = new WindowsAuto();
			}
		}
		return WINDOWS_AUTO_INSTANCE;
	}

	public void WindowsSensorOn(String sToken) {
		wi.setAuto(true);
		if (wss == null) {
			wss = new WindowsScheduleStart();
		}
		if (WindowsTimer == null) {
			WindowsTimer = new Timer();
			WindowsTimer.schedule(wss, 100, 1000 * 1);

			ps.SendAllPush("config", false, sToken, null);
			wx.setXMLAutoValue("true");
			conlog.TraceLog("창문", null, "true", "auto");
			System.out.println("[설정] Windows Auto ON");
		}
	}

	// 전등 자동 종료
	public void WindowsSensorOff(String sToken) {
		wi.setAuto(false);
		if (wi.getSecurity() == true) {
			wi.setSecurity(false);
			wx.setXMLSecurityValue("false");
			conlog.TraceLog("창문", null, "false", "sec");
		}

		if (WindowsTimer != null && wss != null) {
			WindowsTimer.cancel();
			WindowsTimer = null;
			wss = null;

			ps.SendAllPush("config", false, sToken, null);
			wx.setXMLAutoValue("false");
			conlog.TraceLog("창문", null, "false", "auto");
			System.out.println("[설정] Windows Auto OFF");
		}

	}

	public void Auto() {
		String sVal = null;
		for (WindowsObj v : wi.aWindowsArr) {
			String oc = null;
			sVal = ws.SensorValue(wl.searchPin(v.getPinName()), v.getPinName()); // LOW열림/HIGH닫힘
			if ("LOW".equals(sVal)) {	
				oc = "OPEN";
			} else if ("HIGH".equals(sVal)) {
				oc = "CLOSE";
			}
			
			// 이전 상태와 비교
			if (!v.getTurn().equals(oc) && oc.equals("CLOSE")) {
				v.setTurn(oc);
				wx.setXMLDeivceValue(v.getPinName(), oc);
				ps.SendAllPush("config", false, null, null);

				conlog.TraceLog("창문", v.getPinName(), "close", "turn");
				System.out.println("[감지] " + v.getNickName() + " 닫힘");
			} else if (!v.getTurn().equals(oc) && oc.equals("OPEN")) {
				v.setTurn(oc);
				if (conf.isWarning()) {
					ac.WarningOn(true, null, v.getNickName() + "이 열렸습니다.");
				} else {
					ps.SendAllPush("config", false, null, null);
				}
				wx.setXMLDeivceValue(v.getPinName(), oc);

				conlog.TraceLog("창문", v.getPinName(), "open", "turn");
				System.out.println("[감지] " + v.getNickName() + " 열림");
			}

		}
	}
}

class WindowsScheduleStart extends TimerTask {

	public void run() {
		WindowsAuto wa = WindowsAuto.getWindowsAuto();
		wa.Auto();
	}
}