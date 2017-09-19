package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PosItemShow implements Serializable {

	private static final long serialVersionUID = 447676272755010427L;
	private Integer itemNum;
	private String itemCode;
	private String itemName;
	private BigDecimal itemBranchTransferPrice;
	private BigDecimal itemBranchSalePrice;
	private String itemSpec;
	private String imageUrl;
	private boolean isBuy;
	private boolean isNewItem;
	private Integer itemType;// 商品类型
	private String itemBrand;// 商品品牌
	private BigDecimal storeQty = BigDecimal.ZERO;// 库存量
	private BigDecimal coolQty = BigDecimal.ZERO;// 赞数量
	private BigDecimal favoriteQty = BigDecimal.ZERO;// 收藏数量
	private String itemPlace;// 产地
	private BigDecimal itemLevel2Price;
	private BigDecimal itemLevel3Price;
	private BigDecimal itemLevel4Price;
	private BigDecimal itemMinPrice;
	//2015-05-11新增属性
	private String itemUnit;
	private String itemPurchaseUnit;
	private BigDecimal itemPurchaseRate;
	private String itemInventoryUnit;
	private BigDecimal itemInventoryRate;
	private String itemTransferUnit;
	private BigDecimal itemTransferRate;
	private String itemWholesaleUnit;
	private BigDecimal itemWholesaleRate;
	private String itemCategoryCode;
	private String itemNoteInfo;//注意事项
	private String itemAssistUnit;
	private BigDecimal itemAssistRate;
	
	//2015-08-09新增
	private BigDecimal itemCostPrice;
	
	//2016-11-04新增
	private Date itemLastEditTime;
	
	public Date getItemLastEditTime() {
		return itemLastEditTime;
	}

	public void setItemLastEditTime(Date itemLastEditTime) {
		this.itemLastEditTime = itemLastEditTime;
	}

	public BigDecimal getItemCostPrice() {
		return itemCostPrice;
	}

	public void setItemCostPrice(BigDecimal itemCostPrice) {
		this.itemCostPrice = itemCostPrice;
	}

	public String getItemAssistUnit() {
		return itemAssistUnit;
	}

	public void setItemAssistUnit(String itemAssistUnit) {
		this.itemAssistUnit = itemAssistUnit;
	}

	public BigDecimal getItemAssistRate() {
		return itemAssistRate;
	}

	public void setItemAssistRate(BigDecimal itemAssistRate) {
		this.itemAssistRate = itemAssistRate;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemPurchaseUnit() {
		return itemPurchaseUnit;
	}

	public void setItemPurchaseUnit(String itemPurchaseUnit) {
		this.itemPurchaseUnit = itemPurchaseUnit;
	}

	public BigDecimal getItemPurchaseRate() {
		return itemPurchaseRate;
	}

	public void setItemPurchaseRate(BigDecimal itemPurchaseRate) {
		this.itemPurchaseRate = itemPurchaseRate;
	}

	public String getItemInventoryUnit() {
		return itemInventoryUnit;
	}

	public void setItemInventoryUnit(String itemInventoryUnit) {
		this.itemInventoryUnit = itemInventoryUnit;
	}

	public BigDecimal getItemInventoryRate() {
		return itemInventoryRate;
	}

	public void setItemInventoryRate(BigDecimal itemInventoryRate) {
		this.itemInventoryRate = itemInventoryRate;
	}

	public String getItemWholesaleUnit() {
		return itemWholesaleUnit;
	}

	public void setItemWholesaleUnit(String itemWholesaleUnit) {
		this.itemWholesaleUnit = itemWholesaleUnit;
	}

	public BigDecimal getItemWholesaleRate() {
		return itemWholesaleRate;
	}

	public void setItemWholesaleRate(BigDecimal itemWholesaleRate) {
		this.itemWholesaleRate = itemWholesaleRate;
	}

	public BigDecimal getItemMinPrice() {
		return itemMinPrice;
	}

	public void setItemMinPrice(BigDecimal itemMinPrice) {
		this.itemMinPrice = itemMinPrice;
	}

	public BigDecimal getItemLevel2Price() {
		return itemLevel2Price;
	}

	public void setItemLevel2Price(BigDecimal itemLevel2Price) {
		this.itemLevel2Price = itemLevel2Price;
	}

	public BigDecimal getItemLevel3Price() {
		return itemLevel3Price;
	}

	public void setItemLevel3Price(BigDecimal itemLevel3Price) {
		this.itemLevel3Price = itemLevel3Price;
	}

	public BigDecimal getItemLevel4Price() {
		return itemLevel4Price;
	}

	public void setItemLevel4Price(BigDecimal itemLevel4Price) {
		this.itemLevel4Price = itemLevel4Price;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getItemBranchTransferPrice() {
		return itemBranchTransferPrice;
	}

	public void setItemBranchTransferPrice(BigDecimal itemBranchTransferPrice) {
		this.itemBranchTransferPrice = itemBranchTransferPrice;
	}

	public BigDecimal getItemBranchSalePrice() {
		return itemBranchSalePrice;
	}

	public void setItemBranchSalePrice(BigDecimal itemBranchSalePrice) {
		this.itemBranchSalePrice = itemBranchSalePrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	public boolean isNewItem() {
		return isNewItem;
	}

	public void setNewItem(boolean isNewItem) {
		this.isNewItem = isNewItem;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemTransferUnit() {
		return itemTransferUnit;
	}

	public void setItemTransferUnit(String itemTransferUnit) {
		this.itemTransferUnit = itemTransferUnit;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public BigDecimal getStoreQty() {
		return storeQty;
	}

	public void setStoreQty(BigDecimal storeQty) {
		this.storeQty = storeQty;
	}

	public BigDecimal getCoolQty() {
		return coolQty;
	}

	public void setCoolQty(BigDecimal coolQty) {
		this.coolQty = coolQty;
	}

	public BigDecimal getFavoriteQty() {
		return favoriteQty;
	}

	public void setFavoriteQty(BigDecimal favoriteQty) {
		this.favoriteQty = favoriteQty;
	}

	public BigDecimal getItemTransferRate() {
		return itemTransferRate;
	}

	public void setItemTransferRate(BigDecimal itemTransferRate) {
		this.itemTransferRate = itemTransferRate;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemPlace() {
		return itemPlace;
	}

	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}

	public String getItemNoteInfo() {
		return itemNoteInfo;
	}

	public void setItemNoteInfo(String itemNoteInfo) {
		this.itemNoteInfo = itemNoteInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
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
		PosItemShow other = (PosItemShow) obj;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}

}
