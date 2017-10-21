package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class TransferGoal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6630515018925524159L;

	private Integer branchNum;
	private String yearMonth;
	private BigDecimal transferGoal;
	private BigDecimal transferAmount;
	private BigDecimal deliverRate;
	private BigDecimal transferDiffGoal;
	private BigDecimal transferDiffAmount;
	private BigDecimal deliverDiffRate;
	private BigDecimal saleProfitGoal;
	private BigDecimal saleProfitMoney;
	
	public TransferGoal(){
		setTransferAmount(BigDecimal.ZERO);
		setTransferDiffAmount(BigDecimal.ZERO);
		setTransferDiffGoal(BigDecimal.ZERO);
		setTransferGoal(BigDecimal.ZERO);
		setSaleProfitGoal(BigDecimal.ZERO);
		setSaleProfitMoney(BigDecimal.ZERO);
	}

	public BigDecimal getSaleProfitGoal() {
		return saleProfitGoal;
	}

	public void setSaleProfitGoal(BigDecimal saleProfitGoal) {
		this.saleProfitGoal = saleProfitGoal;
	}

	public BigDecimal getSaleProfitMoney() {
		return saleProfitMoney;
	}

	public void setSaleProfitMoney(BigDecimal saleProfitMoney) {
		this.saleProfitMoney = saleProfitMoney;
	}

	public BigDecimal getDeliverRate() {
		return deliverRate;
	}

	public void setDeliverRate(BigDecimal deliverRate) {
		this.deliverRate = deliverRate;
	}

	public BigDecimal getTransferDiffGoal() {
		return transferDiffGoal;
	}

	public void setTransferDiffGoal(BigDecimal transferDiffGoal) {
		this.transferDiffGoal = transferDiffGoal;
	}

	public BigDecimal getTransferDiffAmount() {
		return transferDiffAmount;
	}

	public void setTransferDiffAmount(BigDecimal transferDiffAmount) {
		this.transferDiffAmount = transferDiffAmount;
	}

	public BigDecimal getDeliverDiffRate() {
		return deliverDiffRate;
	}

	public void setDeliverDiffRate(BigDecimal deliverDiffRate) {
		this.deliverDiffRate = deliverDiffRate;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public BigDecimal getTransferGoal() {
		return transferGoal;
	}

	public void setTransferGoal(BigDecimal deliverGoal) {
		this.transferGoal = deliverGoal;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal deliverAmount) {
		this.transferAmount = deliverAmount;
	}

	public BigDecimal getTransferRate() {
		return deliverRate;
	}

	public void setTransferRate(BigDecimal deliverRate) {
		this.deliverRate = deliverRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((branchNum == null) ? 0 : branchNum.hashCode());
		result = prime * result
				+ ((yearMonth == null) ? 0 : yearMonth.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransferGoal other = (TransferGoal) obj;
		if (branchNum == null) {
			if (other.branchNum != null)
				return false;
		} else if (!branchNum.equals(other.branchNum))
			return false;
		if (yearMonth == null) {
			if (other.yearMonth != null)
				return false;
		} else if (!yearMonth.equals(other.yearMonth))
			return false;
		return true;
	}


}
