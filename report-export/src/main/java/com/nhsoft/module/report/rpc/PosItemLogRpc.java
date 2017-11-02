package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;
import com.nhsoft.module.report.query.StoreQueryCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/11/2.
 */
public interface PosItemLogRpc {
	
	/**
	 * 按分店、商品、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition);
	
	/**
	 * 按商品、日期、类型、进出标记汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemBizTypeFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,  String summaries, List<Integer> itemNums, Integer storehouseNum);
	
	/**
	 * 按商品、多特性、进出标记汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemMatrixFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按商品、进出标记汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按分店、进出标记汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
}
