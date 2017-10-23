package com.nhsoft.module.report.param;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class PosClientParam implements Serializable {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PosClientParam.class);
	private static final long serialVersionUID = -7296805037725354256L;
	private Boolean autoCode;
	private Integer codeLength;
	private String codePrix;
	private String codePrixType;
	private Integer creditControlType;
	private String creditControlPW;

	public Boolean getAutoCode() {
		return autoCode;
	}

	public void setAutoCode(Boolean autoCode) {
		this.autoCode = autoCode;
	}

	public Integer getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}

	public String getCodePrix() {
		return codePrix;
	}

	public void setCodePrix(String codePrix) {
		this.codePrix = codePrix;
	}

	public String getCodePrixType() {
		return codePrixType;
	}

	public void setCodePrixType(String codePrixType) {
		this.codePrixType = codePrixType;
	}

	public Integer getCreditControlType() {
		return creditControlType;
	}

	public void setCreditControlType(Integer creditControlType) {
		this.creditControlType = creditControlType;
	}

	public String getCreditControlPW() {
		return creditControlPW;
	}

	public void setCreditControlPW(String creditControlPW) {
		this.creditControlPW = creditControlPW;
	}

	public static PosClientParam readFromXml(String text){
		try {
			Document document = DocumentHelper.parseText(text);
			PosClientParam posClientParam = new PosClientParam();
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode("AutoCode");
			if(element != null && StringUtils.isNotEmpty(element.getText())){
				posClientParam.setAutoCode(BooleanUtils.toBoolean(element.getText(), "1", "0"));
			}
			
			element = (Element) root.selectSingleNode("CodeLenth");
			if(element != null && StringUtils.isNotEmpty(element.getText())){
				posClientParam.setCodeLength(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("CodePrix");
			if(element != null){
				posClientParam.setCodePrix(element.getText());
			}
			
			element = (Element) root.selectSingleNode("CodePrixType");
			if(element != null){
				posClientParam.setCodePrixType(element.getText());
			}
			
			element = (Element) root.selectSingleNode("CreditControlType");
			if(element != null && StringUtils.isNotEmpty(element.getText())){
				posClientParam.setCreditControlType(Integer.parseInt(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("CreditControlPW");
			if(element != null && StringUtils.isNotEmpty(element.getText())){
				posClientParam.setCreditControlPW(element.getText());
			}
			return posClientParam;
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("PosClientParam");
		
		root.addElement("AutoCode").setText(BooleanUtils.toString(autoCode, "1", "0", "0"));
		
		Element element = root.addElement("CodeLenth");
		if(codeLength != null){
			element.setText(codeLength.toString());
		} else {
			element.setText("0");
		}
		
		element = root.addElement("CodePrix");
		if(codePrix != null){
			element.setText(codePrix);
		}
		
		element = root.addElement("CodePrixType");
		if(codePrixType != null){
			element.setText(codePrixType);
		}
		
		element = root.addElement("CreditControlType");
		if(creditControlType != null){
			element.setText(creditControlType.toString());
		} else {
			element.setText("0");
		}
		
		element = root.addElement("CreditControlPW");
		if(creditControlPW != null){
			element.setText(creditControlPW);
		}
		return document.asXML();
	}
}
