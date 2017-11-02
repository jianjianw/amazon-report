package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.PosItemLogSummaryDTO;
import com.nhsoft.module.report.query.StoreQueryCondition;

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
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition);
	
	
	/**
	 * 按商品、多特性、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition);
	
	/**
	 * 按商品、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemFlagSummary(StoreQueryCondition storeQueryCondition);
	
	/**
	 * 按分店、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchFlagSummary(StoreQueryCondition storeQueryCondition);
}
