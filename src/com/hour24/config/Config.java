package com.hour24.config;

public class Config {
	private volatile static Config CONFIG_INSTANCE = null;
	private boolean qWarning = false;

	private Config() {
		// TODO Auto-generated constructor stub
	}
	
	public static Config getConfig(){
		if(CONFIG_INSTANCE == null){
			synchronized (Config.class) {
				CONFIG_INSTANCE = new Config();
			}
		}
		return CONFIG_INSTANCE;
	}

	public boolean isWarning() {
		return qWarning;
	}

	public void setWarning(boolean qWarning) {
		this.qWarning = qWarning;
	}

}
