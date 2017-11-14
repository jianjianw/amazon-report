package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.query.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface Report2Service {
	
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
	public List<Object[]> findProfitAnalysisByBranchDayItem(ProfitAnalysisQueryData profitAnalysisQueryData);

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
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode, List<Integer> branchNums,
                                                                        Date dateFrom, Date dateTo, Integer groupType, String seller);

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
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode,
                                                                            Date dateFrom, Date dateTo, List<Integer> branchNums, List<Integer> itemNums, String saleType, Date timeFrom, Date timeTo);

	/**
	 * 根据门店、营业日汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<CardReportDTO> findCardReportByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                         Date dateTo, Integer cardUserCardType);
	
	/**
	 * 查询单个商品 门店、分级明细汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SaleAnalysisBranchItemGradeDTO> findSaleAnalysisBranchItemGradeDTOs(SaleAnalysisQueryData saleAnalysisQueryData);

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
