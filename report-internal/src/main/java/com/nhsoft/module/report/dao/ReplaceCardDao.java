package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.ReplaceCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReplaceCardDao {

	public List<ReplaceCard> findByCardUserNum(Integer cardUserNum);
	
	public List<ReplaceCard> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);

	 /**
     * 查询换卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getCashMoney(String systemBookCode,
                                   List<Integer> branchNums, Date dateFrom, Date dateTo);
    /**
     * 按门店汇总换卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum);
	
	public BigDecimal sumByByShiftTables(List<ShiftTable> shiftTables);
	
	/**
	 * 按支付方式汇总金额
	 * @param shiftTables
	 * @return
	 */
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);

	/**
	 * 按分店汇总卡换卡数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public ReplaceCard getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String replaceCardMachine);

	public void deleteByBook(String systemBookCode);

	public List<ReplaceCard> findToLog(Date dateFrom, Date dateTo);
}
