package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NeedSaleItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6510011211352215106L;
	private PosItemDTO posItem;
	private Integer storehouseNum;
	private String storehouseName;
	private String lotNumber;// 批次
	private BigDecimal inventoryBasicQty;// 库存数量（基本数量）
	private String useUnit;
	private BigDecimal inventoryUseQty;
	private Date producingDate;// 生产日期
	private Integer expireDay; //还剩几天过期
	private BigDecimal inventoryUseCost;
	private BigDecimal inventoryMoney;
	private Date unValidDate;// 过期日期

	public PosItemDTO getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItemDTO posItem) {
		this.posItem = posItem;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getStorehouseName() {
		return storehouseName;
	}

	public void setStorehouseName(String storehouseName) {
		this.storehouseName = storehouseName;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public Date getProducingDate() {
		return producingDate;
	}

	public void setProducingDate(Date producingDate) {
		this.producingDate = producingDate;
	}

	public BigDecimal getInventoryBasicQty() {
		return inventoryBasicQty;
	}

	public void setInventoryBasicQty(BigDecimal inventoryBasicQty) {
		this.inventoryBasicQty = inventoryBasicQty;
	}

	public String getUseUnit() {
		return useUnit;
	}

	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}

	public BigDecimal getInventoryUseQty() {
		return inventoryUseQty;
	}

	public void setInventoryUseQty(BigDecimal inventoryUseQty) {
		this.inventoryUseQty = inventoryUseQty;
	}

	public Integer getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(Integer expireDay) {
		this.expireDay = expireDay;
	}

	public BigDecimal getInventoryUseCost() {
		return inventoryUseCost;
	}

	public void setInventoryUseCost(BigDecimal inventoryUseCost) {
		this.inventoryUseCost = inventoryUseCost;
	}

	public BigDecimal getInventoryMoney() {
		return inventoryMoney;
	}

	public void setInventoryMoney(BigDecimal inventoryMoney) {
		this.inventoryMoney = inventoryMoney;
	}

	public Date getUnValidDate() {
		return unValidDate;
	}

	public void setUnValidDate(Date unValidDate) {
		this.unValidDate = unValidDate;
	}

}
