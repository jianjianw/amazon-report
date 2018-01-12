package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PosItemQuery extends QueryBuilder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 924046220160477532L;
	private Integer branchNum;//查询门店
	private String categoryCode;// 类别
	private Integer supplierNum;// 供应商
	private Boolean defaultSupplier;// 是否主供应商
	
	private Boolean isSenior;// true 查询var. false 是查询下面的。
	private String itemCode; // 代码
	private String itemBarCode;// 条码
	private String itemPinyin;// 速记码
	private String itemName;// 名称
	private Integer itemType; //类型
	private String itemBrand;
	private String filterType;
	private boolean isRdc = false;
	
	private List<String> categoryCodes;
	private List<String> filterCategoryCodes;//要过滤的类别
	private String itemTreeBrand;
	private String var;
	private Boolean saleCrease;// 停售
	private Boolean stockCrease;// 停购
	private Boolean dromCrease; //休眠商品
	private Boolean newCrease;  //新品
	private BigDecimal regularPriceFrom;// 标准售价大于
	private BigDecimal regularPriceTo;// 标准售价小于
	private BigDecimal minPriceFrom;// 最低价大于
	private BigDecimal minPriceTo;// 最低价小于
	private BigDecimal transferPriceFrom;// 配送价大于
	private BigDecimal transferPriceTo;// 配送价小于
	private BigDecimal purchasePriceFrom;// 采购价大于
	private BigDecimal purchasePriceTo;// 采购价小于
	private BigDecimal wholePriceFrom;// 批发价大于
	private BigDecimal wholePriceTo;// 批发价小于
	private String itemCostMode;
	private Boolean isFindNoStock;
	private String itemMethod;
	private String itemPuchaseScope;
	private String itemPlace;
	private Integer newDay;  //商品引进日期在几天以内被视为新品
	private List<Integer> unSaleItemNums; //停售的商品
	private Date itemCreateTimeFrom;
	private Date itemCreateTimeTo;
	private boolean filterTypeFilterSleepItem = true;//是否过滤休眠商品
	private List<Integer> itemNums;
	private String orderType; //单据类型
	private List<Integer> filterItemTypes = new ArrayList<Integer>(); //要过滤的商品类型
	private List<Integer> itemTypes = new ArrayList<Integer>(); //不要过滤的商品类型
	private Boolean normalCrease; //正常  除去停购、停售、休眠商品
	private Date itemLastEditTime;
	private String itemMethods;
	private Boolean isFindWeedOut = false; //是否查询淘汰商品
	private Boolean isFindItemGrade = false; //是否查询分级
	private Integer managementTemplateNum; //经营范围
	private String itemDepartment; //部门
	private List<String> itemDepartments;
	private Boolean itemManufactureFlag;
	private Boolean queryKit;//是否查询组合商品明细
	private Boolean filterInElementKit;//过滤已存在ItemElementKit中的商品
	private Boolean filterInCategoryProfit;//过滤已存在毛利率调整中的商品
	private List<String> queryProperties;
	private boolean queryAll = false;//是否查询所有商品 主要用于报表
	
	public List<String> getItemDepartments() {
		return itemDepartments;
	}
	
	public void setItemDepartments(List<String> itemDepartments) {
		this.itemDepartments = itemDepartments;
	}
	
	public boolean isQueryAll() {
		return queryAll;
	}
	
	public void setQueryAll(boolean queryAll) {
		this.queryAll = queryAll;
	}
	
	public Boolean getSenior() {
		return isSenior;
	}
	
	public void setSenior(Boolean senior) {
		isSenior = senior;
	}
	
	public Boolean getFindNoStock() {
		return isFindNoStock;
	}
	
	public void setFindNoStock(Boolean findNoStock) {
		isFindNoStock = findNoStock;
	}
	
	public Boolean getFindWeedOut() {
		return isFindWeedOut;
	}
	
	public void setFindWeedOut(Boolean findWeedOut) {
		isFindWeedOut = findWeedOut;
	}
	
	public Boolean getFindItemGrade() {
		return isFindItemGrade;
	}
	
	public void setFindItemGrade(Boolean findItemGrade) {
		isFindItemGrade = findItemGrade;
	}
	
	public Boolean getFilterInElementKit() {
		return filterInElementKit;
	}
	
	public void setFilterInElementKit(Boolean filterInElementKit) {
		this.filterInElementKit = filterInElementKit;
	}
	
	public Boolean getFilterInCategoryProfit() {
		return filterInCategoryProfit;
	}
	
	public void setFilterInCategoryProfit(Boolean filterInCategoryProfit) {
		this.filterInCategoryProfit = filterInCategoryProfit;
	}
	
	public List<String> getQueryProperties() {
		return queryProperties;
	}
	
	public void setQueryProperties(List<String> queryProperties) {
		this.queryProperties = queryProperties;
	}
	
	public Boolean getDefaultSupplier() {
		return defaultSupplier;
	}

	public void setDefaultSupplier(Boolean defaultSupplier) {
		this.defaultSupplier = defaultSupplier;
	}

	private boolean isPaging = true;

	public Boolean getQueryKit() {
		return queryKit;
	}

	public void setQueryKit(Boolean queryKit) {
		this.queryKit = queryKit;
	}

	public Boolean getItemManufactureFlag() {
		return itemManufactureFlag;
	}

	public void setItemManufactureFlag(Boolean itemManufactureFlag) {
		this.itemManufactureFlag = itemManufactureFlag;
	}

	public List<String> getFilterCategoryCodes() {
		return filterCategoryCodes;
	}

	public void setFilterCategoryCodes(List<String> filterCategoryCodes) {
		this.filterCategoryCodes = filterCategoryCodes;
	}

	public String getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(String itemDepartment) {
		this.itemDepartment = itemDepartment;
	}

	public Integer getManagementTemplateNum() {
		return managementTemplateNum;
	}

	public void setManagementTemplateNum(Integer managementTemplateNum) {
		this.managementTemplateNum = managementTemplateNum;
	}

	public List<Integer> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<Integer> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public Boolean getIsFindItemGrade() {
		return isFindItemGrade;
	}

	public void setIsFindItemGrade(Boolean isFindItemGrade) {
		this.isFindItemGrade = isFindItemGrade;
	}

	public String getItemMethods() {
		return itemMethods;
	}

	public void setItemMethods(String itemMethods) {
		this.itemMethods = itemMethods;
	}

	public Date getItemLastEditTime() {
		return itemLastEditTime;
	}

	public void setItemLastEditTime(Date itemLastEditTime) {
		this.itemLastEditTime = itemLastEditTime;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Boolean getIsSenior() {
		return isSenior;
	}

	public void setIsSenior(Boolean isSenior) {
		this.isSenior = isSenior;
	}

	public Boolean getSaleCrease() {
		return saleCrease;
	}

	public void setSaleCrease(Boolean saleCrease) {
		this.saleCrease = saleCrease;
	}

	public Boolean getStockCrease() {
		return stockCrease;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setStockCrease(Boolean stockCrease) {
		this.stockCrease = stockCrease;
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

	public String getItemPinyin() {
		return itemPinyin;
	}

	public void setItemPinyin(String itemPinyin) {
		this.itemPinyin = itemPinyin;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getRegularPriceFrom() {
		return regularPriceFrom;
	}
	
	public String getItemCostMode() {
		return itemCostMode;
	}

	public void setItemCostMode(String itemCostMode) {
		this.itemCostMode = itemCostMode;
	}

	public void setRegularPriceFrom(BigDecimal regularPriceFrom) {
		this.regularPriceFrom = regularPriceFrom;
	}

	public BigDecimal getRegularPriceTo() {
		return regularPriceTo;
	}

	public void setRegularPriceTo(BigDecimal regularPriceTo) {
		this.regularPriceTo = regularPriceTo;
	}

	public Boolean getIsFindNoStock() {
		return isFindNoStock;
	}

	public void setIsFindNoStock(Boolean isFindNoStock) {
		this.isFindNoStock = isFindNoStock;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getMinPriceFrom() {
		return minPriceFrom;
	}

	public void setMinPriceFrom(BigDecimal minPriceFrom) {
		this.minPriceFrom = minPriceFrom;
	}

	public BigDecimal getMinPriceTo() {
		return minPriceTo;
	}

	public void setMinPriceTo(BigDecimal minPriceTo) {
		this.minPriceTo = minPriceTo;
	}

	public BigDecimal getTransferPriceFrom() {
		return transferPriceFrom;
	}

	public void setTransferPriceFrom(BigDecimal transferPriceFrom) {
		this.transferPriceFrom = transferPriceFrom;
	}

	public BigDecimal getTransferPriceTo() {
		return transferPriceTo;
	}

	public void setTransferPriceTo(BigDecimal transferPriceTo) {
		this.transferPriceTo = transferPriceTo;
	}

	public BigDecimal getPurchasePriceFrom() {
		return purchasePriceFrom;
	}

	public void setPurchasePriceFrom(BigDecimal purchasePriceFrom) {
		this.purchasePriceFrom = purchasePriceFrom;
	}

	public BigDecimal getPurchasePriceTo() {
		return purchasePriceTo;
	}

	public void setPurchasePriceTo(BigDecimal purchasePriceTo) {
		this.purchasePriceTo = purchasePriceTo;
	}

	public BigDecimal getWholePriceFrom() {
		return wholePriceFrom;
	}

	public void setWholePriceFrom(BigDecimal wholePriceFrom) {
		this.wholePriceFrom = wholePriceFrom;
	}

	public BigDecimal getWholePriceTo() {
		return wholePriceTo;
	}

	public void setWholePriceTo(BigDecimal wholePriceTo) {
		this.wholePriceTo = wholePriceTo;
	}

	public String getItemBrand() {
		return itemBrand;
	}

	public void setItemBrand(String itemBrand) {
		this.itemBrand = itemBrand;
	}

	public String getItemMethod() {
		return itemMethod;
	}

	public void setItemMethod(String itemMethod) {
		this.itemMethod = itemMethod;
	}

	public String getItemPuchaseScope() {
		return itemPuchaseScope;
	}

	public void setItemPuchaseScope(String itemPuchaseScope) {
		this.itemPuchaseScope = itemPuchaseScope;
	}

	public String getItemPlace() {
		return itemPlace;
	}

	public void setItemPlace(String itemPlace) {
		this.itemPlace = itemPlace;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public String getItemTreeBrand() {
		return itemTreeBrand;
	}

	public void setItemTreeBrand(String itemTreeBrand) {
		this.itemTreeBrand = itemTreeBrand;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public boolean isRdc() {
		return isRdc;
	}

	public void setRdc(boolean isRdc) {
		this.isRdc = isRdc;
	}

	public Boolean getDromCrease() {
		return dromCrease;
	}

	public void setDromCrease(Boolean dromCrease) {
		this.dromCrease = dromCrease;
	}

	public Boolean getNewCrease() {
		return newCrease;
	}

	public void setNewCrease(Boolean newCrease) {
		this.newCrease = newCrease;
	}

	public Integer getNewDay() {
		return newDay;
	}

	public void setNewDay(Integer newDay) {
		this.newDay = newDay;
	}

	public List<Integer> getUnSaleItemNums() {
		return unSaleItemNums;
	}

	public void setUnSaleItemNums(List<Integer> unSaleItemNums) {
		this.unSaleItemNums = unSaleItemNums;
	}

	public boolean isFilterTypeFilterSleepItem() {
		return filterTypeFilterSleepItem;
	}

	public void setFilterTypeFilterSleepItem(boolean filterTypeFilterSleepItem) {
		this.filterTypeFilterSleepItem = filterTypeFilterSleepItem;
	}

	public Date getItemCreateTimeFrom() {
		return itemCreateTimeFrom;
	}

	public void setItemCreateTimeFrom(Date itemCreateTimeFrom) {
		this.itemCreateTimeFrom = itemCreateTimeFrom;
	}

	public Date getItemCreateTimeTo() {
		return itemCreateTimeTo;
	}

	public void setItemCreateTimeTo(Date itemCreateTimeTo) {
		this.itemCreateTimeTo = itemCreateTimeTo;
	}

	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<Integer> getFilterItemTypes() {
		return filterItemTypes;
	}

	public void setFilterItemTypes(List<Integer> filterItemTypes) {
		this.filterItemTypes = filterItemTypes;
	}

	public Boolean getNormalCrease() {
		return normalCrease;
	}

	public void setNormalCrease(Boolean normalCrease) {
		this.normalCrease = normalCrease;
	}

	public Boolean getIsFindWeedOut() {
		return isFindWeedOut;
	}

	public void setIsFindWeedOut(Boolean isFindWeedOut) {
		this.isFindWeedOut = isFindWeedOut;
	}

	public boolean aliasBars(){
		if(var != null || itemBarCode != null){
			return true;
		}
		return false;
	}


}
