package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SupplierItemStock implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9140213382979614764L;

	private String itemCode;
	private String itemName;
	private String itemSpec;
	private String itemUseUnit;//库存单位
	private String itemBasicUnit;//基本单位
	private BigDecimal outQty;//出库数量
	private BigDecimal outBasicQty;//出库基本数量
	private BigDecimal onHandQty;//库存量
	
	public SupplierItemStock(){
		setOutBasicQty(BigDecimal.ZERO);
		setOutQty(BigDecimal.ZERO);
		setOnHandQty(BigDecimal.ZERO);
	}
	
	private BigDecimal itemInventoryRate;
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
	public String getItemUseUnit() {
		return itemUseUnit;
	}
	public void setItemUseUnit(String itemUseUnit) {
		this.itemUseUnit = itemUseUnit;
	}
	public String getItemBasicUnit() {
		return itemBasicUnit;
	}
	public void setItemBasicUnit(String itemBasicUnit) {
		this.itemBasicUnit = itemBasicUnit;
	}
	public BigDecimal getOutQty() {
		return outQty;
	}
	public void setOutQty(BigDecimal outQty) {
		this.outQty = outQty;
	}
	public BigDecimal getOutBasicQty() {
		return outBasicQty;
	}
	public void setOutBasicQty(BigDecimal outBasicQty) {
		this.outBasicQty = outBasicQty;
	}
	public BigDecimal getOnHandQty() {
		return onHandQty;
	}
	public void setOnHandQty(BigDecimal onHandQty) {
		this.onHandQty = onHandQty;
	}
		
	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}
	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
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
		SupplierItemStock other = (SupplierItemStock) obj;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		return true;
	}
	
}
