package com.nhsoft.module.report.param;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class ExpressCompany implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ExpressCompany.class);
	private static final long serialVersionUID = 7348063528043054850L;
	private String expressCompanyName;
	private String ExpressCompanyLinkMan;
	private String ExpressCompanyPhone;
	private String ExpressCompanyFax;
	private String ExpressCompanyMobile;
	private String expressCompanyAddr;
	private String expressCompanyWeb;
	private String expressCompanyMemo;
	private String expressCompanyType;//车辆或者物流公司
	private BigDecimal expressCompanyVolume;//车辆体积
	private BigDecimal expressCompanyWeight;//车辆载重
	private String expressCompanyRealName;
	private String expressCompanyAccount;
	private String expressCompanyPsw;

	public String getExpressCompanyRealName() {
		return expressCompanyRealName;
	}

	public void setExpressCompanyRealName(String expressCompanyRealName) {
		this.expressCompanyRealName = expressCompanyRealName;
	}

	public String getExpressCompanyAccount() {
		return expressCompanyAccount;
	}

	public void setExpressCompanyAccount(String expressCompanyAccount) {
		this.expressCompanyAccount = expressCompanyAccount;
	}

	public String getExpressCompanyPsw() {
		return expressCompanyPsw;
	}

	public void setExpressCompanyPsw(String expressCompanyPsw) {
		this.expressCompanyPsw = expressCompanyPsw;
	}

	public String getExpressCompanyType() {
		return expressCompanyType;
	}

	public void setExpressCompanyType(String expressCompanyType) {
		this.expressCompanyType = expressCompanyType;
	}

	public BigDecimal getExpressCompanyVolume() {
		return expressCompanyVolume;
	}

	public void setExpressCompanyVolume(BigDecimal expressCompanyVolume) {
		this.expressCompanyVolume = expressCompanyVolume;
	}

	public BigDecimal getExpressCompanyWeight() {
		return expressCompanyWeight;
	}

	public void setExpressCompanyWeight(BigDecimal expressCompanyWeight) {
		this.expressCompanyWeight = expressCompanyWeight;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getExpressCompanyLinkMan() {
		return ExpressCompanyLinkMan;
	}

	public void setExpressCompanyLinkMan(String expressCompanyLinkMan) {
		ExpressCompanyLinkMan = expressCompanyLinkMan;
	}

	public String getExpressCompanyPhone() {
		return ExpressCompanyPhone;
	}

	public void setExpressCompanyPhone(String expressCompanyPhone) {
		ExpressCompanyPhone = expressCompanyPhone;
	}

	public String getExpressCompanyFax() {
		return ExpressCompanyFax;
	}

	public void setExpressCompanyFax(String expressCompanyFax) {
		ExpressCompanyFax = expressCompanyFax;
	}

	public String getExpressCompanyMobile() {
		return ExpressCompanyMobile;
	}

	public void setExpressCompanyMobile(String expressCompanyMobile) {
		ExpressCompanyMobile = expressCompanyMobile;
	}

	public String getExpressCompanyAddr() {
		return expressCompanyAddr;
	}

	public void setExpressCompanyAddr(String expressCompanyAddr) {
		this.expressCompanyAddr = expressCompanyAddr;
	}

	public String getExpressCompanyWeb() {
		return expressCompanyWeb;
	}

	public void setExpressCompanyWeb(String expressCompanyWeb) {
		this.expressCompanyWeb = expressCompanyWeb;
	}

	public String getExpressCompanyMemo() {
		return expressCompanyMemo;
	}

	public void setExpressCompanyMemo(String expressCompanyMemo) {
		this.expressCompanyMemo = expressCompanyMemo;
	}
	
	public static List<ExpressCompany> readFromXml(String text){
		List<ExpressCompany> list = new ArrayList<ExpressCompany>();
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				ExpressCompany expressCompany = new ExpressCompany();
				expressCompany.setExpressCompanyAddr(element.attributeValue("ExpressCompanyAddr"));
				expressCompany.setExpressCompanyFax(element.attributeValue("ExpressCompanyFax"));
				expressCompany.setExpressCompanyLinkMan(element.attributeValue("ExpressCompanyLinkMan"));
				expressCompany.setExpressCompanyMemo(element.attributeValue("ExpressCompanyMemo"));
				expressCompany.setExpressCompanyMobile(element.attributeValue("ExpressCompanyMobile"));
				expressCompany.setExpressCompanyName(element.attributeValue("ExpressCompanyName"));
				expressCompany.setExpressCompanyPhone(element.attributeValue("ExpressCompanyPhone"));
				expressCompany.setExpressCompanyWeb(element.attributeValue("ExpressCompanyWeb"));
				
				Attribute attribute = element.attribute("ExpressCompanyType");
				if(attribute != null){
					expressCompany.setExpressCompanyType(attribute.getText());
				}
				attribute = element.attribute("ExpressCompanyVolume");
				if(attribute != null){
					expressCompany.setExpressCompanyVolume(new BigDecimal(attribute.getText()));
				} else {
					expressCompany.setExpressCompanyVolume(BigDecimal.ZERO);
				}
				
				attribute = element.attribute("ExpressCompanyWeight");
				if(attribute != null){
					expressCompany.setExpressCompanyWeight(new BigDecimal(attribute.getText()));
				} else {
					expressCompany.setExpressCompanyWeight(BigDecimal.ZERO);

				}
				attribute = element.attribute("ExpressCompanyRealName");
				if(attribute != null){
					expressCompany.setExpressCompanyRealName(attribute.getText());
				}
				attribute = element.attribute("ExpressCompanyAccount");
				if(attribute != null){
					expressCompany.setExpressCompanyAccount(attribute.getText());
				}
				attribute = element.attribute("ExpressCompanyPsw");
				if(attribute != null){
					expressCompany.setExpressCompanyPsw(attribute.getText());
				}
				list.add(expressCompany);
			}
			
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	public static String writeToXml(List<ExpressCompany> expressCompanies){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("ExpressCompanys");
		for(int i = 0;i < expressCompanies.size();i++){
			ExpressCompany expressCompany = expressCompanies.get(i);
			Element element = root.addElement("ExpressCompany");
		
			element.addAttribute("ExpressCompanyName", expressCompany.getExpressCompanyName() == null?"":expressCompany.getExpressCompanyName());
			element.addAttribute("ExpressCompanyLinkMan", expressCompany.getExpressCompanyLinkMan() == null?"":expressCompany.getExpressCompanyLinkMan());
			element.addAttribute("ExpressCompanyPhone", expressCompany.getExpressCompanyPhone() == null?"":expressCompany.getExpressCompanyPhone());
			element.addAttribute("ExpressCompanyFax", expressCompany.getExpressCompanyFax() == null?"":expressCompany.getExpressCompanyFax());
			element.addAttribute("ExpressCompanyMobile", expressCompany.getExpressCompanyMobile() == null?"":expressCompany.getExpressCompanyMobile());
			element.addAttribute("ExpressCompanyAddr", expressCompany.getExpressCompanyAddr() == null?"":expressCompany.getExpressCompanyAddr());
			element.addAttribute("ExpressCompanyWeb", expressCompany.getExpressCompanyWeb() == null?"":expressCompany.getExpressCompanyWeb());
			element.addAttribute("ExpressCompanyMemo", expressCompany.getExpressCompanyMemo() == null?"":expressCompany.getExpressCompanyMemo());

			if(StringUtils.isNotEmpty(expressCompany.getExpressCompanyType())){
				element.addAttribute("ExpressCompanyType", expressCompany.getExpressCompanyType());
			}
			if(expressCompany.getExpressCompanyVolume() != null){
				element.addAttribute("ExpressCompanyVolume", expressCompany.getExpressCompanyVolume().toString());

			}
			if(expressCompany.getExpressCompanyWeight() != null){
				element.addAttribute("ExpressCompanyWeight", expressCompany.getExpressCompanyWeight().toString());

			}
			if(StringUtils.isNotEmpty(expressCompany.getExpressCompanyRealName())){
				element.addAttribute("ExpressCompanyRealName", expressCompany.getExpressCompanyRealName());
			}
			if(StringUtils.isNotEmpty(expressCompany.getExpressCompanyAccount())){
				element.addAttribute("ExpressCompanyAccount", expressCompany.getExpressCompanyAccount());
			}
			if(StringUtils.isNotEmpty(expressCompany.getExpressCompanyPsw())){
				element.addAttribute("ExpressCompanyPsw", expressCompany.getExpressCompanyPsw());
			}
		}

		return document.asXML();
	}

	public static ExpressCompany get(List<ExpressCompany> expressCompanies, String expressCompanyName) {
		for(int i = 0;i < expressCompanies.size();i++){
			ExpressCompany expressCompany = expressCompanies.get(i);
			
			if(expressCompany.getExpressCompanyName().equals(expressCompanyName)){
				return expressCompany;
			}
		}
		return null;
	}

}
