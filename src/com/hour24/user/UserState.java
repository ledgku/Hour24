package com.hour24.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserState extends HttpServlet {

	private static final long serialVersionUID = -3168827469515989598L;
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
		
		JSONObject jsObj = new JSONObject();
		JSONObject jsObj2 = null;
		JSONArray jsArr = new JSONArray();
		for(UserObj u : ul.aUserList){
			jsObj2 = new JSONObject();
			jsObj2.put("Phone", u.getPhone());
			jsObj2.put("Device", u.getDevice());
			jsObj2.put("Level", u.getLevel());
			jsArr.add(jsObj2);
		}
		jsObj.put("User", jsArr);
		
		out.println(jsObj + "");
		out.close();
	}
 
}
