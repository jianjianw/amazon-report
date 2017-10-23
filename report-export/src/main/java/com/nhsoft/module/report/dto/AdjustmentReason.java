package com.nhsoft.module.report.dto;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class AdjustmentReason implements Serializable {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(AdjustmentReason.class);
	private static final long serialVersionUID = -4024248337900504368L;
	private String adjustmentReasonCode;
	private String adjustmentReasonName;
	private String adjustmentInoutType;
	private String adjustmentInoutCategory;

	public String getAdjustmentReasonCode() {
		return adjustmentReasonCode;
	}

	public void setAdjustmentReasonCode(String adjustmentReasonCode) {
		this.adjustmentReasonCode = adjustmentReasonCode;
	}

	public String getAdjustmentReasonName() {
		return adjustmentReasonName;
	}

	public void setAdjustmentReasonName(String adjustmentReasonName) {
		this.adjustmentReasonName = adjustmentReasonName;
	}

	
	public String getAdjustmentInoutType() {
		return adjustmentInoutType;
	}

	public void setAdjustmentInoutType(String adjustmentInoutType) {
		this.adjustmentInoutType = adjustmentInoutType;
	}

	public String getAdjustmentInoutCategory() {
		return adjustmentInoutCategory;
	}

	public void setAdjustmentInoutCategory(String adjustmentInoutCategory) {
		this.adjustmentInoutCategory = adjustmentInoutCategory;
	}

	public static String writeToXml(List<AdjustmentReason> adjustmentReasons){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("AdjustmentReasons");
		for(int i = 0;i < adjustmentReasons.size();i++){
			AdjustmentReason adjustmentReason = adjustmentReasons.get(i);
			if(adjustmentReason.getAdjustmentInoutCategory() == null){
				adjustmentReason.setAdjustmentInoutCategory("");
			}
			Element element = root.addElement("AdjustmentReason");
			element.addAttribute("AdjustmentReasonCode", adjustmentReason.getAdjustmentReasonCode());
			element.addAttribute("AdjustmentReasonName", adjustmentReason.getAdjustmentReasonName());
			element.addAttribute("AdjustmentInoutType", adjustmentReason.getAdjustmentInoutType());
			element.addAttribute("AdjustmentInoutCategory", adjustmentReason.getAdjustmentInoutCategory());
		}		
		return document.asXML();
	}
	
	public static List<AdjustmentReason> readFromXml(String text){
		List<AdjustmentReason> list = new ArrayList<AdjustmentReason>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				AdjustmentReason adjustmentReason = new AdjustmentReason();
				adjustmentReason.setAdjustmentReasonName(element.attributeValue("AdjustmentReasonName"));
				adjustmentReason.setAdjustmentReasonCode(element.attributeValue("AdjustmentReasonCode"));
				adjustmentReason.setAdjustmentInoutType(element.attributeValue("AdjustmentInoutType"));
				
				Attribute attribute = element.attribute("AdjustmentInoutCategory");
				if(attribute != null){
					adjustmentReason.setAdjustmentInoutCategory(attribute.getValue());
				} else {
					adjustmentReason.setAdjustmentInoutCategory("");
				}				
				list.add(adjustmentReason);
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return list;	
	}

}
