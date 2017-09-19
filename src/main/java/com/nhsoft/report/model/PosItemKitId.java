package com.nhsoft.report.model;

/**
 * PosItemKitId entity. @author MyEclipse Persistence Tools
 */

public class PosItemKitId implements java.io.Serializable {

	private static final long serialVersionUID = 711323540292406707L;
	private Integer itemNum;
	private Integer kitItemNum;

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getKitItemNum() {
		return kitItemNum;
	}

	public void setKitItemNum(Integer kitItemNum) {
		this.kitItemNum = kitItemNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		result = prime * result
				+ ((kitItemNum == null) ? 0 : kitItemNum.hashCode());
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
		PosItemKitId other = (PosItemKitId) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (kitItemNum == null) {
			if (other.kitItemNum != null)
				return false;
		} else if (!kitItemNum.equals(other.kitItemNum))
			return false;
		return true;
	}

}