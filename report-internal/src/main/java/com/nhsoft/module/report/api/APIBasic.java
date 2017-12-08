package com.nhsoft.module.report.api;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.query.ABCListQuery;
import com.nhsoft.module.report.query.ProfitAnalysisQueryData;
import com.nhsoft.module.report.query.SaleAnalysisQueryData;
import com.nhsoft.module.report.query.SupplierSaleQuery;
import com.nhsoft.module.report.rpc.AlipayLogRpc;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.rpc.ReportRpc;
import com.nhsoft.module.report.service.MobileAppV2Service;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.service.ReportService;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yangqin on 2017/10/30.
 */
@RestController()
@RequestMapping("/basic")
public class APIBasic {
	
	@Autowired
	private AlipayLogRpc alipayLogRpc;
	
	@Autowired
	private PosOrderRpc posOrderRpc;
	@Autowired
	private PosOrderService posOrderService;
	@Autowired
	private BranchRpc branchRpc;
	@Autowired
	private MobileAppV2Service mobileAppV2Service;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportRpc reportRpc;

	
	@RequestMapping(method = RequestMethod.GET, value = "/clear")
	public @ResponseBody String clearSystemBookProxy(@RequestParam("systemBookCode") String systemBookCode) {
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test/report")
	public  List<BranchBizRevenueSummary> test () throws Exception {
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");

		//含inner
		CardReportQuery cardReportQuery = new CardReportQuery();
		cardReportQuery.setQueryDetail(true);
		//cardReportQuery.setQueryPayment(true);
		cardReportQuery.setSystemBookCode("4344");
		cardReportQuery.setBranchNum(99);
		cardReportQuery.setDateFrom(dateFrom);
		cardReportQuery.setDateTo(dateTo);
		List<Object[]> summaryByBranch = posOrderService.findSummaryByBizday(cardReportQuery);
		System.out.println();
		return null;
		//return posOrderRpc.findMoneyBizdaySummary("4344", Arrays.asList(1,2,99), AppConstants.BUSINESS_TREND_PAYMENT, DateUtil.getDateStr("20170901"), DateUtil.getDateStr("20171101"), false);
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test1")
	public void test1()throws Exception{
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-10-01");
		Date dateTo = sdf.parse("2017-10-31");

		//含inner
		CardReportQuery cardReportQuery = new CardReportQuery();
		//cardReportQuery.setQueryDetail(true);
		cardReportQuery.setQueryPayment(true);
		cardReportQuery.setSystemBookCode("4344");
		cardReportQuery.setBranchNum(99);
		cardReportQuery.setDateFrom(dateFrom);
		cardReportQuery.setDateTo(dateTo);
		List<Object[]> summaryByBranch = posOrderService.findSummaryByBizday(cardReportQuery);
		System.out.println();

	}
	@RequestMapping(method = RequestMethod.GET,value = "/test2")
	public void test2() throws Exception{		//有or没报错
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
		items.add(110010038);
		items.add(110010038);
		List<Object[]> itemSum = posOrderService.findItemSum(systemBookCode,branchNums,dateFrom,dateTo,items,true);
		System.out.println();

	}
	@RequestMapping(method=RequestMethod.GET,value="/test3")
	public void test3() throws Exception{			//ok 含 case when
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Object[]> customReportByBizday = posOrderService.findCustomReportByBizday(systemBookCode, branchNums, dateFrom, dateTo);
		System.out.println();
	}



	@RequestMapping(method = RequestMethod.GET, value = "/test4")
	public void test4() throws Exception{						// ok 含or
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2016-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);		//修改sql中的 detail.order_detail_has_kit = 0   数据改变
		List<Object[]> branchItemSum = posOrderService.findBranchItemSum(systemBookCode,branchNums,dateFrom,dateTo,items, true);
		System.out.println();
	}
	@RequestMapping(method=RequestMethod.GET,value="/test5")
	public void test5() throws Exception{  //ok case   when
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		//List<Object[]> branchItemMatrixSummary = posOrderService.findBranchItemMatrixSummary(systemBookCode, branchNums, dateFrom, dateTo, items);
		System.out.println();
	}
	/*@RequestMapping(method = RequestMethod.GET,value="/test6")
	public void test6() throws Exception{			//含or没报错
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-10");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		List<Object[]> itemSupplierSumByCategory = posOrderService.findItemSupplierSumByCategory(systemBookCode, branchNums, dateFrom, dateTo, null, true, items);
		System.out.println();
	}*/

	@RequestMapping(method = RequestMethod.GET,value="/test7")
	public void test7() throws Exception{
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		ItemQueryDTO itemQueryDTO = new ItemQueryDTO();
		itemQueryDTO.setDateFrom(dateFrom);
		itemQueryDTO.setDateTo(dateTo);
		itemQueryDTO.setItemMethod("购销");
		itemQueryDTO.setBranchNums(branchNums);
		itemQueryDTO.setQueryKit(true);
		List<Object[]> itemSum = posOrderService.findItemSum(itemQueryDTO);
		System.out.println();
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test8")
	public void test8() throws Exception{
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		//List<NameAndValueDTO> discountDetails = mobileAppV2Service.findDiscountDetails(systemBookCode, branchNums, dateFrom, dateTo);
		List<MobileBusinessDetailDTO> cashSummaryGroupByShop = mobileAppV2Service.findCashSummaryGroupByShop(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.CASH_TYPE_POS);
		System.out.println();
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test9")  //三张表  findDetails
	public void test9() throws Exception{
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		//List<Object[]> details = posOrderService.findDetails(systemBookCode, branchNums, dateFrom, dateTo, null, null, null, null, null);
		System.out.println();
	}

	@RequestMapping(method = RequestMethod.GET,value="/test10")
	public void test10() throws Exception{
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-31");

		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);

		SupplierSaleQuery supplierSaleQuery = new SupplierSaleQuery();
		supplierSaleQuery.setSystemBookCode(systemBookCode);
		supplierSaleQuery.setBranchNums(branchNums);
		supplierSaleQuery.setDateFrom(dateFrom);
		supplierSaleQuery.setDateTo(dateTo);
		supplierSaleQuery.setItemNums(items);


		List<SupplierLianYing> supplierLianYing = reportService.findSupplierLianYing(supplierSaleQuery);
		System.out.println();
	}

	@RequestMapping(method = RequestMethod.GET,value="/test11")
	public void test11() throws Exception{	//子查询    ok带  exists  也ok

		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-05");
		Date dateTo = sdf.parse("2017-10-31");

		ABCListQuery abc = new ABCListQuery();
		abc.setSystemBookCode(systemBookCode);
		abc.setDateFrom(dateFrom);
		abc.setDateTo(dateTo);
		abc.setBranchNums(branchNums);
		List<String> list = new ArrayList<>();
		list.add("10");
		abc.setCategoryCodeList(list);
		List<String> list1 = new ArrayList<>();
		list1.add(AppConstants.CHECKBOX_SALE);
		abc.setTypes(list1);
		List<ABCAnalysis> abcDatasBySale1 = reportService.findABCDatasBySale(abc);
		System.out.println();
	}

	/**

	 类别-门店：reportService.findSaleAnalysisByCategoryBranchs(queryData);
	 类别汇总：reportService.findSaleAnalysisByCategorys(queryData);
	 门店汇总：reportService.findSaleAnalysisByBranchs(queryData);
	 商品汇总：reportService.findSaleAnalysisByPosItems(queryData)
	 部门汇总：reportService.findSaleAnalysisByDepartments(queryData);
	 营业日汇总：reportService.findSaleAnalysisByBranchBizday(queryData);
	 品牌汇总：reportService.findSaleAnalysisByBrands(queryData);
	 对应属性：private String saleType;//销售方式：微商城、实体店（SaleAnalysisQueryData）

	 public static final String ONLINE_ORDER_SOURCE_YOUZAN = "有赞";
	 public static final String ONLINE_ORDER_SOURCE_MEITUAN = "美团外卖";
	 public static final String ONLINE_ORDER_SOURCE_ELE = "饿了么外卖";
	 public static final String ONLINE_ORDER_SOURCE_MERCURY= "水星微商城";
	 public static final String POS_ORDER_SALE_TYPE_BRANCH = "实体店";
	 * */


	//类别汇总
	@RequestMapping(method=RequestMethod.GET,value="/test12")
	public void test12() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Date dateFrom = sdf.parse("2017-10-01");
		Date dateTo = sdf.parse("2017-10-31");


		SaleAnalysisQueryData queryData = new SaleAnalysisQueryData();
		queryData.setDtFrom(dateFrom);
		queryData.setDtTo(dateTo);
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);
		branchNums.add(4);
		branchNums.add(5);
		branchNums.add(6);
		branchNums.add(7);
		branchNums.add(8);
		branchNums.add(9);
		branchNums.add(10);
		branchNums.add(11);
		branchNums.add(12);
		branchNums.add(99);
		queryData.setBranchNums(branchNums);
		queryData.setSystemBookCode("4020");
		queryData.setQueryItemExtendAttribute(true);
		queryData.setBrandCodes(null);
		queryData.setPosItemTypeCodes(null);
		queryData.setPosItemNums(null);
		queryData.setIsQueryChild(false);
		queryData.setIsQueryCF(false);
		queryData.setIsQueryGrade(false);
		queryData.setItemDepartments(null);
		queryData.setItemFlagNum(null);
		queryData.setSaleType("实体店");//水星微商城   实体店
		queryData.setIsQueryCardUser(true);
		queryData.setOrderSources(null);
		queryData.setTwoStringValueDatas(null);
		queryData.setAppUserNum(null);
		List<SaleByBranchSummary> saleAnalysisByBranchs = reportRpc.findSaleAnalysisByBranchs(queryData);
		//List<Object[]> saleAnalysisByCategorys = reportService.findSaleAnalysisByBranchs(queryData);
		System.out.println();

	}

