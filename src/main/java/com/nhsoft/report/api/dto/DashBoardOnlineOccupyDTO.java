package com.nhsoft.report.api.dto;

import java.math.BigDecimal;

public class DashBoardOnlineOccupyDTO {

	private String areaName;               //区域名称
	private Integer areaNum;               //区域编号
	private BigDecimal onlineRevenue;      //线上销售
	private BigDecimal offlineRevenue;     //线下销售
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(Integer areaNum) {
		this.areaNum = areaNum;
	}
	public BigDecimal getOnlineRevenue() {
		return onlineRevenue;
	}
	public void setOnlineRevenue(BigDecimal onlineRevenue) {
		this.onlineRevenue = onlineRevenue;
	}
	public BigDecimal getOfflineRevenue() {
		return offlineRevenue;
	}
	public void setOfflineRevenue(BigDecimal offlineRevenue) {
		this.offlineRevenue = offlineRevenue;
	}
	
}
