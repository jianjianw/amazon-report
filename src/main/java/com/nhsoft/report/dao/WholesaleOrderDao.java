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

	/**
	 * 首页提醒查看 超期未收款单据数量 金额
	 * @param systemBookCode
	 * @param centerBranchNum 
	 * @param branchNum
	 * @param outBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param expire
	 * @return
	 */
	public OutOrderPaying readOutOrderPayingData(String systemBookCode,
												 Integer centerBranchNum, String clientFid, Date dateFrom, Date dateTo, Boolean expire);

	/**
	 ** 中心应收单
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param centerBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param expire
	 * @return
	 */
	public List<WholesaleOrder> findCenterOrder(String systemBookCode, Integer centerBranchNum, String clientFid,
                                                Date dateFrom, Date dateTo, boolean expire);

	public WholesaleOrder read(String fid);

	/**
	 **批发单价价格带 商品项数、批发数量、批发金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param priceFrom
	 * @param priceTo
	 * @return
	 */
	public Object[] findPosItemBand(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, BigDecimal priceFrom, BigDecimal priceTo, List<String> categoryCodes, List<Integer> regionNums);

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
	 *  批发销售明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemDetail(String systemBookCode,
                                         Integer branchNum, Date dateFrom,
                                         Date dateTo, List<Integer> regionNums);

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
	 * 查询满足条件的批发销售数量
	 * @param cilentFid 批发客户，为null则查询所有用户帐套下的批发客户
	 * @param dateFrom 查询批发销售单制单起始时间，取本日最小值
	 * @param dateTo 查询批发销售单制单截止时间，取本日最大值
	 * @param states 批发销售单状态，为空则查询所有状态
	 * @return
	 */
	public int countByCilentAndStates(String systemBookCode, String cilentFid, Date dateFrom, Date dateTo, List<State> states);

	/**
	 * 查询满足条件的批发销售单，按批发销售制单时间排序
	 * @param cilentFid 批发客户，为null则查询所有用户帐套下的批发客户
	 * @param dateFrom 查询批发销售单制单起始时间，取本日最小值
	 * @param dateTo 查询批发销售单制单截止时间，取本日最大值
	 * @param states 批发销售单状态，为空则查询所有状态
	 * @param offset 分页查询起始位
	 * @param limit  分页查询页大小
	 * @return
	 */
	public List<WholesaleOrder> findByCilentAndStates(String systemBookCode, String cilentFid, Date dateFrom, Date dateTo, List<State> states, int offset, int limit);

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

	public void saveAll(WholesaleOrder wholesaleOrder);

	public void updateAll(WholesaleOrder wholesaleOrder);

	public void delete(WholesaleOrder wholesaleOrder);

	public List<WholesaleOrder> findByOrderQueryCondition(String systemBookCode, Integer branchNum, OrderQueryCondition conditionData, int offset, int limit);

	public Object[] sumByOrderQueryCondition(String systemBookCode, Integer branchNum, OrderQueryCondition conditionData);

	/**
	 * 按销售员汇总提成
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param Employname
	 * @return
	 */
	public List<Object[]> findCommissonBySeller(String systemBookCode, Integer branchNum, Date dateFrom,
                                                Date dateTo, String Employname, List<Integer> regionNums);

	/**
	 * 提成明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param Employname
	 * @return
	 */
	public List<Object[]> findCommissonDetails(String systemBookCode, Integer branchNum, Date dateFrom,
                                               Date dateTo, String Employname, List<Integer> regionNums);

	/**
	 * 提成汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param Employname
	 * @return
	 */
	public Object[] sumCommisson(String systemBookCode, Integer branchNum, Date dateFrom,
                                 Date dateTo, String Employname, List<Integer> regionNums);

	public List<WholesaleOrder> findAudit(String systemBookCode, Integer branchNum, String fidOrder,
                                          String clientFid, Date dateFrom, Date dateTo, Boolean hasReturn, boolean isSend, List<Integer> regionNums);

	public List<Object[]> findMoneyByClients(String systemBookCode, List<Integer> branchNums,
                                             Date dateFrom, Date dateTo, List<String> clientFids, List<Integer> regionNums);

	public List<WholesaleOrder> findByPaymentDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String clientFid, List<Integer> regionNums);

	/**
	 * 按客户 商品汇总  批发数量 批发金额 批发成本 零售金额
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClientItemNum(WholesaleProfitQuery wholesaleProfitQuery);

	public void update(WholesaleOrder wholesaleOrder);

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
     * 根据客户汇总未付金额
     * @param systemBookCode
     * @param branchNum
     * @param supplierNums
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<String> clientFid, Date dateFrom, Date dateTo, List<Integer> regionNums);

    /**
     * 按客户汇总未付金额
     * @param systemBookCode
     * @param branchNums
     * @param clientFids
     * @param dateFrom
     * @param dateTo
     * @param regionNums
     * @return
     */
	public List<Object[]> findClientDueMoney(String systemBookCode, List<Integer> branchNums, List<String> clientFids,
                                             Date dateFrom, Date dateTo, List<Integer> regionNums);

	/**
	 * 按门店汇总未付金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param clientFids
	 * @param dateFrom
	 * @param dateTo
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findBranchDueMoney(String systemBookCode, List<Integer> branchNums, List<String> clientFids,
                                             Date dateFrom, Date dateTo, List<Integer> regionNums);

    /**
     * 查询未结算金额
     * @param systemBookCode
     * @param branchNum
     * @param clientFid
     * @return
     */
    public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, String clientFid, List<Integer> regionNums);


    /**
     * 查询需结算的单据
     * @param systemBookCode
     * @param branchNum
     * @param clientFid
     * @return
     */
    public List<WholesaleOrder> findDue(String systemBookCode, List<Integer> branchNums, String clientFid, List<Integer> regionNums);

    /**
     * 查询未审核的商品销售量
     * @param systemBookCode
     * @param branchNum
     * @param itemNum
     * @param itemMatrixNum
     * @param wholesaleBookFid
     * @return
     */
	public Object[] getUnAuditQty(String systemBookCode, Integer branchNum,
                                  Integer itemNum, Integer itemMatrixNum, String wholesaleBookFid);

	/**
	 * 查询未审核的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countUnAudit(String systemBookCode, Integer branchNum, List<Integer> regionNums);

	/**
	 * 查询待配货的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countUnPick(String systemBookCode, Integer branchNum, List<Integer> regionNums);

	/**
	 * 查询待发货的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countUnSend(String systemBookCode, Integer branchNum, List<Integer> regionNums);

	/**
	 * 查询待结算的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public int countToSettlement(String systemBookCode, Integer branchNum, List<Integer> regionNums);
	/**
	 * 已销售过的商品
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findHasSaleItems(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo);


	public List<Object[]> findItemMatrixSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo,
                                            List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);

	/**
	 * 根据订货单查询销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @param wholesaleBookFid
	 * @return
	 */
	public List<WholesaleOrder> findByBookFid(String systemBookCode, Integer branchNum, String wholesaleBookFid);

	public List<Object[]> findOrderSummary(String systemBookCode, List<Integer> branchNums,
                                           List<String> clientFids, String dateType, Date dateFrom, Date dateTo, List<Integer> regionNums);

	public List<Object[]> findMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> itemCategories,
                                                 List<Integer> itemNums, List<String> clients, List<Integer> regionNums, Integer storehouseNum, String auditor, String dateType, List<String> sellers);

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
	public List<WholesaleOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset,
                                                  int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param orderFids
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
	 * 按客户商品汇总数据
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
	public List<Object[]> findClientItemLastTime(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo,
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
	 * 按分店汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findMoneyByBranchs(String systemBookCode, List<Integer> branchNums,
                                             Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 按日汇总商品批发销售数量 销售金额 最低销售价 最高销售价
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param order
	 * @param limit
	 * @return
	 */
	public List<Object[]> findItemSummaryByDay(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer itemNum, Integer limit, String order);

	/**
	 * 根据订单号查询审核的销售单
	 * @param systemBookCode
	 * @param wholesaleBookFids
	 * @return
	 */
	public List<WholesaleOrder> findByBookFids(String systemBookCode, List<String> wholesaleBookFids);

	/**
	 * 查询客户已销售过的商品ID
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @return
	 */
	public List<Integer> findClientSaledItemNums(String systemBookCode, Integer branchNum, String clientFid);

	/**
	 * 修改客户
	 * @param systemBookCode
	 * @param clientFid
	 * @param remainClientFid
	 */
	public void updateClientFid(String systemBookCode, String oldClientFid, String newClientFid);

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
	 * 应收帐款数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Integer countDueOrder(String systemBookCode, Integer branchNum);

	/**
	 * 查询审核的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<WholesaleOrder> findAudit(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo);

	/**
	 * 查询审核的审核员不是管理员的销售单
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<WholesaleOrder> findWmsAudit(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo);

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
	 * 按批发订单号查询制单状态的批发销售单号
	 * @param systemBookCode
	 * @param wholesaleBookFid
	 * @return
	 */
	public List<String> findCreateOrderFids(String systemBookCode, String wholesaleBookFid);

	/**
	 * 按客户和日期汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param clientFids
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @param categoryCodes
	 * @param queryDateType
	 * @return
	 */
	public List<Object[]> findClientDateSummary(String systemBookCode, List<Integer> branchNums, List<String> clientFids,
                                                Date dateFrom, Date dateTo, String dateType, List<String> categoryCodes, String queryDateType);

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

	public List<Object[]> findItemSupplierDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                      List<Integer> regionNums);

	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                              List<Integer> itemNums, List<Integer> regionNums);

	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                    List<Integer> itemNums, List<Integer> regionNums);

	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                 List<Integer> regionNums);

	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes,
                                          List<Integer> itemNums, List<Integer> regionNums);

	public List<Object[]> findSupplierDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                  List<Integer> regionNums);

	public void updateDetail(WholesaleOrderDetail wholesaleOrderDetail);

	/**
	 * 查询门店营业信息
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFro
	 * @param dateTo
	 * @return
	 */
	public Object[] findClientMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据客户汇总批发金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByClient(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据销售员汇总批发金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyBySeller(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据商品汇总批发金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按客户汇总金额和税额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findClientSummaryTax(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                               Date dateTo);

	public List<WholesaleOrder> find(String systemBookCode, List<Integer> branchNums, String query, Date dateFrom, Date dateTo);

	public List<WholesaleOrder> findLossWholesaleOrders();
}
