package com.nhsoft.module.report.model;



/**
 * AdjustmentOrderDetailId generated by hbm2java
 */
public class AdjustmentOrderDetailId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4674831606408723454L;
	private String adjustmentOrderFid;
	private Integer adjustmentOrderDetailNum;

	public AdjustmentOrderDetailId() {
	}

	public AdjustmentOrderDetailId(String adjustmentOrderFid,
			Integer adjustmentOrderDetailNum) {
		this.adjustmentOrderFid = adjustmentOrderFid;
		this.adjustmentOrderDetailNum = adjustmentOrderDetailNum;
	}

	public String getAdjustmentOrderFid() {
		return this.adjustmentOrderFid;
	}

	public void setAdjustmentOrderFid(String adjustmentOrderFid) {
		this.adjustmentOrderFid = adjustmentOrderFid;
	}

	public Integer getAdjustmentOrderDetailNum() {
		return this.adjustmentOrderDetailNum;
	}

	public void setAdjustmentOrderDetailNum(Integer adjustmentOrderDetailNum) {
		this.adjustmentOrderDetailNum = adjustmentOrderDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AdjustmentOrderDetailId))
			return false;
		AdjustmentOrderDetailId castOther = (AdjustmentOrderDetailId) other;

		return ((this.getAdjustmentOrderFid() == castOther
				.getAdjustmentOrderFid()) || (this.getAdjustmentOrderFid() != null
				&& castOther.getAdjustmentOrderFid() != null && this
				.getAdjustmentOrderFid().equals(
						castOther.getAdjustmentOrderFid())))
				&& ((this.getAdjustmentOrderDetailNum() == castOther
						.getAdjustmentOrderDetailNum()) || (this
						.getAdjustmentOrderDetailNum() != null
						&& castOther.getAdjustmentOrderDetailNum() != null && this
						.getAdjustmentOrderDetailNum().equals(
								castOther.getAdjustmentOrderDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getAdjustmentOrderFid() == null ? 0 : this
						.getAdjustmentOrderFid().hashCode());
		result = 37
				* result
				+ (getAdjustmentOrderDetailNum() == null ? 0 : this
						.getAdjustmentOrderDetailNum().hashCode());
		return result;
	}

}