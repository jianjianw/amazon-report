package com.nhsoft.module.report.dao.impl;


import com.nhsoft.module.report.dao.EmployeeDao;
import com.nhsoft.module.report.model.EmployeeItem;
import com.nhsoft.module.report.util.AppUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Repository
public class EmployeeDaoImpl extends  DaoImpl implements EmployeeDao {
	@Override
	public List<EmployeeItem> findEmployeeItems(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<String> employeeNames) {
		StringBuffer sb = new StringBuffer();
		sb.append("select item.branch_num, item.employee_num, item.item_num, e.employee_name ");
		sb.append("from employee_item as item with(nolock) inner join employee as e with(nolock) on item.system_book_code = e.system_book_code ");
		sb.append("and item.branch_num = e.branch_num and item.employee_num = e.employee_num ");
		sb.append("where e.system_book_code = '" + systemBookCode + "' and e.branch_num in " + AppUtil.getIntegerParmeList(branchNums));
		if(itemNums != null && !itemNums.isEmpty()){
			sb.append("and item.item_num in " + AppUtil.getIntegerParmeList(itemNums));
		}
		if(employeeNames != null && !employeeNames.isEmpty()){
			sb.append("and (cast(e.branch_num as varchar) + '-' + cast(e.employee_num as varchar)) in " + AppUtil.getStringParmeList(employeeNames));
		}
		SQLQuery query = currentSession().createSQLQuery(sb.toString());
		List<EmployeeItem> employeeItems = new ArrayList<EmployeeItem>();
		List<Object[]> objects = query.list();
		Object[] object = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			
			EmployeeItem employeeItem = new EmployeeItem();
			EmployeeItem.EmployeeItemId id = new EmployeeItem.EmployeeItemId(systemBookCode, (Integer)object[0], (Integer)object[1], (Integer)object[2]);
			employeeItem.setId(id);
			employeeItem.setEmployeeName((String) object[3]);
			employeeItems.add(employeeItem);
		}
		return employeeItems;
	}
}
