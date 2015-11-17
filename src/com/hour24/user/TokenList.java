package com.hour24.user;

import java.util.ArrayList;
import java.util.Iterator;

public class TokenList {
	private volatile static TokenList TOKEN_INSTANCE = null;
	public static ArrayList<TokenObj> aTokenList = new ArrayList<TokenObj>();

	private TokenList() {

	}

	public static TokenList getToken() {
		if (TOKEN_INSTANCE == null) {
			synchronized (TokenList.class) {
				TOKEN_INSTANCE = new TokenList();
			}
		}
		return TOKEN_INSTANCE;
	}

	public String searchPushKey(String sToken) {
		for (TokenObj t : aTokenList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
			if (t.getsToken() != null && t.getsToken().contains(sToken)) {
				return t.getsPushKey();
			}
		}
		return null;
	}

	public boolean searchRight(String sToken) {
		for (TokenObj t : aTokenList) { // LightList에서 기기명에 해당하는 Pin 번호 찾기
			if (t.getsToken() != null && t.getsToken().contains(sToken)) {
				return t.isqRight();
			}
		}
		return false;
	}

	public boolean searchToken(String sToken) {
		for (TokenObj t : aTokenList) { //
			if (t.getsToken() != null && t.getsToken().contains(sToken)) {
				return true;
			}
		}
		return false;
	}

	public boolean deleteOverlapPushkey(String sPushkey) {
		int i = 0;

		if (aTokenList.size() > 0) {
			for (Iterator<TokenObj> iter = aTokenList.iterator(); iter.hasNext();) {
				TokenObj obj = iter.next();
				if (obj.getsPushKey() != null && obj.getsPushKey().contains(sPushkey)) {
					iter.remove();
				}
				i++;
			}
		}

		if (i <= 0) {
			return false;
		} else {
			return true;
		}

	}

	public ArrayList<TokenObj> getAllTokenList() {
		return aTokenList;
	}

}
