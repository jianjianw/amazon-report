package com.nhsoft.report.dto;

import java.io.Serializable;

public class CloudItemCategoryDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705223724468569311L;
	private String categoryId;
	private String categoryName;
	private String parentCategoryId;
	private String parentCategoryName;
	
	public String getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public String getParentCategoryId() {
		return parentCategoryId;
	}
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public void setParentCategoryId(String parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
}
