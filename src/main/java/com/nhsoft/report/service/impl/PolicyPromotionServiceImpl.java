package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPromotionDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPromotionService;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import com.nhsoft.report.util.BaseManager;

import java.util.List;

public class PolicyPromotionServiceImpl extends BaseManager implements PolicyPromotionService {

	private PolicyPromotionDao policyPromotionDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		return policyPromotionDao.findPolicyPosItems(posItemQuery);
	}
}
