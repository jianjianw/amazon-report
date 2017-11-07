package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchDao;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.BranchRegion;
import com.nhsoft.module.report.service.BranchService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.BaseManager;
import com.nhsoft.module.report.util.MemCacheUtil;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(value = "serviceCache", key = "'AMA_findAll' + #p0")
	public List<Branch> findAll(String systemBookCode) {
		List<Branch> all = branchDao.findAll(systemBookCode);
		//遍历all，将分店号为99的去除
		for (int i = 0; i <all.size() ; i++) {
			Branch branch = all.get(i);
			if(branch.getId().getBranchNum().equals(99)){
				all.remove(i);
				break;
			}
		}
		return all;
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

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findBranchByBranchRegionNum' + #p0")
	public List<Branch> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum) {
		return branchDao.findBranchByBranchRegionNum(systemBookCode,branchRegionNum);
	}

	@Override
	public List<Object[]> findBranchArea(String systemBookCode, List<Integer> branchNums) {
		return branchDao.findBranchArea(systemBookCode,branchNums);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_readWithNolock' + #p0 + #p2")
	public Branch readWithNolock(String systemBookCode, Integer branchNum) {
		return branchDao.readWithNolock(systemBookCode,branchNum);
	}



}