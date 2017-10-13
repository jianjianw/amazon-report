package com.nhsoft.report.rpc;


import com.nhsoft.report.dto.*;
import com.nhsoft.report.shared.queryBuilder.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Report2Rpc {

	/**
	 * 调出特价分析报表
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
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按分店、收银员汇总缴款明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询门店余额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @return
	 */
	public BigDecimal getBranchBalanceMoney(String systemBookCode, Integer centerBranchNum, Integer branchNum);

	/**
	 * 按商品汇总在线订单数据
	 * @param onlineOrderQuery
	 * @return
	 */
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByItem(OnlineOrderQuery onlineOrderQuery);

	/**
	 * 按门店、商品汇总在线订单数据
	 * @param onlineOrderQuery
	 * @return
	 */
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByBranchItem(OnlineOrderQuery onlineOrderQuery);

	/**
	 * 销售分析 -- 门店营业日商品汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchDayItems(SaleAnalysisQueryData queryData);

	/**
	 * 毛利分析 按分店营业日商品汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<BranchBizItemSummary> findProfitAnalysisByBranchDayItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 按班次汇总缴款明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param casher
	 * @return
	 */
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);

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
	 * 查询商品最近收货记录
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findLatestReceiveDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 查询推卡提成
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param groupType 1按门店汇总  2 按门店推卡人汇总
	 * @param seller
	 */
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer groupType, String seller);

	/**
	 * 客单分析 时段分析
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @param itemNums
	 * @param saleType
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 */
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<Integer> itemNums, String saleType, Date timeFrom, Date timeTo);

	/**
	 * 根据门店、营业日汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<CardReportDTO> findCardReportByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 查询单个商品 门店、分级明细汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SaleAnalysisBranchItemGradeDTO> findSaleAnalysisBranchItemGradeDTOs(SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 查询单品进销毛利报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param categoryCodes
	 * @return
	 */
	public List<ItemSummaryDTO> findReceiveSaleReport(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes);
	
	/**
	 * 未配送商品分析
	 * @param systemBookCode
	 * @param transferBranchNum
	 * @param branchNums
	 * @return
	 */
	public List<UnTransferedItemDTO> findUnTransferedItemDTOs(String systemBookCode, Integer transferBranchNum, List<Integer> branchNums);
	
	/**
	 * 按门店查询营业报表
	 * @param customReportQuery
	 * @return
	 */
	public List<CustomReportDTO> findCustomReportByBranch(String systemBookCode, CustomReportQuery customReportQuery);
	
	/**
	 * 按营业日查询营业报表
	 * @param customReportQuery
	 * @return
	 */
	public List<CustomReportDTO> findCustomReportByBizday(String systemBookCode, CustomReportQuery customReportQuery); 
	
	/**
	 * 按商品查询营业报表
	 * @param customReportQuery
	 * @return
	 */
	public List<CustomReportDTO> findCustomReportByItem(String systemBookCode, CustomReportQuery customReportQuery);


	/**
	 * 按商品类别查询报表
	 * @param systemBookCode
	 * @param customReportQuery
	 * @return
	 */
	public List<CustomReportDTO> findCustomReportByCategory(String systemBookCode, CustomReportQuery customReportQuery);
	
	/**
	 * 商品促销查询
	 * @param policyPosItemQuery
	 * @return
	 */
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery policyPosItemQuery);

	/**
	 * 查询前台其他收支报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<OtherInoutReportDTO> findOtherInoutReportDTOs(String systemBookCode, List<Integer> branchNums,
                                                              Date dateFrom, Date dateTo);

	/**
	 * 销售员提成明细 按商品统计
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public List<SalerCommissionDetail> findItemSalerCommissionDetails(String systemBookCode, Date dateFrom,
                                                                      Date dateTo, List<Integer> branchNums, List<String> salerNames);

	/**
	 * 查询要货分析
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param itemCategoryCodes
	 * @return
	 */
	public List<RequestAnalysisDTO> findRequestAnalysisDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> itemCategoryCodes);

	/**
	 * 新品贡献统计
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param itemCategoryCodes
	 * @param newItemValidDay
	 * @return
	 */
	public List<NewItemAnalysis> findNewItemAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> itemCategoryCodes, int newItemValidDay);
	
	
	/**
	 * 查询异动信息
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<OtherInfoSummaryDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	
	/**
	 * 异动明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param infoType
	 * @return
	 */
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String infoType);
}
