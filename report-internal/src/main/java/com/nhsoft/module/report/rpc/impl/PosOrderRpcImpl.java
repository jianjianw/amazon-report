package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.amazon.server.dto.OrderQueryDTO;
import com.nhsoft.amazon.server.dto.OrderReportDTO;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.report.api.dto.BranchFinishRateTopDTO;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.rpc.BranchTransferGoalsRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.service.SystemBookService;
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

	@Override
	public List<BranchRevenueReport> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

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
		BigDecimal value1;
		BigDecimal value2;
		Date dpcLimitTime = DateUtil.addDay(now, -2);       //当前日期减2天
		List<Object[]> returnList = new ArrayList<Object[]>();
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()&& isMember == false) {
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
		/*List<Object[]> returnList = posOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
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
		return list;*/
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
		BigDecimal value1;
		BigDecimal value2;
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
		List<ItemSummary> list = new ArrayList<>();
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <objects.size() ; i++) {
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
		List<BranchItemSummaryDTO> list = new ArrayList();
		if(objects.isEmpty()){
			return list;
		}
		Integer branchNum;
		Integer itemNum;
		Map<String,BranchItemSummaryDTO> map = new HashMap<>();
		for (int i = 0; i <objects.size() ; i++) {
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

	public List<BranchDaily> findBranchDailySummary(String systemBookCode, Date dateFrom, Date dateTo){

		List<Object[]> objects = posOrderService.findBranchDailySummary(systemBookCode,dateFrom,dateTo);
		List<SaleMoneyGoals> goals = branchTransferGoalsRpc.findGoalsByBranchBizday(systemBookCode, null, dateFrom, dateTo);

		List<BranchDaily> list = new ArrayList<>();
		if(objects.isEmpty()){
			return list;
		}

		for (int i = 0; i <objects.size() ; i++) {
			Object[] object = objects.get(i);
			BranchDaily branchDaily = new BranchDaily();
			branchDaily.setSystemBookCode(systemBookCode);
			branchDaily.setBranchNum((Integer) object[0]);
			branchDaily.setShiftTableBizday((String) object[1]);
			branchDaily.setDailyMoney((BigDecimal) object[2]);
			branchDaily.setDailyQty((Integer) object[3]);
			branchDaily.setShiftTableDate(DateUtil.getDateStr(branchDaily.getShiftTableBizday()));
			if(branchDaily.getDailyQty() == 0){
				branchDaily.setDailyPrice(BigDecimal.ZERO);
			}else{
				Integer qty = branchDaily.getDailyQty();
				branchDaily.setDailyPrice(branchDaily.getDailyMoney().divide(new BigDecimal(qty),4,ROUND_HALF_UP));
			}
			//将营业额目标封装到分店日汇总中
			for (int j = 0; j <goals.size() ; j++) {
				SaleMoneyGoals saleMoneyGoals = goals.get(j);
				if (saleMoneyGoals.getSystemBookCode().equals(branchDaily.getSystemBookCode()) && saleMoneyGoals.getBranchNum().equals(branchDaily.getBranchNum()) &&
						saleMoneyGoals.getDate().replace("-","").equals(branchDaily.getShiftTableBizday())){
					branchDaily.setTargetMoney(saleMoneyGoals.getSaleMoney());
				}
			}
			list.add(branchDaily);
		}

		//移除数据
		Iterator<BranchDaily> iterator = list.iterator();
		while (iterator.hasNext()) {
			BranchDaily next = iterator.next();
			BigDecimal dailyMoney = next.getDailyMoney();
			if(dailyMoney == null ||dailyMoney.compareTo(BigDecimal.ZERO) == 0){
				iterator.remove();
			}
		}
		//排序
		Comparator<BranchDaily> comparing = Comparator.comparing(BranchDaily::getShiftTableBizday);
		list.sort(comparing);//comparing.reversed()
		return list;
	}


	public List<ItemDaily> findItemDailySummary(String systemBookCode,Date dateFrom, Date dateTo){

		List<Object[]> objects = posOrderService.findItemDailySummary(systemBookCode,dateFrom,dateTo);
		List<ItemDaily> list = new ArrayList<>();
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <objects.size() ; i++) {
			Object[] object = objects.get(i);
			ItemDaily itemDaily = new ItemDaily();
			itemDaily.setSystemBookCode(systemBookCode);
			itemDaily.setBranchNum((Integer) object[0]);
			itemDaily.setShiftTableBizday((String) object[1]);
			itemDaily.setItemNum((Integer) object[2]);
			itemDaily.setItemMoney((BigDecimal) object[3]);
			itemDaily.setItemAmount((BigDecimal) object[4]);
			itemDaily.setShiftTableDate(DateUtil.getDateStr(itemDaily.getShiftTableBizday()));
			list.add(itemDaily);
		}
		return list;
	}



	public List<ItemDailyDetail> findItemDailyDetailSummary(String systemBookCode, Date dateFrom, Date dateTo) {
		List<Object[]> objects = posOrderService.findItemDailyDetailSummary(systemBookCode, dateFrom, dateTo);
		List<ItemDailyDetail> list = new ArrayList<>();
		if (objects.isEmpty()) {
			return list;
		}
		Map<String, ItemDailyDetail> map = new HashMap<>();
		Integer branchNum;
		String bizday;
		Integer itemNum;
		String source;
		String itemPeriod;
		for (int i = 0; i < objects.size(); i++) {
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

		/*//移除数据
		Iterator<ItemDailyDetail> it = list.iterator();
		while (it.hasNext()) {
			ItemDailyDetail next = it.next();
			BigDecimal dailyMoney = next.getItemMoney();
			BigDecimal itemAmout = next.getItemAmout();
			if((dailyMoney == null ||dailyMoney.compareTo(BigDecimal.ZERO) == 0) && (itemAmout == null || itemAmout.compareTo(BigDecimal.ZERO) == 0)){
				iterator.remove();
			}
		}*/
		return list;

	}

}
