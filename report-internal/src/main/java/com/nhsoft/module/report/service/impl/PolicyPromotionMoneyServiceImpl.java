package com.nhsoft.module.report.service.impl;



import com.nhsoft.module.report.dao.PolicyPromotionMoneyDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
import com.nhsoft.module.report.service.PolicyPromotionMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PolicyPromotionMoneyServiceImpl implements PolicyPromotionMoneyService {
	@Autowired
	private PolicyPromotionMoneyDao policyPromotionMoneyDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery posItemQuery) {
		return policyPromotionMoneyDao.findPolicyPosItems(posItemQuery);
	}
}
