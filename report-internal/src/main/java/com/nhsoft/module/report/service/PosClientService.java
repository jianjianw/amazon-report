package com.nhsoft.module.report.service;




import com.nhsoft.module.report.model.PosClient;

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
