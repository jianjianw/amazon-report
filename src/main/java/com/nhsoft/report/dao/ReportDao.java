package com.nhsoft.report.dao;


import com.nhsoft.report.dto.*;
import com.nhsoft.report.model.CustomReport;
import com.nhsoft.report.shared.queryBuilder.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReportDao {
	
	public Object excuteSql(String systemBookCode, String sql);
	
	
	/**
	 * 日销售报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @parma isMember 是否会员
	 * @return
	 */
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);
	
	/**
	 * 月销售报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);
	
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
	 * 类别同比 环比
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 销售明细对比
	 * @param loadConfig
	 * @param systemBookCode
	 * @param branchNums
	 * @param oderItemCodeList
	 * @param lastDateFrom
	 * @param lastDateTo
	 * @param thisDateFrom
	 * @param thisDateTo
	 * @return
	 */
	public List<Object[]> findOrderDetailCompareDatas(String systemBookCode,
                                                      List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

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
	 * 零售明细查询
	 * @param retailDetailQueryData
	 * @return
	 */
	public List<RetailDetail> findRetailDetails(
            RetailDetailQueryData retailDetailQueryData);

	/**
	 * 毛利分析 日毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisDays(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 客户毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisByClientAndItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 商品毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData);
	
	/**
	 * 毛利分析 商品毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisByItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 商品毛利汇总(成分商品)
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findKitProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 销售员提成 按品牌汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param salerNums
	 * @return
	 */
	public List<SalerCommissionBrand> findSalerCommissionBrands(String systemBookCode, Date dtFrom,
																Date dtTo, List<Integer> branchNums, List<String> salerNums);

	/**
	 * 销售员提成汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param salerNums
	 * @return
	 */
	public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dtFrom,
													  Date dtTo, List<Integer> branchNums, List<String> salerNums);

	/**
	 * 销售员客单金额阶梯汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param salerNums
	 * @param interval
	 * @return
	 */
	public List<Object[]> findSalerCommissionsByMoney(String systemBookCode, Date dtFrom,
                                                      Date dtTo, List<Integer> branchNums, List<String> salerNums, BigDecimal interval);

	/**
	 * 销售员总合计
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param salerNums
	 * @return
	 */
	public Object[] findSalerSummary(String systemBookCode, Date dtFrom,
                                     Date dtTo, List<Integer> branchNums, List<String> salerNums);

	/**
	 * 销售员提成明细
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param salerNums
	 * @return
	 */
	public List<SalerCommissionDetail> findSalerCommissionDetails(String systemBookCode, Date dtFrom,
																  Date dtTo, List<Integer> branchNums, List<String> salerNums);

	/**
	 * 客单分析 历史客单分析
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisHistorys(String systemBookCode,
                                                       Date dtFrom, Date dtTo, List<Integer> branchNums, String saleType);

	/**
	 * 客单分析 客单范围分析
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param rangeFrom
	 * @param rangeTo
	 * @param saleType
	 * @return
	 */
	public Object[] findCustomerAnalysisRanges(
            String systemBookCode, Date dtFrom, Date dtTo,
            List<Integer> branchNums, Integer rangeFrom, Integer rangeTo, String saleType);

	/**
	 * 客单分析 日客单分析
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisDays(String systemBookCode,
                                                   Date dtFrom, Date dtTo, List<Integer> branchNums, String saleType);

	/**
	 * 客单分析 分店汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisBranch(String systemBookCode,
                                                     Date dtFrom, Date dtTo, List<Integer> branchNums, String saleType);

	/**
	 * 客单分析 时段分析
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisTimePeriods(String systemBookCode,
                                                          Date dtFrom, Date dtTo, List<Integer> branchNums, Integer space, String saleType);

	/**
	 * 金额 客单量汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param branchType
	 * @return
	 */
	public Object[] sumCustomerAnalysis(String systemBookCode,
                                        Date dtFrom, Date dtTo, List<Integer> branchNums, String branchType);

	/**
	 * 销售分析 -- 类别 部门 品牌汇总公用
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findSaleAnalysisCommon(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 商品汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 商品 分级码汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findSaleAnalysisCommonItemGrade(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 分店汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findSaleAnalysisByBranchs(SaleAnalysisQueryData queryData);


	/**
	 * 销售分析 -- 类别-分店汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData queryData);

	/**
	 * 返利分析 按商品汇总
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<Object[]> findItemRebates(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 返利分析 明细
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<Object[]> findRebatesDetail(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 返利分析总合计
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public Object[] findRebatesSum(PolicyAllowPriftQuery policyAllowPriftQuery);

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
	 * 按营业日汇总前台销售金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param type
	 * @return
	 */
	public List<Object[]> findPosOrderMoneyByBizDay(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

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
	 * 卡消费 收入汇总
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public BigDecimal sumPosMoneyByCardConsuemAnalysisQuery(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);

	/**
	 * 会员指标消费分析 明细
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public List<Object[]> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);

	/**
	 * 查询门店商品销售排行 前100
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosItemRank> findPosItemRanks(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按门店类型汇总 前台销售金额 客单量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosGroupByBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按小时和门店区域类型汇总客单量 和金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosGroupByHourAndBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按小时汇总客单量 和金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosGroupByHour(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按商品汇总POS销售毛利 数量 金额 成本
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param kitFlag
	 * @return
	 */
	public List<Object[]> findSummaryGroupByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, boolean kitFlag);

	/**
	 * 按成分商品汇总POS销售毛利 数量 金额 成本
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param kitFlag
	 * @return
	 */
	public List<Object[]> findSummaryGroupByKitItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

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
	 * 毛利分析 按分店汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisBranchs(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 查询消费券明细
	 * @param elecTicketQueryDTO
	 * @return
	 */
	public List<ElecTicketDTO> findElecTicketDTOs(ElecTicketQueryDTO elecTicketQueryDTO);

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
	 * 客单分析 按班次客单分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param appUserName 收银员
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisShiftTables(String systemBookCode, Date dateFrom, Date dateTo,
                                                          List<Integer> branchNums, String appUserName, String saleType);

	/**
	 * 查询商品时段分析
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @param space
	 * @param itemNums
	 * @param saleType
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
                                                                 List<Integer> branchNums, Integer space, List<Integer> itemNums, String saleType);

	/**
	 * 生成自定义报表
	 * @param queryReportDTO
	 * @param customReport
	 * @return
	 */
	public List<Object[]> findReportDTOs(ReportDTO queryReportDTO, CustomReport customReport);

	/**
	 * 消费券分析报表
	 * @param elecTicketQueryDTO
	 * @return
	 */
	public List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO);

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
	 * 查询商品连带商品次数排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param selectCount
	 * @return
	 */
	public List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                   Date dateTo, Integer itemNum, Integer selectCount);

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
	 * 销售分析 -- 分店 营业日汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData);

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
	 * 销售分析 按分店 营业日 商品汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByBranchDayItem(
            SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 按分店、营业日 商品汇总数据
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisByBranchDayItem(
            ProfitAnalysisQueryData profitAnalysisQueryData);

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

	/**
	 * 客单分析 时段分析
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @param saleType
	 * @param timeFrom
	 * @param timeTo
	 * @return
	 */
	public Object[] findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo,
                                                    List<Integer> branchNums, String saleType, String timeFrom, String timeTo);

	/**
	 * 查询商品时段分析
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
	public Object[] findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
                                                           List<Integer> branchNums, List<Integer> itemNums, String saleType, String timeFrom, String timeTo);
	
	/**
	 * 按分店号、分级明细汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findBranchGradeSummary(SaleAnalysisQueryData saleAnalysisQueryData);

	public List<Object[]> findCustomerAnalysisBranchItem(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, List<Integer> itemNums);

	/**
	 * 按分店查询营业额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);


	/**
	 * 按分店查询卡存款
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findDepositByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按分店查询卡消费
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findConsumeByBranch(String systemBookCode, List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询报损金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询报损金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询配销差额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findDifferenceMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询新增会员数
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findCardUserCountByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询营业额目标
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findSaleMoneyGoalsByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo,String dateType);

	/**
	 * 查询分店面积
	 * @param systemBookCode
	 * @param branchNums 分店号
	 */

	 public List<Object[]> findBranchArea(String systemBookCode,List<Integer> branchNums);

	/**
	 *按分店查询损耗金额		试吃，去皮，报损，其它
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

}
