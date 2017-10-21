package com.nhsoft.module.report.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CardConsuemAnalysisQuery extends QueryBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5980326478182605474L;
	private Date dateFrom;
	private Date dateTo;
	private Date timeFrom;
	private Date timeTo;
	private BigDecimal moneySpace;
	private BigDecimal moneyFrom;
	private BigDecimal moneyTo;
	private List<Integer> branchNums;
	private List<Integer> cardUserNums;

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

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}

	public BigDecimal getMoneySpace() {
		return moneySpace;
	}

	public void setMoneySpace(BigDecimal moneySpace) {
		this.moneySpace = moneySpace;
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public List<Integer> getCardUserNums() {
		return cardUserNums;
	}

	public void setCardUserNums(List<Integer> cardUserNums) {
		this.cardUserNums = cardUserNums;
	}

	public BigDecimal getMoneyFrom() {
		return moneyFrom;
	}

	public void setMoneyFrom(BigDecimal moneyFrom) {
		this.moneyFrom = moneyFrom;
	}

	public BigDecimal getMoneyTo() {
		return moneyTo;
	}

	public void setMoneyTo(BigDecimal moneyTo) {
		this.moneyTo = moneyTo;
	}

	@Override
	public boolean checkQueryBuild() {
		
		return false;
	}

}
