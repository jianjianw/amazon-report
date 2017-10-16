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

import com.nhsoft.report.api.dto.OperationRegionDTO;
import com.nhsoft.report.api.dto.OperationStoreDTO;
import com.nhsoft.report.dto.AdjustmentCauseMoney;
import com.nhsoft.report.dto.BranchArea;
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

@RestController()
@RequestMapping("/daily")
public class APIOperationDaily {

	@Autowired
	private ReportRpc reportRpc;
	
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
	
	/**
	 * 日营运分析
	 * @param systemBookCode 帐套号
	 * @param branchNums     分店号
	 * @param date           日期
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/area")
	public @ResponseBody List<OperationRegionDTO> listOperation (@RequestHeader("systemBookCode") String systemBookCode,
		@RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
		
		//日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = null;
		Date nextDay = null;
		Date preDay = null;
		try {
			today = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(date)); 
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);  
			String tomorrow= sdf.format(calendar.getTime());

			calendar.add(Calendar.DAY_OF_MONTH, -2);  
			String yesterday= sdf.format(calendar.getTime());
			
			nextDay = sdf.parse(tomorrow);
			preDay = sdf.parse(yesterday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
			
			branchMoneyReportListPre = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, preDay, today, false);
			branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, false);
			memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, true);
			branchConsumeReportList = reportRpc.findConsumeByBranch(systemBookCode, branchNumList, today, nextDay);
			lossMoneyList = reportRpc.findLossMoneyByBranch(systemBookCode, branchNumList, today, nextDay);
			branchDepositReportList = reportRpc.findDepositByBranch(systemBookCode, branchNumList, today, nextDay);
			differentMoneyList = reportRpc.findDifferenceMoneyByBranch(systemBookCode, branchNumList, today, nextDay);		
			increasedMemberList = reportRpc.findCardUserCountByBranch(systemBookCode, branchNumList, today, nextDay);	
			salesMoneyGoalList = reportRpc.findSaleMoneyGoalsByBranch(systemBookCode, branchNumList, today, nextDay, AppConstants.BUSINESS_DATE_SOME_MONTH);	
			checkMoneyList = reportRpc.findCheckMoneyByBranch(systemBookCode, branchNumList, today, nextDay);
			
			//按区域分组，返回数据
			OperationRegionDTO dto = null;
			List<OperationRegionDTO> listDTO = new ArrayList<OperationRegionDTO>();
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
				dto = new OperationRegionDTO();
				String branchString = null;                       //String类型的分店号
				BigDecimal preBizMoney = new BigDecimal("0");     //昨天营业额
				BigDecimal bizMoney = new BigDecimal("0");        //营业额
				Integer orderCount = 0;                           //客单量
				BigDecimal profit = new BigDecimal("0");          //毛利
				BigDecimal deposit = new BigDecimal("0");         //卡存款
				BigDecimal consume = new BigDecimal("0");         //卡消费
				BigDecimal lossMoney = new BigDecimal("0");       //报损金额
				BigDecimal differentMoney = new BigDecimal("0");  //配销差额
				BigDecimal memberBizMoney = new BigDecimal("0");  //会员营业额
				Integer memberOrderCount = 0;                     //会员客单量
				BigDecimal memberProfit = new BigDecimal("0");    //会员毛利
				Integer incressedMember = 0;                      //新增会员数
				BigDecimal salesGoalMoney = new BigDecimal("0");  //目标营业额
				BigDecimal checkMoney = new BigDecimal("0");      //盘损金额
				if(i < branchNumsListArea.length) {
					if(branchNumsListArea[i].size() != 0) {
						branchString  = getBranchString(branchNumsListArea[i]);        //得到string类型的分店号
					} else {
						branchString = "[,]";
					}
					for(int j = 0; j < branchMoneyReportListPre.size(); j++) {
						if(branchNumsListArea[i].contains(branchMoneyReportListPre.get(j).getBranchNum())) {
							listBranchInArea0.add(branchMoneyReportListPre.get(j).getBranchNum());
							if(branchMoneyReportListPre.get(j).getBizMoney() != null) {
								preBizMoney = preBizMoney.add(branchMoneyReportListPre.get(j).getBizMoney());
							}
						}
					}
					for(int j = 0; j < branchMoneyReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchMoneyReportList.get(j).getBranchNum())) {
							listBranchInArea1.add(branchMoneyReportList.get(j).getBranchNum());
							if(branchMoneyReportList.get(j).getBizMoney() != null) {
								bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
							}
							orderCount = orderCount + branchMoneyReportList.get(j).getOrderCount();
							if(branchMoneyReportList.get(j).getProfit() != null) {
								profit = profit.add(branchMoneyReportList.get(j).getProfit());
							}
						}
					}
					for(int j = 0; j < memeberBranchMoneyReportList.size(); j++) {
						if(branchNumsListArea[i].contains(memeberBranchMoneyReportList.get(j).getBranchNum())) {
							listBranchInArea2.add(memeberBranchMoneyReportList.get(j).getBranchNum());
							if(memeberBranchMoneyReportList.get(j).getBizMoney() != null) {
								memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
							}
							memberOrderCount = memberOrderCount + memeberBranchMoneyReportList.get(j).getOrderCount();
							if(memeberBranchMoneyReportList.get(j).getProfit() != null) {
								memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
							}
						}
					}
					for(int j = 0; j < branchConsumeReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchConsumeReportList.get(j).getBranchNum())) {
							listBranchInArea3.add(branchConsumeReportList.get(j).getBranchNum());
							if(branchConsumeReportList.get(j).getConsume() != null) {
								consume = consume.add(branchConsumeReportList.get(j).getConsume());
							}
						}
					}
					for(int j = 0; j < lossMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(lossMoneyList.get(j).getBranchNum())) {
							listBranchInArea4.add(lossMoneyList.get(j).getBranchNum());
							if(lossMoneyList.get(j).getMoney() != null) {
								lossMoney = lossMoney.add(lossMoneyList.get(j).getMoney());
							}
						}
					}
					for(int j = 0; j < branchDepositReportList.size(); j++) {
						if(branchNumsListArea[i].contains(branchDepositReportList.get(j).getBranchNum())) {
							listBranchInArea5.add(branchDepositReportList.get(j).getBranchNum());
							if(branchDepositReportList.get(j).getDeposit() != null) {
								deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
							}
						}
					}
					for(int j = 0; j < differentMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(differentMoneyList.get(j).getBranchNum())) {
							listBranchInArea6.add(differentMoneyList.get(j).getBranchNum());
							if(differentMoneyList.get(j).getMoney() != null) {
								differentMoney = differentMoney.add(differentMoneyList.get(j).getMoney());
							}
						}
					}
					for(int j = 0; j < increasedMemberList.size(); j++) {
						if(branchNumsListArea[i].contains(increasedMemberList.get(j).getBranchNum())) {
							listBranchInArea7.add(increasedMemberList.get(j).getBranchNum());
							incressedMember = incressedMember + increasedMemberList.get(j).getCount();
						}
					}
					for(int j = 0; j < salesMoneyGoalList.size(); j++) {
						if(branchNumsListArea[i].contains(salesMoneyGoalList.get(j).getBranchNum())) {
							listBranchInArea8.add(salesMoneyGoalList.get(j).getBranchNum());
							if(salesMoneyGoalList.get(j).getSaleMoney() != null) {
								salesGoalMoney = salesGoalMoney.add(salesMoneyGoalList.get(j).getSaleMoney());
							}
						}
					}
					for(int j = 0; j < checkMoneyList.size(); j++) {
						if(branchNumsListArea[i].contains(checkMoneyList.get(j).getBranchNum())) {
							listBranchInArea9.add(checkMoneyList.get(j).getBranchNum());
							if(checkMoneyList.get(j).getMoney() != null) {
								checkMoney = checkMoney.add(checkMoneyList.get(j).getMoney());
							}
						}
					}
				} else {
					List<Integer> branchNoAreaList = listBranchNoArea(branchList);
					branchString = getBranchString(branchNoAreaList);        //得到没有区域号分店的list
					for(int j = 0; j < branchMoneyReportListPre.size(); j++) {
						if(!listBranchInArea0.contains(branchMoneyReportListPre.get(j).getBranchNum())) {
							listBranchInArea0.add(branchMoneyReportListPre.get(j).getBranchNum());
							if(branchMoneyReportListPre.get(j).getBizMoney() != null) {
								preBizMoney = preBizMoney.add(branchMoneyReportListPre.get(j).getBizMoney());
							}
						}
					}
					for(int j = 0; j < branchMoneyReportList.size(); j++) {
						if(!listBranchInArea1.contains(branchMoneyReportList.get(j).getBranchNum())) {
							if(branchMoneyReportList.get(j).getBizMoney() != null) {
								bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
							}
							orderCount = orderCount + branchMoneyReportList.get(j).getOrderCount();
							if(branchMoneyReportList.get(j).getProfit() != null) {
								profit = profit.add(branchMoneyReportList.get(j).getProfit());
							}
						}
					}
					for(int j = 0; j < memeberBranchMoneyReportList.size(); j++) {
						if(!listBranchInArea2.contains(memeberBranchMoneyReportList.get(j).getBranchNum())) {
							if(memeberBranchMoneyReportList.get(j).getBizMoney() != null) {
								memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
							}
							memberOrderCount = memberOrderCount + memeberBranchMoneyReportList.get(j).getOrderCount();
							if(memeberBranchMoneyReportList.get(j).getProfit() != null) {
								memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
							}
						}
					}
					for(int j = 0; j < branchConsumeReportList.size(); j++) {
						if(!listBranchInArea3.contains(branchConsumeReportList.get(j).getBranchNum())) {
							if(branchConsumeReportList.get(j).getConsume() != null) {
								consume = consume.add(branchConsumeReportList.get(j).getConsume());
							}
						}
					}
					for(int j = 0; j < lossMoneyList.size(); j++) {
						if(!listBranchInArea4.contains(lossMoneyList.get(j).getBranchNum())) {
							if(lossMoneyList.get(j).getMoney() != null) {
								lossMoney = lossMoney.add(lossMoneyList.get(j).getMoney());
							}
						}
					}
					for(int j = 0; j < branchDepositReportList.size(); j++) {
						if(!listBranchInArea5.contains(branchDepositReportList.get(j).getBranchNum())) {
							if(branchDepositReportList.get(j).getDeposit() != null) {
								deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
							}
						}
					}
					for(int j = 0; j < differentMoneyList.size(); j++) {
						if(!listBranchInArea6.contains(differentMoneyList.get(j).getBranchNum())) {
							if(differentMoneyList.get(j).getMoney() != null) {
								differentMoney = differentMoney.add(differentMoneyList.get(j).getMoney());
							}
						}
					}
					for(int j = 0; j < increasedMemberList.size(); j++) {
						if(!listBranchInArea7.contains(increasedMemberList.get(j).getBranchNum())) {
							incressedMember = incressedMember + increasedMemberList.get(j).getCount();
						}
					}
					for(int j = 0; j < salesMoneyGoalList.size(); j++) {
						if(!listBranchInArea8.contains(salesMoneyGoalList.get(j).getBranchNum())) {
							if(salesMoneyGoalList.get(j).getSaleMoney() != null) {
								salesGoalMoney = salesGoalMoney.add(salesMoneyGoalList.get(j).getSaleMoney());
							}
						}
					}
					for(int j = 0; j < checkMoneyList.size(); j++) {
						if(!listBranchInArea9.contains(checkMoneyList.get(j).getBranchNum())) {
							if(checkMoneyList.get(j).getMoney() != null) {
								checkMoney = checkMoney.add(checkMoneyList.get(j).getMoney());
							}
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
				if(orderCount == 0) {
					dto.setBill(new BigDecimal("0"));
				} else {
					dto.setBill(bizMoney.divide(new BigDecimal(orderCount), 2));
				}
				if(memberOrderCount == 0) {
					dto.setMemberBill(new BigDecimal("0"));
				} else {
					dto.setMemberBill(memberBizMoney.divide(new BigDecimal(memberOrderCount), 2));
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
		}
		return null;
	}

	
	
	@RequestMapping(method = RequestMethod.GET, value = "/store")
	public @ResponseBody List<OperationStoreDTO> listOperationStore (@RequestHeader("systemBookCode") String systemBookCode,
		@RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
		
		//日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = null;
		Date nextDay = null;
		Date preDay = null;
		try {
			today = sdf.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(date)); 
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);  
			String tomorrow= sdf.format(calendar.getTime());

			calendar.add(Calendar.DAY_OF_MONTH, -2);  
			String yesterday= sdf.format(calendar.getTime());
			
			nextDay = sdf.parse(tomorrow);
			preDay = sdf.parse(yesterday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
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
		
		if(!branchNums.equals("")) {
			String branchNumStrs = branchNums.substring(1, branchNums.length() - 1);
			String[] array = branchNumStrs.split(",");
			List<Integer> realBranchNums = new ArrayList<Integer>();
			for(int i = 0; i < array.length; i++) {
				realBranchNums.add(Integer.parseInt(array[i].trim()));
			}
			if(array.length == 0) {
				List list = new ArrayList();
				list.add(new OperationStoreDTO());
				return list;
			}
			
			//按分店查询面积
			List<BranchArea> branchAreaList = reportRpc.findBranchArea(systemBookCode, branchNumList);
			//按分店查询损耗
			List<AdjustmentCauseMoney> adjustmentCauseMoneyByBranchList = reportRpc.findAdjustmentCauseMoneyByBranch(systemBookCode, branchNumList, today, nextDay);
			branchMoneyReportListPre = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, preDay, today, false);
			branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, false);
			memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, true);
			branchConsumeReportList = reportRpc.findConsumeByBranch(systemBookCode, branchNumList, today, nextDay);
			lossMoneyList = reportRpc.findLossMoneyByBranch(systemBookCode, branchNumList, today, nextDay);
			branchDepositReportList = reportRpc.findDepositByBranch(systemBookCode, branchNumList, today, nextDay);
			differentMoneyList = reportRpc.findDifferenceMoneyByBranch(systemBookCode, branchNumList, today, nextDay);		
			increasedMemberList = reportRpc.findCardUserCountByBranch(systemBookCode, branchNumList, today, nextDay);	
			salesMoneyGoalList = reportRpc.findSaleMoneyGoalsByBranch(systemBookCode, branchNumList, today, nextDay, AppConstants.BUSINESS_DATE_SOME_MONTH);	
			checkMoneyList = reportRpc.findCheckMoneyByBranch(systemBookCode, branchNumList, today, nextDay);
			
			//按分店，返回数据
			OperationStoreDTO dto2 = null;
			List<OperationStoreDTO> listDTO = new ArrayList<OperationStoreDTO>();
			for(int i = 0; i < realBranchNums.size(); i++) {
				
				dto2 = new OperationStoreDTO();
				BigDecimal preBizMoney = new BigDecimal("0");     //昨天营业额
				BigDecimal bizMoney = new BigDecimal("0");        //营业额
				Integer orderCount = 0;                           //客单量
				BigDecimal profit = new BigDecimal("0");          //毛利
				BigDecimal deposit = new BigDecimal("0");         //卡存款
				BigDecimal consume = new BigDecimal("0");         //卡消费
				BigDecimal lossMoney = new BigDecimal("0");       //报损金额
				BigDecimal differentMoney = new BigDecimal("0");  //配销差额
				BigDecimal memberBizMoney = new BigDecimal("0");  //会员营业额
				Integer memberOrderCount = 0;                     //会员客单量
				BigDecimal memberProfit = new BigDecimal("0");    //会员毛利
				Integer incressedMember = 0;                      //新增会员数
				BigDecimal salesGoalMoney = new BigDecimal("0");  //目标营业额
				BigDecimal checkMoney = new BigDecimal("0");      //盘损金额
				BigDecimal area = new BigDecimal("0");            //分店面积
				BigDecimal test = new BigDecimal("0");            //试吃
				BigDecimal peel = new BigDecimal("0");            //去皮
				BigDecimal breakage = new BigDecimal("0");        //报损
				BigDecimal other = new BigDecimal("0");                //其他
				
				for(int j = 0; j < branchList.size(); j++) {
					/*
					if(dto2.getBranchNum().equals(branchList.get(j).getId().getBranchNum())) {
						dto2.setBranchName(branchList.get(j).getBranchName());
						break;
					}*/
					
