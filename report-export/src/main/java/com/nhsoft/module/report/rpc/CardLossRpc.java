package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.CardLossDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

public interface CardLossRpc {

	/**
	 * 查询
	 * @param cardUserNum 会员卡主键
	 * @param operateName 操作人
	 * @return
	 */
	public List<CardLossDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum, String operateName);

	public List<CardLossDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery);

}
