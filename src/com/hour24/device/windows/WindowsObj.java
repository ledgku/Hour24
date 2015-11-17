package com.hour24.device.windows;

import com.pi4j.io.gpio.GpioPinDigitalInput;

public class WindowsObj {
	private String sNickName;
	private String sPinName; // Pin 이름
	private GpioPinDigitalInput pin; // Pin 정보
	private String sTurn; // 개폐 여부

	public WindowsObj(String sNickName, String sPinName, GpioPinDigitalInput pin, String sTurn) {
		this.sNickName = sNickName;
		this.sPinName = sPinName;
		this.pin = pin;
		this.sTurn = sTurn;
	}

	public String getNickName(){return sNickName;}
	public String getPinName(){return sPinName;}
	public GpioPinDigitalInput getPin(){return pin;}
	public String getTurn(){return sTurn;}
	
	//public void setNickName(String nickName){this.nickName = nickName;}
	//public void setPinName(String pinName){this.pinName = pinName;}
	public void setPin(GpioPinDigitalInput pin){this.pin = pin;}
	public void setTurn(String sTurn){this.sTurn = sTurn;}
}
