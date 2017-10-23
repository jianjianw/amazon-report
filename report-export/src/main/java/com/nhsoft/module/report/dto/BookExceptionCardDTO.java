package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookExceptionCardDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 137909043750979480L;
	private String systemBookCode;
	private String systemBookName;
	private Integer cardCount;
	private BigDecimal cardBalance;

	public BigDecimal getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(BigDecimal cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getSystemBookName() {
		return systemBookName;
	}

	public void setSystemBookName(String systemBookName) {
		this.systemBookName = systemBookName;
	}

	public Integer getCardCount() {
		return cardCount;
	}

	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}

}
