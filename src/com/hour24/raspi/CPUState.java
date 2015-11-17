package com.hour24.raspi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.pi4j.system.SystemInfo;


public class CPUState extends HttpServlet {

	private static final long serialVersionUID = -4246859398294364065L;
	private CPUInfo ci = CPUInfo.getCPUInfo();

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

		jsObj.put("CPU_TEMP", getCpuTemp() + "");
		jsObj.put("Auto", ci.getAuto());
		jsObj.put("Turn", ci.getTurn());
		
		out.println(jsObj + "");
		out.close();
	}
	
	private float getCpuTemp() {
		try {
			return SystemInfo.getCpuTemperature();
		} catch (NumberFormatException | IOException | InterruptedException e) {
			e.printStackTrace();
			return 0;
		}
	}	

}
