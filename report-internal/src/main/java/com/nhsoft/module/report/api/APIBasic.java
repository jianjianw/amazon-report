package com.nhsoft.module.report.api;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.math.BigDecimal;
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
	private AlipayLogService alipayLogService;
	@Autowired
	private AlipayLogRpc alipayLogRpc;
	@Autowired
	private PosItemLogRpc posItemLogRpc;
	@Autowired
	private ReceiveOrderRpc receiveOrderRpc;
	@Autowired
	private ReturnOrderRpc returnOrderRpc;
	@Autowired
	private TransferOutOrderRpc transferOutOrderRpc;
	@Autowired
	private ShipOrderRpc shipOrderRpc;
	
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
		Date dateFrom = sdf.parse("2017-05-02");
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
		branchNums.add(13);
		branchNums.add(14);
		branchNums.add(59);
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
		String systemBookCode = "4173";
		//List<Integer> branchNums = getBranchNums();
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(139);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-02-12");
		Date dateTo = sdf.parse("2018-02-12");
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
		Date dateTo = sdf.parse("2018-01-31");
		Integer branchNum = 99;
		List<Integer> itemNums = new ArrayList<>();
		List<String>  categorys = new ArrayList<>();
		//categorys.add("91");
		List<InventoryLostDTO> inventoryLostAnalysis = reportRpc.findInventoryLostAnalysis(systemBookCode,branchNum,dateFrom,dateTo,itemNums,AppConstants.UNIT_PURCHASE,null,categorys);
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

	@RequestMapping(method = RequestMethod.GET,value = "test40")
	public List test40() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//findOrderDetailCompareDatasByBranchItem
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums();
		Date lastDateFrom = sdf.parse("2017-10-01");
		Date lastDateTo = sdf.parse("2017-12-31");
		Date thisDateFrom = sdf.parse("2018-01-15");
		Date thisDateTo = sdf.parse("2018-01-21");
		List<Integer> itemNums =  new ArrayList<>();
		List<String> categoryCodes = new ArrayList<>();

		List<OrderDetailCompare> orderDetailCompareDatasByBranchItem = reportRpc.findOrderDetailCompareDatasByBranchItem(systemBookCode,branchNums,lastDateFrom,lastDateTo,
				thisDateFrom,thisDateTo,itemNums,categoryCodes);
		return orderDetailCompareDatasByBranchItem;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test41")
	public List<RequestAnalysisDTO> test41() throws Exception{

		String systemBookCode = "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-29");
		Date dateTo = sdf.parse("2018-01-31");
		List<Integer> branchNums = getBranchNums();
		List<Integer> list = new ArrayList<>();
		list.add(434404819);
		list.add(434404818);
		list.add(434400459);
		list.add(434404817);
		list.add(434400458);
		list.add(434404816);
		list.add(434404815);
		list.add(434401789);
		list.add(434404814);
		list.add(434404813);
		list.add(434401787);
		List<RequestAnalysisDTO> requestAnalysisDTOs = report2Rpc.findRequestAnalysisDTOs(systemBookCode, branchNums, dateFrom, dateTo, list, null);
		return requestAnalysisDTOs;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test42")
	public List test42() throws Exception{
		StoreQueryCondition query = new StoreQueryCondition();
		query.setSystemBookCode("4344");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-29");
		Date dateTo = sdf.parse("2018-01-31");
		query.setDateStart(dateFrom);
		query.setDateEnd(dateTo);
		List<String> categorys = new ArrayList<>();
		categorys.add("01");
		categorys.add("022");
		categorys.add("023");
		categorys.add("024");
		query.setItemCategoryCodes(categorys);
		query.setBranchNum(99);
		query.setAdjustReason("调出门店[管理中心]");

		List<PosItemLogSummaryDTO> dtos = posItemLogRpc.findItemSummaryInOutQtyAndMoney(query);
		return dtos;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test43")
	public List test43() throws Exception{
		StoreQueryCondition query = new StoreQueryCondition();
		query.setSystemBookCode("4344");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-29");
		Date dateTo = sdf.parse("2018-01-31");
		query.setDateStart(dateFrom);
		query.setDateEnd(dateTo);
		List<String> categorys = new ArrayList<>();
		categorys.add("01");
		categorys.add("022");
		categorys.add("023");
		categorys.add("024");
		query.setItemCategoryCodes(categorys);
		query.setBranchNum(99);
		query.setAdjustReason("调出门店[管理中心]");
		List<PosItemLogSummaryDTO> dtos = posItemLogRpc.findItemInOutQtyAndMoney(query);
		return dtos;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test44")
	public List test44() throws Exception{
		WholesaleProfitQuery query = new WholesaleProfitQuery();
		query.setSystemBookCode("4344");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-29");
		Date dateTo = sdf.parse("2018-01-31");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<WholesaleProfitByClient> list = reportRpc.findWholesaleProfitByClient(query);
		return list;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test45")
	public List test45() throws Exception {
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-02-01");
		Date dateTo = sdf.parse("2018-02-23");
		List<Integer> branchNums = getBranchNums();
		List<MonthPurchaseDTO> list = receiveOrderRpc.findPurchaseMonth(systemBookCode, 1, dateFrom, dateTo, AppConstants.STATE_AUDIT_TIME);

		return list;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test46")
	public CardSummaryPageDTO test46() throws Exception{
		CardReportQuery query = new CardReportQuery();
		query.setSystemBookCode("4020");
		query.setPaging(true);
		query.setOffset(0);
		query.setLimit(1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-02-23");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setQueryDetail(true);
		CardSummaryPageDTO summaryByPrintNum = posOrderRpc.findSummaryByPrintNum(query);
		return summaryByPrintNum;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test47")
	public List<BranchSaleAnalysisSummary> test47() throws Exception{
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-07-01");
		Date dateTo = sdf.parse("2018-02-23");
		int type = 4;
		List<BranchSaleAnalysisSummary> result = reportRpc.findMonthSaleAnalysis(systemBookCode, branchNums, dateFrom, dateTo, type);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test48")
	public List<BranchSaleAnalysisSummary> test48() throws Exception{
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-02-23");
		int type = 4;
		List<BranchSaleAnalysisSummary> result = reportRpc.findDaySaleAnalysis(systemBookCode, branchNums, dateFrom, dateTo, type);
		return result;
	}

	@Autowired
	private BranchItemRecoredRpc branchItemRecoredRpc;

	@RequestMapping(method = RequestMethod.GET,value = "/test49")
	public List<BranchItemRecoredDTO> test49() throws Exception{
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-02-23");
		List<String> types = new ArrayList<>();
		types.add(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
		List<BranchItemRecoredDTO> itemReceiveDate = branchItemRecoredRpc.findItemReceiveDate(systemBookCode, null, null, null, types);
		return itemReceiveDate;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test50")
	public List<UnsalablePosItem> test50() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2018-02-28");

		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-02-28");

		UnsalableQuery query = new UnsalableQuery();
		query.setSystemBookCode("4344");
		query.setBranchNums(getBranchNums());
		query.setFilterOutGTInventory(true);
		query.setReceiveDate(date);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setUnit(AppConstants.UNIT_BASIC);
		query.setBranchNum(99);
		query.setIsFilterInAndOut(true);



		List<UnsalablePosItem> unsalableItems = reportRpc.findUnsalableItems(query);
		return unsalableItems;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test51")
	public List<BusinessCollection> test51() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-03-09");

		String systemBookCode = "4020";
		List<BranchDTO> inCache = branchRpc.findInCache(systemBookCode);

		List<BusinessCollection> result = reportRpc.findBusinessCollectionByBranch(systemBookCode,getBranchNums(),dateFrom,dateTo);

		return result;

	}


	@RequestMapping(method = RequestMethod.GET,value = "/test52")
	public List<BusinessCollection> test52() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-09");

		String systemBookCode = "4020";


		List<BusinessCollection> result = reportRpc.findBusinessCollectionByBranchDay(systemBookCode,getBranchNums(),dateFrom,dateTo);

		return result;

	}

	@RequestMapping(method = RequestMethod.GET,value = "/test53")
	public CardAnalysisSummaryDTO test53() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-02-01");
		Date dateTo = sdf.parse("2018-02-28");

		String systemBookCode = "4020";


		CardUserQuery query = new CardUserQuery();
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		List<Integer> branchNums = new ArrayList<>();
		/*List<BranchDTO> branchDTOS = branchRpc.findInCache(systemBookCode);


		for (int i = 0; i < branchDTOS.size(); i++) {
			BranchDTO branchDTO = branchDTOS.get(i);
			Integer branchNum = branchDTO.getBranchNum();
			branchNums.add(branchNum);
		}*/
		branchNums.add(1);
		branchNums.add(2);
		branchNums.add(3);
		branchNums.add(4);
		branchNums.add(5);
		query.setBranchNums(branchNums);
		CardAnalysisSummaryDTO cardAnalysisSummaryDTO = reportRpc.getCardAnalysisSummaryDTO(query);

		return cardAnalysisSummaryDTO;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test54")
	public List<PosItemLogSummaryDTO> test54() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-17");
		StoreQueryCondition query = new StoreQueryCondition();
		query.setSystemBookCode("4020");
		query.setDateStart(dateFrom);
		query.setDateEnd(dateTo);

		List<PosItemLogSummaryDTO> list = posItemLogRpc.findItemBizTypeFlagSummary(query);
		return list;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test55")
	public List<ItemSummary> test55() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-08");

		String systemBookCode = "4020";
		ItemQueryDTO query = new ItemQueryDTO();
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<ItemSummary> result = posOrderRpc.findItemSum(systemBookCode,query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test56")
	public List<SaleAnalysisByPosItemDTO> test56() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-08");

		SaleAnalysisQueryData query = new SaleAnalysisQueryData();
		query.setSystemBookCode("4020");
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		List<SaleAnalysisByPosItemDTO> result = reportRpc.findSaleAnalysisByPosItems(query);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test57")				//byPage//周
	public CardSummaryPageDTO test57() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-02-01");
		Date dateTo = sdf.parse("2018-02-28");

		CardReportQuery query = new CardReportQuery();
		query.setSystemBookCode("4173");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setPaging(true);
		query.setOffset(0);
		query.setLimit(100);
		query.setSortField("printedNum");
		query.setSortType("desc");
		CardSummaryPageDTO list = posOrderRpc.findSummaryByPrintNum(query);
		return list;
	}






	//新加的四张报表
	@RequestMapping(method = RequestMethod.GET,value = "/test60")
	public List<MonthPurchaseDTO> test60() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-03-31");
		List<MonthPurchaseDTO> list = receiveOrderRpc.findPurchaseMonth(systemBookCode, 99, dateFrom,
				dateTo, AppConstants.STATE_AUDIT_TIME, AppConstants.BUSINESS_DATE_MONTH);

		return list;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test61")
	public List<MonthReturnDTO> test61() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");
		List<MonthReturnDTO> list = returnOrderRpc.findReturnMonth(systemBookCode, 99, dateFrom, dateTo, AppConstants.BUSINESS_DATE_MONTH);
		return list;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test62")
	public List<TransferOutMoneyDateDTO> test62() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");

		List<TransferOutMoneyDateDTO> list = transferOutOrderRpc.findDateSummary(systemBookCode, 99, getBranchNums(), dateFrom, dateTo, AppConstants.BUSINESS_DATE_MONTH);
		return list;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test63")
	public List<ShipOrderMoneyDateDTO> test63() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");

		List<ShipOrderMoneyDateDTO> list = shipOrderRpc.findDateSummary(systemBookCode, 99, getBranchNums(), dateFrom, dateTo, AppConstants.BUSINESS_DATE_MONTH);
		return list;
	}





	//test ArrayIndexOutOfBounds
	@RequestMapping(method = RequestMethod.GET,value = "/test64")
	public List<SalerCommissionDetail> test64() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-12-31");
		List<SalerCommissionDetail> list = report2Rpc.findItemSalerCommissionDetails(systemBookCode,dateFrom,dateTo,getBranchNums(),null);

		return list;
	}


	@RequestMapping(method = RequestMethod.GET ,value = "/test65")
	public List test65() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-12-31");
		List<AlipayDetailDTO> result = reportRpc.findAlipayDetailDTOs(systemBookCode,getBranchNums(),dateFrom,dateTo,null,null,false);
		return result;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test58")				//byPage		客单分析 历史客单分析   ok
	public CustomerAnalysisHistoryPageDTO test58() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-08");
		SaleAnalysisQueryData query = new SaleAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		//query.setBranchNums(getBranchNums());
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setOffset(0);
		query.setLimit(5);
		CustomerAnalysisHistoryPageDTO result = reportRpc.findCustomerAnalysisHistorysByPage(query);
		return result;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test59")				//byPage	毛利分析 商品毛利汇总   ok
	public ProfitByBranchAndItemSummaryPageDTO test59() throws Exception {

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-23");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(true);
		query.setOffset(0);
		query.setLimit(50);

		ProfitByBranchAndItemSummaryPageDTO result = reportRpc.findProfitAnalysisByBranchAndItemByPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test66")
	public BranchBizSummaryPageDTO test66() throws Exception{		//page  毛利分析 日毛利汇总   count 太慢
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(false);
		query.setOffset(0);
		query.setLimit(50);

		BranchBizSummaryPageDTO result = reportRpc.findProfitAnalysisDaysByPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test68")
	public List<BranchBizSummary> test68() throws Exception{   //test原接口
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-17");
		Date dateTo = sdf.parse("2018-01-18");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(true);
		query.setOffset(0);
		query.setLimit(100);

		List<BranchBizSummary> list = reportRpc.findProfitAnalysisDays(query);
		return list;

	}





	//
	@RequestMapping(method = RequestMethod.GET,value = "/test69")
	public List<SalerCommission> test69() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-23");
		Date dateTo = sdf.parse("2018-03-24");

		List<SalerCommission> result = reportRpc.findSalerCommissions(systemBookCode,dateFrom,dateTo,null,null, BigDecimal.valueOf(10));
		return result;

	}


	//  findCardConsumeAnalysis
	@RequestMapping(method = RequestMethod.GET,value = "/test70")
	public List<CardConsumeAnalysis> test70() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");

		CardConsuemAnalysisQuery query = new CardConsuemAnalysisQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);

		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		Date timeFrom = time.parse("12:00:00");
		Date timeTo = time.parse("23:59:59");
		query.setTimeFrom(timeFrom);
		query.setTimeTo(timeTo);
		query.setMoneySpace(BigDecimal.valueOf(23));
		//query.setMoneyFrom(BigDecimal.valueOf(100));
		List<CardConsumeAnalysis> cardConsumeAnalysis = reportRpc.findCardConsumeAnalysis(query);
		return cardConsumeAnalysis;
	}


	@RequestMapping(method = RequestMethod.GET,value= "/test71")
	public CardReportPageDTO test71() throws Exception{
		CardReportQuery query = new CardReportQuery();
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");

		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setOffset(0);
		query.setLimit(50);
		query.setBranchNum(1);

		CardReportPageDTO result = posOrderRpc.findByCardReportQueryPage(query);
		return result;
	}

}
