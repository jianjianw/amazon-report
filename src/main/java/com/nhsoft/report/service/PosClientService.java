package com.nhsoft.report.service;


import com.nhsoft.report.model.PosClient;
import com.nhsoft.report.param.PosClientCustomGroupParam;
import com.nhsoft.report.param.PosClientParam;
import com.nhsoft.report.shared.queryBuilder.ClientQuery;

import java.util.Date;
import java.util.List;

/**
 * 批发客户
 * @author nhsoft
 *
 */
public interface PosClientService {
	/**
	 * 从缓存中查询
	 * @param systemBookCode
	 * @return
	 */
	public List<PosClient> findInCache(String systemBookCode);
}
