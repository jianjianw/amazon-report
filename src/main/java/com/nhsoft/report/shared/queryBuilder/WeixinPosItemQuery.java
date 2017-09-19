package com.nhsoft.report.shared.queryBuilder;

import java.math.BigDecimal;
import java.util.List;

public class WeixinPosItemQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6626457756957092712L;
	private String categoryName;
	private Integer status;
	private BigDecimal priceFrom;
	private BigDecimal priceTo;
	private String keyword;
	private String sortField;
	private String sortType;
	private Boolean toped;
	private Boolean team;//是否团购商品
	private String categoryCode;
	private Boolean queryTeamEnd; //查询团购结束的商品
	
	private List<String> categoryCodes;
	private List<Integer> weixinItemNums;


	public Boolean getQueryTeamEnd() {
		return queryTeamEnd;
	}

	public void setQueryTeamEnd(Boolean queryTeamEnd) {
		this.queryTeamEnd = queryTeamEnd;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Boolean getTeam() {
		return team;
	}

	public void setTeam(Boolean team) {
		this.team = team;
	}

	public Boolean getToped() {
		return toped;
	}

	public void setToped(Boolean toped) {
		this.toped = toped;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<String> getCategoryCodes() {
		return categoryCodes;
	}

	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}

	public List<Integer> getWeixinItemNums() {
		return weixinItemNums;
	}

	public void setWeixinItemNums(List<Integer> weixinItemNums) {
		this.weixinItemNums = weixinItemNums;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

}
