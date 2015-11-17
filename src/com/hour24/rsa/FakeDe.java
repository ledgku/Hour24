package com.hour24.rsa;

import java.net.URLDecoder;

public class FakeDe {
	private String result = "", randfirstcut = "", midcut = "",
			randlastcut = "", cut = "", firstcut = "", lastcut = "",
			randcut = "";
	private int rand;
	private static String ranfake[][] = {
			{ "3", "0", "1", "8", "9", "5", "2", "7", "6", "4" },
			{ "3", "2", "4", "9", "5", "6", "7", "8", "0", "1" },
			{ "8", "7", "9", "6", "2", "4", "1", "0", "5", "3" },
			{ "1", "0", "2", "8", "3", "7", "4", "7", "5", "6" },
			{ "2", "9", "3", "0", "4", "8", "5", "7", "6", "7" },
			{ "2", "1", "4", "3", "6", "5", "8", "7", "9", "0" },
			{ "1", "3", "4", "6", "5", "8", "9", "2", "7", "0" } };

	public String fakedecode(String req) {
		result = decode(req);
		return result;
	}

	private String decode(String req) {
		req = URLDecoder.decode(req);
		firstcut = req.substring(5, req.length());
		randcut = firstcut.substring(0, 1);
		rand = Integer.parseInt(randcut);
		midcut = firstcut.substring(1, firstcut.length());
		randfirstcut = midcut.substring(0, rand);
		randlastcut = midcut.substring(rand * 2, midcut.length());
		cut = randfirstcut + randlastcut;
		lastcut = cut.substring(0, cut.trim().length() - 5);

		return lastcut;
	}
}
