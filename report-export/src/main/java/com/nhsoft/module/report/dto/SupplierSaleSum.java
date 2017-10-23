package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierSaleSum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4037148520631494205L;
	private Integer supplierNum;
	private String supplierCode;// 供应商代码
	private String supplierName;// 供应商名称
	private Integer itemNum;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String itemSpec;// 商品规格
	private BigDecimal saleQty;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleCost;// 销售成本
	private BigDecimal saleMaori;// 毛利
	private BigDecimal saleMaoriRate;// 毛利率
	private BigDecimal stockQty;// 库存数量
	private BigDecimal stockCost;// 当前库存成本金额
	private String categoryCode;// 类别代码
	private String categoryName;// 类别名称
	private BigDecimal averagePrice;// 加权平均价
	private BigDecimal inPrice;// 进价
	
	private BigDecimal itemInventoryRate;
	private BigDecimal itemTransferRate;
	private BigDecimal itemWholesaleRate;
	private BigDecimal itemPurcharseRate;

	public SupplierSaleSum(){
		saleQty = BigDecimal.ZERO;
		saleMoney = BigDecimal.ZERO;
		saleCost = BigDecimal.ZERO;
		saleMaori = BigDecimal.ZERO;
	}
	
	public BigDecimal getItemPurcharseRate() {
		return itemPurcharseRate;
	}

	public void setItemPurcharseRate(BigDecimal itemPurcharseRate) {
		this.itemPurcharseRate = itemPurcharseRate;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public BigDecimal getSaleMaori() {
		return saleMaori;
	}

	public void setSaleMaori(BigDecimal saleMaori) {
		this.saleMaori = saleMaori;
	}

	public BigDecimal getSaleMaoriRate() {
		return saleMaoriRate;
	}

	public void setSaleMaoriRate(BigDecimal saleMaoriRate) {
		this.saleMaoriRate = saleMaoriRate;
	}

	public BigDecimal getStockQty() {
		return stockQty;
	}

	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
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

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getInPrice() {
		return inPrice;
	}

	public void setInPrice(BigDecimal inPrice) {
		this.inPrice = inPrice;
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

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public BigDecimal getItemTransferRate() {
		return itemTransferRate;
	}

	public void setItemTransferRate(BigDecimal itemTransferRate) {
		this.itemTransferRate = itemTransferRate;
	}

	public BigDecimal getItemWholesaleRate() {
		return itemWholesaleRate;
	}

	public void setItemWholesaleRate(BigDecimal itemWholesaleRate) {
		this.itemWholesaleRate = itemWholesaleRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result
				+ ((supplierCode == null) ? 0 : supplierCode.hashCode());
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
		SupplierSaleSum other = (SupplierSaleSum) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (supplierCode == null) {
			if (other.supplierCode != null)
				return false;
		} else if (!supplierCode.equals(other.supplierCode))
			return false;
		return true;
	}

}
