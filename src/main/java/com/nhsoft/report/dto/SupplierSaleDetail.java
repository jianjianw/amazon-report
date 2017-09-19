package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierSaleDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4037148520631494205L;

	private Integer supplierNum;
	private Integer branchNum;
	private String branchName;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 商品规格
	private BigDecimal saleQty;// 数量
	private BigDecimal saleMoney;// 金额
	private BigDecimal saleCost;// 成本
	private BigDecimal stockCost;// 当前库存成本金额
	private String categoryCode;// 类别代码
	private String categoryName;// 类别名称
	private Date saleTime;// 销售时间
	private String saleType;// 销售方式
	private BigDecimal salePrice;// 销售价
	private BigDecimal saleCostMoney;// 原价金额
	private String saleFid;// 单据号
	private BigDecimal itemPurchaseRate;
	private BigDecimal saleMaori;// 毛利
	private BigDecimal saleMaoriRate;// 毛利率
	private String biz;//营业日

	private Integer itemNum;

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public BigDecimal getSaleMaoriRate() {
		return saleMaoriRate;
	}

	public void setSaleMaoriRate(BigDecimal saleMaoriRate) {
		this.saleMaoriRate = saleMaoriRate;
	}

	public BigDecimal getSaleMaori() {
		return saleMaori;
	}

	public void setSaleMaori(BigDecimal saleMaori) {
		this.saleMaori = saleMaori;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
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

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getStockCost() {
		return stockCost;
	}

	public void setStockCost(BigDecimal stockCost) {
		this.stockCost = stockCost;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
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

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getSaleCostMoney() {
		return saleCostMoney;
	}

	public void setSaleCostMoney(BigDecimal saleCostMoney) {
		this.saleCostMoney = saleCostMoney;
	}

	public String getSaleFid() {
		return saleFid;
	}

	public void setSaleFid(String saleFid) {
		this.saleFid = saleFid;
	}

	public BigDecimal getItemPurchaseRate() {
		return itemPurchaseRate;
	}

	public void setItemPurchaseRate(BigDecimal itemPurchaseRate) {
		this.itemPurchaseRate = itemPurchaseRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		result = prime * result + ((saleFid == null) ? 0 : saleFid.hashCode());
		result = prime * result + ((saleTime == null) ? 0 : saleTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierSaleDetail other = (SupplierSaleDetail) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (saleFid == null) {
			if (other.saleFid != null)
				return false;
		} else if (!saleFid.equals(other.saleFid))
			return false;
		if (saleTime == null) {
			if (other.saleTime != null)
				return false;
		} else if (!saleTime.equals(other.saleTime))
			return false;
		return true;
	}
}
