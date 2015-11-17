package com.hour24.device.light;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;
import com.pi4j.io.gpio.Pin;

/**
 * <pre>
 * com.hour24.device.light
 * LightHttp.java
 * </pre>
 * 
 * @author Yang.uk.mo
 * @Date 2015. 6. 21.
 * 
 * @comment Http통신으로 파라미터 구분 및 실행
 */
public class LightHttp extends HttpServlet {

	private static final long serialVersionUID = -7826839599227493214L;
	private LightList ll = new LightList();
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

		String sPin = null; // Pin 번호
		String sType = request.getParameter("type");
		Boolean qTurn = null;
		if (request.getParameter("turn") != null) {
			qTurn = Boolean.valueOf(request.getParameter("turn")); // 전원
		}
		String sPinName = request.getParameter("pinname");
		String sToken = request.getParameter("token");

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			if (sPinName != null && !"all".equals(sPinName)) {
				sPin = ll.searchPin(sPinName);
			}

			if (sType != null && qTurn != null && sToken != null) {
				switch (sType) {
				case "turn":
					if ((sPinName != null && sPin != null) || "all".equals(sPinName)) {
						this.setTurn(qTurn, sPinName, sPin, sToken);
					}
					break;
				case "auto":
					this.setAuto(qTurn, sToken);
					break;
				default:
					break;
				}
			}
		}
	}

	private void setAuto(Boolean qAuto, String sToken) { // 자동 // true/false
		LightAuto la = LightAuto.getLightAuto();
		if (qAuto) {
			la.LightAutoOn(sToken);
		} else {
			la.LightAutoOff(sToken);
		}
	}

	private void setTurn(Boolean qTurn, String sPinName, String sPin, String sToken) { // 전원true/false
		LightControl lc = LightControl.getLightControl();
		if (qTurn) {
			lc.LightOn(sPin, sPinName, sToken, "Turn");
		} else if (!qTurn) {
			lc.LightOff(sPin, sPinName, sToken, "Turn");
		}
	}

}
