package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.PosItemLogDao;
import com.nhsoft.module.report.dto.PosItemLogDTO;
import com.nhsoft.module.report.model.PosItemLog;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.service.PosItemLogService;
import com.nhsoft.report.utils.CopyUtil;
import com.nhsoft.report.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class PosItemLogServiceImpl implements PosItemLogService {
	
	@Autowired
	private PosItemLogDao posItemLogDao;
	
	
	@Override
	public List<Object[]> findBranchItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findBranchItemFlagSummary(storeQueryCondition);
	}
	
	@Override
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos) {
		return posItemLogDao.findSumByBranchDateItemFlag(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum, memos);
	}
	
	@Override
	public List<Object[]> findSumByItemFlag(String systemBookCode,
											List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
											Integer storehouseNum, List<String> memos) {
		return posItemLogDao.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum, memos);
	}
	
	@Override
	public List<Object[]> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemBizTypeFlagSummary(storeQueryCondition);
	}
	
	@Override
	public List<Object[]> findItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemFlagSummary(storeQueryCondition);
	}
	
	@Override
	public List<Object[]> findBranchFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findBranchFlagSummary(storeQueryCondition);
	}

	@Override
	public List<Object[]> findItemBizFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemBizFlagSummary(storeQueryCondition);
	}


	//以下都是从amazonCenter中移过来的

	@Override
	public List<Object[]> findItemOutAmountSummary(String systemBookCode,
											Integer branchNum, Date dateFrom, Date dateTo,
											List<Integer> itemNums) {
		return posItemLogDao.findItemOutAmountSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findItemOutDateSummary(String systemBookCode,
										  Integer branchNum, Date dateFrom, Date dateTo,
										  List<Integer> itemNums) {
		return posItemLogDao.findItemOutDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Integer> findNoticeItemSummary(String systemBookCode, Integer branchNum, Date dateFrom,
										 Date dateTo, String posItemLogType) {
		return posItemLogDao.findNoticeItemSummary(systemBookCode, branchNum, dateFrom, dateTo, posItemLogType);
	}

	@Override
	public List<Object[]> findItemInOutQtyAndMoneySummary(
			StoreQueryCondition storeQueryCondition) {
		storeQueryCondition.setDateStart(DateUtil.getMinOfDate(storeQueryCondition.getDateStart()));
		storeQueryCondition.setDateEnd(DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd()));
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = storeQueryCondition.getDateStart();
		int whileCount = 0;
		int toYear = DateUtil.getYear(storeQueryCondition.getDateEnd());
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		Date dateTo = storeQueryCondition.getDateEnd();
		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(toDate);
			if(whileCount == 0){
				objects = posItemLogDao.findItemInOutQtyAndMoneySummary(storeQueryCondition);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[2] = object[2] == null? BigDecimal.ZERO:(BigDecimal)object[2];
					object[3] = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
					object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.findItemInOutQtyAndMoneySummary(storeQueryCondition);

				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])
								&& allObject[1].equals(object[1])){
							allObject[2] = ((BigDecimal)allObject[2]).add(object[2] == null?BigDecimal.ZERO:((BigDecimal)object[2]));
							allObject[3] = ((BigDecimal)allObject[3]).add(object[3] == null?BigDecimal.ZERO:((BigDecimal)object[3]));
							allObject[4] = ((BigDecimal)allObject[4]).add(object[4] == null?BigDecimal.ZERO:((BigDecimal)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							find = true;
							break;
						}
					}
					if(!find){
						object[2] = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						object[3] = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
						object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;
	}

	@Override
	public List<Object[]> sumByStoreQueryCondition(
			StoreQueryCondition storeQueryCondition) {
		storeQueryCondition.setDateStart(DateUtil.getMinOfDate(storeQueryCondition.getDateStart()));
		storeQueryCondition.setDateEnd(DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd()));
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = storeQueryCondition.getDateStart();
		int whileCount = 0;
		int toYear = DateUtil.getYear(storeQueryCondition.getDateEnd());
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		Date dateTo = storeQueryCondition.getDateEnd();

		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(toDate);
			if(whileCount == 0){
				objects = posItemLogDao.sumByStoreQueryCondition(storeQueryCondition);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[1] = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
					object[2] = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
					object[3] = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
					object[4] = object[4] == null?0:(Integer)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.sumByStoreQueryCondition(storeQueryCondition);

				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])){
							allObject[1] = ((BigDecimal)allObject[1]).add(object[1] == null?BigDecimal.ZERO:((BigDecimal)object[1]));
							allObject[2] = ((BigDecimal)allObject[2]).add(object[2] == null?BigDecimal.ZERO:((BigDecimal)object[2]));
							allObject[3] = ((BigDecimal)allObject[3]).add(object[3] == null?BigDecimal.ZERO:((BigDecimal)object[3]));
							allObject[4] = ((Integer)allObject[4]) + (object[4] == null?0:((Integer)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							find = true;
							break;
						}
					}
					if(!find){
						object[1] = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
						object[2] = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						object[3] = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
						object[4] = object[4] == null?0:(Integer)object[4];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;

	}

	@Override
	public List<PosItemLog> findLastSummary(String systemBookCode, Integer branchNum,
									 Integer storehouseNum, Date endDate) {
		return posItemLogDao.findLastSummary(systemBookCode, branchNum, storehouseNum, endDate);
	}

	@Override
	public List<Object[]> findItemSummaryInOutQtyAndMoney(
			StoreQueryCondition storeQueryCondition) {
		storeQueryCondition.setDateStart(DateUtil.getMinOfDate(storeQueryCondition.getDateStart()));
		storeQueryCondition.setDateEnd(DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd()));
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = storeQueryCondition.getDateStart();
		int whileCount = 0;
		int toYear = DateUtil.getYear(storeQueryCondition.getDateEnd());
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		Date dateTo = storeQueryCondition.getDateEnd();

		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(toDate);
			if(whileCount == 0){
				objects = posItemLogDao.findItemSummaryInOutQtyAndMoney(storeQueryCondition);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[3] = object[3] == null?"":(String)object[3];
					object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
					object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.findItemSummaryInOutQtyAndMoney(storeQueryCondition);

				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])
								&& allObject[1].equals(object[1])
								&& allObject[2].equals(object[2])
								&& allObject[3].equals(object[3] == null?"":(String)object[3])){

							allObject[4] = ((BigDecimal)allObject[4]).add(object[4] == null?BigDecimal.ZERO:((BigDecimal)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							allObject[6] = ((BigDecimal)allObject[6]).add(object[6] == null?BigDecimal.ZERO:((BigDecimal)object[6]));
							allObject[7] = ((BigDecimal)allObject[7]).add(object[7] == null?BigDecimal.ZERO:((BigDecimal)object[7]));
							find = true;
							break;
						}
					}
					if(!find){
						object[3] = object[3] == null?"":(String)object[3];
						object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
						object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;
	}

	@Override
	public List<Object[]> findBranchSummaryInOutQtyAndMoney(
			StoreQueryCondition storeQueryCondition) {
		storeQueryCondition.setDateStart(DateUtil.getMinOfDate(storeQueryCondition.getDateStart()));
		storeQueryCondition.setDateEnd(DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd()));
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = storeQueryCondition.getDateStart();
		int whileCount = 0;
		int toYear = DateUtil.getYear(storeQueryCondition.getDateEnd());
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		Date dateTo = storeQueryCondition.getDateEnd();
		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(toDate);
			if(whileCount == 0){
				objects = posItemLogDao.findBranchSummaryInOutQtyAndMoney(storeQueryCondition);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[3] = object[3] == null?"":(String)object[3];
					object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
					object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.findBranchSummaryInOutQtyAndMoney(storeQueryCondition);

				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[1] = object[1] == null?0:(Integer)object[1];
					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])
								&& allObject[1].equals(object[1])
								&& allObject[2].equals(object[2])
								&& allObject[3].equals(object[3] == null?"":(String)object[3])){

							allObject[4] = ((BigDecimal)allObject[4]).add(object[4] == null?BigDecimal.ZERO:((BigDecimal)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							allObject[6] = ((BigDecimal)allObject[6]).add(object[6] == null?BigDecimal.ZERO:((BigDecimal)object[6]));
							allObject[7] = ((BigDecimal)allObject[7]).add(object[7] == null?BigDecimal.ZERO:((BigDecimal)object[7]));
							find = true;
							break;
						}
					}
					if(!find){
						object[3] = object[3] == null?"":(String)object[3];
						object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
						object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;
	}

	@Override
	public List<Object[]> findItemAmountBySummary(String systemBookCode,
												  Integer branchNum, Date dateFrom, Date dateTo, String summaries) {
		return posItemLogDao.findItemAmountBySummary(systemBookCode, branchNum, dateFrom, dateTo, summaries);
	}

	@Override
	public List<Object[]> findBranchAndItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findBranchAndItemFlagSummary(storeQueryCondition);
	}

	@Override
	public int countByBranch(String systemBookCode, Integer branchNum) {
		return posItemLogDao.countByBranch(systemBookCode, branchNum);
	}

	@Override
	public Date getFirstDate(String systemBookCode, Integer branchNum) {
		return posItemLogDao.getFirstDate(systemBookCode, branchNum);
	}

	@Override
	public List<Object[]> findMoneyBranchFlagSummary(String systemBookCode,
												List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posItemLogDao.findMoneyBranchFlagSummary(systemBookCode, branchNums, dateFrom, dateTo);
	}


	@Override
	public List<Object[]> findMoneyBranchItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posItemLogDao.findMoneyBranchItemFlagSummary(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findMinPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return posItemLogDao.findMinPriceAndDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findMaxPriceAndDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return posItemLogDao.findMaxPriceAndDateSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findItemDetailSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemDetailSummary(storeQueryCondition);
	}

	@Override
	public List<PosItemLog> findByDate(String systemBookCode, Date dateFrom,
									   Date dateTo) {
		return posItemLogDao.findByDate(systemBookCode, dateFrom, dateTo);
	}

	@Override
	public List<PosItemLog> findUnUploadSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit) {
		return posItemLogDao.findUnUploadSummary(systemBookCode, branchNum, dateFrom, dateTo, offset, limit);
	}

	@Override
	public List<PosItemLog> findRepeatAuditOrderSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		return posItemLogDao.findRepeatAuditOrderSummary(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		return posItemLogDao.findSummary(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public int countUnUpload(String systemBookCode, Date dateFrom, Date dateTo) {
		return posItemLogDao.countUnUpload(systemBookCode, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findBranchItemFlagMemoSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = dateFrom;
		int whileCount = 0;
		int toYear = DateUtil.getYear(dateTo);
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			if(whileCount == 0){
				objects = posItemLogDao.findBranchItemFlagMemoSummary(systemBookCode, branchNums, compareDate, toDate, summaries, itemNums, storehouseNum);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[3] = object[3] == null?"":(String)object[3];
					object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
					object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
					object[8] = object[8] == null?BigDecimal.ZERO:(BigDecimal)object[8];
					object[9] = object[9] == null?"":(String)object[9];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.findBranchItemFlagMemoSummary(systemBookCode, branchNums, compareDate, toDate, summaries, itemNums, storehouseNum);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);

					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])
								&& allObject[1].equals(object[1])
								&& allObject[2].equals(object[2])
								&& allObject[3].equals(object[3] == null?"":(String)object[3])
								){
							allObject[4] = ((BigDecimal)allObject[4]).add(object[4] == null?BigDecimal.ZERO:((BigDecimal)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							allObject[6] = ((BigDecimal)allObject[6]).add(object[6] == null?BigDecimal.ZERO:((BigDecimal)object[6]));
							allObject[7] = ((BigDecimal)allObject[7]).add(object[7] == null?BigDecimal.ZERO:((BigDecimal)object[7]));
							allObject[8] = ((BigDecimal)allObject[8]).add(object[8] == null?BigDecimal.ZERO:((BigDecimal)object[8]));
							find = true;
							break;
						}
					}
					if(!find){
						object[3] = object[3] == null?"":(String)object[3];
						object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
						object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
						object[8] = object[8] == null?BigDecimal.ZERO:(BigDecimal)object[8];
						object[9] = object[9] == null?"":(String)object[9];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;

	}


	@Override
	public List<Object[]> findBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, String posItemLogSummarys,Boolean itemDelTag) {
		return posItemLogDao.findBranchFlagSummary(systemBookCode, branchNums, dateFrom, dateTo, posItemLogSummarys,itemDelTag);
	}

	@Override
	public List<Object[]> findItemInOutSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemInOutSummary(storeQueryCondition);
	}

	@Override
	public List<PosItemLog> findByBillNoSummary(String systemBookCode, String orderNo) {
		return posItemLogDao.findByBillNoSummary(systemBookCode,orderNo);
	}

	@Override
	public int countByBillNo(String systemBookCode, String posItemLogBillNo) {
		return posItemLogDao.countByBillNo(systemBookCode,posItemLogBillNo);
	}

	@Override
	public List<Object[]> findBranchInOutSummary(StoreQueryCondition storeQueryCondition) {
		storeQueryCondition.setDateStart(DateUtil.getMinOfDate(storeQueryCondition.getDateStart()));
		storeQueryCondition.setDateEnd(DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd()));
		Date compareDate = storeQueryCondition.getDateStart();
		Date dateTo = storeQueryCondition.getDateEnd();
		int fromYear = DateUtil.getYear(compareDate);
		int toYear = DateUtil.getYear(dateTo);
		List<Object[]> objects = new ArrayList<Object[]>();
		while(fromYear <= toYear){
			Date endOfYear = DateUtil.getLastDayOfYear(compareDate);
			if(endOfYear.compareTo(dateTo) > 0){
				endOfYear = dateTo;
			}

			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(endOfYear);
			objects.addAll(posItemLogDao.findBranchInOutSummary(storeQueryCondition));
			compareDate = DateUtil.getFirstDayOfYear(DateUtil.addYear(compareDate, 1));
			fromYear++;
		}
		return objects;
	}

	@Override
	public List<Object[]> findDateItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findDateItemFlagSummary(storeQueryCondition);
	}

	@Override
	public List<Integer> findItemNumSummary(String systemBookCode, Integer branchNum, Integer storehouseNum, Date dateFrom,
									  Date dateTo) {
		return posItemLogDao.findItemNumSummary(systemBookCode, branchNum, storehouseNum, dateFrom, dateTo);
	}

	@Override
	public boolean checkExists(String posItemLogBillNo, Integer posItemLogBillDetailNum, Integer posItemLogSerialNumber) {
		return posItemLogDao.checkExists(posItemLogBillNo,posItemLogBillDetailNum,posItemLogSerialNumber);
	}

	@Override
	public List<Object[]> findBranchItemMatrixFlagSummary(StoreQueryCondition storeQueryCondition) {
		Date dateFrom = DateUtil.getMinOfDate(storeQueryCondition.getDateStart());
		Date dateTo = DateUtil.getMaxOfDate(storeQueryCondition.getDateEnd());
		List<Object[]> allObjects = new ArrayList<Object[]>();
		Date compareDate = dateFrom;
		int whileCount = 0;
		int toYear = DateUtil.getYear(dateTo);
		int fromYear = 0;
		List<Object[]> objects = null;
		Object[] object;
		Object[] allObject;
		while(compareDate.compareTo(dateTo) < 0){

			fromYear = DateUtil.getYear(compareDate);
			Date toDate = fromYear == toYear?dateTo:DateUtil.getLastDayOfYear(compareDate);
			storeQueryCondition.setDateStart(compareDate);
			storeQueryCondition.setDateEnd(toDate);
			if(whileCount == 0){

				objects = posItemLogDao.findBranchItemMatrixFlagSummary(storeQueryCondition);

				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					object[2] = object[2] == null?0:(Integer)object[2];
					object[4] = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
					object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
					object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
					object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
					object[8] = object[8] == null?BigDecimal.ZERO:(BigDecimal)object[8];
					object[9] = object[9] == null?"":(String)object[9];
					allObjects.add(object);
				}
			} else {
				objects = posItemLogDao.findBranchItemMatrixFlagSummary(storeQueryCondition);
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);

					boolean find = false;
					for(int j = 0;j < allObjects.size();j++){
						allObject = allObjects.get(j);
						if(allObject[0].equals(object[0])
								&& allObject[1].equals(object[1])
								&& allObject[2].equals(object[2] == null?0:(Integer)object[2])
								&& allObject[3].equals(object[3])
								){
							allObject[4] = ((BigDecimal)allObject[4]).add(object[4] == null?BigDecimal.ZERO:((BigDecimal)object[4]));
							allObject[5] = ((BigDecimal)allObject[5]).add(object[5] == null?BigDecimal.ZERO:((BigDecimal)object[5]));
							allObject[6] = ((BigDecimal)allObject[6]).add(object[6] == null?BigDecimal.ZERO:((BigDecimal)object[6]));
							allObject[7] = ((BigDecimal)allObject[7]).add(object[7] == null?BigDecimal.ZERO:((BigDecimal)object[7]));
							allObject[8] = ((BigDecimal)allObject[8]).add(object[8] == null?BigDecimal.ZERO:((BigDecimal)object[8]));
							find = true;
							break;
						}
					}
					if(!find){
						object[2] = object[2] == null?0:(Integer)object[2];
						object[5] = object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5];
						object[6] = object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6];
						object[7] = object[7] == null?BigDecimal.ZERO:(BigDecimal)object[7];
						object[8] = object[8] == null?BigDecimal.ZERO:(BigDecimal)object[8];
						object[9] = object[9] == null?"":(String)object[9];
						allObjects.add(object);
					}

				}
			}
			whileCount++;
			compareDate = DateUtil.getMinOfYear(DateUtil.addYear(compareDate, 1));
		}
		return allObjects;
	}

	@Override
	public PosItemLog read(String posItemLogBillNo, Integer posItemLogBillDetailNum) {
		return posItemLogDao.read(posItemLogBillNo,posItemLogBillDetailNum);
	}
}
