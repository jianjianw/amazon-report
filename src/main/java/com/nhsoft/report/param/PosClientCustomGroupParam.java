package com.nhsoft.report.param;

import com.nhsoft.report.util.DateUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PosClientCustomGroupParam implements Serializable {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PosClientCustomGroupParam.class);

	private static final long serialVersionUID = 8994202991668823517L;
	private Date birthdayFrom;// 生日起
	private Date birthdayTo;// 生日至
	private String clientType; // 客户类型
	private String clientSettlementType; // 结算方式
	private Integer firstSaleDayAgeFrom; // 首次销售日期距今天数起
	private Integer firstSaleDayAgeTo; // 首次销售日期距今天止
	private Date nearestSaleDateFrom; // 最近销售日期起
	private Date nearestSaleDateTo; // 最近销售日期止
	private Integer unSaleDayFrom; // 未销售天数起;
	private Integer unSaleDayTo; // 未销售天数止;
	private Date saleDateFrom; // 销售日期起
	private Date saleDateTo; // 销售日期止
	private BigDecimal saleMoneyFrom; // 销售金额起
	private BigDecimal saleMoneyTo; // 销售金额止
	private Integer saleCountFrom; // 销售次数起
	private Integer saleCountTo; // 销售次数止
	private BigDecimal avgMoneyFrom; // 客单价起
	private BigDecimal avgMoneyTo; // 客单价止
	private BigDecimal aveSalePeriodFrom; // 平均销售周期起
	private BigDecimal aveSalePeriodTo; // 平均销售周期止

	/**
	 * 销售次数比较
	 */
	private String saleCountCompareItem1;// 比较项1
	private String saleCountCompareItem2;// 比较项2
	private String saleCountCompareType;// 比较规则
	private Integer saleCountCompareValue;// 比较次数值

	/**
	 * 销售金额比较
	 */
	private String saleMoneyCompareItem1;// 比较项1
	private String saleMoneyCompareItem2;// 比较项2
	private String saleMoneyCompareType;// 比较规则
	private BigDecimal saleMoneyCompareValue;// 比较次数值

	public BigDecimal getAvgMoneyFrom() {
		return avgMoneyFrom;
	}

	public void setAvgMoneyFrom(BigDecimal avgMoneyFrom) {
		this.avgMoneyFrom = avgMoneyFrom;
	}

	public BigDecimal getAvgMoneyTo() {
		return avgMoneyTo;
	}

	public void setAvgMoneyTo(BigDecimal avgMoneyTo) {
		this.avgMoneyTo = avgMoneyTo;
	}

	public Date getBirthdayFrom() {
		return birthdayFrom;
	}

	public void setBirthdayFrom(Date birthdayFrom) {
		this.birthdayFrom = birthdayFrom;
	}

	public Date getBirthdayTo() {
		return birthdayTo;
	}

	public void setBirthdayTo(Date birthdayTo) {
		this.birthdayTo = birthdayTo;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientSettlementType() {
		return clientSettlementType;
	}

	public void setClientSettlementType(String clientSettlementType) {
		this.clientSettlementType = clientSettlementType;
	}

	public Integer getFirstSaleDayAgeFrom() {
		return firstSaleDayAgeFrom;
	}

	public void setFirstSaleDayAgeFrom(Integer firstSaleDayAgeFrom) {
		this.firstSaleDayAgeFrom = firstSaleDayAgeFrom;
	}

	public Integer getFirstSaleDayAgeTo() {
		return firstSaleDayAgeTo;
	}

	public void setFirstSaleDayAgeTo(Integer firstSaleDayAgeTo) {
		this.firstSaleDayAgeTo = firstSaleDayAgeTo;
	}

	public Date getNearestSaleDateFrom() {
		return nearestSaleDateFrom;
	}

	public void setNearestSaleDateFrom(Date nearestSaleDateFrom) {
		this.nearestSaleDateFrom = nearestSaleDateFrom;
	}

	public Date getNearestSaleDateTo() {
		return nearestSaleDateTo;
	}

	public void setNearestSaleDateTo(Date nearestSaleDateTo) {
		this.nearestSaleDateTo = nearestSaleDateTo;
	}

	public Integer getUnSaleDayFrom() {
		return unSaleDayFrom;
	}

	public void setUnSaleDayFrom(Integer unSaleDayFrom) {
		this.unSaleDayFrom = unSaleDayFrom;
	}

	public Integer getUnSaleDayTo() {
		return unSaleDayTo;
	}

	public void setUnSaleDayTo(Integer unSaleDayTo) {
		this.unSaleDayTo = unSaleDayTo;
	}

	public Date getSaleDateFrom() {
		return saleDateFrom;
	}

	public void setSaleDateFrom(Date saleDateFrom) {
		this.saleDateFrom = saleDateFrom;
	}

	public Date getSaleDateTo() {
		return saleDateTo;
	}

	public void setSaleDateTo(Date saleDateTo) {
		this.saleDateTo = saleDateTo;
	}

	public BigDecimal getSaleMoneyFrom() {
		return saleMoneyFrom;
	}

	public void setSaleMoneyFrom(BigDecimal saleMoneyFrom) {
		this.saleMoneyFrom = saleMoneyFrom;
	}

	public BigDecimal getSaleMoneyTo() {
		return saleMoneyTo;
	}

	public void setSaleMoneyTo(BigDecimal saleMoneyTo) {
		this.saleMoneyTo = saleMoneyTo;
	}

	public Integer getSaleCountFrom() {
		return saleCountFrom;
	}

	public void setSaleCountFrom(Integer saleCountFrom) {
		this.saleCountFrom = saleCountFrom;
	}

	public Integer getSaleCountTo() {
		return saleCountTo;
	}

	public void setSaleCountTo(Integer saleCountTo) {
		this.saleCountTo = saleCountTo;
	}

	public BigDecimal getAveSalePeriodFrom() {
		return aveSalePeriodFrom;
	}

	public void setAveSalePeriodFrom(BigDecimal aveSalePeriodFrom) {
		this.aveSalePeriodFrom = aveSalePeriodFrom;
	}

	public BigDecimal getAveSalePeriodTo() {
		return aveSalePeriodTo;
	}

	public void setAveSalePeriodTo(BigDecimal aveSalePeriodTo) {
		this.aveSalePeriodTo = aveSalePeriodTo;
	}

	public String getSaleCountCompareItem1() {
		return saleCountCompareItem1;
	}

	public void setSaleCountCompareItem1(String saleCountCompareItem1) {
		this.saleCountCompareItem1 = saleCountCompareItem1;
	}

	public String getSaleCountCompareItem2() {
		return saleCountCompareItem2;
	}

	public void setSaleCountCompareItem2(String saleCountCompareItem2) {
		this.saleCountCompareItem2 = saleCountCompareItem2;
	}

	public String getSaleCountCompareType() {
		return saleCountCompareType;
	}

	public void setSaleCountCompareType(String saleCountCompareType) {
		this.saleCountCompareType = saleCountCompareType;
	}

	public Integer getSaleCountCompareValue() {
		return saleCountCompareValue;
	}

	public void setSaleCountCompareValue(Integer saleCountCompareValue) {
		this.saleCountCompareValue = saleCountCompareValue;
	}

	public String getSaleMoneyCompareItem1() {
		return saleMoneyCompareItem1;
	}

	public void setSaleMoneyCompareItem1(String saleMoneyCompareItem1) {
		this.saleMoneyCompareItem1 = saleMoneyCompareItem1;
	}

	public String getSaleMoneyCompareItem2() {
		return saleMoneyCompareItem2;
	}

	public void setSaleMoneyCompareItem2(String saleMoneyCompareItem2) {
		this.saleMoneyCompareItem2 = saleMoneyCompareItem2;
	}

	public String getSaleMoneyCompareType() {
		return saleMoneyCompareType;
	}

	public void setSaleMoneyCompareType(String saleMoneyCompareType) {
		this.saleMoneyCompareType = saleMoneyCompareType;
	}

	public BigDecimal getSaleMoneyCompareValue() {
		return saleMoneyCompareValue;
	}

	public void setSaleMoneyCompareValue(BigDecimal saleMoneyCompareValue) {
		this.saleMoneyCompareValue = saleMoneyCompareValue;
	}
	
	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		
		Element root = document.addElement("PosClientCustomGroupParam");
		if (birthdayFrom != null) {
			root.addElement("birthdayFrom").setText(DateUtil.getDateTimeString(birthdayFrom));
			
		}
		if (birthdayTo != null) {
			root.addElement("birthdayTo").setText(DateUtil.getDateTimeString(birthdayTo));
			
		}
		if(clientType != null){
			root.addElement("clientType").setText(clientType);

		}
		if(clientSettlementType != null){
			root.addElement("clientSettlementType").setText(clientSettlementType);

		}
		if(firstSaleDayAgeFrom != null){
			root.addElement("firstSaleDayAgeFrom").setText(firstSaleDayAgeFrom.toString());

		}
		if(firstSaleDayAgeTo != null){
			root.addElement("firstSaleDayAgeTo").setText(firstSaleDayAgeTo.toString());

		}
		if (nearestSaleDateFrom != null) {
			root.addElement("nearestSaleDateFrom").setText(DateUtil.getDateTimeString(nearestSaleDateFrom));
			
		}
		if (nearestSaleDateTo != null) {
			root.addElement("nearestSaleDateTo").setText(DateUtil.getDateTimeString(nearestSaleDateTo));
			
		}
		if(unSaleDayFrom != null){
			root.addElement("unSaleDayFrom").setText(unSaleDayFrom.toString());

		}
		if(unSaleDayTo != null){
			root.addElement("unSaleDayTo").setText(unSaleDayTo.toString());

		}
		if (saleDateFrom != null) {
			root.addElement("saleDateFrom").setText(DateUtil.getDateTimeString(saleDateFrom));
			
		}
		if (saleDateTo != null) {
			root.addElement("saleDateTo").setText(DateUtil.getDateTimeString(saleDateTo));
			
		}
		if(saleMoneyFrom != null){
			root.addElement("saleMoneyFrom").setText(saleMoneyFrom.toString());

		}
		if(saleMoneyTo != null){
			root.addElement("saleMoneyTo").setText(saleMoneyTo.toString());

		}
		if(saleCountFrom != null){
			root.addElement("saleCountFrom").setText(saleCountFrom.toString());

		}
		if(saleCountTo != null){
			root.addElement("saleCountTo").setText(saleCountTo.toString());

		}
		if(aveSalePeriodFrom != null){
			root.addElement("aveSalePeriodFrom").setText(aveSalePeriodFrom.toString());

		}
		if(aveSalePeriodTo != null){
			root.addElement("aveSalePeriodTo").setText(aveSalePeriodTo.toString());

		}
		if(avgMoneyFrom != null){
			root.addElement("avgMoneyFrom").setText(avgMoneyFrom.toString());

		}
		if(avgMoneyTo != null){
			root.addElement("avgMoneyTo").setText(avgMoneyTo.toString());

		}
		
		if(saleCountCompareItem1 != null){
			root.addElement("saleCountCompareItem1").setText(saleCountCompareItem1);

		}
		if(saleCountCompareItem2 != null){
			root.addElement("saleCountCompareItem2").setText(saleCountCompareItem2);

		}
		if(saleCountCompareType != null){
			root.addElement("saleCountCompareType").setText(saleCountCompareType);

		}
		if(saleCountCompareValue != null){
			root.addElement("saleCountCompareValue").setText(saleCountCompareValue.toString());

		}		
		if(saleMoneyCompareItem1 != null){
			root.addElement("saleMoneyCompareItem1").setText(saleMoneyCompareItem1);

		}
		if(saleMoneyCompareItem2 != null){
			root.addElement("saleMoneyCompareItem2").setText(saleMoneyCompareItem2);

		}
		if(saleMoneyCompareType != null){
			root.addElement("saleMoneyCompareType").setText(saleMoneyCompareType);

		}
		if(saleMoneyCompareValue != null){
			root.addElement("saleMoneyCompareValue").setText(saleMoneyCompareValue.toString());

		}
		return document.asXML();
	}
	
	public static PosClientCustomGroupParam readFromXml(String text){ 
		try {
			Document document = DocumentHelper.parseText(text);
			PosClientCustomGroupParam param = new PosClientCustomGroupParam();
			
			Element root = document.getRootElement();
			
			Element element;
			
			element = (Element) root.selectSingleNode("birthdayFrom");
			if (element != null) {
				param.setBirthdayFrom(DateUtil.getDateTimeHMS(element.getText()));
			}
			element = (Element) root.selectSingleNode("birthdayTo");
			if (element != null) {
				param.setBirthdayTo(DateUtil.getDateTimeHMS(element.getText()));
			}
			element = (Element) root.selectSingleNode("clientType");
			if (element != null) {
				param.setClientType(element.getText());
			}
			element = (Element) root.selectSingleNode("clientSettlementType");
			if (element != null) {
				param.setClientSettlementType(element.getText());
			}
			element = (Element) root.selectSingleNode("firstSaleDayAgeFrom");
			if (element != null) {
				param.setFirstSaleDayAgeFrom(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("firstSaleDayAgeTo");
			if (element != null) {
				param.setFirstSaleDayAgeTo(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("nearestSaleDateFrom");
			if (element != null) {
				param.setNearestSaleDateFrom(DateUtil.getDateTimeHMS(element.getText()));
			}
			element = (Element) root.selectSingleNode("nearestSaleDateTo");
			if (element != null) {
				param.setNearestSaleDateTo(DateUtil.getDateTimeHMS(element.getText()));
			}
			element = (Element) root.selectSingleNode("unSaleDayFrom");
			if (element != null) {
				param.setUnSaleDayFrom(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("unSaleDayTo");
			if (element != null) {
				param.setUnSaleDayTo(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleDateFrom");
			if (element != null) {
				param.setSaleDateFrom(DateUtil.getDateTimeHMS(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleDateTo");
			if (element != null) {
				param.setSaleDateTo(DateUtil.getDateTimeHMS(element.getText()));
			}
			
			element = (Element) root.selectSingleNode("saleMoneyFrom");
			if (element != null) {
				param.setSaleMoneyFrom(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleMoneyTo");
			if (element != null) {
				param.setSaleMoneyTo(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleCountFrom");
			if (element != null) {
				param.setSaleCountFrom(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleCountTo");
			if (element != null) {
				param.setSaleCountTo(Integer.parseInt(element.getText()));
			}
			element = (Element) root.selectSingleNode("avgMoneyFrom");
			if (element != null) {
				param.setAvgMoneyFrom(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("avgMoneyTo");
			if (element != null) {
				param.setAvgMoneyTo(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("aveSalePeriodFrom");
			if (element != null) {
				param.setAveSalePeriodFrom(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("aveSalePeriodTo");
			if (element != null) {
				param.setAveSalePeriodTo(new BigDecimal(element.getText()));
			}
			element = (Element) root.selectSingleNode("saleCountCompareItem1");
			if (element != null) {
				param.setSaleCountCompareItem1(element.getText());
			}
			element = (Element) root.selectSingleNode("saleCountCompareItem2");
			if (element != null) {
				param.setSaleCountCompareItem2(element.getText());
			}
			element = (Element) root.selectSingleNode("saleCountCompareType");
			if (element != null) {
				param.setSaleCountCompareType(element.getText());
			}
			element = (Element) root.selectSingleNode("saleCountCompareValue");
			if (element != null) {
				param.setSaleCountCompareValue(Integer.parseInt(element.getText()));
			}
			
			
			element = (Element) root.selectSingleNode("saleMoneyCompareItem1");
			if (element != null) {
				param.setSaleMoneyCompareItem1(element.getText());
			}
			element = (Element) root.selectSingleNode("saleMoneyCompareItem2");
			if (element != null) {
				param.setSaleMoneyCompareItem2(element.getText());
			}
			element = (Element) root.selectSingleNode("saleMoneyCompareType");
			if (element != null) {
				param.setSaleMoneyCompareType(element.getText());
			}
			element = (Element) root.selectSingleNode("saleMoneyCompareValue");
			if (element != null) {
				param.setSaleMoneyCompareValue(new BigDecimal(element.getText()));
			}
			
			return param; 
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
		
	}


}
