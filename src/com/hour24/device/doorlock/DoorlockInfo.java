package com.hour24.device.doorlock;

public class DoorlockInfo {
	private volatile static DoorlockInfo DOORLOCK_INFO_INSTANCE = null;
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

	public static DoorlockInfo getDoorlockInfo() {
		if (DOORLOCK_INFO_INSTANCE == null) {
			synchronized (DoorlockInfo.class) {
				DOORLOCK_INFO_INSTANCE = new DoorlockInfo();
			}
		}
		return DOORLOCK_INFO_INSTANCE;
	}
}
