package com.hour24.device.alert;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AlertHttp extends HttpServlet {

	private static final long serialVersionUID = 497975478259444713L;
	private AlertControl ac = new AlertControl();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sTurn = request.getParameter("turn");
		
		if(sTurn != null){
			ac.AlertChange(sTurn);
		}
	}

}
