package com.nhsoft.report.service.impl;




import com.nhsoft.report.dao.ItemVersionDao;
import com.nhsoft.report.model.ItemVersion;
import com.nhsoft.report.service.ItemVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemVersionServiceImpl implements ItemVersionService {
	@Autowired
	private ItemVersionDao itemVersionDao;

	@Override
	public List<ItemVersion> findFirst(String systemBookCode, Date dateFrom, Date dateTo, String itemVersionType) {
		return itemVersionDao.findFirst(systemBookCode, dateFrom, dateTo, itemVersionType);
	}
	

	
	
}
