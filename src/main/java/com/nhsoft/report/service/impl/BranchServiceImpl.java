package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.BranchDao;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.service.BranchService;

import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.BaseManager;
import com.nhsoft.report.util.MemCacheUtil;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
@Service
@SuppressWarnings("unchecked")
public class BranchServiceImpl extends BaseManager implements BranchService {
	@Autowired
	private BranchDao branchDao;
	private ConcurrentMap<String, Date> cacheDateMap = new ConcurrentHashMap<String, Date>();

	@Override
	public List<Branch> findAll(String systemBookCode) {
		return branchDao.findAll(systemBookCode);
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
	public Branch readInCache(String systemBookCode, Integer branchNum) {
		List<Branch> branchs = findInCache(systemBookCode);
		for(int i = 0;i < branchs.size();i++){
			Branch branch = branchs.get(i);
			if(branch.getId().getBranchNum().equals(branchNum)){
				return branch;
			}
		}
		return branchDao.readWithNolock(systemBookCode, branchNum);
	}


	@Override
	public List<Branch> findActivedRdc(String systemBookCode) {
		return branchDao.findActivedRdc(systemBookCode);
	}

	@Override
	public List<Branch> findAllActived(String systemBookCode) {
		return branchDao.findAllActived(systemBookCode);
	}

	@Override
	public List<BranchRegion> findBranchRegion(String systemBookCode) {
		return branchDao.findBranchRegion(systemBookCode);
	}


}