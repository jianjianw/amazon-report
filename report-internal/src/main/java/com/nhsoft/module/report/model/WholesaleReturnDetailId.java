package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;


/**
 * WholesaleReturnDetailId generated by hbm2java
 */
@Embeddable
public class WholesaleReturnDetailId implements java.io.Serializable {

	private static final long serialVersionUID = 5766345249172804160L;
	private String wholesaleReturnFid;
	private Integer returnDetailNum;

	public WholesaleReturnDetailId() {
	}

	public WholesaleReturnDetailId(String wholesaleReturnFid,
			Integer returnDetailNum) {
		this.wholesaleReturnFid = wholesaleReturnFid;
		this.returnDetailNum = returnDetailNum;
	}

	public String getWholesaleReturnFid() {
		return this.wholesaleReturnFid;
	}

	public void setWholesaleReturnFid(String wholesaleReturnFid) {
		this.wholesaleReturnFid = wholesaleReturnFid;
	}

	public Integer getReturnDetailNum() {
		return this.returnDetailNum;
	}

	public void setReturnDetailNum(Integer returnDetailNum) {
		this.returnDetailNum = returnDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WholesaleReturnDetailId))
			return false;
		WholesaleReturnDetailId castOther = (WholesaleReturnDetailId) other;

		return ((this.getWholesaleReturnFid() == castOther
				.getWholesaleReturnFid()) || (this.getWholesaleReturnFid() != null
				&& castOther.getWholesaleReturnFid() != null && this
				.getWholesaleReturnFid().equals(
						castOther.getWholesaleReturnFid())))
				&& ((this.getReturnDetailNum() == castOther
						.getReturnDetailNum()) || (this.getReturnDetailNum() != null
						&& castOther.getReturnDetailNum() != null && this
						.getReturnDetailNum().equals(
								castOther.getReturnDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getWholesaleReturnFid() == null ? 0 : this
						.getWholesaleReturnFid().hashCode());
		result = 37
				* result
				+ (getReturnDetailNum() == null ? 0 : this.getReturnDetailNum()
						.hashCode());
		return result;
	}

}
