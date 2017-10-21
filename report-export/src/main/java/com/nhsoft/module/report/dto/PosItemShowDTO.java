package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosItemShowDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3136908878300449522L;
	private Integer itemNum;
	private String itemCode;
	private String itemBarCode;
	private String itemName;
	private String itemUnit;
	private String itemPinYin;
	private BigDecimal itemBranchTransferPrice;
	private BigDecimal itemBranchSalePrice;
	private String itemSpec;
	private String itemPlace;// 产地
	private Integer itemType;// 商品类型
	private String itemTransferUnit;
	private String itemBrand;// 商品品牌
	private String itemTypeName;// 商品类别
	private BigDecimal coolQty = BigDecimal.ZERO;// 赞数量
	private BigDecimal favoriteQty = BigDecimal.ZERO;// 收藏数量
	public BigDecimal itemTransferRate;
	private String defaultImageUrl;
	private String itemCategoryCode;
	private String itemCategoryName;
	private Date itemCreateTime;
	private List<String> imageUrls = new ArrayList<String>();

	public PosItemShowDTO() {
		setItemBranchTransferPrice(BigDecimal.ZERO);
		setCoolQty(BigDecimal.ZERO);
		setFavoriteQty(BigDecimal.ZERO);
	}

	public Date getItemCreateTime() {
		return itemCreateTime;
	}

	public void setItemCreateTime(Date itemCreateTime) {
		this.itemCreateTime = itemCreateTime;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemBarCode() {
		return itemBarCode;
	}

	public void setItemBarCode(String itemBarCode) {
		this.itemBarCode = itemBarCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemPinYin() {
		return itemPinYin;
	}

	public void setItemPinYin(String itemPinYin) {
		this.itemPinYin = itemPinYin;
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

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemPlace() {
		return itemPlace;
	}

	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public String getItemTransferUnit() {
		return itemTransferUnit;
	}

	public void setItemTransferUnit(String itemTransferUnit) {
		this.itemTransferUnit = itemTransferUnit;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
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


	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

}
