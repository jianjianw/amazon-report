package com.nhsoft.module.report.rpc.impl;



import com.nhsoft.module.report.dto.AdjustmentReason;
import com.nhsoft.module.report.rpc.BookResourceRpc;
import com.nhsoft.module.report.service.BookResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookResourceRpcImpl implements BookResourceRpc {


	@Autowired
	private BookResourceService bookResourceService;

	@Override
	public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode) {
		return bookResourceService.findAdjustmentReasons(systemBookCode);
	}
}