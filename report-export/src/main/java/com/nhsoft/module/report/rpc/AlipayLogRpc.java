package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.AlipayLogDTO;
import com.nhsoft.module.report.dto.PayFailDTO;
import com.nhsoft.module.report.query.LogQuery;


import java.util.Date;
import java.util.List;

public interface AlipayLogRpc {


	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);

	public List<AlipayLogDTO> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);

	/**
	 * 按分店汇总支付失败金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isDeposit 是否存款单据
	 * @param alipayLogTypes
	 * @return
	 */
	public List<PayFailDTO> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
													 boolean isDeposit, String alipayLogTypes);




}
