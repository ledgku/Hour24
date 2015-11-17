package com.hour24.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpCommunication {
	// Post 전송
	private static String sLine = null;
	private static String sResult = null;

	public String Http(String Param, String Url) {
		try {
			URL url = new URL(Url);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(Param);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((sLine = rd.readLine()) != null) {
				sResult = sLine;
			}
			wr.close();
			rd.close();

			return sResult;
		} catch (Exception e) {
			return "fail : " + e.toString();
		}
	}
}
