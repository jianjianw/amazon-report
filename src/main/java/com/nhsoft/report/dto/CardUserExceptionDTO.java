package com.nhsoft.report.dto;

import com.nhsoft.pos3.shared.State;

import java.io.Serializable;

public class CardUserExceptionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5344365844568133105L;
	private Integer cardUserNum;
	private String cardUserTips;
	private String cardUserPhysicalNum;
	private String cardUserPrintedNum;
	private State state;
	private Boolean cardUserLocked;

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getCardUserTips() {
		return cardUserTips;
	}

	public void setCardUserTips(String cardUserTips) {
		this.cardUserTips = cardUserTips;
	}

	public String getCardUserPhysicalNum() {
		return cardUserPhysicalNum;
	}

	public void setCardUserPhysicalNum(String cardUserPhysicalNum) {
		this.cardUserPhysicalNum = cardUserPhysicalNum;
	}

	public String getCardUserPrintedNum() {
		return cardUserPrintedNum;
	}

	public void setCardUserPrintedNum(String cardUserPrintedNum) {
		this.cardUserPrintedNum = cardUserPrintedNum;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Boolean getCardUserLocked() {
		return cardUserLocked;
	}

	public void setCardUserLocked(Boolean cardUserLocked) {
		this.cardUserLocked = cardUserLocked;
	}

}
