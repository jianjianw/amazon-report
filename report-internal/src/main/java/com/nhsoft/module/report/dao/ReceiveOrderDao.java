package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.ReceiveOrderDetail;

import java.util.Date;
import java.util.List;

public interface ReceiveOrderDao{



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
	 * 时间内收货商品基本数量和金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemAmountAndMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 收货明细
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
	 * 收货明细 按商品汇总
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
     * 按商品、多特性汇总最后生产日期
     * @param systemBookCode
     * @param branchNum 收货分店
     * @param itemNums
     * @return
     */
    public List<Object[]> findItemMatrixMaxProducingDates(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	/**
	 * 查询第一次收货时间在指定周期内的商品
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @return
	 */
    public List<Integer> findFirstReceiveItem(String systemBookCode,
                                              Integer branchNum, Date dateFrom, Date dateTo, List<String> categoryCodes);


	/**
	 * 按分店汇总收货数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店主键列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

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


	/**
	 * 查询明细
	 * @param receiveOrderFids
	 * @return
	 */
	public List<ReceiveOrderDetail> findDetails(List<String> receiveOrderFids);





	/**
	 * 按采购员 商品 批次号 汇总数量 金额 常用数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param employees
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findOperatorItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom,
                                                     Date dateTo, List<String> employees, List<Integer> itemNums);



	/**
	 * 按商品汇总收货数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);



	/**
	 * 按供应商查询收货数量与金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 按商品供应商查询收货数量与金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 按分店商品供应商查询收货数量与金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 按营业日汇总单据数、金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBizSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


	/**
	 * 根据批次号查询收货信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param lotNumbers
	 * @param employees
	 * @return
	 */
	public List<Object[]> findOperatorItemLotSummary(String systemBookCode, Integer branchNum, List<String> lotNumbers,
                                                     List<String> employees);





}
