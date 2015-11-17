package com.hour24.device.curtain;


public class CurtainObj {
	private String sNickName;
	private String sPinName;				// Pin 이름
	private int nTurn;				// 전원 여부
	
	public CurtainObj(String sNickName, String sPinName, int nTurn){
		this.sNickName = sNickName;
		this.sPinName = sPinName;
		this.nTurn = nTurn;
	}
	
	public String getNickName(){return sNickName;}
	public String getPinName(){return sPinName;}
	public int getTurn(){return nTurn;}
	
	//public void setNickName(String nickName){this.nickName = nickName;}
	//public void setPinName(String pinName){this.pinName = pinName;}
	public void setTurn(int nTurn){this.nTurn = nTurn;}
}
