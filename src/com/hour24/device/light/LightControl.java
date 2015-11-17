package com.hour24.device.light;

import com.hour24.log.ControlLog;
import com.hour24.raspi.ExecuteCommand;
import com.hour24.user.PushSend;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * <pre>
 * com.hour24.device.light
 * LightControl.java
 * </pre>
 * 
 * @author Yang.uk.mo
 * @Date 2015. 6. 21.
 * 
 * @comment 조명 컨트롤하는 클래스
 */
public class LightControl {
	private volatile static LightControl LIGHT_CONTROL_INSTANCE = null;
	private GpioController gpio = null;

	private LightInfo li = null;
	private LightXML lx = null;
	private PushSend ps = null;
	private ControlLog conlog = null;
	private ExecuteCommand ec = null;

	private LightControl() {
		gpio = GpioFactory.getInstance();
		li = LightInfo.getLightInfo();
		lx = LightXML.getLightXML();
		ps = new PushSend();
		conlog = new ControlLog();
		ec = new ExecuteCommand();
	}

	public static LightControl getLightControl() {
		if (LIGHT_CONTROL_INSTANCE == null) {
			synchronized (LightControl.class) {
				LIGHT_CONTROL_INSTANCE = new LightControl();
			}
		}
		return LIGHT_CONTROL_INSTANCE;
	}

	public void LightOn(String sPin, String sPinName, String sToken, String sType) {
		if ("all".equals(sPinName)) {
			for (LightObj d : li.aLightArr) {
				d.setTurn(true);
				lx.setXMLDeivceValue(d.getPinName(), "true"); // XML저장
			}
			ec.executeCommand("sudo python /home/pi/Light.py ra1rr"); // 무선제어
			li.setqAllTurn(true);
			System.out.println("[동작] 모든 전등 ON");
		} else {
			for (LightObj d : li.aLightArr) {
				if (d.getPinName() != null && d.getPinName().contains(sPinName) && d.getPin() != null) { // pin정보가있을경우
					d.setTurn(true); // 설정 변경
					lx.setXMLDeivceValue(d.getPinName(), "true"); // XML저장
					ec.executeCommand("sudo python /home/pi/Light.py r" + d.getPin() + "1rr"); // 무선제어
					System.out.println("[동작] " + d.getNickName() + " 전등 ON");
					break;
				}
			}
		}

		if ("Turn".equals(sType)) {
			ps.SendAllPush("Light", false, sToken, null);
		} else if ("Auto".equals(sType)) {

		}
		conlog.TraceLog("전등", sPinName, "true", "turn");
	}

	public void LightOff(String sPin, String sPinName, String sToken, String sType) {
		if ("all".equals(sPinName)) {
			for (LightObj d : li.aLightArr) {
				d.setTurn(false); // 설정 변경
				lx.setXMLDeivceValue(d.getPinName(), "false"); // XML저장
			}
			ec.executeCommand("sudo python /home/pi/Light.py ra0rr"); // 실행
			li.setqAllTurn(false);
			System.out.println("[동작] 모든 전등 OFF");
		} else {
			for (LightObj d : li.aLightArr) {
				if (d.getPinName() != null && d.getPinName().contains(sPinName) && d.getPin() != null) {
					d.setTurn(false); // 설정 변경
					lx.setXMLDeivceValue(d.getPinName(), "false"); // XML저장
					ec.executeCommand("sudo python /home/pi/Light.py r" + d.getPin() + "0rr"); // 실행
					System.out.println("[동작] " + d.getNickName() + " 전등 OFF");
					break;
				}
			}
		}

		if ("Turn".equals(sType)) {
			ps.SendAllPush("Light", false, sToken, null); // 푸쉬전송
		} else if ("Auto".equals(sType)) {

		}
		conlog.TraceLog("전등", sPinName, "false", "turn"); // 로그작성
	}
}
