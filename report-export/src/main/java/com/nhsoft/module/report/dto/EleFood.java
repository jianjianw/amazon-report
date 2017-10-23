package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EleFood implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2918028448810211551L;
	private String description;
    private long id;
    private String name;
    private int isValid;
    private int recentPopularity;
    private long categoryId;
    private long shopId;
    private String shopName;
    private String imageUrl;
    private Integer itemNum;
    private BigDecimal outItemRate;
	public String getDescription() {
		return description;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getIsValid() {
		return isValid;
	}
	public int getRecentPopularity() {
		return recentPopularity;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public long getShopId() {
		return shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public void setRecentPopularity(int recentPopularity) {
		this.recentPopularity = recentPopularity;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getItemNum() {
		return itemNum;
	}
	public BigDecimal getOutItemRate() {
		return outItemRate;
	}
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	public void setOutItemRate(BigDecimal outItemRate) {
		this.outItemRate = outItemRate;
	}


}
