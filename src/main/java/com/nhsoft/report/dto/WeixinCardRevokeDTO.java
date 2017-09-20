package com.nhsoft.report.dto;


import java.io.Serializable;

public class WeixinCardRevokeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1878405363283979480L;
	private String bookCode;
	private String cardId;
	private String code;
	private String reason = "";

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String createMNSContent(){
		return AppConstants.MESSAGE_COMMAND_WEIXIN_CARD_REVOKE + AppUtil.getEnter() + AppUtil.getGson().toJson(this);
	}

}
