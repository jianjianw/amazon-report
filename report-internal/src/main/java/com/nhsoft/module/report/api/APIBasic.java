package com.nhsoft.module.report.api;

import com.nhsoft.module.report.dto.BranchBizRevenueSummary;
import com.nhsoft.module.report.rpc.AlipayLogRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yangqin on 2017/10/30.
 */
@RestController()
@RequestMapping("/basic")
public class APIBasic {
	
	@Autowired
	private AlipayLogRpc alipayLogRpc;
	
	@Autowired
	private PosOrderRpc posOrderRpc;
	
	@RequestMapping(method = RequestMethod.GET, value = "/clear")
	public @ResponseBody String clearSystemBookProxy(@RequestParam("systemBookCode") String systemBookCode) {
		ServiceDeskUtil.clear(systemBookCode);
		return "success";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public @ResponseBody List<BranchBizRevenueSummary> test () {
		
		
		return posOrderRpc.findMoneyBizdaySummary("4344", Arrays.asList(1,2,99), AppConstants.BUSINESS_TREND_PAYMENT, DateUtil.getDateStr("20170901"), DateUtil.getDateStr("20171101"), false);
	}
	
}
