package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.util.Date;

public class MarketQuery extends QueryBuilder{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062889026800935063L;
	private Integer branchNum;
	private Date dateFrom;
	private Date dateTo;
	private Integer marketCountFrom;//营销人数起
	private Integer marketCountTo;//营销人数至
	private String couponTypeId;

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
	
}
