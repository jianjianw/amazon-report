package com.nhsoft.module.report.service.impl;

import com.nhsoft.amazon.server.dto.OrderQueryDTO;
import com.nhsoft.amazon.server.dto.OrderReportDTO;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.service.SystemBookService;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PosOrderServiceImpl implements PosOrderService {
	private static final Logger logger = LoggerFactory.getLogger(PosOrderServiceImpl.class);
	@Autowired
	private PosOrderDao posOrderDao;
	@Autowired
	public SystemBookService systemBookService;
	@Autowired
	private PosOrderRemoteService posOrderRemoteService;


	@Override
	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findCustomReportByBizday(systemBookCode, branchNums, dateFrom, dateTo);
	}


	@Override
	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery) {
		return posOrderDao.findSummaryByBizday(cardReportQuery);
	}

	@Override
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery) {
		return posOrderDao.findSummaryByBranch(cardReportQuery);
	}

	@Override
	public List<Object[]> findBranchDetailSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findBranchDetailSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findBranchItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO) {
		return posOrderDao.findItemSum(itemQueryDTO);
	}


	@Override
	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO) {
		return posOrderDao.findBranchItemSum(itemQueryDTO);
	}

	@Override
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, List<Integer> itemNums) {
		return posOrderDao.findBizmonthItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
	}


	@Override
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findItemMatrixSum(systemBookCode, branchNums, dateFrom, dateTo, false);
	}


	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findSupplierSum(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums, queryKit);
	}


	@Override
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit) {
		return posOrderDao.findItemSupplierMatrixSum(systemBookCode, branchNums, dateFrom, dateTo, queryKit);
	}


	@Override
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums) {
		return posOrderDao.findItemSupplierSumByCategory(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, queryKit, itemNums);
	}

	@Override
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, boolean queryKit) {
		return posOrderDao.findOrderDetailWithSupplier(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, queryKit);
	}

	@Override
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return posOrderDao.findCustomReportByBranch(systemBookCode, branchNums, dateFrom, dateTo, dateType);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo,Boolean isMember) {
		//TODO 需要添加type参数，下面的两个接口也需要
		int type = 0;//暂时解决报错，等会去掉

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
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()) {

			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findBranchSummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBranchNum();//分店号
					if(type == 0){
						objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());//营业额

					} else if(type == 1){
						objects[1] = BigDecimal.valueOf(list.get(i).getOrderCount());
					} else if(type == 2){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[1] = BigDecimal.ZERO;
						}

					} else if(type == 5){
						objects[1] = list.get(i).getProfit();
					} else if(type == 6){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = list.get(i).getProfit();
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = BigDecimal.ZERO;
						} else {
							objects[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						objects[2] = value1;//客单量
						objects[3] = value2;//毛利


					}
					returnList.add(objects);
				}
				return returnList;
			} else {        //dateTo大于等于当前日期减2天

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list = posOrderRemoteService.findBranchDaySummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
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
				List<Object[]> localObjects = posOrderDao.findMoneyBranchSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, false);
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
				if(type > 0){
					Object[] object = null;
					for(int i = 0;i < returnList.size();i++){
						object = returnList.get(i);
						if(type == 1){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

							object[1] = value1;


						} else if(type == 2){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
							value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
							}
						} else if(type == 5){
							object[1] = object[3];
						} else if(type == 6){
							value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
							value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
							}
							object[2] = value1;
							object[3] = value2;
						}
					}
				}
				return returnList;
			}
		} else {
			//不去大中心查数据，都到本地查
			List<Object[]> objects = null;
			if(type == 3 || type == 4){
				objects = posOrderDao.findMoneyBranchSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, true);

			} else {
				objects = posOrderDao.findMoneyBranchSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, false);

			}
			if(type > 0){
				Object[] object = null;
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					if(type == 1 || type == 3){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

						object[1] = value1;

					} else if(type == 2 || type == 4){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
						value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if(type == 5){
						object[1] = object[3];
					} else if(type == 6){
						value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
						value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						object[2] = value1;
						object[3] = value2;
					}
				}
			}

			return objects;
		}

	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

		int type = 0;//暂时解决报错，等会去掉

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
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()) {

			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findDaySummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizday();//营业日
					//objects[0] = list.get(i).getBranchNum();//分店号
					if(type == 0){
						objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());//营业额

					} else if(type == 1){
						objects[1] = BigDecimal.valueOf(list.get(i).getOrderCount());
					} else if(type == 2){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[1] = BigDecimal.ZERO;
						}

					} else if(type == 5){
						objects[1] = list.get(i).getProfit();
					} else if(type == 6){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = list.get(i).getProfit();
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = BigDecimal.ZERO;
						} else {
							objects[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						objects[2] = value1;//客单量
						objects[3] = value2;//毛利


					}
					returnList.add(objects);
				}
				return returnList;
			} else {        //dateTo大于等于当前日期减2天

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list = posOrderRemoteService.findDaySummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
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
				List<Object[]> localObjects = posOrderDao.findMoneyBizdaySummary(systemBookCode, branchNums, dpcLimitTime, dateTo, false);
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
				if(type > 0){
					Object[] object = null;
					for(int i = 0;i < returnList.size();i++){
						object = returnList.get(i);
						if(type == 1){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

							object[1] = value1;


						} else if(type == 2){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
							value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
							}
						} else if(type == 5){
							object[1] = object[3];
						} else if(type == 6){
							value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
							value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
							}
							object[2] = value1;
							object[3] = value2;
						}
					}
				}
				return returnList;
			}
		} else {
			//不去大中心查数据，都到本地查
			List<Object[]> objects = null;
			if(type == 3 || type == 4){
				objects = posOrderDao.findMoneyBizdaySummary(systemBookCode, branchNums,  dpcLimitTime, dateTo, true);

			} else {
				objects = posOrderDao.findMoneyBizdaySummary(systemBookCode, branchNums, dpcLimitTime, dateTo, false);

			}
			if(type > 0){
				Object[] object = null;
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					if(type == 1 || type == 3){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

						object[1] = value1;

					} else if(type == 2 || type == 4){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
						value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if(type == 5){
						object[1] = object[3];
					} else if(type == 6){
						value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
						value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						object[2] = value1;
						object[3] = value2;
					}
				}
			}

			return objects;
		}
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

		int type = 0;//暂时解决报错，等会去掉

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
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()) {

			if (dpcLimitTime.compareTo(dateTo) > 0) {           //dateTo小于当前日期减2天（3天前的数据）所有数据去大中心查
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findMonthSummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[4];
					objects[0] = list.get(i).getBizMonth();//营业月
					if(type == 0){
						objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());//营业额

					} else if(type == 1){
						objects[1] = BigDecimal.valueOf(list.get(i).getOrderCount());
					} else if(type == 2){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[1] = BigDecimal.ZERO;
						}

					} else if(type == 5){
						objects[1] = list.get(i).getProfit();
					} else if(type == 6){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = list.get(i).getProfit();
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							objects[1] = BigDecimal.ZERO;
						} else {
							objects[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						objects[2] = value1;//客单量
						objects[3] = value2;//毛利


					}
					returnList.add(objects);
				}
				return returnList;
			} else {        //dateTo大于等于当前日期减2天

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));  //(3天前的数据去大中心查）
				List<OrderReportDTO> list = posOrderRemoteService.findMonthSummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
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
				List<Object[]> localObjects = posOrderDao.findMoneyBizmonthSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, false);
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
				if(type > 0){
					Object[] object = null;
					for(int i = 0;i < returnList.size();i++){
						object = returnList.get(i);
						if(type == 1){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

							object[1] = value1;


						} else if(type == 2){
							value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
							value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
							}
						} else if(type == 5){
							object[1] = object[3];
						} else if(type == 6){
							value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
							value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
							}
							object[2] = value1;
							object[3] = value2;
						}
					}
				}
				return returnList;
			}
		} else {
			//不去大中心查数据，都到本地查
			List<Object[]> objects = null;
			if(type == 3 || type == 4){
				objects = posOrderDao.findMoneyBizmonthSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, true);
			} else {
				objects = posOrderDao.findMoneyBizmonthSummary(systemBookCode, branchNums,dpcLimitTime, dateTo, false);

			}
			if(type > 0){
				Object[] object = null;
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					if(type == 1 || type == 3){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);

						object[1] = value1;

					} else if(type == 2 || type == 4){
						value1 = object[2] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[2]);
						value2 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if(type == 5){
						object[1] = object[3];
					} else if(type == 6){
						value1 = object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1];
						value2 = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];

						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[1] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						object[2] = value1;
						object[3] = value2;
					}
				}
			}

			return objects;
		}
	}


}

