package com.nhsoft.module.report.dto;


import com.nhsoft.module.report.annotation.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class RequestAnalysisDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2158286046542727645L;
	@ReportKey
	private Integer itemNum;
	private String itemCode;
	private String itemBarcode;
	private String abc;
	private String itemName;
	private String itemSpec;
	private Boolean itemSaleCease;
	private Boolean itemStockCease;
	private BigDecimal itemSalePrice;
	private BigDecimal itemLevel2Price;
	private String itemUnit;
	private BigDecimal saleQty;
	private BigDecimal inventoryQty;
	private String itemTransferUnit;
	private BigDecimal itemTransferRate;
	private Integer supplierNum;
	private String supplierCode;
	private String supplierName;
	private String supplierMethod;
	private String supplierKind;
	private Integer itemType;
	private String itemPurchaseScope;
	private List<RequestAnalysisDTO> details;

	public Boolean getItemStockCease() {
		return itemStockCease;
	}

	public void setItemStockCease(Boolean itemStockCease) {
		this.itemStockCease = itemStockCease;
	}

	public String getItemPurchaseScope() {
		return itemPurchaseScope;
	}
	
	public void setItemPurchaseScope(String itemPurchaseScope) {
		this.itemPurchaseScope = itemPurchaseScope;
	}
	
	public String getSupplierKind() {
		return supplierKind;
	}

	public void setSupplierKind(String supplierKind) {
		this.supplierKind = supplierKind;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public List<RequestAnalysisDTO> getDetails() {
		return details;
	}

	public void setDetails(List<RequestAnalysisDTO> details) {
		this.details = details;
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
	
	public String getItemBarcode() {
		return itemBarcode;
	}
	
	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}
	
	public String getAbc() {
		return abc;
	}
	
	public void setAbc(String abc) {
		this.abc = abc;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemSpec() {
		return itemSpec;
	}
	
	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}
	
	public BigDecimal getItemSalePrice() {
		return itemSalePrice;
	}
	
	public void setItemSalePrice(BigDecimal itemSalePrice) {
		this.itemSalePrice = itemSalePrice;
	}
	
	public String getItemUnit() {
		return itemUnit;
	}
	
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	public BigDecimal getSaleQty() {
		return saleQty;
	}
	
	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}
	
	public BigDecimal getInventoryQty() {
		return inventoryQty;
	}
	
	public void setInventoryQty(BigDecimal inventoryQty) {
		this.inventoryQty = inventoryQty;
	}
	
	public String getItemTransferUnit() {
		return itemTransferUnit;
	}
	
	public void setItemTransferUnit(String itemTransferUnit) {
		this.itemTransferUnit = itemTransferUnit;
	}
	
	public BigDecimal getItemTransferRate() {
		return itemTransferRate;
	}
	
	public void setItemTransferRate(BigDecimal itemTransferRate) {
		this.itemTransferRate = itemTransferRate;
	}
	
	public Integer getSupplierNum() {
		return supplierNum;
	}
	
	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
	
	public String getSupplierCode() {
		return supplierCode;
	}
	
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getSupplierMethod() {
		return supplierMethod;
	}
	
	public void setSupplierMethod(String supplierMethod) {
		this.supplierMethod = supplierMethod;
	}

	public Boolean getItemSaleCease() {
		return itemSaleCease;
	}

	public void setItemSaleCease(Boolean itemSaleCease) {
		this.itemSaleCease = itemSaleCease;
	}

	public BigDecimal getItemLevel2Price() {
		return itemLevel2Price;
	}

	public void setItemLevel2Price(BigDecimal itemLevel2Price) {
		this.itemLevel2Price = itemLevel2Price;
	}
	
}
