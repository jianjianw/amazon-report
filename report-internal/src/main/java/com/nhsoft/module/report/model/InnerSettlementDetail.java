package com.nhsoft.module.report.model;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

/**
 * InnerSettlementDetail generated by hbm2java
 */
@Entity
public class InnerSettlementDetail implements java.io.Serializable {

	private static final long serialVersionUID = 3600397786444990819L;
	@EmbeddedId
	private InnerSettlementDetailId id;
	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name="outOrderFid")
	private TransferOutOrder transferOutOrder;
	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name="inOrderFid")
	private TransferInOrder transferInOrder;
	private BigDecimal innerSettlementDetailMoney;
	private BigDecimal innerSettlementDetailDiscount;
	private String innerSettlementDetailMemo;
	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name="cardSettlementNo")
	private CardSettlement cardSettlement;
	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name="otherInoutBillNo")
	private OtherInout otherInout;

	public InnerSettlementDetail() {
	}

	public InnerSettlementDetailId getId() {
		return this.id;
	}

	public void setId(InnerSettlementDetailId id) {
		this.id = id;
	}

	public BigDecimal getInnerSettlementDetailMoney() {
		return this.innerSettlementDetailMoney;
	}

	public void setInnerSettlementDetailMoney(
			BigDecimal innerSettlementDetailMoney) {
		this.innerSettlementDetailMoney = innerSettlementDetailMoney;
	}

	public BigDecimal getInnerSettlementDetailDiscount() {
		return this.innerSettlementDetailDiscount;
	}

	public void setInnerSettlementDetailDiscount(
			BigDecimal innerSettlementDetailDiscount) {
		this.innerSettlementDetailDiscount = innerSettlementDetailDiscount;
	}

	public String getInnerSettlementDetailMemo() {
		return this.innerSettlementDetailMemo;
	}

	public void setInnerSettlementDetailMemo(String innerSettlementDetailMemo) {
		this.innerSettlementDetailMemo = innerSettlementDetailMemo;
	}

	public CardSettlement getCardSettlement() {
		return cardSettlement;
	}

	public void setCardSettlement(CardSettlement cardSettlement) {
		this.cardSettlement = cardSettlement;
	}

	public TransferOutOrder getTransferOutOrder() {
		return transferOutOrder;
	}

	public void setTransferOutOrder(TransferOutOrder transferOutOrder) {
		this.transferOutOrder = transferOutOrder;
	}

	public TransferInOrder getTransferInOrder() {
		return transferInOrder;
	}

	public void setTransferInOrder(TransferInOrder transferInOrder) {
		this.transferInOrder = transferInOrder;
	}

	public OtherInout getOtherInout() {
		return otherInout;
	}

	public void setOtherInout(OtherInout otherInout) {
		this.otherInout = otherInout;
	}

}
