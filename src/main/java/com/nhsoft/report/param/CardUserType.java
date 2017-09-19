package com.nhsoft.report.param;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unchecked")
public class CardUserType implements Serializable {
	
	private static final Logger logger = LoggerFactory.getLogger(CardUserType.class);
	private static final long serialVersionUID = 6145162577269307990L;
	private String typeCode;
	private String typeName;
	private Integer typeDefaultDays;
	private Boolean typeDefaultPermanent;
	private Integer typePriceLevel;
	private BigDecimal typeDiscount;
	private Boolean typeSupportDeposit;
	private Boolean typeSupportPoint;
	private BigDecimal typeDepositRate;
	private BigDecimal typeConsumeRate;
	private Boolean useInEnrollShop;
	private BigDecimal depositBasic;
	private BigDecimal birthDiscount; // 生日折扣
	private List<PointRange> depositRates = new ArrayList<PointRange>();
	private List<PointRange> consumeRates = new ArrayList<PointRange>();
	private String cardUserTypeImageId;
	private String cardUserTypeImagePath;
	private BigDecimal cardMinBalance;// 最低控制金额
	private Boolean disablePayDiscount = false;//使用会员卡支付不享受折扣。
	
	public Boolean getDisablePayDiscount() {
		return disablePayDiscount;
	}
	
	public void setDisablePayDiscount(Boolean disablePayDiscount) {
		this.disablePayDiscount = disablePayDiscount;
	}
	
	public BigDecimal getCardMinBalance() {
		return cardMinBalance;
	}
	
	public void setCardMinBalance(BigDecimal cardMinBalance) {
		this.cardMinBalance = cardMinBalance;
	}
	
	public String getCardUserTypeImagePath() {
		return cardUserTypeImagePath;
	}
	
	public void setCardUserTypeImagePath(String cardUserTypeImagePath) {
		this.cardUserTypeImagePath = cardUserTypeImagePath;
	}
	
	public BigDecimal getBirthDiscount() {
		return birthDiscount;
	}
	
	public String getCardUserTypeImageId() {
		return cardUserTypeImageId;
	}
	
	public void setCardUserTypeImageId(String cardUserTypeImageId) {
		this.cardUserTypeImageId = cardUserTypeImageId;
	}
	
	public void setBirthDiscount(BigDecimal birthDiscount) {
		this.birthDiscount = birthDiscount;
	}
	
	public BigDecimal getDepositBasic() {
		return depositBasic;
	}
	
