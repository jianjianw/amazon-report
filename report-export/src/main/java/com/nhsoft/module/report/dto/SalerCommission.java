package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalerCommission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269690444983886943L;
	private String saler;// 销售员
	private BigDecimal saleNums;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal saleCommission;// 销售提成
	private BigDecimal saleOrderCount;//客单量
	private BigDecimal saleOrderPrice;//客单金额
	private BigDecimal saleOrderMoney;// 销售单据金额
	private BigDecimal saleItemRelatRate; //连带率
	private String branchName;//所属门店（销售员对应门店）
	private Integer branchNum;
	private BigDecimal saleCost;//成本

	private List<Integer> rank = new ArrayList<Integer>();
	
	public SalerCommission() {
		for(int i = 0;i<10;i++){
			rank.add(0);
		}
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}


	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}


	public BigDecimal getSaleItemRelatRate() {
		return saleItemRelatRate;
	}


	public void setSaleItemRelatRate(BigDecimal saleItemRelatRate) {
		this.saleItemRelatRate = saleItemRelatRate;
	}


	public List<Integer> getRank() {
		return rank;
	}

	public void setRank(List<Integer> rank) {
		this.rank = rank;
	}

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

	public BigDecimal getSaleOrderMoney() {
		return saleOrderMoney;
	}

	public void setSaleOrderMoney(BigDecimal saleOrderMoney) {
		this.saleOrderMoney = saleOrderMoney;
	}

	public String getSaler() {
		return saler;
	}

	public void setSaler(String saler) {
		this.saler = saler;
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

	public BigDecimal getSaleOrderCount() {
		return saleOrderCount;
	}

	public void setSaleOrderCount(BigDecimal saleOrderCount) {
		this.saleOrderCount = saleOrderCount;
	}

	public BigDecimal getSaleOrderPrice() {
		return saleOrderPrice;
	}

	public void setSaleOrderPrice(BigDecimal saleOrderPrice) {
		this.saleOrderPrice = saleOrderPrice;
	}

}
