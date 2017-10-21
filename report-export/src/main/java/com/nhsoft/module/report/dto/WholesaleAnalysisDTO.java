package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WholesaleAnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1137892805015232108L;
	private Integer itemNum;// 商品编号
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 商品规格
	private String itemUnit;// 计量单位
	private String itemCategory;//商品类别
	private BigDecimal itemStoreQty;//库存数量
	private BigDecimal wholesaleRate;// 购(进)货频率
	private BigDecimal itemTotalQty;// 累计数量
	private int saleDays;// 已卖天数
	private Date firstSaleDate;// 首次销售日期
	private Date lastWholesaleDate;// 最后购货日期
	private int unWholesaleDays;// 未购货天数
	private BigDecimal twoWeekQty;// 平均2星期销量（爆款商品用到）
	private BigDecimal itemTotalMoney;// 金额（利润商品用到）
	private BigDecimal itemTotalProfit;// 利润 建议零售价（利润商品用到）
	private BigDecimal everyMonthProfit;// 每月 利润 /天数 * 30（利润商品用到）

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public BigDecimal getWholesaleRate() {
		return wholesaleRate;
	}

	public void setWholesaleRate(BigDecimal wholesaleRate) {
		this.wholesaleRate = wholesaleRate;
	}

	public BigDecimal getItemTotalQty() {
		return itemTotalQty;
	}

	public void setItemTotalQty(BigDecimal itemTotalQty) {
		this.itemTotalQty = itemTotalQty;
	}

	public int getSaleDays() {
		return saleDays;
	}

	public void setSaleDays(int saleDays) {
		this.saleDays = saleDays;
	}

	public Date getFirstSaleDate() {
		return firstSaleDate;
	}

	public void setFirstSaleDate(Date firstSaleDate) {
		this.firstSaleDate = firstSaleDate;
	}

	public Date getLastWholesaleDate() {
		return lastWholesaleDate;
	}

	public void setLastWholesaleDate(Date lastWholesaleDate) {
		this.lastWholesaleDate = lastWholesaleDate;
	}

	public int getUnWholesaleDays() {
		return unWholesaleDays;
	}

	public void setUnWholesaleDays(int unWholesaleDays) {
		this.unWholesaleDays = unWholesaleDays;
	}

	public BigDecimal getTwoWeekQty() {
		return twoWeekQty;
	}

	public void setTwoWeekQty(BigDecimal twoWeekQty) {
		this.twoWeekQty = twoWeekQty;
	}

	public BigDecimal getItemTotalMoney() {
		return itemTotalMoney;
	}

	public void setItemTotalMoney(BigDecimal itemTotalMoney) {
		this.itemTotalMoney = itemTotalMoney;
	}

	public BigDecimal getItemTotalProfit() {
		return itemTotalProfit;
	}

	public void setItemTotalProfit(BigDecimal itemTotalProfit) {
		this.itemTotalProfit = itemTotalProfit;
	}

	public BigDecimal getEveryMonthProfit() {
		return everyMonthProfit;
	}

	public void setEveryMonthProfit(BigDecimal everyMonthProfit) {
		this.everyMonthProfit = everyMonthProfit;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public BigDecimal getItemStoreQty() {
		return itemStoreQty;
	}

	public void setItemStoreQty(BigDecimal itemStoreQty) {
		this.itemStoreQty = itemStoreQty;
	}

}
