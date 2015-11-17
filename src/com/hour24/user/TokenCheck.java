package com.hour24.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hour24.rsa.RSADecode;

public class TokenCheck {

	public TokenCheck() {
		// TODO Auto-generated constructor stub
	}

	public boolean isToken(String sToken) { // 토큰 유효성 검사
		String decodedToken = DecodingToken(sToken);
		if (decodedToken.length() == 14) {
			return CheckTerm(decodedToken);
		}
		return false;
	}

	private String DecodingToken(String sToken) {	// Decoding > 2015 12 25 01 05 30
		RSADecode decode = new RSADecode(sToken);
		return decode.onProc();
	}

	private boolean CheckTerm(String sToken) { // 날짜 유효성 검사
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault());
			long now = System.currentTimeMillis();
			Date nowDate = new Date(now);
			Date tokenData = dateFormat.parse(sToken);

			return nowDate.before(tokenData);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
