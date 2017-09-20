package com.nhsoft.report.rpc.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.*;
import com.nhsoft.report.model.*;
import com.nhsoft.report.rpc.Report2Rpc;
import com.nhsoft.report.service.*;
import com.nhsoft.report.shared.queryBuilder.*;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.DateUtil;
import com.nhsoft.report.util.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

@Service
public class Report2RpcImpl implements Report2Rpc {
	
	@Autowired
	private Report2Service report2Service;
	@Autowired
	private PosOrderService posOrderService;
	@Autowired
	private ReceiveOrderService receiveOrderService;
	@Autowired
	private PosItemService posItemService;
	@Autowired
	private TransferOutOrderService transferOutOrderService;
	@Autowired
	private TransferInOrderService transferInOrderService;
	@Autowired
	private StorehouseService storehouseService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private CardUserService cardUserService;
	@Autowired
	private CardDepositService cardDepositService;
	@Autowired
	private CardConsumeService cardConsumeService;
	@Autowired
	private BranchTransferGoalsService branchTransferGoalsService;
	@Autowired
	private OtherInoutService otherInoutService;
	@Autowired
	private PosItemGradeService posItemGradeService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private StoreMatrixService storeMatrixService;
	@Autowired
	private PosItemLogService posItemLogService;
	private ItemVersionService itemVersionService;

	private PolicyPromotionQuantityService policyPromotionQuantityService;
	private PolicyPromotionMoneyService policyPromotionMoneyService;
	private PolicyPromotionService policyPromotionService;
	private PolicyPresentService policyPresentService;
	private StoreItemSupplierService storeItemSupplierService;
	
