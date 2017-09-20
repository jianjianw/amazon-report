package com.nhsoft.report.model;



/**
 * WholesaleOrderDetailId generated by hbm2java
 */
public class WholesaleOrderDetailId implements java.io.Serializable {

	private static final long serialVersionUID = 7127198487700228639L;
	private String wholesaleOrderFid;
	private Integer orderDetailNum;

	public WholesaleOrderDetailId() {
	}

	public WholesaleOrderDetailId(String wholesaleOrderFid,
			Integer orderDetailNum) {
		this.wholesaleOrderFid = wholesaleOrderFid;
		this.orderDetailNum = orderDetailNum;
	}

	public String getWholesaleOrderFid() {
		return this.wholesaleOrderFid;
	}

	public void setWholesaleOrderFid(String wholesaleOrderFid) {
		this.wholesaleOrderFid = wholesaleOrderFid;
	}

	public Integer getOrderDetailNum() {
		return this.orderDetailNum;
	}

	public void setOrderDetailNum(Integer orderDetailNum) {
		this.orderDetailNum = orderDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WholesaleOrderDetailId))
			return false;
		WholesaleOrderDetailId castOther = (WholesaleOrderDetailId) other;

		return ((this.getWholesaleOrderFid() == castOther
				.getWholesaleOrderFid()) || (this.getWholesaleOrderFid() != null
				&& castOther.getWholesaleOrderFid() != null && this
				.getWholesaleOrderFid()
				.equals(castOther.getWholesaleOrderFid())))
				&& ((this.getOrderDetailNum() == castOther.getOrderDetailNum()) || (this
						.getOrderDetailNum() != null
						&& castOther.getOrderDetailNum() != null && this
						.getOrderDetailNum().equals(
								castOther.getOrderDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getWholesaleOrderFid() == null ? 0 : this
						.getWholesaleOrderFid().hashCode());
		result = 37
				* result
				+ (getOrderDetailNum() == null ? 0 : this.getOrderDetailNum()
						.hashCode());
		return result;
	}

}