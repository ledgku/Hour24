package com.hour24.raspi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteCommand {
	public ExecuteCommand() {
		// TODO Auto-generated constructor stub
	}

	public String executeCommand(String command) {
		StringBuffer sbOutput = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String sLine = "";
			while ((sLine = reader.readLine()) != null) {
				sbOutput.append(sLine + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sbOutput.toString();
	}
}
