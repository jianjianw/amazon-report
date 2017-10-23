package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.EmployeeDao;
import com.nhsoft.module.report.model.EmployeeItem;
import com.nhsoft.module.report.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;
	
	
	
	@Override
	public List<EmployeeItem> findEmployeeItems(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<String> employeeNames) {
		return employeeDao.findEmployeeItems(systemBookCode, branchNums, itemNums, employeeNames);
	}
}
