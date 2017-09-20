package com.nhsoft.report.dao;


import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;

import java.util.Date;
import java.util.List;

public interface PolicyPromotionDao {
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType, String policyPromotionCategory, List<Integer> stateCodes);

	/**
	 * 商品促销查询
	 * @param posItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery);


}
