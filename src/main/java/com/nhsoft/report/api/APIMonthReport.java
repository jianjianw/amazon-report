package com.nhsoft.report.api;

import com.nhsoft.report.api.dto.MonthReportDTO;
import com.nhsoft.report.dto.BranchMonthReport;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.service.BranchService;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController()
@RequestMapping("/report")
public class APIMonthReport {

	@Autowired
	private ReportRpc reportRpc;
	@Autowired
	private BranchService branchService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/findMonthReports")
	public @ResponseBody List<MonthReportDTO> findMonthReports(@RequestHeader("systemBookCode") String systemBookCode, @RequestHeader("branchNums") String branchNumStrs,
			@RequestHeader("countType") int countType, @RequestHeader("year") int year) {
		
		List<Integer> branchNums = new ArrayList<Integer>();
		String[] array = branchNumStrs.split(",");
		for(int i = 0;i < array.length;i++){
			branchNums.add(Integer.parseInt(array[i]));
		}
		
		Date dateFrom = DateUtil.getDateStr(year + "0101");
		Date dateTo = DateUtil.getLastDayOfYear(dateFrom);
		List<BranchMonthReport> list = reportRpc.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, countType);
		List<MonthReportDTO> dtos = new ArrayList<MonthReportDTO>();
		if(list.isEmpty()){
			return dtos;
		}
		List<Branch> branches = branchService.findInCache(systemBookCode);
		for(int i = 0;i < list.size();i++){
			BranchMonthReport branchMonthReport = list.get(i);
			
			MonthReportDTO dto = MonthReportDTO.get(dtos, branchMonthReport.getBranchNum());
			if(dto == null){
				dto = new MonthReportDTO();
				dto.setBranchNum(branchMonthReport.getBranchNum());
				Branch branch = AppUtil.getBranch(branches, branchMonthReport.getBranchNum());
				dto.setBranchName(branch.getBranchName());
				dto.setSummary(BigDecimal.ZERO);
				dtos.add(dto);
			}
			String month = branchMonthReport.getMonth().substring(4);
			switch(month){
				case "01":dto.setJanuary(branchMonthReport.getBizMoney());
				case "02":dto.setFebruary(branchMonthReport.getBizMoney());
				case "03":dto.setMarch(branchMonthReport.getBizMoney());
				case "04":dto.setApril(branchMonthReport.getBizMoney());
				case "05":dto.setMay(branchMonthReport.getBizMoney());
				case "06":dto.setJune(branchMonthReport.getBizMoney());
				case "07":dto.setJuly(branchMonthReport.getBizMoney());
				case "08":dto.setAugust(branchMonthReport.getBizMoney());
				case "09":dto.setSeptember(branchMonthReport.getBizMoney());
				case "10":dto.setOctober(branchMonthReport.getBizMoney());
				case "11":dto.setNovember(branchMonthReport.getBizMoney());
				case "12":dto.setDecember(branchMonthReport.getBizMoney());
				
			}
			dto.setSummary(dto.getSummary().add(branchMonthReport.getBizMoney()));
			dto.setAverage(dto.getSummary().divide(BigDecimal.valueOf(12), 4, BigDecimal.ROUND_HALF_UP));
		}
		return dtos;
		
		
	}
	
}
