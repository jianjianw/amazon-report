package com.nhsoft.report.dao;


import com.nhsoft.report.dto.ItemQueryDTO;
import com.nhsoft.report.model.*;
import com.nhsoft.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.report.shared.queryBuilder.PolicyAllowPriftQuery;
import com.nhsoft.report.shared.queryBuilder.PosOrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PosOrderDao {

	public PosOrder read(String orderNo);

	public BigDecimal findUnClearData(String systemBookCode, Integer branchNum, String clientFid);
	
	public PosOrder readByTicketUUid(String systemBookCode, String ticketSendDetailUuid);
	
	/**
	 **销售单价价格带 商品项数、调出数量、调出金额
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param priceFrom
	 * @param priceTo
	 * @return
	 */
	public Object[] findPosItemBand(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, BigDecimal priceFrom, BigDecimal priceTo, List<String> categoryCodes);
	
	/**
	 * 按商品、多特性编码汇总销售数量 销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit 组合商品按成分统计
	 * @return
	 */
	public List<Object[]> findItemMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit);
	
	/**
	 * 按门店汇总金额 折扣金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按年 月 日汇总门店销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findBranchSumByDateType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
	
	/**
	 * 商品销售明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit 组合商品按成分统计 
	 * @return
	 */
	public List<Object[]> findOrderDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                          List<String> categoryCodes, boolean queryKit);

	/**
	 * 门店 商品汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit 组合商品是否按明细查询
	 * @param itemNums
	 * @return
	 */

	public List<Object[]> findItemSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 *  零售金额 成本金额 毛利 总合计
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @return
	 */
	public Object[] readDetailSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes);

	/**
	 * 按本店 商品 营业日汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @param categoryCodes
	 * @param queryKit 组合商品按明细统计
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchAndItemAndBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit);

	public PosOrder readByPaymentNo(String paymentNo);

	/**
	 * 根据商品和月份汇总 销售金额和成本
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * ABC 分析 商品汇总  赠送状态金额为0 退货状态减去
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findAbcItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList);

	/**
	 * ABC 分析 商品汇总  赠送状态金额为0 退货状态减去 不按ItemMatrix区分
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findAbcItemSumV2(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList);


	/**
	 * 让利明细
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<Object[]> findPromotionDetails(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 让利按商品汇总
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<Object[]> findPromotionItems(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 根据班次查询
	 * @param shiftTables
	 * @return
	 */
	public List<PosOrder> findByShiftTables(List<ShiftTable> shiftTables);

    /**
     * 根据班次查询汇总数据
     * @param shiftTables
     * @return
     */
    public Object[] sumByByShiftTables(List<ShiftTable> shiftTables);

    /**
     * 根据班次查询明细汇总数据
     * @param shiftTables
     * @param detailStateCode
     * @return
     */
    public Object[] sumDetailByShiftTables(List<ShiftTable> shiftTables, Integer detailStateCode);

    /**
     * 根据班次统计消费券合计
     * @param shiftTables
     * @return
     */
    public Object[] sumTicketByShiftTables(List<ShiftTable> shiftTables);

    /**
     * 根据支付方式汇总金额
     * @param shiftTables
     * @return
     */
    public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);

    /**
     * 营业折扣汇总
     * @param systemBookCode
     * @param dtFrom
     * @param dtTo
     * @param branchNums
     * @return
     */
    public Object[] sumBusiDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

    /**
     * 营业折扣明细
     * @param systemBookCode
     * @param dtFrom
     * @param dtTo
     * @param branchNums
     * @return
     */
    public List<Object[]> findClientDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

    /**
     * 营业折扣明细
     * @param systemBookCode
     * @param dtFrom
     * @param dtTo
     * @param branchNums
     * @return
     */
    public List<Object[]> findMgrDiscountAnalysisAmountAndMoney(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

    /**
     * 营业折扣汇总 根据分店
     * @param systemBookCode
     * @param dtFrom
     * @param dtTo
     * @param branchNums
     * @return
     */
    public List<Object[]> findBusiDiscountAnalysisBranchs(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

    /**
     * 营业折扣汇总 根据营业日
     * @param systemBookCode
     * @param dtFrom
     * @param dtTo
     * @param branchNums
     * @return
     */
    public List<Object[]> findBusiDiscountAnalysisDays(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

    /**
     * 已结账单汇总
     * @param systemBookCode
     * @param branchNums
     * @param dtFrom
     * @param dtTo
     * @param orderNo
     * @param saleType
     * @param orderOperator  收银员
     * @param orderState
     * @param clientFid
     * @param orderExternalNo
     * @return
     */
    public Object[] sumSettled(PosOrderQuery posOrderQuery);

    /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
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
    public void deleteByDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

    /**
     * 取最小单据日期
     * @param systemBookCode
     * @param branchNum
     * @return
     */
    public Date getFirstDate(String systemBookCode, Integer branchNum);

    public List<Payment> findBySettlement(String systemBookCode, Integer branchNum, Date dateTo, int offset, int limit, String sortField, String sortType);

    public Object[] sumBySettlement(String systemBookCode, Integer branchNum, Date dateTo);

    /**
     * 根据门店汇总 单据数 单据总额 折扣总额 积分总额
     * @param cardReportQuery
     * @return
     */
    public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery);

    /**
     * 根据表面卡号 姓名 卡类型汇总 单据数 单据总额 折扣总额 积分总额
     * @param cardReportQuery
     * @return
     */
    public List<Object[]> findSummaryByPrintNum(CardReportQuery cardReportQuery);

    /**
     * 查询单据
     * @param cardReportQuery
     * @return
     */
    public List<PosOrder> findByCardReportQuery(CardReportQuery cardReportQuery);

    /**
     * 查询未扣款记录
     * @param cardReportQuery
     * @return
     */
    public List<Object[]> findUnPayByCardReportQuery(CardReportQuery cardReportQuery);

    /**
     * 查询付款明细
     * @param cardReportQuery
     * @return
     */
    public List<Object[]> findOrderDetailsByCardReportQuery(CardReportQuery cardReportQuery);


    /**
     * 查询前台现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getPosCash(String systemBookCode,
                                 List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按门店汇总前台现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findPosCashGroupByBranch(String systemBookCode,
                                                   List<Integer> branchNums, Date dateFrom, Date dateTo);

	public void save(PosOrder posOrder);

	public void saveDetails(List<PosOrderDetail> posOrderDetails);

	/**
	 * 根据关联单据查询
	 * @param refBillno
	 * @return
	 */
	public List<PosOrder> findByRefBillno(String systemBookCode, Integer branchNum, String refBillno);

	public void update(PosOrder posOrder);

	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                      Date dateTo, List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType);

	/**
	 * 按分店汇总明细金额 数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchDetailSum(String systemBookCode,
                                              List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, boolean queryKit);

	/**
	 * 查询会员卡消费金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getCardPayedMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查询有营业数据产生的分店数量
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public int countActiveBranchs(String systemBookCode, Date dateFrom, Date dateTo);


	/**
	 * 按班次查询POS单据 包含明细
	 * @param shiftTable
	 * @return
	 */
	public List<PosOrder> findByShiftTable(ShiftTable shiftTable);

	/**
	 * 按月、门店汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchSumByMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店 商品 多特性 汇总 销售金额 数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 查询客户签单记录
	 * @param systemBookCode
	 * @param branchNums
	 * @param clientFid
	 * @return
	 */
	public List<Payment> findClientSignPayments(String systemBookCode, List<Integer> branchNums, String clientFid);

	public Payment readPayment(String paymentNo);

	public void updatePayment(Payment payment);

	/**
	 * 按客户汇总签单金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findClientsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 按客户汇总欠款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findClientDueMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 查询客户欠款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFid
	 * @return
	 */
	public BigDecimal getClientDueMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String clientFid);

	/**
	 * 按分店汇总欠款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findBranchDueMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 按分店汇总客户签单金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Object[]> findClientsMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 查询客户签单记录
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param clientFids
	 * @return
	 */
	public List<Payment> findPaymentByClient(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);

	/**
	 * 按客户汇总签单余额
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSignBalanceGroupByClient(String systemBookCode, Integer branchNum, List<String> clientFid, Date dateFrom, Date dateTo);

	/**
	 * 批量删除指定日期之前的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param date
	 * @param batchSize
	 * @return
	 */
    public int batchDelete(String systemBookCode, Integer branchNum, Date date, int batchSize);

	/**
	 * 按日汇总商品销售数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param orderType
	 * @param limitCount
	 * @return
	 */
	public List<Object[]> findItemSummaryByDay(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer itemNum, Integer limitCount, String orderType);


	public List<PosOrderDetail> findDetails(String orderNo);

	/**
	 * 根据客户汇总销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemCategories
	 * @param itemNums
	 * @param clients
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClient(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                 List<String> itemCategories, List<Integer> itemNums, List<String> clientFids);

	/**
	 * 按分店汇总支付金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param paymentType
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                            String paymentType);

	/**
	 * 按消费群体 汇总 笔数 单据金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param categoryCodes
	 * @param customerTypes
	 * @return
	 */
	public List<Object[]> findGroupSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                           Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<String> customerTypes);

	/**
	 * 按消费群体 商品汇总 商品消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param customerTypes
	 * @return
	 */
	public List<Object[]> findGroupItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                               Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<String> customerTypes);

	/**
	 * 修改客户
	 * @param systemBookCode
	 * @param clientFid
	 * @param remainClientFid
	 */
	public void updateClientFid(String systemBookCode, String oldClientFid, String newClientFid);

	/**
	 * 按营业日 商品 汇总 销售金额
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBizItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                             Date dateTo, List<Integer> itemNums);


	/**
	 * 按分店汇总 销售金额 销售毛利  四舍五入 经理折扣 总消费券面值 消费券折扣
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                            Date dateTo);

	/**
	 * 按商品主键汇总销售数量 销售金额 成本金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细统计
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                      List<Integer> itemNums, boolean queryKit);

	public List<Object[]> findItemBranchInfo(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                             List<Integer> itemNums, boolean queryKit);

	/**
	 * 查询主键最大值的记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday 营业日
	 * @param orderMachine 终端标识号
	 * @return
	 */
	public PosOrder getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String orderMachine);

	/**
	 * 查询payment主键最大值的记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday 营业日
	 * @param shiftTableNums 班次号列表
	 * @return
	 */
	public Payment getPaymentMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, List<Integer> shiftTableNums);


	/**
	 * 按分店  营业日 商品 汇总 销售金额
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBranchBizItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                   Date dateTo, List<Integer> itemNums);

	/**
	 * 查询单据号 付款名称 金额
	 * @param orderNos
	 * @return
	 */
	public List<Object[]> findOrderPaymentMoneys(List<String> orderNos);

	/**
	 * 查询上传账单明细对应的数据库明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param posOrderList
	 * @return
	 */
	public List<PosOrder> findByOrderNos(List<String> orderNos);

	/**
	 * 查询上传支付明细对应的数据库明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param paymentList
	 * @return
	 */
	public List<Payment> findPaymentsByPaymentNos(String systemBookCode, Integer branchNum, List<String> paymentNoList);

	public void delete(PosOrder posOrder);

	/**
	 * 查询反结账单据数量和金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Object[] findRepayCountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public void flush();

	/**
	 * 按营业日汇总payment 记录数 金额
	 * @param shiftTable
	 * @return
	 */
	public Object[] sumPaymentByShiftTable(ShiftTable shiftTable);

	/**
	 * 查询会员消费记录
	 * @param systemBookCode
	 * @param cardUserNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<PosOrder> findByCard(String systemBookCode, Integer cardUserNum, Date dateFrom, Date dateTo);

	/**
	 * 按门店 商品 汇总 数量  销售金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 是否按组合商品查询
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                List<Integer> itemNums, boolean queryKit);

	public Object[] findBusiDiscountAnalysisSum(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums);

	/**
	 * 按月、分店汇总 消费次数大于consumeCount的会员数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param consumeCount
	 * @return
	 */
	public List<Object[]> findMonthBranchCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer consumeCount);

	/**
	 * 查询未更新冗余字段的记录
	 * @param batchSize
	 * @return
	 */
	public List<PosOrderKitDetail> findUnUpdateKitDetail(int batchSize);

	public PosOrderDetail readDetail(PosOrderDetailId id);

	public void updateDetail(PosOrderDetail posOrderDetail);

	public void updateKitDetail(PosOrderKitDetail posOrderKitDetail);

	public List<PosOrderDetail> findDetails(List<String> orderNos);

	public List<PosOrder> findSettled(PosOrderQuery posOrderQuery, int offset, int limit);

    /**
     * 按商品汇总数量 金额
     * @param cardReportQuery
     * @return
     */
    public List<Object[]> findItemSummaryByCardReportQuery(CardReportQuery cardReportQuery);

    /**
     * 查询反结账明细
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findRepayDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按支付方式查询未付金额
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @param paymentType
     * @return
     */
    public BigDecimal getPaymentTypeUnPayMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String paymentType);

	public PosOrder readWithLock(String orderNo);

	/**
	 * 按营业月 商品 汇总 销售金额 销售数量
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                  Date dateTo, List<Integer> itemNums);

	/**
	 * 按分店商品供应商营业日查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchAndItemAndSupplierAndBizday(String systemBookCode, List<Integer> branchNums,
                                                                            Date dateFrom, Date dateTo, boolean queryKit);

	/**
	 * 按商品供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                    Date dateTo, boolean queryKit);

	/**
	 * 按商品分店供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                        Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 * 按供应商查询销售明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                      Date dateTo, List<String> categoryCodes, boolean queryKit);

	/**
	 * 按供应商汇总销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                    Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 * 按分店供应商营业日分组销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchAndSupplierAndBizday(String systemBookCode, List<Integer> branchNums,
                                                                     Date dateFrom, Date dateTo, boolean queryKit);

	/**
	 * 按供应商汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums,
                                          boolean queryKit);

	/**
	 * 按商品供应商汇总销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierInfoByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                         Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);

	/**
	 * 批量删除
	 * @param limitBizday
	 * @param batchSize
	 * @return
	 */
	public Integer batchDelete(String limitBizday, int batchSize);

	/**
	 * 按分店、支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchPaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                   Date dateTo);

	/**
	 * 按门店汇总金额 税额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findFinanceBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                               Date dateTo);

	/**
	 * 根据单据号查询部分属性
	 * @param orderNos
	 * @return
	 */
	public List<PosOrder> findShortByOrderNos(List<String> orderNos);

	/**
	 * 查询组合商品明细
	 * @param orderNo
	 * @param orderDetailNum
	 * @return
	 */
	public List<PosOrderKitDetail> findKitDetails(String orderNo, Integer orderDetailNum);
	
	/**
	 * 按第三方单据号查询销售单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param orderExternalNo
	 * @return
	 */
	public List<PosOrder> findOrderByExternalNo(String systemBookCode, Integer branchNum, String orderExternalNo);

	public List<Payment> findShiftUnPaidPayments(String systemBookCode, Integer branchNum);

	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery);

	public List<Object[]> findCustomReportByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	public List<Payment> findLossPayments();
	
	public List<Payment> findPaymentsByOrderNos(List<String> orderNos);

	public boolean checkPosOrderByRefBillNo(String systemBookCode, String orderRefBillNo);

	public List<Object[]> findRankByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int count, String sortField);

	public void savePayment(Payment payment);

	public List<String> findReSendIds(List<String> orderNos);

	public List<Object[]> findItemSaleSummaryDTO(String systemBookCode, Integer branchNum, List<Integer> itemNums, Date dateFrom, Date dateTo);

	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO);

	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO);

}
