package com.hour24.device.doorlock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.hour24.device.motion.MotionInfo;

public class DoorlockState extends HttpServlet {

	private static final long serialVersionUID = -2457368179983294780L;
	private DoorlockInfo di = DoorlockInfo.getDoorlockInfo();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		JSONObject jsObj = new JSONObject();

		jsObj.put("Auto", di.getsAuto());
		jsObj.put("Turn", di.getsTurn());

		out.println(jsObj + "");
		out.close();
	}

}
