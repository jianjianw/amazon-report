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
			List<Integer> branchRegionNumList = new ArrayList<Integer>();
			for(int i = 0; i < branchRegionList.size(); i++) {
				branchRegionNumList.add(branchRegionList.get(i).getBranchRegionNum());
			}
			
			
			List<Branch> branchList2 = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(0));
			List<Integer> branchNumList2 = new ArrayList<Integer>();
			for(int j = 1; j < branchList2.size(); j++) {
				branchNumList2.add(branchList2.get(j).getId().getBranchNum());
			}
			System.out.println(branchNumList2+"++++++++++++++++");    
			
			//根据区域号拼数据
			for(int i = 0; i < branchRegionNumList.size(); i++) {
				List<Branch> branchList = reportRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionNumList.get(0));
				List<Integer> branchNumList = new ArrayList<Integer>();
				for(int j = 1; j < branchList.size(); j++) {
					branchNumList.add(branchList.get(j).getId().getBranchNum());
				}
				System.out.println(branchNumList+"++++++++++++++++");
			}
			
			
			
			//List<Integer>[] branchNumList = new ArrayList[branchRegionNumList.size()];
			/*for(int i = 0; i < branchList.length; i++) {
				for(int j = 0; j < branchList[i].size(); j++) {
					branchNumList[i].add(branchList[i].get(j).getId().getBranchNum());
				}
				System.out.println(branchNumList[i]);
			}*/
			
			
			
			
			
			/*
			List<Integer> branchRegionNumList = new ArrayList<Integer>();
			for(int i = 0; i < branchRegionList.size(); i++) {
				branchRegionNumList.add(branchRegionNumList.get(i));
			}
			System.out.println(branchRegionNumList);
			//根据区域得到分店
			List<Branch> branchList = new ArrayList<Branch>();
			for(int i = 0; i < branchRegionNumList.size(); i++) {
				
			}*/
			
			
			
			
			
			
			
			
			
	
			
			
			
			
			
			
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
