package com.nhsoft.report.dao;


import com.nhsoft.report.dto.WeixinNoticeDTO;
import com.nhsoft.report.model.CardDeposit;
import com.nhsoft.report.model.ShiftTable;
import com.nhsoft.report.shared.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardDepositDao {

	/**
	 * 根据卡ID分页查询储值明细
	 * @param systemBookCode
	 * @param cardUserNum
	 * @return
	 */
	public List<CardDeposit> findByCustNum(Integer cardUserNum, int offset, int limit, String sortField, String sortType);
	
	/**
	 * 数量 总金额
	 * @param systemBookCode
	 * @param cardUserNum
	 * @return
	 */
	public Object[] countAndSumByCustNum(Integer cardUserNum);
	
	/**
     * 根据卡ID查询储值明细
     * @param systemBookCode
     * @param cardUserNum
     * @return
     */
    public List<CardDeposit> findByCustNum(Integer cardUserNum);
    
    /**
     * 根据班次查询
     * @param shiftTables
     * @return
     */
    public List<CardDeposit> findByShiftTables(List<ShiftTable> shiftTables);
    
    /**
     * 根据班次汇总数据
     * @param shiftTables
     * @return
     */
    public Object[] sumByByShiftTables(List<ShiftTable> shiftTables);
    
    /**
     * 根据支付方式汇总金额
     * @param shiftTables
     * @return
     */
    public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables);
    
    /**
     * 查询需结算的储值记录
     * @param systemBookCode
     * @param settlementBranchNum
     * @param dateBefore
     * @param offset
     * @param limit
     * @param sortField
     * @param sortType
     * @return
     */
    public List<CardDeposit> findBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore, int offset, int limit, String sortField, String sortType);
	
    /**
     * 需结算的储值记录汇总
     * @param systemBookCode
     * @param settlementBranchNum
     * @param dateBefore
     * @param offset
     * @param limit
     * @param sortField
     * @param sortType
     * @return
     */
	public Object[] sumBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore);
	
	public CardDeposit read(String depositFid);
	
	public void update(CardDeposit cardDeposit);
	
	public List<CardDeposit> findBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore);

	public List<CardDeposit> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery);
	
	/**
	 * 根据分店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param cardTypeNum
	 * @return
	 */
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, Integer cardTypeNum);
	
	/**
	 * 根据发卡门店和操作门店汇总 金额
	 * @param systemBookCode
	 * @param dateTo 
	 * @param dateFrom 
	 * @param cardUserCardType 
	 * @return
	 */
	public List<Object[]> findMoneyByEnrollAndOperatBranch(String systemBookCode, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 查询会员储值记录
	 * @param cardUserNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CardDeposit> findByCardUserNumAndDate(Integer cardUserNum,
                                                      Date dateFrom, Date dateTo);

	/**
	 * 按门店汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	 /**
     * 查询存款方式统计现金
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getCashMoney(String systemBookCode,
                                   List<Integer> branchNums, Date dateFrom, Date dateTo, String type);

    /**
     * 按门店汇总存款现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findCashGroupByBranch(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, String type);

    public void save(CardDeposit cardDeposit);

    public void delete(CardDeposit cardDeposit);

	/**
	 * 查询最后存款记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday
	 * @param shiftTableNum
	 * @param cardUserNum
	 * @return
	 */
	public CardDeposit findLastest(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum,
                                   Integer cardUserNum);

	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum, Integer cardUserNum);

	/**
	 * 按月、门店汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchSumByMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询时间范围内有储值记录的卡ID
	 * @param systemBookCode
	 * @param lastDate
	 * @param now
	 * @return
	 */
	public List<Integer> findCardUserNums(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 查询待微信通知的储值记录
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<WeixinNoticeDTO> findUnNotice(Date dateFrom, Date dateTo);

	/**
	 * 查询客户编号 存款时间 付款金额 存款金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 查询主键最大值的记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday 营业日
	 * @param depositMachine 终端标示号
	 * @return
	 */
	public CardDeposit getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String depositMachine);

	/**
	 * 按营业日汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);


	/**
	 * 批量更新通知标记
	 * @param depositFids
	 */
	public void updateNoticeFlag(List<String> depositFids);


	/**
	 * 按门店查找门店存款额度
	 * @param systemBookCode
	 * @return
	 */
	public List<Object[]> findWarningMoneyByBranch(String systemBookCode);

	/**
	 * 按销售员统计卡存款
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param salerNames
	 * @return
	 */
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames);

	/**
	 * 根据会员编号查询消费记录 只返回部分属性
	 * @param cardUserNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CardDeposit> findShortByUserNum(Integer cardUserNum, Date dateFrom, Date dateTo);

	/**
	 * 根据第三方单据号查询主键
	 * @param systemBookCode
	 * @param depositBillref
	 * @return
	 */
	public String readFidByBillref(String systemBookCode, String depositBillref);

	/**
	 * 按分店 卡类型汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCardTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                Date dateTo, Integer cardUserCardType);


	/**
	 * 根据营业日 、支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按分店 卡类型、支付方式汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCardTypePaymentTypeSum(String systemBookCode, List<Integer> branchNums,
                                                           Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按门店、支付方式汇总储值收款 储值金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchPaymentTypeSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findDateSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                          Date dateTo, String dateType);

	/**
	 * 根据分店、营业日 、支付方式汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchBizdayPaymentTypeSum(String systemBookCode, List<Integer> branchNums,
                                                         Date dateFrom, Date dateTo, Integer cardUserCardType);

	public List<String> findFids(List<String> depositFids);
	
	/**
	 * 查询未结金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo 
	 * @param dateFrom 
	 * @return
	 */
	public BigDecimal getUnSettlementMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按班次汇总记录数 卡收款 卡存款 
	 * @param shiftTable
	 * @param depositCategory
	 * @return
	 */
	public Object[] sumByShiftTable(ShiftTable shiftTable, Integer depositCategory);

	public void deleteByBook(String systemBookCode);

	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	public List<CardDeposit> findUnSettledDeposits(String cardSettlementNo);

	public String generateWeixinBizdayFid(String systemBookCode, Integer branchNum, String shiftTableBizday);

	public List<CardDeposit> findToLog(Date dateFrom, Date dateTo);

	public List<String> findReSendIds(List<String> depositFids);
}
