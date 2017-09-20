package com.nhsoft.report.model;



/**
 * PolicyDiscountDetailId generated by hbm2java
 */
public class PolicyDiscountDetailId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4213559475434988998L;
	private String policyDiscountNo;
	private int policyDiscountDetailNum;

	public PolicyDiscountDetailId() {
	}

	public PolicyDiscountDetailId(String policyDiscountNo, int policyDiscountDetailNum) {
		this.policyDiscountNo = policyDiscountNo;
		this.policyDiscountDetailNum = policyDiscountDetailNum;
	}

	public String getPolicyDiscountNo() {
		return this.policyDiscountNo;
	}

	public void setPolicyDiscountNo(String policyDiscountNo) {
		this.policyDiscountNo = policyDiscountNo;
	}

	public int getPolicyDiscountDetailNum() {
		return this.policyDiscountDetailNum;
	}

	public void setPolicyDiscountDetailNum(int policyDiscountDetailNum) {
		this.policyDiscountDetailNum = policyDiscountDetailNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PolicyDiscountDetailId))
			return false;
		PolicyDiscountDetailId castOther = (PolicyDiscountDetailId) other;

		return ((this.getPolicyDiscountNo() == castOther.getPolicyDiscountNo()) || (this.getPolicyDiscountNo() != null
				&& castOther.getPolicyDiscountNo() != null && this.getPolicyDiscountNo().equals(
				castOther.getPolicyDiscountNo())))
				&& (this.getPolicyDiscountDetailNum() == castOther.getPolicyDiscountDetailNum());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyDiscountNo() == null ? 0 : this.getPolicyDiscountNo().hashCode());
		result = 37 * result + this.getPolicyDiscountDetailNum();
		return result;
	}

}
