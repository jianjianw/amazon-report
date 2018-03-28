package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;

import java.util.Date;
import java.util.List;

public interface PosOrderRpc  {

    /**
     * 按分店汇总营业额,客单量,毛利
     * @param systemBookCode
     * @param branchNums
     * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
     * @param dateFrom
     * @param dateTo
     * @param isMember 是否会员
     * @return
     *
     */
    public List<BranchRevenueReport> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);


    /**
     * 按营业日汇总 销售额 客单数
     * @param systemBookCode
     * @param branchNums
     * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
     * @param dateFrom
     * @param dateTo
     * @param isMember 是否会员
     * @return
     */
    public List<BranchBizRevenueSummary> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);

    /**
     * 按月份汇总 销售额 客单数 毛利
     * @param systemBookCode
     * @param branchNums
     * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
     * @param dateFrom
     * @param dateTo
     * @param isMember 是否会员
     * @return
     */
    public List<BranchBizRevenueSummary> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);


    public List<ItemSummary> findItemSum(String systemBookCode, ItemQueryDTO itemQueryDTO);


    public List<BranchItemSummaryDTO> findBranchItemSum(String systemBookCode, ItemQueryDTO itemQueryDTO);





    /**
     * 按分店汇总消费券
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchCouponSummary(String systemBookCode,
                                                  List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按分店汇总折扣金额
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchDiscountSummary(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo);
    /**
     * 按分店 营业日 汇总消费券
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchBizdayCouponSummary(String systemBookCode,
                                                        List<Integer> branchNums, Date dateFrom, Date dateTo);
    /**
     * 按分店 营业日 汇总折扣金额
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchBizdayDiscountSummary(String systemBookCode,
                                                          List<Integer> branchNums, Date dateFrom, Date dateTo);
    /**
     * 按分店 营业日 终端 汇总消费券
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findCouponSummary(String systemBookCode, List<Integer> branchNums,
                                            Date dateFrom, Date dateTo);
    /**
     * 按分店 班次 支付方式 汇总消费金额
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchShiftTablePaymentSummary(String systemBookCode, List<Integer> branchNums,
                                                             Date dateFrom, Date dateTo, String casher);
    /**
     * 按分店 班次 汇总消费券
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<BusinessCollection> findBranchShiftTableCouponSummary(String systemBookCode, List<Integer> branchNums,
                                                            Date dateFrom, Date dateTo, String casher);
    /**
     * 按分店 操作员 支付方式 汇总消费金额
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @param branchNums
     * @return
     */
    public List<PosReceiveDiffMoneySumDTO> findBranchOperatorPayByMoneySummary(String systemBookCode,
                                                              List<Integer> branchNums, Date dateFrom, Date dateTo);


    /**
     * 已结账单查询
     * @param posOrderQuery
     * @param offset 查询起始位
     * @param limit 查询数量
     * @return
     * */
    public List<PosOrderDTO> findSettled(String systemBookCode, PosOrderQuery posOrderQuery, int offset, int limit);

    public Object[] sumSettled(String systemBookCode, PosOrderQuery posOrderQuery);


    /**
     * 根据表面卡号 姓名 卡类型汇总 单据数 单据总额 折扣总额 积分总额
     * @param cardReportQuery
     * @return
     */
    public CardSummaryPageDTO findSummaryByPrintNum(CardReportQuery cardReportQuery);


    /**
     * 查询单据   分页查询
     * @param cardReportQuery
     * @return
     */
    public CardReportPageDTO findByCardReportQueryPage(CardReportQuery cardReportQuery);

}
