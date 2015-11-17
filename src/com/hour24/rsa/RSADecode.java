package com.hour24.rsa;


public class RSADecode { // extends HttpServlet {

	private String data;

	public RSADecode(String data) {
		this.data = data;
	}

	public String onProc() {
		DeCompare com = new DeCompare();
		String code = com.Com(data);
		return code;
	}
}