					if(realBranchNums.get(i).equals(branchList.get(j).getId().getBranchNum())) {
						dto2.setBranchName(branchList.get(j).getBranchName());
						break;
					}	
				}
				
				for(int j = 0; j < branchMoneyReportListPre.size(); j++) {
					if(realBranchNums.get(i) == branchMoneyReportListPre.get(j).getBranchNum()) {
						if(branchMoneyReportListPre.get(j).getBizMoney() != null) {
							preBizMoney = preBizMoney.add(branchMoneyReportListPre.get(j).getBizMoney());
						}
						break;
					}
				}
				
				for(int j = 0; j < branchMoneyReportList.size(); j++) {
					if(realBranchNums.get(i) == branchMoneyReportList.get(j).getBranchNum()) {
						if(branchMoneyReportList.get(j).getBizMoney() != null) {
							bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
						}
						orderCount = orderCount + branchMoneyReportList.get(j).getOrderCount();
						if(branchMoneyReportList.get(j).getProfit() != null) {
							profit = profit.add(branchMoneyReportList.get(j).getProfit());
						}
						break;
					}
				}
				
				for(int j = 0; j < memeberBranchMoneyReportList.size(); j++) {
					if(realBranchNums.get(i) == memeberBranchMoneyReportList.get(j).getBranchNum()) {
						if(memeberBranchMoneyReportList.get(j).getBizMoney() != null) {
							memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
						}
						memberOrderCount = memberOrderCount + memeberBranchMoneyReportList.get(j).getOrderCount();
						if(memeberBranchMoneyReportList.get(j).getProfit() != null) {
							memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
						}
						break;
					}
				}
				
				for(int j = 0; j < branchConsumeReportList.size(); j++) {
					if(realBranchNums.get(i) == branchConsumeReportList.get(j).getBranchNum()) {
						if(branchConsumeReportList.get(j).getConsume() != null) {
							consume = consume.add(branchConsumeReportList.get(j).getConsume());
						}
						break;
					}
				}
				
				for(int j = 0; j < lossMoneyList.size(); j++) {
					if(realBranchNums.get(i) == lossMoneyList.get(j).getBranchNum()) {
						if(lossMoneyList.get(j).getMoney() != null) {
							lossMoney = lossMoney.add(lossMoneyList.get(j).getMoney());
						}
						break;
					}
				}
				
				for(int j = 0; j < branchDepositReportList.size(); j++) {
					if(realBranchNums.get(i) == branchDepositReportList.get(j).getBranchNum()) {
						if(branchDepositReportList.get(j).getDeposit() != null) {
							deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
						}
						break;
					}
				}
				
				for(int j = 0; j < differentMoneyList.size(); j++) {
					if(realBranchNums.get(i) == differentMoneyList.get(j).getBranchNum()) {
						if(differentMoneyList.get(j).getMoney() != null) {
							differentMoney = differentMoney.add(differentMoneyList.get(j).getMoney());
						}
						break;
					}
				}
				
				for(int j = 0; j < increasedMemberList.size(); j++) {
					if(realBranchNums.get(i) == increasedMemberList.get(j).getBranchNum()) {
						incressedMember = incressedMember + increasedMemberList.get(j).getCount();
						break;
					}
				}
				
				for(int j = 0; j < salesMoneyGoalList.size(); j++) {
					if(realBranchNums.get(i) == salesMoneyGoalList.get(j).getBranchNum()) {
						if(salesMoneyGoalList.get(j).getSaleMoney() != null) {
							salesGoalMoney = salesGoalMoney.add(salesMoneyGoalList.get(j).getSaleMoney());
						}
						break;
					}
				}
				
				for(int j = 0; j < checkMoneyList.size(); j++) {
					if(realBranchNums.get(i) == checkMoneyList.get(j).getBranchNum()) {
						if(checkMoneyList.get(j).getMoney() != null) {
							checkMoney = checkMoney.add(checkMoneyList.get(j).getMoney());
						}
						break;
					}
				}
				
				for(int j = 0; j < branchAreaList.size(); j++) {
					if(realBranchNums.get(i) == branchAreaList.get(j).getBranchNum()) {
						if(branchAreaList.get(j).getArea() != null) {
							if(branchAreaList.get(j).getArea() != null) {
								area = area.add(branchAreaList.get(j).getArea());
							}
							break;
						}
					}
				}
				
				for(int j = 0; j < adjustmentCauseMoneyByBranchList.size(); j++) {
					if(realBranchNums.get(i) == adjustmentCauseMoneyByBranchList.get(j).getBranchNum()) {
						if(adjustmentCauseMoneyByBranchList.get(j).getTryEat() != null) {
							test = test.add(adjustmentCauseMoneyByBranchList.get(j).getTryEat());
						}
						if(adjustmentCauseMoneyByBranchList.get(j).getFaly() != null) {
							peel = peel.add(adjustmentCauseMoneyByBranchList.get(j).getFaly());
						}
						if(adjustmentCauseMoneyByBranchList.get(j).getLoss() != null) {
							breakage = breakage.add(adjustmentCauseMoneyByBranchList.get(j).getLoss());
						}
						if(adjustmentCauseMoneyByBranchList.get(j).getOther() != null) {
							other = other.add(adjustmentCauseMoneyByBranchList.get(j).getOther());
						}
					}
				}
				dto2.setBranchNum(realBranchNums.get(i));
				dto2.setRevenue(bizMoney);
				if(salesGoalMoney.compareTo(new BigDecimal("0.00")) != 0) {
					dto2.setRealizeRate1(bizMoney.divide(salesGoalMoney, 2));
				} else {
					dto2.setRealizeRate1(new BigDecimal("1"));
				}
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto2.setMemeberRevenueOccupy(new BigDecimal("0"));
				} else {
					dto2.setMemeberRevenueOccupy(memberBizMoney.divide(bizMoney, 2));
				}
				dto2.setMemberSalesRealizeRate(new BigDecimal("1"));
				dto2.setAveBillNums(orderCount);
				dto2.setAllBillRealizeRate(new BigDecimal("1"));
				dto2.setMemberBillNums(memberOrderCount);
				if(orderCount == 0) {
					dto2.setBill(new BigDecimal("0"));
				} else {
					dto2.setBill(bizMoney.divide(new BigDecimal(orderCount), 2));
				}
				if(memberOrderCount == 0) {
					dto2.setMemberBill(new BigDecimal("0"));
				} else {
					dto2.setMemberBill(memberBizMoney.divide(new BigDecimal(memberOrderCount), 2));
				}
				dto2.setDistributionDifferent(differentMoney);
				dto2.setDestroyDefferent(lossMoney);
				dto2.setAdjustAmount(checkMoney);
				dto2.setGrossProfit(profit);
				dto2.setGrossProfitRate(new BigDecimal("1"));
				dto2.setIncressedMember(incressedMember);
				dto2.setRealizeRate2(new BigDecimal("1"));
				dto2.setCardStorage(deposit);
				dto2.setRealizeRate3(new BigDecimal("1"));
				dto2.setCartStorageConsume(consume);
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto2.setStorageConsumeOccupy(new BigDecimal("0"));
				} else {
					dto2.setStorageConsumeOccupy(consume.divide(bizMoney, 2));
				}
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto2.setGrowthOf(new BigDecimal("0"));
				} else {
					dto2.setGrowthOf(preBizMoney.divide(bizMoney, 2).subtract(new BigDecimal("1")));
				}
				if(area.compareTo(new BigDecimal("0.00")) != 0) {
					dto2.setAreaEfficiency(bizMoney.divide(area, 2));
				}
				dto2.setTest(test);
				dto2.setPeel(peel);
				dto2.setBreakage(breakage);
				dto2.setOther(other);
				listDTO.add(dto2);
			}
			return listDTO;
		}
		return null;
	}
		
}
