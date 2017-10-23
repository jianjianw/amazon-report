package com.nhsoft.module.report.dao;

import com.nhsoft.phone.server.model.*;
import com.nhsoft.module.report.dto.MobileBusinessDTO;
import com.nhsoft.module.report.dto.MobileCardDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MobileAppDao {

	/**
	 * 汇总营业数据
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusiness findMobileAppBusiness(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
    
	/**
	 * 分店营业额
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopPaymentMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 分店营业收款 开单数 开单均价
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopReceipt(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 分店折扣金额
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopDiscountMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按支付方式汇总收款
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按门店汇总收款
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopPayment(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String paymentType);
	
	/**
	 * 商品销售排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCode
	 * @param var
	 * @param rankFrom
	 * @param rankTo
	 * @param sortField
	 * @return
	 */
	public List<MobileSalesRank> findProductRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String categoryCode, String var, Integer rankFrom, Integer rankTo, String sortField);
	
	/**
	 * 商品类别销售排行
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @return
	 */
	public List<MobileSalesRank> findCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes);
	
	/**
	 * 卡数据汇总
	 * @param branchNums
	 * @return
	 */
	public MobileCardSum readCardSum(String systemBookCode, List<Integer> branchNums);
	
	/**
	 * 查询会员总数 储值总额 消费总额 按门店汇总
	 * @param branchNums
	 * @return
	 */
	public List<MobileCardSum> findCardSums(String systemBookCode, List<Integer> branchNums);
	
	/**
	 * 会员经营指标汇总
	 * @param branchNums
	 * @return
	 */
	public MobileCardManage readCardManager(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按门店汇总会员经营指标
	 * @param branchNums
	 * @return
	 */
	public List<MobileCardManage> findShopCardManagers(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按帐套和门店查询时段分析（帐套或者一个门店24个时间段，按时间段返回）
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopTimeAnalysis(String systemBookCode,
	                                           List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按帐套、门店和时间段查询时段分析（每个门店一个时间段，按门店列表返回）
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param timeType
	 * @return
	 */
	public List<Object[]> findShopGroupReportsByTime(String systemBookCode,
	                                                 List<Integer> branchNums, Date dateFrom, Date dateTo,
	                                                 String timeType);

	/**
	 * 按终端统计查询门店一个时间段内的时段分析（将终端名称存到shopName）
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param timeType
	 * @return
	 */
	public List<Object[]> findShopGroupReportsByTerminal(String systemBookCode,
	                                                     List<Integer> branchNums, Date dateFrom, Date dateTo,
	                                                     String timeType);

	/**
	 * 按每个终端24个时段统计
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findTerminalTimeAnalysis(String systemBookCode,
	                                               List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询商品赠退折扣情况
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom
	 * @param rankTo
	 * @param sortType
	 * @return
	 */
	public List<SalesDiscount> findItemDiscount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom,
	                                            Integer rankTo, String sortType);

	/**
	 * 按消费券类型汇总 数量和金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findCouponTypeSummaryByDate(String systemBookCode, Integer branchNum, Date dateFrom,
	                                                  Date dateTo);
	
	/**
	 * 查询消费券金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public BigDecimal getCouponMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                 Date dateTo);

	/**
	 * 按门店汇总消费券金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findShopCouponMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                          Date dateTo);
	
	/**
	 * 新版手机查询用
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusinessDTO findMobileAppBusinessDTO(String systemBookCode, List<Integer> branchNums,
	                                                  Date dateFrom, Date dateTo);

	/**
	 * 新版老板查询卡数据汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @return
	 */
	public MobileCardDTO getMobileCardDTO(String systemBookCode, List<Integer> branchNums);


	/**
	 * 商品部门销售排行
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<MobileSalesRank> findDeptRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);




}
