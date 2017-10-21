package com.nhsoft.report.service.impl;


import com.nhsoft.module.report.dto.AdjustmentReason;
import com.nhsoft.report.dao.BookResourceDao;
import com.nhsoft.report.model.BookResource;
import com.nhsoft.report.param.CardUserType;
import com.nhsoft.report.param.PosItemTypeParam;
import com.nhsoft.report.service.BookResourceService;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.BaseManager;
import com.nhsoft.report.util.RedisUtil;
import net.sf.ehcache.Element;
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
		if(RedisUtil.isRedisValid()){
			String key = AppConstants.REDIS_PRE_BOOK_RESOURCE + AppConstants.POS_ITEM_TYPE + systemBookCode;
			Object object = RedisUtil.get(key);
			if(object == null){
				List<PosItemTypeParam> params = findPosItemTypeParams(systemBookCode);
				RedisUtil.put(key, params, AppConstants.REDIS_CACHE_LIVE_SECOND);
				return params;
			} else {
				return (List<PosItemTypeParam>) object;
			}

		} else {

			Element element = getElementFromCache(AppConstants.POS_ITEM_TYPE + systemBookCode);
			if(element == null){
				element = new Element(AppConstants.POS_ITEM_TYPE + systemBookCode, findPosItemTypeParams(systemBookCode));
				element.setEternal(true);
				putElementToCache(element);
			}
			return (List<PosItemTypeParam>) element.getObjectValue();
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

		if(RedisUtil.isRedisValid()){
			String key = AppConstants.REDIS_PRE_BOOK_RESOURCE + AppConstants.CARD_CATEGORY + systemBookCode;
			Object object = RedisUtil.get(key);
			if(object == null){
				List<CardUserType> cardUserTypes = findCardUserTypes(systemBookCode);
				RedisUtil.put(key, cardUserTypes, AppConstants.REDIS_CACHE_LIVE_SECOND);
				return cardUserTypes;
			} else {
				return (List<CardUserType>) object;
			}

		} else {

			Element element = getElementFromCache(AppConstants.CARD_CATEGORY + systemBookCode);
			if(element == null){
				List<CardUserType> cardUserTypes = findCardUserTypes(systemBookCode);
				element = new Element(AppConstants.CARD_CATEGORY + systemBookCode, cardUserTypes);
				element.setEternal(true);
				putElementToCache(element);
			}
			return (List<CardUserType>) element.getObjectValue();
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
}