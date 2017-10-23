package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OnlineOrderSaleAnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7778826199004558095L;
	private Integer branchNum;// 门店编号
	private String branchCode;// 门店代码
	private String branchName;// 门店名称
	private Integer itemNum;// 商品编号
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private BigDecimal saleQty;// 销售数量
	private BigDecimal salePrice;// 销售单价
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleSendRate;// 提货比例（提货完成/下单成功）

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

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

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
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

	public BigDecimal getSaleSendRate() {
		return saleSendRate;
	}

	public void setSaleSendRate(BigDecimal saleSendRate) {
		this.saleSendRate = saleSendRate;
	}

}
