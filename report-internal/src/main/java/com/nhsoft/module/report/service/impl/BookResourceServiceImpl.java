package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.BookResourceDao;
import com.nhsoft.module.report.dto.AdjustmentReason;
import com.nhsoft.module.report.model.BookResource;
import com.nhsoft.module.report.param.CardUserType;
import com.nhsoft.module.report.param.ChainDeliveryParam;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.service.BookResourceService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.BaseManager;
import com.nhsoft.module.report.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BookResourceServiceImpl extends BaseManager implements BookResourceService {
	@Autowired
	private BookResourceDao bookResourceDao;

	@Override
	public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode) {
		BookResource bookResource = bookResourceDao.read(systemBookCode, AppConstants.ADJUSTMENT_REASON);
		if(bookResource == null){
			return new ArrayList<AdjustmentReason>();
		}
		List<AdjustmentReason> adjustmentReasons = AdjustmentReason.readFromXml(bookResource.getBookResourceParam());
		return adjustmentReasons;
	}


	@Override
	public List<PosItemTypeParam> findPosItemTypeParamsInCache(String systemBookCode) {
		
		String key = AppConstants.REDIS_PRE_BOOK_RESOURCE + AppConstants.POS_ITEM_TYPE + systemBookCode;
		Object object = RedisUtil.get(key);
		if(object == null){
			List<PosItemTypeParam> params = findPosItemTypeParams(systemBookCode);
			RedisUtil.put(key, params, AppConstants.REDIS_CACHE_LIVE_SECOND);
			return params;
		} else {
			return (List<PosItemTypeParam>) object;
		}

	}

	@Override
	public List<PosItemTypeParam> findPosItemTypeParams(String systemBookCode) {
		BookResource bookResource = bookResourceDao.read(systemBookCode, AppConstants.POS_ITEM_TYPE);
		if(bookResource == null){
			return new ArrayList<PosItemTypeParam>();
		}
		List<PosItemTypeParam> params = PosItemTypeParam.fromXml(bookResource.getBookResourceParam().getBytes());
		return params;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CardUserType> findCardUserTypesInCache(String systemBookCode) {

		
		String key = AppConstants.REDIS_PRE_BOOK_RESOURCE + AppConstants.CARD_CATEGORY + systemBookCode;
		Object object = RedisUtil.get(key);
		if(object == null){
			List<CardUserType> cardUserTypes = findCardUserTypes(systemBookCode);
			RedisUtil.put(key, cardUserTypes, AppConstants.REDIS_CACHE_LIVE_SECOND);
			return cardUserTypes;
		} else {
			return (List<CardUserType>) object;
		}


	}

	@Override
	public List<CardUserType> findCardUserTypes(String systemBookCode) {
		BookResource bookResource = bookResourceDao.read(systemBookCode, AppConstants.CARD_CATEGORY);
		if(bookResource == null){
			return new ArrayList<CardUserType>();
		}
		List<CardUserType> params = CardUserType.readFromXml(bookResource.getBookResourceParam());
		return params;
	}
	
	private ChainDeliveryParam readChainDeliveryParam(String systemBookCode) {
		BookResource bookResource = bookResourceDao.read(systemBookCode, AppConstants.CHAIN_DELIVERY_PARAM);
		if(bookResource == null){
			return new ChainDeliveryParam();
		}
		ChainDeliveryParam param = ChainDeliveryParam.fromXml(bookResource.getBookResourceParam());
		return param;
	}
	
	@Override
	public ChainDeliveryParam readChainDeliveryParamInCache(String systemBookCode) {
		String key = AppConstants.REDIS_PRE_BOOK_RESOURCE + AppConstants.CHAIN_DELIVERY_PARAM + systemBookCode;
		Object object = RedisUtil.get(key);
		if(object == null){
			ChainDeliveryParam param = readChainDeliveryParam(systemBookCode);
			RedisUtil.put(key, param, AppConstants.CACHE_LIVE_SECOND);
			return param;
		} else {
			return (ChainDeliveryParam) object;
		}
	}
}