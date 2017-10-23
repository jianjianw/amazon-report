package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BranchCategoryAnalyseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3922534238227822905L;
	private Integer branchNum;
	private String branchName;
	private BigDecimal profitTotalRate;// 总毛利率
	private BigDecimal profitMoney;
	private BigDecimal saleMoney;
	private BigDecimal transferMoney;
	private List<TypeAndTwoValuesDTO> categoryValueDTOs = new ArrayList<TypeAndTwoValuesDTO>();// 两个值分别是：配送额 销售额
	private List<TypeAndTwoValuesDTO> departmentValueDTOs = new ArrayList<TypeAndTwoValuesDTO>();// 两个值分别是： 配送额 销售额
																									
	public BranchCategoryAnalyseDTO(){
		setTransferMoney(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
	}
	
	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
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

	public BigDecimal getProfitTotalRate() {
		return profitTotalRate;
	}

	public void setProfitTotalRate(BigDecimal profitTotalRate) {
		this.profitTotalRate = profitTotalRate;
	}

	public BigDecimal getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(BigDecimal profitMoney) {
		this.profitMoney = profitMoney;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public List<TypeAndTwoValuesDTO> getCategoryValueDTOs() {
		return categoryValueDTOs;
	}

	public void setCategoryValueDTOs(List<TypeAndTwoValuesDTO> categoryValueDTOs) {
		this.categoryValueDTOs = categoryValueDTOs;
	}

	public List<TypeAndTwoValuesDTO> getDepartmentValueDTOs() {
		return departmentValueDTOs;
	}

	public void setDepartmentValueDTOs(List<TypeAndTwoValuesDTO> departmentValueDTOs) {
		this.departmentValueDTOs = departmentValueDTOs;
	}
	
	public static BranchCategoryAnalyseDTO get(List<BranchCategoryAnalyseDTO> branchCategoryAnalyseDTOs, Integer branchNum){
		for(int i = 0;i < branchCategoryAnalyseDTOs.size();i++){
			BranchCategoryAnalyseDTO branchCategoryAnalyseDTO = branchCategoryAnalyseDTOs.get(i);
			if(branchCategoryAnalyseDTO.getBranchNum().equals(branchNum)){
				return branchCategoryAnalyseDTO;
			}
		}
		return null;
		
	}
	
	public TypeAndTwoValuesDTO getCategoryValue(String categoryCode){
		for(int i = 0;i < categoryValueDTOs.size();i++){
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = categoryValueDTOs.get(i);
			if(typeAndTwoValuesDTO.getType().equals(categoryCode)){
				return typeAndTwoValuesDTO;
			}
		}
		return null;
	}
	
	public TypeAndTwoValuesDTO getDepartmentValue(String departmentName){
		for(int i = 0;i < departmentValueDTOs.size();i++){
			TypeAndTwoValuesDTO typeAndTwoValuesDTO = departmentValueDTOs.get(i);
			if(typeAndTwoValuesDTO.getType().equals(departmentName)){
				return typeAndTwoValuesDTO;
			}
		}
		return null;
	}

}
