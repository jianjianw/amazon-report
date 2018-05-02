package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.RelatCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RelatCardDao {

	public List<RelatCard> findByCardUserNum(Integer cardUserNum);
	
    /**
     * 根据班次查询
     * @param shiftTables
     * @return
     */
    public List<RelatCard> findByShiftTables(List<ShiftTable> shiftTables);
    
	public List<RelatCard> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);
	
	 /**
     * 查询续卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getCashMoney(String systemBookCode,
                                   List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按门店汇总续卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findCashGroupByBranch(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo);
    
	public Object[] sumByByShiftTables(List<ShiftTable> shiftTables);
	
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);

	public List<RelatCard> findToLog(Date dateFrom, Date dateTo);
}
