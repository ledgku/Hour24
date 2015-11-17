package com.hour24.raspi;

import java.util.ArrayList;
import com.pi4j.io.gpio.GpioPinDigitalOutput;


public class CPUInfo {
	private volatile static CPUInfo CPU_INFO_INSTANCE = null;
	public static ArrayList<CPUObj> CPUArr = null;
	public boolean qAuto = false;
	public boolean qTurn = false;
	
	public static void setPinInfo(){	// 기기 추가
		CPUArr = new ArrayList<CPUObj>();
		CPUArr.add(new CPUObj("CPU FAN", "fan-01", null, false));
	}
	
	private CPUInfo() {
	}
	
	public static CPUInfo getCPUInfo(){
		if(CPU_INFO_INSTANCE == null){
			synchronized (CPUInfo.class) {
				CPU_INFO_INSTANCE = new CPUInfo();
			}
		}
		return CPU_INFO_INSTANCE;
	}
	
	public boolean getAuto(){return this.qAuto;}
	public void setAuto(boolean qAuto){this.qAuto = qAuto;}
	public boolean getTurn(){return this.qTurn;}
	public void setTurn(boolean qTurn){this.qTurn = qTurn;}
}

class CPUObj{
	private String sNickName;
	private String sPinName;				// Pin 이름
	private GpioPinDigitalOutput pin;	// Pin 정보
	private boolean qTurn;				// 전원 여부
	
	public CPUObj(String sNickName, String sPinName, GpioPinDigitalOutput pin, boolean qTurn){
		this.sNickName = sNickName;
		this.sPinName = sPinName;
		this.pin = pin;
		this.qTurn = qTurn;
	}
	
	public String getNickName(){return sNickName;}
	public String getPinName(){return sPinName;}
	public GpioPinDigitalOutput getPin(){return pin;}
	public boolean getTurn(){return qTurn;}
	
	//public void setNickName(String nickName){this.nickName = nickName;}
	//public void setPinName(String pinName){this.pinName = pinName;}
	public void setPin(GpioPinDigitalOutput pin){this.pin = pin;}
	public void setTurn(boolean qTurn){this.qTurn = qTurn;}
}