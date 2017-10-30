package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;

/**
 * ItemMatrixId entity. @author MyEclipse Persistence Tools
 */

@Embeddable
public class ItemMatrixId implements java.io.Serializable {

	private static final long serialVersionUID = 8411903278702435117L;
	private Integer itemNum;
	private Integer itemMatrixNum;
	
	public ItemMatrixId(){
		
	}
	
	public ItemMatrixId(Integer itemNum, Integer itemMatrixNum){
		this.itemNum = itemNum;
		this.itemMatrixNum = itemMatrixNum;

	}
	
	

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemMatrixNum == null) ? 0 : itemMatrixNum.hashCode());
		result = prime * result + ((itemNum == null) ? 0 : itemNum.hashCode());
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
		ItemMatrixId other = (ItemMatrixId) obj;
		if (itemMatrixNum == null) {
			if (other.itemMatrixNum != null)
				return false;
		} else if (!itemMatrixNum.equals(other.itemMatrixNum))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}

	
	
}