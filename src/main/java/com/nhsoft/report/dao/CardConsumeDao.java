package com.nhsoft.report.dao;


import com.nhsoft.report.dto.WeixinNoticeDTO;
import com.nhsoft.report.model.CardConsume;
import com.nhsoft.report.model.ShiftTable;
import com.nhsoft.report.shared.queryBuilder.CardReportQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardConsumeDao {

	/**
	 * 根据卡ID分页查询消费明细
	 * @param systemBookCode
	 * @param cardUserNum
	 * @return
	 */
	public List<CardConsume> findByCustNum(Integer cardUserNum, int offset, int limit, String sortField, String sortType);
	
	/**
	 * 数量 总金额
	 * @param systemBookCode
	 * @param cardUserNum
	 * @return
	 */
	public Object[] countAndSumByCustNum(Integer cardUserNum);
	
	/**
	 * 根据卡查询消费明细
	 * @param cardUserNum
	 * @return
	 */
	public List<CardConsume> findByCustNum(Integer cardUserNum);
	
	/**
	 *  查询需结算的消费记录
	 * @param systemBookCode
	 * @param settlementBranchNum
	 * @param dateBefore
	 * @param isReCard
	 * @param offset
	 * @param limit
	 * @param sortField
	 * @param sortType
	 * @return
	 */
	public List<CardConsume> findBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore, Boolean isReCard, int offset, int limit, String sortField, String sortType);
	
	/**
	 *  需结算的消费记录汇总
	 * @param systemBookCode
	 * @param settlementBranchNum
	 * @param dateBefore
	 * @param isReCard
	 * @param offset
	 * @param limit
	 * @param sortField
	 * @param sortType
	 * @return
	 */
	public Object[] sumBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore, Boolean isReCard);
	
	public CardConsume read(String consumeFid);
	
	public void update(CardConsume cardConsume);
	
	public List<CardConsume> findBySettlement(String systemBookCode, Integer settlementBranchNum, Date dateBefore, Boolean isReCard);

	public List<CardConsume> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
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
	 * 查询会员消费明细
	 * @param cardUserNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CardConsume> findByCardUserNumAndDate(Integer cardUserNum, Date dateFrom, Date dateTo);
	
	public List<Object[]> sumByCardUserNumAndDate(Integer cardUserNum, Date dateFrom, Date dateTo);
	
	public void save(CardConsume cardConsume);
	
	public void delete(CardConsume cardConsume);

	/**
	 * 查询中奖次数
	 * @param systemBookCode
	 * @param object
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Integer countReward(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 根据班次查询消费记录
	 * @param shiftTables
	 * @return
	 */
	public List<CardConsume> findByShiftTables(List<ShiftTable> shiftTables);
	
	/**
	 * 查询最后消费记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday
	 * @param shiftTableNum
	 * @param parseInt
	 * @return
	 */
	public CardConsume findLastest(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum, Integer cardUserNum);

	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum, Integer cardUserNum);

	/**
	 * 按门店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType 
	 * @return
	 */
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 按门店汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 根据营业日汇总卡消费金额
	 * @param shiftTables
	 * @param hasBillNum
	 */
	public BigDecimal sumByByShiftTables(List<ShiftTable> shiftTables, Boolean hasBillNum);
	
	/**
	 * 查询时间范围内有消费记录的卡ID
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Integer> findCardUserNums(String systemBookCode, Date dateFrom, Date dateTo);
	
	/**
	 * 查询待微信通知的消费记录
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<WeixinNoticeDTO> findUnNotice(Date dateFrom, Date dateTo);

	/**
	 * 查询客户编号 消费时间 消费金额明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findUserDateMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 根据关联单据号查询交易流水
	 * @param systemBookCode
	 * @param consumeOrderNo 关联单据号
	 * @return
	 */
	public String readFidByOrderNo(String systemBookCode, String consumeOrderNo);

	/**
	 * 查询最大消费次数
	 * @param systemBookCode
	 * @param cardUserNum
	 * @return
	 */
	public int getMaxCount(String systemBookCode, Integer cardUserNum);
	
	
	/**
	 * 查询主键最大值的记录
	 * @param systemBookCode
	 * @param branchNum
	 * @param shiftTableBizday 营业日
	 * @param consumeMachine 终端标志号
	 * @return
	 */
	public CardConsume getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String consumeMachine);

	/**
	 * 根据营业日汇总记录数 金额
	 * @param shiftTable
	 * @param consumeCategory 
	 * @return
	 */
	public Object[] sumByShiftTable(ShiftTable shiftTable, Integer consumeCategory);
	
	/**
	 * 根据营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType 
	 * @return
	 */
	public List<Object[]> findBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);


	/**
	 * 根据营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdayCategorySum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);

	/**
	 * 批量更新通知标记
	 * @param consumeFids
	 */
	public void updateNoticeFlag(List<String> consumeFids);
	
	/**
	 * 按销售员汇总
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
	public List<CardConsume> findShortByUserNum(Integer cardUserNum, Date dateFrom, Date dateTo);

	/**
	 * 按分店 卡类型汇总金额
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
	 * 按月份汇总消费金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchSumByMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                               Date dateTo);

	/**
	 * 按营业日汇总消费金额
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
	 * 根据分店、营业日汇总金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                              Date dateTo, Integer cardUserCardType);

	public List<String> findFids(List<String> consumeFids);

	public void deleteByBook(String systemBookCode);

	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	public List<CardConsume> findUnSettledConsumes(String cardSettlementNo);
	
	public String generateWeixinBizdayFid(String systemBookCode, Integer branchNum, String shiftTableBizday);
	
	public void flush();

	public List<CardConsume> findToLog(Date dateFrom, Date dateTo);

	public List<String> findReSendIds(List<String> consumeFids);


}
