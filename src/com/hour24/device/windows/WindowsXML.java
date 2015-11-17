package com.hour24.device.windows;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.hour24.http.EndPoint;

public class WindowsXML {
	private volatile static WindowsXML WINDOWS_XML_INSTANCE = null;
	private File file = null;

	private WindowsXML() {
		file = new File(EndPoint.WINDOWS_XML_FILE_PATH);
	}

	public static WindowsXML getWindowsXML() {
		if (WINDOWS_XML_INSTANCE == null) {
			synchronized (WindowsXML.class) {
				WINDOWS_XML_INSTANCE = new WindowsXML();
			}
		}
		return WINDOWS_XML_INSTANCE;
	}

	public void CreateXML() {
		WindowsInfo wi = WindowsInfo.getWindowsInfo();

		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Node device = document.createElement("Device");
		document.appendChild(device);
		{
			for (WindowsObj d : wi.aWindowsArr) {
				Element curtain = document.createElement("Windows");
				curtain.setAttribute("id", d.getPinName());
				device.appendChild(curtain);
				{
					Element turn = document.createElement("turn");
					turn.appendChild(document.createTextNode(d.getTurn() + ""));
					curtain.appendChild(turn);
				}
			}
		}
		{
			Element auto = document.createElement("Auto");
			auto.appendChild(document.createTextNode(wi.getAuto() + ""));
			device.appendChild(auto);
		}
		{
			Element sec = document.createElement("Security");
			sec.appendChild(document.createTextNode(wi.getAuto() + ""));
			device.appendChild(sec);
		}

		// Document 저장
		DOMSource xmlDOM = new DOMSource(document);
		StreamResult xmlFile = new StreamResult(file);
		try {
			TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setValueFromXML() {
		WindowsInfo wi = WindowsInfo.getWindowsInfo();
		for (WindowsObj d : wi.aWindowsArr) {
			d.setTurn(getXMLDeviceValue(d.getPinName()));
		}
		wi.setAuto(Boolean.valueOf(getXMLAutoValue()));
		wi.setSecurity(Boolean.valueOf(getXMLSecurityValue()));
	}

	// XML에서 디바이스 값 불러오기
	private String getXMLDeviceValue(String key) {
		Document xml = null;
		Node node = null;

		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			node = (Node) xpath.evaluate("//Windows[@id='" + key + "']", xml, XPathConstants.NODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node.getTextContent();
	}

	// XML에서 자동값 불러오기
	private String getXMLAutoValue() {
		Document xml = null;
		Node node = null;

		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			node = (Node) xpath.evaluate("//Auto", xml, XPathConstants.NODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node.getTextContent();
	}
	
	private String getXMLSecurityValue() {
		Document xml = null;
		Node node = null;

		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		XPath xpath = XPathFactory.newInstance().newXPath();

		try {
			node = (Node) xpath.evaluate("//Security", xml, XPathConstants.NODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node.getTextContent();
	}

	// XML에 각 디바이스벼로 값 등록
	public void setXMLDeivceValue(String key, String value) {
		Document xml = null;

		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node ch = null;
		try {
			ch = (Node) xpath.evaluate("//Windows[@id='" + key + "']", xml, XPathConstants.NODE);
			ch.setTextContent(value);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		DOMSource xmlDOM = new DOMSource(xml);
		StreamResult xmlFile = new StreamResult(file);
		try {
			TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// XML에 자동여부 등록
	public void setXMLAutoValue(String value) {
		Document xml = null;

		try {
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		xml.getDocumentElement().normalize();
		XPath xpath = XPathFactory.newInstance().newXPath();
		Node ch = null;
		try {
			ch = (Node) xpath.evaluate("//Auto", xml, XPathConstants.NODE);
			ch.setTextContent(value);

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		DOMSource xmlDOM = new DOMSource(xml);
		StreamResult xmlFile = new StreamResult(file);
		try {
			TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// XML에 자동여부 등록
		public void setXMLSecurityValue(String value) {
			Document xml = null;

			try {
				xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			xml.getDocumentElement().normalize();
			XPath xpath = XPathFactory.newInstance().newXPath();
			Node ch = null;
			try {
				ch = (Node) xpath.evaluate("//Security", xml, XPathConstants.NODE);
				ch.setTextContent(value);

			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}

			DOMSource xmlDOM = new DOMSource(xml);
			StreamResult xmlFile = new StreamResult(file);
			try {
				TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
