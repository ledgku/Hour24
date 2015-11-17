package com.hour24.device.temperature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class TempState extends HttpServlet {

	private static final long serialVersionUID = 901941777342199029L;
	static String sW1DirPath = "/sys/bus/w1/devices";

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
		jsObj.put("temperature", checkTemp());
		out.println(jsObj.toString());
		out.close();
	}

	private String checkTemp() {
		File dir = new File(sW1DirPath);
		File[] files = dir.listFiles(new DirectoryFileFilter());
		if (files != null) {
			// while (true) {
			for (File file : files) {
				if (!"w1_bus_master1".equals(file.getName())) {
					// Device data in w1_slave file
					String sFilePath = sW1DirPath + "/" + file.getName() + "/w1_slave";
					File f = new File(sFilePath);
					try (BufferedReader br = new BufferedReader(new FileReader(f))) {
						String sOutput;
						while ((sOutput = br.readLine()) != null) {
							int idx = sOutput.indexOf("t=");
							if (idx > -1) {
								// Temp data (multiplied by 1000) in 5 chars
								// after t=
								float fTempC = Float.parseFloat(sOutput.substring(sOutput.indexOf("t=") + 2));
								// Divide by 1000 to get degrees Celsius
								fTempC /= 1000;
								return String.format("%.1f ", fTempC);
								// float tempF = tempC * 9 / 5 + 32;
								// System.out.println(String.format("%.3f",
								// tempF));
							}
						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			// }
		}
		return null;
	}

}

class DirectoryFileFilter implements FileFilter {
	public boolean accept(File file) {
		String sDirName = file.getName();
		String sStartOfName = sDirName.substring(0, 3);
		return (file.isDirectory() && sStartOfName.equals("28-"));
	}
}
