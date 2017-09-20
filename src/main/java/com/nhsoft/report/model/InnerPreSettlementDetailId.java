package com.nhsoft.report.model;

/**
 * InnerPreSettlementDetailId entity. @author MyEclipse Persistence Tools
 */

public class InnerPreSettlementDetailId implements java.io.Serializable {

	private static final long serialVersionUID = 8846727857762470983L;
	private String innerSettlementNo;
	private String preSettlementNo;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((innerSettlementNo == null) ? 0 : innerSettlementNo.hashCode());
		result = prime * result + ((preSettlementNo == null) ? 0 : preSettlementNo.hashCode());
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
		InnerPreSettlementDetailId other = (InnerPreSettlementDetailId) obj;
		if (innerSettlementNo == null) {
			if (other.innerSettlementNo != null)
				return false;
		} else if (!innerSettlementNo.equals(other.innerSettlementNo))
			return false;
		if (preSettlementNo == null) {
			if (other.preSettlementNo != null)
				return false;
		} else if (!preSettlementNo.equals(other.preSettlementNo))
			return false;
		return true;
	}

	public String getInnerSettlementNo() {
		return innerSettlementNo;
	}

	public void setInnerSettlementNo(String innerSettlementNo) {
		this.innerSettlementNo = innerSettlementNo;
	}

	public String getPreSettlementNo() {
		return preSettlementNo;
	}

	public void setPreSettlementNo(String preSettlementNo) {
		this.preSettlementNo = preSettlementNo;
	}

}