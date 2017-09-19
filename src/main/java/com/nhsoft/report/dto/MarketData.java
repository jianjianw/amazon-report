package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.model.Branch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MarketData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 882049442455108845L;
	private String actionId; 
	private String ticketTypeName; //券名称
	private BigDecimal ticketTypeLimitMoney; //满xx元可使用
	private Date ticketSendDetailValidStart; //券生效日期
	private Date ticketSendDetailValidDate;  //券截止日期
	private Date marketDetailValidStart;     //营销活动开始日期
	private Date marketDetailValidDate;      //营销活动截止日期
	private Integer state; //营销活动状态
	private String weixinActionQrcodeUrl; //二维码显示url
	private List<Branch> appliedBranches; //应用门店
	private Integer maxCount; //总可发券数量
	private Integer ticketStock; //剩余可发券数量
	
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public BigDecimal getTicketTypeLimitMoney() {
		return ticketTypeLimitMoney;
	}
	public Date getTicketSendDetailValidStart() {
		return ticketSendDetailValidStart;
	}
	public Date getTicketSendDetailValidDate() {
		return ticketSendDetailValidDate;
	}
	public Date getMarketDetailValidStart() {
		return marketDetailValidStart;
	}
	public Date getMarketDetailValidDate() {
		return marketDetailValidDate;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public void setTicketTypeLimitMoney(BigDecimal ticketTypeLimitMoney) {
		this.ticketTypeLimitMoney = ticketTypeLimitMoney;
	}
	public void setTicketSendDetailValidStart(Date ticketSendDetailValidStart) {
		this.ticketSendDetailValidStart = ticketSendDetailValidStart;
	}
	public void setTicketSendDetailValidDate(Date ticketSendDetailValidDate) {
		this.ticketSendDetailValidDate = ticketSendDetailValidDate;
	}
	public void setMarketDetailValidStart(Date marketDetailValidStart) {
		this.marketDetailValidStart = marketDetailValidStart;
	}
	public void setMarketDetailValidDate(Date marketDetailValidDate) {
		this.marketDetailValidDate = marketDetailValidDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getWeixinActionQrcodeUrl() {
		return weixinActionQrcodeUrl;
	}
	public List<Branch> getAppliedBranches() {
		return appliedBranches;
	}
	public void setWeixinActionQrcodeUrl(String weixinActionQrcodeUrl) {
		this.weixinActionQrcodeUrl = weixinActionQrcodeUrl;
	}
	public void setAppliedBranches(List<Branch> appliedBranches) {
		this.appliedBranches = appliedBranches;
	}
	public Integer getTicketStock() {
		return ticketStock;
	}
	public void setTicketStock(Integer ticketStock) {
		this.ticketStock = ticketStock;
	}
	public Integer getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
}