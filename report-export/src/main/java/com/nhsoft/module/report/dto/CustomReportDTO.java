package com.nhsoft.module.report.dto;




import com.nhsoft.module.report.annotation.ReportInfo;
import com.nhsoft.module.report.annotation.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomReportDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8375082895671557596L;
	@ReportKey
	private Integer branchNum;
	@ReportInfo
	private String branchName;
	
	@ReportKey
	private Integer itemNum;
	@ReportInfo
	private String itemName;
	@ReportInfo
	private String itemCode;
	@ReportInfo
	private String itemUnit;
	@ReportInfo
	private String itemSpec;

	@ReportKey
	private String itemCategoryCode;
	@ReportInfo
	private String itemCategoryName;
	
	@ReportKey
	private String bizday;
	
	private BigDecimal saleMoney; // 销售额
	private BigDecimal saleProfit; // 毛利
	private Integer saleOrderCount; // 客单数
	private Integer saleItemCount; // 总销售商品总数
	private Integer employeeCount; // 人数
	private BigDecimal areaCount; // 面积
	private BigDecimal centerTransferMoney; // 总部配送金额
	private BigDecimal branchInMoney; // 门店调入金额
	private BigDecimal branchOutMoney; // 门店调出金额
	private BigDecimal lossMoney; // 损耗
	private Integer newCardCount; // 新会员数
	private Integer revokeCardCount; // 退卡数
	private Integer availCardCount; // 有效会员数
	private Integer cardSaleOrderCount; // 会员客单数
	private BigDecimal cardSaleConsumeMoney; // 会员消费金额
	private BigDecimal cardConsumeMoney; // 卡消费金额
	private BigDecimal cardDepositCash; //
	private BigDecimal cardDepositMoney; // 卡储值金额
	private BigDecimal topItemSaleMoney; // TOP10商品销售金额
	private BigDecimal transferTarget; // 配送目标
	private BigDecimal saleMoneyTarget; // 销售目标
	private BigDecimal saleProfitTarget; // 销售毛利目标
	private BigDecimal transferProfitTarget; // 配销差额目标
	private BigDecimal operatingExpense; // 运营费用
	private BigDecimal itemAmount; // 商品数量
	private Integer validSaleOrderCount; // 连带率大于0的客单数
	private BigDecimal adjustAmount; //损益数量
	private BigDecimal adjustMoney; //损益金额
	
	public Integer getValidSaleOrderCount() {
		return validSaleOrderCount;
	}

	public void setValidSaleOrderCount(Integer validSaleOrderCount) {
		this.validSaleOrderCount = validSaleOrderCount;
	}

	public Integer getBranchNum() {
		return branchNum;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public String getItemUnit() {
		return itemUnit;
	}
	
	public String getItemSpec() {
		return itemSpec;
	}
	
	public String getBizday() {
		return bizday;
	}
	
	public BigDecimal getSaleMoney() {
		return saleMoney;
	}
	
	public BigDecimal getSaleProfit() {
		return saleProfit;
	}
	
	public Integer getSaleOrderCount() {
		return saleOrderCount;
	}
	
	public Integer getSaleItemCount() {
		return saleItemCount;
	}
	
	public Integer getEmployeeCount() {
		return employeeCount;
	}
	
	public BigDecimal getAreaCount() {
		return areaCount;
	}
	
	public BigDecimal getCenterTransferMoney() {
		return centerTransferMoney;
	}
	
	public BigDecimal getBranchInMoney() {
		return branchInMoney;
	}
	
	public BigDecimal getBranchOutMoney() {
		return branchOutMoney;
	}
	
	public BigDecimal getLossMoney() {
		return lossMoney;
	}
	
	public Integer getNewCardCount() {
		return newCardCount;
	}
	
	public Integer getRevokeCardCount() {
		return revokeCardCount;
	}
	
	public Integer getAvailCardCount() {
		return availCardCount;
	}
	
	public Integer getCardSaleOrderCount() {
		return cardSaleOrderCount;
	}
	
	public BigDecimal getCardConsumeMoney() {
		return cardConsumeMoney;
	}
	
	public BigDecimal getCardDepositMoney() {
		return cardDepositMoney;
	}
	
	public BigDecimal getTopItemSaleMoney() {
		return topItemSaleMoney;
	}
	
	public BigDecimal getSaleMoneyTarget() {
		return saleMoneyTarget;
	}
	
	public BigDecimal getSaleProfitTarget() {
		return saleProfitTarget;
	}
	
	public BigDecimal getTransferProfitTarget() {
		return transferProfitTarget;
	}
	
	public BigDecimal getOperatingExpense() {
		return operatingExpense;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	
	public void setBizday(String bizday) {
		this.bizday = bizday;
	}
	
	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}
	
	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}
	
	public void setSaleOrderCount(Integer saleOrderCount) {
		this.saleOrderCount = saleOrderCount;
	}
	
	public void setSaleItemCount(Integer saleItemCount) {
		this.saleItemCount = saleItemCount;
	}
	
	public void setEmployeeCount(Integer employeeCount) {
		this.employeeCount = employeeCount;
	}
	
	public void setAreaCount(BigDecimal areaCount) {
		this.areaCount = areaCount;
	}
	
	public void setCenterTransferMoney(BigDecimal centerTransferMoney) {
		this.centerTransferMoney = centerTransferMoney;
	}
	
	public void setBranchInMoney(BigDecimal branchInMoney) {
		this.branchInMoney = branchInMoney;
	}
	
	public void setBranchOutMoney(BigDecimal branchOutMoney) {
		this.branchOutMoney = branchOutMoney;
	}
	
	public void setLossMoney(BigDecimal lossMoney) {
		this.lossMoney = lossMoney;
	}
	
	public void setNewCardCount(Integer newCardCount) {
		this.newCardCount = newCardCount;
	}
	
	public void setRevokeCardCount(Integer revokeCardCount) {
		this.revokeCardCount = revokeCardCount;
	}
	
	public void setAvailCardCount(Integer availCardCount) {
		this.availCardCount = availCardCount;
	}
	
	public void setCardSaleOrderCount(Integer cardSaleOrderCount) {
		this.cardSaleOrderCount = cardSaleOrderCount;
	}
	
	public void setCardConsumeMoney(BigDecimal cardConsumeMoney) {
		this.cardConsumeMoney = cardConsumeMoney;
	}
	
	public void setCardDepositMoney(BigDecimal cardDepositMoney) {
		this.cardDepositMoney = cardDepositMoney;
	}
	
	public void setTopItemSaleMoney(BigDecimal topItemSaleMoney) {
		this.topItemSaleMoney = topItemSaleMoney;
	}
	
	public void setSaleMoneyTarget(BigDecimal saleMoneyTarget) {
		this.saleMoneyTarget = saleMoneyTarget;
	}
	
	public void setSaleProfitTarget(BigDecimal saleProfitTarget) {
		this.saleProfitTarget = saleProfitTarget;
	}
	
	public void setTransferProfitTarget(BigDecimal transferProfitTarget) {
		this.transferProfitTarget = transferProfitTarget;
	}
	
	public BigDecimal getTransferTarget() {
		return transferTarget;
	}
	
	public void setTransferTarget(BigDecimal transferTarget) {
		this.transferTarget = transferTarget;
	}
	
	public void setOperatingExpense(BigDecimal operatingExpense) {
		this.operatingExpense = operatingExpense;
	}
	
	public BigDecimal getCardSaleConsumeMoney() {
		return cardSaleConsumeMoney;
	}
	
	public void setCardSaleConsumeMoney(BigDecimal cardSaleConsumeMoney) {
		this.cardSaleConsumeMoney = cardSaleConsumeMoney;
	}
	
	public BigDecimal getCardDepositCash() {
		return cardDepositCash;
	}
	
	public void setCardDepositCash(BigDecimal cardDepositCash) {
		this.cardDepositCash = cardDepositCash;
	}
	
	public BigDecimal getItemAmount() {
		return itemAmount;
	}
	
	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public BigDecimal getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(BigDecimal adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}
}
