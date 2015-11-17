package com.hour24.device.light;

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

public class LightXML {
	private volatile static LightXML LIGHT_XML_INSTANCE = null;
	private File file = null;
	private LightXML() {
		file = new File(EndPoint.LIGHT_XML_FILE_PATH);
	}

	public static LightXML getLightXML() {
		if (LIGHT_XML_INSTANCE == null) {
			synchronized (LightInfo.class) {
				LIGHT_XML_INSTANCE = new LightXML();
			}
		}
		return LIGHT_XML_INSTANCE;
	}

	public void CreateXML() {
		LightInfo li = LightInfo.getLightInfo();

		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Node device = document.createElement("Device");
		document.appendChild(device);
		{
			for (LightObj d : li.aLightArr) {
				Element curtain = document.createElement("Light");
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
			auto.appendChild(document.createTextNode(li.getAuto() + ""));
			device.appendChild(auto);
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

	// 재정의
	public void setValueFromXML() {
		LightInfo li = LightInfo.getLightInfo();
		for (LightObj d : li.aLightArr) {
			d.setTurn(Boolean.valueOf(getXMLDeviceValue(d.getPinName())));
		}
		li.setAuto(Boolean.valueOf(getXMLAutoValue()));
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
			node = (Node) xpath.evaluate("//Light[@id='" + key + "']", xml, XPathConstants.NODE);
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
			ch = (Node) xpath.evaluate("//Light[@id='" + key + "']", xml, XPathConstants.NODE);
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

}
