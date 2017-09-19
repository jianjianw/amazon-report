package com.nhsoft.report.dao;



import java.util.Date;

public interface MessageBoardDao {
	/**
	 * 按发送时间查询数量
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Integer countByDate(String systemBookCode, Date dateFrom, Date dateTo);

}
