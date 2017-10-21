package com.nhsoft.report.service;



import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;

import java.util.List;

/**
 * 赠送促销
 * @author nhsoft
 *
 */
public interface PolicyPresentService {
	/**
	 * 商品促销查询
	 * @param posItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery);
}
