package com.nhsoft.report.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhsoft.report.api.dto.InputControlsWeekDTO;
import com.nhsoft.report.util.DateUtil;

@RestController()
@RequestMapping("/inputControls")
public class APIInputContorls {
	
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
	public @ResponseBody List<InputControlsWeekDTO> listBranchJson() {
		
		
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
			dto.setWeek(i);
			list.add(dto);
		}
		return list;
	}
	
}
