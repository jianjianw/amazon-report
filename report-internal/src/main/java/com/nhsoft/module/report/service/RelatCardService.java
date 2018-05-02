package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.RelatCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

/**
 * 续卡
 * @author nhsoft
 *
 */
public interface RelatCardService {

	/**
	 * 查询
	 * @param cardUserNum 卡主键
	 * @return
	 */
	public List<RelatCard> findByCardUserNum(Integer cardUserNum);
	
    /**
     * 根据班次查询
     * @param shiftTables 班次列表
     * @return
     */
    public List<RelatCard> findByShiftTables(List<ShiftTable> shiftTables);
    
	public List<RelatCard> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);
	
	/**
	 * 查询续卡数量  续卡金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public Object[] sumByByShiftTables(List<ShiftTable> shiftTables);
	
	/**
	 * 按支付方式汇总续卡金额
	 * @param shiftTables 班次列表
	 * @return
	 */
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);

	public List<RelatCard> findToLog(Date dateFrom, Date dateTo);

}
