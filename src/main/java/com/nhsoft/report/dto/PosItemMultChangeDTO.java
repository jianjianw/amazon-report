package com.nhsoft.report.dto;



import com.nhsoft.report.model.SaleCommission;
import com.nhsoft.report.shared.queryBuilder.PosItemQuery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PosItemMultChangeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 565622449572608225L;

	// 以下确定商品修改范围
	private PosItemQuery posIQuery;

	private Integer changeType;// 0:都改 1:销售提成;2:商品积分;3:计量单位;4:其他属性;5:商品毛利率;

	// 以下确定商品修改属性
	// 销售提成
	private SaleCommission saleCommission;

	// 商品积分
	private boolean itemPointActived;// 是否积分
	private Boolean pointActived;// 是否积分  乐盟用
	private BigDecimal itemPointValue;// 这个不为null就按这个值，为null时按下面规则来
	private String priceType;
	private BigDecimal priceMultiply;
	private String roundType;

	// 计量单位
	private String itemUnitGroup;
	private String itemUnit;
	private String itemPurchaseUnit;
	private BigDecimal itemPurchaseRate;
	private String itemInventoryUnit;
	private BigDecimal itemInventoryRate;
	private String itemTransferUnit;
	private BigDecimal itemTransferRate;
	private String itemWholesaleUnit;
	private BigDecimal itemWholesaleRate;
	private String itemAssistUnit;
	private BigDecimal itemAssistRate;

	// 其他属性，以下属性null时表示不修改
	private Boolean itemWholesaleFlag;// 是否允许在线订购
	private Boolean itemPriceTagFlag;// 打印价签
	private Boolean itemWeightFlag;// 是否称重
	private Boolean itemStockCeaseFlag;// 是否停购
	private Boolean itemSaleCeaseFlag;// 是否停售
	private Boolean itemPosChangePriceFlag;// 前台议价
	private Boolean itemPosDiscount;//允许前台折扣
	private List<Integer> managementTemplateNums;//经营范围
	private BigDecimal itemInTax;//进项税
	private BigDecimal itemOutTax;//销项税

	private String itemPurchaseScope;// 采购范围
	private Integer itemValidPeriod;// 有效天数
	private String itemCategoryCode;// 分类编码
	private String itemCategory;// 分类名称
	private String itemMethod;// 经营方式
	private Integer itemRemindPeriod;// 过期催销天数
	private String itemStorePlace;// 仓储位置
	private String itemDepartment;// 部门
	private BigDecimal itemGrossRate;// 联营扣率
	private String itemBrand;//品牌
	private Boolean itemPosPriceTagFlag;
	private Integer itemTransferDay;

	// 商品毛利率.
	private BigDecimal itemTransferGross;
	private Boolean itemTransferFixedGross;
	private BigDecimal itemWholesaleGross;
	private Boolean itemWholesaleFixedGross;
	private boolean changeTransferPrice;// 同时修改配送价
	private boolean changeWholesalePrice;// 同时修改批发价
	private BigDecimal itemWholesaleGross2;
	private Boolean itemWholesaleFixedGross2;
	private boolean changeWholesalePrice2;
	private BigDecimal itemWholesaleGross3;
	private Boolean itemWholesaleFixedGross3;
	private boolean changeWholesalePrice3;
	private BigDecimal itemWholesaleGross4;
	private Boolean itemWholesaleFixedGross4;
	private boolean changeWholesalePrice4;
	private boolean changeTransferGross;//同时修改毛利率
	private boolean changeWholesaleGross;//同时修改毛利率
	private boolean changeWholesaleGross2;//同时修改毛利率
	private boolean changeWholesaleGross3;//同时修改毛利率
	private boolean changeWholesaleGross4;//同时修改毛利率
	
	private Boolean itemTypeNormalToGrade; //标准商品是否转分级商品

	public BigDecimal getItemInTax() {
		return itemInTax;
	}

	public void setItemInTax(BigDecimal itemInTax) {
		this.itemInTax = itemInTax;
	}

	public BigDecimal getItemOutTax() {
		return itemOutTax;
	}

	public void setItemOutTax(BigDecimal itemOutTax) {
		this.itemOutTax = itemOutTax;
	}

	public Boolean getPointActived() {
		return pointActived;
	}

	public void setPointActived(Boolean pointActived) {
		this.pointActived = pointActived;
	}

	public Boolean getItemTypeNormalToGrade() {
		return itemTypeNormalToGrade;
	}

	public void setItemTypeNormalToGrade(Boolean itemTypeNormalToGrade) {
		this.itemTypeNormalToGrade = itemTypeNormalToGrade;
	}

	public boolean isChangeTransferGross() {
		return changeTransferGross;
	}

	public void setChangeTransferGross(boolean changeTransferGross) {
		this.changeTransferGross = changeTransferGross;
	}

	public boolean isChangeWholesaleGross() {
		return changeWholesaleGross;
	}

	public void setChangeWholesaleGross(boolean changeWholesaleGross) {
		this.changeWholesaleGross = changeWholesaleGross;
	}

	public boolean isChangeWholesaleGross2() {
		return changeWholesaleGross2;
	}

	public void setChangeWholesaleGross2(boolean changeWholesaleGross2) {
		this.changeWholesaleGross2 = changeWholesaleGross2;
	}

	public boolean isChangeWholesaleGross3() {
		return changeWholesaleGross3;
	}

	public void setChangeWholesaleGross3(boolean changeWholesaleGross3) {
		this.changeWholesaleGross3 = changeWholesaleGross3;
	}

	public boolean isChangeWholesaleGross4() {
		return changeWholesaleGross4;
	}

	public void setChangeWholesaleGross4(boolean changeWholesaleGross4) {
		this.changeWholesaleGross4 = changeWholesaleGross4;
	}

	public Integer getItemTransferDay() {
		return itemTransferDay;
	}

	public void setItemTransferDay(Integer itemTransferDay) {
		this.itemTransferDay = itemTransferDay;
	}

	public BigDecimal getItemWholesaleGross2() {
		return itemWholesaleGross2;
	}

	public void setItemWholesaleGross2(BigDecimal itemWholesaleGross2) {
		this.itemWholesaleGross2 = itemWholesaleGross2;
	}

	public Boolean getItemWholesaleFixedGross2() {
		return itemWholesaleFixedGross2;
	}

	public void setItemWholesaleFixedGross2(Boolean itemWholesaleFixedGross2) {
		this.itemWholesaleFixedGross2 = itemWholesaleFixedGross2;
	}

	public boolean isChangeWholesalePrice2() {
		return changeWholesalePrice2;
	}

	public void setChangeWholesalePrice2(boolean changeWholesalePrice2) {
		this.changeWholesalePrice2 = changeWholesalePrice2;
	}

	public BigDecimal getItemWholesaleGross3() {
		return itemWholesaleGross3;
	}

	public void setItemWholesaleGross3(BigDecimal itemWholesaleGross3) {
		this.itemWholesaleGross3 = itemWholesaleGross3;
	}

	public Boolean getItemWholesaleFixedGross3() {
		return itemWholesaleFixedGross3;
	}

	public void setItemWholesaleFixedGross3(Boolean itemWholesaleFixedGross3) {
		this.itemWholesaleFixedGross3 = itemWholesaleFixedGross3;
	}

	public boolean isChangeWholesalePrice3() {
		return changeWholesalePrice3;
	}

	public void setChangeWholesalePrice3(boolean changeWholesalePrice3) {
		this.changeWholesalePrice3 = changeWholesalePrice3;
	}

	public BigDecimal getItemWholesaleGross4() {
		return itemWholesaleGross4;
	}

	public void setItemWholesaleGross4(BigDecimal itemWholesaleGross4) {
		this.itemWholesaleGross4 = itemWholesaleGross4;
	}

	public Boolean getItemWholesaleFixedGross4() {
		return itemWholesaleFixedGross4;
	}

	public void setItemWholesaleFixedGross4(Boolean itemWholesaleFixedGross4) {
		this.itemWholesaleFixedGross4 = itemWholesaleFixedGross4;
	}

	public boolean isChangeWholesalePrice4() {
		return changeWholesalePrice4;
	}

	public void setChangeWholesalePrice4(boolean changeWholesalePrice4) {
		this.changeWholesalePrice4 = changeWholesalePrice4;
	}

	public Boolean getItemPosPriceTagFlag() {
		return itemPosPriceTagFlag;
	}

	public void setItemPosPriceTagFlag(Boolean itemPosPriceTagFlag) {
		this.itemPosPriceTagFlag = itemPosPriceTagFlag;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public PosItemQuery getPosIQuery() {
		return posIQuery;
	}

	public List<Integer> getManagementTemplateNums() {
		return managementTemplateNums;
	}

	public void setManagementTemplateNums(List<Integer> managementTemplateNums) {
		this.managementTemplateNums = managementTemplateNums;
	}

	public void setPosIQuery(PosItemQuery posIQuery) {
		this.posIQuery = posIQuery;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public SaleCommission getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(SaleCommission saleCommission) {
		this.saleCommission = saleCommission;
	}

	public boolean isItemPointActived() {
		return itemPointActived;
	}

	public void setItemPointActived(boolean itemPointActived) {
		this.itemPointActived = itemPointActived;
	}

	public BigDecimal getItemPointValue() {
		return itemPointValue;
	}

	public void setItemPointValue(BigDecimal itemPointValue) {
		this.itemPointValue = itemPointValue;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public BigDecimal getPriceMultiply() {
		return priceMultiply;
	}

	public void setPriceMultiply(BigDecimal priceMultiply) {
		this.priceMultiply = priceMultiply;
	}

	public String getRoundType() {
		return roundType;
	}

	public void setRoundType(String roundType) {
		this.roundType = roundType;
	}

	public String getItemUnitGroup() {
		return itemUnitGroup;
	}

	public void setItemUnitGroup(String itemUnitGroup) {
		this.itemUnitGroup = itemUnitGroup;
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

	public Boolean getItemWholesaleFlag() {
		return itemWholesaleFlag;
	}

	public void setItemWholesaleFlag(Boolean itemWholesaleFlag) {
		this.itemWholesaleFlag = itemWholesaleFlag;
	}

	public Boolean getItemPriceTagFlag() {
		return itemPriceTagFlag;
	}

	public void setItemPriceTagFlag(Boolean itemPriceTagFlag) {
		this.itemPriceTagFlag = itemPriceTagFlag;
	}

	public Boolean getItemWeightFlag() {
		return itemWeightFlag;
	}

	public void setItemWeightFlag(Boolean itemWeightFlag) {
		this.itemWeightFlag = itemWeightFlag;
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

	public Boolean getItemPosChangePriceFlag() {
		return itemPosChangePriceFlag;
	}

	public void setItemPosChangePriceFlag(Boolean itemPosChangePriceFlag) {
		this.itemPosChangePriceFlag = itemPosChangePriceFlag;
	}

	public String getItemPurchaseScope() {
		return itemPurchaseScope;
	}

	public void setItemPurchaseScope(String itemPurchaseScope) {
		this.itemPurchaseScope = itemPurchaseScope;
	}

	public Integer getItemValidPeriod() {
		return itemValidPeriod;
	}

	public void setItemValidPeriod(Integer itemValidPeriod) {
		this.itemValidPeriod = itemValidPeriod;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemMethod() {
		return itemMethod;
	}

	public void setItemMethod(String itemMethod) {
		this.itemMethod = itemMethod;
	}

	public Integer getItemRemindPeriod() {
		return itemRemindPeriod;
	}

	public void setItemRemindPeriod(Integer itemRemindPeriod) {
		this.itemRemindPeriod = itemRemindPeriod;
	}

	public String getItemStorePlace() {
		return itemStorePlace;
	}

	public void setItemStorePlace(String itemStorePlace) {
		this.itemStorePlace = itemStorePlace;
	}

	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public BigDecimal getItemGrossRate() {
		return itemGrossRate;
	}

	public void setItemGrossRate(BigDecimal itemGrossRate) {
		this.itemGrossRate = itemGrossRate;
	}

	public BigDecimal getItemTransferGross() {
		return itemTransferGross;
	}

	public void setItemTransferGross(BigDecimal itemTransferGross) {
		this.itemTransferGross = itemTransferGross;
	}

	public Boolean getItemTransferFixedGross() {
		return itemTransferFixedGross;
	}

	public void setItemTransferFixedGross(Boolean itemTransferFixedGross) {
		this.itemTransferFixedGross = itemTransferFixedGross;
	}

	public BigDecimal getItemWholesaleGross() {
		return itemWholesaleGross;
	}

	public void setItemWholesaleGross(BigDecimal itemWholesaleGross) {
		this.itemWholesaleGross = itemWholesaleGross;
	}

	public Boolean getItemWholesaleFixedGross() {
		return itemWholesaleFixedGross;
	}

	public void setItemWholesaleFixedGross(Boolean itemWholesaleFixedGross) {
		this.itemWholesaleFixedGross = itemWholesaleFixedGross;
	}

	public boolean isChangeTransferPrice() {
		return changeTransferPrice;
	}

	public void setChangeTransferPrice(boolean changeTransferPrice) {
		this.changeTransferPrice = changeTransferPrice;
	}

	public boolean isChangeWholesalePrice() {
		return changeWholesalePrice;
	}

	public void setChangeWholesalePrice(boolean changeWholesalePrice) {
		this.changeWholesalePrice = changeWholesalePrice;
	}

	public Boolean getItemPosDiscount() {
		return itemPosDiscount;
	}

	public void setItemPosDiscount(Boolean itemPosDiscount) {
		this.itemPosDiscount = itemPosDiscount;
	}

}
