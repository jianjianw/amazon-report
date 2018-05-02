package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.ReplaceCardDTO;
import com.nhsoft.module.report.dto.ShiftTableDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.List;

public interface ReplaceCardRpc {

	/**
	 * 查询
	 * @param cardUserNum 卡主键
	 * @return
	 */
	public List<ReplaceCardDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum);

	public List<ReplaceCardDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery);

	/**
	 * 查询数量
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param shiftTableBizday 营业日
	 * @param shiftTableNum 班次编号
	 * @return
	 */
	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum);

	/**
	 * 按班次查询换卡金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public BigDecimal sumByByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables);

	/**
	 * 按支付方式汇总金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public List<Object[]> findPaymentTypeByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables);


	public ReplaceCardDTO getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String replaceCardMachine);

}
