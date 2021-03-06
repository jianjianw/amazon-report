package com.nhsoft.module.report.service;




import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;

import java.util.List;

/**
 * 超量特价单
 * @author nhsoft
 *
 */
public interface PolicyPromotionQuantityService {
	/**
	 * 商品促销查询
	 * @param posItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery);

}
