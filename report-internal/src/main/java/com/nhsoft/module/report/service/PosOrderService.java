package com.nhsoft.module.report.service;


import com.nhsoft.module.report.dto.CardConsuemAnalysisQuery;
import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.model.PosOrder;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 前台销售单
 * @author nhsoft
 *
 */
public interface PosOrderService {

	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery);

	/**
	 * 根据门店汇总 单据数 单据总额 折扣总额 积分总额
	 * @param cardReportQuery
	 * @return
	 */
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery);

	/**
	 * 按商品主键汇总销售数量 销售金额 毛利金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                      List<Integer> itemNums, boolean queryKit);
	/**
	 * 按门店汇总明细金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchDetailSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                              Date dateTo, List<Integer> itemNums, boolean queryKit);



	/**
	 * 按分店、商品主键汇总销售数量 销售金额 毛利金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                            List<Integer> itemNums, boolean queryKit);


	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO);


	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO);



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


	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);


	/**
	 * 按分店汇总营业额 客单量 毛利
	 * @param systemBookCode
	 * @param branchNums
	 * @param queryBy
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);

	/**
	 * 按营业日汇总 销售额 客单数 毛利
	 * @param systemBookCode
	 * @param branchNums
	 * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);


	/**
	 * 按月份汇总 销售额 客单数 毛利
	 * @param systemBookCode
	 * @param branchNums
	 * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);


	/**
	 * 按班次查询POS单据 包含明细
	 * @param shiftTable
	 * @return
	 */
	public List<PosOrder> findByShiftTableWithDetails(ShiftTable shiftTable);




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
	 * 按分店 操作员 支付方式 汇总消费金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findBranchOperatorPayByMoneySummary(String systemBookCode,
															  List<Integer> branchNums, Date dateFrom, Date dateTo);



	/**
	 * test三张表
	 * */
	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
									  Date dateTo, List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType);


	/**
	 * 已结账单查询
	 * @param posOrderQuery
	 * @param offset 查询起始位
	 * @param limit 查询数量
	 * @return
	 */
	public List<PosOrder> findSettled(PosOrderQuery posOrderQuery,
									  int offset, int limit);

	public Object[] sumSettled(PosOrderQuery posOrderQuery);

	/**
	 * 根据表面卡号 姓名 卡类型汇总 单据数 单据总额 折扣总额 积分总额
	 * @param cardReportQuery
	 * @return
	 */
	public List<Object[]> findSummaryByPrintNum(CardReportQuery cardReportQuery);


	/**
	 * 根据表面卡号 姓名 卡类型    汇总记录数
	 * @param cardReportQuery
	 * @return
	 */
	public Object[] findCountByPrintNum(CardReportQuery cardReportQuery);


	/**
	 * 卡消费 收入汇总
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public BigDecimal sumPosMoneyByCardConsuemAnalysisQuery(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);


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


}

