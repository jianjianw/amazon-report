package com.nhsoft.module.report.rpc.impl;



import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.rpc.Report2Rpc;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
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
	@Autowired
	private ItemVersionService itemVersionService;
	@Autowired
	private PolicyPromotionQuantityService policyPromotionQuantityService;
	@Autowired
	private PolicyPromotionMoneyService policyPromotionMoneyService;
	@Autowired
	private PolicyPromotionService policyPromotionService;
	@Autowired
	private PolicyPresentService policyPresentService;
	@Autowired
	private StoreItemSupplierService storeItemSupplierService;
	@Autowired
	private MobileAppV2Service mobileAppV2Service;
	@Autowired
	private PosOrderRpc posOrderRpc;

	
	@Override
	public List<TransferPolicyDTO>
	findTransferPolicyDTOs(PolicyPosItemQuery policyPosItemQuery) {
		return report2Service.findTransferPolicyDTOs(policyPosItemQuery);
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return report2Service.findPosReceiveDiffMoneySumDTOsByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByBranchCasher(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		List<PosReceiveDiffMoneySumDTO> list = report2Service.findPosReceiveDiffMoneySumDTOsByBranchCasher(systemBookCode, branchNums, dateFrom, dateTo);
		List<Object[]> posList = posOrderService.findBranchOperatorPayByMoneySummary(systemBookCode, branchNums, dateFrom, dateTo);

		int posSize = posList.size();
		Integer branchNum = null;
		String operator = null;
		String type = null;
		BigDecimal money = null;
		for (int i = 0; i < posSize; i++) {
			Object[] object = posList.get(i);
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

		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			dto.setTotalReceiveDiff(dto.getTotalReceiveMoney().subtract(dto.getTotalSaleMoney())
					.subtract(dto.getTotalCardDeposit()).subtract(dto.getTotalOtherMoney()).subtract(dto.getTotalRelatMoney()).subtract(dto.getTotalReplaceMoney()));

			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());

			}
		}
		return list;
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
	@Cacheable(value = "serviceCache", key="'findSaleAnalysisByBranchDayItems' + #p0.getKey()")
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchDayItems(SaleAnalysisQueryData policyPosItemQuery) {
		return report2Service.findSaleAnalysisByBranchDayItems(policyPosItemQuery);
	}

	@Override
	public List<BranchBizItemSummary> findProfitAnalysisByBranchDayItem(ProfitAnalysisQueryData profitAnalysisQueryData) {

		List<Object[]> objects = report2Service.findProfitAnalysisByBranchDayItem(profitAnalysisQueryData);
		List<BranchBizItemSummary> list = new ArrayList<BranchBizItemSummary>();
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0;i<objects.size();i++){
			Object[] object = objects.get(i);
			BranchBizItemSummary branchBizItemSummary = new BranchBizItemSummary();
			branchBizItemSummary.setBranchNum((Integer)object[0]);
			branchBizItemSummary.setBizday((String)object[1]);
			branchBizItemSummary.setItemNum((Integer)object[2]);
			if(object[3] instanceof BigDecimal){
				branchBizItemSummary.setGrossProfit(object[3] == null ? BigDecimal.ZERO : (BigDecimal)object[3]);
			}
			if(object[4] instanceof BigDecimal){
				branchBizItemSummary.setAmount(object[4] == null ? BigDecimal.ZERO : (BigDecimal)object[4]);
			}
			if(object[5] instanceof BigDecimal){
				branchBizItemSummary.setPaymentMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal)object[5]);
			}
			if(object[6] instanceof BigDecimal){
				branchBizItemSummary.setCost(object[6] == null ? BigDecimal.ZERO : (BigDecimal)object[6]);
			}
			list.add(branchBizItemSummary);

		}
		return list;
	}

	@Override
	public List<PosReceiveDiffMoneySumDTO> findPosReceiveDiffMoneySumDTOsByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		List<PosReceiveDiffMoneySumDTO> list = report2Service.findPosReceiveDiffMoneySumDTOsByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
		List<Object[]> posList = posOrderService.findBranchShiftTablePaymentSummary(systemBookCode, branchNums, dateFrom, dateTo, casher);
		int posSize = posList.size();
		Integer branchNum = null;
		String bizday = null;
		Integer biznum = null;
		String type = null;
		BigDecimal money = null;
		for (int i = 0; i < posSize; i++) {
			Object[] object = posList.get(i);
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


		List<ShiftTable> shiftTables = reportService.findShiftTables(systemBookCode, branchNums, dateFrom, dateTo, casher);
		BigDecimal bankMoney = null;
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			branchNum = shiftTable.getId().getBranchNum();
			bizday = shiftTable.getId().getShiftTableBizday();
			biznum = shiftTable.getId().getShiftTableNum();
			money = shiftTable.getShiftTableActualMoney() == null ? BigDecimal.ZERO : shiftTable.getShiftTableActualMoney();
			bankMoney = shiftTable.getShiftTableActualBankMoney() == null ? BigDecimal.ZERO : shiftTable.getShiftTableActualBankMoney();

			PosReceiveDiffMoneySumDTO dto = PosReceiveDiffMoneySumDTO.getByShift(list, branchNum, bizday, biznum);
			if (dto == null) {
				dto = new PosReceiveDiffMoneySumDTO();
				dto.setBranchNum(branchNum);
				dto.setShiftTableBizday(bizday);
				dto.setShiftTableNum(biznum);
				list.add(dto);
			}
			dto.setBranchInputMemo(shiftTable.getShiftTableMemo());
			dto.setCasher(shiftTable.getShiftTableUserName());
			dto.setTotalReceiveMoney(dto.getTotalReceiveMoney().add(money.add(bankMoney)));

			TypeAndTwoValuesDTO typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_CASH);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_CASH);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(money));

			typeAndTwoValuesDTO = TypeAndTwoValuesDTO.get(dto.getTypeAndTwoValuesDTOs(), AppConstants.PAYMENT_YINLIAN);
			if (typeAndTwoValuesDTO == null) {
				typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
				typeAndTwoValuesDTO.setType(AppConstants.PAYMENT_YINLIAN);
				dto.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
			}
			typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(bankMoney));
		}


		List<Branch> branchs = branchService.findInCache(systemBookCode);
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			dto.setTotalReceiveDiff(dto.getTotalReceiveMoney().subtract(dto.getTotalSaleMoney())
					.subtract(dto.getTotalCardDeposit()).subtract(dto.getTotalOtherMoney()).subtract(dto.getTotalRelatMoney()).subtract(dto.getTotalReplaceMoney()));

			Branch branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if(branch != null){
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
			}
		}
		return list;
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

            StoreQueryCondition storeQueryCondition = new StoreQueryCondition();
            storeQueryCondition.setSystemBookCode(customReportQuery.getSystemBookCode());
            storeQueryCondition.setBranchNums(customReportQuery.getBranchNums());
            storeQueryCondition.setDateStart(customReportQuery.getDateFrom());
            storeQueryCondition.setDateEnd(customReportQuery.getDateTo());
            storeQueryCondition.setSummaries(customReportQuery.getSummaries());

			List<Object[]> objects = posItemLogService.findBranchItemFlagSummary(storeQueryCondition);
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
	@Cacheable(value = "serviceCache")
	public List<RequestAnalysisDTO> findRequestAnalysisDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> itemCategoryCodes) {
		Integer branchNum = branchNums.get(0);

		PosItemQuery posItemQuery = new PosItemQuery();
		posItemQuery.setSystemBookCode(systemBookCode);
		posItemQuery.setBranchNum(branchNum);
		posItemQuery.setCategoryCodes(itemCategoryCodes);
		posItemQuery.setPaging(false);
		posItemQuery.setItemNums(itemNums);
        posItemQuery.setFilterItemTypes(Arrays.asList(AppConstants.C_ITEM_TYPE_KIT, AppConstants.C_ITEM_TYPE_ELEMENT));
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
			chainItemNums.remove(analysis.getItemNum());
		}
		int size = chainItemNums.size();
		for(int i = 0;i < size;i++){
			Integer itemNum = chainItemNums.get(i);
			innerItemNums.add(itemNum);

			RequestAnalysisDTO dto = reportUtil.getInstance();
			dto.setItemNum(itemNum);
			dto.setAbc("C");
			dto.setSaleQty(BigDecimal.ZERO);
			reportUtil.add(dto);
		}

		List<PosItem> posItems = null;
		List<StoreMatrix> storeMatrices = null;
		List<StoreItemSupplier> storeItemSuppliers = null;
		List<Object[]> objects = new ArrayList<Object[]>();
		if(innerItemNums.size() > 0) {
			List<Integer> itemNumList = new ArrayList<Integer>(innerItemNums);
			posItems = posItemService.findByItemNums(itemNumList);
			objects = inventoryService.findItemAmount(systemBookCode, branchNums, itemNumList, null);
			storeItemSuppliers = storeItemSupplierService.findDefaults(systemBookCode, Arrays.asList(AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM), itemNumList);
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
            if(item.getItemStatus() != null && item.getItemStatus() == AppConstants.ITEM_STATUS_SLEEP) {
                dtos.remove(i);
                continue;
            }
			StoreMatrix storeMatrix = AppUtil.getStoreMatrix(systemBookCode, branchNum, dto.getItemNum(), storeMatrices);
			dto.setItemCode(item.getItemCode());
			dto.setItemBarcode(item.getItemBarcode());
			dto.setItemName(item.getItemName());
			dto.setItemSpec(item.getItemSpec());
			if(storeMatrix != null && storeMatrix.getStoreMatrixSaleCeaseFlag() != null
					&& storeMatrix.getStoreMatrixSaleEnabled() != null && storeMatrix.getStoreMatrixSaleEnabled()) {
				dto.setItemSaleCease(storeMatrix.getStoreMatrixSaleCeaseFlag());
			} else {
				dto.setItemSaleCease(item.getItemSaleCeaseFlag());
			}
			if(storeMatrix != null && storeMatrix.getStoreMatrixStockCeaseFlag() != null
					&& (storeMatrix.getStoreMatrixStockEnabled() == null || storeMatrix.getStoreMatrixStockEnabled())) {
				dto.setItemStockCease(storeMatrix.getStoreMatrixStockCeaseFlag());
			} else {
				dto.setItemStockCease(item.getItemStockCeaseFlag());
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
            dto.setItemPurchaseScope(item.getItemPurchaseScope());

			if(dto.getItemType() == AppConstants.C_ITEM_TYPE_ASSEMBLE){
				List<PosItemKit> posItemKits = posItemService.findPosItemKits(dto.getItemNum());
				dto.setDetails(new ArrayList<RequestAnalysisDTO>());
				for(int j = 0,len = posItemKits.size();j < len;j++){
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
			if(storeItemSupplier != null) {
				Supplier supplier = AppUtil.getSupplier(storeItemSupplier.getId().getSupplierNum(), suppliers);
				if(supplier != null) {
					dto.setSupplierNum(supplier.getSupplierNum());
					dto.setSupplierCode(supplier.getSupplierCode());
					dto.setSupplierName(supplier.getSupplierName());
					dto.setSupplierMethod(supplier.getSupplierMethod());
					dto.setSupplierKind(supplier.getSupplierKind());
				}

			}

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
	
	
	@Override
	public List<OtherInfoSummaryDTO> findOtherInfos(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		if(branchNums == null || branchNums.isEmpty()){
			return new ArrayList<>();
		}
		List<Branch> branches = branchService.findInCache(systemBookCode);
		List<OtherInfoSummaryDTO> list = new ArrayList<>();
		for(Integer branchNum : branchNums) {
			List<NameAndValueDTO> dtos = mobileAppV2Service.findOtherInfos(systemBookCode, Collections.singletonList(branchNum), dateFrom, dateTo);
			OtherInfoSummaryDTO otherInfoSummaryDTO = new OtherInfoSummaryDTO();
			otherInfoSummaryDTO.setBranchNum(branchNum);
			otherInfoSummaryDTO.setBranchName(AppUtil.getBranch(branches, branchNum).getBranchName());

			for(NameAndValueDTO dto : dtos) {

				switch (dto.getName()) {
					case "删除":
						otherInfoSummaryDTO.setDeleteCount(dto.getIntValue());
						otherInfoSummaryDTO.setDeleteMoney(dto.getValue());
						break;
					case "退货":
						otherInfoSummaryDTO.setReturnCount(dto.getIntValue());
						otherInfoSummaryDTO.setReturnMoney(dto.getValue());
						break;
					case "整单取消":
						otherInfoSummaryDTO.setOrderCancelCount(dto.getIntValue());
						otherInfoSummaryDTO.setOrderCancelMoney(dto.getValue());
						break;
					case "挂单":
						otherInfoSummaryDTO.setHangOrderCount(dto.getIntValue());
						otherInfoSummaryDTO.setHangOrderMoney(dto.getValue());
						break;
					case "修改价格":
						otherInfoSummaryDTO.setChangePriceCount(dto.getIntValue());
						otherInfoSummaryDTO.setChangePriceMoney(dto.getValue());
						break;
					case "打开钱箱":
						otherInfoSummaryDTO.setCashBoxOpenCount(dto.getIntValue());
						break;
					case "反结账":
						otherInfoSummaryDTO.setAntiOrderCount(dto.getIntValue());
						otherInfoSummaryDTO.setAntiOrderMoney(dto.getValue());
						break;
					case "经理折扣":
						otherInfoSummaryDTO.setMgrDiscountCount(dto.getIntValue());
						otherInfoSummaryDTO.setMgrDiscountMoney(dto.getValue());
						break;
					case "赠送":
						otherInfoSummaryDTO.setPresentCount(dto.getIntValue());
						otherInfoSummaryDTO.setPresentMoney(dto.getValue());
						break;
				}
			}
			list.add(otherInfoSummaryDTO);

		}
		return list;
		
	}
	
	@Override
	public List<OtherInfoDTO> findOtherInfoDetails(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String infoType) {
		return mobileAppV2Service.findOtherInfoDetails(systemBookCode, branchNum, dateFrom, dateTo, infoType);
	}

	@Override
	public List<SaleInventoryDTO> findSaleInventorys(SaleInventoryQuery saleInventoryQuery) {

	    String systemBookCode = saleInventoryQuery.getSystemBookCode();
	    List<Integer> branchNums = saleInventoryQuery.getBranchNums();
	    List<Integer> itemNums = saleInventoryQuery.getItemNums();
	    Date dateFrom = saleInventoryQuery.getDateFrom();
	    Date dateTo = saleInventoryQuery.getDateTo();
	    Integer storehouseNum = saleInventoryQuery.getStorehouseNum();
	    List<String> categoryCodes = saleInventoryQuery.getCategoryCodes();
		Map<Integer, SaleInventoryDTO> map = new HashMap<>();


		ItemQueryDTO queryDTO = new ItemQueryDTO();
		queryDTO.setSystemBookCode(systemBookCode);
		queryDTO.setBranchNums(branchNums);
		queryDTO.setDateFrom(dateFrom);
		queryDTO.setDateTo(dateTo);
		queryDTO.setQueryKit(saleInventoryQuery.isQueryKit());
		queryDTO.setItemNums(itemNums);
		List<Object[]> objects = posOrderService.findItemSum(queryDTO);

		Integer itemNum;
		BigDecimal saleQty;
		List<Integer> queryItemNums = new ArrayList<>(100);

		for(Object[] object : objects){
			itemNum = (Integer) object[0];
			saleQty = object[1] == null?BigDecimal.ZERO:(BigDecimal) object[1];

			SaleInventoryDTO dto = map.get(itemNum);
			if(dto == null){
				dto = new SaleInventoryDTO();
				dto.setItemNum(itemNum);
				map.put(itemNum, dto);
				if(!queryItemNums.contains(dto.getItemNum())){
					queryItemNums.add(dto.getItemNum());

				}
			}
			dto.setSaleQty(dto.getSaleQty().add(saleQty));

		}
		if(saleInventoryQuery.isShowZeroInventory()){
			objects = inventoryService.findItemAmount(systemBookCode, branchNums, saleInventoryQuery.getItemNums(), storehouseNum);
			for(Object[] object : objects){
				itemNum = (Integer) object[0];

				SaleInventoryDTO dto = map.get(itemNum);
				if(dto == null){
					dto = new SaleInventoryDTO();
					dto.setItemNum(itemNum);
					map.put(itemNum, dto);

					if(!queryItemNums.contains(dto.getItemNum())){
						queryItemNums.add(dto.getItemNum());

					}

				}
				dto.setInventoryQty((BigDecimal) object[1]);

			}

		} else {
			objects = inventoryService.findItemAmount(systemBookCode, branchNums, queryItemNums, storehouseNum);
			for(Object[] object : objects){
				itemNum = (Integer) object[0];

				SaleInventoryDTO dto = map.get(itemNum);
				if(dto != null){
					dto.setInventoryQty((BigDecimal) object[1]);

				}
			}
		}


		if(map.isEmpty()){
			return Collections.emptyList();
		}

		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(queryItemNums);
		BigDecimal diffDay = BigDecimal.valueOf(DateUtil.getDaysBetween(dateFrom, dateTo) + 1);
		List<SaleInventoryDTO> list = new ArrayList<>(map.values());
		for(int  i = list.size() - 1;i >= 0;i--){
            SaleInventoryDTO dto = list.get(i);

			PosItem posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if(posItem == null){
				list.remove(i);
				continue;
			}
            if(posItem.getItemDelTag()){
                list.remove(i);
                continue;
            }
			if(categoryCodes != null && !categoryCodes.isEmpty()){
                if(!categoryCodes.contains(posItem.getItemCategoryCode())){
                    list.remove(i);
                    continue;
                }
            }
            if(saleInventoryQuery.isFilterEliminativeItems()){
			    if(posItem.getItemEliminativeFlag() != null && posItem.getItemEliminativeFlag()){
                    list.remove(i);
                    continue;
                }
            }
			dto.setItemName(posItem.getItemName());
			dto.setItemCode(posItem.getItemCode());
			dto.setItemBarcode(posItem.getItemBarcode());
			dto.setItemCategory(posItem.getItemCategory());
			dto.setItemCategoryCode(posItem.getItemCategoryCode());
			dto.setItemCostMode(posItem.getItemCostMode());
			dto.setSalePrice(posItem.getItemRegularPrice());
			dto.setSalePrice2(posItem.getItemLevel2Price());
            dto.setSaleAveQty(dto.getSaleQty().divide(diffDay, 4, BigDecimal.ROUND_HALF_UP));

		}
		return list;
	}

	@Override
	public List<SaleInventoryDetailDTO> findSaleInventoryDetails(SaleInventoryQuery saleInventoryQuery) {
		String systemBookCode = saleInventoryQuery.getSystemBookCode();
		List<Integer> branchNums = saleInventoryQuery.getBranchNums();
		Integer itemNum = saleInventoryQuery.getItemNums().get(0);
		Date dateFrom = saleInventoryQuery.getDateFrom();
		Date dateTo = saleInventoryQuery.getDateTo();

		List<Object[]> objects = inventoryService.findBranchItemSummary(systemBookCode, branchNums, Collections.singletonList(itemNum));
		Map<Integer, SaleInventoryDetailDTO> map = new HashMap<>();

		for(Object[] object : objects){
			SaleInventoryDetailDTO dto = new SaleInventoryDetailDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setInventoryQty((BigDecimal) object[2]);
			map.put(dto.getBranchNum(), dto);

		}
		ItemQueryDTO queryDTO = new ItemQueryDTO();
		queryDTO.setSystemBookCode(systemBookCode);
		queryDTO.setBranchNums(branchNums);
		queryDTO.setDateFrom(dateFrom);
		queryDTO.setDateTo(dateTo);
		queryDTO.setQueryKit(saleInventoryQuery.isQueryKit());
		queryDTO.setItemNums(Collections.singletonList(itemNum));
		objects = posOrderService.findBranchItemSum(queryDTO);

		BigDecimal qty;
		Integer branchNum;
		for(Object[] object : objects){
			branchNum = (Integer) object[0];
            qty = object[2] == null?BigDecimal.ZERO:(BigDecimal) object[2];

			SaleInventoryDetailDTO dto = map.get(branchNum);
			if(dto == null){
				dto = new SaleInventoryDetailDTO();
				dto.setBranchNum(branchNum);
				map.put(branchNum, dto);
			}
			dto.setSaleQty(dto.getSaleQty().add(qty));

		}

        objects = transferOutOrderService.findUnInBranchItemSummary(systemBookCode, Collections.singletonList(saleInventoryQuery.getBranchNum()), branchNums, dateFrom, dateTo, Collections.singletonList(itemNum));
        for(Object[] object : objects){
            branchNum = (Integer) object[0];
            qty = object[2] == null?BigDecimal.ZERO:(BigDecimal) object[2];

            SaleInventoryDetailDTO dto = map.get(branchNum);
            if(dto == null){
                dto = new SaleInventoryDetailDTO();
                dto.setBranchNum(branchNum);
                map.put(branchNum, dto);
            }
            dto.setOnloadQty(qty);

        }

        if(map.isEmpty()){
			return Collections.emptyList();
		}
		BigDecimal diffDay = BigDecimal.valueOf(DateUtil.getDaysBetween(dateFrom, dateTo) + 1);
		List<SaleInventoryDetailDTO> list = new ArrayList<>(map.values());
		List<StoreMatrix> storeMatrices = storeMatrixService.findByBranch(systemBookCode, branchNums, Collections.singletonList(itemNum));
		List<Branch> branches = branchService.findInCache(systemBookCode);
		PosItem posItem = posItemService.readWithoutDetails(itemNum);
		for(int  i = list.size() - 1;i >= 0;i--){
			SaleInventoryDetailDTO dto = list.get(i);
			Branch branch = Branch.get(branches, dto.getBranchNum());
			dto.setBranchName(branch.getBranchName());
			dto.setBranchCode(branch.getBranchCode());

			dto.setSalePrice(posItem.getItemRegularPrice());
			dto.setSalePrice2(posItem.getItemLevel2Price());
			if(branch.getBranchMatrixPriceActived() != null && branch.getBranchMatrixPriceActived()){
                StoreMatrix storeMatrix = StoreMatrix.get(dto.getBranchNum(), itemNum, storeMatrices);
                if(storeMatrix != null && storeMatrix.getStoreMatrixPriceEnabled()){
                    if(storeMatrix.getStoreMatrixRegularPrice() != null && storeMatrix.getStoreMatrixRegularPrice().compareTo(BigDecimal.ZERO) > 0){
                        dto.setSalePrice(storeMatrix.getStoreMatrixRegularPrice());
                    }
                    if(storeMatrix.getStoreMatrixLevel2Price() != null && storeMatrix.getStoreMatrixLevel2Price().compareTo(BigDecimal.ZERO) > 0){
                        dto.setSalePrice2(storeMatrix.getStoreMatrixLevel2Price());
                    }
                }
            }
			dto.setSaleAveQty(dto.getSaleQty().divide(diffDay, 4, BigDecimal.ROUND_HALF_UP));

		}
		return list;
	}

}