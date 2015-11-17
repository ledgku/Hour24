package com.hour24.device.curtain;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
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

public class CurtainXML {
	private volatile static CurtainXML CURTAIN_XML_INSTANCE = null;
	private File file = null;
	
	private CurtainXML() {
		file = new File(EndPoint.CURTAIN_XML_FILE_PATH);
	}

	public static CurtainXML getCurtainXML() {
		if (CURTAIN_XML_INSTANCE == null) {
			synchronized (CurtainInfo.class) {
				CURTAIN_XML_INSTANCE = new CurtainXML();
			}
		}
		return CURTAIN_XML_INSTANCE;
	}

	// XML파일 생성
	public void CreateXML() {
		CurtainInfo ci = CurtainInfo.getCurtainInfo();

		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Node device = document.createElement("Device");
		document.appendChild(device);
		{
			for (CurtainObj d : ci.aCurtainArr) {
				Element curtain = document.createElement("Curtain");
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
			auto.appendChild(document.createTextNode(ci.getAuto() + ""));
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

	// XML에 값 입력
	public void setValueFromXML() {
		CurtainInfo ci = CurtainInfo.getCurtainInfo();
		for (CurtainObj d : ci.aCurtainArr) {
			d.setTurn(Integer.parseInt(getXMLDeviceValue(d.getPinName())));
		}
		ci.setAuto(Boolean.valueOf(getXMLAutoValue()));
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
			node = (Node) xpath.evaluate("//Curtain[@id='" + key + "']", xml, XPathConstants.NODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node.getTextContent();
	}

	// XML에서 auto 값 불러오기
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

	// XML에 각 디바이스별로 설정
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
			ch = (Node) xpath.evaluate("//Curtain[@id='" + key + "']", xml, XPathConstants.NODE);
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

	// XML에 Auto 설정
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
