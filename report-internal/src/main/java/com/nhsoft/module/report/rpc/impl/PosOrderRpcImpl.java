package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.amazon.server.dto.OrderQueryDTO;
import com.nhsoft.amazon.server.dto.OrderReportDTO;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.BranchDailyDirect;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.api.dto.BranchFinishRateTopDTO;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.rpc.BranchTransferGoalsRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.service.SystemBookService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Component
public class PosOrderRpcImpl implements PosOrderRpc {

	@Autowired
	private PosOrderService posOrderService;
	@Autowired
	public SystemBookService systemBookService;
	@Autowired
	private PosOrderRemoteService posOrderRemoteService;
	@Autowired
	private BranchTransferGoalsRpc branchTransferGoalsRpc;


	public boolean isToCenterData(String systemBookCode, Date dateFrom){
		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		Date dpcLimitTime = DateUtil.addDay(now, -2);       //当前日期减2天
		if(dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<BranchRevenueReport> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
		/*SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {       //如果dateTo >= 当前时间，就将当前时间赋给dateTo
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();       //获取业务库数据删除截止日期
		if (dateFrom != null) {
			dateFrom = DateUtil.getMinOfDate(dateFrom);
		} else {
			dateFrom = deleteDate;      //如果dateFrom为null  就将数据删除截止日期赋给dateFrom
		}
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;      //如果截止日期不等于null 且 dateFrom<deleteDate ,就将数据删除截止日期赋给 dateFrom
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Date dpcLimitTime = DateUtil.addDay(now, -2);       //当前日期减2天*/



		Date now = Calendar.getInstance().getTime();
		boolean toCenterData = isToCenterData(systemBookCode, dateFrom);
		Date dpcLimitTime = DateUtil.addDay(now, -2);
		List<Object[]> returnList = new ArrayList<Object[]>();
		if (toCenterData && isMember == false ) {//去大中心查
			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findBranchSummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBranchNum();//分店号
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());//营业额
					objects[2] = list.get(i).getOrderCount();//客单量
					objects[3] = list.get(i).getProfit();//毛利
					returnList.add(objects);
				}
			} else {        //dateTo大于等于当前日期减2天
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list = posOrderRemoteService.findBranchSummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBranchNum();
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					objects[2] = list.get(i).getOrderCount();
					objects[3] = list.get(i).getProfit();
					returnList.add(objects);
				}
				//前2天的数据到本地查
				List<Object[]> localObjects = posOrderService.findMoneyBranchSummary(systemBookCode, branchNums, queryBy, dpcLimitTime, dateTo, isMember);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);
					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0])) {
							objects[1] = ((BigDecimal) objects[1]).add((BigDecimal) localObject[1]);
							objects[2] = (Integer) objects[2] + (Integer) localObject[2];
							objects[3] = ((BigDecimal) objects[3]).add((BigDecimal) localObject[3]);
							find = true;
							break;
						}
					}
					if (!find) {
						returnList.add(localObject);
					}
				}
			}
		} else {
			//全部数据到本地查，不去大中心
			returnList = posOrderService.findMoneyBranchSummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
		}

		List<BranchRevenueReport> list = new ArrayList<BranchRevenueReport>();
		if (returnList.isEmpty()) {
			return list;
		}
		for (int i = 0; i < returnList.size(); i++) {
			Object[] object = returnList.get(i);
			BranchRevenueReport branchRevenueReport = new BranchRevenueReport();
			branchRevenueReport.setBranchNum((Integer) object[0]);
			branchRevenueReport.setBizMoney((BigDecimal) object[1]);
			branchRevenueReport.setOrderCount((Integer) object[2]);
			branchRevenueReport.setProfit((BigDecimal) object[3]);
			list.add(branchRevenueReport);
		}
		return list;

	}

	@Override
	public List<BranchBizRevenueSummary> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {       //如果dateTo >= 当前时间，就将当前时间赋给dateTo
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();       //获取业务库数据删除截止日期
		if (dateFrom != null) {
			dateFrom = DateUtil.getMinOfDate(dateFrom);
		} else {
			dateFrom = deleteDate;      //如果dateFrom为null  就将数据删除截止日期赋给dateFrom
		}
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;      //如果截止日期不等于null 且 dateFrom<deleteDate ,就将数据删除截止日期赋给 dateFrom
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Date dpcLimitTime = DateUtil.addDay(now, -2);       //当前日期减2天
		List<Object[]> returnList = new ArrayList<Object[]>();
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc() && isMember == false) {
			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findDaySummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizday();//营业日
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());//营业额
					objects[2] = list.get(i).getOrderCount();//客单量
					objects[3] = list.get(i).getProfit();//毛利
					returnList.add(objects);
				}
			} else {        //dateTo大于等于当前日期减2天
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list =  posOrderRemoteService.findDaySummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizday();
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					objects[2] = list.get(i).getOrderCount();
					objects[3] = list.get(i).getProfit();
					returnList.add(objects);
				}
				//前2天的数据到本地查
				List<Object[]> localObjects = posOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, queryBy, dpcLimitTime, dateTo, isMember);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0])) {
							objects[1] = ((BigDecimal) objects[1]).add((BigDecimal) localObject[1]);
							objects[2] = (Integer) objects[2] + (Integer) localObject[2];
							objects[3] = ((BigDecimal) objects[3]).add((BigDecimal) localObject[3]);
							find = true;
							break;
						}
					}
					if (!find) {
						returnList.add(localObject);
					}
				}
			}
		} else {
			returnList = posOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
		}
		List<BranchBizRevenueSummary> list = new ArrayList<>();
		if (returnList.isEmpty()) {
			return list;
		}
		for (int i = 0; i < returnList.size(); i++) {
			Object[] object = returnList.get(i);
			BranchBizRevenueSummary branchBizRevenueSummary = new BranchBizRevenueSummary();
			branchBizRevenueSummary.setBiz((String) object[0]);
			branchBizRevenueSummary.setBizMoney((BigDecimal) object[1]);
			branchBizRevenueSummary.setOrderCount((Integer) object[2]);
			branchBizRevenueSummary.setProfit((BigDecimal) object[3]);
			list.add(branchBizRevenueSummary);
		}
		return list;
	}


	@Override
	public List<BranchBizRevenueSummary> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {       //如果dateTo >= 当前时间，就将当前时间赋给dateTo
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();       //获取业务库数据删除截止日期
		if (dateFrom != null) {
			dateFrom = DateUtil.getMinOfDate(dateFrom);
		} else {
			dateFrom = deleteDate;      //如果dateFrom为null  就将数据删除截止日期赋给dateFrom
		}
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;      //如果截止日期不等于null 且 dateFrom<deleteDate ,就将数据删除截止日期赋给 dateFrom
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		Date dpcLimitTime = DateUtil.addDay(now, -2);       //当前日期减2天
		List<Object[]> returnList = new ArrayList<Object[]>();
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc() && isMember == false) {
			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findMonthSummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizMonth();//营业月
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());//营业额
					objects[2] = list.get(i).getOrderCount();//客单量
					objects[3] = list.get(i).getProfit();//毛利
					returnList.add(objects);
				}
			} else {        //dateTo大于等于当前日期减2天
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list = posOrderRemoteService.findMonthSummary(orderQueryDTO);
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizMonth();
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					objects[2] = list.get(i).getOrderCount();
					objects[3] = list.get(i).getProfit();
					returnList.add(objects);
				}
				//前2天的数据到本地查
				List<Object[]> localObjects = posOrderService.findMoneyBizmonthSummary(systemBookCode, branchNums, queryBy, dpcLimitTime, dateTo, isMember);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0])) {
							objects[1] = ((BigDecimal) objects[1]).add((BigDecimal) localObject[1]);
							objects[2] = (Integer) objects[2] + (Integer) localObject[2];
							objects[3] = ((BigDecimal) objects[3]).add((BigDecimal) localObject[3]);
							find = true;
							break;
						}
					}
					if (!find) {
						returnList.add(localObject);
					}
				}
			}
		} else {
			returnList = posOrderService.findMoneyBizmonthSummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
		}
		List<BranchBizRevenueSummary> list = new ArrayList<>();
		if (returnList.isEmpty()) {
			return list;
		}
		for (int i = 0; i < returnList.size(); i++) {
			Object[] object = returnList.get(i);
			BranchBizRevenueSummary branchBizRevenueSummary = new BranchBizRevenueSummary();
			branchBizRevenueSummary.setBiz((String) object[0]);
			branchBizRevenueSummary.setBizMoney((BigDecimal) object[1]);
			branchBizRevenueSummary.setOrderCount((Integer) object[2]);
			branchBizRevenueSummary.setProfit((BigDecimal) object[3]);
			list.add(branchBizRevenueSummary);
		}

		return list;
	}

	@Override
	public List<ItemSummary> findItemSum(String systemBookCode, ItemQueryDTO itemQueryDTO) {
		itemQueryDTO.setSystemBookCode(systemBookCode);
		List<Object[]> objects = posOrderService.findItemSum(itemQueryDTO);
		//封装数据
		int size = objects.size();
		List<ItemSummary> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			ItemSummary itemSummary = new ItemSummary();
			itemSummary.setItemNnum((Integer)object[0]);
			itemSummary.setAmount((BigDecimal) object[1]);
			itemSummary.setMoney((BigDecimal) object[2]);
			itemSummary.setProfit((BigDecimal) object[3]);
			itemSummary.setSaleCount((Integer) object[4]);
			itemSummary.setCost((BigDecimal) object[5]);
			list.add(itemSummary);
		}
		return list;
	}


	@Override
	public List<BranchItemSummaryDTO> findBranchItemSum(String systemBookCode, ItemQueryDTO itemQueryDTO) {
		itemQueryDTO.setSystemBookCode(systemBookCode);
		List<Object[]> objects = posOrderService.findBranchItemSum(itemQueryDTO);
		int size = objects.size();
		List<BranchItemSummaryDTO> list = new ArrayList(size);
		if(objects.isEmpty()){
			return list;
		}
		Integer branchNum;
		Integer itemNum;
		Map<String,BranchItemSummaryDTO> map = new HashMap<>();
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer)object[0];
			itemNum = (Integer) object[1];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(itemNum).toString();
			BranchItemSummaryDTO branchItemSummary = map.get(key);
			if(branchItemSummary != null){
				branchItemSummary.setAmount(branchItemSummary.getAmount().add((BigDecimal) object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2] ));
				branchItemSummary.setMoney(branchItemSummary.getMoney().add((BigDecimal) object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));
				branchItemSummary.setProfit(branchItemSummary.getProfit().add((BigDecimal) object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));
				branchItemSummary.setSaleCount(branchItemSummary.getSaleCount() + ((Integer) object[5] == null ? 0 : (Integer) object[5]));
				branchItemSummary.setCost(branchItemSummary.getCost().add((BigDecimal) object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]));
			}else{
				BranchItemSummaryDTO branchItemSummaryDTO = new BranchItemSummaryDTO();
				branchItemSummaryDTO.setBranchNum((Integer) object[0]);
				branchItemSummaryDTO.setItemNum((Integer) object[1]);
				branchItemSummaryDTO.setAmount((BigDecimal) object[2]);
				branchItemSummaryDTO.setMoney((BigDecimal) object[3]);
				branchItemSummaryDTO.setProfit((BigDecimal) object[4]);
				branchItemSummaryDTO.setSaleCount((Integer) object[5]);
				branchItemSummaryDTO.setCost((BigDecimal) object[6]);
				map.put(key,branchItemSummaryDTO);
			}
		}
		return new ArrayList<BranchItemSummaryDTO>(map.values());
	}

	public List<BranchDaily> findBranchDailys(String systemBookCode, Date dateFrom, Date dateTo){

		List<Object[]> objects = posOrderService.findBranchDailys(systemBookCode,dateFrom,dateTo);
		List<SaleMoneyGoals> goals = branchTransferGoalsRpc.findGoalsByBranchBizday(systemBookCode, null, dateFrom, dateTo);
		int size = objects.size();
		List<BranchDaily> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		int goalsSize = goals.size();
		BigDecimal qty;
		BigDecimal itemCount;
		BigDecimal money;
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			money = (BigDecimal) object[2];
			//移除数据营业额为0的数据
			if(money == null || money .compareTo(BigDecimal.ZERO) == 0){
				continue;
			}
			BranchDaily branchDaily = new BranchDaily();
			branchDaily.setSystemBookCode(systemBookCode);
			branchDaily.setBranchNum((Integer) object[0]);
			branchDaily.setShiftTableBizday((String) object[1]);
			branchDaily.setDailyMoney(money);

			branchDaily.setDailyQty((Integer) object[3]);//客单量
			qty = new BigDecimal(branchDaily.getDailyQty());
			if((Integer) object[4] == null){
				itemCount = BigDecimal.ZERO;//商品数量
			}else{
				itemCount = new BigDecimal((Integer) object[4]);//商品数量
			}
			if(qty.compareTo(BigDecimal.ZERO) == 0){
				branchDaily.setDailyCount(BigDecimal.ZERO);
				branchDaily.setDailyPrice(BigDecimal.ZERO);
			}else{
				branchDaily.setDailyCount(itemCount.divide(qty, 2, ROUND_HALF_UP));//客单购买数
				branchDaily.setDailyPrice(branchDaily.getDailyMoney().divide(qty,4,ROUND_HALF_UP));
			}
			branchDaily.setShiftTableDate(DateUtil.getDateStr(branchDaily.getShiftTableBizday()));
			//将营业额目标封装到分店日汇总中
			for (int j = 0; j <goalsSize ; j++) {
				SaleMoneyGoals saleMoneyGoals = goals.get(j);
				if (saleMoneyGoals.getSystemBookCode().equals(branchDaily.getSystemBookCode()) && saleMoneyGoals.getBranchNum().equals(branchDaily.getBranchNum()) &&
						saleMoneyGoals.getDate().replace("-","").equals(branchDaily.getShiftTableBizday())){
					branchDaily.setTargetMoney(saleMoneyGoals.getSaleMoney());
				}
			}
			list.add(branchDaily);
		}
		return list;
	}

	public List<ItemDailyDetail> findItemDailyDetails(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums) {

		List<Object[]> objects = posOrderService.findItemDailyDetails(systemBookCode, dateFrom, dateTo ,itemNums);
		int size = objects.size();
		List<ItemDailyDetail> list = new ArrayList<>(size);
		if (objects.isEmpty()) {
			return list;
		}
		Map<String, ItemDailyDetail> map = new HashMap<>();
		Integer branchNum;
		String bizday;
		Integer itemNum;
		String source;
		String itemPeriod;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String) object[1];
			itemPeriod = (String) object[2];
			source = (String) object[3];
			itemNum = (Integer) object[4];
			String hour = itemPeriod.substring(0, 2);
			Integer intHour = Integer.valueOf(hour);
			Integer intMin = Integer.valueOf(itemPeriod.substring(2, 4));

			if(intMin >= 0 && intMin <= 30){
				itemPeriod = hour + "30";
			} else {
				itemPeriod = StringUtils.leftPad((intHour + 1) + "", 2, "0") + "00";
			}
			//向map添加数据
			StringBuilder append = new StringBuilder();
			append.append(branchNum).append(bizday).append(source).append(itemNum);
			String key = append.toString();
			ItemDailyDetail itemDailyDetail = map.get(key);
			if (itemDailyDetail == null) {
				itemDailyDetail = new ItemDailyDetail();
				itemDailyDetail.setSystemBookCode(systemBookCode);
				itemDailyDetail.setBranchNum(branchNum);
				itemDailyDetail.setShiftTableBizday(bizday);
				itemDailyDetail.setItemSource(source);
				itemDailyDetail.setItemNum(itemNum);
				itemDailyDetail.setItemMoney(BigDecimal.ZERO);
				itemDailyDetail.setItemAmout(BigDecimal.ZERO);
				itemDailyDetail.setShiftTableDate(DateUtil.getDateStr(itemDailyDetail.getShiftTableBizday()));
				map.put(key, itemDailyDetail);
			}
			BigDecimal money = (BigDecimal) object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal amount = (BigDecimal) object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			itemDailyDetail.append(money,amount,itemPeriod);
		}

		Collection<ItemDailyDetail> values = map.values();
		Iterator<ItemDailyDetail> iterator = values.iterator();
		while(iterator.hasNext()){
			ItemDailyDetail next = iterator.next();
			List<ItemDailyDetail> itemDailyDetails = next.toArray();
			list.addAll(itemDailyDetails);
		}
		//移出营业额和数量为0的数据
		Iterator<ItemDailyDetail> it = list.iterator();
		while (it.hasNext()) {
			ItemDailyDetail next = it.next();
			BigDecimal dailyMoney = next.getItemMoney();
			BigDecimal itemAmout = next.getItemAmout();
			if((dailyMoney.compareTo(BigDecimal.ZERO) == 0) && (itemAmout.compareTo(BigDecimal.ZERO) == 0)){
				it.remove();
			}
		}
		return list;

	}

	@Override
	public List<ItemSaleDailyDTO> findItemSaleDailys(String systemBookCode, Date dateFrom, Date dateTo) {

		List<Object[]> objects = posOrderService.findItemSaleDailys(systemBookCode, dateFrom, dateTo);
		int size = objects.size();
		List<ItemSaleDailyDTO> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		String source;
		for (int i = 0; i < size ; i++) {
			Object[] object = objects.get(i);
			ItemSaleDailyDTO itemSaleDailyDTO = new ItemSaleDailyDTO();
			itemSaleDailyDTO.setSystemBookCode(systemBookCode);
			itemSaleDailyDTO.setBranchNum((Integer) object[0]);
			itemSaleDailyDTO.setShiftTableBizday((String) object[1]);
			itemSaleDailyDTO.setItemNum((Integer) object[2]);
			source = (String) object[3];
			itemSaleDailyDTO.setItemSource(source);
			if((Integer) object[4] > 0){
				itemSaleDailyDTO.setItemMemberTag("会员");
			}else{
				itemSaleDailyDTO.setItemMemberTag("非会员");
			}
			itemSaleDailyDTO.setItemMoney((BigDecimal) object[5]);
			itemSaleDailyDTO.setItemAmount((BigDecimal) object[6]);
			itemSaleDailyDTO.setItemCount((Integer) object[7]);
			itemSaleDailyDTO.setShiftTableDate(DateUtil.getDateStr(itemSaleDailyDTO.getShiftTableBizday()));
			list.add(itemSaleDailyDTO);
		}
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchToDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		List<Object[]> objects = posOrderService.findBusinessCollectionByBranchToDetail(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		Map<Integer, BusinessCollection> map = new HashMap<Integer, BusinessCollection>();
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchToPosOrder(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Object[]> objects = posOrderService.findBusinessCollectionByBranchToPosOrder(systemBookCode, branchNums, dateFrom, dateTo);

		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if (objects.isEmpty()) {
			return list;
		}
		Map<Integer, BusinessCollection> map = new HashMap<Integer, BusinessCollection>();
		for (int i = 0; i < size ; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setAllDiscountMoney(money);
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDayToDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		List<Object[]> objects = posOrderService.findBusinessCollectionByBranchDayToDetail(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}

		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDayToPosOrder(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {


		List<Object[]> objects = posOrderService.findBusinessCollectionByBranchDayToPosOrder(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum + shiftTableBizday);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday, data);
			}
			data.setAllDiscountMoney(money);
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Object[]> objects = posOrderService.findBusinessCollectionByTerminal(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if (objects.isEmpty()) {
			return list;
		}
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();

		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null ? "" : (String) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(branchNum + shiftTableBizday + machineName, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTableToPayment(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {

		List<Object[]> objects = posOrderService.findBusinessCollectionByShiftTableToPayment(systemBookCode, branchNums, dateFrom, dateTo, casher);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal unPaidMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setUnPaidMoney(BigDecimal.ZERO);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			if (type.equals(AppConstants.PAYMENT_GIFTCARD)) {
				data.setUnPaidMoney(data.getUnPaidMoney().add(unPaidMoney));

			}
			if (type.equals(AppConstants.PAYMENT_YINLIAN)) {
				data.setAllBankMoney(data.getAllBankMoney().add(money));
			}
			data.getPosIncomes().add(detail);
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTableToPosOrder(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {

		List<Object[]> objects = posOrderService.findBusinessCollectionByShiftTableToPosOrder(systemBookCode, branchNums, dateFrom, dateTo, casher);
		int size = objects.size();
		List<BusinessCollection> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}

		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>();
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BusinessCollection data = map.get(branchNum.toString() + bizDay + bizNum.toString());
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(branchNum.toString() + bizDay + bizNum.toString(), data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}
		list.addAll(map.values());
		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		List<Object[]> objects = posOrderService.findPosReceiveDiffMoneySumDTOsByBranchCasher(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		Integer branchNum = null;
		String operator = null;
		String type = null;
		BigDecimal money = null;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			operator = (String) object[1];
			type = (String) object[2];
			money = (BigDecimal) object[3];

			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByBranchCashier(list, branchNum, operator);
			if (dto == null) {
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setCasher(operator);
				list.add(dto);
			}
			dto.setTotalSaleMoney(dto.getTotalSaleMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));

			TypeAndTwoValuesDTO saleTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalSaleMoneyDetails(), type);
			if (saleTypeAndTwoValuesDTO == null) {
				saleTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				saleTypeAndTwoValuesDTO.setType(type);
				dto.getTotalSaleMoneyDetails().add(saleTypeAndTwoValuesDTO);
			}
			saleTypeAndTwoValuesDTO.setAmount(saleTypeAndTwoValuesDTO.getAmount().add(money));
		}

		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		List<Object[]> objects = posOrderService.findPosReceiveDiffMoneySumDTOsByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
		int size = objects.size();
		List<PosReceiveDiffMoneySumDTO> list = new ArrayList<PosReceiveDiffMoneySumDTO>(size);
		Integer branchNum = null;
		String bizday = null;
		Integer biznum = null;
		String type = null;
		BigDecimal money = null;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			branchNum = (Integer) object[0];
			bizday = (String) object[1];
			biznum = (Integer) object[2];
			type = (String) object[3];
			money = (BigDecimal) object[4];

			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if (dto == null) {
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setTotalSaleMoney(dto.getTotalSaleMoney().add(money));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), type);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(type);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));

			TypeAndTwoValuesDTO saleTypeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTotalSaleMoneyDetails(), type);
			if (saleTypeAndTwoValuesDTO == null) {
				saleTypeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				saleTypeAndTwoValuesDTO.setType(type);
				dto.getTotalSaleMoneyDetails().add(saleTypeAndTwoValuesDTO);
			}
			saleTypeAndTwoValuesDTO.setAmount(saleTypeAndTwoValuesDTO.getAmount().add(money));
		}
		return list;
	}


	private BusinessCollectionIncome getBusinessCollectionIncome(
			List<BusinessCollectionIncome> businessCollectionIncomes, String name) {
		for (int i = 0; i < businessCollectionIncomes.size(); i++) {
			BusinessCollectionIncome businessCollectionIncome = businessCollectionIncomes.get(i);
			if (businessCollectionIncome.getName().equals(name)) {
				return businessCollectionIncome;
			}
		}
		return null;
	}

}
