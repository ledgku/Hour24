package com.hour24.main;

import java.io.File;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

import com.hour24.config.ConfigHttp;
import com.hour24.config.ConfigState;
import com.hour24.device.alert.AlertControl;
import com.hour24.device.alert.AlertHttp;
import com.hour24.device.curtain.CurtainHttp;
import com.hour24.device.curtain.CurtainInfo;
import com.hour24.device.curtain.CurtainList;
import com.hour24.device.curtain.CurtainState;
import com.hour24.device.curtain.CurtainXML;
import com.hour24.device.doorlock.DoorlockAuto;
import com.hour24.device.doorlock.DoorlockHttp;
import com.hour24.device.doorlock.DoorlockState;
import com.hour24.device.light.LightHttp;
import com.hour24.device.light.LightInfo;
import com.hour24.device.light.LightList;
import com.hour24.device.light.LightState;
import com.hour24.device.light.LightXML;
import com.hour24.device.motion.MotionAuto;
import com.hour24.device.motion.MotionHttp;
import com.hour24.device.motion.MotionState;
import com.hour24.device.raspicam.RaspicamControl;
import com.hour24.device.raspicam.RaspicamHttp;
import com.hour24.device.raspicam.RaspicamState;
import com.hour24.device.raspicam.RaspicamStream;
import com.hour24.device.temperature.TempHttp;
import com.hour24.device.temperature.TempState;
import com.hour24.device.windows.WindowsAuto;
import com.hour24.device.windows.WindowsHttp;
import com.hour24.device.windows.WindowsInfo;
import com.hour24.device.windows.WindowsList;
import com.hour24.device.windows.WindowsState;
import com.hour24.device.windows.WindowsTest;
import com.hour24.device.windows.WindowsXML;
import com.hour24.http.EndPoint;
import com.hour24.log.LogHttp;
import com.hour24.management.ManageHttp;
import com.hour24.raspi.CPUFanAuto;
import com.hour24.raspi.CPUFanControl;
import com.hour24.raspi.CPUHttp;
import com.hour24.raspi.CPUInfo;
import com.hour24.raspi.CPUList;
import com.hour24.raspi.CPUState;
import com.hour24.user.InitHttp;
import com.hour24.user.RaspiShutdown;
//import com.hour24.user.UserCheck;
//import com.hour24.user.UserInsert;
//import com.hour24.user.UserState;

/**
 * <pre>
 * com.hour24.main
 * Activator.java
 * </pre>
 * 
 * @author Yang.uk.mo
 * @Date 2015. 6. 21.
 * 
 * @comment Main Activator
 */
public class Activator implements BundleActivator {

	private ServiceReference serviceReference;
	private HttpService httpService;
	private WindowsAuto wa = WindowsAuto.getWindowsAuto();
	private MotionAuto ma = MotionAuto.getMotionAuto();
	private RaspicamControl rc = RaspicamControl.getRaspicamControl();
	private CPUFanAuto cfa = CPUFanAuto.getCPUFanAuto();
	private DoorlockAuto da = DoorlockAuto.getDoorlockAuto();
	private AlertControl ac = new AlertControl();

