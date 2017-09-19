package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleAnalysisBranchItemGradeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6077734496033315272L;
	private Integer branchNum;
	private String branchName;
	private String branchCode;
	private Integer branchRegionNum;
	private BigDecimal saleAmount;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleProfit;// 销售毛利
	private BigDecimal saleProfitRate;// 销售毛利率
	private List<NameAndValueDTO> details = new ArrayList<NameAndValueDTO>();
	
	public SaleAnalysisBranchItemGradeDTO(){
		setSaleMoney(BigDecimal.ZERO);
		setSaleAmount(BigDecimal.ZERO);
		setSaleProfit(BigDecimal.ZERO);
		setSaleProfitRate(BigDecimal.ZERO);
	}

	public Integer getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(Integer branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
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

	public BigDecimal getSaleProfitRate() {
		return saleProfitRate;
	}

	public void setSaleProfitRate(BigDecimal saleProfitRate) {
		this.saleProfitRate = saleProfitRate;
	}

	
	public List<NameAndValueDTO> getDetails() {
		return details;
	}

	public void setDetails(List<NameAndValueDTO> details) {
		this.details = details;
	}

	public static SaleAnalysisBranchItemGradeDTO get(List<SaleAnalysisBranchItemGradeDTO> list, Integer branchNum){
		for(int i = 0;i < list.size();i++){
			SaleAnalysisBranchItemGradeDTO dto = list.get(i);
			if(dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}

}
