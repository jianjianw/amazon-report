package com.nhsoft;

import com.google.gson.Gson;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.ServiceDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangqin on 2017/9/5.
 */

@RestController
public class Test {
	
	
	@Autowired
	private ReportRpc reportRpc;
	private Gson gson = AppUtil.toBuilderGson();
	
	@RequestMapping("/query/{systemBookCode}/{sql}")
	public @ResponseBody String query(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("sql") String sql){
		
		return gson.toJson(reportRpc.excuteSql(systemBookCode, sql));
		
	}
	
	@RequestMapping("/clear/{systemBookCode}")
	public @ResponseBody String clear(@PathVariable("systemBookCode") String systemBookCode){
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
		
	}
}
