package com.hour24.device.motion;

public class MotionInfo {
	private volatile static MotionInfo MOTION_INFO_INSTANCE = null;
	private String sAuto = "false";
	private String sTurn = "LOW";

	public String getsAuto() {
		return sAuto;
	}

	public void setsAuto(String sAuto) {
		this.sAuto = sAuto;
	}

	public String getsTurn() {
		return sTurn;
	}

	public void setsTurn(String sTurn) {
		this.sTurn = sTurn;
	}

	public static MotionInfo getMotionInfo() {
		if (MOTION_INFO_INSTANCE == null) {
			synchronized (MotionInfo.class) {
				MOTION_INFO_INSTANCE = new MotionInfo();
			}
		}
		return MOTION_INFO_INSTANCE;
	}
}
