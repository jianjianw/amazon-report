package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.ReplaceCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 换卡
 * @author nhsoft
 *
 */
public interface ReplaceCardService {

	/**
	 * 查询
	 * @param cardUserNum 卡主键
	 * @return
	 */
	public List<ReplaceCard> findByCardUserNum(Integer cardUserNum);

	public void deleteByBook(String systemBookCode);
	
	public List<ReplaceCard> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);
	
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
	public BigDecimal sumByByShiftTables(List<ShiftTable> shiftTables);
	
	/**
	 * 按支付方式汇总金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);


	public ReplaceCard getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String replaceCardMachine);
	
	public List<ReplaceCard> findToLog(Date dateFrom, Date dateTo);
}
