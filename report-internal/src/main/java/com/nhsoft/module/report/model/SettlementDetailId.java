package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;


/**
 * SettlementDetailId generated by hbm2java
 */
@Embeddable
public class SettlementDetailId implements java.io.Serializable {

	private static final long serialVersionUID = 3583054021114923861L;
	private String settlementNo;
	private Integer settlementDetailNum;

	public SettlementDetailId() {
	}

	public SettlementDetailId(String settlementNo, Integer settlementDetailNum) {
		this.settlementNo = settlementNo;
		this.settlementDetailNum = settlementDetailNum;
	}

	public String getSettlementNo() {
		return this.settlementNo;
	}

	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
	}

	public Integer getSettlementDetailNum() {
		return this.settlementDetailNum;
	}

	public void setSettlementDetailNum(Integer settlementDetailNum) {
		this.settlementDetailNum = settlementDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SettlementDetailId))
			return false;
		SettlementDetailId castOther = (SettlementDetailId) other;

		return ((this.getSettlementNo() == castOther.getSettlementNo()) || (this
				.getSettlementNo() != null
				&& castOther.getSettlementNo() != null && this
				.getSettlementNo().equals(castOther.getSettlementNo())))
				&& ((this.getSettlementDetailNum() == castOther
						.getSettlementDetailNum()) || (this
						.getSettlementDetailNum() != null
						&& castOther.getSettlementDetailNum() != null && this
						.getSettlementDetailNum().equals(
								castOther.getSettlementDetailNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSettlementNo() == null ? 0 : this.getSettlementNo()
						.hashCode());
		result = 37
				* result
				+ (getSettlementDetailNum() == null ? 0 : this
						.getSettlementDetailNum().hashCode());
		return result;
	}

}
