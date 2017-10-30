package com.nhsoft.module.report.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * InventoryLnDetail entity. @author MyEclipse Persistence Tools
 */

@Entity
public class InventoryLnHistory implements java.io.Serializable {

	@Embeddable
	public static class InventoryLnHistoryId implements java.io.Serializable {

		private static final long serialVersionUID = -2924475859564587979L;
		private Integer storehouseNum;
		private Integer inventoryNum;
		private Integer inventoryLnDetailNum;
		
		public InventoryLnHistoryId(){
			
		}
		
		public InventoryLnHistoryId(Integer storehouseNum, Integer inventoryNum, Integer inventoryLnDetailNum){
			this.storehouseNum = storehouseNum;
			this.inventoryNum = inventoryNum;
			this.inventoryLnDetailNum = inventoryLnDetailNum;
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
			InventoryLnHistoryId other = (InventoryLnHistoryId) obj;
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
	
	private static final long serialVersionUID = 2035213025643053078L;
	@EmbeddedId
	private InventoryLnHistoryId id;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Date inventoryLnDetailProducingDate;
	private String inventoryLnDetailLotNumber;
	private BigDecimal inventoryLnDetailAmount;
	private BigDecimal inventoryLnDetailCostPrice;
	private BigDecimal inventoryLnDetailInitAmount;
	private BigDecimal inventoryLnDetailAssistAmount;
	private BigDecimal inventoryLnDetailInitAssistAmount;
	private Integer inventoryLnDetailPri;
	private Date inventoryLnDetailDate;
	private BigDecimal inventoryLnDetailUseRate;
	private String inventoryLnDetailUseUnit;
	@Column(name = "inventoryLnDetailUseQty")
	private BigDecimal inventoryLnDetailUseAmount;
	private Date inventoryLnDetailUpdateTime;
	private String inventoryLnDetailMemo;
	private BigDecimal inventoryLnDetailTare;//件皮重
	private Integer supplierNum;
	@Transient
	private BigDecimal inventoryLnDetailTransferPrice; //批次锁定配送价

	public BigDecimal getInventoryLnDetailTransferPrice() {
		return inventoryLnDetailTransferPrice;
	}

	public void setInventoryLnDetailTransferPrice(BigDecimal inventoryLnDetailTransferPrice) {
		this.inventoryLnDetailTransferPrice = inventoryLnDetailTransferPrice;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public String getInventoryLnDetailMemo() {
		return inventoryLnDetailMemo;
	}

	public void setInventoryLnDetailMemo(String inventoryLnDetailMemo) {
		this.inventoryLnDetailMemo = inventoryLnDetailMemo;
	}

	public InventoryLnHistoryId getId() {
		return id;
	}

	public void setId(InventoryLnHistoryId id) {
		this.id = id;
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

	public Date getInventoryLnDetailProducingDate() {
		return inventoryLnDetailProducingDate;
	}

	public void setInventoryLnDetailProducingDate(
			Date inventoryLnDetailProducingDate) {
		this.inventoryLnDetailProducingDate = inventoryLnDetailProducingDate;
	}

	public String getInventoryLnDetailLotNumber() {
		return inventoryLnDetailLotNumber;
	}

	public void setInventoryLnDetailLotNumber(String inventoryLnDetailLotNumber) {
		this.inventoryLnDetailLotNumber = inventoryLnDetailLotNumber;
	}

	public BigDecimal getInventoryLnDetailAmount() {
		return inventoryLnDetailAmount;
	}

	public void setInventoryLnDetailAmount(BigDecimal inventoryLnDetailAmount) {
		this.inventoryLnDetailAmount = inventoryLnDetailAmount;
	}

	public BigDecimal getInventoryLnDetailCostPrice() {
		return inventoryLnDetailCostPrice;
	}

	public void setInventoryLnDetailCostPrice(
			BigDecimal inventoryLnDetailCostPrice) {
		this.inventoryLnDetailCostPrice = inventoryLnDetailCostPrice;
	}

	public BigDecimal getInventoryLnDetailInitAmount() {
		return inventoryLnDetailInitAmount;
	}

	public void setInventoryLnDetailInitAmount(
			BigDecimal inventoryLnDetailInitAmount) {
		this.inventoryLnDetailInitAmount = inventoryLnDetailInitAmount;
	}

	public BigDecimal getInventoryLnDetailAssistAmount() {
		return inventoryLnDetailAssistAmount;
	}

	public void setInventoryLnDetailAssistAmount(
			BigDecimal inventoryLnDetailAssistAmount) {
		this.inventoryLnDetailAssistAmount = inventoryLnDetailAssistAmount;
	}

	public BigDecimal getInventoryLnDetailInitAssistAmount() {
		return inventoryLnDetailInitAssistAmount;
	}

	public void setInventoryLnDetailInitAssistAmount(
			BigDecimal inventoryLnDetailInitAssistAmount) {
		this.inventoryLnDetailInitAssistAmount = inventoryLnDetailInitAssistAmount;
	}

	public Integer getInventoryLnDetailPri() {
		return inventoryLnDetailPri;
	}

	public void setInventoryLnDetailPri(Integer inventoryLnDetailPri) {
		this.inventoryLnDetailPri = inventoryLnDetailPri;
	}

	public Date getInventoryLnDetailDate() {
		return inventoryLnDetailDate;
	}

	public void setInventoryLnDetailDate(Date inventoryLnDetailDate) {
		this.inventoryLnDetailDate = inventoryLnDetailDate;
	}

	public BigDecimal getInventoryLnDetailUseRate() {
		return inventoryLnDetailUseRate;
	}

	public void setInventoryLnDetailUseRate(BigDecimal inventoryLnDetailUseRate) {
		this.inventoryLnDetailUseRate = inventoryLnDetailUseRate;
	}

	public String getInventoryLnDetailUseUnit() {
		return inventoryLnDetailUseUnit;
	}

	public void setInventoryLnDetailUseUnit(String inventoryLnDetailUseUnit) {
		this.inventoryLnDetailUseUnit = inventoryLnDetailUseUnit;
	}

	public BigDecimal getInventoryLnDetailUseAmount() {
		return inventoryLnDetailUseAmount;
	}

	public void setInventoryLnDetailUseAmount(BigDecimal inventoryLnDetailUseAmount) {
		this.inventoryLnDetailUseAmount = inventoryLnDetailUseAmount;
	}

	public Date getInventoryLnDetailUpdateTime() {
		return inventoryLnDetailUpdateTime;
	}

	public void setInventoryLnDetailUpdateTime(Date inventoryLnDetailUpdateTime) {
		this.inventoryLnDetailUpdateTime = inventoryLnDetailUpdateTime;
	}

	public BigDecimal getInventoryLnDetailTare() {
		return inventoryLnDetailTare;
	}

	public void setInventoryLnDetailTare(BigDecimal inventoryLnDetailTare) {
		this.inventoryLnDetailTare = inventoryLnDetailTare;
	}


}