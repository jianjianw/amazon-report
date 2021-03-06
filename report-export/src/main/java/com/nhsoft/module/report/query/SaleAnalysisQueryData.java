package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class SaleAnalysisQueryData extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4068231328591768862L;
	private Date dtFrom;
	private Date dtTo;
	private List<Integer> branchNums;//分店
	private List<String> brandCodes;//品牌
	private List<String> posItemTypeCodes;//商品类别
	private List<Integer> posItemNums;//商品
	private Boolean isQueryChild;//按大类汇总
	private Boolean isQueryCF;//组合商品按成分统计
	private Boolean isQueryGrade = false;//分级商品按分级汇总;
	private String itemDepartments;
	private Integer itemFlagNum;
	private String saleType;//销售方式：微商城、实体店
	private Boolean isQueryCardUser = false;//查询会员消费;
	private List<String> orderSources;
	private List<TwoStringValueData> twoStringValueDatas;
	private Boolean queryItemExtendAttribute;
	private Integer appUserNum;

	private Integer merchantNum;
	private Integer stallNum;
	private Boolean isPolicy;

	private Integer offset;
	private Integer limit;
	private boolean page = true;
	private String sortField;
	private String sortType;

	private Integer max = 50000;

	public Integer getMax() {
		return max == null?50000:max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getAppUserNum() {
		return appUserNum;
	}

	public void setAppUserNum(Integer appUserNum) {
		this.appUserNum = appUserNum;
	}

	public Boolean getQueryItemExtendAttribute() {
		return queryItemExtendAttribute;
	}

	public void setQueryItemExtendAttribute(Boolean queryItemExtendAttribute) {
		this.queryItemExtendAttribute = queryItemExtendAttribute;
	}


	public List<TwoStringValueData> getTwoStringValueDatas() {
		return twoStringValueDatas;
	}

	public void setTwoStringValueDatas(List<TwoStringValueData> twoStringValueDatas) {
		this.twoStringValueDatas = twoStringValueDatas;
	}

	public List<String> getOrderSources() {
		return orderSources;
	}

	public void setOrderSources(List<String> orderSources) {
		this.orderSources = orderSources;
	}

	public Boolean getIsQueryCardUser() {
		return isQueryCardUser;
	}

	public void setIsQueryCardUser(Boolean isQueryCardUser) {
		this.isQueryCardUser = isQueryCardUser;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public Integer getItemFlagNum() {
		return itemFlagNum;
	}

	public void setItemFlagNum(Integer itemFlagNum) {
		this.itemFlagNum = itemFlagNum;
	}

	public Date getDtFrom() {
		return dtFrom;
	}

	public void setDtFrom(Date dtFrom) {
		this.dtFrom = dtFrom;
	}

	public Date getDtTo() {
		return dtTo;
	}

	public void setDtTo(Date dtTo) {
		this.dtTo = dtTo;
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

	public Boolean getIsQueryChild() {
		return isQueryChild;
	}

	public void setIsQueryChild(Boolean isQueryChild) {
		this.isQueryChild = isQueryChild;
	}

	public Boolean getIsQueryCF() {
		return isQueryCF;
	}

	public void setIsQueryCF(Boolean isQueryCF) {
		this.isQueryCF = isQueryCF;
	}

	public Boolean getIsQueryGrade() {
		return isQueryGrade;
	}

	public void setIsQueryGrade(Boolean isQueryGrade) {
		this.isQueryGrade = isQueryGrade;
	}

	public String getItemDepartments() {
		return itemDepartments;
	}

	public void setItemDepartments(String itemDepartments) {
		this.itemDepartments = itemDepartments;
	}

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}

	public Boolean getPolicy() {
		return isPolicy;
	}

	public void setPolicy(Boolean policy) {
		isPolicy = policy;
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

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
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

	private String getDateStr(Date date){
		StringBuffer sb = new StringBuffer();
		sb.append(date.getYear() + 1900).append("-").append(date.getMonth() + 1).append("-").append(date.getDate());
		return sb.toString();
	}
	
	public String getKey() {
		StringBuffer sb = new StringBuffer();
		sb.append("systemBookCode:");
		sb.append(systemBookCode);
		sb.append("dtFrom:");
		sb.append(getDateStr(dtFrom));
		sb.append("dtTo:");
		sb.append(getDateStr(dtTo));
		if(isQueryCF != null){
			sb.append("isQueryCF:");
			sb.append(isQueryCF.toString());
			
		}
		if(branchNums != null){
			sb.append("branchNums:");
			sb.append(branchNums.toString());
			
		}
		if(brandCodes != null){
			sb.append("brandCodes:");
			sb.append(brandCodes.toString());

		}
		if(posItemTypeCodes != null){
			sb.append("posItemTypeCodes:");
			sb.append(posItemTypeCodes.toString());
		}
		if(posItemNums != null){
			sb.append("posItemNums:");
			sb.append(posItemNums.toString());
	
		}
		if(itemDepartments != null){
			sb.append("itemDepartments:");
			sb.append(itemDepartments);
		}
		if(saleType != null){
			sb.append("saleType:");
			sb.append(saleType);
		}
		if(isQueryGrade == null){
			isQueryGrade = false;
		}
		if(merchantNum != null) {
			sb.append("merchantNum:");
			sb.append(merchantNum);
		}
		if(stallNum != null) {
			sb.append("stallNum:");
			sb.append(stallNum);
		}
		if(isPolicy != null) {
			sb.append("isPolicy:");
			sb.append(isPolicy);
		}

		if(offset != null){
			sb.append("offset:");
			sb.append(offset);
		}
		if(limit != null){
			sb.append("limit:");
			sb.append(limit);
		}

		if(sortField != null){
			sb.append("sortField:");
			sb.append(sortField);
		}
		if(sortType != null){
			sb.append("sortType:");
			sb.append(sortType);
		}

		sb.append("isQueryGrade:");
		sb.append(isQueryGrade.toString());
		if(isQueryCardUser == null){
			isQueryCardUser = false;
		}
		if(orderSources != null){
			sb.append("orderSources:");
			sb.append(orderSources.toString());
		}
		sb.append("isQueryCardUser:");
		sb.append(isQueryCardUser.toString());
		
		if(twoStringValueDatas != null && twoStringValueDatas.size() > 0){
			sb.append("twoStringValueDatas:");
			for(int i = 0;i < twoStringValueDatas.size();i++){
				TwoStringValueData data = twoStringValueDatas.get(i);
				sb.append(data.getKey()).append("|").append(data.getValue());
			}
		}
		return sb.toString();
	}

	
}
