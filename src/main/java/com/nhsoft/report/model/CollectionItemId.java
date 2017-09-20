package com.nhsoft.report.model;



/**
 * CollectionItemId generated by hbm2java
 */
public class CollectionItemId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6169891986202997624L;
	private Integer posItemNum;
	private Integer itemNum;

	public CollectionItemId() {
	}

	public CollectionItemId(Integer posItemNum, Integer itemNum) {
		this.posItemNum = posItemNum;
		this.itemNum = itemNum;
	}

	public Integer getPosItemNum() {
		return this.posItemNum;
	}

	public void setPosItemNum(Integer posItemNum) {
		this.posItemNum = posItemNum;
	}

	public Integer getItemNum() {
		return this.itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
		result = prime * result + ((posItemNum == null) ? 0 : posItemNum.hashCode());
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
		CollectionItemId other = (CollectionItemId) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		if (posItemNum == null) {
			if (other.posItemNum != null)
				return false;
		} else if (!posItemNum.equals(other.posItemNum))
			return false;
		return true;
	}



}