package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.*;

import java.util.Date;
import java.util.List;

public interface MobileAppV2Rpc {

	/**
	 * 查询总部推荐商品
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param keyword 关键字
	 * @param rankFrom
	 * @param rankTo
	 * @return
	 */
	public List<PosItemShowDTO> findPosItemShowByPush(String systemBookCode, Integer centerBranchNum, Integer branchNum,
                                                      String keyword, Integer rankFrom, Integer rankTo);

	/**
	 * 查询门店新品提醒
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param rankFrom
	 * @param rankTo
	 * @return
	 */
	public List<PosItemShowDTO> findPosItemShowByNew(String systemBookCode, Integer centerBranchNum, Integer branchNum,
                                                     String keyword, Integer rankFrom, Integer rankTo);
	
	/**
	 * 按类别汇总新品提醒数量
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @return
	 */
	public List<TwoTypeAndTwoValue> findCategoryShowByNew(String systemBookCode, Integer centerBranchNum, Integer branchNum);
	
	/**
	 * 按类别查询新品提醒
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param categoryCode
	 * @param keyword
	 * @param rankFrom
	 * @param rankTo
	 * @return
	 */
	public List<PosItemShowDTO> findNewPosItemShowByCategory(String systemBookCode, Integer centerBranchNum, Integer branchNum,
                                                             String categoryCode, String keyword, Integer rankFrom, Integer rankTo);
	
	/**
	 * 门店往来账款
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public AccountPayDTO findAccountPays(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 门店往来账款明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom
	 * @param rankTo
	 * @return
	 */
	public List<AccountDetailDTO> findAccountDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                     Integer rankFrom, Integer rankTo);
	
