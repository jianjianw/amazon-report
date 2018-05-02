package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReportRpc {
	
	
	/**
	 * 按分店汇总销售毛利 毛利= 本店收货- 本店销售
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBranch(SaleAnalysisQueryData saleAnalysisQueryData);
	
	/**
	 * 按分店、商品汇总销售毛利 毛利= 本店收货- 本店销售
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByItem(SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 按分店、类别汇总销售毛利 毛利= 本店收货- 本店销售
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBranchCategory(SaleAnalysisQueryData saleAnalysisQueryData);


	/**
	 * 按类别汇总销售毛利 毛利= 本店收货- 本店销售
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByCategory(SaleAnalysisQueryData saleAnalysisQueryData);
	
	/**
	 * 按营业月 商品 汇总 前台销售数量 金额
	 * @param orderQueryDTO
	 * @return
	 */
	public List<OrderDetailReportDTO> findbizmonthItemSummary(OrderQueryDTO orderQueryDTO);
	
	/**
	 * 按营业日汇总销售毛利 毛利= 本店收货- 本店销售
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBiz(SaleAnalysisQueryData saleAnalysisQueryData);
	
	/**
	 * 查询综合毛利报表
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<MultipleProfitReportDTO> findMultipleProfitReportDTOs(String systemBookCode, Date dateFrom, Date dateTo,
                                                                      List<String> categoryCodes, List<Integer> itemNums);
	
	/**
	 * 供应商销售分析 供应商汇总
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierComplexReportDTO> findSupplierSumDatas(SupplierSaleQuery supplierSaleQuery);
	
	/**
	 * 供应商销售分析 销售汇总
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierComplexReportDTO> findSupplierSaleSumDatas(SupplierSaleQuery supplierSaleQuery);
	
	/**
	 * 供应商销售分析 门店销售汇总
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierComplexReportDTO> findSupplierSaleGroupByBranchDatas(SupplierSaleQuery supplierSaleQuery);
	
	/**
	 * 供应商销售分析 销售明细
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierComplexReportDetailDTO> findSupplierSaleDetailDatas(SupplierSaleQuery supplierSaleQuery);

	/**
	 * 日销售报表 按分店、营业日汇总营业额
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param type type 0营业额 1客单量2客单价3会员客单量4会员客单价5毛利6平均毛利率   add 7 会员销售额 8 会员毛利
	 * @return
	 */
	public List<BranchDayReport> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type);

	/**
	 * 月销售报表 按分店、营业月汇总营业额
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param type 0营业额 1日均客单量2客单价3会员日均客单量4会员客单价5毛利6平均毛利率  add 7 会员销售额 8 会员毛利
	 * @return
	 */
	public List<BranchMonthReport> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type);

	/**
	 *  营业收款统计  根据分店汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 *  营业收款统计  根据分店、营业日汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 *  营业收款统计  根据分店、营业日汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByMerchantDay(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);

	/**
	 * 营业收款统计 根据终端汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 营业收款统计 根据班次汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);

	/**
	 * 营业收款统计 根据班次汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo, String casher);

	/**
	 *  营业收款统计  根据分店汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByMerchant(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo);

	/**
	 *  营业收款统计  根据分店汇总
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByStall(String systemBookCode, Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo);


	/**
	 * 类别同比 环比
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<OrderCompare> findCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 销售明细对比
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param itemCategoryCodes 商品类别代码列表
	 * @param lastDateFrom 上期营业日起
	 * @param lastDateTo  上期营业日止
	 * @param thisDateFrom  本期营业日起
	 * @param thisDateTo 本期营业日止
	 * @return
	 */
	public List<OrderDetailCompare> findOrderDetailCompareDatas(String systemBookCode, List<Integer> branchNums, List<String> itemCategoryCodes, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo, List<Integer> itemNums);

	/**
	 * 供应商销售分析 每天单品销售
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierSaleGroupByDate> findSupplierSaleGroupByDateDatas(SupplierSaleQuery supplierSaleQuery);

	/**
	 * ABC分析 销售金额
	 * @param query
	 * @return
	 */
	public List<ABCAnalysis> findABCDatasBySale(ABCListQuery query);

	/**
	 * ABC分析 毛利
	 * @param query
	 * @return
	 */
	public List<ABCAnalysis> findABCDatasByProfit(ABCListQuery query);

	/**
	 * 配送目标分析 年(月)
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 年 或 月
	 * @return
	 */
	public List<TransferGoal> findTransferGoalByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 查询供应商-分店销售报表——供应商销售汇总
	 * @param supplierBranchQuery
	 * @return
	 */
	public List<SupplierBranchSum> findSupplierBranchSum(SupplierBranchQuery supplierBranchQuery);

	/**
	 * 查询供应商-分店销售报表——供应商商品销售
	 * @param supplierBranchQuery
	 * @return
	 */
	public List<SupplierBranchGroupByItem> findSupplierBranchGroupByItem(SupplierBranchQuery supplierBranchQuery);

	/**
	 * 查询供应商-分店销售报表——供应商日销售汇总
	 * @param supplierBranchQuery
	 * @return
	 */
	public List<SupplierBranchGroupByDate> findSupplierBranchGroupByDate(SupplierBranchQuery supplierBranchQuery);

	/**
	 * 供应商销售排行
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierSaleRank> findSupplierSaleRank(SupplierSaleQuery supplierSaleQuery);

	/**
	 * ABC图标分析
	 * @param query
	 * @param type  i=0为销售额 i=1为毛利
	 * @return
	 */
	public List<ABCChart> findABCChartByType(ABCChartQuery query, int type);

	/**
	 * 供应商日销售汇总
	 * @param supplierBranchQuery
	 * @return
	 */
	public List<SupplierBranchDetail> findSupplierBranchDetail(SupplierBranchQuery supplierBranchQuery);

	/**
	 * 查询供应商信用度
	 * @param supplierBranchQuery
	 * @return
	 */
	public List<SupplierCredit> findSupplierCredit(SupplierBranchQuery supplierBranchQuery);

	/**
	 * 查询供应商联营信息
	 * @param supplierSaleQuery
	 * @return
	 */
	public List<SupplierLianYing> findSupplierLianYing(SupplierSaleQuery supplierSaleQuery);

	/**
	 * 滞销商品分析
	 * @param query
	 * @return
	 */
	public List<UnsalablePosItem> findUnsalableItems(UnsalableQuery query);

	/**
	 * 批发毛利 -- 客户汇总
	 * @param queryData
	 * @return
	 */
	public List<WholesaleProfitByClient> findWholesaleProfitByClient(WholesaleProfitQuery queryData);

	/**
	 * 批发毛利 -- 分店汇总
	 * @param queryData
	 * @return
	 */
	public List<WholesaleProfitByClient> findWholesaleProfitByBranch(WholesaleProfitQuery queryData);

	/**
	 * 批发毛利 -- 商品汇总
	 * @param queryData
	 * @return
	 */
	public List<WholesaleProfitByPosItem> findWholesaleProfitByPosItem(WholesaleProfitQuery queryData);

	/**
	 * 批发毛利 -- 批发销售、退货明细
	 * @param queryData
	 * @return
	 */
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByPosItemDetail(WholesaleProfitQuery queryData);

	/**
	 * 查询待配货单据 调出单+批发销售单
	 * @param queryData
	 * @return
	 */
	public List<ToPicking> findToPicking(ShipGoodsQuery queryData);

	/**
	 * 查询待发货单据 调出单+批发销售单
	 * @param queryData
	 * @return
	 */
	public List<ToShip> findToShip(ShipGoodsQuery queryData);

	/**
	 * 查询负毛利商品
	 * @param queryData
	 * @return
	 */
	public List<UnsalablePosItem> findNegativeMargin(InventoryExceptQuery queryData);

	/**
	 * 查询异常商品
	 * @param inventoryExceptQuery
	 * @return
	 */
	public List<ExceptInventory> findSingularItem(InventoryExceptQuery inventoryExceptQuery);

	/**
	 * 查询异常配送价商品
	 * @param inventoryExceptQuery
	 * @return
	 */
	public List<SingularPrice> findSingularPrice(InventoryExceptQuery inventoryExceptQuery);

	/**
	 * 按营业日统计营业额 储值额 发卡量
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param queryBy 统计类型 按营业额 or 按储值额 or 按发卡量
	 * @param dateType 时间类型 按月 or 按年
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @return
	 */
	public List<BranchBizSummary> findBizAndMoney(String systemBookCode, Integer branchNum, String queryBy, String dateType, Date dateFrom, Date dateTo);

	/**
	 * 查询库存积压
	 * @param unsalableQuery
	 * @return
	 */
	public List<UnsalablePosItem> findInventoryOverStock(UnsalableQuery unsalableQuery);

	/**
	 * 零售明细查询
	 * @param retailDetailQueryData
	 * @return
	 */
	public List<RetailDetail> findRetailDetails(String systemBookCode, RetailDetailQueryData retailDetailQueryData);

	/**
	 * 零售明细查询（农贸市场）
	 * @param retailDetailQueryData
	 * @return
	 */
	public List<RetailDetail> findMerchantRetailDetails(String systemBookCode, RetailDetailQueryData retailDetailQueryData);

	/**
	 * 零售明细查询
	 * @param retailDetailQueryData
	 * @return
	 */
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData retailDetailQueryData);

	/**
	 * 查询采购商品明细
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findPurchaseDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 查询采购商品汇总
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findPurchaseItem(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 查询采购供应商汇总
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findPurchaseSupplier(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 查询采购商品类别汇总
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findPurchaseBranchCategory(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 查询采购门店、供应商汇总
	 * @param purchaseOrderCollectQuery
	 * @return
	 */
	public List<PurchaseOrderCollect> findPurchaseBranchSupplier(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 毛利分析 日毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<BranchBizSummary> findProfitAnalysisDays(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 客户毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<ProfitByClientAndItemSummary> findProfitAnalysisByClientAndItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 商品毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<ProfitByBranchAndItemSummary> findProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 商品毛利汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public List<ProfitAnalysisByItemSummary> findProfitAnalysisByItem(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 销售员提成 按品牌汇总
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public List<SalerCommissionBrand> findSalerCommissionBrands(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames);

	/**
	 * 销售员提成 按品牌汇总
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public List<SalerCommissionCard> findSalerCommissionCards(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames);

	/**
	 * 销售员提成汇总
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames, BigDecimal interval);

	/**
	 * 销售员总合计
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public SalerSummary findSalerSummary(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames);

	/**
	 * 销售员提成明细
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param salerNames 销售员名称列表
	 * @return
	 */
	public List<SalerCommissionDetail> findSalerCommissionDetails(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames);

	/**
	 * 客单分析 历史客单分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param saleType 销售类型 微商城、实体店
	 * @return
	 */
	public List<CustomerAnalysisHistory> findCustomerAnalysisHistorys(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType);

	/**
	 * 客单分析 客单范围
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param rangeFrom 客单金额起
	 * @param rangeTo 客单金额止
	 * @param space 增幅
	 * @param saleType 销售类型 微商城、实体店
	 * @return
	 */
	public List<CustomerAnalysisRange> findCustomerAnalysisRanges(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, Integer rangeFrom, Integer rangeTo, Integer space, String saleType);

	/**
	 * 客单分析 日客单分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param saleType 销售类型 微商城、实体店
	 * @return
	 */
	public List<CustomerAnalysisDay> findCustomerAnalysisDays(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType);

	/**
	 * 客单分析 按班次客单分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param appUserName 收银员
	 * @param saleType 销售类型 微商城、实体店
	 * @return
	 */
	public List<CustomerAnalysisDay> findCustomerAnalysisShiftTables(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String appUserName, String saleType);

	/**
	 * 客单分析 时段分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param space 增幅
	 * @param itemNums
	 * @param saleType 销售类型 微商城、实体店
	 * @return
	 */
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, Integer space, List<Integer> itemNums, String saleType);

	/**
	 * 金额 客单量汇总
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param branchType  门店类型 加盟或直营
	 * @return
	 */
	public CustomerSummary sumCustomerAnalysis(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, String branchType);

	/**
	 * 销售分析 -- 商品汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByPosItems(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 商品汇总(农贸市场)
	 * @param queryData
	 * @return
	 */
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByMerchantPosItems(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 分店汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleByBranchSummary> findSaleAnalysisByBranchs(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 类别汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleByCategorySummary> findSaleAnalysisByCategorys(SaleAnalysisQueryData queryData);

    /**
     * 销售分析 -- 类别汇总
     * @param queryData
     * @return
     */
    public List<SaleByCategorySummary> findSaleAnalysisByMerchantCategorys(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 类别-分店汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleByCategoryBranchSummary> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 部门汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleByDepartmentSummary> findSaleAnalysisByDepartments(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 商品汇总
	 * @param queryData
	 * @return
	 * */
	public List<SaleAnalysisByItemDTO> findSaleAnalysisByItems(SaleAnalysisQueryData queryData);

	/**
	 * 销售分析 -- 品牌汇总
	 * @param queryData
	 * @return
	 */
	public List<SaleByBrandSummary> findSaleAnalysisByBrands(SaleAnalysisQueryData queryData);

	/**
	 * 查询批发毛利 按商品类别汇总
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<WholesaleProfitByPosItem> findWholesaleProfitByCategory(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 查询批发毛利 按客户 商品汇总
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByClientAndItem(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 返利分析 按商品汇总
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<ItemRebatesSummary> findItemRebates(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 返利分析 明细
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public List<RebatesDetailSummary> findRebatesDetail(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 返利分析总合计
	 * @param policyAllowPriftQuery
	 * @return
	 */
	public RebatesSumSummary findRebatesSum(PolicyAllowPriftQuery policyAllowPriftQuery);

	/**
	 * 按时间类型查询调出单数量和金额
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param branchNum 分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<OutOrderCountAndMoneySummary> findOutOrderCountAndMoneyByDate(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询调入单数量和金额
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param branchNum 分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<ReturnCountAndMoneySummary> findReturnCountAndMoneyByDate(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询批发销售单数量和金额
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param clientFid 客户主键
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<WholesaleOrderCountAndMoneySummary> findWholesaleOrderCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询批发退货单数量和金额
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param clientFid 客户主键
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<WholesaleReturnCountAndMoneySummary> findWholesaleReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按营业日或营业月汇总前台销售金额
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param dateType  日期类型 按年或 按月
	 * @return
	 */
	public List<PosOrderMoneyByBizDaySummary> findPosOrderMoneyByBizDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询收货单数量和金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param supplierNum 供应商主键
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<PurchaseReceiveCountMoneySummary> findPurchaseReceiveCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询采购退货单数量和金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param supplierNum 供应商主键
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<PurchaseReturnCountMoneySummary> findPurchaseReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 按时间类型查询采购单数量和金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param supplierNum 供应商主键
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 日期类型 按年或 按月
	 * @return
	 */
	public List<PurchaseCountMoneySummary> findPurchaseCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 会员消费总额分析
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public List<CardConsumeAnalysis> findCardConsumeAnalysis(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);

	/**
	 * 会员指标消费分析 明细
	 * @param cardConsuemAnalysisQuery
	 * @return
	 */
	public List<CardConsumeDetailSummary> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery);

	/**
	 * 计算帐套汇总数据
	 * @param systemBookCode
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @return
	 */
	public BookSummaryReport getBookSummaryReport(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 查询库存异常商品 （低库存或高库存）
	 * @param inventoryExceptQuery
	 * @param highExceptFlag 为true则统计高库存商品
	 * @return
	 */
	List<ExceptInventory> findExceptInventories(InventoryExceptQuery inventoryExceptQuery, boolean highExceptFlag);

	/**
	 * 查询门店汇总数据
	 * @param systemBookCode
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @return
	 */
	List<BranchBusinessSummary> findBranchBusinessSummaries(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 查询分店配送金额  调入 - 调出
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param transferBranchNums 配送分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	BigDecimal sumTransferMoney(String systemBookCode, Integer branchNum, List<Integer> transferBranchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询配送仓库存数量 金额 滞销商品数量 金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @return
	 */
	InventoryUnsaleSummary findInventoryUnsale(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按门店类型汇总 前台销售金额 客单量
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	List<PosGroupBranchRegionTypeSummary> findPosGroupByBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按小时和门店区域类型汇总客单量 和金额
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	List<PosGroupHourAndBranchRegionTypeSummary> findPosGroupByHourAndBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按小时汇总客单量 和金额
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	List<PosGroupHourSummary> findPosGroupByHour(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按商品汇总POS销售毛利 数量 金额 成本
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param kitFlag 是否按成分商品查询
	 * @return
	 */
	List<GroupByItemSummary> findSummaryGroupByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, boolean kitFlag);

	/**
	 * 按商品汇总销量 （中心销量 = 调出 + 批发   门店销量 = 前台销售 + 批发） 
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param includePos 是否包含POS销售
	 * @param includeTranferOut 是否包含调出
	 * @param includeWholesale 是否包含批发销售
	 * @return
	 */
	List<ItemSaleQtySummary> findItemSaleQty(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, boolean includePos, boolean includeTranferOut, boolean includeWholesale);

	/**
	 * 按客户商品汇总缺货信息
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param clientFids 客户主键列表
	 * @param itemNums 商品主键列表
	 * @return
	 */
	List<WholesaleOrderLostSummary> findWholesaleOrderLostByClientAndItem(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> clientFids, List<Integer> itemNums);

	/**
	 * 查询批发客户分析 滞销商品
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientFid 客户主键号
	 * @param wholesaleRateFrom 批发次数起
	 * @param wholesaleRateTo 批发次数止
	 * @param unWholesaleDaysFrom 滞销天数起
	 * @param unWholesaleDaysTo 滞销天数止
	 * @param unitType 单位类型
	 * @return
	 */
	List<WholesaleAnalysisDTO> findWholeSaleUnsalableItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType);

	/**
	 * 查询批发客户分析 利润商品
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientFid 客户主键号
	 * @param wholesaleRateFrom 批发次数起
	 * @param wholesaleRateTo 批发次数止
	 * @param unWholesaleDaysFrom 滞销天数起
	 * @param unWholesaleDaysTo 滞销天数止
	 * @param unitType 单位类型
	 * @return
	 */
	List<WholesaleAnalysisDTO> findWholeSaleProfitItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType);

	/**
	 * 查询批发客户分析 爆款商品
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientFid 客户主键号
	 * @param wholesaleRateFrom 批发次数起
	 * @param wholesaleRateTo 批发次数止
	 * @param unWholesaleDaysFrom 滞销天数起
	 * @param unWholesaleDaysTo 滞销天数止
	 * @param unitType 单位类型
	 * @return
	 */
	List<WholesaleAnalysisDTO> findWholeSaleHotItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType);

	/**
	 * 查询分店销售金额 数量 （POS销售+ 调出 + 销售）
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @return
	 */
	List<BranchSaleQtySummary> findBranchSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店 商品汇总销量 （中心销量 = 调出 + 批发   门店销量 = 前台销售 + 批发） 
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	List<BranchItemSummary> findBranchItemSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 分店客单分析
	 * @param systemBookCode
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param branchNums 分店列表
	 * @param saleType
	 * @return
	 */
	List<CustomerAnalysisDay> findCusotmerAnalysisBranchs(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType);

	/**
	 * 查询批发客户未销售但是有库存的商品
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param clientFid 客户主键
	 * @return
	 */
	List<WholesaleAnalysisDTO> findClientUnSaleItems(String systemBookCode, Integer branchNum, String clientFid);

	/**
	 * 过期催销商品查询
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param storehouseNum 仓库主键
	 * @param categoryCodes 商品类别代码列表
	 * @param itemNums  商品主键列表
	 * @param unitType 单位类型
	 * @return
	 */
	List<NeedSaleItemDTO> findNeedSaleItemDatas(String systemBookCode, Integer branchNum, Integer storehouseNum, List<String> categoryCodes, List<Integer> itemNums, String unitType);

	/**
	 * 查询支付宝报表
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 操作时间起
	 * @param dateTo 操作时间止
	 * @param payType 线上支付类型
	 * @return
	 */
	List<AlipaySumDTO> findAlipaySumDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String payType);

	/**
	 * 查询支付宝报表明细
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 操作时间起
	 * @param dateTo 操作时间止
	 * @param type 前台销售 or 卡存款
	 * @parma paymentType 线上支付类型
	 * @param queryAll 是否查询所有明细 包含失败的记录
	 * @return
	 */
	List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String type, String paymentType, Boolean queryAll);

	/**
	 * 查询会员消费汇总
	 * @param cardUserQuery
	 * @return
	 */
	List<CardAnalysisDTO> findCardAnalysisDTOs(CardUserQuery cardUserQuery);

	/**
	 * 毛利分析 按分店汇总
	 * @param profitAnalysisQueryData
	 * @return
	 */
	List<BranchProfitSummary> findProfitAnalysisBranchs(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 按分店对比客单量 金额 客单价
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param lastDateFrom 上期营业日起
	 * @param lastDateTo 上期营业日止
	 * @param thisDateFrom 本期营业日起
	 * @param thisDateTo 本期营业日止
	 * @return
	 */
	List<OrderDetailCompare> findOrderDetailCompareDatasByBranch(String systemBookCode, List<Integer> branchNums, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo);

	/**
	 * 按分店商品对比客单量 金额 客单价
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param lastDateFrom 上期营业日起
	 * @param lastDateTo 上期营业日止
	 * @param thisDateFrom 本期营业日起
	 * @param thisDateTo 本期营业日止
	 * @return
	 */
	List<OrderDetailCompare> findOrderDetailCompareDatasByBranchItem(String systemBookCode, List<Integer> branchNums, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo, List<Integer> itemNums, List<String> categoryCodes);
	/**
	 * 销售目标分析 年(月)
	 * @param systemBookCode
	 * @paream centerBranchNum
	 * @param branchNums 分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param dateType 年 或 月
	 * @return
	 */
	List<TransferGoal> findTransferSaleGoalByDate(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

	/**
	 * 查询消费券明细
	 * @param elecTicketQueryDTO
	 * @return
	 */
	List<ElecTicketDTO> findElecTicketDTOs(ElecTicketQueryDTO elecTicketQueryDTO);

	/**
	 * 批发毛利汇总 供应商汇总
	 * @param wholesaleProfitQuery
	 * @return
	 */
	List<WholesaleProfitByClient> findWholesaleProfitBySupplier(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 消费券分析报表
	 * @param elecTicketQueryDTO
	 * @return
	 */
	List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO);

	/**
	 * 查询进出筐 按商品汇总 
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	List<PackageSumDTO> findPackageSum(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询商品连带商品次数排行
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param selectCount 查询记录数
	 * @return
	 */
	List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer itemNum, Integer selectCount);

	/**
	 * 运输费率报表 按线路
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param transferLineNums
	 * @return
	 */
	List<CarriageCostDTO> findCarriageCosts(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> transferLineNums);

	/**
	 * 查询中心筐数据
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	List<CenterBoxReportDTO> findCenterBoxReportDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 查找客户超额数量
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	Integer countByClientOverDue(String systemBookCode, Integer branchNum);

	/**
	 * 销售分析 -- 分店 营业日汇总
	 * @param saleAnalysisQueryData
	 * @return
	 */
	List<BranchBizSaleSummary> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 根据分店汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 根据营业日汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	List<CardReportDTO> findCardReportByDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 门店配销分析	
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param saleDate
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	List<BranchCategoryTransSaleAnalyseDTO> findBranchCategoryTransSaleAnalyseDTOs(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date saleDate, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 门店类别分析
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @return
	 */
	List<BranchCategoryAnalyseDTO> findBranchCategoryAnalyseDTOs(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo,List<String> categoryCodes);

	/**
	 * 查询item_recording 过期催销商品
	 * @param bookCode
	 * @param branchNum
	 * @return
	 */
	List<Object[]> findNeedSaleItemRecords(String bookCode, Integer branchNum);

	/**
	 * 查询商品价格带
	 * @param posItemPriceBandQuery
	 * @return
	 */
	List<PosItemPriceBandDTO> findPosItemPriceBandDTOs(PosItemPriceBandQuery posItemPriceBandQuery);

	/**
	 * 配货员绩效报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param pickers
	 * @return
	 */
	List<PickerPerformanceDTO> findPickerPerformanceDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> pickers);

	/**
	 * 查询进出筐 按分店汇总 
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	List<PackageSumDTO> findPackageSumByBranch(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询进出筐 按单价
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	List<PackageSumDTO> findPackageSumByPrice(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询会员消费汇总
	 * @param cardUserQuery
	 * @return
	 */
	CardAnalysisSummaryDTO getCardAnalysisSummaryDTO(CardUserQuery cardUserQuery);

	/**
	 * 客单分析 分店汇总
	 * @param systemBookCode
	 * @param dtFrom
	 * @param dtTo
	 * @param branchNums
	 * @param saleType
	 * @return
	 */
	List<BranchCustomerSummary> findCustomerAnalysisBranch(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, String saleType);

	List<ShipOrderBranchDTO> findShipOrderBranch(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, Date itemDateFrom, Date itemDateTo, List<String> categoryCodes, List<Integer> exceptItemNums, BigDecimal minMoney);

	List<ShipOrderBranchDetailDTO> findShipOrderBranchDetail(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes);

	List<ShipOrderBranchDetailDTO> findShipOrderBranchNewItem(String systemBookCode, Integer outBranchNum, Date itemDateFrom, Date itemDateTo, List<String> categoryCodes, List<Integer> exceptItemNums);

	/**
	 * 查询采购员采购配送报表
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param employees
	 * @param itemNums
	 * @param categoryCodes
	 * @param unitType
	 * @return
	 */
	List<PurchaseAndTransferDTO> findPurchaseAndTransferDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums, String categoryCodes, String unitType);

	/**
	 * 采购员采购配送明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNum
	 * @param itemNums
	 * @param employees
	 * @param categoryCodes
	 * @param unitType
	 * @return
	 */
	List<PurchaseAndTransferDetailDTO> findPurchaseAndTransferDetailDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums, String categoryCodes, String unitType);

	/**
	 * 根据分店 卡类型 汇总卡数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	List<CardReportDTO> findCardReportByBranchCardType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 查询手工指定商品出入库汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNum
	 * @param itemLotNumber
	 * @param itemUnit
	 * @return
	 */
	List<LnItemSummaryDTO> findLnItemSummaryDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer itemNum, String itemLotNumber, String itemUnit);
	
	public Object excuteSql(String systemBookCode, String sql);

	/**
	 * 销售分析 -- 按分店汇总商品信息
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchPosItems(String systemBookCode, SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 销售分析 -- 按分店汇总商品信息
	 * @param saleAnalysisQueryData
	 * @return
	 */
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchPosItems(SaleAnalysisQueryData saleAnalysisQueryData);


	/**
	 * 缺货分析
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * */
	public List<InventoryLostDTO> findInventoryLostAnalysis(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums,String unitType,
															List<String> itemDepartments, List<String> itemCategoryCodes);

	//日销售分析
	public List<BranchSaleAnalysisSummary> findDaySaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type);

	//月销售分析
	public List<BranchSaleAnalysisSummary> findMonthSaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type);


	/**
	 * 客单分析 历史客单分析  分页查询
	 * @return
	 */
	public CustomerAnalysisHistoryPageDTO findCustomerAnalysisHistorysByPage(SaleAnalysisQueryData saleAnalysisQueryData);

	/**
	 * 毛利分析 门店商品毛利汇总  分页查询
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public ProfitByBranchAndItemSummaryPageDTOV2 findProfitAnalysisByBranchAndItemByPageV2(ProfitAnalysisQueryData profitAnalysisQueryData);

	/**
	 * 毛利分析 门店商品毛利汇总  分页查询
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public ProfitByBranchAndItemSummaryPageDTO findProfitAnalysisByBranchAndItemByPage(ProfitAnalysisQueryData profitAnalysisQueryData);



	/**
	 * 毛利分析 日毛利汇总   分页查询
	 * @param profitAnalysisQueryData
	 * @return
	 */
	public BranchBizSummaryPageDTO findProfitAnalysisDaysByPage(ProfitAnalysisQueryData profitAnalysisQueryData);


	/**
	 * 销售分析 -- 按分店商品汇总信息   分页查询
	 * @param queryData
	 * @return
	 */
	public SaleAnalysisBranchItemPageSummary findSaleAnalysisByBranchPosItemsByPage(SaleAnalysisQueryData queryData);


	/**
	 * 零售明细查询  分页查询
	 * @param retailDetailQueryData
	 * @return
	 */
	public RetailDetailPageSummary findRetailDetailsByPage(RetailDetailQueryData retailDetailQueryData);


	/**
	 * 采购计划周期表(好想来)
	 * */
	public List<PurchaseCycleSummary> findPurchaseCycleByBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 商品配送top榜（好想来）
	 * */
	public List<TransferItemDetailSummary> findTransferItemTop(String systemBookCode,Integer branchNum,Date dateFrom,Date dateTo,List<String> itemCategoryCodes,String sortField);

	/**
	 * 商品库存动态表（好想来）
	 * */
	public List<ItemInventoryTrendSummary> findItemTrendInventory(ItemInventoryQueryDTO inventoryQuery);

	/**
	 * 查询支付宝报表明细
	 * @return
	 */
	List<AlipayDetailDTO> findAlipayDetailDTOs(AlipayDetailQuery alipayDetailQuery);

	/**
	 * 查询门店商品销售排行 前100
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @return
	 */
	public List<PosItemRank> findPosItemRanks(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


    /**
	 * AMA-23167 性能优化
     * 门店营业分析-门店商品汇总
     * */
    public BranchProfitDataPageDTO findBranchAndItemProfit(BranchProfitQuery query);

	/**
	 * AMA-23175  性能优化
	 *直调查询-门店商品汇总
	 */
	public TransferProfitByPosItemPageDTO findTransferProfitByPosItemBranch(TransferProfitQuery queryData);

	/**
	 * AMA-23175  性能优化
	 * 直调查询-商品明细
	 */
	public TransferProfitByPosItemDetailPageDTO findTransferProfitByPosItemDetail(TransferProfitQuery queryData);

	/**
	 * AMA-23179 性能优化-损益统计报表
	 * 商品汇总
	 * */
	public InventoryProfitPageDTO findInventoryProfit(InventoryProfitQuery queryData);

	/**
	 * AMA-23179 性能优化-损益统计报表
	 * 类别汇总
	 * */
	public InventoryProfitPageDTO findInventoryProfitSum(InventoryProfitQuery queryData);

	/**
	 * AMA-24018 性能优化-会员卡操作记录
	 * */
	public AllOperatePageDTO findCardUserOperate(CardReportQuery cardReportQuery);








}
