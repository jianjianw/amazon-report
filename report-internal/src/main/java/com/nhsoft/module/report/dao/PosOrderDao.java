package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.model.PosOrderDetail;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;

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
	 * 按商品主键汇总销售数量 销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细统计
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                      List<Integer> itemNums, boolean queryKit);

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
								 List<Integer> branchNums, Date dateFrom, Date dateTo);

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
	 * @return
	 */
	public List<Object[]> findBusiDiscountAnalysisBranchs(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

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
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);

	/**
	 * 按营业日汇总     销售额     客单数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums,Date dateFrom, Date dateTo, Boolean isMember);


	/**
	 * 按月份汇总 销售额 客单数 毛利
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums,Date dateFrom, Date dateTo, Boolean isMember);


}
