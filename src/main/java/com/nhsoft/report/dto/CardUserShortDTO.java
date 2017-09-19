package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CardUserShortDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5974038214039795420L;
	private Integer cardUserNum;
	private String cardUserPrintedNum;
	private Date cardUserDeadline;
	private String cardUserStateName;
	private String cardUserPhone;
	private String cardUserOpenId;
	private String cardUserStorage;//卡存储类型
	private Boolean cardUserLocked;
	private String cardUserCustName;
	private BigDecimal cardUserPoint;
	
	public BigDecimal getCardUserPoint() {
		return cardUserPoint;
	}
	
	public void setCardUserPoint(BigDecimal cardUserPoint) {
		this.cardUserPoint = cardUserPoint;
	}
	
	public String getCardUserCustName() {
		return cardUserCustName;
	}

	public void setCardUserCustName(String cardUserCustName) {
		this.cardUserCustName = cardUserCustName;
	}

	public String getCardUserStorage() {
		return cardUserStorage;
	}

	public void setCardUserStorage(String cardUserStorage) {
		this.cardUserStorage = cardUserStorage;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getCardUserPrintedNum() {
		return cardUserPrintedNum;
	}

	public void setCardUserPrintedNum(String cardUserPrintedNum) {
		this.cardUserPrintedNum = cardUserPrintedNum;
	}

	public Date getCardUserDeadline() {
		return cardUserDeadline;
	}

	public void setCardUserDeadline(Date cardUserDeadline) {
		this.cardUserDeadline = cardUserDeadline;
	}

	public String getCardUserStateName() {
		return cardUserStateName;
	}

	public void setCardUserStateName(String cardUserStateName) {
		this.cardUserStateName = cardUserStateName;
	}

	public String getCardUserPhone() {
		return cardUserPhone;
	}

	public void setCardUserPhone(String cardUserPhone) {
		this.cardUserPhone = cardUserPhone;
	}

	public String getCardUserOpenId() {
		return cardUserOpenId;
	}

	public void setCardUserOpenId(String cardUserOpenId) {
		this.cardUserOpenId = cardUserOpenId;
	}

	public Boolean getCardUserLocked() {
		return cardUserLocked;
	}

	public void setCardUserLocked(Boolean cardUserLocked) {
		this.cardUserLocked = cardUserLocked;
	}


}
