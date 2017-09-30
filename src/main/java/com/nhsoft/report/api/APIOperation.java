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
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.util.AppConstants;

@RestController()
@RequestMapping("/operation")
public class APIOperation {

	@Autowired
	private ReportRpc reportRpc;
	
	/**
	 * 日目标分析
	 * @param systemBookCode 帐套号
	 * @param branchNums     分店号
	 * @param date           日期
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/area")
	public @ResponseBody List<OperationDTO> listOperation (@RequestHeader("systemBookCode") String systemBookCode,
		@RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
		if(branchNums.equals("")) {
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

				calendar.add(Calendar.DAY_OF_MONTH, -1);  
				String yesterday= sdf.format(calendar.getTime());
				
				nextDay = sdf.parse(tomorrow);
				preDay = sdf.parse(yesterday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
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
			
			//根据帐套号查询所有分店号
			List<Branch> branchList = reportRpc.findAll(systemBookCode);
			List<Integer> branchNumList = new ArrayList<Integer>();
			for(int j = 0; j < branchList.size(); j++) {
				branchNumList.add(branchList.get(j).getId().getBranchNum());
			}
			
			//根据所有分店查询营业额  非会员
			List<BranchMoneyReport> branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, false);
			//根据所有分店查询营业额  会员
			List<BranchMoneyReport> memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, today, nextDay, true);
			//根据分店查询卡存款
			List<BranchDepositReport> branchDepositReportList = reportRpc.findDepositByBranch(systemBookCode, branchNumList, today, nextDay);
			//根据分店查询卡消费
			List<BranchConsumeReport> branchConsumeReportList = reportRpc.findConsumeByBranch(systemBookCode, branchNumList, today, nextDay);
			
			//按区域分组，返回数据
			OperationDTO dto = null;
			List<OperationDTO> listDTO = new ArrayList<OperationDTO>();
			for(int i = 0; i < branchNumsListArea.length; i++) {
				dto = new OperationDTO();
				BigDecimal bizMoney = new BigDecimal("0");  //营业额
				BigDecimal orderCount = new BigDecimal("0");//客单量
				BigDecimal profit = new BigDecimal("0");    //毛利
				BigDecimal deposit = new BigDecimal("0");   //卡存款
				BigDecimal consume = new BigDecimal("0");   //卡消费
				BigDecimal memberBizMoney = new BigDecimal("0");  //会员营业额
				BigDecimal memberOrderCount = new BigDecimal("0");//会员客单量
				BigDecimal memberProfit = new BigDecimal("0");    //会员毛利
				for(int j = 0; j < branchMoneyReportList.size(); j++) {
					if(branchNumsListArea[i].contains(branchMoneyReportList.get(j).getBranchNum())) {
						bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
						orderCount = orderCount.add(new BigDecimal(branchMoneyReportList.get(j).getOrderCount()));
						profit = profit.add(branchMoneyReportList.get(j).getProfit());
						memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
						memberOrderCount = memberOrderCount.add(new BigDecimal(memeberBranchMoneyReportList.get(j).getOrderCount()));
						memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
						deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
						consume = consume.add(branchConsumeReportList.get(j).getConsume());
					}
				}
				dto.setArea(branchRegionName[i]);
				dto.setRevenue(bizMoney);
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setMemeberRevenueOccupy(new BigDecimal("0"));
				} else {
					dto.setMemeberRevenueOccupy(memberBizMoney.divide(bizMoney, 2));
				}
				dto.setAveBillNums(orderCount);
				dto.setMemberBillNums(memberOrderCount);
				if(orderCount.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setBill(new BigDecimal("0"));
				} else {
					dto.setBill(bizMoney.divide(orderCount, 2));
				}
				if(memberOrderCount.compareTo(new BigDecimal("0")) == 0) {
					dto.setMemberBill(new BigDecimal("0"));
				} else {
					dto.setBill(memberBizMoney.divide(memberOrderCount, 2));
				}
				dto.setGrossProfit(profit);
				dto.setCardStorage(deposit);
				dto.setStorageConsumeOccupy(consume);
				listDTO.add(dto);
			}
			return listDTO;
			
			
			
			
			/*
			//根据帐套号得到所有区域号和区域名称
			List<BranchRegion> branchRegionList = reportRpc.findBranchRegion(systemBookCode);
			String[] branchRegionName = new String[branchRegionList.size()];
			List<Integer> branchRegionNumList = new ArrayList<Integer>();
			for(int i = 0; i < branchRegionList.size(); i++) {
				branchRegionNumList.add(branchRegionList.get(i).getBranchRegionNum());
				branchRegionName[i] = branchRegionList.get(i).getBranchRegionName();
			}
			//根据区域号分类拼数据
			OperationDTO dto = null;
			List<OperationDTO> listDTO = new ArrayList<OperationDTO>();
			
			List<Branch> branchList = null;                                 //分店查询的结果
			List<Integer> branchNumList = null;
			List<BranchMoneyReport> branchMoneyReportList = null;           //非会员营业额的查询结果
			List<BranchMoneyReport> memeberBranchMoneyReportList = null;    //会员的营业额查询结果
			List<BranchDepositReport> branchDepositReportList = null;       //查询卡存款的结果
			List<BranchConsumeReport> branchConsumeReportList = null;       //查询卡消费的结果
			for(int i = 0; i < branchRegionNumList.size(); i++) {
				branchList = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(i));
				branchNumList = new ArrayList<Integer>();
				for(int j = 0; j < branchList.size(); j++) {
					branchNumList.add(branchList.get(j).getId().getBranchNum());
				}
				
				
				System.out.println(branchNumList+branchRegionName[i]);
				
				
				branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFromAndTo, dateFromAndTo, false);
				memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFromAndTo, dateFromAndTo, true);
				branchDepositReportList = reportRpc.findDepositByBranch(systemBookCode, branchNumList, dateFromAndTo, dateFromAndTo);
				branchConsumeReportList = reportRpc.findConsumeByBranch(systemBookCode, branchNumList, dateFromAndTo, dateFromAndTo);
				BigDecimal bizMoney = new BigDecimal("0");  //营业额
				BigDecimal orderCount = new BigDecimal("0");//客单量
				BigDecimal profit = new BigDecimal("0");    //毛利
				BigDecimal deposit = new BigDecimal("0");   //卡存款
				BigDecimal consume = new BigDecimal("0");   //卡消费
				BigDecimal memberBizMoney = new BigDecimal("0");  //会员营业额
				BigDecimal memberOrderCount = new BigDecimal("0");//会员客单量
				BigDecimal memberProfit = new BigDecimal("0");    //会员毛利
				for(int j = 0; j < branchMoneyReportList.size(); j++) {
					bizMoney = bizMoney.add(branchMoneyReportList.get(j).getBizMoney());
					orderCount = orderCount.add(new BigDecimal(branchMoneyReportList.get(j).getOrderCount()));
					profit = profit.add(branchMoneyReportList.get(j).getProfit());
					memberBizMoney = memberBizMoney.add(memeberBranchMoneyReportList.get(j).getBizMoney());
					memberOrderCount = memberOrderCount.add(new BigDecimal(memeberBranchMoneyReportList.get(j).getOrderCount()));
					memberProfit = memberProfit.add(memeberBranchMoneyReportList.get(j).getProfit());
					deposit = deposit.add(branchDepositReportList.get(j).getDeposit());
					consume = consume.add(branchConsumeReportList.get(j).getConsume());
				}
				dto = new OperationDTO();
				dto.setArea(branchRegionName[i]);
				dto.setRevenue(bizMoney);
				if(bizMoney.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setMemeberRevenueOccupy(new BigDecimal("0"));
				} else {
					dto.setMemeberRevenueOccupy(memberBizMoney.divide(bizMoney));
				}
				dto.setAveBillNums(orderCount);
				dto.setMemberBillNums(memberOrderCount);
				if(orderCount.compareTo(new BigDecimal("0.00")) == 0) {
					dto.setBill(new BigDecimal("0"));
				} else {
					dto.setBill(bizMoney.divide(orderCount));
				}
				if(memberOrderCount.compareTo(new BigDecimal("0")) == 0) {
					dto.setMemberBill(new BigDecimal("0"));
				} else {
					dto.setBill(memberBizMoney.divide(memberOrderCount));
				}
				dto.setGrossProfit(profit);
				dto.setCardStorage(deposit);
				dto.setStorageConsumeOccupy(consume);
				listDTO.add(dto);
			}
			return listDTO;
		
			*/
			
			
		} else {
			/*String branchNumStrs = branchNums.substring(1, branchNums.length() - 1);
			String[] array = branchNumStrs.split(",");
			List<Integer> realBranchNums = new ArrayList<Integer>();
			for(int i = 0; i < array.length; i++) {
				realBranchNums.add(Integer.parseInt(array[i].trim()));
			}*/
			
		}
		return null;
	}
	
	
}
