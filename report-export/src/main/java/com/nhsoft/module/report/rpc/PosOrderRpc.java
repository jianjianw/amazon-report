package com.nhsoft.module.report.rpc;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.BranchDailyDirect;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.report.dto.*;
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
     *	bi 分店日销售汇总表
     * */
    public List<BranchDaily> findBranchDailySummary(String systemBookCode, Date dateFrom, Date dateTo);

    /**
     * bi  商品日时段销售汇总
     * */
    public List<ItemDailyDetail> findItemDailyDetailSummary(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums);

    /**
     *  bi  商品日销售汇总
     **/
    public List<ItemSaleDailyDTO> findItemSaleDailySummary(String systemBookCode, Date dateFrom, Date dateTo);




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

}
