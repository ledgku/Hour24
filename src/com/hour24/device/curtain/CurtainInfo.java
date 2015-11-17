package com.hour24.device.curtain;

import java.util.ArrayList;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class CurtainInfo {
	private volatile static CurtainInfo CURTAIN_INFO_INSTANCE = null;
	public static ArrayList<CurtainObj> aCurtainArr = null;
	public boolean qAuto = false;

	public static void setPinInfo() { // 기기 추가
		aCurtainArr = new ArrayList<CurtainObj>();
		aCurtainArr.add(new CurtainObj("거실1", "curtain-01", 0));
		aCurtainArr.add(new CurtainObj("거실2", "curtain-02", 0));
	}

	private CurtainInfo() {
		
	}

	public static CurtainInfo getCurtainInfo() {
		if (CURTAIN_INFO_INSTANCE == null) {
			synchronized (CurtainInfo.class) {
				CURTAIN_INFO_INSTANCE = new CurtainInfo();
			}
		}
		return CURTAIN_INFO_INSTANCE;
	}

	public boolean getAuto() {
		return this.qAuto;
	}

	public void setAuto(boolean qAuto) {
		this.qAuto = qAuto;
	}
}

/*class CurtainObj{
	private String nickName;
	private String pinName;				// Pin 이름
	private GpioPinDigitalOutput pin_step;	// Pin 정보
	private GpioPinDigitalOutput pin_dir;	// Pin 정보
	private GpioPinDigitalOutput pin_ena;	// Pin 정보
	private int turn;				// 전원 여부
	
	public CurtainObj(String nickName, String pinName, GpioPinDigitalOutput pin_step, GpioPinDigitalOutput pin_dir, GpioPinDigitalOutput pin_ena, int turn){
		this.nickName = nickName;
		this.pinName = pinName;
		this.pin_step = pin_step;
		this.pin_dir = pin_dir;
		this.pin_ena = pin_ena;
		this.turn = turn;
	}
	
	public String getNickName(){return nickName;}
	public String getPinName(){return pinName;}
	public GpioPinDigitalOutput getStepPin(){return pin_step;}
	public GpioPinDigitalOutput getDirPin(){return pin_dir;}
	public GpioPinDigitalOutput getEnaPin(){return pin_ena;}
	public int getTurn(){return turn;}
	
	//public void setNickName(String nickName){this.nickName = nickName;}
	//public void setPinName(String pinName){this.pinName = pinName;}
	public void setStepPin(GpioPinDigitalOutput pin_step){this.pin_step = pin_step;}
	public void setDirPin(GpioPinDigitalOutput pin_dir){this.pin_dir = pin_dir;}
	public void setEnaPin(GpioPinDigitalOutput pin_ena){this.pin_ena = pin_ena;}
	public void setTurn(int turn){this.turn = turn;}
}*/
