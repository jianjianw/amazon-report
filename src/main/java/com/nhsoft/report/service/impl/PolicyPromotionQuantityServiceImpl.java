package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPromotionQuantityDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPromotionQuantityService;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import com.nhsoft.report.util.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class PolicyPromotionQuantityServiceImpl extends BaseManager implements PolicyPromotionQuantityService {
	@Autowired
	private PolicyPromotionQuantityDao policyPromotionQuantityDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(
			PolicyPosItemQuery posItemQuery) {
		return policyPromotionQuantityDao.findPolicyPosItems(posItemQuery);
	}

}
