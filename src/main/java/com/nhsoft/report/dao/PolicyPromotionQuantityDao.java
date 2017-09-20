package com.nhsoft.report.dao;

import java.util.Date;
import java.util.List;

public interface PolicyPromotionQuantityDao {
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType, String promotionQuantityCategory);
}