package com.nhsoft.report.service;



import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;

import java.util.List;

/**
 * 促销特价单
 * @author nhsoft
 *
 */
public interface PolicyPromotionService {
	/**
	 * 商品促销查询
	 * @param posItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery);

}