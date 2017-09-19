package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierItemCollect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5716471086797206373L;
	
	private String fid;
	private Integer supplierNum;
	private Integer branchNum;
	private Date operateTime;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemUnit;
	private String itemBasicUnit;
	private BigDecimal itemPrice;
	private BigDecimal itemQty;
	private BigDecimal itemMoney;
	private BigDecimal itemBasicQty;
	private String itemPresentUnit;
	private BigDecimal itemPresentQty;
	
	private Integer itemNum;
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public Integer getSupplierNum() {
		return supplierNum;
	}
	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
	public Integer getBranchNum() {
		return branchNum;
	}
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
	public BigDecimal getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}
	public BigDecimal getItemQty() {
		return itemQty;
	}
	public void setItemQty(BigDecimal itemQty) {
		this.itemQty = itemQty;
	}
	public BigDecimal getItemMoney() {
		return itemMoney;
	}
	public void setItemMoney(BigDecimal itemMoney) {
		this.itemMoney = itemMoney;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public String getItemBasicUnit() {
		return itemBasicUnit;
	}
	public void setItemBasicUnit(String itemBasicUnit) {
		this.itemBasicUnit = itemBasicUnit;
	}
	public BigDecimal getItemBasicQty() {
		return itemBasicQty;
	}
	public void setItemBasicQty(BigDecimal itemBasicQty) {
		this.itemBasicQty = itemBasicQty;
	}
	public String getItemPresentUnit() {
		return itemPresentUnit;
	}
	public void setItemPresentUnit(String itemPresentUnit) {
		this.itemPresentUnit = itemPresentUnit;
	}
	public BigDecimal getItemPresentQty() {
		return itemPresentQty;
	}
	public void setItemPresentQty(BigDecimal itemPresentQty) {
		this.itemPresentQty = itemPresentQty;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fid == null) ? 0 : fid.hashCode());
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
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
		SupplierItemCollect other = (SupplierItemCollect) obj;
		if (fid == null) {
			if (other.fid != null)
				return false;
		} else if (!fid.equals(other.fid))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		return true;
	}
	
}
