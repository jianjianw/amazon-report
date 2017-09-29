package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.AlipayLogDao;
import com.nhsoft.report.model.AlipayLog;
import com.nhsoft.report.service.AlipayLogService;
import com.nhsoft.report.shared.queryBuilder.LogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlipayLogServiceImpl implements AlipayLogService {

	private static final Logger logger = LoggerFactory.getLogger(AlipayLogServiceImpl.class);

	@Autowired
	private AlipayLogDao alipayLogDao;


	@Override
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery) {
		return alipayLogDao.countByLogQuery(systemBookCode, branchNum, logQuery);
	}

	@Override
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset,
                                          int limit) {
		return alipayLogDao.findByLogQuery(systemBookCode, branchNum, logQuery, offset, limit);
	}

	


}
