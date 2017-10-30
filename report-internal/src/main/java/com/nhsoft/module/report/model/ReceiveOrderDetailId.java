package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;


/**
 * ReceiveOrderDetailId generated by hbm2java
 */
@Embeddable
public class ReceiveOrderDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -2035785506125531324L;
	private String receiveOrderFid;
	private Integer receiveOrderDetailNum;

	public ReceiveOrderDetailId() {
	}

	public String getReceiveOrderFid() {
		return this.receiveOrderFid;
	}

	public void setReceiveOrderFid(String receiveOrderFid) {
		this.receiveOrderFid = receiveOrderFid;
	}

	public Integer getReceiveOrderDetailNum() {
		return this.receiveOrderDetailNum;
	}

	public void setReceiveOrderDetailNum(Integer receiveOrderDetailNum) {
		this.receiveOrderDetailNum = receiveOrderDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReceiveOrderDetailId))
			return false;
		ReceiveOrderDetailId castOther = (ReceiveOrderDetailId) other;

		return ((this.getReceiveOrderFid() == castOther.getReceiveOrderFid()) || (this
				.getReceiveOrderFid() != null
				&& castOther.getReceiveOrderFid() != null && this
				.getReceiveOrderFid().equals(castOther.getReceiveOrderFid())))
				&& ((this.getReceiveOrderDetailNum() == castOther
						.getReceiveOrderDetailNum()) || (this
						.getReceiveOrderDetailNum() != null
						&& castOther.getReceiveOrderDetailNum() != null && this
						.getReceiveOrderDetailNum().equals(
								castOther.getReceiveOrderDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getReceiveOrderFid() == null ? 0 : this.getReceiveOrderFid()
						.hashCode());
		result = 37
				* result
				+ (getReceiveOrderDetailNum() == null ? 0 : this
						.getReceiveOrderDetailNum().hashCode());
		return result;
	}

}
