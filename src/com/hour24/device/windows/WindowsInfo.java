package com.hour24.device.windows;

import java.util.ArrayList;

import com.pi4j.io.gpio.GpioPinDigitalInput;

public class WindowsInfo {
	private volatile static WindowsInfo WINDOWS_INFO_INSTANCE = null;
	public static ArrayList<WindowsObj> aWindowsArr = null;
	private boolean qAuto = false;
	private boolean qSecurity = false;

	public static void setPinInfo() {
		aWindowsArr = new ArrayList<WindowsObj>();
		aWindowsArr.add(new WindowsObj("좌측 창문", "windows-01", null, "CLOSE"));
		aWindowsArr.add(new WindowsObj("우측 창문", "windows-02", null, "CLOSE"));
	}

	private WindowsInfo() {
	}

	public static WindowsInfo getWindowsInfo() {
		if (WINDOWS_INFO_INSTANCE == null) {
			synchronized (WindowsInfo.class) {
				WINDOWS_INFO_INSTANCE = new WindowsInfo();
			}
		}
		return WINDOWS_INFO_INSTANCE;
	}
	
	public boolean getAuto(){return this.qAuto;}
	public void setAuto(boolean qAuto){this.qAuto = qAuto;}
	public boolean getSecurity(){return this.qSecurity;}
	public void setSecurity(boolean qSecurity){this.qSecurity = qSecurity;}
}

/*class WindowsObj {
	private String nickName;
	private String pinName; // Pin 이름
	private GpioPinDigitalInput pin; // Pin 정보
	private String turn; // 개폐 여부

	public WindowsObj(String nickName, String pinName, GpioPinDigitalInput pin, String turn) {
		this.nickName = nickName;
		this.pinName = pinName;
		this.pin = pin;
		this.turn = turn;
	}

	public String getNickName(){return nickName;}
	public String getPinName(){return pinName;}
	public GpioPinDigitalInput getPin(){return pin;}
	public String getTurn(){return turn;}
	
	//public void setNickName(String nickName){this.nickName = nickName;}
	//public void setPinName(String pinName){this.pinName = pinName;}
	public void setPin(GpioPinDigitalInput pin){this.pin = pin;}
	public void setTurn(String turn){this.turn = turn;}
}*/