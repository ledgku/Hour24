package com.hour24.device.light;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * <pre>
 * com.hour24.device.light
 * LightState.java
 * </pre>
 * 
 * @author Yang.uk.mo
 * @Date 2015. 6. 21.
 * 
 * @comment 초기 실행시 조명 정보 출력
 */
public class LightState extends HttpServlet {

	private static final long serialVersionUID = 1378274232968338832L;
	LightInfo li = LightInfo.getLightInfo();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		JSONObject jsObj = new JSONObject();
		JSONObject jsObj2 = new JSONObject();
		JSONArray jsArr = new JSONArray();

		jsObj2.put("NickName", "false");
		jsObj2.put("PinName", "isAuto");
		jsObj2.put("Turn", li.getAuto());
		jsArr.add(jsObj2);
		for (LightObj d : li.aLightArr) {
			jsObj2 = new JSONObject();
			jsObj2.put("NickName", d.getNickName());
			jsObj2.put("PinName", d.getPinName());
			jsObj2.put("Turn", d.getTurn());
			jsArr.add(jsObj2);
		}
		
		jsObj2 = new JSONObject();
		jsObj2.put("NickName", "모든전등");
		jsObj2.put("PinName", "all");
		jsObj2.put("Turn", li.isqAllTurn());
		jsArr.add(jsObj2);
		
		jsObj.put("Device", jsArr);
		out.println(jsObj + "");
		out.close();
	}

}
