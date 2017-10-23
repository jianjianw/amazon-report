package com.nhsoft.module.report.shared.queryBuilder;

import java.util.Date;

public class TicketSendQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8538352992364769748L;

	private Integer applyBranchNum; // 应用分店
	private Integer useBranchNum; // 使用分店
	private Date dateFrom; //
	private Date dateTo; //
	private String stateName; // 状态
	private String printedNumFrom; // 券编号
	private String printedNumTo; // 券编号
	private String ticketType;// 券类型
	private Integer stateType; //0全部  1已使用 2有效 3 过期
	private Integer cardUserNum;
	private String sortField;
	private String sortType;
	private boolean isPaging = true;
	private String printedNum;
	private String barCode;
	private String dateType;	//时间类型

	public String getPrintedNum() {
		return printedNum;
	}

	public void setPrintedNum(String printedNum) {
		this.printedNum = printedNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Integer getApplyBranchNum() {
		return applyBranchNum;
	}

	public void setApplyBranchNum(Integer applyBranchNum) {
		this.applyBranchNum = applyBranchNum;
	}

	public Integer getUseBranchNum() {
		return useBranchNum;
	}

	public void setUseBranchNum(Integer useBranchNum) {
		this.useBranchNum = useBranchNum;
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPrintedNumFrom() {
		return printedNumFrom;
	}

	public void setPrintedNumFrom(String printedNumFrom) {
		this.printedNumFrom = printedNumFrom;
	}

	public String getPrintedNumTo() {
		return printedNumTo;
	}

	public void setPrintedNumTo(String printedNumTo) {
		this.printedNumTo = printedNumTo;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
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

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
}
