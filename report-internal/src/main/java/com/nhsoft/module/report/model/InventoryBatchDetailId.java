package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * InventoryBatchDetailId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class InventoryBatchDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -4204028976118998139L;
	private Integer storehouseNum;
	private Integer inventoryNum;
	private Integer inventoryBatchDetailPri;

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

	public Integer getInventoryBatchDetailPri() {
		return inventoryBatchDetailPri;
	}

	public void setInventoryBatchDetailPri(Integer inventoryBatchDetailPri) {
		this.inventoryBatchDetailPri = inventoryBatchDetailPri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((inventoryBatchDetailPri == null) ? 0
						: inventoryBatchDetailPri.hashCode());
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
		InventoryBatchDetailId other = (InventoryBatchDetailId) obj;
		if (inventoryBatchDetailPri == null) {
			if (other.inventoryBatchDetailPri != null)
				return false;
		} else if (!inventoryBatchDetailPri
				.equals(other.inventoryBatchDetailPri))
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