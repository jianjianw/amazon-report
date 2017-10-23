package com.nhsoft.module.report.model;

/**
 * ClientPreSettlementDetailId entity. @author MyEclipse Persistence Tools
 */

public class ClientPreSettlementDetailId implements java.io.Serializable {

	private static final long serialVersionUID = -1130251994300875L;
	private String clientSettlementNo;
	private String preSettlementNo;

	public String getClientSettlementNo() {
		return clientSettlementNo;
	}

	public void setClientSettlementNo(String clientSettlementNo) {
		this.clientSettlementNo = clientSettlementNo;
	}

	public String getPreSettlementNo() {
		return preSettlementNo;
	}

	public void setPreSettlementNo(String preSettlementNo) {
		this.preSettlementNo = preSettlementNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((clientSettlementNo == null) ? 0 : clientSettlementNo
						.hashCode());
		result = prime * result
				+ ((preSettlementNo == null) ? 0 : preSettlementNo.hashCode());
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
		ClientPreSettlementDetailId other = (ClientPreSettlementDetailId) obj;
		if (clientSettlementNo == null) {
			if (other.clientSettlementNo != null)
				return false;
		} else if (!clientSettlementNo.equals(other.clientSettlementNo))
			return false;
		if (preSettlementNo == null) {
			if (other.preSettlementNo != null)
				return false;
		} else if (!preSettlementNo.equals(other.preSettlementNo))
			return false;
		return true;
	}

}