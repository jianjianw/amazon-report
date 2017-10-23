package com.nhsoft.module.report.model;

import java.io.Serializable;

/**
 * 供应商共享门店
 * 
 * @author nhsoft
 * 
 */
public class SupplierShareBranch implements Serializable {

	/**
	 * 
	 */
	public static class SupplierShareBranchId implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5696557429529431255L;
		private Integer supplierNum;
		private String systemBookCode;
		private Integer branchNum;

		public Integer getSupplierNum() {
			return supplierNum;
		}

		public void setSupplierNum(Integer supplierNum) {
			this.supplierNum = supplierNum;
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

	}

	private static final long serialVersionUID = 6392390498546654429L;
	private SupplierShareBranchId id;

	public SupplierShareBranchId getId() {
		return id;
	}

	public void setId(SupplierShareBranchId id) {
		this.id = id;
	}

}