	@SuppressWarnings("unchecked")
	public void start(BundleContext context) throws Exception {
		serviceReference = context.getServiceReference("org.osgi.service.http.HttpService");

		if (serviceReference != null) {
			httpService = (HttpService) context.getService(serviceReference);
			httpService.registerServlet("/LightHttp", new LightHttp(), null, null);
			httpService.registerServlet("/LightState", new LightState(), null, null);
			httpService.registerServlet("/CurtainHttp", new CurtainHttp(), null, null);
			httpService.registerServlet("/CurtainState", new CurtainState(), null, null);
			httpService.registerServlet("/WindowsHttp", new WindowsHttp(), null, null);
			httpService.registerServlet("/WindowsState", new WindowsState(), null, null);
			httpService.registerServlet("/DoorlockHttp", new DoorlockHttp(), null, null);
			httpService.registerServlet("/DoorlockState", new DoorlockState(), null, null);
			httpService.registerServlet("/CPUHttp", new CPUHttp(), null, null);
			httpService.registerServlet("/CPUState", new CPUState(), null, null);
			httpService.registerServlet("/TempHttp", new TempHttp(), null, null);
			httpService.registerServlet("/TempState", new TempState(), null, null);
			httpService.registerServlet("/RaspicamHttp", new RaspicamHttp(), null, null);
			httpService.registerServlet("/RaspicamStream", new RaspicamStream(), null, null);
			httpService.registerServlet("/RaspicamState", new RaspicamState(), null, null);
			httpService.registerServlet("/RaspiShutdown", new RaspiShutdown(), null, null);
//			httpService.registerServlet("/UserInsert", new UserInsert(), null, null);
//			httpService.registerServlet("/UserCheck", new UserCheck(), null, null);
//			httpService.registerServlet("/UserState", new UserState(), null, null);
			httpService.registerServlet("/LogHttp", new LogHttp(), null, null);
			httpService.registerServlet("/ConfigHttp", new ConfigHttp(), null, null);
			httpService.registerServlet("/ConfigState", new ConfigState(), null, null);
			httpService.registerServlet("/MotionHttp", new MotionHttp(), null, null);
			httpService.registerServlet("/MotionState", new MotionState(), null, null);
			httpService.registerServlet("/AlertHttp", new AlertHttp(), null, null);
			httpService.registerServlet("/ManageHttp", new ManageHttp(), null, null);
			httpService.registerServlet("/InitHttp", new InitHttp(), null, null);
			
			httpService.registerServlet("/WindowsTest", new WindowsTest(), null, null);
		}

		// 전등, 커튼 기기 등록
		LightInfo.setPinInfo();
		LightList.setList();
		CurtainInfo.setPinInfo();
		WindowsInfo.setPinInfo();
		WindowsList.setList();
		CPUInfo.setPinInfo();
		CPUList.setList();

		searchCurtainXML();	// 전등 XML 확인
		searchLightXML();	// 커튼 XML 확인
		searchWindowsXML();	// 창문XML 확인
			
		wa.WindowsSensorOn(null);
		ma.MotionSensorOn(null);
		da.DoorlockSensorOn(null);	
		cfa.getCPUTempStart(null);
		ac.WarningOff();
	}

	public void stop(BundleContext context) throws Exception {
		httpService.unregister("/LightHttp");
		httpService.unregister("/LightState");
		httpService.unregister("/CurtainHttp");
		httpService.unregister("/CurtainState");
		httpService.unregister("/WindowsHttp");
		httpService.unregister("/WindowsState");
		httpService.unregister("/DoorlockHttp");
		httpService.unregister("/DoorlockState");
		httpService.unregister("/CPUHttp");
		httpService.unregister("/CPUState");
		httpService.unregister("/TempHttp");
		httpService.unregister("/TempState");
		httpService.unregister("/RaspicamHttp");
		httpService.unregister("/RaspicamStream");
		httpService.unregister("/RaspicamState");
		httpService.unregister("/RaspiShutdown");
//		httpService.unregister("/UserInsert");
//		httpService.unregister("/UserCheck");
//		httpService.unregister("/UserState");
		httpService.unregister("/LogHttp");
		httpService.unregister("/ConfigHttp");
		httpService.unregister("/ConfigState");
		httpService.unregister("/MotionHttp");
		httpService.unregister("/MotionState");
		httpService.unregister("/AlertHttp");
		httpService.unregister("/ManageHttp");
		httpService.unregister("/InitHttp");
		
		httpService.unregister("/WindowsTest");
		
		wa.WindowsSensorOff(null);
		ma.MotionSensorOff(null);
		rc.RaspicamOff();
		da.DoorlockSensorOff(null);	
		cfa.getCPUTempStart(null);
	}

	// 전등 XML 확인
	private void searchLightXML() {
		File files = new File(EndPoint.LIGHT_XML_FILE_PATH);
		LightXML lx = LightXML.getLightXML();
		if (files.exists() == false) {
			lx.CreateXML();
		} else {
			lx.setValueFromXML();
		}
	}

	// 커튼 XML 확인
	private void searchCurtainXML() {
		File files = new File(EndPoint.CURTAIN_XML_FILE_PATH);
		CurtainXML cx = CurtainXML.getCurtainXML();
		if (files.exists() == false) {
			cx.CreateXML();
		} else {
			cx.setValueFromXML();
		}
	}

	// 창문XML 확인
	private void searchWindowsXML() {
		File files = new File(EndPoint.WINDOWS_XML_FILE_PATH);
		WindowsXML wx = WindowsXML.getWindowsXML();
		if (files.exists() == false) {
			wx.CreateXML();
		} else {
			wx.setValueFromXML();
		}
	}
}
