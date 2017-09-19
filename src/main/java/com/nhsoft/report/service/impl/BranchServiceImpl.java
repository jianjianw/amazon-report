package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.BranchDao;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.service.BranchService;
import com.nhsoft.report.shared.AppConstants;
import com.nhsoft.report.util.BaseManager;
import com.nhsoft.report.util.MemCacheUtil;
import net.sf.ehcache.Element;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unchecked")
public class BranchServiceImpl extends BaseManager implements BranchService {

	private BranchDao branchDao;
	private ConcurrentMap<String, Date> cacheDateMap = new ConcurrentHashMap<String, Date>();
	public void setBranchDao(BranchDao branchDao) {
		this.branchDao = branchDao;
	}




	@Override
	public List<Branch> findInCache(String systemBookCode) {

		String key = AppConstants.CACHE_NAME_BRANCH + systemBookCode;
		Date updateDate = cacheDateMap.get(systemBookCode);
		if(updateDate == null){
			updateDate = Calendar.getInstance().getTime();
			cacheDateMap.put(systemBookCode, updateDate);
		}

		Date memberCacheDate = (Date) MemCacheUtil.get(AppConstants.MEMCACHED_PRE_BRANCH_UPDATE_TIME + systemBookCode);
		boolean query = false; //是否重新查询
		if(memberCacheDate != null
				&& memberCacheDate.compareTo(updateDate) != 0){
			query = true;
		}

		Element element = getElementFromCache(key);
		if(element == null || query){

			List<Branch> branchs = branchDao.findAll(systemBookCode);
			element = new Element(key, branchs);
			element.setTimeToLive(AppConstants.CACHE_LIVE_DAY);
			putElementToCache(element);

			if(memberCacheDate != null){
				cacheDateMap.put(systemBookCode, memberCacheDate);
			}
		}

		List<Branch> branchs = (List<Branch>) element.getObjectValue();
		return branchs;


	}


	@Override
	public List<Branch> findActivedRdc(String systemBookCode) {
		return branchDao.findActivedRdc(systemBookCode);
	}


}