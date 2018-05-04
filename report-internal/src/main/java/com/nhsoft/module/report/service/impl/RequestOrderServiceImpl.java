package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.RequestOrderDao;
import com.nhsoft.module.report.dto.RequestOrderDTO;
import com.nhsoft.module.report.model.RequestOrderDetail;
import com.nhsoft.module.report.service.RequestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/11/10.
 */
@Service
public class RequestOrderServiceImpl implements RequestOrderService {
	
	@Autowired
	private RequestOrderDao requestOrderDao;
	
	@Override
	public BigDecimal readBranchUnOutMoney(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		return requestOrderDao.readBranchUnOutMoney(systemBookCode, branchNum, outBranchNum);
	}
	
	@Override
	public List<RequestOrderDTO> findDTOs(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo) {
		return requestOrderDao.findDTOs(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo);
	}
	
	@Override
	public List<Object[]> findFidShipOrderDeliver(List<String> requestOrderFids) {
		return requestOrderDao.findFidShipOrderDeliver(requestOrderFids);
	}
	
	@Override
	public List<RequestOrderDetail> findDetails(String requestOrderFid) {
		return requestOrderDao.findDetails(requestOrderFid);
	}

	@Override
	public List<Object[]> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return requestOrderDao.findItemSummary(systemBookCode,centerBranchNum,branchNum,dateFrom,dateTo,itemNums);
	}

	@Override
	public List<Object[]> findCenterRequestMatrixAmount(String systemBookCode, Integer outBranchNum, List<Integer> itemNums) {
		return requestOrderDao.findCenterRequestMatrixAmount(systemBookCode,outBranchNum,itemNums);
	}
}
