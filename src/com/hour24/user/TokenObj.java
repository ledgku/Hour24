package com.hour24.user;

public class TokenObj {
	private String sToken;
	private String sPushKey;
	private boolean qRight;

	public boolean isqRight() {
		return qRight;
	}

	public void setqRight(boolean qRight) {
		this.qRight = qRight;
	}

	public String getsToken() {
		return sToken;
	}

	public void setsToken(String sToken) {
		this.sToken = sToken;
	}

	public String getsPushKey() {
		return sPushKey;
	}

	public void setsPushKey(String sPushKey) {
		this.sPushKey = sPushKey;
	}

	public TokenObj(String sToken, String sPushKey, boolean qRight) {
		// TODO Auto-generated constructor stub
		this.sToken = sToken;
		this.sPushKey = sPushKey;
		this.qRight = qRight;
	}
}
