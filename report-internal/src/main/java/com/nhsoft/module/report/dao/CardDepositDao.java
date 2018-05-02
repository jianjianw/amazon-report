package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.CardDeposit;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
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

	/**
	 * 查询存款方式统计现金
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getCashMoney(String systemBookCode,
								   List<Integer> branchNums, Date dateFrom, Date dateTo, String type);

	/**
	 * 按支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findDateSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
										  Date dateTo, String dateType);

	/**
	 * 按门店汇总存款现金收入
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo, String type);



	public List<CardDeposit> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);



}
