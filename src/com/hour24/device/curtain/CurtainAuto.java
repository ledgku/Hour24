package com.hour24.device.curtain;

import java.util.Timer;
import java.util.TimerTask;

import com.hour24.log.ControlLog;
import com.hour24.user.PushSend;

public class CurtainAuto {
	
	//private static CurtainAuto CURTAIN_AUTO_INSTANCE = new CurtainAuto();
	private volatile static CurtainAuto CURTAIN_AUTO_INSTANCE = null;
	private CurtainInfo ci = null;
	private CurtainXML cx = null;
	private PushSend ps = null;
	private ControlLog conlog = null;
	private CurtainScheduleStart css = null;
	private Timer CurtainTimer = null;
	
	private static String sMyPhone = null;
	
	private CurtainAuto(){
		ci = CurtainInfo.getCurtainInfo();
		cx = CurtainXML.getCurtainXML();
		ps = new PushSend();
		conlog = new ControlLog();
	}
	
	public static CurtainAuto getCurtainAuto() {
		if (CURTAIN_AUTO_INSTANCE == null) {
			synchronized (CurtainAuto.class) {
				CURTAIN_AUTO_INSTANCE = new CurtainAuto();
			}
		}
		return CURTAIN_AUTO_INSTANCE;
	}

	// 커튼 자동 시작
	public void CurtainAutoOn(String sToken) {
		conlog.TraceLog("커튼", null, "true", "auto");
		ps.SendAllPush("Curtain", false, sToken, null);
		if (css == null) {
			css = new CurtainScheduleStart();
		}
		if (CurtainTimer == null) {
			CurtainTimer = new Timer();
			CurtainTimer.schedule(css, 100, 1000 * 15);
			System.out.println("[설정] 커튼 자동 ON");
		}
		
		ci.setAuto(true);	// 환경설정 true
		cx.setXMLAutoValue("true"); // XML파일 설정 true
	}

	// 커튼 자동 중지
	public void CurtainAutoOff(String sPhone) {
		conlog.TraceLog("커튼", null, "false", "auto");
		ps.SendAllPush("Curtain", false, sPhone, null);
		if (CurtainTimer != null && css != null) {
			CurtainTimer.cancel();
			CurtainTimer = null;
			css = null;
			System.out.println("[설정] 커튼 자동 OFF");
		}
		ci.setAuto(false);	// 환경설정 false 
		cx.setXMLAutoValue("false");	// XML파일 설정 false
	}
	
	public static void Auto() {
		
	}
}

class CurtainScheduleStart extends TimerTask {

	public void run() {
		CurtainAuto.Auto();
	}
}