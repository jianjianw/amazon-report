package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

/**
 * 促销商品查询条件
 * @author lsd
 *
 */
public class PolicyPosItemQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 903300107300938904L;
	private Integer branchNum;
	private Date dtFrom;
	private Date dtTo;
	private List<Integer> branchNums;
	private List<Integer> itemNums;
	private String policyType;
	private String policyOrderFid;
	private List<String> categoryCodes;
	private Date policyDateFrom;
	private Date policyDateTo;
	private String policyCategory;

	public String getPolicyCategory() {
		return policyCategory;
	}


	public void setPolicyCategory(String policyCategory) {
		this.policyCategory = policyCategory;
	}


	public Date getPolicyDateFrom() {
		return policyDateFrom;
	}


	public void setPolicyDateFrom(Date policyDateFrom) {
		this.policyDateFrom = policyDateFrom;
	}


	public Date getPolicyDateTo() {
		return policyDateTo;
	}


	public void setPolicyDateTo(Date policyDateTo) {
		this.policyDateTo = policyDateTo;
	}


	public List<String> getCategoryCodes() {
		return categoryCodes;
	}


	public void setCategoryCodes(List<String> categoryCodes) {
		this.categoryCodes = categoryCodes;
	}


	public String getPolicyOrderFid() {
		return policyOrderFid;
	}


	public void setPolicyOrderFid(String policyOrderFid) {
		this.policyOrderFid = policyOrderFid;
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


	public List<Integer> getItemNums() {
		return itemNums;
	}


	public void setItemNums(List<Integer> itemNums) {
		this.itemNums = itemNums;
	}


	public String getPolicyType() {
		return policyType;
	}


	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}


	public Integer getBranchNum() {
		return branchNum;
	}


	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}


	public void clear(){
		dtFrom = null;
		dtTo = null;
		branchNums = null;
		itemNums = null;
		policyType = null;
	}
}
