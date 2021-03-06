package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;


/**
 * InnerSettlementDetailId generated by hbm2java
 */
@Embeddable
public class InnerSettlementDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -9199421071325023473L;
	private String innerSettlementNo;
	private Integer innerSettlementDetailNum;

	public InnerSettlementDetailId() {
	}

	public String getInnerSettlementNo() {
		return this.innerSettlementNo;
	}

	public void setInnerSettlementNo(String innerSettlementNo) {
		this.innerSettlementNo = innerSettlementNo;
	}

	public Integer getInnerSettlementDetailNum() {
		return this.innerSettlementDetailNum;
	}

	public void setInnerSettlementDetailNum(Integer innerSettlementDetailNum) {
		this.innerSettlementDetailNum = innerSettlementDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InnerSettlementDetailId))
			return false;
		InnerSettlementDetailId castOther = (InnerSettlementDetailId) other;

		return ((this.getInnerSettlementNo() == castOther
				.getInnerSettlementNo()) || (this.getInnerSettlementNo() != null
				&& castOther.getInnerSettlementNo() != null && this
				.getInnerSettlementNo()
				.equals(castOther.getInnerSettlementNo())))
				&& ((this.getInnerSettlementDetailNum() == castOther
						.getInnerSettlementDetailNum()) || (this
						.getInnerSettlementDetailNum() != null
						&& castOther.getInnerSettlementDetailNum() != null && this
						.getInnerSettlementDetailNum().equals(
								castOther.getInnerSettlementDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getInnerSettlementNo() == null ? 0 : this
						.getInnerSettlementNo().hashCode());
		result = 37
				* result
				+ (getInnerSettlementDetailNum() == null ? 0 : this
						.getInnerSettlementDetailNum().hashCode());
		return result;
	}

}
