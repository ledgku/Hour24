package com.hour24.device.curtain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CurtainState extends HttpServlet {

	private static final long serialVersionUID = 7611372163901746375L;
	CurtainInfo ci = CurtainInfo.getCurtainInfo();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		jsObj2.put("Turn", ci.getAuto());
		jsArr.add(jsObj2);

		for (CurtainObj d : ci.aCurtainArr) {
			jsObj2 = new JSONObject();
			jsObj2.put("NickName", d.getNickName());
			jsObj2.put("PinName", d.getPinName());
			jsObj2.put("Turn", d.getTurn());
			jsArr.add(jsObj2);
		}
		jsObj.put("Device", jsArr);

		out.println(jsObj + "");
		out.close();
	}

}
