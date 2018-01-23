package com.nhsoft.module.report.api;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import jdk.nashorn.internal.runtime.ECMAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yangqin on 2017/10/30.
 */
@RestController
@RequestMapping("/basic")
public class APIBasic {

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
	@Autowired
	private Report2Rpc report2Rpc;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CardUserService cardUserService;
	@Autowired
	private PosOrderDao posOrderDao;
	@Autowired
	private AlipayLogService alipayLogService;
	@Autowired
	private AlipayLogRpc alipayLogRpc;

	
	@RequestMapping(method = RequestMethod.GET, value = "/clear")
	public @ResponseBody String clearSystemBookProxy(@RequestParam("systemBookCode") String systemBookCode) {
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test1")
	public  List<Object[]> test () throws Exception {
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

		//含join
		CardReportQuery cardReportQuery = new CardReportQuery();
		cardReportQuery.setQueryDetail(true);
		//cardReportQuery.setQueryPayment(true);
		cardReportQuery.setSystemBookCode("4344");
		cardReportQuery.setBranchNum(99);
		cardReportQuery.setDateFrom(dateFrom);
		cardReportQuery.setDateTo(dateTo);
		List<Object[]> summaryByBranch = posOrderService.findSummaryByBizday(cardReportQuery);   //含join是ok的
		return summaryByBranch;
		//return posOrderRpc.findMoneyBizdaySummary("4344", Arrays.asList(1,2,99), AppConstants.BUSINESS_TREND_PAYMENT, DateUtil.getDateStr("20170901"), DateUtil.getDateStr("20171101"), false);
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test2")
	public List<Object[]> test2() throws Exception{		//含or没报错   含case when 不报错
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
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
		items.add(110010038);
		items.add(110010038);
		List<Object[]> itemSum = posOrderService.findItemSum(systemBookCode,branchNums,dateFrom,dateTo,items,true);
		return itemSum;

	}
	@RequestMapping(method=RequestMethod.GET,value="/test3/{systemBookCode}/{dateFrom}/{dateTo}")
	public List<Object[]> test3(@PathVariable("systemBookCode") String systemBookCode,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception{			//ok 含 case when

		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);	//含 case when 不报错
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date from = sdf.parse(dateFrom);
		Date to = sdf.parse(dateTo);
		List<Object[]> customReportByBizday = posOrderService.findCustomReportByBizday(systemBookCode, branchNums, from, to);
		return customReportByBizday;
	}



	@RequestMapping(method = RequestMethod.GET, value = "/test4")
	public List<Object[]> test4() throws Exception{						// ok 含or
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2016-05-01");
		Date dateTo = sdf.parse("2017-05-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);		//修改sql中的 detail.order_detail_has_kit = 0   数据改变
		List<Object[]> branchItemSum = posOrderService.findBranchItemSum(systemBookCode,branchNums,dateFrom,dateTo,items, true);
		return branchItemSum;
	}
	@RequestMapping(method=RequestMethod.GET,value="/test5")
	public List<Object[]> test5() throws Exception{  //ok case   when
		String systemBookCode= "4344";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		List<Object[]> branchItemSaleQty = reportService.findBranchItemSaleQty(systemBookCode, branchNums, dateFrom, dateTo, items);
		//为了测试findBranchItemMatrixSummary方法     含case when   是ok的
		return branchItemSaleQty;
	}

	@RequestMapping(method = RequestMethod.GET,value="/test6")
	public List<Object[]> test6() throws Exception{			//含or没报错   (将or放在任何地方也不报错)
		String systemBookCode= "4344";				//含  exists  也不报错
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

		List<String> category = new ArrayList<>();
		category.add("456123");
		List<Object[]> itemSupplierSumByCategory = posOrderService.findItemSupplierSumByCategory(systemBookCode, branchNums, dateFrom, dateTo, category, true, items);
		return itemSupplierSumByCategory;
	}

	@RequestMapping(method = RequestMethod.GET,value="/test7")
	public void test7() throws Exception{
		String systemBookCode= "4344";				//含or  放在exists关键字的上面不会报错，再高一点就报错了
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		ItemQueryDTO itemQueryDTO = new ItemQueryDTO();
		itemQueryDTO.setSystemBookCode(systemBookCode);
		itemQueryDTO.setDateFrom(dateFrom);
		itemQueryDTO.setDateTo(dateTo);
		itemQueryDTO.setItemMethod("购销");
		itemQueryDTO.setBranchNums(branchNums);
		itemQueryDTO.setQueryKit(true);
		List<Object[]> itemSum = posOrderService.findItemSum(itemQueryDTO);
		System.out.println();
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test8")
	public List<MobileBusinessDetailDTO> test8() throws Exception{			//ok
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
		return cashSummaryGroupByShop;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test9")  //三张表  findDetails
	public void test9() throws Exception{
		String systemBookCode= "4344";				//不ok   Cannot find table rule and default data source with logic tables: '[]'
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
		List<Object[]> details = posOrderService.findDetails(systemBookCode, branchNums, dateFrom, dateTo, null, null, null, null, null);
		System.out.println();
	}

	@RequestMapping(method = RequestMethod.GET,value="/test10")
	public List<SupplierLianYing> test10() throws Exception{		//ok
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
		return supplierLianYing;
	}

	@RequestMapping(method = RequestMethod.GET,value="/test11")
	public List<ABCAnalysis> test11() throws Exception{	//子查询    ok带  exists  也ok

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
		return abcDatasBySale1;
	}

	//类别汇总
	@RequestMapping(method=RequestMethod.GET,value="/test12")
	public List<SaleByBranchSummary> test12() throws Exception{	//多表 有问题  关联不没有使用sharding的表positem   不报错
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		branchNums.add(11);
		branchNums.add(12);
		branchNums.add(99);
		queryData.setBranchNums(branchNums);
		queryData.setSystemBookCode("4344");
		queryData.setQueryItemExtendAttribute(true);
		queryData.setBrandCodes(null);
		queryData.setPosItemTypeCodes(null);
		queryData.setPosItemNums(null);
		queryData.setIsQueryChild(false);
		queryData.setIsQueryCF(true);
		queryData.setIsQueryGrade(false);
		queryData.setItemDepartments(null);
		queryData.setItemFlagNum(null);
		queryData.setSaleType("实体店");//水星微商城   实体店
		queryData.setIsQueryCardUser(false);
		queryData.setOrderSources(null);
		queryData.setTwoStringValueDatas(null);
		queryData.setAppUserNum(null);
		List<SaleByBranchSummary> saleAnalysisByBranchs = reportRpc.findSaleAnalysisByBranchs(queryData);

		return saleAnalysisByBranchs;

	}

	@RequestMapping(method=RequestMethod.GET,value="/test13")
	public List<BranchBizSummary> test13()throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar instance = Calendar.getInstance();
		Date dateFrom = sdf.parse("2017-10-01");
		Date dateTo = sdf.parse("2017-10-31");
		ProfitAnalysisQueryData queryData = new ProfitAnalysisQueryData();
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
		queryData.setSystemBookCode("4344");
		queryData.setBrandCodes(null);
		queryData.setPosItemTypeCodes(null);
		queryData.setPosItemNums(null);
		queryData.setIsQueryChild(false);
		queryData.setIsQueryCF(false);
		queryData.setItemFlagNum(null);
		queryData.setSaleType("实体店");//水星微商城   实体店
		queryData.setOrderSources(null);		//关联没有使用sharding的表 不报错。

		List<BranchBizSummary> profitAnalysisDays = reportRpc.findProfitAnalysisDays(queryData);
		return profitAnalysisDays;
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
	public List<BranchItemSummaryDTO> test15() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		List<BranchItemSummaryDTO> branchItemSum = posOrderRpc.findBranchItemSum("4344",itemQueryDTO);
		return branchItemSum;

	}

	public List<Integer> getBranchNums(){
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
		return branchNums;
	}

	@RequestMapping(method = RequestMethod.GET,value="/test20")
	public List<SupplierLianYing> test20() throws Exception{
		String systemBookCode= "4344";
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
		supplierSaleQuery.setDateFrom(dateFrom);
		supplierSaleQuery.setDateTo(dateTo);
		supplierSaleQuery.setItemNums(items);

		List<SupplierLianYing> supplierLianYing = reportService.findSupplierLianYing(supplierSaleQuery);
		return supplierLianYing;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test21")
	public List<Branch> test21(){
		List<Branch> branchByBranchRegionNum = branchService.findBranchByBranchRegionNum("4344", 1);
		return branchByBranchRegionNum;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test22")
	public List<Object[]> test22(){
		List<Object[]> cardUserCountByBranch = cardUserService.findCardUserCountByBranch("4344", null, null, null);
		return cardUserCountByBranch;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test23")
	public List test23() throws Exception{
		String systemBookCode= "4410";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(434400126);
		SupplierSaleQuery supplierSaleQuery = new SupplierSaleQuery();
		supplierSaleQuery.setSystemBookCode(systemBookCode);
		supplierSaleQuery.setDateFrom(dateFrom);
		supplierSaleQuery.setDateTo(dateTo);
		supplierSaleQuery.setItemNums(items);
		List<SupplierComplexReportDTO> list = reportRpc.findSupplierSaleGroupByBranchDatas(supplierSaleQuery);
		return list;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/test24")
	public List test24() throws Exception{
		String systemBookCode = "4344";

		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-31");

		List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(4344);

		List<String> itemCategoryCodes = new ArrayList<>();
		itemCategoryCodes.add("qqq");
		itemCategoryCodes.add("www");

		List<RequestAnalysisDTO> list = report2Rpc.findRequestAnalysisDTOs(systemBookCode, branchNums, dateFrom, dateTo, items, itemCategoryCodes);
		return list;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test25")
	public List test25() throws Exception{
		String systemBookCode = "4344";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		//branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		String payType = "微信二维码支付";//微信二维码支付

		List<AlipaySumDTO> list = reportRpc.findAlipaySumDTOs(systemBookCode, branchNums, dateFrom, dateTo, payType);
		return list;
	}

	//findCusotmerAnalysisBranchs
	@RequestMapping(method = RequestMethod.GET,value = "/test26")
	public List test26() throws Exception{
		String systemBookCode = "4344";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-30");

		String saleType = "美团外卖";
		List<CustomerAnalysisDay> list = reportRpc.findCusotmerAnalysisBranchs(systemBookCode, dateFrom, dateTo, branchNums, saleType);
		return list;
	}

	//findCustomerAnalysisHistorys
	@RequestMapping(method = RequestMethod.GET,value = "/test27")
	public List test27() throws Exception{

		String systemBookCode = "4344";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-30");

		String saleType = "微商城";//微商城
		List<CustomerAnalysisHistory> list = reportRpc.findCustomerAnalysisHistorys(systemBookCode,dateFrom,dateTo,branchNums,saleType);
		return list;
	}
	//sumCustomerAnalysis
	@RequestMapping(method = RequestMethod.GET,value = "/test28")
	public CustomerSummary test28() throws Exception{
		String systemBookCode = "4344";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-30");

		String branchType = "微商城";//微商城
		CustomerSummary customerSummary = reportRpc.sumCustomerAnalysis(systemBookCode, dateFrom, dateTo, branchNums, branchType);
		return customerSummary;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test29")
	public List<CustomerAnalysisDay> test29() throws Exception{
		//findCustomerAnalysisDays
		String systemBookCode = "4344";
		List<Integer> branchNums = new ArrayList();
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-30");

		String saleType = "饿了么";//微商城
		List<CustomerAnalysisDay> list = reportRpc.findCustomerAnalysisDays(systemBookCode,dateFrom,dateTo,branchNums,saleType);
		return list;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nimei")
	public String nimei(){
		return "nimei";
	}


	@RequestMapping(method = RequestMethod.GET,value = "test30")
	public List<SupplierLianYing> test30()throws Exception{


		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2015-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400100);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
		SupplierSaleQuery supplierSaleQuery = new SupplierSaleQuery();
		supplierSaleQuery.setSystemBookCode(systemBookCode);
		supplierSaleQuery.setDateFrom(dateFrom);
		supplierSaleQuery.setDateTo(dateTo);
		supplierSaleQuery.setItemNums(items);

		List<SupplierLianYing> supplierLianYing = reportRpc.findSupplierLianYing(supplierSaleQuery);
		return supplierLianYing;
	}










    @RequestMapping(method = RequestMethod.GET,value = "test31")
    public List<ItemDailyDetail> test31()throws Exception{  //test bi   两个内连接都不行


        String systemBookCode= "4344";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2016-05-01");
        Date dateTo = sdf.parse("2017-10-31");
        List<Integer> items = new ArrayList<>();
        items.add(434400100);
        items.add(434400126);
        items.add(110010009);
        items.add(110010007);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode,dateFrom,dateTo,items);
        return itemDailyDetailSummary;
        //findItemDailyDetailSummary
    }

	@RequestMapping(method = RequestMethod.GET,value = "test31_1")
	public List<ItemSaleDailyDTO> test31_1()throws Exception{  //test bi   两个内连接都不行


		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2016-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400100);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
		List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBookCode, dateFrom, dateTo);
		return itemSaleDailySummary;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test31_2")
	public List<BranchDaily> test31_2()throws Exception{  //单表是ok的


		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2016-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> items = new ArrayList<>();
		items.add(434400100);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
		List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, dateFrom, dateTo);
		return branchDailySummary;
	}


    @RequestMapping(method = RequestMethod.GET,value = "test32")
    public List<BusinessCollection> test32()throws Exception{


        String systemBookCode= "4344";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = sdf.parse("2015-05-01");
        Date dateTo = sdf.parse("2017-10-31");
        List<Integer> branchNums = getBranchNums();


        List<BusinessCollection> couponSummary = posOrderRpc.findCouponSummary(systemBookCode, branchNums, dateFrom, dateTo);
        return couponSummary;
    }


    //findRetailDetails


	@RequestMapping(method = RequestMethod.GET,value = "test33")
	public List<RetailDetail> test33()throws Exception{
		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		List<Integer> branchNums = getBranchNums();
		RetailDetailQueryData query = new RetailDetailQueryData();
		query.setDtFromShiftTable(dateFrom);
		query.setDtToShiftTable(dateTo);
		query.setSystemBookCode(systemBookCode);
		//去掉 with(nolock) 就会走sharding
		// sb.append("from pos_order as p inner join pos_order_detail as detail on p.order_no = detail.order_no ");
		List<RetailDetail> retailDetails = reportRpc.findRetailDetails(query);
		return retailDetails;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test34")
	public List<ProfitByClientAndItemSummary> test34()throws Exception{
		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		ProfitAnalysisQueryData query  = new ProfitAnalysisQueryData();
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		//去掉with(nolock) 就走sharding
		////sb.append("from pos_order as p inner join pos_order_detail as detail on p.order_no = detail.order_no ");
		List<ProfitByClientAndItemSummary> profitAnalysisByClientAndItem = reportRpc.findProfitAnalysisByClientAndItem(query);
		return profitAnalysisByClientAndItem;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test35")
	public List<PayFailDTO> test35()throws Exception{
		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-01-20");
		List<Integer> branchNums = getBranchNums();
		List<Integer> list = new ArrayList<>();
		list.add(8);
		List<PayFailDTO> dtos = alipayLogRpc.findBranchSummaryPayFail(systemBookCode, branchNums, dateFrom, dateTo, false, null);
		return dtos;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test36")
	public List<AlipayLogDTO> test36()throws Exception{
		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		LogQuery query  =new LogQuery();
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		//query.setLogItem("123");
		List<AlipayLogDTO> byLogQuery = alipayLogRpc.findByLogQuery(systemBookCode, 99, query, 0,100);
		return byLogQuery;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test37")
	public List<AlipayLog> test37()throws Exception{
		String systemBookCode= "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-05-01");
		Date dateTo = sdf.parse("2017-10-31");
		LogQuery query  =new LogQuery();
		//query.setLogItem("123");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<AlipayLog> test = alipayLogService.test(systemBookCode, query,dateFrom, dateTo);
		return test;
	}



	//test测试新加的报表
	@RequestMapping(method = RequestMethod.GET,value = "test38")
	public List<InventoryLostDTO> test38() throws Exception{
		String systemBookCode= "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-01-20");
		Integer branchNum = 99;
		List<Integer> itemNums = new ArrayList<>();
		itemNums.add(402000092);
		itemNums.add(402000134);
		itemNums.add(402000145);
		itemNums.add(402000073);
		List<InventoryLostDTO> inventoryLostAnalysis = reportRpc.findInventoryLostAnalysis(systemBookCode,branchNum,dateFrom,dateTo,itemNums,null,null,null);
		return inventoryLostAnalysis;

	}

	@RequestMapping(method = RequestMethod.GET,value = "test39")
	public List<PosOrderDTO> test39() throws Exception{

		PosOrderQuery query = new PosOrderQuery();
		query.setSystemBookCode("4020");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-01-31");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<Integer> list = new ArrayList<>();
		list.add(99);
		query.setBranchNums(list);
		List<PosOrderDTO> settled = posOrderRpc.findSettled("4020",query,0,5000);
		return settled;
	}



}
