package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.List;

public class ShopPosResultSuit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251085613271304829L;
	private List<ShopPosItem> shopPosItems;
	private Integer totalCount;

	public List<ShopPosItem> getShopPosItems() {
		return shopPosItems;
	}

	public void setShopPosItems(List<ShopPosItem> shopPosItems) {
		this.shopPosItems = shopPosItems;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	


}