	public void setDepositBasic(BigDecimal depositBasic) {
		this.depositBasic = depositBasic;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getTypeDefaultDays() {
		return typeDefaultDays;
	}
	
	public void setTypeDefaultDays(Integer typeDefaultDays) {
		this.typeDefaultDays = typeDefaultDays;
	}
	
	public Boolean getTypeDefaultPermanent() {
		return typeDefaultPermanent;
	}
	
	public void setTypeDefaultPermanent(Boolean typeDefaultPermanent) {
		this.typeDefaultPermanent = typeDefaultPermanent;
	}
	
	public Integer getTypePriceLevel() {
		return typePriceLevel;
	}
	
	public void setTypePriceLevel(Integer typePriceLevel) {
		this.typePriceLevel = typePriceLevel;
	}
	
	public BigDecimal getTypeDiscount() {
		return typeDiscount;
	}
	
	public void setTypeDiscount(BigDecimal typeDiscount) {
		this.typeDiscount = typeDiscount;
	}
	
	public Boolean getTypeSupportDeposit() {
		return typeSupportDeposit;
	}
	
	public void setTypeSupportDeposit(Boolean typeSupportDeposit) {
		this.typeSupportDeposit = typeSupportDeposit;
	}
	
	public Boolean getTypeSupportPoint() {
		return typeSupportPoint;
	}
	
	public void setTypeSupportPoint(Boolean typeSupportPoint) {
		this.typeSupportPoint = typeSupportPoint;
	}
	
	public BigDecimal getTypeDepositRate() {
		return typeDepositRate;
	}
	
	public void setTypeDepositRate(BigDecimal typeDepositRate) {
		this.typeDepositRate = typeDepositRate;
	}
	
	public BigDecimal getTypeConsumeRate() {
		return typeConsumeRate;
	}
	
	public void setTypeConsumeRate(BigDecimal typeConsumeRate) {
		this.typeConsumeRate = typeConsumeRate;
	}
	
	public Boolean getUseInEnrollShop() {
		return useInEnrollShop;
	}
	
	public void setUseInEnrollShop(Boolean useInEnrollShop) {
		this.useInEnrollShop = useInEnrollShop;
	}
	
	public List<PointRange> getDepositRates() {
		return depositRates;
	}
	
	public void setDepositRates(List<PointRange> depositRates) {
		this.depositRates = depositRates;
	}
	
	public List<PointRange> getConsumeRates() {
		return consumeRates;
	}
	
	public void setConsumeRates(List<PointRange> consumeRates) {
		this.consumeRates = consumeRates;
	}
	
	public static String writeToXml(List<CardUserType> cardUserTypes) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("消费卡类型列表");
		for (int i = 0; i < cardUserTypes.size(); i++) {
			CardUserType cardUserType = cardUserTypes.get(i);
			Element element = root.addElement("消费卡类型");
			element.addAttribute("编号", cardUserType.getTypeCode());
			
			element.addElement("名称").setText(cardUserType.getTypeName());
			
			if (cardUserType.getTypeDefaultDays() != null) {
				element.addElement("默认有效日期").setText(cardUserType.getTypeDefaultDays().toString());
			}
			
			if (cardUserType.getTypeDefaultPermanent() != null) {
				element.addElement("永久有效期")
						.setText(BooleanUtils.toString(cardUserType.getTypeDefaultPermanent(), "-1", "0"));
			}
			
			if (cardUserType.getTypePriceLevel() != null) {
				element.addElement("价格级别").setText(cardUserType.getTypePriceLevel().toString());
				
			}
			
			if (cardUserType.getTypeDiscount() != null) {
				element.addElement("折扣").setText(cardUserType.getTypeDiscount().toString());
			}
			
			if (cardUserType.getTypeSupportDeposit() != null) {
				element.addElement("支持储值")
						.setText(BooleanUtils.toString(cardUserType.getTypeSupportDeposit(), "-1", "0"));
			}
			
			if (cardUserType.getTypeSupportPoint() != null) {
				element.addElement("支持积分")
						.setText(BooleanUtils.toString(cardUserType.getTypeSupportPoint(), "-1", "0"));
			}
			
			if (cardUserType.getTypeDepositRate() != null) {
				element.addElement("存款兑分比例").setText(cardUserType.getTypeDepositRate().toString());
			}
			
			if (cardUserType.getTypeConsumeRate() != null) {
				element.addElement("消费兑分比例").setText(cardUserType.getTypeConsumeRate().toString());
			}
			
			if (cardUserType.getUseInEnrollShop() != null) {
				element.addElement("仅发卡门店使用")
						.setText(BooleanUtils.toString(cardUserType.getUseInEnrollShop(), "-1", "0"));
			}
			
			if (cardUserType.getDepositBasic() != null) {
				element.addElement("存款金额基数").setText(cardUserType.getDepositBasic().toString());
			}
			
			if (cardUserType.getBirthDiscount() != null) {
				element.addElement("生日折扣").setText(cardUserType.getBirthDiscount().toString());
			}
			
			if (cardUserType.getCardUserTypeImageId() != null) {
				element.addElement("图片ID").setText(cardUserType.getCardUserTypeImageId());
			}
			if (cardUserType.getCardUserTypeImagePath() != null) {
				element.addElement("图片路径").setText(cardUserType.getCardUserTypeImagePath());
			}
			if (cardUserType.getCardMinBalance() != null) {
				element.addElement("最低控制金额").setText(cardUserType.getCardMinBalance().toString());
			}
			if (cardUserType.getDisablePayDiscount() != null) {
				element.addElement("使用会员卡支付不享受折扣")
						.setText(BooleanUtils.toString(cardUserType.getDisablePayDiscount(), "-1", "0"));
			}
			
		}
		
		return document.asXML();
	}
	
