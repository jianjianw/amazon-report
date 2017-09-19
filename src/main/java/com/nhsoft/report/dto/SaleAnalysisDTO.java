package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleAnalysisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8254932404570581193L;
	private BigDecimal saleNum;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private BigDecimal returnNum;// 退货数量
	private BigDecimal returnMoney;// 退货金额
	private BigDecimal presentNum;// 赠送数量
	private BigDecimal presentMoney;// 赠送金额
	private BigDecimal totalNum;// 数量小计
	private BigDecimal totalMoney;// 金额小计
	private BigDecimal countTotal;// 次数小计
	private BigDecimal saleAssist;// 销售辅量
	private BigDecimal returnAssist;// 退货辅量
	private BigDecimal presentAssist;// 赠送辅量

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

	public BigDecimal getPresentNum() {
		return presentNum;
	}

	public void setPresentNum(BigDecimal presentNum) {
		this.presentNum = presentNum;
	}

	public BigDecimal getPresentMoney() {
		return presentMoney;
	}

	public void setPresentMoney(BigDecimal presentMoney) {
		this.presentMoney = presentMoney;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigDecimal getCountTotal() {
		return countTotal;
	}

	public void setCountTotal(BigDecimal countTotal) {
		this.countTotal = countTotal;
	}

	public BigDecimal getSaleAssist() {
		return saleAssist;
	}

	public void setSaleAssist(BigDecimal saleAssist) {
		this.saleAssist = saleAssist;
	}

	public BigDecimal getReturnAssist() {
		return returnAssist;
	}

	public void setReturnAssist(BigDecimal returnAssist) {
		this.returnAssist = returnAssist;
	}

	public BigDecimal getPresentAssist() {
		return presentAssist;
	}

	public void setPresentAssist(BigDecimal presentAssist) {
		this.presentAssist = presentAssist;
	}

}
