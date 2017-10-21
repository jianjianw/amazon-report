package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SingularPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7039131865248461881L;

	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemCategoryCode;
	private String itemCategory;
	private BigDecimal itemTransfer;
	private String inventoryUnit;
	private BigDecimal inventoryUseAmount; // 库存数量
	private BigDecimal itemLogPrice; // 最近进价
	private BigDecimal maxPrice;
	private BigDecimal minPrice;
	private BigDecimal rate;

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

	public BigDecimal getItemTransfer() {
		return itemTransfer;
	}

	public void setItemTransfer(BigDecimal itemTransfer) {
		this.itemTransfer = itemTransfer;
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

	public BigDecimal getItemLogPrice() {
		return itemLogPrice;
	}

	public void setItemLogPrice(BigDecimal itemLogPrice) {
		this.itemLogPrice = itemLogPrice;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
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
		SingularPrice other = (SingularPrice) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}

}
