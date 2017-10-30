package com.nhsoft.module.report.model;

import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * WeixinPosItem generated by hbm2java
 */
@Entity
public class WeixinPosItem implements java.io.Serializable {

	/**
	 * 
	 */
	@Transient
	private static Logger logger = LoggerFactory.getLogger(WeixinPosItem.class);
	private static final long serialVersionUID = -7155184074587788864L;
	@Id
	private int weixinItemNum;
	private int itemNum;
	private String systemBookCode;
	private String weixinItemName;
	private String weixinItemUnit;
	private BigDecimal weixinItemRate;
	private BigDecimal weixinItemSalePrice;
	private String weixinItemCategory;
	private Integer weixinItemStatus;
	private String weixinItemSpec;
	private Boolean weixinItemToped;
	private Boolean weixinItemTeamed; // 是否支持团购
	private Integer weixinItemTeamCount;// 团购人数
	private BigDecimal weixinItemTeamPrice;// 团购价
	private BigDecimal weixinItemInitSaleQty;// 初始销量
	private Date weixinItemSaleTime;// 上架时间
	private String weixinItemTeamSupportPay;// 拼团指定支付方式
	private String weixinItemTeamSupportDelivery;// 拼团指定物流
	private BigDecimal weixinItemSaleQty; // 实时销量 件
	private BigDecimal weixinItemInventory;
	private String weixinItemCategoryCode;
	private String weixinItemSupportPay;
	private BigDecimal weixinItemRefPrice;
	private Integer weixinItemTeamHour;
	private Integer weixinItemSequence;
	private Boolean weixinItemTeamPlaned;
	private Date weixinItemTeamStart;
	private Date weixinItemTeamEnd;
	private Date weixinItemLastEditTime;
	private String weixinItemTeamShops;
	private Boolean weixinItemTeamSelf;
	private Date weixinItemTeamDeadline;
	private Integer weixinItemTeamDay;
	private String weixinItemMemo;
	private BigDecimal weixinItemVipPrice;
	private Boolean weixinItemTeamAutoRefund;
	private BigDecimal weixinItemTeamVipPrice;
	private Integer weixinItemBatch;
	private Boolean weixinItemTeamOnce;
	private Integer weixinItemTeamLimit;
	private Integer weixinItemTeamLimitExist;

	@Transient
	private PosItem posItem;
	@Transient
	private WeixinItemDetail weixinItemDetail;
	@Transient
	private AppUser appUser;
	@Transient
	private String ossImageUrl;

	public WeixinPosItem() {
	}

	public Integer getWeixinItemTeamLimitExist() {
		return weixinItemTeamLimitExist;
	}

	public void setWeixinItemTeamLimitExist(Integer weixinItemTeamLimitExist) {
		this.weixinItemTeamLimitExist = weixinItemTeamLimitExist;
	}

	public BigDecimal getWeixinItemTeamVipPrice() {
		return weixinItemTeamVipPrice;
	}

	public void setWeixinItemTeamVipPrice(BigDecimal weixinItemTeamVipPrice) {
		this.weixinItemTeamVipPrice = weixinItemTeamVipPrice;
	}

	public Boolean getWeixinItemTeamAutoRefund() {
		return weixinItemTeamAutoRefund;
	}

	public void setWeixinItemTeamAutoRefund(Boolean weixinItemTeamAutoRefund) {
		this.weixinItemTeamAutoRefund = weixinItemTeamAutoRefund;
	}

	public BigDecimal getWeixinItemVipPrice() {
		return weixinItemVipPrice;
	}

	public void setWeixinItemVipPrice(BigDecimal weixinItemVipPrice) {
		this.weixinItemVipPrice = weixinItemVipPrice;
	}

	public String getWeixinItemMemo() {
		return weixinItemMemo;
	}

	public void setWeixinItemMemo(String weixinItemMemo) {
		this.weixinItemMemo = weixinItemMemo;
	}

	public String getOssImageUrl() {
		return ossImageUrl;
	}

	public void setOssImageUrl(String ossImageUrl) {
		this.ossImageUrl = ossImageUrl;
	}

	public Integer getWeixinItemTeamDay() {
		return weixinItemTeamDay;
	}

