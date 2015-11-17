package com.hour24.device.curtain;

import com.hour24.log.ControlLog;
import com.hour24.raspi.ExecuteCommand;
import com.hour24.user.PushSend;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.Pin;

public class CurtainControl {
	private CurtainInfo ci = null;
	private CurtainXML cx = null;
	private volatile static CurtainControl CURTAIN_CONTROL_INSTANCE = null;
	private static GpioController gpio = null;
	private PushSend ps = null;
	private ControlLog conlog = null;
	private ExecuteCommand ec = null;

	private CurtainControl() {
		ci = CurtainInfo.getCurtainInfo();
		cx = CurtainXML.getCurtainXML();
		gpio = GpioFactory.getInstance();
		ps = new PushSend();
		ec = new ExecuteCommand();
	}

	public static CurtainControl getCurtainControl() {
		if (CURTAIN_CONTROL_INSTANCE == null) {
			synchronized (CurtainControl.class) {
				CURTAIN_CONTROL_INSTANCE = new CurtainControl();
			}
		}
		return CURTAIN_CONTROL_INSTANCE;
	}

	// 커튼 조절
	public void CurtainTurn(int nTurn, String sPinName, String sToken) throws InterruptedException {
		conlog = new ControlLog();
		conlog.TraceLog("커튼", sPinName, Integer.toString(nTurn), "turn");
		int nCycleStep = 200 + 220;
		int nType = 0; // 현재 레벨

		String sCurtain = "";
		String cmd = null;
		if ("curtain-01".equals(sPinName)) {
			sCurtain = "c1";
		} else if ("curtain-02".equals(sPinName)) {
			sCurtain = "c2";
		}

		// 커튼 검색
		for (CurtainObj d : ci.aCurtainArr) {
			if (d.getPinName() != null && d.getPinName().contains(sPinName)) {

				nType = d.getTurn(); // 현재 레벨을 가져옴

				if (nTurn > nType) { // 조절할 레벨 > 현재 레벨
					int nGap = nTurn - nType; // 레벨 차이
					d.setTurn(nTurn); // 환경설정 적용
					cx.setXMLDeivceValue(d.getPinName(), nTurn + ""); // XML파일
																		// 설정
																		// 적용
					ps.SendAllPush("Curtain", false, sToken, null); // 푸쉬 전송			
					System.out.println("[동작] " + sPinName + "커튼 " + nGap + "회전 하강");
					
					if (nGap < 10) {
						cmd = "sudo python /home/pi/Curtain.py " + sCurtain + "0" + "0" +  nGap;
						ec.executeCommand(cmd);
					} else if (nGap >= 10) {
						cmd = "sudo python /home/pi/Curtain.py " + sCurtain + "0" + "11";
						ec.executeCommand(cmd);
					}
					

				} else if (nTurn < nType) { // 조절할 레벨 < 현재레벨
					int nGap = nType - nTurn; // 레벨 차이
					d.setTurn(nTurn); // 환경설정 적용
					cx.setXMLDeivceValue(d.getPinName(), nTurn + ""); // XML파일
																		// 설정
																		// 적용
					ps.SendAllPush("Curtain", false, sToken, null); // 푸쉬 전송
					System.out.println("[동작] " + sPinName + "커튼 " + nGap + "회전 상승");

					if (nGap < 10) {
						cmd = "sudo python /home/pi/Curtain.py " + sCurtain + "1" + "0" + nGap;
						ec.executeCommand(cmd);
					} else if (nGap >= 10) {
						cmd = "sudo python /home/pi/Curtain.py " + sCurtain + "1" + "11";
						ec.executeCommand(cmd);
					}
				}

				break;
			}
		}
	}
}
