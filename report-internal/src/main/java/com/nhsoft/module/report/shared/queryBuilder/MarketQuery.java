package com.nhsoft.module.report.shared.queryBuilder;

import java.io.Serializable;
import java.util.Date;

public class MarketQuery implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062889026800935063L;
	private String systemBookCode;
	private Integer branchNum;
	private Date dateFrom;
	private Date dateTo;
	private Integer marketCountFrom;//营销人数起
	private Integer marketCountTo;//营销人数至
	private String couponTypeId;

	
	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getMarketCountFrom() {
		return marketCountFrom;
	}

	public void setMarketCountFrom(Integer marketCountFrom) {
		this.marketCountFrom = marketCountFrom;
	}

	public Integer getMarketCountTo() {
		return marketCountTo;
	}

	public void setMarketCountTo(Integer marketCountTo) {
		this.marketCountTo = marketCountTo;
	}

	public String getCouponTypeId() {
		return couponTypeId;
	}

	public void setCouponTypeId(String couponTypeId) {
		this.couponTypeId = couponTypeId;
	}

	public boolean checkQueryBuild() {
		if (dateFrom != null && dateTo != null) {
			if (dateFrom.after(dateTo)) {
				return false;
			}
		}
		if (marketCountFrom != null && marketCountTo != null) {
			if (marketCountFrom > marketCountTo) {
				return false;
			}
		}
		return true;
	}
}