	public void setWeixinItemTeamDay(Integer weixinItemTeamDay) {
		this.weixinItemTeamDay = weixinItemTeamDay;
	}

	public Date getWeixinItemTeamDeadline() {
		return weixinItemTeamDeadline;
	}

	public void setWeixinItemTeamDeadline(Date weixinItemTeamDeadline) {
		this.weixinItemTeamDeadline = weixinItemTeamDeadline;
	}

	public Boolean getWeixinItemTeamSelf() {
		return weixinItemTeamSelf;
	}

	public void setWeixinItemTeamSelf(Boolean weixinItemTeamSelf) {
		this.weixinItemTeamSelf = weixinItemTeamSelf;
	}

	public String getWeixinItemTeamShops() {
		return weixinItemTeamShops;
	}

	public void setWeixinItemTeamShops(String weixinItemTeamShops) {
		this.weixinItemTeamShops = weixinItemTeamShops;
	}

	public Integer getWeixinItemSequence() {
		return weixinItemSequence;
	}

	public void setWeixinItemSequence(Integer weixinItemSequence) {
		this.weixinItemSequence = weixinItemSequence;
	}

	public Integer getWeixinItemTeamHour() {
		return weixinItemTeamHour;
	}

	public void setWeixinItemTeamHour(Integer weixinItemTeamHour) {
		this.weixinItemTeamHour = weixinItemTeamHour;
	}

	public BigDecimal getWeixinItemRefPrice() {
		return weixinItemRefPrice;
	}

	public void setWeixinItemRefPrice(BigDecimal weixinItemRefPrice) {
		this.weixinItemRefPrice = weixinItemRefPrice;
	}

	public String getWeixinItemSupportPay() {
		return weixinItemSupportPay;
	}

	public void setWeixinItemSupportPay(String weixinItemSupportPay) {
		this.weixinItemSupportPay = weixinItemSupportPay;
	}

	public BigDecimal getWeixinItemInventory() {
		return weixinItemInventory;
	}

	public void setWeixinItemInventory(BigDecimal weixinItemInventory) {
		this.weixinItemInventory = weixinItemInventory;
	}

	public WeixinItemDetail getWeixinItemDetail() {
		return weixinItemDetail;
	}

	public void setWeixinItemDetail(WeixinItemDetail weixinItemDetail) {
		this.weixinItemDetail = weixinItemDetail;
	}

	public Boolean getWeixinItemTeamed() {
		return weixinItemTeamed;
	}

	public void setWeixinItemTeamed(Boolean weixinItemTeamed) {
		this.weixinItemTeamed = weixinItemTeamed;
	}

	public Integer getWeixinItemTeamCount() {
		return weixinItemTeamCount;
	}

	public void setWeixinItemTeamCount(Integer weixinItemTeamCount) {
		this.weixinItemTeamCount = weixinItemTeamCount;
	}

	public BigDecimal getWeixinItemTeamPrice() {
		return weixinItemTeamPrice;
	}

	public void setWeixinItemTeamPrice(BigDecimal weixinItemTeamPrice) {
		this.weixinItemTeamPrice = weixinItemTeamPrice;
	}

	public BigDecimal getWeixinItemInitSaleQty() {
		return weixinItemInitSaleQty;
	}

	public void setWeixinItemInitSaleQty(BigDecimal weixinItemInitSaleQty) {
		this.weixinItemInitSaleQty = weixinItemInitSaleQty;
	}

	public Date getWeixinItemSaleTime() {
		return weixinItemSaleTime;
	}

	public void setWeixinItemSaleTime(Date weixinItemSaleTime) {
		this.weixinItemSaleTime = weixinItemSaleTime;
	}

	public String getWeixinItemTeamSupportPay() {
		return weixinItemTeamSupportPay;
	}

	public void setWeixinItemTeamSupportPay(String weixinItemTeamSupportPay) {
		this.weixinItemTeamSupportPay = weixinItemTeamSupportPay;
	}

	public String getWeixinItemTeamSupportDelivery() {
		return weixinItemTeamSupportDelivery;
	}

	public void setWeixinItemTeamSupportDelivery(String weixinItemTeamSupportDelivery) {
		this.weixinItemTeamSupportDelivery = weixinItemTeamSupportDelivery;
	}

