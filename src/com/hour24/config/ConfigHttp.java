package com.hour24.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.device.alert.AlertControl;
import com.hour24.user.PushSend;

public class ConfigHttp extends HttpServlet {

	private static final long serialVersionUID = -3252197160398161554L;
	private Config conf = Config.getConfig();
	private PushSend ps = new PushSend();
	private AlertControl ac = new AlertControl();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String sWarning = request.getParameter("warning");
		String sPhone = request.getParameter("phone");

		if (sPhone != null) {
			if ("true".equals(sWarning)) {
				conf.setWarning(true);
				ps.SendAllPush("config", false, sPhone, null);
				System.out.println("[설정] 보안 ON");
			} else if ("false".equals(sWarning)) {
				conf.setWarning(false);
				ps.SendAllPush("config", false, sPhone, null);
				System.out.println("[설정] 보안 OFF");
				ac.WarningOff();
			}
		}
	}

}
