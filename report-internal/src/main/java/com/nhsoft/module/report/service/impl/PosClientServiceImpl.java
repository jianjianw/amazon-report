package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.PosClientDao;
import com.nhsoft.module.report.model.PosClient;
import com.nhsoft.module.report.service.PosClientService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.BaseManager;
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
	public List<PosClient> findInCache(String systemBookCode,List<String> clientFids ) {


		Element element = getElementFromCache(AppConstants.CACHE_NAME_POS_CLIENT + systemBookCode);
		if(element == null){
			List<PosClient> posClients = posClientDao.findAll(systemBookCode, null,clientFids);
			element = new Element(AppConstants.CACHE_NAME_POS_CLIENT + systemBookCode, posClients);
			element.setTimeToLive(AppConstants.CACHE_LIVE_SECOND);
			putElementToCache(element);
		}
		List<PosClient> posClients = (List<PosClient>) element.getObjectValue();
		return posClients;

	}
}
