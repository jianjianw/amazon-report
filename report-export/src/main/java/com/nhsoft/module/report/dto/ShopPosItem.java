package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopPosItem implements java.io.Serializable {

	private static final long serialVersionUID = -5109960546148847470L;

	private Integer itemNum; // 商品系统编号
	private Integer itemId; // 商品外部编号
	private String itemName; // 商品名称
	private String itemCode;
	private BigDecimal itemRegularPrice; // 售价
	private String itemDesc; // 商品描述
	private Boolean itemVirtual; // 是否虚拟商品
	private BigDecimal itemPostFee; // 运费
	private BigDecimal itemOriginPrice; // 原价
	private String itemBuyUrl; // 外部购买地址
	private Integer itemBuyQuota; // 每人限购多少件
	private Integer itemQuantity; // 商品总库存
	private Integer itemHideQuantity;// 是否隐藏商品库存
	private Boolean itemIsListing; // 是否上架
	private Date itemListingTime; // 上架时间
	private String itemDetailUrl;
	private String itemPicUrl;
	private String itemPicThumbUrl;
	private List<byte[]> itemImages;
	private Integer itemCategoryid;
	private Integer itemPromotionId;
	private String itemTagIds;
	private Date itemCreatedTime;
	private BigDecimal itemLocalRegularPrice; // 本地售价
	private String itemImageIds;
	private BigDecimal localInventoryAmount;
	private String itemUnit;
	private List<ShopPosItemImage> shopPosItemImages = new ArrayList<ShopPosItemImage>();
	private List<String> imageUrls = new ArrayList<String>();


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

	public BigDecimal getItemRegularPrice() {
		return itemRegularPrice;
	}

	public void setItemRegularPrice(BigDecimal itemRegularPrice) {
		this.itemRegularPrice = itemRegularPrice;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Boolean getItemVirtual() {
		return itemVirtual;
	}

	public void setItemVirtual(Boolean itemVirtual) {
		this.itemVirtual = itemVirtual;
	}

	public BigDecimal getItemPostFee() {
		return itemPostFee;
	}

	public void setItemPostFee(BigDecimal itemPostFee) {
		this.itemPostFee = itemPostFee;
	}

	public BigDecimal getItemOriginPrice() {
		return itemOriginPrice;
	}

	public void setItemOriginPrice(BigDecimal itemOriginPrice) {
		this.itemOriginPrice = itemOriginPrice;
	}

	public String getItemBuyUrl() {
		return itemBuyUrl;
	}

	public void setItemBuyUrl(String itemBuyUrl) {
		this.itemBuyUrl = itemBuyUrl;
	}

	public Integer getItemBuyQuota() {
		return itemBuyQuota;
	}

	public void setItemBuyQuota(Integer itemBuyQuota) {
		this.itemBuyQuota = itemBuyQuota;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Integer getItemHideQuantity() {
		return itemHideQuantity;
	}

	public void setItemHideQuantity(Integer itemHideQuantity) {
		this.itemHideQuantity = itemHideQuantity;
	}

	public Boolean getItemIsListing() {
		return itemIsListing;
	}

	public void setItemIsListing(Boolean itemIsListing) {
		this.itemIsListing = itemIsListing;
	}

	public Date getItemListingTime() {
		return itemListingTime;
	}

	public void setItemListingTime(Date itemListingTime) {
		this.itemListingTime = itemListingTime;
	}

	public String getItemDetailUrl() {
		return itemDetailUrl;
	}

	public void setItemDetailUrl(String itemDetailUrl) {
		this.itemDetailUrl = itemDetailUrl;
	}

	public String getItemPicUrl() {
		return itemPicUrl;
	}

	public void setItemPicUrl(String itemPicUrl) {
		this.itemPicUrl = itemPicUrl;
	}

	public String getItemPicThumbUrl() {
		return itemPicThumbUrl;
	}

	public void setItemPicThumbUrl(String itemPicThumbUrl) {
		this.itemPicThumbUrl = itemPicThumbUrl;
	}

	public List<byte[]> getItemImages() {
		return itemImages;
	}

	public void setItemImages(List<byte[]> itemImages) {
		this.itemImages = itemImages;
	}

	public Integer getItemCategoryid() {
		return itemCategoryid;
	}

	public void setItemCategoryid(Integer itemCategoryid) {
		this.itemCategoryid = itemCategoryid;
	}

	public Integer getItemPromotionId() {
		return itemPromotionId;
	}

	public void setItemPromotionId(Integer itemPromotionId) {
		this.itemPromotionId = itemPromotionId;
	}

	public String getItemTagIds() {
		return itemTagIds;
	}

	public void setItemTagIds(String itemTagIds) {
		this.itemTagIds = itemTagIds;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Date getItemCreatedTime() {
		return itemCreatedTime;
	}

	public void setItemCreatedTime(Date itemCreatedTime) {
		this.itemCreatedTime = itemCreatedTime;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public List<ShopPosItemImage> getShopPosItemImages() {
		return shopPosItemImages;
	}

	public void setShopPosItemImages(List<ShopPosItemImage> shopPosItemImages) {
		this.shopPosItemImages = shopPosItemImages;
	}

	public BigDecimal getItemLocalRegularPrice() {
		return itemLocalRegularPrice;
	}

	public void setItemLocalRegularPrice(BigDecimal itemLocalRegularPrice) {
		this.itemLocalRegularPrice = itemLocalRegularPrice;
	}

	public String getItemImageIds() {
		return itemImageIds;
	}

	public void setItemImageIds(String itemImageIds) {
		this.itemImageIds = itemImageIds;
	}

	public BigDecimal getLocalInventoryAmount() {
		return localInventoryAmount;
	}

	public void setLocalInventoryAmount(BigDecimal localInventoryAmount) {
		this.localInventoryAmount = localInventoryAmount;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	
}