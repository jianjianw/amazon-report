package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;

import java.util.Date;
import java.util.List;

public interface PolicyPromotionMoneyDao {
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 商品促销查询
	 * @param posItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery);

}
