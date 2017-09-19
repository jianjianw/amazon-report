package com.nhsoft.report.shared.queryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CardUserQuery extends QueryBuilder{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5719439848468255358L;
	private List<Integer> branchNums;
	private String cardUserName;//名称
	private String cardUserType;//类型
	private String cardUsetPrintNum; //表面卡号
	private String cardUserException;//异常状态
	private Boolean islock;//锁定
	private String stateName;// 状态
	private String cardPrintNum; 
	private String cardPhone;
	private Date birthDay;//生日
	private Boolean month;
	private Boolean day;
	private String cardIdNum;  //
	private Date dateFrom;//发卡
	private Date dateTo; //发卡
	private BigDecimal balanceForm; //余额
	private BigDecimal balanceTo;
	private BigDecimal consumeForm;//消费
	private BigDecimal consumeTo;
	private BigDecimal depositForm;//存款
	private BigDecimal depositTo;
	private BigDecimal pointForm;//积分
	private BigDecimal pointTo;
	private BigDecimal pointBalanceFrom;//积分余额
	private BigDecimal pointBalanceTo;
	private List<Integer> cardUserNums;
	private Boolean valid; //是否查有效卡
	private Date birthFrom;
	private Date birthTo;
	private Date deadlineFrom;//有效日期
	private Date deadlineTo;//有效日期
	private List<Integer> cardUserTypes;
	private String cardUserStorage;
	
	private String sortField;
	private String sortType;
	private boolean isPaging = true;
	
	
	public CardUserQuery(){

	}

	public String getCardUserStorage() {
		return cardUserStorage;
	}

	public void setCardUserStorage(String cardUserStorage) {
		this.cardUserStorage = cardUserStorage;
	}

	public List<Integer> getCardUserTypes() {
		return cardUserTypes;
	}

	public void setCardUserTypes(List<Integer> cardUserTypes) {
		this.cardUserTypes = cardUserTypes;
	}

	public Date getDeadlineFrom() {
		return deadlineFrom;
	}

	public void setDeadlineFrom(Date deadlineFrom) {
		this.deadlineFrom = deadlineFrom;
	}

	public Date getDeadlineTo() {
		return deadlineTo;
	}

	public void setDeadlineTo(Date deadlineTo) {
		this.deadlineTo = deadlineTo;
	}

	public BigDecimal getPointBalanceFrom() {
		return pointBalanceFrom;
	}

	public void setPointBalanceFrom(BigDecimal pointBalanceFrom) {
		this.pointBalanceFrom = pointBalanceFrom;
	}

	public BigDecimal getPointBalanceTo() {
		return pointBalanceTo;
	}

	public void setPointBalanceTo(BigDecimal pointBalanceTo) {
		this.pointBalanceTo = pointBalanceTo;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	public String getCardUserType() {
		return cardUserType;
	}

	public void setCardUserType(String cardUserType) {
		this.cardUserType = cardUserType;
	}

	public String getCardUsetPrintNum() {
		return cardUsetPrintNum;
	}

	public void setCardUsetPrintNum(String cardUsetPrintNum) {
		this.cardUsetPrintNum = cardUsetPrintNum;
	}

	public String getCardUserException() {
		return cardUserException;
	}

	public void setCardUserException(String cardUserException) {
		this.cardUserException = cardUserException;
	}

	public Boolean getIslock() {
		return islock;
	}

	public void setIslock(Boolean islock) {
		this.islock = islock;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCardPrintNum() {
		return cardPrintNum;
	}

	public void setCardPrintNum(String cardPrintNum) {
		this.cardPrintNum = cardPrintNum;
	}

	public String getCardPhone() {
		return cardPhone;
	}

	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Boolean getMonth() {
		return month;
	}

	public void setMonth(Boolean month) {
		this.month = month;
	}

	public Boolean getDay() {
		return day;
	}

	public void setDay(Boolean day) {
		this.day = day;
	}

	public String getCardIdNum() {
		return cardIdNum;
	}

	public void setCardIdNum(String cardIdNum) {
		this.cardIdNum = cardIdNum;
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

	public BigDecimal getBalanceForm() {
		return balanceForm;
	}

	public void setBalanceForm(BigDecimal balanceForm) {
		this.balanceForm = balanceForm;
	}

	public BigDecimal getBalanceTo() {
		return balanceTo;
	}

	public void setBalanceTo(BigDecimal balanceTo) {
		this.balanceTo = balanceTo;
	}

	public BigDecimal getConsumeForm() {
		return consumeForm;
	}

	public void setConsumeForm(BigDecimal consumeForm) {
		this.consumeForm = consumeForm;
	}

	public BigDecimal getConsumeTo() {
		return consumeTo;
	}

	public void setConsumeTo(BigDecimal consumeTo) {
		this.consumeTo = consumeTo;
	}

	public BigDecimal getDepositForm() {
		return depositForm;
	}

	public void setDepositForm(BigDecimal depositForm) {
		this.depositForm = depositForm;
	}

	public BigDecimal getDepositTo() {
		return depositTo;
	}

	public void setDepositTo(BigDecimal depositTo) {
		this.depositTo = depositTo;
	}

	public BigDecimal getPointForm() {
		return pointForm;
	}

	public void setPointForm(BigDecimal pointForm) {
		this.pointForm = pointForm;
	}

	public BigDecimal getPointTo() {
		return pointTo;
	}

	public void setPointTo(BigDecimal pointTo) {
		this.pointTo = pointTo;
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

	public List<Integer> getCardUserNums() {
		return cardUserNums;
	}

	public void setCardUserNums(List<Integer> cardUserNums) {
		this.cardUserNums = cardUserNums;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Date getBirthFrom() {
		return birthFrom;
	}

	public void setBirthFrom(Date birthFrom) {
		this.birthFrom = birthFrom;
	}

	public Date getBirthTo() {
		return birthTo;
	}

	public void setBirthTo(Date birthTo) {
		this.birthTo = birthTo;
	}
	
	
}
