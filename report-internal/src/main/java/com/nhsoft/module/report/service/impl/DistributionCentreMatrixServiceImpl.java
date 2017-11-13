package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.DistributionCentreMatrixDao;
import com.nhsoft.module.report.service.DistributionCentreMatrixService;
import com.nhsoft.module.report.util.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionCentreMatrixServiceImpl extends BaseManager implements DistributionCentreMatrixService {
	
	@Autowired
	private DistributionCentreMatrixDao distributionCentreMatrixDao;

	@Override
	public List<Integer> findRecommend(String systemBookCode, Integer branchNum) {
		return distributionCentreMatrixDao.findRecommend(systemBookCode, branchNum);
	}
	
}
