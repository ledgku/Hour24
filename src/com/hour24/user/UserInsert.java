package com.hour24.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.http.EndPoint;
import com.hour24.http.HttpCommunication;

public class UserInsert extends HttpServlet {

	private static final long serialVersionUID = 1241927170161043621L;
	UserList ul = UserList.getUserList();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sPhone = request.getParameter("phone");
		String sDevice = request.getParameter("device");
		String sLevel = null;
		
		if("01087905711".equals(sPhone)){
			sLevel = "master";
		}else{
			sLevel = "member";
		}

		if (sPhone != null && sDevice != null && sLevel != null) {	
			ul.setUserList(sPhone, sDevice, sLevel);
		}
	}

}
