package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * InventoryId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class InventoryId implements java.io.Serializable {

	private static final long serialVersionUID = 5262320139349705192L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	
	public InventoryId(){
		
	}
	
	public InventoryId(Integer storehouseNum, Integer inventoryNum){
		this.storehouseNum = storehouseNum;
		this.inventoryNum = inventoryNum;
	}
	
	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((inventoryNum == null) ? 0 : inventoryNum.hashCode());
		result = prime * result
				+ ((storehouseNum == null) ? 0 : storehouseNum.hashCode());
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
		InventoryId other = (InventoryId) obj;
		if (inventoryNum == null) {
			if (other.inventoryNum != null)
				return false;
		} else if (!inventoryNum.equals(other.inventoryNum))
			return false;
		if (storehouseNum == null) {
			if (other.storehouseNum != null)
				return false;
		} else if (!storehouseNum.equals(other.storehouseNum))
			return false;
		return true;
	}

	
}