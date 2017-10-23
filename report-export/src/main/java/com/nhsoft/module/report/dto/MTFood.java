package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MTFood implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5435357840193172243L;
	private String eDishCode;// erp方菜品id
	private long dishId;// 美团方菜品id
	private String dishName;// 菜品名
	private String categoryName;// 菜品分类名
	private BigDecimal rate;
	
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String geteDishCode() {
		return eDishCode;
	}
	
	public void seteDishCode(String eDishCode) {
		this.eDishCode = eDishCode;
	}
	
	public long getDishId() {
		return dishId;
	}
	
	public void setDishId(long dishId) {
		this.dishId = dishId;
	}
	
	public String getDishName() {
		return dishName;
	}
	
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
