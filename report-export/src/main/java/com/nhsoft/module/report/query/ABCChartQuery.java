package com.nhsoft.module.report.query;

import java.util.Date;
import java.util.List;

public class ABCChartQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219192918386299172L;
	private Integer branchNum;           				//当前店
	private List<Integer> branchNums;			//分店List
    private Date dateFrom;								//开始时间
    private Date dateTo; 									//结束时间
    private List<String> types;            				//【调出，批发， 销售】
    private List<Integer> posItemNums;			//商品nums

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
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

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public List<Integer> getPosItemNums() {
		return posItemNums;
	}

	public void setPosItemNums(List<Integer> posItemNums) {
		this.posItemNums = posItemNums;
	}

}