	public static String writeShortToXml(List<CardUserType> cardUserTypes) {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("消费卡类型列表");
		for (int i = 0; i < cardUserTypes.size(); i++) {
			CardUserType cardUserType = cardUserTypes.get(i);
			Element element = root.addElement("消费卡类型");
			element.addAttribute("编号", cardUserType.getTypeCode());
			
			element.addElement("名称").setText(cardUserType.getTypeName());
			
		}
		return document.asXML();
	}
	
	public static List<CardUserType> readFromXml(String text) {
		
		List<CardUserType> list = new ArrayList<CardUserType>();
		if (StringUtils.isEmpty(text)) {
			return list;
		}
		try {
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Iterator<Element> iterator = root.elementIterator("消费卡类型");
			while (iterator.hasNext()) {
				Element element = iterator.next();
				CardUserType cardUserType = new CardUserType();
				cardUserType.setTypeCode(element.attributeValue("编号"));
				cardUserType.setTypeName(element.selectSingleNode("名称").getText());
				
				Element node = (Element) element.selectSingleNode("默认有效日期");
				if (node != null) {
					cardUserType.setTypeDefaultDays(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("永久有效期");
				if (node != null) {
					cardUserType.setTypeDefaultPermanent(BooleanUtils.toBoolean(node.getText(), "-1", "0"));
				}
				
				node = (Element) element.selectSingleNode("价格级别");
				if (node != null) {
					cardUserType.setTypePriceLevel(Integer.parseInt(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("折扣");
				if (node != null) {
					cardUserType.setTypeDiscount(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("支持储值");
				if (node != null) {
					cardUserType.setTypeSupportDeposit(BooleanUtils.toBoolean(node.getText(), "-1", "0"));
				}
				
				node = (Element) element.selectSingleNode("支持积分");
				if (node != null) {
					cardUserType.setTypeSupportPoint(BooleanUtils.toBoolean(node.getText(), "-1", "0"));
				}
				
				node = (Element) element.selectSingleNode("存款兑分比例");
				if (node != null) {
					cardUserType.setTypeDepositRate(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("消费兑分比例");
				if (node != null) {
					cardUserType.setTypeConsumeRate(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("仅发卡门店使用");
				if (node != null) {
					cardUserType.setUseInEnrollShop(BooleanUtils.toBoolean(node.getText(), "-1", "0"));
				}
				
				node = (Element) element.selectSingleNode("存款兑分规则");
				if (node != null) {
					cardUserType.setDepositRates(PointRange.readFromNode(node));
				}
				
				node = (Element) element.selectSingleNode("消费兑分规则");
				if (node != null) {
					cardUserType.setConsumeRates(PointRange.readFromNode(node));
				}
				
				node = (Element) element.selectSingleNode("存款金额基数");
				if (node != null) {
					cardUserType.setDepositBasic(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("生日折扣");
				if (node != null) {
					cardUserType.setBirthDiscount(new BigDecimal(node.getText()));
				}
				
				node = (Element) element.selectSingleNode("图片ID");
				if (node != null) {
					cardUserType.setCardUserTypeImageId(node.getText());
				}
				
				node = (Element) element.selectSingleNode("图片路径");
				if (node != null) {
					cardUserType.setCardUserTypeImagePath(node.getText());
				}
				node = (Element) element.selectSingleNode("最低控制金额");
				if (node != null) {
					cardUserType.setCardMinBalance(new BigDecimal(node.getText()));
				}
				node = (Element) element.selectSingleNode("使用会员卡支付不享受折扣");
				if (node != null) {
					cardUserType.setDisablePayDiscount(BooleanUtils.toBoolean(node.getText(), "-1", "0"));
				} else {
					cardUserType.setDisablePayDiscount(false);
					
				}
				list.add(cardUserType);
			}
			
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return list;
	}
	
	public static String getCardUserTypeName(List<CardUserType> cardUserTypes, Integer cardUserCardType) {
		for (int i = 0; i < cardUserTypes.size(); i++) {
			CardUserType cardUserType = cardUserTypes.get(i);
			if (cardUserType.getTypeCode().equals(cardUserCardType.toString())) {
				return cardUserType.getTypeName();
			}
		}
		return null;
	}
	
}
