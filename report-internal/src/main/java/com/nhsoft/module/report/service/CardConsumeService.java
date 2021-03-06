package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.CardConsume;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
public interface CardConsumeService {
	
	/**
	 * 按门店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按营业日汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据分店、营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											  Date dateTo, Integer cardUserCardType);


	public List<CardConsume> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);

}
