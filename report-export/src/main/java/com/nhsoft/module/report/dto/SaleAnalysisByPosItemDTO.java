package com.nhsoft.module.report.dto;


import java.math.BigDecimal;
import java.util.List;

public class SaleAnalysisByPosItemDTO extends SaleAnalysisDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342405487990271691L;
	private String topCategoryName;
	private String topCategoryCode;
	private String categoryName;
	private String categoryCode;
	private String itemName;
	private String itemCode;
	private String spec;
	private String unit;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Integer itemGradeNum;
	private BigDecimal itemDiscount;//折扣金额
	private Integer saleBranchCount;//销售门店数

	private Integer branchNum;
	private String branchName;
	private String bizday;
	private List<ItemExtendAttributeDTO> itemExtendAttributes;
	private String itemBarCode;

	public SaleAnalysisByPosItemDTO() {
		setSaleNum(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
		setReturnNum(BigDecimal.ZERO);
		setReturnMoney(BigDecimal.ZERO);
		setPresentNum(BigDecimal.ZERO);
		setPresentMoney(BigDecimal.ZERO);
		setTotalMoney(BigDecimal.ZERO);
		setTotalNum(BigDecimal.ZERO);
		setCountTotal(BigDecimal.ZERO);
		setSaleAssist(BigDecimal.ZERO);
		setReturnAssist(BigDecimal.ZERO);
		setPresentAssist(BigDecimal.ZERO);
		setItemDiscount(BigDecimal.ZERO);
		setSaleBranchCount(0);
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public List<ItemExtendAttributeDTO> getItemExtendAttributes() {
		return itemExtendAttributes;
	}

	public void setItemExtendAttributes(List<ItemExtendAttributeDTO> itemExtendAttributes) {
		this.itemExtendAttributes = itemExtendAttributes;
	}

	public Integer getSaleBranchCount() {
		return saleBranchCount;
	}

	public void setSaleBranchCount(Integer saleBranchCount) {
		this.saleBranchCount = saleBranchCount;
	}

	public String getTopCategoryName() {
		return topCategoryName;
	}

	public void setTopCategoryName(String topCategoryName) {
		this.topCategoryName = topCategoryName;
	}

	public String getTopCategoryCode() {
		return topCategoryCode;
	}

	public void setTopCategoryCode(String topCategoryCode) {
		this.topCategoryCode = topCategoryCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public BigDecimal getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(BigDecimal itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public Integer getItemGradeNum() {
		return itemGradeNum;
	}

	public void setItemGradeNum(Integer itemGradeNum) {
		this.itemGradeNum = itemGradeNum;
	}

	public String getItemBarCode() {
		return itemBarCode;
	}

	public void setItemBarCode(String itemBarCode) {
		this.itemBarCode = itemBarCode;
	}
}
