package com.nhsoft.module.report.model;


import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * StoreMatrixDetail generated by hbm2java
 */
@Entity
public class StoreMatrixDetail implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4939184178740935928L;
	@Embeddable
	public static class StoreMatrixDetailId implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5456475623233019710L;
		private String systemBookCode;
		private int itemNum;
		private int branchNum;
		private int itemMatrixNum;

		public StoreMatrixDetailId() {
		}

		public StoreMatrixDetailId(String systemBookCode, int itemNum, int branchNum, int itemMatrixNum) {
			this.systemBookCode = systemBookCode;
			this.itemNum = itemNum;
			this.branchNum = branchNum;
			this.itemMatrixNum = itemMatrixNum;
		}

		public String getSystemBookCode() {
			return this.systemBookCode;
		}

		public void setSystemBookCode(String systemBookCode) {
			this.systemBookCode = systemBookCode;
		}

		public int getItemNum() {
			return this.itemNum;
		}

		public void setItemNum(int itemNum) {
			this.itemNum = itemNum;
		}

		public int getBranchNum() {
			return this.branchNum;
		}

		public void setBranchNum(int branchNum) {
			this.branchNum = branchNum;
		}

		public int getItemMatrixNum() {
			return this.itemMatrixNum;
		}

		public void setItemMatrixNum(int itemMatrixNum) {
			this.itemMatrixNum = itemMatrixNum;
		}

		public boolean equals(Object other) {
			if ((this == other))
				return true;
			if ((other == null))
				return false;
			if (!(other instanceof StoreMatrixDetailId))
				return false;
			StoreMatrixDetailId castOther = (StoreMatrixDetailId) other;

			return ((this.getSystemBookCode() == castOther.getSystemBookCode()) || (this.getSystemBookCode() != null
					&& castOther.getSystemBookCode() != null && this.getSystemBookCode().equals(
					castOther.getSystemBookCode())))
					&& (this.getItemNum() == castOther.getItemNum())
					&& (this.getBranchNum() == castOther.getBranchNum())
					&& (this.getItemMatrixNum() == castOther.getItemMatrixNum());
		}

		public int hashCode() {
			int result = 17;

			result = 37 * result + (getSystemBookCode() == null ? 0 : this.getSystemBookCode().hashCode());
			result = 37 * result + this.getItemNum();
			result = 37 * result + this.getBranchNum();
			result = 37 * result + this.getItemMatrixNum();
			return result;
		}

	}
	@EmbeddedId
	private StoreMatrixDetailId id;
	private BigDecimal storeMatrixDetailCustOrderQty;
	private BigDecimal storeMatrixDetailOnOrderQty;
	private BigDecimal storeMatrixDetailBaseStock;
	private BigDecimal storeMatrixDetailReorderPoint;
	private BigDecimal storeMatrixDetailReorderQty;
	private BigDecimal storeMatrixDetailUpperStock;

	public StoreMatrixDetail() {
	}

	public StoreMatrixDetailId getId() {
		return this.id;
	}

	public void setId(StoreMatrixDetailId id) {
		this.id = id;
	}

	public BigDecimal getStoreMatrixDetailCustOrderQty() {
		return this.storeMatrixDetailCustOrderQty;
	}

	public void setStoreMatrixDetailCustOrderQty(BigDecimal storeMatrixDetailCustOrderQty) {
		this.storeMatrixDetailCustOrderQty = storeMatrixDetailCustOrderQty;
	}

	public BigDecimal getStoreMatrixDetailOnOrderQty() {
		return this.storeMatrixDetailOnOrderQty;
	}

	public void setStoreMatrixDetailOnOrderQty(BigDecimal storeMatrixDetailOnOrderQty) {
		this.storeMatrixDetailOnOrderQty = storeMatrixDetailOnOrderQty;
	}

	public BigDecimal getStoreMatrixDetailBaseStock() {
		return this.storeMatrixDetailBaseStock;
	}

	public void setStoreMatrixDetailBaseStock(BigDecimal storeMatrixDetailBaseStock) {
		this.storeMatrixDetailBaseStock = storeMatrixDetailBaseStock;
	}

	public BigDecimal getStoreMatrixDetailReorderPoint() {
		return this.storeMatrixDetailReorderPoint;
	}

	public void setStoreMatrixDetailReorderPoint(BigDecimal storeMatrixDetailReorderPoint) {
		this.storeMatrixDetailReorderPoint = storeMatrixDetailReorderPoint;
	}

	public BigDecimal getStoreMatrixDetailReorderQty() {
		return this.storeMatrixDetailReorderQty;
	}

	public void setStoreMatrixDetailReorderQty(BigDecimal storeMatrixDetailReorderQty) {
		this.storeMatrixDetailReorderQty = storeMatrixDetailReorderQty;
	}

	public BigDecimal getStoreMatrixDetailUpperStock() {
		return this.storeMatrixDetailUpperStock;
	}

	public void setStoreMatrixDetailUpperStock(BigDecimal storeMatrixDetailUpperStock) {
		this.storeMatrixDetailUpperStock = storeMatrixDetailUpperStock;
	}

}
