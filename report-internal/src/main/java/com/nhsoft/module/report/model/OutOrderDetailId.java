package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * OutOrderDetailId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class OutOrderDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -3567163112511867497L;
	private String outOrderFid;
	private Integer outOrderDetailNum;
	
	public OutOrderDetailId(){
		
	}
	
	public OutOrderDetailId(String outOrderFid, Integer outOrderDetailNum){
		this.outOrderFid = outOrderFid;
		this.outOrderDetailNum = outOrderDetailNum;
	}

	public String getOutOrderFid() {
		return outOrderFid;
	}

	public void setOutOrderFid(String outOrderFid) {
		this.outOrderFid = outOrderFid;
	}

	public Integer getOutOrderDetailNum() {
		return outOrderDetailNum;
	}

	public void setOutOrderDetailNum(Integer outOrderDetailNum) {
		this.outOrderDetailNum = outOrderDetailNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((outOrderDetailNum == null) ? 0 : outOrderDetailNum
						.hashCode());
		result = prime * result
				+ ((outOrderFid == null) ? 0 : outOrderFid.hashCode());
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
		OutOrderDetailId other = (OutOrderDetailId) obj;
		if (outOrderDetailNum == null) {
			if (other.outOrderDetailNum != null)
				return false;
		} else if (!outOrderDetailNum.equals(other.outOrderDetailNum))
			return false;
		if (outOrderFid == null) {
			if (other.outOrderFid != null)
				return false;
		} else if (!outOrderFid.equals(other.outOrderFid))
			return false;
		return true;
	}

	
	
}