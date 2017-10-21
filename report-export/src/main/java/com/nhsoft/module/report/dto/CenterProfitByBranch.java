	package com.nhsoft.module.report.dto;
	
	import java.io.Serializable;
import java.math.BigDecimal;

    public class CenterProfitByBranch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2331964225835002225L;

private Integer responseBranchNum;    //调往门店 号
private String responseBranchName;    //调往门店 名
private BigDecimal distributionMoney;	 //配送金额
private BigDecimal distributionProfit; 	//配送毛利
private String distributionProfitRate;		//配送毛利率
private BigDecimal saleMoney;				//零售金额
private BigDecimal saleProfit;				//零售毛利
private String saleProfitRate;				//零售毛利率
private BigDecimal cost; 						//成本金额

public CenterProfitByBranch(){
       setDistributionMoney(BigDecimal.ZERO);
       setDistributionProfit(BigDecimal.ZERO);
       setDistributionProfitRate("0.00%");
       setSaleMoney(BigDecimal.ZERO);
       setSaleProfit(BigDecimal.ZERO);
       setSaleProfitRate("0.00%");
       cost = BigDecimal.ZERO;
}

    public Integer getResponseBranchNum() {
        return responseBranchNum;
    }
    public void setResponseBranchNum(Integer responseBranchNum) {
        this.responseBranchNum = responseBranchNum;
    }

    public String getResponseBranchName() {
        return responseBranchName;
    }
    public void setResponseBranchName(String responseBranchName) {
        this.responseBranchName = responseBranchName;
    }
    public BigDecimal getDistributionMoney() {
        return distributionMoney;
    }
    public void setDistributionMoney(BigDecimal distributionMoney) {
        this.distributionMoney = distributionMoney;
    }
    public BigDecimal getDistributionProfit() {
        return distributionProfit;
    }
    public void setDistributionProfit(BigDecimal distributionProfit) {
        this.distributionProfit = distributionProfit;
    }
    public String getDistributionProfitRate() {
        return distributionProfitRate;
    }
    public void setDistributionProfitRate(String distributionProfitRate) {
        this.distributionProfitRate = distributionProfitRate;
    }
    public BigDecimal getSaleMoney() {
        return saleMoney;
    }
    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }
    public BigDecimal getSaleProfit() {
        return saleProfit;
    }
    public void setSaleProfit(BigDecimal saleProfit) {
        this.saleProfit = saleProfit;
    }
    public String getSaleProfitRate() {
        return saleProfitRate;
    }
    public void setSaleProfitRate(String saleProfitRate) {
        this.saleProfitRate = saleProfitRate;
    }

    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    }
