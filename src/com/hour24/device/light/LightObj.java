package com.hour24.device.light;

public class LightObj {
	private String sNickName;
	private String sPinName;				// Pin 이름
	private String sPin;	// Pin 정보
	private boolean qTurn;				// 전원 여부
	
	public LightObj(String sNickName, String sPinName, String sPin, boolean qTurn){
		this.sNickName = sNickName;
		this.sPinName = sPinName;
		this.sPin = sPin;
		this.qTurn = qTurn;
	}
	
	public String getNickName(){return sNickName;}
	public String getPinName(){return sPinName;}
	public String getPin(){return sPin;}
	public boolean getTurn(){return qTurn;}
	
	public void setPin(String sPin){this.sPin = sPin;}
	public void setTurn(boolean qTurn){this.qTurn = qTurn;}
}
