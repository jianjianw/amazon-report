package com.nhsoft.report.api;

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

import com.nhsoft.report.api.dto.InputControlsBranchDTO;
import com.nhsoft.report.api.dto.InputControlsMonthDTO;
import com.nhsoft.report.api.dto.InputControlsWeekDTO;
import com.nhsoft.report.api.dto.InputControlsYearDTO;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.rpc.BranchRpc;
import com.nhsoft.report.util.DateUtil;

@RestController()
@RequestMapping("/inputControls")
public class APIInputContorls {
	
	@Autowired
	private BranchRpc branchRpc;
	
	/**
	 * @param specifiedDate 任意一天的日期
	 * @return 返回该日所在的本周一和上周一，date[0]是本周一
	 */
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

	@RequestMapping(method = RequestMethod.GET, value = "/week")
	public @ResponseBody List<InputControlsWeekDTO> listWeekJson() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式
		Date today = new Date();                                       
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		
		String specifiedDateStr = null;
		InputControlsWeekDTO dto = null;
		List<InputControlsWeekDTO> list = new ArrayList<InputControlsWeekDTO>();
		for(int i = 0; i <= 5; i++) {
			
			if(i == 0) {
				calendar.add(Calendar.DAY_OF_MONTH, 7);
				specifiedDateStr = sdf.format(calendar.getTime());
			} else {
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				specifiedDateStr = sdf.format(calendar.getTime());
			}
			
			
			Date specifiedDate = null;
			try {
				specifiedDate = sdf.parse(specifiedDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date[] date = getMonday(specifiedDate);
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(date[0]);
			calendar2.add(Calendar.DAY_OF_MONTH, -1);
			
			dto = new InputControlsWeekDTO();
			if(i == 0) {
				dto.setDisplay("本周");
			} else if(i == 1){
				dto.setDisplay("上周");
			} else {
				dto.setDisplay(sdf.format(date[1])+"~"+sdf.format(calendar2.getTime()));
			}
			dto.setWeek(sdf.format(date[1])+"~"+sdf.format(calendar2.getTime()));
			list.add(dto);
		}
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/month")
	public @ResponseBody List<InputControlsMonthDTO> listMonthJson() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式 
		Date today = new Date();
		
		String subTodayStr = sdf.format(today).substring(5, 7);
		int month = Integer.parseInt(subTodayStr);
		
		Calendar calendar = Calendar.getInstance();
		
		InputControlsMonthDTO dto = null;
		List<InputControlsMonthDTO> list = new ArrayList<InputControlsMonthDTO>();
		
		if(month > 3) {
			String startDay = sdf.format(today).substring(0, 4) + "-01-01";
			Date start = null;
			try {
				start = sdf.parse(startDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calendar.setTime(start);
			for(int i = 1; i <= 12; i++) {
				dto = new InputControlsMonthDTO();
				dto.setDisplay(sdf.format(calendar.getTime()).substring(0, 7));
				dto.setMonth(sdf.format(calendar.getTime()).substring(0, 7));
				list.add(dto);
				calendar.add(Calendar.MONTH, 1);
			}
		} else {
			calendar.setTime(today);
			calendar.add(Calendar.YEAR, -1);
			String startDay = sdf.format(calendar.getTime()).substring(0, 4) + "-01-01";
			Date start = null;
			try {
				start = sdf.parse(startDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calendar.setTime(start);
			for(int i = 1; i <= 12+month; i++) {
				dto = new InputControlsMonthDTO();
				dto.setDisplay(sdf.format(calendar.getTime()).substring(0, 7));
				dto.setMonth(sdf.format(calendar.getTime()).substring(0, 7));
				list.add(dto);
				calendar.add(Calendar.MONTH, 1);
			}
		}
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/year")
	public @ResponseBody List<InputControlsYearDTO> listYearJson() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式 
		Date today = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		String yearStr = null;
		
		//返回本年和前五年的年份
		InputControlsYearDTO dto = null;
		List<InputControlsYearDTO> list = new ArrayList<InputControlsYearDTO>();
		for(int i = 0; i < 6; i++) {
			dto = new InputControlsYearDTO();
			yearStr = sdf.format(calendar.getTime()).substring(0, 4);
			dto.setDisplay(yearStr);
			dto.setYear(yearStr);
			calendar.add(Calendar.YEAR, -1);
			list.add(dto);
		}
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/branch")
	public @ResponseBody List<InputControlsBranchDTO> listBranchJson(@RequestHeader("systemBookCode") String systemBookCode,
		@RequestHeader("branchNums") String branchNums) {
		
		// 防止传入branchNums的值为空[]，导致转化错误，但也不能是[,,, , ]这种格式
		if(branchNums.equals("[]")) {
			List<InputControlsBranchDTO> list = new ArrayList<InputControlsBranchDTO>();
			return list;
		}
		
		String branchNumStrs = branchNums.substring(1, branchNums.length() - 1);
		String[] array = branchNumStrs.split(",");
		
		List<Integer> branchNumsList = new ArrayList<Integer>();
		for(int i = 0; i < array.length; i++) {
			branchNumsList.add(Integer.parseInt(array[i].trim()));
		}
		
		List<Branch> branchList = branchRpc.findAll(systemBookCode);
		List<Branch> branchForUser = new ArrayList<Branch>();
		for(int i = 0; i < branchNumsList.size(); i++) {
			for(int j = 0; j < branchList.size(); j++) {
				if(branchNumsList.get(i).equals(branchList.get(j).getId().getBranchNum())) {
					branchForUser.add(branchList.get(j));
					break;
				}
			}
		}
		
		InputControlsBranchDTO dto = null;
		List<InputControlsBranchDTO> list = new ArrayList<InputControlsBranchDTO>();
		for(int i = 0; i < branchForUser.size(); i++) {
			dto = new InputControlsBranchDTO();
			dto.setBranchName(branchForUser.get(i).getBranchName());
			dto.setBranchNum(branchForUser.get(i).getId().getBranchNum());
			list.add(dto);
		}
		return list;
	}
	
}
