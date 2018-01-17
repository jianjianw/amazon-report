package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.TransferOutOrderDao;
import com.nhsoft.module.report.model.TransferOutOrder;
import com.nhsoft.module.report.service.TransferOutOrderService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.BaseManager;
import com.nhsoft.report.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TransferOutOrderServiceImpl extends BaseManager implements TransferOutOrderService {



	private static Logger logger = LoggerFactory.getLogger(TransferOutOrderServiceImpl.class);
	@Autowired
	private TransferOutOrderDao transferOutOrderDao;

	@Override
	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyByBizday(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums,
												List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryLists, List<Integer> itemNums) {
		return transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, branchNums, dateFrom,
				dateTo, categoryLists, itemNums);
	}


	@Override
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return transferOutOrderDao.findBranchItemMatrixSummary(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums) {
		return transferOutOrderDao.findUnTransferedItems(systemBookCode, outBranchNum, branchNums, storehouseNums);
	}

	@Override
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyByBranch(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyBranchSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyBizdaySummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyBymonthSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}
	
	@Override
	public List<Integer> findTransferedItems(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		String key = systemBookCode + "|" + outBranchNum + "|" + branchNum;
		int flag = AppUtil.getValue(RedisUtil.get(AppConstants.REDIS_PRE_OLD_TRANSFER_OUT_DATA_SYNCH + key), Integer.class);
		if(flag == 0) {
			List<Integer> itemNums = transferOutOrderDao.findTransferedItems(systemBookCode, branchNum, outBranchNum);
			RedisUtil.setPutList(AppConstants.REDIS_PRE_TRANSFERED_ITEM + key, itemNums);
			RedisUtil.put(AppConstants.REDIS_PRE_OLD_TRANSFER_OUT_DATA_SYNCH + key, 1);
			return itemNums;
		} else {
			return new ArrayList<Integer>((Set<Integer>)RedisUtil.setGet(AppConstants.REDIS_PRE_TRANSFERED_ITEM + key));
			
		}
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer outBranchNum, Date dateFrom, Date dateTo, List<Integer> branchNums) {
		return transferOutOrderDao.findMoneyByBranchNums(systemBookCode, outBranchNum, dateFrom, dateTo, branchNums);
	}
	
	@Override
	public List<Object[]> findDueMoney(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findDueMoney(systemBookCode, outBranchNum, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer outBranchNum) {
		return transferOutOrderDao.readBranchUnPaidMoney(systemBookCode, branchNum, outBranchNum);
	}
	
	@Override
	public List<TransferOutOrder> findBySettleBranch(String systemBookCode, Integer branchNum, Integer outBranchNum, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findBySettleBranch(systemBookCode, branchNum, outBranchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return transferOutOrderDao.findItemSummary(systemBookCode,outBranchNums,branchNums,dateFrom,dateTo,itemNums);
	}

}