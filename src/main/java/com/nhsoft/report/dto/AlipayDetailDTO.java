package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AlipayDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4975757409893838541L;
	private String orderFid;// 交易流水号
	private String orderNo;// 商户订单号
	private String branchName;// 交易门店
	private Date orderTime;// 交易完成时间
	private String customerId;// 对方账户
	private BigDecimal consumeSuccessMoney;// 订单金额
	private BigDecimal consumeSuccessActualMoney;// 实收金额
	private Integer branchNum;// 交易门店
	private String type; //交易类型 前台销售or 卡存款
	private BigDecimal alipayDiscountMoney;//支付宝优惠金额
	private BigDecimal branchDiscountMoney;//商家优惠金额
	private Boolean valid = true; //是否有效

	public BigDecimal getAlipayDiscountMoney() {
		return alipayDiscountMoney;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
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

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getOrderFid() {
		return orderFid;
	}

	public void setOrderFid(String orderFid) {
		this.orderFid = orderFid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getConsumeSuccessMoney() {
		return consumeSuccessMoney;
	}

	public void setConsumeSuccessMoney(BigDecimal consumeSuccessMoney) {
		this.consumeSuccessMoney = consumeSuccessMoney;
	}

	public BigDecimal getConsumeSuccessActualMoney() {
		return consumeSuccessActualMoney;
	}

	public void setConsumeSuccessActualMoney(BigDecimal consumeSuccessActualMoney) {
		this.consumeSuccessActualMoney = consumeSuccessActualMoney;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
