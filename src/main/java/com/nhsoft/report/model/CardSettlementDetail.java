package com.nhsoft.report.model;



/**
 * CardSettlementDetail generated by hbm2java
 */
public class CardSettlementDetail implements java.io.Serializable {

	private static final long serialVersionUID = 8174261793033437746L;
	private CardSettlementDetailId id;
	private CardDeposit cardDeposit;
	private CardConsume cardConsume;
	
	//临时属性 
	private String consumeFid;
	private String depositFid;

	public CardSettlementDetail() {
	}

	public String getConsumeFid() {
		return consumeFid;
	}

	public void setConsumeFid(String consumeFid) {
		this.consumeFid = consumeFid;
	}

	public String getDepositFid() {
		return depositFid;
	}

	public void setDepositFid(String depositFid) {
		this.depositFid = depositFid;
	}

	public CardSettlementDetailId getId() {
		return this.id;
	}

	public void setId(CardSettlementDetailId id) {
		this.id = id;
	}

	public CardDeposit getCardDeposit() {
		return cardDeposit;
	}

	public void setCardDeposit(CardDeposit cardDeposit) {
		this.cardDeposit = cardDeposit;
	}

	public CardConsume getCardConsume() {
		return cardConsume;
	}

	public void setCardConsume(CardConsume cardConsume) {
		this.cardConsume = cardConsume;
	}



}
