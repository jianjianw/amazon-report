package com.nhsoft.module.report.rpc;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.report.dto.*;

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

    /*
	*
	*  bi  商品日销售汇总
	* */
    public List<ItemDaily> findItemDailySummary(String systemBookCode, Date dateFrom, Date dateTo);
}
