package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;


/**
 * ShiftTableId generated by hbm2java
 */
@Embeddable
public class ShiftTableId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3283199860369666345L;
	private String systemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;

	public ShiftTableId() {
	}

	public ShiftTableId(String systemBookCode, Integer branchNum,
			Integer shiftTableNum, String shiftTableBizday) {
		this.systemBookCode = systemBookCode;
		this.branchNum = branchNum;
		this.shiftTableNum = shiftTableNum;
		this.shiftTableBizday = shiftTableBizday;
	}

	public String getSystemBookCode() {
		return this.systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getBranchNum() {
		return this.branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getShiftTableNum() {
		return this.shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return this.shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ShiftTableId that = (ShiftTableId) o;

		if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
			return false;
		if (branchNum != null ? !branchNum.equals(that.branchNum) : that.branchNum != null) return false;
		if (shiftTableNum != null ? !shiftTableNum.equals(that.shiftTableNum) : that.shiftTableNum != null)
			return false;
		return shiftTableBizday != null ? shiftTableBizday.equals(that.shiftTableBizday) : that.shiftTableBizday == null;
	}

	@Override
	public int hashCode() {
		int result = systemBookCode != null ? systemBookCode.hashCode() : 0;
		result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
		result = 31 * result + (shiftTableNum != null ? shiftTableNum.hashCode() : 0);
		result = 31 * result + (shiftTableBizday != null ? shiftTableBizday.hashCode() : 0);
		return result;
	}

	public static ShiftTableId getByClosedCashId(String closedCashId) {
		String[] array = closedCashId.split("-");
		ShiftTableId id = new ShiftTableId(array[0], Integer.parseInt(array[1]), Integer.parseInt(array[3]), array[2]);
		return id;
	}

	public String getClosedCashId() {
		return systemBookCode + "-" + branchNum + "-" + shiftTableBizday + "-" + shiftTableNum;
	}

}
