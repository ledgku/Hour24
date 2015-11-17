package com.hour24.device.curtain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.device.light.LightAuto;
import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;
import com.pi4j.io.gpio.Pin;

public class CurtainHttp extends HttpServlet {

	private static final long serialVersionUID = -2509274591038855650L;
	private CurtainInfo ci = CurtainInfo.getCurtainInfo();
	private CurtainList cl = new CurtainList();
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

		int nTurn = 0;
		// 숫자 확인
		if (request.getParameter("turn") != null && CheckNumber(request.getParameter("turn"))) {
			nTurn = Integer.parseInt(request.getParameter("turn"));
		}
		String sPinName = request.getParameter("pinname"); // 기기 명
		String sToken = request.getParameter("token"); // 자신의 휴대폰 번호

		Boolean qAuto = Boolean.valueOf(request.getParameter("auto")); // 자동

		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			// 상하 조절
			if (nTurn >= 0 && nTurn <= 10 && sPinName != null && sToken != null) {
				CurtainControl cc = CurtainControl.getCurtainControl();
				try {
					cc.CurtainTurn(nTurn, sPinName, sToken);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// 자동 제어
			if (qAuto != null && sPinName == null && sToken != null) {
				this.setAuto(qAuto, sToken);
			}
		}
	}

	// 자동
	private void setAuto(Boolean qAuto, String sPhone) {
		// true/false
		CurtainAuto ca = CurtainAuto.getCurtainAuto();
		if (qAuto) {
			ca.CurtainAutoOn(sPhone);
		} else {
			ca.CurtainAutoOff(sPhone);
		}
	}

	// 숫자 확인
	private boolean CheckNumber(String sStr) {
		char cCheck;
		if (sStr.equals("")) {
			// 문자열이 공백인지 확인
			return false;
		}

		for (int i = 0; i < sStr.length(); i++) {
			cCheck = sStr.charAt(i);
			if (cCheck < 48 || cCheck > 58) {
				// 해당 char값이 숫자가 아닐 경우
				return false;
			}
		}
		return true;
	}
}
