package com.nhsoft.report.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;






import com.nhsoft.report.rpc.ReportRpc;
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
		
		
		
		
		
		
		
		if(branchNums.equals("")) {
			
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
	
	
}
