package com.hour24.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitHttp extends HttpServlet {

	private static final long serialVersionUID = 2371097437359251726L;
	private TokenCheck tc = new TokenCheck();
	private TokenList tl = TokenList.getToken();

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

		String sToken = request.getParameter("token");
		String sPushKey = request.getParameter("pushkey");
		
		if (sToken != null && sPushKey != null) { // 푸쉬키 맞는지 판별
			if (tc.isToken(sToken)) { // 토큰이 없을 경우
				if (!tl.searchToken(sToken)) {
					tl.deleteOverlapPushkey(sPushKey);
					tl.aTokenList.add(new TokenObj(sToken, sPushKey, true)); // 토큰추가
					out.print(true);
					System.out.println("[동작] TOKEN 발급 완료");
				} else {
					out.print(false);
					System.out.println("[동작] TOKEN 발급 거부");
				}
			} else { // 틀릴 경우
				out.print(false);
				System.out.println("[동작] TOKEN 발급 거부");
			}
		} else {
			out.print(false);
			System.out.println("[동작] TOKEN 발급 거부");
		}
		out.close();
	}

}
