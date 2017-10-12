package com.nhsoft.report.dao;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OtherInoutDao {
	
	/**
	 * 按分店、标记、收支类型汇总前台金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                                   Date dateTo);
	public List<Object[]> findClientsMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * type0 查分店 1查供应商  2查客户
	 * @param systemBookCode
	 * @param branchNum
	 * @param innerBranchNum
	 * @param supplierNum
	 * @param clientFid
	 * @param type
	 * @return
	 */
	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer innerBranchNum, Integer supplierNum, String clientFid, int type);

	/**
	 * 查询其他收支现金收入
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getCashMoney(String systemBookCode,
								   List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店汇总其他收支现金收入
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo);
}
