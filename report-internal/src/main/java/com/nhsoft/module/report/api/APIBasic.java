package com.nhsoft.module.report.api;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.apache.http.impl.execchain.TunnelRefusedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
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

	@Autowired
	private BookResourceRpc bookResourceRpc;
	@Autowired
	private MobileAppV2Rpc mobileAppV2Rpc;
	@Autowired
	private PosItemRpc posItemRpc;
	
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
	public List<Object[]> test7() throws Exception{
		String systemBookCode= "4344";				//含or  放在exists关键字的上面不会报错，再高一点就报错了
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-13");
		/*List<Integer> items = new ArrayList<>();
		items.add(434400126);*/
		ItemQueryDTO itemQueryDTO = new ItemQueryDTO();
		itemQueryDTO.setSystemBookCode(systemBookCode);
		itemQueryDTO.setDateFrom(dateFrom);
		itemQueryDTO.setDateTo(dateTo);
		itemQueryDTO.setItemMethod("购销");
		itemQueryDTO.setBranchNums(getBranchNums(systemBookCode));
		itemQueryDTO.setQueryKit(true);
		List<Object[]> itemSum = posOrderService.findItemSum(itemQueryDTO);
		for (int i = 0; i <itemSum.size() ; i++) {
			Object[] object = itemSum.get(i);
			System.out.println(object[0]);
		}
		return itemSum;
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
		String systemBookCode= "4020";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-21");

		/*List<Integer> items = new ArrayList<>();
		items.add(434400126);
		items.add(434400126);
		items.add(110010009);
		items.add(110010007);
*/
		SupplierSaleQuery supplierSaleQuery = new SupplierSaleQuery();
		supplierSaleQuery.setSystemBookCode(systemBookCode);
		supplierSaleQuery.setBranchNums(branchNums);
		supplierSaleQuery.setDateFrom(dateFrom);
		supplierSaleQuery.setDateTo(dateTo);
		//supplierSaleQuery.setItemNums(items);


		List<SupplierLianYing> supplierLianYing = reportService.findSupplierLianYing(supplierSaleQuery);
		return supplierLianYing;
	}

	@RequestMapping(method = RequestMethod.GET,value="/test11")
	public List<ABCAnalysis> test11() throws Exception{	//子查询    ok带  exists  也ok

		String systemBookCode= "4020";
		List<BranchDTO> all = branchRpc.findInCache(systemBookCode);
		List<Integer> branchNums = new ArrayList<Integer>();
		for (BranchDTO b : all) {
			Integer branchNum = b.getBranchNum();
			branchNums.add(branchNum);
		}
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-19");

		ABCListQuery abc = new ABCListQuery();
		abc.setSystemBookCode(systemBookCode);
		abc.setDateFrom(dateFrom);
		abc.setDateTo(dateTo);
		abc.setBranchNums(branchNums);
		/*List<String> list = new ArrayList<>();
		list.add("10");
		abc.setCategoryCodeList(list);*/
		List<String> list1 = new ArrayList<>();
		list1.add(AppConstants.CHECKBOX_SALE);
		abc.setTypes(list1);
		List<ABCAnalysis> abcDatasBySale1 = reportService.findABCDatasBySale(abc);
		return abcDatasBySale1;
	}

	@RequestMapping(method=RequestMethod.GET,value="/test12")
	public List<SaleByBranchSummary> test12() throws Exception{	//多表 有问题  关联不没有使用sharding的表positem   不报错
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");

		SaleAnalysisQueryData queryData = new SaleAnalysisQueryData();
		String systemBookCode = "4020";
		queryData.setDtFrom(dateFrom);
		queryData.setDtTo(dateTo);
		queryData.setBranchNums(getBranchNums(systemBookCode));
		queryData.setSystemBookCode("4020");
		//queryData.setSaleType("实体店");//水星微商城   实体店
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

	public List<Integer> getBranchNums(String systemBookCode){
		List<Integer> branchNums = new ArrayList();
		List<BranchDTO> branchDTOS = branchRpc.findInCache(systemBookCode);
		for (int i = 0; i < branchDTOS.size(); i++) {
			BranchDTO branchDTO = branchDTOS.get(i);
			Integer branchNum = branchDTO.getBranchNum();
			branchNums.add(branchNum);
		}
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
		//List<Integer> branchNums = getBranchNums(systemBookCode);
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

	@RequestMapping(method = RequestMethod.GET,value = "/test28")///////8
	public CustomerSummary test28() throws Exception{
		String systemBookCode = "4020";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-27");

		String branchType = "直营";//微商城
		CustomerSummary customerSummary = reportRpc.sumCustomerAnalysis(systemBookCode, dateFrom, dateTo, getBranchNums(systemBookCode), null);
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
        List<Integer> branchNums = getBranchNums(systemBookCode);


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
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		Boolean saleCrease = null;// 停售
		Boolean stockCrease = null ;// 停购
		//categorys.add("91");
		List<InventoryLostDTO> inventoryLostAnalysis = reportRpc.findInventoryLostAnalysis(systemBookCode,branchNum,dateFrom,dateTo,itemNums,AppConstants.UNIT_PURCHASE,null,categorys,saleCrease,stockCrease);
		return inventoryLostAnalysis;

	}

	@RequestMapping(method = RequestMethod.GET,value = "test39")///////6
	public List<PosOrderDTO> test39() throws Exception{

		String systemBookCode = "4444";
		PosOrderQuery query = new PosOrderQuery();
		query.setSystemBookCode("4444");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-07");
		Date dateTo = sdf.parse("2018-05-07");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setPaymentType("quan");
		BigDecimal count = BigDecimal.valueOf(1);
		query.setDelCountType(">");
		query.setDelCount(count);
		/*query.setDelMoneyType(">");
		query.setDelMoney(count);*/

		query.setOprateTimeType(">");
		query.setOprateTime(count);
		List<PosOrderDTO> settled = posOrderRpc.findSettled("4020",query,0,5000);
		return settled;
	}

	@RequestMapping(method = RequestMethod.GET,value = "test40")
	public List test40() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//findOrderDetailCompareDatasByBranchItem
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);
		//List<Integer> branchNums = getBranchNums(systemBookCode);
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
		List<Integer> branchNums = getBranchNums(systemBookCode);
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

	@RequestMapping(method = RequestMethod.GET,value = "/test47")///////
	public List<BranchSaleAnalysisSummary> test47() throws Exception{
		String systemBookCode = "4020";
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		List<Integer> branchNums = getBranchNums(systemBookCode);
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
		String systemBookCode =  "4020";
		UnsalableQuery query = new UnsalableQuery();
		query.setSystemBookCode("4020");
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setFilterOutGTInventory(true);
		query.setReceiveDate(date);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setUnit(AppConstants.UNIT_BASIC);
		query.setBranchNum(99);
		query.setIsFilterInAndOut(true);
		List<String> list = new ArrayList<>();
		list.add("6001");
		query.setCategoryCodeList(list);


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

		List<BusinessCollection> result = reportRpc.findBusinessCollectionByBranch(systemBookCode,getBranchNums(systemBookCode),dateFrom,dateTo);

		return result;

	}


	@RequestMapping(method = RequestMethod.GET,value = "/test52")
	public List<BusinessCollection> test52() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-09");

		String systemBookCode = "4020";


		List<BusinessCollection> result = reportRpc.findBusinessCollectionByBranchDay(systemBookCode,getBranchNums(systemBookCode),dateFrom,dateTo);

		return result;

	}

	@RequestMapping(method = RequestMethod.GET,value = "/test53")
	public CardAnalysisSummaryDTO test53() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-01");
		String systemBookCode = "4020";
		CardUserQuery query = new CardUserQuery();
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		query.setBranchNums(getBranchNums(systemBookCode));
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

	@RequestMapping(method = RequestMethod.GET,value = "/test55")///////7
	public List<ItemSummary> test55() throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-04-27");

		String systemBookCode = "4020";
		ItemQueryDTO query = new ItemQueryDTO();
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		//query.setItemMethod("联营");耗时184秒 dao
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
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-13");

		CardReportQuery query = new CardReportQuery();
		query.setSystemBookCode("4020");
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setPaging(false);
		query.setOffset(0);
		query.setLimit(10);
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

		List<TransferOutMoneyDateDTO> list = transferOutOrderRpc.findDateSummary(systemBookCode, 99, getBranchNums(systemBookCode), dateFrom, dateTo, AppConstants.BUSINESS_DATE_MONTH);
		return list;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test63")
	public List<ShipOrderMoneyDateDTO> test63() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");

		List<ShipOrderMoneyDateDTO> list = shipOrderRpc.findDateSummary(systemBookCode, 99, getBranchNums(systemBookCode), dateFrom, dateTo, AppConstants.BUSINESS_DATE_MONTH);
		return list;
	}





	//test ArrayIndexOutOfBounds
	@RequestMapping(method = RequestMethod.GET,value = "/test64")
	public List<SalerCommissionDetail> test64() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-12-31");
		List<SalerCommissionDetail> list = report2Rpc.findItemSalerCommissionDetails(systemBookCode,dateFrom,dateTo,getBranchNums(systemBookCode),null);

		return list;
	}


	@RequestMapping(method = RequestMethod.GET ,value = "/test65")
	public List test65() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-12-31");
		List<AlipayDetailDTO> result = reportRpc.findAlipayDetailDTOs(systemBookCode,getBranchNums(systemBookCode),dateFrom,dateTo,null,null,false);
		return result;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test58")				//byPage		客单分析 历史客单分析   ok
	public CustomerAnalysisHistoryPageDTO test58() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-10");
		SaleAnalysisQueryData query = new SaleAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setOffset(0);
		query.setLimit(100);
		query.setSortField("totalMoney");
		query.setSortType("desc");
		CustomerAnalysisHistoryPageDTO result = reportRpc.findCustomerAnalysisHistorysByPage(query);
		return result;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test59/{sortField}/{sortType}")				//byPage	毛利分析 商品毛利汇总   ok
	public ProfitByBranchAndItemSummaryPageDTO test59(@PathVariable String sortField,@PathVariable String sortType) throws Exception {

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(true);
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(46);
		query.setBranchNums(getBranchNums(systemBookCode));
		/*List<String> orderSource = new ArrayList<>();
		orderSource.add("第三方商城");
		query.setOrderSources(orderSource);*/

		List<String> list = new ArrayList<>();
		list.add("210");
		query.setPosItemTypeCodes(list);
		query.setSortField(sortField);
		query.setSortType(sortType);


		ProfitByBranchAndItemSummaryPageDTO result = reportRpc.findProfitAnalysisByBranchAndItemByPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test66/{sortField}/{sortType}")
	public BranchBizSummaryPageDTO test66(@PathVariable(value = "sortField") String sortField, @PathVariable(value = "sortType") String sortType) throws Exception{		//page  毛利分析 日毛利汇总
		String systemBookCode = "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-08");
		Date dateTo = sdf.parse("2018-05-08");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);
		query.setBranchNums(branchNums);
		query.setIsQueryCF(true);
		query.setPage(false);
		query.setOffset(0);
		query.setLimit(100);
		query.setSortField(sortField);
		query.setSortType(sortType);


		BranchBizSummaryPageDTO result = reportRpc.findProfitAnalysisDaysByPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test68")
	public List<BranchBizSummary> test68() throws Exception{   //test原接口
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-10");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(true);
		query.setOffset(0);
		query.setLimit(100);

		List<BranchBizSummary> list = reportRpc.findProfitAnalysisDays(query);
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal cost = BigDecimal.ZERO;
		BigDecimal profit = BigDecimal.ZERO;
		for (int i = 0; i <list.size() ; i++) {
			BranchBizSummary branchBizSummary = list.get(i);
			money = money.add(branchBizSummary.getMoney());
			cost = cost.add(branchBizSummary.getCost());
			profit = profit.add(branchBizSummary.getProfit());
		}
		System.out.println("money : "+money);
		System.out.println("cost : "+cost);
		System.out.println("profit : "+profit);
		return list;

	}





	//
	@RequestMapping(method = RequestMethod.GET,value = "/test69")
	public List<SalerCommission> test69() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-23");
		Date dateTo = sdf.parse("2018-03-24");
		List<Integer> brnachNums = new ArrayList<>();
		brnachNums.add(99);

		List<String> saleNames = new ArrayList<>();
		saleNames.add("");

		List<SalerCommission> result = reportRpc.findSalerCommissions(systemBookCode,dateFrom,dateTo,brnachNums,saleNames, BigDecimal.valueOf(20));

		Integer integer0= 0;
		Integer integer1 = 0;
		Integer integer2 = 0;
		for (int i = 0; i <result.size() ; i++) {
			SalerCommission salerCommission = result.get(i);
			List<Integer> rank = salerCommission.getRank();
			integer0 += rank.get(0);
			integer1 += rank.get(1);
			integer2 += rank.get(2);
		}
		System.out.println(integer0);
		System.out.println(integer1);
		System.out.println(integer2);

		return result;

	}


	@RequestMapping(method = RequestMethod.GET,value = "/test699")
	public List<SalerCommission> test699() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-23");
		Date dateTo = sdf.parse("2018-03-24");
		List<Integer> brnachNums = new ArrayList<>();
		brnachNums.add(99);

		List<String> saleNames = new ArrayList<>();
		saleNames.add("");

		List<SalerCommission> result = reportRpc.findSalerCommissions(systemBookCode,dateFrom,dateTo,brnachNums,saleNames, BigDecimal.valueOf(20.0));

		Integer integer0= 0;
		Integer integer1 = 0;
		Integer integer2 = 0;
		for (int i = 0; i <result.size() ; i++) {
			SalerCommission salerCommission = result.get(i);
			List<Integer> rank = salerCommission.getRank();
			integer0 += rank.get(0);
			integer1 += rank.get(1);
			integer2 += rank.get(2);
		}
		System.out.println(integer0);
		System.out.println(integer1);
		System.out.println(integer2);

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
		query.setMoneySpace(BigDecimal.valueOf(100));
		query.setMoneyFrom(BigDecimal.valueOf(200));
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
		query.setSortField("orderPoint");
		query.setSortType("desc");

		CardReportPageDTO result = posOrderRpc.findByCardReportQueryPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value= "/test72")
	public List<PurchaseCycleSummary> test72() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-30");
		List<PurchaseCycleSummary> purchaseCycleByBiz = reportRpc.findPurchaseCycleByBiz(systemBookCode,dateFrom,dateTo,null);

		return purchaseCycleByBiz;
	}

	@RequestMapping(method = RequestMethod.GET,value= "/test73")
	public List<TransferItemDetailSummary> test73()throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-03-31");
		List<TransferItemDetailSummary> transferItemTop = reportRpc.findTransferItemTop(systemBookCode,99,null,dateFrom,dateTo,null,"transferQty");

		BigDecimal transferQty = BigDecimal.ZERO;
		BigDecimal transferMoney = BigDecimal.ZERO;
		BigDecimal receiveQty = BigDecimal.ZERO;
		BigDecimal requestQty = BigDecimal.ZERO;
		for (int i = 0; i <transferItemTop.size() ; i++) {
			TransferItemDetailSummary transferItemDetailSummary = transferItemTop.get(i);
			transferQty = transferQty.add(transferItemDetailSummary.getTransferQty() == null ? BigDecimal.ZERO : transferItemDetailSummary.getTransferQty());
			transferMoney = transferMoney.add(transferItemDetailSummary.getTransferMoney() == null ? BigDecimal.ZERO : transferItemDetailSummary.getTransferMoney());
			receiveQty = receiveQty.add(transferItemDetailSummary.getReceiveQty() == null ? BigDecimal.ZERO : transferItemDetailSummary.getReceiveQty());
			requestQty = requestQty.add(transferItemDetailSummary.getRequestQty() == null ? BigDecimal.ZERO : transferItemDetailSummary.getRequestQty());
		}
		System.out.println("配送数量： "+transferQty);
		System.out.println("配送金额： "+transferMoney);
		System.out.println("到货数量： "+receiveQty);
		System.out.println("要货数量： "+requestQty);
		return  transferItemTop;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test74")///////10
	public Object test74() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-02-01");
		Date dateTo = sdf.parse("2018-04-30");
		RetailDetailQueryData query = new RetailDetailQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setDtFromShiftTable(dateFrom);
		query.setDtToShiftTable(dateTo);
		/*List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);*/
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(50);
		query.setSortField("posItemCode");
		query.setSortType("desc");
		RetailDetailPageSummary retailDetailsByPage = reportRpc.findRetailDetailsByPage(query);
		return retailDetailsByPage;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test75")
	public Object test75() throws Exception{
		String systemBookCode = "4173";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-04");
		Date dateTo = sdf.parse("2018-04-04");
		SaleAnalysisQueryData query = new SaleAnalysisQueryData();
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setOffset(0);
		query.setLimit(20);
		query.setSortField("categoryCode");
		query.setSortType("asc");
		//query.setIsQueryCF(true);
		//query.setIsQueryGrade(true);
		query.setPage(false);
		SaleAnalysisBranchItemPageSummary result = reportRpc.findSaleAnalysisByBranchPosItemsByPage(query);
 		return result;
	}

	@Autowired
	private BookResourceService bookResourceService;

	@RequestMapping(method = RequestMethod.GET, value = "/test76")
	public void test76() {
		List<PosItemTypeParam> bookResource = bookResourceService.findPosItemTypeParamsInCache("4020");
		System.out.println();
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test77")
	public List<ItemInventoryTrendSummary> test77(){

		String systemBookCode = "4020";
		ItemInventoryQueryDTO query = new ItemInventoryQueryDTO();
		query.setSystemBookCode(systemBookCode);
		Integer inventoryAmount = 0;
		Integer unInventoryAmount = 0;
		List<ItemInventoryTrendSummary> itemTrendInventory = reportRpc.findItemTrendInventory(query);
		for (int i = 0; i < itemTrendInventory.size(); i++) {
			ItemInventoryTrendSummary itemInventoryTrendSummary = itemTrendInventory.get(i);
			inventoryAmount = inventoryAmount + itemInventoryTrendSummary.getInventoryAmount();
			unInventoryAmount =  unInventoryAmount + itemInventoryTrendSummary.getUnInventoryAmount();
		}
		System.out.println(inventoryAmount);
		System.out.println(unInventoryAmount);
		return itemTrendInventory;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test78")
	public List<AlipayDetailDTO> test78() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-10");


		AlipayDetailQuery query = new AlipayDetailQuery();
		query.setSystemBookCode(systemBookCode);
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(1);
		query.setBranchNums(branchNums);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setQueryAll(false);
		query.setOrderState(false);
		List<AlipayDetailDTO> alipayDetailDTOs = reportRpc.findAlipayDetailDTOs(query);

		return alipayDetailDTOs;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test79")
	public List<ProfitAnalysisByItemSummary> test79() throws Exception{
		List<BranchDTO> all = branchRpc.findInCache("4344");


		String systemBookCode = "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-07");
		Date dateTo = sdf.parse("2018-03-07");

		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		List<Integer> itemNums = new ArrayList<>();
		itemNums.add(434400819);
		query.setPosItemNums(itemNums);
		query.setIsQueryCF(false);
		/*query.setQueryClient(true);
		List<String> list = new ArrayList<>();
		list.add("1111");
		List<String> codes = new ArrayList<>();
		codes.add("2222");
		query.setBrandCodes(codes);
		query.setClientFids(list);*/
		List<String> itemType = new ArrayList<>();
		List<PosItemTypeParam> posItemTypeParamsInCache = bookResourceService.findPosItemTypeParamsInCache("4344");
		for (int i = 0; i <posItemTypeParamsInCache.size() ; i++) {
			PosItemTypeParam posItemTypeParam = posItemTypeParamsInCache.get(i);
			itemType.add(posItemTypeParam.getPosItemTypeCode());
		}
		query.setPosItemTypeCodes(itemType);

		List<ProfitAnalysisByItemSummary> profitAnalysisByItem = reportRpc.findProfitAnalysisByItem(query);
		return profitAnalysisByItem;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test80")
	public PromotionItemPageDTO test80() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-18");
		Date dateTo = sdf.parse("2018-04-23");

		PolicyAllowPriftQuery query = new PolicyAllowPriftQuery();

		query.setSystemBookCode(systemBookCode);
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);
		query.setBranchNums(branchNums);
		query.setPromotion(false);
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(50);
		query.setSortField("itemCode");
		query.setSortType("desc");
		/*List<String> list = new ArrayList<>();
		list.add("aa");
		query.setCategoryCodes(list);*/
		/*List<String> sellers = new ArrayList<>();
		sellers.add("");
		query.setOrderSellers(sellers);*/
		//query.setProfitType("超额折扣");//0
		//query.setProfitType("超量特价");//0
		//query.setProfitType("超额奖励");//0
		//query.setProfitType("赠送促销");//0
		//query.setProfitType("促销特价");//3

		//促销特价



		PromotionItemPageDTO result = posOrderRpc.findPromotionItemsByPage(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test81")
	public Object test81() throws Exception{

		String systemBookCode = "4173";			//门店营业分析-门店商品汇总
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-28");
		Date dateTo = sdf.parse("2018-04-28");

		BranchProfitQuery query = new BranchProfitQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setBranchNum(99);
		query.setFilterDel(true);
		query.setOffset(0);
		query.setLimit(50);
		query.setSortField("itemNum");
		query.setSortType("DESC");
		query.setQueryKit(true);
		/*List<String> categorys = new ArrayList<>();
		categorys.add("11");
		query.setCategoryCodeList(categorys);*/

		BranchProfitDataPageDTO branchAndItemProfit = reportRpc.findBranchAndItemProfit(query);

		return branchAndItemProfit;

	}

	@RequestMapping(method = RequestMethod.GET,value = "/test83")
	public TransferProfitByPosItemPageDTO test83() throws Exception{
		String systemBookCode = "4173";			//直调查询-门店商品汇总
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-04-10");
		TransferProfitQuery query = new TransferProfitQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setResponseBranchNums(getBranchNums(systemBookCode));
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(10);
		query.setSortField("saleProfit");
		query.setSortType("ASC");
		TransferProfitByPosItemPageDTO result = reportRpc.findTransferProfitByPosItemBranch(query);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test84/{sortType}")
	public TransferProfitByPosItemDetailPageDTO test84(@PathVariable(value = "sortType") String sortType) throws Exception{
		String systemBookCode = "4020";			//直调查询-商品明细
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		TransferProfitQuery query = new TransferProfitQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setResponseBranchNums(getBranchNums(systemBookCode));
		query.setSortField("costUnitPrice");
		query.setSortType(sortType);
		query.setOffset(0);
		query.setLimit(10);
		TransferProfitByPosItemDetailPageDTO result = reportRpc.findTransferProfitByPosItemDetail(query);

		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test85")
	public InventoryProfitPageDTO test85() throws Exception{
		String systemBookCode = "4020";			//损益统计报表     商品汇总
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/*Date dateFrom = sdf.parse("2018-05-01");
		Date dateTo = sdf.parse("2018-05-31");*/

		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-30");

		InventoryProfitQuery query = new InventoryProfitQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);
		query.setBranchNums(branchNums);
		query.setUnit("基本单位");
		query.setIsChechZero(true);
		List<String> reasons = new ArrayList<>();
		reasons.add("原料领用单");
		//reasons.add("盘点单");
		query.setReasons(reasons);
		query.setCheckType("调整单");
		//query.setCheckType("盘盈");


		query.setPage(true);
		query.setOffset(0);
		query.setLimit(10000);
		query.setSortField("itemCode");
		query.setSortType("asc");
		query.setStoreNum(402011281);
		InventoryProfitPageDTO result = reportRpc.findInventoryProfit(query);

		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test86/{sortType}")
	public InventoryProfitPageDTO test86(@PathVariable(value = "sortType") String sortType) throws Exception{
		String systemBookCode = "4173";			//损益统计报表    类别汇总
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");

		InventoryProfitQuery query = new InventoryProfitQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setUnit("基本单位");
		query.setIsChechZero(true);
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(10000);
		query.setSortField("profitQty");
		query.setSortType(sortType);
		InventoryProfitPageDTO result = reportRpc.findInventoryProfitSum(query);

		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test82")///////9
	public List<CustomerAnalysisTimePeriod> test82() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-04-27");

		List<CustomerAnalysisTimePeriod> result = report2Rpc.findCustomerAnalysisTimePeriods(systemBookCode,dateFrom,dateTo,getBranchNums(systemBookCode),getBranchNums(systemBookCode),null,null,null);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test90")
	public List<BusinessCollection> test90() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");

		List<BusinessCollection> businessCollectionByMerchantDay = reportRpc.findBusinessCollectionByMerchantDay(systemBookCode,null,null,dateFrom,dateTo);
		return businessCollectionByMerchantDay;
	}




	@RequestMapping(method = RequestMethod.GET,value = "/test91")
	public List<BusinessCollection> test91() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");

		List<BusinessCollection> businessCollectionByTerminal = reportRpc.findBusinessCollectionByTerminal(systemBookCode, getBranchNums(systemBookCode), dateFrom, dateTo);
		return businessCollectionByTerminal;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test92")
	public List<BusinessCollection> test92() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");


		List<BusinessCollection> businessCollectionByShiftTable = reportRpc.findBusinessCollectionByShiftTable(systemBookCode, getBranchNums(systemBookCode), dateFrom, dateTo, null);
		return businessCollectionByShiftTable;
	}





	@RequestMapping(method = RequestMethod.GET,value = "/test93")
	public List<BusinessCollection> test93() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");


		List<BusinessCollection> businessCollectionByShiftTable = reportRpc.findBusinessCollectionByShiftTable(systemBookCode, 99,99, dateFrom, dateTo, null);
		return businessCollectionByShiftTable;
	}



	@RequestMapping(method = RequestMethod.GET,value = "/test94")
	public List<BusinessCollection> test94() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");


		List<BusinessCollection> businessCollectionByShiftTable = reportRpc.findBusinessCollectionByMerchant(systemBookCode, 99, null,dateFrom, dateTo);
		return businessCollectionByShiftTable;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test95")
	public List<BusinessCollection> test95() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-01");
		Date dateTo = sdf.parse("2018-04-12");


		List<BusinessCollection> businessCollectionByShiftTable = reportRpc.findBusinessCollectionByStall(systemBookCode, 99,null,null, dateFrom, dateTo);
		return businessCollectionByShiftTable;
	}

	//findABCDatasBySale

	@RequestMapping(method = RequestMethod.GET,value = "/test96")
	public List<ABCAnalysis> test96() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-18");
		Date dateTo = sdf.parse("2018-04-23");

		ABCListQuery query = new ABCListQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<String> list = new ArrayList<>();
		list.add("本店调出");
		list.add("本店批发");
		query.setTypes(list);

		List<ABCAnalysis> abcDatasBySale = reportRpc.findABCDatasBySale(query);
		return abcDatasBySale;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test97")
	public List<ABCAnalysis> test97() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-18");
		Date dateTo = sdf.parse("2018-04-23");

		ABCListQuery query = new ABCListQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<String> list = new ArrayList<>();
		list.add("本店调出");
		list.add("本店批发");
		query.setTypes(list);

		List<ABCAnalysis> abcDatasBySale = reportRpc.findABCDatasByProfit(query);
		return abcDatasBySale;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test98/{sys}")
	public List test98() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-04-18");
		Date dateTo = sdf.parse("2018-04-23");
		ShipGoodsQuery query = new ShipGoodsQuery();
		query.setSystemBookCode(systemBookCode);
		query.setBranchNum(99);
		List<ToShip> toShip = reportRpc.findToShip(query);
		return toShip;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test99")///////2
	public List<BranchSaleAnalysisSummary> test99() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		List<BranchSaleAnalysisSummary> result = reportRpc.findMonthSaleAnalysis(systemBookCode,getBranchNums(systemBookCode),dateFrom,dateTo,2);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test100")///////3
	public List<SupplierComplexReportDetailDTO> test100() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		SupplierSaleQuery query =new SupplierSaleQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		List<SupplierComplexReportDetailDTO> result = reportRpc.findSupplierSaleDetailDatas(query);
		return result;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test101")///////4
	public List<TransferGoal> test101() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2017-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		List<TransferGoal> result = reportRpc.findTransferSaleGoalByDate(systemBookCode,99,getBranchNums(systemBookCode),dateFrom,dateTo,AppConstants.BUSINESS_DATE_SOME_DATE);
		return result;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test102")///////5
	public List<PosItemLogSummaryDTO> test102() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		StoreQueryCondition query = new StoreQueryCondition();
		query.setSystemBookCode(systemBookCode);
		query.setDateStart(dateFrom);
		query.setDateEnd(dateTo);
		List<String> list = new ArrayList<>();
		list.add("11");
		query.setItemCategoryCodes(list);

		List<PosItemLogSummaryDTO> result = posItemLogRpc.findBranchFlagSummary(query);

		return result;
	}


	//findCustomerAnalysisBranch
	@RequestMapping(method = RequestMethod.GET,value = "/test103")
	public List test103() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		String saleType = "";
		List<BranchCustomerSummary> result = reportRpc.findCustomerAnalysisBranch(systemBookCode,dateFrom,dateTo,getBranchNums(systemBookCode),saleType);
		return result;
	}

	//findItemSaleQty
	@RequestMapping(method = RequestMethod.GET,value = "/test104")
	public List test104() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		List<ItemSaleQtySummary> result = reportRpc.findItemSaleQty(systemBookCode,99,dateFrom,dateTo,true,true,true);
		return result;
	}


	//findPurchaseAndTransferDTOs
	@RequestMapping(method = RequestMethod.GET,value = "/test106")
	public List test106() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-01-01");
		Date dateTo = sdf.parse("2018-04-27");
		List<PurchaseAndTransferDetailDTO> result = reportRpc.findPurchaseAndTransferDetailDTOs(systemBookCode, 99, dateFrom, dateTo, null, null, null, AppConstants.UNIT_USE);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test107/{sortField}/{sortType}")				//byPage	毛利分析 商品毛利汇总   ok
	public ProfitByBranchAndItemSummaryPageDTOV2 test107(@PathVariable String sortField,@PathVariable String sortType) throws Exception {

		String systemBookCode = "4344";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-08");
		Date dateTo = sdf.parse("2018-05-08");
		ProfitAnalysisQueryData query = new ProfitAnalysisQueryData();
		query.setSystemBookCode(systemBookCode);
		query.setShiftTableFrom(dateFrom);
		query.setShiftTableTo(dateTo);
		query.setIsQueryCF(true);
		query.setPage(true);
		query.setOffset(0);
		query.setLimit(50);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setSortField(sortField);
		query.setSortType(sortType);


		ProfitByBranchAndItemSummaryPageDTOV2 result = reportRpc.findProfitAnalysisByBranchAndItemByPageV2(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test108")
	public List test108(){
		InventoryAnalysisQuery query = new InventoryAnalysisQuery();
		query.setSystemBookCode("4344");
		query.setBranchNum(99);
		query.setIsShowAll(true);
		query.setIndexUnit(0);
		query.setFindCount(false);
		query.setRule1(false);
		query.setRule2(false);
		query.setRule3(false);
		query.setRule4(false);
		query.setRule5(false);
		query.setRule6(true);
		query.setSuggestionType(3);
		query.setLastDays(28);
		query.setStorehouseNum(434400017);
		List<InventoryAnalysisDTO> result = reportRpc.findInventoryAnalysiss(query,null);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test109")
	public List test109() throws Exception{

		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		PolicyPosItemQuery query = new PolicyPosItemQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		List<TransferPolicyDTO> result = report2Rpc.findTransferPolicyDTOs(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test110")
	public List test110() throws Exception{

		InventoryExceptQuery query = new InventoryExceptQuery();
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		query.setSystemBookCode(systemBookCode);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setBranchNum(99);

		List<SingularPrice> result = reportRpc.findSingularPrice(query);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test111")
	public List test111() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		PolicyAllowPriftQuery query = new PolicyAllowPriftQuery();
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		query.setBranchNum(99);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setProfitType("全部");
		query.setPromotion(true);
		List<ItemRebatesSummary> result = reportRpc.findItemRebates(query);
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;

		for (int i = 0; i < result.size(); i++) {
			ItemRebatesSummary itemRebatesSummary = result.get(i);
			amount = amount.add(itemRebatesSummary.getAmount());
			money = money.add(itemRebatesSummary.getMoney());
			discount = discount.add(itemRebatesSummary.getMoney());
		}
		System.out.println("--------------------------");
		System.out.println("amount: "+ amount);
		System.out.println("money: "+money);
		System.out.println("discount: "+discount);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test112")
	public List test112() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		PolicyAllowPriftQuery query = new PolicyAllowPriftQuery();
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		query.setBranchNum(99);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setProfitType("全部");
		query.setPromotion(true);
		List<RebatesDetailSummary> result = reportRpc.findRebatesDetail(query);
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal money = BigDecimal.ZERO;
		BigDecimal discount = BigDecimal.ZERO;
		for (int i = 0; i < result.size(); i++) {
			RebatesDetailSummary rebatesDetailSummary = result.get(i);
			 amount = amount.add(rebatesDetailSummary.getOrderDetailAmount());
			 money =money.add(rebatesDetailSummary.getOrderDetailPaymentMoney());
			discount = discount.add(rebatesDetailSummary.getOrderDetailDiscount());
		}
		System.out.println("--------------------------");
		System.out.println("amount: "+ amount);
		System.out.println("money: "+money);
		System.out.println("discount: "+discount);
		return result;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test113")
	public RebatesSumSummary test113() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		PolicyAllowPriftQuery query = new PolicyAllowPriftQuery();
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		query.setSystemBookCode(systemBookCode);
		query.setBranchNum(99);
		query.setBranchNums(getBranchNums(systemBookCode));
		query.setProfitType("全部");
		query.setPromotion(true);
		RebatesSumSummary result = reportRpc.findRebatesSum(query);
		BigDecimal amount = result.getAmount();
		BigDecimal money = result.getMoney();
		BigDecimal discount = result.getDiscount();
		System.out.println("--------------------------");
		System.out.println("amount: "+ amount);
		System.out.println("money: "+money);
		System.out.println("discount: "+discount);
		return result;
	}
	@RequestMapping(method = RequestMethod.GET,value = "/test114")
	public List<OtherInfoDTO> test114() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		Integer branchNum = 99;
		List<OtherInfoDTO> result = report2Rpc.findOtherInfoDetails(systemBookCode,branchNum,dateFrom,dateTo,"删除");
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test115")
	public List test115() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		StoreQueryCondition query = new StoreQueryCondition();
		query.setSystemBookCode(systemBookCode);
		query.setDateStart(dateFrom);
		query.setDateEnd(dateTo);
		List<Integer> list = new ArrayList<>();
		list.add(99);
		query.setBranchNums(list);
		List test = posItemLogRpc.test(query);
		return test;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test116")
	public AccountPayDTO test116() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-03-01");
		Date dateTo = sdf.parse("2018-03-31");
		//findAccountPays
		AccountPayDTO result = mobileAppV2Rpc.findAccountPays(systemBookCode,99,dateFrom,dateTo);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test117")
	public List<NameAndValueDTO> test117() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-01");
		Date dateTo = sdf.parse("2018-05-31");
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);
		//findAccountPays
		List<NameAndValueDTO> branchCardDeposit = mobileAppV2Rpc.findBranchCardDeposit(systemBookCode, branchNums, dateFrom, dateTo);
		return branchCardDeposit;
	}


	@RequestMapping(method = RequestMethod.GET,value = "/test118")
	public List<CardReportDTO> test118() throws Exception{
		String systemBookCode = "4020";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse("2018-05-01");
		Date dateTo = sdf.parse("2018-05-31");
		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(99);

		List<CardReportDTO> cardReportByBranch = mobileAppV2Rpc.findCardReportByBranch(systemBookCode,branchNums,dateFrom,dateTo);
		return cardReportByBranch;
	}

	@RequestMapping(method = RequestMethod.GET,value = "/test119")
	public List test119() throws Exception{
		String systemBookCode = "4020";

		List<PosItemDTO> properties = posItemRpc.findProperties(systemBookCode, null, "itemName", "itemCode", "itemBarcode", "itemStatus");
		return properties;
	}



	private String systemBookCode = "4020";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static Date dateFrom = null;
	private static Date dateTo = null;
	private static void getDate(){
		try {
			dateFrom = sdf.parse("2018-05-01");
			dateTo = sdf.parse("2018-05-07");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	//以下是移除posItemService.findShortItems()方法的测试
	@RequestMapping(method = RequestMethod.GET,value = "/test120")
	public List test120(){
		SaleAnalysisQueryData queryData = new SaleAnalysisQueryData();
		queryData.setSystemBookCode(systemBookCode);
		queryData.setDtFrom(dateFrom);
		queryData.setDtTo(dateTo);
		queryData.setBranchNums(getBranchNums(systemBookCode));
		List<SalePurchaseProfitDTO> result = reportRpc.findSalePurchaseProfitDTOsByItem(queryData);
		return result;
	}

	//findSalePurchaseProfitDTOsByCategory
	@RequestMapping(method = RequestMethod.GET,value = "/test121")
	public List test121(){
		SaleAnalysisQueryData queryData = new SaleAnalysisQueryData();
		queryData.setSystemBookCode(systemBookCode);
		queryData.setDtFrom(dateFrom);
		queryData.setDtTo(dateTo);
		queryData.setBranchNums(getBranchNums(systemBookCode));
		List<SalePurchaseProfitDTO> result = reportRpc.findSalePurchaseProfitDTOsByCategory(queryData);
		return result;
	}

	//findSalePurchaseProfitDTOsByBranchCategory
	@RequestMapping(method = RequestMethod.GET,value = "/test122")
	public List test122(){
		SaleAnalysisQueryData queryData = new SaleAnalysisQueryData();
		queryData.setSystemBookCode(systemBookCode);
		queryData.setDtFrom(dateFrom);
		queryData.setDtTo(dateTo);
		queryData.setBranchNums(getBranchNums(systemBookCode));
		List<SalePurchaseProfitDTO> result = reportRpc.findSalePurchaseProfitDTOsByBranchCategory(queryData);
		return result;
	}

	//test40   findOrderDetailCompareDatasByBranchItem

	//test107

	//test81

	//test83

	//test85

	//test86

	//findLatestReceiveDetail
	@RequestMapping(method = RequestMethod.GET,value = "/test123")
	public List test123(){
		PurchaseOrderCollectQuery query = new PurchaseOrderCollectQuery();
		query.setSystemBookCode(systemBookCode);
		query.setDtFrom(dateFrom);
		query.setDtTo(dateTo);
		List<PurchaseOrderCollect> result = report2Rpc.findLatestReceiveDetail(query);
		return result;
	}


}
