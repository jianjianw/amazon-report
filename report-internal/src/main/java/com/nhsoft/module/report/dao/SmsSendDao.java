package com.nhsoft.module.report.dao;

import java.util.Date;
import java.util.List;

public interface SmsSendDao {
	public int count(String systemBookCode,
					 List<Integer> branchNums, Date dateFrom, Date dateTo, String status, String phone, String delivery);


}
