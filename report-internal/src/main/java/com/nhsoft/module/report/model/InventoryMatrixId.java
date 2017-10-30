package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * InventoryMatrixId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class InventoryMatrixId implements java.io.Serializable {

	private static final long serialVersionUID = -7804800321862334004L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer itemNum;
	private Integer itemMatrixNum;

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

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((inventoryNum == null) ? 0 : inventoryNum.hashCode());
		result = prime * result
				+ ((itemMatrixNum == null) ? 0 : itemMatrixNum.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
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
		InventoryMatrixId other = (InventoryMatrixId) obj;
		if (inventoryNum == null) {
			if (other.inventoryNum != null)
				return false;
		} else if (!inventoryNum.equals(other.inventoryNum))
			return false;
		if (itemMatrixNum == null) {
			if (other.itemMatrixNum != null)
				return false;
		} else if (!itemMatrixNum.equals(other.itemMatrixNum))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (storehouseNum == null) {
			if (other.storehouseNum != null)
				return false;
		} else if (!storehouseNum.equals(other.storehouseNum))
			return false;
		return true;
	}
	
	

}