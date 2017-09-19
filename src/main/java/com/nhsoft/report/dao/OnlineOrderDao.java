package com.nhsoft.report.dao;


import java.util.Date;


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

}
