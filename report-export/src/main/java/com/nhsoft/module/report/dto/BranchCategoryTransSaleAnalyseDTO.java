package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 门店配销分析
 * 
 * @author nhsoft
 * 
 */
public class BranchCategoryTransSaleAnalyseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2205237274545562076L;
	private Integer branchNum;// 门店编号
	private String branchName;// 门店名称
	private String categoryCode;//类别代码
	private String categoryName;//类别名称
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleRate;// 销售占比
	private BigDecimal transferMoney;// 配送金额
	private BigDecimal profitMoney;// 毛利
	private BigDecimal profitRate;// 毛利率
	private BigDecimal saleTotal;// 累计销售
	private BigDecimal saleTotalRate;// 累计占比
	private BigDecimal transferTotal;// 累计配送
	private BigDecimal profitTotal;// 累计毛利
	private BigDecimal profitTotalRate;// 累计毛利率
	
	public BranchCategoryTransSaleAnalyseDTO(){
		setSaleMoney(BigDecimal.ZERO);
		setSaleRate(BigDecimal.ZERO);
		setTransferMoney(BigDecimal.ZERO);
		setProfitMoney(BigDecimal.ZERO);
		setProfitRate(BigDecimal.ZERO);
		setSaleTotal(BigDecimal.ZERO);
		setSaleTotalRate(BigDecimal.ZERO);
		setTransferTotal(BigDecimal.ZERO);
		setProfitTotal(BigDecimal.ZERO);
		setProfitTotalRate(BigDecimal.ZERO);
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

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(BigDecimal saleRate) {
		this.saleRate = saleRate;
	}

	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
	}

	public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(BigDecimal saleTotal) {
		this.saleTotal = saleTotal;
	}

	public BigDecimal getSaleTotalRate() {
		return saleTotalRate;
	}

	public void setSaleTotalRate(BigDecimal saleTotalRate) {
		this.saleTotalRate = saleTotalRate;
	}

	public BigDecimal getTransferTotal() {
		return transferTotal;
	}

	public void setTransferTotal(BigDecimal transferTotal) {
		this.transferTotal = transferTotal;
	}

	public BigDecimal getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(BigDecimal profitTotal) {
		this.profitTotal = profitTotal;
	}

	public BigDecimal getProfitTotalRate() {
		return profitTotalRate;
	}

	public void setProfitTotalRate(BigDecimal profitTotalRate) {
		this.profitTotalRate = profitTotalRate;
	}
	
	public boolean checkZero(){
		
		return saleMoney.compareTo(BigDecimal.ZERO) == 0
				&& transferMoney.compareTo(BigDecimal.ZERO) == 0
				&& profitMoney.compareTo(BigDecimal.ZERO) == 0
				&& saleTotal.compareTo(BigDecimal.ZERO) == 0
				&& transferTotal.compareTo(BigDecimal.ZERO) == 0
				&& profitTotal.compareTo(BigDecimal.ZERO) == 0;
	}
	
	public static BranchCategoryTransSaleAnalyseDTO get(List<BranchCategoryTransSaleAnalyseDTO> branchTransSaleAnalyseDTOs, 
			Integer branchNum, String categoryCode){
		for(int i = 0;i < branchTransSaleAnalyseDTOs.size();i++){
			BranchCategoryTransSaleAnalyseDTO branchTransSaleAnalyseDTO = branchTransSaleAnalyseDTOs.get(i);
			if(branchTransSaleAnalyseDTO.getBranchNum().equals(branchNum) 
					&& branchTransSaleAnalyseDTO.getCategoryCode().equals(categoryCode)){
				return branchTransSaleAnalyseDTO;
			}
		}
		return null;
	}

}
