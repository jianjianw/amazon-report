package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExceptInventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7663974091171942471L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemCategoryCode;
	private String itemCategory;
	private String inventoryUnit;
	private BigDecimal inventoryUseAmount; // 库存数量
	private BigDecimal inventoryMoney; // 库存金额
	private BigDecimal inventoryRegular; // 零售价
	private Date inventoryDate; // 出库日期  进货日期
	private Integer itemValidPeriod; //有效天数
	private Boolean stockCrease; //停购标记
	private Boolean itemEliminativeFlag;
	private BigDecimal rate;
	private Boolean saleCease; //停售标记
	
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

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getInventoryUnit() {
		return inventoryUnit;
	}

	public void setInventoryUnit(String inventoryUnit) {
		this.inventoryUnit = inventoryUnit;
	}

	public BigDecimal getInventoryUseAmount() {
		return inventoryUseAmount;
	}

	public void setInventoryUseAmount(BigDecimal inventoryUseAmount) {
		this.inventoryUseAmount = inventoryUseAmount;
	}

	public BigDecimal getInventoryMoney() {
		return inventoryMoney;
	}

	public void setInventoryMoney(BigDecimal inventoryMoney) {
		this.inventoryMoney = inventoryMoney;
	}

	public BigDecimal getInventoryRegular() {
		return inventoryRegular;
	}

	public void setInventoryRegular(BigDecimal inventoryRegular) {
		this.inventoryRegular = inventoryRegular;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}
	
	public Integer getItemValidPeriod() {
		return itemValidPeriod;
	}

	public void setItemValidPeriod(Integer itemValidPeriod) {
		this.itemValidPeriod = itemValidPeriod;
	}
	
	public Boolean getStockCrease() {
		return stockCrease;
	}

	public void setStockCrease(Boolean stockCrease) {
		this.stockCrease = stockCrease;
	}
	
	public Boolean getItemEliminativeFlag() {
		return itemEliminativeFlag;
	}

	public void setItemEliminativeFlag(Boolean itemEliminativeFlag) {
		this.itemEliminativeFlag = itemEliminativeFlag;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Boolean getSaleCease() {
		return saleCease;
	}

	public void setSaleCease(Boolean saleCease) {
		this.saleCease = saleCease;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
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
		ExceptInventory other = (ExceptInventory) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		return true;
	}
}
