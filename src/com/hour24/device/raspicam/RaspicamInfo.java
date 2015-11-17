package com.hour24.device.raspicam;

public class RaspicamInfo {
	private volatile static RaspicamInfo RASPICAM_INFO_INSTANCE = null;
	public Boolean qTurn = false;

	private RaspicamInfo() {
		
	}

	public static RaspicamInfo getRaspicamInfo() {
		if (RASPICAM_INFO_INSTANCE == null) {
			synchronized (RaspicamInfo.class) {
				RASPICAM_INFO_INSTANCE = new RaspicamInfo();
			}
		}
		return RASPICAM_INFO_INSTANCE;
	}

	public Boolean getTurn() {
		return qTurn;
	}

	public void setTurn(Boolean qTurn) {
		this.qTurn = qTurn;
	}
	
	
}
