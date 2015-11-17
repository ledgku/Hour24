package com.hour24.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCheck extends HttpServlet {

	private static final long serialVersionUID = -2150406404673891156L;
	UserList ul = UserList.getUserList();

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
		
		String sPhone = request.getParameter("phone");
		String sDevice = request.getParameter("device");
		
		if(sPhone != null && sDevice != null){
			out.println(ul.checkUser(sPhone, sDevice));
		}
		
		out.close();
	}

}
