package com.nhsoft.module.report.service;



import java.util.Date;
import java.util.List;

/**
 * 收货单
 * @author nhsoft
 *
 */
public interface ReceiveOrderService{

		/**
		 * 根据供应商查询明细
		 * @param systemBookCode
		 * @param branchNum 收货分店
		 * @param supplierNum 供应商主键
		 * @param dateFrom 审核时间起
		 * @param dateTo 审核时间止
		 * @param itemNums 商品主键列表
		 * @return
		 */
		public List<Object[]> findDetailBySupplierNum(String systemBookCode, List<Integer> branchNums, Integer supplierNum,
													  Date dateFrom, Date dateTo, List<Integer> itemNums);

		/**
		 * 按分店商品供应商汇总收货信息
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
		 * 按商品供应商汇总收货信息
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
		 * 按供应商汇总收货信息
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
		 * 按营业日汇总单据数、金额
		 * @param systemBookCode
		 * @param branchNums
		 * @param dateFrom
		 * @param dateTo
		 * @return
		 */
		public List<Object[]> findBizSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

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
	 * 按商品汇总收货数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);


}