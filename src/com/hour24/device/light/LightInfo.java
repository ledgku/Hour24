package com.hour24.device.light;

import java.util.ArrayList;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * <pre>
 * com.hour24.device.light
 * LightInfo.java
 * </pre>
 * 
 * @author Yang.uk.mo
 * @Date 2015. 6. 21.
 * 
 * @comment 조명에 대한 변수 저장
 */
public class LightInfo {
	private volatile static LightInfo LIGHT_INFO_INSTANCE = null;
	public static ArrayList<LightObj> aLightArr = null;
	public boolean qAuto = false;
	public boolean qAllTurn = false;

	

	public static void setPinInfo(){	// 기기 추가
		aLightArr = new ArrayList<LightObj>();
		aLightArr.add(new LightObj("안방", "light-01", "1", false));
		aLightArr.add(new LightObj("거실", "light-02", "2", false));
		aLightArr.add(new LightObj("방1", "light-03", "3", false));
		aLightArr.add(new LightObj("방2", "light-04", "4", false));
	}
	
	private LightInfo() {
	}
	
	public static LightInfo getLightInfo(){
		if(LIGHT_INFO_INSTANCE == null){
			synchronized (LightInfo.class) {
				LIGHT_INFO_INSTANCE = new LightInfo();
			}
		}
		return LIGHT_INFO_INSTANCE;
	}
	
	public boolean getAuto(){return this.qAuto;}
	public void setAuto(boolean qAuto){this.qAuto = qAuto;}
	public boolean isqAllTurn() {return qAllTurn;}
	public void setqAllTurn(boolean qAllTurn) {this.qAllTurn = qAllTurn;}
}

