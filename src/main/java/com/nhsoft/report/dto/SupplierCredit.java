package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierCredit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051147525388641212L;

	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private BigDecimal stockInQty;
	private BigDecimal stockInMoney;
	private BigDecimal saleQty;
	private BigDecimal saleMoney;
	private BigDecimal saleMaori;
	private BigDecimal saleMaoriRate;

	private BigDecimal itemInventoryRate;
	private BigDecimal itemPurcharseRate;

	public SupplierCredit() {
		setStockInQty(BigDecimal.ZERO);
		setStockInMoney(BigDecimal.ZERO);
		setSaleQty(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
		setSaleMaori(BigDecimal.ZERO);
		setSaleMaoriRate(BigDecimal.ZERO);
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
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

	public BigDecimal getStockInQty() {
		return stockInQty;
	}

	public void setStockInQty(BigDecimal stockInQty) {
		this.stockInQty = stockInQty;
	}

	public BigDecimal getStockInMoney() {
		return stockInMoney;
	}

	public void setStockInMoney(BigDecimal stockInMoney) {
		this.stockInMoney = stockInMoney;
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

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public BigDecimal getItemPurcharseRate() {
		return itemPurcharseRate;
	}

	public void setItemPurcharseRate(BigDecimal itemPurcharseRate) {
		this.itemPurcharseRate = itemPurcharseRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((supplierNum == null) ? 0 : supplierNum.hashCode());
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
		SupplierCredit other = (SupplierCredit) obj;
		if (supplierNum == null) {
			if (other.supplierNum != null)
				return false;
		} else if (!supplierNum.equals(other.supplierNum))
			return false;
		return true;
	}

}
