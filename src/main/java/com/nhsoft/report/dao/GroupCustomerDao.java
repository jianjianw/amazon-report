package com.nhsoft.report.dao;


import com.nhsoft.report.model.GroupCustomer;

import java.util.List;

public interface GroupCustomerDao {
	

	
	public List<GroupCustomer> findByBranch(String systemBookCode, Integer branchNum, String groupCustomerType);
	
	
	public List<GroupCustomer> findDefault(String systemBookCode, Integer branchNum);
	
	
}
