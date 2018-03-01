package com.nhsoft.module.report.queryBuilder;

import com.nhsoft.module.report.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardReportQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9110900859257239866L;
	private Integer branchNum;// 发卡
	private Integer operateBranch;// 操作
	private String cardPrintNum;
	private String operator;
	private Boolean prizeCard;
	private Date dateFrom;// 操作
	private Date dateTo; // 操作
	private String compareType;
	private BigDecimal compareValue;
	private String cardTypeName;
	private boolean unPayFlag = false;//未扣款标记
	private List<Integer> branchNums;
	private Integer cardUserNum;
	private String paymentType;//收款方式
	private List<Integer> operateBranchNums;
	private boolean queryDate = false;
	private Boolean firstDeposit;//是否首存
	private Integer cardUserCardType;
	private Integer category; //交易类型
	private String seller;//销售员
	
	private String sortField;
	private String sortType;
	private boolean isPaging = true;
	private boolean isQueryDetail = false;
	private boolean isQueryPayment = false;
	private int offset;
	private int limit;
	
	private List<String> clientPointOperateType = new ArrayList<String>();
		
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public boolean isQueryPayment() {
		return isQueryPayment;
	}

	public void setQueryPayment(boolean isQueryPayment) {
		this.isQueryPayment = isQueryPayment;
	}

	public boolean isQueryDetail() {
		return isQueryDetail;
	}

	public void setQueryDetail(boolean isQueryDetail) {
		this.isQueryDetail = isQueryDetail;
	}

	public Integer getCardUserCardType() {
		return cardUserCardType;
	}

	public void setCardUserCardType(Integer cardUserCardType) {
		this.cardUserCardType = cardUserCardType;
	}

	public Boolean getFirstDeposit() {
		return firstDeposit;
	}

	public void setFirstDeposit(Boolean firstDeposit) {
		this.firstDeposit = firstDeposit;
	}

	public boolean isQueryDate() {
		return queryDate;
	}

	public void setQueryDate(boolean queryDate) {
		this.queryDate = queryDate;
	}

	public List<Integer> getOperateBranchNums() {
		return operateBranchNums;
	}

	public void setOperateBranchNums(List<Integer> operateBranchNums) {
		this.operateBranchNums = operateBranchNums;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getOperateBranch() {
		return operateBranch;
	}

	public void setOperateBranch(Integer operateBranch) {
		this.operateBranch = operateBranch;
	}

	public String getCardPrintNum() {
		return cardPrintNum;
	}

	public void setCardPrintNum(String cardPrintNum) {
		this.cardPrintNum = cardPrintNum;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Boolean getPrizeCard() {
		return prizeCard;
	}

	public void setPrizeCard(Boolean prizeCard) {
		this.prizeCard = prizeCard;
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

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	public BigDecimal getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(BigDecimal compareValue) {
		this.compareValue = compareValue;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public boolean isUnPayFlag() {
		return unPayFlag;
	}

	public void setUnPayFlag(boolean unPayFlag) {
		this.unPayFlag = unPayFlag;
	}

	public List<String> getClientPointOperateType() {
		return clientPointOperateType;
	}

	public void setClientPointOperateType(List<String> clientPointOperateType) {
		this.clientPointOperateType = clientPointOperateType;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
