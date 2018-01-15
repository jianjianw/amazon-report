package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Inventory entity. @author MyEclipse Persistence Tools
 */

public class InventoryDTO implements java.io.Serializable {

	private static final long serialVersionUID = 7870322134326430600L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer itemNum;
	private BigDecimal inventoryAmount;
	private BigDecimal inventoryMoney;
	private BigDecimal inventoryAssistAmount;
	private List<InventoryBatchDetailDTO> inventoryBatchDetails = new ArrayList<InventoryBatchDetailDTO>();
	private List<InventoryLnDetailDTO> inventoryLnDetails = new ArrayList<InventoryLnDetailDTO>();
	private List<InventoryMatrixDTO> inventoryMatrixs = new ArrayList<InventoryMatrixDTO>();
	
	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
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

	public List<InventoryBatchDetailDTO> getInventoryBatchDetails() {
		return inventoryBatchDetails;
	}

	public void setInventoryBatchDetails(
			List<InventoryBatchDetailDTO> inventoryBatchDetails) {
		this.inventoryBatchDetails = inventoryBatchDetails;
	}

	public List<InventoryLnDetailDTO> getInventoryLnDetails() {
		return inventoryLnDetails;
	}

	public void setInventoryLnDetails(List<InventoryLnDetailDTO> inventoryLnDetails) {
		this.inventoryLnDetails = inventoryLnDetails;
	}

	public List<InventoryMatrixDTO> getInventoryMatrixs() {
		return inventoryMatrixs;
	}

	public void setInventoryMatrixs(List<InventoryMatrixDTO> inventoryMatrixs) {
		this.inventoryMatrixs = inventoryMatrixs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		InventoryDTO that = (InventoryDTO) o;
		
		if (storehouseNum != null ? !storehouseNum.equals(that.storehouseNum) : that.storehouseNum != null)
			return false;
		return inventoryNum != null ? inventoryNum.equals(that.inventoryNum) : that.inventoryNum == null;
	}
	
	@Override
	public int hashCode() {
		int result = storehouseNum != null ? storehouseNum.hashCode() : 0;
		result = 31 * result + (inventoryNum != null ? inventoryNum.hashCode() : 0);
		return result;
	}
}