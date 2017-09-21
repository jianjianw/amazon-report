package com.nhsoft.report.dao;




import com.nhsoft.report.model.OutOrderDetail;
import com.nhsoft.report.model.TransferOutOrder;
import com.nhsoft.report.shared.queryBuilder.TransferProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransferOutOrderDao{


	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums);

	public BigDecimal findBalance(String systemBookCode, Integer centerBranchNum,
                                  Integer branchNum, Date dtFrom, Date dtTo);

	/**
	 * 配送中心、商品汇总调出金额、毛利、数量
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByOutBranchAndItem(TransferProfitQuery transferProfitQuery);

	/**
	 * 配送中心汇总调出金额、毛利、数量
	 * @param transferBranchNum
	 * @param branchNum
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @param itemNums
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findProfitGroupByOutBranch(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums,
                                                     Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 商品汇总调出金额、毛利、数量
	 * @param systemBookCode
	 * @param transferBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryLists
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryLists, List<Integer> itemNums);

	/**
	 * 配送毛利 商品明细
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findDetails(TransferProfitQuery transferProfitQuery);



	/**
	 * 按门店、商品和日期汇总 销售金额 毛利 数量
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemDate(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店和日期汇总配送金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findOutMoneyGroupByBranch(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                    String dateType);

	/**
	 * 按商品和月份汇总金额和毛利
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> transferBranchNums, Date dateFrom, Date dateTo,
                                                  List<Integer> itemNums);

	/**
	 * 查询待配货
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param storehouseNum
	 * @return
	 */
	public List<TransferOutOrder> findToPicking(String systemBookCode,
                                                Integer centerBranchNum, List<Integer> branchNums, Integer storehouseNum);

	/**
	 * 查询已配货待发车
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param storehouseNum
	 * @return
	 */
	public List<TransferOutOrder> findToShip(String systemBookCode,
                                             Integer centerBranchNum, List<Integer> branchNums, Integer storehouseNum);


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
	 * 按商品主键汇总基本数量 金额 常用数量
	 * @param systemBookCode
	 * @param outBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums,
                                          Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 按分店 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums,
                                            Date dateFrom, Date dateTo);
	/**
	 * 按分店 商品 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, Integer centerBranchNum,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 根据调出单主键查询明细
	 * @param outOrderFids
	 * @return
	 */
	public List<OutOrderDetail> findDetails(List<String> outOrderFids);




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
	 * 按年 月 日汇总门店调出金额
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSumByDateType(String systemBookCode, Integer outBranchNum,
                                                  List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 按线路汇总金额和数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param transferLineNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findLineSummary(String systemBookCode, Integer branchNum, List<Integer> transferLineNums,
                                          Date dateFrom, Date dateTo);

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

	public List<Object[]> findProfitGroupByBranch(TransferProfitQuery transferProfitQuery);


	/**
	 * 按商品汇总数量
	 * @param requestOrderFids
	 * @param audit 是否审核
	 * @return
	 */
	public List<Object[]> findItemSummary(List<String> requestOrderFids, Boolean audit);


	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);
}
