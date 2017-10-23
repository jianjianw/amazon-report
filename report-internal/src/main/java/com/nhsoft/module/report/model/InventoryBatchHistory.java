package com.nhsoft.module.report.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * InventoryBatchDetail entity. @author MyEclipse Persistence Tools
 */

public class InventoryBatchHistory implements java.io.Serializable {

	public static class InventoryBatchHistoryId implements java.io.Serializable {

		private static final long serialVersionUID = -4204028976118998139L;
		private Integer storehouseNum;
		private Integer inventoryNum;
		private Integer inventoryBatchDetailPri;
		
		public InventoryBatchHistoryId(){
			
		}
		
		public InventoryBatchHistoryId(Integer storehouseNum, Integer inventoryNum, Integer inventoryBatchDetailPri){
			this.storehouseNum = storehouseNum;
			this.inventoryNum = inventoryNum;
			this.inventoryBatchDetailPri = inventoryBatchDetailPri;
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
			InventoryBatchHistoryId other = (InventoryBatchHistoryId) obj;
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
	
	private static final long serialVersionUID = -2715190943803830563L;
	private InventoryBatchHistoryId id;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private String inventoryBatchDetailRefFid;
	private String inventoryBatchDetailBizType;
	private BigDecimal inventoryBatchDetailCostPrice;
	private BigDecimal inventoryBatchDetailAmount;
	private BigDecimal inventoryBatchDetailInitAmount;
	private BigDecimal inventoryBatchDetailAssistAmount;
	private BigDecimal inventoryBatchDetailInitAssistAmount;
	private BigDecimal inventoryBatchDetailUseRate;
	private String inventoryBatchDetailUseUnit;
	private BigDecimal inventoryBatchDetailUseAmount;
	private Date inventoryBatchDetailUpdateTime;
	private Date inventoryBatchDetailDate;
	private String inventoryBatchDetailUuid;

	public String getInventoryBatchDetailUuid() {
		return inventoryBatchDetailUuid;
	}

	public void setInventoryBatchDetailUuid(String inventoryBatchDetailUuid) {
		this.inventoryBatchDetailUuid = inventoryBatchDetailUuid;
	}

	public Date getInventoryBatchDetailDate() {
		return inventoryBatchDetailDate;
	}

	public void setInventoryBatchDetailDate(Date inventoryBatchDetailDate) {
		this.inventoryBatchDetailDate = inventoryBatchDetailDate;
	}

	public String getInventoryBatchDetailUseUnit() {
		return inventoryBatchDetailUseUnit;
	}

	public void setInventoryBatchDetailUseUnit(String inventoryBatchDetailUseUnit) {
		this.inventoryBatchDetailUseUnit = inventoryBatchDetailUseUnit;
	}

	public Date getInventoryBatchDetailUpdateTime() {
		return inventoryBatchDetailUpdateTime;
	}

	public void setInventoryBatchDetailUpdateTime(Date inventoryBatchDetailUpdateTime) {
		this.inventoryBatchDetailUpdateTime = inventoryBatchDetailUpdateTime;
	}

	public BigDecimal getInventoryBatchDetailUseRate() {
		return inventoryBatchDetailUseRate;
	}

	public void setInventoryBatchDetailUseRate(BigDecimal inventoryBatchDetailUseRate) {
		this.inventoryBatchDetailUseRate = inventoryBatchDetailUseRate;
	}

	public BigDecimal getInventoryBatchDetailUseAmount() {
		return inventoryBatchDetailUseAmount;
	}

	public void setInventoryBatchDetailUseAmount(BigDecimal inventoryBatchDetailUseAmount) {
		this.inventoryBatchDetailUseAmount = inventoryBatchDetailUseAmount;
	}

	public InventoryBatchHistoryId getId() {
		return id;
	}

	public void setId(InventoryBatchHistoryId id) {
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

	public String getInventoryBatchDetailRefFid() {
		return inventoryBatchDetailRefFid;
	}

	public void setInventoryBatchDetailRefFid(String inventoryBatchDetailRefFid) {
		this.inventoryBatchDetailRefFid = inventoryBatchDetailRefFid;
	}

	public String getInventoryBatchDetailBizType() {
		return inventoryBatchDetailBizType;
	}

	public void setInventoryBatchDetailBizType(String inventoryBatchDetailBizType) {
		this.inventoryBatchDetailBizType = inventoryBatchDetailBizType;
	}

	public BigDecimal getInventoryBatchDetailCostPrice() {
		return inventoryBatchDetailCostPrice;
	}

	public void setInventoryBatchDetailCostPrice(BigDecimal inventoryBatchDetailCostPrice) {
		this.inventoryBatchDetailCostPrice = inventoryBatchDetailCostPrice;
	}

	public BigDecimal getInventoryBatchDetailAmount() {
		return inventoryBatchDetailAmount;
	}

	public void setInventoryBatchDetailAmount(BigDecimal inventoryBatchDetailAmount) {
		this.inventoryBatchDetailAmount = inventoryBatchDetailAmount;
	}

	public BigDecimal getInventoryBatchDetailInitAmount() {
		return inventoryBatchDetailInitAmount;
	}

	public void setInventoryBatchDetailInitAmount(BigDecimal inventoryBatchDetailInitAmount) {
		this.inventoryBatchDetailInitAmount = inventoryBatchDetailInitAmount;
	}

	public BigDecimal getInventoryBatchDetailAssistAmount() {
		return inventoryBatchDetailAssistAmount;
	}

	public void setInventoryBatchDetailAssistAmount(BigDecimal inventoryBatchDetailAssistAmount) {
		this.inventoryBatchDetailAssistAmount = inventoryBatchDetailAssistAmount;
	}

	public BigDecimal getInventoryBatchDetailInitAssistAmount() {
		return inventoryBatchDetailInitAssistAmount;
	}

	public void setInventoryBatchDetailInitAssistAmount(BigDecimal inventoryBatchDetailInitAssistAmount) {
		this.inventoryBatchDetailInitAssistAmount = inventoryBatchDetailInitAssistAmount;
	}

}