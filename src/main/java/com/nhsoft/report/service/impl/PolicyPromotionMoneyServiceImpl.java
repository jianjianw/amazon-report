package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPromotionMoneyDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPromotionMoneyService;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
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
