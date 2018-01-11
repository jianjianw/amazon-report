package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface CardConsumeDao {



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

	/**
	 * 按门店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 按营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 查询中奖次数
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Integer countReward(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 查询客户编号 消费时间 消费金额明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按门店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 根据营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdayCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery);
	
	/**
	 * 按分店 卡类型汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCardTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                            Date dateTo, Integer cardUserCardType);
	
	
	/**
	 * 按销售员汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param salerNames
	 * @return
	 */
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames);

	/**
	 * 按营业日汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findDateSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
										  Date dateTo, String dateType);
}
