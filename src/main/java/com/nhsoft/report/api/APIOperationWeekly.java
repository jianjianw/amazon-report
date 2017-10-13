package com.nhsoft.report.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;









import com.nhsoft.report.api.dto.OperationDTO;
import com.nhsoft.report.dto.BranchConsumeReport;
import com.nhsoft.report.dto.BranchDepositReport;
import com.nhsoft.report.dto.BranchMoneyReport;
import com.nhsoft.report.dto.CardUserCount;
import com.nhsoft.report.dto.CheckMoney;
import com.nhsoft.report.dto.DifferenceMoney;
import com.nhsoft.report.dto.LossMoneyReport;
import com.nhsoft.report.dto.SaleMoneyGoals;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;

@RestController()
@RequestMapping("/weekly")
public class APIOperationWeekly {

	@Autowired
	private ReportRpc reportRpc;
	
	/**
	 * 周营运分析
	 * @param systemBookCode 帐套号
	 * @param branchNums     分店号
	 * @param week           周
	 * @return 
	 */
	
	@RequestMapping(method = RequestMethod.GET, value = "/json")
	public @ResponseBody List listOperation (@RequestHeader("systemBookCode") String systemBookCode,
		@RequestHeader("branchNums") String branchNums, @RequestHeader("week") int week) {
		
		//得到将要查询周的开始日期和结束日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式
		Date today = new Date();                                       
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		
		calendar.add(Calendar.DAY_OF_MONTH, -(week-1)*7);        
		String specifiedDateStr = sdf.format(calendar.getTime());         
		
		Date specifiedDate = null;
		try {
			specifiedDate = sdf.parse(specifiedDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date[] date = getMonday(specifiedDate);
		
		//根据帐套号查询所有分店号
		List<Branch> branchList = reportRpc.findAll(systemBookCode);
		List<Integer> branchNumList = new ArrayList<Integer>();
		for(int j = 0; j < branchList.size(); j++) {
			branchNumList.add(branchList.get(j).getId().getBranchNum());
		}
		
		//根据所有分店查询上一天的营业额
		List<BranchMoneyReport> branchMoneyReportListPre = null;
		//根据所有分店查询营业额  非会员
		List<BranchMoneyReport> branchMoneyReportList = null;
		//根据所有分店查询营业额  会员
		List<BranchMoneyReport> memeberBranchMoneyReportList = null;
		//根据分店查询卡消费
		List<BranchConsumeReport> branchConsumeReportList = null;
		//根据分店查询报损金额
		List<LossMoneyReport> lossMoneyList = null;
		//根据分店查询卡存款
		List<BranchDepositReport> branchDepositReportList = null;
		//根据分店查询配销差额
		List<DifferenceMoney> differentMoneyList = null;
		//根据分店查询新增会员数
		List<CardUserCount> increasedMemberList = null;
		//根据分店查询营业额目标
		List<SaleMoneyGoals> salesMoneyGoalList = null;
		//根据分店查询盘损金额
		List<CheckMoney> checkMoneyList = null;
		
		if(branchNums.equals("")) {
			//根据帐套号得到所有区域号和区域名称
			List<BranchRegion> branchRegionList = reportRpc.findBranchRegion(systemBookCode);
			String[] branchRegionName = new String[branchRegionList.size()];
			List<Integer> branchRegionNumList = new ArrayList<Integer>();
			for(int i = 0; i < branchRegionList.size(); i++) {
				branchRegionNumList.add(branchRegionList.get(i).getBranchRegionNum());
				branchRegionName[i] = branchRegionList.get(i).getBranchRegionName();
			}
			
			//根据区域号得到多组分店号
			List<Branch>[] branchListArea =new ArrayList[branchRegionNumList.size()];
			List<Integer>[] branchNumsListArea = new ArrayList[branchRegionNumList.size()];
			for(int i = 0; i < branchRegionNumList.size(); i ++) {
				branchListArea[i] = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(i));
				branchNumsListArea[i] = new ArrayList<Integer>();
				for(int j = 0; j < branchListArea[i].size(); j++) {
					branchNumsListArea[i].add(branchListArea[i].get(j).getId().getBranchNum());
				}
			}
			
			branchMoneyReportListPre = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, date[0], date[1], false);
			branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, date[0], date[1], false);
			memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, date[0], date[1], true);
			branchConsumeReportList = reportRpc.findConsumeByBranch(systemBookCode, branchNumList, date[0], date[1]);
			lossMoneyList = reportRpc.findLossMoneyByBranch(systemBookCode, branchNumList, date[0], date[1]);
			branchDepositReportList = reportRpc.findDepositByBranch(systemBookCode, branchNumList, date[0], date[1]);
			differentMoneyList = reportRpc.findDifferenceMoneyByBranch(systemBookCode, branchNumList, date[0], date[1]);		
			increasedMemberList = reportRpc.findCardUserCountByBranch(systemBookCode, branchNumList, date[0], date[1]);	
			salesMoneyGoalList = reportRpc.findSaleMoneyGoalsByBranch(systemBookCode, branchNumList, date[0], date[1], AppConstants.BUSINESS_DATE_SOME_MONTH);	
			checkMoneyList = reportRpc.findCheckMoneyByBranch(systemBookCode, branchNumList, date[0], date[1]);
			
