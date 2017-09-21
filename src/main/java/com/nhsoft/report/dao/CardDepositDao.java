package com.nhsoft.report.dao;


import com.nhsoft.report.shared.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface CardDepositDao {
	
	/**
	 * 按门店汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 按营业日汇总收款金额、存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 查询客户编号 存款时间 付款金额 存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按门店、支付方式汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 根据营业日 、支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery);
	
	/**
	 * 按分店 卡类型、支付方式汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCardTypePaymentTypeSum(String systemBookCode, List<Integer> branchNums,
	                                                       Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 按销售员统计卡存款
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param salerNames
	 * @return
	 */
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames);


	/**
	 * 根据分店、营业日 、支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums,
														 Date dateFrom, Date dateTo, Integer cardUserCardType);
}
