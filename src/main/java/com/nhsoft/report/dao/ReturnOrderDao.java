package com.nhsoft.report.dao;



import com.nhsoft.report.model.ReturnOrder;
import com.nhsoft.report.model.ReturnOrderDetail;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReturnOrderDao {

	
	public ReturnOrder read(String fid);

	
	public List<ReturnOrder> findByOrderQueryCondition(String systemBookCode, Integer branchNum,
													   OrderQueryCondition conditionData, int offset, int limit);

	public Object[] sumByOrderQueryCondition(String systemBookCode, Integer branchNum,
                                             OrderQueryCondition conditionData);

	/**
	 * 查找需结算的退货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @return
	 */
	public List<ReturnOrder> findDue(String systemBookCode, Integer branchNum, Integer supplierNum);

	/**
	 * 应付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param suppliers
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> supplierNums);

	/**
	 * 供应商审核的退货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param supplier
	 * @return
	 */
	public List<ReturnOrder> findBySupplier(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer supplier);

	/**
	 * 过期未付的退货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public List<ReturnOrder> findExpired(String systemBookCode, Integer branchNum, List<Integer> settleNums);

	/**
	 * 过期未付的退货单数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public int countExpired(String systemBookCode, Integer branchNum, List<Integer> settleNums);

	/**
	 * 过期未付的金额合计
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public List<Object[]> sumExpired(String systemBookCode, Integer branchNum, List<Integer> settleNums);

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
	 * 根据供应商查询商品汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @param dateFrom
	 * @param dateTo
	 * @param selectItemNums
	 * @return
	 */
	public List<Object[]> findSumBySupplierNum(String systemBookCode, Integer branchNum, Integer supplierNum,
                                               Date dateFrom, Date dateTo, List<Integer> selectItemNums);

	/**
	 * 根据供应商查询总合计
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @param dateFrom
	 * @param dateTo
	 * @param selectItemNums
	 * @return
	 */
	public BigDecimal findTotalMoneyBySupplier(String systemBookCode, Integer branchNum, Integer supplierNum,
                                               Date dateFrom, Date dateTo, List<Integer> selectItemNums);

	/**
	 * 根据审核时间和供应商查询
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param supplierNum
	 * @return
	 */
	public List<ReturnOrder> findByAuditAndSupplier(String systemBookCode, Date dateFrom, Date dateTo,
                                                    Integer supplierNum);

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
     * 按日期删除单据
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     */
    public void deleteByDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, boolean remainUnSettled);

    /**
     * 取最小单据日期
     * @param systemBookCode
     * @param branchNum
     * @return
     */
    public Date getFirstDate(String systemBookCode, Integer branchNum);

    /**
     * 根据供应商汇总未付金额
     * @param systemBookCode
     * @param branchNum
     * @param supplierNums
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<Integer> supplierNums, Date dateFrom, Date dateTo);

    /**
     * 查询退货总合计 退货基本数量 赠送基本数量 退货常用数量 退货金额  赠送常用数量 赠送金额
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @param itemNums
     * @param itemCategoryCodes
     * @param supplierNums
     * @return
     */
    public Object[] findSum(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                            List<Integer> itemNums, List<String> itemCategoryCodes,
                            List<Integer> supplierNums);

    /**
     * 查询未上传单据
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @param offset
     * @param limit
     * @return
     */
	public List<ReturnOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param begin
	 * @param end
	 */
	public void updateTransferFlag(String systemBookCode, Integer branchNum, List<String> orderFids);

	/**
     * 查询未上传单据数量
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public int countUnUploadAudit(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 批量删除
	 * @param systemBookCode
	 * @param limitDate
	 * @param batchSize
	 * @param keepUnSettleChain
	 * @return
	 */
	public Integer batchDelete(String systemBookCode, Date limitDate, int batchSize, Boolean keepUnSettle);

	/**
	 * 查询审核的单据
	 * @param systemBookCode
	 * @param branchNum 收货分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<ReturnOrder> findAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查询审核的审核员不是管理员的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<ReturnOrder> findWmsAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

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
	
	/**
	 * 应付账款数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
    public Integer countPaidOrder(String systemBookCode, Integer branchNum);
    
    /**
     * 查询退货汇总
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);
	
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);
	
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	public List<ReturnOrder> findLossReturnOrders();

	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer supplierNum);


}
