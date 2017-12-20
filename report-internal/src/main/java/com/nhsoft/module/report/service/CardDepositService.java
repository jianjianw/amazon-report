package com.nhsoft.module.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
public interface CardDepositService {
	
	/**
	 * 按门店汇总收款金额 存款金额
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按营业日汇总收款金额 存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * bi 按分店和营业日查询   付款金额 存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * */
	public List<Object[]> findSumByBizdayBranch(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo);



	
}
