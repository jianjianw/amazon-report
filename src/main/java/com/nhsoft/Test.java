package com.nhsoft;

import com.google.gson.Gson;
import com.nhsoft.report.dto.LogQuery;
import com.nhsoft.report.model.AlipayLog;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.service.AlipayLogService;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.ServiceDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/5.
 */

@RestController
public class Test {
	
	
	@Autowired
	private ReportRpc reportRpc;
	private Gson gson = AppUtil.toBuilderGson();

	@Autowired
	private AlipayLogService alipayLogService;

	@Autowired
	private
	
	@RequestMapping("/query/{systemBookCode}/{sql}")
	public @ResponseBody String query(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("sql") String sql){
		
		return gson.toJson(reportRpc.excuteSql(systemBookCode, sql));
		
	}
	
	@RequestMapping("/clear/{systemBookCode}")
	public @ResponseBody String clear(@PathVariable("systemBookCode") String systemBookCode){
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
		
	}
	@RequestMapping("/test/{systemBookCode}")
	public  @ResponseBody List<AlipayLog> testAlipay(@PathVariable("systemBookCode") String systemBookCode){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateFrom=null;
		Date dateTo = null;
		try {
			dateFrom = sdf.parse("2017-01-20 08:02:35");
			dateTo = sdf.parse("2017-05-20 08:02:35");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Integer branchNum = null;
		LogQuery logQuery = new LogQuery();
		logQuery.setDateFrom(dateFrom);
		logQuery.setDateTo(dateTo);
		int offset=0;
		int limit = 10;
		List<AlipayLog> byLogQuery = alipayLogService.findByLogQuery(systemBookCode, branchNum, logQuery, offset, limit);
		return byLogQuery;

	}
}
