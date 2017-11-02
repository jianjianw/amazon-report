package com.nhsoft.module.report.shared.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;

public class UnusalItemQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -647697328593198070L;

	private String oldPrice; // 价格
	private String newPrice; // 只有priceUnusal true启用，
	private Boolean priceUnusal; // 价格 ture 数值 false
	private String compareType; // 比较符号
	private BigDecimal value; // 百分比 数值
	private Date dateFrom;
	private Date dateTo;
	private BigDecimal profitRateFrom;
	private BigDecimal profitRateTo;
	private String keyword;

	private boolean isPaging = true;
	private String sortField;
	private String sortType;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public BigDecimal getProfitRateFrom() {
		return profitRateFrom;
	}

	public void setProfitRateFrom(BigDecimal profitRateFrom) {
		this.profitRateFrom = profitRateFrom;
	}

	public BigDecimal getProfitRateTo() {
		return profitRateTo;
	}

	public void setProfitRateTo(BigDecimal profitRateTo) {
		this.profitRateTo = profitRateTo;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	public Boolean getPriceUnusal() {
		return priceUnusal;
	}

	public void setPriceUnusal(Boolean priceUnusal) {
		this.priceUnusal = priceUnusal;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}
	
}
