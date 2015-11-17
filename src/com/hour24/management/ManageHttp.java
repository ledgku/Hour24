package com.hour24.management;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.hour24.device.curtain.CurtainControl;
import com.hour24.device.curtain.CurtainInfo;
import com.hour24.device.curtain.CurtainObj;
import com.hour24.device.light.LightControl;
import com.hour24.device.light.LightInfo;
import com.hour24.device.light.LightList;
import com.hour24.device.light.LightObj;

public class ManageHttp extends HttpServlet {

	private static final long serialVersionUID = -2064980218258749889L;
	private LightInfo li = LightInfo.getLightInfo();
	private LightControl lc = LightControl.getLightControl();
	private LightList ll = new LightList();
	private CurtainInfo ci = CurtainInfo.getCurtainInfo();

	private CurtainControl cc = CurtainControl.getCurtainControl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sData = request.getParameter("data");
		String sPhone = request.getParameter("phone");

		if (sData != null && sPhone != null) {
			try {
				JSONArray jsonData = (JSONArray) new JSONParser().parse(sData);

				int count = jsonData.size();

				for (int i = 0; i < count; i++) {
					JSONObject c = (JSONObject) jsonData.get(i);
					String sGet = c.get("pinName") + "";

					if (searchLight(sGet)) { // 전등
						Boolean qTurn = Boolean.valueOf(c.get("flag") + "");
						String sPin = ll.searchPin(sGet);
						if (qTurn) {
							lc.LightOn(sPin, sGet, sPhone, "Turn");
						} else if (!qTurn) {
							lc.LightOff(sPin, sGet, sPhone, "Turn");
						}
					} else if (searchCurtain(sGet)) {
						System.out.println(c.get("pinName") + "/" + c.get("flag"));
						try {
							Boolean qTurn = Boolean.valueOf(c.get("flag") + "");
							if (qTurn) {
								cc.CurtainTurn(10, sGet, sPhone);
							} else if(!qTurn){
								cc.CurtainTurn(0, sGet, sPhone);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Thread.sleep(500);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Boolean searchLight(String sPinName) {
		for (LightObj d : li.aLightArr) {
			if (d.getPinName().contains(sPinName)) {
				return true;
			}
		}
		return false;
	}

	private Boolean searchCurtain(String sPinName) {
		for (CurtainObj d : ci.aCurtainArr) {
			if (d.getPinName().contains(sPinName)) {
				return true;
			}
		}
		return false;
	}

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
