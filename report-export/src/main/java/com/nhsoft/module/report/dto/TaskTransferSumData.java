package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaskTransferSumData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7799277159986182894L;
	private Integer taskTransferBranchNum;
	private BigDecimal taskTransferOutQty;
	private BigDecimal taskTransferOutBaseQty;
	private BigDecimal taskTransferOutViewQty;

	
	public TaskTransferSumData() {
		setTaskTransferOutQty(BigDecimal.ZERO);
		setTaskTransferOutBaseQty(BigDecimal.ZERO);
		setTaskTransferOutViewQty(BigDecimal.ZERO);
	}
	public Integer getTaskTransferBranchNum() {
		return taskTransferBranchNum;
	}
	public void setTaskTransferBranchNum(Integer taskTransferBranchNum) {
		this.taskTransferBranchNum = taskTransferBranchNum;
	}
	public BigDecimal getTaskTransferOutQty() {
		return taskTransferOutQty;
	}
	public void setTaskTransferOutQty(BigDecimal taskTransferOutQty) {
		this.taskTransferOutQty = taskTransferOutQty;
	}
	public BigDecimal getTaskTransferOutBaseQty() {
		return taskTransferOutBaseQty;
	}
	public void setTaskTransferOutBaseQty(BigDecimal taskTransferOutBaseQty) {
		this.taskTransferOutBaseQty = taskTransferOutBaseQty;
	}
	public BigDecimal getTaskTransferOutViewQty() {
		return taskTransferOutViewQty;
	}
	public void setTaskTransferOutViewQty(BigDecimal taskTransferOutViewQty) {
		this.taskTransferOutViewQty = taskTransferOutViewQty;
	}
	
	
}
