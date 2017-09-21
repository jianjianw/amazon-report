package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.PosClientDao;
import com.nhsoft.report.model.PosClient;
import com.nhsoft.report.service.PosClientService;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.BaseManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
@Service
@SuppressWarnings("unchecked")
public class PosClientServiceImpl extends BaseManager implements PosClientService {
	@Autowired
	private PosClientDao posClientDao;
	@Override
	public List<PosClient> findInCache(String systemBookCode) {


		Element element = getElementFromCache(AppConstants.CACHE_NAME_POS_CLIENT + systemBookCode);
		if(element == null){
			List<PosClient> posClients = posClientDao.findAll(systemBookCode, null);
			element = new Element(AppConstants.CACHE_NAME_POS_CLIENT + systemBookCode, posClients);
			element.setTimeToLive(AppConstants.CACHE_LIVE_SECOND);
			putElementToCache(element);
		}
		List<PosClient> posClients = (List<PosClient>) element.getObjectValue();
		return posClients;

	}
}
