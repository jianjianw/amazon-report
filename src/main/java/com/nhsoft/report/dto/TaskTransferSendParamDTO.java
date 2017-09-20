package com.nhsoft.report.dto;


import com.nhsoft.report.model.AppUser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TaskTransferSendParamDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8709235130098960085L;
	private String systemBookCode;
	private Integer branchNum;
	private Integer transferLineNum;
	private String taskTransferBizday;
	private AppUser appUser;
	private BigDecimal transferlineMoney;
	private boolean auditOutOrder = true;
	private boolean hasOrderTask = true;
	private Integer toBranchNum;
	private boolean needTransferLine = true;
	private TaskTransferSendDTO transferSendDTO;
	private List<Integer> sendItemNums;
	private Boolean sendDirectTransfer;
	private Boolean sendPickedTransfer;//发送已配货确认的任务

	public Boolean getSendPickedTransfer() {
		return sendPickedTransfer;
	}

	public void setSendPickedTransfer(Boolean sendPickedTransfer) {
		this.sendPickedTransfer = sendPickedTransfer;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}
	
	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public Integer getTransferLineNum() {
		return transferLineNum;
	}
	
	public void setTransferLineNum(Integer transferLineNum) {
		this.transferLineNum = transferLineNum;
	}
	
	public String getTaskTransferBizday() {
		return taskTransferBizday;
	}
	
	public void setTaskTransferBizday(String taskTransferBizday) {
		this.taskTransferBizday = taskTransferBizday;
	}
	
	public AppUser getAppUser() {
		return appUser;
	}
	
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	
	public BigDecimal getTransferlineMoney() {
		return transferlineMoney;
	}
	
	public void setTransferlineMoney(BigDecimal transferlineMoney) {
		this.transferlineMoney = transferlineMoney;
	}
	
	public boolean isAuditOutOrder() {
		return auditOutOrder;
	}
	
	public void setAuditOutOrder(boolean auditOutOrder) {
		this.auditOutOrder = auditOutOrder;
	}
	
	public boolean isHasOrderTask() {
		return hasOrderTask;
	}
	
	public void setHasOrderTask(boolean hasOrderTask) {
		this.hasOrderTask = hasOrderTask;
	}
	
	public Integer getToBranchNum() {
		return toBranchNum;
	}
	
	public void setToBranchNum(Integer toBranchNum) {
		this.toBranchNum = toBranchNum;
	}
	
	public boolean isNeedTransferLine() {
		return needTransferLine;
	}
	
	public void setNeedTransferLine(boolean needTransferLine) {
		this.needTransferLine = needTransferLine;
	}
	
	public TaskTransferSendDTO getTransferSendDTO() {
		return transferSendDTO;
	}
	
	public void setTransferSendDTO(TaskTransferSendDTO transferSendDTO) {
		this.transferSendDTO = transferSendDTO;
	}
	
	public List<Integer> getSendItemNums() {
		return sendItemNums;
	}
	
	public void setSendItemNums(List<Integer> sendItemNums) {
		this.sendItemNums = sendItemNums;
	}
	
	public Boolean getSendDirectTransfer() {
		return sendDirectTransfer;
	}
	
	public void setSendDirectTransfer(Boolean sendDirectTransfer) {
		this.sendDirectTransfer = sendDirectTransfer;
	}
	
}
