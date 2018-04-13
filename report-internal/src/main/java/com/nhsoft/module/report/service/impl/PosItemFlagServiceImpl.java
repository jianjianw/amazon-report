package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.PosItemFlagDao;
import com.nhsoft.module.report.model.ItemFlagDetail;
import com.nhsoft.module.report.model.PosItemFlag;
import com.nhsoft.module.report.service.PosItemFlagService;
import com.nhsoft.report.utils.ServiceBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PosItemFlagServiceImpl implements PosItemFlagService {
	
	@Autowired
	private PosItemFlagDao posItemFlagDao;


	@Override
	public List<PosItemFlag> find(String systemBookCode, String itemFlagType) {
		return posItemFlagDao.find(systemBookCode, itemFlagType);
	}

	@Override
	public List<ItemFlagDetail> findDetails(int itemFlagNum) {
		return posItemFlagDao.findDetails(itemFlagNum);
	}

	@Override
	public PosItemFlag readWithoutDetails(Integer itemFlagNum) {
		PosItemFlag posItemFlag = posItemFlagDao.read(itemFlagNum);
		if(posItemFlag != null){
			posItemFlag.setItemFlagDetails(new ArrayList<ItemFlagDetail>());
		}
		return posItemFlag;
	}

	@Override
	public List<Integer> findItemNums(int itemFlagNum) {
		return posItemFlagDao.findItemNums(itemFlagNum);
	}	

}
