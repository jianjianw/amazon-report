package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory entity. @author MyEclipse Persistence Tools
 */

public class Inventory implements java.io.Serializable {

	private static final long serialVersionUID = 7870322134326430600L;
	private InventoryId id;
	private Integer itemNum;
	private BigDecimal inventoryAmount;
	private BigDecimal inventoryMoney;
	private BigDecimal inventoryAssistAmount;
	private List<InventoryBatchDetail> inventoryBatchDetails = new ArrayList<InventoryBatchDetail>();
	private List<InventoryLnDetail> inventoryLnDetails = new ArrayList<InventoryLnDetail>();
	private List<InventoryMatrix> inventoryMatrixs = new ArrayList<InventoryMatrix>();
	
	public InventoryId getId() {
		return id;
	}

	public void setId(InventoryId id) {
		this.id = id;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getInventoryAmount() {
		return inventoryAmount;
	}

	public void setInventoryAmount(BigDecimal inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}

	public BigDecimal getInventoryMoney() {
		return inventoryMoney;
	}

	public void setInventoryMoney(BigDecimal inventoryMoney) {
		this.inventoryMoney = inventoryMoney;
	}

	public BigDecimal getInventoryAssistAmount() {
		return inventoryAssistAmount;
	}

	public void setInventoryAssistAmount(BigDecimal inventoryAssistAmount) {
		this.inventoryAssistAmount = inventoryAssistAmount;
	}

	public List<InventoryBatchDetail> getInventoryBatchDetails() {
		return inventoryBatchDetails;
	}

	public void setInventoryBatchDetails(
			List<InventoryBatchDetail> inventoryBatchDetails) {
		this.inventoryBatchDetails = inventoryBatchDetails;
	}

	public List<InventoryLnDetail> getInventoryLnDetails() {
		return inventoryLnDetails;
	}

	public void setInventoryLnDetails(List<InventoryLnDetail> inventoryLnDetails) {
		this.inventoryLnDetails = inventoryLnDetails;
	}

	public List<InventoryMatrix> getInventoryMatrixs() {
		return inventoryMatrixs;
	}

	public void setInventoryMatrixs(List<InventoryMatrix> inventoryMatrixs) {
		this.inventoryMatrixs = inventoryMatrixs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Inventory other = (Inventory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}