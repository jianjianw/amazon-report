package com.nhsoft.report.dao;


import com.nhsoft.report.dto.OutOrderPaying;
import com.nhsoft.report.model.WholesaleOrder;
import com.nhsoft.report.model.WholesaleOrderDetail;
import com.nhsoft.report.shared.State;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;
import com.nhsoft.report.shared.queryBuilder.WholesaleProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WholesaleOrderDao {


	public WholesaleOrder read(String fid);

	/**
	 * 按商品汇总销售数量 销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums);


	/**
	 * 按商品和日期汇总 数量 销售金额 成本
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> regionNums);

	/**
	 * 按商品和月份汇总 销售金额和毛利
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                  List<Integer> itemNums, List<Integer> regionNums);


	/**
	 * 按客户汇总批发金额 成本金额 销售金额
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClient(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 读取批发毛利报表总合计
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public Object[] readProfitSummary(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 按商品 多特性编码汇总批发数量 批发金额 批发成本
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemNum(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 查询销售明细
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findDetail(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 查询待配货单据
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param clientFids
	 * @param storehouseNum
	 * @return
	 */
	public List<WholesaleOrder> findToPicking(String systemBookCode,
                                              Integer centerBranchNum, List<String> clientFids, Integer storehouseNum, List<Integer> regionNums);

	/**
	 * 查询已配货待发车
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param clientFids
	 * @param storehouseNum
	 * @return
	 */
	public List<WholesaleOrder> findToShip(String systemBookCode,
                                           Integer centerBranchNum, List<String> clientFids, Integer storehouseNum, List<Integer> regionNums);

	/**
	 * 根据类别查询商品汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @return
	 */
	public List<Object[]> findItemSumByCategory(String systemBookCode,
                                                Integer branchNum, Date dateFrom, Date dateTo,
                                                List<String> categoryCodeList, List<Integer> regionNums);



	/**
	 * 按客户 商品汇总  批发数量 批发金额 批发成本 零售金额
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClientItemNum(WholesaleProfitQuery wholesaleProfitQuery);


    /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
     * @param dateTo
     * @param dateFrom
     * @return
     */
    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);



    /**
     * 根据客户汇总未付金额
     * @param systemBookCode
     * @param branchNum
     * @param supplierNums
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<String> clientFid, Date dateFrom, Date dateTo, List<Integer> regionNums);









	public List<Object[]> findMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> itemCategories,
                                                 List<Integer> itemNums, List<String> clients, List<Integer> regionNums, Integer storehouseNum, String auditor, String dateType, List<String> sellers);


	/**
	 * 按商品汇总数据
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFids
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param categoryCodes
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo,
                                      List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	/**
	 * 按分店、商品汇总批发数量 批发金额 批发成本
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param object
	 * @param itemNums
	 * @param clients
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                     List<Integer> itemNums, List<String> clients, List<Integer> regionNums);


	/**
	 * 查询客户已销售过的商品ID
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @return
	 */
	public List<Integer> findClientSaledItemNums(String systemBookCode, Integer branchNum, String clientFid);

	/**
	 * 按分店汇总 金额、成本、销售金额、数量
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 按商品主键汇总 金额、成本、销售金额、数量
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按商品 多特性编码汇总批发数量 批发金额 批发成本
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemMatrix(WholesaleProfitQuery wholesaleProfitQuery);



	/**
	 * 根据主键查询明细
	 * @param wholesaleOrderFids
	 * @return
	 */
	public List<WholesaleOrderDetail> findDetails(List<String> wholesaleOrderFids);

	/**
	 * 按操作员汇总 单据数  单据金额  单品数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param operators
	 * @param operatorType
	 * @return
	 */
	public List<Object[]> findOperatorSummary(String systemBookCode,
                                              List<Integer> branchNums, Date dateFrom, Date dateTo,
                                              List<String> operators, String operatorType);

	/**
	 * 按商品 批次号汇总 数量 金额 毛利 常用数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                             List<Integer> itemNums);


	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                              List<Integer> itemNums, List<Integer> regionNums);

	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                    List<Integer> itemNums, List<Integer> regionNums);

	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                 List<Integer> regionNums);

	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes,
                                          List<Integer> itemNums, List<Integer> regionNums);


	public List<WholesaleOrder> find(String systemBookCode, List<Integer> branchNums, String query, Date dateFrom, Date dateTo);


}