			//按区域分组，返回数据
			OperationDTO dto = null;
			List<OperationDTO> listDTO = new ArrayList<OperationDTO>();
			List<Integer> listBranchInArea0 = new ArrayList<Integer>();        //保存有区域号的所有分店号
			List<Integer> listBranchInArea1 = new ArrayList<Integer>();        //保存有区域号的所有分店号
			List<Integer> listBranchInArea2 = new ArrayList<Integer>();        //保存有区域号的所有分店号
			List<Integer> listBranchInArea3 = new ArrayList<Integer>();        //保存有区域号的所有分店号
			List<Integer> listBranchInArea4 = new ArrayList<Integer>();
			List<Integer> listBranchInArea5 = new ArrayList<Integer>();
			List<Integer> listBranchInArea6 = new ArrayList<Integer>();
			List<Integer> listBranchInArea7 = new ArrayList<Integer>();        //新增会员分店号
			List<Integer> listBranchInArea8 = new ArrayList<Integer>();        //营业额目标
			List<Integer> listBranchInArea9 = new ArrayList<Integer>();        //盘损金额
			for(int i = 0; i < branchNumsListArea.length + 1; i++) {
				dto = new OperationDTO();
				String branchString = null;                       //String类型的分店号
				BigDecimal preBizMoney = new BigDecimal("0");     //昨天营业额
				BigDecimal bizMoney = new BigDecimal("0");        //营业额
				BigDecimal orderCount = new BigDecimal("0");      //客单量
				BigDecimal profit = new BigDecimal("0");          //毛利
				BigDecimal deposit = new BigDecimal("0");         //卡存款
				BigDecimal consume = new BigDecimal("0");         //卡消费
				BigDecimal lossMoney = new BigDecimal("0");       //报损金额
				BigDecimal differentMoney = new BigDecimal("0");  //配销差额
				BigDecimal memberBizMoney = new BigDecimal("0");  //会员营业额
				BigDecimal memberOrderCount = new BigDecimal("0");//会员客单量
				BigDecimal memberProfit = new BigDecimal("0");    //会员毛利
				BigDecimal incressedMember = new BigDecimal("0"); //新增会员数
				BigDecimal salesGoalMoney = new BigDecimal("0");  //目标营业额
				BigDecimal checkMoney = new BigDecimal("0");      //盘损金额
				if(i < branchNumsListArea.length) {
					branchString  = getBranchString(branchNumsListArea[i]);        //得到string类型的分店号
					for(int j = 0; j < branchMoneyReportListPre.size(); j++) {
						if(branchNumsListArea[i].contains(branchMoneyReportListPre.get(j).getBranchNum())) {
							listBranchInArea0.add(branchMoneyReportListPre.get(j).getBranchNum());
							preBizMoney = preBizMoney.add(branchMoneyReportListPre.get(j).getBizMoney());
						}
					}
					for(int j = 0; j < branchMoneyReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchMoneyReportList.get(j).getBranchNum())) {
							listBranchInArea1.add(branchMoneyReportList.get(j).getBranchNum());
							bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
							orderCount = orderCount.add(new BigDecimal(branchMoneyReportList.get(j).getOrderCount()));
							profit = profit.add(branchMoneyReportList.get(j).getProfit());
						}
					}
					for(int j = 0; j < memeberBranchMoneyReportList.size(); j++) {
						if(branchNumsListArea[i].contains(memeberBranchMoneyReportList.get(j).getBranchNum())) {
							listBranchInArea2.add(memeberBranchMoneyReportList.get(j).getBranchNum());
							memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
							memberOrderCount = memberOrderCount.add(new BigDecimal(memeberBranchMoneyReportList.get(j).getOrderCount()));
							memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
						}
					}
					for(int j = 0; j < branchConsumeReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchConsumeReportList.get(j).getBranchNum())) {
							listBranchInArea3.add(branchConsumeReportList.get(j).getBranchNum());
							consume = consume.add(branchConsumeReportList.get(j).getConsume());
						}
					}
					for(int j = 0; j < lossMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(lossMoneyList.get(j).getBranchNum())) {
							listBranchInArea4.add(lossMoneyList.get(j).getBranchNum());
							lossMoney = lossMoney.add(lossMoneyList.get(j).getMoney());
						}
					}
					for(int j = 0; j < branchDepositReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchDepositReportList.get(j).getBranchNum())) {
							listBranchInArea5.add(branchDepositReportList.get(j).getBranchNum());
							deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
						}
					}
					for(int j = 0; j < differentMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(differentMoneyList.get(j).getBranchNum())) {
							listBranchInArea6.add(differentMoneyList.get(j).getBranchNum());
							differentMoney = differentMoney.add(differentMoneyList.get(j).getMoney());
						}
					}
					for(int j = 0; j < increasedMemberList.size(); j++) {
						if(branchNumsListArea[i].contains(increasedMemberList.get(j).getBranchNum())) {
							listBranchInArea7.add(increasedMemberList.get(j).getBranchNum());
							incressedMember = incressedMember.add(new BigDecimal(increasedMemberList.get(j).getCount()));
						}
					}
					for(int j = 0; j < salesMoneyGoalList.size(); j++) {
						if(branchNumsListArea[i].contains(salesMoneyGoalList.get(j).getBranchNum())) {
							listBranchInArea8.add(salesMoneyGoalList.get(j).getBranchNum());
							salesGoalMoney = salesGoalMoney.add(salesMoneyGoalList.get(j).getSaleMoney());
						}
					}
					for(int j = 0; j < checkMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(checkMoneyList.get(j).getBranchNum())) {
							listBranchInArea9.add(checkMoneyList.get(j).getBranchNum());
							checkMoney = checkMoney.add(checkMoneyList.get(j).getMoney());
						}
					}
				} else {
					List<Integer> branchNoAreaList = listBranchNoArea(branchList);
					branchString = getBranchString(branchNoAreaList);        //得到没有区域号分店的list
					for(int j = 0; j < branchMoneyReportListPre.size(); j++) {
						if(!listBranchInArea0.contains(branchMoneyReportListPre.get(j).getBranchNum())) {
							listBranchInArea0.add(branchMoneyReportListPre.get(j).getBranchNum());
							preBizMoney = preBizMoney.add(branchMoneyReportListPre.get(j).getBizMoney());
						}
					}
					for(int j = 0; j < branchMoneyReportList.size(); j++) {
						if(!listBranchInArea1.contains(branchMoneyReportList.get(j).getBranchNum())) {
							bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
							orderCount = orderCount.add(new BigDecimal(branchMoneyReportList.get(j).getOrderCount()));
							profit = profit.add(branchMoneyReportList.get(j).getProfit());
						}
					}
					for(int j = 0; j < memeberBranchMoneyReportList.size(); j++) {
						if(!listBranchInArea2.contains(memeberBranchMoneyReportList.get(j).getBranchNum())) {
							memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
							memberOrderCount = memberOrderCount.add(new BigDecimal(memeberBranchMoneyReportList.get(j).getOrderCount()));
							memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
						}
					}
					for(int j = 0; j < branchConsumeReportList.size(); j++) {
						if(!listBranchInArea3.contains(branchConsumeReportList.get(j).getBranchNum())) {
							consume = consume.add(branchConsumeReportList.get(j).getConsume());
						}
					}
					for(int j = 0; j < lossMoneyList.size(); j++) {
						if(!listBranchInArea4.contains(lossMoneyList.get(j).getBranchNum())) {
							lossMoney = lossMoney.add(lossMoneyList.get(j).getMoney());
						}
					}
					for(int j = 0; j < branchDepositReportList.size(); j++) {
						if(!listBranchInArea5.contains(branchDepositReportList.get(j).getBranchNum())) {
							deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
						}
					}
					for(int j = 0; j < differentMoneyList.size(); j++) {
						if(!listBranchInArea6.contains(differentMoneyList.get(j).getBranchNum())) {
							differentMoney = differentMoney.add(differentMoneyList.get(j).getMoney());
						}
					}
					for(int j = 0; j < increasedMemberList.size(); j++) {
						if(!listBranchInArea7.contains(increasedMemberList.get(j).getBranchNum())) {
							incressedMember = incressedMember.add(new BigDecimal(increasedMemberList.get(j).getCount()));
						}
					}
					for(int j = 0; j < salesMoneyGoalList.size(); j++) {
						if(!listBranchInArea8.contains(salesMoneyGoalList.get(j).getBranchNum())) {
							salesGoalMoney = salesGoalMoney.add(salesMoneyGoalList.get(j).getSaleMoney());
						}
					}
					for(int j = 0; j < checkMoneyList.size(); j++) {
						if(!listBranchInArea9.contains(checkMoneyList.get(j).getBranchNum())) {
							checkMoney = checkMoney.add(checkMoneyList.get(j).getMoney());
						}
					}
				}
				if(i < branchNumsListArea.length) {
					dto.setArea(branchRegionName[i]);
				} else {
					dto.setArea("其他区域");
				}
				dto.setAreaBranchNums(branchString);
				dto.setRevenue(bizMoney);
				if(salesGoalMoney.compareTo(new BigDecimal("0.00")) != 0) {
					dto.setRealizeRate1(bizMoney.divide(salesGoalMoney, 2));
				} else {
					dto.setRealizeRate1(new BigDecimal("1"));
				}
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setMemeberRevenueOccupy(new BigDecimal("0"));
				} else {
					dto.setMemeberRevenueOccupy(memberBizMoney.divide(bizMoney, 2));
				}
				dto.setMemberSalesRealizeRate(new BigDecimal("1"));
				dto.setAveBillNums(orderCount);
				dto.setAllBillRealizeRate(new BigDecimal("1"));
				dto.setMemberBillNums(memberOrderCount);
				if(orderCount.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setBill(new BigDecimal("0"));
				} else {
					dto.setBill(bizMoney.divide(orderCount, 2));
				}
				if(memberOrderCount.compareTo(new BigDecimal("0")) == 0) {
					dto.setMemberBill(new BigDecimal("0"));
				} else {
					dto.setMemberBill(memberBizMoney.divide(memberOrderCount, 2));
				}
				dto.setDistributionDifferent(differentMoney);
				dto.setDestroyDefferent(lossMoney);
				dto.setAdjustAmount(checkMoney);
				dto.setGrossProfit(profit);
				dto.setGrossProfitRate(new BigDecimal("1"));
				dto.setIncressedMember(incressedMember);
				dto.setRealizeRate2(new BigDecimal("1"));
				dto.setCardStorage(deposit);
				dto.setRealizeRate3(new BigDecimal("1"));
				dto.setCartStorageConsume(consume);
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setStorageConsumeOccupy(new BigDecimal("0"));
				} else {
					dto.setStorageConsumeOccupy(consume.divide(bizMoney, 2));
				}
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setGrowthOf(new BigDecimal("0"));
				} else {
					dto.setGrowthOf(preBizMoney.divide(bizMoney, 2).subtract(new BigDecimal("1")));
				}
				listDTO.add(dto);
			}
			return listDTO;
		} else {
			
		}
		return null;
	}
	
	
	//得到指定日期的本周一和上周一，data[0]是本周一
	private Date[] getMonday(Date specifiedDate) {
		Date[] date = new Date[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String stringToday = sdf.format(specifiedDate);
		String thisMondayStr = null;
		String preMondayStr = null;
		Date today = null;
		Date thisMonday = null;
		Date preMonday = null;
		try {
			today = sdf.parse(stringToday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dayOfWeek = DateUtil.getDayOfWeek(today);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		
		if(dayOfWeek >= 3 && dayOfWeek <= 7) {
			calendar.add(Calendar.DAY_OF_MONTH, -(dayOfWeek-2));
			thisMondayStr = sdf.format(calendar.getTime());
		} else if(dayOfWeek == 2) {
			calendar.add(Calendar.DAY_OF_MONTH, 0);
			thisMondayStr = sdf.format(calendar.getTime());
		} else if(dayOfWeek == 1){
			calendar.add(Calendar.DAY_OF_MONTH, -6);
			thisMondayStr = sdf.format(calendar.getTime());
		}
		
		calendar.add(Calendar.DAY_OF_MONTH, -7);                       
		preMondayStr = sdf.format(calendar.getTime());
		
		try {
			thisMonday = sdf.parse(thisMondayStr);
			preMonday = sdf.parse(preMondayStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		date[0] = thisMonday;
		date[1] = preMonday;
		return date;
	}
	
	
	
	
	//将list中的分店号转换成string，逗号隔开的形式
	private String getBranchString(List<?> list) {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < list.size(); i++) {
			if(i == 0) {
				buffer.append("[");
			}
			buffer.append(String.valueOf(list.get(i)));
			if(i < list.size() - 1) {
				buffer.append(",");
			} else {
				buffer.append("]");
			}
		}
		return buffer.toString();
	}
	
	//返回无区域的分店号
	private List<Integer> listBranchNoArea(List<Branch> allList) {
		ArrayList<Branch> list = (ArrayList)allList;
		ArrayList<Branch> list1 = (ArrayList)list.clone();
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for(int i = 0; i < list1.size(); i++) {
			if(list1.get(i).getBranchRegionNum() == null) {
				returnList.add(list1.get(i).getId().getBranchNum());
			}
		}
		return returnList;
	}
	
	
	
}
