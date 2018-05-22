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
		 *
		 * @param systemBookCode
		 * @param branchNums     分店主键列表
		 * @param dateFrom       审核时间起
		 * @param dateTo         审核时间止
		 * @param itemNums       商品主键列表
		 * @return
		 */
		public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

		/**
		 * 按商品汇总收货数量 金额
		 *
		 * @param systemBookCode
		 * @param branchNums     分店
		 * @param dateFrom       审核时间起
		 * @param dateTo         审核时间止
		 * @param itemNums       商品主键列表
		 * @return
		 */
		public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

		/**
		 * 查询月采购汇总
		 * @param systemBookCode
		 * @param branchNum
		 * @param dateFrom
		 * @param dateTo
		 * @param dateType  收获期日 or 审核日期
		 * @param strDate 按时间汇总   按日 or  按月
		 * @return
		 */
		public List<Object[]> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String dateType,String strDate);

		/**
		 * 根据营业日查询采购 金额 数量
		 */
		public List<Object[]> findPurchaseByBiz(String systemBookCode,Date dateFrom, Date dateTo,List<Integer> itemNums);

		/**
		 * 按商品、多特性汇总最后生产日期
		 *
		 * @param systemBookCode
		 * @param branchNum      收货分店
		 * @param itemNums
		 * @return
		 */
		public List<Object[]> findItemMatrixMaxProducingDates(String systemBookCode, Integer branchNum, List<Integer> itemNums);


		/**
		 * 根据商品营业日汇总 采购金额和数量
		 * */
		public List<Object[]> findPurchaseByItemBiz(String systemBookCode,Date dateFrom,Date dateTo,List<Integer> itemNums);

}