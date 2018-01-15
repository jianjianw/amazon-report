package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * InventoryMatrix entity. @author MyEclipse Persistence Tools
 */

public class InventoryMatrixDTO implements java.io.Serializable {

	private static final long serialVersionUID = 5498132124120012563L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private BigDecimal inventoryMatrixAmount;
	private BigDecimal inventoryMatrixAssistAmount;

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
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

	public static List<InventoryMatrixDTO> findAndRemove(List<InventoryMatrixDTO> inventoryMatrixs,
			Integer storehouseNum, Integer inventoryNum) {
		List<InventoryMatrixDTO> list = new ArrayList<InventoryMatrixDTO>();
		int size = inventoryMatrixs.size();
		InventoryMatrixDTO detail = null;
		for(int i = size - 1;i >= 0;i--){
			detail =  inventoryMatrixs.get(i);
			if(detail.getStorehouseNum().equals(storehouseNum)
					&& detail.getInventoryNum().equals(inventoryNum)){
				list.add(detail);
				inventoryMatrixs.remove(i);
				continue;
			}
		}		
		return list;
	}

}