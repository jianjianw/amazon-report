package com.nhsoft.report.service;

import com.nhsoft.module.report.dto.AdjustmentReason;
import com.nhsoft.report.param.CardUserType;
import com.nhsoft.report.param.PosItemTypeParam;

import java.util.List;


/**
 * 帐套字典表
 * @author nhsoft
 *
 */
public interface BookResourceService {


	/**
	 * 读取库存调整原因
	 * @param systemBookCode
	 * @return
	 */
	public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode);

	/**
	 * 缓存中读取商品档案类别
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItemTypeParam> findPosItemTypeParamsInCache(String systemBookCode);

	/**
	 * 读商品类型
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItemTypeParam> findPosItemTypeParams(String systemBookCode) ;


	/**
	 * 缓存中查询消费卡类型
	 * @param systemBookCode
	 * @return
	 */
	public List<CardUserType> findCardUserTypesInCache(String systemBookCode);

	/**
	 * 查询消费卡类型
	 * @param systemBookCode
	 * @return
	 */
	public List<CardUserType> findCardUserTypes(String systemBookCode);


}