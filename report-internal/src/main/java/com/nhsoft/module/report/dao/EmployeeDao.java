package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.EmployeeItem;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface EmployeeDao {
	
	/**
	 * 查询关联商品
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNums
	 * @param employeeNames
	 * @return
	 */
	public List<EmployeeItem> findEmployeeItems(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<String> employeeNames);

}
