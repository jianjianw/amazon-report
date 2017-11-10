package com.nhsoft.module.report.service;


import com.nhsoft.module.report.dto.RequestOrderDTO;
import com.nhsoft.module.report.model.RequestOrderDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 要货单
 * @author nhsoft
 *
 */
public interface RequestOrderService {
	
	/**
	 * 要货期内未调出审核的金额
	 * @param systemBookCode
	 * @param branchNum 要货分店
	 * @param outBranchNum  调出分店
	 * @return
	 */
	public BigDecimal readBranchUnOutMoney(String systemBookCode, Integer branchNum, Integer outBranchNum);
	
	/**
	 * 查询要货单部分属性
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<RequestOrderDTO> findDTOs(String systemBookCode, Integer centerBranchNum, Integer branchNum,
	                                      Date dateFrom, Date dateTo);
	
	/**
	 * 查询要货单对应的发货单货运方式
	 * @param requestOrderFids
	 * @return
	 */
	public List<Object[]> findFidShipOrderDeliver(List<String> requestOrderFids);
	
	/**
	 * 查询明细
	 * @param requestOrderFid
	 * @return
	 */
	public List<RequestOrderDetail> findDetails(String requestOrderFid);
	
	
	
	
	

}
