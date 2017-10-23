package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TaskTransferSendDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5017925672523185408L;
	private Integer toBranchNum;
	private BigDecimal transferMoney;
	private String driverPhone;
	private String driverNo;
	private Integer supplierNum;

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public TaskTransferSendDTO() {
		setTransferMoney(BigDecimal.ZERO);
	}

	public Integer getToBranchNum() {
		return toBranchNum;
	}

	public void setToBranchNum(Integer toBranchNum) {
		this.toBranchNum = toBranchNum;
	}

	public BigDecimal getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(BigDecimal transferMoney) {
		this.transferMoney = transferMoney;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}
}
