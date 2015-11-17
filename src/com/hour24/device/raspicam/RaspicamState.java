package com.hour24.device.raspicam;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class RaspicamState extends HttpServlet {
	
	private RaspicamInfo ri = RaspicamInfo.getRaspicamInfo();

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
		jsObj.put("Turn", ri.getTurn());
		
		out.println(jsObj.toString());
		out.close();
	}

}
