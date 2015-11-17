package com.hour24.user;

import java.util.ArrayList;

public class UserList {
	private volatile static UserList USER_LIST_INSTANCE = null;
	public static ArrayList<UserObj> aUserList = new ArrayList<UserObj>();

	private UserList() {
	}

	public static UserList getUserList() {
		if (USER_LIST_INSTANCE == null) {
			synchronized (UserList.class) {
				USER_LIST_INSTANCE = new UserList();
			}
		}
		return USER_LIST_INSTANCE;
	}
	
	public String searchUser(String phone){
		for(UserObj f : aUserList){	// LightList에서 기기명에 해당하는 Pin 번호 찾기
			if(f.getDevice() != null && f.getPhone().contains(phone)){
				return f.getDevice();
			}
		}
		return null;
	}
	
	public void setUserList(String sPhone, String sDevice, String sLevel){
		String confirm = searchUser(sPhone);
		if(confirm == null){
			aUserList.add(new UserObj(sPhone, sDevice, sLevel));
		}
	}
	
	public Boolean checkUser(String sPhone, String sDevice){
		for(UserObj f : aUserList){	// LightList에서 기기명에 해당하는 Pin 번호 찾기
			if(f.getDevice() != null && f.getPhone().contains(sPhone) && f.getDevice().contains(sDevice)){
				return true;
			}
		}	
		return false;
	}
	
	public ArrayList<UserObj> getAllUserList(){
		return aUserList;
	}
}

class UserObj{
	private String sPhone;
	private String sDevice;
	private String sLevel;
	
	public UserObj(String sPhone, String sDevice, String sLevel){
		this.sPhone = sPhone;
		this.sDevice = sDevice;
		this.sLevel = sLevel;
	}
	
	public String getPhone(){return this.sPhone;}
	public String getDevice(){return this.sDevice;}
	public String getLevel(){return this.sLevel;}
}