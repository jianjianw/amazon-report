package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * InventoryLnDetailId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class InventoryLnDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -2924475859564587979L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer inventoryLnDetailNum;

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

	public Integer getInventoryLnDetailNum() {
		return inventoryLnDetailNum;
	}

	public void setInventoryLnDetailNum(Integer inventoryLnDetailNum) {
		this.inventoryLnDetailNum = inventoryLnDetailNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((inventoryLnDetailNum == null) ? 0 : inventoryLnDetailNum
						.hashCode());
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
		InventoryLnDetailId other = (InventoryLnDetailId) obj;
		if (inventoryLnDetailNum == null) {
			if (other.inventoryLnDetailNum != null)
				return false;
		} else if (!inventoryLnDetailNum.equals(other.inventoryLnDetailNum))
			return false;
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