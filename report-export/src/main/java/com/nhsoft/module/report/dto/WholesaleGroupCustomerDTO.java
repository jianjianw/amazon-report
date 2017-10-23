package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class WholesaleGroupCustomerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7733526710576810886L;
	private String groupCustomerId;
	private String groupCustomerName;
	private String groupCustomerMemo;
	private Integer groupCustomerCount = 0;
	
	public String getGroupCustomerName() {
		return groupCustomerName;
	}
	public String getGroupCustomerMemo() {
		return groupCustomerMemo;
	}
	public Integer getGroupCustomerCount() {
		return groupCustomerCount;
	}
	public void setGroupCustomerName(String groupCustomerName) {
		this.groupCustomerName = groupCustomerName;
	}
	public void setGroupCustomerMemo(String groupCustomerMemo) {
		this.groupCustomerMemo = groupCustomerMemo;
	}
	public void setGroupCustomerCount(Integer groupCustomerCount) {
		this.groupCustomerCount = groupCustomerCount;
	}
	public String getGroupCustomerId() {
		return groupCustomerId;
	}
	public void setGroupCustomerId(String groupCustomerId) {
		this.groupCustomerId = groupCustomerId;
	}
}
