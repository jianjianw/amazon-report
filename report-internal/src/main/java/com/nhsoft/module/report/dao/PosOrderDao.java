package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.PolicyAllowPriftQuery;
import com.nhsoft.module.report.query.ProfitAnalysisQueryData;
import com.nhsoft.module.report.query.RetailDetailQueryData;
import com.nhsoft.module.report.query.SaleAnalysisQueryData;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PosOrderDao {

	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery);

	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据门店汇总 单据数 单据总额 折扣总额 积分总额
	 * @param cardReportQuery
	 * @return
	 */
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery);

	/**
	 * 按商品、多特性编码汇总销售数量 销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit 组合商品按成分统计
	 * @return
	 */
	public List<Object[]> findItemMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit);
	
	/**
	 * 按门店汇总金额 折扣金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按年 月 日汇总门店销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findBranchSumByDateType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);


	/**
	 * 门店 商品汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit 组合商品是否按明细查询
	 * @param itemNums
	 * @return
	 */

	public List<Object[]> findItemSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 * 按本店 商品 营业日汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @param categoryCodes
	 * @param queryKit 组合商品按明细统计
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchAndItemAndBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit);


	/**
	 * 根据商品和月份汇总 销售金额和成本
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * ABC 分析 商品汇总  赠送状态金额为0 退货状态减去
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findAbcItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList);


	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                      Date dateTo, List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType);

	/**
	 * 按分店汇总明细金额 数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchDetailSum(String systemBookCode,
                                              List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, boolean queryKit);


	/**
	 * 查询有营业数据产生的分店数量
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public int countActiveBranchs(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 按门店 商品 多特性 汇总 销售金额 数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 按客户汇总签单金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findClientsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> clientFids);


	public List<PosOrderDetail> findDetails(String orderNo);

	/**
	 * 按分店汇总支付金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param paymentType
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                            String paymentType);

	/**
	 * 按分店汇总 销售金额 销售毛利  四舍五入 经理折扣 总消费券面值 消费券折扣
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                            Date dateTo);

	/**
	 * 按门店 商品 汇总 数量  销售金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 是否按组合商品查询
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                List<Integer> itemNums, boolean queryKit);

	public List<PosOrderDetail> findDetails(List<String> orderNos);

	/**
	 * 按营业月 商品 汇总 销售金额 销售数量
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                  Date dateTo, List<Integer> itemNums);

	/**
	 * 按商品供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                    Date dateTo, boolean queryKit);

	/**
	 * 按商品分店供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                        Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 * 按供应商查询销售明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo, List<String> categoryCodes, boolean queryKit);


	/**
	 * 按供应商汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums,
                                          boolean queryKit);

	/**
	 * 按商品供应商汇总销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierInfoByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                         Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);


	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO);

	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO);


	/**
	 * 按门店统计单据数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 查询前台现金收入
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getPosCash(String systemBookCode,
                                 List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 查询反结账单据数量和金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Object[] findRepayCountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 营业折扣汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @return
	 */
	public Object[] sumBusiDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

	/**
	 * 营业折扣汇总 根据分店
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param stallNums
	 * @return
	 */
	public List<Object[]> findBusiDiscountAnalysisBranchs(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, List<Integer> stallNums);

	/**
	 * 按门店汇总前台现金收入
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosCashGroupByBranch(String systemBookCode,
                                                   List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询反结账明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findRepayDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 营业折扣明细
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findClientDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

	/**
	 * 营业折扣明细
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMgrDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

	/**
	 * 按分店查询营业额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);

	/**
	 * 按营业日汇总     销售额     客单数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Boolean isMember);


	/**
	 * 按月份汇总 销售额 客单数 毛利
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Boolean isMember);


	//以下都是从ReportDao移过来的
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
	 * 零售明细查询
	 * @param retailDetailQueryData
	 * @return
	 */
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData retailDetailQueryData, boolean isFm);

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

	public List<Object[]>  findMerchantSaleAnalysisCommon(SaleAnalysisQueryData queryData);


	/**
	 * 销售分析 -- 商品汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 商品汇总
	 * @param queryData
	 * @return
	 */
	public List<Object[]>  findMerchantSaleAnalysisCommonItemMatrix(SaleAnalysisQueryData queryData);

	/**
	 * 按班次查询POS单据 包含明细
	 * @param shiftTable
	 * @return
	 */
	public List<PosOrder> findByShiftTable(ShiftTable shiftTable);

	public List<Payment> findPaymentsByOrderNos(List<String> orderNos);

	/**
	 * 按第三方单据号查询销售单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param orderExternalNo
	 * @return
	 */
	public List<PosOrder> findOrderByExternalNo(String systemBookCode, Integer branchNum, String orderExternalNo);

	/**
	 * 根据班次查询
	 * @param shiftTables
	 * @return
	 */
	public List<PosOrder> findByShiftTables(List<ShiftTable> shiftTables);

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
	 * 按营业日汇总前台销售金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findPosOrderMoneyByBizDay(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType, List<Integer> stallNums);

	/**
	 * 卡消费 收入汇总
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public BigDecimal sumPosMoneyByCardConsuemAnalysisQuery(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);


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
	 * @param branchNum
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
	 * 消费券分析报表
	 * @param elecTicketQueryDTO
	 * @return
	 */
	public List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO);


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
	 * 销售分析 -- 分店 营业日汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData);


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
	public List<Object[]> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo,
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
	public List<Object[]> findCustomerAnalysisTimePeriodsByItems(String systemBookCode, Date dateFrom, Date dateTo,
                                                                 List<Integer> branchNums, List<Integer> itemNums, String saleType, String timeFrom, String timeTo);


	/**
	 * 按分店号、分级明细汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findBranchGradeSummary(SaleAnalysisQueryData saleAnalysisQueryData);


	public List<Object[]> findCustomerAnalysisBranchItem(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, List<Integer> itemNums);


	/**
	 * 销售分析 -- 按分店汇总商品信息
	 * @param queryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByBranchPosItems(String systemBookCode, SaleAnalysisQueryData queryData);

	/**
	 * 按分店汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchCouponSummary(String systemBookCode,
																   List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按分店汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMerchantCouponSummary(String systemBookCode,
												  Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);

	/**
	 * 按分店汇总折扣金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchDiscountSummary(String systemBookCode,
														 List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMerchantDiscountSummary(String systemBookCode,
													  Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);

	public List<Object[]> findStallDiscountSummary(String systemBookCode,
													  Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo);

	/**
	 * 按分店 营业日 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchBizdayCouponSummary(String systemBookCode,
																	  List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMerchantBizdayCouponSummary(String systemBookCode,
														Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);

	public List<Object[]> findStallCouponSummary(String systemBookCode,
														  Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo);

	/**
	 * 按分店 营业日 汇总折扣金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchBizdayDiscountSummary(String systemBookCode,
																	List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMerchantBizdayDiscountSummary(String systemBookCode,
															Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);
	/**
	 * 按分店 营业日 终端 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findCouponSummary(String systemBookCode, List<Integer> branchNums,
														   Date dateFrom, Date dateTo);

	/**
	 * 按分店 营业日 终端 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findCouponDiscountSummary(String systemBookCode, List<Integer> branchNums,
											Date dateFrom, Date dateTo);
	/**
	 * 按分店 班次 支付方式 汇总消费金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, List<Integer> branchNums,
																	   Date dateFrom, Date dateTo, String casher);

	/**
	 * 按分店 班次 支付方式 汇总消费金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, Integer branchNum, Integer merchantNum,
															 Date dateFrom, Date dateTo, String casher);

	/**
	 * 按分店 班次 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchShiftTableCouponSummary(String systemBookCode, List<Integer> branchNums,
														  Date dateFrom, Date dateTo, String casher);


	/**
	 * 按分店 班次 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchShiftTableDiscountSummary(String systemBookCode, List<Integer> branchNums,
															  Date dateFrom, Date dateTo, String casher);

	/**
	 * 按分店 班次 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMerchantShiftTableCouponSummary(String systemBookCode, Integer branchNum, Integer merchantNum,
															Date dateFrom, Date dateTo, String casher);

	/**
	 * 按分店 班次 汇总消费券
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMerchantShiftTableDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum,
																Date dateFrom, Date dateTo, String casher);

	/**
	 * 按分店 操作员 支付方式 汇总消费金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchOperatorPayByMoneySummary(String systemBookCode,
																	   List<Integer> branchNums, Date dateFrom, Date dateTo);


	public List<PosOrder> findSettled(PosOrderQuery posOrderQuery, int offset, int limit);

	/**
	 * 查询单据号 付款名称 金额
	 * @param orderNos
	 * @return
	 */
	public List<Object[]> findOrderPaymentMoneys(List<String> orderNos);

	public List<Object[]> findOrderPaymentMoneys(PosOrderQuery posOrderQuery);

	/**
	 * 已结账单汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dtFrom
	 * @param dtTo
	 * @param orderNo
	 * @param saleType
	 * @param orderOperator  收银员
	 * @param orderState
	 * @param clientFid
	 * @param orderExternalNo
	 * @return
	 */
	public Object[] sumSettled(PosOrderQuery posOrderQuery);


	/**
	 * 根据表面卡号 姓名 卡类型汇总 单据数 单据总额 折扣总额 积分总额
	 * @param cardReportQuery
	 * @return
	 */
	public List<Object[]> findSummaryByPrintNum(CardReportQuery cardReportQuery);

	/**
	 * 根据表面卡号 姓名 卡类型    汇总记录数,
	 * @param cardReportQuery
	 * @return
	 */
	public Object[] findCountByPrintNum(CardReportQuery cardReportQuery);


	//日销售分析
	public List<Object[]> findDaySaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	//月销售分析
	public List<Object[]> findMonthSaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


	/**
	 * 客单分析 历史客单分析  分页查询
	 * @return
	 */
	public List<Object[]> findCustomerAnalysisHistorysByPage(SaleAnalysisQueryData saleAnalysisQueryData);


	/**
	 * 客单分析 历史客单分析  统计总条数
	 * */
	public Object[] findCustomerAnalysisHistorysCount(SaleAnalysisQueryData saleAnalysisQueryData);



	/**
	 * 毛利分析 商品毛利汇总   分页查询
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisByBranchAndItemByPage(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 商品毛利汇总  分页查询  查询总条数
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public Object[] findProfitAnalysisByBranchAndItemCount(ProfitAnalysisQueryData profitAnalysisQueryData);


	/**
	 * 毛利分析 日毛利汇总  分页查询
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<Object[]> findProfitAnalysisDaysByPage(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 日毛利汇总  分页查询   查询总条数
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public Object[] findProfitAnalysisDaysCount(ProfitAnalysisQueryData profitAnalysisQueryData);


	/**
	 * 销售分析 -- 按分店汇总商品信息   分页查询
	 * @param queryData
	 * @return
	 */
	public List<Object[]> findSaleAnalysisByBranchPosItemsByPage(SaleAnalysisQueryData queryData);


	/**
	 * 销售分析 -- 按分店汇总商品信息   分页查询    查询总条数
	 * @param queryData
	 * @return
	 */
	public Object[] findSaleAnalysisByBranchPosItemsCount(SaleAnalysisQueryData queryData);

	/**
	 * 查询单据  分页查询
	 * @param cardReportQuery
	 * @return
	 */
	public List<PosOrder> findByCardReportQueryPage(CardReportQuery cardReportQuery);


	/**
	 * 查询单据  分页查询   查询总条数
	 * @param cardReportQuery
	 * @return
	 */
	public Object[] findByCardReportQueryCount(CardReportQuery cardReportQuery);

	/**
	 * 零售明细统计  分页查询
	 */
	public List<RetailDetail> findRetailDetailsByPage(RetailDetailQueryData queryData, boolean isFm);


	/**
	 * 零售明细统计  分页查询   查询总条数
	 * */
	public Object[] findRetailDetailsCount(RetailDetailQueryData queryData, boolean isFm);



	/**
	 * 让利按商品汇总  分页查询
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<Object[]> findPromotionItemsByPage(PolicyAllowPriftQuery policyAllowPriftQuery);


	/**
	 * 让利按商品汇总  分页查询   查询总条数
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public Object[] findPromotionItemsCount(PolicyAllowPriftQuery policyAllowPriftQuery);

	public List<PosOrderMatrix> findMatrixs(List<String> orderNos);

	public List<PosOrderMatrix> findMatrixs(PosOrderQuery posOrderQuery);



}
