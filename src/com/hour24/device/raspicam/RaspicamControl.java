package com.hour24.device.raspicam;

import com.hour24.raspi.ExecuteCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class RaspicamControl {
	private final String START_CHMOD = "sudo chmod +x /home/pi/start_stream.sh";
	private final String START_SH = "sudo sh /home/pi/start_stream.sh";
	private final String STOP_CHMOD = "sudo chmod +x /home/pi/stop_stream.sh";
	private final String STOP_SH = "sudo sh /home/pi/stop_stream.sh";

	private volatile static RaspicamControl RASPICAM_CONTROL_INSTANCE = null;
	private static GpioPinDigitalOutput servoUpDown = null;
	private static GpioController gpio = GpioFactory.getInstance();
	private RaspicamInfo ri = null;
	private ExecuteCommand ec = null;

	private RaspicamControl() {
		// TODO Auto-generated constructor stub
		ec = new ExecuteCommand();
		ri = RaspicamInfo.getRaspicamInfo();
	}

	public static RaspicamControl getRaspicamControl() {
		if (RASPICAM_CONTROL_INSTANCE == null) {
			synchronized (RaspicamControl.class) {
				RASPICAM_CONTROL_INSTANCE = new RaspicamControl();
			}
		}
		return RASPICAM_CONTROL_INSTANCE;
	}

	public void RaspicamOn() {
		ec.executeCommand(START_CHMOD);
		ec.executeCommand(START_SH);
		ri.setTurn(true);
		System.out.println("[설정] CCTV ON");
	}

	public void RaspicamOff() {
		ec.executeCommand(STOP_CHMOD);
		ec.executeCommand(STOP_SH);
		ri.setTurn(false);
		System.out.println("[설정] CCTV OFF");
	}

	public void servoLeftRight(String step) throws InterruptedException {
		String sEdit = "0";
		switch (step) {
		case "0":
			sEdit = "001";
			movingMotor(sEdit, "lr");
			break;
		case "10":
			sEdit = "011";
			movingMotor(sEdit, "lr");
			break;
		case "20":
			sEdit = "021";
			movingMotor(sEdit, "lr");
			break;
		case "30":
			sEdit = "031";
			movingMotor(sEdit, "lr");
			break;
		case "40":
			sEdit = "041";
			movingMotor(sEdit, "lr");
			break;
		case "50":
			sEdit = "051";
			movingMotor(sEdit, "lr");
			break;
		case "60":
			sEdit = "061";
			movingMotor(sEdit, "lr");
			break;
		case "70":
			sEdit = "071";
			movingMotor(sEdit, "lr");
			break;
		case "80":
			sEdit = "081";
			movingMotor(sEdit, "lr");
			break;
		case "90":
			sEdit = "091";
			movingMotor(sEdit, "lr");
			break;
		case "99":
			sEdit = "099";
			movingMotor(sEdit, "lr");
			break;
		case "110":
			sEdit = "111";
			movingMotor(sEdit, "lr");
			break;
		case "120":
			sEdit = "121";
			movingMotor(sEdit, "lr");
			break;
		case "130":
			sEdit = "131";
			movingMotor(sEdit, "lr");
			break;
		case "140":
			sEdit = "141";
			movingMotor(sEdit, "lr");
			break;
		case "150":
			sEdit = "151";
			movingMotor(sEdit, "lr");
			break;
		case "160":
			sEdit = "161";
			movingMotor(sEdit, "lr");
			break;
		case "170":
			sEdit = "171";
			movingMotor(sEdit, "lr");
			break;
		case "180":
			sEdit = "179";
			movingMotor(sEdit, "lr");
			break;
		}
	}

	public void servoTopBottom(String step) throws InterruptedException {
		String sEdit = "0";
		switch (step) {
		case "40":
			sEdit = "041";
			movingMotor(sEdit, "tb");
			break;
		case "50":
			sEdit = "051";
			movingMotor(sEdit, "tb");
			break;
		case "60":
			sEdit = "061";
			movingMotor(sEdit, "tb");
			break;
		case "70":
			sEdit = "071";
			movingMotor(sEdit, "tb");
			break;
		case "80":
			sEdit = "081";
			movingMotor(sEdit, "tb");
			break;
		case "90":
			sEdit = "091";
			movingMotor(sEdit, "tb");
			break;
		case "99":
			sEdit = "099";
			movingMotor(sEdit, "tb");
			break;
		}
	}

	private void movingMotor(String sStep, String sType) {
		if ("tb".equals(sType)) {
			ec.executeCommand("sudo python /home/pi/Cam.py ud" + sStep);
			System.out.println("[동작] CCTV 동작 상하각도:" + sStep);
		} else if ("lr".equals(sType)) {
			ec.executeCommand("sudo python /home/pi/Cam.py lr" + sStep);
			System.out.println("[동작] CCTV 동작 좌우각도:" + sStep);
		}
	}

}
