package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WholesaleProfitByPosItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9169351846416597502L;
	private String posItemTypeCode; // 商品类别代码
	private String posItemTypeName; // 商品类别名称
	private String posItemCode; // 商品代码
	private String posItemName; // 商品名称
	private Integer posItemNum; // 商品编号
	private Integer itemMatrixNum;
	private String spec; // 规格
	private String unit; // 单位
	private BigDecimal saleNum; // 销售数量
	private BigDecimal saleMoney; // 销售金额
	private BigDecimal saleCost; // 销售成本
	private BigDecimal saleProfit; // 销售毛利
	private BigDecimal saleProfitRate; // 销售毛利率
	private BigDecimal returnNum; // 退货数量
	private BigDecimal returnMoney; // 退货金额
	private BigDecimal returnCost; // 退货成本
	private BigDecimal returnProfit; // 退货毛利
	private BigDecimal returnProfitRate; // 退货毛利率
	private BigDecimal reSaleCost; // 零售金额
	private BigDecimal reSaleProfit; // 零售毛利
	private BigDecimal reSaleProfitRate; // 零售毛利率
	private BigDecimal saleUseQty; // 销售数量
	private BigDecimal saleBaseQty;//基本数量
	private String baseUnit; // 单位
	private BigDecimal presentQty;
	private BigDecimal presentUseQty;
	private BigDecimal presentCostMoney;
	private BigDecimal presentMoney;
	
	public WholesaleProfitByPosItem(){
		setSaleMoney(BigDecimal.ZERO);
		setSaleCost(BigDecimal.ZERO);
		setSaleProfit(BigDecimal.ZERO);
		setReSaleCost(BigDecimal.ZERO);
		setReSaleProfit(BigDecimal.ZERO);
		setSaleNum(BigDecimal.ZERO);
		setSaleUseQty(BigDecimal.ZERO);
		setSaleBaseQty(BigDecimal.ZERO);
		setPresentQty(BigDecimal.ZERO);
		setPresentUseQty(BigDecimal.ZERO);
		setPresentCostMoney(BigDecimal.ZERO);
		setPresentMoney(BigDecimal.ZERO);
		
	}

	public BigDecimal getPresentQty() {
		return presentQty;
	}

	public void setPresentQty(BigDecimal presentQty) {
		this.presentQty = presentQty;
	}

	public BigDecimal getPresentUseQty() {
		return presentUseQty;
	}

	public void setPresentUseQty(BigDecimal presentUseQty) {
		this.presentUseQty = presentUseQty;
	}

	public BigDecimal getPresentCostMoney() {
		return presentCostMoney;
	}

	public void setPresentCostMoney(BigDecimal presentCostMoney) {
		this.presentCostMoney = presentCostMoney;
	}

	public BigDecimal getPresentMoney() {
		return presentMoney;
	}

	public void setPresentMoney(BigDecimal presentMoney) {
		this.presentMoney = presentMoney;
	}

	public BigDecimal getSaleBaseQty() {
		return saleBaseQty;
	}

	public void setSaleBaseQty(BigDecimal saleBaseQty) {
		this.saleBaseQty = saleBaseQty;
	}

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public BigDecimal getSaleUseQty() {
		return saleUseQty;
	}

	public void setSaleUseQty(BigDecimal saleUseQty) {
		this.saleUseQty = saleUseQty;
	}

	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}

	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}

	public BigDecimal getReSaleCost() {
		return reSaleCost;
	}

	public void setReSaleCost(BigDecimal reSaleCost) {
		this.reSaleCost = reSaleCost;
	}

	public BigDecimal getReSaleProfit() {
		return reSaleProfit;
	}

	public void setReSaleProfit(BigDecimal reSaleProfit) {
		this.reSaleProfit = reSaleProfit;
	}

	public BigDecimal getReSaleProfitRate() {
		return reSaleProfitRate;
	}

	public void setReSaleProfitRate(BigDecimal reSaleProfitRate) {
		this.reSaleProfitRate = reSaleProfitRate;
	}

	public String getPosItemTypeCode() {
		return posItemTypeCode;
	}

	public void setPosItemTypeCode(String posItemTypeCode) {
		this.posItemTypeCode = posItemTypeCode;
	}

	public String getPosItemTypeName() {
		return posItemTypeName;
	}

	public void setPosItemTypeName(String posItemTypeName) {
		this.posItemTypeName = posItemTypeName;
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}

	public String getPosItemName() {
		return posItemName;
	}

	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}

	public Integer getPosItemNum() {
		return posItemNum;
	}

	public void setPosItemNum(Integer posItemNum) {
		this.posItemNum = posItemNum;
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

	public BigDecimal getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleProfit() {
		return saleProfit;
	}

	public void setSaleProfit(BigDecimal saleProfit) {
		this.saleProfit = saleProfit;
	}

	public BigDecimal getSaleProfitRate() {
		return saleProfitRate;
	}

	public void setSaleProfitRate(BigDecimal saleProfitRate) {
		this.saleProfitRate = saleProfitRate;
	}

	public BigDecimal getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(BigDecimal returnNum) {
		this.returnNum = returnNum;
	}

	public BigDecimal getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(BigDecimal returnMoney) {
		this.returnMoney = returnMoney;
	}

	public BigDecimal getReturnCost() {
		return returnCost;
	}

	public void setReturnCost(BigDecimal returnCost) {
		this.returnCost = returnCost;
	}

	public BigDecimal getReturnProfit() {
		return returnProfit;
	}

	public void setReturnProfit(BigDecimal returnProfit) {
		this.returnProfit = returnProfit;
	}

	public BigDecimal getReturnProfitRate() {
		return returnProfitRate;
	}

	public void setReturnProfitRate(BigDecimal returnProfitRate) {
		this.returnProfitRate = returnProfitRate;
	}

}
