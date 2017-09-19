package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RequestOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6246771462564439886L;
	private String requestOrderFid;
	private BigDecimal requestOrderTotalMoney;
	private Date requestOrderAuditTime;
	private String requestOrderState;
	private Date requestOrderOutDate;
	private Date requestOrderPickDate;
	private Date requestOrderSendDate;
	private Date requestOrderInDate;
	private String expressCompanyLinkMan;
	private String expressCompanyPhone;
	private String expressCompanyName;
	
	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}


	public String getExpressCompanyLinkMan() {
		return expressCompanyLinkMan;
	}

	public void setExpressCompanyLinkMan(String expressCompanyLinkMan) {
		this.expressCompanyLinkMan = expressCompanyLinkMan;
	}

	public String getExpressCompanyPhone() {
		return expressCompanyPhone;
	}

	public void setExpressCompanyPhone(String expressCompanyPhone) {
		this.expressCompanyPhone = expressCompanyPhone;
	}

	public Date getRequestOrderOutDate() {
		return requestOrderOutDate;
	}

	public void setRequestOrderOutDate(Date requestOrderOutDate) {
		this.requestOrderOutDate = requestOrderOutDate;
	}

	public Date getRequestOrderPickDate() {
		return requestOrderPickDate;
	}

	public void setRequestOrderPickDate(Date requestOrderPickDate) {
		this.requestOrderPickDate = requestOrderPickDate;
	}

	public Date getRequestOrderSendDate() {
		return requestOrderSendDate;
	}

	public void setRequestOrderSendDate(Date requestOrderSendDate) {
		this.requestOrderSendDate = requestOrderSendDate;
	}

	public Date getRequestOrderInDate() {
		return requestOrderInDate;
	}

	public void setRequestOrderInDate(Date requestOrderInDate) {
		this.requestOrderInDate = requestOrderInDate;
	}

	public String getRequestOrderFid() {
		return requestOrderFid;
	}

	public void setRequestOrderFid(String requestOrderFid) {
		this.requestOrderFid = requestOrderFid;
	}

	public BigDecimal getRequestOrderTotalMoney() {
		return requestOrderTotalMoney;
	}

	public void setRequestOrderTotalMoney(BigDecimal requestOrderTotalMoney) {
		this.requestOrderTotalMoney = requestOrderTotalMoney;
	}

	public Date getRequestOrderAuditTime() {
		return requestOrderAuditTime;
	}

	public void setRequestOrderAuditTime(Date requestOrderAuditTime) {
		this.requestOrderAuditTime = requestOrderAuditTime;
	}

	public String getRequestOrderState() {
		return requestOrderState;
	}

	public void setRequestOrderState(String requestOrderState) {
		this.requestOrderState = requestOrderState;
	}

	public static RequestOrderDTO get(List<RequestOrderDTO> requestOrderDTOs, String requestOrderFid) {
		for(int i = 0;i < requestOrderDTOs.size();i++){
			RequestOrderDTO dto = requestOrderDTOs.get(i);
			if(dto.getRequestOrderFid().equals(requestOrderFid)){
				return dto;
			}
		}
		return null;
	}

}
