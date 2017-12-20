package com.nhsoft.module.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface BranchTransferGoalsService {
	
	/**
	 * 根据时间汇总
	 * @param systemBookCode 帐套号
	 * @param branchNums 分店号列表
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param dateType 月 或 年
	 * @return
	 */
	public List<Object[]> findSummaryByDate(String systemBookCode,
                                            List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按分店查询营业额目标
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param dateType 时间类型
	 */
	public List<Object[]> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);


	/**
	 * 按时间（年，月，周）汇总营业额目标
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param dateType 时间类型  年 AppConstants.BUSINESS_DATE_SOME_MONTH ，月，周，日
	 */
	public List<Object[]> findSaleMoneyGoalsByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 *
	 *按分店 营业日 汇总营业额目标
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findGoalsByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


	/**
	 * bi 按分店和营业日查询 存款目标
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * */
	public List<Object[]> findDepositGoalsByBizdayBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);



	/**
	 *  bi 按分店和营业日查询 发卡目标
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * */
	public List<Object[]> findNewCardGoalsByBizdayBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


}
