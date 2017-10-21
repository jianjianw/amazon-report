package com.nhsoft.module.report.dto;




import com.nhsoft.module.report.query.WeixinPosItemQuery;

import java.io.Serializable;
import java.math.BigDecimal;

public class WeixinPosItemMultChangeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5465939102267200330L;

	private WeixinPosItemQuery weixinIQuery;
	
	private Integer changeType;
	
	private String weixinItemCategoryCode;
	private String weixinItemCategory;
	private String weixinItemSpec;
	private String weixinItemUnit;
	private Integer weixinItemStatus;
	private BigDecimal weixinItemInventory;
	private BigDecimal weixinItemInitSaleQty;
	private String weixinItemSupportPay;
	private Boolean weixinItemToped;
	private Boolean weixinItemTeamed;
	private Integer weixinItemTeamCount;
	private Integer weixinItemTeamHour;
	private String weixinItemTeamSupportPay;
	private String weixinItemTeamSupportDelivery;
	public WeixinPosItemQuery getWeixinIQuery() {
		return weixinIQuery;
	}
	public void setWeixinIQuery(WeixinPosItemQuery weixinIQuery) {
		this.weixinIQuery = weixinIQuery;
	}
	public Integer getChangeType() {
		return changeType;
	}
	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	public String getWeixinItemCategoryCode() {
		return weixinItemCategoryCode;
	}
	public void setWeixinItemCategoryCode(String weixinItemCategoryCode) {
		this.weixinItemCategoryCode = weixinItemCategoryCode;
	}
	public String getWeixinItemCategory() {
		return weixinItemCategory;
	}
	public void setWeixinItemCategory(String weixinItemCategory) {
		this.weixinItemCategory = weixinItemCategory;
	}
	public String getWeixinItemSpec() {
		return weixinItemSpec;
	}
	public void setWeixinItemSpec(String weixinItemSpec) {
		this.weixinItemSpec = weixinItemSpec;
	}
	public String getWeixinItemUnit() {
		return weixinItemUnit;
	}
	public void setWeixinItemUnit(String weixinItemUnit) {
		this.weixinItemUnit = weixinItemUnit;
	}
	public Integer getWeixinItemStatus() {
		return weixinItemStatus;
	}
	public void setWeixinItemStatus(Integer weixinItemStatus) {
		this.weixinItemStatus = weixinItemStatus;
	}
	public BigDecimal getWeixinItemInventory() {
		return weixinItemInventory;
	}
	public void setWeixinItemInventory(BigDecimal weixinItemInventory) {
		this.weixinItemInventory = weixinItemInventory;
	}
	public BigDecimal getWeixinItemInitSaleQty() {
		return weixinItemInitSaleQty;
	}
	public void setWeixinItemInitSaleQty(BigDecimal weixinItemInitSaleQty) {
		this.weixinItemInitSaleQty = weixinItemInitSaleQty;
	}
	public String getWeixinItemSupportPay() {
		return weixinItemSupportPay;
	}
	public void setWeixinItemSupportPay(String weixinItemSupportPay) {
		this.weixinItemSupportPay = weixinItemSupportPay;
	}
	public Boolean getWeixinItemToped() {
		return weixinItemToped;
	}
	public void setWeixinItemToped(Boolean weixinItemToped) {
		this.weixinItemToped = weixinItemToped;
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
	public Integer getWeixinItemTeamHour() {
		return weixinItemTeamHour;
	}
	public void setWeixinItemTeamHour(Integer weixinItemTeamHour) {
		this.weixinItemTeamHour = weixinItemTeamHour;
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
	public void setWeixinItemTeamSupportDelivery(
			String weixinItemTeamSupportDelivery) {
		this.weixinItemTeamSupportDelivery = weixinItemTeamSupportDelivery;
	}
	
	
}
