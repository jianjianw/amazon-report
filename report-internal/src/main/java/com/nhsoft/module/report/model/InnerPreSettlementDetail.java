package com.nhsoft.module.report.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * InnerPreSettlementDetail entity. @author MyEclipse Persistence Tools
 */

@Entity
public class InnerPreSettlementDetail implements java.io.Serializable {
	
	

	private static final long serialVersionUID = -7842817419316421617L;
	@EmbeddedId
	private InnerPreSettlementDetailId id;
	@ManyToOne
	@JoinColumn(name="preSettlementNo", insertable=false, updatable=false)
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