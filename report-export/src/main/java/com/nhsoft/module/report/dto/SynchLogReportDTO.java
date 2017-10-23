package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.Date;

public class SynchLogReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2803067358526889443L;
	private String systemBookCode;
	private String systemBookName;
	private Integer branchNum;
	private String branchName;
	private String synchDirect;
	private String synchContent;
	private Integer synchSuccessCount;
	private Integer synchFailCount;
	private Date synchLastSuccessDate;
	private Date synchLastFailDate;
	private Integer synchAvgTime;
	private Integer synchLongTime;
	private Integer synchShortTime;

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getSystemBookName() {
		return systemBookName;
	}

	public void setSystemBookName(String systemBookName) {
		this.systemBookName = systemBookName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getSynchDirect() {
		return synchDirect;
	}

	public void setSynchDirect(String synchDirect) {
		this.synchDirect = synchDirect;
	}

	public String getSynchContent() {
		return synchContent;
	}

	public void setSynchContent(String synchContent) {
		this.synchContent = synchContent;
	}

	public Integer getSynchSuccessCount() {
		return synchSuccessCount;
	}

	public void setSynchSuccessCount(Integer synchSuccessCount) {
		this.synchSuccessCount = synchSuccessCount;
	}

	public Integer getSynchFailCount() {
		return synchFailCount;
	}

	public void setSynchFailCount(Integer synchFailCount) {
		this.synchFailCount = synchFailCount;
	}

	public Date getSynchLastSuccessDate() {
		return synchLastSuccessDate;
	}

	public void setSynchLastSuccessDate(Date synchLastSuccessDate) {
		this.synchLastSuccessDate = synchLastSuccessDate;
	}

	public Date getSynchLastFailDate() {
		return synchLastFailDate;
	}

	public void setSynchLastFailDate(Date synchLastFailDate) {
		this.synchLastFailDate = synchLastFailDate;
	}

	public Integer getSynchAvgTime() {
		return synchAvgTime;
	}

	public void setSynchAvgTime(Integer synchAvgTime) {
		this.synchAvgTime = synchAvgTime;
	}

	public Integer getSynchLongTime() {
		return synchLongTime;
	}

	public void setSynchLongTime(Integer synchLongTime) {
		this.synchLongTime = synchLongTime;
	}

	public Integer getSynchShortTime() {
		return synchShortTime;
	}

	public void setSynchShortTime(Integer synchShortTime) {
		this.synchShortTime = synchShortTime;
	}

}
