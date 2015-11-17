package com.hour24.device.motion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;

public class MotionHttp extends HttpServlet {

	private static final long serialVersionUID = 9101281461806179288L;
	private MotionAuto ma = MotionAuto.getMotionAuto();
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
		String sTurn = request.getParameter("turn");
		String sToken = request.getParameter("token");

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			if ("true".equals(sTurn)) {
				ma.MotionSensorOn(sToken);
			} else if ("false".equals(sTurn)) {
				ma.MotionSensorOff(sToken);
			}
		}
	}

}
