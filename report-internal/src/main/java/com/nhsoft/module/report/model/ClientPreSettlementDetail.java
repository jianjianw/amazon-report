package com.nhsoft.module.report.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * ClientPreSettlementDetail entity. @author MyEclipse Persistence Tools
 */

@Entity
public class ClientPreSettlementDetail implements java.io.Serializable {

	private static final long serialVersionUID = -9005188644448458908L;
	@EmbeddedId
	private ClientPreSettlementDetailId id;
	@ManyToOne
	@JoinColumn(name="preSettlementNo", insertable=false, updatable=false)
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