package com.nhsoft.report.api;

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
			//根据帐套号得到所有区域
			List<BranchRegion> branchRegionList = reportRpc.findBranchRegion(systemBookCode);
			
			//根据帐套号得到分店号
			List[] branchNumList = new ArrayList[branchRegionList.size()];
			for(int i = 0; i < branchRegionList.size(); i++) {
				branchNumList[i] = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionList.get(i).getBranchRegionNum());
			}
			
			
			
			
			
			
			
			
			
			
			/*
			//每个区域查询一组分店号
			List<Branch> branchNumsList = null;
			for(int i = 0; i < branchRegionNumList.size(); i++) {
				branchNumsList = new ArrayList<Branch>();
				branchNumsList = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(i));
			}*/
			
			
			//List<Branch> branchList = new ArrayList<Branch>();
			
			//for(int i = 0; i < branchList.size(); i++) {
				//branchNumsList.add(branchList.get(i).getId().getBranchNum());
			//}
			//按分店得到营业额
			//List<BranchMoneyReport> branchMoneyReportList = reportRpc.findMoneyByBranch(systemBookCode, branchNumsList, AppConstants.BUSINESS_TREND_PAYMENT, dateFromAndTo, dateFromAndTo, false);
			
			
			
			
			
			
			
			
			
			OperationDTO dto = new OperationDTO();
			List<OperationDTO> list = new ArrayList<OperationDTO>();
			list.add(dto);
			return list;
			
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
