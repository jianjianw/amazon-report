package com.nhsoft.report.dao;



import com.nhsoft.report.dto.OutOrderPaying;
import com.nhsoft.report.dto.PosItemCategoryRank;
import com.nhsoft.report.dto.PosItemRank;
import com.nhsoft.report.model.OutOrderDetail;
import com.nhsoft.report.model.PosItem;
import com.nhsoft.report.model.TransferOutOrder;
import com.nhsoft.report.shared.State;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;
import com.nhsoft.report.shared.queryBuilder.TransferOutOrderQuery;
import com.nhsoft.report.shared.queryBuilder.TransferProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransferOutOrderDao extends Dao{
	
	public TransferOutOrder read(String outOrderFid);
	
	public TransferOutOrder readWithLock(String outOrderFid);

	
	public List<TransferOutOrder> findByQuery(TransferOutOrderQuery transferOutOrderQuery, int first, int count);
	
	public int countByQuery(TransferOutOrderQuery transferOutOrderQuery);
	
	public List<OutOrderDetail> findByfid(String outOrderFid);
	
	public int findTransferOutOrderCount(String systemBookCode,
                                         Integer branchNum, Date dateFrom, Date dateTo);

	public Object[] findTransferOutOrderDetail(String systemBookCode,
                                               Integer branchNum, Integer outBranchNum, Date dateFrom, Date dateTo, Boolean expire);

	public List<PosItemRank> findPosItemRankDatas(String systemBookCode,
												  Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String selectedSort, String categoryCode);

	public List<PosItemCategoryRank> findPosItemCategoryRanks(
            String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom,
            Date dateTo);

	/**
	 * 查询门店应付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNum2
	 * @param dtFrom
	 * @param dtTo
	 * @return
	 */
	public BigDecimal findTransderOutMoney(String systemBookCode,
                                           Integer centerBranchNum, Integer branchNum, Date dtFrom, Date dtTo);

	public BigDecimal findBalance(String systemBookCode, Integer centerBranchNum,
                                  Integer branchNum, Date dtFrom, Date dtTo);

	public List<TransferOutOrder> findBranchTransferOutOrder(String systemBookCode, Integer branchNum,
                                                             OrderQueryCondition orderQueryCondition, int first, int count);

	public void saveAll(TransferOutOrder transferOutOrder);

	public void updateAll(TransferOutOrder transferOutOrder);

	public void remove(TransferOutOrder transferOutOrder);

	public List<TransferOutOrder> findByFids(List<String> fids);

	/**
	 * 根据TransferOutOrderQuery查总合计
	 * @param transferOutOrderQuery
	 * @return
	 */
	public List<Object[]> findSummaryByTransferOutOrderQuery(TransferOutOrderQuery transferOutOrderQuery);

	/**
	 * 根据OrderQueryCondition查总合计
	 * @param branchTransferOutOrderQuery
	 * @return
	 */
	public Object[] sumByOrderQueryCondition(String systemBookCode, Integer branchNum,
                                             OrderQueryCondition orderQueryCondition);

	/**
	 * 晴雨表 按门店汇总应收金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<OutOrderPaying> findBarometerOrder(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 判断配货单号合法性
	 * @param systemBookCode
	 * @param branchNum
	 * @param fid
	 * @return
	 */
	public Boolean isValid(String systemBookCode, Integer branchNum, String fid);

	/**
	 * 根据结算门店查询单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public List<TransferOutOrder> findBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo);

	/**
	 * 根据结算门店查询单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleNums
	 * @return
	 */
	public List<Object[]> findMoneyBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo);

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
	 * 配送中心、调往分店汇总商品调出金额、毛利、数量
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByBranchAndItem(TransferProfitQuery transferProfitQuery);

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
	 * 总合计
	 * @param systemBookCode
	 * @param transferBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @param itemNums
	 * @return
	 */

	public Object[] findProfitSum(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);

	/**
	 * 已审核的调出单
	 * @param outBranchNum
	 * @param branchNum
	 * @param trim
	 * @param dateFrom
	 * @param dateTo
	 * @param unIn
	 * @param isSend
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TransferOutOrder> findAudit(String systemBookCode, Integer outBranchNum, Integer branchNum, String trim, Date dateFrom,
                                            Date dateTo, Boolean unIn, Boolean isSend, Integer offset, Integer limit);



	/**
	 * 已审核的调出单数量
	 * @param outBranchNum
	 * @param branchNum
	 * @param trim
	 * @param dateFrom
	 * @param dateTo
	 * @param unIn
	 * @param isSend
	 * @return
	 */
	public int countAudit(String systemBookCode, Integer outBranchNum, Integer branchNum, String trim, Date dateFrom,
                          Date dateTo, Boolean unIn, Boolean isSend);

	/**
	 * 已调出审核未结算金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer centerBranchNum);

	/**
	 ** 中心应收单
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param expire
	 * @return
	 */
	public List<TransferOutOrder> findCenterOrder(String systemBookCode, Integer branchNum, Integer centerBranchNum,
                                                  Date dateFrom, Date dateTo, boolean expire);

	/**
	 **配送单价价格带 商品项数、调出数量、调出金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param priceFrom
	 * @param priceTo
	 * @return
	 */
	public Object[] findPosItemBand(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, BigDecimal priceFrom, BigDecimal priceTo, List<String> categoryCodes);

	/**
	 * 调出商品明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemDetail(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

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
	 * 按门店、商品和日期汇总 销售金额 毛利 数量
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemSupplierDate(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

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
	 * 查询未审核的调出单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int findNoAudit(String systemBookCode, Integer branchNum);

	/**
	 * 查询待配送的调出单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int findOut(String systemBookCode, Integer branchNum);

	/**
	 * 查询待发货的调出单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int findPurchase(String systemBookCode, Integer branchNum);

	/**
	 * 查询待结算的调出单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int findSettlement(String systemBookCode, Integer branchNum);

	/**
	 * 调出商品数量
	 * @param systemBookCode
	 * @param outBranchNums
	 * @param inBranchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemQty(String systemBookCode, List<Integer> outBranchNums, List<Integer> inBranchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 待审核的调出量
	 * @param itemNum
	 * @param itemMatrixNum
	 * @param requestOrderFid
	 * @return
	 */
	public BigDecimal getItemToOutOrderQty(Integer itemNum, Integer itemMatrixNum, String requestOrderFid);

	/**
	 * 门店汇总 应付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer branchNum,
                                                Date dateFrom, Date dateTo, List<Integer> branchNums);

	/**
	 * 已配送过的商品
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findHasOutItems(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo);

	public void update(TransferOutOrder transferOutOrder);

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
     * 根据加盟店汇总未付金额
     * @param systemBookCode
     * @param branchNum
     * @param supplierNums
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 查询需结算的单据
     * @param systemBookCode
     * @param outBranchNum
     * @param branchNum
     * @return
     */
    public List<TransferOutOrder> findDue(String systemBookCode, Integer outBranchNum, Integer branchNum);

    /**
     * 年|月 配送统计（柱状图、Grid表）
     * @param systemBookCode
     * @param branchNums
     * @param centerbranchNum
     * @param dateType
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findOutSummary(String systemBookCode, List<Integer> branchNums,
                                         Integer centerbranchNum, String dateType, Date dateFrom, Date dateTo);

	/**
	 * 查询未上传单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo
	 * @param dateFrom
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<TransferOutOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset,
                                                    int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param outOrderFids
	 */
	public void updateTransferFlag(String systemBookCode, Integer branchNum, List<String> outOrderFids);

	/**
     * 查询未上传单据数量
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public int countUnUploadAudit(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 按日汇总商品调出数量 调出金额 最低配送价 最高配送价
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param branchNum 调入分店
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param order
	 * @param limit
	 * @return
	 */
	public List<Object[]> findItemSummaryByDay(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, Integer itemNum, Integer limit, String order);

	public List<Object[]> findRequestOrderFids(List<String> outOrderFids);

	/**
	 * 批量删除
	 * @param systemBookCode
	 * @param limitDate
	 * @param batchSize
	 * @return
	 */
	public Integer batchDelete(String systemBookCode, Date limitDate, int batchSize, Boolean keeyUnSettle);

	/**
	 * 按单号查询商品汇总数据
	 * @param systemBookCode
	 * @param outOrderFids
	 * @return
	 */
	public List<Object[]> findItemSummaryByFids(String systemBookCode, List<String> outOrderFids);

	/**
	 * 按商品、调入分店、调出日期（按天） 汇总 数量、成本、调出金额、销售金额
	 * @param systemBookCode
	 * @param outBranchNums 调出分店列表
	 * @param branchNums 调入分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByItemInBranchDay(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);

	public List<OutOrderDetail> findDetailsByRef(String orderTaskDetailFid);

	/**
	 * 按营业日 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findDateSummary(String systemBookCode, Integer centerBranchNum,
                                          List<Integer> branchNums, Date dateFrom, Date dateTo);

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
	 * 按营业日 商品 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findDateItemSummary(String systemBookCode, Integer centerBranchNum,
                                              List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

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
	 * 查询审核的单据
	 * @param systemBookCode
	 * @param inBranchNum 调入分店
	 * @param outBranchNum 调出分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<TransferOutOrder> findAudit(String systemBookCode, Integer inBranchNum, Integer outBranchNum, Date dateFrom,
                                            Date dateTo);

	/**
	 * 查询审核的审核员不是管理员的单据
	 * @param systemBookCode
	 * @param inBranchNum
	 * @param outBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<TransferOutOrder> findWmsAudit(String systemBookCode, Integer inBranchNum, Integer outBranchNum, Date dateFrom,
                                               Date dateTo);

	/**
	 * 按商品和批次号查询审核时间之后的所有明细
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param storehouseNum
	 * @param itemNum
	 * @param outOrderDetailLotNumber
	 * @param outOrderAuditTime 审核时间起
	 * @return
	 */
	public List<OutOrderDetail> findDetailsByItemLot(String systemBookCode, Integer outBranchNum, Integer storehouseNum,
                                                     Integer itemNum, String outOrderDetailLotNumber, Date outOrderAuditTime);

	/**
	 * 按收货门店汇总 明细金额 明细常用数量
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSummaryDetail(String systemBookCode, Integer outBranchNum, List<Integer> branchNums,
                                                  Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 应收账款数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
    public Integer countDueOrder(String systemBookCode, Integer branchNum);

	public void flush();

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
	 * 查询超期未补货商品
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNum
	 * @return
	 */
	public List<PosItem> findExpiredUnTransferItems(String systemBookCode, Integer outBranchNum, Integer branchNum);

	/**
	 * 根据要货单号查询调出单号
	 * @param requestOrderFid
	 * @return
	 */
	public List<String> findFidsByRequestOrderFid(String requestOrderFid);

	/**
	 * 根据主键列表查询
	 * @param outOrderFids
	 * @return
	 */
	public List<TransferOutOrder> findByIds(List<String> outOrderFids);

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

	public TransferOutOrder readByRefInBillNo(String systemBookCode, String outOrderRefInBillNo);

	public void updateAllNotDelete(TransferOutOrder transferOutOrder);

	public List<Object[]> findProfitGroupByBranch(TransferProfitQuery transferProfitQuery);

	/**
	 * 按商品、多特性、批次号汇总制单状态单据的调出量
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @param itemNums
	 * @param dateFrom
	 * @param dateTo
	 * @param exceptOrderFid
	 * @return
	 */
	public List<Object[]> findItemMatrixLotUnAuditQty(String systemBookCode, Integer branchNum, Integer storehouseNum, List<Integer> itemNums, Date dateFrom, Date dateTo, String exceptOrderFid);

	/**
	 * 按商品主供应商查询调出信息
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @param itemNums
	 * @param onlyLianYingItemShow
	 * @return
	 */
	public List<Object[]> findProfitGroupByItemSupplier(String systemBookCode, List<Integer> transferBranchNums,
                                                        List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList,
                                                        List<Integer> itemNums);

	/**
	 * 按商品主供应商查询调出单明细信息
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> transferBranchNums,
                                                 List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按主供应商查询调出信息
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findProfitGroupBySupplier(String systemBookCode, List<Integer> transferBranchNums,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList,
                                                    List<Integer> itemNums);

	/**
	 * 按主供应商日期查询调出信息
	 * @param systemBookCode
	 * @param transferBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyGroupBySupplierDate(String systemBookCode, List<Integer> transferBranchNums,
                                                       List<Integer> branchNums, Date dateFrom, Date dateTo);

	public void updateDetail(OutOrderDetail outOrderDetail);

	/**
	 * 按类别代码、类别名称汇总调出金额、毛利、数量
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByCategory(TransferProfitQuery transferProfitQuery);

	/**
	 * 读取状态
	 * @param outOrderFid
	 * @return
	 */
	public State readState(String outOrderFid);

	/**
	 * 查询配送过的商品编码
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findTransferedItems(String systemBookCode, Integer branchNum, Integer outBranchNum);

	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums);

	public List<Object[]> findTransferBranchQuery(String systemBookCode, List<Integer> inBranchNums, List<Integer> outBranchNums, List<Integer> itemNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<TransferOutOrder> findLossTransferOutOrders();


	/**
	 * 按商品汇总数量
	 * @param requestOrderFids
	 * @param audit 是否审核
	 * @return
	 */
	public List<Object[]> findItemSummary(List<String> requestOrderFids, Boolean audit);

	public List<TransferOutOrder> findTmallOrders(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<TransferOutOrder> findAuditByFids(String systemBookCode, List<String> outOrderFids);

	/**
	 * 按调入分店、商品汇总未调入数量和金额
	 * @param systemBookCode
	 * @param centerBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findUnInBranchItemSummary(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums,
                                                    Date dateFrom, Date dateTo, List<Integer> itemNums);

	public List<Integer> findItemNums(String outOrderFid);

	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

}
