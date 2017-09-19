package com.nhsoft.report.shared.queryBuilder;

import java.util.Date;
import java.util.List;

public class TicketSendDetailQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1062889026800935063L;
	private List<Integer> branchNums;
	private Date dateFrom; // 有效期起
	private Date dateTo; // 有效期至
	private String stateName;
	private String printedNum; // 券编号
	private String exactPrintedNum; //精确券编号
	private String cardUserPrintNum; // 会员卡号
	private List<String> actionIds; // 营销活动Id
	private Integer consumeAccountNum;
	private String dateType;
	private String systemBookCode;
	private String couponId;
	private String ticketType;// 券类型
	private Integer stateType; //0全部  1已使用 2有效 3 过期
	private boolean paging = true;

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
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

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getPrintedNum() {
		return printedNum;
	}

	public void setPrintedNum(String printedNum) {
		this.printedNum = printedNum;
	}

	public String getCardUserPrintNum() {
		return cardUserPrintNum;
	}

	public void setCardUserPrintNum(String cardUserPrintNum) {
		this.cardUserPrintNum = cardUserPrintNum;
	}

	public Integer getConsumeAccountNum() {
		return consumeAccountNum;
	}

	public void setConsumeAccountNum(Integer consumeAccountNum) {
		this.consumeAccountNum = consumeAccountNum;
	}

	public List<String> getActionIds() {
		return actionIds;
	}

	public void setActionIds(List<String> actionIds) {
		this.actionIds = actionIds;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public boolean isPaging() {
		return paging;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

	public String getExactPrintedNum() {
		return exactPrintedNum;
	}

	public void setExactPrintedNum(String exactPrintedNum) {
		this.exactPrintedNum = exactPrintedNum;
	}

}