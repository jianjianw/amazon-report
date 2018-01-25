package com.nhsoft.module.report.dto;



import com.nhsoft.module.report.annotation.ReportKey;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetailCompare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6567823874212299732L;
	private Integer branchRegionNum;
	@ReportKey
	private Integer branchNum; 
	private String branchName;
	private String categoryCode; // 类别代码;
	private String categoryName; // 类别名称
	private String orderCode; // 商品代码
	private String orderName; // 商品名称
	private String spec; // 规格
	private String unit; // 计量单位
	private BigDecimal lastSellNum; // 上期销售数
	private BigDecimal lastOutNum;// 上期调出数
	private BigDecimal lastWholeNum;// 上期批发数
	private BigDecimal lastSumNum;// 上期合计数
	private BigDecimal thisSellNum; // 本期销售数
	private BigDecimal thisOutNum;// 本期调出数
	private BigDecimal thisWholeNum;// 本期批发数
	private BigDecimal thisSumNum;// 本期合计数
	private String numGrowthRate; // 销售数增长率
	private BigDecimal numGrowthRateValue; // 销售数增长率
	
	private BigDecimal lastSellMoney; // 上期销售额
	private BigDecimal lastOutMoney;// 上期调出额
	private BigDecimal lastWholeMoney;// 上期批发额
	private BigDecimal lastSumMoney;// 上期合计金额
	private BigDecimal thisSellMoney; // 本期销售额
	private BigDecimal thisOutMoney;// 本期调出额
	private BigDecimal thisWholeMoney;// 本期批发数
	private BigDecimal thisSumMoney;// 本期合计额
	private String moneyGrowthRate; // 销售额增长率
	private BigDecimal moneyGrowthRateValue; // 销售额增长率
	
	private BigDecimal lastAdjustMoney;//上期损耗金额
	private BigDecimal thisAdjustMoney;//本期损耗金额
	private String adjustRate;//损耗占比
	private BigDecimal adjustRateValue;//损耗占比
	
	private BigDecimal lastInMoney; // 上期调入额
	private BigDecimal thisInMoney; // 本期调入额
	private String inGrowthRate; // 调入额增长率
	private BigDecimal inGrowthRateValue; // 调入额增长率

	private BigDecimal thisAvgPrice;//本期平均客单价（销售价）
	private BigDecimal lastAvgPrice;//上期平均客单价（销售价）
	private BigDecimal priceGrowthRate;//客单价（销售价）增加率
	
	private BigDecimal lastProfitMoney;//上期毛利
	private BigDecimal thisProfitMoney;//本期毛利 
	private String profitMoneyGrowthRate;//毛利增长率
	private BigDecimal profitMoneyGrowthRateValue;//毛利增长率
	
	private BigDecimal lastProfitRate;//上期毛利率
	private BigDecimal thisProfitRate;//本期毛利率
	private String profitGrowthRate; // 增加毛利率
	private BigDecimal profitGrowthRateValue; // 增加毛利率
	
	private BigDecimal lastSaleProfit;//上期毛利
	private BigDecimal thisSaleProfit;//本期毛利 
	private String saleProfitGrowthRate;//毛利增长率
	private BigDecimal saleProfitGrowthRateValue;//毛利增长率

	@ReportKey
	private Integer itemNum;
	@ReportKey
	private Integer itemMatrixNum;
	
	public BigDecimal getNumGrowthRateValue() {
		return numGrowthRateValue;
	}
	
	public void setNumGrowthRateValue(BigDecimal numGrowthRateValue) {
		this.numGrowthRateValue = numGrowthRateValue;
	}
	
	public BigDecimal getMoneyGrowthRateValue() {
		return moneyGrowthRateValue;
	}
	
	public void setMoneyGrowthRateValue(BigDecimal moneyGrowthRateValue) {
		this.moneyGrowthRateValue = moneyGrowthRateValue;
	}
	
	public BigDecimal getAdjustRateValue() {
		return adjustRateValue;
	}
	
	public void setAdjustRateValue(BigDecimal adjustRateValue) {
		this.adjustRateValue = adjustRateValue;
	}
	
	public BigDecimal getInGrowthRateValue() {
		return inGrowthRateValue;
	}
	
	public void setInGrowthRateValue(BigDecimal inGrowthRateValue) {
		this.inGrowthRateValue = inGrowthRateValue;
	}
	
	public BigDecimal getProfitMoneyGrowthRateValue() {
		return profitMoneyGrowthRateValue;
	}
	
	public void setProfitMoneyGrowthRateValue(BigDecimal profitMoneyGrowthRateValue) {
		this.profitMoneyGrowthRateValue = profitMoneyGrowthRateValue;
	}
	
	public BigDecimal getProfitGrowthRateValue() {
		return profitGrowthRateValue;
	}
	
	public void setProfitGrowthRateValue(BigDecimal profitGrowthRateValue) {
		this.profitGrowthRateValue = profitGrowthRateValue;
	}
	
	public BigDecimal getSaleProfitGrowthRateValue() {
		return saleProfitGrowthRateValue;
	}
	
	public void setSaleProfitGrowthRateValue(BigDecimal saleProfitGrowthRateValue) {
		this.saleProfitGrowthRateValue = saleProfitGrowthRateValue;
	}
	
	public Integer getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(Integer branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public OrderDetailCompare() {
		lastSellNum = BigDecimal.ZERO;
		thisSellNum = BigDecimal.ZERO;
		lastOutNum = BigDecimal.ZERO;
		lastWholeNum = BigDecimal.ZERO;
		lastSumNum = BigDecimal.ZERO;
		thisOutNum = BigDecimal.ZERO;
		thisWholeNum = BigDecimal.ZERO;
		thisSumNum = BigDecimal.ZERO;
		lastSellMoney = BigDecimal.ZERO;
		lastOutMoney = BigDecimal.ZERO;
		lastWholeMoney = BigDecimal.ZERO;
		lastSumMoney = BigDecimal.ZERO;
		thisSellMoney = BigDecimal.ZERO;
		thisOutMoney = BigDecimal.ZERO;
		thisWholeMoney = BigDecimal.ZERO;
		thisSumMoney = BigDecimal.ZERO;
		thisAvgPrice = BigDecimal.ZERO;
		lastAvgPrice = BigDecimal.ZERO;
		priceGrowthRate = BigDecimal.ZERO;
		lastAdjustMoney = BigDecimal.ZERO;
		thisAdjustMoney = BigDecimal.ZERO;
		lastInMoney = BigDecimal.ZERO;
		thisInMoney = BigDecimal.ZERO;
		lastProfitMoney = BigDecimal.ZERO;
		thisProfitMoney = BigDecimal.ZERO;
		lastProfitRate = BigDecimal.ZERO;		
		thisProfitRate = BigDecimal.ZERO;
		lastSaleProfit = BigDecimal.ZERO;
		thisSaleProfit = BigDecimal.ZERO;
	}

	public BigDecimal getLastSaleProfit() {
		return lastSaleProfit;
	}

	public void setLastSaleProfit(BigDecimal lastSaleProfit) {
		this.lastSaleProfit = lastSaleProfit;
	}

	public BigDecimal getThisSaleProfit() {
		return thisSaleProfit;
	}

	public void setThisSaleProfit(BigDecimal thisSaleProfit) {
		this.thisSaleProfit = thisSaleProfit;
	}

	public String getSaleProfitGrowthRate() {
		return saleProfitGrowthRate;
	}

	public void setSaleProfitGrowthRate(String saleProfitGrowthRate) {
		this.saleProfitGrowthRate = saleProfitGrowthRate;
	}

	public BigDecimal getLastProfitRate() {
		return lastProfitRate;
	}

	public void setLastProfitRate(BigDecimal lastProfitRate) {
		this.lastProfitRate = lastProfitRate;
	}

	public BigDecimal getThisProfitRate() {
		return thisProfitRate;
	}

	public void setThisProfitRate(BigDecimal thisProfitRate) {
		this.thisProfitRate = thisProfitRate;
	}

	public String getProfitGrowthRate() {
		return profitGrowthRate;
	}

	public void setProfitGrowthRate(String profitGrowthRate) {
		this.profitGrowthRate = profitGrowthRate;
	}

	public String getAdjustRate() {
		return adjustRate;
	}

	public void setAdjustRate(String adjustRate) {
		this.adjustRate = adjustRate;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getThisAvgPrice() {
		return thisAvgPrice;
	}

	public void setThisAvgPrice(BigDecimal thisAvgPrice) {
		this.thisAvgPrice = thisAvgPrice;
	}

	public BigDecimal getLastAvgPrice() {
		return lastAvgPrice;
	}

	public void setLastAvgPrice(BigDecimal lastAvgPrice) {
		this.lastAvgPrice = lastAvgPrice;
	}

	public BigDecimal getPriceGrowthRate() {
		return priceGrowthRate;
	}

	public void setPriceGrowthRate(BigDecimal priceGrowthRate) {
		this.priceGrowthRate = priceGrowthRate;
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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
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

	public BigDecimal getLastSellNum() {
		return lastSellNum;
	}

	public void setLastSellNum(BigDecimal lastSellNum) {
		this.lastSellNum = lastSellNum;
	}

	public BigDecimal getThisSellNum() {
		return thisSellNum;
	}

	public void setThisSellNum(BigDecimal thisSellNum) {
		this.thisSellNum = thisSellNum;
	}

	public String getNumGrowthRate() {
		return numGrowthRate;
	}

	public void setNumGrowthRate(String numGrowthRate) {
		this.numGrowthRate = numGrowthRate;
	}

	public BigDecimal getLastSellMoney() {
		return lastSellMoney;
	}

	public void setLastSellMoney(BigDecimal lastSellMoney) {
		this.lastSellMoney = lastSellMoney;
	}

	public BigDecimal getThisSellMoney() {
		return thisSellMoney;
	}

	public void setThisSellMoney(BigDecimal thisSellMoney) {
		this.thisSellMoney = thisSellMoney;
	}

	public String getMoneyGrowthRate() {
		return moneyGrowthRate;
	}

	public void setMoneyGrowthRate(String moneyGrowthRate) {
		this.moneyGrowthRate = moneyGrowthRate;
	}

	public BigDecimal getLastOutNum() {
		return lastOutNum;
	}

	public void setLastOutNum(BigDecimal lastOutNum) {
		this.lastOutNum = lastOutNum;
	}

	public BigDecimal getLastWholeNum() {
		return lastWholeNum;
	}

	public void setLastWholeNum(BigDecimal lastWholeNum) {
		this.lastWholeNum = lastWholeNum;
	}

	public BigDecimal getLastSumNum() {
		return lastSumNum;
	}

	public void setLastSumNum(BigDecimal lastSumNum) {
		this.lastSumNum = lastSumNum;
	}

	public BigDecimal getThisOutNum() {
		return thisOutNum;
	}

	public void setThisOutNum(BigDecimal thisOutNum) {
		this.thisOutNum = thisOutNum;
	}

	public BigDecimal getThisWholeNum() {
		return thisWholeNum;
	}

	public void setThisWholeNum(BigDecimal thisWholeNum) {
		this.thisWholeNum = thisWholeNum;
	}

	public BigDecimal getThisSumNum() {
		return thisSumNum;
	}

	public void setThisSumNum(BigDecimal thisSumNum) {
		this.thisSumNum = thisSumNum;
	}

	public BigDecimal getLastOutMoney() {
		return lastOutMoney;
	}

	public void setLastOutMoney(BigDecimal lastOutMoney) {
		this.lastOutMoney = lastOutMoney;
	}

	public BigDecimal getLastWholeMoney() {
		return lastWholeMoney;
	}

	public void setLastWholeMoney(BigDecimal lastWholeMoney) {
		this.lastWholeMoney = lastWholeMoney;
	}

	public BigDecimal getLastSumMoney() {
		return lastSumMoney;
	}

	public void setLastSumMoney(BigDecimal lastSumMoney) {
		this.lastSumMoney = lastSumMoney;
	}

	public BigDecimal getThisOutMoney() {
		return thisOutMoney;
	}

	public void setThisOutMoney(BigDecimal thisOutMoney) {
		this.thisOutMoney = thisOutMoney;
	}

	public BigDecimal getThisWholeMoney() {
		return thisWholeMoney;
	}

	public void setThisWholeMoney(BigDecimal thisWholeMoney) {
		this.thisWholeMoney = thisWholeMoney;
	}

	public BigDecimal getThisSumMoney() {
		return thisSumMoney;
	}

	public void setThisSumMoney(BigDecimal thisSumMoney) {
		this.thisSumMoney = thisSumMoney;
	}

	public BigDecimal getLastAdjustMoney() {
		return lastAdjustMoney;
	}

	public void setLastAdjustMoney(BigDecimal lastAdjustMoney) {
		this.lastAdjustMoney = lastAdjustMoney;
	}

	public BigDecimal getThisAdjustMoney() {
		return thisAdjustMoney;
	}

	public void setThisAdjustMoney(BigDecimal thisAdjustMoney) {
		this.thisAdjustMoney = thisAdjustMoney;
	}

	public BigDecimal getLastInMoney() {
		return lastInMoney;
	}

	public void setLastInMoney(BigDecimal lastInMoney) {
		this.lastInMoney = lastInMoney;
	}

	public BigDecimal getThisInMoney() {
		return thisInMoney;
	}

	public void setThisInMoney(BigDecimal thisInMoney) {
		this.thisInMoney = thisInMoney;
	}

	public String getInGrowthRate() {
		return inGrowthRate;
	}

	public void setInGrowthRate(String inGrowthRate) {
		this.inGrowthRate = inGrowthRate;
	}

	public BigDecimal getLastProfitMoney() {
		return lastProfitMoney;
	}

	public void setLastProfitMoney(BigDecimal lastProfitMoney) {
		this.lastProfitMoney = lastProfitMoney;
	}

	public BigDecimal getThisProfitMoney() {
		return thisProfitMoney;
	}

	public void setThisProfitMoney(BigDecimal thisProfitMoney) {
		this.thisProfitMoney = thisProfitMoney;
	}

	public String getProfitMoneyGrowthRate() {
		return profitMoneyGrowthRate;
	}

	public void setProfitMoneyGrowthRate(String profitMoneyGrowthRate) {
		this.profitMoneyGrowthRate = profitMoneyGrowthRate;
	}
	
	

}
