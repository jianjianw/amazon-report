package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.dto.AlipayDetailDTO;

import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.query.LogQuery;

import java.util.Date;
import java.util.List;

public interface AlipayLogDao {

	
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);
	
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);
	
	/**
	 * 按分店汇总支付失败金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param paymentType
	 * @param isDeposit 是否存款单据
	 * @param alipayLogTypes 
	 * @return
	 */
	public List<Object[]> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                   boolean isDeposit, String alipayLogTypes);

	/**
	 * 按分店汇总反结账金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param alipayLogTypes
	 * @return
	 */
	public List<Object[]> findBranchSummaryRePay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String alipayLogTypes);

	/**
	 * 查询线上支付报表明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param orderNoPres
	 * @param alipayLogTypes
	 * @return
	 */
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums,
                                                      Date dateFrom, Date dateTo, String orderNoPres, String alipayLogTypes);

	/**
	 * 查询已退款的记录
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param orderNoPres
	 * @param alipayLogTypes
	 * @return
	 */
	public List<AlipayDetailDTO> findCancelAlipayDetailDTOs(String systemBookCode,
                                                            List<Integer> branchNums, Date dateFrom, Date dateTo, String orderNoPres, String alipayLogTypes);

	/**
	 * 查询POS存款、微会员存款成功的笔数和金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param alipayLogTypes
	 * @return
	 */
	public List<Object[]> findDepositSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                             Date dateTo, String alipayLogTypes);


	public List<AlipayLog> test(String systemBookCode,Date dateFrom,Date dateTo);

}
