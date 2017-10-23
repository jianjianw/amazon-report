package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.dto.OnlineOrderSaleAnalysisDTO;
import com.nhsoft.module.report.query.OnlineOrderQuery;

import java.util.Date;
import java.util.List;


public interface OnlineOrderDao {
	/**
	 * 订单数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Integer count(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 按商品汇总在线订单数据
	 * @param onlineOrderQuery
	 * @return
	 */
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByItem(OnlineOrderQuery onlineOrderQuery);

	/**
	 * 按门店、商品汇总在线订单数据
	 * @param onlineOrderQuery
	 * @return
	 */
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByBranchItem(OnlineOrderQuery onlineOrderQuery);

}
