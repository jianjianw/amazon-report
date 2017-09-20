package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPresentDao;
import com.nhsoft.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPresentService;
import com.nhsoft.report.shared.queryBuilder.PolicyPosItemQuery;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PolicyPresentServiceImpl implements PolicyPresentService {
	@Autowired
	private PolicyPresentDao policyPresentDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(
			PolicyPosItemQuery posItemQuery) {
		return policyPresentDao.findPolicyPosItems(posItemQuery);
	}


}
