package com.hour24.rsa;

public class Decode {

	private static String ranint[][] = {
		{ "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" },
		{ "5", "9", "1", "2", "3", "0", "4", "6", "7", "8" },
		{ "4", "7", "6", "8", "5", "3", "9", "0", "2", "1" },
		{ "6", "0", "3", "9", "1", "5", "8", "7", "4", "2" },
		{ "0", "9", "2", "4", "6", "1", "5", "8", "7", "3" },
		{ "9", "4", "3", "6", "5", "1", "0", "8", "7", "2" },
		{ "3", "5", "7", "6", "2", "4", "1", "0", "8", "9" },
		{ "9", "8", "3", "6", "1", "5", "7", "2", "0", "4" } };
	private static String ranstring[][] = {
		{ "A", "0", "~", "W", "K", "P", "(", "C", "H", ")", "$", "B", "!",
				"D" },
		{ "?", "A", "3", "4", "Y", "P", "Z", "M", "*", "O", "H", "G", "*",
				"@" },
		{ "4", "#", "@", "!", "G", "D", "I", "J", "K", "L", ")", "0", "$",
				"V" },
		{ "Q", ")", "P", "@", "G", "V", "!", "R", "1", "2", "A", "{", ":",
				"W" },
		{ "A", "G", "U", "F", "?", "@", "4", "X", "6", "T", "_", "O", "P",
				"-" },
		{ "Q", "9", "$", "P", "U", "W", "4", ")", "1", "2", "}", "~", "=",
				"T" },
		{ "*", "!", "8", "9", "3", "4", "7", "0", "D", "J", "F", "G", "B",
				"#" } };

	public static String toTax(String req) {
		String result = toTaxString(req);
		return result;
	}

	private static String toTaxString(String req) {

		String Fresult = "", Lresult = "", chreq, arrstr;
		int reqnum = 0, arrnum = 0, subrand1, subrand2, numarr = 0;
		char creq;
		int reqlen = req.length(); // request 길이값

		subrand1 = Integer.parseInt(req.substring(0, 1)); // 랜덤컷 (숫자배열)
		subrand2 = Integer.parseInt(req.substring(reqlen - 1)); // 랜덤컷 (문자배열)
		req = req.substring(1, reqlen - 1);
		reqlen = req.length(); // 길이값 다시 설정
		String finalint[] = new String[10]; // 랜덤배열 넣어주는 배열
		String finalstring[] = new String[10]; // 랜덤배열 넣어주는 배열
		// 배열에 랜덤배열 집어넣기
		for (int r = 0; r < 10; r++) {
			finalint[r] = ranint[subrand1][r];
			finalstring[r] = ranstring[subrand2][r];
		}

		// 첫번쨰 문자를 변환
		while (reqnum < reqlen) {
			creq = req.charAt(reqnum);
			chreq = Character.toString(creq);
			arrstr = finalstring[arrnum];
			if (chreq.equals(arrstr)) {
				Fresult += arrnum;
				arrnum = 0;
				reqnum++;
			} else {
				arrnum++;
			}
		}
		arrnum = 0;
		reqnum = 0;
		// 두번째 문자를 변환
		creq = Fresult.charAt(reqnum);
		chreq = Character.toString(creq);
		numarr = Integer.parseInt(chreq);
		while (reqnum < reqlen) {
			creq = Fresult.charAt(reqnum);
			chreq = Character.toString(creq);
			numarr = Integer.parseInt(chreq);
			Lresult += finalint[numarr];
			reqnum++;
		}
		String result = Lresult;

		return result;
	}

}
