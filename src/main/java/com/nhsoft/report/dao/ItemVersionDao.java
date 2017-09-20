package com.nhsoft.report.dao;



import com.nhsoft.report.model.ItemVersion;

import java.util.Date;
import java.util.List;

public interface ItemVersionDao {

	
	/**
	 * 查询时间范围内最早的记录
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param itemVersionType
	 * @return
	 */
	public List<ItemVersion> findFirst(String systemBookCode, Date dateFrom, Date dateTo, String itemVersionType);
}