	@Override
	public List<TransferPolicyDTO> findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery) {
		return report2Service.findTransferPolicyDTOs(policyPosItemQuery);
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return report2Service.findPosReceiveDiffMoneySumDTOsByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return report2Service.findPosReceiveDiffMoneySumDTOsByBranchCasher(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public BigDecimal getBranchBalanceMoney(String systemBookCode, Integer centerBranchNum, Integer branchNum) {
		return report2Service.getBranchBalanceMoney(systemBookCode,centerBranchNum,branchNum);
	}

	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByItem(OnlineOrderQuery onlineOrderQuery) {
		return report2Service.findOnlineOrderSaleAnalysisByItem(onlineOrderQuery);
	}

	@Override
	public List<OnlineOrderSaleAnalysisDTO> findOnlineOrderSaleAnalysisByBranchItem(OnlineOrderQuery onlineOrderQuery) {
		return report2Service.findOnlineOrderSaleAnalysisByBranchItem(onlineOrderQuery);
	}

	@Override
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchDayItems(SaleAnalysisQueryData policyPosItemQuery) {
		return report2Service.findSaleAnalysisByBranchDayItems(policyPosItemQuery);
	}

	@Override
	public List<Object[]> findProfitAnalysisByBranchDayItem(ProfitAnalysisQueryData profitAnalysisQueryData) {
		return report2Service.findProfitAnalysisByBranchDayItem(profitAnalysisQueryData);
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		return report2Service.findPosReceiveDiffMoneySumDTOsByShiftTable(systemBookCode,branchNums,dateFrom,dateTo,casher);
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return report2Service.findCardQtySumDatasByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<CardQtySumDTO> findCardQtySumDatasByBranchAndDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return report2Service.findCardQtySumDatasByBranchAndDay(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<PurchaseOrderCollect> findLatestReceiveDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		return report2Service.findLatestReceiveDetail(purchaseOrderCollectQuery);
	}

	@Override
	public List<CardDepositCommissionDTO> findCardDepositCommissionDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer groupType, String seller) {
		return report2Service.findCardDepositCommissionDTOs(systemBookCode,branchNums,dateFrom,dateTo,groupType,seller);
	}

	@Override
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<Integer> itemNums, String saleType, Date timeFrom, Date timeTo) {
		return report2Service.findCustomerAnalysisTimePeriods(systemBookCode,dateFrom,dateTo,branchNums,itemNums,saleType,timeFrom,timeTo);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return report2Service.findCardReportByBranchDay(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<SaleAnalysisBranchItemGradeDTO> findSaleAnalysisBranchItemGradeDTOs(SaleAnalysisQueryData saleAnalysisQueryData) {
		return report2Service.findSaleAnalysisBranchItemGradeDTOs(saleAnalysisQueryData);
	}

	@Override
	public List<ItemSummaryDTO> findReceiveSaleReport(String systemBookCode, List<Integer> branchNums, Date dateFrom,
			Date dateTo, List<Integer> itemNums, List<String> categoryCodes) {
		ReportUtil<ItemSummaryDTO> reportUtil = new ReportUtil<ItemSummaryDTO>(ItemSummaryDTO.class);
		List<Object[]> objects = posOrderService.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, true);
		Set<Integer> itemNumSet = new HashSet<Integer>();
		for(Object[] object : objects) {
			ItemSummaryDTO dto = reportUtil.getInstance();
			itemNumSet.add((Integer)object[0]);
			dto.setItemNum((Integer)object[0]);
			dto.setPosSaleQty(AppUtil.getValue(object[1], BigDecimal.class));
			dto.setPosSaleMoney(AppUtil.getValue(object[2], BigDecimal.class));
			reportUtil.add(dto);
		}
		List<Object[]> objects2 = receiveOrderService.findItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
		for(Object[] object : objects2) {
			ItemSummaryDTO dto = reportUtil.getInstance();
			itemNumSet.add((Integer)object[0]);
			dto.setItemNum((Integer)object[0]);
			dto.setReceiveOrderQty(AppUtil.getValue(object[1], BigDecimal.class));
			dto.setReceiveOrderMoney(AppUtil.getValue(object[2], BigDecimal.class));
			reportUtil.add(dto);
		}
		List<PosItem> posItems = new ArrayList<PosItem>();
		if(itemNumSet.size() > 0) {
			posItems = posItemService.findByItemNumsWithoutDetails(new ArrayList<Integer>(itemNumSet));
		}
		List<ItemSummaryDTO> dtos = reportUtil.toList();
		for(int i = dtos.size()-1;i>=0;i--) {
			ItemSummaryDTO dto = dtos.get(i);
			dto.setProfit(dto.getPosSaleMoney().subtract(dto.getReceiveOrderMoney()));
			PosItem item = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if(item == null) {
				continue;
			}
			if(categoryCodes != null && categoryCodes.size() > 0 && !categoryCodes.contains(item.getItemCategoryCode())) {
				dtos.remove(i);
			}
			dto.setItemCode(item.getItemCode());
			dto.setItemName(item.getItemName());
			dto.setItemUnit(item.getItemUnit());
			dto.setItemSpec(item.getItemSpec());
		}
		return dtos;
	}

	@Override
	public List<UnTransferedItemDTO> findUnTransferedItemDTOs(String systemBookCode, Integer transferBranchNum, List<Integer> branchNums) {
		List<Storehouse> storehouses = storehouseService.findByBranch(systemBookCode, transferBranchNum);
		List<Integer> storehouseNums = new ArrayList<Integer>();
		for(Storehouse storehouse : storehouses) {
			if(storehouse.getStorehouseActived() == null || !storehouse.getStorehouseActived()) {
				continue;
			}
			if(storehouse.getStorehouseDelTag() != null && storehouse.getStorehouseDelTag()) {
				continue;
			}
			if(storehouse.getStorehouseCenterTag() == null || !storehouse.getStorehouseCenterTag()) {
				continue;
			}
			storehouseNums.add(storehouse.getStorehouseNum());
		}
		//List<Object[]> branchStorehouses = storehouseService.find
		List<Object[]> objects = transferOutOrderService.findUnTransferedItems(systemBookCode, transferBranchNum, branchNums, storehouseNums);
		ReportUtil<UnTransferedItemDTO> reportUtil = new ReportUtil<UnTransferedItemDTO>(UnTransferedItemDTO.class);
		UnTransferedItemDTO dto = reportUtil.getInstance();
		for(Object[] object : objects) {
			//dto.((Integer)object[0]);
			dto.setItemAmount(AppUtil.getValue(object[1], BigDecimal.class));
		}
		//List<UnTransferedItemDTO>
		return null;
	}

	@Override
	public List<CustomReportDTO> findCustomReportByBranch(String systemBookCode, CustomReportQuery customReportQuery) {
		ReportUtil<CustomReportDTO> reportUtil = new ReportUtil<CustomReportDTO>(CustomReportDTO.class);
		if(customReportQuery.isQueryCardPos()) {
			List<Object[]> objects = posOrderService.findCustomReportByBranch(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), customReportQuery.getDateType());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setSaleMoney(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setSaleProfit(AppUtil.getValue(object[2], BigDecimal.class));
				dto.setSaleOrderCount(AppUtil.getValue(object[3], Integer.class));
				dto.setSaleItemCount(AppUtil.getValue(object[4], Integer.class));
				dto.setValidSaleOrderCount(AppUtil.getValue(object[5], Integer.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardPos()) {
			CardReportQuery cardReportQuery = new CardReportQuery();
			cardReportQuery.setSystemBookCode(customReportQuery.getSystemBookCode());
			cardReportQuery.setBranchNums(customReportQuery.getBranchNums());
			cardReportQuery.setDateFrom(customReportQuery.getDateFrom());
			cardReportQuery.setDateTo(customReportQuery.getDateTo());
			List<Object[]> objects = posOrderService.findSummaryByBranch(cardReportQuery);
			BigDecimal money = null;
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				
				money = (BigDecimal) object[2];
				money = money.subtract(object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5]);
				money = money.add(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

				dto.setBranchNum((Integer)object[0]);
				dto.setCardSaleOrderCount(AppUtil.getValue(object[1], Integer.class));
				dto.setCardSaleConsumeMoney(AppUtil.getValue(money, BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryChain()) {
			List<Object[]> objects = transferOutOrderService.findMoneyByBranch(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[1], BigDecimal.class));
				reportUtil.add(dto);
			}
			objects = transferInOrderService.findMoneyByBranch(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[1], BigDecimal.class).negate());
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryGoals()) {
			List<Object[]> objects = branchTransferGoalsService.findSummaryByDate(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), customReportQuery.getDateType());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setTransferTarget(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setSaleMoneyTarget(AppUtil.getValue(object[2], BigDecimal.class));
				dto.setSaleProfitTarget(AppUtil.getValue(object[3], BigDecimal.class));
				dto.setTransferProfitTarget(AppUtil.getValue(object[4], BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryNewCard()) {
			List<Object[]> objects = cardUserService.findCardCount(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setNewCardCount(AppUtil.getValue(object[1], Integer.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryRevokeCard()) {
			List<Object[]> objects = cardUserService.findRevokeCardCount(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setRevokeCardCount(AppUtil.getValue(object[1], Long.class).intValue());
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardDeposit()) {
			List<Object[]> objects = cardDepositService.findBranchSum(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setCardDepositCash(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setCardDepositMoney(AppUtil.getValue(object[2], BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardConsume()) {
			List<Object[]> objects = cardConsumeService.findBranchSum(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				dto.setCardConsumeMoney(AppUtil.getValue(object[1], BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryAdjust()) {
			List<Object[]> objects = posItemLogService.findBranchItemFlagSummary(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), customReportQuery.getSummaries(), null, null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBranchNum((Integer)object[0]);
				if(object[2] != null && (Boolean)object[2]) {
					dto.setAdjustAmount(AppUtil.getValue(object[3], BigDecimal.class));
					dto.setAdjustMoney(AppUtil.getValue(object[4], BigDecimal.class));
				} else {
					dto.setAdjustAmount(AppUtil.getValue(object[3], BigDecimal.class).negate());
					dto.setAdjustMoney(AppUtil.getValue(object[4], BigDecimal.class).negate());
				}
				reportUtil.add(dto);
			}
		}
		List<CustomReportDTO> dtos = reportUtil.toList();
		List<Branch> branches = branchService.findInCache(systemBookCode);
		for(CustomReportDTO ret : dtos) {
			Branch branch = AppUtil.getBranch(branches, ret.getBranchNum());
			if(branch == null) {
				continue;
			}
			ret.setBranchName(branch.getBranchName());
		}
		return dtos;
	}

	@Override
	public List<CustomReportDTO> findCustomReportByBizday(String systemBookCode, CustomReportQuery customReportQuery) {
		ReportUtil<CustomReportDTO> reportUtil = new ReportUtil<CustomReportDTO>(CustomReportDTO.class);
		if(customReportQuery.isQueryCardPos()) {
			List<Object[]> objects = posOrderService.findCustomReportByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setSaleMoney(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setSaleProfit(AppUtil.getValue(object[2], BigDecimal.class));
				dto.setSaleOrderCount(AppUtil.getValue(object[3], Integer.class));
				dto.setSaleItemCount(AppUtil.getValue(object[4], Integer.class));
				dto.setValidSaleOrderCount(AppUtil.getValue(object[5], Integer.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardPos()) {
			CardReportQuery cardReportQuery = new CardReportQuery();
			cardReportQuery.setSystemBookCode(customReportQuery.getSystemBookCode());
			cardReportQuery.setBranchNums(customReportQuery.getBranchNums());
			cardReportQuery.setDateFrom(customReportQuery.getDateFrom());
			cardReportQuery.setDateTo(customReportQuery.getDateTo());
			List<Object[]> objects = posOrderService.findSummaryByBizday(cardReportQuery);
			BigDecimal money;
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				
				money = (BigDecimal) object[2];
				money = money.subtract(object[5] == null?BigDecimal.ZERO:(BigDecimal)object[5]);
				money = money.add(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);
				dto.setCardSaleOrderCount(AppUtil.getValue(object[1], Integer.class));
				dto.setCardSaleConsumeMoney(AppUtil.getValue(money, BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryChain()) {
			List<Object[]> objects = transferOutOrderService.findMoneyByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[1], BigDecimal.class));
				reportUtil.add(dto);
			}
			objects = transferInOrderService.findMoneyByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[1], BigDecimal.class).negate());
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryNewCard()) {
			List<Object[]> objects = cardUserService.findCardCountByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setNewCardCount(AppUtil.getValue(object[1], Long.class).intValue());
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryRevokeCard()) {
			List<Object[]> objects = cardUserService.findRevokeCardCountByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setRevokeCardCount(AppUtil.getValue(object[1], Long.class).intValue());
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardDeposit()) {
			List<Object[]> objects = cardDepositService.findSumByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setCardDepositCash(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setCardDepositMoney(AppUtil.getValue(object[2], BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryCardConsume()) {
			List<Object[]> objects = cardConsumeService.findSumByBizday(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo());
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[0]);
				dto.setCardConsumeMoney(AppUtil.getValue(object[1], BigDecimal.class));
				reportUtil.add(dto);
			}
		}
		if(customReportQuery.isQueryAdjust()) {
			List<Object[]> objects = posItemLogService.findSumByBranchDateItemFlag(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), customReportQuery.getSummaries(), null, null, null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setBizday((String)object[1]);
				if(object[4] != null && (Boolean)object[4]) {
					dto.setAdjustAmount(AppUtil.getValue(object[5], BigDecimal.class));
					dto.setAdjustMoney(AppUtil.getValue(object[6], BigDecimal.class));
				} else {
					dto.setAdjustAmount(AppUtil.getValue(object[5], BigDecimal.class).negate());
					dto.setAdjustMoney(AppUtil.getValue(object[6], BigDecimal.class).negate());
				}
				reportUtil.add(dto);
			}
		}
		List<CustomReportDTO> dtos = reportUtil.toList();
		return dtos;
	}

	@Override
	public List<CustomReportDTO> findCustomReportByItem(String systemBookCode, CustomReportQuery customReportQuery) {
		ReportUtil<CustomReportDTO> reportUtil = new ReportUtil<CustomReportDTO>(CustomReportDTO.class);
		List<Integer> itemNums = new ArrayList<Integer>();
		if(customReportQuery.isQueryPos()) {
			List<Object[]> objects = posOrderService.findItemSum(systemBookCode, customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null, true);
			for(Object[] object : objects) {
				if(object[0] == null){
					continue;
				}
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setItemNum((Integer)object[0]);
				dto.setItemAmount(AppUtil.getValue(object[1], BigDecimal.class));
				dto.setSaleMoney(AppUtil.getValue(object[2], BigDecimal.class));
				dto.setSaleProfit(AppUtil.getValue(object[3], BigDecimal.class));
				reportUtil.add(dto);
				itemNums.add(dto.getItemNum());
			}
		}
		if(customReportQuery.isQueryChain()) {
			List<Object[]> objects = transferOutOrderService.findProfitGroupByItem(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null, null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setItemNum((Integer)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[3], BigDecimal.class));
				reportUtil.add(dto);
				
				if(!itemNums.contains(dto.getItemNum())){
					itemNums.add(dto.getItemNum());
					
				}
			}
			objects = transferInOrderService.findProfitGroupByItem(customReportQuery.getSystemBookCode(), customReportQuery.getCenterBranchNums(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), null, null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setItemNum((Integer)object[0]);
				dto.setCenterTransferMoney(AppUtil.getValue(object[3], BigDecimal.class).negate());
				reportUtil.add(dto);
				
				if(!itemNums.contains(dto.getItemNum())){
					itemNums.add(dto.getItemNum());
					
				}
			}
		}
		if(customReportQuery.isQueryAdjust()) {
			List<Object[]> objects = posItemLogService.findSumByItemFlag(customReportQuery.getSystemBookCode(), customReportQuery.getBranchNums(), customReportQuery.getDateFrom(), customReportQuery.getDateTo(), customReportQuery.getSummaries(), null, null, null);
			for(Object[] object : objects) {
				CustomReportDTO dto = reportUtil.getInstance();
				dto.setItemNum((Integer)object[0]);
				if(object[2] != null && (Boolean)object[2]) {
					dto.setAdjustAmount(AppUtil.getValue(object[3], BigDecimal.class));
					dto.setAdjustMoney(AppUtil.getValue(object[4], BigDecimal.class));
				} else {
					dto.setAdjustAmount(AppUtil.getValue(object[3], BigDecimal.class).negate());
					dto.setAdjustMoney(AppUtil.getValue(object[4], BigDecimal.class).negate());
				}
				reportUtil.add(dto);

				if(!itemNums.contains(dto.getItemNum())){
					itemNums.add(dto.getItemNum());

				}
			}
		}
		List<CustomReportDTO> dtos = reportUtil.toList();
		if(itemNums.isEmpty()){
			return dtos;
		}
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		for(int i = dtos.size()-1;i>=0;i--) {
			CustomReportDTO ret = dtos.get(i);
			PosItem posItem = AppUtil.getPosItem(ret.getItemNum(), posItems);
			if(posItem == null) {
				dtos.remove(i);
				continue;
			}
			if(customReportQuery.getItemCategoryCodes() != null && customReportQuery.getItemCategoryCodes().size() > 0 && !customReportQuery.getItemCategoryCodes().contains(posItem.getItemCategoryCode())) {
				dtos.remove(i);
				continue;
			}
			ret.setItemName(posItem.getItemName());
			ret.setItemCode(posItem.getItemCode());
			ret.setItemSpec(posItem.getItemSpec());
			ret.setItemUnit(posItem.getItemUnit());
			ret.setItemCategoryCode(posItem.getItemCategoryCode());
			ret.setItemCategoryName(posItem.getItemCategory());
		}
		return dtos;
	}

	@Override
	public List<CustomReportDTO> findCustomReportByCategory(String systemBookCode, CustomReportQuery customReportQuery) {
		List<CustomReportDTO> dtos = findCustomReportByItem(systemBookCode, customReportQuery);
		ReportUtil<CustomReportDTO> categoryDTOs = new ReportUtil<>(CustomReportDTO.class);
		dtos.forEach(d -> {
			d.setItemNum(null);
			categoryDTOs.add(d);
		});
		return categoryDTOs.toList();
	}

	@Override
	public List<PolicyPosItem> findPolicyPosItems(PolicyPosItemQuery policyPosItemQuery) {
		List<PolicyPosItem> policyPosItems = new ArrayList<PolicyPosItem>();
		List<Integer> queryBranchNums = policyPosItemQuery.getBranchNums();
		if(StringUtils.isNotEmpty(policyPosItemQuery.getPolicyType())){
			String[] array = policyPosItemQuery.getPolicyType().split(",");
			for(int i = 0;i < array.length;i++){
				String type = array[i];
				
				if(type.equals(AppConstants.POLICY_PROMOTION)){
					policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.POLICY_PRENSENT)){
					policyPosItems.addAll(policyPresentService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.POLICY_PROMOTION_MONEY)){
					policyPosItems.addAll(policyPromotionMoneyService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.POLICY_PROMOTION_QUANTITY)){
					policyPosItems.addAll(policyPromotionQuantityService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.PROMOTION_QUANTITY_CATEGORY_TRANSFER)){
					
					policyPosItemQuery.setPolicyCategory(AppConstants.PROMOTION_QUANTITY_CATEGORY_TRANSFER);
					policyPosItems.addAll(policyPromotionQuantityService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.POLICY_PROMOTION_CATEGORY_PURCHASE)){
					policyPosItemQuery.setPolicyCategory(AppConstants.POLICY_PROMOTION_CATEGORY_PURCHASE);
					policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
				} else if(type.equals(AppConstants.POLICY_PROMOTION_CATEGORY_WHOLESALE)){
					policyPosItemQuery.setPolicyCategory(AppConstants.POLICY_PROMOTION_CATEGORY_WHOLESALE);
					policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
				}
									
			}
			
		} else {
			policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
			policyPosItemQuery.setPolicyCategory(AppConstants.POLICY_PROMOTION_CATEGORY_PURCHASE);
			policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
			policyPosItemQuery.setPolicyCategory(AppConstants.POLICY_PROMOTION_CATEGORY_WHOLESALE);
			policyPosItems.addAll(policyPromotionService.findPolicyPosItems(policyPosItemQuery));
			policyPosItemQuery.setPolicyCategory(null);
			policyPosItems.addAll(policyPresentService.findPolicyPosItems(policyPosItemQuery));
			policyPosItems.addAll(policyPromotionMoneyService.findPolicyPosItems(policyPosItemQuery));
			policyPosItems.addAll(policyPromotionQuantityService.findPolicyPosItems(policyPosItemQuery));
			policyPosItemQuery.setPolicyCategory(AppConstants.PROMOTION_QUANTITY_CATEGORY_TRANSFER);
			policyPosItems.addAll(policyPromotionQuantityService.findPolicyPosItems(policyPosItemQuery));
		}
		if(policyPosItems.isEmpty()){
			return policyPosItems;
		}
		
		Set<Integer> itemNums = new HashSet<Integer>();
		Set<Integer> itemGradeNums = new HashSet<Integer>();
		for(int i = policyPosItems.size() - 1;i >= 0;i--){
			PolicyPosItem data = policyPosItems.get(i);
			itemNums.add(data.getItemNum());
			if(data.getItemGradeNum() != null) {
				itemGradeNums.add(data.getItemGradeNum());
			}
		}
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(new ArrayList<Integer>(itemNums));
		List<PosItemGrade> posItemGrades = new ArrayList<PosItemGrade>();
		if(itemGradeNums.size() > 0) {
			posItemGrades = posItemGradeService.findByIds(new ArrayList<Integer>(itemGradeNums));
		}
		for(int i = policyPosItems.size() - 1;i >= 0;i--){
			PolicyPosItem data = policyPosItems.get(i);
			List<Branch> applyBranchs = Branch.readFromXml(data.getPolicyAppliedBranch());
			if(queryBranchNums != null && queryBranchNums.size() > 0 ){
				if(!hasSameBranch(applyBranchs, queryBranchNums)){
					policyPosItems.remove(i);
					continue;
				}
			}
			data.setPolicyAppliedBranch(getApplyBranchNames(applyBranchs));
			PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
			data.setItemCode(posItem.getItemCode());
			data.setItemName(posItem.getItemName());
			data.setItemSpec(posItem.getItemSpec());
			data.setItemCategoryName(posItem.getItemCategory());
			data.setItemCategoryCode(posItem.getItemCategoryCode());
			data.setItemUnit(posItem.getItemUnit());
			if(data.getPolicyType().equals(AppConstants.POLICY_PRENSENT)){
				data.setPolicyPrice(posItem.getItemRegularPrice());
				data.setPolicyStdPrice(posItem.getItemRegularPrice());
			} else if(data.getPolicyType().equals(AppConstants.POLICY_PROMOTION_MONEY)){
				data.setPolicyStdPrice(posItem.getItemRegularPrice());
			}
			if(data.getItemGradeNum() != null) {
				PosItemGrade posItemGrade = AppUtil.getPosItemGrade(posItemGrades, data.getItemGradeNum());
				if(posItemGrade != null) {
					data.setItemGradeName(posItemGrade.getItemGradeName());
				}
			}
		}
		return policyPosItems;
	}
	
	private boolean hasSameBranch(List<Branch> branchs, List<Integer> branchNums) {
		if (branchs.size() == 0) {
			return true;
		}
		for (int i = 0; i < branchs.size(); i++) {
			Branch branch = branchs.get(i);
			if (branchNums.contains(branch.getId().getBranchNum())) {
				return true;
			}
		}
		return false;
	}
	
	private String getApplyBranchNames(List<Branch> branchs){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < branchs.size();i++){
			Branch branch = branchs.get(i);
			if(sb.toString().isEmpty()){
				sb.append(branch.getBranchName());
			} else {
				sb.append("," + branch.getBranchName());
			}
		}
		return sb.toString();
	}

	@Override
	public List<OtherInoutReportDTO> findOtherInoutReportDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<OtherInoutReportDTO> list = new ArrayList<OtherInoutReportDTO>();
		
		List<Object[]> objects = otherInoutService.findPosBranchFlagKindSummary(systemBookCode, branchNums, dateFrom, dateTo);
		if(objects.isEmpty()){
			return list;
		}
		Object[] object;
		Integer branchNum;
		boolean flag = false;
		String kind = null;
		BigDecimal money = null;
		OtherInoutReportDTO dto;
		NameAndValueDTO detailDTO;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			branchNum = (Integer) object[0];
			flag = (Boolean) object[1];
			kind = object[2] == null?"":(String)object[2];
			money = object[3] == null?BigDecimal.ZERO:(BigDecimal)object[3];
			if(money.compareTo(BigDecimal.ZERO) == 0){
				continue;
			}
			
			dto = OtherInoutReportDTO.get(list, branchNum);
			if(dto == null){
				dto = new OtherInoutReportDTO();
				dto.setBranchNum(branchNum);
				list.add(dto);
			}
			
			
			if(flag && money.compareTo(BigDecimal.ZERO) > 0){
				detailDTO = NameAndValueDTO.get(dto.getInDetails(), kind);
				if(detailDTO == null){
					detailDTO = new NameAndValueDTO();
					detailDTO.setName(kind);
					detailDTO.setValue(BigDecimal.ZERO);
					dto.getInDetails().add(detailDTO);
				}
				detailDTO.setValue(detailDTO.getValue().add(money));

			} else {
				if(flag && money.compareTo(BigDecimal.ZERO) < 0){
					money = money.negate();

				}
				detailDTO = NameAndValueDTO.get(dto.getOutDetails(), kind);
				if(detailDTO == null){
					detailDTO = new NameAndValueDTO();
					detailDTO.setName(kind);
					detailDTO.setValue(BigDecimal.ZERO);
					dto.getOutDetails().add(detailDTO);
				}
				detailDTO.setValue(detailDTO.getValue().add(money));

			}
		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < list.size();i++){
			dto = list.get(i);
			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			dto.setBranchName(branch.getBranchName());
		}
		return list;
	}


	@Override
	public List<SalerCommissionDetail> findItemSalerCommissionDetails(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, 
			List<String> salerNames) {

		List<EmployeeItem> employeeItems = employeeService.findEmployeeItems(systemBookCode, branchNums, null, salerNames);
		List<Integer> itemNums = null;
		Integer itemNum = null;
		Integer branchNum = null;
		List<String> branchItems = null;
		if(salerNames != null && !salerNames.isEmpty()){
			itemNums = new ArrayList<Integer>();
			branchItems = new ArrayList<String>();
			for(int i = 0;i < employeeItems.size();i++){
				branchNum = employeeItems.get(i).getId().getBranchNum();
				itemNum = employeeItems.get(i).getId().getItemNum();
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
				branchItems.add(branchNum + "-" + itemNum);
			}
		}
		
		List<SalerCommissionDetail> list = new ArrayList<SalerCommissionDetail>();
		List<Object[]> objects = posOrderService.findBranchItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		Object[] object = null;
		itemNums = new ArrayList<Integer>();
		
		String name = null;
		EmployeeItem employeeItem = null;
		for(int i = 0;i < objects.size();i++){
			object = objects.get(i);
			branchNum = (Integer) object[0];
			itemNum = (Integer) object[1];
			if(branchItems != null){
				if(!branchItems.contains(branchNum + "-" + itemNum)){
					continue;
				}
			}
			
			SalerCommissionDetail detail = new SalerCommissionDetail();
			
			employeeItem = EmployeeItem.get(employeeItems, branchNum, itemNum);
			if(employeeItem == null){
				name = "未指定销售员";
			} else {
				name = employeeItem.getEmployeeName();
			}
			
			detail.setSaler(name);
			detail.setBranchNum(branchNum);
			detail.setItemNum(itemNum);
			detail.setSaleNums(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			detail.setSaleMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			detail.setSaleCommission(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			detail.setSaleProfit(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			detail.setSaleCost(detail.getSaleMoney().subtract(detail.getSaleProfit()));
			list.add(detail);
			
			
			if(!itemNums.contains(itemNum)){
				itemNums.add(itemNum);
			}
			
		}
		if(list.isEmpty()){
			return list;
		}

		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(itemNums);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for (int i = 0; i < list.size(); i++) {
			SalerCommissionDetail salerCommissionDetail = list.get(i);
			PosItem posItem = AppUtil.getPosItem(salerCommissionDetail.getItemNum(), posItems);
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
		return list;
	}

	@Override
	public List<RequestAnalysisDTO> findRequestAnalysisDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> itemCategoryCodes) {
		Integer branchNum = branchNums.get(0);
		
		PosItemQuery posItemQuery = new PosItemQuery();
		posItemQuery.setSystemBookCode(systemBookCode);
		posItemQuery.setBranchNum(branchNum);
		posItemQuery.setCategoryCodes(itemCategoryCodes);
		posItemQuery.setFilterType(AppConstants.ITEM_TYPE_CHAIN);
		posItemQuery.setIsFindNoStock(false);
		posItemQuery.setPaging(false);
		posItemQuery.setItemNums(itemNums);
		List<Integer> chainItemNums = posItemService.findItemNumsByPosItemQuery(posItemQuery, 0, 0);
		if(chainItemNums.isEmpty()){
			return new ArrayList<RequestAnalysisDTO>();
		}
		ReportUtil<RequestAnalysisDTO> reportUtil = new ReportUtil<RequestAnalysisDTO>(RequestAnalysisDTO.class);
		ABCListQuery query = new ABCListQuery();
		query.setSystemBookCode(systemBookCode);
		query.setBranchNums(branchNums);
		query.setDateFrom(dateFrom);
		query.setDateTo(dateTo);
		query.setCategoryCodeList(itemCategoryCodes);
		
		List<ABCAnalysis> analysisList = reportService.findABCDatasBySaleV2(query);
		if(analysisList.isEmpty()){
			return new ArrayList<RequestAnalysisDTO>();
		}
		Set<Integer> innerItemNums = new HashSet<Integer>();
		
		for(ABCAnalysis analysis : analysisList) {
			if(!chainItemNums.contains(analysis.getItemNum())) {
				continue;
			}
			innerItemNums.add(analysis.getItemNum());
			RequestAnalysisDTO dto = reportUtil.getInstance();
			dto.setItemNum(analysis.getItemNum());
			dto.setAbc(analysis.getABC());
			dto.setSaleQty(analysis.getSaleQty());
			reportUtil.add(dto);
		}
		List<PosItem> posItems = null;
		List<StoreMatrix> storeMatrices = null;
		List<StoreItemSupplier> storeItemSuppliers = null;
		List<Object[]> objects = new ArrayList<Object[]>();
		if(innerItemNums.size() > 0) {
			List<Integer> itemNumList = new ArrayList<Integer>(innerItemNums);
			posItems = posItemService.findByItemNums(itemNumList);
			objects = inventoryService.findItemAmount(systemBookCode, branchNums, itemNumList);
			storeItemSuppliers = storeItemSupplierService.findDefaults(systemBookCode, Arrays.asList(branchNums.get(0)), itemNumList);
			storeMatrices = storeMatrixService.findByBranch(systemBookCode, branchNums.get(0), itemNumList);
		}
		for(Object[] object : objects) {
			Integer itemNum = (Integer)object[0];
			BigDecimal inventoryAmount = AppUtil.getValue(object[1], BigDecimal.class);
			RequestAnalysisDTO dto = reportUtil.getInstance();
			dto.setItemNum(itemNum);
			dto.setInventoryQty(inventoryAmount);
			reportUtil.add(dto);
		}
		List<Supplier> suppliers = supplierService.findInCache(systemBookCode);
		List<RequestAnalysisDTO> dtos = reportUtil.toList();
		Branch branch = branchService.readInCache(systemBookCode, branchNum);
		for(int i = dtos.size()-1;i>=0;i--) {
			RequestAnalysisDTO dto = dtos.get(i);
			PosItem item = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if(item == null) {
				dtos.remove(i);
				continue;
			}
			StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, dto.getItemNum(), storeMatrices);
			dto.setItemCode(item.getItemCode());
			dto.setItemBarcode(item.getItemBarcode());
			dto.setItemName(item.getItemName());
			dto.setItemSpec(item.getItemSpec());
			if(storeMatrix != null && storeMatrix.getStoreMatrixSaleCeaseFlag() != null) {
				dto.setItemSaleCease(storeMatrix.getStoreMatrixSaleCeaseFlag());
			} else {
				dto.setItemSaleCease(item.getItemSaleCeaseFlag());
			}
			dto.setItemSalePrice(item.getItemRegularPrice());
			dto.setItemLevel2Price(item.getItemLevel2Price());
			if(branch.getBranchMatrixPriceActived() && storeMatrix != null
					&& storeMatrix.getStoreMatrixPriceEnabled() != null && storeMatrix.getStoreMatrixPriceEnabled()) {
				if(storeMatrix.getStoreMatrixRegularPrice() != null && storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0) {
					dto.setItemSalePrice(storeMatrix.getStoreMatrixRegularPrice());
				}
				if(storeMatrix.getStoreMatrixLevel2Price() != null && storeMatrix.getStoreMatrixLevel2Price().compareTo(BigDecimal.ZERO) > 0) {
					dto.setItemLevel2Price(storeMatrix.getStoreMatrixLevel2Price());
				}
			}
			dto.setItemUnit(item.getItemUnit());
			dto.setItemTransferUnit(item.getItemTransferUnit());
			dto.setItemTransferRate(item.getItemTransferRate());
			dto.setItemType(item.getItemType());
			
			if(dto.getItemType() == AppConstants.C_ITEM_TYPE_ASSEMBLE){
				List<PosItemKit> posItemKits = posItemService.findPosItemKits(dto.getItemNum());
				dto.setDetails(new ArrayList<RequestAnalysisDTO>());
				for(int j = 0;j < posItemKits.size();j++){
					PosItemKit posItemKit = posItemKits.get(j);
					RequestAnalysisDTO detail = new RequestAnalysisDTO();
					detail.setItemNum(posItemKit.getPosItem().getItemNum());
					detail.setItemName(posItemKit.getPosItem().getItemName());
					detail.setItemSpec(posItemKit.getPosItem().getItemSpec());
					detail.setItemCode(posItemKit.getPosItem().getItemCode());
					detail.setItemUnit(posItemKit.getPosItem().getItemUnit());
					detail.setItemTransferUnit(posItemKit.getPosItem().getItemTransferUnit());
					detail.setItemTransferRate(posItemKit.getPosItem().getItemTransferRate());
					dto.getDetails().add(detail);
					
				}
				
			}
			StoreItemSupplier storeItemSupplier = StoreItemSupplier.getDefault(storeItemSuppliers, branchNums.get(0), dto.getItemNum());
			if(storeItemSupplier == null) {
				continue;
			}
			Supplier supplier = AppUtil.getSupplier(storeItemSupplier.getId().getSupplierNum(), suppliers);
			if(supplier == null) {
				continue;
			}
			dto.setSupplierNum(supplier.getSupplierNum());
			dto.setSupplierCode(supplier.getSupplierCode());
			dto.setSupplierName(supplier.getSupplierName());
			dto.setSupplierMethod(supplier.getSupplierMethod());
			dto.setSupplierKind(supplier.getSupplierKind());
		}
		return dtos;
	}
	
	@Override
	public List<NewItemAnalysis> findNewItemAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums,
	                                                 List<String> itemCategoryCodes, int newItemValidDay) {

		List<ItemVersion> itemVersions = itemVersionService.findFirst(systemBookCode, DateUtil.addDay(dateFrom, -newItemValidDay), dateTo, "POS_ITEM");
		List<NewItemAnalysis> list = new ArrayList<NewItemAnalysis>();
		if(itemVersions.isEmpty()){
			return list;
		}
		List<Integer> existsItemNums = new ArrayList<Integer>();
		for(int i = 0;i < itemVersions.size();i++){
			ItemVersion itemVersion = itemVersions.get(i);

			NewItemAnalysis newItemAnalysis = new NewItemAnalysis();
			newItemAnalysis.setItemCreator(itemVersion.getItemVersionOperator());
			newItemAnalysis.setItemCreateDate(itemVersion.getItemVersionTime());
			newItemAnalysis.setItemNum(Integer.parseInt(itemVersion.getItemVersionRef()));
			if(itemNums != null && !itemNums.isEmpty()){
				if(!itemNums.contains(newItemAnalysis.getItemNum())){
					continue;
				}
			}
			existsItemNums.add(newItemAnalysis.getItemNum());
			list.add(newItemAnalysis);
		}
		if(list.isEmpty()){
			return list;
		}
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(existsItemNums);
		Date saleDateFrom = null;
		Date saleDateTo = null;
		Date newItemValidDate = null;
		dateFrom = DateUtil.getMinOfDate(dateFrom);
		dateTo = DateUtil.getMaxOfDate(dateTo);
		for(int i = list.size() - 1;i >= 0;i--){
			NewItemAnalysis newItemAnalysis = list.get(i);
			PosItem posItem = AppUtil.getPosItem(newItemAnalysis.getItemNum(), posItems);
			if(itemCategoryCodes != null && !itemCategoryCodes.isEmpty()){
				if(!itemCategoryCodes.contains(posItem.getItemCategoryCode())){
					list.remove(i);
					continue;
				}
			}
			newItemAnalysis.setItemName(posItem.getItemName());
			newItemAnalysis.setItemCode(posItem.getItemCode());
			newItemAnalysis.setItemSpec(posItem.getItemSpec());
			newItemAnalysis.setItemUnit(posItem.getItemUnit());
			newItemAnalysis.setItemSaleMoney(BigDecimal.ZERO);
			newItemAnalysis.setItemSaleProfit(BigDecimal.ZERO);
			if(newItemAnalysis.getItemCreateDate().compareTo(dateFrom) > 0){
				saleDateFrom = newItemAnalysis.getItemCreateDate();
			} else {
				saleDateFrom = dateFrom;
			}

			newItemValidDate = DateUtil.addDay(newItemAnalysis.getItemCreateDate(), newItemValidDay);
			if(newItemValidDate.compareTo(dateTo) > 0){
				saleDateTo = dateTo;
			} else {
				saleDateTo = newItemValidDate;
			}
			existsItemNums = new ArrayList<Integer>();
			existsItemNums.add(newItemAnalysis.getItemNum());
			List<Object[]> objects = posOrderService.findItemSum(systemBookCode, branchNums, saleDateFrom, saleDateTo, existsItemNums, true);
			if(!objects.isEmpty()){
				newItemAnalysis.setItemSaleMoney((objects.get(0))[2] == null?BigDecimal.ZERO:(BigDecimal) (objects.get(0))[2]);
				newItemAnalysis.setItemSaleProfit((objects.get(0))[3] == null?BigDecimal.ZERO:(BigDecimal) (objects.get(0))[3]);
			}

		}
		return list;
	}
	
}