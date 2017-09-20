package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.OtherInoutDao;
import com.nhsoft.report.service.OtherInoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class OtherInoutServiceImpl implements OtherInoutService {
	
	@Autowired
	private OtherInoutDao otherInoutDao;
	
	
	@Override
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return otherInoutDao.findPosBranchFlagKindSummary(systemBookCode, branchNums, dateFrom, dateTo);
	}
}
