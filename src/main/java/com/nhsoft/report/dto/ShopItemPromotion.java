package com.nhsoft.report.dto;

import java.io.Serializable;

public class ShopItemPromotion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1795322786903204604L;
	private Integer promotionId;
	private String promotionName;

	public Integer getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

}
