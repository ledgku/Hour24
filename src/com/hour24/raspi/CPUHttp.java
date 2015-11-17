package com.hour24.raspi;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;
import com.pi4j.system.SystemInfo;

public class CPUHttp extends HttpServlet {

	private CPUFanAuto cfa = CPUFanAuto.getCPUFanAuto();
	private CPUFanControl cfc = CPUFanControl.getCPUFanControl();
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

		String sType = request.getParameter("type");
		String sTurn = request.getParameter("auto");
		String sToken = request.getParameter("token");

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			if (sType != null && sTurn != null && sToken != null) {
				switch (sType) {
				case "cpu":
					if ("true".equals(sTurn)) {
						cfa.getCPUTempStart(sToken);

					} else if ("false".equals(sTurn)) {
						cfa.getCPUTempStop(sToken);
						cfc.CPUFanOff();
					}
					break;
				default:
					break;
				}
			}
		}
	}

}
