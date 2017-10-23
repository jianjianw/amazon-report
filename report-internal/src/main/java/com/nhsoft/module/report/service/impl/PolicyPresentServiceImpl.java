package com.nhsoft.module.report.service.impl;



import com.nhsoft.module.report.dao.PolicyPresentDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
import com.nhsoft.module.report.service.PolicyPresentService;
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
