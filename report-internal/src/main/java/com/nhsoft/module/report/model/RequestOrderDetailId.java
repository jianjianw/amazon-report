package com.nhsoft.module.report.model;

/**
 * RequestOrderDetailId entity. @author MyEclipse Persistence Tools
 */

public class RequestOrderDetailId implements java.io.Serializable {

	private static final long serialVersionUID = 2527266138765184069L;
	private String requestOrderFid;
	private Integer requestOrderDetailNum;


	public String getRequestOrderFid() {
		return requestOrderFid;
	}

	public void setRequestOrderFid(String requestOrderFid) {
		this.requestOrderFid = requestOrderFid;
	}

	public Integer getRequestOrderDetailNum() {
		return requestOrderDetailNum;
	}

	public void setRequestOrderDetailNum(Integer requestOrderDetailNum) {
		this.requestOrderDetailNum = requestOrderDetailNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((requestOrderDetailNum == null) ? 0 : requestOrderDetailNum
						.hashCode());
		result = prime * result
				+ ((requestOrderFid == null) ? 0 : requestOrderFid.hashCode());
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
		RequestOrderDetailId other = (RequestOrderDetailId) obj;
		if (requestOrderDetailNum == null) {
			if (other.requestOrderDetailNum != null)
				return false;
		} else if (!requestOrderDetailNum.equals(other.requestOrderDetailNum))
			return false;
		if (requestOrderFid == null) {
			if (other.requestOrderFid != null)
				return false;
		} else if (!requestOrderFid.equals(other.requestOrderFid))
			return false;
		return true;
	}

	
}