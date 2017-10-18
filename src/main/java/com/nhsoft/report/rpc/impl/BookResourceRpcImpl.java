package com.nhsoft.report.rpc.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.param.AdjustmentReason;
import com.nhsoft.report.rpc.BookResourceRpc;
import com.nhsoft.report.service.BookResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
@Service
@Component
public class BookResourceRpcImpl implements BookResourceRpc {


	@Autowired
	private BookResourceService bookResourceService;

	@Override
	public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode) {
		return bookResourceService.findAdjustmentReasons(systemBookCode);
	}
}