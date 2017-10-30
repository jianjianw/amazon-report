package com.nhsoft.module.report.model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 供应商共享门店
 * 
 * @author nhsoft
 * 
 */
@Entity
public class SupplierShareBranch implements Serializable {

	/**
	 * 
	 */
	@Embeddable
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

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			SupplierShareBranchId that = (SupplierShareBranchId) o;

			if (supplierNum != null ? !supplierNum.equals(that.supplierNum) : that.supplierNum != null) return false;
			if (systemBookCode != null ? !systemBookCode.equals(that.systemBookCode) : that.systemBookCode != null)
				return false;
			return branchNum != null ? branchNum.equals(that.branchNum) : that.branchNum == null;
		}

		@Override
		public int hashCode() {
			int result = supplierNum != null ? supplierNum.hashCode() : 0;
			result = 31 * result + (systemBookCode != null ? systemBookCode.hashCode() : 0);
			result = 31 * result + (branchNum != null ? branchNum.hashCode() : 0);
			return result;
		}
	}

	private static final long serialVersionUID = 6392390498546654429L;
	@EmbeddedId
	private SupplierShareBranchId id;

	public SupplierShareBranchId getId() {
		return id;
	}

	public void setId(SupplierShareBranchId id) {
		this.id = id;
	}

}
