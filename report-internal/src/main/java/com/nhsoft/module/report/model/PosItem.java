package com.nhsoft.module.report.model;

import com.nhsoft.module.report.util.AppConstants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class PosItem implements java.io.Serializable {

	private static final long serialVersionUID = -4598224475348745971L;
	@Id
	private Integer itemNum;
	private String systemBookCode;
	private String itemCode;
	private String itemBarcode;
	private String itemName;
	private String itemSpec;
	private String storeItemPinyin;
	private Integer itemType;
	private String itemUnit;
	private String itemDepartment;
	private String itemCategory;
	private BigDecimal itemRegularPrice;
	@Column(name = "itemLevel2_Price")
	private BigDecimal itemLevel2Price;
	private Boolean itemDelTag;
	private String itemBrand;
	private String itemCostMode;
	private Integer itemValidPeriod;
	private Boolean itemStockCeaseFlag;
	private Boolean itemSaleCeaseFlag;
	private String itemPurchaseScope;
	private BigDecimal itemCostPrice;
	private BigDecimal itemTransferPrice;
	private String itemCategoryCode;
	private String itemPlace;
	private Date itemCreateTime;
	private Boolean itemEliminativeFlag;
	private String itemPurchaseUnit;
	private BigDecimal itemPurchaseRate;
	private String itemInventoryUnit;
	private BigDecimal itemInventoryRate;
	private String itemTransferUnit;
	private BigDecimal itemTransferRate;
	private String itemWholesaleUnit;
	private BigDecimal itemWholesaleRate;
	private BigDecimal itemWholesalePrice;
	private String itemMethod;
	private BigDecimal itemGrossRate;
	private Integer itemStatus;
	private Integer itemTransferDay;

	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "itemNum", updatable=false, insertable=false)
	private List<ItemBar> itemBars = new ArrayList<ItemBar>();
	@OneToMany
	@Fetch(FetchMode.SUBSELECT)
	@JoinColumn(name = "itemNum", updatable=false, insertable=false)
	private List<ItemMatrix> itemMatrixs = new ArrayList<ItemMatrix>(); //lazy = false

	@Transient
	private AppUser appUser;
	
	public Integer getItemTransferDay() {
		return itemTransferDay;
	}
	
	public void setItemTransferDay(Integer itemTransferDay) {
		this.itemTransferDay = itemTransferDay;
	}
	
	public Integer getItemStatus() {
		return itemStatus;
	}
	
	public void setItemStatus(Integer itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	public String getSystemBookCode() {
		return systemBookCode;
	}
	
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
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
	
	public String getStoreItemPinyin() {
		return storeItemPinyin;
	}
	
	public void setStoreItemPinyin(String storeItemPinyin) {
		this.storeItemPinyin = storeItemPinyin;
	}
	
	public Integer getItemType() {
		return itemType;
	}
	
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
	
	public String getItemUnit() {
		return itemUnit;
	}
	
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	public String getItemDepartment() {
		return itemDepartment;
	}
	
	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}
	
	public String getItemCategory() {
		return itemCategory;
	}
	
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	
	public BigDecimal getItemRegularPrice() {
		return itemRegularPrice;
	}
	
	public void setItemRegularPrice(BigDecimal itemRegularPrice) {
		this.itemRegularPrice = itemRegularPrice;
	}
	
	public BigDecimal getItemLevel2Price() {
		return itemLevel2Price;
	}
	
	public void setItemLevel2Price(BigDecimal itemLevel2Price) {
		this.itemLevel2Price = itemLevel2Price;
	}
	
	public Boolean getItemDelTag() {
		return itemDelTag;
	}
	
	public void setItemDelTag(Boolean itemDelTag) {
		this.itemDelTag = itemDelTag;
	}
	
	public String getItemBrand() {
		return itemBrand;
	}
	
	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}
	
	public String getItemCostMode() {
		return itemCostMode;
	}
	
	public void setItemCostMode(String itemCostMode) {
		this.itemCostMode = itemCostMode;
	}
	
	public Integer getItemValidPeriod() {
		return itemValidPeriod;
	}
	
	public void setItemValidPeriod(Integer itemValidPeriod) {
		this.itemValidPeriod = itemValidPeriod;
	}
	
	public Boolean getItemStockCeaseFlag() {
		return itemStockCeaseFlag;
	}
	
	public void setItemStockCeaseFlag(Boolean itemStockCeaseFlag) {
		this.itemStockCeaseFlag = itemStockCeaseFlag;
	}
	
	public Boolean getItemSaleCeaseFlag() {
		return itemSaleCeaseFlag;
	}
	
	public void setItemSaleCeaseFlag(Boolean itemSaleCeaseFlag) {
		this.itemSaleCeaseFlag = itemSaleCeaseFlag;
	}
	
	public String getItemPurchaseScope() {
		return itemPurchaseScope;
	}
	
	public void setItemPurchaseScope(String itemPurchaseScope) {
		this.itemPurchaseScope = itemPurchaseScope;
	}
	
	public BigDecimal getItemCostPrice() {
		return itemCostPrice;
	}
	
	public void setItemCostPrice(BigDecimal itemCostPrice) {
		this.itemCostPrice = itemCostPrice;
	}
	
	public BigDecimal getItemTransferPrice() {
		return itemTransferPrice;
	}
	
	public void setItemTransferPrice(BigDecimal itemTransferPrice) {
		this.itemTransferPrice = itemTransferPrice;
	}
	
	public String getItemCategoryCode() {
		return itemCategoryCode;
	}
	
	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}
	
	public String getItemPlace() {
		return itemPlace;
	}
	
	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}
	
	public Date getItemCreateTime() {
		return itemCreateTime;
	}
	
	public void setItemCreateTime(Date itemCreateTime) {
		this.itemCreateTime = itemCreateTime;
	}
	
	public Boolean getItemEliminativeFlag() {
		return itemEliminativeFlag;
	}
	
	public void setItemEliminativeFlag(Boolean itemEliminativeFlag) {
		this.itemEliminativeFlag = itemEliminativeFlag;
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
	
	public BigDecimal getItemWholesalePrice() {
		return itemWholesalePrice;
	}
	
	public void setItemWholesalePrice(BigDecimal itemWholesalePrice) {
		this.itemWholesalePrice = itemWholesalePrice;
	}

	
	public String getItemMethod() {
		return itemMethod;
	}
	
	public void setItemMethod(String itemMethod) {
		this.itemMethod = itemMethod;
	}
	
	public BigDecimal getItemGrossRate() {
		return itemGrossRate;
	}
	
	public void setItemGrossRate(BigDecimal itemGrossRate) {
		this.itemGrossRate = itemGrossRate;
	}
	
	public List<ItemBar> getItemBars() {
		return itemBars;
	}
	
	public void setItemBars(List<ItemBar> itemBars) {
		this.itemBars = itemBars;
	}
	
	public List<ItemMatrix> getItemMatrixs() {
		return itemMatrixs;
	}
	
	public void setItemMatrixs(List<ItemMatrix> itemMatrixs) {
		this.itemMatrixs = itemMatrixs;
	}
	
	public AppUser getAppUser() {
		return appUser;
	}
	
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		PosItem other = (PosItem) obj;
		if (itemNum == null) {
			if (other.itemNum != null)
				return false;
		} else if (!itemNum.equals(other.itemNum))
			return false;
		return true;
	}
	
	public String getItemCostMode(Branch branch){
		if(itemCostMode.trim().equals(AppConstants.C_ITEM_COST_MODE_CENTER_MANUAL)){
			
			if(branch == null 
					|| branch.getId().getBranchNum().equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)
					|| branch.getBranchRdc()){
				return AppConstants.C_ITEM_COST_MODE_MANUAL;
			} else {
				return AppConstants.C_ITEM_COST_MODE_AVERAGE;
			}
			
		} else {
			return itemCostMode;
		}
	}

	public static PosItem get(Integer itemNum, List<PosItem> posItems) {
		if (posItems == null) {
			return null;
		}
		for (int i = 0, len = posItems.size(); i < len; i++) {
			if (posItems.get(i).getItemNum().equals(itemNum)) {
				return posItems.get(i);
			}
		}
		return null;
	}

}