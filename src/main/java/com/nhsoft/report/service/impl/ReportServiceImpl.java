package com.nhsoft.report.service.impl;


import com.nhsoft.amazon.server.dto.OrderQueryDTO;
import com.nhsoft.amazon.server.dto.OrderDetailReportDTO;
import com.nhsoft.amazon.server.dto.OrderReportDTO;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.report.dao.*;
import com.nhsoft.report.dto.*;
import com.nhsoft.report.model.*;
import com.nhsoft.report.param.AdjustmentReason;
import com.nhsoft.report.param.CardUserType;
import com.nhsoft.report.param.ChainDeliveryParam;
import com.nhsoft.report.param.PosItemTypeParam;
import com.nhsoft.report.service.*;
import com.nhsoft.report.shared.queryBuilder.*;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	public ReportDao reportDao;
	@Autowired
	public BookResourceService bookResourceService;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	public PosItemService posItemService;
	@Autowired
	private PosOrderDao posOrderDao;
	@Autowired
	public TransferOutOrderDao transferOutOrderDao;
	@Autowired
	private WholesaleOrderDao wholesaleOrderDao;
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private BranchTransferGoalsDao branchTransferGoalsDao;
	@Autowired
	private ReturnOrderDao returnOrderDao;
	@Autowired
	public ReceiveOrderDao receiveOrderDao;
	@Autowired
	private PosItemLogDao posItemLogDao;
	@Autowired
	private PosClientService posClientService;
	@Autowired
	private WholesaleReturnDao wholesaleReturnDao;
	@Autowired
	private StoreItemSupplierDao storeItemSupplierDao;
	@Autowired
	private PosMachineDao posMachineDao;
	@Autowired
	public BranchService branchService;
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	@Autowired
	private RequestOrderDao requestOrderDao;
	@Autowired
	public TransferInOrderDao transferInOrderDao;
	@Autowired
	private SettlementDao settlementDao;
	@Autowired
	private PreSettlementDao preSettlementDao;
	@Autowired
	public InnerSettlementDao innerSettlementDao;
	@Autowired
	public InnerPreSettlementDao innerPreSettlementDao;
	@Autowired
	private ClientSettlementDao clientSettlementDao;
	@Autowired
	private ClientPreSettlementDao clientPreSettlementDao;
	@Autowired
	public CardUserDao cardUserDao;
	@Autowired
	private WholesaleBookDao wholesaleBookDao;
	@Autowired
	private PolicyPresentDao policyPresentDao;
	@Autowired
	private PolicyPromotionMoneyDao policyPromotionMoneyDao;
	@Autowired
	private PolicyPromotionQuantityDao policyPromotionQuantityDao;
	@Autowired
	private PolicyPromotionDao policyPromotionDao;
	@Autowired
	private MarketActionDao marketActionDao;
	@Autowired
	private WebLogDao webLogDao;
	@Autowired
	public CardSettlementDao cardSettlementDao;
	@Autowired
	private CheckOrderDao checkOrderDao;
	@Autowired
	private AssembleSplitDao assembleSplitDao;
	@Autowired
	private AllocationOrderDao allocationOrderDao;
	@Autowired
	private AdjustmentOrderDao adjustmentOrderDao;
	@Autowired
	private SmsSendDao smsSendDao;
	@Autowired
	private MessageBoardDao messageBoardDao;
	@Autowired
	public OnlineOrderDao onlineOrderDao;
	@Autowired
	public CardConsumeDao cardConsumeDao;
	@Autowired
	private StoreMatrixDao storeMatrixDao;
	@Autowired
	public CardDepositDao cardDepositDao;
	@Autowired
	private SystemBookDao systemBookDao;
	@Autowired
	private InventoryCollectDao inventoryCollectDao;
	@Autowired
	private AlipayLogDao alipayLogDao;
	@Autowired
	private PosItemGradeDao posItemGradeDao;
	@Autowired
	private PolicyDiscountDao policyDiscountDao;
	@Autowired
	private ShiftTableDao shiftTableDao;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private TransferLineDao transferLineDao;
	@Autowired
	private ShipOrderDao shipOrderDao;
	@Autowired
	public OtherInoutDao otherInoutDao;
	@Autowired
	private CardUserRegisterDao cardUserRegisterDao;
	@Autowired
	public SystemBookService systemBookService;
	@Autowired
	private BranchItemRecoredDao branchItemRecoredDao;
	@Autowired
	private ItemExtendAttributeDao itemExtendAttributeDao;
	@Autowired
	private CardUserLogDao cardUserLogDao;
	@Autowired
	private PosOrderRemoteService posOrderRemoteService;
	@Autowired
	private PosItemDao posItemDao;
	
	
	@Override
	public Object excuteSql(String systemBookCode, String sql) {
		return reportDao.excuteSql(systemBookCode, sql);
	}
	
	@Override
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {

		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();
		if (dateFrom != null) {
			dateFrom = DateUtil.getMinOfDate(dateFrom);
		} else {
			dateFrom = deleteDate;
		}
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		BigDecimal value1;
		BigDecimal value2;
		Date dpcLimitTime = DateUtil.addDay(now, -2);
		if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc() && (type != 3 && type != 4)) {

			if (dpcLimitTime.compareTo(dateTo) > 0) {
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findBranchDaySummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[5];
					objects[0] = list.get(i).getBranchNum();
					objects[1] = list.get(i).getBizday();
					if(type == 0){
						objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						
					} else if(type == 1){
						objects[2] = BigDecimal.valueOf(list.get(i).getOrderCount());
					} else if(type == 2){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[2] = BigDecimal.ZERO;
						}
							
					} else if(type == 5){
						objects[2] = list.get(i).getProfit();
					} else if(type == 6){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = list.get(i).getProfit();
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							objects[2] = BigDecimal.ZERO;
						} else {
							objects[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						objects[3] = value1;
						objects[4] = value2;
								
						
					}
					returnList.add(objects);
				}
				return returnList;
			} else {

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
				List<OrderReportDTO> list = posOrderRemoteService.findBranchDaySummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[5];
					objects[0] = list.get(i).getBranchNum();
					objects[1] = list.get(i).getBizday();
					objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					objects[3] = list.get(i).getOrderCount();
					objects[4] = list.get(i).getProfit();
					returnList.add(objects);
				}
				List<Object[]> localObjects = reportDao.findDayWholes(systemBookCode, branchNums, dpcLimitTime, dateTo, false);
				
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0]) && objects[1].equals(localObject[1])) {
							objects[2] = ((BigDecimal) objects[2]).add((BigDecimal) localObject[2]);
							objects[3] = (Integer) objects[3] + (Integer) localObject[3];
							objects[4] = ((BigDecimal) objects[4]).add((BigDecimal) localObject[4]);
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
							value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
							
							object[2] = value1;
							
							
						} else if(type == 2){
							value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
							value2 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
							
							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
							}
						} else if(type == 5){
							object[2] = object[4];
						} else if(type == 6){
							value1 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
							value2 = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
							
							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
							} 
							object[3] = value1;
							object[4] = value2;
						}
					}
				}
				return returnList;
			}
		} else {
			
			List<Object[]> objects = null;
			if(type == 3 || type == 4){
				objects = reportDao.findDayWholes(systemBookCode, branchNums, dateFrom, dateTo, true);
			} else {
				objects = reportDao.findDayWholes(systemBookCode, branchNums, dateFrom, dateTo, false);

			}
			if(type > 0){
				Object[] object = null;
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					if(type == 1 || type == 3){
						value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
												
						object[2] = value1;						
						
					} else if(type == 2 || type == 4){
						value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
						value2 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if(type == 5){
						object[2] = object[4];
					} else if(type == 6){
						value1 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						value2 = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						} 
						object[3] = value1;
						object[4] = value2;
					}
				}
			}
			
			return objects;
		}
	}

	@Override
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {

		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		BigDecimal value1;
		BigDecimal value2;
		Date dpcLimitTime = DateUtil.addDay(now, -2);
		if (dpcLimitTime.compareTo(dateFrom) > 0 
				&& systemBook.getBookReadDpc() != null 
				&& systemBook.getBookReadDpc() 
				&& (type != 3 && type != 4)) {
			if (dpcLimitTime.compareTo(dateTo) > 0) {
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = posOrderRemoteService.findBranchMonthSummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[3];
					objects[0] = list.get(i).getBranchNum();
					objects[1] = list.get(i).getBizMonth();
					if(type == 0){
						objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						
					} else if(type == 1){
						
						value1 = BigDecimal.valueOf(list.get(i).getOrderCount());
						value2 = BigDecimal.valueOf(list.get(i).getBizCount());
						
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[2] = value1;
						}
					} else if(type == 2){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							objects[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							objects[2] = BigDecimal.ZERO;
						}
							
					} else if(type == 5){
						objects[2] = list.get(i).getProfit();
					} else if(type == 6){
						value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
								.subtract(list.get(i).getMgrDiscount());
						value2 = list.get(i).getProfit();
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							objects[2] = BigDecimal.ZERO;
						} else {
							objects[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						}
						
					}
					returnList.add(objects);
				}
				return returnList;
			} else {
				Date lDpcLimitTime = DateUtil.addSecond(dpcLimitTime, -1);

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(lDpcLimitTime);
				List<OrderReportDTO> list = posOrderRemoteService.findBranchMonthSummary(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[6];
					objects[0] = list.get(i).getBranchNum();
					objects[1] = list.get(i).getBizMonth();
					objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					objects[3] = list.get(i).getOrderCount();
					objects[4] = list.get(i).getProfit();
					objects[5] =  list.get(i).getBizCount(); 
					returnList.add(objects);
				}

				List<Object[]> localObjects = reportDao.findMonthWholes(systemBookCode, branchNums, dpcLimitTime,
						dateTo, false);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0]) && objects[1].equals(localObject[1])) {
							objects[2] = ((BigDecimal) objects[2]).add((BigDecimal) localObject[2]);
							objects[3] = (Integer) objects[3] + (Integer) localObject[3];
							objects[4] = ((BigDecimal) objects[4]).add((BigDecimal) localObject[4]);
							objects[5] = (Integer) objects[5] + (Integer) localObject[5];
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
							value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
							value2 = object[5] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[5]);
							
							if(value2.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
							} else {
								object[2] = value1;
							}
							
						} else if(type == 2){
							value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
							value2 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
							
							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
							}
						} else if(type == 5){
							object[2] = object[4];
						} else if(type == 6){
							value1 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
							value2 = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
							
							if(value1.compareTo(BigDecimal.ZERO) > 0){
								object[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
							} 
						}
					}
				}
				return returnList;
			}
		} else {
			List<Object[]> objects = null;
			if(type == 3 || type == 4){
				objects = reportDao.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, true);
			} else {
				objects = reportDao.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, false);

			}
			if(type > 0){
				Object[] object = null;
				for(int i = 0;i < objects.size();i++){
					object = objects.get(i);
					if(type == 1 || type == 3){
						value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
						value2 = object[5] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[5]);
						
						if(value2.compareTo(BigDecimal.ZERO) > 0){
							object[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
						} else {
							object[2] = value1;
						}
						
					} else if(type == 2 || type == 4){
						value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
						value2 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if(type == 5){
						object[2] = object[4];
					} else if(type == 6){
						value1 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
						value2 = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];
						
						if(value1.compareTo(BigDecimal.ZERO) > 0){
							object[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
						} 
					}
				}
			}
			
			return objects;
		}
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByBranch(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByBranchDay(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByTerminal(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, String casher) {
		return reportDao.findBusinessCollectionByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
	}

	private OrderCompare readOrderCompareFromList(List<OrderCompare> orderCompareDatas, String itemTypeCode) {
		for (int i = 0; i < orderCompareDatas.size(); i++) {
			OrderCompare orderCompareData = orderCompareDatas.get(i);
			if (orderCompareData.getCategoryCode().equals(itemTypeCode)) {
				return orderCompareData;
			}
		}
		return null;
	}

	@Override
	public List<OrderCompare> findCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {

		Date lastFromDate = DateUtil.getFirstDayOfMonth(dateFrom);
		Date lastToDate = DateUtil.getLastDayOfMonth(dateFrom);

		Date nowFromDate = DateUtil.getFirstDayOfMonth(dateTo);
		Date nowToDate = DateUtil.getLastDayOfMonth(dateTo);

		// 销售量
		List<Object[]> thisObjects = reportDao.findCategoryMoney(systemBookCode, branchNums, nowFromDate, nowToDate);
		List<Object[]> lastObjects = reportDao.findCategoryMoney(systemBookCode, branchNums, lastFromDate, lastToDate);
		List<OrderCompare> list = new ArrayList<OrderCompare>();
		List<PosItemTypeParam> posItemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);
		for (int i = 0; i < thisObjects.size(); i++) {
			Object[] object = thisObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setThisMoney(money);
				list.add(data);
			} else {
				data.setThisMoney(data.getThisMoney().add(money));
			}
		}
		for (int i = 0; i < lastObjects.size(); i++) {
			Object[] object = lastObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setLastMoney(money);
				list.add(data);
			} else {
				data.setLastMoney(data.getLastMoney().add(money));
			}
		}

		// 调出量
		thisObjects = reportDao.findOutCategoryMoney(systemBookCode, branchNums, nowFromDate, nowToDate);
		lastObjects = reportDao.findOutCategoryMoney(systemBookCode, branchNums, lastFromDate, lastToDate);
		for (int i = 0; i < thisObjects.size(); i++) {
			Object[] object = thisObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setThisOutMoney(money);
				list.add(data);
			} else {
				data.setThisOutMoney(data.getThisOutMoney().add(money));
			}
		}
		for (int i = 0; i < lastObjects.size(); i++) {
			Object[] object = lastObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setLastOutMoney(money);
				list.add(data);
			} else {
				data.setLastOutMoney(data.getLastOutMoney().add(money));
			}
		}

		// 批发销售量
		thisObjects = reportDao.findWholesaleCategoryMoney(systemBookCode, branchNums, nowFromDate, nowToDate);
		lastObjects = reportDao.findWholesaleCategoryMoney(systemBookCode, branchNums, lastFromDate, lastToDate);
		for (int i = 0; i < thisObjects.size(); i++) {
			Object[] object = thisObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setThisWholeMoney(money);
				list.add(data);
			} else {
				data.setThisWholeMoney(data.getThisWholeMoney().add(money));
			}
		}
		for (int i = 0; i < lastObjects.size(); i++) {
			Object[] object = lastObjects.get(i);
			String categoryName = (String) object[0];
			String categoryCode = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			String topCategoryCode = AppUtil.getTopCategoryCode(posItemTypeParams, categoryCode);
			OrderCompare data = readOrderCompareFromList(list, topCategoryCode);
			if (data == null) {
				data = new OrderCompare();
				data.setCategoryCode(categoryCode);
				data.setCategoryName(categoryName);
				data.setLastWholeMoney(money);
				list.add(data);
			} else {
				data.setLastWholeMoney(data.getLastWholeMoney().add(money));
			}
		}
		for (int i = 0; i < list.size(); i++) {
			OrderCompare data = list.get(i);
			data.setLastSumMoney(data.getLastMoney().add(data.getLastOutMoney()).add(data.getLastWholeMoney()));
			data.setThisSumMoney(data.getThisMoney().add(data.getThisOutMoney()).add(data.getThisWholeMoney()));
		}
		return list;
	}

	@Override
	public List<OrderDetailCompare> findOrderDetailCompareDatas(String systemBookCode, List<Integer> branchNums,
			List<String> categoryCodes, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo,
			List<Integer> itemNums) {
		Map<String, OrderDetailCompare> map = new HashMap<String, OrderDetailCompare>();
		List<Object[]> thisObjects = reportDao.findOrderDetailCompareDatas(systemBookCode, branchNums, thisDateFrom,
				thisDateTo, itemNums);
		List<Object[]> lastObjects = reportDao.findOrderDetailCompareDatas(systemBookCode, branchNums, lastDateFrom,
				lastDateTo, itemNums);

		for (int i = 0; i < thisObjects.size(); i++) {
			Object[] object = thisObjects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setItemMatrixNum(itemMatrixNum);
				data.setItemNum(itemNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			data.setThisSellMoney(data.getThisSellMoney().add(money));
			data.setThisSellNum(data.getThisSellNum().add(amount));
			data.setThisSaleProfit(data.getThisSaleProfit().add(profit));
		}
		for (int i = 0; i < lastObjects.size(); i++) {
			Object[] object = lastObjects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setItemMatrixNum(itemMatrixNum);
				data.setItemNum(itemNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			data.setLastSellMoney(data.getLastSellMoney().add(money));
			data.setLastSellNum(data.getLastSellNum().add(amount));
			data.setLastSaleProfit(data.getLastSaleProfit().add(profit));

		}

		List<AdjustmentReason> adjustmentReasons = bookResourceService.findAdjustmentReasons(systemBookCode);
		List<String> reasons = new ArrayList<String>();
		for (int i = 0; i < adjustmentReasons.size(); i++) {
			AdjustmentReason adjustmentReason = adjustmentReasons.get(i);
			if (StringUtils.equals(adjustmentReason.getAdjustmentInoutCategory(),
					AppConstants.ADJUSTMENT_REASON_TYPE_LOSS)) {
				reasons.add(adjustmentReason.getAdjustmentReasonName());
			}
		}
		if (reasons.size() > 0) {
			List<Object[]> objects = adjustmentOrderDao.findItemSummary(systemBookCode, branchNums, thisDateFrom,
					thisDateTo, reasons, null, true, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new OrderDetailCompare();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				data.setThisAdjustMoney(money);
			}

			objects = adjustmentOrderDao.findItemSummary(systemBookCode, branchNums, lastDateFrom, lastDateTo, reasons,
					null, true, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new OrderDetailCompare();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				data.setLastAdjustMoney(money);
			}

		}

		List<Integer> centerBranchNums = new ArrayList<Integer>();
		centerBranchNums.add(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM);
		List<Object[]> outThisObjects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, centerBranchNums,
				branchNums, thisDateFrom, thisDateTo, null, itemNums);
		List<Object[]> outLastObjects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, centerBranchNums,
				branchNums, lastDateFrom, lastDateTo, null, itemNums);
		for (int i = 0; i < outThisObjects.size(); i++) {
			Object[] object = outThisObjects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setItemMatrixNum(itemMatrixNum);
				data.setItemNum(itemNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			data.setThisInMoney(money);
		}

		for (int i = 0; i < outLastObjects.size(); i++) {
			Object[] object = outLastObjects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			OrderDetailCompare data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setItemMatrixNum(itemMatrixNum);
				data.setItemNum(itemNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			data.setLastInMoney(money);
		}

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		List<OrderDetailCompare> list = new ArrayList<OrderDetailCompare>(map.values());
		BigDecimal hundred = BigDecimal.valueOf(100);
		for (int i = list.size() - 1; i >= 0; i--) {
			OrderDetailCompare orderDetailCompare = list.get(i);
			PosItem posItem = AppUtil.getPosItem(orderDetailCompare.getItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (orderDetailCompare.getThisSellNum().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setThisAvgPrice(orderDetailCompare.getThisSellMoney().divide(orderDetailCompare.getThisSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
			if (orderDetailCompare.getLastSellNum().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setLastAvgPrice(orderDetailCompare.getLastSellMoney().divide(orderDetailCompare.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
			
			orderDetailCompare.setCategoryCode(posItem.getItemCategoryCode());
			orderDetailCompare.setCategoryName(posItem.getItemCategory());
			orderDetailCompare.setOrderCode(posItem.getItemCode());
			orderDetailCompare.setOrderName(posItem.getItemName());
			orderDetailCompare.setSpec(posItem.getItemSpec());
			orderDetailCompare.setUnit(posItem.getItemUnit());
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(orderDetailCompare.getCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (orderDetailCompare.getItemMatrixNum() != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(),
						orderDetailCompare.getItemNum(), orderDetailCompare.getItemMatrixNum());
				if (itemMatrix != null) {
					orderDetailCompare.setOrderName(orderDetailCompare.getOrderName().concat(
							AppUtil.getMatrixName(itemMatrix)));
				}
			}
			BigDecimal price = orderDetailCompare.getThisAvgPrice().subtract(orderDetailCompare.getLastAvgPrice());
			orderDetailCompare.setPriceGrowthRate(hundred);
			if (orderDetailCompare.getLastAvgPrice().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setPriceGrowthRate(price
						.divide(orderDetailCompare.getLastAvgPrice(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
			}

			orderDetailCompare.setNumGrowthRate("100%");
			orderDetailCompare.setNumGrowthRateValue(hundred);
			BigDecimal numGrowth = orderDetailCompare.getThisSellNum().subtract(orderDetailCompare.getLastSellNum());
			if (orderDetailCompare.getLastSellNum().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setNumGrowthRateValue(numGrowth
						.divide(orderDetailCompare.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setNumGrowthRate(orderDetailCompare.getNumGrowthRateValue() + "%");

			}

			orderDetailCompare.setMoneyGrowthRate("100%");
			orderDetailCompare.setMoneyGrowthRateValue(hundred);
			BigDecimal moneyGrowth = orderDetailCompare.getThisSellMoney().subtract(
					orderDetailCompare.getLastSellMoney());
			if (orderDetailCompare.getLastSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setMoneyGrowthRateValue(moneyGrowth
						.divide(orderDetailCompare.getLastSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setMoneyGrowthRate(orderDetailCompare.getMoneyGrowthRateValue()
						+ "%");

			}

			orderDetailCompare.setAdjustRate("100%");
			orderDetailCompare.setAdjustRateValue(hundred);
			if (orderDetailCompare.getThisSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setAdjustRateValue(orderDetailCompare.getThisAdjustMoney()
						.divide(orderDetailCompare.getThisSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setAdjustRate(orderDetailCompare.getAdjustRateValue()
						+ "%");

			}

			BigDecimal subInMoney = orderDetailCompare.getThisInMoney().subtract(orderDetailCompare.getLastInMoney());
			if (orderDetailCompare.getLastInMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setInGrowthRateValue(subInMoney
						.divide(orderDetailCompare.getLastInMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setInGrowthRate(orderDetailCompare.getInGrowthRateValue() + "%");
			} else if (subInMoney.compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setInGrowthRate("100%");
				orderDetailCompare.setInGrowthRateValue(hundred);
			} else {
				orderDetailCompare.setInGrowthRate("0%");
				orderDetailCompare.setInGrowthRateValue(BigDecimal.ZERO);
				
			}

			BigDecimal thisProfit = orderDetailCompare.getThisSellMoney().subtract(orderDetailCompare.getThisInMoney());
			orderDetailCompare.setThisProfitMoney(thisProfit);
			if (orderDetailCompare.getThisSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setThisProfitRate(thisProfit
						.divide(orderDetailCompare.getThisSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));

			}
			BigDecimal lastProfit = orderDetailCompare.getLastSellMoney().subtract(orderDetailCompare.getLastInMoney());
			orderDetailCompare.setLastProfitMoney(lastProfit);
			if (orderDetailCompare.getLastSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setLastProfitRate(lastProfit
						.divide(orderDetailCompare.getLastSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));

			}
			BigDecimal subProfitMoney = orderDetailCompare.getThisProfitMoney().subtract(
					orderDetailCompare.getLastProfitMoney());
			if (orderDetailCompare.getLastProfitMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setProfitMoneyGrowthRateValue(subProfitMoney
						.divide(orderDetailCompare.getLastProfitMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setProfitMoneyGrowthRate(orderDetailCompare.getProfitMoneyGrowthRateValue() + "%");
			} else if (subProfitMoney.compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setProfitMoneyGrowthRate("100%");
				orderDetailCompare.setProfitMoneyGrowthRateValue(hundred);
			} else {
				orderDetailCompare.setProfitMoneyGrowthRate("0%");
				orderDetailCompare.setProfitMoneyGrowthRateValue(BigDecimal.ZERO);
				
			}
			BigDecimal subSaleProfit = orderDetailCompare.getThisSaleProfit().subtract(
					orderDetailCompare.getLastSaleProfit());
			if (orderDetailCompare.getLastSaleProfit().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setSaleProfitGrowthRateValue(subSaleProfit
						.divide(orderDetailCompare.getLastSaleProfit(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setSaleProfitGrowthRate(orderDetailCompare.getSaleProfitGrowthRateValue() + "%");
			} else if (subSaleProfit.compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setSaleProfitGrowthRate("100%");
				orderDetailCompare.setSaleProfitGrowthRateValue(hundred);
				
			} else {
				orderDetailCompare.setSaleProfitGrowthRate("0%");
				orderDetailCompare.setSaleProfitGrowthRateValue(BigDecimal.ZERO);
				
			}

			orderDetailCompare.setProfitGrowthRateValue(orderDetailCompare.getThisProfitRate()
					.subtract(orderDetailCompare.getLastProfitRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
			orderDetailCompare.setProfitGrowthRate(orderDetailCompare.getProfitGrowthRateValue() + "%");

		}
		return list;
	}

	private Supplier readSupplier(List<Supplier> suppliers, Integer supplierNum) {
		for (int i = 0; i < suppliers.size(); i++) {
			Supplier supplier = suppliers.get(i);
			if (supplier.getSupplierNum().equals(supplierNum)) {
				return supplier;
			}
		}
		return null;
	}

	@Override
	public List<SupplierSaleGroupByDate> findSupplierSaleGroupByDateDatas(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		Integer branchNum = supplierSaleQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(null, supplierSaleQuery.getItemNums(),
				supplierSaleQuery.getCategoryCodes(), branchNum, systemBookCode);
		Map<Integer, SupplierSaleGroupByDate> baseMap = new HashMap<Integer, SupplierSaleGroupByDate>();
		Map<String, SupplierSaleGroupByDate> map = new HashMap<String, SupplierSaleGroupByDate>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleGroupByDate data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleGroupByDate();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemName((String) object[2]);
				data.setItemCode((String) object[3]);
				data.setItemSpec((String) object[4]);
				data.setCategoryCode((String) object[5]);
				data.setCategoryName((String) object[6]);
				data.setItemInventoryRate((BigDecimal) object[7]);
				data.setItemTransferRate((BigDecimal) object[8]);
				data.setItemWholesaleRate((BigDecimal) object[9]);
				data.setItemPurchaseRate((BigDecimal) object[10]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					if (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0) {
						if (!supplierSaleQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}

		}
		List<String> types = supplierSaleQuery.getCheckList();
		if (types.contains(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findMoneyGroupByItemDate(systemBookCode, transferBranchNums, null,
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String saleDate = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				SupplierSaleGroupByDate baseData = baseMap.get(itemNum);
				if (baseData != null) {

					if (baseData.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleGroupByDate data = map.get(branchNum.toString() + itemNum + saleDate);
					if (data == null) {
						data = new SupplierSaleGroupByDate();
						BeanUtils.copyProperties(baseData, data);
						data.setBranchNum(branch);
						data.setSaleDate(saleDate);
						map.put(branchNum.toString() + itemNum + saleDate, data);

					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleCost(data.getSaleCost().add(costMoney));
					data.setSaleMaori(data.getSaleMaori().add(profit));

				}

			}

			objects = transferInOrderDao.findMoneyByItemDate(systemBookCode, transferBranchNums, null,
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String saleDate = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				SupplierSaleGroupByDate baseData = baseMap.get(itemNum);
				if (baseData != null) {

					if (baseData.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleGroupByDate data = map.get(branchNum.toString() + itemNum + saleDate);
					if (data == null) {
						data = new SupplierSaleGroupByDate();
						BeanUtils.copyProperties(baseData, data);
						data.setBranchNum(branch);
						data.setSaleDate(saleDate);
						map.put(branchNum.toString() + itemNum + saleDate, data);

					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleCost(data.getSaleCost().subtract(costMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(profit));

				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemDateSummary(systemBookCode, branchNum, supplierSaleQuery.getDateFrom(),
					supplierSaleQuery.getDateTo(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String saleDate = (String) object[1];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				SupplierSaleGroupByDate baseData = baseMap.get(itemNum);
				if (baseData != null) {

					if (baseData.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleGroupByDate data = map.get(branchNum.toString() + itemNum + saleDate);
					if (data == null) {
						data = new SupplierSaleGroupByDate();
						BeanUtils.copyProperties(baseData, data);
						data.setBranchNum(branch);
						data.setSaleDate(saleDate);
						map.put(branchNum.toString() + itemNum + saleDate, data);

					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleCost(data.getSaleCost().add(costMoney));
					data.setSaleMaori(data.getSaleMaori().add(profit));

				}
			}

			objects = wholesaleReturnDao.findItemDateSummary(systemBookCode, branchNum,
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String saleDate = (String) object[1];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				SupplierSaleGroupByDate baseData = baseMap.get(itemNum);
				if (baseData != null) {

					if (baseData.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleGroupByDate data = map.get(branchNum.toString() + itemNum + saleDate);
					if (data == null) {
						data = new SupplierSaleGroupByDate();
						BeanUtils.copyProperties(baseData, data);
						data.setBranchNum(branch);
						data.setSaleDate(saleDate);
						map.put(branchNum.toString() + itemNum + saleDate, data);

					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleCost(data.getSaleCost().subtract(costMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(profit));

				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findMoneyGroupByBranchAndItemAndBizday(systemBookCode,
					supplierSaleQuery.getBranchNums(), supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), true);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				String bizday = (String) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal amount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				SupplierSaleGroupByDate baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleQty(amount);
					baseData.setSaleMoney(saleMoney);
					baseData.setSaleCost(saleMoney.subtract(profit));
					baseData.setSaleMaori(profit);
					baseData.setBranchNum(branch);
					baseData.setSaleDate(DateUtil.getDateType(bizday));
					SupplierSaleGroupByDate data = map.get(branchNum.toString() + itemNum + baseData.getSaleDate());
					if (data == null) {
						data = new SupplierSaleGroupByDate();
						BeanUtils.copyProperties(baseData, data);
						map.put(branchNum.toString() + itemNum + baseData.getSaleDate(), data);

					} else {
						data.setSaleQty(data.getSaleQty().add(baseData.getSaleQty()));
						data.setSaleMoney(data.getSaleMoney().add(baseData.getSaleMoney()));
						data.setSaleCost(data.getSaleCost().add(baseData.getSaleCost()));
						data.setSaleMaori(data.getSaleMaori().add(baseData.getSaleMaori()));
					}
				}
			}
		}

		List<SupplierSaleGroupByDate> list = new ArrayList<SupplierSaleGroupByDate>(map.values());
		// 算毛利率
		for (int i = 0; i < list.size(); i++) {
			SupplierSaleGroupByDate data = list.get(i);
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleMaoriRate(BigDecimal.ZERO);
			} else {
				data.setSaleMaoriRate(data.getSaleMaori().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)).setScale(2));

			}
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findABCDatasBySale' + #p0.getKey()")
	public List<ABCAnalysis> findABCDatasBySale(ABCListQuery abcListQuery) {

		String systemBookCode = abcListQuery.getSystemBookCode();
		Integer branchNum = abcListQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		Date dateFrom = abcListQuery.getDateFrom();
		Date dateTo = abcListQuery.getDateTo();
		List<Integer> branchNums = abcListQuery.getBranchNums();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<String> typeList = abcListQuery.getTypes();

		Map<String, ABCAnalysis> map = new HashMap<String, ABCAnalysis>();
		BigDecimal totalMoney = BigDecimal.ZERO;
		if (typeList.contains(AppConstants.CHECKBOX_SALE)) {
			List<Object[]> objects = posOrderDao.findAbcItemSum(systemBookCode, branchNums, dateFrom, dateTo,
					abcListQuery.getCategoryCodeList());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = (Integer) object[1];
				if (itemMatrixNum == null) {
					itemMatrixNum = 0;
				}
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				data.setAnalysisContent(data.getAnalysisContent().add(saleMoney));
				totalMoney = totalMoney.add(saleMoney);
			}
		}

		if (typeList.contains(AppConstants.CHECKBOX_OUT)) {
			List<Object[]> objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums,
					branchNums, dateFrom, dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.add(saleMoney);
				data.setAnalysisContent(data.getAnalysisContent().add(saleMoney));

			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, branchNums,
					dateFrom, dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.subtract(saleMoney);
				data.setAnalysisContent(data.getAnalysisContent().subtract(saleMoney));

			}

		}
		if (typeList.contains(AppConstants.CHECKBOX_WHO)) {
			List<Object[]> objects = wholesaleOrderDao.findItemSumByCategory(systemBookCode, branchNum, dateFrom,
					dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.add(saleMoney);
				data.setAnalysisContent(data.getAnalysisContent().add(saleMoney));

			}
			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null, dateFrom, dateTo, null,
					abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.subtract(saleMoney);
				data.setAnalysisContent(data.getAnalysisContent().subtract(saleMoney));

			}
		}
		List<ABCAnalysis> list = new ArrayList<ABCAnalysis>(map.values());
		BigDecimal totalRate = BigDecimal.ZERO;
		BigDecimal aCount = BigDecimal.ZERO;
		BigDecimal bCount = BigDecimal.ZERO;
		BigDecimal cCount = BigDecimal.ZERO;

		Comparator<ABCAnalysis> comparator = new Comparator<ABCAnalysis>() {

			@Override
			public int compare(ABCAnalysis o1, ABCAnalysis o2) {
				return -o1.getAnalysisContent().compareTo(o2.getAnalysisContent());
			}

		};
		Collections.sort(list, comparator);
		BigDecimal aRate = null;
		List<Integer> itemNums = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			ABCAnalysis data = list.get(i);
			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				i = i - 1;
				continue;
			}
			data.setPosItem(posItem);
			String state = "";
			if (posItem.getItemStockCeaseFlag() != null && posItem.getItemStockCeaseFlag()) {
				if (state.isEmpty()) {
					state = state.concat("停购");

				} else {
					state = state.concat(",停购");
				}
			}
			if (posItem.getItemSaleCeaseFlag() != null && posItem.getItemSaleCeaseFlag()) {
				if (state.isEmpty()) {
					state = state.concat("停售");

				} else {
					state = state.concat(",停售");
				}
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				if (state.isEmpty()) {
					state = state.concat("淘汰");

				} else {
					state = state.concat(",淘汰");
				}
			}/////
			if (posItem.getItemStatus() != null && posItem.getItemStatus().equals(AppConstants.ITEM_STATUS_SLEEP)) {
				if (state.isEmpty()) {
					state = state.concat("休眠");

				} else {
					state = state.concat(",休眠");
				}
			}
			data.setState(state);
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemType(posItem.getItemCategory());
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setUnit(posItem.getItemUnit());
			data.setSpec(posItem.getItemSpec());

			if (data.getItemMatrixNum() != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(),
						data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			data.setTotalMoney(totalMoney);
			if (totalMoney.compareTo(BigDecimal.ZERO) == 0) {
				data.setRate(BigDecimal.ZERO);
			} else {
				BigDecimal rate = data.getAnalysisContent().divide(totalMoney, 8, BigDecimal.ROUND_HALF_UP);
				data.setRate(rate.multiply(BigDecimal.valueOf(100).setScale(6)));
			}
			data.setTotalRate(totalRate.add(data.getRate()));
			totalRate = data.getTotalRate();

			if (!itemNums.contains(data.getItemNum())) {
				itemNums.add(data.getItemNum());

			}

			if (i == 0 && data.getTotalRate().compareTo(BigDecimal.valueOf(70.0000)) >= 0) {
				data.setABC("A");
				aCount = aCount.add(data.getAnalysisContent());
				continue;
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) > 0 && aRate == null) {
				aRate = totalRate.subtract(data.getRate());
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) <= 0) {
				data.setABC("A");

				aCount = aCount.add(data.getAnalysisContent());
			} else if (totalRate.compareTo(BigDecimal.valueOf(90.0000)) <= 0
					
					//AMA-12105
					//&& totalRate.compareTo((aRate.add(BigDecimal.valueOf(20.0000)))) <= 0
					) {
				data.setABC("B");
				bCount = bCount.add(data.getAnalysisContent());
			} else {
				data.setABC("C");
				cCount = cCount.add(data.getAnalysisContent());
			}

		}
		if (itemNums.size() > 0) {
			List<Inventory> inventories = inventoryDao.findByItemAndBranch(systemBookCode, branchNum, itemNums, false);
			Branch branch = branchService.readInCache(systemBookCode, branchNum);
			for (int i = 0; i < list.size(); i++) {
				ABCAnalysis data = list.get(i);
				Object[] objects = AppUtil.getInventoryAmount(inventories, data.getPosItem(), data.getItemMatrixNum(),
						null, branch);
				data.setInventoryQty((BigDecimal) objects[0]);
			}
		}

		if (list.size() > 0) {
			list.get(0).setaLevelCount(aCount);
			list.get(0).setbLevelCount(bCount);
			list.get(0).setcLevelCount(cCount);
		}

		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findABCDatasByProfit' + #p0.getKey()")
	public List<ABCAnalysis> findABCDatasByProfit(ABCListQuery abcListQuery) {
		String systemBookCode = abcListQuery.getSystemBookCode();
		Integer branchNum = abcListQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		Date dateFrom = abcListQuery.getDateFrom();
		Date dateTo = abcListQuery.getDateTo();
		List<Integer> branchNums = abcListQuery.getBranchNums();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<String> typeList = abcListQuery.getTypes();

		Map<String, ABCAnalysis> map = new HashMap<String, ABCAnalysis>();
		BigDecimal totalMoney = BigDecimal.ZERO;
		if (typeList.contains(AppConstants.CHECKBOX_SALE)) {
			List<Object[]> objects = posOrderDao.findAbcItemSum(systemBookCode, branchNums, dateFrom, dateTo,
					abcListQuery.getCategoryCodeList());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemMatrixNum(itemMatrixNum);
					data.setItemNum(itemNum);
					map.put(itemNum + "|" + itemMatrixNum, data);

				}
				data.setAnalysisContent(data.getAnalysisContent().add(money));
				totalMoney = totalMoney.add(money);
			}
		}

		if (typeList.contains(AppConstants.CHECKBOX_OUT)) {
			List<Object[]> objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums,
					branchNums, dateFrom, dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemNum(itemNum);
					data.setItemMatrixNum(itemMatrixNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.add(profit);
				data.setAnalysisContent(data.getAnalysisContent().add(profit));

			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, branchNums,
					dateFrom, dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemNum(itemNum);
					data.setItemMatrixNum(itemMatrixNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.subtract(profit);
				data.setAnalysisContent(data.getAnalysisContent().subtract(profit));

			}
		}
		if (typeList.contains(AppConstants.CHECKBOX_WHO)) {
			List<Object[]> objects = wholesaleOrderDao.findItemSumByCategory(systemBookCode, branchNum, dateFrom,
					dateTo, abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal profit = saleMoney.subtract(costMoney);
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemNum(itemNum);
					data.setItemMatrixNum(itemMatrixNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.add(profit);
				data.setAnalysisContent(data.getAnalysisContent().add(profit));

			}

			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null, dateFrom, dateTo, null,
					abcListQuery.getCategoryCodeList(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				BigDecimal profit = saleMoney.subtract(costMoney);
				ABCAnalysis data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new ABCAnalysis();
					data.setItemNum(itemNum);
					data.setItemMatrixNum(itemMatrixNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				totalMoney = totalMoney.subtract(profit);
				data.setAnalysisContent(data.getAnalysisContent().subtract(profit));

			}
		}

		List<ABCAnalysis> list = new ArrayList<ABCAnalysis>(map.values());
		BigDecimal totalRate = BigDecimal.ZERO;
		BigDecimal aCount = BigDecimal.ZERO;
		BigDecimal bCount = BigDecimal.ZERO;
		BigDecimal cCount = BigDecimal.ZERO;
		Comparator<ABCAnalysis> comparator = new Comparator<ABCAnalysis>() {

			@Override
			public int compare(ABCAnalysis o1, ABCAnalysis o2) {
				return -o1.getAnalysisContent().compareTo(o2.getAnalysisContent());
			}

		};
		Collections.sort(list, comparator);
		BigDecimal aRate = null;
		for (int i = 0; i < list.size(); i++) {
			ABCAnalysis data = list.get(i);
			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				i = i - 1;
				continue;
			}
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemType(posItem.getItemCategory());
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setUnit(posItem.getItemUnit());
			data.setSpec(posItem.getItemSpec());

			if (data.getItemMatrixNum() != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(),
						data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			data.setTotalMoney(totalMoney);
			if (totalMoney.compareTo(BigDecimal.ZERO) == 0) {
				data.setRate(BigDecimal.ZERO);
			} else {
				BigDecimal rate = data.getAnalysisContent().divide(totalMoney, 8, BigDecimal.ROUND_HALF_UP);
				data.setRate(rate.multiply(BigDecimal.valueOf(100)).setScale(6));
			}
			data.setTotalRate(totalRate.add(data.getRate()));
			totalRate = data.getTotalRate();

			if (i == 0 && data.getTotalRate().compareTo(BigDecimal.valueOf(70.0000)) >= 0) {
				data.setABC("A");
				aCount = aCount.add(data.getAnalysisContent());
				continue;
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) > 0 && aRate == null) {
				aRate = totalRate.subtract(data.getRate());
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) <= 0) {
				data.setABC("A");
				aCount = aCount.add(data.getAnalysisContent());
			} else if (totalRate.compareTo(BigDecimal.valueOf(90.0000)) <= 0
					
					//AMA-12105
					//&& totalRate.compareTo((aRate.add(BigDecimal.valueOf(20.0000)))) <= 0
					) {
				data.setABC("B");
				bCount = bCount.add(data.getAnalysisContent());
			} else {
				data.setABC("C");
				cCount = cCount.add(data.getAnalysisContent());
			}
		}
		if (list.size() > 0) {
			list.get(0).setaLevelCount(aCount);
			list.get(0).setbLevelCount(bCount);
			list.get(0).setcLevelCount(cCount);
		}
		return list;
	}

	public List<BranchTransferGoals> findBranchGoals(List<BranchTransferGoals> branchTransferGoals, Integer branchNum) {
		List<BranchTransferGoals> list = new ArrayList<BranchTransferGoals>();
		for (int i = 0; i < branchTransferGoals.size(); i++) {
			BranchTransferGoals branchTransferGoal = branchTransferGoals.get(i);
			if (branchTransferGoal.getId().getBranchNum().equals(branchNum)) {
				list.add(branchTransferGoal);
			}
		}
		return list;
	}

	@Override
	public List<ABCAnalysis> findABCDatasBySaleV2(ABCListQuery abcListQuery) {
		String systemBookCode = abcListQuery.getSystemBookCode();
		Date dateFrom = abcListQuery.getDateFrom();
		Date dateTo = abcListQuery.getDateTo();
		List<Integer> branchNums = abcListQuery.getBranchNums();
		List<ABCAnalysis> list = new ArrayList<ABCAnalysis>();
		BigDecimal totalMoney = BigDecimal.ZERO;
		
		List<Object[]> objects = posOrderDao.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, null, true);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = AppUtil.getValue(object[1], BigDecimal.class);
			BigDecimal money = AppUtil.getValue(object[2], BigDecimal.class);
			ABCAnalysis data = new ABCAnalysis();
			data.setItemNum(itemNum);
			data.setSaleQty(amount);
			data.setAnalysisContent(money);
			list.add(data);
			totalMoney = totalMoney.add(money);
		}
		BigDecimal totalRate = BigDecimal.ZERO;
		BigDecimal aCount = BigDecimal.ZERO;
		BigDecimal bCount = BigDecimal.ZERO;
		BigDecimal cCount = BigDecimal.ZERO;
		Comparator<ABCAnalysis> comparator = new Comparator<ABCAnalysis>() {

			@Override
			public int compare(ABCAnalysis o1, ABCAnalysis o2) {
				return -o1.getAnalysisContent().compareTo(o2.getAnalysisContent());
			}

		};
		Collections.sort(list, comparator);
		BigDecimal aRate = null;
		for (int i = 0; i < list.size(); i++) {
			ABCAnalysis data = list.get(i);

			data.setTotalMoney(totalMoney);
			if (totalMoney.compareTo(BigDecimal.ZERO) == 0) {
				data.setRate(BigDecimal.ZERO);
			} else {
				BigDecimal rate = data.getAnalysisContent().divide(totalMoney, 8, BigDecimal.ROUND_HALF_UP);
				data.setRate(rate.multiply(BigDecimal.valueOf(100)).setScale(6));
			}
			data.setTotalRate(totalRate.add(data.getRate()));
			totalRate = data.getTotalRate();

			if (i == 0 && data.getTotalRate().compareTo(BigDecimal.valueOf(70.0000)) >= 0) {
				data.setABC("A");
				aCount = aCount.add(data.getAnalysisContent());
				continue;
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) > 0 && aRate == null) {
				aRate = totalRate.subtract(data.getRate());
			}
			if (totalRate.compareTo(BigDecimal.valueOf(70.0000)) <= 0) {
				data.setABC("A");
				aCount = aCount.add(data.getAnalysisContent());
			} else if (totalRate.compareTo(BigDecimal.valueOf(90.0000)) <= 0) {
				data.setABC("B");
				bCount = bCount.add(data.getAnalysisContent());
			} else {
				data.setABC("C");
				cCount = cCount.add(data.getAnalysisContent());
			}
		}
		return list;
	}

	@Override
	public List<TransferGoal> findTransferGoalByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {
		List<Object[]> objects = transferOutOrderDao.findOutMoneyGroupByBranch(systemBookCode, branchNums, dateFrom,
				dateTo, dateType);
		List<BranchTransferGoals> branchTransferGoals = branchTransferGoalsDao.findByDate(systemBookCode, branchNums,
				dateFrom, dateTo, dateType);
		Map<String, TransferGoal> map = new HashMap<String, TransferGoal>();
		for (int i = 0; i < branchTransferGoals.size(); i++) {
			BranchTransferGoals branchTransferGoal = branchTransferGoals.get(i);
			TransferGoal data = new TransferGoal();
			data.setBranchNum(branchTransferGoal.getId().getBranchNum());
			data.setTransferAmount(BigDecimal.ZERO);
			data.setTransferGoal(branchTransferGoal.getBranchTransferValue() == null ? BigDecimal.ZERO
					: branchTransferGoal.getBranchTransferValue());
			data.setYearMonth(branchTransferGoal.getId().getBranchTransferInterval());
			map.put(data.getBranchNum() + "|" + data.getYearMonth(), data);
		}

		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String date = (String) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if (dateType.equals(AppConstants.BUSINESS_DATE_SOME_MONTH)) {
				date = date.replaceAll("-", "");
			}
			TransferGoal data = map.get(branchNum + "|" + date);
			if (data == null) {
				data = new TransferGoal();
				data.setTransferGoal(BigDecimal.ZERO);
				data.setTransferAmount(amount);
				data.setBranchNum(branchNum);
				data.setYearMonth(date);
				map.put(branchNum + "|" + date, data);
			} else {
				data.setTransferAmount(amount);
			}
			if (data.getTransferGoal().compareTo(BigDecimal.ZERO) == 0) {
				data.setTransferRate(BigDecimal.ZERO);
			} else {
				data.setTransferRate(data.getTransferAmount()
						.divide(data.getTransferGoal(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
		}
		return new ArrayList<TransferGoal>(map.values());
	}

	@Override
	public List<SupplierBranchSum> findSupplierBranchSum(SupplierBranchQuery supplierBranchQuery) {
		String systemBookCode = supplierBranchQuery.getSystemBookCode();
		Integer branchNum = supplierBranchQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<Supplier> suppliers = supplierService.findInCache(systemBookCode);

		List<Object[]> objects = posItemService.findBySuppliers(null, null, null, branchNum, systemBookCode);
		Map<Integer, SupplierSaleGroupByBranch> baseMap = new HashMap<Integer, SupplierSaleGroupByBranch>();
		Map<Integer, SupplierBranchSum> map = new HashMap<Integer, SupplierBranchSum>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleGroupByBranch data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleGroupByBranch();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					if (supplierBranchQuery.getSupplierNums() != null
							&& supplierBranchQuery.getSupplierNums().size() > 0) {
						if (!supplierBranchQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}
		}
		String type = supplierBranchQuery.getCheckString();
		if (type.equals(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchSum data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierBranchSum();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(data.getSupplierNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					SupplierBranchSum data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierBranchSum();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(data.getSupplierNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, supplierBranchQuery.getDateFrom(),
					supplierBranchQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchSum data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierBranchSum();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(data.getSupplierNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchSum data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierBranchSum();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(data.getSupplierNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findItemSumByCategory(systemBookCode, supplierBranchQuery.getBranchNums(),
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, true, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchSum data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierBranchSum();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branch);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(data.getSupplierNum(), data);
					} else {
						BranchValue branchValueData = readBranchValueData(data.getBranchValues(), branch);
						if (branchValueData != null) {
							branchValueData.setValue(branchValueData.getValue().add(saleMoney));

						} else {
							branchValueData = new BranchValue();
							branchValueData.setBranchNum(branch);
							branchValueData.setValue(saleMoney);
							data.getBranchValues().add(branchValueData);
						}
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		}

		List<SupplierBranchSum> list = new ArrayList<SupplierBranchSum>(map.values());
		return list;
	}

	private BranchValue readBranchValueData(List<BranchValue> branchValueDatas, Integer branchNum) {
		for (int i = 0; i < branchValueDatas.size(); i++) {
			BranchValue branchValueData = branchValueDatas.get(i);
			if (branchValueData.getBranchNum().equals(branchNum)) {
				return branchValueData;
			}
		}
		return null;
	}

	@Override
	public List<SupplierBranchGroupByItem> findSupplierBranchGroupByItem(SupplierBranchQuery supplierBranchQuery) {
		String systemBookCode = supplierBranchQuery.getSystemBookCode();
		Integer branchNum = supplierBranchQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		List<Integer> itemNums = supplierBranchQuery.getItemNums();
		
		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(null, null, null, branchNum, systemBookCode);
		Map<Integer, SupplierSaleGroupByBranch> baseMap = new HashMap<Integer, SupplierSaleGroupByBranch>();
		Map<String, SupplierBranchGroupByItem> map = new HashMap<String, SupplierBranchGroupByItem>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleGroupByBranch data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleGroupByBranch();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemName((String) object[2]);
				data.setItemCode((String) object[3]);
				data.setItemSpec((String) object[4]);
				data.setCategoryCode((String) object[5]);
				data.setCategoryName((String) object[6]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					if (supplierBranchQuery.getSupplierNums() != null
							&& supplierBranchQuery.getSupplierNums().size() > 0) {
						if (!supplierBranchQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}

		}
		String type = supplierBranchQuery.getCheckString();
		if (type.equals(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByItem data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum());
					if (data == null) {
						data = new SupplierBranchGroupByItem();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByItem data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum());
					if (data == null) {
						data = new SupplierBranchGroupByItem();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, supplierBranchQuery.getDateFrom(),
					supplierBranchQuery.getDateTo(), itemNums, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByItem data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum());
					if (data == null) {
						data = new SupplierBranchGroupByItem();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), itemNums, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByItem data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum());
					if (data == null) {
						data = new SupplierBranchGroupByItem();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum(), data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findItemSumByCategory(systemBookCode, supplierBranchQuery.getBranchNums(),
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null, true, itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByItem data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum());
					if (data == null) {
						data = new SupplierBranchGroupByItem();
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branch);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum(), data);
					} else {
						BranchValue branchValueData = readBranchValueData(data.getBranchValues(), branch);
						if (branchValueData != null) {
							branchValueData.setValue(branchValueData.getValue().add(saleMoney));

						} else {
							branchValueData = new BranchValue();
							branchValueData.setBranchNum(branch);
							branchValueData.setValue(saleMoney);
							data.getBranchValues().add(branchValueData);
						}
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		}

		List<SupplierBranchGroupByItem> list = new ArrayList<SupplierBranchGroupByItem>(map.values());
		return list;
	}

	@Override
	public List<SupplierBranchGroupByDate> findSupplierBranchGroupByDate(SupplierBranchQuery supplierBranchQuery) {
		String systemBookCode = supplierBranchQuery.getSystemBookCode();
		Integer branchNum = supplierBranchQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(null, null, null, branchNum, systemBookCode);
		Map<Integer, SupplierSaleGroupByBranch> baseMap = new HashMap<Integer, SupplierSaleGroupByBranch>();
		Map<String, SupplierBranchGroupByDate> map = new HashMap<String, SupplierBranchGroupByDate>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleGroupByBranch data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleGroupByBranch();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemName((String) object[2]);
				data.setItemCode((String) object[3]);
				data.setItemSpec((String) object[4]);
				data.setCategoryCode((String) object[5]);
				data.setCategoryName((String) object[6]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					if (supplierBranchQuery.getSupplierNums() != null
							&& supplierBranchQuery.getSupplierNums().size() > 0) {
						if (!supplierBranchQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}

		}
		String type = supplierBranchQuery.getCheckString();
		if (type.equals(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findMoneyGroupByItemDate(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String date = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByDate data = map.get(baseData.getSupplierNum() + ":" + date);
					if (data == null) {
						data = new SupplierBranchGroupByDate();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = transferInOrderDao.findMoneyByItemDate(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String date = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByDate data = map.get(baseData.getSupplierNum() + ":" + date);
					if (data == null) {
						data = new SupplierBranchGroupByDate();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemDateSummary(systemBookCode, branchNum,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String date = (String) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByDate data = map.get(baseData.getSupplierNum() + ":" + date);
					if (data == null) {
						data = new SupplierBranchGroupByDate();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = wholesaleReturnDao.findItemDateSummary(systemBookCode, branchNum,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String date = (String) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByDate data = map.get(baseData.getSupplierNum() + ":" + date);
					if (data == null) {
						data = new SupplierBranchGroupByDate();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findMoneyGroupByBranchAndItemAndBizday(systemBookCode,
					supplierBranchQuery.getBranchNums(), supplierBranchQuery.getDateFrom(),
					supplierBranchQuery.getDateTo(), true);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				String date = (String) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				date = DateUtil.getDateType(date);
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchGroupByDate data = map.get(baseData.getSupplierNum() + ":" + date);
					if (data == null) {
						data = new SupplierBranchGroupByDate();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branch);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + date, data);
					} else {
						BranchValue branchValueData = readBranchValueData(data.getBranchValues(), branch);
						if (branchValueData != null) {
							branchValueData.setValue(branchValueData.getValue().add(saleMoney));

						} else {
							branchValueData = new BranchValue();
							branchValueData.setBranchNum(branch);
							branchValueData.setValue(saleMoney);
							data.getBranchValues().add(branchValueData);
						}
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		}

		List<SupplierBranchGroupByDate> list = new ArrayList<SupplierBranchGroupByDate>(map.values());
		return list;
	}

	@Override
	public List<SupplierSaleRank> findSupplierSaleRank(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		Integer branchNum = supplierSaleQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(null, supplierSaleQuery.getItemNums(),
				supplierSaleQuery.getCategoryCodes(), branchNum, systemBookCode);
		Map<Integer, SupplierSaleSum> baseMap = new HashMap<Integer, SupplierSaleSum>();
		Map<Integer, SupplierSaleRank> map = new HashMap<Integer, SupplierSaleRank>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleSum data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleSum();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemPurcharseRate((BigDecimal) object[10]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {

					if (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0) {
						if (!supplierSaleQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}
		}
		List<String> types = supplierSaleQuery.getCheckList();
		if (types.contains(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleRank data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierSaleRank();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleCost(data.getSaleCost().add(costMoney));
					data.setSaleMaori(data.getSaleMaori().add(saleMoney.subtract(costMoney)));

				}
			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleRank data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierSaleRank();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleCost(data.getSaleCost().subtract(costMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(saleMoney.subtract(costMoney)));

				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, supplierSaleQuery.getDateFrom(),
					supplierSaleQuery.getDateTo(), null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleRank data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierSaleRank();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleCost(data.getSaleCost().add(costMoney));
					data.setSaleMaori(data.getSaleMaori().add(saleMoney.subtract(costMoney)));

				}
			}

			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null, supplierSaleQuery.getDateFrom(),
					supplierSaleQuery.getDateTo(), null, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierSaleRank data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierSaleRank();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleCost(data.getSaleCost().subtract(costMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(saleMoney.subtract(costMoney)));

				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findItemMatrixSum(systemBookCode, supplierSaleQuery.getBranchNums(),
					supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), true);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					SupplierSaleRank data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierSaleRank();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleCost(data.getSaleCost().add(saleMoney.subtract(profit)));
					data.setSaleMaori(data.getSaleMaori().add(profit));

				}

			}
		}
		List<SupplierSaleRank> list = new ArrayList<SupplierSaleRank>(map.values());
		return list;
	}

	private ABCCharXy readABCCharXyData(List<ABCCharXy> abcCharXyDatas, String month) {
		for (int i = 0; i < abcCharXyDatas.size(); i++) {
			ABCCharXy abcCharXyData = abcCharXyDatas.get(i);
			if (abcCharXyData.getMonth().equals(month)) {
				return abcCharXyData;
			}
		}
		return null;
	}

	@Override
	public List<ABCChart> findABCChartByType(ABCChartQuery query, int type) {
		String systemBookCode = query.getSystemBookCode();
		Integer branchNum = query.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<Integer> branchNums = query.getBranchNums();
		Date dateFrom = query.getDateFrom();
		Date dateTo = query.getDateTo();
		List<Integer> itemNums = query.getPosItemNums();

		Map<Integer, ABCChart> map = new LinkedHashMap<Integer, ABCChart>();
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		for (int i = 0; i < posItems.size(); i++) {
			ABCChart data = new ABCChart();
			PosItem posItem = posItems.get(i);
			Integer itemNum = posItem.getItemNum();
			data.setItemNum(itemNum);
			data.setItemName(posItem.getItemName());
			// monthFrom = 201301
			Integer monthFrom = Integer.parseInt(DateUtil.getDateShortStr(dateFrom).substring(0, 6));
			Integer monthTo = Integer.parseInt(DateUtil.getDateShortStr(dateTo).substring(0, 6));
			while (monthFrom <= monthTo) {
				// month = 2013-01
				String month = DateUtil.getDateMonthType(monthFrom.toString());
				ABCCharXy xyData = new ABCCharXy();
				xyData.setContent(BigDecimal.ZERO);
				xyData.setMonth(month);
				data.getAbcchartXYs().add(xyData);
				monthFrom++;
			}
			map.put(itemNum, data);
		}

		if (query.getTypes().contains(AppConstants.CHECKBOX_SALE)) {
			List<Object[]> objects = posOrderDao.findMoneyByItemAndMonth(systemBookCode, branchNums, dateFrom, dateTo,
					itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				// 201301 to 2013-01
				String date = DateUtil.getDateMonthType(((String) object[1]));
				BigDecimal money = BigDecimal.ZERO;
				// 销售金额
				if (type == 0) {
					BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
					BigDecimal cancel = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
					money = saleMoney.subtract(cancel);
				} else {
					// 毛利
					BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
					BigDecimal cancelProfit = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
					money = profit.subtract(cancelProfit);
				}
				ABCChart data = map.get(itemNum);
				List<ABCCharXy> xyDatas = data.getAbcchartXYs();
				ABCCharXy xyData = readABCCharXyData(xyDatas, date);
				{
					xyData.setContent(xyData.getContent().add(money));
				}
			}
		}
		if (query.getTypes().contains(AppConstants.CHECKBOX_OUT)) {
			List<Object[]> objects = transferOutOrderDao.findMoneyByItemAndMonth(systemBookCode, transferBranchNums,
					dateFrom, dateTo, itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				// 201301 to 2013-01
				String date = (String) object[1];
				BigDecimal money = BigDecimal.ZERO;
				// 销售金额
				if (type == 0) {
					money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				} else {
					// 毛利
					BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
					BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
					money = saleMoney.subtract(costMoney);
				}
				ABCChart data = map.get(itemNum);
				List<ABCCharXy> xyDatas = data.getAbcchartXYs();
				ABCCharXy xyData = readABCCharXyData(xyDatas, date);
				{
					xyData.setContent(xyData.getContent().add(money));
				}

			}
			objects = transferInOrderDao.findMoneyByItemAndMonth(systemBookCode, transferBranchNums, dateFrom, dateTo,
					itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				// 201301 to 2013-01
				String date = (String) object[1];
				BigDecimal money = BigDecimal.ZERO;
				// 销售金额
				if (type == 0) {
					money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				} else {
					// 毛利
					BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
					BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
					money = saleMoney.subtract(costMoney);
				}
				ABCChart data = map.get(itemNum);
				List<ABCCharXy> xyDatas = data.getAbcchartXYs();
				ABCCharXy xyData = readABCCharXyData(xyDatas, date);
				xyData.setContent(xyData.getContent().subtract(money));

			}
		}
		if (query.getTypes().contains(AppConstants.CHECKBOX_WHO)) {
			List<Object[]> objects = wholesaleOrderDao.findMoneyByItemAndMonth(systemBookCode, branchNum, dateFrom,
					dateTo, itemNums, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				// 201301 to 2013-01
				String date = (String) object[1];
				BigDecimal money = BigDecimal.ZERO;
				// 销售金额
				if (type == 0) {
					money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				} else {
					// 毛利
					BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
					BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
					money = saleMoney.subtract(costMoney);
				}
				ABCChart data = map.get(itemNum);
				List<ABCCharXy> xyDatas = data.getAbcchartXYs();
				ABCCharXy xyData = readABCCharXyData(xyDatas, date);
				xyData.setContent(xyData.getContent().add(money));

			}
			objects = wholesaleReturnDao.findMoneyByItemAndMonth(systemBookCode, branchNum, dateFrom, dateTo, itemNums,
					null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				// 201301 to 2013-01
				String date = (String) object[1];
				BigDecimal money = BigDecimal.ZERO;
				// 销售金额
				if (type == 0) {
					money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				} else {
					// 毛利
					BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
					BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
					money = saleMoney.subtract(costMoney);
				}
				ABCChart data = map.get(itemNum);
				List<ABCCharXy> xyDatas = data.getAbcchartXYs();
				ABCCharXy xyData = readABCCharXyData(xyDatas, date);
				xyData.setContent(xyData.getContent().subtract(money));

			}
		}
		List<ABCChart> list = new ArrayList<ABCChart>(map.values());
		return list;
	}

	@Override
	public List<SupplierBranchDetail> findSupplierBranchDetail(SupplierBranchQuery supplierBranchQuery) {
		String systemBookCode = supplierBranchQuery.getSystemBookCode();
		Integer branchNum = supplierBranchQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		List<Integer> itemNums = supplierBranchQuery.getItemNums();

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(supplierBranchQuery.getSupplierNums(), null, null,
				branchNum, systemBookCode);
		Map<Integer, SupplierSaleGroupByBranch> baseMap = new HashMap<Integer, SupplierSaleGroupByBranch>();
		Map<String, SupplierBranchDetail> map = new HashMap<String, SupplierBranchDetail>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleGroupByBranch data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleGroupByBranch();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemName((String) object[2]);
				data.setItemCode((String) object[3]);
				data.setItemSpec((String) object[4]);
				data.setCategoryCode((String) object[5]);
				data.setCategoryName((String) object[6]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}

		}
		String type = supplierBranchQuery.getCheckString();
		if (type.equals(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findMoneyGroupByItemDate(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				
				if(itemNums != null && itemNums.size() > 0){
					if(!itemNums.contains(itemNum)){
						continue;
					}
				}
				
				String date = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchDetail data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":"
							+ date);
					if (data == null) {
						data = new SupplierBranchDetail();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = transferInOrderDao.findMoneyByItemDate(systemBookCode, transferBranchNums, null,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), itemNums);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				String date = (String) object[1];
				BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchDetail data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":"
							+ date);
					if (data == null) {
						data = new SupplierBranchDetail();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemDateSummary(systemBookCode, branchNum,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo(), null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				
				if(itemNums != null && itemNums.size() > 0){
					if(!itemNums.contains(itemNum)){
						continue;
					}
				}
				
				String date = (String) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchDetail data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":"
							+ date);
					if (data == null) {
						data = new SupplierBranchDetail();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}

			objects = wholesaleReturnDao.findItemDateSummary(systemBookCode, branchNum,
					supplierBranchQuery.getDateFrom(), supplierBranchQuery.getDateTo());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = branchNum;
				Integer itemNum = (Integer) object[0];
				if(itemNums != null && itemNums.size() > 0){
					if(!itemNums.contains(itemNum)){
						continue;
					}
				}
				String date = (String) object[1];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				saleMoney = saleMoney.negate();
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setBranchNum(branch);
					SupplierBranchDetail data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":"
							+ date);
					if (data == null) {
						data = new SupplierBranchDetail();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branchNum);
						branchValueData.setValue(saleMoney);
						branchValueDatas.add(branchValueData);
						data.setTotalValue(saleMoney);
						data.setBranchValues(branchValueDatas);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":" + date, data);

					} else {
						BranchValue branchValueData = data.getBranchValues().get(0);
						branchValueData.setValue(branchValueData.getValue().add(saleMoney));
						data.setTotalValue(data.getTotalValue().add(saleMoney));
					}
				}
			}
		} else if (type.equals(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findMoneyGroupByBranchAndItemAndBizday(systemBookCode,
					supplierBranchQuery.getBranchNums(), supplierBranchQuery.getDateFrom(),
					supplierBranchQuery.getDateTo(), true);
			
			BigDecimal saleMoney;
			BigDecimal saleDiscount;
			BigDecimal saleAmount;
			BigDecimal saleProfit;
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branch = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				
				if(itemNums != null && itemNums.size() > 0){
					if(!itemNums.contains(itemNum)){
						continue;
					}
				}
				String date = (String) object[2];
				date = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);

				saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				saleProfit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				saleAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				saleDiscount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
				SupplierSaleGroupByBranch baseData = baseMap.get(itemNum);
				if (baseData != null) {
					baseData.setSaleMoney(saleMoney);
					baseData.setSaleDiscount(saleDiscount);
					baseData.setBranchNum(branch);
					SupplierBranchDetail data = map.get(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":"
							+ date);
					if (data == null) {
						data = new SupplierBranchDetail();
						data.setSaleDate(date);
						BeanUtils.copyProperties(baseData, data);
						List<BranchValue> branchValueDatas = new ArrayList<BranchValue>();
						BranchValue branchValueData = new BranchValue();
						branchValueData.setBranchNum(branch);
						branchValueData.setValue(saleMoney);
						branchValueData.setValue2(saleDiscount);
						branchValueData.setValue3(saleAmount);
						branchValueData.setValue4(saleMoney.subtract(saleProfit));
						branchValueDatas.add(branchValueData);
						data.setBranchValues(branchValueDatas);
						data.setTotalValue(saleMoney);
						data.setTotalDiscount(saleDiscount);
						map.put(baseData.getSupplierNum() + ":" + baseData.getItemNum() + ":" + date, data);
					} else {
						BranchValue branchValueData = readBranchValueData(data.getBranchValues(), branch);
						if (branchValueData != null) {
							branchValueData.setValue(branchValueData.getValue().add(saleMoney));
							branchValueData.setValue2(branchValueData.getValue2().add(saleDiscount));
							branchValueData.setValue3(branchValueData.getValue3().add(saleAmount));
							branchValueData.setValue4(branchValueData.getValue4().add(saleMoney.subtract(saleProfit)));
						} else {
							branchValueData = new BranchValue();
							branchValueData.setBranchNum(branch);
							branchValueData.setValue(saleMoney);
							branchValueData.setValue2(saleDiscount);
							branchValueData.setValue3(saleAmount);
							branchValueData.setValue4(saleMoney.subtract(saleProfit));
							data.getBranchValues().add(branchValueData);
						}
						data.setTotalValue(data.getTotalValue().add(saleMoney));
						data.setTotalDiscount(data.getTotalDiscount().add(saleDiscount));
					}
				}
			}
		}

		List<SupplierBranchDetail> list = new ArrayList<SupplierBranchDetail>(map.values());
		return list;
	}

	@Override
	public List<SupplierCredit> findSupplierCredit(SupplierBranchQuery supplierBranchQuery) {
		String systemBookCode = supplierBranchQuery.getSystemBookCode();
		Integer branchNum = supplierBranchQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		Date dateFrom = supplierBranchQuery.getDateFrom();
		Date dateTo = supplierBranchQuery.getDateTo();
		List<Integer> branchNums = supplierBranchQuery.getBranchNums();

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<Object[]> objects = posItemService.findBySuppliers(null, null, null, branchNum, systemBookCode);
		Map<Integer, SupplierSaleSum> baseMap = new HashMap<Integer, SupplierSaleSum>();
		Map<Integer, SupplierCredit> map = new HashMap<Integer, SupplierCredit>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			SupplierSaleSum data = baseMap.get((Integer) object[1]);
			if (data == null) {
				data = new SupplierSaleSum();
				data.setSupplierNum((Integer) object[0]);
				data.setItemNum((Integer) object[1]);
				data.setItemName((String) object[2]);
				data.setItemCode((String) object[3]);
				data.setItemSpec((String) object[4]);
				data.setCategoryCode((String) object[5]);
				data.setCategoryName((String) object[6]);
				data.setItemInventoryRate((BigDecimal) object[7]);
				data.setItemTransferRate((BigDecimal) object[8]);
				data.setItemWholesaleRate((BigDecimal) object[9]);
				data.setItemPurcharseRate((BigDecimal) object[10]);
				Supplier supplier = readSupplier(suppliers, data.getSupplierNum());
				if (supplier != null) {
					if (supplierBranchQuery.getSupplierNums() != null
							&& supplierBranchQuery.getSupplierNums().size() > 0) {
						if (!supplierBranchQuery.getSupplierNums().contains(data.getSupplierNum())) {
							continue;
						}
					}
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
					baseMap.put(data.getItemNum(), data);
				}
			}

		}
		List<String> types = supplierBranchQuery.getListStrings();
		if (types.contains(AppConstants.CHECKBOX_OUT)) {
			objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null, dateFrom,
					dateTo, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierCredit data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierCredit();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleMaori(data.getSaleMaori().add(saleMoney.subtract(costMoney)));
				}
			}

			objects = transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null, dateFrom,
					dateTo, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierCredit data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierCredit();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(saleMoney.subtract(costMoney)));
				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_WHO)) {
			objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo, null, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierCredit data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierCredit();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleMaori(data.getSaleMaori().add(saleMoney.subtract(costMoney)));
				}
			}

			objects = wholesaleReturnDao.findItemSum(systemBookCode, branchNum, null, dateFrom, dateTo, null, null,
					null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					SupplierCredit data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierCredit();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().subtract(amount));
					data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
					data.setSaleMaori(data.getSaleMaori().subtract(saleMoney.subtract(costMoney)));
				}
			}
		}
		if (types.contains(AppConstants.CHECKBOX_SALE)) {
			objects = posOrderDao.findItemMatrixSum(systemBookCode, branchNums, dateFrom, dateTo, true);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				SupplierSaleSum baseData = baseMap.get(itemNum);
				if (baseData != null) {
					SupplierCredit data = map.get(baseData.getSupplierNum());
					if (data == null) {
						data = new SupplierCredit();
						BeanUtils.copyProperties(baseData, data);
						map.put(data.getSupplierNum(), data);
					}
					data.setSaleQty(data.getSaleQty().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(saleMoney));
					data.setSaleMaori(data.getSaleMaori().add(profit));
				}
			}
		}
		// 收货数量和金额
		objects = receiveOrderDao.findItemAmountAndMoney(systemBookCode, branchNum, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			SupplierSaleSum baseData = baseMap.get(itemNum);
			if (baseData != null) {
				if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
					amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
				SupplierCredit data = map.get(baseData.getSupplierNum());
				if (data == null) {
					data = new SupplierCredit();
					BeanUtils.copyProperties(baseData, data);
					map.put(data.getSupplierNum(), data);
				}
				data.setStockInMoney(data.getStockInMoney().add(money));
				data.setStockInQty(data.getStockInQty().add(amount));
			}
		}

		// 退货数量和金额
		objects = returnOrderDao.findItemAmountAndMoney(systemBookCode, branchNum, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			SupplierSaleSum baseData = baseMap.get(itemNum);
			if (baseData != null) {
				if (baseData.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
					amount = amount.divide(baseData.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
				SupplierCredit data = map.get(baseData.getSupplierNum());
				if (data == null) {
					data = new SupplierCredit();
					BeanUtils.copyProperties(baseData, data);
					map.put(data.getSupplierNum(), data);
				}
				data.setStockInMoney(data.getStockInMoney().subtract(money));
				data.setStockInQty(data.getStockInQty().subtract(amount));
			}
		}

		List<SupplierCredit> list = new ArrayList<SupplierCredit>(map.values());
		// 算毛利率
		for (int i = list.size() - 1; i >= 0; i--) {
			SupplierCredit data = list.get(i);
			if (data.getItemPurcharseRate().compareTo(BigDecimal.ZERO) != 0) {
				data.setStockInQty(data.getStockInQty()
						.divide(data.getItemPurcharseRate(), 4, BigDecimal.ROUND_HALF_UP));
			}
			if (data.getSaleQty().compareTo(BigDecimal.ZERO) == 0) {
				list.remove(i);
				continue;
			}
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleMaoriRate(BigDecimal.ZERO);
			} else {
				data.setSaleMaoriRate(data.getSaleMaori().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));

			}
		}
		return list;
	}

	@Override
	public List<UnsalablePosItem> findUnsalableItems(UnsalableQuery query) {
		String systemBookCode = query.getSystemBookCode();
		Integer branchNum = query.getBranchNum();
		List<Integer> branchNums = query.getBranchNums();
		Date dateTo = null;
		Date dateFrom = null;
		if (query.getDateFrom() != null && query.getDateTo() != null) {
			dateTo = query.getDateTo();
			dateFrom = query.getDateFrom();
		} else {
			dateTo = Calendar.getInstance().getTime();
			dateFrom = DateUtil.addDay(dateTo, -query.getDays());

		}
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Map<Integer, UnsalablePosItem> map = new HashMap<Integer, UnsalablePosItem>();
		for (int i = posItems.size() - 1; i >= 0; i--) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			// 过滤类别
			if (query.getCategoryCodeList() != null && query.getCategoryCodeList().size() > 0) {
				if (!query.getCategoryCodeList().contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			// 过滤停售标记
			if (query.isFilterStopSale() != null && query.isFilterStopSale()) {
				if (posItem.getItemSaleCeaseFlag() != null && posItem.getItemSaleCeaseFlag()) {
					continue;
				}
			}

			UnsalablePosItem data = new UnsalablePosItem();
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemType(posItem.getItemCategory());
			data.setSepc(posItem.getItemSpec());
			data.setPrice(posItem.getItemRegularPrice());
			if (posItem.getItemStockCeaseFlag() != null) {
				data.setStockCrease(posItem.getItemStockCeaseFlag());
			}
			if (posItem.getItemEliminativeFlag() != null) {
				data.setEliminativeFlag(posItem.getItemEliminativeFlag());
			}
			data.setItemNum(posItem.getItemNum());
			if (query.getUnit().equals(AppConstants.UNIT_SOTRE)) {
				data.setPosItemUnit(posItem.getItemInventoryUnit());
			} else if (query.getUnit().equals(AppConstants.UNIT_TRANFER)) {
				data.setPosItemUnit(posItem.getItemTransferUnit());
			} else if (query.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
				data.setPosItemUnit(posItem.getItemPurchaseUnit());
			} else if (query.getUnit().equals(AppConstants.UNIT_PIFA)) {
				data.setPosItemUnit(posItem.getItemWholesaleUnit());
			} else {
				data.setPosItemUnit(posItem.getItemUnit());
			}
			map.put(data.getItemNum(), data);
		}

		// 调出
		List<Object[]> objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums,
				branchNums, dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal saleSubtotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal subtotal = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = saleSubtotal.subtract(subtotal);
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem != null) {

				if (query.getUnit().equals(AppConstants.UNIT_SOTRE)) {
					if (posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_TRANFER)) {
					if (posItem.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
					if (posItem.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_PIFA)) {
					if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				}
				UnsalablePosItem data = map.get(itemNum);
				if (data != null) {
					// 调出量大于设定值的过滤
					if (amount.compareTo(query.getItemAmount()) > 0) {
						map.remove(itemNum);
						continue;
					}
					data.setCurrentOutProfit(data.getCurrentOutProfit().add(profit));
					data.setCurrentOutMoney(data.getCurrentOutMoney().add(saleSubtotal));
					data.setCurrentOutNum(data.getCurrentOutNum().add(amount));
				}
			}

		}

		// 批发
		objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal cost = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = money.subtract(cost);
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem != null) {
				if (query.getUnit().equals(AppConstants.UNIT_SOTRE)) {
					if (posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_TRANFER)) {
					if (posItem.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
					if (posItem.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (query.getUnit().equals(AppConstants.UNIT_PIFA)) {
					if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				}
				UnsalablePosItem data = map.get(itemNum);
				if (data != null) {
					// 批发量大于设定值的过滤
					if (amount.compareTo(query.getItemAmount()) > 0) {
						map.remove(itemNum);
						continue;
					}
					data.setCurrentPifaProfit(data.getCurrentPifaProfit().add(profit));
					data.setCurrentPifaMoney(data.getCurrentPifaMoney().add(money));
					data.setCurrentPifaNum(data.getCurrentPifaNum().add(amount));
				}
			}

		}
		if (!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			List<Integer> branchs = new ArrayList<Integer>();
			branchs.add(branchNum); // 查询自己的销售
			objects = posOrderDao.findItemMatrixSum(systemBookCode, branchs, dateFrom, dateTo, false);
		} else {
			objects = posOrderDao.findItemMatrixSum(systemBookCode, null, dateFrom, dateTo, false);
		}
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			UnsalablePosItem data = map.get(itemNum);
			if (data != null) {
				// 销售量大于设定值 过滤
				if (amount.compareTo(query.getItemAmount()) > 0) {
					map.remove(itemNum);
					continue;
				}
				data.setCurrentSaleNum(data.getCurrentSaleNum().add(amount));
				data.setCurrentSaleMoney(data.getCurrentSaleMoney().add(saleMoney));
				data.setCurrentSaleProfit(data.getCurrentSaleProfit().add(profit));
			}
		}

		List<UnsalablePosItem> list = new ArrayList<UnsalablePosItem>(map.values());
		if (query.isTransfer()) {
			objects = inventoryDao.findCenterStore(systemBookCode, branchNum, null);
		} else {
			objects = inventoryDao.findItemAmount(systemBookCode, branchNum, null);

		}
		for (int i = list.size() - 1; i >= 0; i--) {
			UnsalablePosItem data = list.get(i);
			Integer itemNum = data.getItemNum();
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);

			// 总调出量大于设定值的过滤
			BigDecimal totalAmount = data.getCurrentSaleNum().add(data.getCurrentPifaNum())
					.add(data.getCurrentOutNum());
			if (totalAmount.compareTo(query.getItemAmount()) > 0) {
				list.remove(i);
				continue;
			}
			BigDecimal stockAmount = readItemAmount(objects, itemNum);
			if (query.getUnit().equals(AppConstants.UNIT_SOTRE)) {
				if (posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (query.getUnit().equals(AppConstants.UNIT_TRANFER)) {
				if (posItem.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (query.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
				if (posItem.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (query.getUnit().equals(AppConstants.UNIT_PIFA)) {
				if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			}
			data.setCurrentStore(stockAmount);
			// 过滤库存为0的商品
			if (query.isFilterStoreZero() != null && query.isFilterStoreZero()) {
				if (data.getCurrentStore().compareTo(BigDecimal.ZERO) <= 0) {
					list.remove(i);
					continue;
				}
			}
		}
		// 过滤15天内没有入库记录的商品
		if (query.isFilterInFifteen() != null && query.isFilterInFifteen()) {
			List<Integer> itemNums = posItemLogDao.findItemNum(systemBookCode, branchNum, DateUtil.addDay(dateTo, -15),
					dateTo, true);
			for (int i = list.size() - 1; i >= 0; i--) {
				UnsalablePosItem data = list.get(i);
				if (itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		// 过滤供应商
		if (query.getSupplierNums() != null && query.getSupplierNums().size() != 0) {
			List<StoreItemSupplier> storeItemSuppliers = storeItemSupplierDao.find(systemBookCode, branchNum,
					query.getSupplierNums(), true, null);
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < storeItemSuppliers.size(); i++) {
				itemNums.add(storeItemSuppliers.get(i).getId().getItemNum());
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				UnsalablePosItem data = list.get(i);
				if (!itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		// 过滤从没出入库的商品
		if (query.isFilterInAndOut() != null && query.isFilterInAndOut()) {
			List<Integer> itemNums = posItemLogDao.findItemNum(systemBookCode, branchNum, null, null, null);
			for (int i = list.size() - 1; i >= 0; i--) {
				UnsalablePosItem data = list.get(i);
				if (!itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		return list;
	}

	private BigDecimal readItemAmount(List<Object[]> objects, Integer itemNum) {
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			if (itemNum.equals((Integer) object[0])) {
				return object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			}
		}
		return BigDecimal.ZERO;
	}

	private PosClient readPosClient(List<PosClient> posClients, String clientFid) {
		for (int i = 0; i < posClients.size(); i++) {
			PosClient posClient = posClients.get(i);
			if (posClient.getClientFid().equals(clientFid)) {
				return posClient;
			}
		}
		return null;
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitByClient(WholesaleProfitQuery queryData) {
		String systemBookCode = queryData.getSystemBookCode();

		Map<String, WholesaleProfitByClient> map = new HashMap<String, WholesaleProfitByClient>();

		List<PosClient> posClients = posClientService.findInCache(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByClient(queryData);

		String clientFid = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal qty = null;
		BigDecimal useQty = null;
		BigDecimal baseQty = null;

		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			clientFid = (String) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			costMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			qty = object[4] == null ? BigDecimal.ZERO : ((BigDecimal) object[4]);
			useQty = object[5] == null ? BigDecimal.ZERO : ((BigDecimal) object[5]);
			baseQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);

			WholesaleProfitByClient data = new WholesaleProfitByClient();
			data.setFid(clientFid);
			data.setWholesaleMoney(money);
			data.setWholesaleCost(costMoney);
			data.setRetailPrice(saleMoney);
			data.setWholesaleQty(qty);
			data.setWholesaleUseQty(useQty);
			data.setWholesaleBaseQty(baseQty);
			data.setPresentQty(object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]));
			data.setPresentUseQty(object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]));
			data.setPresentCostMoney(object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]));
			data.setPresentMoney(object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]));
			map.put(clientFid, data);
		}

		BigDecimal presentQty = null;
		BigDecimal presentUseQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByClient(queryData);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			clientFid = (String) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			costMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			qty = object[4] == null ? BigDecimal.ZERO : ((BigDecimal) object[4]);
			useQty = object[5] == null ? BigDecimal.ZERO : ((BigDecimal) object[5]);
			baseQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentUseQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentCostMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);

			WholesaleProfitByClient data = map.get(clientFid);
			if (data == null) {
				data = new WholesaleProfitByClient();
				data.setFid(clientFid);
				data.setWholesaleMoney(money.negate());
				data.setWholesaleCost(costMoney.negate());
				data.setRetailPrice(saleMoney.negate());
				data.setWholesaleQty(qty.negate());
				data.setWholesaleUseQty(useQty.negate());
				data.setWholesaleBaseQty(baseQty.negate());
				data.setPresentQty(presentQty.negate());
				data.setPresentUseQty(presentUseQty.negate());
				data.setPresentCostMoney(presentCostMoney.negate());
				data.setPresentMoney(presentMoney.negate());
				map.put(clientFid, data);
			} else {
				data.setFid(clientFid);
				data.setWholesaleMoney(data.getWholesaleMoney().subtract(money));
				data.setWholesaleCost(data.getWholesaleCost().subtract(costMoney));
				data.setRetailPrice(data.getRetailPrice().subtract(saleMoney));
				data.setWholesaleQty(data.getWholesaleQty().subtract(qty));
				data.setWholesaleUseQty(data.getWholesaleUseQty().subtract(useQty));
				data.setWholesaleBaseQty(data.getWholesaleBaseQty().subtract(baseQty));
				data.setPresentQty(data.getPresentQty().subtract(presentQty));
				data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
				data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
				data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
			}
		}

		List<WholesaleProfitByClient> list = new ArrayList<WholesaleProfitByClient>(map.values());
		for (int i = 0; i < list.size(); i++) {
			WholesaleProfitByClient data = list.get(i);
			PosClient posClient = readPosClient(posClients, data.getFid());
			if (posClient != null) {
				data.setName(posClient.getClientName());
				data.setClientCode(posClient.getClientCode());
			}
			data.setWholesaleProfit(data.getWholesaleMoney().subtract(data.getWholesaleCost()));
			data.setRetailProfit(data.getRetailPrice().subtract(data.getWholesaleMoney()));
			// 零售毛利率
			if (data.getRetailPrice().compareTo(BigDecimal.ZERO) == 0) {
				data.setRetailProfitRate(BigDecimal.ZERO);
			} else {
				data.setRetailProfitRate(data.getRetailProfit()
						.divide(data.getRetailPrice(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
			// 批发毛利率
			if (data.getWholesaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setWholesaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setWholesaleProfitRate(data.getWholesaleProfit()
						.divide(data.getWholesaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
		}
		return list;
	}

	@Override
	public List<WholesaleProfitByPosItem> findWholesaleProfitByPosItem(WholesaleProfitQuery wholesaleProfitQuery) {
		String systemBookCode = wholesaleProfitQuery.getSystemBookCode();

		Map<String, WholesaleProfitByPosItem> map = new HashMap<String, WholesaleProfitByPosItem>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByItemMatrix(wholesaleProfitQuery);

		Integer itemNum = null;
		Integer itemMatrixNum = null;
		BigDecimal qty = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal useQty = null;
		BigDecimal presentQty = null;
		BigDecimal presentUseQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			itemNum = (Integer) object[0];
			itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			qty = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			saleMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			useQty = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			presentQty = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			presentUseQty = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			presentCostMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);
			
			WholesaleProfitByPosItem data = new WholesaleProfitByPosItem();
			data.setPosItemNum(itemNum);
			data.setItemMatrixNum(itemMatrixNum);
			data.setSaleNum(qty);
			data.setSaleMoney(money);
			data.setSaleCost(costMoney);
			data.setReSaleCost(saleMoney);
			data.setSaleUseQty(useQty);
			data.setSaleBaseQty(qty);
			data.setPresentQty(presentQty);
			data.setPresentUseQty(presentUseQty);
			data.setPresentCostMoney(presentCostMoney);
			data.setPresentMoney(presentMoney);
			map.put(itemNum + "|" + itemMatrixNum, data);

		}

		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByItemMatrix(wholesaleProfitQuery);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			itemNum = (Integer) object[0];
			itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			qty = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			saleMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			useQty = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			presentQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentUseQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentCostMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);
			
			WholesaleProfitByPosItem data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new WholesaleProfitByPosItem();
				data.setPosItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			data.setSaleNum(data.getSaleNum().subtract(qty));
			data.setSaleMoney(data.getSaleMoney().subtract(money));
			data.setSaleCost(data.getSaleCost().subtract(costMoney));
			data.setReSaleCost(data.getReSaleCost().subtract(saleMoney));
			data.setSaleUseQty(data.getSaleUseQty().subtract(useQty));
			data.setSaleBaseQty(data.getSaleBaseQty().subtract(qty));
			data.setPresentQty(data.getPresentQty().subtract(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
		}

		List<WholesaleProfitByPosItem> list = new ArrayList<WholesaleProfitByPosItem>(map.values());
		for (int i = list.size() - 1; i >= 0; i--) {
			WholesaleProfitByPosItem data = list.get(i);
			itemMatrixNum = data.getItemMatrixNum();
			PosItem posItem = AppUtil.getPosItem(data.getPosItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					list.remove(i);
					continue;
				}
			}
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setPosItemTypeName(posItem.getItemCategory());
			data.setSpec(posItem.getItemSpec());
			data.setUnit(posItem.getItemWholesaleUnit());
			data.setBaseUnit(posItem.getItemUnit());
			if (itemMatrixNum > 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getPosItemNum(),
						data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			BigDecimal rate = posItem.getItemWholesaleRate();
			if (rate.compareTo(BigDecimal.ZERO) != 0) {
				data.setSaleNum(data.getSaleNum().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				data.setPresentQty(data.getPresentQty().divide(rate, 4, BigDecimal.ROUND_HALF_UP));

			}
			data.setSaleProfit(data.getSaleMoney().subtract(data.getSaleCost()));
			data.setReSaleProfit(data.getReSaleCost().subtract(data.getSaleMoney()));
			if (data.getReSaleCost().compareTo(BigDecimal.ZERO) == 0) {
				data.setReSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setReSaleProfitRate(data.getReSaleProfit()
						.divide(data.getReSaleCost(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100))
						.setScale(2));
			}
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)).setScale(2));
			}
		}
		return list;
	}

	@Override
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByPosItemDetail(
			WholesaleProfitQuery wholesaleProfitQuery) {
		String systemBookCode = wholesaleProfitQuery.getSystemBookCode();

		List<WholesaleProfitByPosItemDetail> list = new ArrayList<WholesaleProfitByPosItemDetail>();
		List<PosClient> posClients = posClientService.findInCache(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findDetail(wholesaleProfitQuery);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		BigDecimal presentQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			String orderFid = (String) object[0];
			Date saleDate = (Date) object[1];
			String seller = (String) object[2];
			String creator = (String) object[3];
			String auditor = (String) object[4];
			String clientFid = (String) object[5];
			String itemCode = (String) object[6];
			String itemName = (String) object[7];
			String itemSpec = (String) object[8];
			String useUnit = (String) object[9];
			BigDecimal useQty = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];
			BigDecimal usePrice = object[11] == null ? BigDecimal.ZERO : (BigDecimal) object[11];
			BigDecimal money = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			BigDecimal qty = object[13] == null ? BigDecimal.ZERO : (BigDecimal) object[13];
			BigDecimal cost = object[14] == null ? BigDecimal.ZERO : (BigDecimal) object[14];
			String memo = (String) object[15];
			Integer itemNum = (Integer) object[16];
			Integer itemMatrixNum = (Integer) object[17];
			presentQty = object[18] == null ? BigDecimal.ZERO : (BigDecimal) object[18];
			presentCostMoney = presentQty.multiply(object[20] == null ? BigDecimal.ZERO : ((BigDecimal) object[20])).setScale(2, BigDecimal.ROUND_HALF_UP);
			presentMoney = presentQty.multiply(object[21] == null ? BigDecimal.ZERO : ((BigDecimal) object[21])).setScale(2, BigDecimal.ROUND_HALF_UP);

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (itemMatrixNum != null && itemMatrixNum != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), itemNum, itemMatrixNum);
				if (itemMatrix != null) {
					itemName = itemName.concat(AppUtil.getMatrixName(itemMatrix));
				}
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					continue;
				}
			}
			WholesaleProfitByPosItemDetail data = new WholesaleProfitByPosItemDetail();
			data.setPosOrderNum(orderFid);
			data.setPosOrderType(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER);
			data.setSaleTime(saleDate);
			data.setOrderSeller(seller);
			data.setOrderMaker(creator);
			data.setOrderAuditor(auditor);
			PosClient posClient = readPosClient(posClients, clientFid);
			if (posClient != null) {
				data.setClientCode(posClient.getClientCode());
				data.setClientName(posClient.getClientName());
			}

			data.setPosItemCode(itemCode);
			data.setPosItemName(itemName);
			data.setSpec(itemSpec);
			data.setUnit(useUnit);
			data.setWholesaleNum(useQty);
			data.setWholesaleMoney(money);
			data.setWholesaleCost(qty.multiply(cost));
			data.setWholesaleUnitPrice(usePrice);
			data.setWholesaleProfit(money.subtract(data.getWholesaleCost()));
			data.setRemark(memo);
			data.setInnerNo((String) object[19]);
			data.setBaseUnit(posItem.getItemUnit());
			data.setWholesaleBaseNum(qty);
			data.setPresentCostMoney(presentCostMoney);
			data.setPresentMoney(presentMoney);
			data.setPresentUseQty(object[22] == null ? BigDecimal.ZERO : ((BigDecimal) object[22]));
			data.setPresentUnit((String) object[23]);
			data.setItemValidPeriod(posItem.getItemValidPeriod());
			data.setProductDate((Date) object[24]);
			list.add(data);

		}

		List<Object[]> returnObjects = wholesaleReturnDao.findDetail(wholesaleProfitQuery);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			String orderFid = (String) object[0];
			Date saleDate = (Date) object[1];
			String seller = (String) object[2];
			String creator = (String) object[3];
			String auditor = (String) object[4];
			String clientFid = (String) object[5];
			String itemCode = (String) object[6];
			String itemName = (String) object[7];
			String itemSpec = (String) object[8];
			String useUnit = (String) object[9];
			BigDecimal useQty = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];
			BigDecimal usePrice = object[11] == null ? BigDecimal.ZERO : (BigDecimal) object[11];
			BigDecimal money = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			BigDecimal qty = object[13] == null ? BigDecimal.ZERO : (BigDecimal) object[13];
			BigDecimal cost = object[14] == null ? BigDecimal.ZERO : (BigDecimal) object[14];
			String memo = (String) object[15];
			Integer itemNum = (Integer) object[16];
			Integer itemMatrixNum = (Integer) object[17];
			presentQty = object[18] == null ? BigDecimal.ZERO : (BigDecimal) object[18];
			presentCostMoney = presentQty.multiply(object[19] == null ? BigDecimal.ZERO : ((BigDecimal) object[19])).setScale(2, BigDecimal.ROUND_HALF_UP);
			presentMoney = presentQty.multiply(object[20] == null ? BigDecimal.ZERO : ((BigDecimal) object[20])).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (itemMatrixNum != null && itemMatrixNum != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), itemNum, itemMatrixNum);
				if (itemMatrix != null) {
					itemName = itemName.concat(AppUtil.getMatrixName(itemMatrix));
				}
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					continue;
				}
			}

			WholesaleProfitByPosItemDetail data = new WholesaleProfitByPosItemDetail();
			data.setPosOrderNum(orderFid);
			data.setPosOrderType(AppConstants.POS_ITEM_LOG_WHOLESALE_RETURN_ORDER);
			data.setSaleTime(saleDate);
			data.setOrderSeller(seller);
			data.setOrderMaker(creator);
			data.setOrderAuditor(auditor);
			PosClient posClient = readPosClient(posClients, clientFid);
			data.setClientCode(posClient.getClientCode());
			data.setClientName(posClient.getClientName());
			data.setPosItemCode(itemCode);
			data.setPosItemName(itemName);
			data.setSpec(itemSpec);
			data.setUnit(useUnit);
			data.setWholesaleNum(useQty.negate());
			data.setWholesaleMoney(money.negate());
			data.setWholesaleCost(qty.multiply(cost).negate());
			data.setWholesaleUnitPrice(usePrice);
			data.setWholesaleProfit(data.getWholesaleMoney().subtract(data.getWholesaleCost()));
			data.setRemark(memo);
			data.setBaseUnit(posItem.getItemUnit());
			data.setWholesaleBaseNum(qty.negate());
			data.setPresentCostMoney(presentCostMoney);
			data.setPresentMoney(presentMoney);
			data.setPresentUseQty(object[21] == null ? BigDecimal.ZERO : ((BigDecimal) object[21]));
			data.setPresentUnit((String) object[22]);
			data.setItemValidPeriod(posItem.getItemValidPeriod());
			data.setProductDate((Date) object[23]);
			list.add(data);

		}
		return list;
	}

	@Override
	public Object[] readWholesaleSummary(WholesaleProfitQuery wholesaleProfitQuery) {

		BigDecimal wholesaleAmount = BigDecimal.ZERO;
		BigDecimal wholesaleMoney = BigDecimal.ZERO;
		BigDecimal wholesaleProfit = BigDecimal.ZERO;
		BigDecimal wholesaleCost = BigDecimal.ZERO;
		BigDecimal saleMoney = BigDecimal.ZERO;
		BigDecimal saleProfit = BigDecimal.ZERO;
		BigDecimal wholesaleProfitRate = BigDecimal.ZERO;
		BigDecimal saleProfitRate = BigDecimal.ZERO;

		Object[] saleObjects = wholesaleOrderDao.readProfitSummary(wholesaleProfitQuery);
		wholesaleMoney = saleObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[0];
		wholesaleCost = saleObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[1];
		saleMoney = saleObjects[2] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[2];
		wholesaleAmount = saleObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[3];

		Object[] returnObjects = wholesaleReturnDao.readProfitSummary(wholesaleProfitQuery);

		wholesaleMoney = wholesaleMoney.subtract(returnObjects[0] == null ? BigDecimal.ZERO
				: (BigDecimal) returnObjects[0]);
		wholesaleCost = wholesaleCost.subtract(returnObjects[1] == null ? BigDecimal.ZERO
				: (BigDecimal) returnObjects[1]);
		saleMoney = saleMoney.subtract(returnObjects[2] == null ? BigDecimal.ZERO : (BigDecimal) returnObjects[2]);
		wholesaleAmount = wholesaleAmount.subtract(returnObjects[3] == null ? BigDecimal.ZERO
				: (BigDecimal) returnObjects[3]);

		wholesaleProfit = wholesaleMoney.subtract(wholesaleCost);
		saleProfit = saleMoney.subtract(wholesaleMoney);

		if (wholesaleMoney.compareTo(BigDecimal.ZERO) != 0) {
			wholesaleProfitRate = wholesaleProfit.divide(wholesaleMoney, 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(100)).setScale(2);

		}
		if (saleMoney.compareTo(BigDecimal.ZERO) != 0) {
			saleProfitRate = saleProfit.divide(saleMoney, 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(100)).setScale(2);

		}

		Object[] objects = new Object[8];
		objects[0] = wholesaleAmount; // 销售数量
		objects[1] = wholesaleMoney;// 销售金额
		objects[2] = wholesaleProfit;// 销售毛利
		objects[3] = wholesaleCost;// 销售成本
		objects[4] = saleMoney;// 零售金额
		objects[5] = saleProfit;// 零售毛利
		objects[6] = wholesaleProfitRate;// 销售毛利率
		objects[7] = saleProfitRate;// 零售毛利率
		return objects;
	}

	@Override
	public Object[] readWholesaleOrderAndReturnSummary(WholesaleProfitQuery wholesaleProfitQuery) {
		BigDecimal wholesaleOrderAmount = BigDecimal.ZERO;
		BigDecimal wholesaleOrderMoney = BigDecimal.ZERO;
		BigDecimal wholesaleOrderProfit = BigDecimal.ZERO;
		BigDecimal wholesaleOrderCost = BigDecimal.ZERO;
		Object[] saleObjects = wholesaleOrderDao.readProfitSummary(wholesaleProfitQuery);
		wholesaleOrderMoney = saleObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[0];
		wholesaleOrderCost = saleObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[1];
		wholesaleOrderAmount = saleObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) saleObjects[3];
		wholesaleOrderProfit = wholesaleOrderMoney.subtract(wholesaleOrderCost);

		BigDecimal wholesaleReturnAmount = BigDecimal.ZERO;
		BigDecimal wholesaleReturnMoney = BigDecimal.ZERO;
		BigDecimal wholesaleReturnProfit = BigDecimal.ZERO;
		BigDecimal wholesaleReturnCost = BigDecimal.ZERO;
		Object[] returnObjects = wholesaleReturnDao.readProfitSummary(wholesaleProfitQuery);
		wholesaleReturnMoney = returnObjects[0] == null ? BigDecimal.ZERO : (BigDecimal) returnObjects[0];
		wholesaleReturnCost = returnObjects[1] == null ? BigDecimal.ZERO : (BigDecimal) returnObjects[1];
		wholesaleReturnAmount = returnObjects[3] == null ? BigDecimal.ZERO : (BigDecimal) returnObjects[3];
		wholesaleReturnProfit = wholesaleReturnMoney.subtract(wholesaleReturnCost);

		Object[] objects = new Object[8];
		objects[0] = wholesaleOrderAmount;
		objects[1] = wholesaleOrderMoney;
		objects[2] = wholesaleOrderProfit;
		objects[3] = wholesaleOrderCost;
		objects[4] = wholesaleReturnAmount;
		objects[5] = wholesaleReturnMoney;
		objects[6] = wholesaleReturnProfit;
		objects[7] = wholesaleReturnCost;
		return objects;
	}

	@Override
	public List<ToPicking> findToPicking(ShipGoodsQuery queryData) {
		String systemBookCode = queryData.getSystemBookCode();
		List<Integer> branchNums = queryData.getBranchNums();
		List<String> clientFids = queryData.getClientFids();
		Integer centerBranchNum = queryData.getCenterBranchNum();
		Integer storehouseNum = queryData.getStorehouseNum();
		List<ToPicking> list = new ArrayList<ToPicking>();
		String settlementState = queryData.getSettlementState();
		Integer branchNum = queryData.getBranchNum();

		if (queryData.getIsClientFid() == null || (queryData.getIsClientFid() != null && !queryData.getIsClientFid())) {
			List<TransferOutOrder> transferOutOrders = transferOutOrderDao.findToPicking(systemBookCode,
					centerBranchNum, branchNums, storehouseNum);
			for (int i = 0; i < transferOutOrders.size(); i++) {
				TransferOutOrder outOrder = transferOutOrders.get(i);
				ToPicking data = new ToPicking();
				data.setOrderNo(outOrder.getOutOrderFid());
				data.setOrderType(AppConstants.POS_ITEM_LOG_OUT_ORDER);
				data.setBranchNum(outOrder.getBranchNum());
				data.setSettlementState(outOrder.createSettleMentState());
				if (StringUtils.isNotEmpty(settlementState)) {
					if (!settlementState.equals(data.getSettlementState())) {
						continue;
					}
				}
				data.setOrderState(outOrder.getState().getStateName());
				data.setShipOrderTotalMoeny(outOrder.getOutOrderDueMoney());
				data.setStorehouseNum(outOrder.getStorehouseNum());
				data.setOrderCreator(outOrder.getOutOrderCreator());
				data.setOrderCreateTime(outOrder.getOutOrderCreateTime());
				data.setShipOrderAuditor(outOrder.getOutOrderAuditor());
				data.setShipOrderAuditTime(outOrder.getOutOrderAuditTime());
				data.setMemo(outOrder.getOutOrderMemo());
				list.add(data);
			}
		}

		if (clientFids != null) {

			List<WholesaleOrder> wholesaleOrders = wholesaleOrderDao.findToPicking(systemBookCode, branchNum,
					clientFids, storehouseNum, null);
			List<PosClient> posClients = posClientService.findInCache(systemBookCode);
			for (int i = 0; i < wholesaleOrders.size(); i++) {
				WholesaleOrder wholesaleOrder = wholesaleOrders.get(i);
				ToPicking data = new ToPicking();
				data.setOrderNo(wholesaleOrder.getWholesaleOrderFid());
				data.setOrderType(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER);
				PosClient posClient = readPosClient(posClients, wholesaleOrder.getClientFid());
				if (posClient != null) {
					data.setShipClient(posClient.getClientCode() + "|" + posClient.getClientName());
				}
				data.setSettlementState(wholesaleOrder.getSettleMentState());
				if (StringUtils.isNotEmpty(settlementState)) {
					if (!settlementState.equals(data.getSettlementState())) {
						continue;
					}
				}
				data.setOrderState(wholesaleOrder.getState().getStateName());
				data.setShipOrderTotalMoeny(wholesaleOrder.getWholesaleOrderDueMoney());
				data.setStorehouseNum(wholesaleOrder.getStorehouseNum());
				data.setOrderCreator(wholesaleOrder.getWholesaleOrderCreator());
				data.setOrderCreateTime(wholesaleOrder.getWholesaleOrderCreateTime());
				data.setShipOrderAuditor(wholesaleOrder.getWholesaleOrderAuditor());
				data.setShipOrderAuditTime(wholesaleOrder.getWholesaleOrderAuditTime());
				data.setMemo(wholesaleOrder.getWholesaleOrderMemo());
				list.add(data);
			}
		}

		return list;
	}

	@Override
	public List<ToShip> findToShip(ShipGoodsQuery queryData) {
		String systemBookCode = queryData.getSystemBookCode();
		List<Integer> branchNums = queryData.getBranchNums();
		List<String> clientFids = queryData.getClientFids();
		Integer storehouseNum = queryData.getStorehouseNum();
		Integer centerBranchNum = queryData.getCenterBranchNum();
		String settlementState = queryData.getSettlementState();
		Integer branchNum = queryData.getBranchNum();
		List<ToShip> list = new ArrayList<ToShip>();

		if (queryData.getIsClientFid() == null || (queryData.getIsClientFid() != null && !queryData.getIsClientFid())) {
			List<TransferOutOrder> transferOutOrders = transferOutOrderDao.findToShip(systemBookCode, centerBranchNum,
					branchNums, storehouseNum);
			for (int i = 0; i < transferOutOrders.size(); i++) {
				TransferOutOrder outOrder = transferOutOrders.get(i);
				ToShip data = new ToShip();
				data.setOrderNo(outOrder.getOutOrderFid());
				data.setOrderType(AppConstants.POS_ITEM_LOG_OUT_ORDER);
				data.setBranchNum(outOrder.getBranchNum());
				data.setSettlementState(outOrder.createSettleMentState());
				if (StringUtils.isNotEmpty(settlementState)) {
					if (!settlementState.equals(data.getSettlementState())) {
						continue;
					}
				}
				data.setOrderState(outOrder.getState().getStateName());
				data.setShipOrderTotalMoeny(outOrder.getOutOrderDueMoney());
				data.setStorehouseNum(outOrder.getStorehouseNum());
				data.setShipOrderAuditor(outOrder.getOutOrderAuditor());
				data.setShipOrderAuditorTime(outOrder.getOutOrderAuditTime());
				data.setShipOrderAuditor(outOrder.getOutOrderAuditor());
				data.setOrderPickingDate(outOrder.getOutOrderPickingTime());
				data.setMemo(outOrder.getOutOrderMemo());
				data.setShipOrderPicker(outOrder.getOutOrderPicker());
				list.add(data);
			}
		}

		List<WholesaleOrder> wholesaleOrders = wholesaleOrderDao.findToShip(systemBookCode, branchNum, clientFids,
				storehouseNum, null);
		List<PosClient> posClients = posClientService.findInCache(systemBookCode);
		for (int i = 0; i < wholesaleOrders.size(); i++) {
			WholesaleOrder wholesaleOrder = wholesaleOrders.get(i);
			ToShip data = new ToShip();
			PosClient posClient = AppUtil.getPosClient(wholesaleOrder.getClientFid(), posClients);
			if (posClient != null) {
				data.setShipClient(posClient.getClientCode() + "|" + posClient.getClientName());
			}
			data.setOrderNo(wholesaleOrder.getWholesaleOrderFid());
			data.setOrderType(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER);
			data.setSettlementState(wholesaleOrder.getSettleMentState());
			if (StringUtils.isNotEmpty(settlementState)) {
				if (!settlementState.equals(data.getSettlementState())) {
					continue;
				}
			}
			data.setOrderState(wholesaleOrder.getState().getStateName());
			data.setShipOrderTotalMoeny(wholesaleOrder.getWholesaleOrderDueMoney());
			data.setStorehouseNum(wholesaleOrder.getStorehouseNum());
			data.setShipOrderAuditor(wholesaleOrder.getWholesaleOrderAuditor());
			data.setShipOrderAuditorTime(wholesaleOrder.getWholesaleOrderAuditTime());
			data.setShipOrderAuditor(wholesaleOrder.getWholesaleOrderAuditor());
			data.setOrderPickingDate(wholesaleOrder.getWholesaleOrderPickingTime());
			data.setMemo(wholesaleOrder.getWholesaleOrderMemo());
			data.setShipOrderPicker(wholesaleOrder.getWholesaleOrderPicker());
			list.add(data);
		}
		return list;
	}

	@Override
	public List<UnsalablePosItem> findNegativeMargin(InventoryExceptQuery queryData) {
		String systemBookCode = queryData.getSystemBookCode();
		Integer branchNum = queryData.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		Date dateFrom = queryData.getDateFrom();
		Date dateTo = queryData.getDateTo();
		Map<Integer, UnsalablePosItem> map = new HashMap<Integer, UnsalablePosItem>();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		for (int i = posItems.size() - 1; i >= 0; i--) {
			PosItem posItem = posItems.get(i);

			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			// 过滤类别
			if (queryData.getItemCategorys() != null && queryData.getItemCategorys().size() > 0) {
				if (!queryData.getItemCategorys().contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			UnsalablePosItem data = new UnsalablePosItem();
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemType(posItem.getItemCategory());
			data.setSepc(posItem.getItemSpec());
			data.setPrice(posItem.getItemRegularPrice());
			data.setItemNum(posItem.getItemNum());
			data.setItemPurchaseRate(posItem.getItemPurchaseRate());
			data.setItemTransferRate(posItem.getItemTransferRate());
			data.setItemInventoryRate(posItem.getItemInventoryRate());
			data.setItemWholesaleRate(posItem.getItemWholesaleRate());
			if (posItem.getItemStockCeaseFlag() != null) {
				data.setStockCrease(posItem.getItemStockCeaseFlag());
			}
			if (queryData.getUnit().equals(AppConstants.UNIT_SOTRE)) {
				data.setPosItemUnit(posItem.getItemInventoryUnit());
			} else if (queryData.getUnit().equals(AppConstants.UNIT_TRANFER)) {
				data.setPosItemUnit(posItem.getItemTransferUnit());
			} else if (queryData.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
				data.setPosItemUnit(posItem.getItemPurchaseUnit());
			} else if (queryData.getUnit().equals(AppConstants.UNIT_PIFA)) {
				data.setPosItemUnit(posItem.getItemWholesaleUnit());
			} else {
				data.setPosItemUnit(posItem.getItemUnit());
			}
			map.put(data.getItemNum(), data);
		}
		// 调出
		List<Object[]> objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
				dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal saleSubtotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal subtotal = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = saleSubtotal.subtract(subtotal);
			UnsalablePosItem data = map.get(itemNum);
			if (data != null) {

				if (queryData.getUnit().equals(AppConstants.UNIT_SOTRE)) {
					if (data.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_TRANFER)) {
					if (data.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
					if (data.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_PIFA)) {
					if (data.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				}
				data.setCurrentOutProfit(data.getCurrentOutProfit().add(profit));
				data.setCurrentOutMoney(data.getCurrentOutMoney().add(saleSubtotal));
				data.setCurrentOutNum(data.getCurrentOutNum().add(amount));

			}

		}

		// 批发
		objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal cost = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = money.subtract(cost);
			UnsalablePosItem data = map.get(itemNum);
			if (data != null) {

				if (queryData.getUnit().equals(AppConstants.UNIT_SOTRE)) {
					if (data.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_TRANFER)) {
					if (data.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
					if (data.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				} else if (queryData.getUnit().equals(AppConstants.UNIT_PIFA)) {
					if (data.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
						amount = amount.divide(data.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
				}
				data.setCurrentPifaProfit(data.getCurrentPifaProfit().add(profit));
				data.setCurrentPifaMoney(data.getCurrentPifaMoney().add(money));
				data.setCurrentPifaNum(data.getCurrentPifaNum().add(amount));

			}

		}
		if (!queryData.isTransferCenter()) {

			List<Integer> branchs = new ArrayList<Integer>();
			branchs.add(branchNum); // 查询自己的销售
			objects = posOrderDao.findItemMatrixSum(systemBookCode, branchs, dateFrom, dateTo, false);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				UnsalablePosItem data = map.get(itemNum);
				if (data != null) {
					data.setCurrentSaleNum(data.getCurrentSaleNum().add(amount));
					data.setCurrentSaleMoney(data.getCurrentSaleMoney().add(saleMoney));
					data.setCurrentSaleProfit(data.getCurrentSaleProfit().add(profit));
				}
			}
		}
		List<UnsalablePosItem> list = new ArrayList<UnsalablePosItem>(map.values());
		objects = inventoryDao.findItemAmount(systemBookCode, branchNum, null);
		for (int i = list.size() - 1; i >= 0; i--) {
			UnsalablePosItem data = list.get(i);
			Integer itemNum = data.getItemNum();

			// 过滤正毛利商品
			if (data.getCurrentOutProfit().compareTo(BigDecimal.ZERO) >= 0
					&& data.getCurrentPifaProfit().compareTo(BigDecimal.ZERO) >= 0
					&& data.getCurrentSaleProfit().compareTo(BigDecimal.ZERO) >= 0) {
				list.remove(i);
				continue;
			}
			BigDecimal stockAmount = readItemAmount(objects, itemNum);
			if (queryData.getUnit().equals(AppConstants.UNIT_SOTRE)) {
				if (data.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(data.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (queryData.getUnit().equals(AppConstants.UNIT_TRANFER)) {
				if (data.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(data.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (queryData.getUnit().equals(AppConstants.UNIT_PURCHASE)) {
				if (data.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(data.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			} else if (queryData.getUnit().equals(AppConstants.UNIT_PIFA)) {
				if (data.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0) {
					stockAmount = stockAmount.divide(data.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				}
			}
			data.setCurrentStore(stockAmount);
		}

		// 过滤供应商
		if (queryData.getSupplierNums() != null && queryData.getSupplierNums().size() != 0) {
			List<StoreItemSupplier> storeItemSuppliers = storeItemSupplierDao.find(systemBookCode, branchNum,
					queryData.getSupplierNums(), true, null);
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < storeItemSuppliers.size(); i++) {
				itemNums.add(storeItemSuppliers.get(i).getId().getItemNum());
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				UnsalablePosItem data = list.get(i);
				if (!itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public List<ExceptInventory> findSingularItem(InventoryExceptQuery inventoryExceptQuery) {
		String systemBookCode = inventoryExceptQuery.getSystemBookCode();
		Integer branchNum = inventoryExceptQuery.getBranchNum();
		List<Integer> branchNums = inventoryExceptQuery.getBranchNums();
		if(branchNums == null){
			return new ArrayList<ExceptInventory>();
		}
		Date dateFrom = inventoryExceptQuery.getDateFrom();
		Date dateTo = inventoryExceptQuery.getDateTo();

		Map<Integer, ExceptInventory> map = new HashMap<Integer, ExceptInventory>();
		Date initDate = DateUtil.getDateTimeHMS(AppConstants.INIT_TIME);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		for (int i = posItems.size() - 1; i >= 0; i--) {
			PosItem posItem = posItems.get(i);
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			// 过滤类别
			if (inventoryExceptQuery.getItemCategorys() != null && inventoryExceptQuery.getItemCategorys().size() > 0) {
				if (!inventoryExceptQuery.getItemCategorys().contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			ExceptInventory data = new ExceptInventory();
			data.setItemCategoryCode(posItem.getItemCategoryCode());
			data.setItemCategory(posItem.getItemCategory());
			data.setItemName(posItem.getItemName());
			data.setItemCode(posItem.getItemCode());
			data.setItemSpec(posItem.getItemSpec());
			data.setItemNum(posItem.getItemNum());
			data.setInventoryUnit(posItem.getItemInventoryUnit());
			data.setItemValidPeriod(posItem.getItemValidPeriod());
			if (posItem.getItemStockCeaseFlag() != null) {
				data.setStockCrease(posItem.getItemStockCeaseFlag());
			}
			data.setSaleCease(posItem.getItemSaleCeaseFlag());
			data.setRate(posItem.getItemInventoryRate());
			if (branchNums.size() > 1) {
				data.setInventoryDate(initDate);
			}
			map.put(data.getItemNum(), data);
		}

		List<Object[]> objects = inventoryCollectDao.findItemLatestDate(systemBookCode, branchNums, dateFrom, dateTo,
				null, inventoryExceptQuery.getPosItemLogSummary());
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Date logDate = (Date) object[1];
			ExceptInventory data = map.get(itemNum);
			if (data != null) {
				data.setInventoryDate(logDate);
			}
		}
		List<ExceptInventory> list = new ArrayList<ExceptInventory>(map.values());
		// 过滤时间小于天数
		for (int i = list.size() - 1; i >= 0; i--) {
			ExceptInventory data = list.get(i);
			if (data.getInventoryDate() == null) {
				list.remove(data);
			} else {
				int diffDay = DateUtil.diffDay(data.getInventoryDate(),
						DateUtil.getMinOfDate(Calendar.getInstance().getTime()));
				if (inventoryExceptQuery.getReceiveDay() != null) {
					if (diffDay < inventoryExceptQuery.getReceiveDay()) {
						list.remove(data);
					}
				} else {
					if (diffDay < data.getItemValidPeriod()) {
						list.remove(data);
					}
				}

			}
		}
		objects = inventoryDao.findItemAmount(systemBookCode, branchNum, null);
		for (int i = list.size() - 1; i >= 0; i--) {
			ExceptInventory data = list.get(i);
			Integer itemNum = data.getItemNum();
			BigDecimal stockAmount = readItemAmount(objects, itemNum);
			if (inventoryExceptQuery.isFilterInventory()) {
				if (stockAmount.compareTo(BigDecimal.ZERO) > 0) {
					list.remove(i);
					continue;
				}
			}
			BigDecimal rate = data.getRate();
			if (rate.compareTo(BigDecimal.ZERO) != 0) {
				stockAmount = stockAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
			}
			data.setInventoryUseAmount(stockAmount);
		}
		// 过滤供应商
		if (inventoryExceptQuery.getSupplierNums() != null && inventoryExceptQuery.getSupplierNums().size() != 0) {
			List<StoreItemSupplier> storeItemSuppliers = storeItemSupplierDao.find(systemBookCode, branchNum,
					inventoryExceptQuery.getSupplierNums(), true, null);
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < storeItemSuppliers.size(); i++) {
				itemNums.add(storeItemSuppliers.get(i).getId().getItemNum());
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				ExceptInventory data = list.get(i);
				if (!itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public List<SingularPrice> findSingularPrice(InventoryExceptQuery inventoryExceptQuery) {
		String systemBookCode = inventoryExceptQuery.getSystemBookCode();
		Integer branchNum = inventoryExceptQuery.getBranchNum();
		Date dateFrom = inventoryExceptQuery.getDateFrom();
		Date dateTo = inventoryExceptQuery.getDateTo();
		Map<Integer, SingularPrice> map = new HashMap<Integer, SingularPrice>();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<StoreMatrix> storeMatrixs = null;
		if(!branchNum.equals(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)){
			storeMatrixs = storeMatrixDao.findByBranch(systemBookCode, branchNum, null);
		}
		BigDecimal transferPrice = null;
		for (int i = posItems.size() - 1; i >= 0; i--) {
			PosItem posItem = posItems.get(i);

			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			// 过滤类别
			if (inventoryExceptQuery.getItemCategorys() != null && inventoryExceptQuery.getItemCategorys().size() > 0) {
				if (!inventoryExceptQuery.getItemCategorys().contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			SingularPrice data = new SingularPrice();
			data.setItemCategoryCode(posItem.getItemCategoryCode());
			data.setItemCategory(posItem.getItemCategory());
			data.setItemName(posItem.getItemName());
			data.setItemCode(posItem.getItemCode());
			data.setItemSpec(posItem.getItemSpec());
			data.setItemNum(posItem.getItemNum());
			data.setInventoryUnit(posItem.getItemInventoryUnit());
			data.setItemTransfer(posItem.getItemTransferPrice());
			
			transferPrice = AppUtil.getTransferPrice(storeMatrixs, branchNum, data.getItemNum());
			if(transferPrice != null){
				data.setItemTransfer(transferPrice);

			}
			
			data.setRate(posItem.getItemInventoryRate());
			map.put(data.getItemNum(), data);
		}
		// 查询最近进货价
		List<Object[]> objects = posItemLogDao.findItemLatestPriceDate(systemBookCode, branchNum, dateFrom, dateTo,
				null, AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			
			BigDecimal price = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			SingularPrice data = map.get(itemNum);
			if (data != null) {
				data.setItemLogPrice(price);
			}
		}
		List<Object[]> objects1 = posItemLogDao.findItemPrice(systemBookCode, branchNum, dateFrom, dateTo, null);
		for (int i = 0; i < objects1.size(); i++) {
			Object[] object = objects1.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal maxprice = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal minPrice = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			SingularPrice data = map.get(itemNum);
			if (data != null) {
				data.setMaxPrice(maxprice);
				data.setMinPrice(minPrice);
			}
		}

		List<SingularPrice> list = new ArrayList<SingularPrice>(map.values());
		if (inventoryExceptQuery.getMultiple() == null) {
			inventoryExceptQuery.setMultiple(BigDecimal.ONE);
		}
		if (StringUtils.isEmpty(inventoryExceptQuery.getCompare())) {
			inventoryExceptQuery.setCompare("<=");
		}
		// 进价大于配送价
		for (int i = list.size() - 1; i >= 0; i--) {
			SingularPrice data = list.get(i);
			
			if (data.getItemLogPrice() == null) {
				list.remove(data);
				continue;
			}
			if (inventoryExceptQuery.getCompare().equals("<=")) {
				if (data.getItemTransfer().multiply(inventoryExceptQuery.getMultiple())
						.compareTo(data.getItemLogPrice()) > 0) {
					list.remove(data);
				}
			} else if (inventoryExceptQuery.getCompare().equals("<")) {
				if (data.getItemTransfer().multiply(inventoryExceptQuery.getMultiple())
						.compareTo(data.getItemLogPrice()) >= 0) {
					list.remove(data);
				}
			} else if (inventoryExceptQuery.getCompare().equals(">=")) {
				if (data.getItemTransfer().multiply(inventoryExceptQuery.getMultiple())
						.compareTo(data.getItemLogPrice()) < 0) {
					list.remove(data);
				}
			} else if (inventoryExceptQuery.getCompare().equals(">")) {
				if (data.getItemTransfer().multiply(inventoryExceptQuery.getMultiple())
						.compareTo(data.getItemLogPrice()) <= 0) {
					list.remove(data);
				}
			}
		}
		objects = inventoryDao.findItemAmount(systemBookCode, branchNum, null);
		for (int i = list.size() - 1; i >= 0; i--) {
			SingularPrice data = list.get(i);
			Integer itemNum = data.getItemNum();
			BigDecimal stockAmount = readItemAmount(objects, itemNum);
			BigDecimal rate = data.getRate();
			if (rate.compareTo(BigDecimal.ZERO) != 0) {
				stockAmount = stockAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
			}
			data.setInventoryUseAmount(stockAmount);
		}
		// 过滤供应商
		if (inventoryExceptQuery.getSupplierNums() != null && inventoryExceptQuery.getSupplierNums().size() != 0) {
			List<StoreItemSupplier> storeItemSuppliers = storeItemSupplierDao.find(systemBookCode, branchNum,
					inventoryExceptQuery.getSupplierNums(), true, null);
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < storeItemSuppliers.size(); i++) {
				itemNums.add(storeItemSuppliers.get(i).getId().getItemNum());
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				SingularPrice data = list.get(i);
				if (!itemNums.contains(data.getItemNum())) {
					list.remove(i);
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public List<Object[]> findBizAndMoney(String systemBookCode, Integer branchNum, String queryBy, String dateType,
			Date dateFrom, Date dateTo) {
		List<Object[]> objects = null;
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		if (queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)) {
			if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
				objects = reportDao.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, false);
			} else {
				objects = reportDao.findDayWholes(systemBookCode, branchNums, dateFrom, dateTo, false);
			}

		} else if (queryBy.equals(AppConstants.BUSINESS_TREND_DEPOSIT)) {
			objects = reportDao.findDepositBizMoneyByDateType(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		} else {
			objects = reportDao.findCardBizCountByDateType(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		}
		return objects;
	}

	@Override
	public List<UnsalablePosItem> findInventoryOverStock(UnsalableQuery unsalableQuery) {
		String systemBookCode = unsalableQuery.getSystemBookCode();
		Integer branchNum = unsalableQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		List<String> categoryCodes = unsalableQuery.getCategoryCodeList();
		Date dateTo = Calendar.getInstance().getTime();
		Date dateFrom = DateUtil.addDay(dateTo, -unsalableQuery.getDays());
		BigDecimal param = unsalableQuery.getItemAmount(); // 这里用作倍数
		String unit = unsalableQuery.getUnit();

		Map<Integer, UnsalablePosItem> map = new HashMap<Integer, UnsalablePosItem>();
		List<Object[]> inventoryObjects = inventoryDao.findInventory(systemBookCode, branchNum, null, null);
		for (int i = 0; i < inventoryObjects.size(); i++) {
			UnsalablePosItem data = new UnsalablePosItem();
			Object[] object = inventoryObjects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = (BigDecimal) object[1];
			data.setItemNum(itemNum);
			data.setCurrentStore(amount);
			map.put(itemNum, data);
		}
		// 销售数量 金额 毛利
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		List<Object[]> posOrderObjects = posOrderDao.findItemSumByCategory(systemBookCode, branchNums, dateFrom,
				dateTo, null, true, null);
		for (int i = 0; i < posOrderObjects.size(); i++) {
			Object[] object = posOrderObjects.get(i);
			Integer itemNum = (Integer) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal profit = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			UnsalablePosItem data = map.get(itemNum);
			if (data == null) {
				data = new UnsalablePosItem();
				data.setItemNum(itemNum);
				map.put(itemNum, data);
			}
			data.setCurrentSaleNum(data.getCurrentSaleNum().add(amount));
			data.setCurrentSaleMoney(data.getCurrentSaleMoney().add(money));
			data.setCurrentSaleProfit(data.getCurrentSaleProfit().add(profit));

		}

		// 调出量
		List<Object[]> outObjects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, null,
				dateFrom, dateTo, null, null);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = (BigDecimal) object[2];
			BigDecimal money = (BigDecimal) object[3];
			BigDecimal cost = (BigDecimal) object[4];
			BigDecimal profit = money.subtract(cost);
			UnsalablePosItem data = map.get(itemNum);
			if (data == null) {
				data = new UnsalablePosItem();
				data.setItemNum(itemNum);
				map.put(itemNum, data);
			}
			data.setCurrentOutNum(data.getCurrentSaleNum().add(amount));
			data.setCurrentOutMoney(data.getCurrentSaleMoney().add(money));
			data.setCurrentOutProfit(data.getCurrentSaleProfit().add(profit));

		}

		// 批发调出量
		List<Object[]> wholesaleObjects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo,
				null, null);
		for (int i = 0; i < wholesaleObjects.size(); i++) {
			Object[] object = wholesaleObjects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = (BigDecimal) object[2];
			BigDecimal money = (BigDecimal) object[3];
			BigDecimal cost = (BigDecimal) object[4];
			BigDecimal profit = money.subtract(cost);
			UnsalablePosItem data = map.get(itemNum);
			if (data == null) {
				data = new UnsalablePosItem();
				data.setItemNum(itemNum);
				map.put(itemNum, data);
			}
			data.setCurrentPifaNum(data.getCurrentSaleNum().add(amount));
			data.setCurrentPifaMoney(data.getCurrentSaleMoney().add(money));
			data.setCurrentPifaProfit(data.getCurrentSaleProfit().add(profit));

		}

		Branch branch = branchService.readInCache(systemBookCode, branchNum);
		List<String> branchItemRecoredTypes = new ArrayList<String>();
		if (branch.getBranchRdc() != null && branch.getBranchRdc()) {
			branchItemRecoredTypes.add(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
		} else {
			branchItemRecoredTypes.add(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
			branchItemRecoredTypes.add(AppConstants.POS_ITEM_LOG_IN_ORDER);
		}
		List<Object[]> inDateObjects = branchItemRecoredDao.findItemAuditDate(systemBookCode, branchNum, null, null,
				branchItemRecoredTypes);
		for (int i = 0; i < inDateObjects.size(); i++) {
			Object[] object = inDateObjects.get(i);
			Integer itemNum = (Integer) object[0];
			Date inDate = DateUtil.getDateStr((String) object[1]);

			UnsalablePosItem data = map.get(itemNum);
			if (data == null) {
				data = new UnsalablePosItem();
				data.setItemNum(itemNum);
				map.put(itemNum, data);
			}
			data.setLastestInDate(inDate);

		}

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<UnsalablePosItem> list = new ArrayList<UnsalablePosItem>(map.values());
		Date now = Calendar.getInstance().getTime();
		for (int i = list.size() - 1; i >= 0; i--) {
			UnsalablePosItem unsalablePosItem = list.get(i);
			BigDecimal outAmount = unsalablePosItem.getCurrentOutNum().add(unsalablePosItem.getCurrentSaleNum())
					.add(unsalablePosItem.getCurrentPifaNum());
			if(param != null){
				if (outAmount.multiply(param).compareTo(unsalablePosItem.getCurrentStore()) > 0) {
					list.remove(i);
					continue;
				}
				
			}
			PosItem posItem = AppUtil.getPosItem(unsalablePosItem.getItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				list.remove(i);
				continue;
			}
			unsalablePosItem.setPrice(posItem.getItemRegularPrice());
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (unsalableQuery.isFilterStopSale() != null && unsalableQuery.isFilterStopSale()) {
				if (posItem.getItemSaleCeaseFlag() != null && posItem.getItemSaleCeaseFlag()) {
					list.remove(i);
					continue;
				}
			}
			if (unsalableQuery.getIsFilterStopPurchase() != null && unsalableQuery.getIsFilterStopPurchase()) {
				if (posItem.getItemStockCeaseFlag() != null && posItem.getItemStockCeaseFlag()) {
					list.remove(i);
					continue;
				}
			}

			// AMA-8671
			if (unsalableQuery.getItemTransferDayMultiple() != null) {
				if (unsalablePosItem.getLastestInDate() == null) {
					list.remove(i);
					continue;
				}
				if (posItem.getItemTransferDay() == null) {
					list.remove(i);
					continue;
				}
				if (DateUtil.diffDay(unsalablePosItem.getLastestInDate(), now) < unsalableQuery
						.getItemTransferDayMultiple() * posItem.getItemTransferDay()) {
					list.remove(i);
					continue;
				}
			}

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
				unsalablePosItem.setPosItemUnit(posItem.getItemInventoryUnit());

			} else if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
				unsalablePosItem.setPosItemUnit(posItem.getItemTransferUnit());

			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
				unsalablePosItem.setPosItemUnit(posItem.getItemPurchaseUnit());

			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
				unsalablePosItem.setPosItemUnit(posItem.getItemWholesaleUnit());
			} else {
				unsalablePosItem.setPosItemUnit(posItem.getItemUnit());
			}
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				unsalablePosItem.setCurrentSaleNum(unsalablePosItem.getCurrentSaleNum().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				unsalablePosItem.setCurrentStore(unsalablePosItem.getCurrentStore().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				unsalablePosItem.setCurrentPifaNum(unsalablePosItem.getCurrentPifaNum().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				unsalablePosItem.setCurrentOutNum(unsalablePosItem.getCurrentOutNum().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));

			}
			unsalablePosItem.setPosItemType(posItem.getItemCategory());
			unsalablePosItem.setPosItemTypeCode(posItem.getItemCategoryCode());
			unsalablePosItem.setPosItemCode(posItem.getItemCode());
			unsalablePosItem.setPosItemName(posItem.getItemName());
			unsalablePosItem.setSepc(posItem.getItemSpec());
			unsalablePosItem.setPrice(posItem.getItemRegularPrice());
		}
		return list;
	}

	@Override
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData retailDetailQueryData) {
		List<RetailDetail> retailDetails = reportDao.findRetailDetails(retailDetailQueryData);
		if (retailDetails.size() == 0) {
			return retailDetails;
		}
		List<Branch> branchs = branchService.findInCache(retailDetailQueryData.getSystemBookCode());
		List<Integer> itemNums = new ArrayList<Integer>();
		for (int i = retailDetails.size() - 1; i >= 0; i--) {
			itemNums.add(retailDetails.get(i).getItemNum());
		}
		List<PosItem> posItems = posItemDao.findByItemNums(itemNums);

		List<Integer> itemGradeNums = new ArrayList<Integer>();
		for (int i = retailDetails.size() - 1; i >= 0; i--) {
			RetailDetail retailDetail = retailDetails.get(i);
			PosItem posItem = AppUtil.getPosItem(retailDetail.getItemNum(), posItems);
			if (posItem == null) {
				retailDetails.remove(i);
				continue;
			}
			retailDetail.setPosItemCode(posItem.getItemCode());
			retailDetail.setPosItemName(posItem.getItemName());
			retailDetail.setPosItemSpec(posItem.getItemSpec());
			retailDetail.setItemUnit(posItem.getItemUnit());
			if (retailDetail.getItemMatrixNum() != null && retailDetail.getItemMatrixNum() != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), retailDetail.getItemNum(),
						retailDetail.getItemMatrixNum());
				if (itemMatrix != null) {
					retailDetail
							.setPosItemName(retailDetail.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			Branch branch = AppUtil.getBranch(branchs, retailDetail.getBranchNum());
			if (branch == null) {
				retailDetails.remove(i);
				continue;
			}
			retailDetail.setBranchName(branch.getBranchName());

			if (retailDetail.getItemGradeNum() != null && !itemGradeNums.contains(retailDetail.getItemGradeNum())) {
				itemGradeNums.add(retailDetail.getItemGradeNum());
			}
		}
		if (itemGradeNums.size() > 0) {
			List<PosItemGrade> posItemGrades = posItemGradeDao.findByIds(itemGradeNums);
			for (int i = 0; i < retailDetails.size(); i++) {
				RetailDetail retailDetail = retailDetails.get(i);
				if (retailDetail.getItemGradeNum() == null) {
					continue;
				}
				PosItemGrade posItemGrade = AppUtil.getPosItemGrade(posItemGrades, retailDetail.getItemGradeNum());
				if (posItemGrade == null) {
					continue;
				}
				retailDetail.setPosItemName(retailDetail.getPosItemName().concat(
						"(" + posItemGrade.getItemGradeName() + ")"));
			}

		}
		return retailDetails;
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		String systemBookCode = purchaseOrderCollectQuery.getSystemBookCode();
		List<Integer> branchNums = new ArrayList<Integer>();
		Integer branchNum = purchaseOrderCollectQuery.getBranchNum();
		if (branchNum != null) {
			branchNums.add(branchNum);
		} else {
			branchNums.addAll(purchaseOrderCollectQuery.getBranchNums());
		}
		List<Integer> itemNums = purchaseOrderCollectQuery.getItemNums();
		List<String> itemCategoryCodes = purchaseOrderCollectQuery.getItemCategoryCodes();
		List<Integer> supplierNums = purchaseOrderCollectQuery.getSupplierNums();
		String unit = purchaseOrderCollectQuery.getUnitType();
		Date dateFrom = purchaseOrderCollectQuery.getDtFrom();
		Date dateTo = purchaseOrderCollectQuery.getDtTo();
		String operator = purchaseOrderCollectQuery.getOperator();
		Integer storehouseNum = purchaseOrderCollectQuery.getStorehouseNum();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<PurchaseOrderCollect> list = new ArrayList<PurchaseOrderCollect>();
		List<Object[]> receiveObjects = receiveOrderDao.findQueryDetails(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		
		List<String> departments = null;
		if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemDepartment())){
			String[] array = purchaseOrderCollectQuery.getItemDepartment().split(",");
			departments = Arrays.asList(array);
		}
		for (int i = 0; i < receiveObjects.size(); i++) {
			Object[] object = receiveObjects.get(i);
			PurchaseOrderCollect data = new PurchaseOrderCollect();
			data.setPurchaseOrderNo((String) object[0]);
			Supplier supplier = (Supplier) object[1];
			data.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
			data.setOperateDate((Date) object[2]);
			data.setItemNum((Integer) object[3]);
			data.setPurchaseItemAmount((BigDecimal) object[4]);
			data.setPurchaseItemPrice((BigDecimal) object[5]);
			data.setPurchaseItemMoney((BigDecimal) object[6]);
			data.setPurchasePresentAmount(object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]);
			BigDecimal presentCount = (BigDecimal) object[9];
			data.setProduceDate((Date) object[10]);
			BigDecimal cost = (BigDecimal) object[11];
			String itemName = (String) object[12];
			Integer itemMatrixNum = object[13] == null ? 0 : (Integer) object[13];
			data.setReceiveDate((Date) object[14]);
			data.setReceiveMemo((String) object[15]);
			data.setAveragePrice(cost);
			if (purchaseOrderCollectQuery.getItemMatrix() != null
					&& !purchaseOrderCollectQuery.getItemMatrix().equals(itemMatrixNum)) {
				continue;
			}
			data.setPurchasePresentMoney(presentCount.multiply(cost));
			data.setBranchNum((Integer) object[20]);
			data.setOperator((String) object[24]);
			data.setReceiveSaleMoney(AppUtil.getValue(object[26], BigDecimal.class).multiply(AppUtil.getValue(object[4], BigDecimal.class)));
			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			if (posItem == null) {
				continue;
			}
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			data.setItemCode(posItem.getItemCode());
			data.setItemName(itemName);
			data.setItemSpec(posItem.getItemSpec());
			data.setBaseUnit(posItem.getItemUnit());
			data.setBasePresentAmount(presentCount);
			data.setBaseAmount(data.getPurchaseItemAmount());
			data.setOverDate(data.getProduceDate());
			data.setItemCategory(posItem.getItemCategory());
			data.setItemCategoryCode(posItem.getItemCategoryCode());
			Integer validDay = posItem.getItemValidPeriod();
			if (validDay != null && data.getOverDate() != null) {
				data.setOverDate(DateUtil.addDay(data.getOverDate(), validDay));
			}

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				data.setPurchaseItemUnit(posItem.getItemUnit());
				data.setPurchasePresentUnit(posItem.getItemUnit());
				rate = BigDecimal.ONE;
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				data.setPurchaseItemUnit(posItem.getItemInventoryUnit());
				data.setPurchasePresentUnit(posItem.getItemInventoryUnit());
				rate = posItem.getItemInventoryRate();

			}
			if (unit.equals(AppConstants.UNIT_TRANFER)) {
				data.setPurchaseItemUnit(posItem.getItemTransferUnit());
				data.setPurchasePresentUnit(posItem.getItemTransferUnit());
				rate = posItem.getItemTransferRate();

			}
			if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				data.setPurchaseItemUnit(posItem.getItemPurchaseUnit());
				data.setPurchasePresentUnit(posItem.getItemPurchaseUnit());
				rate = posItem.getItemPurchaseRate();

			}
			if (unit.equals(AppConstants.UNIT_PIFA)) {
				data.setPurchaseItemUnit(posItem.getItemWholesaleUnit());
				data.setPurchasePresentUnit(posItem.getItemWholesaleUnit());
				rate = posItem.getItemWholesaleRate();

			}
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				data.setPurchaseItemAmount(data.getPurchaseItemAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				data.setPurchasePresentAmount(presentCount.divide(rate, 4, BigDecimal.ROUND_HALF_UP)); // 赠品常用数量
			}
			data.setPurchaseUseRate(rate);
			if (unit.equals(AppConstants.UNIT_USE)) {
				data.setPurchaseItemAmount((BigDecimal) object[16]);
				data.setPurchasePresentAmount(object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]);
				data.setPurchasePresentUnit((String) object[17]);
				data.setPurchaseItemUnit((String) object[18]);
				data.setPurchaseUseRate((BigDecimal) object[25]);
				
				
			}
			data.setReceiveOrderDetailOtherMoney(object[19] == null ? BigDecimal.ZERO : (BigDecimal) object[19]);
			if (data.getReceiveOrderDetailOtherMoney().compareTo(BigDecimal.ZERO) > 0) {
				data.setReceiveOrderDetailOtherQty((BigDecimal) object[16]);
			}
			data.setReceiveOrderDetailSupplierMoney(object[21] == null ? BigDecimal.ZERO : (BigDecimal) object[21]);
			ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(), itemMatrixNum);
			if (itemMatrix != null) {
				data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
			}
			list.add(data);
		}

		List<Object[]> returnObjects = returnOrderDao.findQueryDetails(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			PurchaseOrderCollect data = new PurchaseOrderCollect();
			data.setPurchaseOrderNo((String) object[0]);
			Supplier supplier = (Supplier) object[1];
			data.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
			data.setOperateDate((Date) object[2]);
			data.setItemNum((Integer) object[3]);
			data.setPurchaseItemReturnAmount((BigDecimal) object[4]);
			data.setPurchaseItemReturnPrice((BigDecimal) object[5]);
			data.setPurchaseItemReturnMoney((BigDecimal) object[6]);
			BigDecimal presentUseCount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			BigDecimal presentCount = object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9];
			data.setProduceDate((Date) object[10]);
			BigDecimal cost = (BigDecimal) object[11];
			String itemName = (String) object[12];
			Integer itemMatrixNum = object[13] == null ? 0 : (Integer) object[13];
			data.setReceiveDate((Date) object[15]);
			data.setReceiveMemo((String) object[16]);
			data.setBranchNum((Integer) object[19]);
			data.setOperator((String) object[20]);
			data.setAveragePrice(cost);
			if (purchaseOrderCollectQuery.getItemMatrix() != null
					&& !purchaseOrderCollectQuery.getItemMatrix().equals(itemMatrixNum)) {
				continue;
			}
			data.setPurchasePresentMoney(presentCount.negate().multiply(cost));
			data.setPurchasePresentAmount(presentUseCount.negate());

			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			data.setItemCode(posItem.getItemCode());
			data.setItemName(itemName);
			data.setItemSpec(posItem.getItemSpec());
			data.setBaseUnit(posItem.getItemUnit());
			data.setBaseAmount(data.getPurchaseItemReturnAmount());
			data.setBasePresentAmount(presentCount.negate());
			data.setItemCategory(posItem.getItemCategory());
			data.setItemCategoryCode(posItem.getItemCategoryCode());

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				data.setPurchaseItemUnit(posItem.getItemUnit());
				data.setPurchasePresentUnit(posItem.getItemUnit());
				rate = BigDecimal.ONE;
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				data.setPurchaseItemUnit(posItem.getItemInventoryUnit());
				data.setPurchasePresentUnit(posItem.getItemInventoryUnit());
				rate = posItem.getItemInventoryRate();

			}
			if (unit.equals(AppConstants.UNIT_TRANFER)) {
				data.setPurchaseItemUnit(posItem.getItemTransferUnit());
				data.setPurchasePresentUnit(posItem.getItemTransferUnit());
				rate = posItem.getItemTransferRate();

			}
			if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				data.setPurchaseItemUnit(posItem.getItemPurchaseUnit());
				data.setPurchasePresentUnit(posItem.getItemPurchaseUnit());
				rate = posItem.getItemPurchaseRate();

			}
			if (unit.equals(AppConstants.UNIT_PIFA)) {
				data.setPurchaseItemUnit(posItem.getItemWholesaleUnit());
				data.setPurchasePresentUnit(posItem.getItemWholesaleUnit());
				rate = posItem.getItemWholesaleRate();

			}
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				data.setPurchaseItemReturnAmount(data.getPurchaseItemReturnAmount().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				data.setPurchasePresentAmount(presentCount.negate().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
			}
			data.setPurchaseUseRate(rate);
			
			if (unit.equals(AppConstants.UNIT_USE)) {
				data.setPurchaseItemReturnAmount((BigDecimal) object[14]);
				data.setPurchasePresentAmount(presentUseCount.negate());
				data.setPurchasePresentUnit((String) object[17]);
				data.setPurchaseItemUnit((String) object[18]);
				data.setPurchaseUseRate((BigDecimal) object[21]);
				
			}
			ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(), itemMatrixNum);
			if (itemMatrix != null) {
				data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
			}
			list.add(data);
		}
		return list;
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseItem(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		String systemBookCode = purchaseOrderCollectQuery.getSystemBookCode();
		List<Integer> branchNums = new ArrayList<Integer>();
		Integer branchNum = purchaseOrderCollectQuery.getBranchNum();
		if (branchNum != null) {
			branchNums.add(branchNum);
		} else {
			branchNums.addAll(purchaseOrderCollectQuery.getBranchNums());
		}
		List<Integer> itemNums = purchaseOrderCollectQuery.getItemNums();
		List<String> itemCategoryCodes = purchaseOrderCollectQuery.getItemCategoryCodes();
		List<Integer> supplierNums = purchaseOrderCollectQuery.getSupplierNums();
		String unit = purchaseOrderCollectQuery.getUnitType();
		Date dateFrom = purchaseOrderCollectQuery.getDtFrom();
		Date dateTo = purchaseOrderCollectQuery.getDtTo();
		String operator = purchaseOrderCollectQuery.getOperator();
		Integer storehouseNum = purchaseOrderCollectQuery.getStorehouseNum();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Map<String, PurchaseOrderCollect> map = new HashMap<String, PurchaseOrderCollect>();
		List<Object[]> receiveObjects = receiveOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		

		List<String> departments = null;
		if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemDepartment())){
			String[] array = purchaseOrderCollectQuery.getItemDepartment().split(",");
			departments = Arrays.asList(array);
		}
		
		for (int i = 0; i < receiveObjects.size(); i++) {
			Object[] object = receiveObjects.get(i);
			Integer itemNum = (Integer) object[1];
			Integer itemMatrixNum = (Integer) object[2];
			if (itemMatrixNum == null) {
				itemMatrixNum = 0;
			}
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			BigDecimal supplierMoney = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			Integer queryBranchNum = (Integer) object[11];
			BigDecimal saleMoney = AppUtil.getValue(object[13], BigDecimal.class);
			PurchaseOrderCollect data = map.get(queryBranchNum + "|" + itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setBranchNum(queryBranchNum);
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setReceiveOrderDetailSupplierMoney(BigDecimal.ZERO);
				data.setReceiveSaleMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
				if (posItem == null) {
					continue;
				}
				if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
					if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
						continue;
					}
					
				}
				if(departments != null && !departments.contains(posItem.getItemDepartment())){
					continue;
										
				}
				data.setBaseUnit(posItem.getItemUnit());
				data.setItemCode(posItem.getItemCode());
				data.setItemName(posItem.getItemName());
				data.setItemSpec(posItem.getItemSpec());
				if (data.getItemMatrixNum() > 0) {
					ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(),
							data.getItemMatrixNum());
					if (itemMatrix != null) {
						data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
					}
				}

				BigDecimal rate = BigDecimal.ZERO;
				if (unit.equals(AppConstants.UNIT_BASIC)) {
					data.setPurchaseItemUnit(posItem.getItemUnit());
					rate = BigDecimal.ONE;
				}
				if (unit.equals(AppConstants.UNIT_SOTRE)) {
					data.setPurchaseItemUnit(posItem.getItemInventoryUnit());
					rate = posItem.getItemInventoryRate();

				}
				if (unit.equals(AppConstants.UNIT_TRANFER)) {
					data.setPurchaseItemUnit(posItem.getItemTransferUnit());
					rate = posItem.getItemTransferRate();

				}
				if (unit.equals(AppConstants.UNIT_PURCHASE)) {
					data.setPurchaseItemUnit(posItem.getItemPurchaseUnit());
					rate = posItem.getItemPurchaseRate();

				}
				if (unit.equals(AppConstants.UNIT_PIFA)) {
					data.setPurchaseItemUnit(posItem.getItemWholesaleUnit());
					rate = posItem.getItemWholesaleRate();

				}
				data.setRate(rate);
				map.put(queryBranchNum + "|" + itemNum + "|" + itemMatrixNum, data);
			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (data.getRate().compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(data.getRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(data.getRate(), 4, BigDecimal.ROUND_HALF_UP);

			}
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}
			data.setPurchaseItemAmount(data.getPurchaseItemAmount().add(amount));
			data.setPurchaseItemMoney(data.getPurchaseItemMoney().add(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().add(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().add(presentMoney));
			data.setReceiveOrderDetailOtherMoney(data.getReceiveOrderDetailOtherMoney().add(
					object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9]));
			data.setReceiveOrderDetailOtherQty(data.getReceiveOrderDetailOtherQty().add(
					object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10]));
			data.setReceiveOrderDetailSupplierMoney(data.getReceiveOrderDetailSupplierMoney().add(supplierMoney));
			data.setReceiveSaleMoney(data.getReceiveSaleMoney().add(saleMoney));
		}

		List<Object[]> returnObjects = returnOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator,storehouseNum);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			Integer itemNum = (Integer) object[1];
			Integer itemMatrixNum = (Integer) object[2];
			if (itemMatrixNum == null) {
				itemMatrixNum = 0;
			}
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			Integer queryBranchNum = (Integer) object[9];

			PurchaseOrderCollect data = map.get(queryBranchNum + "|" + itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setBranchNum(queryBranchNum);
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
				if (posItem == null) {
					continue;
				}
				if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
					if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
						continue;
					}
					
				}
				if(departments != null && !departments.contains(posItem.getItemDepartment())){
					continue;
										
				}
				data.setBaseUnit(posItem.getItemUnit());
				data.setItemCode(posItem.getItemCode());
				data.setItemName(posItem.getItemName());
				data.setItemSpec(posItem.getItemSpec());
				if (data.getItemMatrixNum() > 0) {
					ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(),
							data.getItemMatrixNum());
					if (itemMatrix != null) {
						data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
					}
				}

				BigDecimal rate = BigDecimal.ZERO;
				if (unit.equals(AppConstants.UNIT_BASIC)) {
					data.setPurchaseItemUnit(posItem.getItemUnit());
					rate = BigDecimal.ONE;
				}
				if (unit.equals(AppConstants.UNIT_SOTRE)) {
					data.setPurchaseItemUnit(posItem.getItemInventoryUnit());
					rate = posItem.getItemInventoryRate();

				}
				if (unit.equals(AppConstants.UNIT_TRANFER)) {
					data.setPurchaseItemUnit(posItem.getItemTransferUnit());
					rate = posItem.getItemTransferRate();

				}
				if (unit.equals(AppConstants.UNIT_PURCHASE)) {
					data.setPurchaseItemUnit(posItem.getItemPurchaseUnit());
					rate = posItem.getItemPurchaseRate();

				}
				if (unit.equals(AppConstants.UNIT_PIFA)) {
					data.setPurchaseItemUnit(posItem.getItemWholesaleUnit());
					rate = posItem.getItemWholesaleRate();

				}
				data.setRate(rate);
				map.put(queryBranchNum + "|" + itemNum + "|" + itemMatrixNum, data);
			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (data.getRate().compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(data.getRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(data.getRate(), 4, BigDecimal.ROUND_HALF_UP);

			}
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}
			data.setPurchaseItemReturnAmount(data.getPurchaseItemReturnAmount().add(amount));
			data.setPurchaseItemReturnMoney(data.getPurchaseItemReturnMoney().add(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().subtract(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().subtract(presentMoney));
		}

		List<PurchaseOrderCollect> list = new ArrayList<PurchaseOrderCollect>(map.values());
		for (int i = 0; i < list.size(); i++) {
			PurchaseOrderCollect data = list.get(i);
			data.setAmountTotal(data.getPurchaseItemAmount().subtract(data.getPurchaseItemReturnAmount()));
			data.setMoneyTotal(data.getPurchaseItemMoney().subtract(data.getPurchaseItemReturnMoney()));
		}
		return list;
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseSupplier(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		String systemBookCode = purchaseOrderCollectQuery.getSystemBookCode();
		List<Integer> branchNums = new ArrayList<Integer>();
		Integer branchNum = purchaseOrderCollectQuery.getBranchNum();
		if (branchNum != null) {
			branchNums.add(branchNum);
		} else {
			branchNums.addAll(purchaseOrderCollectQuery.getBranchNums());
		}
		List<Integer> itemNums = purchaseOrderCollectQuery.getItemNums();
		List<String> itemCategoryCodes = purchaseOrderCollectQuery.getItemCategoryCodes();
		List<Integer> supplierNums = purchaseOrderCollectQuery.getSupplierNums();
		String unit = purchaseOrderCollectQuery.getUnitType();
		Date dateFrom = purchaseOrderCollectQuery.getDtFrom();
		Date dateTo = purchaseOrderCollectQuery.getDtTo();
		String operator = purchaseOrderCollectQuery.getOperator();
		Integer storehouseNum = purchaseOrderCollectQuery.getStorehouseNum();


		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Map<Integer, PurchaseOrderCollect> map = new HashMap<Integer, PurchaseOrderCollect>();
		List<Object[]> receiveObjects = receiveOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		List<String> departments = null;
		if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemDepartment())){
			String[] array = purchaseOrderCollectQuery.getItemDepartment().split(",");
			departments = Arrays.asList(array);
		}
		for (int i = 0; i < receiveObjects.size(); i++) {
			Object[] object = receiveObjects.get(i);
			Supplier supplier = (Supplier) object[0];
			Integer itemNum = (Integer) object[1];
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			BigDecimal supplierMoney = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			PurchaseOrderCollect data = map.get(supplier.getSupplierNum());
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
				data.setSupplierNum(supplier.getSupplierNum());
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setReceiveOrderDetailSupplierMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				map.put(supplier.getSupplierNum(), data);
			}

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				data.setPurchaseItemUnit(posItem.getItemUnit());
				rate = BigDecimal.ONE;
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();

			}
			if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();

			}
			if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();

			}
			if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();

			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
			}
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}
			data.setPurchaseItemAmount(data.getPurchaseItemAmount().add(amount));
			data.setPurchaseItemMoney(data.getPurchaseItemMoney().add(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().add(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().add(presentMoney));
			data.setReceiveOrderDetailOtherMoney(data.getReceiveOrderDetailOtherMoney().add(
					object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9]));
			data.setReceiveOrderDetailOtherQty(data.getReceiveOrderDetailOtherQty().add(
					object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10]));
			data.setReceiveOrderDetailSupplierMoney(data.getReceiveOrderDetailSupplierMoney().add(supplierMoney));
			data.setAmountTotal(data.getAmountTotal().add(amount));
			data.setMoneyTotal(data.getMoneyTotal().add(money));
		}

		List<Object[]> returnObjects = returnOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			Supplier supplier = (Supplier) object[0];
			Integer itemNum = (Integer) object[1];
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			PurchaseOrderCollect data = map.get(supplier.getSupplierNum());
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setSupplierName(supplier.getSupplierCode() + "|" + supplier.getSupplierName());
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				map.put(supplier.getSupplierNum(), data);
			}

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				data.setPurchaseItemUnit(posItem.getItemUnit());
				rate = BigDecimal.ONE;
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();

			}
			if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();

			}
			if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();

			}
			if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();

			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
			}
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}
			data.setPurchaseItemReturnAmount(data.getPurchaseItemReturnAmount().add(amount));
			data.setPurchaseItemReturnMoney(data.getPurchaseItemReturnMoney().add(money));
			data.setAmountTotal(data.getAmountTotal().subtract(amount));
			data.setMoneyTotal(data.getMoneyTotal().subtract(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().subtract(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().subtract(presentMoney));
		}
		List<PurchaseOrderCollect> list = new ArrayList<PurchaseOrderCollect>(map.values());
		return list;
	}

	@Override
	public List<Object[]> findProfitAnalysisDays(ProfitAnalysisQueryData profitAnalysisQueryData) {
		if(profitAnalysisQueryData.getIsQueryCF() == null){
			profitAnalysisQueryData.setIsQueryCF(true);
		}
		return reportDao.findProfitAnalysisDays(profitAnalysisQueryData);
	}

	@Override
	public List<Object[]> findProfitAnalysisByClientAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		profitAnalysisQueryData.setIsQueryCF(false);
		List<Object[]> objects = reportDao.findProfitAnalysisByClientAndItem(profitAnalysisQueryData);
		return objects;
	}

	@Override
	public List<Object[]> findProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		List<Object[]> objects = reportDao.findProfitAnalysisByBranchAndItem(profitAnalysisQueryData);
		if (profitAnalysisQueryData.getIsQueryCF()) {
			List<Object[]> kitObjects = reportDao.findKitProfitAnalysisByBranchAndItem(profitAnalysisQueryData);
			objects.addAll(kitObjects);
		}
		return objects;
	}

	@Override
	public List<SalerCommissionBrand> findSalerCommissionBrands(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, List<String> salerNums) {
		List<SalerCommissionBrand> salerCommissionBrands = reportDao.findSalerCommissionBrands(systemBookCode, dtFrom,
				dtTo, branchNums, salerNums);
		if (salerCommissionBrands.size() > 0) {
			List<Branch> branchs = branchService.findInCache(systemBookCode);
			for (int i = 0; i < salerCommissionBrands.size(); i++) {
				SalerCommissionBrand salerCommissionBrand = salerCommissionBrands.get(i);

				Branch branch = AppUtil.getBranch(branchs, salerCommissionBrand.getBranchNum());
				if (branch != null) {
					salerCommissionBrand.setBranchName(branch.getBranchName());
				}
			}
		}
		return salerCommissionBrands;
	}

	@Override
	public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, List<String> salerNums, BigDecimal interval) {
		List<SalerCommission> salerCommissions = reportDao.findSalerCommissions(systemBookCode, dtFrom, dtTo,
				branchNums, salerNums);
		if (interval != null) {
			List<Object[]> salerRanks = reportDao.findSalerCommissionsByMoney(systemBookCode, dtFrom, dtTo, branchNums,
					salerNums, interval);
			for (int i = 0; i < salerRanks.size(); i++) {
				Object[] objects = salerRanks.get(i);
				SalerCommission salerCommission = getSalerCommission(salerCommissions, (String) objects[0]);
				if (salerCommission == null) {
					continue;
				}
				int rank = Integer.parseInt((String) objects[1]) + 1;
				if (rank > 9) {
					rank = 9;
				}
				Integer count = objects[2] == null ? 0 : (Integer) objects[2];
				Integer data = salerCommission.getRank().get(rank);
				if (data == null) {
					salerCommission.getRank().set(rank, count);
				} else {
					salerCommission.getRank().set(rank, data + count);
				}
			}
		}/////
		if (salerCommissions.size() > 0) {
			List<Branch> branchs = branchService.findInCache(systemBookCode);
			for (int i = 0; i < salerCommissions.size(); i++) {
				SalerCommission salerCommission = salerCommissions.get(i);

				Branch branch = AppUtil.getBranch(branchs, salerCommission.getBranchNum());
				if (branch != null) {
					salerCommission.setBranchName(branch.getBranchName());
				}
			}
		}
		return salerCommissions;
	}

	private SalerCommission getSalerCommission(List<SalerCommission> list, String saler) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getSaler().equals(saler)) {
				return list.get(i);
			}
		}
		return null;
	}

	@Override
	public List<SalerCommissionCard> findSalerCommissionCards(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, List<String> salerNames) {
		List<SalerCommissionCard> list = new ArrayList<SalerCommissionCard>();
		List<Object[]> cardUserList = cardUserRegisterDao.findSalerSummary(systemBookCode, branchNums, dateFrom,
				dateTo, salerNames);
		for (int i = 0; i < cardUserList.size(); i++) {
			Object[] objects = cardUserList.get(i);
			SalerCommissionCard salerCommissionCard = getSalerCommissionCard(list, (String) objects[0],
					(Integer) objects[1]);
			salerCommissionCard.setCardUserCount(objects[2] == null ? 0 : (Integer) objects[2]);
		}
		List<Object[]> cardDepositList = cardDepositDao.findSalerSummary(systemBookCode, branchNums, dateFrom, dateTo,
				salerNames);
		for (int i = 0; i < cardDepositList.size(); i++) {
			Object[] objects = cardDepositList.get(i);
			SalerCommissionCard salerCommissionCard = getSalerCommissionCard(list, (String) objects[0],
					(Integer) objects[1]);
			salerCommissionCard.setCardDepositCashSum(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2]);
			salerCommissionCard.setCardDepositMoneySum(objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]);
		}
		List<Object[]> cardConsumeList = cardConsumeDao.findSalerSummary(systemBookCode, branchNums, dateFrom, dateTo,
				salerNames);
		for (int i = 0; i < cardConsumeList.size(); i++) {
			Object[] objects = cardConsumeList.get(i);
			SalerCommissionCard salerCommissionCard = getSalerCommissionCard(list, (String) objects[0],
					(Integer) objects[1]);
			salerCommissionCard.setCardConsumeMoneySum(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2]);
		}
		List<Branch> branches = branchService.findInCache(systemBookCode);
		for (int i = 0; i < list.size(); i++) {
			SalerCommissionCard salerCommissionCard = list.get(i);
			if (salerCommissionCard.getCardUserCount() == null) {
				salerCommissionCard.setCardUserCount(new Integer(0));
			}
			if (salerCommissionCard.getCardDepositCashSum() == null) {
				salerCommissionCard.setCardDepositCashSum(BigDecimal.ZERO);
			}
			if (salerCommissionCard.getCardDepositMoneySum() == null) {
				salerCommissionCard.setCardDepositMoneySum(BigDecimal.ZERO);
			}
			if (salerCommissionCard.getCardConsumeMoneySum() == null) {
				salerCommissionCard.setCardConsumeMoneySum(BigDecimal.ZERO);
			}
			Branch branch = AppUtil.getBranch(branches, salerCommissionCard.getBranchNum());
			if (branch != null) {
				salerCommissionCard.setBranchName(branch.getBranchName());
			}
		}
		return list;
	}

	private SalerCommissionCard getSalerCommissionCard(List<SalerCommissionCard> list, String salerName,
			Integer branchNum) {
		if (salerName == null) {
			salerName = "";
		}
		for (SalerCommissionCard card : list) {
			if (card.getSaler().equals(salerName) && card.getBranchNum().compareTo(branchNum) == 0) {
				return card;
			}
		}
		SalerCommissionCard card = new SalerCommissionCard();
		card.setSaler(salerName);
		card.setBranchNum(branchNum);
		list.add(card);
		return card;
	}

	@Override
	public List<SalerCommissionDetail> findSalerCommissionDetails(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, List<String> salerNums) {

		List<SalerCommissionDetail> list = reportDao.findSalerCommissionDetails(systemBookCode, dtFrom, dtTo,
				branchNums, salerNums);
		if (list.size() > 0) {
			List<Integer> itemNums = new ArrayList<Integer>();
			for (int i = 0; i < list.size(); i++) {
				SalerCommissionDetail salerCommissionDetail = list.get(i);
				if (!itemNums.contains(salerCommissionDetail.getItemNum())) {
					itemNums.add(salerCommissionDetail.getItemNum());
				}
			}
			List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
			List<Branch> branchs = branchService.findInCache(systemBookCode);
			for (int i = 0; i < list.size(); i++) {
				SalerCommissionDetail salerCommissionDetail = list.get(i);
				Integer itemNum = salerCommissionDetail.getItemNum();
				PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
				if (posItem != null) {
					salerCommissionDetail.setCategoryCode(posItem.getItemCategoryCode());
					salerCommissionDetail.setCategoryName(posItem.getItemCategory());
					salerCommissionDetail.setItemCode(posItem.getItemCode());
					salerCommissionDetail.setItemName(posItem.getItemName());
					salerCommissionDetail.setSpec(posItem.getItemSpec());
				}
				Branch branch = AppUtil.getBranch(branchs, salerCommissionDetail.getBranchNum());
				if (branch != null) {
					salerCommissionDetail.setBranchName(branch.getBranchName());
				}
			}
		}
		return list;
	}

	@Override
	public List<CustomerAnalysisHistory> findCustomerAnalysisHistorys(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {

		List<Object[]> objects = reportDao.findCustomerAnalysisHistorys(systemBookCode, dtFrom, dtTo, branchNums,
				saleType);
		List<CustomerAnalysisHistory> list = new ArrayList<CustomerAnalysisHistory>();
		List<PosMachine> posMachines = posMachineDao.findByBranchs(systemBookCode, branchNums, null);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal mgrDiscount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];

			CustomerAnalysisHistory data = new CustomerAnalysisHistory();
			data.setBranchNum((Integer) object[0]);
			Branch branch = AppUtil.getBranch(branchs, data.getBranchNum());
			if (branch != null) {
				data.setBranchName(branch.getBranchName());
				data.setBranchRegionNum(branch.getBranchRegionNum());
			}

			data.setPosMachineTerminalId((String) object[1]);
			data.setShiftTableDate((String) object[2]);
			data.setTotalMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			data.setTotalMoney(data.getTotalMoney().add(couponMoney).subtract(mgrDiscount));
			data.setCustomerNums(BigDecimal.valueOf((Long) object[4]));
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}
			PosMachine posMachine = AppUtil.getPosMachine(posMachines, data.getPosMachineTerminalId());
			if (posMachine != null) {
				data.setPosMachineName(posMachine.getPosMachineName());
			} else {
				data.setPosMachineName("未知终端");
			}
			list.add(data);
		}
		return list;
	}

	@Override
	public List<CustomerAnalysisRange> findCustomerAnalysisRanges(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, Integer rangeFrom, Integer rangeTo, Integer space, String saleType) {
		List<CustomerAnalysisRange> list = new ArrayList<CustomerAnalysisRange>();
		if(rangeFrom == null || rangeTo == null){
			return list;
		}
		Integer begin = null;
		while (rangeFrom <= rangeTo) {
			CustomerAnalysisRange data = new CustomerAnalysisRange();
			Object[] objects = reportDao.findCustomerAnalysisRanges(systemBookCode, dtFrom, dtTo, branchNums, begin,
					rangeFrom, saleType);
			BigDecimal money = objects[0] == null ? BigDecimal.ZERO : (BigDecimal) objects[0];
			BigDecimal count = BigDecimal.valueOf((Long) objects[1]);
			BigDecimal couponMoney = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
			BigDecimal mgrDiscount = objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3];

			data.setTotalMoney(money.add(couponMoney).subtract(mgrDiscount));
			data.setCustomerNums(count);
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}

			if (begin == null) {
				begin = rangeFrom;
				data.setCustomerRange(rangeFrom + "以下");
			} else {

				data.setCustomerRange(begin + "-" + rangeFrom);
				begin = begin + space;
			}
			rangeFrom = rangeFrom + space;
			list.add(data);
		}

		CustomerAnalysisRange data = new CustomerAnalysisRange();
		Object[] objects = reportDao.findCustomerAnalysisRanges(systemBookCode, dtFrom, dtTo, branchNums, rangeTo,
				null, saleType);
		BigDecimal money = objects[0] == null ? BigDecimal.ZERO : (BigDecimal) objects[0];
		BigDecimal count = BigDecimal.valueOf((Long) objects[1]);
		BigDecimal couponMoney = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
		BigDecimal mgrDiscount = objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3];
		data.setTotalMoney(money.add(couponMoney).subtract(mgrDiscount));
		data.setCustomerNums(count);
		data.setCustomerAvePrice(BigDecimal.ZERO);
		if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
			data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4, BigDecimal.ROUND_HALF_UP));
		}
		data.setCustomerRange(rangeTo + "以上");
		list.add(data);
		return list;
	}

	@Override
	public List<CustomerAnalysisDay> findCustomerAnalysisDays(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {
		List<Object[]> objects = reportDao.findCustomerAnalysisDays(systemBookCode, dtFrom, dtTo, branchNums, saleType);
		List<CustomerAnalysisDay> list = new ArrayList<CustomerAnalysisDay>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal mgrDiscount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			CustomerAnalysisDay data = new CustomerAnalysisDay();
			data.setShiftTableDate((String) object[0]);
			data.setTotalMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			data.setTotalMoney(data.getTotalMoney().add(couponMoney).subtract(mgrDiscount));
			data.setCustomerNums(BigDecimal.valueOf((Long) object[2]));
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}
			list.add(data);
		}
		return list;

	}

	private Object[] getTimeObjects(List<Object[]> objects, Date timeFrom, Date timeTo) {
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Date time = (Date) object[0];
			if (DateUtil.betweenTime(time, timeFrom, timeTo)) {
				return object;
			}
		}
		return null;
	}

	@Override
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom,
			Date dateTo, List<Integer> branchNums, Integer space, List<Integer> itemNums, String saleType) {
		List<Object[]> objects = null;
		if (itemNums != null && itemNums.size() > 0) {
			objects = reportDao.findCustomerAnalysisTimePeriodsByItems(systemBookCode, dateFrom, dateTo, branchNums,
					space, itemNums, saleType);
		} else {
			objects = reportDao.findCustomerAnalysisTimePeriods(systemBookCode, dateFrom, dateTo, branchNums, space,
					saleType);
		}
		List<CustomerAnalysisTimePeriod> list = new ArrayList<CustomerAnalysisTimePeriod>();
		Date beginTime = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
		Date nextTime = DateUtil.addMinute(beginTime, space);
		nextTime = DateUtil.addSecond(nextTime, -1);
		Date endTime = DateUtil.getMaxOfDate(Calendar.getInstance().getTime());
		while (nextTime.compareTo(endTime) <= 0) {
			CustomerAnalysisTimePeriod data = new CustomerAnalysisTimePeriod();
			data.setTimePeroid(DateUtil.format("HH:mm", beginTime) + " - " + DateUtil.format("HH:mm", nextTime));
			Object[] object = getTimeObjects(objects, beginTime, nextTime);
			if (object == null) {
				data.setCustomerNums(BigDecimal.ZERO);
				data.setTotalMoney(BigDecimal.ZERO);
			} else {
				data.setTotalMoney((BigDecimal) object[1]);
				data.setCustomerNums(BigDecimal.valueOf((Integer) object[2]));
			}
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}

			list.add(data);
			beginTime = DateUtil.addMinute(beginTime, space);
			nextTime = DateUtil.addMinute(nextTime, space);
		}
		return list;
	}

	@Override
	public Object[] sumCustomerAnalysis(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums,
			String branchType) {
		return reportDao.sumCustomerAnalysis(systemBookCode, dtFrom, dtTo, branchNums, branchType);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByPosItems' + #p0.getKey()")
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByPosItems(SaleAnalysisQueryData saleAnalysisQueryData) {
		List<Object[]> objects = new ArrayList<Object[]>();

		SystemBook systemBook = systemBookService.readInCache(saleAnalysisQueryData.getSystemBookCode());
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);

		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;
		}
		if (saleAnalysisQueryData.getIsQueryCF() == null) {
			saleAnalysisQueryData.setIsQueryCF(false);
		}
		if (saleAnalysisQueryData.getIsQueryGrade() == null) {
			saleAnalysisQueryData.setIsQueryGrade(false);
		}
		if (saleAnalysisQueryData.getIsQueryCardUser() == null) {
			saleAnalysisQueryData.setIsQueryCardUser(false);
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		if (systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()
				&& StringUtils.isEmpty(saleAnalysisQueryData.getSaleType()) && !saleAnalysisQueryData.getIsQueryCF()
				&& !saleAnalysisQueryData.getIsQueryGrade() && !saleAnalysisQueryData.getIsQueryCardUser()) {
			Date dpcLimitTime = DateUtil.addDay(now, -2);

			if (dpcLimitTime.compareTo(dateFrom) <= 0) {
				objects = reportDao.findSaleAnalysisCommonItemMatrix(saleAnalysisQueryData);
			} else if (dpcLimitTime.compareTo(dateTo) > 0) {
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());
				orderQueryDTO.setItemNums(saleAnalysisQueryData.getPosItemNums());

				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemMatrixStateSummaryDetail(orderQueryDTO);
				Object[] object = null;
				for (int i = 0; i < list.size(); i++) {
					object = new Object[9];
					object[0] = list.get(i).getItemNum();
					object[1] = list.get(i).getItemMatrixNum();
					object[2] = list.get(i).getStateCode();
					object[3] = list.get(i).getAmount();
					object[4] = list.get(i).getPaymentMoney();
					object[5] = list.get(i).getAssistAmount();
					object[6] = list.get(i).getItemCount();
					object[7] = list.get(i).getDiscount();
					object[8] = list.get(i).getBranchCount();
					objects.add(object);
				}
			} else {

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setItemNums(saleAnalysisQueryData.getPosItemNums());
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
				if (saleAnalysisQueryData.getIsQueryCF() != null) {
					orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());

				}
				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemMatrixStateSummaryDetail(orderQueryDTO);
				Object[] object = null;
				for (int i = 0; i < list.size(); i++) {
					object = new Object[9];
					object[0] = list.get(i).getItemNum();
					object[1] = list.get(i).getItemMatrixNum();
					object[2] = list.get(i).getStateCode();
					object[3] = list.get(i).getAmount();
					object[4] = list.get(i).getPaymentMoney();
					object[5] = list.get(i).getAssistAmount();
					object[6] = list.get(i).getItemCount();
					object[7] = list.get(i).getDiscount();
					object[8] = list.get(i).getBranchCount() == null?0:list.get(i).getBranchCount();
					objects.add(object);
				}

				saleAnalysisQueryData.setDtFrom(dpcLimitTime);
				List<Object[]> localObjects = reportDao.findSaleAnalysisCommonItemMatrix(saleAnalysisQueryData);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);
					if (localObject[1] == null) {
						localObject[1] = 0;
					}
					if (localObject[8] == null) {
						localObject[8] = 0;
					}
					find = false;
					for (int j = 0; j < objects.size(); j++) {
						object = objects.get(j);
						if (object[0].equals(localObject[0]) && object[1].equals(localObject[1])
								&& object[2].equals(localObject[2])) {
							object[3] = ((BigDecimal) object[3]).add(localObject[3] == null ? BigDecimal.ZERO
									: (BigDecimal) localObject[3]);
							object[4] = ((BigDecimal) object[4]).add(localObject[4] == null ? BigDecimal.ZERO
									: (BigDecimal) localObject[4]);
							object[5] = ((BigDecimal) object[5]).add(localObject[5] == null ? BigDecimal.ZERO
									: (BigDecimal) localObject[5]);
							object[6] = ((Integer) object[6]) + (localObject[6] == null ? 0 : (Integer) localObject[6]);
							object[7] = ((BigDecimal) object[7]).add(localObject[7] == null ? BigDecimal.ZERO
									: (BigDecimal) localObject[7]);
							
							if((Integer) localObject[8] > (Integer) object[8]){
								object[8] = localObject[8];
							}
							find = true;
							break;
						}
					}

					if (!find) {
						objects.add(localObject);
					}
				}
			}
		} else {
			objects = reportDao.findSaleAnalysisCommonItemMatrix(saleAnalysisQueryData);

		}
		
		Map<String, SaleAnalysisByPosItemDTO> map = new HashMap<String, SaleAnalysisByPosItemDTO>();
		Integer itemNum;
		Integer itemMatrixNum;
		Integer stateCode;
		BigDecimal amount;
		BigDecimal money;
		BigDecimal assistAmount;
		BigDecimal count_;
		BigDecimal discount;
		Integer itemGradeNum;
		Integer saleBranchCount;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			itemNum = (Integer) object[0];
			itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			stateCode = (Integer) object[2];
			amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			count_ = BigDecimal.valueOf(object[6] == null ? 0 : (Integer) object[6]);
			if (object[7] instanceof BigDecimal) {
				discount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];

			} else if (object[7] instanceof Double) {
				discount = object[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Double) object[7]);
			} else {
				discount = BigDecimal.ZERO;
			}
			saleBranchCount = 0;
			if(object.length > 8){
				saleBranchCount = (Integer) object[8];
			}
			
			if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_REMOVE) {
				continue;
			}

			SaleAnalysisByPosItemDTO data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new SaleAnalysisByPosItemDTO();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_CANCEL)) {
				data.setTotalNum(data.getTotalNum().subtract(amount));
				data.setTotalMoney(data.getTotalMoney().subtract(money));
				data.setCountTotal(data.getCountTotal().subtract(count_));
				data.setReturnNum(data.getReturnNum().add(amount));
				data.setReturnMoney(data.getReturnMoney().add(money));
				data.setReturnAssist(data.getReturnAssist().add(assistAmount));
				data.setItemDiscount(data.getItemDiscount().subtract(discount));

			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_PRESENT)) {
				data.setPresentNum(data.getPresentNum().add(amount));
				data.setPresentMoney(data.getPresentMoney().add(money));
				data.setPresentAssist(data.getPresentAssist().add(assistAmount));
				data.setCountTotal(data.getCountTotal().add(count_));
				data.setTotalNum(data.getTotalNum().add(amount));
			}
			if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_SALE)) {
				data.setTotalNum(data.getTotalNum().add(amount));
				data.setTotalMoney(data.getTotalMoney().add(money));
				data.setCountTotal(data.getCountTotal().add(count_));
				data.setSaleNum(data.getSaleNum().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(money));
				data.setSaleAssist(data.getSaleAssist().add(assistAmount));
				data.setItemDiscount(data.getItemDiscount().add(discount));
				data.setSaleBranchCount(saleBranchCount);
			}

		}
		List<PosItemGrade> posItemGrades = new ArrayList<PosItemGrade>();
		if (saleAnalysisQueryData.getIsQueryGrade()) {
			objects = reportDao.findSaleAnalysisCommonItemGrade(saleAnalysisQueryData);
			List<Integer> gradeItemNums = new ArrayList<Integer>();
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				itemNum = (Integer) object[0];
				itemGradeNum = (Integer) object[1];
				stateCode = (Integer) object[2];
				amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
				count_ = BigDecimal.valueOf(object[6] == null ? 0 : (Integer) object[6]);
				discount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];

				if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_REMOVE) {
					continue;
				}

				SaleAnalysisByPosItemDTO data = map.get(itemNum + "|" + itemGradeNum);
				if (data == null) {
					data = new SaleAnalysisByPosItemDTO();
					data.setItemNum(itemNum);
					data.setItemGradeNum(itemGradeNum);
					data.setItemMatrixNum(0);
					map.put(itemNum + "|" + itemGradeNum, data);
				}
				if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_CANCEL)) {
					data.setTotalNum(data.getTotalNum().subtract(amount));
					data.setTotalMoney(data.getTotalMoney().subtract(money));
					data.setCountTotal(data.getCountTotal().subtract(count_));
					data.setReturnNum(data.getReturnNum().add(amount));
					data.setReturnMoney(data.getReturnMoney().add(money));
					data.setReturnAssist(data.getReturnAssist().add(assistAmount));
					data.setItemDiscount(data.getItemDiscount().subtract(discount));

				}
				if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_PRESENT)) {
					data.setPresentNum(data.getPresentNum().add(amount));
					data.setPresentMoney(data.getPresentMoney().add(money));
					data.setPresentAssist(data.getPresentAssist().add(assistAmount));
					data.setCountTotal(data.getCountTotal().add(count_));
					data.setTotalNum(data.getTotalNum().add(amount));
				}
				if (stateCode.equals(AppConstants.POS_ORDER_DETAIL_STATE_SALE)) {
					data.setTotalNum(data.getTotalNum().add(amount));
					data.setTotalMoney(data.getTotalMoney().add(money));
					data.setCountTotal(data.getCountTotal().add(count_));
					data.setSaleNum(data.getSaleNum().add(amount));
					data.setSaleMoney(data.getSaleMoney().add(money));
					data.setSaleAssist(data.getSaleAssist().add(assistAmount));
					data.setItemDiscount(data.getItemDiscount().add(discount));

				}
				if (!gradeItemNums.contains(itemNum)) {
					gradeItemNums.add(itemNum);
				}

			}
			if (gradeItemNums.size() > 0) {
				posItemGrades.addAll(posItemGradeDao.find(saleAnalysisQueryData.getSystemBookCode(), gradeItemNums));

			}
		}

		List<SaleAnalysisByPosItemDTO> list = new ArrayList<SaleAnalysisByPosItemDTO>(map.values());
		if (list.isEmpty()) {
			return list;
		}
		
		List<PosItem> posItems = posItemService.findShortItems(saleAnalysisQueryData.getSystemBookCode());
		
		List<ItemExtendAttribute> itemExtendAttributes = null;
		if((saleAnalysisQueryData.getQueryItemExtendAttribute() != null 
				&& saleAnalysisQueryData.getQueryItemExtendAttribute())
				
				|| (saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0)){
			itemExtendAttributes = itemExtendAttributeDao.find(saleAnalysisQueryData.getSystemBookCode());
		}
		
		PosItemGrade posItemGrade;
		List<ItemExtendAttribute> itemItemExtendAttributes = null;
		for (int i = list.size() - 1; i >= 0; i--) {
			SaleAnalysisByPosItemDTO data = list.get(i);

			Integer posItemNum = data.getItemNum();
			itemMatrixNum = data.getItemMatrixNum();
			itemGradeNum = data.getItemGradeNum();

			if(itemExtendAttributes != null){
				itemItemExtendAttributes = ItemExtendAttribute.find(itemExtendAttributes, posItemNum);
				if(saleAnalysisQueryData.getTwoStringValueDatas() != null && saleAnalysisQueryData.getTwoStringValueDatas().size() > 0 
						&& !ItemExtendAttribute.exists(itemItemExtendAttributes, saleAnalysisQueryData.getTwoStringValueDatas())){
					list.remove(i);
					continue;
				}
				
				if(saleAnalysisQueryData.getQueryItemExtendAttribute() != null && saleAnalysisQueryData.getQueryItemExtendAttribute()){
					data.setItemExtendAttributes(itemItemExtendAttributes);
				}
					
			}
			
			PosItem posItem = AppUtil.getPosItem(posItemNum, posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (saleAnalysisQueryData.getBrandCodes() != null && saleAnalysisQueryData.getBrandCodes().size() > 0) {
				if (!saleAnalysisQueryData.getBrandCodes().contains(posItem.getItemBrand())) {
					list.remove(i);
					continue;
				}
			}
			if (saleAnalysisQueryData.getPosItemTypeCodes() != null
					&& saleAnalysisQueryData.getPosItemTypeCodes().size() > 0) {
				if (!saleAnalysisQueryData.getPosItemTypeCodes().contains(posItem.getItemCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (StringUtils.isNotEmpty(saleAnalysisQueryData.getItemDepartments())) {
				if (!saleAnalysisQueryData.getItemDepartments().contains(posItem.getItemDepartment())) {
					list.remove(i);
					continue;
				}
			}
			data.setItemName(posItem.getItemName());
			data.setItemCode(posItem.getItemCode());
			data.setSpec(posItem.getItemSpec());
			data.setUnit(posItem.getItemUnit());
			data.setCategoryName(posItem.getItemCategory());
			data.setCategoryCode(posItem.getItemCategoryCode());

			if (itemMatrixNum > 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), posItemNum, itemMatrixNum);
				if (itemMatrix != null) {
					data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}
			if (itemGradeNum != null) {
				posItemGrade = AppUtil.getPosItemGrade(posItemGrades, itemGradeNum);
				if (posItemGrade != null) {
					data.setItemName(data.getItemName().concat("(" + posItemGrade.getItemGradeName() + ")"));
					data.setItemCode(posItemGrade.getItemGradeCode());
				}
			}
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByBranchs' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByBranchs(SaleAnalysisQueryData saleAnalysisQueryData) {

		List<Object[]> objects = reportDao.findSaleAnalysisByBranchs(saleAnalysisQueryData);
		return objects;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByCategorys' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByCategorys(SaleAnalysisQueryData saleAnalysisQueryData) {

		SystemBook systemBook = systemBookService.readInCache(saleAnalysisQueryData.getSystemBookCode());
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);

		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);
		if (saleAnalysisQueryData.getIsQueryCF() == null) {
			saleAnalysisQueryData.setIsQueryCF(false);
		}
		if (saleAnalysisQueryData.getIsQueryCardUser() == null) {
			saleAnalysisQueryData.setIsQueryCardUser(false);
		}
		if (systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()
				&& StringUtils.isEmpty(saleAnalysisQueryData.getSaleType()) 
				&& !saleAnalysisQueryData.getIsQueryCF()
				&& !saleAnalysisQueryData.getIsQueryCardUser()) {
			Date dpcLimitTime = DateUtil.addDay(now, -2);

			if (dpcLimitTime.compareTo(dateFrom) <= 0) {
				return reportDao.findSaleAnalysisCommon(saleAnalysisQueryData);
			} else if (dpcLimitTime.compareTo(dateTo) > 0) {
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());
				orderQueryDTO.setItemNums(saleAnalysisQueryData.getPosItemNums());

				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemStateSummaryDetail(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();

				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[7];
					objects[0] = list.get(i).getItemNum();
					objects[1] = list.get(i).getStateCode();
					objects[2] = list.get(i).getAmount();
					objects[3] = list.get(i).getPaymentMoney();
					objects[4] = list.get(i).getAssistAmount();
					objects[5] = list.get(i).getItemCount();
					objects[6] = list.get(i).getDiscount();
					returnList.add(objects);
				}
				return returnList;
			} else {

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(saleAnalysisQueryData.getSystemBookCode());
				orderQueryDTO.setBranchNum(saleAnalysisQueryData.getBranchNums());
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
				orderQueryDTO.setItemNums(saleAnalysisQueryData.getPosItemNums());

				if (saleAnalysisQueryData.getIsQueryCF() != null) {
					orderQueryDTO.setQueryKit(saleAnalysisQueryData.getIsQueryCF());

				}
				List<OrderDetailReportDTO> list = posOrderRemoteService.findItemStateSummaryDetail(orderQueryDTO);
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[7];
					objects[0] = list.get(i).getItemNum();
					objects[1] = list.get(i).getStateCode();
					objects[2] = list.get(i).getAmount();
					objects[3] = list.get(i).getPaymentMoney();
					objects[4] = list.get(i).getAssistAmount();
					objects[5] = list.get(i).getItemCount();
					objects[6] = list.get(i).getDiscount();
					returnList.add(objects);
				}
				saleAnalysisQueryData.setDtFrom(dpcLimitTime);
				saleAnalysisQueryData.setDtTo(dateTo);
				List<Object[]> localObjects = reportDao.findSaleAnalysisCommon(saleAnalysisQueryData);
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						Object[] objects = returnList.get(j);
						if (objects[0].equals(localObject[0]) && objects[1].equals(localObject[1])) {
							objects[2] = ((BigDecimal) objects[2]).add((BigDecimal) localObject[2]);
							objects[3] = ((BigDecimal) objects[3]).add((BigDecimal) localObject[3]);
							objects[4] = ((BigDecimal) objects[4]).add((BigDecimal) localObject[4]);
							objects[5] = ((Integer) objects[5]) + ((Integer) localObject[5]);
							objects[6] = ((BigDecimal) objects[6]).add((BigDecimal) localObject[6]);
							find = true;
							break;
						}
					}

					if (!find) {
						returnList.add(localObject);
					}
				}
				return returnList;
			}
		} else {
			return reportDao.findSaleAnalysisCommon(saleAnalysisQueryData);

		}

	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByCategoryBranchs' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData saleAnalysisQueryData) {
		return reportDao.findSaleAnalysisByCategoryBranchs(saleAnalysisQueryData);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisCommon' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByDepartments(SaleAnalysisQueryData saleAnalysisQueryData) {
		return findSaleAnalysisByCategorys(saleAnalysisQueryData);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisCommon' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByBrands(SaleAnalysisQueryData saleAnalysisQueryData) {
		return findSaleAnalysisByCategorys(saleAnalysisQueryData);
	}

	@Override
	public List<WholesaleProfitByPosItem> findWholesaleProfitByCategory(WholesaleProfitQuery wholesaleProfitQuery) {
		String systemBookCode = wholesaleProfitQuery.getSystemBookCode();

		Map<String, WholesaleProfitByPosItem> map = new HashMap<String, WholesaleProfitByPosItem>();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByItemNum(wholesaleProfitQuery);

		Integer itemNum = null;
		BigDecimal qty = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal useQty = null;
		BigDecimal presentQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		BigDecimal presentUseQty = null;
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			itemNum = (Integer) object[0];
			qty = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			useQty = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			presentQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentUseQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentCostMoney = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					continue;
				}
			}
			String category = posItem.getItemCategory();
			String categoryCode = posItem.getItemCategoryCode();

			WholesaleProfitByPosItem data = map.get(category + categoryCode);
			if (data == null) {
				data = new WholesaleProfitByPosItem();
				data.setPosItemTypeName(category);
				data.setPosItemTypeCode(categoryCode);
				map.put(category + categoryCode, data);
			}
			data.setSaleBaseQty(data.getSaleBaseQty().add(qty));
			if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) > 0) {
				qty = qty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentQty = presentQty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);

			}
			data.setSaleNum(data.getSaleNum().add(qty));
			data.setSaleMoney(data.getSaleMoney().add(money));
			data.setSaleCost(data.getSaleCost().add(costMoney));
			data.setReSaleCost(data.getReSaleCost().add(saleMoney));
			data.setSaleUseQty(data.getSaleUseQty().add(useQty));
			data.setPresentQty(data.getPresentQty().add(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().add(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().add(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().add(presentMoney));
		}

		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByItemNum(wholesaleProfitQuery);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			itemNum = (Integer) object[0];
			qty = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			useQty = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			presentQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentUseQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentCostMoney = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					continue;
				}
			}
			String category = posItem.getItemCategory();
			String categoryCode = posItem.getItemCategoryCode();

			WholesaleProfitByPosItem data = map.get(category + categoryCode);
			if (data == null) {
				data = new WholesaleProfitByPosItem();
				data.setPosItemTypeName(category);
				data.setPosItemTypeCode(categoryCode);
				map.put(category + categoryCode, data);
			}
			data.setSaleBaseQty(data.getSaleBaseQty().subtract(qty));
			if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) > 0) {
				qty = qty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentQty = presentQty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);

			}
			data.setSaleNum(data.getSaleNum().subtract(qty));
			data.setSaleMoney(data.getSaleMoney().subtract(money));
			data.setSaleCost(data.getSaleCost().subtract(costMoney));
			data.setReSaleCost(data.getReSaleCost().subtract(saleMoney));
			data.setSaleUseQty(data.getSaleUseQty().subtract(useQty));
			data.setPresentQty(data.getPresentQty().subtract(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
		}

		List<WholesaleProfitByPosItem> list = new ArrayList<WholesaleProfitByPosItem>(map.values());
		for (int i = 0; i < list.size(); i++) {
			WholesaleProfitByPosItem data = list.get(i);
			data.setSaleProfit(data.getSaleMoney().subtract(data.getSaleCost()));
			data.setReSaleProfit(data.getReSaleCost().subtract(data.getSaleMoney()));
			if (data.getReSaleCost().compareTo(BigDecimal.ZERO) == 0) {
				data.setReSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setReSaleProfitRate(data.getReSaleProfit()
						.divide(data.getReSaleCost(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
		}
		return list;
	}

	@Override
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByClientAndItem(
			WholesaleProfitQuery wholesaleProfitQuery) {
		String systemBookCode = wholesaleProfitQuery.getSystemBookCode();

		Map<String, WholesaleProfitByPosItemDetail> map = new HashMap<String, WholesaleProfitByPosItemDetail>();

		List<PosClient> posClients = posClientService.findInCache(systemBookCode);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByClientItemNum(wholesaleProfitQuery);

		String clientFid = null;
		Integer itemNum = null;
		Integer itemMatrixNum = null;
		BigDecimal qty = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal useQty = null;
		BigDecimal presentQty = null;
		BigDecimal presentUseQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			clientFid = (String) object[0];
			itemNum = (Integer) object[1];
			itemMatrixNum = object[2] == null ? 0 : (Integer) object[2];
			qty = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			saleMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			useQty = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			presentQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentUseQty = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentCostMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);
			presentMoney = object[11] == null ? BigDecimal.ZERO : ((BigDecimal) object[11]);


			WholesaleProfitByPosItemDetail data = new WholesaleProfitByPosItemDetail();
			data.setItemNum(itemNum);
			data.setItemMatrixNum(itemMatrixNum);
			data.setClientFid(clientFid);
			data.setWholesaleNum(qty);
			data.setWholesaleMoney(money);
			data.setWholesaleCost(costMoney);
			data.setSaleCost(saleMoney);
			data.setWholesaleUseNum(useQty);
			data.setWholesaleBaseNum(qty);
			data.setPresentQty(presentQty);
			data.setPresentUseQty(presentUseQty);
			data.setPresentCostMoney(presentCostMoney);
			data.setPresentMoney(presentMoney);
			map.put(clientFid + itemNum.toString() + itemMatrixNum.toString(), data);

		}

		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByClientItemNum(wholesaleProfitQuery);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			clientFid = (String) object[0];
			itemNum = (Integer) object[1];
			itemMatrixNum = object[2] == null ? 0 : (Integer) object[2];
			qty = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			saleMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			presentQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentUseQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentCostMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);

			WholesaleProfitByPosItemDetail data = map.get(clientFid + itemNum.toString() + itemMatrixNum.toString());
			if (data == null) {
				data = new WholesaleProfitByPosItemDetail();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setClientFid(clientFid);
				map.put(clientFid + itemNum.toString() + itemMatrixNum.toString(), data);
			}
			data.setWholesaleNum(data.getWholesaleNum().subtract(qty));
			data.setWholesaleMoney(data.getWholesaleMoney().subtract(money));
			data.setWholesaleCost(data.getWholesaleCost().subtract(costMoney));
			data.setSaleCost(data.getSaleCost().subtract(saleMoney));
			data.setWholesaleUseNum(data.getWholesaleUseNum().subtract(useQty));
			data.setWholesaleBaseNum(data.getWholesaleBaseNum().subtract(qty));
			data.setPresentQty(data.getPresentQty().subtract(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
		}

		List<WholesaleProfitByPosItemDetail> list = new ArrayList<WholesaleProfitByPosItemDetail>(map.values());
		for (int i = list.size() - 1; i >= 0; i--) {
			WholesaleProfitByPosItemDetail data = list.get(i);
			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), posItem.getItemMethod())) {
					list.remove(i);
					continue;
				}
			}
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());
			data.setPosItemCategory(posItem.getItemCategoryCode() + "|" + posItem.getItemCategory());
			data.setSpec(posItem.getItemSpec());
			data.setUnit(posItem.getItemWholesaleUnit());
			data.setBaseUnit(posItem.getItemUnit());
			if (data.getItemMatrixNum() > 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), data.getItemNum(),
						data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			PosClient posClient = AppUtil.getPosClient(data.getClientFid(), posClients);
			if (posClient != null) {
				data.setClientCode(posClient.getClientCode());
				data.setClientName(posClient.getClientName());
			}
			BigDecimal rate = posItem.getItemWholesaleRate();
			if (rate.compareTo(BigDecimal.ZERO) != 0) {
				data.setWholesaleNum(data.getWholesaleNum().divide(posItem.getItemWholesaleRate(), 4,
						BigDecimal.ROUND_HALF_UP));
			}
			data.setWholesaleProfit(data.getWholesaleMoney().subtract(data.getWholesaleCost()));
			data.setSaleProfit(data.getSaleCost().subtract(data.getWholesaleMoney()));
			if (data.getSaleCost().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleCost(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
			if (data.getWholesaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setWholesaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setWholesaleProfitRate(data.getWholesaleProfit()
						.divide(data.getWholesaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}

		}
		return list;
	}

	@Override
	public List<Object[]> findItemRebates(PolicyAllowPriftQuery policyAllowPriftQuery) {
		return reportDao.findItemRebates(policyAllowPriftQuery);
	}

	@Override
	public List<Object[]> findRebatesDetail(PolicyAllowPriftQuery policyAllowPriftQuery) {
		return reportDao.findRebatesDetail(policyAllowPriftQuery);
	}

	@Override
	public Object[] findRebatesSum(PolicyAllowPriftQuery policyAllowPriftQuery) {
		return reportDao.findRebatesSum(policyAllowPriftQuery);
	}

	@Override
	public List<Object[]> findOutOrderCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum,
			Integer branchNum, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findOutOrderCountAndMoneyByDate(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo,
				dateType);
	}

	@Override
	public List<Object[]> findReturnCountAndMoneyByDate(String systemBookCode, Integer centerBranchNum,
			Integer branchNum, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findReturnCountAndMoneyByDate(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo,
				dateType);
	}

	@Override
	public Object[] findSalerSummary(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums,
			List<String> salerNums) {
		return reportDao.findSalerSummary(systemBookCode, dtFrom, dtTo, branchNums, salerNums);
	}

	@Override
	public List<Object[]> findWholesaleOrderCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			String clientFid, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findWholesaleOrderCountAndMoneyByDate(systemBookCode, branchNum, clientFid, dateFrom, dateTo,
				dateType);
	}

	@Override
	public List<Object[]> findWholesaleReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			String clientFid, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findWholesaleReturnCountAndMoneyByDate(systemBookCode, branchNum, clientFid, dateFrom, dateTo,
				dateType);
	}

	@Override
	public List<Object[]> findPosOrderMoneyByBizDay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String dateType) {

		SystemBook systemBook = systemBookService.readInCache(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
		}

		Date deleteDate = systemBook.getDeleteDate();
		if (dateFrom != null) {
			dateFrom = DateUtil.getMinOfDate(dateFrom);
		} else {
			dateFrom = deleteDate;
		}
		if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
			dateFrom = deleteDate;
		}
		dateTo = DateUtil.getMaxOfDate(dateTo);

		boolean queryMonth = false;
		if (dateType.equals(AppConstants.BUSINESS_DATE_YEAR)) {
			queryMonth = true;
		}
		if (systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc() && !dateType.equals(AppConstants.BUSINESS_DATE_DAY)) {
			Date dpcLimitTime = DateUtil.addDay(now, -2);

			if (dpcLimitTime.compareTo(dateFrom) <= 0) {
				return reportDao.findPosOrderMoneyByBizDay(systemBookCode, branchNums, dateFrom, dateTo, dateType);
			} else if (dpcLimitTime.compareTo(dateTo) > 0) {
				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(dateTo);
				List<OrderReportDTO> list = null;
				if (queryMonth) {
					list = posOrderRemoteService.findMonthSummary(orderQueryDTO);

				} else {

					list = posOrderRemoteService.findDaySummary(orderQueryDTO);
				}
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[2];
					if (queryMonth) {
						objects[0] = list.get(i).getBizMonth();

					} else {

						objects[0] = list.get(i).getBizday();
					}
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					returnList.add(objects);
				}
				return returnList;
			} else {

				OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
				orderQueryDTO.setSystemBookCode(systemBookCode);
				orderQueryDTO.setBranchNum(branchNums);
				orderQueryDTO.setDateFrom(dateFrom);
				orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
				List<OrderReportDTO> list = null;
				if (queryMonth) {
					list = posOrderRemoteService.findMonthSummary(orderQueryDTO);

				} else {

					list = posOrderRemoteService.findDaySummary(orderQueryDTO);
				}
				List<Object[]> returnList = new ArrayList<Object[]>();
				for (int i = 0; i < list.size(); i++) {
					Object[] objects = new Object[2];
					if (queryMonth) {
						objects[0] = list.get(i).getBizMonth();

					} else {

						objects[0] = list.get(i).getBizday();
					}
					objects[1] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
							.subtract(list.get(i).getMgrDiscount());
					returnList.add(objects);
				}
				List<Object[]> localObjects = reportDao.findPosOrderMoneyByBizDay(systemBookCode, branchNums,
						dpcLimitTime, dateTo, dateType);
				Object[] objects = null;
				boolean find = false;
				for (int i = 0; i < localObjects.size(); i++) {
					Object[] localObject = localObjects.get(i);

					find = false;
					for (int j = 0; j < returnList.size(); j++) {
						objects = returnList.get(j);
						if (objects[0].equals(localObject[0])) {
							objects[1] = ((BigDecimal) objects[1]).add((BigDecimal) localObject[1]);
							find = true;
							break;
						}
					}

					if (!find) {
						returnList.add(localObject);
					}
				}
				return returnList;
			}
		} else {
			return reportDao.findPosOrderMoneyByBizDay(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		}
	}

	@Override
	public List<Object[]> findPurchaseReceiveCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findPurchaseReceiveCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom,
				dateTo, dateType);
	}

	@Override
	public List<Object[]> findPurchaseReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findPurchaseReturnCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom,
				dateTo, dateType);
	}

	@Override
	public List<Object[]> findPurchaseCountAndMoneyByDate(String systemBookCode, Integer branchNum,
			Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		return reportDao.findPurchaseCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom, dateTo,
				dateType);
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitByBranch(WholesaleProfitQuery queryData) {
		String systemBookCode = queryData.getSystemBookCode();
		Integer branchNum = queryData.getBanchNum();
		List<Integer> itemNums = queryData.getPosItemNums();
		Date dateFrom = queryData.getDateFrom();
		Date dateTo = queryData.getDateTo();
		List<String> clients = queryData.getClientFids();
		List<String> itemCategories = queryData.getCategorys();
		List<Integer> regionNums = queryData.getRegionNums();
		List<String> sellers = queryData.getSellers();
		List<Integer> branchNums = new ArrayList<Integer>();
		if (branchNum != null) {
			branchNums.add(branchNum);

		}
		if(queryData.getBranchNums() != null && queryData.getBranchNums().size() > 0){
			branchNums = queryData.getBranchNums();
		}
		Integer storehouseNum = queryData.getStorehouseNum();
		String auditor = queryData.getAuditor();

		Map<Integer, WholesaleProfitByClient> map = new HashMap<Integer, WholesaleProfitByClient>();

		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByBranch(systemBookCode, branchNums, dateFrom,
				dateTo, itemCategories, itemNums, clients, regionNums, storehouseNum, auditor, queryData.getDateType(), sellers);

		Integer branchNum_ = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal qty = null;
		BigDecimal useQty = null;
		BigDecimal baseQty = null;
		BigDecimal presentQty = null;
		BigDecimal presentUseQty = null;
		BigDecimal presentMoney = null;
		BigDecimal presentCostMoney = null;
		
		
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			branchNum_ = (Integer) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			costMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			qty = object[4] == null ? BigDecimal.ZERO : ((BigDecimal) object[4]);
			useQty = object[5] == null ? BigDecimal.ZERO : ((BigDecimal) object[5]);
			baseQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentUseQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentCostMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);
			
			
			WholesaleProfitByClient data = new WholesaleProfitByClient();
			data.setBranchNum(branchNum_);
			data.setWholesaleMoney(money);
			data.setWholesaleCost(costMoney);
			data.setRetailPrice(saleMoney);
			data.setWholesaleQty(qty);
			data.setWholesaleUseQty(useQty);
			data.setWholesaleBaseQty(baseQty);
			data.setPresentQty(presentQty);
			data.setPresentUseQty(presentUseQty);
			data.setPresentMoney(presentMoney);
			data.setPresentCostMoney(presentCostMoney);
			map.put(branchNum_, data);
		}

		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByBranch(systemBookCode, branchNums, dateFrom,
				dateTo, itemCategories, itemNums, clients, regionNums, storehouseNum, auditor, queryData.getDateType(), sellers);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			branchNum_ = (Integer) object[0];
			money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			costMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			qty = object[4] == null ? BigDecimal.ZERO : ((BigDecimal) object[4]);
			useQty = object[5] == null ? BigDecimal.ZERO : ((BigDecimal) object[5]);
			baseQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentUseQty = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			presentCostMoney = object[10] == null ? BigDecimal.ZERO : ((BigDecimal) object[10]);

			WholesaleProfitByClient data = map.get(branchNum_);
			if (data == null) {
				data = new WholesaleProfitByClient();
				data.setBranchNum(branchNum_);
				data.setWholesaleMoney(money.negate());
				data.setWholesaleCost(costMoney.negate());
				data.setRetailPrice(saleMoney.negate());
				data.setWholesaleQty(qty.negate());
				data.setWholesaleUseQty(useQty.negate());
				data.setWholesaleBaseQty(baseQty.negate());
				data.setPresentQty(presentQty.negate());
				data.setPresentUseQty(presentUseQty.negate());
				data.setPresentMoney(presentMoney.negate());
				data.setPresentCostMoney(presentCostMoney.negate());
				map.put(branchNum_, data);
			} else {
				data.setWholesaleMoney(data.getWholesaleMoney().subtract(money));
				data.setWholesaleCost(data.getWholesaleCost().subtract(costMoney));
				data.setRetailPrice(data.getRetailPrice().subtract(saleMoney));
				data.setWholesaleQty(data.getWholesaleQty().subtract(qty));
				data.setWholesaleUseQty(data.getWholesaleUseQty().subtract(useQty));
				data.setWholesaleBaseQty(data.getWholesaleBaseQty().subtract(baseQty));
				data.setPresentQty(data.getPresentQty().subtract(presentQty));
				data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
				data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
				data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
			}
		}

		List<WholesaleProfitByClient> list = new ArrayList<WholesaleProfitByClient>(map.values());
		for (int i = 0; i < list.size(); i++) {
			WholesaleProfitByClient data = list.get(i);
			Branch branch = AppUtil.getBranch(branchs, data.getBranchNum());
			if (branch != null) {
				data.setName(branch.getBranchName());

			}
			data.setWholesaleProfit(data.getWholesaleMoney().subtract(data.getWholesaleCost()));
			data.setRetailProfit(data.getRetailPrice().subtract(data.getWholesaleMoney()));
			// 零售毛利率
			if (data.getRetailPrice().compareTo(BigDecimal.ZERO) == 0) {
				data.setRetailProfitRate(BigDecimal.ZERO);
			} else {
				data.setRetailProfitRate(data.getRetailProfit()
						.divide(data.getRetailPrice(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
			// 批发毛利率
			if (data.getWholesaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setWholesaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setWholesaleProfitRate(data.getWholesaleProfit()
						.divide(data.getWholesaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
		}
		return list;
	}

	@Override
	public List<CardConsumeAnalysis> findCardConsumeAnalysis(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {
		List<CardConsumeAnalysis> list = new ArrayList<CardConsumeAnalysis>();
		BigDecimal moneyFrom = cardConsuemAnalysisQuery.getMoneyFrom();
		BigDecimal moneyTo = cardConsuemAnalysisQuery.getMoneyTo();
		BigDecimal moneySpace = cardConsuemAnalysisQuery.getMoneySpace();

		BigDecimal compareMoney = moneyFrom;
		BigDecimal nextMoney = null;
		List<Object[]> objects = reportDao.findCustomerConusmeByCardConsuemAnalysisQuery(cardConsuemAnalysisQuery);
		BigDecimal consumeMoneyTotal = BigDecimal.ZERO;
		if(moneyTo == null){
			moneyTo = BigDecimal.valueOf(1000);
			
		}
		while (compareMoney.compareTo(moneyTo) < 0) {
			nextMoney = compareMoney.add(moneySpace);
			if (nextMoney.compareTo(moneyTo) > 0) {
				nextMoney = moneyTo;
			}
			CardConsumeAnalysis cardConsumeAnalysis = new CardConsumeAnalysis();
			cardConsumeAnalysis.setRang(compareMoney.toString() + "-" + nextMoney.toString());
			Object[] obj = findCountAndMoney(objects, compareMoney, nextMoney);
			cardConsumeAnalysis.setCardUserNum((Integer) obj[0]);
			cardConsumeAnalysis.setConsumeMoney((BigDecimal) obj[1]);
			cardConsumeAnalysis.setMoneyFrom(compareMoney);
			cardConsumeAnalysis.setMoneyTo(nextMoney);
			list.add(cardConsumeAnalysis);
			compareMoney = compareMoney.add(moneySpace);
			consumeMoneyTotal = consumeMoneyTotal.add(cardConsumeAnalysis.getConsumeMoney());

		}
		Object[] obj = findCountAndMoney(objects, moneyTo, null);
		CardConsumeAnalysis cardConsumeAnalysis = new CardConsumeAnalysis();
		cardConsumeAnalysis.setRang(moneyTo + "以上");
		cardConsumeAnalysis.setCardUserNum((Integer) obj[0]);
		cardConsumeAnalysis.setConsumeMoney((BigDecimal) obj[1]);
		cardConsumeAnalysis.setMoneyFrom(moneyTo);
		cardConsumeAnalysis.setMoneyTo(null);
		consumeMoneyTotal = consumeMoneyTotal.add(cardConsumeAnalysis.getConsumeMoney());
		list.add(cardConsumeAnalysis);
		BigDecimal posMoneyTotal = reportDao.sumPosMoneyByCardConsuemAnalysisQuery(cardConsuemAnalysisQuery);

		for (int i = 0; i < list.size(); i++) {
			cardConsumeAnalysis = list.get(i);
			if (consumeMoneyTotal.compareTo(BigDecimal.ZERO) > 0) {
				cardConsumeAnalysis.setConsumeRate(cardConsumeAnalysis.getConsumeMoney()
						.divide(consumeMoneyTotal, 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100).setScale(2)));
			}
			if (posMoneyTotal.compareTo(BigDecimal.ZERO) > 0) {
				cardConsumeAnalysis.setBusiRate(cardConsumeAnalysis.getConsumeMoney()
						.divide(posMoneyTotal, 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100).setScale(2)));
			}
		}
		return list;
	}

	private Object[] findCountAndMoney(List<Object[]> objects, BigDecimal compareMoney, BigDecimal nextMoney) {
		int count = 0;
		BigDecimal money = BigDecimal.ZERO;
		for (int i = objects.size() - 1; i >= 0; i--) {
			Object[] object = objects.get(i);
			Integer custNum = object[0] == null ? 0 : (Integer) object[0];
			BigDecimal value = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			if (custNum == 0) {
				objects.remove(i);
				continue;
			}
			if (nextMoney != null) {
				if (value.compareTo(compareMoney) >= 0 && value.compareTo(nextMoney) < 0) {
					count++;
					money = money.add(value);
					objects.remove(i);
					continue;
				}
			} else {
				if (value.compareTo(compareMoney) >= 0) {
					count++;
					money = money.add(value);
					objects.remove(i);
					continue;
				}
			}
		}
		Object[] obj = new Object[2];
		obj[0] = count;
		obj[1] = money;
		return obj;
	}

	@Override
	public List<Object[]> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {
		return reportDao.findCardConsumeAnalysisDetails(cardConsuemAnalysisQuery);
	}

	@Override
	public BookSummaryReport getBookSummaryReport(String systemBookCode, Date dateFrom, Date dateTo) {
		BookSummaryReport bookSummaryReport = new BookSummaryReport();
		bookSummaryReport.setSystemBookCode(systemBookCode);

		SystemBook systemBook = systemBookDao.read(systemBookCode);
		if (systemBook == null) {
			return null;
		}
		bookSummaryReport.setSystemBookName(systemBook.getBookName());
		List<Branch> branchs = branchService.findAll(systemBookCode);
		Branch centerBranch = AppUtil.getBranch(branchs, AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM);
		if (centerBranch == null) {
			return null;
		}
		String module = centerBranch.getBranchModule();

		bookSummaryReport.setAllBranchs(branchs.size());
		bookSummaryReport.setActiveBranchs(posOrderDao.countActiveBranchs(systemBookCode, dateFrom, dateTo));
		bookSummaryReport.setPosOrderCount(posOrderDao.countByBranch(systemBookCode, null, dateFrom, dateTo));
		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_PURCHASE)) {
			bookSummaryReport.setPurchaseOrderCount(purchaseOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setReceiveOrderCount(receiveOrderDao
					.countByBranch(systemBookCode, null, dateFrom, dateTo));
			bookSummaryReport.setReturnOrderCount(returnOrderDao.countByBranch(systemBookCode, null, dateFrom, dateTo));
		}

		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_CHAIN)) {
			bookSummaryReport.setRequestOrderCount(requestOrderDao
					.countByBranch(systemBookCode, null, dateFrom, dateTo));
			bookSummaryReport.setTransferInCount(transferInOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setTransferOutCount(transferOutOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
		}
		////
		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_WHOLESALE)) {
			bookSummaryReport.setWholesaleOrderCount(wholesaleOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setWholesaleBookCount(wholesaleBookDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo, null));
			bookSummaryReport.setWholesaleReturnCount(wholesaleReturnDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
		}

		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_INVNETORY)) {
			bookSummaryReport.setCheckOrderCount(checkOrderDao.countByBranch(systemBookCode, null, dateFrom, dateTo));
			bookSummaryReport.setAllocationOrderCount(allocationOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setAdjustmentOrderCount(adjustmentOrderDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setAssembleOrderCount(assembleSplitDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
		}

		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_SETTLEMENT)) {
			bookSummaryReport.setSettlementOrderCount(settlementDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));
			bookSummaryReport.setPreSettlementOrderCount(preSettlementDao.countByBranch(systemBookCode, null, dateFrom,
					dateTo));

			bookSummaryReport.setInnerPreSettlementOrderCount(innerPreSettlementDao.countByBranch(systemBookCode, null,
					dateFrom, dateTo));
			bookSummaryReport.setInnerSettlementOrderCount(innerSettlementDao.countByBranch(systemBookCode, null,
					dateFrom, dateTo));

			bookSummaryReport.setClientPreSettlementOrderCount(clientPreSettlementDao.countByBranch(systemBookCode,
					null, dateFrom, dateTo));
			bookSummaryReport.setClientSettlementOrderCount(clientSettlementDao.countByBranch(systemBookCode, null,
					dateFrom, dateTo));

			bookSummaryReport.setCardSettlementOrderCount(cardSettlementDao.countByBranch(systemBookCode, null,
					dateFrom, dateTo));
		}

		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_PROMOTION)) {
			bookSummaryReport.setPromotionCount(policyPromotionDao.count(systemBookCode, null, dateFrom, dateTo,
					AppConstants.STATE_INIT_TIME, null, null));
			bookSummaryReport.setPromotionMoneyCount(policyPromotionMoneyDao.count(systemBookCode, null, dateFrom,
					dateTo, AppConstants.STATE_INIT_TIME));
			bookSummaryReport.setPromotionPresentCount(policyPresentDao.count(systemBookCode, null, dateFrom, dateTo,
					AppConstants.STATE_INIT_TIME));
			bookSummaryReport.setPromotionQuantityCount(policyPromotionQuantityDao.count(systemBookCode, null,
					dateFrom, dateTo, AppConstants.STATE_INIT_TIME, null));
			bookSummaryReport.setPolicyDiscountCount(policyDiscountDao.count(systemBookCode, null, dateFrom, dateTo,
					AppConstants.STATE_INIT_TIME));
		}

		bookSummaryReport.setNewCustomerCount(cardUserDao.count(systemBookCode, null, dateFrom, dateTo, null));
		bookSummaryReport.setMarketActionCount(marketActionDao
				.count(systemBookCode, null, dateFrom, dateTo, null, null));
		bookSummaryReport.setMessageCount(messageBoardDao.countByDate(systemBookCode, dateFrom, dateTo));

		if (StringUtils.isEmpty(module) || module.contains(AppConstants.C_AMA_MODULE_SMS)) {
			bookSummaryReport.setSmsSendCount(smsSendDao
					.count(systemBookCode, null, dateFrom, dateTo, null, null, null));
		}
		bookSummaryReport.setRewardCount(cardConsumeDao.countReward(systemBookCode, null, dateFrom, dateTo));
		LogQuery logQuery = new LogQuery();
		logQuery.setDateFrom(dateFrom);
		logQuery.setDateTo(dateTo);
		logQuery.setOperateType(AppConstants.WEB_LOG_ACTION_LOGIN);
		logQuery.setLogItem(AppConstants.WEB_LOG_ITEM_PHONE);
		bookSummaryReport.setMobileLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		logQuery.setLogItem(AppConstants.WEB_LOG_ITEM_SERVER);
		bookSummaryReport.setWebLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		logQuery.setLogItem(AppConstants.WEB_LOG_ITEM_BRANCH_REQUEST);
		bookSummaryReport.setShopLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		logQuery.setLogItem(AppConstants.WEB_LOG_ITEM_POS);
		bookSummaryReport.setPosMachineLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		logQuery.setWholesaleUserId("0");
		logQuery.setLogItem(null);
		bookSummaryReport.setWholesaleLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		logQuery.setWholesaleUserId(null);
		logQuery.setSupplierUserNum(0);
		bookSummaryReport.setSupplierLoginCount(webLogDao.countByQuery(systemBookCode, null, logQuery));

		bookSummaryReport.setOnlineOrderCount(onlineOrderDao.count(systemBookCode, null, dateFrom, dateTo));
		return bookSummaryReport;
	}

	@Override
	public List<ExceptInventory> findExceptInventories(InventoryExceptQuery inventoryExceptQuery, boolean highExceptFlag) {
		String systemBookCode = inventoryExceptQuery.getSystemBookCode();
		Integer branchNum = inventoryExceptQuery.getBranchNum();

		List<Object[]> objects = inventoryDao.findCenterStore(systemBookCode, branchNum, null);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<ExceptInventory> list = new ArrayList<ExceptInventory>();
		List<StoreMatrix> storeMatrixs = null;
		if (highExceptFlag) {
			storeMatrixs = storeMatrixDao.findByBranch(systemBookCode, branchNum, null);
		}
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal inventoryAmount = (BigDecimal) object[1];

			if (highExceptFlag) {
				if (inventoryAmount.compareTo(BigDecimal.ZERO) <= 0) {
					continue;
				}
				StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, itemNum, storeMatrixs);
				if (storeMatrix == null) {
					continue;
				}
				if (storeMatrix != null && storeMatrix.getStoreMatrixUpperStock() == null) {
					continue;
				}
				if (storeMatrix != null && storeMatrix.getStoreMatrixUpperStock() == null
						&& storeMatrix.getStoreMatrixUpperStock().compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				if (storeMatrix != null && storeMatrix.getStoreMatrixUpperStock() != null
						&& storeMatrix.getStoreMatrixUpperStock().compareTo(BigDecimal.ZERO) > 0
						&& storeMatrix.getStoreMatrixUpperStock().compareTo(inventoryAmount) > 0) {
					continue;
				}
			} else {
				if (inventoryAmount.compareTo(BigDecimal.ZERO) >= 0) {
					continue;
				}
			}

			ExceptInventory exceptInventory = new ExceptInventory();
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem != null) {
				if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
					continue;
				}
				if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
					continue;
				}
				if (posItem.getItemInventoryRate() != null
						&& posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
					exceptInventory.setInventoryUseAmount(inventoryAmount.divide(posItem.getItemInventoryRate(), 4,
							BigDecimal.ROUND_HALF_UP));
				} else {
					exceptInventory.setInventoryUseAmount(inventoryAmount);
				}
				exceptInventory.setInventoryUnit(posItem.getItemInventoryUnit());
				exceptInventory.setInventoryRegular(posItem.getItemRegularPrice());
				exceptInventory.setItemCode(posItem.getItemCode());
				exceptInventory.setItemSpec(posItem.getItemSpec());
				exceptInventory.setItemName(posItem.getItemName());
				exceptInventory.setItemCategory(posItem.getItemCategory());
				exceptInventory.setItemCategoryCode(posItem.getItemCategoryCode());
				list.add(exceptInventory);
			}
		}
		return list;
	}

	@Override
	public List<BranchBusinessSummary> findBranchBusinessSummaries(String systemBookCode, Date dateFrom, Date dateTo) {
		Map<Integer, BranchBusinessSummary> map = new LinkedHashMap<Integer, BranchBusinessSummary>();
		List<Branch> branchs = branchService.findAllActived(systemBookCode);
		BigDecimal consumeMoneySum = BigDecimal.ZERO;
		BigDecimal depositCashSum = BigDecimal.ZERO;
		BigDecimal depositMoneySum = BigDecimal.ZERO;
		Integer countSum = 0;
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			Integer branchNum = branch.getId().getBranchNum();
			String branchName = branch.getBranchName();

			BranchBusinessSummary branchBusinessSummary = new BranchBusinessSummary();
			branchBusinessSummary.setBranchName(branchName);
			branchBusinessSummary.setBranchNum(branchNum);
			map.put(branchNum, branchBusinessSummary);
		}
		List<Object[]> objects = cardUserDao.findCardCount(systemBookCode, null, dateFrom, dateTo, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer count = (Integer) object[1];

			BranchBusinessSummary branchBusinessSummary = map.get(branchNum);
			if (branchBusinessSummary != null) {
				branchBusinessSummary.setCardCount(count);
				countSum = countSum + count;
			}
		}

		objects = cardConsumeDao.findBranchSum(systemBookCode, null, dateFrom, dateTo, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = (BigDecimal) object[1];

			BranchBusinessSummary branchBusinessSummary = map.get(branchNum);
			if (branchBusinessSummary != null) {
				branchBusinessSummary.setConsumeMoney(money);
				consumeMoneySum = consumeMoneySum.add(money);
			}

		}

		objects = cardDepositDao.findBranchSum(systemBookCode, null, dateFrom, dateTo, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal cash = (BigDecimal) object[1];
			BigDecimal money = (BigDecimal) object[2];

			BranchBusinessSummary branchBusinessSummary = map.get(branchNum);
			if (branchBusinessSummary != null) {
				branchBusinessSummary.setDepositCash(cash);
				branchBusinessSummary.setDepositMoney(money);

				depositCashSum = depositCashSum.add(cash);
				depositMoneySum = depositMoneySum.add(money);
			}
		}
		List<BranchBusinessSummary> branchBusinessSummaries = new ArrayList<BranchBusinessSummary>(map.values());
		if (branchBusinessSummaries.size() > 0) {
			branchBusinessSummaries.get(0).setCardCountSum(countSum);
			branchBusinessSummaries.get(0).setDepositMoneySum(depositMoneySum);
			branchBusinessSummaries.get(0).setDepositCashSum(depositCashSum);
			branchBusinessSummaries.get(0).setConsumeMoneySum(consumeMoneySum);

		}
		return branchBusinessSummaries;
	}

	@Override
	public List<PosItemRank> findPosItemRanks(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
			List<PosItem> posItems) {
		List<PosItemRank> posItemRanks = reportDao.findPosItemRanks(systemBookCode, branchNum, dateFrom, dateTo);
		for (int i = 0; i < posItemRanks.size(); i++) {
			PosItemRank posItemRank = posItemRanks.get(i);
			PosItem posItem = AppUtil.getPosItem(posItemRank.getItemNum(), posItems);
			if (posItem != null) {
				posItemRank.setOutOrderDetailItemCode(posItem.getItemCode());
				posItemRank.setOutOrderDetailItemName(posItem.getItemName());
				posItemRank.setOutOrderDetailItemSpec(posItem.getItemSpec());
				posItemRank.setOutOrderDetailUsePrice(posItem.getItemRegularPrice());
				posItemRank.setOutOrderDetailUseUnit(posItem.getItemUnit());
			}

		}
		return posItemRanks;
	}

	@Override
	public BigDecimal sumTransferMoney(String systemBookCode, Integer branchNum, List<Integer> transferBranchNums,
			Date dateFrom, Date dateTo) {
		
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setResponseBranchNums(branchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);	
		
		List<Object[]> inObjects = transferInOrderDao.findProfitGroupByBranch(transferProfitQuery);
		List<Object[]> outObjects = transferOutOrderDao.findProfitGroupByBranch(transferProfitQuery);
		BigDecimal returnMoney = BigDecimal.ZERO;

		if (inObjects.size() > 0) {
			Object[] objects = inObjects.get(0);
			returnMoney = returnMoney.add(objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]);
		}
		if (outObjects.size() > 0) {
			Object[] objects = outObjects.get(0);
			returnMoney = returnMoney.subtract(objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]);
		}
		return returnMoney;
	}

	@Override
	public Object[] findInventoryUnsale(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		BigDecimal inventoryAmount = BigDecimal.ZERO;
		BigDecimal inventoryMoney = BigDecimal.ZERO;
		BigDecimal unSaleAmount = BigDecimal.ZERO;
		BigDecimal unSaleMoney = BigDecimal.ZERO;
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);

		// 出库数量小于0的算滞销
		BigDecimal compareAmount = BigDecimal.ZERO;

		List<Object[]> inventoryObjects = inventoryDao.findCenterStore(systemBookCode, branchNum, null);
		List<Object[]> inOutObjects = inventoryCollectDao.findSummaryByItemFlag(systemBookCode, branchNum,
				DateUtil.addDay(dateTo, 1), null, true);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);

		Map<Integer, UnsalablePosItem> map = new HashMap<Integer, UnsalablePosItem>();
		for (int i = posItems.size() - 1; i >= 0; i--) {
			PosItem posItem = posItems.get(i);

			// 过滤停售标记
			if (posItem.getItemSaleCeaseFlag() != null && posItem.getItemSaleCeaseFlag()) {
				continue;
			}
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}

			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			UnsalablePosItem unsalablePosItem = new UnsalablePosItem();
			unsalablePosItem.setItemNum(posItem.getItemNum());
			map.put(unsalablePosItem.getItemNum(), unsalablePosItem);
		}

		// 调出
		List<Object[]> objects = transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums,
				branchNums, dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			UnsalablePosItem data = map.get(itemNum);
			if (data != null) {
				data.setCurrentOutNum(data.getCurrentOutNum().add(amount));
			}
		}

		// 批发
		objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			UnsalablePosItem data = map.get(itemNum);
			if (data != null) {
				data.setCurrentPifaNum(data.getCurrentPifaNum().add(amount));
			}

		}
		BigDecimal amountSum = BigDecimal.ZERO;
		BigDecimal useAmountSum = BigDecimal.ZERO;
		for (int i = 0; i < inventoryObjects.size(); i++) {
			Object[] inventoryObject = inventoryObjects.get(i);
			Integer itemNum = (Integer) inventoryObject[0];
			BigDecimal currentAmount = inventoryObject[1] == null ? BigDecimal.ZERO : (BigDecimal) inventoryObject[1];
			BigDecimal currentMoney = inventoryObject[2] == null ? BigDecimal.ZERO : (BigDecimal) inventoryObject[2];
			for (int j = 0; j < inOutObjects.size(); j++) {
				Object[] inOutObject = inOutObjects.get(j);
				if (((Integer) inOutObject[0]).equals(itemNum)) {
					Boolean flag = (Boolean) inOutObject[1];
					BigDecimal amount = inOutObject[2] == null ? BigDecimal.ZERO : (BigDecimal) inOutObject[2];
					BigDecimal money = inOutObject[3] == null ? BigDecimal.ZERO : (BigDecimal) inOutObject[3];

					if (flag) {
						currentAmount = currentAmount.subtract(amount);
						currentMoney = currentMoney.subtract(money);
					} else {
						currentAmount = currentAmount.add(amount);
						currentMoney = currentMoney.add(money);
					}
				}

			}
			amountSum = amountSum.add(currentAmount);
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem != null) {
				if (posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
					currentAmount = currentAmount.divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					useAmountSum = useAmountSum.add(currentAmount);
				}
				UnsalablePosItem unsalablePosItem = map.get(itemNum);
				if (unsalablePosItem != null) {
					BigDecimal totalAmount = unsalablePosItem.getCurrentPifaNum().add(
							unsalablePosItem.getCurrentOutNum());
					if (posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0) {
						totalAmount = totalAmount.divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP);
					}
					// 批发 + 调出量小于设定值且当前库存大于0的算滞销商品
					if (totalAmount.compareTo(compareAmount) <= 0 && currentAmount.compareTo(BigDecimal.ZERO) > 0) {
						unSaleAmount = unSaleAmount.add(currentAmount);
						unSaleMoney = unSaleMoney.add(currentMoney);
					}

				}
			}
			inventoryAmount = inventoryAmount.add(currentAmount);
			inventoryMoney = inventoryMoney.add(currentMoney);

		}
		Object[] obj = new Object[4];
		obj[0] = inventoryAmount;
		obj[1] = inventoryMoney;
		obj[2] = unSaleAmount;
		obj[3] = unSaleMoney;
		return obj;
	}

	@Override
	public List<Object[]> findPosGroupByBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo) {
		return reportDao.findPosGroupByBranchRegionType(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findPosGroupByHourAndBranchRegionType(String systemBookCode, Integer branchNum,
			Date dateFrom, Date dateTo) {
		return reportDao.findPosGroupByHourAndBranchRegionType(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findPosGroupByHour(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		return reportDao.findPosGroupByHour(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findSummaryGroupByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums, boolean kitFlag) {
		List<Object[]> objects = reportDao.findSummaryGroupByItem(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, kitFlag);
		if (kitFlag) {
			objects.addAll(reportDao.findSummaryGroupByKitItem(systemBookCode, branchNums, dateFrom, dateTo, itemNums));
		}
		return objects;
	}

	@Override
	public List<Object[]> findItemSaleQty(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
			boolean includePos, boolean includeTranferOut, boolean includeWholesale) {
		List<Object[]> objects = new ArrayList<Object[]>();
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);

		Date now = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
		List<Object[]> posObjects = new ArrayList<Object[]>();
		if (includePos) {
			String type = AppConstants.POS_ITEM_LOG_POS + "," + AppConstants.POS_ITEM_LOG_ANTI_POS;
			StoreQueryCondition storeQueryCondition = new StoreQueryCondition();
			storeQueryCondition.setBranchNum(branchNum);
			storeQueryCondition.setSystemBookCode(systemBookCode);
			storeQueryCondition.setPosItemLogSummary(type);
			storeQueryCondition.setDateStart(now);
			storeQueryCondition.setDateEnd(now);
			Date yesterday = DateUtil.addDay(now, -1);
			if (dateTo.compareTo(now) < 0) {
				posObjects.addAll(inventoryCollectDao.findSummaryByItemMatrixFlag(systemBookCode, branchNums, dateFrom,
						dateTo, type, null, null));
			} else if (dateTo.compareTo(now) >= 0 && dateFrom.compareTo(now) >= 0) {

				posObjects.addAll(posItemLogDao.findItemMatrixInOutQtyAndMoney(storeQueryCondition));

			} else if (dateTo.compareTo(now) >= 0 && dateFrom.compareTo(now) < 0) {

				posObjects.addAll(inventoryCollectDao.findSummaryByItemMatrixFlag(systemBookCode, branchNums, dateFrom,
						yesterday, type, null, null));
				posObjects.addAll(posItemLogDao.findItemMatrixInOutQtyAndMoney(storeQueryCondition));
			}

			for (int i = 0; i < posObjects.size(); i++) {
				Object[] posObject = posObjects.get(i);
				Integer itemNum = (Integer) posObject[0];
				Integer itemMatrixNum = posObject[1] == null ? 0 : (Integer) posObject[1];
				boolean flag = (Boolean) posObject[2];
				BigDecimal amount = posObject[3] == null ? BigDecimal.ZERO : (BigDecimal) posObject[3];
				if (itemNum == null) {
					continue;
				}
				boolean find = false;
				if (flag) {
					amount = amount.negate();
				}
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (object[0].equals(itemNum) && object[1].equals(itemMatrixNum)) {
						object[2] = ((BigDecimal) object[2]).add(amount);
						find = true;
					}
				}
				if (!find) {
					Object[] object = new Object[3];
					object[0] = itemNum;
					object[1] = itemMatrixNum;
					object[2] = amount;
					objects.add(object);
				}
			}
		}

		 if(includeTranferOut){
			 List<Object[]> outObjects =
			 transferOutOrderDao.findProfitGroupByItem(systemBookCode, branchNums,
			 null, dateFrom, dateTo, null, null);
			 for(int i = 0;i < outObjects.size();i++){
			 Object[] outObject = outObjects.get(i);
			 Integer itemNum = (Integer)outObject[0];
			 Integer itemMatrixNum = outObject[1] == null?0:(Integer)outObject[1];
			 BigDecimal amount = outObject[2] ==
			 null?BigDecimal.ZERO:(BigDecimal)outObject[2];
			 BigDecimal presentAmount = outObject[5] ==
			 null?BigDecimal.ZERO:(BigDecimal)outObject[5];

			 amount = amount.add(presentAmount);
			 boolean find = false;
			 for(int j = 0;j < objects.size();j++){
			 Object[] object = objects.get(j);
			 if(object[0].equals(itemNum) && object[1].equals(itemMatrixNum)){
			 object[2] = ((BigDecimal)object[2]).add(amount);
			 find = true;
			 break;
		 }
		 }
		 if(!find){
		 Object[] object = new Object[3];
		 object[0] = itemNum;
		 object[1] = itemMatrixNum;
		 object[2] = amount;
		 objects.add(object);
		 }
		 }

		 }
		if (includeWholesale) {

			List<Object[]> orderObjects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, dateFrom, dateTo,
					null, null);
			for (int i = 0; i < orderObjects.size(); i++) {
				Object[] orderObject = orderObjects.get(i);
				Integer itemNum = (Integer) orderObject[0];
				Integer itemMatrixNum = orderObject[1] == null ? 0 : (Integer) orderObject[1];
				BigDecimal amount = orderObject[2] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[2];

				BigDecimal presentAmount = orderObject[5] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[5];
				amount = amount.add(presentAmount);
				boolean find = false;
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (object[0].equals(itemNum) && object[1].equals(itemMatrixNum)) {
						object[2] = ((BigDecimal) object[2]).add(amount);
						find = true;
						break;
					}
				}
				if (!find) {
					Object[] object = new Object[3];
					object[0] = itemNum;
					object[1] = itemMatrixNum;
					object[2] = amount;
					objects.add(object);
				}
			}
		}
		return objects;
	}

	@Override
	public List<Object[]> findWholesaleOrderLostByClientAndItem(String systemBookCode, Integer branchNum,
			Date dateFrom, Date dateTo, List<String> clientFids, List<Integer> itemNums) {
		return reportDao.findWholesaleOrderLostByClientAndItem(systemBookCode, branchNum, dateFrom, dateTo, clientFids,
				itemNums);
	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleUnsalableItem(String systemBookCode, Integer branchNum,
			String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom,
			Integer unWholesaleDaysTo, String unitType) {
		List<String> clientFids = new ArrayList<String>();
		clientFids.add(clientFid);
		List<Object[]> objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, clientFids, null, null, null,
				null, null);
		List<WholesaleAnalysisDTO> wholesaleAnalysisDTOs = new ArrayList<WholesaleAnalysisDTO>();
		if(objects.isEmpty()){
			return wholesaleAnalysisDTOs;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		if (unWholesaleDaysFrom == null) {
			unWholesaleDaysFrom = 30;
		}
		BigDecimal useQty = null;
		BigDecimal rate = null;
		String unit = null;
		Date now = Calendar.getInstance().getTime();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			Date lastOrderDate = (Date) object[4];
			Date firstOrderDate = (Date) object[5];
			Integer orderCount = ((Long) object[6]).intValue();
			useQty = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			if (lastOrderDate == null) {
				continue;
			}
			Integer unSaleDay = DateUtil.diffDay(lastOrderDate, now);
			if (unSaleDay < unWholesaleDaysFrom) {
				continue;
			}
			if (unWholesaleDaysTo != null && unSaleDay > unWholesaleDaysTo) {
				continue;
			}

			if (wholesaleRateFrom != null && orderCount < wholesaleRateFrom) {
				continue;
			}
			if (wholesaleRateTo != null && orderCount > wholesaleRateTo) {
				continue;
			}
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			rate = BigDecimal.ONE;
			unit = posItem.getItemUnit();
			if (unitType.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
				unit = posItem.getItemInventoryUnit();

			} else if (unitType.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
				unit = posItem.getItemTransferUnit();

			} else if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
				unit = posItem.getItemPurchaseUnit();

			} else if (unitType.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
				unit = posItem.getItemWholesaleUnit();
			} 
			WholesaleAnalysisDTO wholesaleAnalysisDTO = new WholesaleAnalysisDTO();
			wholesaleAnalysisDTO.setItemNum(itemNum);
			wholesaleAnalysisDTO.setItemCode(posItem.getItemCode());
			wholesaleAnalysisDTO.setItemName(posItem.getItemName());
			wholesaleAnalysisDTO.setItemSpec(posItem.getItemSpec());
			wholesaleAnalysisDTO.setItemUnit(unit);
			wholesaleAnalysisDTO.setLastWholesaleDate(lastOrderDate);
			wholesaleAnalysisDTO.setFirstSaleDate(firstOrderDate);
			wholesaleAnalysisDTO.setItemTotalQty(amount);
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				wholesaleAnalysisDTO.setItemTotalQty(wholesaleAnalysisDTO.getItemTotalQty().divide(
						rate, 4, BigDecimal.ROUND_HALF_UP));
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				wholesaleAnalysisDTO.setItemTotalQty(useQty);
			}
			wholesaleAnalysisDTO.setWholesaleRate(BigDecimal.valueOf(orderCount));
			wholesaleAnalysisDTO.setUnWholesaleDays(DateUtil.diffDay(lastOrderDate, Calendar.getInstance().getTime()));
			wholesaleAnalysisDTO.setSaleDays(DateUtil.diffDay(firstOrderDate, Calendar.getInstance().getTime()));
			if (wholesaleAnalysisDTO.getSaleDays() == 0) {
				wholesaleAnalysisDTO.setSaleDays(1);
			}
			wholesaleAnalysisDTOs.add(wholesaleAnalysisDTO);
		}
		return wholesaleAnalysisDTOs;
	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleProfitItem(String systemBookCode, Integer branchNum,
			String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom,
			Integer unWholesaleDaysTo, String unitType) {
		List<String> clientFids = new ArrayList<String>();
		clientFids.add(clientFid);
		List<Object[]> objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, clientFids, null, null, null,
				null, null);
		List<WholesaleAnalysisDTO> wholesaleAnalysisDTOs = new ArrayList<WholesaleAnalysisDTO>();
		if(objects.isEmpty()){
			return wholesaleAnalysisDTOs;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		BigDecimal rate = null;
		BigDecimal useQty = null;
		String unit = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal cost = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			Date lastOrderDate = (Date) object[4];
			Date firstOrderDate = (Date) object[5];
			Integer orderCount = ((Long) object[6]).intValue();
			useQty = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			
			if (firstOrderDate == null) {
				continue;
			}
			if (wholesaleRateFrom != null && orderCount < wholesaleRateFrom) {
				continue;
			}
			if (wholesaleRateTo != null && orderCount > wholesaleRateTo) {
				continue;
			}
			Integer unWholesaleDays = DateUtil.diffDay(lastOrderDate, Calendar.getInstance().getTime());
			if (unWholesaleDaysFrom != null && unWholesaleDays < unWholesaleDaysFrom) {
				continue;
			}
			if (unWholesaleDaysTo != null && unWholesaleDays > unWholesaleDaysTo) {
				continue;
			}
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			rate = BigDecimal.ONE;
			unit = posItem.getItemUnit();
			if (unitType.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
				unit = posItem.getItemInventoryUnit();

			} else if (unitType.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
				unit = posItem.getItemTransferUnit();

			} else if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
				unit = posItem.getItemPurchaseUnit();

			} else if (unitType.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
				unit = posItem.getItemWholesaleUnit();
			} 
			WholesaleAnalysisDTO wholesaleAnalysisDTO = new WholesaleAnalysisDTO();
			wholesaleAnalysisDTO.setItemNum(itemNum);
			wholesaleAnalysisDTO.setItemCode(posItem.getItemCode());
			wholesaleAnalysisDTO.setItemName(posItem.getItemName());
			wholesaleAnalysisDTO.setItemSpec(posItem.getItemSpec());
			wholesaleAnalysisDTO.setItemUnit(unit);
			wholesaleAnalysisDTO.setItemTotalMoney(money);
			wholesaleAnalysisDTO.setFirstSaleDate(firstOrderDate);
			wholesaleAnalysisDTO.setItemTotalProfit(money.subtract(cost));
			wholesaleAnalysisDTO.setItemTotalQty(amount);
			wholesaleAnalysisDTO.setLastWholesaleDate(lastOrderDate);
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				wholesaleAnalysisDTO.setItemTotalQty(wholesaleAnalysisDTO.getItemTotalQty().divide(
						rate, 4, BigDecimal.ROUND_HALF_UP));
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				wholesaleAnalysisDTO.setItemTotalQty(useQty);
			}
			
			wholesaleAnalysisDTO.setWholesaleRate(BigDecimal.valueOf(orderCount));
			wholesaleAnalysisDTO.setUnWholesaleDays(unWholesaleDays);
			wholesaleAnalysisDTO.setSaleDays(DateUtil.diffDay(firstOrderDate, Calendar.getInstance().getTime()));
			if (wholesaleAnalysisDTO.getSaleDays() == 0) {
				wholesaleAnalysisDTO.setSaleDays(1);
			}
			wholesaleAnalysisDTO.setEveryMonthProfit(wholesaleAnalysisDTO.getItemTotalProfit()
					.divide(BigDecimal.valueOf(wholesaleAnalysisDTO.getSaleDays()), 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(30)));
			wholesaleAnalysisDTOs.add(wholesaleAnalysisDTO);
		}
		return wholesaleAnalysisDTOs;
	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleHotItem(String systemBookCode, Integer branchNum, String clientFid,
			Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo,
			String unitType) {
		List<String> clientFids = new ArrayList<String>();
		clientFids.add(clientFid);
		List<Object[]> objects = wholesaleOrderDao.findItemSum(systemBookCode, branchNum, clientFids, null, null, null,
				null, null);
		List<WholesaleAnalysisDTO> wholesaleAnalysisDTOs = new ArrayList<WholesaleAnalysisDTO>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		if (wholesaleRateFrom == null) {
			wholesaleRateFrom = 3;
		}
		BigDecimal rate = null;
		BigDecimal useQty = null;
		String unit = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal cost = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			Date lastOrderDate = (Date) object[4];
			Date firstOrderDate = (Date) object[5];
			Integer orderCount = ((Long) object[6]).intValue();
			useQty = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			
			if (firstOrderDate == null) {
				continue;
			}
			if (orderCount < wholesaleRateFrom) {
				continue;
			}
			Integer unWholesaleDays = DateUtil.diffDay(lastOrderDate, Calendar.getInstance().getTime());
			if (unWholesaleDaysFrom != null && unWholesaleDays < unWholesaleDaysFrom) {
				continue;
			}
			if (unWholesaleDaysTo != null && unWholesaleDays > unWholesaleDaysTo) {
				continue;
			}
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			rate = BigDecimal.ONE;
			unit = posItem.getItemUnit();
			if (unitType.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
				unit = posItem.getItemInventoryUnit();

			} else if (unitType.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
				unit = posItem.getItemTransferUnit();

			} else if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
				unit = posItem.getItemPurchaseUnit();

			} else if (unitType.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
				unit = posItem.getItemWholesaleUnit();
			} 
			WholesaleAnalysisDTO wholesaleAnalysisDTO = new WholesaleAnalysisDTO();
			wholesaleAnalysisDTO.setItemNum(itemNum);
			wholesaleAnalysisDTO.setItemCode(posItem.getItemCode());
			wholesaleAnalysisDTO.setItemName(posItem.getItemName());
			wholesaleAnalysisDTO.setItemSpec(posItem.getItemSpec());
			wholesaleAnalysisDTO.setItemUnit(unit);
			wholesaleAnalysisDTO.setItemTotalMoney(money);
			wholesaleAnalysisDTO.setItemTotalProfit(money.subtract(cost));
			wholesaleAnalysisDTO.setItemTotalQty(amount);
			wholesaleAnalysisDTO.setFirstSaleDate(firstOrderDate);
			wholesaleAnalysisDTO.setLastWholesaleDate(lastOrderDate);
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				wholesaleAnalysisDTO.setItemTotalQty(wholesaleAnalysisDTO.getItemTotalQty().divide(
						rate, 4, BigDecimal.ROUND_HALF_UP));
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				wholesaleAnalysisDTO.setItemTotalQty(useQty);
			}
			wholesaleAnalysisDTO.setWholesaleRate(BigDecimal.valueOf(orderCount));
			wholesaleAnalysisDTO.setSaleDays(DateUtil.diffDay(firstOrderDate, Calendar.getInstance().getTime()));
			if (wholesaleAnalysisDTO.getSaleDays() == 0) {
				wholesaleAnalysisDTO.setSaleDays(1);
			}
			wholesaleAnalysisDTO.setUnWholesaleDays(unWholesaleDays);
			wholesaleAnalysisDTO.setTwoWeekQty(wholesaleAnalysisDTO.getItemTotalQty()
					.divide(BigDecimal.valueOf(wholesaleAnalysisDTO.getSaleDays()), 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(14)));
			wholesaleAnalysisDTOs.add(wholesaleAnalysisDTO);
		}
		return wholesaleAnalysisDTOs;
	}

	@Override
	public List<Object[]> findBranchSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		
		List<Object[]> objects = new ArrayList<Object[]>();
		
		List<Object[]> posObjects = posOrderDao.findBranchDetailSum(systemBookCode, branchNums, dateFrom, dateTo, null,
				false);
		for (int i = 0; i < posObjects.size(); i++) {
			Object[] posObject = posObjects.get(i);
			Integer branchNum = (Integer) posObject[0];
			BigDecimal money = posObject[1] == null ? BigDecimal.ZERO : (BigDecimal) posObject[1];
			BigDecimal amount = posObject[2] == null ? BigDecimal.ZERO : (BigDecimal) posObject[2];
			boolean find = false;
			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (object[0].equals(branchNum)) {
					object[1] = ((BigDecimal) object[1]).add(money);
					object[2] = ((BigDecimal) object[2]).add(amount);
					find = true;
				}
			}
			if (!find) {
				Object[] object = new Object[3];
				object[0] = branchNum;
				object[1] = money;
				object[2] = amount;
				objects.add(object);
			}
		}
		
		List<Object[]> outObjects = transferOutOrderDao.findProfitGroupByOutBranch(systemBookCode, branchNums, null,
				dateFrom, dateTo, null, null, null);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] outObject = outObjects.get(i);
			Integer branchNum = (Integer) outObject[0];
			BigDecimal amount = outObject[1] == null ? BigDecimal.ZERO : (BigDecimal) outObject[1];
			BigDecimal money = outObject[3] == null ? BigDecimal.ZERO : (BigDecimal) outObject[3];
			boolean find = false;
			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (object[0].equals(branchNum)) {
					object[1] = ((BigDecimal) object[1]).add(money);
					object[2] = ((BigDecimal) object[2]).add(amount);
					find = true;
				}
			}
			if (!find) {
				Object[] object = new Object[3];
				object[0] = branchNum;
				object[1] = money;
				object[2] = amount;
				objects.add(object);
			}
		}
		
		List<Object[]> orderObjects = wholesaleOrderDao.findMoneyGroupByBranch(systemBookCode, branchNums, dateFrom,
				dateTo, null, null, null, null, null, null, null, null);
		for (int i = 0; i < orderObjects.size(); i++) {
			Object[] orderObject = orderObjects.get(i);
			Integer branchNum = (Integer) orderObject[0];
			BigDecimal money = orderObject[1] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[1];
			BigDecimal amount = orderObject[5] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[5];
			boolean find = false;
			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (object[0].equals(branchNum)) {
					object[1] = ((BigDecimal) object[1]).add(money);
					object[2] = ((BigDecimal) object[2]).add(amount);
					find = true;
				}
			}
			if (!find) {
				Object[] object = new Object[3];
				object[0] = branchNum;
				object[1] = money;
				object[2] = amount;
				objects.add(object);
			}
		}
		return objects;
	}
	
	@Override
	public List<Object[]> findBranchItemSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums) {
		List<Object[]> objects = new ArrayList<Object[]>();

		List<Object[]> posObjects = posOrderDao.findBranchItemMatrixSummary(systemBookCode, branchNums, dateFrom,
				dateTo, itemNums);
		for (int i = 0; i < posObjects.size(); i++) {
			Object[] posObject = posObjects.get(i);
			Integer branchNum = (Integer) posObject[0];
			Integer itemNum = (Integer) posObject[1];
			Integer itemMatrixNum = posObject[2] == null ? 0 : (Integer) posObject[2];
			BigDecimal money = posObject[3] == null ? BigDecimal.ZERO : (BigDecimal) posObject[3];
			BigDecimal amount = posObject[4] == null ? BigDecimal.ZERO : (BigDecimal) posObject[4];
			boolean find = false;
			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (object[0].equals(branchNum) && object[1].equals(itemNum) && object[2].equals(itemMatrixNum)) {
					object[3] = ((BigDecimal) object[3]).add(money);
					object[4] = ((BigDecimal) object[4]).add(amount);
					find = true;
				}
			}
			if (!find) {
				Object[] object = new Object[5];
				object[0] = branchNum;
				object[1] = itemNum;
				object[2] = itemMatrixNum;
				object[3] = money;
				object[4] = amount;
				objects.add(object);
			}
		}

		List<Integer> centerBranchNums = new ArrayList<Integer>();
		if (branchNums.contains(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM)) {
			centerBranchNums.add(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM);
		}
		if (centerBranchNums.size() > 0) {
			TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
			transferProfitQuery.setSystemBookCode(systemBookCode);
			transferProfitQuery.setDistributionBranchNums(centerBranchNums);
			transferProfitQuery.setDtFrom(dateFrom);
			transferProfitQuery.setDtTo(dateTo);
			transferProfitQuery.setItemNums(itemNums);
			List<Object[]> outObjects = transferOutOrderDao.findProfitGroupByOutBranchAndItem(transferProfitQuery);
			for (int i = 0; i < outObjects.size(); i++) {
				Object[] outObject = outObjects.get(i);
				Integer branchNum = (Integer) outObject[0];
				Integer itemNum = (Integer) outObject[1];
				Integer itemMatrixNum = outObject[2] == null ? 0 : (Integer) outObject[2];
				BigDecimal amount = outObject[3] == null ? BigDecimal.ZERO : (BigDecimal) outObject[3];
				BigDecimal money = outObject[5] == null ? BigDecimal.ZERO : (BigDecimal) outObject[5];
				boolean find = false;
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (object[0].equals(branchNum) && object[1].equals(itemNum) && object[2].equals(itemMatrixNum)) {
						object[3] = ((BigDecimal) object[3]).add(money);
						object[4] = ((BigDecimal) object[4]).add(amount);
						find = true;
					}
				}
				if (!find) {
					Object[] object = new Object[5];
					object[0] = branchNum;
					object[1] = itemNum;
					object[2] = itemMatrixNum;
					object[3] = money;
					object[4] = amount;
					objects.add(object);
				}
			}

			List<Object[]> orderObjects = wholesaleOrderDao.findMoneyGroupByBranchItem(systemBookCode,
					centerBranchNums, dateFrom, dateTo, itemNums, null, null);
			for (int i = 0; i < orderObjects.size(); i++) {
				Object[] orderObject = orderObjects.get(i);
				Integer branchNum = (Integer) orderObject[0];
				Integer itemNum = (Integer) orderObject[1];
				Integer itemMatrixNum = orderObject[2] == null ? 0 : (Integer) orderObject[2];
				BigDecimal amount = orderObject[3] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[3];
				BigDecimal money = orderObject[4] == null ? BigDecimal.ZERO : (BigDecimal) orderObject[4];
				boolean find = false;
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (object[0].equals(branchNum) && object[1].equals(itemNum) && object[2].equals(itemMatrixNum)) {
						object[3] = ((BigDecimal) object[3]).add(money);
						object[4] = ((BigDecimal) object[4]).add(amount);
						find = true;
					}
				}
				if (!find) {
					Object[] object = new Object[5];
					object[0] = branchNum;
					object[1] = itemNum;
					object[2] = itemMatrixNum;
					object[3] = money;
					object[4] = amount;
					objects.add(object);
				}
			}
		}
		return objects;
	}

	@Override
	public List<CustomerAnalysisDay> findCusotmerAnalysisBranchs(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, String saleType) {
		List<Object[]> objects = reportDao.findCustomerAnalysisBranch(systemBookCode, dateFrom, dateTo, branchNums,
				saleType);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<CustomerAnalysisDay> list = new ArrayList<CustomerAnalysisDay>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal mgrDiscount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			CustomerAnalysisDay data = new CustomerAnalysisDay();
			data.setBranchNum((Integer) object[0]);
			Branch branch = AppUtil.getBranch(branchs, data.getBranchNum());
			if (branch != null) {
				data.setBranchName(branch.getBranchName());
			}

			data.setTotalMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			data.setTotalMoney(data.getTotalMoney().add(couponMoney).subtract(mgrDiscount));
			data.setCustomerNums(BigDecimal.valueOf((Long) object[2]));
			data.setCustomerItemRelatRate(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			data.setCustomerItemCount(data.getCustomerItemRelatRate());
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}

			data.setCusotmerVipNums(object[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[7]));
			data.setCustomerVipMoney(object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]);
			data.setCustomerValidNums(object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9]);
			if (data.getCustomerValidNums().compareTo(BigDecimal.ZERO) > 0) {

				data.setCustomerItemRelatRate(data.getCustomerItemRelatRate().divide(data.getCustomerValidNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}

			list.add(data);
		}
		return list;
	}

	@Override
	public List<WholesaleAnalysisDTO> findClientUnSaleItems(String systemBookCode, Integer branchNum, String clientFid) {
		List<Object[]> objects = inventoryDao.findCenterStore(systemBookCode, branchNum, null);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		List<Integer> saledItemNums = wholesaleOrderDao.findClientSaledItemNums(systemBookCode, branchNum, clientFid);
		List<Integer> unSaleItemNums = posItemService.findUnSaleItemNums(systemBookCode, branchNum);

		List<WholesaleAnalysisDTO> list = new ArrayList<WholesaleAnalysisDTO>();
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);

			if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
				continue;
			}
			if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
				continue;
			}
			if (saledItemNums.contains(posItem.getItemNum())) {
				continue;
			}
			BigDecimal amount = BigDecimal.ZERO;
			Object[] object = null;
			for (int j = 0; j < objects.size(); j++) {
				object = objects.get(j);
				if (posItem.getItemNum().equals(object[0])) {
					amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
				}
			}
			if (amount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			if (posItem.getItemStatus() != null && posItem.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
				continue;
			}
			if (unSaleItemNums != null && unSaleItemNums.size() > 0 && unSaleItemNums.contains(posItem.getItemNum())) {
				continue;
			}
			WholesaleAnalysisDTO wholesaleAnalysisDTO = new WholesaleAnalysisDTO();
			wholesaleAnalysisDTO.setItemNum(posItem.getItemNum());
			wholesaleAnalysisDTO.setItemCode(posItem.getItemCode());
			wholesaleAnalysisDTO.setItemName(posItem.getItemName());
			wholesaleAnalysisDTO.setItemUnit(posItem.getItemWholesaleUnit());
			wholesaleAnalysisDTO.setItemSpec(posItem.getItemSpec());
			wholesaleAnalysisDTO.setItemCategory(posItem.getItemCategoryCode() + "|" + posItem.getItemCategory());
			wholesaleAnalysisDTO.setItemStoreQty(amount.divide(posItem.getItemWholesaleRate(), 4,
					BigDecimal.ROUND_HALF_UP));
			list.add(wholesaleAnalysisDTO);
		}
		return list;
	}

	@Override
	public List<NeedSaleItemDTO> findNeedSaleItemDatas(String systemBookCode, Integer branchNum, Integer storehouseNum,
			List<String> categoryCodes, List<Integer> itemNums, String unitType) {
		List<InventoryLnDetail> inventoryLnDetails = inventoryDao.findExpireLns(systemBookCode, branchNum,
				storehouseNum, categoryCodes, itemNums);
		List<NeedSaleItemDTO> list = new ArrayList<NeedSaleItemDTO>();
		if (inventoryLnDetails.size() == 0) {
			return list;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);

		String unit = null;
		BigDecimal rate = null;
		if (unitType == null) {
			unitType = AppConstants.UNIT_BASIC;
		}
		for (int i = 0; i < inventoryLnDetails.size(); i++) {
			InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(i);
			NeedSaleItemDTO needSaleItemDTO = new NeedSaleItemDTO();
			needSaleItemDTO.setStorehouseNum(inventoryLnDetail.getId().getStorehouseNum());

			PosItem posItem = AppUtil.getPosItem(inventoryLnDetail.getItemNum(), posItems);
			if (posItem == null) {
				continue;
			}
			if (posItem.getItemValidPeriod() != null) {
				needSaleItemDTO.setExpireDay(DateUtil.diffDay(now,
						inventoryLnDetail.getInventoryLnDetailProducingDate())
						+ posItem.getItemValidPeriod());
			} else {
				continue;
			}
			needSaleItemDTO.setPosItem(posItem);
			needSaleItemDTO.setUnValidDate(DateUtil.addDay(inventoryLnDetail.getInventoryLnDetailProducingDate(),
					posItem.getItemValidPeriod()));
			needSaleItemDTO.setInventoryBasicQty(inventoryLnDetail.getInventoryLnDetailAmount());
			needSaleItemDTO.setInventoryUseQty(inventoryLnDetail.getInventoryLnDetailAmount());
			needSaleItemDTO.setLotNumber(inventoryLnDetail.getInventoryLnDetailLotNumber());
			needSaleItemDTO.setProducingDate(inventoryLnDetail.getInventoryLnDetailProducingDate());
			needSaleItemDTO.setInventoryUseCost(inventoryLnDetail.getInventoryLnDetailCostPrice());
			needSaleItemDTO.setInventoryMoney(inventoryLnDetail.getInventoryLnDetailCostPrice().multiply(
					inventoryLnDetail.getInventoryLnDetailAmount()));
			if (unitType.equals(AppConstants.UNIT_BASIC)) {
				unit = posItem.getItemUnit();
				rate = BigDecimal.ONE;
			} else if (unitType.equals(AppConstants.UNIT_PIFA)) {
				unit = posItem.getItemWholesaleUnit();
				rate = posItem.getItemWholesaleRate();
			} else if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
				unit = posItem.getItemPurchaseUnit();
				rate = posItem.getItemPurchaseRate();
			} else if (unitType.equals(AppConstants.UNIT_TRANFER)) {
				unit = posItem.getItemTransferUnit();
				rate = posItem.getItemTransferRate();
			} else if (unitType.equals(AppConstants.UNIT_SOTRE)) {
				unit = posItem.getItemInventoryUnit();
				rate = posItem.getItemInventoryRate();
			}
			needSaleItemDTO.setUseUnit(unit);
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				needSaleItemDTO.setInventoryUseQty(needSaleItemDTO.getInventoryUseQty().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				needSaleItemDTO.setInventoryUseCost(needSaleItemDTO.getInventoryUseCost().multiply(rate));
			}

			list.add(needSaleItemDTO);
		}

		return list;
	}

	@Override
	public List<AlipaySumDTO> findAlipaySumDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String payType) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Map<Integer, AlipaySumDTO> map = new LinkedHashMap<Integer, AlipaySumDTO>();
		for (int i = 0; i < branchNums.size(); i++) {
			Integer branchNum = branchNums.get(i);
			Branch branch = AppUtil.getBranch(branchs, branchNum);

			AlipaySumDTO alipaySumDTO = new AlipaySumDTO();
			alipaySumDTO.setBranchNum(branchNum);
			if (branch != null) {
				alipaySumDTO.setBranchName(branch.getBranchName());
				alipaySumDTO.setBranchRegionNum(branch.getBranchRegionNum());
			}
			map.put(branchNum, alipaySumDTO);
		}

		// 存款支付成功
		String alipayLogTypes = "";
		if (StringUtils.isEmpty(payType)) {
			alipayLogTypes = AppConstants.ALIPAY_CREATE_BY_BARCODE + "," + AppConstants.ALIPAY_PRE_CREATE_BY_QR + ","
					+ AppConstants.WEIXINPAY_CREATE_BY_BARCODE + "," + AppConstants.WEIXINPAY_CREATE_BY_QRCODE + ","
					+ AppConstants.DP_HUI_BY_BARCODE + "," + AppConstants.DP_HUI_BY_QR + ",";
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_ALIPAY)) {
			alipayLogTypes = AppConstants.ALIPAY_CREATE_BY_BARCODE + "," + AppConstants.ALIPAY_PRE_CREATE_BY_QR + ",";
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_WEIXINPAY)) {
			alipayLogTypes = AppConstants.WEIXINPAY_CREATE_BY_BARCODE + "," + AppConstants.WEIXINPAY_CREATE_BY_QRCODE
					+ ",";
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_DPHUI)) {
			alipayLogTypes = AppConstants.DP_HUI_BY_BARCODE + "," + AppConstants.DP_HUI_BY_QR + ",";
		} else {
			alipayLogTypes = payType;
		}
		String paymentTypes = payType;
		if (StringUtils.isEmpty(paymentTypes)) {
			paymentTypes = AppConstants.PAY_TYPE_ALIPAY + "," + AppConstants.PAY_TYPE_WEIXINPAY + ","
					+ AppConstants.PAY_TYPE_DPHUI;
		}
		List<Object[]> objects = alipayLogDao.findDepositSummary(systemBookCode, branchNums, dateFrom, dateTo, alipayLogTypes);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipaySumDTO alipaySumDTO = map.get((Integer) object[0]);
			if (alipaySumDTO == null) {
				continue;
			}
			alipaySumDTO.setDepositSuccessMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			alipaySumDTO.setDepositSuccessQty(BigDecimal.valueOf((Long) object[2]));
		}

		// POS消费成功
		objects = posOrderDao.findBranchSummary(systemBookCode, branchNums, dateFrom, dateTo, paymentTypes);
		BigDecimal buyerMoney = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipaySumDTO alipaySumDTO = map.get((Integer) object[0]);
			if (alipaySumDTO == null) {
				continue;
			}
			alipaySumDTO.setPosConsumeSuccessMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			alipaySumDTO.setPosConsumeSuccessQty(BigDecimal.valueOf((Long) object[2]));
			alipaySumDTO.setPosConsumeSuccessActualMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			buyerMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			alipaySumDTO.setAlipayDiscountMoney(alipaySumDTO.getPosConsumeSuccessActualMoney().subtract(buyerMoney));
			alipaySumDTO.setBranchDiscountMoney((object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5])
					.subtract(alipaySumDTO.getPosConsumeSuccessActualMoney()));
		}

		// 存款支付失败
		objects = alipayLogDao.findBranchSummaryPayFail(systemBookCode, branchNums, dateFrom, dateTo, true,
				alipayLogTypes);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipaySumDTO alipaySumDTO = map.get((Integer) object[0]);
			if (alipaySumDTO == null) {
				continue;
			}
			alipaySumDTO.setDepositFailMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			alipaySumDTO.setDepositFailQty(BigDecimal.valueOf((Long) object[2]));
		}

		// POS消费失败
		objects = alipayLogDao.findBranchSummaryPayFail(systemBookCode, branchNums, dateFrom, dateTo, false,
				alipayLogTypes);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipaySumDTO alipaySumDTO = map.get((Integer) object[0]);
			if (alipaySumDTO == null) {
				continue;
			}
			alipaySumDTO.setPosConsumeFailMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			alipaySumDTO.setPosConsumeFailQty(BigDecimal.valueOf((Long) object[2]));
		}

		alipayLogTypes = "";
		if (StringUtils.isEmpty(payType)) {
			alipayLogTypes = AppConstants.ALIPAY_CANCEL + "," + AppConstants.WEIXINPAY_CANCEL + ","
					+ AppConstants.DP_HUI_CANCEL + ",";
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_ALIPAY)) {
			alipayLogTypes = AppConstants.ALIPAY_CANCEL;
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_WEIXINPAY)) {
			alipayLogTypes = AppConstants.WEIXINPAY_CANCEL;
		} else if (StringUtils.equals(payType, AppConstants.PAY_TYPE_DPHUI)) {
			alipayLogTypes = AppConstants.DP_HUI_CANCEL;
		} else {
			alipayLogTypes = payType;
		}
		// 反结账
		objects = alipayLogDao.findBranchSummaryRePay(systemBookCode, branchNums, dateFrom, dateTo, alipayLogTypes);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			AlipaySumDTO alipaySumDTO = map.get((Integer) object[0]);
			if (alipaySumDTO == null) {
				continue;
			}
			alipaySumDTO.setConsumeOverMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			alipaySumDTO.setConsumeOverQty(BigDecimal.valueOf((Long) object[2]));
		}
		List<AlipaySumDTO> list = new ArrayList<AlipaySumDTO>(map.values());
		for (int i = 0; i < list.size(); i++) {
			AlipaySumDTO alipaySumDTO = list.get(i);
			alipaySumDTO.setPosConsumeTotalQty(alipaySumDTO.getPosConsumeFailQty().add(
					alipaySumDTO.getPosConsumeSuccessQty()));
			alipaySumDTO.setDepositTotalQty(alipaySumDTO.getDepositFailQty().add(alipaySumDTO.getDepositSuccessQty()));

			if (alipaySumDTO.getPosConsumeTotalQty().compareTo(BigDecimal.ZERO) > 0) {
				alipaySumDTO.setPosConsumeFailRate(alipaySumDTO.getPosConsumeFailQty()
						.divide(alipaySumDTO.getPosConsumeTotalQty(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)).setScale(2));
			}
			if (alipaySumDTO.getDepositTotalQty().compareTo(BigDecimal.ZERO) > 0) {
				alipaySumDTO.setDepositFailRate(alipaySumDTO.getDepositFailQty()
						.divide(alipaySumDTO.getDepositTotalQty(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)).setScale(2));
			}
		}
		return list;
	}

	// 此报表存在严重性能问题
	@Override
	public List<CardAnalysisDTO> findCardAnalysisDTOs(CardUserQuery cardUserQuery) {
		Date now = Calendar.getInstance().getTime();

		Date dateFrom = cardUserQuery.getDateFrom();
		Date dateTo = DateUtil.getMaxOfDate(cardUserQuery.getDateTo());
		List<Integer> branchNums = cardUserQuery.getBranchNums();

		cardUserQuery.setDateFrom(null);
		cardUserQuery.setDateTo(null);
		cardUserQuery.setBranchNums(null);
		cardUserQuery.setPaging(false);
		List<CardUser> cardUsers = cardUserDao.findByCardUserQuery(cardUserQuery, 0, 0);
		Map<Integer, CardAnalysisDTO> map = new HashMap<Integer, CardAnalysisDTO>();
		List<CardUserType> cardUserTypes = bookResourceService.findCardUserTypesInCache(cardUserQuery
				.getSystemBookCode());
		for (int i = 0; i < cardUsers.size(); i++) {
			CardUser cardUser = cardUsers.get(i);
			CardAnalysisDTO cardAnalysisDTO = new CardAnalysisDTO();
			cardAnalysisDTO.setCardUserNum(cardUser.getCardUserNum());
			cardAnalysisDTO.setCardPrintId(cardUser.getCardUserPrintedNum());
			cardAnalysisDTO.setCardType(cardUser.getCardUserCardType());
			for (int j = 0; j < cardUserTypes.size(); j++) {
				CardUserType cardUserType = cardUserTypes.get(j);
				if (cardUserType.getTypeCode().equals(cardAnalysisDTO.getCardType().toString())) {
					cardAnalysisDTO.setCardTypeName(cardUserType.getTypeName());
					break;
				}
			}
			cardAnalysisDTO.setCardBalance(BigDecimal.ZERO);
			cardAnalysisDTO.setTotalPaymentMoney(cardUser.getCardUserTotalCash());
			cardAnalysisDTO.setCardBalance(BigDecimal.ZERO);
			if (cardUser.getCardBalance() != null) {
				cardAnalysisDTO.setCardBalance(cardUser.getCardBalance().getCardBalanceMoney());
			}
			cardAnalysisDTO.setPaymentMoney(BigDecimal.ZERO);
			cardAnalysisDTO.setDepositMoney(BigDecimal.ZERO);
			cardAnalysisDTO.setConsumeMoney(BigDecimal.ZERO);
			cardAnalysisDTO.setLastCardBalance(cardAnalysisDTO.getCardBalance());
			cardAnalysisDTO.setBalanceMoney(cardAnalysisDTO.getCardBalance());
			map.put(cardUser.getCardUserNum(), cardAnalysisDTO);
		}
		List<Object[]> objects = cardConsumeDao.findUserDateMoney(cardUserQuery.getSystemBookCode(), branchNums,
				dateFrom, now);
		List<Object[]> depositObjects = cardDepositDao.findUserDateMoney(cardUserQuery.getSystemBookCode(), branchNums,
				dateFrom, now);
		objects.addAll(depositObjects);

		// 按日期倒序
		Collections.sort(objects, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {
				return -((Date) o1[1]).compareTo((Date) o2[1]);
			}

		});
		Object[] object;
		Date date;

		BigDecimal cash;
		BigDecimal money;
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			CardAnalysisDTO cardAnalysisDTO = map.get((Integer) object[0]);
			if (cardAnalysisDTO == null) {
				continue;
			}
			date = (Date) object[1];

			// 消费明细
			if (object.length == 3) {
				money = (BigDecimal) object[2];
				// 期间内
				if (date.compareTo(dateTo) <= 0) {
					cardAnalysisDTO.setConsumeMoney(cardAnalysisDTO.getConsumeMoney().add(money));
				} else {
					cardAnalysisDTO.setBalanceMoney(cardAnalysisDTO.getBalanceMoney().add(money));

				}
				cardAnalysisDTO.setLastCardBalance(cardAnalysisDTO.getLastCardBalance().add(money));
			} else {
				// 存款明细
				cash = (BigDecimal) object[2];
				money = (BigDecimal) object[3];
				// 期间内
				if (date.compareTo(dateTo) <= 0) {
					cardAnalysisDTO.setPaymentMoney(cardAnalysisDTO.getPaymentMoney().add(cash));
					cardAnalysisDTO.setDepositMoney(cardAnalysisDTO.getDepositMoney().add(money));
				} else {
					cardAnalysisDTO.setBalanceMoney(cardAnalysisDTO.getBalanceMoney().subtract(money));

				}
				cardAnalysisDTO.setLastCardBalance(cardAnalysisDTO.getLastCardBalance().subtract(money));
			}
		}
		return new ArrayList<CardAnalysisDTO>(map.values());
	}

	@Override
	public List<Object[]> findProfitAnalysisBranchs(ProfitAnalysisQueryData profitAnalysisQueryData) {
		if(profitAnalysisQueryData.getIsQueryCF() == null){
			profitAnalysisQueryData.setIsQueryCF(true);
		}
		return reportDao.findProfitAnalysisBranchs(profitAnalysisQueryData);
	}

	@Override
	public List<OrderDetailCompare> findOrderDetailCompareDatasByBranch(String systemBookCode,
			List<Integer> branchNums, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo) {
		Map<Integer, OrderDetailCompare> map = new HashMap<Integer, OrderDetailCompare>();

		List<Object[]> objects = reportDao.findCustomerAnalysisBranch(systemBookCode, thisDateFrom, thisDateTo,
				branchNums, null);

		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal mgrDiscount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			OrderDetailCompare data = new OrderDetailCompare();
			data.setBranchNum((Integer) object[0]);

			data.setThisSellMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			data.setThisSellMoney(data.getThisSellMoney().add(couponMoney).subtract(mgrDiscount));
			data.setThisSellNum(BigDecimal.valueOf((Long) object[2]));
			data.setThisSaleProfit(profit);
			if (data.getThisSellNum().compareTo(BigDecimal.ZERO) > 0) {
				data.setThisAvgPrice(data.getThisSellMoney().divide(data.getThisSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
			map.put(data.getBranchNum(), data);
		}
		objects = reportDao.findCustomerAnalysisBranch(systemBookCode, lastDateFrom, lastDateTo, branchNums, null);

		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal mgrDiscount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			OrderDetailCompare data = map.get((Integer) object[0]);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setBranchNum((Integer) object[0]);
				map.put(data.getBranchNum(), data);
			}

			data.setLastSellMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			data.setLastSellMoney(data.getLastSellMoney().add(couponMoney).subtract(mgrDiscount));
			data.setLastSellNum(BigDecimal.valueOf((Long) object[2]));
			data.setLastSaleProfit(profit);
			if (data.getLastSellNum().compareTo(BigDecimal.ZERO) > 0) {
				data.setLastAvgPrice(data.getLastSellMoney().divide(data.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}

		List<AdjustmentReason> adjustmentReasons = bookResourceService.findAdjustmentReasons(systemBookCode);
		List<String> reasons = new ArrayList<String>();
		for (int i = 0; i < adjustmentReasons.size(); i++) {
			AdjustmentReason adjustmentReason = adjustmentReasons.get(i);
			if (StringUtils.equals(adjustmentReason.getAdjustmentInoutCategory(),
					AppConstants.ADJUSTMENT_REASON_TYPE_LOSS)) {
				reasons.add(adjustmentReason.getAdjustmentReasonName());
			}
		}
		if (reasons.size() > 0) {
			objects = adjustmentOrderDao.findBranchSummary(systemBookCode, branchNums, thisDateFrom, thisDateTo,
					reasons);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branchNum = (Integer) object[0];
				BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
				OrderDetailCompare data = map.get(branchNum);
				if (data == null) {
					data = new OrderDetailCompare();
					data.setBranchNum(branchNum);
					map.put(branchNum, data);
				}
				data.setThisAdjustMoney(money);
			}

			objects = adjustmentOrderDao.findBranchSummary(systemBookCode, branchNums, lastDateFrom, lastDateTo,
					reasons);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branchNum = (Integer) object[0];
				BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
				OrderDetailCompare data = map.get(branchNum);
				if (data == null) {
					data = new OrderDetailCompare();
					data.setBranchNum(branchNum);
					map.put(branchNum, data);
				}
				data.setLastAdjustMoney(money);
			}

		}

		List<Object[]> outThisObjects = transferOutOrderDao.findBranchSummary(systemBookCode,
				AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM, branchNums, thisDateFrom, thisDateTo);
		List<Object[]> outLastObjects = transferOutOrderDao.findBranchSummary(systemBookCode,
				AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM, branchNums, lastDateFrom, lastDateTo);
		for (int i = 0; i < outThisObjects.size(); i++) {
			Object[] object = outThisObjects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			OrderDetailCompare data = map.get(branchNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setThisInMoney(money);
		}

		for (int i = 0; i < outLastObjects.size(); i++) {
			Object[] object = outLastObjects.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			OrderDetailCompare data = map.get(branchNum);
			if (data == null) {
				data = new OrderDetailCompare();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setLastInMoney(money);
		}

		BigDecimal hundred = BigDecimal.valueOf(100);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<OrderDetailCompare> list = new ArrayList<OrderDetailCompare>(map.values());
		for (int i = 0; i < list.size(); i++) {
			OrderDetailCompare orderDetailCompare = list.get(i);
			Branch branch = AppUtil.getBranch(branchs, orderDetailCompare.getBranchNum());
			if (branch != null) {
				orderDetailCompare.setBranchName(branch.getBranchName());
				orderDetailCompare.setBranchRegionNum(branch.getBranchRegionNum());
			}
			BigDecimal thisProfit = orderDetailCompare.getThisSellMoney().subtract(orderDetailCompare.getThisInMoney());
			orderDetailCompare.setThisProfitMoney(thisProfit);
			if (orderDetailCompare.getThisSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setThisProfitRate(thisProfit
						.divide(orderDetailCompare.getThisSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));

			}
			BigDecimal lastProfit = orderDetailCompare.getLastSellMoney().subtract(orderDetailCompare.getLastInMoney());
			orderDetailCompare.setLastProfitMoney(lastProfit);
			if (orderDetailCompare.getLastSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setLastProfitRate(lastProfit
						.divide(orderDetailCompare.getLastSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));

			}

			orderDetailCompare.setProfitMoneyGrowthRate("100%");
			orderDetailCompare.setProfitMoneyGrowthRateValue(hundred);
			BigDecimal subProfit = orderDetailCompare.getThisProfitMoney().subtract(
					orderDetailCompare.getLastProfitMoney());
			if (orderDetailCompare.getLastProfitMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setProfitMoneyGrowthRateValue(subProfit
						.divide(orderDetailCompare.getLastProfitMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setProfitMoneyGrowthRate(orderDetailCompare.getProfitMoneyGrowthRateValue()
						+ "%");
			}
			orderDetailCompare.setProfitGrowthRate(orderDetailCompare.getThisProfitRate()
					.subtract(orderDetailCompare.getLastProfitRate()).setScale(2, BigDecimal.ROUND_HALF_UP)
					+ "%");

			orderDetailCompare.setSaleProfitGrowthRate("100%");
			orderDetailCompare.setSaleProfitGrowthRateValue(hundred);
			BigDecimal subSaleProfit = orderDetailCompare.getThisSaleProfit().subtract(
					orderDetailCompare.getLastSaleProfit());
			if (orderDetailCompare.getLastSaleProfit().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setSaleProfitGrowthRateValue(subSaleProfit
						.divide(orderDetailCompare.getLastSaleProfit(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setSaleProfitGrowthRate(orderDetailCompare.getSaleProfitGrowthRate()
						+ "%");
			}

			orderDetailCompare.setPriceGrowthRate(hundred);
			BigDecimal price = orderDetailCompare.getThisAvgPrice().subtract(orderDetailCompare.getLastAvgPrice());
			if (orderDetailCompare.getLastAvgPrice().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setPriceGrowthRate(price
						.divide(orderDetailCompare.getLastAvgPrice(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
			}

			orderDetailCompare.setNumGrowthRate("100%");
			orderDetailCompare.setNumGrowthRateValue(hundred);
			BigDecimal numGrowth = orderDetailCompare.getThisSellNum().subtract(orderDetailCompare.getLastSellNum());
			if (orderDetailCompare.getLastSellNum().compareTo(BigDecimal.ZERO) > 0) {
				
				orderDetailCompare.setNumGrowthRateValue(numGrowth
						.divide(orderDetailCompare.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(2));
				orderDetailCompare.setNumGrowthRate(orderDetailCompare.getNumGrowthRateValue() + "%");

			}

			orderDetailCompare.setMoneyGrowthRate("100%");
			orderDetailCompare.setMoneyGrowthRateValue(hundred);
			BigDecimal moneyGrowth = orderDetailCompare.getThisSellMoney().subtract(
					orderDetailCompare.getLastSellMoney());
			if (orderDetailCompare.getLastSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				
				orderDetailCompare.setMoneyGrowthRateValue(moneyGrowth
						.divide(orderDetailCompare.getLastSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setMoneyGrowthRate(orderDetailCompare.getMoneyGrowthRateValue()
						+ "%");

			}

			orderDetailCompare.setAdjustRate("100%");
			orderDetailCompare.setAdjustRateValue(hundred);
			if (orderDetailCompare.getThisSellMoney().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setAdjustRateValue(orderDetailCompare.getThisAdjustMoney()
						.divide(orderDetailCompare.getThisSellMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setAdjustRate(orderDetailCompare.getAdjustRateValue()
						+ "%");

			}
			BigDecimal subInMoney = orderDetailCompare.getThisInMoney().subtract(orderDetailCompare.getLastInMoney());
			if (orderDetailCompare.getLastInMoney().compareTo(BigDecimal.ZERO) > 0) {
				
				orderDetailCompare.setInGrowthRateValue(subInMoney
						.divide(orderDetailCompare.getLastInMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setInGrowthRate(orderDetailCompare.getInGrowthRateValue()
						+ "%");
			} else if (subInMoney.compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setInGrowthRate("100%");
				orderDetailCompare.setInGrowthRateValue(hundred);
				
			} else {
				orderDetailCompare.setInGrowthRate("0%");
				orderDetailCompare.setInGrowthRateValue(BigDecimal.ZERO);
				
			}
		}
		return list;
	}

	@Override
	public List<TransferGoal> findTransferSaleGoalByDate(String systemBookCode, Integer centerBranchNum,
			List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = posOrderDao.findBranchSumByDateType(systemBookCode, branchNums, dateFrom, dateTo,
				dateType);
		List<BranchTransferGoals> branchTransferGoals = branchTransferGoalsDao.findByDate(systemBookCode, branchNums,
				dateFrom, dateTo, dateType);
		Map<String, TransferGoal> map = new HashMap<String, TransferGoal>();
		for (int i = 0; i < branchTransferGoals.size(); i++) {
			BranchTransferGoals branchTransferGoal = branchTransferGoals.get(i);
			TransferGoal data = new TransferGoal();
			data.setBranchNum(branchTransferGoal.getId().getBranchNum());
			data.setTransferAmount(BigDecimal.ZERO);
			data.setTransferDiffAmount(BigDecimal.ZERO);
			data.setTransferGoal(branchTransferGoal.getBranchTransferSaleValue() == null ? BigDecimal.ZERO
					: branchTransferGoal.getBranchTransferSaleValue());
			data.setYearMonth(branchTransferGoal.getId().getBranchTransferInterval());
			data.setTransferDiffGoal(branchTransferGoal.getBranchTransferDiffValue() == null ? BigDecimal.ZERO
					: branchTransferGoal.getBranchTransferDiffValue());
			data.setSaleProfitGoal(branchTransferGoal.getBranchTransferGrossValue() == null?BigDecimal.ZERO:(BigDecimal)branchTransferGoal.getBranchTransferGrossValue());
			map.put(data.getBranchNum() + "|" + data.getYearMonth(), data);
		}

		BigDecimal profit = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String date = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			profit = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			TransferGoal data = map.get(branchNum + "|" + date);
			if (data == null) {
				data = new TransferGoal();
				data.setTransferGoal(BigDecimal.ZERO);
				data.setTransferAmount(money);
				data.setBranchNum(branchNum);
				data.setYearMonth(date);
				data.setSaleProfitMoney(profit);
				map.put(branchNum + "|" + date, data);
			} else {
				data.setTransferAmount(money);
				data.setSaleProfitMoney(profit);
			}
			if (data.getTransferGoal().compareTo(BigDecimal.ZERO) == 0) {
				data.setTransferRate(BigDecimal.ZERO);
			} else {
				data.setTransferRate(data.getTransferAmount()
						.divide(data.getTransferGoal(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
		}
		objects = transferOutOrderDao.findBranchSumByDateType(systemBookCode, centerBranchNum, branchNums, dateFrom,
				dateTo, dateType, null, null);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String date = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			TransferGoal data = map.get(branchNum + "|" + date);
			if (data == null) {
				data = new TransferGoal();
				data.setTransferGoal(BigDecimal.ZERO);
				data.setTransferDiffAmount(money);
				data.setBranchNum(branchNum);
				data.setYearMonth(date);
				map.put(branchNum + "|" + date, data);
			} else {
				data.setTransferDiffAmount(money);
			}

		}

		objects = transferInOrderDao.findBranchSumByDateType(systemBookCode, centerBranchNum, branchNums, dateFrom,
				dateTo, dateType);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String date = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			TransferGoal data = map.get(branchNum + "|" + date);
			if (data == null) {
				data = new TransferGoal();
				data.setTransferGoal(BigDecimal.ZERO);
				data.setTransferDiffAmount(money.negate());
				data.setBranchNum(branchNum);
				data.setYearMonth(date);
				map.put(branchNum + "|" + date, data);
			} else {
				data.setTransferDiffAmount(data.getTransferDiffAmount().subtract(money));
			}
		}
		List<TransferGoal> transferGoals = new ArrayList<TransferGoal>(map.values());
		for (int i = 0; i < transferGoals.size(); i++) {
			TransferGoal transferGoal = transferGoals.get(i);

			transferGoal.setTransferDiffAmount(transferGoal.getTransferAmount().subtract(
					transferGoal.getTransferDiffAmount()));

			if (transferGoal.getTransferDiffGoal().compareTo(BigDecimal.ZERO) == 0) {
				transferGoal.setDeliverDiffRate(BigDecimal.ZERO);
			} else {
				transferGoal.setDeliverDiffRate(transferGoal.getTransferDiffAmount()
						.divide(transferGoal.getTransferDiffGoal(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
		}

		return transferGoals;
	}

	@Override
	public List<ElecTicketDTO> findElecTicketDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {
		return reportDao.findElecTicketDTOs(elecTicketQueryDTO);
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, String type, String paymentType, Boolean queryAll) {
		List<AlipayDetailDTO> list = new ArrayList<AlipayDetailDTO>();
		String paymentTypes = paymentType;
		if (StringUtils.isEmpty(paymentTypes)) {
			paymentTypes = AppConstants.PAY_TYPE_ALIPAY + "," + AppConstants.PAY_TYPE_WEIXINPAY + ","
					+ AppConstants.PAY_TYPE_DPHUI;
		}
		String alipayLogTypes = "";
		if (StringUtils.isEmpty(paymentType)) {
			alipayLogTypes = AppConstants.ALIPAY_CREATE_BY_BARCODE + "," + AppConstants.ALIPAY_PRE_CREATE_BY_QR + ","
					+ AppConstants.WEIXINPAY_CREATE_BY_BARCODE + "," + AppConstants.WEIXINPAY_CREATE_BY_QRCODE + ","
					+ AppConstants.DP_HUI_BY_BARCODE + "," + AppConstants.DP_HUI_BY_QR + ",";
		} else if (StringUtils.equals(paymentType, AppConstants.PAY_TYPE_ALIPAY)) {
			alipayLogTypes = AppConstants.ALIPAY_CREATE_BY_BARCODE + "," + AppConstants.ALIPAY_PRE_CREATE_BY_QR + ",";
		} else if (StringUtils.equals(paymentType, AppConstants.PAY_TYPE_WEIXINPAY)) {
			alipayLogTypes = AppConstants.WEIXINPAY_CREATE_BY_BARCODE + "," + AppConstants.WEIXINPAY_CREATE_BY_QRCODE
					+ ",";
		} else if (StringUtils.equals(paymentType, AppConstants.PAY_TYPE_DPHUI)) {
			alipayLogTypes = AppConstants.DP_HUI_BY_BARCODE + "," + AppConstants.DP_HUI_BY_QR + ",";
		} else {
			alipayLogTypes = paymentType;
		}

		if (StringUtils.isEmpty(type)) {
			list.addAll(reportDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, paymentTypes));
			list.addAll(alipayLogDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "DEP," + AppConstants.S_Prefix_WD + ",member",
					alipayLogTypes));
			if(queryAll){
				list.addAll(alipayLogDao.findCancelAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, null,
						alipayLogTypes));
			}
		} else if (type.equals("POS消费")) {
			list.addAll(reportDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, paymentTypes));
			if(queryAll){
				list.addAll(alipayLogDao.findCancelAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "POS",
						alipayLogTypes));
			}
			
		} else if (type.equals("POS存款")) {
			list.addAll(alipayLogDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "DEP",
					alipayLogTypes));
			if(queryAll){
				list.addAll(alipayLogDao.findCancelAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "DEP",
						alipayLogTypes));
			}
		} else if (type.equals("微会员存款")) {
			list.addAll(alipayLogDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "member",
					alipayLogTypes));
			if(queryAll){
				list.addAll(alipayLogDao.findCancelAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, "member",
						alipayLogTypes));
			}
		} else if (type.equals("微店消费")) {
			list.addAll(alipayLogDao.findAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.S_Prefix_WD,
					alipayLogTypes));
			if(queryAll){
				list.addAll(alipayLogDao.findCancelAlipayDetailDTOs(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.S_Prefix_WD,
						alipayLogTypes));
			}
		}

		if (list.size() == 0) {
			return list;
		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < list.size(); i++) {
			AlipayDetailDTO alipayDetailDTO = list.get(i);

			Branch branch = AppUtil.getBranch(branchs, alipayDetailDTO.getBranchNum());
			if (branch != null) {
				alipayDetailDTO.setBranchName(branch.getBranchName());
			}
		}
		return list;
	}

	@Override
	public List<CustomerAnalysisDay> findCustomerAnalysisShiftTables(String systemBookCode, Date dateFrom, Date dateTo,
			List<Integer> branchNums, String appUserName, String saleType) {
		List<Object[]> objects = reportDao.findCustomerAnalysisShiftTables(systemBookCode, dateFrom, dateTo,
				branchNums, appUserName, saleType);
		List<CustomerAnalysisDay> list = new ArrayList<CustomerAnalysisDay>();
		List<String> shiftTableBizdays = new ArrayList<String>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			BigDecimal couponMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal mgrDiscount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];

			CustomerAnalysisDay data = new CustomerAnalysisDay();
			data.setBranchNum((Integer) object[0]);
			data.setShiftTableDate((String) object[1]);
			data.setShiftTableNum((Integer) object[2]);
			data.setTotalMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			data.setTotalMoney(data.getTotalMoney().add(couponMoney).subtract(mgrDiscount));
			data.setCustomerNums(BigDecimal.valueOf((Long) object[4]));
			data.setCustomerAvePrice(BigDecimal.ZERO);
			if (data.getCustomerNums().compareTo(BigDecimal.ZERO) > 0) {
				data.setCustomerAvePrice(data.getTotalMoney().divide(data.getCustomerNums(), 4,
						BigDecimal.ROUND_HALF_UP));
			}
			data.setCusotmerVipNums(object[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[7]));
			data.setCustomerVipMoney(object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]);
			if (!shiftTableBizdays.contains(data.getShiftTableDate())) {
				shiftTableBizdays.add(data.getShiftTableDate());
			}
			list.add(data);
		}
		if (shiftTableBizdays.size() == 0) {
			return list;
		}
		List<ShiftTable> shiftTables = shiftTableDao.find(systemBookCode, branchNums, shiftTableBizdays);
		for (int i = 0; i < list.size(); i++) {
			CustomerAnalysisDay customerAnalysisDay = list.get(i);

			ShiftTable shiftTable = ShiftTable.getShiftTable(shiftTables, customerAnalysisDay.getBranchNum(),
					customerAnalysisDay.getShiftTableDate(), customerAnalysisDay.getShiftTableNum());
			if (shiftTable != null) {
				customerAnalysisDay.setShiftTableStart(shiftTable.getShiftTableStart());
				customerAnalysisDay.setShiftTableEnd(shiftTable.getShiftTableEnd());
				customerAnalysisDay.setShiftTableUser(shiftTable.getShiftTableUserName());
			}
		}
		return list;
	}

	@Override
	public List<ReportDTO> findReportDTOs(ReportDTO queryReportDTO, CustomReport customReport) {
		List<Object[]> objects = reportDao.findReportDTOs(queryReportDTO, customReport);
		String[] columnNames = customReport.getCustomReportColumn().split(",");
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			ReportDTO reportDTO = new ReportDTO();
			for (int j = 0; j < columnNames.length; j++) {
				reportDTO.set(columnNames[j], object[j]);
			}

			list.add(reportDTO);
		}
		return list;
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitBySupplier(WholesaleProfitQuery wholesaleProfitQuery) {
		String systemBookCode = wholesaleProfitQuery.getSystemBookCode();

		Map<String, WholesaleProfitByClient> map = new HashMap<String, WholesaleProfitByClient>();
		List<Supplier> suppliers = supplierService.findInCache(systemBookCode);
		List<StoreItemSupplier> storeItemSuppliers = storeItemSupplierDao.findByItemNums(systemBookCode, null, null);
		List<Object[]> saleObjects = wholesaleOrderDao.findMoneyGroupByItemNum(wholesaleProfitQuery);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		StoreItemSupplier storeItemSupplier = null;
		PosItem posItem = null;
		Integer itemNum = null;
		BigDecimal qty = null;
		BigDecimal money = null;
		BigDecimal costMoney = null;
		BigDecimal saleMoney = null;
		BigDecimal useQty = null;
		BigDecimal presentQty = null;
		BigDecimal presentUseQty = null;
		BigDecimal presentCostMoney = null;
		BigDecimal presentMoney = null;
		for (int i = 0; i < saleObjects.size(); i++) {
			Object[] object = saleObjects.get(i);
			itemNum = (Integer) object[0];
			qty = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			useQty = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			presentQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentUseQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentCostMoney = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			
			storeItemSupplier = AppUtil.getPreStoreItemSupplier(storeItemSuppliers, systemBookCode, null, itemNum);
			if (storeItemSupplier == null) {
				continue;
			}

			posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}

			WholesaleProfitByClient data = map.get(storeItemSupplier.getId().getSupplierNum().toString());
			if (data == null) {
				data = new WholesaleProfitByClient();
				data.setFid(storeItemSupplier.getId().getSupplierNum().toString());
				data.setWholesaleMoney(BigDecimal.ZERO);
				data.setWholesaleCost(BigDecimal.ZERO);
				data.setRetailPrice(BigDecimal.ZERO);
				data.setWholesaleQty(BigDecimal.ZERO);
				data.setWholesaleUseQty(BigDecimal.ZERO);
				data.setWholesaleBaseQty(BigDecimal.ZERO);
				data.setPresentQty(BigDecimal.ZERO);
				data.setPresentUseQty(BigDecimal.ZERO);
				data.setPresentCostMoney(BigDecimal.ZERO);
				data.setPresentMoney(BigDecimal.ZERO);
				map.put(data.getFid(), data);
			}
			data.setWholesaleBaseQty(data.getWholesaleBaseQty().add(qty));

			if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) > 0) {
				qty = qty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentQty = presentQty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);

			}
			data.setWholesaleQty(data.getWholesaleQty().add(qty));
			data.setWholesaleMoney(data.getWholesaleMoney().add(money));
			data.setWholesaleCost(data.getWholesaleCost().add(costMoney));
			data.setRetailPrice(data.getRetailPrice().add(saleMoney));
			data.setWholesaleUseQty(data.getWholesaleUseQty().add(useQty));
			data.setPresentQty(data.getPresentQty().add(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().add(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().add(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().add(presentMoney));
		}

		List<Object[]> returnObjects = wholesaleReturnDao.findMoneyGroupByItemNum(wholesaleProfitQuery);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			itemNum = (Integer) object[0];
			qty = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			useQty = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			presentQty = object[6] == null ? BigDecimal.ZERO : ((BigDecimal) object[6]);
			presentUseQty = object[7] == null ? BigDecimal.ZERO : ((BigDecimal) object[7]);
			presentCostMoney = object[8] == null ? BigDecimal.ZERO : ((BigDecimal) object[8]);
			presentMoney = object[9] == null ? BigDecimal.ZERO : ((BigDecimal) object[9]);
			
			storeItemSupplier = AppUtil.getPreStoreItemSupplier(storeItemSuppliers, systemBookCode, null, itemNum);
			if (storeItemSupplier == null) {
				continue;
			}
			posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			WholesaleProfitByClient data = map.get(storeItemSupplier.getId().getSupplierNum().toString());
			if (data == null) {
				data = new WholesaleProfitByClient();
				data.setFid(storeItemSupplier.getId().getSupplierNum().toString());
				data.setWholesaleMoney(BigDecimal.ZERO);
				data.setWholesaleCost(BigDecimal.ZERO);
				data.setRetailPrice(BigDecimal.ZERO);
				data.setWholesaleQty(BigDecimal.ZERO);
				data.setWholesaleUseQty(BigDecimal.ZERO);
				data.setWholesaleBaseQty(BigDecimal.ZERO);
				data.setPresentQty(BigDecimal.ZERO);
				data.setPresentUseQty(BigDecimal.ZERO);
				data.setPresentCostMoney(BigDecimal.ZERO);
				data.setPresentMoney(BigDecimal.ZERO);
				map.put(data.getFid(), data);
			}

			data.setWholesaleBaseQty(data.getWholesaleBaseQty().add(qty));
			if (posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) > 0) {
				qty = qty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
				presentQty = presentQty.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);

			}

			data.setWholesaleQty(data.getWholesaleQty().subtract(qty));
			data.setWholesaleMoney(data.getWholesaleMoney().subtract(money));
			data.setWholesaleCost(data.getWholesaleCost().subtract(costMoney));
			data.setRetailPrice(data.getRetailPrice().subtract(saleMoney));
			data.setWholesaleUseQty(data.getWholesaleUseQty().subtract(useQty));
			data.setPresentQty(data.getPresentQty().subtract(presentQty));
			data.setPresentUseQty(data.getPresentUseQty().subtract(presentUseQty));
			data.setPresentCostMoney(data.getPresentCostMoney().subtract(presentCostMoney));
			data.setPresentMoney(data.getPresentMoney().subtract(presentMoney));
		}

		List<WholesaleProfitByClient> list = new ArrayList<WholesaleProfitByClient>(map.values());
		for (int i = list.size() - 1; i >= 0; i--) {
			WholesaleProfitByClient data = list.get(i);
			Supplier supplier = AppUtil.getSupplier(Integer.parseInt(data.getFid()), suppliers);
			if (supplier == null) {
				list.remove(i);
				continue;
			}
			if (StringUtils.isNotEmpty(wholesaleProfitQuery.getMethod())) {
				if (!StringUtils.equals(wholesaleProfitQuery.getMethod(), supplier.getSupplierMethod())) {
					list.remove(i);
					continue;
				}
			}
		}
		return list;
	}

	@Override
	public List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {
		List<TicketUseAnalysisDTO> list = reportDao.findTicketUseAnalysisDTOs(elecTicketQueryDTO);
		List<Branch> branchs = branchService.findInCache(elecTicketQueryDTO.getSystemBookCode());
		for (int i = 0; i < list.size(); i++) {
			TicketUseAnalysisDTO ticketUseAnalysisDTO = list.get(i);
			Branch branch = AppUtil.getBranch(branchs, ticketUseAnalysisDTO.getBranchNum());
			if (branch != null) {
				ticketUseAnalysisDTO.setBranchName(branch.getBranchName());
			}

		}
		return list;
	}

	@Override
	public List<PackageSumDTO> findPackageSum(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums,
			Date dateFrom, Date dateTo) {
		List<PackageSumDTO> packageSumDTOs = reportDao.findPackageSum(systemBookCode, branchNums, dateFrom, dateTo);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		for (int i = 0; i < packageSumDTOs.size(); i++) {
			PackageSumDTO packageSumDTO = packageSumDTOs.get(i);

			PosItem posItem = AppUtil.getPosItem(packageSumDTO.getItemNum(), posItems);
			if (posItem != null) {
				packageSumDTO.setItemCode(posItem.getItemCode());
				packageSumDTO.setItemName(posItem.getItemName());
			}
		}
		return packageSumDTOs;
	}

	@Override
	public List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer itemNum, Integer selectCount) {
		return reportDao.findItemRelatedItemRanks(systemBookCode, branchNums, dateFrom, dateTo, itemNum, selectCount);
	}

	@Override
	public List<CarriageCostDTO> findCarriageCosts(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo, List<Integer> transferLineNums) {
		Map<Integer, CarriageCostDTO> map = new HashMap<Integer, CarriageCostDTO>();
		List<TransferLine> transferLines = transferLineDao.findByBranch(systemBookCode, branchNum);
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		BigDecimal diffDay = BigDecimal.valueOf(DateUtil.diffDay(dateFrom, dateTo));
		for (int i = 0; i < transferLines.size(); i++) {
			TransferLine transferLine = transferLines.get(i);

			if (transferLineNums != null && transferLineNums.size() > 0) {
				if (!transferLineNums.contains(transferLine.getTransferLineNum())) {
					continue;
				}
			}

			CarriageCostDTO carriageCostDTO = new CarriageCostDTO();
			carriageCostDTO.setTransferLineCode(transferLine.getTransferLineCode());
			carriageCostDTO.setTransferLineName(transferLine.getTransferLineName());
			carriageCostDTO.setTransferLineNum(transferLine.getTransferLineNum());
			carriageCostDTO.setBranchCount(transferLine.getTransferLineDetails().size());

			map.put(carriageCostDTO.getTransferLineNum(), carriageCostDTO);

		}

		List<Object[]> objects = shipOrderDao.findLineSummary(systemBookCode, branchNum, transferLineNums, dateFrom,
				dateTo);
		BigDecimal carriageCostTotalSum = BigDecimal.ZERO;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			CarriageCostDTO carriageCostDTO = map.get(object[0]);
			if (carriageCostDTO != null) {
				carriageCostDTO.setCarriageCostTotal(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
				carriageCostTotalSum = carriageCostTotalSum.add(carriageCostDTO.getCarriageCostTotal());

			}
		}

		objects = shipOrderDao.findLineCount(systemBookCode, branchNum, transferLineNums, dateFrom, dateTo);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			CarriageCostDTO carriageCostDTO = map.get(object[0]);
			if (carriageCostDTO != null) {
				carriageCostDTO.setCarriageCount(object[1] == null ? 0 : (Integer) object[1]);

			}
		}

		objects = transferOutOrderDao.findLineSummary(systemBookCode, branchNum, transferLineNums, dateFrom, dateTo);
		BigDecimal carriageMoneySum = BigDecimal.ZERO;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			CarriageCostDTO carriageCostDTO = map.get(object[0]);
			if (carriageCostDTO != null) {
				carriageCostDTO.setCarriageMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
				carriageCostDTO.setCarriageQty(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
				carriageMoneySum = carriageMoneySum.add(carriageCostDTO.getCarriageMoney());

			}

		}

		List<CarriageCostDTO> list = new ArrayList<CarriageCostDTO>(map.values());
		for (int i = 0; i < list.size(); i++) {
			CarriageCostDTO carriageCostDTO = list.get(i);

			if (carriageMoneySum.compareTo(BigDecimal.ZERO) > 0) {
				carriageCostDTO.setCarriageMoneyRate(carriageCostDTO.getCarriageMoney().divide(carriageMoneySum, 4,
						BigDecimal.ROUND_HALF_UP));
				carriageCostDTO.setTotalCarriageRate(carriageCostDTO.getCarriageCostTotal().divide(carriageMoneySum, 4,
						BigDecimal.ROUND_HALF_UP));

			}

			if (carriageCostTotalSum.compareTo(BigDecimal.ZERO) > 0) {
				carriageCostDTO.setCarriageRate(carriageCostDTO.getCarriageCostTotal().divide(carriageCostTotalSum, 4,
						BigDecimal.ROUND_HALF_UP));
			}
			BigDecimal value = diffDay.multiply(BigDecimal.valueOf(carriageCostDTO.getBranchCount()));
			if (value.compareTo(BigDecimal.ZERO) > 0) {
				carriageCostDTO.setBranchCarriageCostDay(carriageCostDTO.getCarriageCostTotal().divide(value, 2,
						BigDecimal.ROUND_HALF_UP));
			}
			if (carriageCostDTO.getCarriageMoney().compareTo(BigDecimal.ZERO) > 0) {
				carriageCostDTO.setLineCarriageRate(carriageCostDTO.getCarriageCostTotal().divide(
						carriageCostDTO.getCarriageMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return list;
	}

	@Override
	public List<CenterBoxReportDTO> findCenterBoxReportDTOs(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo) {
		return reportDao.findCenterBoxReportDTOs(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public Integer countByClientOverDue(String systemBookCode, Integer branchNum) {

		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		List<Object[]> wholesaleReturnList = wholesaleReturnDao.findDueMoney(systemBookCode, branchNum, null, null,
				null);
		List<Object[]> otherInoutList = otherInoutDao.findClientsMoney(systemBookCode, branchNums, null, null, null);
		List<Object[]> paymentList = posOrderDao.findClientsMoney(systemBookCode, branchNum, null, null, null);
		List<Object[]> clientPreSettlementList = clientPreSettlementDao.findDueMoney(systemBookCode, branchNum, null,
				null, null);
		HashMap<String, BigDecimal> totalDueMoney = new HashMap<String, BigDecimal>();
		String clientFid = null;
		BigDecimal dueMoney = null;
		List<Object[]> wholesaleOrderList = wholesaleOrderDao.findDueMoney(systemBookCode, branchNum, null, null, null,
				null);
		for (int i = 0; i < wholesaleOrderList.size(); i++) {
			Object[] wholesaleOrder = wholesaleOrderList.get(i);
			clientFid = (String) wholesaleOrder[0];
			dueMoney = (BigDecimal) wholesaleOrder[1];
			if (dueMoney.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			if (totalDueMoney.containsKey(clientFid)) {
				BigDecimal newValue = totalDueMoney.get(clientFid);
				totalDueMoney.put(clientFid, newValue.add(dueMoney));
			} else {
				totalDueMoney.put(clientFid, dueMoney);
			}
		}
		for (int i = 0; i < wholesaleReturnList.size(); i++) {
			Object[] wholesaleReturn = wholesaleReturnList.get(i);
			clientFid = (String) wholesaleReturn[0];
			dueMoney = (BigDecimal) wholesaleReturn[1];
			if (dueMoney.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			if (totalDueMoney.containsKey(clientFid)) {
				BigDecimal newValue = totalDueMoney.get(clientFid);
				totalDueMoney.put(clientFid, newValue.add(dueMoney));
			} else {
				totalDueMoney.put(clientFid, dueMoney);
			}
		}
		for (int i = 0; i < otherInoutList.size(); i++) {
			Object[] otherInout = otherInoutList.get(i);
			clientFid = (String) otherInout[0];
			dueMoney = (BigDecimal) otherInout[3];

			if (totalDueMoney.containsKey(clientFid)) {
				BigDecimal newValue = totalDueMoney.get(clientFid);
				totalDueMoney.put(clientFid, newValue.add(dueMoney));
			} else {
				totalDueMoney.put(clientFid, dueMoney);
			}
		}
		for (int i = 0; i < paymentList.size(); i++) {
			Object[] payment = paymentList.get(i);
			clientFid = (String) payment[0];
			dueMoney = (BigDecimal) payment[2];
			if (dueMoney.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			if (totalDueMoney.containsKey(clientFid)) {
				BigDecimal newValue = totalDueMoney.get(clientFid);
				totalDueMoney.put(clientFid, newValue.add(dueMoney));
			} else {
				totalDueMoney.put(clientFid, dueMoney);
			}
		}
		for (int i = 0; i < clientPreSettlementList.size(); i++) {
			Object[] clientPreSettlement = clientPreSettlementList.get(i);
			clientFid = (String) clientPreSettlement[0];
			dueMoney = clientPreSettlement[1] == null?BigDecimal.ZERO:(BigDecimal) clientPreSettlement[1];
			if (dueMoney.compareTo(BigDecimal.ZERO) <= 0)
				continue;
			if (totalDueMoney.containsKey(clientFid)) {
				BigDecimal newValue = totalDueMoney.get(clientFid);
				totalDueMoney.put(clientFid, newValue.add(dueMoney));
			} else {
				totalDueMoney.put(clientFid, dueMoney);
			}
		}
		List<PosClient> posClients = posClientService.findInCache(systemBookCode);
		int totalDueCount = 0;
		for (int i = 0; i < posClients.size(); i++) {
			if (posClients.get(i).getClientCreditLimit().compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			dueMoney = totalDueMoney.get(posClients.get(i).getClientFid());
			if (dueMoney != null && dueMoney.compareTo(posClients.get(i).getClientCreditLimit()) > 0) {
				totalDueCount++;
			}
		}
		return new Integer(totalDueCount);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByBranchBizday' + #p0.getKey()")
	public List<Object[]> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData) {
		return reportDao.findSaleAnalysisByBranchBizday(saleAnalysisQueryData);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer cardUserCardType) {
		List<CardReportDTO> cardReportDTOs = new ArrayList<CardReportDTO>();
		List<Object[]> cardSendObjects = cardUserDao.findCardCount(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		List<Object[]> cardRevokeObjects = cardUserDao.findRevokeCardCount(systemBookCode, branchNums, dateFrom,
				dateTo, cardUserCardType);

		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < cardSendObjects.size(); i++) {
			Object[] sendObject = cardSendObjects.get(i);
			CardReportDTO cardReportDTO = new CardReportDTO();
			cardReportDTO.setBranchNum((Integer) sendObject[0]);
			cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) sendObject[0]).getBranchName());
			cardReportDTOs.add(cardReportDTO);
			cardReportDTO.setSendCardCount((Integer) sendObject[1]);
		}
		for (int i = 0; i < cardRevokeObjects.size(); i++) {
			Object[] revokeObject = cardRevokeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) revokeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) revokeObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) revokeObject[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setReturnCardCount(((Long) revokeObject[1]).intValue());
			cardReportDTO.setReturnCardMoney(revokeObject[2] == null?BigDecimal.ZERO:(BigDecimal)revokeObject[2]);
		}
		
		List<Object[]> cardDepositObjects = cardDepositDao.findBranchPaymentTypeSum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		String paymentType = null; 
		for (int i = 0; i < cardDepositObjects.size(); i++) {
			
			Object[] depositObject = cardDepositObjects.get(i);
			paymentType = (String) depositObject[1];
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) depositObject[0], cardReportDTOs);
			if (cardReportDTO == null) {    
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) depositObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) depositObject[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			
			if(paymentType.equals(AppConstants.PAYMENT_ORI)){
				cardReportDTO.setOriDepositMoney(cardReportDTO.getOriDepositMoney().add((BigDecimal) depositObject[3]));
			} else {

				cardReportDTO.setPaymentMoney(cardReportDTO.getPaymentMoney().add((BigDecimal) depositObject[2]));
				cardReportDTO.setDepositMoney(cardReportDTO.getDepositMoney().add((BigDecimal) depositObject[3]));
				cardReportDTO.setSendMoney(cardReportDTO.getDepositMoney().subtract(cardReportDTO.getPaymentMoney()));
			}
		}
		List<Object[]> cardConsumeObjects = cardConsumeDao.findBranchCategorySum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		for (int i = 0; i < cardConsumeObjects.size(); i++) {
			Object[] consumeObject = cardConsumeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) consumeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) consumeObject[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) consumeObject[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setConsumeMoney(cardReportDTO.getConsumeMoney().add(AppUtil.getValue(consumeObject[2], BigDecimal.class)));
			if(AppUtil.getValue(consumeObject[1], Integer.class) == 2) {
				cardReportDTO.setOnlineConsumeMoney(AppUtil.getValue(consumeObject[2], BigDecimal.class));
			}
		}
		
		List<Object[]> objects = cardUserLogDao.findBranchCount(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.CARD_USER_LOG_TYPE_CHANGE_STORGE,
				cardUserCardType);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranch((Integer) object[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) object[0]);
				cardReportDTO.setBranchName(AppUtil.getBranch(branchs, (Integer) object[0]).getBranchName());
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setChangeEleCardCount(object[1] == null?0:(Integer)object[1]);
		}
		return cardReportDTOs;
	}

	@Override
	public List<CardReportDTO> findCardReportByDay(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, Integer cardUserCardType) {
		List<CardReportDTO> cardReportDTOs = new ArrayList<CardReportDTO>();
		List<Object[]> cardSendObjects = cardUserDao.findCardCountByBizday(systemBookCode, branchNums, dateFrom,
				dateTo, cardUserCardType);
		List<Object[]> cardDepositObjects = cardDepositDao.findBizdayPaymentTypeSum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		List<Object[]> cardConsumeObjects = cardConsumeDao.findBizdayCategorySum(systemBookCode, branchNums, dateFrom, dateTo,
				cardUserCardType);
		for (int i = 0; i < cardSendObjects.size(); i++) {
			Object[] sendObject = cardSendObjects.get(i);
			CardReportDTO cardReportDTO = new CardReportDTO();
			cardReportDTO.setBizDay((String) sendObject[0]);
			cardReportDTOs.add(cardReportDTO);
			cardReportDTO.setSendCardCount(((Long) sendObject[1]).intValue());
		}
		List<Object[]> cardRevokeObjects = cardUserDao.findRevokeCardCountByBizday(systemBookCode, branchNums,
				dateFrom, dateTo, cardUserCardType);
		for (int i = 0; i < cardRevokeObjects.size(); i++) {
			Object[] revokeObject = cardRevokeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBizday((String) revokeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBizDay((String) revokeObject[0]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setReturnCardCount(((Long) revokeObject[1]).intValue());
			cardReportDTO.setReturnCardMoney(revokeObject[2] == null?BigDecimal.ZERO:(BigDecimal)revokeObject[2]);

		}
		
		String paymentType = null;
		for (int i = 0; i < cardDepositObjects.size(); i++) {
			Object[] depositObject = cardDepositObjects.get(i);
			paymentType = (String) depositObject[1];
			CardReportDTO cardReportDTO = CardReportDTO.readByBizday((String) depositObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBizDay((String) depositObject[0]);
				cardReportDTOs.add(cardReportDTO);
			}
			if(paymentType.equals(AppConstants.PAYMENT_ORI)){
				cardReportDTO.setOriDepositMoney(cardReportDTO.getOriDepositMoney().add((BigDecimal) depositObject[3]));
			} else {
				cardReportDTO.setPaymentMoney(cardReportDTO.getPaymentMoney().add((BigDecimal) depositObject[2]));
				cardReportDTO.setDepositMoney(cardReportDTO.getDepositMoney().add((BigDecimal) depositObject[3]));
				cardReportDTO.setSendMoney(cardReportDTO.getDepositMoney().subtract(cardReportDTO.getPaymentMoney()));
				
			}
		}
		for (int i = 0; i < cardConsumeObjects.size(); i++) {
			Object[] consumeObject = cardConsumeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBizday((String) consumeObject[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBizDay((String) consumeObject[0]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setConsumeMoney(cardReportDTO.getConsumeMoney().add(AppUtil.getValue(consumeObject[2], BigDecimal.class)));
			if(AppUtil.getValue(consumeObject[1], Integer.class) == 2) {
				cardReportDTO.setOnlineConsumeMoney(AppUtil.getValue(consumeObject[2], BigDecimal.class));
			}
		}
		
		
		List<Object[]> objects = cardUserLogDao.findBizdayCount(systemBookCode, branchNums, dateFrom, dateTo, AppConstants.CARD_USER_LOG_TYPE_CHANGE_STORGE,
				cardUserCardType);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			
			CardReportDTO cardReportDTO = CardReportDTO.readByBizday((String) object[0], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBizDay((String) object[0]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setChangeEleCardCount(object[1] == null?0:(Integer)object[1]);
		}
		return cardReportDTOs;
	}

	@Override
	public List<BranchCategoryTransSaleAnalyseDTO> findBranchCategoryTransSaleAnalyseDTOs(String systemBookCode,
			Integer centerBranchNum, List<Integer> branchNums, Date saleDate, Date dateFrom, Date dateTo,
			List<String> categoryCodes, List<Integer> itemNums) {
		List<BranchCategoryTransSaleAnalyseDTO> list = new ArrayList<BranchCategoryTransSaleAnalyseDTO>();
		List<BranchCategoryTransSaleAnalyseDTO> categorySumList = new ArrayList<BranchCategoryTransSaleAnalyseDTO>();
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Integer branchNum = null;
		Integer itemNum = null;
		BigDecimal money = null;
		String categoryCode = null;
		String categoryName = null;

		Map<String, BigDecimal> branchMoneyMap = new HashMap<String, BigDecimal>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		BigDecimal saleMoneySum = BigDecimal.ZERO;
		BigDecimal saleTotalSum = BigDecimal.ZERO;
		List<Object[]> posObjects = posOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo,
				null, false);
		for (int i = 0; i < posObjects.size(); i++) {
			Object[] object = posObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			if (itemNums != null && itemNums.size() > 0) {
				if (!itemNums.contains(itemNum)) {
					continue;
				}
			}

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				categoryCode = "";
				categoryName = "未知类别";
			} else {
				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(categoryCode)) {
					continue;
				}
			}
			BranchCategoryTransSaleAnalyseDTO dto = BranchCategoryTransSaleAnalyseDTO
					.get(list, branchNum, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(branchNum);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
				if (branch != null) {
					dto.setBranchName(branch.getBranchName());
				}
				list.add(dto);
			}
			dto.setSaleTotal(dto.getSaleTotal().add(money));
			saleTotalSum = saleTotalSum.add(money);

			dto = BranchCategoryTransSaleAnalyseDTO.get(categorySumList, 0, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(0);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				dto.setBranchName("小计");
				categorySumList.add(dto);
			}
			dto.setSaleTotal(dto.getSaleTotal().add(money));

			BigDecimal branchMoney = branchMoneyMap.get(branchNum + "total");
			if (branchMoney == null) {
				branchMoney = BigDecimal.ZERO;
			}
			branchMoney = branchMoney.add(money);
			branchMoneyMap.put(branchNum + "total", branchMoney);
		}

		posObjects = posOrderDao.findBranchItemSummary(systemBookCode, branchNums, saleDate, saleDate, null, false);
		for (int i = 0; i < posObjects.size(); i++) {
			Object[] object = posObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			if (itemNums != null && itemNums.size() > 0) {
				if (!itemNums.contains(itemNum)) {
					continue;
				}
			}

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				categoryCode = "";
				categoryName = "未知类别";
			} else {
				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(categoryCode)) {
					continue;
				}
			}
			BranchCategoryTransSaleAnalyseDTO dto = BranchCategoryTransSaleAnalyseDTO
					.get(list, branchNum, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(branchNum);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
				if (branch != null) {
					dto.setBranchName(branch.getBranchName());
				}
				list.add(dto);
			}
			dto.setSaleMoney(dto.getSaleMoney().add(money));

			dto = BranchCategoryTransSaleAnalyseDTO.get(categorySumList, 0, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(0);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				dto.setBranchName("小计");
				categorySumList.add(dto);
			}
			dto.setSaleMoney(dto.getSaleMoney().add(money));
			saleMoneySum = saleMoneySum.add(money);

			BigDecimal branchMoney = branchMoneyMap.get(branchNum.toString());
			if (branchMoney == null) {
				branchMoney = BigDecimal.ZERO;
			}
			branchMoney = branchMoney.add(money);
			branchMoneyMap.put(branchNum.toString(), branchMoney);
		}

		List<Object[]> outObjects = transferOutOrderDao.findBranchItemSummary(systemBookCode, centerBranchNum,
				branchNums, dateFrom, dateTo, null);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			if (itemNums != null && itemNums.size() > 0) {
				if (!itemNums.contains(itemNum)) {
					continue;
				}
			}
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				categoryCode = "";
				categoryName = "未知类别";
			} else {
				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(categoryCode)) {
					continue;
				}
			}
			BranchCategoryTransSaleAnalyseDTO dto = BranchCategoryTransSaleAnalyseDTO
					.get(list, branchNum, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(branchNum);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
				if (branch != null) {
					dto.setBranchName(branch.getBranchName());
				}
				list.add(dto);
			}
			dto.setTransferTotal(dto.getTransferTotal().add(money));

			dto = BranchCategoryTransSaleAnalyseDTO.get(categorySumList, 0, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(0);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				dto.setBranchName("小计");
				categorySumList.add(dto);
			}
			dto.setTransferTotal(dto.getTransferTotal().add(money));
		}

		outObjects = transferOutOrderDao.findBranchItemSummary(systemBookCode, centerBranchNum, branchNums, saleDate,
				saleDate, null);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			if (itemNums != null && itemNums.size() > 0) {
				if (!itemNums.contains(itemNum)) {
					continue;
				}
			}

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				categoryCode = "";
				categoryName = "未知类别";
			} else {
				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(categoryCode)) {
					continue;
				}
			}
			BranchCategoryTransSaleAnalyseDTO dto = BranchCategoryTransSaleAnalyseDTO
					.get(list, branchNum, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(branchNum);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
				if (branch != null) {
					dto.setBranchName(branch.getBranchName());
				}
				list.add(dto);
			}
			dto.setTransferMoney(dto.getTransferMoney().add(money));

			dto = BranchCategoryTransSaleAnalyseDTO.get(categorySumList, 0, categoryCode);
			if (dto == null) {
				dto = new BranchCategoryTransSaleAnalyseDTO();
				dto.setBranchNum(0);
				dto.setCategoryCode(categoryCode);
				dto.setCategoryName(categoryName);
				dto.setBranchName("小计");
				categorySumList.add(dto);
			}
			dto.setTransferMoney(dto.getTransferMoney().add(money));
		}

		// AMA-7586 去除0的
		for (int i = categorySumList.size() - 1; i >= 0; i--) {
			BranchCategoryTransSaleAnalyseDTO categoryDto = categorySumList.get(i);
			if (categoryDto.checkZero()) {
				boolean remain = false;
				for (int j = list.size() - 1; j >= 0; j--) {
					BranchCategoryTransSaleAnalyseDTO dto = list.get(j);
					if (dto.getCategoryCode().equals(categoryDto.getCategoryCode())) {
						if(dto.checkZero()){
							list.remove(j);
							
						} else {
							remain = true;
						}
					}
				}
				if(!remain){
					categorySumList.remove(i);
					
				}
			}

		}

		BigDecimal hundred = BigDecimal.valueOf(100);
		List<String> categories = new ArrayList<String>();
		list.addAll(categorySumList);

		BigDecimal branchSaleMoneySum = null;
		BigDecimal branchSaleTotalSum = null;
		for (int i = 0; i < list.size(); i++) {
			BranchCategoryTransSaleAnalyseDTO dto = list.get(i);
			String category = dto.getCategoryCode() + "," + dto.getCategoryName();
			if (!categories.contains(category)) {
				categories.add(category);
			}

			dto.setProfitMoney(dto.getSaleMoney().subtract(dto.getTransferMoney()));
			dto.setProfitTotal(dto.getSaleTotal().subtract(dto.getTransferTotal()));

			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfitMoney().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(hundred));
			}
			if (dto.getSaleTotal().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitTotalRate(dto.getProfitTotal().divide(dto.getSaleTotal(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(hundred));
			}

			if (dto.getBranchNum() > 0) {
				branchSaleMoneySum = branchMoneyMap.get(dto.getBranchNum().toString());
				branchSaleTotalSum = branchMoneyMap.get(dto.getBranchNum() + "total");
			} else {
				branchSaleMoneySum = saleMoneySum;
				branchSaleTotalSum = saleTotalSum;
			}
			if (branchSaleMoneySum != null && branchSaleMoneySum.compareTo(BigDecimal.ZERO) > 0) {
				dto.setSaleRate(dto.getSaleMoney().divide(branchSaleMoneySum, 4, BigDecimal.ROUND_HALF_UP)
						.multiply(hundred));
			}
			if (branchSaleTotalSum != null && branchSaleTotalSum.compareTo(BigDecimal.ZERO) > 0) {
				dto.setSaleTotalRate(dto.getSaleTotal().divide(branchSaleTotalSum, 4, BigDecimal.ROUND_HALF_UP)
						.multiply(hundred));
			}

		}
		for (int i = 0; i < categories.size(); i++) {
			String[] array = categories.get(i).split(",");
			categoryCode = array[0];
			categoryName = array[1];
			for (int j = 0; j < branchNums.size(); j++) {
				branchNum = branchNums.get(j);
				BranchCategoryTransSaleAnalyseDTO dto = BranchCategoryTransSaleAnalyseDTO.get(list, branchNum,
						categoryCode);
				if (dto == null) {
					dto = new BranchCategoryTransSaleAnalyseDTO();
					dto.setBranchNum(branchNum);
					dto.setCategoryCode(categoryCode);
					dto.setCategoryName(categoryName);
					Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
					if (branch != null) {
						dto.setBranchName(branch.getBranchName());
					}
					list.add(dto);
				}
			}
		}

		return list;
	}

	@Override
	public List<BranchCategoryAnalyseDTO> findBranchCategoryAnalyseDTOs(String systemBookCode, Integer centerBranchNum,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<BranchCategoryAnalyseDTO> list = new ArrayList<BranchCategoryAnalyseDTO>();

		List<Object[]> posObjects = posOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo,
				null, false);
		List<Object[]> outObjects = transferOutOrderDao.findBranchItemSummary(systemBookCode, centerBranchNum,
				branchNums, dateFrom, dateTo, null);

		if (posObjects.size() == 0 && outObjects.size() == 0) {
			return list;
		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < branchNums.size(); i++) {
			BranchCategoryAnalyseDTO branchCategoryAnalyseDTO = new BranchCategoryAnalyseDTO();
			branchCategoryAnalyseDTO.setBranchNum(branchNums.get(i));
			Branch branch = AppUtil.getBranch(branchs, branchCategoryAnalyseDTO.getBranchNum());
			if (branch != null) {
				branchCategoryAnalyseDTO.setBranchName(branch.getBranchName());
			}

			list.add(branchCategoryAnalyseDTO);
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Integer branchNum = null;
		Integer itemNum = null;
		BigDecimal money = null;
		String departmentName = null;
		String category = null;
		TypeAndTwoValuesDTO typeAndTwoValuesDTO = null;
		for (int i = 0; i < posObjects.size(); i++) {
			Object[] object = posObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			BranchCategoryAnalyseDTO branchCategoryAnalyseDTO = BranchCategoryAnalyseDTO.get(list, branchNum);
			branchCategoryAnalyseDTO.setSaleMoney(branchCategoryAnalyseDTO.getSaleMoney().add(money));

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				departmentName = "";
				category = "";
			} else {
				departmentName = posItem.getItemDepartment();
				category = posItem.getItemCategoryCode() + "|" + posItem.getItemCategory();
			}
			// 类别明细
			typeAndTwoValuesDTO = branchCategoryAnalyseDTO.getCategoryValue(category);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(category);
				branchCategoryAnalyseDTO.getCategoryValueDTOs().add(typeAndTwoValuesDTO);
			}

			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));

			// 部门明细
			typeAndTwoValuesDTO = branchCategoryAnalyseDTO.getDepartmentValue(departmentName);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(departmentName);
				branchCategoryAnalyseDTO.getDepartmentValueDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));

		}

		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			BranchCategoryAnalyseDTO branchCategoryAnalyseDTO = BranchCategoryAnalyseDTO.get(list, branchNum);
			branchCategoryAnalyseDTO.setTransferMoney(branchCategoryAnalyseDTO.getTransferMoney().add(money));

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				departmentName = "";
				category = "";
			} else {
				departmentName = posItem.getItemDepartment();
				category = posItem.getItemCategoryCode() + "|" + posItem.getItemCategory();
			}

			// 类别明细
			typeAndTwoValuesDTO = branchCategoryAnalyseDTO.getCategoryValue(category);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(category);
				branchCategoryAnalyseDTO.getCategoryValueDTOs().add(typeAndTwoValuesDTO);
			}

			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));

			// 部门明细
			typeAndTwoValuesDTO = branchCategoryAnalyseDTO.getDepartmentValue(departmentName);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(departmentName);
				branchCategoryAnalyseDTO.getDepartmentValueDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setAmount(typeAndTwoValuesDTO.getAmount().add(money));

		}
		return list;
	}

	@Override
	public List<InventoryAnalysisDTO> findInventoryAnalysiss(InventoryAnalysisQuery inventoryAnalysisQuery,
			ChainDeliveryParam chainDeliveryParam) {
		String systemBookCode = inventoryAnalysisQuery.getSystemBookCode();
		Integer branchNum = inventoryAnalysisQuery.getBranchNum();
		Date now = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
		Date yesterday = DateUtil.addDay(now, -1);
		if (inventoryAnalysisQuery.getSuggestionType() == null) {
			inventoryAnalysisQuery.setSuggestionType(1);
		}
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		Branch branch = branchService.readInCache(systemBookCode, branchNum);
		List<InventoryAnalysisDTO> list = new ArrayList<InventoryAnalysisDTO>();
		List<Inventory> inventories = null;
		List<Integer> itemNums = inventoryAnalysisQuery.getItemNums();
		
		
		//手工添加补货的商品忽略规则
		boolean ignoreRule = false;
		if(itemNums != null && itemNums.size() > 0){
			ignoreRule = true;
		}
		
		if (inventoryAnalysisQuery.getStorehouseNum() == null) {
			inventories = inventoryDao.findByItemAndBranch(systemBookCode, inventoryAnalysisQuery.getBranchNum(), itemNums,
					true);
		} else {
			inventories = inventoryDao.findByStorehouseNum(inventoryAnalysisQuery.getStorehouseNum(), itemNums);
		}
		Comparator<StoreItemSupplier> supplierComparator = new Comparator<StoreItemSupplier>() {

			@Override
			public int compare(StoreItemSupplier arg0, StoreItemSupplier arg1) {
				if (arg0.getStoreItemSupplierPri() > arg1.getStoreItemSupplierPri()) {
					return -1;
				} else if (arg0.getStoreItemSupplierPri() < arg1.getStoreItemSupplierPri()) {
					return 1;
				} else {
					if (arg0.getStoreItemSupplierLastestTime() == null
							&& arg1.getStoreItemSupplierLastestTime() == null) {
						return 0;
					} else if (arg0.getStoreItemSupplierLastestTime() != null
							&& arg1.getStoreItemSupplierLastestTime() == null) {
						return -1;
					} else if (arg0.getStoreItemSupplierLastestTime() == null
							&& arg1.getStoreItemSupplierLastestTime() != null) {
						return 1;
					} else {
						return -arg0.getStoreItemSupplierLastestTime()
								.compareTo(arg1.getStoreItemSupplierLastestTime());
					}
				}
			}
		};

		List<PosItem> posItems = new ArrayList<PosItem>();
		if (inventoryAnalysisQuery.isQueryAssemble()) {
			if(itemNums != null && itemNums.size() > 0){
				List<PosItem> tempPosItems = posItemService.findByItemNums(itemNums);
				List<Integer> manufactureItemNums = new ArrayList<Integer>();
				for (int i = 0; i < tempPosItems.size(); i++) {
					PosItem posItem = tempPosItems.get(i);
					posItem.setPosItemKits(new ArrayList<PosItemKit>());

					if(posItem.getItemManufactureFlag() != null && posItem.getItemManufactureFlag()){
						manufactureItemNums.add(posItem.getItemNum());
					}
				}
				if(manufactureItemNums.size() > 0 ){
					List<PosItemKit> posItemKits = posItemService.findPosItemKitsWithDetails(manufactureItemNums);
					for (int i = 0; i < tempPosItems.size(); i++) {
						PosItem posItem = tempPosItems.get(i);
						posItem.setPosItemKits(PosItemKit.find(posItemKits, posItem.getItemNum()));

					}
				}
				posItems.addAll(tempPosItems);

			} else {
				posItems.addAll(posItemService.findShortItems(systemBookCode));
				
			}
			if(!ignoreRule){
				
				for (int i = posItems.size() - 1; i >= 0; i--) {
					PosItem posItem = posItems.get(i);
					if (posItem.getItemType() != AppConstants.C_ITEM_TYPE_ASSEMBLE 
							&& (posItem.getItemManufactureFlag() == null || !posItem.getItemManufactureFlag())) {
						posItems.remove(i);
						continue;
					}
					if (posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
						posItems.remove(i);
						continue;
					}
					if (posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()) {
						posItems.remove(i);
						continue;
					}
					if (posItem.getItemStatus() != null && posItem.getItemStatus().equals(AppConstants.ITEM_STATUS_SLEEP)) {
						posItems.remove(i);
						continue;
					}
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_NON_STOCK) {
						posItems.remove(i);
						continue;
					}
					if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_CUSTOMER_KIT) {
						posItems.remove(i);
						continue;
					}
					
					if (StringUtils.equals(posItem.getItemMethod(), AppConstants.BUSINESS_TYPE_SHOP)) {
						posItems.remove(i);
						continue;
					}
					if (inventoryAnalysisQuery.getCategoryCodes() != null
							&& inventoryAnalysisQuery.getCategoryCodes().size() > 0) {
						if (!inventoryAnalysisQuery.getCategoryCodes().contains(posItem.getItemCategoryCode())) {
							posItems.remove(i);
							continue;
						}
					}
					if (inventoryAnalysisQuery.getStoreItemNums() != null
							&& inventoryAnalysisQuery.getStoreItemNums().size() > 0) {
						if (!inventoryAnalysisQuery.getStoreItemNums().contains(posItem.getItemNum())) {
							posItems.remove(i);
							continue;
						}
					}
					if (StringUtils.isNotEmpty(inventoryAnalysisQuery.getPosItemDepartmentCode())) {
						if (!inventoryAnalysisQuery.getPosItemDepartmentCode().equals(posItem.getItemDepartment())) {
							posItems.remove(i);
							continue;
						}
					}
				}
			}
		} else {
			PosItemQuery posItemQuery = new PosItemQuery();
			posItemQuery.setSystemBookCode(inventoryAnalysisQuery.getSystemBookCode());
			posItemQuery.setBranchNum(inventoryAnalysisQuery.getBranchNum());
			posItemQuery.setCategoryCodes(inventoryAnalysisQuery.getCategoryCodes());
			posItemQuery.setItemDepartment(inventoryAnalysisQuery.getPosItemDepartmentCode());
			if(inventoryAnalysisQuery.getStoreItemNums() != null && itemNums == null){
				posItemQuery.setItemNums(inventoryAnalysisQuery.getStoreItemNums());
				itemNums = inventoryAnalysisQuery.getStoreItemNums();
				
			} else if(inventoryAnalysisQuery.getStoreItemNums() == null && itemNums != null){
				posItemQuery.setItemNums(itemNums);
				
			} else if(inventoryAnalysisQuery.getStoreItemNums() != null && itemNums != null){
				
				itemNums.retainAll(inventoryAnalysisQuery.getStoreItemNums());
				posItemQuery.setItemNums(itemNums);
			}
			
			posItemQuery.setPaging(false);
			posItemQuery.setOrderType(AppConstants.POS_ITEM_LOG_PURCHASE_ORDER);
			posItemQuery.setFilterType(AppConstants.ITEM_TYPE_PURCHASE);
			posItemQuery.setIsFindNoStock(false);
			posItemQuery.setRdc(branch.isRdc());
			posItems = posItemService.findByPosItemQuery(posItemQuery, null, null, 0, 0);
		}

		List<Integer> matrixItemNums = new ArrayList<Integer>();
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);

			List<ItemMatrix> itemMatrixs = posItem.getItemMatrixs();
			InventoryAnalysisDTO dto = new InventoryAnalysisDTO();
			dto.setPosItem(posItem);
			dto.setItemNum(posItem.getItemNum());
			dto.setItemMatrixNum(0);
			dto.getPosItem().getItemMatrixs().clear();
			dto.setItemMinQuantity(posItem.getItemMinQuantity());
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {

				for (int j = 0; j < itemMatrixs.size(); j++) {
					ItemMatrix itemMatrix = itemMatrixs.get(j);
					InventoryAnalysisDTO matrixDTO = new InventoryAnalysisDTO();
					Object[] objects = AppUtil.getInventoryAmount(inventories, posItem, itemMatrix.getId()
							.getItemMatrixNum(), null, branch);
					if (!(Boolean) objects[4] &&  !ignoreRule) {
						continue;
					}
					matrixDTO.setInventoryQty((BigDecimal) objects[0]);
					matrixDTO.setPosItem(posItem);
					matrixDTO.setItemNum(posItem.getItemNum());
					matrixDTO.setItemMatrixNum(itemMatrix.getId().getItemMatrixNum());
					matrixDTO.setItemMatrix(itemMatrix);
					matrixItemNums.add(posItem.getItemNum());
					list.add(matrixDTO);

				}

			} else {
				Object[] objects = AppUtil.getInventoryAmount(inventories, posItem, null, null, branch);
				if (!(Boolean) objects[4] && !inventoryAnalysisQuery.isQueryAssemble() && !ignoreRule) {
					continue;
				}
				dto.setInventoryQty((BigDecimal) objects[0]);
				list.add(dto);
			}
		}

		// 在订量
		if (!inventoryAnalysisQuery.isQueryAssemble()) {
			List<Object[]> purchaseObjects = purchaseOrderDao.findUnReceivedItemAmount(systemBookCode, branchNum, itemNums);
			for (int i = 0; i < purchaseObjects.size(); i++) {
				Object[] object = purchaseObjects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal amount = (BigDecimal) object[2];

				InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
				if (data != null) {
					data.setOnLoadQty(amount);
				}
			}

		}
		if (!inventoryAnalysisQuery.isFindCount()) {
			Date dateFrom = DateUtil.addDay(now, -inventoryAnalysisQuery.getLastDays());
			List<Object[]> objects = findItemSaleQty(systemBookCode, branchNum, dateFrom, yesterday,
					inventoryAnalysisQuery.isFindSale(), inventoryAnalysisQuery.isFindOut(),
					inventoryAnalysisQuery.isFindWhole());
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];

				InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
				if (data != null) {

					data.set("saleQty", amount);
				}
			}

			if (inventoryAnalysisQuery.isFindAdjustOut()) {

				objects = adjustmentOrderDao.findItemSummary(systemBookCode, branchNums, dateFrom, yesterday, null,
						false, true, itemNums);
				for (int i = 0; i < objects.size(); i++) {
					Object[] object = objects.get(i);
					Integer itemNum = (Integer) object[0];
					Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
					BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

					InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
					if (data != null) {

						BigDecimal value = (BigDecimal) data.get("saleQty");
						if (value == null) {
							value = BigDecimal.ZERO;
						}
						value = value.add(amount);
						data.set("saleQty", value);
					}
				}

			}

		}
		if (inventoryAnalysisQuery.isAddRequest()) {

			List<Object[]> requestObjects = requestOrderDao.findCenterRequestMatrixAmount(systemBookCode,
					inventoryAnalysisQuery.getBranchNum(), itemNums);
			for (int i = 0; i < requestObjects.size(); i++) {
				Object[] object = requestObjects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal outAmount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal purchaseAmount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

				InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
				if (data != null) {
					data.setRequestLostQty(amount.subtract(outAmount).subtract(purchaseAmount)
							.subtract(data.getInventoryQty()));
					if (data.getRequestLostQty().compareTo(BigDecimal.ZERO) < 0) {
						data.setRequestLostQty(BigDecimal.ZERO);
					}
				}
			}

		}

		if (!inventoryAnalysisQuery.isFindCount()) {

			List<Object[]> receiveObjects = receiveOrderDao.findItemMatrixMaxProducingDates(systemBookCode, branchNum,
					itemNums);
			for (int i = 0; i < receiveObjects.size(); i++) {
				Object[] object = receiveObjects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				Date maxProductDate = (Date) object[2];
				InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
				if (data != null) {
					if (data.getLastProductDate() == null) {
						data.setLastProductDate(maxProductDate);
					} else if (data.getLastProductDate().compareTo(maxProductDate) < 0) {
						data.setLastProductDate(maxProductDate);
					}
				}
			}
		}

		List<StoreMatrix> storeMatrixs = storeMatrixDao.findByBranch(systemBookCode, branchNum, itemNums);

		List<StoreMatrixDetail> storeMatrixDetails = new ArrayList<StoreMatrixDetail>();
		if (matrixItemNums.size() > 0) {
			storeMatrixDetails = storeMatrixDao.findDetails(systemBookCode, branchNum, matrixItemNums);
		}
		List<StoreItemSupplier> storeItemSuppliers = new ArrayList<StoreItemSupplier>();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		if (!inventoryAnalysisQuery.isQueryAssemble()) {
			storeItemSuppliers = storeItemSupplierDao.find(systemBookCode, branchNum, null, false, null);
			suppliers = supplierService.findInCache(systemBookCode);
		}

		// 查询今日制单的加工入库数量
		if (inventoryAnalysisQuery.isQueryAssemble()) {

			List<String> reasons = new ArrayList<String>();
			reasons.add("加工入库单");
			List<Object[]> todayAdjustmentObjects = adjustmentOrderDao.findItemSummary(systemBookCode, branchNums, now,
					now, reasons, null, false, itemNums);
			for (int i = 0; i < todayAdjustmentObjects.size(); i++) {
				Object[] object = todayAdjustmentObjects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

				InventoryAnalysisDTO data = InventoryAnalysisDTO.get(list, itemNum, itemMatrixNum);
				if (data != null) {
					data.setOnLoadQty(amount);
				}
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			InventoryAnalysisDTO data = list.get(i);
			Integer itemNum = data.getItemNum();
			BigDecimal amount = (BigDecimal) data.get("saleQty");
			if (amount == null) {
				amount = BigDecimal.ZERO;
			}
			data.setAvgSaleQty(amount.divide(BigDecimal.valueOf(inventoryAnalysisQuery.getLastDays()), 8,
					BigDecimal.ROUND_HALF_UP));

			if (data.getAvgSaleQty().compareTo(BigDecimal.ZERO) != 0) {
				data.setCanUseDate(data.getInventoryQty().divide(data.getAvgSaleQty(), 0, BigDecimal.ROUND_DOWN)
						.intValue());
			}

			BigDecimal storeMatrixUpperStock = BigDecimal.ZERO;
			BigDecimal storeMatrixReorderQty = BigDecimal.ZERO;
			StoreMatrix storeMatrix = AppUtil
					.getStoreMatrix(systemBookCode, branchNum, data.getItemNum(), storeMatrixs);
			if (storeMatrix != null) {
				if (storeMatrix.getStoreMatrixOrderEnabled()) {

					if (storeMatrix.getStoreMatrixReorderPoint().compareTo(BigDecimal.ZERO) > 0) {
						data.setInventoryBasicQty(storeMatrix.getStoreMatrixReorderPoint());

					}
					if (storeMatrix.getStoreMatrixBaseStock().compareTo(BigDecimal.ZERO) > 0) {
						data.setBasicInventoryQty(storeMatrix.getStoreMatrixBaseStock());

					}

					if (storeMatrix.getStoreMatrixReorderQty().compareTo(BigDecimal.ZERO) > 0) {
						storeMatrixReorderQty = storeMatrix.getStoreMatrixReorderQty();

					}

				}
				storeMatrixUpperStock = storeMatrix.getStoreMatrixUpperStock();
				if (storeMatrix.getStoreMatrixStockCeaseFlag() != null && storeMatrix.getStoreMatrixStockCeaseFlag() && !ignoreRule) {
					list.remove(i);
					continue;
				}
			} else {
				if (data.getPosItem().getItemStockCeaseFlag() != null && data.getPosItem().getItemStockCeaseFlag() && !ignoreRule) {
					list.remove(i);
					continue;
				}
			}
			if (data.getItemMatrixNum() != 0) {
				StoreMatrixDetail storeMatrixDetail = AppUtil.getStoreMatrixDetail(systemBookCode, branchNum, itemNum,
						data.getItemMatrixNum(), storeMatrixDetails);
				if (storeMatrixDetail != null) {

					if (storeMatrixDetail.getStoreMatrixDetailReorderPoint().compareTo(BigDecimal.ZERO) > 0) {
						data.setInventoryBasicQty(storeMatrixDetail.getStoreMatrixDetailReorderPoint());

					}
					if (storeMatrixDetail.getStoreMatrixDetailBaseStock().compareTo(BigDecimal.ZERO) > 0) {
						data.setBasicInventoryQty(storeMatrixDetail.getStoreMatrixDetailBaseStock());

					}

					if (storeMatrixDetail.getStoreMatrixDetailReorderQty().compareTo(BigDecimal.ZERO) > 0) {
						storeMatrixReorderQty = storeMatrixDetail.getStoreMatrixDetailReorderQty();

					}
					storeMatrixUpperStock = storeMatrixDetail.getStoreMatrixDetailUpperStock();
				}
			}
			if(!ignoreRule){
				
				if (inventoryAnalysisQuery.isRule1()) {
					if (data.getInventoryQty().compareTo(data.getInventoryBasicQty()) > 0) {
						list.remove(i);
						continue;
					}
				}
				if (inventoryAnalysisQuery.isRule2()) {
					if ((data.getInventoryQty().add(data.getOnLoadQty())).compareTo(data.getInventoryBasicQty()) > 0) {
						list.remove(i);
						continue;
					}
				}
				if (inventoryAnalysisQuery.isRule3()) {
					if (data.getInventoryQty().compareTo(data.getBasicInventoryQty()) > 0) {
						list.remove(i);
						continue;
					}
				}
				if (inventoryAnalysisQuery.isRule4()) {
					if ((data.getInventoryQty().add(data.getOnLoadQty())).compareTo(data.getBasicInventoryQty()) > 0) {
						list.remove(i);
						continue;
					}
				}
				if (inventoryAnalysisQuery.isRule5()) {
					if (data.getInventoryBasicQty().compareTo(BigDecimal.ZERO) == 0) {
						if ((data.getInventoryQty().add(data.getOnLoadQty())).compareTo(data.getBasicInventoryQty()) > 0) {
							list.remove(i);
							continue;
						}
					} else {
						if ((data.getInventoryQty().add(data.getOnLoadQty())).compareTo(data.getInventoryBasicQty()) > 0) {
							list.remove(i);
							continue;
						}
					}
				}
				if (inventoryAnalysisQuery.isRule6()) {
					if (data.getInventoryQty().compareTo(storeMatrixUpperStock) >= 0) {
						list.remove(i);
						continue;
					}
				}
			}
			List<StoreItemSupplier> itemStoreItemSuppliers = AppUtil.findStoreItemSuppliers(storeItemSuppliers,
					systemBookCode, branchNum, itemNum);
			Collections.sort(itemStoreItemSuppliers, supplierComparator);
			for (int j = 0; j < itemStoreItemSuppliers.size(); j++) {
				StoreItemSupplier itemStoreItemSupplier = itemStoreItemSuppliers.get(j);
				if(inventoryAnalysisQuery.getDefaultSupplier()!= null && inventoryAnalysisQuery.getDefaultSupplier()){
					if(itemStoreItemSupplier.getStoreItemSupplierDefault() == null || !itemStoreItemSupplier.getStoreItemSupplierDefault()){
						continue;
					}
				}
				if (inventoryAnalysisQuery.getSupplierNum() != null) {
					if (!itemStoreItemSupplier.getId().getSupplierNum().equals(inventoryAnalysisQuery.getSupplierNum())) {
						continue;
					}
				}
				Supplier supplier = AppUtil.getSupplier(itemStoreItemSupplier.getId().getSupplierNum(), suppliers);
				if (supplier != null && supplier.getSupplierActived() != null && supplier.getSupplierActived()) {
					if (itemStoreItemSupplier.getStoreItemSupplierCost().compareTo(BigDecimal.ZERO) > 0) {
						supplier.setItemPrice(itemStoreItemSupplier.getStoreItemSupplierCost());
					} else {
						supplier.setItemPrice(data.getPosItem().getItemCostPrice());
					}
					if (itemStoreItemSupplier.getStoreItemSupplierLastestPrice() != null
							&& itemStoreItemSupplier.getStoreItemSupplierLastestPrice().compareTo(BigDecimal.ZERO) > 0) {
						supplier.setLastPrice(itemStoreItemSupplier.getStoreItemSupplierLastestPrice());
					} else {
						supplier.setLastPrice(data.getPosItem().getItemCostPrice());
					}
					if (supplier.getSupplierPurchaseDate() == null) {
						supplier.setSupplierPurchaseDate(purchaseOrderDao.getLastDate(supplier.getSystemBookCode(),
								supplier.getBranchNum(), supplier.getSupplierNum()));

					}
					Supplier itemSupplier = new Supplier();
					BeanUtils.copyProperties(supplier, itemSupplier);
					itemSupplier.setStoreItemReturnType(itemStoreItemSupplier.getStoreItemReturnType());
					data.getSuppliers().add(itemSupplier);
				}
			}
			if (inventoryAnalysisQuery.getSupplierNum() != null) {
				if (data.getSuppliers().isEmpty()) {
					list.remove(i);
					continue;
				}
				
			}
			if (inventoryAnalysisQuery.getSuggestionType() == 1) {
				if (data.getSuppliers().size() > 0) {
					Supplier supplier = data.getSuppliers().get(0);
					if (supplier.getSupplierPurchasePeriod() != null) {
						if (StringUtils.isEmpty(supplier.getSupplierPurchasePeriodUnit())) {
							supplier.setSupplierPurchasePeriodUnit(AppConstants.PERIOD_UNIT_DAY);
						}
						if (supplier.getSupplierPurchasePeriodUnit().equals(AppConstants.PERIOD_UNIT_DAY)) {

							data.setSuggestionQty(data.getAvgSaleQty().multiply(
									BigDecimal.valueOf(supplier.getSupplierPurchasePeriod())));
						} else if (supplier.getSupplierPurchasePeriodUnit().equals(AppConstants.PERIOD_UNIT_WEEk)) {

							data.setSuggestionQty(data.getAvgSaleQty().multiply(
									BigDecimal.valueOf(supplier.getSupplierPurchasePeriod() * 7)));
						} else if (supplier.getSupplierPurchasePeriodUnit().equals(AppConstants.PERIOD_UNIT_MONTH)) {
							int day = 30;
							if (supplier.getSupplierPurchaseDate() != null) {
								Date nextPurchaseDate = DateUtil.getMinOfDate(supplier.getSupplierPurchaseDate());
								while (nextPurchaseDate.before(now)) {
									nextPurchaseDate = DateUtil.addMonth(nextPurchaseDate, 1);
								}
								day = DateUtil.diffDay(DateUtil.addMonth(nextPurchaseDate, -1), nextPurchaseDate);
							}
							data.setSuggestionQty(data.getAvgSaleQty().multiply(
									BigDecimal.valueOf(supplier.getSupplierPurchasePeriod() * day)));
						}
					}
				}

			} else if (inventoryAnalysisQuery.getSuggestionType() == 3) {
				data.setSuggestionQty(storeMatrixReorderQty);

			} else if (inventoryAnalysisQuery.getSuggestionType() == 2) {
				data.setSuggestionQty(storeMatrixUpperStock.subtract(data.getInventoryQty()));

			} else if (inventoryAnalysisQuery.getSuggestionType() == 4) {
				if (data.get("saleQty") == null) {
					data.set("saleQty", BigDecimal.ZERO);
				}
				data.setSuggestionQty(((BigDecimal) data.get("saleQty")).subtract(data.getInventoryQty()));

			}
			if (inventoryAnalysisQuery.isSubBuying()) {
				data.setSuggestionQty(data.getSuggestionQty().subtract(data.getOnLoadQty()));
			}
			data.setSuggestionQty(data.getSuggestionQty().add(data.getRequestLostQty()));
			if (inventoryAnalysisQuery.isQueryAssemble()) {
				data.setSuggestionQty(data.getSuggestionQty().subtract(data.getOnLoadQty()));
				if (data.getItemMinQuantity() != null && data.getItemMinQuantity().compareTo(BigDecimal.ZERO) > 0) {

					data.setSuggestionQty(data.getSuggestionQty()
							.divide(data.getItemMinQuantity(), 0, BigDecimal.ROUND_UP)
							.multiply(data.getItemMinQuantity()));

				}
			}
			if (data.getSuggestionQty().compareTo(BigDecimal.ZERO) < 0) {
				data.setSuggestionQty(BigDecimal.ZERO);
			}
			BigDecimal rate = BigDecimal.ONE;
			String unit = data.getPosItem().getItemUnit();
			if (inventoryAnalysisQuery.getUnitType() != null) {

				if (inventoryAnalysisQuery.getUnitType().equals(AppConstants.UNIT_SOTRE)) {
					rate = data.getPosItem().getItemInventoryRate();
					unit = data.getPosItem().getItemInventoryUnit();
				} else if (inventoryAnalysisQuery.getUnitType().equals(AppConstants.UNIT_TRANFER)) {
					rate = data.getPosItem().getItemTransferRate();
					unit = data.getPosItem().getItemTransferUnit();
				} else if (inventoryAnalysisQuery.getUnitType().equals(AppConstants.UNIT_PURCHASE)) {
					rate = data.getPosItem().getItemPurchaseRate();
					unit = data.getPosItem().getItemPurchaseUnit();
				} else if (inventoryAnalysisQuery.getUnitType().equals(AppConstants.UNIT_PIFA)) {
					rate = data.getPosItem().getItemWholesaleRate();
					unit = data.getPosItem().getItemWholesaleUnit();
				}
			}
			if (data.getInventoryQty().abs().compareTo(BigDecimal.valueOf(0.001)) < 0
					&& data.getInventoryBasicQty().abs().compareTo(BigDecimal.valueOf(0.001)) < 0
					//&& data.getAvgSaleQty().abs().compareTo(BigDecimal.valueOf(0.001)) < 0
					&& data.getBasicInventoryQty().abs().compareTo(BigDecimal.valueOf(0.001)) < 0
					&& !inventoryAnalysisQuery.isQueryAssemble()
					&& !ignoreRule) {
				list.remove(i);
				continue;
			}

			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				data.setInventoryQty(data.getInventoryQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setBasicInventoryQty(data.getBasicInventoryQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setInventoryBasicQty(data.getInventoryBasicQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setOnLoadQty(data.getOnLoadQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setSuggestionQty(data.getSuggestionQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setAvgSaleQty(data.getAvgSaleQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				data.setRequestLostQty(data.getRequestLostQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
				if (data.get("saleQty") == null) {
					data.set("saleQty", BigDecimal.ZERO);
				}
				data.set("saleQty", ((BigDecimal) data.get("saleQty")).divide(rate, 2, BigDecimal.ROUND_HALF_UP));
			}
			data.setItemRate(rate);
			data.setItemUnit(unit);
			data.setSuggestionQty(data.getSuggestionQty().setScale(0, BigDecimal.ROUND_UP));
			data.setBuyQty(data.getSuggestionQty());
			data.set("storeItemCode", data.getPosItem().getItemCode());
			data.set("itemCategoryCode", "");
			if (data.getPosItem().getItemCategoryCode() != null) {
				data.set("itemCategoryCode", data.getPosItem().getItemCategoryCode());

			}

		}
		return list;
	}

	@Override
	public List<Object[]> findNeedSaleItemRecords(String bookCode, Integer branchNum) {
		return reportDao.findNeedSaleItemRecords(bookCode, branchNum);
	}

	@Override
	public List<PosItemPriceBandDTO> findPosItemPriceBandDTOs(PosItemPriceBandQuery posItemPriceBandQuery) {
		String systemBookCode = posItemPriceBandQuery.getSystemBookCode();
		Integer branchNum = posItemPriceBandQuery.getBranchNum();
		Date dateFrom = posItemPriceBandQuery.getDateFrom();
		Date dateTo = posItemPriceBandQuery.getDateTo();
		List<Integer> branchNums = posItemPriceBandQuery.getBranchNums();
		List<String> categoryCodes = posItemPriceBandQuery.getCategoryCodeList();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Integer> lowItemNums = new ArrayList<Integer>();
		List<Integer> cheapItemNums = new ArrayList<Integer>();
		List<Integer> popularItemNums = new ArrayList<Integer>();
		List<Integer> suitableItemNums = new ArrayList<Integer>();
		List<Integer> higherItemNums = new ArrayList<Integer>();
		List<Integer> highestItemNums = new ArrayList<Integer>();

		BigDecimal comparePrice = null;
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);

			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_SALE)) {
				comparePrice = posItem.getItemRegularPrice();
			} else if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_OUT)) {
				comparePrice = posItem.getItemTransferPrice();
			} else {
				comparePrice = posItem.getItemWholesalePrice();
			}
			if (comparePrice.compareTo(posItemPriceBandQuery.getLowPrice()) >= 0
					&& comparePrice.compareTo(posItemPriceBandQuery.getCheap()) < 0) {
				lowItemNums.add(posItem.getItemNum());
			} else if (comparePrice.compareTo(posItemPriceBandQuery.getCheap()) >= 0
					&& comparePrice.compareTo(posItemPriceBandQuery.getPopular()) < 0) {
				cheapItemNums.add(posItem.getItemNum());
			} else if (comparePrice.compareTo(posItemPriceBandQuery.getPopular()) >= 0
					&& comparePrice.compareTo(posItemPriceBandQuery.getSuitable()) < 0) {
				popularItemNums.add(posItem.getItemNum());
			} else if (comparePrice.compareTo(posItemPriceBandQuery.getSuitable()) >= 0
					&& comparePrice.compareTo(posItemPriceBandQuery.getHigher()) < 0) {
				suitableItemNums.add(posItem.getItemNum());
			} else if (comparePrice.compareTo(posItemPriceBandQuery.getHigher()) >= 0
					&& comparePrice.compareTo(posItemPriceBandQuery.getHighest()) < 0) {
				higherItemNums.add(posItem.getItemNum());
			} else if (comparePrice.compareTo(posItemPriceBandQuery.getHighest()) >= 0) {
				highestItemNums.add(posItem.getItemNum());
			} else {
				continue;
			}
		}
		List<PosItemPriceBandDTO> list = new ArrayList<PosItemPriceBandDTO>();
		list.add(new PosItemPriceBandDTO(0, lowItemNums.size()));
		list.add(new PosItemPriceBandDTO(1, cheapItemNums.size()));
		list.add(new PosItemPriceBandDTO(2, popularItemNums.size()));
		list.add(new PosItemPriceBandDTO(3, suitableItemNums.size()));
		list.add(new PosItemPriceBandDTO(4, higherItemNums.size()));
		list.add(new PosItemPriceBandDTO(5, highestItemNums.size()));

		List<Object[]> objects = null;
		if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_SALE)) {

			objects = posOrderDao.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, null, false);

		} else if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_OUT)) {
			if(branchNums == null){
				branchNums = new ArrayList<Integer>();
				branchNums.add(branchNum);
			}
			objects = transferOutOrderDao
					.findItemSummary(systemBookCode, branchNums, null, dateFrom, dateTo, null);
			List<Object[]> inObjects = transferInOrderDao.findItemSummary(systemBookCode, branchNum, branchNums,
					dateFrom, dateTo, null);
			for (int i = 0; i < inObjects.size(); i++) {
				Object[] inObject = inObjects.get(i);

				boolean find = false;
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (inObject[0].equals(object[0])) {
						object[1] = ((BigDecimal) object[1]).subtract(((BigDecimal) inObject[1]));
						object[2] = ((BigDecimal) object[2]).subtract(((BigDecimal) inObject[2]));
						find = true;
					}
				}
				if (!find) {
					Object[] object = new Object[3];
					object[0] = inObject[0];
					object[1] = ((BigDecimal) inObject[1]).negate();
					object[2] = ((BigDecimal) inObject[2]).negate();
					objects.add(object);
				}
			}

		} else {
	
			objects = wholesaleOrderDao.findItemSummary(systemBookCode, branchNums, dateFrom, dateTo);
			List<Object[]> returnObjects = wholesaleReturnDao.findItemSummary(systemBookCode, branchNums, dateFrom,
					dateTo);

			for (int i = 0; i < returnObjects.size(); i++) {
				Object[] returnObject = returnObjects.get(i);

				boolean find = false;
				for (int j = 0; j < objects.size(); j++) {
					Object[] object = objects.get(j);
					if (returnObject[0].equals(object[0])) {
						object[1] = ((BigDecimal) object[1]).subtract(((BigDecimal) returnObject[1]));
						object[2] = ((BigDecimal) object[2]).subtract(((BigDecimal) returnObject[2]));
						find = true;
					}
				}
				if (!find) {
					Object[] object = new Object[3];
					object[0] = returnObject[0];
					object[1] = ((BigDecimal) returnObject[1]).negate();
					object[2] = ((BigDecimal) returnObject[2]).negate();
					objects.add(object);
				}
			}
		}

		PosItemPriceBandDTO posItemPriceBandDTO = null;
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];

//			if (!posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_SALE)) {
//				PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
//				if (posItem == null) {
//					continue;
//				}
//				if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_OUT)) {
//					amount = amount.divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP);
//				} else if (posItemPriceBandQuery.getType().equals(AppConstants.CHECKBOX_WHO)) {
//					amount = amount.divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP);
//				}
//			}

			if (lowItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(0);
			} else if (cheapItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(1);
			} else if (popularItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(2);
			} else if (suitableItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(3);
			} else if (higherItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(4);
			} else if (highestItemNums.contains(itemNum)) {
				posItemPriceBandDTO = list.get(5);
			} else {
				continue;
			}

			posItemPriceBandDTO.setOutMoney(posItemPriceBandDTO.getOutMoney().add(money));
			posItemPriceBandDTO.setOutNum(posItemPriceBandDTO.getOutNum().add(amount));

		}
		return list;
	}

	@Override
	public List<PickerPerformanceDTO> findPickerPerformanceDTOs(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, List<String> pickers) {

		List<PickerPerformanceDTO> list = new ArrayList<PickerPerformanceDTO>();
		// 制单
		List<Object[]> outObjects = transferOutOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom,
				dateTo, pickers, AppConstants.ORDER_OPERATOR_TYPE_CREATOR);
		List<Object[]> wholesaleObjects = wholesaleOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom,
				dateTo, pickers, AppConstants.ORDER_OPERATOR_TYPE_CREATOR);
		outObjects.addAll(wholesaleObjects);
		String operator = null;
		BigDecimal itemAmount = null;
		BigDecimal money = null;
		BigDecimal useQty = null;
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			operator = object[0] == null ? "" : (String) object[0];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			itemAmount = object[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[3]);
			useQty = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			PickerPerformanceDTO pickerPerformanceDTO = PickerPerformanceDTO.get(list, operator);
			if (pickerPerformanceDTO == null) {
				pickerPerformanceDTO = new PickerPerformanceDTO();
				pickerPerformanceDTO.setPickerName(operator);
				list.add(pickerPerformanceDTO);
			}
			pickerPerformanceDTO.setPickerCreateCount(pickerPerformanceDTO.getPickerCreateCount().add(itemAmount));
			pickerPerformanceDTO.setPickerCreateMoney(pickerPerformanceDTO.getPickerCreateMoney().add(money));
			pickerPerformanceDTO.setPickerCreateAmount(pickerPerformanceDTO.getPickerCreateAmount().add(useQty));
		}
		// 配货
		outObjects = transferOutOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom, dateTo, pickers,
				AppConstants.ORDER_OPERATOR_TYPE_PICKER);
		wholesaleObjects = wholesaleOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom, dateTo, pickers,
				AppConstants.ORDER_OPERATOR_TYPE_PICKER);
		outObjects.addAll(wholesaleObjects);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			operator = object[0] == null ? "" : (String) object[0];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			itemAmount = object[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[3]);
			useQty = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			PickerPerformanceDTO pickerPerformanceDTO = PickerPerformanceDTO.get(list, operator);
			if (pickerPerformanceDTO == null) {
				pickerPerformanceDTO = new PickerPerformanceDTO();
				pickerPerformanceDTO.setPickerName(operator);
				list.add(pickerPerformanceDTO);
			}
			pickerPerformanceDTO.setPickerPickCount(pickerPerformanceDTO.getPickerPickCount().add(itemAmount));
			pickerPerformanceDTO.setPickerPickMoney(pickerPerformanceDTO.getPickerPickMoney().add(money));
			pickerPerformanceDTO.setPickerPickAmount(pickerPerformanceDTO.getPickerPickAmount().add(useQty));

		}
		
		
		// 发货
		outObjects = transferOutOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom, dateTo, pickers,
				AppConstants.ORDER_OPERATOR_TYPE_SENDER);
		wholesaleObjects = wholesaleOrderDao.findOperatorSummary(systemBookCode, branchNums, dateFrom, dateTo, pickers,
				AppConstants.ORDER_OPERATOR_TYPE_SENDER);
		outObjects.addAll(wholesaleObjects);
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] object = outObjects.get(i);
			operator = object[0] == null ? "" : (String) object[0];
			money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			itemAmount = object[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[3]);
			useQty = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];

			PickerPerformanceDTO pickerPerformanceDTO = PickerPerformanceDTO.get(list, operator);
			if (pickerPerformanceDTO == null) {
				pickerPerformanceDTO = new PickerPerformanceDTO();
				pickerPerformanceDTO.setPickerName(operator);
				list.add(pickerPerformanceDTO);
			}
			pickerPerformanceDTO.setPickerSendCount(pickerPerformanceDTO.getPickerSendCount().add(itemAmount));
			pickerPerformanceDTO.setPickerSendMoney(pickerPerformanceDTO.getPickerSendMoney().add(money));
			pickerPerformanceDTO.setPickerSendAmount(pickerPerformanceDTO.getPickerSendAmount().add(useQty));

		}
		return list;
	}

	@Override
	public List<PackageSumDTO> findPackageSumByBranch(String systemBookCode, Integer centerBranchNum,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<PackageSumDTO> list = reportDao.findPackageSumByBranch(systemBookCode, centerBranchNum, branchNums,
				dateFrom, dateTo);
		for (int i = 0; i < list.size(); i++) {
			PackageSumDTO packageSumDTO = list.get(i);
			Branch branch = AppUtil.getBranch(branchs, packageSumDTO.getBranchNum());
			if (branch != null) {
				packageSumDTO.setBranchName(branch.getBranchName());
			}

		}
		return list;
	}

	@Override
	public List<PackageSumDTO> findPackageSumByPrice(String systemBookCode, Integer centerBranchNum,
			List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findPackageSumByPrice(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo);
	}

	@Override
	public CardAnalysisSummaryDTO getCardAnalysisSummaryDTO(CardUserQuery cardUserQuery) {
		Date now = Calendar.getInstance().getTime();
		now = DateUtil.getMinOfDate(now);

		Date dateFrom = cardUserQuery.getDateFrom();
		Date dateTo = cardUserQuery.getDateTo();
		List<Integer> branchNums = cardUserQuery.getBranchNums();
		BigDecimal revokeMoney = null;

		
		CardAnalysisSummaryDTO cardAnalysisSummaryDTO = new CardAnalysisSummaryDTO();
		cardUserQuery.setDateFrom(null);
		cardUserQuery.setDateTo(null);
		cardUserQuery.setBranchNums(null);
		Object[] object = cardUserDao.sumByCardUserQuery(cardUserQuery);
		
		//AMA-11807
		revokeMoney = cardUserDao.getRevokeMoney(cardUserQuery.getSystemBookCode(), null, null, null);
		cardAnalysisSummaryDTO.setTotalPaymentMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
		cardAnalysisSummaryDTO.setCardBalance(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
		cardAnalysisSummaryDTO.setCardBalance(cardAnalysisSummaryDTO.getCardBalance().subtract(revokeMoney));
		cardAnalysisSummaryDTO.setPaymentMoney(BigDecimal.ZERO);
		cardAnalysisSummaryDTO.setDepositMoney(BigDecimal.ZERO);
		cardAnalysisSummaryDTO.setConsumeMoney(BigDecimal.ZERO);
		cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getCardBalance());
		cardAnalysisSummaryDTO.setBalanceMoney(cardAnalysisSummaryDTO.getCardBalance());

		CardReportQuery cardReportQuery = new CardReportQuery();
		cardReportQuery.setSystemBookCode(cardUserQuery.getSystemBookCode());
		cardReportQuery.setOperateBranchNums(branchNums);

		BigDecimal consumeMoney = null;
		BigDecimal depositMoney = null;
		if (dateTo.compareTo(now) < 0) {
			cardReportQuery.setDateFrom(DateUtil.addDay(dateTo, 1));
			cardReportQuery.setDateTo(now);
			object = cardConsumeDao.sumByCardReportQuery(cardReportQuery);
			consumeMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getLastCardBalance().add(consumeMoney));
			cardAnalysisSummaryDTO.setBalanceMoney(cardAnalysisSummaryDTO.getBalanceMoney().add(consumeMoney));

			object = cardDepositDao.sumByCardReportQuery(cardReportQuery);
			depositMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getLastCardBalance()
					.subtract(depositMoney));
			cardAnalysisSummaryDTO.setBalanceMoney(cardAnalysisSummaryDTO.getBalanceMoney().subtract(depositMoney));
			
			revokeMoney = cardUserDao.getRevokeMoney(cardReportQuery.getSystemBookCode(), null, cardReportQuery.getDateFrom(), cardReportQuery.getDateTo());
			cardAnalysisSummaryDTO.setBalanceMoney(cardAnalysisSummaryDTO.getBalanceMoney().add(revokeMoney));

		}
		cardReportQuery.setDateFrom(dateFrom);
		cardReportQuery.setDateTo(dateTo);
		object = cardConsumeDao.sumByCardReportQuery(cardReportQuery);
		consumeMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
		cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getLastCardBalance().add(consumeMoney));
		cardAnalysisSummaryDTO.setConsumeMoney(consumeMoney);

		object = cardDepositDao.sumByCardReportQuery(cardReportQuery);
		depositMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
		cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getLastCardBalance().subtract(depositMoney));
		cardAnalysisSummaryDTO.setDepositMoney(depositMoney);
		cardAnalysisSummaryDTO.setPaymentMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
		
		revokeMoney = cardUserDao.getRevokeMoney(cardReportQuery.getSystemBookCode(), null, cardReportQuery.getDateFrom(), cardReportQuery.getDateTo());
		cardAnalysisSummaryDTO.setLastCardBalance(cardAnalysisSummaryDTO.getLastCardBalance().add(revokeMoney));
		cardAnalysisSummaryDTO.setRevokeMoney(revokeMoney);
		return cardAnalysisSummaryDTO;
	}

	@Override
	public List<Object[]> findCustomerAnalysisBranch(String systemBookCode, Date dtFrom, Date dtTo,
			List<Integer> branchNums, String saleType) {
		return reportDao.findCustomerAnalysisBranch(systemBookCode, dtFrom, dtTo, branchNums, saleType);
	}

	@Override
	public List<Object[]> findCustomerAnalysisBranchItem(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, List<Integer> itemNums) {
		return reportDao.findCustomerAnalysisBranchItem(systemBookCode, dtFrom, dtTo, branchNums, itemNums);
	}

	@Override
	public List<ShipOrderBranchDTO> findShipOrderBranch(String systemBookCode, Integer outBranchNum,
			List<Integer> branchNums, Date dateFrom, Date dateTo, Date itemDateFrom, Date itemDateTo,
			List<String> categoryCodes, List<Integer> exceptItemNums, BigDecimal minMoney) {
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<Integer> newItemNums = receiveOrderDao.findFirstReceiveItem(systemBookCode, outBranchNum, itemDateFrom,
				itemDateTo, categoryCodes);
		Map<Integer, ShipOrderBranchDTO> map = new HashMap<Integer, ShipOrderBranchDTO>();
		newItemNums.removeAll(exceptItemNums);
		BigDecimal hundred = BigDecimal.valueOf(100);
		for (int i = branchNums.size() - 1; i >= 0; i--) {
			Branch branch = AppUtil.getBranch(branchs, branchNums.get(i));
			if (branch == null || branch.getBranchActived() == null || branch.getBranchActived() == false) {
				branchNums.remove(i);
				continue;
			}
			ShipOrderBranchDTO data = new ShipOrderBranchDTO();
			data.setBranchNum(branchNums.get(i));
			data.setBranchName(branch.getBranchName());
			data.setNewItemCount(0);
			if (newItemNums != null && newItemNums.size() > 0) {
				data.setNewItemRate(BigDecimal.ZERO);
			} else {
				data.setNewItemRate(hundred);
			}
			data.setNewRequestItemRate(BigDecimal.ZERO);
			data.setNewItemNums(newItemNums);
			map.put(branchNums.get(i), data);
		}
		List<Object[]> shipOrderBranchItems = shipOrderDao.findBranchItemSummary(systemBookCode, outBranchNum,
				branchNums, categoryCodes, exceptItemNums, dateFrom, dateTo);
		for (int i = 0; i < shipOrderBranchItems.size(); i++) {
			Object[] objects = shipOrderBranchItems.get(i);
			Integer branchNum = (Integer) objects[0];
			String shipOrderFid = (String) objects[1];
			BigDecimal money = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
			ShipOrderBranchDTO data = map.get(branchNum);
			data.setShipOrderMoney(data.getShipOrderMoney().add(money));
			ShipOrderBranchDTO subData = getShipOrderBranchData(data.getShipOrderBranchDatas(), shipOrderFid);
			subData.setShipOrderMoney(subData.getShipOrderMoney().add(money));
		}
		if (newItemNums != null && newItemNums.size() > 0) {
			List<Object[]> shipOrderNewItems = shipOrderDao.findBranchNewItemSummary(systemBookCode, outBranchNum,
					branchNums, newItemNums, categoryCodes, dateFrom, dateTo);
			for (int i = 0; i < shipOrderNewItems.size(); i++) {
				Object[] objects = shipOrderNewItems.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer newItemCount = objects[1] == null ? 0 : (Integer) objects[1];
				ShipOrderBranchDTO data = map.get(branchNum);
				data.setNewItemCount(newItemCount);
				data.setNewItemRate(new BigDecimal(newItemCount).divide(new BigDecimal(newItemNums.size()), 4,
						BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}
		}
		List<Object[]> requestOrderNewItems = requestOrderDao.findBranchNewItemSummary(systemBookCode, outBranchNum,
				branchNums, newItemNums, categoryCodes, dateFrom, dateTo);
		for (int i = 0; i < requestOrderNewItems.size(); i++) {
			Object[] objects = requestOrderNewItems.get(i);
			Integer branchNum = (Integer) objects[0];
			Integer newItemCount = objects[1] == null ? 0 : (Integer) objects[1];
			ShipOrderBranchDTO data = map.get(branchNum);
			if (newItemNums != null && newItemNums.size() > 0) {
				data.setNewRequestItemRate(new BigDecimal(newItemCount).divide(new BigDecimal(newItemNums.size()), 4,
						BigDecimal.ROUND_HALF_UP).multiply(hundred));
			} else {
				data.setNewRequestItemRate(hundred);
			}
		}
		List<ShipOrderBranchDTO> list = new ArrayList<ShipOrderBranchDTO>(map.values());
		for (int i = 0; i < list.size(); i++) {
			ShipOrderBranchDTO data = list.get(i);
			for (int j = data.getShipOrderBranchDatas().size() - 1; j >= 0; j--) {
				if (minMoney != null
						&& data.getShipOrderBranchDatas().get(j).getShipOrderMoney().compareTo(minMoney) <= 0) {
					data.getShipOrderBranchDatas().remove(j);
				}
			}
			data.setShipOrderCount(data.getShipOrderBranchDatas().size());
		}
		return list;
	}

	private ShipOrderBranchDTO getShipOrderBranchData(List<ShipOrderBranchDTO> list, String shipOrderFid) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getShipOrderFid().equals(shipOrderFid)) {
				return list.get(i);
			}
		}
		ShipOrderBranchDTO data = new ShipOrderBranchDTO();
		data.setShipOrderFid(shipOrderFid);
		list.add(data);
		return data;
	}

	@Override
	public List<ShipOrderBranchDetailDTO> findShipOrderBranchDetail(String systemBookCode, Integer outBranchNum,
			Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes) {
		List<ShipOrderBranchDetailDTO> list = new ArrayList<ShipOrderBranchDetailDTO>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Object[]> objects = shipOrderDao.findBranchNewItemDetail(systemBookCode, outBranchNum, branchNum,
				itemNums, dateFrom, dateTo, categoryCodes);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal totalMoney = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			ShipOrderBranchDetailDTO dto = new ShipOrderBranchDetailDTO();
			PosItem item = AppUtil.getPosItem(itemNum, posItems);
			if (item == null) {
				continue;
			}
			dto.setItemCode(item.getItemCode());
			dto.setItemName(item.getItemName());
			dto.setShipOrderItemMoney(totalMoney);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<ShipOrderBranchDetailDTO> findShipOrderBranchNewItem(String systemBookCode, Integer outBranchNum,
			Date itemDateFrom, Date itemDateTo, List<String> categoryCodes, List<Integer> exceptItemNums) {
		List<ShipOrderBranchDetailDTO> list = new ArrayList<ShipOrderBranchDetailDTO>();
		List<Integer> newItemNums = receiveOrderDao.findFirstReceiveItem(systemBookCode, outBranchNum, itemDateFrom,
				itemDateTo, categoryCodes);
		newItemNums.removeAll(exceptItemNums);
		if (newItemNums.size() == 0) {
			return list;
		}
		List<PosItem> posItems = posItemService.findByItemNumsInCache(systemBookCode, newItemNums);
		for (int i = 0; i < newItemNums.size(); i++) {
			ShipOrderBranchDetailDTO dto = new ShipOrderBranchDetailDTO();
			PosItem item = AppUtil.getPosItem(newItemNums.get(i), posItems);
			if (item == null) {
				continue;
			}
			dto.setItemCode(item.getItemCode());
			dto.setItemName(item.getItemName());
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<PurchaseAndTransferDTO> findPurchaseAndTransferDTOs(String systemBookCode, Integer branchNum,
			Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums, String categoryCodes,
			String unitType) {
		List<PurchaseAndTransferDetailDTO> detailDTOs = findPurchaseAndTransferDetailDTOs(systemBookCode, branchNum,
				dateFrom, dateTo, employees, itemNums, categoryCodes, unitType);
		List<PurchaseAndTransferDTO> list = new ArrayList<PurchaseAndTransferDTO>();

		for (int i = 0; i < detailDTOs.size(); i++) {
			PurchaseAndTransferDetailDTO detailDTO = detailDTOs.get(i);

			PurchaseAndTransferDTO dto = PurchaseAndTransferDTO.get(list, detailDTO.getPurchaser(),
					detailDTO.getItemNum());
			if (dto == null) {
				dto = new PurchaseAndTransferDTO();
				dto.setItemNum(detailDTO.getItemNum());
				dto.setPurchaser(detailDTO.getPurchaser());
				dto.setItemName(detailDTO.getItemName());
				dto.setItemCode(detailDTO.getItemCode());
				dto.setItemUnit(detailDTO.getItemUnit());
				list.add(dto);
			}
			dto.setItemPurchaseMoney(dto.getItemPurchaseMoney().add(detailDTO.getItemPurchaseMoney()));
			dto.setItemPurchaseQty(dto.getItemPurchaseQty().add(detailDTO.getItemPurchaseQty()));
			dto.setItemTransferMoney(dto.getItemTransferMoney().add(detailDTO.getItemTransferMoney()));
			dto.setItemTransferQty(dto.getItemTransferQty().add(detailDTO.getItemTransferQty()));
			dto.setItemTransferProfit(dto.getItemTransferProfit().add(detailDTO.getItemTransferProfit()));
			dto.setItemWholesaleQty(dto.getItemWholesaleQty().add(detailDTO.getItemWholesaleQty()));
			dto.setItemWholesaleMoney(dto.getItemWholesaleMoney().add(detailDTO.getItemWholesaleMoney()));
			dto.setItemOtherQty(dto.getItemOtherQty().add(detailDTO.getItemOtherQty()));
			dto.setItemOtherMoney(dto.getItemOtherMoney().add(detailDTO.getItemOtherMoney()));
			dto.setItemAllocationMoney(dto.getItemAllocationMoney().add(detailDTO.getItemAllocationMoney()));
			dto.setItemAllocationQty(dto.getItemAllocationQty().add(detailDTO.getItemAllocationQty()));

		}

		return list;
	}

	@Override
	public List<PurchaseAndTransferDetailDTO> findPurchaseAndTransferDetailDTOs(String systemBookCode,
			Integer branchNum, Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums,
			String categoryCodes, String unitType) {
		List<Object[]> objects = transferOutOrderDao.findItemLotSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
		Object[] object = null;
		Integer itemNum = null;
		String itemLotnumber = null;
		List<PurchaseAndTransferDetailDTO> list = new ArrayList<PurchaseAndTransferDetailDTO>();
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			itemNum = (Integer) object[0];
			itemLotnumber = (String) object[1];

			PurchaseAndTransferDetailDTO dto = PurchaseAndTransferDetailDTO.get(list, itemNum, itemLotnumber);
			if (dto == null) {
				dto = new PurchaseAndTransferDetailDTO();
				dto.setItemNum(itemNum);
				dto.setItemLotnumber(itemLotnumber);
				list.add(dto);
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				dto.setItemTransferQty(dto.getItemTransferQty().add(
						object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]));

			} else {
				dto.setItemTransferQty(dto.getItemTransferQty().add(
						object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]));

			}
			dto.setItemTransferMoney(dto.getItemTransferMoney().add(
					object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));
			dto.setItemTransferProfit(dto.getItemTransferProfit().add(
					object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));

		}
		objects = wholesaleOrderDao.findItemLotSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums);
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			itemNum = (Integer) object[0];
			itemLotnumber = (String) object[1];

			PurchaseAndTransferDetailDTO dto = PurchaseAndTransferDetailDTO.get(list, itemNum, itemLotnumber);
			if (dto == null) {
				dto = new PurchaseAndTransferDetailDTO();
				dto.setItemNum(itemNum);
				dto.setItemLotnumber(itemLotnumber);
				list.add(dto);
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				dto.setItemWholesaleQty(dto.getItemWholesaleQty().add(
						object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]));

			} else {
				dto.setItemWholesaleQty(dto.getItemWholesaleQty().add(
						object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]));

			}
			dto.setItemWholesaleMoney(dto.getItemWholesaleMoney().add(
					object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));
			dto.setItemTransferProfit(dto.getItemTransferProfit().add(
					object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));

		}
		
		List<String> summaries = new ArrayList<String>();
		summaries.add(AppConstants.POS_ITEM_LOG_CHECKORDER);
		summaries.add(AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER);
		summaries.add(AppConstants.POS_ITEM_LOG_COST_ADJUST);

		objects = posItemLogDao.findItemLotSummary(systemBookCode, branchNum, dateFrom, dateTo, itemNums, summaries);
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			itemNum = (Integer) object[0];
			itemLotnumber = (String) object[1];
			PurchaseAndTransferDetailDTO dto = PurchaseAndTransferDetailDTO.get(list, itemNum, itemLotnumber);
			if (dto == null) {
				dto = new PurchaseAndTransferDetailDTO();
				dto.setItemNum(itemNum);
				dto.setItemLotnumber(itemLotnumber);
				list.add(dto);
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				dto.setItemOtherQty(dto.getItemOtherQty().add(
						object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));

			} else {
				dto.setItemOtherQty(dto.getItemOtherQty().add(
						object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]));

			}
			dto.setItemOtherMoney(dto.getItemOtherMoney().add(
					object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));

		}
		
		objects = allocationOrderDao.findItemLotSummary(systemBookCode,  branchNum,  dateFrom,  dateTo,
				 itemNums);
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			itemNum = (Integer) object[0];
			itemLotnumber = (String) object[1];

			PurchaseAndTransferDetailDTO dto = PurchaseAndTransferDetailDTO.get(list, itemNum, itemLotnumber);
			if (dto == null) {
				dto = new PurchaseAndTransferDetailDTO();
				dto.setItemNum(itemNum);
				dto.setItemLotnumber(itemLotnumber);
				list.add(dto);
			}
			if (unitType.equals(AppConstants.UNIT_USE)) {
				dto.setItemAllocationQty(dto.getItemWholesaleQty().add(
						object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]));

			} else {
				dto.setItemAllocationQty(dto.getItemWholesaleQty().add(
						object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]));

			}
			dto.setItemAllocationMoney(dto.getItemWholesaleMoney().add(
					object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]));

		}
		if(list.isEmpty()) {
			return list;
		}
		List<String> lotNumbers = new ArrayList<String>();
		if(itemNums == null) {
			itemNums = new ArrayList<Integer>();
		}
		itemNums.clear();
		for(PurchaseAndTransferDetailDTO dto : list) {
			lotNumbers.add(dto.getItemLotnumber());
			itemNums.add(dto.getItemNum());
		}
		objects = receiveOrderDao.findOperatorItemLotSummary(systemBookCode, branchNum, lotNumbers, employees);
		if (objects.size() == 0) {
			return list;
		}
		Branch branch = branchService.readInCache(systemBookCode, branchNum);

		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		String purchaser = null;
		Date itemPurchaseDate = null;

		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);
			purchaser = (String) object[0];
			itemNum = (Integer) object[1];
			itemLotnumber = (String) object[2];
			itemPurchaseDate = (Date) object[3];

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if (StringUtils.isNotEmpty(categoryCodes)) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					continue;
				}
			}
			if (!posItem.getItemCostMode(branch).equals(AppConstants.C_ITEM_COST_MODE_MANUAL)) {
				continue;
			}
			PurchaseAndTransferDetailDTO dto = PurchaseAndTransferDetailDTO
					.get(list, itemNum, itemLotnumber);
			if (dto == null) {
				continue;
			}
			if(StringUtils.isEmpty(dto.getPurchaser())) {
				dto.setPurchaser(purchaser);
				dto.setItemNum(itemNum);
				dto.setItemLotnumber(itemLotnumber);
				dto.setItemCode(posItem.getItemCode());
				dto.setItemName(posItem.getItemName());
				dto.setItemUnit(posItem.getItemUnit());
				dto.setItemRate(BigDecimal.ONE);
				if (unitType.equals(AppConstants.UNIT_SOTRE)) {
					dto.setItemRate(posItem.getItemInventoryRate());
					dto.setItemUnit(posItem.getItemInventoryUnit());
				} else if (unitType.equals(AppConstants.UNIT_TRANFER)) {
					dto.setItemRate(posItem.getItemTransferRate());
					dto.setItemUnit(posItem.getItemTransferUnit());
				} else if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
					dto.setItemRate(posItem.getItemPurchaseRate());
					dto.setItemUnit(posItem.getItemPurchaseUnit());
				} else if (unitType.equals(AppConstants.UNIT_PIFA)) {
					dto.setItemRate(posItem.getItemWholesaleRate());
					dto.setItemUnit(posItem.getItemWholesaleUnit());
				} else if (unitType.equals(AppConstants.UNIT_USE)) {
					dto.setItemUnit(posItem.getItemPurchaseUnit());
				}
			}
			dto.setItemPurchaseDate(itemPurchaseDate);
			if (unitType.equals(AppConstants.UNIT_USE)) {
				dto.setItemPurchaseQty(dto.getItemPurchaseQty().add(
						object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8]));

			} else {
				dto.setItemPurchaseQty(dto.getItemPurchaseQty().add(
						object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]));

			}
			dto.setItemPurchaseMoney(dto.getItemPurchaseMoney().add(
					object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7]));
		}
		for(int i = list.size()-1;i>=0;i--) {
			if(list.get(i).getPurchaser() == null) {
				list.remove(i);
			}
		}
		if (!unitType.equals(AppConstants.UNIT_USE)) {
			for (int i = 0; i < list.size(); i++) {
				PurchaseAndTransferDetailDTO dto = list.get(i);
				dto.setItemPurchaseQty(dto.getItemPurchaseQty().divide(dto.getItemRate(), 2, BigDecimal.ROUND_HALF_UP));
				dto.setItemTransferQty(dto.getItemTransferQty().divide(dto.getItemRate(), 2, BigDecimal.ROUND_HALF_UP));
				dto.setItemWholesaleQty(dto.getItemWholesaleQty().divide(dto.getItemRate(), 2, BigDecimal.ROUND_HALF_UP));
				dto.setItemOtherQty(dto.getItemOtherQty().divide(dto.getItemRate(), 2, BigDecimal.ROUND_HALF_UP));
				dto.setItemAllocationQty(dto.getItemAllocationQty().divide(dto.getItemRate(), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return list;
	}

	@Override
	public List<CardReportDTO> findCardReportByBranchCardType(String systemBookCode, List<Integer> branchNums,
			Date dateFrom, Date dateTo, Integer cardUserCardType) {
		List<CardReportDTO> cardReportDTOs = new ArrayList<CardReportDTO>();
		List<Object[]> cardSendObjects = cardUserDao.findCardCountByBranchCardType(systemBookCode, branchNums,
				dateFrom, dateTo, cardUserCardType);
		for (int i = 0; i < cardSendObjects.size(); i++) {
			Object[] sendObject = cardSendObjects.get(i);
			CardReportDTO cardReportDTO = new CardReportDTO();
			cardReportDTO.setBranchNum((Integer) sendObject[0]);
			cardReportDTO.setCardUserType((Integer) sendObject[1]);
			cardReportDTO.setSendCardCount(((Long) sendObject[2]).intValue());
			cardReportDTOs.add(cardReportDTO);
		}
		
		List<Object[]> cardRevokeObjects = cardUserDao.findRevokeCardCountByBranchCardType(systemBookCode, branchNums,
				dateFrom, dateTo, cardUserCardType);
		for (int i = 0; i < cardRevokeObjects.size(); i++) {
			Object[] revokeObject = cardRevokeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchCardType((Integer) revokeObject[0],
					(Integer) revokeObject[1], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) revokeObject[0]);
				cardReportDTO.setCardUserType((Integer) revokeObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setReturnCardCount(((Long) revokeObject[2]).intValue());
			cardReportDTO.setReturnCardMoney(revokeObject[3] == null?BigDecimal.ZERO:(BigDecimal)revokeObject[3]);

		}
		
		List<Object[]> cardDepositObjects = cardDepositDao.findBranchCardTypePaymentTypeSum(systemBookCode, branchNums, dateFrom,
				dateTo, cardUserCardType);
		String paymentType = null;
		for (int i = 0; i < cardDepositObjects.size(); i++) {
			Object[] depositObject = cardDepositObjects.get(i);
			paymentType = (String) depositObject[2];
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchCardType((Integer) depositObject[0],
					(Integer) depositObject[1], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) depositObject[0]);
				cardReportDTO.setCardUserType((Integer) depositObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			if(paymentType.equals(AppConstants.PAYMENT_ORI)){
				cardReportDTO.setOriDepositMoney(cardReportDTO.getOriDepositMoney().add((BigDecimal) depositObject[4]));
			} else {
				cardReportDTO.setPaymentMoney(cardReportDTO.getPaymentMoney().add((BigDecimal) depositObject[3]));
				cardReportDTO.setDepositMoney(cardReportDTO.getDepositMoney().add((BigDecimal) depositObject[4]));
				cardReportDTO.setSendMoney(cardReportDTO.getDepositMoney().subtract(cardReportDTO.getPaymentMoney()));
				
			}
		}
		List<Object[]> cardConsumeObjects = cardConsumeDao.findBranchCardTypeSum(systemBookCode, branchNums, dateFrom,
				dateTo, cardUserCardType);
		for (int i = 0; i < cardConsumeObjects.size(); i++) {
			Object[] consumeObject = cardConsumeObjects.get(i);
			CardReportDTO cardReportDTO = CardReportDTO.readByBranchCardType((Integer) consumeObject[0],
					(Integer) consumeObject[1], cardReportDTOs);
			if (cardReportDTO == null) {
				cardReportDTO = new CardReportDTO();
				cardReportDTO.setBranchNum((Integer) consumeObject[0]);
				cardReportDTO.setCardUserType((Integer) consumeObject[1]);
				cardReportDTOs.add(cardReportDTO);
			}
			cardReportDTO.setConsumeMoney((BigDecimal) consumeObject[2]);
		}
		return cardReportDTOs;
	}

	@Override
	public List<LnItemSummaryDTO> findLnItemSummaryDTOs(String systemBookCode, Integer branchNum, Date dateFrom,
			Date dateTo, Integer itemNum, String itemLotNumber, String itemUnit) {
		PosItem posItem = posItemService.readWithoutDetails(itemNum);
		List<Integer> itemNums = new ArrayList<Integer>();
		itemNums.add(itemNum);
		BigDecimal rate = BigDecimal.ONE;
		String unit = "";
		boolean useQty = false;
		if (itemUnit == null) {
			itemUnit = AppConstants.UNIT_PURCHASE;
		}
		if (itemUnit.equals(AppConstants.UNIT_BASIC)) {
			rate = BigDecimal.ONE;
			unit = posItem.getItemUnit();
		} else if (itemUnit.equals(AppConstants.UNIT_PIFA)) {
			rate = posItem.getItemWholesaleRate();
			unit = posItem.getItemWholesaleUnit();
		} else if (itemUnit.equals(AppConstants.UNIT_PURCHASE)) {
			rate = posItem.getItemPurchaseRate();
			unit = posItem.getItemPurchaseUnit();
		} else if (itemUnit.equals(AppConstants.UNIT_TRANFER)) {
			rate = posItem.getItemTransferRate();
			unit = posItem.getItemTransferUnit();
		} else if (itemUnit.equals(AppConstants.UNIT_SOTRE)) {
			rate = posItem.getItemInventoryRate();
			unit = posItem.getItemInventoryUnit();
		} else if (itemUnit.equals(AppConstants.UNIT_USE)) {
			rate = BigDecimal.ONE;
			useQty = true;
			unit = null;
		}
		List<LnItemSummaryDTO> list = new ArrayList<LnItemSummaryDTO>();

		List<Object[]> objects = receiveOrderDao.findOperatorItemLotSummary(systemBookCode, branchNum, dateFrom,
				dateTo, null, itemNums);
		if (objects.size() == 0) {
			return list;
		}
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			String lot = (String) object[2];
			if (lot == null) {
				continue;
			}
			if (StringUtils.isNotEmpty(itemLotNumber)) {
				if (!lot.equals(itemLotNumber)) {
					continue;
				}
			}

			LnItemSummaryDTO dto = LnItemSummaryDTO.get(list, (String) object[2]);
			if (dto == null) {
				dto = new LnItemSummaryDTO();
				dto.setItemBillOperater((String) object[0]);
				dto.setItemNum(itemNum);
				dto.setItemCode(posItem.getItemCode());
				dto.setItemName(posItem.getItemName());
				dto.setItemLotNumber(lot);
				dto.setItemPurchaseDate((Date) object[3]);
				dto.setItemInventory(BigDecimal.ZERO);
				dto.setItemUnit(unit);
				list.add(dto);
			}
			if (useQty) {

				dto.setItemPurchaseQty(dto.getItemPurchaseQty().add((BigDecimal) object[8]));

			} else {
				dto.setItemPurchaseQty(dto.getItemPurchaseQty().add((BigDecimal) object[6]));

			}
			dto.setItemPurchaseMoney(dto.getItemPurchaseMoney().add((BigDecimal) object[7]));

			LnItemSummaryDTO.LnItemDetailDTO detailDTO = new LnItemSummaryDTO.LnItemDetailDTO();
			detailDTO.setBillNo((String) object[4]);
			detailDTO.setBillType(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
			if (useQty) {
				detailDTO.setItemQty((BigDecimal) object[8]);

			} else {
				detailDTO.setItemQty((BigDecimal) object[6]);

			}
			detailDTO.setItemMoney((BigDecimal) object[7]);
			detailDTO.setBillOperater((String) object[0]);
			detailDTO.setDate((Date) object[3]);
			detailDTO.setBillMemo(((Integer)object[5]).toString());
			dto.getLnItemDetailDTOs().add(detailDTO);
		}

		List<InventoryLnDetail> inventoryLnDetails = inventoryDao.findInventoryLnDetails(systemBookCode, branchNum,
				itemNums);
		for (int i = 0; i < inventoryLnDetails.size(); i++) {
			InventoryLnDetail inventoryLnDetail = inventoryLnDetails.get(i);

			LnItemSummaryDTO dto = LnItemSummaryDTO.get(list, inventoryLnDetail.getInventoryLnDetailLotNumber());
			if (dto == null) {
				continue;
			}
			if (useQty && inventoryLnDetail.getInventoryLnDetailUseAmount() != null) {
				dto.setItemInventory(dto.getItemInventory().add(inventoryLnDetail.getInventoryLnDetailUseAmount()));
			} else {
				dto.setItemInventory(dto.getItemInventory().add(inventoryLnDetail.getInventoryLnDetailAmount()));

			}
			dto.setItemInventoryMoney(dto.getItemInventoryMoney().add(
					inventoryLnDetail.getInventoryLnDetailAmount().multiply(
							inventoryLnDetail.getInventoryLnDetailCostPrice())));
			if (dto.getItemUnit() == null) {
				dto.setItemUnit(inventoryLnDetail.getInventoryLnDetailUseUnit());
			}

		}

		StoreQueryCondition storeQueryCondition = new StoreQueryCondition();
		storeQueryCondition.setSystemBookCode(systemBookCode);
		storeQueryCondition.setBranchNum(branchNum);
		storeQueryCondition.setDateStart(dateFrom);
		storeQueryCondition.setDateEnd(dateTo);
		storeQueryCondition.setItemNums(itemNums);
		storeQueryCondition.setPosItemLogLotNumber(itemLotNumber);
		storeQueryCondition.setPaging(false);
		List<PosItemLog> posItemLogs = posItemLogDao.findByStoreQueryCondition(storeQueryCondition, 0, 0);

		BigDecimal amount = null;
		BigDecimal money = null;
		boolean isOut = false;
		for (int i = 0; i < posItemLogs.size(); i++) {
			PosItemLog posItemLog = posItemLogs.get(i);
			if (StringUtils.isEmpty(posItemLog.getPosItemLogLotNumber())) {
				continue;
			}

			LnItemSummaryDTO dto = LnItemSummaryDTO.get(list, posItemLog.getPosItemLogLotNumber());
			if (dto == null) {
				continue;
			}
			if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER)) {
				continue;
			}
			// 调出单数据从单据明细中查
			if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_OUT_ORDER)) {
				continue;
			}
			isOut = false;
			if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_RETURN_ORDER)) {

				if (useQty) {
					amount = posItemLog.getPosItemLogUseQty().negate();

				} else {
					amount = posItemLog.getPosItemLogItemAmount().negate();

				}
				money = posItemLog.getPosItemLogMoney().negate();
				dto.setItemPurchaseQty(dto.getItemPurchaseQty().add(amount));
				dto.setItemPurchaseMoney(dto.getItemPurchaseMoney().add(money));

			} else if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER)) {
				isOut = true;
				if (useQty) {
					amount = posItemLog.getPosItemLogUseQty();

				} else {
					amount = posItemLog.getPosItemLogItemAmount();

				}
				money = posItemLog.getPosItemLogOperatePrice().multiply(posItemLog.getPosItemLogItemAmount());
				dto.setItemOutQty(dto.getItemOutQty().add(amount));
				dto.setItemOutMoney(dto.getItemOutMoney().add(money));

			} else if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_IN_ORDER)
					|| posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_WHOLESALE_RETURN_ORDER)) {

				isOut = true;
				if (useQty) {
					amount = posItemLog.getPosItemLogUseQty().negate();

				} else {
					amount = posItemLog.getPosItemLogItemAmount().negate();

				}
				money = posItemLog.getPosItemLogOperatePrice().multiply(posItemLog.getPosItemLogItemAmount()).negate();
				dto.setItemOutQty(dto.getItemOutQty().add(amount));
				dto.setItemOutMoney(dto.getItemOutMoney().add(money));

			} else if (posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_CHECKORDER)
					|| posItemLog.getPosItemLogSummary().equals(AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER)) {

				if (useQty) {
					amount = posItemLog.getPosItemLogUseQty();

				} else {
					amount = posItemLog.getPosItemLogItemAmount();

				}
				money = posItemLog.getPosItemLogMoney();
				if (!posItemLog.getPosItemLogInoutFlag()) {
					amount = amount.negate();
					money = money.negate();
				}

				dto.setItemAdjustQty(dto.getItemAdjustQty().add(amount));
				dto.setItemAdjustMoney(dto.getItemAdjustMoney().add(money));

			} else {
				continue;
			}

			LnItemSummaryDTO.LnItemDetailDTO detailDTO = new LnItemSummaryDTO.LnItemDetailDTO();
			detailDTO.setBillNo(posItemLog.getPosItemLogBillNo());
			detailDTO.setBillType(posItemLog.getPosItemLogSummary());
			// 调出单 调入单 批发销售 批发退货显示的数据要取相反数
			if (isOut) {
				detailDTO.setItemQty(amount.negate());
				detailDTO.setItemMoney(money.negate());
			} else {
				detailDTO.setItemQty(amount);
				detailDTO.setItemMoney(money);
			}

			detailDTO.setBillOperater(posItemLog.getPosItemLogOperator());
			detailDTO.setDate(posItemLog.getPosItemLogDate());
			detailDTO.setBillMemo(posItemLog.getPosItemLogRef());
			dto.getLnItemDetailDTOs().add(detailDTO);
		}

		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		
		TransferProfitQuery transferProfitQuery = new TransferProfitQuery();
		transferProfitQuery.setSystemBookCode(systemBookCode);
		transferProfitQuery.setDistributionBranchNums(transferBranchNums);
		transferProfitQuery.setDtFrom(dateFrom);
		transferProfitQuery.setDtTo(dateTo);
		transferProfitQuery.setItemNums(itemNums);
		
		List<Object[]> outDetails = transferOutOrderDao.findDetails(transferProfitQuery);
		Object[] object = null;
		for (int i = 0; i < outDetails.size(); i++) {
			object = outDetails.get(i);

			LnItemSummaryDTO dto = LnItemSummaryDTO.get(list, (String) object[21]);
			if (dto == null) {
				continue;
			}
			if (useQty) {
				amount = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];

			} else {
				amount = object[18] == null ? BigDecimal.ZERO : (BigDecimal) object[18];

			}
			money = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			dto.setItemOutQty(dto.getItemOutQty().add(amount));
			dto.setItemOutMoney(dto.getItemOutMoney().add(money));

			LnItemSummaryDTO.LnItemDetailDTO detailDTO = new LnItemSummaryDTO.LnItemDetailDTO();
			detailDTO.setBillNo((String) object[0]);
			detailDTO.setBillType(AppConstants.POS_ITEM_LOG_OUT_ORDER);
			// 调出单 调入单 批发销售 批发退货显示的数据要取相反数
			detailDTO.setItemQty(amount.negate());
			detailDTO.setItemMoney(money.negate());
			detailDTO.setBillOperater((String) object[4]);
			detailDTO.setDate((Date) object[1]);
			detailDTO.setBillMemo(((Integer)object[5]).toString());
			dto.getLnItemDetailDTOs().add(detailDTO);
		}

		Comparator<LnItemSummaryDTO.LnItemDetailDTO> comparator = new Comparator<LnItemSummaryDTO.LnItemDetailDTO>() {

			@Override
			public int compare(LnItemSummaryDTO.LnItemDetailDTO o1, LnItemSummaryDTO.LnItemDetailDTO o2) {
				return o1.getDate().compareTo(o2.getDate());
			}

		};
		
		List<Supplier> suppliers = null;
		List<PosClient> posClients = null;
		List<Branch> branchs = null;

		for (int i = list.size() - 1; i >= 0; i--) {
			LnItemSummaryDTO dto = list.get(i);
			dto.setItemPurchaseQty(dto.getItemPurchaseQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
			dto.setItemOutQty(dto.getItemOutQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
			dto.setItemAdjustQty(dto.getItemAdjustQty().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
			dto.setItemInventory(dto.getItemInventory().divide(rate, 2, BigDecimal.ROUND_HALF_UP));
			dto.setItemProfit(dto.getItemOutMoney().subtract(dto.getItemPurchaseMoney())
					.add(dto.getItemInventoryMoney()));
			
			for(int j = 0;j < dto.getLnItemDetailDTOs().size();j++){
				LnItemSummaryDTO.LnItemDetailDTO detailDTO = dto.getLnItemDetailDTOs().get(j);
				
				if(StringUtils.isNotEmpty(detailDTO.getBillMemo())){
					if(detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_OUT_ORDER) 
							|| detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_IN_ORDER)){
						if(branchs == null){
							branchs = branchService.findInCache(systemBookCode);
						}
						
						Branch branch = AppUtil.getBranch(branchs, Integer.parseInt(detailDTO.getBillMemo()));
						if(branch != null){
							detailDTO.setBillMemo(branch.getBranchName());
						}
						
					} else if(detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER) 
							|| detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_RETURN_ORDER)){
						if(suppliers == null){
							suppliers = supplierService.findInCache(systemBookCode);
						}
						Supplier supplier = AppUtil.getSupplier(Integer.parseInt(detailDTO.getBillMemo()), suppliers);
						if(supplier != null){
							detailDTO.setBillMemo(supplier.getSupplierName());
						}
					} else if(detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_WHOLESALE_ORDER_ORDER) 
							|| detailDTO.getBillType().equals(AppConstants.POS_ITEM_LOG_WHOLESALE_RETURN_ORDER)){
						if(posClients == null){
							posClients = posClientService.findInCache(systemBookCode);
						}
						PosClient posClient = AppUtil.getPosClient(detailDTO.getBillMemo(), posClients);
						if(posClient != null){
							detailDTO.setBillMemo(posClient.getClientName());
						}
					}
					
					
					
				}
				
			}
			
			
			Collections.sort(dto.getLnItemDetailDTOs(), comparator);
		}
		return list;
	}


	@Override
	public List<PurchaseOrderCollect> findPurchaseBranchCategory(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		String systemBookCode = purchaseOrderCollectQuery.getSystemBookCode();
		List<Integer> branchNums = new ArrayList<Integer>();
		Integer branchNum = purchaseOrderCollectQuery.getBranchNum();
		if (branchNum != null) {
			branchNums.add(branchNum);
		} else {
			branchNums.addAll(purchaseOrderCollectQuery.getBranchNums());
		}
		List<Integer> itemNums = purchaseOrderCollectQuery.getItemNums();
		List<String> itemCategoryCodes = purchaseOrderCollectQuery.getItemCategoryCodes();
		List<Integer> supplierNums = purchaseOrderCollectQuery.getSupplierNums();
		String unit = purchaseOrderCollectQuery.getUnitType();
		Date dateFrom = purchaseOrderCollectQuery.getDtFrom();
		Date dateTo = purchaseOrderCollectQuery.getDtTo();
		String operator = purchaseOrderCollectQuery.getOperator();
		Integer storehouseNum = purchaseOrderCollectQuery.getStorehouseNum();

		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		Map<String, PurchaseOrderCollect> map = new HashMap<String, PurchaseOrderCollect>();
		List<Object[]> receiveObjects = receiveOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		
		List<PosItemTypeParam> itemTypeParams = null;
		if(purchaseOrderCollectQuery.getQueryTopCategory() != null && purchaseOrderCollectQuery.getQueryTopCategory()){
			itemTypeParams = bookResourceService.findPosItemTypeParamsInCache(systemBookCode);
		}
		List<String> departments = null;
		if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemDepartment())){
			String[] array = purchaseOrderCollectQuery.getItemDepartment().split(",");
			departments = Arrays.asList(array);
		}
		String categoryCode = null;
		String categoryName = null;
		for (int i = 0; i < receiveObjects.size(); i++) {
			Object[] object = receiveObjects.get(i);
			Integer itemNum = (Integer) object[1];

			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			BigDecimal supplierMoney = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			Integer queryBranchNum = (Integer) object[11];
			
			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				rate = BigDecimal.ONE;
			} else if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();

			} else if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();

			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();

			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();

			} 
			
			categoryCode = posItem.getItemCategoryCode();
			categoryName = posItem.getItemCategory();
			if(itemTypeParams != null){
				PosItemTypeParam typeParam = AppUtil.getTopCategory(itemTypeParams, categoryCode);
				if(typeParam != null){
					categoryCode = typeParam.getPosItemTypeCode();
					categoryName = typeParam.getPosItemTypeName();
				}
			}
			
			PurchaseOrderCollect data = map.get(queryBranchNum + "|" + categoryCode);
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setBranchNum(queryBranchNum);
				data.setItemCategory(categoryName);
				data.setItemCategoryCode(categoryCode);
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setReceiveOrderDetailSupplierMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				data.setBaseUnit(posItem.getItemUnit());
				data.setItemCode(posItem.getItemCode());
				data.setItemName(posItem.getItemName());
				data.setItemSpec(posItem.getItemSpec());

				map.put(queryBranchNum + "|" + categoryCode, data);
			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				
			} 
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}

			data.setPurchaseItemAmount(data.getPurchaseItemAmount().add(amount));
			data.setPurchaseItemMoney(data.getPurchaseItemMoney().add(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().add(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().add(presentMoney));
			data.setReceiveOrderDetailOtherMoney(data.getReceiveOrderDetailOtherMoney().add(
					object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9]));
			data.setReceiveOrderDetailOtherQty(data.getReceiveOrderDetailOtherQty().add(
					object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10]));
			data.setReceiveOrderDetailSupplierMoney(data.getReceiveOrderDetailSupplierMoney().add(supplierMoney));

		}

		List<Object[]> returnObjects = returnOrderDao.findQueryItems(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums, itemCategoryCodes, supplierNums, operator, storehouseNum);
		for (int i = 0; i < returnObjects.size(); i++) {
			Object[] object = returnObjects.get(i);
			Integer itemNum = (Integer) object[1];
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				continue;
			}
			if(StringUtils.isNotEmpty(purchaseOrderCollectQuery.getItemBrand())){
				if(!purchaseOrderCollectQuery.getItemBrand().equals(posItem.getItemBrand())){
					continue;
				}
				
			}
			if(departments != null && !departments.contains(posItem.getItemDepartment())){
				continue;
									
			}
			BigDecimal amount = (BigDecimal) object[3];
			BigDecimal money = (BigDecimal) object[4];
			BigDecimal presentAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal presentMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal presentUseAmount = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			Integer queryBranchNum = (Integer) object[9];

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_BASIC)) {
				rate = BigDecimal.ONE;
			} else if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();

			} else if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();

			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();

			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();

			} 
			categoryCode = posItem.getItemCategoryCode();
			categoryName = posItem.getItemCategory();
			if(itemTypeParams != null){
				PosItemTypeParam typeParam = AppUtil.getTopCategory(itemTypeParams, categoryCode);
				if(typeParam != null){
					categoryCode = typeParam.getPosItemTypeCode();
					categoryName = typeParam.getPosItemTypeName();
				}
			}
			
			PurchaseOrderCollect data = map.get(queryBranchNum + "|" + categoryCode);
			if (data == null) {
				data = new PurchaseOrderCollect();
				data.setBranchNum(queryBranchNum);
				data.setItemCategory(categoryName);
				data.setItemCategoryCode(categoryCode);
				data.setPurchaseItemAmount(BigDecimal.ZERO);
				data.setPurchaseItemMoney(BigDecimal.ZERO);
				data.setPurchasePresentAmount(BigDecimal.ZERO);
				data.setPurchasePresentMoney(BigDecimal.ZERO);
				data.setPurchaseItemReturnAmount(BigDecimal.ZERO);
				data.setPurchaseItemReturnMoney(BigDecimal.ZERO);
				data.setBaseAmount(BigDecimal.ZERO);
				data.setBaseUnit(posItem.getItemUnit());
				data.setItemCode(posItem.getItemCode());
				data.setItemName(posItem.getItemName());
				data.setItemSpec(posItem.getItemSpec());

				map.put(queryBranchNum + "|" + categoryCode, data);
			}
			data.setBaseAmount(data.getBaseAmount().add(amount));
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				amount = amount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				presentAmount = presentAmount.divide(rate, 4, BigDecimal.ROUND_HALF_UP);
				
			} 
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
				presentAmount = presentUseAmount;
			}
			data.setPurchaseItemReturnAmount(data.getPurchaseItemReturnAmount().add(amount));
			data.setPurchaseItemReturnMoney(data.getPurchaseItemReturnMoney().add(money));
			data.setPurchasePresentAmount(data.getPurchasePresentAmount().subtract(presentAmount));
			data.setPurchasePresentMoney(data.getPurchasePresentMoney().subtract(presentMoney));
		}

		List<PurchaseOrderCollect> list = new ArrayList<PurchaseOrderCollect>(map.values());
		for (int i = 0; i < list.size(); i++) {
			PurchaseOrderCollect data = list.get(i);
			data.setAmountTotal(data.getPurchaseItemAmount().subtract(data.getPurchaseItemReturnAmount()));
			data.setMoneyTotal(data.getPurchaseItemMoney().subtract(data.getPurchaseItemReturnMoney()));
		}
		return list;
	}

	@Override
	public List<SupplierLianYing> findSupplierLianYing(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		Integer branchNum = supplierSaleQuery.getBranchNum();
		List<Integer> transferBranchNums = new ArrayList<Integer>();
		transferBranchNums.add(branchNum);
		List<Integer> itemNums = supplierSaleQuery.getItemNums();

		List<Supplier> suppliers = supplierDao.find(systemBookCode, branchNum, null, null, true, null, null);
		List<PosItem> posItems = posItemService.find(systemBookCode, null, itemNums, AppConstants.BUSINESS_TYPE_SHOP);
		Map<Integer, PosItem> baseMap = new HashMap<Integer, PosItem>();
		List<SupplierLianYing> list = new ArrayList<SupplierLianYing>();
		for (int i = 0; i < posItems.size(); i++) {
			PosItem posItem = posItems.get(i);
			baseMap.put(posItem.getItemNum(), posItem);
		}
		List<Object[]> objects = posOrderDao.findItemSupplierInfoByCategory(systemBookCode, supplierSaleQuery.getBranchNums(), supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, true, itemNums);
		for (Object[] object : objects) {
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if (supplierNum == null || (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierLianYing data = new SupplierLianYing();
			PosItem item = baseMap.get(itemNum);
			if (item == null) {
				continue;
			}
			Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
			if (supplier == null || !supplier.getSupplierMethod().equals(AppConstants.BUSINESS_TYPE_SHOP)) {
				continue;
			}
			data.setItemNum(itemNum);
			data.setItemCode(item.getItemCode());
			data.setItemName(item.getItemName());
			data.setItemSpec(item.getItemSpec());
			data.setCategoryCode(item.getItemCategoryCode());
			data.setCategoryName(item.getItemCategory());
			data.setSupplierNum(supplierNum);
			data.setSupplierCode(supplier.getSupplierCode());
			data.setSupplierName(supplier.getSupplierName());
			if (item.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0) {
				amount = amount.divide(item.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP);
			}
			data.setSaleAmount(amount);
			data.setSaleMoney(saleMoney);
			if (item.getItemGrossRate().compareTo(BigDecimal.ZERO) != 0) {
				data.setPointMoney(saleMoney.multiply(item.getItemGrossRate()).divide(BigDecimal.valueOf(100), 4, BigDecimal.ROUND_HALF_UP));
			}
			data.setPayMoney(data.getSaleMoney().subtract(data.getPointMoney()));
			list.add(data);
		}
		return list;
	}
	@Override
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo,Boolean isMember) {


		List<Object[]> objects = null;
		if(queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)){
			objects = reportDao.findMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}

		return objects;
	}


	@Override
	public List<Object[]> findDepositByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo ) {
		List<Object[]> objects = reportDao.findDepositByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		return objects;
	}

	@Override
	public List<Object[]> findConsumeByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Object[]> objects = reportDao.findConsumeByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		return objects;
	}

	@Override
	public List<Object[]> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo) {
		return reportDao.findLossMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findCheckMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findDifferenceMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findDifferenceMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findCardUserCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findCardUserCountByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

}
