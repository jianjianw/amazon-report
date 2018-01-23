package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.PosItemLogDTO;
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

	/**
	 * 按商品 营业日 进出标记汇总
	 *
	 * */
	public List<PosItemLogSummaryDTO> findItemBizFlagSummary(StoreQueryCondition storeQueryCondition);



	//以下都是从amazonCenter中移过来的

	public List<PosItemLogSummaryDTO> findItemOutAmountSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 查询商品的最近出库日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemOutDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 需要到货通知的商品
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param posItemLogType
	 * @return
	 */
	public List<Integer> findNoticeItemSummary(String systemBookCode, Integer branchNum, Date dateFrom,
										 Date dateTo, String posItemLogType);

	/**
	 * 按商品汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemInOutQtyAndMoneySummary(StoreQueryCondition storeQueryCondition);


	/**
	 * 进出总合计
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> sumByStoreQueryCondition(StoreQueryCondition storeQueryCondition);


	/**
	 * 最后一次出库记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @param endDate
	 * @return
	 */
	public List<PosItemLogDTO> findLastSummary(String systemBookCode,
									 Integer branchNum, Integer storehouseNum, Date endDate);


	/**
	 * 按商品、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 按分店、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 按分店、进出标记汇总进出库数量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchInOutSummary(StoreQueryCondition storeQueryCondition);


	/**
	 * 根据摘要查询商品进出库量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries);


	/**
	 * 根据门店 商品 进出库类型汇总 数量 金额
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchAndItemFlagSummary(StoreQueryCondition storeQueryCondition);


	/**
	 * 按门店统计单据数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countByBranch(String systemBookCode, Integer branchNum);

	/**
	 * 取最小单据日期
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Date getFirstDate(String systemBookCode, Integer branchNum);

	/**
	 * 根据门店 进出库类型汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findMoneyBranchFlagSummary(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据门店 商品 进出库类型汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findMoneyBranchItemFlagSummary(String systemBookCode,
													List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询商品最低进价 和 日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findMinPriceAndDateSummary(String systemBookCode,
											  Integer branchNum, Date dateFrom, Date dateTo,
											  List<Integer> itemNums);

	/**
	 * 查询商品最高进价 和 日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findMaxPriceAndDateSummary(String systemBookCode,
											  Integer branchNum, Date dateFrom, Date dateTo,
											  List<Integer> itemNums);
	//含name code  (已删)
	public List<PosItemLogSummaryDTO> findItemDetailSummary(StoreQueryCondition storeQueryCondition);

	public List<PosItemLogDTO> findByDate(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 查询未上传记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo
	 * @param dateFrom
	 * @return
	 */
	public List<PosItemLogDTO> findUnUploadSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit);


	/**	含name code （已删除）
	 * 查询重复审核的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogDTO> findRepeatAuditOrderSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


	/**
	 * 按仓库 商品 日期 摘要 进出标记 多特性标示 汇总进出数量 辅量 金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


	/**
	 * 查询未上传单据数量
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public int countUnUpload(String systemBookCode, Date dateFrom, Date dateTo);


	/**
	 * 根据分店 商品 进出库类型 备注(调整原因) 汇总 数量 金额 辅助数量
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchItemFlagMemoSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
																	Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 按商品、进出标记汇总进出库数量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findItemInOutSummary(StoreQueryCondition storeQueryCondition);


	public List<PosItemLogDTO> findByBillNoSummary(String systemBookCode, String orderNo);

	public int countByBillNo(String systemBookCode, String posItemLogBillNo);


	/**
	 * 根据营业日 商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findDateItemFlagSummary(StoreQueryCondition storeQueryCondition);


	/**
	 * 查询范围内有进出库记录的商品
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Integer> findItemNumSummary(String systemBookCode, Integer branchNum, Integer storehouseNum, Date dateFrom, Date dateTo);


	/**
	 * 判断组合明细商品是否存在
	 * @param posItemLogBillNo
	 * @param posItemLogBillDetailNum
	 * @param posItemLogSerialNumber
	 * @return
	 */
	public boolean checkExists(String posItemLogBillNo, Integer posItemLogBillDetailNum, Integer posItemLogSerialNumber);


	/**
	 * 根据分店  商品 多特性 进出标记  汇总 数量 金额 辅助数量
	 * @return
	 */
	public List<PosItemLogSummaryDTO> findBranchItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition);

	/**
	 *
	 * @param posItemLogBillNo
	 * @param posItemLogBillDetailNum
	 * @return
	 */
	public PosItemLogDTO read(String posItemLogBillNo, Integer posItemLogBillDetailNum);
}
