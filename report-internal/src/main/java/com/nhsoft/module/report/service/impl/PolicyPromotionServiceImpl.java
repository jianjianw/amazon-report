package com.nhsoft.module.report.service.impl;



import com.nhsoft.module.report.dao.PolicyPromotionDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
import com.nhsoft.module.report.service.PolicyPromotionService;
import com.nhsoft.report.utils.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PolicyPromotionServiceImpl extends BaseManager implements PolicyPromotionService {
	@Autowired
	private PolicyPromotionDao policyPromotionDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		return policyPromotionDao.findPolicyPosItems(posItemQuery);
	}
}
