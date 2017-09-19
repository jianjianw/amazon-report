package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SalerCommissionBrand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9067475851851192212L;
	private String saler;// 销售员
	private String brand;// 品牌
	private BigDecimal saleNums;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleCommission;// 销售提成
	private String branchName;//所属门店（销售员对应门店）
	private Integer branchNum;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getSaleNums() {
		return saleNums;
	}

	public void setSaleNums(BigDecimal saleNums) {
		this.saleNums = saleNums;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}

	public BigDecimal getSaleCommission() {
		return saleCommission;
	}

	public void setSaleCommission(BigDecimal saleCommission) {
		this.saleCommission = saleCommission;
	}

	public static SalerCommissionBrand find(List<SalerCommissionBrand> list, String saler, Integer branchNum,
			String brand) {
		for(int i = 0;i < list.size();i++){
			SalerCommissionBrand data = list.get(i);
			if(data.getSaler().equals(saler) 
					&& data.getBranchNum().equals(branchNum) 
					&& data.getBrand().equals(brand)){
				return data;
			}
		}
		return null;
	}

}
