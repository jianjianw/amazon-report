package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AlipaySumDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1378851232964464779L;
	private Integer branchNum;
	private String branchName;
	private BigDecimal posConsumeSuccessQty;// POS消费成功笔数
	private BigDecimal posConsumeSuccessMoney;// POS消费成功金额
	private BigDecimal posConsumeSuccessActualMoney;//POS消费成功实收金额
	private BigDecimal posConsumeFailQty;// POS消费支付失败笔数
	private BigDecimal posConsumeFailMoney;// POS消费支付失败金额
	private BigDecimal posConsumeFailRate;// POS消费支付失败占比
	private BigDecimal depositSuccessQty;// 存款支付成功笔数
	private BigDecimal depositSuccessMoney;// 存款支付成功金额
	private BigDecimal depositFailQty;// 存款支付失败笔数
	private BigDecimal depositFailMoney;// 存款支付失败金额
	private BigDecimal depositFailRate;// 存款支付失败占比
	private BigDecimal consumeCancelSuccessQty;// POS消费撤销笔数
	private BigDecimal consumeCancelSuccessMoney;// POS消费撤销金额
	private BigDecimal consumeOverQty;// POS消费反结账笔数
	private BigDecimal consumeOverMoney;// POS消费反结账金额
	private BigDecimal alipayDiscountMoney;//支付宝优惠金额
	private BigDecimal branchDiscountMoney;//商家优惠金额
	private Integer branchRegionNum;
	//临时属性
	private BigDecimal posConsumeTotalQty;//POS消费总笔数
	private BigDecimal depositTotalQty;//存款总笔数
	
	public AlipaySumDTO(){
		posConsumeSuccessQty = BigDecimal.ZERO;
		posConsumeSuccessMoney = BigDecimal.ZERO;
		posConsumeFailQty = BigDecimal.ZERO;
		posConsumeFailMoney = BigDecimal.ZERO;
		posConsumeFailRate = BigDecimal.ZERO;
		depositSuccessQty = BigDecimal.ZERO;
		depositSuccessMoney = BigDecimal.ZERO;
		depositFailQty = BigDecimal.ZERO;
		depositFailMoney = BigDecimal.ZERO;
		depositFailRate = BigDecimal.ZERO;
		consumeCancelSuccessQty = BigDecimal.ZERO;
		consumeCancelSuccessMoney = BigDecimal.ZERO;
		consumeOverQty = BigDecimal.ZERO;
		consumeOverMoney = BigDecimal.ZERO;
		posConsumeTotalQty = BigDecimal.ZERO;
		depositTotalQty = BigDecimal.ZERO;
		posConsumeSuccessActualMoney = BigDecimal.ZERO;
		alipayDiscountMoney = BigDecimal.ZERO;
		branchDiscountMoney = BigDecimal.ZERO;
	}

	public Integer getBranchRegionNum() {
		return branchRegionNum;
	}

	public void setBranchRegionNum(Integer branchRegionNum) {
		this.branchRegionNum = branchRegionNum;
	}

	public BigDecimal getAlipayDiscountMoney() {
		return alipayDiscountMoney;
	}

	public void setAlipayDiscountMoney(BigDecimal alipayDiscountMoney) {
		this.alipayDiscountMoney = alipayDiscountMoney;
	}

	public BigDecimal getBranchDiscountMoney() {
		return branchDiscountMoney;
	}

	public void setBranchDiscountMoney(BigDecimal branchDiscountMoney) {
		this.branchDiscountMoney = branchDiscountMoney;
	}

	public BigDecimal getPosConsumeSuccessActualMoney() {
		return posConsumeSuccessActualMoney;
	}

	public void setPosConsumeSuccessActualMoney(BigDecimal posConsumeSuccessActualMoney) {
		this.posConsumeSuccessActualMoney = posConsumeSuccessActualMoney;
	}

	public BigDecimal getPosConsumeTotalQty() {
		return posConsumeTotalQty;
	}

	public void setPosConsumeTotalQty(BigDecimal posConsumeTotalQty) {
		this.posConsumeTotalQty = posConsumeTotalQty;
	}

	public BigDecimal getDepositTotalQty() {
		return depositTotalQty;
	}

	public void setDepositTotalQty(BigDecimal depositTotalQty) {
		this.depositTotalQty = depositTotalQty;
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

	public BigDecimal getPosConsumeSuccessQty() {
		return posConsumeSuccessQty;
	}

	public void setPosConsumeSuccessQty(BigDecimal posConsumeSuccessQty) {
		this.posConsumeSuccessQty = posConsumeSuccessQty;
	}

	public BigDecimal getPosConsumeSuccessMoney() {
		return posConsumeSuccessMoney;
	}

	public void setPosConsumeSuccessMoney(BigDecimal posConsumeSuccessMoney) {
		this.posConsumeSuccessMoney = posConsumeSuccessMoney;
	}

	public BigDecimal getPosConsumeFailQty() {
		return posConsumeFailQty;
	}

	public void setPosConsumeFailQty(BigDecimal posConsumeFailQty) {
		this.posConsumeFailQty = posConsumeFailQty;
	}

	public BigDecimal getPosConsumeFailMoney() {
		return posConsumeFailMoney;
	}

	public void setPosConsumeFailMoney(BigDecimal posConsumeFailMoney) {
		this.posConsumeFailMoney = posConsumeFailMoney;
	}

	public BigDecimal getPosConsumeFailRate() {
		return posConsumeFailRate;
	}

	public void setPosConsumeFailRate(BigDecimal posConsumeFailRate) {
		this.posConsumeFailRate = posConsumeFailRate;
	}

	public BigDecimal getDepositSuccessQty() {
		return depositSuccessQty;
	}

	public void setDepositSuccessQty(BigDecimal depositSuccessQty) {
		this.depositSuccessQty = depositSuccessQty;
	}

	public BigDecimal getDepositSuccessMoney() {
		return depositSuccessMoney;
	}

	public void setDepositSuccessMoney(BigDecimal depositSuccessMoney) {
		this.depositSuccessMoney = depositSuccessMoney;
	}

	public BigDecimal getDepositFailQty() {
		return depositFailQty;
	}

	public void setDepositFailQty(BigDecimal depositFailQty) {
		this.depositFailQty = depositFailQty;
	}

	public BigDecimal getDepositFailMoney() {
		return depositFailMoney;
	}

	public void setDepositFailMoney(BigDecimal depositFailMoney) {
		this.depositFailMoney = depositFailMoney;
	}

	public BigDecimal getDepositFailRate() {
		return depositFailRate;
	}

	public void setDepositFailRate(BigDecimal depositFailRate) {
		this.depositFailRate = depositFailRate;
	}

	public BigDecimal getConsumeCancelSuccessQty() {
		return consumeCancelSuccessQty;
	}

	public void setConsumeCancelSuccessQty(BigDecimal consumeCancelSuccessQty) {
		this.consumeCancelSuccessQty = consumeCancelSuccessQty;
	}

	public BigDecimal getConsumeCancelSuccessMoney() {
		return consumeCancelSuccessMoney;
	}

	public void setConsumeCancelSuccessMoney(BigDecimal consumeCancelSuccessMoney) {
		this.consumeCancelSuccessMoney = consumeCancelSuccessMoney;
	}

	public BigDecimal getConsumeOverQty() {
		return consumeOverQty;
	}

	public void setConsumeOverQty(BigDecimal consumeOverQty) {
		this.consumeOverQty = consumeOverQty;
	}

	public BigDecimal getConsumeOverMoney() {
		return consumeOverMoney;
	}

	public void setConsumeOverMoney(BigDecimal consumeOverMoney) {
		this.consumeOverMoney = consumeOverMoney;
	}

}
