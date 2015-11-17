package com.hour24.device.windows;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.device.light.LightAuto;
import com.hour24.log.ControlLog;
import com.hour24.user.PushSend;
import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;
import com.pi4j.io.gpio.Pin;

public class WindowsHttp extends HttpServlet {

	private static final long serialVersionUID = -3776973681363031882L;
	private WindowsAuto wa = WindowsAuto.getWindowsAuto();
	private WindowsList wl = new WindowsList();
	private WindowsInfo wi = WindowsInfo.getWindowsInfo();
	private WindowsXML wx = WindowsXML.getWindowsXML();
	private ControlLog conlog = new ControlLog();
	private PushSend ps = new PushSend();
	private TokenList tl = TokenList.getToken();
	private TokenCheck tc = new TokenCheck();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		Boolean qAuto = null;
		Boolean qSecurity = null;
		if (request.getParameter("auto") != null) {
			qAuto = Boolean.valueOf(request.getParameter("auto"));
		}
		if (request.getParameter("sec") != null) {
			qSecurity = Boolean.valueOf(request.getParameter("sec"));
		}

		String sToken = request.getParameter("token");

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			// 자동 true/false
			if (qAuto != null && sToken != null) {
				this.setAuto(qAuto, sToken);
			}

			// 보안 true/false
			if (qSecurity != null && sToken != null) {
				this.setSecurity(qSecurity, sToken);
			}
		}
	}

	private void setAuto(Boolean qAuto, String sToken) { // 자동 // true/false
		if (qAuto) {
			wa.WindowsSensorOn(sToken);
		} else {
			wa.WindowsSensorOff(null);
		}
	}

	private void setSecurity(Boolean qSec, String sToken) {
		if (qSec) {
			wi.setSecurity(true);
			if (wa.WindowsTimer == null) {
				wa.WindowsSensorOn(null);
			}
			wx.setXMLSecurityValue("true");
			conlog.TraceLog("창문", null, "true", "sec");
			System.out.println("Security : " + wi.getSecurity());
		} else if (!qSec) {
			wi.setSecurity(false);
			wx.setXMLSecurityValue("false");
			conlog.TraceLog("창문", null, "false", "sec");
			System.out.println("Security : " + wi.getSecurity());
		}
	}
}
