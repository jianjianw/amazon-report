package com.nhsoft.report.dto;

import java.io.Serializable;

public class MarketActionAbbr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4250725368323918655L;

	private String abbrId;
	private String abbrName;

	public String getAbbrId() {
		return abbrId;
	}

	public void setAbbrId(String abbrId) {
		this.abbrId = abbrId;
	}

	public String getAbbrName() {
		return abbrName;
	}

	public void setAbbrName(String abbrName) {
		this.abbrName = abbrName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbrId == null) ? 0 : abbrId.hashCode());
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
		MarketActionAbbr other = (MarketActionAbbr) obj;
		if (abbrId == null) {
			if (other.abbrId != null)
				return false;
		} else if (!abbrId.equals(other.abbrId))
			return false;
		return true;
	}

}