	public BigDecimal getWeixinItemSaleQty() {
		return weixinItemSaleQty;
	}

	public void setWeixinItemSaleQty(BigDecimal weixinItemSaleQty) {
		this.weixinItemSaleQty = weixinItemSaleQty;
	}

	public Boolean getWeixinItemToped() {
		return weixinItemToped;
	}

	public void setWeixinItemToped(Boolean weixinItemToped) {
		this.weixinItemToped = weixinItemToped;
	}

	public String getWeixinItemSpec() {
		return weixinItemSpec;
	}

	public void setWeixinItemSpec(String weixinItemSpec) {
		this.weixinItemSpec = weixinItemSpec;
	}

	public int getWeixinItemNum() {
		return weixinItemNum;
	}

	public void setWeixinItemNum(int weixinItemNum) {
		this.weixinItemNum = weixinItemNum;
	}

	public String getWeixinItemName() {
		return weixinItemName;
	}

	public void setWeixinItemName(String weixinItemName) {
		this.weixinItemName = weixinItemName;
	}

	public String getWeixinItemUnit() {
		return weixinItemUnit;
	}

	public void setWeixinItemUnit(String weixinItemUnit) {
		this.weixinItemUnit = weixinItemUnit;
	}

	public BigDecimal getWeixinItemRate() {
		return weixinItemRate;
	}