//	/**
//	 * 门店健康报表
//	 * @param systemBookCode
//	 * @param branchNum
//	 * @param userCode
//	 * @param emailAddress
//	 * @param shopArea
//	 * @param peopleCount
//	 * @param year
//	 * @param month
//	 * @param shopRent
//	 * @return
//	 */
//	public List<BranchHealthDTO> sendHealthyEmail(String systemBookCode, Integer branchNum, String userCode,
//	                                              String emailAddress, BigDecimal shopArea, Integer peopleCount, Integer year, Integer month,
//	                                              BigDecimal shopRent);
	/**
	 * 查询要货单状态
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<RequestOrderDTO> findRequestOrders(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查询要货单明细
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param requestOrderFid
	 * @return
	 */
	public List<RequestOrderDetailDTO> findRequestOrderDetails(String systemBookCode, Integer centerBranchNum, String requestOrderFid);

	/**
	 * 查询首页营业汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusinessDTO getIndexMobileBusinessDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询首页营业汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusinessDTO getStallIndexMobileBusinessDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 查询异动信息
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询折扣分类明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findDiscountDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询折扣分类明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findStallDiscountDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 查询现金收入明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findCashDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询现金收入明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findStallCashDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 按支付方式汇总前台收入明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findPaymentDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按支付方式汇总前台收入明细
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<NameAndValueDTO> findStallPaymentDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 按支付方式汇总存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findCardDepositDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询商品排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom 查询起始位 
	 * @param rankTo 查询页大小 
	 * @param sortField 排序方式 amount or money
	 * @return
	 */
	public List<NameAndTwoValueDTO> findItemRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField);

    /**
     * 查询商品排行
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param rankFrom 查询起始位
     * @param rankTo 查询页大小
     * @param sortField 排序方式 amount or money
     * @return
     */
    public List<NameAndTwoValueDTO> findStallItemRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums,
                                                      Integer rankFrom, Integer rankTo, String sortField);

	/**
	 * 查询商品类别排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom 查询起始位 
	 * @param rankTo 查询页大小 
	 * @param sortField 排序方式 amount or money
	 * @return
	 */
	public List<NameAndTwoValueDTO> findCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortField);

	/**
	 * 查询时段营业数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusinessPeriodDTO getMobileBusinessPeriodDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询时段营业数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileBusinessPeriodDTO getStallMobileBusinessPeriodDTO(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 查询卡汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @return
	 */
	public MobileCardDTO getMobileCardDTO(String systemBookCode, List<Integer> branchNums);

	/**
	 * 查询门店发卡量排名  逆序
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findBranchCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询分店会员群体人数
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<NameAndValueDTO> findGroupCustomerCount(String systemBookCode, Integer branchNum);

	/**
	 * 查询会员模型
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<MobileCustomerModelDTO> findMobileCustomerModelDTOs(String systemBookCode, Integer branchNum);

	/**
	 * 查询门店卡消费排名  逆序
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findBranchCardConsume(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询门店卡存款排名  逆序
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findBranchCardDeposit(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询时间范围内卡汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public MobileCardDTO getMobileCardDTOByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 收藏分店
	 * @param appUserNum
	 * @param branchNum
	 */
	public void addUserBranchNum(String systemBookCode, Integer appUserNum, Integer branchNum);

	/**
	 * 取消收藏
	 * @param appUserNum
	 * @param branchNum
	 */
	public void deleteUserBranchNum(String systemBookCode, Integer appUserNum, Integer branchNum);

	/**
	 * 查询收藏分店
	 * @param appUserNum
	 * @return
	 */
	public List<Integer> findUserBranchNums(String systemBookCode, Integer appUserNum);

	/**
	 * 按门店汇总单品前台销售数量、金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndTwoValueDTO> findBranchPosSummary(String systemBookCode, List<Integer> branchNums, Integer itemNum, Date dateFrom, Date dateTo);

    /**
     * 按档口汇总单品前台销售数量、金额
     * @param systemBookCode
     * @param branchNums
     * @param itemNum
     * @param dateFrom
     * @param dateTo
     * @param stallNums
     * @return
     */
    public List<NameAndTwoValueDTO> findStallPosSummary(String systemBookCode, List<Integer> branchNums, Integer itemNum, Date dateFrom, Date dateTo, List<Integer> stallNums);

	/**
	 * 商品折让统计
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom
	 * @param rankTo
	 * @param sortType
	 * @return
	 */
	public List<SalesDiscountDTO> findSalesDiscount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer rankFrom, Integer rankTo, String sortType);
	
	/**
	 * 查询部门排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom 查询起始位
	 * @param rankTo 查询页大小
	 * @param sortField 排序方式 amount or money
	 * @return
	 */
	public List<NameAndTwoValueDTO> findDeptRank(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                 Date dateTo, Integer rankFrom, Integer rankTo, String sortField);


	/**
	 * 查询商品小类别排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param rankFrom 查询起始位
	 * @param rankTo 查询页大小
	 * @param sortField 排序方式 amount or money
	 * @return
	 */
	public List<NameAndTwoValueDTO> findSmallCategoryRank(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
														  Integer rankFrom, Integer rankTo, String sortField);

	/**
	 * 查询门店发卡量排名  逆序
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<NameAndValueDTO> findBranchCardCountV2(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public MobileBusinessDTO findMobileBusiness(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo);

	public List<MobileBusinessDetailDTO> findPaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
															Date dateTo);

	public List<MobileBusinessDetailDTO> findDepositSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
															Date dateTo);

	public List<NameAndValueDTO> findDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													 Date dateTo);

	public List<MobileBusinessDTO> findBusinessMoneyGroupByShop(String systemBookCode, List<Integer> branchNums,
																Date dateFrom, Date dateTo);

	public List<MobileBusinessDTO> findDiscountGroupByShop(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														   Date dateTo);

	public List<MobileBusinessDTO> findBusinessReceiptGroupByShop(String systemBookCode, List<Integer> branchNums,
																  Date dateFrom, Date dateTo);

	public List<MobileBusinessDTO> findBusinessReceiptGroupByStall(String systemBookCode, List<Integer> branchNums,
																  Date dateFrom, Date dateTo, List<Integer> stallNums);

	public List<MobileBusinessDetailDTO> findCashSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
																	Date dateFrom, Date dateTo, String cashType);

	public List<MobileBusinessDetailDTO> findDepositSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
																	   Date dateFrom, Date dateTo, String cashType);

	public List<MobileBusinessDetailDTO> findPaymentSummaryGroupByShop(String systemBookCode, List<Integer> branchNums,
																	   Date dateFrom, Date dateTo, String type);

	public List<MobileBusinessDetailDTO> findCashSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														 Date dateTo);

	/**
	 * 异动明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param infoType
	 * @return
	 */
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode,
												   Integer branchNum,
												   Date dateFrom, Date dateTo, String infoType);

	public List<ShipOrderDTO> findShipOrders(String systemBookCode,
											 Integer centerBranchNum,
											 Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 根据分店汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													  Date dateTo);


	/**
	 * 门店会员卡统计top10 (Bq 用)
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param sortField
	 * @param branchNum
	 * @return
	 */
	public List<CardReportDTO> findCardReportByBranchBq(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														Date dateTo, String sortField, Integer branchNum);
}
