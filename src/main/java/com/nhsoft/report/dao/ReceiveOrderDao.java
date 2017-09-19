package com.nhsoft.report.dao;


import com.nhsoft.report.dto.ReceiveOrderInfoDTO;
import com.nhsoft.report.model.ReceiveOrder;
import com.nhsoft.report.model.ReceiveOrderDetail;
import com.nhsoft.report.model.ReceiveOrderMatrix;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReceiveOrderDao extends Dao{
	
	public ReceiveOrder read(String fid);
	

	
	public List<ReceiveOrder> findByOrderQueryCondition(String systemBookCode, Integer branchNum,
                                                        OrderQueryCondition conditionData, int offset, int limit);

	public Object[] sumByOrderQueryCondition(String systemBookCode, Integer branchNum,
                                             OrderQueryCondition conditionData);

	public List<ReceiveOrder> findAudit(String systemBookCode, Integer branchNum, String orderFid,
                                        Integer supplierNum, Date dateFrom, Date dateTo, boolean hasReturn,
                                        Integer storehouseNum, int offset, int limit, String sortField, String sortType);

	public int countAudit(String systemBookCode, Integer branchNum, String orderFid,
                          Integer supplierNum, Date dateFrom, Date dateTo, boolean hasReturn,
                          Integer storehouseNum);

	/**
	 * 查找需结算的收货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @return
	 */
	public List<ReceiveOrder> findDue(String systemBookCode, Integer branchNum, Integer supplierNum);

	/**
	 * 按供应商汇总应付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param supplierNums
	 * @return
	 */
	public List<Object[]> findMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> suppliers);

	/**
	 * 供应商审核的收货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param supplier
	 * @return
	 */
	public List<ReceiveOrder> findBySupplier(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer supplier);

	/**
	 * 过期未付的收货单
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public List<ReceiveOrder> findExpired(String systemBookCode, Integer branchNum, List<Integer> settleNums);

	/**
	 * 过期未付的数量
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
	public List<ReceiveOrder> findByAuditAndSupplier(String systemBookCode, Date dateFrom, Date dateTo,
                                                     Integer supplierNum);

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

	public List<Object[]> findReceiveOrders(String systemBookCode, List<String> receiveOrderFids);

    /**
     * 查询商品最后生产日期
     * @param systemBookCode
     * @param branchNum
     * @param itemNum
     * @param itemMatrixNum
     * @return
     */
    public Date getMaxProducingDate(String systemBookCode, Integer branchNum, Integer itemNum, Integer itemMatrixNum);

    /**
     * 按商品汇总最后生产日期
     * @param systemBookCode
     * @param branchNum
     * @param storehouseNum
     * @return
     */
    public List<Object[]> findItemMaxProducingDates(String systemBookCode, Integer branchNum, Integer storehouseNum);

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
     * 查询商品的最后收货日期
     * @param systemBookCode
     * @param branchNum
     * @return
     */
	public List<Object[]> findItemLastDate(String systemBookCode, Integer branchNum);

    /**
     * 查询商品收货价格变动明细
     * @param systemBookCode
     * @param branchNum
     * @param itemNum
     * @param dateFrom
     * @param dateTo
     * @param supplierNums
     * @return
     */
    public List<Object[]> findItemPriceDetail(String systemBookCode, Integer branchNum, Integer itemNum, Date dateFrom, Date dateTo, List<Integer> supplierNums);

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
	public List<ReceiveOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param begin
	 * @param end
	 */
	public void updateTransferFlag(String systemBookCode, Integer branchNum, List<String> receiveOrderFids);

	/**
     * 查询未上传单据数量
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public int countUnUploadAudit(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 按日汇总商品收货数量 收货金额 最低进价 最高进价
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @return
	 */
	public List<Object[]> findItemSummaryByDay(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                               Integer itemNum, Integer limit, String order);

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
	 * 查询已经存在的关联单号
	 * @param orderTaskDetailFids
	 * @return
	 */
	public List<String> findDetailRefBills(List<String> orderTaskDetailFids);

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
	 * 查询审核的单据
	 * @param systemBookCode
	 * @param branchNum 收货分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<ReceiveOrder> findAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查询审核的审核员不是管理员的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<ReceiveOrder> findWmsAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查询明细
	 * @param receiveOrderFids
	 * @return
	 */
	public List<ReceiveOrderDetail> findDetails(List<String> receiveOrderFids);

	/**
	 * 应付账款数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Integer countPaidOrder(String systemBookCode, Integer branchNum);

	public ReceiveOrder readLock(String receiveOrderFid);

	/**
	 * 修改单据收货状态
	 * @param receiveOrderFid
	 * @param receiveOrderPurchaseState
	 */
	public void updatePurchaseState(String receiveOrderFid, String receiveOrderPurchaseState);

	/**
	 * 查询月采购汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public void updateDetail(ReceiveOrderDetail receiveOrderDetail);

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
     * 按商品汇总最后生产日期
     * @param systemBookCode
     * @param branchNum 收货分店
     * @param itemNums 商品主键列表
     * @return
     */
    public List<Object[]> findItemMaxProducingDates(String systemBookCode, Integer branchNum, List<Integer> itemNums);

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

	public void updateAllNotDelete(ReceiveOrder receiveOrder);

	/**
	 * 查询已生成的收货单的数量、赠品数量
	 * @param purchaseOrderFid
	 * @return
	 */
	public List<Object[]> findItemSummaryByPurchase(String purchaseOrderFid);

	/**
	 * 按采购单查询通过该采购单的收货单数量
	 * @param purchaseOrderFid
	 * @return
	 */
	public Integer getCount(String purchaseOrderFid);

	/**
	 * 查询收货时间
	 * @param purchaseOrderFids
	 * @return
	 */
	public List<Object[]> findReceiveDate(List<String> purchaseOrderFids);

	/**
	 * 按供应商查询收货数量与金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

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

	public ReceiveOrderDetail getByRefBill(String receiveOrderDetailRefBill);

	public List<ReceiveOrder> findByFids(List<String> receiveOrderFids);

	public void saveReceiveOrderMatrix(ReceiveOrderMatrix receiveOrderMatrix);

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

	/**
	 * 按财务科目类型查询数据 按门店汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param financeSubjectType
	 * @return
	 */
	public List<Object[]> findFinanceBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                   Date dateTo, String financeSubjectType);

	/**
	 * 按财务科目类型查询数据 按类别汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param financeSubjectType
	 * @return
	 */
	public List<Object[]> findFinanceCategorySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                     Date dateTo, String financeSubjectType);

	public List<ReceiveOrder> findByPurchaseOrderFid(String systemBookCode, String purchaseOrderFid);
	
	/**
	 * 根据收货单号查询商品
	 * @param receiveOrderFids
	 * @return
	 */
	public List<Integer> findItemNumsByFids(List<String> receiveOrderFids);

	public List<ReceiveOrder> findLostReceiveOrders();

	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer supplierNum);

	public List<ReceiveOrderInfoDTO> findReceiveOrderInfos(String systemBookCode, Integer itemNum, Integer supplierNum);

}
