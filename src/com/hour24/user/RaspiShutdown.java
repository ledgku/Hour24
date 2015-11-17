package com.hour24.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hour24.raspi.ExecuteCommand;

public class RaspiShutdown extends HttpServlet {

	private static final long serialVersionUID = -1112243384193293201L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Boolean turn = Boolean.valueOf(request.getParameter("turn"));
		String type = request.getParameter("type");
		
		if("shutdown".equals(type)){
			if(turn){
				ExecuteCommand ec = new ExecuteCommand();
				ec.executeCommand("sudo shutdown -h now");
			}
		}
	}

}
