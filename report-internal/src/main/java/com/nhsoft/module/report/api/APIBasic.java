package com.nhsoft.module.report.api;

import com.nhsoft.module.report.dto.AlipayLogDTO;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.module.report.rpc.AlipayLogRpc;
import com.nhsoft.module.report.util.DateUtil;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/10/30.
 */
@RestController()
@RequestMapping("/basic")
public class APIBasic {
	
	@Autowired
	private AlipayLogRpc alipayLogRpc;
	
	@RequestMapping(method = RequestMethod.GET, value = "/clear")
	public @ResponseBody String clearSystemBookProxy(@RequestParam("systemBookCode") String systemBookCode) {
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findAlipayLogs")
	public @ResponseBody List<AlipayLogDTO> findAlipayLogs (@RequestParam("systemBookCode") String systemBookCode) {
		
		LogQuery logQuery = new LogQuery();
		logQuery.setSystemBookCode(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		logQuery.setDateTo(now);
		logQuery.setDateFrom(DateUtil.addMonth(now, -9));
		logQuery.setPaging(false);
		return alipayLogRpc.findByLogQuery(systemBookCode, 99, logQuery, 0, 0);
	}
	
}
