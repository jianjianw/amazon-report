package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.PosImageDao;
import com.nhsoft.module.report.model.PosImage;
import com.nhsoft.module.report.service.PosImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosImageServiceImpl implements PosImageService {
	private static final Logger logger = LoggerFactory.getLogger(PosImageServiceImpl.class);

	@Autowired
	private PosImageDao posImageDao;

	@Override
	public List<PosImage> find(String systemBookCode, List<Integer> itemNums) {
		return posImageDao.find(systemBookCode, itemNums);
	}
	
}
