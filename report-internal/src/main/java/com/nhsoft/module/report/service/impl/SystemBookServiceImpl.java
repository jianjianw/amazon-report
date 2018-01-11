package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.SystemBookDao;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.service.SystemBookService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.BaseManager;
import com.nhsoft.module.report.util.RedisUtil;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemBookServiceImpl extends BaseManager implements SystemBookService {
    @Autowired
    private SystemBookDao systemBookDao;

    public void setSystemBookDao(SystemBookDao systemBookDao) {
        this.systemBookDao = systemBookDao;
    }

    @Override
    public SystemBook read(String systemBookCode) {
        return systemBookDao.read(systemBookCode);
    }

    @Override
    public List<SystemBook> findAll() {
        return systemBookDao.findAll();
    }

	@Override
	public List<SystemBook> findAllActiveBooks() {
		return systemBookDao.findAllActiveBooks();
	}



	@Override
	public SystemBook readInCache(String systemBookCode) {
		
		String key = AppConstants.REDIS_PRE_SYSTEM_BOOK + systemBookCode;
		Object object = RedisUtil.get(key);
		if(object == null){
			SystemBook systemBook = systemBookDao.read(systemBookCode);
			RedisUtil.put(key, systemBook, AppConstants.REDIS_CACHE_LIVE_SECOND);
			return systemBook;
		} else {
			return (SystemBook) object;
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemBook> findAllInCache() {
		
		Element element = getElementFromCache(AppConstants.CACHE_SYSTEM_BOOK);
		if(element == null){
			List<SystemBook> systemBooks = findAll();
			element = new Element(AppConstants.CACHE_SYSTEM_BOOK, systemBooks);
			element.setEternal(true);
			putElementToCache(element);
		}
		return (List<SystemBook>) element.getObjectValue();
	}



	@Override
	public List<SystemBook> findByParent(String systemBookCode) {
		return systemBookDao.findByParent(systemBookCode);
	}




}
