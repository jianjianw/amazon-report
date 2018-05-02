package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.RelatCardDTO;
import com.nhsoft.module.report.dto.ShiftTableDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

public interface RelatCardRpc {

	/**
	 * 查询
	 * @param cardUserNum 卡主键
	 * @return
	 */
	public List<RelatCardDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum);

	/**
     * 根据班次查询
     * @param shiftTables 班次列表
     * @return
     */
	public List<RelatCardDTO> findByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables);

	public List<RelatCardDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery);

	/**
	 * 查询续卡数量  续卡金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public Object[] sumByByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables);

	/**
	 * 按支付方式汇总续卡金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public List<Object[]> findPaymentTypeByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables);


}
