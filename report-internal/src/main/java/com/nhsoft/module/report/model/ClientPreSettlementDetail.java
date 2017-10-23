package com.nhsoft.module.report.model;

import java.math.BigDecimal;

/**
 * ClientPreSettlementDetail entity. @author MyEclipse Persistence Tools
 */

public class ClientPreSettlementDetail implements java.io.Serializable {

	private static final long serialVersionUID = -9005188644448458908L;
	private ClientPreSettlementDetailId id;
	private ClientPreSettlement clientPreSettlement;
	private BigDecimal preSettlementDetailMoney;

	public ClientPreSettlementDetailId getId() {
		return this.id;
	}

	public void setId(ClientPreSettlementDetailId id) {
		this.id = id;
	}

	public BigDecimal getPreSettlementDetailMoney() {
		return this.preSettlementDetailMoney;
	}

	public void setPreSettlementDetailMoney(BigDecimal preSettlementDetailMoney) {
		this.preSettlementDetailMoney = preSettlementDetailMoney;
	}

	public ClientPreSettlement getClientPreSettlement() {
		return clientPreSettlement;
	}

	public void setClientPreSettlement(ClientPreSettlement clientPreSettlement) {
		this.clientPreSettlement = clientPreSettlement;
	}

}