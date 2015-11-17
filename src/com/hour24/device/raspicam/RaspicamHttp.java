package com.hour24.device.raspicam;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;

public class RaspicamHttp extends HttpServlet {

	private static final long serialVersionUID = -2780982367964736741L;
	private TokenList tl = TokenList.getToken();
	private TokenCheck tc = new TokenCheck();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RaspicamControl rc = RaspicamControl.getRaspicamControl();

		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		Boolean qTurn = null;
		if (request.getParameter("turn") != null) {
			qTurn = Boolean.valueOf(request.getParameter("turn"));
		}
		String sStepLR = request.getParameter("steplr");
		String sStepUD = request.getParameter("steptb");
		String sToken = request.getParameter("token");

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			if (qTurn != null) {
				if (qTurn) {
					rc.RaspicamOn();
				} else if (!qTurn) {
					rc.RaspicamOff();
				}
			}

			if (sStepLR != null) {
				try {
					rc.servoLeftRight(sStepLR);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (sStepUD != null) {
				try {
					rc.servoTopBottom(sStepUD);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
	/*
	 * private boolean CheckNumber(String str) { char check; if (str.equals(""))
	 * { // 문자열이 공백인지 확인 return false; }
	 * 
	 * for (int i = 0; i < str.length(); i++) { check = str.charAt(i); if (check
	 * < 48 || check > 58) { // 해당 char값이 숫자가 아닐 경우 return false; } } return
	 * true; }
	 */

}
