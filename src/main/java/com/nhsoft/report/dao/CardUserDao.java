package com.nhsoft.report.dao;

import com.nhsoft.report.model.CardUser;
import com.nhsoft.report.shared.queryBuilder.CardUserQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardUserDao {
	

	public List<CardUser> findByCardUserQuery(CardUserQuery cardUserQuery, int offset, int limit);

	public Object[] sumByCardUserQuery(CardUserQuery cardUserQuery);


	/**
	 * 查询时间段内发卡量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);


	/**
	 * 查询时间段内发卡量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param valid
	 * @return
	 */
	public Integer count(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean valid);

	/**
	 * 查询截止到deadline之前 未消费天数超过day天的会员数
	 * @param systemBookCode
	 * @param branchNum
	 * @param deadline
	 * @param day
	 * @param filterCard
	 * @return
	 */


	/**
	 * 查询卡回收数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按营业日查找发卡数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按营业日查找退卡数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);





	/**
	 * 按分店 卡类型汇总卡回收数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCountByBranchCardType(String systemBookCode, List<Integer> branchNums,
                                                              Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按分店 卡类型汇总发卡数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findCardCountByBranchCardType(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                        Date dateTo, Integer cardUserCardType);


	/**
	 * 按分店、营业日查找发卡数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo, Integer cardUserCardType);

	/**
	 * 按分店、营业日查找退卡数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums,
                                                            Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 查询卡回收金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getRevokeMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	


	
	
}
