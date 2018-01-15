package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReportDao {
	
	public Object excuteSql(String systemBookCode, String sql);

	/**
	 *  营业收款统计  根据分店汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode,
                                                                   List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 营业收款统计 根据终端汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode,
                                                                     List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询班次
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param casher
	 */
	public List<ShiftTable> findShiftTables(String systemBookCode,
											List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);

	/**
	 * 营业收款统计 根据班次汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param casher
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode,
                                                                       List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);

	/**
	 *  营业收款统计  根据分店、营业日汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode,
                                                                      List<Integer> branchNums, Date dateFrom, Date dateTo);



	/**
	 * 按年或按月统计储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findDepositBizMoneyByDateType(String systemBookCode,
                                                        List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                        String dateType);

	/**
	 * 按年或月统计发卡量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findCardBizCountByDateType(String systemBookCode,
                                                     List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                     String dateType);

	/**
	 * 按时间类型查询订单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findOutOrderCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询退货单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findReturnCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 查询调出金额 按商品类别汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param nowFromDate
	 * @param nowToDate
	 * @return
	 */
	public List<Object[]> findOutCategoryMoney(String systemBookCode,
                                               List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询批发销售金额 按商品类别汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param nowFromDate
	 * @param nowToDate
	 * @return
	 */
	public List<Object[]> findWholesaleCategoryMoney(String systemBookCode,
                                                     List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按时间类型查询批发销售单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findWholesaleOrderCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询批发退货单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findWholesaleReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询采购收货单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findPurchaseReceiveCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询采购退货单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findPurchaseReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询采购单数量和金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findPurchaseCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按会员汇总条件范围内消费金额
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public List<Object[]> findCustomerConusmeByCardConsuemAnalysisQuery(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);



	/**
	 * 会员指标消费分析 明细
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public List<Object[]> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);

	/**
	 * 按客户商品汇总缺货信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findWholesaleOrderLostByClientAndItem(String systemBookCode, Integer branchNum, Date dateFrom,
                                                                Date dateTo, List<String> clientFids, List<Integer> itemNums);

	/**
	 * 查询线上支付报表明细
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 操作时间起
	 * @param dateTo 操作时间止
	 * @param paymentTypes 线上支付类型
	 * @return
	 */
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo, String paymentTypes);


	/**
	 * 生成自定义报表
	 * @param queryReportDTO
	 * @param customReport
	 * @return
	 */
	public List<Object[]> findReportDTOs(ReportDTO queryReportDTO, CustomReport customReport);



	/**
	 * 查询进出筐 按商品汇总
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PackageSumDTO> findPackageSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                              Date dateTo);



	/**
	 * 查询中心筐数据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CenterBoxReportDTO> findCenterBoxReportDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);



	/**
	 * 查询item_recording 过期催销商品
	 * @param bookCode
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findNeedSaleItemRecords(String bookCode, Integer branchNum);


	/**
	 * 查询进出筐 按分店汇总
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PackageSumDTO> findPackageSumByBranch(String systemBookCode,
                                                      Integer centerBranchNum, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo);

	/**
	 * 查询进出筐 按单价
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PackageSumDTO> findPackageSumByPrice(String systemBookCode,
                                                     Integer centerBranchNum, List<Integer> branchNums, Date dateFrom,
                                                     Date dateTo);

	/**
	 * 配送超量特价查询
	 * @param policyPosItemQuery
	 * @return
	 */
	public List<TransferPolicyDTO> findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery);

	/**
	 * 按门店汇总缴款明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode,
                                                                                  List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按分店、收银员汇总缴款明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode,
                                                                                        List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按班次汇总缴款明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param casher
	 * @return
	 */
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode,
                                                                                      List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);

	/**
	* 卖卡汇总报表按门店汇总
	* @param systemBookCode
	* @param branchNums
	* @param dateFrom
	* @param dateTo
	* @return
	*/
	public List<CardQtySumDTO> findCardQtySumDatasByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	* 卖卡汇总报表按门店营业额
	* @param systemBookCode
	* @param branchNums
	* @param dateFrom
	* @param dateTo
	* @return
	*/
	public List<CardQtySumDTO> findCardQtySumDatasByBranchAndDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询推卡提成
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param groupType 1按门店汇总  2 按门店推卡人汇总
	 * @param sellers
	 */
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode, List<Integer> branchNums,
                                                                        Date dateFrom, Date dateTo, Integer groupType, String sellers);












	//以下都是从amazonCenter中动过来的

	public List<Object[]> findItemOutAmount(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 查询商品的最近出库日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemOutDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 查询商品的最近价格 最近操作日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemLatestPriceDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, String posItemLogSummary);


	/**
	 * 需要到货通知的商品
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param posItemLogType
	 * @return
	 */
	public List<Integer> findNoticeItems(String systemBookCode, Integer branchNum, Date dateFrom,
										 Date dateTo, String posItemLogType);


	//  select *
	public List<PosItemLog> findByStoreQueryCondition(StoreQueryCondition storeQueryCondition, int offset, int limit);


	/**
	 * 按商品汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 进出总合计
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> sumByStoreQueryCondition(StoreQueryCondition storeQueryCondition);


	/**
	 * 最后一次出库记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @param endDate
	 * @return
	 */
	public List<PosItemLog> findLast(String systemBookCode,
									 Integer branchNum, Integer storehouseNum, Date endDate);


	/**
	 * 按商品、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 按分店、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findBranchSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 按分店、进出标记汇总进出库数量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findBranchInOutSummary(StoreQueryCondition storeQueryCondition);


	/**
	 * 根据摘要查询商品进出库量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @return
	 */
	public List<Object[]> findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries);


	/**
	 * 根据门店 商品 进出库类型汇总 数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findSumByBranchAndItemFlag(String systemBookCode,
													 List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums);


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
	public List<Object[]> findMoneyByBranchFlag(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据门店 商品 进出库类型汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByBranchItemFlag(String systemBookCode,
													List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询商品最高进价和最低进价
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 查询商品最低进价 和 日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMinPriceAndDate(String systemBookCode,
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
	public List<Object[]> findMaxPriceAndDate(String systemBookCode,
											  Integer branchNum, Date dateFrom, Date dateTo,
											  List<Integer> itemNums);
	//含name code  (已删)
	public List<Object[]> findItemDetails(String systemBookCode,
										  Integer branchNum, Date dateFrom, Date dateTo, List<String> summaries,
										  List<Integer> itemNums, Integer offset, Integer limit, String sortField, String sortType);

	public List<PosItemLog> findByDate(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 查询未上传记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo
	 * @param dateFrom
	 * @return
	 */
	public List<PosItemLog> findUnUpload(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit);


	/**	含name code （已删除）
	 * 查询重复审核的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemLog> findRepeatAuditOrder(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


	/**
	 * 按仓库 商品 日期 摘要 进出标记 多特性标示 汇总进出数量 辅量 金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


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
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @param itemNums
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findBranchItemFlagMemoSummary(String systemBookCode,
														List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 按分店、进出标记汇总数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param posItemLogSummarys 进出类型 有多个用逗号隔开
	 * @return
	 */
	public List<Object[]> findBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, String posItemLogSummarys);

	public void save(PosItemLog posItemLog);

	public void update(PosItemLog posItemLog);

	//  select *
	public void recalItemLogs(String systemBookCode, Integer branchNum, Integer storehouseNum,
							  Integer itemNum, Date dateFrom, Date dateTo, List<OrderTaskDetail> orderTaskDetails, Inventory inventory);


	/**
	 * 按商品、进出标记汇总进出库数量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemInOutSummary(StoreQueryCondition storeQueryCondition);


	public List<PosItemLog> findByBillNo(String systemBookCode, String orderNo);

	public int countByBillNo(String systemBookCode, String posItemLogBillNo);


	/**
	 * 根据营业日 商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findSumByDateItemFlag(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
												Integer storehouseNum, List<String> memos);


	/**
	 * 根据分店 营业日 商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode,
													  List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
													  Integer storehouseNum, List<String> memos);

	/**
	 * 查询范围内有进出库记录的商品
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Integer> findItemNums(String systemBookCode, Integer branchNum, Integer storehouseNum, Date dateFrom, Date dateTo);


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
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findBranchItemMatrixFlagSummary(String systemBookCode,
														  List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
														  Integer storehouseNum, List<String> memos);

	/**
	 *
	 * @param posItemLogBillNo
	 * @param posItemLogBillDetailNum
	 * @return
	 */
	public PosItemLog read(String posItemLogBillNo, Integer posItemLogBillDetailNum);

}
