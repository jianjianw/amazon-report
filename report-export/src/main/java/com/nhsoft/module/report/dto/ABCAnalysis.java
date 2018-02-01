package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ABCAnalysis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5262127295908249542L;
	private String ABC;  								//ABC
	private String posItemType;					//商品类型
	private String posItemTypeCode;
	private String posItemCode;					//商品代码
	private String posItemName;					//商品名称
	private String spec;								//规格
	private String unit;									//计量单位
	private BigDecimal analysisContent;		//分析内容（销售金额 or 销售毛利）
	private BigDecimal rate;							//占比
	private BigDecimal totalRate = BigDecimal.ZERO;					//累计占比
	private BigDecimal totalMoney;
	private Integer itemNum;
	private Integer itemMatrixNum;
	private BigDecimal saleQty;
	private BigDecimal inventoryQty;
	private String state;
	
	private BigDecimal aLevelCount;
	private BigDecimal bLevelCount;
	private BigDecimal cLevelCount;

	public BigDecimal getInventoryQty() {
		return inventoryQty;
	}

	public void setInventoryQty(BigDecimal inventoryQty) {
		this.inventoryQty = inventoryQty;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public BigDecimal getaLevelCount() {
		return aLevelCount;
	}

	public void setaLevelCount(BigDecimal aLevelCount) {
		this.aLevelCount = aLevelCount;
	}

	public BigDecimal getbLevelCount() {
		return bLevelCount;
	}

	public void setbLevelCount(BigDecimal bLevelCount) {
		this.bLevelCount = bLevelCount;
	}

	public BigDecimal getcLevelCount() {
		return cLevelCount;
	}

	public void setcLevelCount(BigDecimal cLevelCount) {
		this.cLevelCount = cLevelCount;
	}

	public String getPosItemTypeCode() {
		return posItemTypeCode;
	}

	public void setPosItemTypeCode(String posItemTypeCode) {
		this.posItemTypeCode = posItemTypeCode;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public ABCAnalysis(){
		setAnalysisContent(BigDecimal.ZERO);
		
	}
	
	public String getPosItemName() {
		return posItemName;
	}
	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}
	public String getABC() {
		return ABC;
	}
	public void setABC(String ABC) {
		this.ABC = ABC;
	}
	public String getPosItemType() {
		return posItemType;
	}
	public void setPosItemType(String posItemType) {
		this.posItemType = posItemType;
	}
	public String getPosItemCode() {
		return posItemCode;
	}
	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
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
	
	public BigDecimal getAnalysisContent() {
		return analysisContent;
	}
	public void setAnalysisContent(BigDecimal analysisContent) {
		this.analysisContent = analysisContent;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(BigDecimal saleQty) {
		this.saleQty = saleQty;
	}

}
