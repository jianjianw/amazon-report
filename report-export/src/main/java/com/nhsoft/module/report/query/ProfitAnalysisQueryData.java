package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;


public class ProfitAnalysisQueryData extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4446652887877243107L;
	private Date shiftTableFrom;//营业日起
	private Date shiftTableTo;//营业日止
	private List<Integer> branchNums;//分店
	private List<String> brandCodes;//品牌
	private List<String> posItemTypeCodes;//商品类别
	private List<Integer> posItemNums;//商品
	private List<String> clientFids;//客户
	private Boolean isQueryCF;//组合商品按成分统计
	private Boolean isQueryChild;//统计类别包含子类
	private Date checkFrom;//盘点时间起 只在毛利分析里使用
	private Date checkTo;//盘点时间止 只在毛利分析里使用
	private boolean isQueryClient = false; //是否查询客户
	private Integer itemFlagNum;
	private boolean isQueryPresent = false; //是否查询赠品
	private String saleType;//销售方式：微商城、实体店
	private List<String> orderSources;
	private boolean page = true;
	private Integer offset;
	private Integer limit;

	private Integer max = 50000;

	public Integer getMax() {
		return max == null?50000:max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public List<String> getOrderSources() {
		return orderSources;
	}


	public void setOrderSources(List<String> orderSources) {
		this.orderSources = orderSources;
	}


	public String getSaleType() {
		return saleType;
	}


	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}


	public boolean isQueryPresent() {
		return isQueryPresent;
	}


	public void setQueryPresent(boolean isQueryPresent) {
		this.isQueryPresent = isQueryPresent;
	}


	public Integer getItemFlagNum() {
		return itemFlagNum;
	}


	public void setItemFlagNum(Integer itemFlagNum) {
		this.itemFlagNum = itemFlagNum;
	}


	public boolean isQueryClient() {
		return isQueryClient;
	}


	public void setQueryClient(boolean isQueryClient) {
		this.isQueryClient = isQueryClient;
	}


	public Date getShiftTableFrom() {
		return shiftTableFrom;
	}


	public void setShiftTableFrom(Date shiftTableFrom) {
		this.shiftTableFrom = shiftTableFrom;
	}


	public Date getShiftTableTo() {
		return shiftTableTo;
	}


	public void setShiftTableTo(Date shiftTableTo) {
		this.shiftTableTo = shiftTableTo;
	}


	public List<Integer> getBranchNums() {
		return branchNums;
	}


	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}



	public List<String> getBrandCodes() {
		return brandCodes;
	}


	public void setBrandCodes(List<String> brandCodes) {
		this.brandCodes = brandCodes;
	}


	public List<String> getPosItemTypeCodes() {
		return posItemTypeCodes;
	}


	public void setPosItemTypeCodes(List<String> posItemTypeCodes) {
		this.posItemTypeCodes = posItemTypeCodes;
	}


	public List<Integer> getPosItemNums() {
		return posItemNums;
	}


	public void setPosItemNums(List<Integer> posItemNums) {
		this.posItemNums = posItemNums;
	}


	public List<String> getClientFids() {
		return clientFids;
	}


	public void setClientFids(List<String> clientFids) {
		this.clientFids = clientFids;
	}


	public Boolean getIsQueryCF() {
		return isQueryCF;
	}


	public void setIsQueryCF(Boolean isQueryCF) {
		this.isQueryCF = isQueryCF;
	}


	public Boolean getIsQueryChild() {
		return isQueryChild;
	}


	public void setIsQueryChild(Boolean isQueryChild) {
		this.isQueryChild = isQueryChild;
	}


	public Date getCheckFrom() {
		return checkFrom;
	}


	public void setCheckFrom(Date checkFrom) {
		this.checkFrom = checkFrom;
	}


	public Date getCheckTo() {
		return checkTo;
	}


	public void setCheckTo(Date checkTo) {
		this.checkTo = checkTo;
	}


	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
