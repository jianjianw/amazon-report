package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.SystemBookDao;
import com.nhsoft.report.model.SystemBook;
import com.nhsoft.report.service.SystemBookService;

import com.nhsoft.report.util.*;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
		if(RedisUtil.isRedisValid()){
			String key = AppConstants.REDIS_PRE_SYSTEM_BOOK + systemBookCode;
			Object object = RedisUtil.get(key);
			if(object == null){
				SystemBook systemBook = systemBookDao.read(systemBookCode);
				RedisUtil.put(key, systemBook, AppConstants.REDIS_CACHE_LIVE_SECOND);
				return systemBook;
			} else {
				return (SystemBook) object;
			}
		} else {
			
			List<SystemBook> systemBooks = findAllInCache();
			for(int i = 0;i < systemBooks.size();i++){
				SystemBook systemBook = systemBooks.get(i);
				if(systemBook.getSystemBookCode().equals(systemBookCode)){
					return systemBook;
				}
			}
			return null;
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
