package com.nhsoft.report.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class MonthReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3430814653177456849L;
	private int branchNum;            //分店号
	private String branchName;        //分店名称
	
	private BigDecimal january;
	private BigDecimal february;
	private BigDecimal march;
	private BigDecimal april;
	private BigDecimal may;
	private BigDecimal june;
	private BigDecimal july;
	private BigDecimal august;
	private BigDecimal september;
	private BigDecimal october;
	private BigDecimal november;
	private BigDecimal december;
	
	private BigDecimal average;      //平均
	private BigDecimal summary;      //汇总
	
	public int getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(int branchNum) {
		this.branchNum = branchNum;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public BigDecimal getJanuary() {
		return january;
	}
	
	public void setJanuary(BigDecimal january) {
		this.january = january;
	}
	
	public BigDecimal getFebruary() {
		return february;
	}
	
	public void setFebruary(BigDecimal february) {
		this.february = february;
	}
	
	public BigDecimal getMarch() {
		return march;
	}
	
	public void setMarch(BigDecimal march) {
		this.march = march;
	}
	
	public BigDecimal getApril() {
		return april;
	}
	
	public void setApril(BigDecimal april) {
		this.april = april;
	}
	
	public BigDecimal getMay() {
		return may;
	}
	
	public void setMay(BigDecimal may) {
		this.may = may;
	}
	
	public BigDecimal getJune() {
		return june;
	}
	
	public void setJune(BigDecimal june) {
		this.june = june;
	}
	
	public BigDecimal getJuly() {
		return july;
	}
	
	public void setJuly(BigDecimal july) {
		this.july = july;
	}
	
	public BigDecimal getAugust() {
		return august;
	}
	
	public void setAugust(BigDecimal august) {
		this.august = august;
	}
	
	public BigDecimal getSeptember() {
		return september;
	}
	
	public void setSeptember(BigDecimal september) {
		this.september = september;
	}
	
	public BigDecimal getOctober() {
		return october;
	}
	
	public void setOctober(BigDecimal october) {
		this.october = october;
	}
	
	public BigDecimal getNovember() {
		return november;
	}
	
	public void setNovember(BigDecimal november) {
		this.november = november;
	}
	
	public BigDecimal getDecember() {
		return december;
	}
	
	public void setDecember(BigDecimal december) {
		this.december = december;
	}
	
	public BigDecimal getAverage() {
		return average;
	}
	
	public void setAverage(BigDecimal average) {
		this.average = average;
	}
	
	public BigDecimal getSummary() {
		return summary;
	}
	
	public void setSummary(BigDecimal summary) {
		this.summary = summary;
	}
	
	public static MonthReportDTO get(List<MonthReportDTO> list, int branchNum) {
		for(int i = 0;i < list.size();i++){
			MonthReportDTO monthReportDTO = list.get(i);
			if(monthReportDTO.getBranchNum() == branchNum){
				return monthReportDTO;
			}
		}
		return null;
	}
}
