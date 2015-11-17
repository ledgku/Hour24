package com.hour24.device.raspicam;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RaspicamStream extends HttpServlet {

	private static final long serialVersionUID = 8195434454410746584L;

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
		
		out.println("<html>");
		out.println("<head>");
		out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no\" />");
		out.println("<style>body{margin:0;padding:0;background:#000;}img{width:100%;max-width:640px;filter:flipv()}</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<img src=\"http://" + request.getServerName() + ":12346/?action=stream\" />");
		out.println("</body></html>");
		out.close();
	}

}
