package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferPolicyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3821081872287781140L;
	private Integer itemNum;// 商品主键
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 规格
	private String itemUnit;// 计量单位（基本单位）
	private BigDecimal policyQty;// 调出数
	private BigDecimal policyPrice;// 平均调出价
	private BigDecimal policyStdPrice;// 平均正常调出价
	private BigDecimal policyMoney;// 调出金额
	private BigDecimal policyStdMoney;// 正常调出金额
	private BigDecimal policyDiscount;// 优惠金额

	private String policyNo;
	private Integer branchNum;
	
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
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

	public BigDecimal getPolicyQty() {
		return policyQty;
	}

	public void setPolicyQty(BigDecimal policyQty) {
		this.policyQty = policyQty;
	}

	public BigDecimal getPolicyPrice() {
		return policyPrice;
	}

	public void setPolicyPrice(BigDecimal policyPrice) {
		this.policyPrice = policyPrice;
	}

	public BigDecimal getPolicyStdPrice() {
		return policyStdPrice;
	}

	public void setPolicyStdPrice(BigDecimal policyStdPrice) {
		this.policyStdPrice = policyStdPrice;
	}

	public BigDecimal getPolicyMoney() {
		return policyMoney;
	}

	public void setPolicyMoney(BigDecimal policyMoney) {
		this.policyMoney = policyMoney;
	}

	public BigDecimal getPolicyStdMoney() {
		return policyStdMoney;
	}

	public void setPolicyStdMoney(BigDecimal policyStdMoney) {
		this.policyStdMoney = policyStdMoney;
	}

	public BigDecimal getPolicyDiscount() {
		return policyDiscount;
	}

	public void setPolicyDiscount(BigDecimal policyDiscount) {
		this.policyDiscount = policyDiscount;
	}

}
