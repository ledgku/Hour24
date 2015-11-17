package com.hour24.device.alert;

import com.hour24.config.Config;
import com.hour24.log.ControlLog;
import com.hour24.raspi.ExecuteCommand;
import com.hour24.user.PushSend;

public class AlertControl {
	private ExecuteCommand ec = null;
	private ControlLog conlog = null;
	private Config conf = null;
	private PushSend ps = null;

	public AlertControl() {
		// TODO Auto-generated constructor stub
		ec = new ExecuteCommand();
		conf = Config.getConfig();
		ps = new PushSend();
	}

	public void WarningOn(boolean qRefresh, String sToken, String sMessage) {
		ec.executeCommand("sudo python /home/pi/Warning.py a1aaa");
		if (conf.isWarning()) {
			ps.SendAllPush("warning", true, null, sMessage);
		}
		System.out.println("[동작/경고] 경광등 ON");
	}

	public void WarningOff() {
		ec.executeCommand("sudo python /home/pi/Warning.py a0aaa");
		System.out.println("[동작] 경광등 OFF");
	}

	public void AlertChange(String sTurn) {
		if ("red".equals(sTurn)) {
			ec.executeCommand("sudo python /home/pi/Warning.py a1aaa");
			System.out.println("[동작/경고] 경광등 ON");
		} else if ("green".equals(sTurn)) {
			ec.executeCommand("sudo python /home/pi/Warning.py a0aaa");
			System.out.println("[동작] 경광등 OFF");
		}
	}
}
