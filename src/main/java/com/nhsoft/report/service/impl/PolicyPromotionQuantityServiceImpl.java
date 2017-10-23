package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PolicyPromotionQuantityDao;
import com.nhsoft.module.report.dto.PolicyPosItem;
import com.nhsoft.report.service.PolicyPromotionQuantityService;
import com.nhsoft.module.report.query.PolicyPosItemQuery;
import com.nhsoft.report.util.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class PolicyPromotionQuantityServiceImpl extends BaseManager implements PolicyPromotionQuantityService {
	@Autowired
	private PolicyPromotionQuantityDao policyPromotionQuantityDao;

	@Override
	public List<PolicyPosItem> findPolicyPosItems(
			PolicyPosItemQuery posItemQuery) {
		return policyPromotionQuantityDao.findPolicyPosItems(posItemQuery);
	}

}
