package com.nhsoft.report.rpc.impl;

import com.nhsoft.report.param.AdjustmentReason;
import com.nhsoft.report.rpc.BookResourceRpc;
import com.nhsoft.report.service.BookResourceService;

import java.util.List;

public class BookResourceRpcImpl implements BookResourceRpc {

	private BookResourceService bookResourceService;

	@Override
	public List<AdjustmentReason> findAdjustmentReasons(String systemBookCode) {
		return bookResourceService.findAdjustmentReasons(systemBookCode);
	}
}