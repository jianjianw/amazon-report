package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class WarningInterfaceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4213451403115506384L;
	private String interfaceName;
	private Integer executionTotalTime;
	private Integer executionAvgTime;
	private Integer executionMinTime;
	private Integer executionMaxTime;
	private Integer executionRowCount;
	private Integer executionCount;
	
	public WarningInterfaceDTO(){
		executionCount = 0;
		executionTotalTime = 0;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getExecutionAvgTime() {
		return executionAvgTime;
	}

	public void setExecutionAvgTime(Integer executionAvgTime) {
		this.executionAvgTime = executionAvgTime;
	}

	public Integer getExecutionMinTime() {
		return executionMinTime;
	}

	public void setExecutionMinTime(Integer executionMinTime) {
		this.executionMinTime = executionMinTime;
	}

	public Integer getExecutionMaxTime() {
		return executionMaxTime;
	}

	public void setExecutionMaxTime(Integer executionMaxTime) {
		this.executionMaxTime = executionMaxTime;
	}

	public Integer getExecutionRowCount() {
		return executionRowCount;
	}

	public void setExecutionRowCount(Integer executionRowCount) {
		this.executionRowCount = executionRowCount;
	}

	public Integer getExecutionCount() {
		return executionCount;
	}

	public void setExecutionCount(Integer executionCount) {
		this.executionCount = executionCount;
	}

	public Integer getExecutionTotalTime() {
		return executionTotalTime;
	}

	public void setExecutionTotalTime(Integer executionTotalTime) {
		this.executionTotalTime = executionTotalTime;
	}

}
