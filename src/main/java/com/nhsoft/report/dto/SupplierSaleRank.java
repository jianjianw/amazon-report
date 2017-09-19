package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierSaleRank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2878917132124984383L;

	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private BigDecimal saleMoney;
	private BigDecimal saleQty;
	private BigDecimal saleMaori;
	private BigDecimal saleCost;

	public SupplierSaleRank() {
		saleQty = BigDecimal.ZERO;
		saleMoney = BigDecimal.ZERO;
		saleCost = BigDecimal.ZERO;
		saleMaori = BigDecimal.ZERO;
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

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

	public BigDecimal getSaleMaori() {
		return saleMaori;
	}

	public void setSaleMaori(BigDecimal saleMaori) {
		this.saleMaori = saleMaori;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
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
		SupplierSaleRank other = (SupplierSaleRank) obj;
		if (supplierNum == null) {
			if (other.supplierNum != null)
				return false;
		} else if (!supplierNum.equals(other.supplierNum))
			return false;
		return true;
	}

}
