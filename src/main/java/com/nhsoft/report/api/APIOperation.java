package com.nhsoft.report.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhsoft.report.api.dto.OperationDTO;
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
			Date dateFromAndTo = null;
			try {
				dateFromAndTo = sdf.parse(date);
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
			//根据区域号分类拼数据
			OperationDTO dto = null;
			List<OperationDTO> listDTO = new ArrayList<OperationDTO>();
			
			List<Branch> branchList = null;
			List<Integer> branchNumList = null;
			List<BranchMoneyReport> branchMoneyReportList = null;           //非会员的查询结果
			List<BranchMoneyReport> memeberBranchMoneyReportList = null;    //会员的查询结果
			for(int i = 0; i < branchRegionNumList.size(); i++) {
				branchList = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(i));
				branchNumList = new ArrayList<Integer>();
				for(int j = 0; j < branchList.size(); j++) {
					branchNumList.add(branchList.get(j).getId().getBranchNum());
				}
				branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFromAndTo, new Date("2017-02-12"), false);
				memeberBranchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFromAndTo, dateFromAndTo, true);
				BigDecimal bizMoney = new BigDecimal("0");  //营业额
				BigDecimal orderCount = new BigDecimal("0");//客单量
				BigDecimal profit = new BigDecimal("0");    //毛利
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
				//dto.setBill(bizMoney.divide(orderCount));
				//dto.setMemberBill(memberBizMoney.divide(memberProfit));
				dto.setGrossProfit(profit);
				
				listDTO.add(dto);
			}
			return listDTO;
		
			
			
			
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
