package com.nhsoft.report.shared.queryBuilder;

import java.util.List;

public class InventoryAnalysisQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100195567877649367L;

	public InventoryAnalysisQuery() {
	}

	public InventoryAnalysisQuery(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	private Integer branchNum;
	private Integer centerBranchNum;
	private List<String> categoryCodes;
	private List<Integer> storeItemNums;
	private Integer supplierNum;
	private Boolean isShowAll;
	private int indexUnit;
	private boolean findCount = false; //只统计数量 展示用属性不查询
	
	private String unitType;
	private boolean isRule1 = false;//库存量<=补货订购点
	private boolean isRule2 = false;//库存量+在订量<=补货订购点
	private boolean isRule3 = false;//库存量<=基础库存
	private boolean isRule4 = false;//库存量+在订量<=基础库存
	private boolean isRule5 = false;//若补货订购点为0 按rule4  否则按rule2	
	private boolean isRule6 = false;//库存量< 库存上限
	private Integer suggestionType;//建议订量计算规则：1、日均销量*订货周期；2、按库存上限补足 3、补货订购量4、上期销量-库存量
	private Integer lastDays;//最近天数
	
	private String posItemDepartmentCode;//查询条件部门
	private boolean isSubBuying;//是否减去在订量
	private boolean isAddRequest;//是否要货缺货
	private boolean isFindSale;//销量是否包含前台销售
	private boolean isFindOut;//销量是否包含调出单
	private boolean isFindWhole;//销量是否包含批发销售
	private boolean isFindAdjustOut;//销量是否包含调整出库
	private boolean isQueryAssemble = false;//是否只查询制单组合商品
	private Integer storehouseNum;
	private List<Integer> itemNums;
	private Boolean defaultSupplier;//主供应商
	
	
	public List<Integer> getItemNums() {
		return itemNums;
	}

	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public boolean isRule6() {
		return isRule6;
	}

	public void setRule6(boolean isRule6) {
		this.isRule6 = isRule6;
	}

	public boolean isFindAdjustOut() {
		return isFindAdjustOut;
	}

	public void setFindAdjustOut(boolean isFindAdjustOut) {
		this.isFindAdjustOut = isFindAdjustOut;
	}

	public boolean isQueryAssemble() {
		return isQueryAssemble;
	}

	public void setQueryAssemble(boolean isQueryAssemble) {
		this.isQueryAssemble = isQueryAssemble;
	}

	public boolean isRule5() {
		return isRule5;
	}

	public void setRule5(boolean isRule5) {
		this.isRule5 = isRule5;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getCenterBranchNum() {
		return centerBranchNum;
	}

	public void setCenterBranchNum(Integer centerBranchNum) {
		this.centerBranchNum = centerBranchNum;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public List<Integer> getStoreItemNums() {
		return storeItemNums;
	}

	public void setStoreItemNums(List<Integer> storeItemNums) {
		this.storeItemNums = storeItemNums;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public Boolean getIsShowAll() {
		return isShowAll;
	}

	public void setIsShowAll(Boolean isShowAll) {
		this.isShowAll = isShowAll;
	}

	@Override
	public boolean checkQueryBuild() {
		return false;
	}

	public int getIndexUnit() {
		return indexUnit;
	}

	public void setIndexUnit(int indexUnit) {
		this.indexUnit = indexUnit;
	}

	public boolean isFindCount() {
		return findCount;
	}

	public void setFindCount(boolean findCount) {
		this.findCount = findCount;
	}

	public boolean isRule1() {
		return isRule1;
	}

	public void setRule1(boolean isRule1) {
		this.isRule1 = isRule1;
	}

	public boolean isRule2() {
		return isRule2;
	}

	public void setRule2(boolean isRule2) {
		this.isRule2 = isRule2;
	}

	public boolean isRule3() {
		return isRule3;
	}

	public void setRule3(boolean isRule3) {
		this.isRule3 = isRule3;
	}

	public boolean isRule4() {
		return isRule4;
	}

	public void setRule4(boolean isRule4) {
		this.isRule4 = isRule4;
	}

	public Integer getSuggestionType() {
		return suggestionType;
	}

	public void setSuggestionType(Integer suggestionType) {
		this.suggestionType = suggestionType;
	}

	public Integer getLastDays() {
		return lastDays;
	}

	public void setLastDays(Integer lastDays) {
		this.lastDays = lastDays;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getPosItemDepartmentCode() {
		return posItemDepartmentCode;
	}

	public void setPosItemDepartmentCode(String posItemDepartmentCode) {
		this.posItemDepartmentCode = posItemDepartmentCode;
	}

	public boolean isSubBuying() {
		return isSubBuying;
	}

	public void setSubBuying(boolean isSubBuying) {
		this.isSubBuying = isSubBuying;
	}

	public boolean isAddRequest() {
		return isAddRequest;
	}

	public void setAddRequest(boolean isAddRequest) {
		this.isAddRequest = isAddRequest;
	}

	public boolean isFindSale() {
		return isFindSale;
	}

	public void setFindSale(boolean isFindSale) {
		this.isFindSale = isFindSale;
	}

	public boolean isFindOut() {
		return isFindOut;
	}

	public void setFindOut(boolean isFindOut) {
		this.isFindOut = isFindOut;
	}

	public boolean isFindWhole() {
		return isFindWhole;
	}

	public void setFindWhole(boolean isFindWhole) {
		this.isFindWhole = isFindWhole;
	}
	
	public String getKey() {
		StringBuffer sb = new StringBuffer();
		sb.append(systemBookCode);
		if(branchNum != null){
			sb.append("|").append(branchNum.toString());
			
		}
		if(centerBranchNum != null){
			sb.append("|").append(centerBranchNum.toString());
			
		}
		if(categoryCodes != null){
			sb.append("categoryCodes:");
			for(int i = 0;i < categoryCodes.size();i++){
				sb.append(categoryCodes.get(i));
			}
		}
		if(storeItemNums != null){
			sb.append("storeItemNums:");
			for(int i = 0;i < storeItemNums.size();i++){
				sb.append(storeItemNums.get(i));
			}
		}
		if(supplierNum != null){
			sb.append("|").append(supplierNum.toString());
			
		}
		if(isShowAll != null){
			sb.append("|").append(isShowAll.toString());
			
		}
		sb.append("|").append(indexUnit);
		sb.append("|").append(findCount);
		if(unitType != null){
			sb.append("|").append(unitType.toString());
			
		}
		sb.append("|").append(isRule1);
		sb.append("|").append(isRule2);
		sb.append("|").append(isRule3);
		sb.append("|").append(isRule4);
		sb.append("|").append(isRule5);
		sb.append("|").append(isRule6);
		if(suggestionType != null){
			sb.append("|").append(suggestionType.toString());
			
		}
		if(lastDays != null){
			sb.append("|").append(lastDays.toString());
			
		}
		if(posItemDepartmentCode != null){
			sb.append("|").append(posItemDepartmentCode.toString());
			
		}
		sb.append("|").append(isSubBuying);
		sb.append("|").append(isAddRequest);
		sb.append("|").append(isFindSale);
		sb.append("|").append(isFindOut);
		sb.append("|").append(isFindWhole);
		sb.append("|").append(isFindAdjustOut);
		sb.append("|").append(isQueryAssemble);
		if(storehouseNum != null){
			sb.append("|").append(storehouseNum.toString());
			
		}
		return sb.toString();
	}
	
	public Boolean getDefaultSupplier() {
		return defaultSupplier;
	}
	
	public void setDefaultSupplier(Boolean defaultSupplier) {
		this.defaultSupplier = defaultSupplier;
	}
}