	public void setWeixinItemRate(BigDecimal weixinItemRate) {
		this.weixinItemRate = weixinItemRate;
	}

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}

	public WeixinPosItem(int itemNum) {
		this.itemNum = itemNum;
	}

	public int getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public BigDecimal getWeixinItemSalePrice() {
		return this.weixinItemSalePrice;
	}

	public void setWeixinItemSalePrice(BigDecimal weixinItemSalePrice) {
		this.weixinItemSalePrice = weixinItemSalePrice;
	}

	public String getWeixinItemCategory() {
		return this.weixinItemCategory;
	}

	public void setWeixinItemCategory(String weixinItemCategory) {
		this.weixinItemCategory = weixinItemCategory;
	}

	public Integer getWeixinItemStatus() {
		return this.weixinItemStatus;
	}

	public void setWeixinItemStatus(Integer weixinItemStatus) {
		this.weixinItemStatus = weixinItemStatus;
	}

	public String getWeixinItemCategoryCode() {
		return weixinItemCategoryCode;
	}

	public void setWeixinItemCategoryCode(String weixinItemCategoryCode) {
		this.weixinItemCategoryCode = weixinItemCategoryCode;
	}
	
	public Date getWeixinItemLastEditTime() {
		return weixinItemLastEditTime;
	}

	public void setWeixinItemLastEditTime(Date weixinItemLastEditTime) {
		this.weixinItemLastEditTime = weixinItemLastEditTime;
	}

	public Boolean getWeixinItemTeamPlaned() {
		return weixinItemTeamPlaned;
	}

	public void setWeixinItemTeamPlaned(Boolean weixinItemTeamPlaned) {
		this.weixinItemTeamPlaned = weixinItemTeamPlaned;
	}

	public Date getWeixinItemTeamStart() {
		return weixinItemTeamStart;
	}

	public void setWeixinItemTeamStart(Date weixinItemTeamStart) {
		this.weixinItemTeamStart = weixinItemTeamStart;
	}

	public Date getWeixinItemTeamEnd() {
		return weixinItemTeamEnd;
	}

	public void setWeixinItemTeamEnd(Date weixinItemTeamEnd) {
		this.weixinItemTeamEnd = weixinItemTeamEnd;
	}

	public Integer getWeixinItemBatch() {
		return weixinItemBatch;
	}

	public void setWeixinItemBatch(Integer weixinItemBatch) {
		this.weixinItemBatch = weixinItemBatch;
	}
	
	public Boolean getWeixinItemTeamOnce() {
		return weixinItemTeamOnce;
	}

	public void setWeixinItemTeamOnce(Boolean weixinItemTeamOnce) {
		this.weixinItemTeamOnce = weixinItemTeamOnce;
	}

	public Integer getWeixinItemTeamLimit() {
		return weixinItemTeamLimit;
	}

	public void setWeixinItemTeamLimit(Integer weixinItemTeamLimit) {
		this.weixinItemTeamLimit = weixinItemTeamLimit;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public static WeixinPosItem getWeixinPosItem(List<WeixinPosItem> list, Integer weixinItemNum){
		if(weixinItemNum == null || list == null){
			return null;
		}
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getWeixinItemNum() == weixinItemNum){
				return list.get(i);
			}
		}
		return null;
	}
	
	public String writeToXml(){
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("WEIXIN_POS_ITEM_LIST");
		Element element = root.addElement("WEIXIN_POS_ITEM");
		
		element.addElement("WEIXIN_ITEM_NUM").setText(weixinItemNum+"");
		
		element.addElement("SYSTEM_BOOK_CODE").setText(systemBookCode);
		Element subElement = element.addElement("WEIXIN_ITEM_NAME");
		if(weixinItemName != null){
			subElement.setText(weixinItemName);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_UNIT");
		if(weixinItemUnit != null){
			subElement.addText(weixinItemUnit);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_RATE");
		if(weixinItemRate != null){
			subElement.addText(weixinItemRate+"");
		}
		
		subElement = element.addElement("WEIXIN_ITEM_SALE_PRICE");
		if(weixinItemSalePrice != null){
			subElement.addText(weixinItemSalePrice.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_CATEGORY");
		if(weixinItemCategory != null){
			subElement.addText(weixinItemCategory);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_STATUS");	
		if(weixinItemStatus != null){
			subElement.addText(weixinItemStatus.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_SPEC");
		if(weixinItemSpec != null){
			subElement.addText(weixinItemSpec);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TOPED");
		if(weixinItemToped != null){
			subElement.addText(BooleanUtils.toString(weixinItemToped, "1", "0"));
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAMED");
		if(weixinItemTeamed != null){
			subElement.addText(BooleanUtils.toString(weixinItemTeamed, "1", "0"));
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_COUNT");
		if(weixinItemTeamCount != null){
			subElement.addText(weixinItemTeamCount.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_PRICE");
		if(weixinItemTeamPrice != null){
			subElement.addText(weixinItemTeamPrice.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_INIT_SALE_QTY");
		if(weixinItemInitSaleQty != null){
			subElement.addText(weixinItemInitSaleQty.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_SALE_TIME");
		if(weixinItemSaleTime != null){
			subElement.addText(DateUtil.getXmlTString(weixinItemSaleTime));
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_SUPPORT_PAY");
		if(weixinItemTeamSupportPay != null){
			subElement.addText(weixinItemTeamSupportPay);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_SUPPORT_DELIVERY");
		if(weixinItemTeamSupportDelivery != null){
			subElement.addText(weixinItemTeamSupportDelivery);
		} 
		
		subElement = element.addElement("WEIXIN_ITEM_SALE_QTY");
		if(weixinItemSaleQty != null){
			subElement.addText(weixinItemSaleQty.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_INVENTORY");
		if(weixinItemInventory != null){
			subElement.addText(weixinItemInventory.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_CATEGORY_CODE");
		if(weixinItemCategoryCode != null){
			subElement.addText(weixinItemCategoryCode);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_SUPPORT_PAY");
		if(weixinItemSupportPay != null){
			subElement.addText(weixinItemSupportPay);
		}
		
		subElement = element.addElement("WEIXIN_ITEM_REF_PRICE");
		if(weixinItemRefPrice != null){
			subElement.addText(weixinItemRefPrice.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_HOUR");
		if(weixinItemTeamHour != null){
			subElement.addText(weixinItemTeamHour.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_SEQUENCE");
		if(weixinItemSequence != null){
			subElement.addText(weixinItemSequence.toString());
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_PLANED");
		if(weixinItemTeamPlaned != null){
			subElement.addText(BooleanUtils.toString(weixinItemTeamPlaned, "1", "0"));
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_START");
		if(weixinItemTeamStart != null){
			subElement.addText(DateUtil.getXmlTString(weixinItemTeamStart));
		}
		
		subElement = element.addElement("WEIXIN_ITEM_TEAM_END");
		if(weixinItemTeamEnd != null){
			subElement.addText(DateUtil.getXmlTString(weixinItemTeamEnd));
		}
		subElement = element.addElement("WEIXIN_ITEM_TEAM_DAY");
		if(weixinItemTeamDay != null){
			subElement.addText(weixinItemTeamDay.toString());
		}
		subElement = element.addElement("WEIXIN_ITEM_TEAM_ONCE");
		if(weixinItemTeamOnce != null){
			subElement.addText(BooleanUtils.toString(weixinItemTeamOnce, "1", "0"));
		}
		if(weixinItemBatch != null){
			subElement = element.addElement("WEIXIN_ITEM_BATCH");
			subElement.addText(weixinItemBatch.toString());
		}
		if(weixinItemTeamLimit != null){
			subElement = element.addElement("WEIXIN_ITEM_TEAM_LIMIT");
			subElement.addText(weixinItemTeamLimit.toString());
		}
		return document.asXML();
	}
	
	public static WeixinPosItem readFromXml(String text){	
		if(StringUtils.isEmpty(text)){
			return null;
		}
		try {
			WeixinPosItem weixinPosItem = new WeixinPosItem();
			Document document = DocumentHelper.parseText(text);
			Element root = document.getRootElement();
			Element element = (Element) root.selectSingleNode("WEIXIN_POS_ITEM");
			if(element != null){
				Element subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_NUM");
				if(subElement != null){
					weixinPosItem.setWeixinItemNum(Integer.parseInt(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("SYSTEM_BOOK_CODE");
				if(subElement != null){
					weixinPosItem.setSystemBookCode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_NAME");
				if(subElement != null){
					weixinPosItem.setWeixinItemName(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_UNIT");
				if(subElement != null){
					weixinPosItem.setWeixinItemUnit(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_RATE");
				if(subElement != null){
					weixinPosItem.setWeixinItemRate(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_SALE_PRICE");
				if(subElement != null){
					weixinPosItem.setWeixinItemSalePrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_CATEGORY");
				if(subElement != null){
					weixinPosItem.setWeixinItemCategory(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TOPED");
				if(subElement != null){
					weixinPosItem.setWeixinItemToped(AppUtil.strToBool(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_INIT_SALE_QTY");
				if(subElement != null){
					weixinPosItem.setWeixinItemInitSaleQty(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_INVENTORY");
				if(subElement != null){
					weixinPosItem.setWeixinItemInventory(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_CATEGORY_CODE");
				if(subElement != null){
					weixinPosItem.setWeixinItemCategoryCode(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_SUPPORT_PAY");
				if(subElement != null){
					weixinPosItem.setWeixinItemSupportPay(subElement.getText());
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_REF_PRICE");
				if(subElement != null){
					weixinPosItem.setWeixinItemRefPrice(new BigDecimal(subElement.getText()));
				}
				
				subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAMED");
				if(subElement != null && !subElement.getText().equals("")){
					weixinPosItem.setWeixinItemTeamed(AppUtil.strToBool(subElement.getText()));
				}
				
				if(weixinPosItem.getWeixinItemTeamed()){
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_COUNT");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamCount(Integer.parseInt(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_PRICE");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamPrice(new BigDecimal(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_SUPPORT_PAY");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamSupportPay(subElement.getText());
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_SUPPORT_DELIVERY");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamSupportDelivery(subElement.getText());
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_HOUR");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamHour(Integer.parseInt(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_PLANED");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamPlaned(AppUtil.strToBool(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_START");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamStart(DateUtil.getXmlTDate(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_END");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamEnd(DateUtil.getXmlTDate(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_DAY");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamDay(Integer.parseInt(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_ONCE");
					if(subElement != null){
						weixinPosItem.setWeixinItemTeamOnce(AppUtil.strToBool(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_BATCH");
					if(subElement != null && !subElement.getText().isEmpty()){
						weixinPosItem.setWeixinItemBatch(Integer.parseInt(subElement.getText()));
					}
					
					subElement = (Element) element.selectSingleNode("WEIXIN_ITEM_TEAM_LIMIT");
					if(subElement != null && !subElement.getText().isEmpty()){
						weixinPosItem.setWeixinItemTeamLimit(Integer.parseInt(subElement.getText()));
					}
				}
			}	
			
			
			return weixinPosItem;
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}				
		return null;
	}

}
