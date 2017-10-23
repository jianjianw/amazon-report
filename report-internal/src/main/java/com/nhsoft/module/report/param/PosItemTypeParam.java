package com.nhsoft.module.report.param;

import com.nhsoft.module.report.util.AppUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class PosItemTypeParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6731489339254079073L;
	private static final Logger logger = LoggerFactory.getLogger(PosItemTypeParam.class);
	private String posItemTypeCode;
	private String posItemTypeName;
	private Boolean isEnable;
	private String posItemTypeShortName;
	private String posItemTypeFatherCode;
	private Integer posItemTypeSn;
	private Boolean dayPurchase; //是否日采类别
	private Boolean posItemTypePriceAdj; //是否允许调价
	
	public PosItemTypeParam(){
		
	}
	
	public PosItemTypeParam(String posItemTypeCode, String posItemTypeName){
		this.posItemTypeCode = posItemTypeCode;
		this.posItemTypeName = posItemTypeName;
	}
	
	
	public Boolean getPosItemTypePriceAdj() {
		return posItemTypePriceAdj;
	}

	public void setPosItemTypePriceAdj(Boolean posItemTypePriceAdj) {
		this.posItemTypePriceAdj = posItemTypePriceAdj;
	}

	public Boolean getDayPurchase() {
		return dayPurchase;
	}

	public void setDayPurchase(Boolean dayPurchase) {
		this.dayPurchase = dayPurchase;
	}

	public String getPosItemTypeCode() {
		return posItemTypeCode;
	}

	public void setPosItemTypeCode(String posItemTypeCode) {
		this.posItemTypeCode = posItemTypeCode;
	}

	public String getPosItemTypeName() {
		return posItemTypeName;
	}

	public void setPosItemTypeName(String posItemTypeName) {
		this.posItemTypeName = posItemTypeName;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getPosItemTypeShortName() {
		return posItemTypeShortName;
	}

	public void setPosItemTypeShortName(String posItemTypeShortName) {
		this.posItemTypeShortName = posItemTypeShortName;
	}

	public String getPosItemTypeFatherCode() {
		return posItemTypeFatherCode;
	}

	public void setPosItemTypeFatherCode(String posItemTypeFatherCode) {
		this.posItemTypeFatherCode = posItemTypeFatherCode;
	}

	public Integer getPosItemTypeSn() {
		return posItemTypeSn;
	}

	public void setPosItemTypeSn(Integer posItemTypeSn) {
		this.posItemTypeSn = posItemTypeSn;
	}
	
	public static PosItemTypeParam fromXml(Element father){
		PosItemTypeParam param = new PosItemTypeParam();
		Attribute attribute = father.attribute("商品类别编码");
		if(attribute != null){
			param.setPosItemTypeCode(attribute.getText());
		}
		
		Element element = (Element)father.selectSingleNode("商品类别名称");
		if(element != null){
			param.setPosItemTypeName(element.getText());
		}
		
		element = (Element)father.selectSingleNode("是否启用");
		if(element != null){
			param.setIsEnable(BooleanUtils.toBoolean(element.getText(), "-1", "0"));
		}
		
		element = (Element)father.selectSingleNode("商品类别速记码");
		if(element != null){
			param.setPosItemTypeShortName(element.getText());
		}
		
		element = (Element)father.selectSingleNode("商品类别父类编码");
		if(element != null){
			if(!element.getText().isEmpty()){
				param.setPosItemTypeFatherCode(element.getText());
			}	
		}
		
		element = (Element)father.selectSingleNode("商品类别序号");
		if(element != null){
			param.setPosItemTypeSn(Integer.parseInt(element.getText()));
		}
		
		element = (Element)father.selectSingleNode("是否日采类别");
		if(element != null){
			param.setDayPurchase(AppUtil.strToBool(element.getText()));
		} else {
			param.setDayPurchase(false);
		}
		
		element = (Element)father.selectSingleNode("PosItemCategoryPriceAdj");
		if(element != null){
			param.setPosItemTypePriceAdj(AppUtil.strToBool(element.getText()));
		} else {
			param.setPosItemTypePriceAdj(true);
		}
		return param;
	}
	
	public void toXml(Element father){
		Element element;
		if(posItemTypeCode != null){
			father.addAttribute("商品类别编码", posItemTypeCode.toString());
			
		}		
		if(posItemTypeName != null){
			element = father.addElement("商品类别名称");
			element.setText(posItemTypeName);
		}
		if(isEnable != null){
			element = father.addElement("是否启用");
			element.setText(BooleanUtils.toString(isEnable, "-1", "0"));
		}
		if(posItemTypeShortName != null){
			element = father.addElement("商品类别速记码");
			element.setText(posItemTypeShortName);
		}
		if(posItemTypeFatherCode != null){
			element = father.addElement("商品类别父类编码");
			element.setText(posItemTypeFatherCode.toString());
		}
		if(posItemTypeSn != null){
			element = father.addElement("商品类别序号");
			element.setText(posItemTypeSn.toString());
		}
		father.addElement("是否日采类别").setText(BooleanUtils.toString(dayPurchase, "1", "0", "0"));;

		if(posItemTypePriceAdj != null){
			element = father.addElement("PosItemCategoryPriceAdj");
			element.setText(BooleanUtils.toString(posItemTypePriceAdj, "-1", "0"));
		}
	}
	
	public static List<PosItemTypeParam> fromXml(byte[] stream){
		List<PosItemTypeParam> list = new ArrayList<PosItemTypeParam>();		
		try {
			Document document = DocumentHelper.parseText(new String(stream));
			document.setXMLEncoding("GBK");
			Element root = (Element)document.selectSingleNode("商品类别列表");
			Iterator<Element> iterator  = root.elementIterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				PosItemTypeParam param = fromXml(element);
				list.add(param);
			}	
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}		
		return list;
	}
	
	public static String toXml(List<PosItemTypeParam> params){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("商品类别列表");
		for(int i = 0;i < params.size();i++){
			PosItemTypeParam param = params.get(i);
			if(StringUtils.isEmpty(param.getPosItemTypeCode()) && StringUtils.isEmpty(param.getPosItemTypeName())){
				continue;
			}
			Element father = root.addElement("商品类别");
			param.toXml(father);
		}		
		return document.asXML();
	}

	public static String getFatherCode(List<PosItemTypeParam> posItemTypeParams, String posItemTypeCode) {
		for(int i = 0;i < posItemTypeParams.size();i++){
			PosItemTypeParam param = posItemTypeParams.get(i);
			if(param.getPosItemTypeCode().equals(posItemTypeCode)){
				return param.getPosItemTypeFatherCode();
			}
		}
		return null;
	}
}
