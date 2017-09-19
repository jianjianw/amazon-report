package com.nhsoft.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryMatrix entity. @author MyEclipse Persistence Tools
 */

public class InventoryMatrix implements java.io.Serializable {

	private static final long serialVersionUID = 5498132124120012563L;
	private InventoryMatrixId id;
	private BigDecimal inventoryMatrixAmount;
	private BigDecimal inventoryMatrixAssistAmount;

	public InventoryMatrixId getId() {
		return id;
	}

	public void setId(InventoryMatrixId id) {
		this.id = id;
	}

	public BigDecimal getInventoryMatrixAmount() {
		return inventoryMatrixAmount;
	}

	public void setInventoryMatrixAmount(BigDecimal inventoryMatrixAmount) {
		this.inventoryMatrixAmount = inventoryMatrixAmount;
	}

	public BigDecimal getInventoryMatrixAssistAmount() {
		return inventoryMatrixAssistAmount;
	}

	public void setInventoryMatrixAssistAmount(
			BigDecimal inventoryMatrixAssistAmount) {
		this.inventoryMatrixAssistAmount = inventoryMatrixAssistAmount;
	}

	public static List<InventoryMatrix> findAndRemove(List<InventoryMatrix> inventoryMatrixs,
			Integer storehouseNum, Integer inventoryNum) {
		List<InventoryMatrix> list = new ArrayList<InventoryMatrix>();
		int size = inventoryMatrixs.size();
		InventoryMatrix detail = null;
		for(int i = size - 1;i >= 0;i--){
			detail =  inventoryMatrixs.get(i);
			if(detail.getId().getStorehouseNum().equals(storehouseNum) 
					&& detail.getId().getInventoryNum().equals(inventoryNum)){
				list.add(detail);
				inventoryMatrixs.remove(i);
				continue;
			}
		}		
		return list;
	}

}