	@RequestMapping(method=RequestMethod.GET,value="/test13")
	public void test13()throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Date dateFrom = sdf.parse("2017-10-01");
		Date dateTo = sdf.parse("2017-10-31");
		ProfitAnalysisQueryData queryData = new ProfitAnalysisQueryData();
		/*private Date shiftTableFrom;//营业日起
		private Date shiftTableTo;//营业日止
		private List<Integer> branchNums;//分店
		private List<String> brandCodes;//品牌
		private List<String> posItemTypeCodes;//商品类别
		private List<Integer> posItemNums;//商品
		private List<String> clientFids;//客户
		private Boolean isQueryCF;//组合商品按成分统计
		private Boolean isQueryChild;//统计类别包含子类
		private Date checkFrom;//盘点时间起 只在毛利分析里使用
		private Date checkTo;//盘点时间止 只在毛利分析里使用
		private boolean isQueryClient = false; //是否查询客户
		private Integer itemFlagNum;
		private boolean isQueryPresent = false; //是否查询赠品
		private String saleType;//销售方式：微商城、实体店
		private List<String> orderSources;*/
		queryData.setShiftTableFrom(dateFrom);
		queryData.setShiftTableTo(dateTo);
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);
		branchNums.add(4);
		branchNums.add(5);
		branchNums.add(6);
		branchNums.add(7);
		branchNums.add(8);
		branchNums.add(9);
		branchNums.add(10);
		branchNums.add(11);
		branchNums.add(12);
		branchNums.add(99);
		queryData.setBranchNums(branchNums);
		queryData.setSystemBookCode("4020");
		queryData.setBrandCodes(null);
		queryData.setPosItemTypeCodes(null);
		queryData.setPosItemNums(null);
		queryData.setIsQueryChild(false);
		queryData.setIsQueryCF(false);
		queryData.setItemFlagNum(null);
		queryData.setSaleType("实体店");//水星微商城   实体店
		queryData.setOrderSources(null);

		List<BranchBizSummary> profitAnalysisDays = reportRpc.findProfitAnalysisDays(queryData);
		System.out.println();
		//reportService.

	}
	@RequestMapping(method=RequestMethod.GET,value="/test14")
	public void test14() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Date dateFrom = sdf.parse("2017-10-01");
		Date dateTo = sdf.parse("2017-10-31");
		String systemBookCode = "4020";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);
		branchNums.add(4);
		branchNums.add(5);
		branchNums.add(6);
		branchNums.add(7);
		branchNums.add(8);
		branchNums.add(9);
		branchNums.add(10);
		branchNums.add(11);
		branchNums.add(12);
		branchNums.add(99);
		String saleType = "水星微商城";//水星微商城   实体店

		List<CustomerAnalysisHistory> customerAnalysisHistorys = reportRpc.findCustomerAnalysisHistorys(systemBookCode, dateFrom, dateTo, branchNums, saleType);
		System.out.println();

	}

	@RequestMapping(method=RequestMethod.GET,value="/test15")
	public void test15() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Date dateFrom = sdf.parse("2017-01-28");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);
		branchNums.add(4);
		branchNums.add(5);
		branchNums.add(6);
		branchNums.add(7);
		branchNums.add(8);
		branchNums.add(9);
		branchNums.add(10);
		branchNums.add(11);
		branchNums.add(12);
		branchNums.add(99);
		ItemQueryDTO itemQueryDTO = new ItemQueryDTO();
		itemQueryDTO.setBranchNums(branchNums);
		itemQueryDTO.setDateFrom(dateFrom);
		itemQueryDTO.setDateTo(dateTo);
		List<BranchItemSummaryDTO> branchItemSum = posOrderRpc.findBranchItemSum("4020",itemQueryDTO);
		System.out.println();
		//reportService.

	}

	@RequestMapping(method=RequestMethod.GET,value="/test6")
	public void test16(){

		//reportService.

	}

	@RequestMapping(method=RequestMethod.GET,value="/test17")
	public void test17(){

		//reportService.

	}



	
}
