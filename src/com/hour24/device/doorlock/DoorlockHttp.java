package com.hour24.device.doorlock;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.user.TokenCheck;
import com.hour24.user.TokenList;

public class DoorlockHttp extends HttpServlet {

	private static final long serialVersionUID = -9014021374765133359L;
	DoorlockContorl dc = DoorlockContorl.getDoorlockControl();
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

		ServletOutputStream out = response.getOutputStream();
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String sType = request.getParameter("type");
		String sTurn = request.getParameter("turn");
		String sToken = request.getParameter("token");
		
		if (sToken != null && tl.searchToken(sToken) && tc.isToken(sToken)) { // 토큰검색&유효성검사
			if ("doorlock".equals(sType)) {
				if ("open".equals(sTurn)) {
					out.println(dc.Open());
				} else if ("close".equals(sTurn)) {
					out.println(dc.Close());
				}
			}
		}
	}

}
