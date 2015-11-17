package com.hour24.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

import com.hour24.http.EndPoint;

public class ControlLog {
	SimpleDateFormat current_date = null;
	Date date = null;

	public ControlLog() {
		date = new Date();
		current_date = new SimpleDateFormat("yyyy-MM-dd");
	}

	public JSONObject readLog() {
		File files = new File(EndPoint.LOG_FILE_PATH + current_date.format(date) + ".txt");
		if (files.exists()) {
			JSONObject jsObj = new JSONObject();
			String sLine = "";
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(files));
				int i=0;
				while ((sLine = br.readLine()) != null) {
					jsObj.put(i, sLine);
					i++;
				}
				br.close();
				return jsObj;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void TraceLog(String sDevice, String sPinName, String sTurn, String sType) {

		SimpleDateFormat current_datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		File newfile = new File(EndPoint.LOG_FILE_PATH + current_date.format(date) + ".txt");
		String sCurrent = "[" + current_datetime.format(date) + "] ";
		String message = "";

		switch (sDevice) {
		case "전등":
			if ("sTurn".equals(sType)) {
				message += "[" + sDevice + "] " + sPinName;
				switch (sTurn) {
				case "true":
					message += " ON";
					break;
				case "false":
					message += " OFF";
					break;
				default: // 커튼

					break;
				}
			} else if ("auto".equals(sType)) {
				message += "[" + sDevice + "]";
				switch (sTurn) {
				case "true":
					message += " 자동조절 시작";
					break;
				case "false":
					message += " 자동조절 종료";
					break;
				default:
					break;
				}
			}
			break;
		case "커튼":
			if ("sTurn".equals(sType)) {
				message += "[" + sDevice + "] " + sPinName;
				message += " 레벨:" + sTurn;
			} else if ("auto".equals(sType)) {

			}
			break;
		case "창문":
			if ("sTurn".equals(sType)) {
				message += "[" + sDevice + "] " + sPinName;
				switch (sTurn) {
				case "open":
					message += " OPEN";
					break;
				case "close":
					message += " CLOSE";
					break;
				default:
					break;
				}
			} else if ("auto".equals(sType)) {
				message += "[" + sDevice + "]";
				switch (sTurn) {
				case "true":
					message += " 감지 시작";
					break;
				case "false":
					message += " 감지 종료";
					break;
				default:
					break;
				}
			} else if ("sec".equals(sType)) {
				message += "[" + sDevice + "]";
				switch (sTurn) {
				case "true":
					message += " 보안알림 시작";
					break;
				case "false":
					message += " 보안알림 종료";
					break;
				default:
					break;
				}
			}
			break;
		case "CPU":
			if ("sTurn".equals(sType)) {
				message += "[" + sDevice + "]";
				switch (sTurn) {
				case "true":
					message += " Fan 작동 시작";
					break;
				case "false":
					message += " Fan 작동 종료";
					break;
				default:
					break;
				}
			} else if ("auto".equals(sType)) {
				message += "[" + sDevice + "]";
				switch (sTurn) {
				case "true":
					message += " Fan 자동 시작";
					break;
				case "false":
					message += " Fan 자동 종료";
					break;
				default:
					break;
				}
			} else if ("temp".equals(sType)) {
				message += "[" + sDevice + "]";
				message += " CPU 온도:" + sTurn;
			}
			break;
		default:
			break;
		}

		if (message != null) {
			String sResult = sCurrent + message;
			try {
				if (!newfile.exists()) {
					newfile.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newfile, true), "UTF-8"));
				bw.newLine();
				bw.write(sResult); // 파일에 문자열을 저장한다.
				bw.flush();
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
