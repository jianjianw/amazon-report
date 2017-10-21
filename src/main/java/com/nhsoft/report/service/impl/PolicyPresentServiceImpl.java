package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPresentDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPresentService;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PolicyPresentServiceImpl implements PolicyPresentService {
	@Autowired
	private PolicyPresentDao policyPresentDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(
			PolicyPosItemQuery posItemQuery) {
		return policyPresentDao.findPolicyPosItems(posItemQuery);
	}


}
