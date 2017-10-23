package com.nhsoft.module.report.model;

import java.math.BigDecimal;

/**
 * InnerPreSettlementDetail entity. @author MyEclipse Persistence Tools
 */

public class InnerPreSettlementDetail implements java.io.Serializable {
	
	

	private static final long serialVersionUID = -7842817419316421617L;
	private InnerPreSettlementDetailId id;
	private InnerPreSettlement innerPreSettlement;
	private BigDecimal preSettlementDetailMoney;

	public InnerPreSettlementDetailId getId() {
		return id;
	}

	public void setId(InnerPreSettlementDetailId id) {
		this.id = id;
	}

	public BigDecimal getPreSettlementDetailMoney() {
		return preSettlementDetailMoney;
	}

	public void setPreSettlementDetailMoney(BigDecimal preSettlementDetailMoney) {
		this.preSettlementDetailMoney = preSettlementDetailMoney;
	}

	public InnerPreSettlement getInnerPreSettlement() {
		return innerPreSettlement;
	}

	public void setInnerPreSettlement(InnerPreSettlement innerPreSettlement) {
		this.innerPreSettlement = innerPreSettlement;
	}

}