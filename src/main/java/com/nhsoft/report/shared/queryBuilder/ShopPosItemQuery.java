package com.nhsoft.report.shared.queryBuilder;


public class ShopPosItemQuery implements java.io.Serializable {

	private static final long serialVersionUID = -5109960546148847470L;
	private String systemBookCode;
	private String itemName; // 商品名称
	private String ItemType; // 可选值：for_shelved（已下架的）/ sold_out（已售罄的） / on_sale (当前在售)
	private String sortField;
	private String sortType;
	private Integer storehouseNum;
		
	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

}