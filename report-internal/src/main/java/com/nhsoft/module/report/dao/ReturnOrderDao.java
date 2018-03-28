package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.ReturnOrderDetail;

import java.util.Date;
import java.util.List;

public interface ReturnOrderDao {



	/**
	 * 根据供应商查询明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @param dateFrom
	 * @param dateTo
	 * @param selectItemNums
	 * @return
	 */
	public List<Object[]> findDetailBySupplierNum(String systemBookCode, List<Integer> branchNums, Integer supplierNum,
                                                  Date dateFrom, Date dateTo, List<Integer> selectItemNums);

	/**
	 * 时间内退货商品基本数量和金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemAmountAndMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 退货明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param itemCategoryCodes
	 * @param supplierNums
	 * @param operator
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findQueryDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums,
                                           List<String> itemCategoryCodes, List<Integer> supplierNums, String operator, Integer storehouseNum);

	/**
	 * 退货明细 按商品汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param itemCategoryCodes
	 * @param supplierNums
	 * @param operator
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findQueryItems(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums,
                                         List<String> itemCategoryCodes, List<Integer> supplierNums, String operator, Integer storehouseNum);

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
	 * 查询明细
	 * @param returnOrderFids
	 * @return
	 */
	public List<ReturnOrderDetail> findDetails(List<String> returnOrderFids);

	/**
	 * 按分店汇总退货数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                            Date dateTo, List<Integer> itemNums);
	
	/**
	 * 按分店、商品汇总收货数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店主键列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);
	
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);
	
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 查询退货汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String strDate);


}
