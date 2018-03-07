package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RetailDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1683550297544583485L;
	private String orderNo; // 单据号
	private String posItemCode; // 项目代码
	private String posItemName; // 项目名称
	private String posItemSpec; // 项目规格
	private BigDecimal amount; // 数量
	private BigDecimal salePrice; // 实际零售价
	private BigDecimal saleMoney; // 实际销售额
	private BigDecimal discountMoney; // 折扣金额
	private Integer branchNum; // 分店编号
	private String branchName; // 分店名称
	private String cashier; // 收银员
	private String posMachine; // 收款机
	private Date posTime; // 收款时间
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Integer stateCode;//销售类型
	private String itemUnit;//销售单位
	private String saler; // 销售员
	private Integer itemGradeNum;  //分级商品
	private BigDecimal saleCommission; // 提成
	private String memo;// 
	private BigDecimal saleCost;
	private BigDecimal saleProfit;
	private String itemBarCode;

	private Integer merchantNum;
	private Integer stallNum;


	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(BigDecimal saleCommission) {
		this.saleCommission = saleCommission;
	}

	public Integer getItemGradeNum() {
		return itemGradeNum;
	}

	public void setItemGradeNum(Integer itemGradeNum) {
		this.itemGradeNum = itemGradeNum;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}

	public String getPosItemName() {
		return posItemName;
	}

	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}

	public String getPosItemSpec() {
		return posItemSpec;
	}

	public void setPosItemSpec(String posItemSpec) {
		this.posItemSpec = posItemSpec;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCashier() {
		return cashier;
	}

	public void setCashier(String cashier) {
		this.cashier = cashier;
	}

	public String getPosMachine() {
		return posMachine;
	}

	public void setPosMachine(String posMachine) {
		this.posMachine = posMachine;
	}

	public Date getPosTime() {
		return posTime;
	}

	public void setPosTime(Date posTime) {
		this.posTime = posTime;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public String getItemBarCode() {
		return itemBarCode;
	}

	public void setItemBarCode(String itemBarCode) {
		this.itemBarCode = itemBarCode;
	}

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}
}
