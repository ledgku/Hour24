package com.hour24.user;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.hour24.user.UserList;
import com.hour24.user.UserObj;

public class PushSend {
	private TokenList tl = TokenList.getToken();

	public PushSend() {
		// TODO Auto-generated constructor stub
	}

	public void SendAllPush(String sTicker, boolean qRefresh, String sToken, String sMessage) {

		try {
			ArrayList<String> aRegid = new ArrayList<String>(); // reg_id
			String sMESSAGE_ID = String.valueOf(Math.random() % 100 + 1); // 메시지
																			// 고유
																			// ID
			boolean qSHOW_ON_IDLE = false; // 기기가 활성화 상태일때 보여줄것인지
			int nLIVE_TIME = 1; // 기기가 비활성화 상태일때 GCM가 메시지를 유효화하는 시간
			int nRETRY = 2; // 메시지 전송실패시 재시도 횟수

			String sSimpleApiKey = "AIzaSyC3-nIrWf0qxQi3AsEzZGrrNPFFWQP21YI"; // 개발자콘솔에서
			String sGcmURL = "https://android.googleapis.com/gcm/send";
			
			if (sToken == null) {
				for (TokenObj t : tl.getAllTokenList()) {
					aRegid.add(t.getsPushKey());
				}
			} else {
				for (TokenObj v : tl.getAllTokenList()) {
					if (v.getsPushKey() != null && !v.getsToken().contains(sToken)) {
						aRegid.add(v.getsPushKey());
					}
				}
			}

			// msg = URLEncoder.encode(msg, "EUC-KR"); //메시지 인코딩
			Sender sender = new Sender(sSimpleApiKey);
			Message message = new Message.Builder().collapseKey(sMESSAGE_ID).delayWhileIdle(qSHOW_ON_IDLE).timeToLive(nLIVE_TIME).addData("ticker", sTicker)
					.addData("refresh", String.valueOf(qRefresh)).addData("message", String.valueOf(sMessage)).build();

			MulticastResult result1 = sender.send(message, aRegid, nRETRY);
			if (result1 != null) {
				List<Result> resultList = result1.getResults();
				for (Result result : resultList) {
					// System.out.println(result.getErrorCodeName());
				}
			}
		} catch (Exception e) {

		} finally {

		}
	}
}
