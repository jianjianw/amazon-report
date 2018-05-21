package com.nhsoft.module.report.rpc.impl;


import com.google.gson.Gson;
import com.nhsoft.module.report.api.dto.BranchFinishRateTopDTO;
import com.nhsoft.module.report.comparator.ComparatorBaseModelData;
import com.nhsoft.module.report.comparator.ComparatorGroupModelData;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.param.CardUserType;
import com.nhsoft.module.report.param.PosItemTypeParam;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.queryBuilder.RequestOrderQuery;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.CopyUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.RedisUtil;
import com.nhsoft.report.utils.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.secure.spi.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReportRpcImpl implements ReportRpc {
	private static final Logger logger = LoggerFactory.getLogger(ReportRpcImpl.class);

	@Autowired
	private ReceiveOrderService receiveOrderService;
	@Autowired
	private ReturnOrderService returnOrderService;
	@Autowired
	private PosOrderService posOrderService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private PosItemService posItemService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private TransferOutOrderService transferOutOrderService;
	@Autowired
	private TransferInOrderService transferInOrderService;
	@Autowired
	private WholesaleOrderService wholesaleOrderService;
	@Autowired
	private WholesaleReturnService wholesaleReturnService;
	@Autowired
	private AdjustmentOrderService adjustmentOrderService;
	@Autowired
	private StorehouseService storehouseService;
	@Autowired
	private BookResourceRpc bookResourceRpc;
	@Autowired
	private CardUserRpc cardUserRpc;
	@Autowired
	private CardDepositRpc cardDepositRpc;
	@Autowired
	private BranchTransferGoalsRpc branchTransferGoalsRpc;
	@Autowired
	private CardConsumeRpc cardConsumeRpc;
	@Autowired
	private BranchRpc branchRpc;
	@Autowired
	private InventoryRpc inventoryRpc;
	@Autowired
	private ReceiveOrderRpc receiveOrderRpc;
	@Autowired
	private RequestOrderRpc requestOrderRpc;
	@Autowired
	private TransferOutOrderRpc transferOutOrderRpc;
	@Autowired
	private BranchItemRecoredRpc branchItemRecoredRpc;
	@Autowired
	private PosItemLogRpc posItemLogRpc;
	@Autowired
	private MarketActionOpenIdService marketActionOpenIdService;
	@Autowired
	private WholesaleOrderRpc wholesaleOrderRpc;
	@Autowired
	private BookResourceService bookResourceService;
	@Autowired
	private PosItemFlagRpc posItemFlagRpc;
	@Autowired
	private PosItemRpc posItemRpc;
	@Autowired
	private ItemMatrixService itemMatrixService;
	@Autowired
	private PosItemLogService posItemLogService;

	@Autowired
	private CardLossRpc cardLossRpc;
	@Autowired
	private ReplaceCardRpc replaceCardRpc;
	@Autowired
	private RelatCardRpc relatCardRpc;
	@Autowired
	private ConsumePointRpc  consumePointRpc;
	@Autowired
	private CardUserRegisterRpc cardUserRegisterRpc;
	@Autowired
	private CardUserLogRpc cardUserLogRpc;
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	private RequestOrderService requestOrderService;
	@Autowired
	private StoreMatrixService storeMatrixService;
	@Autowired
	private StoreItemSupplierService storeItemSupplierService;




	@Override
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBranch(SaleAnalysisQueryData saleAnalysisQueryData) {
		String systemBookCode = saleAnalysisQueryData.getSystemBookCode();
		List<Integer> branchNums = saleAnalysisQueryData.getBranchNums();
		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		List<Integer> itemNums = saleAnalysisQueryData.getPosItemNums();
		List<String> categoryCodes = saleAnalysisQueryData.getPosItemTypeCodes();

		List<SalePurchaseProfitDTO> list = new ArrayList<SalePurchaseProfitDTO>();
		if (categoryCodes != null && categoryCodes.size() > 0) {
			StringBuffer categoryBuffer = new StringBuffer();
			for (int i = 0; i < categoryCodes.size(); i++) {
				String categoryCode = categoryCodes.get(i);
				if (i > 0) {
					categoryBuffer.append(",");
				}
				categoryBuffer.append(categoryCode);
			}
			List<Integer> categoryItemNums = posItemService.findItemNums(systemBookCode, categoryBuffer.toString());
			if (itemNums != null && itemNums.size() > 0) {
				itemNums.retainAll(categoryItemNums);
			} else {
				itemNums = categoryItemNums;
			}

		}

		List<Object[]> objects = receiveOrderService.findBranchSummary(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums);
		Object[] object = null;
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = new SalePurchaseProfitDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setSaleCost(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			list.add(dto);

		}

		objects = posOrderService.findBranchDetailSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = SalePurchaseProfitDTO.getByBranch(list, (Integer) object[0]);
			if (dto == null) {
				dto = new SalePurchaseProfitDTO();
				dto.setBranchNum((Integer) object[0]);
				list.add(dto);
			}
			dto.setSaleMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);

		}
		int size = list.size();
		if (size == 0) {
			return list;
		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Branch branch;
		SalePurchaseProfitDTO dto;
		for (int i = 0; i < size; i++) {
			dto = list.get(i);
			branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if (branch != null) {
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
			}
			dto.setProfit(dto.getSaleMoney().subtract(dto.getSaleCost()));
			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return list;
	}

	@Override
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByItem(SaleAnalysisQueryData saleAnalysisQueryData) {
		String systemBookCode = saleAnalysisQueryData.getSystemBookCode();
		List<Integer> branchNums = saleAnalysisQueryData.getBranchNums();
		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		List<Integer> itemNums = saleAnalysisQueryData.getPosItemNums();
		List<String> categoryCodes = saleAnalysisQueryData.getPosItemTypeCodes();

		List<SalePurchaseProfitDTO> list = new ArrayList<SalePurchaseProfitDTO>();
		List<Object[]> objects = receiveOrderService.findBranchItemSummary(systemBookCode, branchNums, dateFrom,
				dateTo, itemNums);
		Object[] object = null;
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = new SalePurchaseProfitDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setItemNum((Integer) object[1]);
			dto.setSaleCost(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			list.add(dto);

		}

		objects = posOrderService.findBranchItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = SalePurchaseProfitDTO.getByBranchItem(list, (Integer) object[0],
					(Integer) object[1]);
			if (dto == null) {
				dto = new SalePurchaseProfitDTO();
				dto.setBranchNum((Integer) object[0]);
				dto.setItemNum((Integer) object[1]);
				list.add(dto);
			}
			dto.setSaleAmount(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			dto.setSaleMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);

		}
		int size = list.size();
		if (size == 0) {
			return list;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Branch branch;
		PosItem posItem;
		SalePurchaseProfitDTO dto;
		for (int i = size - 1; i >= 0; i--) {
			dto = list.get(i);
			posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if (posItem != null) {
				if (categoryCodes != null && categoryCodes.size() > 0) {
					if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
						list.remove(i);
					}
				}
				dto.setItemCode(posItem.getItemCode());
				dto.setItemName(posItem.getItemName());
				dto.setUnit(posItem.getItemUnit());
				dto.setSpec(posItem.getItemSpec());
				dto.setCategoryCode(posItem.getItemCategoryCode());
				dto.setCategoryName(posItem.getItemCategory());

			}

			branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if (branch != null) {
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
			}

			dto.setProfit(dto.getSaleMoney().subtract(dto.getSaleCost()));
			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return list;
	}

	@Override
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByCategory(SaleAnalysisQueryData saleAnalysisQueryData) {
		String systemBookCode = saleAnalysisQueryData.getSystemBookCode();
		List<Integer> branchNums = saleAnalysisQueryData.getBranchNums();
		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		List<Integer> itemNums = saleAnalysisQueryData.getPosItemNums();
		List<String> categoryCodes = saleAnalysisQueryData.getPosItemTypeCodes();

		List<SalePurchaseProfitDTO> list = new ArrayList<SalePurchaseProfitDTO>();
		List<Object[]> objects = receiveOrderService.findItemSummary(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums);
		Object[] object = null;
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = new SalePurchaseProfitDTO();
			dto.setItemNum((Integer) object[0]);
			dto.setSaleCost(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			list.add(dto);

		}

		objects = posOrderService.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);
			if (object[0] == null) {
				continue;
			}
			SalePurchaseProfitDTO dto = SalePurchaseProfitDTO.getByItem(list, (Integer) object[0]);
			if (dto == null) {
				dto = new SalePurchaseProfitDTO();
				dto.setItemNum((Integer) object[0]);
				list.add(dto);
			}
			dto.setSaleAmount(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			dto.setSaleMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);

		}
		int size = list.size();
		if (size == 0) {
			return list;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		PosItem posItem;
		SalePurchaseProfitDTO dto;
		SalePurchaseProfitDTO categoryDto;
		List<SalePurchaseProfitDTO> categoryList = new ArrayList<SalePurchaseProfitDTO>();
		String categoryCode = "";
		String categoryName = "未知类别";
		for (int i = 0; i < size; i++) {
			dto = list.get(i);
			posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if (posItem != null) {
				if (categoryCodes != null && categoryCodes.size() > 0) {
					if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
						continue;
					}
				}

				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			} else {
				categoryCode = "";
				categoryName = "未知类别";
			}
			categoryDto = SalePurchaseProfitDTO.getByCategory(categoryList, categoryCode);
			if (categoryDto == null) {
				categoryDto = new SalePurchaseProfitDTO();
				categoryDto.setCategoryCode(categoryCode);
				categoryDto.setCategoryName(categoryName);
				categoryList.add(categoryDto);
			}
			categoryDto.setSaleAmount(categoryDto.getSaleAmount().add(dto.getSaleAmount()));
			categoryDto.setSaleMoney(categoryDto.getSaleMoney().add(dto.getSaleMoney()));
			categoryDto.setSaleCost(categoryDto.getSaleCost().add(dto.getSaleCost()));

		}
		for (int i = 0,len = categoryList.size(); i < len; i++) {
			dto = categoryList.get(i);

			dto.setProfit(dto.getSaleMoney().subtract(dto.getSaleCost()));
			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return categoryList;
	}

	@Override
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBranchCategory(
			SaleAnalysisQueryData saleAnalysisQueryData) {
		String systemBookCode = saleAnalysisQueryData.getSystemBookCode();
		List<Integer> branchNums = saleAnalysisQueryData.getBranchNums();
		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();
		List<Integer> itemNums = saleAnalysisQueryData.getPosItemNums();
		List<String> categoryCodes = saleAnalysisQueryData.getPosItemTypeCodes();

		List<SalePurchaseProfitDTO> list = new ArrayList<SalePurchaseProfitDTO>();
		List<Object[]> objects = receiveOrderService.findBranchItemSummary(systemBookCode, branchNums, dateFrom,
				dateTo, itemNums);
		Object[] object = null;
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = new SalePurchaseProfitDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setItemNum((Integer) object[1]);
			dto.setSaleCost(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			list.add(dto);

		}

		objects = posOrderService.findBranchItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, false);
		for (int i = 0,len = objects.size(); i < objects.size(); i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = SalePurchaseProfitDTO.getByBranchItem(list, (Integer) object[0],
					(Integer) object[1]);
			if (dto == null) {
				dto = new SalePurchaseProfitDTO();
				dto.setBranchNum((Integer) object[0]);
				dto.setItemNum((Integer) object[1]);
				list.add(dto);
			}
			dto.setSaleAmount(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			dto.setSaleMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);

		}
		int size = list.size();
		if (size == 0) {
			return list;
		}
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		PosItem posItem;

		SalePurchaseProfitDTO dto;
		SalePurchaseProfitDTO categoryDto;
		List<SalePurchaseProfitDTO> categoryList = new ArrayList<SalePurchaseProfitDTO>();
		String categoryCode = "";
		String categoryName = "未知类别";
		for (int i = 0; i < size; i++) {
			dto = list.get(i);
			posItem = AppUtil.getPosItem(dto.getItemNum(), posItems);
			if (posItem != null) {
				if (categoryCodes != null && categoryCodes.size() > 0) {
					if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
						continue;
					}
				}
				categoryCode = posItem.getItemCategoryCode();
				categoryName = posItem.getItemCategory();
			} else {
				categoryCode = "";
				categoryName = "未知类别";
			}
			categoryDto = SalePurchaseProfitDTO.getByBranchCategory(categoryList, dto.getBranchNum(), categoryCode);
			if (categoryDto == null) {
				categoryDto = new SalePurchaseProfitDTO();
				categoryDto.setBranchNum(dto.getBranchNum());
				categoryDto.setCategoryCode(categoryCode);
				categoryDto.setCategoryName(categoryName);
				categoryList.add(categoryDto);
			}
			categoryDto.setSaleAmount(categoryDto.getSaleAmount().add(dto.getSaleAmount()));
			categoryDto.setSaleMoney(categoryDto.getSaleMoney().add(dto.getSaleMoney()));
			categoryDto.setSaleCost(categoryDto.getSaleCost().add(dto.getSaleCost()));

		}
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		Branch branch;
		for (int i = 0,len = categoryList.size(); i < len; i++) {
			dto = categoryList.get(i);

			branch = AppUtil.getBranch(branchs, dto.getBranchNum());
			if (branch != null) {
				dto.setBranchCode(branch.getBranchCode());
				dto.setBranchName(branch.getBranchName());
			}
			dto.setProfit(dto.getSaleMoney().subtract(dto.getSaleCost()));
			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return categoryList;
	}

	@Override
	public List<OrderDetailReportDTO> findbizmonthItemSummary(OrderQueryDTO orderQueryDTO) {
		String systemBookCode = orderQueryDTO.getSystemBookCode();
		List<Integer> branchNums = orderQueryDTO.getBranchNums();
		Date dateFrom = orderQueryDTO.getDateFrom();
		Date dateTo = orderQueryDTO.getDateTo();
		List<Integer> itemNums = orderQueryDTO.getItemNums();

		List<Object[]> objects = posOrderService.findBizmonthItemSummary(systemBookCode, branchNums, dateFrom, dateTo,
				itemNums);
		int size = objects.size();
		OrderDetailReportDTO dto;
		Object[] object;
		List<OrderDetailReportDTO> list = new ArrayList<OrderDetailReportDTO>(size);
		for (int i = 0; i < size; i++) {
			object = objects.get(i);
			dto = new OrderDetailReportDTO();
			dto.setBizMonth((String) object[0]);
			dto.setItemNum((Integer) object[1]);
			dto.setAmount(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			dto.setPaymentMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			list.add(dto);
		}
		return list;
	}

	@Override
	public List<SalePurchaseProfitDTO> findSalePurchaseProfitDTOsByBiz(SaleAnalysisQueryData saleAnalysisQueryData) {
		String systemBookCode = saleAnalysisQueryData.getSystemBookCode();
		List<Integer> branchNums = saleAnalysisQueryData.getBranchNums();
		Date dateFrom = saleAnalysisQueryData.getDtFrom();
		Date dateTo = saleAnalysisQueryData.getDtTo();

		List<SalePurchaseProfitDTO> list = new ArrayList<SalePurchaseProfitDTO>();

		List<Object[]> objects = receiveOrderService.findBizSummary(systemBookCode, branchNums, dateFrom, dateTo);
		Object[] object = null;
		for (int i = 0,len = objects.size(); i < len; i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = new SalePurchaseProfitDTO();
			dto.setBizday((String) object[0]);
			dto.setReceiveOrderCount(object[1] == null ? 0 : (Integer) object[1]);
			dto.setSaleCost(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			list.add(dto);

		}

		objects = reportService.findPosOrderMoneyByBizDay(systemBookCode, branchNums, dateFrom, dateTo,
				AppConstants.BUSINESS_DATE_MONTH);
		for (int i = 0; i < objects.size(); i++) {
			object = objects.get(i);

			SalePurchaseProfitDTO dto = SalePurchaseProfitDTO.getByBizday(list, (String) object[0]);
			if (dto == null) {
				dto = new SalePurchaseProfitDTO();
				dto.setBizday((String) object[0]);
				list.add(dto);
			}
			dto.setSaleMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
			dto.setSaleOrderCount(object[2] == null ? 0 : (Integer) object[2]);
		}
		int size = list.size();
		if (size == 0) {
			return list;
		}

		SalePurchaseProfitDTO dto;
		for (int i = 0; i < size; i++) {
			dto = list.get(i);
			dto.setProfit(dto.getSaleMoney().subtract(dto.getSaleCost()));
			if (dto.getSaleMoney().compareTo(BigDecimal.ZERO) > 0) {
				dto.setProfitRate(dto.getProfit().divide(dto.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP));
			}
		}
		return list;
	}

	@Override
	public List<MultipleProfitReportDTO> findMultipleProfitReportDTOs(String systemBookCode, Date dateFrom, Date
				dateTo, List<String> categoryCodes, List<Integer> itemNums) {

		List<Branch> branchs = branchService.findActivedRdc(systemBookCode);
		int size = branchs.size();
		List<Integer> branchNums = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			branchNums.add(branchs.get(i).getId().getBranchNum());
		}

		Map<Integer, MultipleProfitReportDTO> map = new HashMap<Integer, MultipleProfitReportDTO>();
		List<Integer> existsItemNums = new ArrayList<Integer>();
		MultipleProfitReportDTO multipleProfitReportDTO;
		List<Object[]> wholesaleOrders = wholesaleOrderService.findItemSum(systemBookCode, null, null, dateFrom,
				dateTo, itemNums, null, null);
		// 批发销售
		Object[] objects;
		for (int i = 0,len = wholesaleOrders.size(); i < len ; i++) {
			multipleProfitReportDTO = new MultipleProfitReportDTO();
			objects = wholesaleOrders.get(i);
			multipleProfitReportDTO.setItemNum((Integer) objects[0]);
			multipleProfitReportDTO.setWholesaleQty((objects[1] == null ? BigDecimal.ZERO : (BigDecimal) objects[1])
					.add(objects[8] == null ? BigDecimal.ZERO : (BigDecimal) objects[8]));

			multipleProfitReportDTO.setWholesaleCost(objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]);
			multipleProfitReportDTO.setWholesaleMoney(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2]);
			map.put(multipleProfitReportDTO.getItemNum(), multipleProfitReportDTO);
			existsItemNums.add(multipleProfitReportDTO.getItemNum());
		}
		// 批发退货
		List<Object[]> wholesaleReturns = wholesaleReturnService.findItemSum(systemBookCode, null, null, dateFrom,
				dateTo, itemNums, null);
		for (int i = 0,len = wholesaleReturns.size(); i < len; i++) {
			objects = wholesaleReturns.get(i);
			multipleProfitReportDTO = map.get(objects[0]);
			if (multipleProfitReportDTO == null) {
				multipleProfitReportDTO = new MultipleProfitReportDTO();
				multipleProfitReportDTO.setItemNum((Integer) objects[0]);
				existsItemNums.add(multipleProfitReportDTO.getItemNum());
				map.put(multipleProfitReportDTO.getItemNum(), multipleProfitReportDTO);
			}

			multipleProfitReportDTO.setWholesaleQty(multipleProfitReportDTO.getWholesaleQty()
					.subtract(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2])
					.subtract(objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]));
			multipleProfitReportDTO.setWholesaleCost(multipleProfitReportDTO.getWholesaleCost().subtract(
					objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5]));
			multipleProfitReportDTO.setWholesaleMoney(multipleProfitReportDTO.getWholesaleMoney().subtract(
					objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4]));
		}

		List<Object[]> transferOutOrders = transferOutOrderService.findProfitGroupByItem(systemBookCode, branchNums,
				null, dateFrom, dateTo, null, itemNums);
		// 调出
		for (int i = 0,len = transferOutOrders.size(); i < len; i++) {
			objects = transferOutOrders.get(i);
			multipleProfitReportDTO = map.get(objects[0]);
			if (multipleProfitReportDTO == null) {
				multipleProfitReportDTO = new MultipleProfitReportDTO();
				multipleProfitReportDTO.setItemNum((Integer) objects[0]);
				existsItemNums.add(multipleProfitReportDTO.getItemNum());
				map.put(multipleProfitReportDTO.getItemNum(), multipleProfitReportDTO);

			}
			multipleProfitReportDTO.setTranferQty((objects[2] == null ? BigDecimal.ZERO : (BigDecimal)objects[2]).add(objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5]));
			multipleProfitReportDTO.setTranferCost(objects[4] == null ? BigDecimal.ZERO : (BigDecimal)objects[4]);
			multipleProfitReportDTO.setTranferMoney(objects[3] == null ? BigDecimal.ZERO : (BigDecimal)objects[3]);
		}
		// 调入
		List<Object[]> transferInOrders = transferInOrderService.findProfitGroupByItem(systemBookCode, branchNums,
				null, dateFrom, dateTo, null, itemNums);
		
		for (int i = 0,len = transferInOrders.size(); i < len; i++) {
			objects = transferInOrders.get(i);
			multipleProfitReportDTO = map.get(objects[0]);
			if (multipleProfitReportDTO == null) {
				multipleProfitReportDTO = new MultipleProfitReportDTO();
				multipleProfitReportDTO.setItemNum((Integer) objects[0]);
				existsItemNums.add(multipleProfitReportDTO.getItemNum());
				map.put(multipleProfitReportDTO.getItemNum(), multipleProfitReportDTO);
			}
			multipleProfitReportDTO.setTranferQty(multipleProfitReportDTO.getTranferQty()
					.subtract(objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2])
					.subtract(objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5]));
			multipleProfitReportDTO.setTranferCost(multipleProfitReportDTO.getTranferCost().subtract(
					objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4]));
			multipleProfitReportDTO.setTranferMoney(multipleProfitReportDTO.getTranferMoney().subtract(
					objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3]));
		}
		
		List<Object[]> posOrders = posOrderService.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, true);
		// 零售
		BigDecimal hundred = BigDecimal.valueOf(100);
		for (int i = 0,len = posOrders.size(); i < len; i++) {
			objects = posOrders.get(i);
			multipleProfitReportDTO = map.get(objects[0]);
			if (multipleProfitReportDTO == null) {
				multipleProfitReportDTO = new MultipleProfitReportDTO();
				multipleProfitReportDTO.setItemNum((Integer) objects[0]);
				existsItemNums.add(multipleProfitReportDTO.getItemNum());
				map.put(multipleProfitReportDTO.getItemNum(), multipleProfitReportDTO);
			}
			multipleProfitReportDTO.setPosSaleQty(objects[1] == null ? BigDecimal.ZERO : (BigDecimal)objects[1]);
			multipleProfitReportDTO.setPosSaleMoney(objects[2] == null ? BigDecimal.ZERO : (BigDecimal)objects[2]);
			multipleProfitReportDTO.setPosSaleProfit(objects[3] == null ? BigDecimal.ZERO : (BigDecimal)objects[3]);
			multipleProfitReportDTO.setPosSaleCost(multipleProfitReportDTO.getPosSaleMoney().subtract(multipleProfitReportDTO.getPosSaleProfit()));
			if (multipleProfitReportDTO.getPosSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setPosSaleProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setPosSaleProfitRate(multipleProfitReportDTO.getPosSaleProfit().divide(
						multipleProfitReportDTO.getPosSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}
		}
		List<MultipleProfitReportDTO> multipleProfitReportDTOs = new ArrayList<MultipleProfitReportDTO>();
		if(existsItemNums.isEmpty()){
			return multipleProfitReportDTOs;
		}
		List<PosItem> posItems = posItemService.findByItemNumsWithoutDetails(existsItemNums);
		PosItem posItem;
		for (int i = 0,len = posItems.size(); i < len; i++) {
			posItem = posItems.get(i);
			
			if(categoryCodes != null && categoryCodes.size() > 0){
				if(!categoryCodes.contains(posItem.getItemCategoryCode())){
					continue;
				}
			}
			
			multipleProfitReportDTO = map.get(posItem.getItemNum());
			multipleProfitReportDTO.setCategoryCode(posItem.getItemCategoryCode());
			multipleProfitReportDTO.setCategoryName(posItem.getItemCategory());
			multipleProfitReportDTO.setItemNum(posItem.getItemNum());
			multipleProfitReportDTO.setItemCode(posItem.getItemCode());
			multipleProfitReportDTO.setItemName(posItem.getItemName());
			multipleProfitReportDTO.setItemSpec(posItem.getItemSpec());
			multipleProfitReportDTO.setItemUnit(posItem.getItemUnit());

			multipleProfitReportDTO.setWholesaleProfit(multipleProfitReportDTO.getWholesaleMoney().subtract(multipleProfitReportDTO.getWholesaleCost()));
			if (multipleProfitReportDTO.getWholesaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setWholesaleProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setWholesaleProfitRate(multipleProfitReportDTO.getWholesaleProfit()
						.divide(multipleProfitReportDTO.getWholesaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}

			multipleProfitReportDTO.setTranferProfit(multipleProfitReportDTO.getTranferMoney().subtract(multipleProfitReportDTO.getTranferCost()));
			if (multipleProfitReportDTO.getTranferMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setTranferProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setTranferProfitRate(multipleProfitReportDTO.getTranferProfit().divide(
						multipleProfitReportDTO.getTranferMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}
			multipleProfitReportDTO.setTotalMoney(multipleProfitReportDTO.getPosSaleMoney()
					.add(multipleProfitReportDTO.getTranferMoney()).add(multipleProfitReportDTO.getWholesaleMoney()));
			multipleProfitReportDTO.setTotalProfit(multipleProfitReportDTO.getPosSaleProfit().add(
					multipleProfitReportDTO.getTranferProfit().add(multipleProfitReportDTO.getWholesaleProfit())));
			if (multipleProfitReportDTO.getTotalMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setTotalProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setTotalProfitRate(multipleProfitReportDTO.getTotalProfit().divide(
						multipleProfitReportDTO.getTotalMoney(), 4,BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}
			multipleProfitReportDTOs.add(multipleProfitReportDTO);
		}

		return multipleProfitReportDTOs;

	}

	@Override
	public List<SupplierComplexReportDTO> findSupplierSumDatas(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		List<Integer> branchNums = supplierSaleQuery.getBranchNums();

		List<Supplier> suppliers = supplierService.find(systemBookCode, branchNums, null, null, true, null);
		List<Storehouse> storehouses = storehouseService.findByBranchs(systemBookCode, branchNums);
		List<Integer> storehouseNums = new ArrayList<Integer>();
		for (Storehouse storehouse : storehouses) {
			storehouseNums.add(storehouse.getStorehouseNum());
		}
		Map<Integer, SupplierComplexReportDTO> map = new HashMap<Integer, SupplierComplexReportDTO>();
		List<Object[]> objects = receiveOrderService.findSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer supplierNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDTO data = map.get(supplierNum);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setInQty(amount);
				data.setInMoney(saleMoney);
				map.put(supplierNum, data);
			} else {
				data.setInQty(data.getInQty().add(amount));
				data.setInMoney(data.getInMoney().add(saleMoney));
			}
		}
		objects = returnOrderService.findSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer supplierNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			amount = amount.negate();
			saleMoney = saleMoney.negate();
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDTO data = map.get(supplierNum);
			if (data == null) {/////
				data = new SupplierComplexReportDTO();
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setInQty(amount);
				data.setInMoney(saleMoney);
				map.put(supplierNum, data);
			} else {
				data.setInQty(data.getInQty().add(amount));
				data.setInMoney(data.getInMoney().add(saleMoney));
			}
		}
		objects = wholesaleOrderService.findSupplierSum(systemBookCode, branchNums, supplierSaleQuery.getDateFrom(),
				supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(), supplierSaleQuery.getItemNums(),
				null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer supplierNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal costMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDTO data = map.get(supplierNum);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount);
				data.setSaleMoney(saleMoney);
				data.setSaleCost(costMoney);
				data.setSaleProfit(saleMoney.subtract(costMoney));
				map.put(supplierNum, data);
			} else {
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(saleMoney));
				data.setSaleCost(data.getSaleCost().add(costMoney));
				data.setSaleProfit(data.getSaleProfit().add(saleMoney.subtract(costMoney)));
			}
		}

		objects = wholesaleReturnService.findSupplierSum(systemBookCode, branchNums, null,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getItemNums(),
				supplierSaleQuery.getCategoryCodes(), null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer supplierNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal costMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDTO data = map.get(supplierNum);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount.negate());
				data.setSaleMoney(saleMoney.negate());
				data.setSaleCost(costMoney.negate());
				data.setSaleProfit(saleMoney.subtract(costMoney).negate());
				map.put(supplierNum, data);
			} else {
				data.setSaleQty(data.getSaleQty().subtract(amount));
				data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
				data.setSaleCost(data.getSaleCost().subtract(costMoney));
				data.setSaleProfit(data.getSaleProfit().subtract(saleMoney.subtract(costMoney)));
			}
		}
		objects = posOrderService.findSupplierSum(systemBookCode, supplierSaleQuery.getBranchNums(),
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums(), true);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer supplierNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal saleMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal profit = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDTO data = map.get(supplierNum);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount);
				data.setSaleMoney(saleMoney);
				data.setSaleCost(saleMoney.subtract(profit));
				data.setSaleProfit(profit);
				map.put(supplierNum, data);
			} else {
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(saleMoney));
				data.setSaleCost(data.getSaleCost().add(saleMoney.subtract(profit)));
				data.setSaleProfit(data.getSaleProfit().add(profit));
			}
		}

		List<SupplierComplexReportDTO> list = new ArrayList<SupplierComplexReportDTO>(map.values());
		int size = list.size();
		// 算毛利率
		for (int i = size - 1; i >= 0; i--) {
			SupplierComplexReportDTO data = list.get(i);
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
	public List<SupplierComplexReportDTO> findSupplierSaleSumDatas(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		List<Integer> branchNums = supplierSaleQuery.getBranchNums();

		List<Supplier> suppliers = supplierService.find(systemBookCode, branchNums, null, null, true, null);
		List<PosItem> posItems = posItemService.find(systemBookCode, supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums(), null);
		List<Storehouse> storehouses = storehouseService.findByBranchs(systemBookCode, branchNums);
		List<Integer> storehouseNums = new ArrayList<Integer>();
		for (Storehouse storehouse : storehouses) {
			storehouseNums.add(storehouse.getStorehouseNum());
		}
		Map<Integer, SupplierComplexReportDTO> baseMap = new HashMap<Integer, SupplierComplexReportDTO>();
		Map<String, SupplierComplexReportDTO> map = new HashMap<String, SupplierComplexReportDTO>();
		for (int i = 0,len = posItems.size(); i < len; i++) {
			PosItem posItem = posItems.get(i);
			SupplierComplexReportDTO data = new SupplierComplexReportDTO();
			BeanUtils.copyProperties(posItem, data);
			data.setCategoryCode(posItem.getItemCategoryCode());
			data.setCategoryName(posItem.getItemCategory());
			baseMap.put(data.getItemNum(), data);
		}
		List<Object[]> objects = inventoryService.findItemAmount(systemBookCode, branchNums, null, null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			BigDecimal amount = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			SupplierComplexReportDTO data = baseMap.get(itemNum);
			if (data != null) {
				data.setStockQty(amount);
			}
		}
		objects = receiveOrderService.findItemSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setInQty(amount);
				data.setInMoney(saleMoney);
				map.put(key, data);
			} else {
				data.setInQty(data.getInQty().add(amount));
				data.setInMoney(data.getInMoney().add(saleMoney));
			}
		}
		objects = returnOrderService.findItemSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setInQty(amount.negate());
				data.setInMoney(saleMoney.negate());
				map.put(key, data);
			} else {
				data.setInQty(data.getInQty().subtract(amount));
				data.setInMoney(data.getInMoney().subtract(saleMoney));
			}
		}
		objects = wholesaleOrderService.findItemSupplierSum(systemBookCode, branchNums, supplierSaleQuery.getDateFrom(),
				supplierSaleQuery.getDateTo(), supplierSaleQuery.getItemNums(), null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal costMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount);
				data.setSaleMoney(saleMoney);
				data.setSaleCost(costMoney);
				data.setSaleProfit(saleMoney.subtract(costMoney));
				map.put(key, data);
			} else {
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(saleMoney));
				data.setSaleCost(data.getSaleCost().add(costMoney));
				data.setSaleProfit(data.getSaleProfit().add(saleMoney.subtract(costMoney)));
			}
		}

		objects = wholesaleReturnService.findItemSupplierSum(systemBookCode, branchNums, null,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getItemNums(),
				supplierSaleQuery.getCategoryCodes(), null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal saleMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal costMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount.negate());
				data.setSaleMoney(saleMoney.negate());
				data.setSaleCost(costMoney.negate());
				data.setSaleProfit(saleMoney.subtract(costMoney).negate());
				map.put(key, data);
			} else {
				data.setSaleQty(data.getSaleQty().subtract(amount));
				data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
				data.setSaleCost(data.getSaleCost().subtract(costMoney));
				data.setSaleProfit(data.getSaleProfit().subtract(saleMoney.subtract(costMoney)));
			}
		}
		objects = posOrderService.findItemSupplierMatrixSum(systemBookCode, supplierSaleQuery.getBranchNums(),
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), true);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer supplierNum = (Integer) object[1];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				data.setSaleQty(amount);
				data.setSaleMoney(saleMoney);
				data.setSaleCost(saleMoney.subtract(profit));
				data.setSaleProfit(profit);
				map.put(key, data);
			} else {
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(saleMoney));
				data.setSaleCost(data.getSaleCost().add(saleMoney.subtract(profit)));
				data.setSaleProfit(data.getSaleProfit().add(profit));
			}
		}

		List<SupplierComplexReportDTO> list = new ArrayList<SupplierComplexReportDTO>(map.values());
		// 算毛利率和周转率
		int size = list.size();
		for (int i = size - 1; i >= 0; i--) {
			SupplierComplexReportDTO data = list.get(i);
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				data.setSaleProfitRate(BigDecimal.ZERO);
			} else {
				data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
			if (data.getSaleQty().compareTo(BigDecimal.ZERO) == 0) {
				data.setRoundRate(BigDecimal.ZERO);
			} else {
				data.setRoundRate(data
						.getStockQty()
						.divide(data.getSaleQty(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(
								new BigDecimal(DateUtil.diffDayV2(
										DateUtil.getMinOfDate(supplierSaleQuery.getDateFrom()),
										DateUtil.getMaxOfDate(supplierSaleQuery.getDateTo())))));
			}
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSupplierSaleGroupByBranchDatas'+ #p0.getKey()")
	public List<SupplierComplexReportDTO> findSupplierSaleGroupByBranchDatas(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		List<Integer> branchNums = supplierSaleQuery.getBranchNums();

		List<Supplier> suppliers = supplierService.find(systemBookCode, branchNums, null, null, true, null);
		List<PosItem> posItems = posItemService.find(systemBookCode, supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums(), null);
		List<Branch> branches = branchService.findInCache(systemBookCode);
		List<Storehouse> storehouses = storehouseService.findByBranchs(systemBookCode, branchNums);
		List<Integer> storehouseNums = new ArrayList<Integer>();
		for (Storehouse storehouse : storehouses) {
			storehouseNums.add(storehouse.getStorehouseNum());
		}
		Map<Integer, SupplierComplexReportDTO> baseMap = new HashMap<Integer, SupplierComplexReportDTO>();
		Map<String, SupplierComplexReportDTO> map = new HashMap<String, SupplierComplexReportDTO>();
		for (int i = 0,len = posItems.size(); i < len; i++) {
			PosItem posItem = posItems.get(i);
			SupplierComplexReportDTO data = new SupplierComplexReportDTO();
			BeanUtils.copyProperties(posItem, data);
			data.setCategoryCode(posItem.getItemCategoryCode());
			data.setCategoryName(posItem.getItemCategory());
			baseMap.put(data.getItemNum(), data);
		}
		List<Object[]> objects = receiveOrderService.findBranchItemSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		List<Integer> itemNums = new ArrayList<>(100);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer supplierNum = (Integer) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append("_").append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				Branch branchInfo = AppUtil.getBranch(branches, branchNum);
				if (branchInfo == null) {
					continue;
				}
				data.setBranchCode(branchInfo.getBranchCode());
				data.setBranchName(branchInfo.getBranchName());
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				map.put(key, data);
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
			}
			data.setInQty(data.getInQty().add(amount));
			data.setInMoney(data.getInMoney().add(saleMoney));
		}
		objects = returnOrderService.findBranchItemSupplierAmountAndMoney(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer supplierNum = (Integer) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append("_").append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				Branch branchInfo = AppUtil.getBranch(branches, branchNum);
				if (branchInfo == null) {
					continue;
				}
				data.setBranchCode(branchInfo.getBranchCode());
				data.setBranchName(branchInfo.getBranchName());
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				map.put(key, data);
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
			}
			data.setInQty(data.getInQty().subtract(amount));
			data.setInMoney(data.getInMoney().subtract(saleMoney));
		}
		objects = wholesaleOrderService.findBranchItemSupplierSum(systemBookCode, branchNums, supplierSaleQuery.getDateFrom(),
				supplierSaleQuery.getDateTo(), null, null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer supplierNum = (Integer) object[2];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal saleMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal costMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal profit = saleMoney.subtract(costMoney);
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append("_").append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				Branch branchInfo = AppUtil.getBranch(branches, branchNum);
				if (branchInfo == null) {
					continue;
				}
				data.setBranchCode(branchInfo.getBranchCode());
				data.setBranchName(branchInfo.getBranchName());
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				map.put(key, data);
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
			}
			data.setSaleQty(data.getSaleQty().add(amount));
			data.setSaleMoney(data.getSaleMoney().add(saleMoney));
			data.setSaleCost(data.getSaleCost().add(costMoney));
			data.setSaleProfit(data.getSaleProfit().add(profit));
		}

		objects = wholesaleReturnService.findBranchItemSupplierSum(systemBookCode, branchNums, null,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, null, null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer supplierNum = (Integer) object[2];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal saleMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			BigDecimal costMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			BigDecimal profit = saleMoney.subtract(costMoney);
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append("_").append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				Branch branchInfo = AppUtil.getBranch(branches, branchNum);
				if (branchInfo == null) {
					continue;
				}
				data.setBranchCode(branchInfo.getBranchCode());
				data.setBranchName(branchInfo.getBranchName());
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				map.put(key, data);
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
			}
			data.setSaleQty(data.getSaleQty().subtract(amount));
			data.setSaleMoney(data.getSaleMoney().subtract(saleMoney));
			data.setSaleCost(data.getSaleCost().subtract(costMoney));
			data.setSaleProfit(data.getSaleProfit().subtract(profit));
		}
		objects = posOrderService.findItemSupplierSumByCategory(systemBookCode, supplierSaleQuery.getBranchNums(),
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, true, null);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branch = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer supplierNum = (Integer) object[2];
			BigDecimal saleMoney = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal amount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branch).append("_").append(itemNum).append("_").append(supplierNum).toString();
			SupplierComplexReportDTO data = map.get(key);
			if (data == null) {
				data = new SupplierComplexReportDTO();
				SupplierComplexReportDTO baseData = baseMap.get(itemNum);
				if (baseData == null) {
					continue;
				}
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branch);
				Branch branchInfo = AppUtil.getBranch(branches, branch);
				if (branchInfo == null) {
					continue;
				}
				data.setBranchCode(branchInfo.getBranchCode());
				data.setBranchName(branchInfo.getBranchName());
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier == null) {
					continue;
				}
				data.setSupplierCode(supplier.getSupplierCode());
				data.setSupplierName(supplier.getSupplierName());
				map.put(key, data);
				if(!itemNums.contains(itemNum)){
					itemNums.add(itemNum);
				}
			}
			data.setSaleQty(data.getSaleQty().add(amount));
			data.setSaleMoney(data.getSaleMoney().add(saleMoney));
			data.setSaleCost(data.getSaleCost().add(saleMoney.subtract(profit)));
			data.setSaleProfit(data.getSaleProfit().add(profit));
		}
		List<SupplierComplexReportDTO> list = new ArrayList<SupplierComplexReportDTO>(map.values());
		if(itemNums.isEmpty()){
			return list;
		}
		objects = inventoryService.findBranchItemSummary(systemBookCode, supplierSaleQuery.getBranchNums(), itemNums);
		// 算毛利率 读取库存金额 数量
		for (SupplierComplexReportDTO supplierComplexReportDTO : list) {
			Object[] object = readInventoryObjects(objects, supplierComplexReportDTO.getBranchNum(),
					supplierComplexReportDTO.getItemNum());
			if (object != null) {
				BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				supplierComplexReportDTO.setStockQty(amount);
			}
			if (supplierComplexReportDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
				supplierComplexReportDTO.setSaleProfitRate(BigDecimal.ZERO);
			} else {
				supplierComplexReportDTO.setSaleProfitRate(supplierComplexReportDTO.getSaleProfit()
						.divide(supplierComplexReportDTO.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)));
			}
			if (supplierComplexReportDTO.getSaleQty().compareTo(BigDecimal.ZERO) == 0) {
				supplierComplexReportDTO.setRoundRate(BigDecimal.ZERO);
			} else {
				supplierComplexReportDTO.setRoundRate(supplierComplexReportDTO
						.getStockQty()
						.divide(supplierComplexReportDTO.getSaleQty(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(
								new BigDecimal(DateUtil.diffDayV2(supplierSaleQuery.getDateFrom(),
										supplierSaleQuery.getDateTo()))));
			}
		}
		return list;

	}

	private Object[] readInventoryObjects(List<Object[]> objects, Integer branchNum, Integer itemNum) {
		for (int i = 0, len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			if (object[0] == null || object[1] == null) {
				continue;
			}
			if (object[0].equals(branchNum) && object[1].equals(itemNum)) {
				objects.remove(i);
				return object;
			}
		}
		return null;
	}

	@Override
	public List<SupplierComplexReportDetailDTO> findSupplierSaleDetailDatas(SupplierSaleQuery supplierSaleQuery) {
		String systemBookCode = supplierSaleQuery.getSystemBookCode();
		List<Integer> branchNums = supplierSaleQuery.getBranchNums();

		List<PosItem> posItems = posItemService.find(systemBookCode, supplierSaleQuery.getCategoryCodes(),
				supplierSaleQuery.getItemNums(), null);
		List<Branch> branches = branchService.findInCache(systemBookCode);
		List<Supplier> suppliers = supplierService.findAll(systemBookCode);
		List<Storehouse> storehouses = storehouseService.findByBranchs(systemBookCode, branchNums);
		List<Integer> storehouseNums = new ArrayList<Integer>();
		for (Storehouse storehouse : storehouses) {
			storehouseNums.add(storehouse.getStorehouseNum());
		}
		Map<Integer, SupplierComplexReportDetailDTO> map = new HashMap<Integer, SupplierComplexReportDetailDTO>();
		List<SupplierComplexReportDetailDTO> list = new ArrayList<SupplierComplexReportDetailDTO>();
		for (int i = 0, len = posItems.size(); i < len; i++) {
			PosItem posItem = posItems.get(i);
			SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
			BeanUtils.copyProperties(posItem, data);
			data.setCategoryCode(posItem.getItemCategoryCode());
			data.setCategoryName(posItem.getItemCategory());
			map.put(posItem.getItemNum(), data);
		}
		List<Object[]> objects = receiveOrderService.findDetailBySupplierNum(systemBookCode, branchNums, null,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[15];
			Integer supplierNum = (Integer) object[1];
			Integer branchNum = (Integer) object[2];
			BigDecimal qty = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			BigDecimal subtotal = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];
			String fid = (String) object[0];
			Date saleDate = (Date) object[3];
			String itemName = (String) object[5];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDetailDTO baseData = map.get(itemNum);
			if (baseData != null) {
				SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
				BeanUtils.copyProperties(baseData, data);
				data.setOrderFid(fid);
				data.setSupplierNum(supplierNum);
				data.setBranchNum(branchNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
				}
				Branch branch = AppUtil.getBranch(branches, branchNum);
				if (branch != null) {
					data.setBranchCode(branch.getBranchCode());
					data.setBranchName(branch.getBranchName());
				}
				data.setInQty(qty);
				data.setInMoney(subtotal);
				data.setSaleType(AppConstants.CHECKBOX_RECEIVE);
				data.setItemName(itemName);
				data.setDate(saleDate);
				list.add(data);
			}
		}
		objects = returnOrderService.findDetailBySupplierNum(systemBookCode, branchNums, null,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), supplierSaleQuery.getItemNums());
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[15];
			Integer branchNum = (Integer) object[2];
			Integer supplierNum = (Integer) object[1];
			BigDecimal qty = object[12] == null ? BigDecimal.ZERO : (BigDecimal) object[12];
			BigDecimal subtotal = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];
			String fid = (String) object[0];
			Date saleDate = (Date) object[3];
			String itemName = (String) object[5];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDetailDTO baseData = map.get(itemNum);
			if (baseData != null) {
				SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
				BeanUtils.copyProperties(baseData, data);
				data.setOrderFid(fid);
				data.setSupplierNum(supplierNum);
				data.setBranchNum(branchNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
				}
				Branch branch = AppUtil.getBranch(branches, branchNum);
				if (branch != null) {
					data.setBranchCode(branch.getBranchCode());
					data.setBranchName(branch.getBranchName());
				}
				data.setInQty(qty.negate());
				data.setInMoney(subtotal.negate());
				data.setSaleType(AppConstants.CHECKBOX_RETURN);
				data.setItemName(itemName);
				data.setDate(saleDate);
				list.add(data);
			}
		}
		objects = wholesaleOrderService.findItemSupplierDetail(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null);
		for (int i = 0, len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			String fid = (String) object[0];
			Integer itemNum = (Integer) object[1];
			Integer branchNum = (Integer) object[2];
			Integer supplierNum = (Integer) object[3];
			BigDecimal qty = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal saleSubtotal = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			BigDecimal subTotal = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			Date saleDate = (Date) object[9];
			String itemName = (String) object[10];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDetailDTO baseData = map.get(itemNum);
			if (baseData != null) {
				SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				data.setOrderFid(fid);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
				}
				Branch branch = AppUtil.getBranch(branches, branchNum);
				if (branch != null) {
					data.setBranchCode(branch.getBranchCode());
					data.setBranchName(branch.getBranchName());
				}
				data.setSaleQty(qty);
				data.setSaleMoney(saleSubtotal);
				data.setSaleType(AppConstants.CHECKBOX_WHO);
				data.setSaleCost(subTotal);
				data.setItemName(itemName);
				data.setSaleProfit(saleSubtotal.subtract(subTotal));
				if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
					data.setSaleProfitRate(BigDecimal.ZERO);
				} else {
					data.setSaleProfitRate(data.getSaleProfit()
							.divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
				}
				data.setDate(saleDate);
				list.add(data);
			}
		}
		objects = wholesaleReturnService.findItemSupplierDetail(systemBookCode, branchNums,
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo());
		for (int i = 0, len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			String fid = (String) object[0];
			Integer itemNum = (Integer) object[1];
			Integer branchNum = (Integer) object[2];
			Integer supplierNum = (Integer) object[3];
			BigDecimal qty = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal saleSubtotal = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			BigDecimal subTotal = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
			Date saleDate = (Date) object[9];
			String itemName = (String) object[10];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			SupplierComplexReportDetailDTO baseData = map.get(itemNum);
			if (baseData != null) {
				SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branchNum);
				data.setOrderFid(fid);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
				}
				Branch branch = AppUtil.getBranch(branches, branchNum);
				if (branch != null) {
					data.setBranchCode(branch.getBranchCode());
					data.setBranchName(branch.getBranchName());
				}
				data.setSaleQty(qty.negate());
				data.setSaleMoney(saleSubtotal.negate());
				data.setSaleType(AppConstants.CHECKBOX_WHO_RETURN);
				data.setSaleCost(subTotal.negate());
				data.setItemName(itemName);
				if (qty.compareTo(BigDecimal.ZERO) == 0) {
					data.setSaleCost(BigDecimal.ZERO);
				} else {
					data.setSaleCost(subTotal.divide(qty, 4, BigDecimal.ROUND_HALF_UP));
				}
				data.setSaleProfit(saleSubtotal.subtract(subTotal).negate());
				if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
					data.setSaleProfitRate(BigDecimal.ZERO);
				} else {
					data.setSaleProfitRate(data.getSaleProfit()
							.divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
				}
				data.setDate(saleDate);
				list.add(data);
			}
		}
		objects = posOrderService.findOrderDetailWithSupplier(systemBookCode, supplierSaleQuery.getBranchNums(),
				supplierSaleQuery.getDateFrom(), supplierSaleQuery.getDateTo(), null, true);
		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			Integer branch = (Integer) object[0];
			Integer itemNum = (Integer) object[2];
			Integer supplierNum = (Integer) object[3];
			BigDecimal saleMoney = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal profit = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			String fid = (String) object[7];
			BigDecimal qty = object[10] == null ? BigDecimal.ZERO : (BigDecimal) object[10];
			Integer stateCode = (Integer) object[11];
			if (supplierNum == null
					|| (supplierSaleQuery.getSupplierNums() != null && supplierSaleQuery.getSupplierNums().size() > 0 && !supplierSaleQuery
							.getSupplierNums().contains(supplierNum))) {
				continue;
			}
			if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_REMOVE) {
				continue;
			}
			SupplierComplexReportDetailDTO baseData = map.get(itemNum);
			if (baseData != null) {
				SupplierComplexReportDetailDTO data = new SupplierComplexReportDetailDTO();
				BeanUtils.copyProperties(baseData, data);
				data.setBranchNum(branch);
				data.setOrderFid(fid);
				data.setSupplierNum(supplierNum);
				Supplier supplier = AppUtil.getSupplier(supplierNum, suppliers);
				if (supplier != null) {
					data.setSupplierCode(supplier.getSupplierCode());
					data.setSupplierName(supplier.getSupplierName());
				}
				Branch branchModel = AppUtil.getBranch(branches, branch);
				if (branch != null) {
					data.setBranchCode(branchModel.getBranchCode());
					data.setBranchName(branchModel.getBranchName());
				}
				// 赠送/
				if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_PRESENT) {
					saleMoney = BigDecimal.ZERO;
					// 退货
				} else if (stateCode == AppConstants.POS_ORDER_DETAIL_STATE_CANCEL) {
					saleMoney = saleMoney.negate();
					qty = qty.negate();
					profit = profit.negate();
				}
				data.setSaleQty(qty);
				data.setSaleMoney(saleMoney);
				data.setSaleType(AppConstants.CHECKBOX_SALE);
				data.setSaleCost(saleMoney.subtract(profit));
				data.setSaleProfit(profit);
				if (data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
					data.setSaleProfitRate(BigDecimal.ZERO);
				} else {
					data.setSaleProfitRate(data.getSaleProfit()
							.divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
				}
				data.setDate(DateUtil.getDateStr((String) object[1]));
				list.add(data);
			}
		}
		return list;
	}

	@Override
	public List<BranchDayReport> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {
		List<Object[]> objects = reportService.findDayWholes(systemBookCode,branchNums,dateFrom,dateTo,type);
		int size = objects.size();
		List<BranchDayReport> list = new ArrayList<BranchDayReport>(size);
		if(objects.isEmpty()){
			return list;
		}
		Object[] object;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			BranchDayReport branchDayReport = new BranchDayReport();
			branchDayReport.setBranchNum((Integer) object[0]);
			branchDayReport.setDay((String) object[1]);
			branchDayReport.setValue(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			if(type == 3 || type == 4 || type ==7 || type ==8 ){
				branchDayReport.setMember(true);
			}
			list.add(branchDayReport);
		}
		return list;
	}

	@Override
	public List<BranchMonthReport> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {

		List<Object[]> objects = reportService.findMonthWholes(systemBookCode,branchNums,dateFrom,dateTo,type);
		int size = objects.size();
		List<BranchMonthReport> list = new ArrayList<BranchMonthReport>(size);
		if(objects.isEmpty()){
			return list;
		}
		Object[] object;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			BranchMonthReport branchMonthReport = new BranchMonthReport();
			branchMonthReport.setBranchNum((Integer) object[0]);
			branchMonthReport.setMonth((String) object[1]);
			branchMonthReport.setBizMoney(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			branchMonthReport.setOrderCount(object[3] == null?0:(Integer) object[3]);
			branchMonthReport.setProfit(object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4]);
			branchMonthReport.setBizdayCount(object[5] == null?0:(Integer) object[5]);
			if(type == 3 || type == 4 || type ==7 || type ==8 ){
				branchMonthReport.setMember(true);
			}
			list.add(branchMonthReport);
			
		}
		return list;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<BusinessCollection> list = reportService.findBusinessCollectionByBranch(systemBookCode, branchNums, dateFrom, dateTo);
		int size = list.size();
		Map<Integer, BusinessCollection> map = new HashMap<Integer, BusinessCollection>(size);
		for (int i = 0; i <size ; i++) {
			BusinessCollection collection = list.get(i);
			map.put(collection.getBranchNum(),collection);
		}
		List<Object[]> detailList = posOrderService.findBranchCouponSummary(systemBookCode, branchNums, dateFrom, dateTo);

		for (int i = 0,len = detailList.size(); i < len; i++) {
			Object[] object = detailList.get(i);
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

		}

		List<Object[]> posList = posOrderService.findBranchDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = posList.size(); i < len ; i++) {
			Object[] object = posList.get(i);
			Integer branchNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal couponMoney = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(branchNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setAllDiscountMoney(money);

			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponMoney));
		}


		String redisKey = AppConstants.REDIS_PRE_BOOK_FUNCTION + systemBookCode;
		Object object = RedisUtil.hashGet(redisKey,AppConstants.MARKETACTION_SELF_ACTION);
		if(object != null){
			BigDecimal payMoney = marketActionOpenIdService.findPayMoneyByBranch(systemBookCode, dateFrom, dateTo);
			Integer branchNum = 99;
			BusinessCollection data = map.get(branchNum);
			if(data == null){
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				map.put(branchNum, data);
			}
			data.setPayMoney(payMoney);
		}


		List<BusinessCollection> result = new ArrayList<>(map.values());

		List<BranchDTO> branchDTOS = branchRpc.findInCache(systemBookCode);//返回数据增加branchName
		for (int i = 0,len = result.size(); i < len ; i++) {
			BusinessCollection data = result.get(i);
			Integer branchNum = data.getBranchNum();
			for (int j = 0; j <branchDTOS.size() ; j++) {
				BranchDTO branchDTO = branchDTOS.get(j);
				if(branchNum.equals(branchDTO.getBranchNum())){
					data.setBranchName(branchDTO.getBranchName());
					break;
				}
			}
		}

		return result;
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		List<BusinessCollection> list = reportService.findBusinessCollectionByBranchDay(systemBookCode, branchNums, dateFrom, dateTo);
		int size = list.size();
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>(size);
		for (int i = 0; i < size ; i++) {
			BusinessCollection collection = list.get(i);
			StringBuilder sb = new StringBuilder();
			String key = sb.append(collection.getBranchNum()).append(collection.getShiftTableBizday()).toString();
			map.put(key,collection);
		}

		List<Object[]> detailList = posOrderService.findBranchBizdayCouponSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = detailList.size(); i < len; i++) {
			Object[] object = detailList.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(shiftTableBizday).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

		}

		List<Object[]> postList = posOrderService.findBranchBizdayDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = postList.size(); i < len; i++) {
			Object[] object = postList.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];

			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(shiftTableBizday).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}
			data.setAllDiscountMoney(money);

			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}




		String redisKey = AppConstants.REDIS_PRE_BOOK_FUNCTION + systemBookCode;
		Object obj = RedisUtil.hashGet(redisKey,AppConstants.MARKETACTION_SELF_ACTION);
		if(obj != null){
			List<Object[]> payMoneyList = marketActionOpenIdService.findPayMoneyByBranchBizday(systemBookCode,dateFrom, dateTo);
			for (int i = 0,len = payMoneyList.size(); i < len ; i++) {
				Object[] object = payMoneyList.get(i);
				int branchNum = 99;
				String  shiftTableBizday = (String)object[0];
				BigDecimal payMoney = (BigDecimal)object[1];
				StringBuilder sb = new StringBuilder();
				String key = sb.append(branchNum).append(shiftTableBizday).toString();
				BusinessCollection data = map.get(key);
				if(data == null){
					data = new BusinessCollection();
					data.setBranchNum(branchNum);
					data.setShiftTableBizday(shiftTableBizday);
					map.put(key, data);
				}
				data.setPayMoney(payMoney);
			}
		}


		return new ArrayList<BusinessCollection>(map.values());
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {


		List<BusinessCollection> list = reportService.findBusinessCollectionByTerminal(systemBookCode, branchNums, dateFrom, dateTo);
		int size = list.size();
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>(size);
		for (int i = 0; i <size; i++) {
			BusinessCollection collection = list.get(i);
			Integer branchNum = collection.getBranchNum();
			String bizday = collection.getShiftTableBizday();
			String machineName = collection.getPosMachineName();
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(bizday).append(machineName).toString();
			map.put(key,collection);
		}

		List<Object[]> poslist = posOrderService.findCouponSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = poslist.size(); i < len; i++) {
			Object[] object = poslist.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null ? "" : (String) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(shiftTableBizday).append(machineName).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

		}

		List<Object[]> couponSummary = posOrderService.findCouponDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = couponSummary.size(); i < len ; i++) {
			Object[] object = couponSummary.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String machineName = object[2] == null ? "" : (String) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(shiftTableBizday).append(machineName).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setPosMachineName(machineName);
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}

			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));

		}

		return new ArrayList<BusinessCollection>(map.values());
	}

	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {


		List<BusinessCollection> list = reportService.findBusinessCollectionByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
		int size = list.size();
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>(size);
		for (int i = 0; i < size ; i++) {
			BusinessCollection collection = list.get(i);
			Integer branchNum = collection.getBranchNum();
			String bizday = collection.getShiftTableBizday();
			Integer bizNum = collection.getShiftTableNum();
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(bizday).append(bizNum).toString();
			map.put(key,collection);
		}

		List<Object[]> payment = posOrderService.findBranchShiftTablePaymentSummary(systemBookCode, branchNums, dateFrom, dateTo, casher);
		for (int i = 0,len = payment.size(); i < len; i++) {
			Object[] object = payment.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal unPaidMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(bizDay).append(bizNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setBranchNum(branchNum);
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setUnPaidMoney(BigDecimal.ZERO);
				map.put(key, data);
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

		List<Object[]> detailItem = posOrderService.findBranchShiftTableCouponSummary(systemBookCode, branchNums, dateFrom, dateTo, casher);
		for (int i = 0,len = detailItem.size(); i < len; i++) {
			Object[] object = detailItem.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(bizDay).append(bizNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);

		}

		List<Object[]> discountSummary = posOrderService.findBranchShiftTableDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo, casher);
		for (int i = 0,len = discountSummary.size(); i < len ; i++) {
			Object[] object = discountSummary.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(bizDay).append(bizNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				data.setBranchNum(branchNum);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}

		List<ShiftTable> shiftTables = reportService.findShiftTables(systemBookCode, branchNums, dateFrom, dateTo, casher);
		for (int i = 0; i < shiftTables.size(); i++) {
			ShiftTable shiftTable = shiftTables.get(i);
			Integer branchNum = shiftTable.getId().getBranchNum();
			String shiftTableBizday = shiftTable.getId().getShiftTableBizday();
			Integer shiftTableNum = shiftTable.getId().getShiftTableNum();
			StringBuilder sb = new StringBuilder();
			String key = sb.append(branchNum).append(shiftTableBizday).append(shiftTableNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setShiftTableBizday(shiftTable.getId().getShiftTableBizday());
				data.setShiftTableNum(shiftTable.getId().getShiftTableNum());
				data.setBranchNum(shiftTable.getId().getBranchNum());
				map.put(key, data);
			}
			data.setShiftTableTerminalId(shiftTable.getShiftTableTerminalId());
			data.setCasher(shiftTable.getShiftTableUserName());
			data.setReceiveCash(shiftTable.getShiftTableActualMoney() == null ? BigDecimal.ZERO : shiftTable
					.getShiftTableActualMoney());
			data.setReceiveBankMoney(shiftTable.getShiftTableActualBankMoney() == null ? BigDecimal.ZERO : shiftTable
					.getShiftTableActualBankMoney());
			data.setShiftTableStart(shiftTable.getShiftTableStart());
			data.setShiftTableEnd(shiftTable.getShiftTableEnd());
		}

		return new ArrayList<BusinessCollection>(map.values());
	}

    @Override
    public List<BusinessCollection> findBusinessCollectionByMerchantDay(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		List<BusinessCollection> list = reportService.findBusinessCollectionByMerchantDay(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		int size = list.size();
		Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>(size);
		for (int i = 0; i < size ; i++) {
			BusinessCollection collection = list.get(i);
			StringBuilder sb = new StringBuilder();
			String key = sb.append(collection.getMerchantNum()).append(collection.getShiftTableBizday()).toString();
			map.put(key,collection);
		}
		List<Object[]> postList = posOrderService.findMerchantBizdayCouponSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		for (int i = 0,len = postList.size(); i < len; i++) {
			Object[] object = postList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			String type = (String) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal money = AppUtil.getValue(object[4], BigDecimal.class);
			StringBuilder sb = new StringBuilder();
			String key = sb.append(tempMerchantNum).append(shiftTableBizday).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
		}
		postList = posOrderService.findMerchantBizdayDiscountSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		for (int i = 0,len = postList.size(); i < len; i++) {
			Object[] object = postList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(tempMerchantNum).append(shiftTableBizday).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setShiftTableBizday(shiftTableBizday);
				map.put(key, data);
			}
			data.setAllDiscountMoney(money);

			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}
		return new ArrayList<>(map.values());
    }

    @Override
    public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo, String casher) {

        List<BusinessCollection> list = reportService.findBusinessCollectionByShiftTable(systemBookCode, branchNum, merchantNum, dateFrom, dateTo, casher);
        int size = list.size();
        Map<String, BusinessCollection> map = new HashMap<String, BusinessCollection>(size);
        for (int i = 0; i < size ; i++) {
            BusinessCollection collection = list.get(i);
            Integer tempMerchantNum = collection.getBranchNum();
            String bizday = collection.getShiftTableBizday();
            Integer bizNum = collection.getShiftTableNum();
            StringBuilder sb = new StringBuilder();
            String key = sb.append(tempMerchantNum).append(bizday).append(bizNum).toString();
            map.put(key,collection);
        }
		List<Object[]> payment = posOrderService.findMerchantShiftTableCouponSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo, casher);
		for (int i = 0,len = payment.size(); i < len; i++) {
			Object[] object = payment.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			Integer bizNum = (Integer) object[2];
			String type = (String) object[3];
			BigDecimal amount = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal money = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(tempMerchantNum).append(shiftTableBizday).append(bizNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setShiftTableBizday(shiftTableBizday);
				data.setShiftTableNum(bizNum);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
		}

		List<Object[]> discountSummary = posOrderService.findMerchantShiftTableDiscountSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo, casher);
		for (int i = 0,len = discountSummary.size(); i < len ; i++) {
			Object[] object = discountSummary.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			String bizDay = (String) object[1];
			Integer bizNum = (Integer) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			StringBuilder sb = new StringBuilder();
			String key = sb.append(tempMerchantNum).append(bizDay).append(bizNum).toString();
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setShiftTableBizday(bizDay);
				data.setShiftTableNum(bizNum);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}


		payment = posOrderService.findBranchShiftTablePaymentSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo, casher);
        for (int i = 0,len = payment.size(); i < len; i++) {
            Object[] object = payment.get(i);
            Integer tempMerchantNum = (Integer) object[0];
            String bizDay = (String) object[1];
            Integer bizNum = (Integer) object[2];
            String type = (String) object[3];
            BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
            BigDecimal unPaidMoney = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
            StringBuilder sb = new StringBuilder();
            String key = sb.append(tempMerchantNum).append(bizDay).append(bizNum).toString();
            BusinessCollection data = map.get(key);
            if (data == null) {
                data = new BusinessCollection();
                data.setMerchantNum(tempMerchantNum);
                data.setShiftTableBizday(bizDay);
                data.setShiftTableNum(bizNum);
                data.setUnPaidMoney(BigDecimal.ZERO);
                map.put(key, data);
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

        List<ShiftTable> shiftTables = reportService.findShiftTables(systemBookCode, branchNum, merchantNum, dateFrom, dateTo, casher);
        for (int i = 0; i < shiftTables.size(); i++) {
            ShiftTable shiftTable = shiftTables.get(i);
            Integer tempMerchantNum = shiftTable.getMerchantNum();
            String shiftTableBizday = shiftTable.getId().getShiftTableBizday();
            Integer shiftTableNum = shiftTable.getId().getShiftTableNum();
            StringBuilder sb = new StringBuilder();
            String key = sb.append(tempMerchantNum).append(shiftTableBizday).append(shiftTableNum).toString();
            BusinessCollection data = map.get(key);
            if (data == null) {
                data = new BusinessCollection();
                data.setMerchantNum(tempMerchantNum);
                data.setShiftTableBizday(shiftTable.getId().getShiftTableBizday());
                data.setShiftTableNum(shiftTable.getId().getShiftTableNum());
                data.setBranchNum(shiftTable.getId().getBranchNum());
                map.put(key, data);
            }
            data.setShiftTableTerminalId(shiftTable.getShiftTableTerminalId());
            data.setCasher(shiftTable.getShiftTableUserName());
            data.setReceiveCash(shiftTable.getShiftTableActualMoney() == null ? BigDecimal.ZERO : shiftTable
                    .getShiftTableActualMoney());
            data.setReceiveBankMoney(shiftTable.getShiftTableActualBankMoney() == null ? BigDecimal.ZERO : shiftTable
                    .getShiftTableActualBankMoney());
            data.setShiftTableStart(shiftTable.getShiftTableStart());
            data.setShiftTableEnd(shiftTable.getShiftTableEnd());
        }

        return new ArrayList<BusinessCollection>(map.values());
    }

    @Override
    public List<BusinessCollection> findBusinessCollectionByMerchant(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		List<BusinessCollection> list = reportService.findBusinessCollectionByMerchant(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		int size = list.size();
		Map<Integer, BusinessCollection> map = new HashMap<Integer, BusinessCollection>(size);
		for (int i = 0; i <size ; i++) {
			BusinessCollection collection = list.get(i);
			map.put(collection.getMerchantNum(),collection);
		}
		List<Object[]> detailList = posOrderService.findMerchantCouponSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		for (int i = 0,len = detailList.size(); i < len; i++) {
			Object[] object = detailList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			String type = (String) object[1];
			BigDecimal amount = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal money = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(tempMerchantNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				map.put(tempMerchantNum, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);


		}
		List<Object[]> posList = posOrderService.findMerchantDiscountSummary(systemBookCode, branchNum, merchantNum, dateFrom, dateTo);
		for (int i = 0,len = posList.size(); i < len ; i++) {
			Object[] object = posList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			BigDecimal money = object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1];
			BigDecimal couponTotal = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BusinessCollection data = map.get(tempMerchantNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				map.put(tempMerchantNum, data);
			}
			data.setAllDiscountMoney(money);
			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}

		return new ArrayList<>(map.values());
    }

    @Override
    public List<BusinessCollection> findBusinessCollectionByStall(String systemBookCode, Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo) {
		List<BusinessCollection> list = reportService.findBusinessCollectionByStall(systemBookCode, branchNum, merchantNum, stallNum, dateFrom, dateTo);
		int size = list.size();
		Map<String, BusinessCollection> map = new HashMap<>(size);
		for (int i = 0; i <size ; i++) {
			BusinessCollection collection = list.get(i);
			map.put(collection.getMerchantNum()+"_"+collection.getStallNum(),collection);
		}
		List<Object[]> posList = posOrderService.findStallCouponSummary(systemBookCode, branchNum, merchantNum, stallNum, dateFrom, dateTo);
		for (int i = 0,len = posList.size(); i < len; i++) {
			Object[] object = posList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			Integer tempStallNum = (Integer) object[1];
			String type = (String) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			String key = tempMerchantNum+"_"+tempStallNum;
			BusinessCollection data = map.get(key);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setStallNum(tempStallNum);
				map.put(key, data);
			}
			BusinessCollectionIncome detail = new BusinessCollectionIncome();
			detail.setName(type);
			detail.setMoney(money);
			detail.setQty(amount);
			data.getTicketIncomes().add(detail);
		}
		posList = posOrderService.findStallDiscountSummary(systemBookCode, branchNum, merchantNum, stallNum, dateFrom, dateTo);
		for (int i = 0,len = posList.size(); i < len ; i++) {
			Object[] object = posList.get(i);
			Integer tempMerchantNum = (Integer) object[0];
			Integer tempStallNum = (Integer) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
			BigDecimal couponTotal = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			BusinessCollection data = map.get(tempMerchantNum+"_"+tempStallNum);
			if (data == null) {
				data = new BusinessCollection();
				data.setMerchantNum(tempMerchantNum);
				data.setStallNum(tempStallNum);
				map.put(tempMerchantNum+"_"+stallNum, data);
			}
			data.setAllDiscountMoney(money);

			BusinessCollectionIncome detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(couponTotal));
		}

		return new ArrayList<>(map.values());
    }

    @Override
	public List<OrderCompare> findCategoryMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findCategoryMoney(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<OrderDetailCompare> findOrderDetailCompareDatas(String systemBookCode, List<Integer> branchNums, List<String> itemCategoryCodes, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo, List<Integer> itemNums) {
		return reportService.findOrderDetailCompareDatas(systemBookCode,branchNums,itemCategoryCodes,lastDateFrom,lastDateTo,thisDateFrom,thisDateTo,itemNums);
	}

	@Override
	public List<SupplierSaleGroupByDate> findSupplierSaleGroupByDateDatas(SupplierSaleQuery supplierSaleQuery) {
		return reportService.findSupplierSaleGroupByDateDatas(supplierSaleQuery);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findABCDatasBySale' + #p0.getKey()")
	public List<ABCAnalysis> findABCDatasBySale(ABCListQuery query) {
		return reportService.findABCDatasBySale(query);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findABCDatasByProfit' + #p0.getKey()")
	public List<ABCAnalysis> findABCDatasByProfit(ABCListQuery query) {
		return reportService.findABCDatasByProfit(query);
	}

	@Override
	public List<TransferGoal> findTransferGoalByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return reportService.findTransferGoalByDate(systemBookCode,branchNums,dateFrom,dateTo,dateType);
	}

	@Override
	public List<SupplierBranchSum> findSupplierBranchSum(SupplierBranchQuery supplierBranchQuery) {
		return reportService.findSupplierBranchSum(supplierBranchQuery);
	}

	@Override
	public List<SupplierBranchGroupByItem> findSupplierBranchGroupByItem(SupplierBranchQuery supplierBranchQuery) {
		return reportService.findSupplierBranchGroupByItem(supplierBranchQuery);
	}

	@Override
	public List<SupplierBranchGroupByDate> findSupplierBranchGroupByDate(SupplierBranchQuery supplierBranchQuery) {
		return reportService.findSupplierBranchGroupByDate(supplierBranchQuery);
	}

	@Override
	public List<SupplierSaleRank> findSupplierSaleRank(SupplierSaleQuery supplierSaleQuery) {
		return reportService.findSupplierSaleRank(supplierSaleQuery);
	}

	@Override
	public List<ABCChart> findABCChartByType(ABCChartQuery query, int type) {
		return reportService.findABCChartByType(query,type);
	}

	@Override
	public List<SupplierBranchDetail> findSupplierBranchDetail(SupplierBranchQuery supplierBranchQuery) {
		return reportService.findSupplierBranchDetail(supplierBranchQuery);
	}

	@Override
	public List<SupplierCredit> findSupplierCredit(SupplierBranchQuery supplierBranchQuery) {
		return reportService.findSupplierCredit(supplierBranchQuery);
	}

	@Override
	@Cacheable(value = "serviceCache", key="'AMA_findSupplierLianYing' + #p0.getKey()")
	public List<SupplierLianYing> findSupplierLianYing(SupplierSaleQuery supplierSaleQuery) {
		return reportService.findSupplierLianYing(supplierSaleQuery);
	}

	@Override
	public List<UnsalablePosItem> findUnsalableItems(UnsalableQuery query) {
		return reportService.findUnsalableItems(query);
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitByClient(WholesaleProfitQuery queryData) {
		return reportService.findWholesaleProfitByClient(queryData);
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitByBranch(WholesaleProfitQuery queryData) {
		return reportService.findWholesaleProfitByBranch(queryData);
	}

	@Override
	public List<WholesaleProfitByPosItem> findWholesaleProfitByPosItem(WholesaleProfitQuery queryData) {
		return reportService.findWholesaleProfitByPosItem(queryData);
	}

	@Override
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByPosItemDetail(WholesaleProfitQuery queryData) {
		return reportService.findWholesaleProfitByPosItemDetail(queryData);
	}

	@Override
	public List<ToPicking> findToPicking(ShipGoodsQuery queryData) {
		return reportService.findToPicking(queryData);
	}

	@Override
	public List<ToShip> findToShip(ShipGoodsQuery queryData) {
		return reportService.findToShip(queryData);
	}

	@Override
	public List<UnsalablePosItem> findNegativeMargin(InventoryExceptQuery queryData) {
		return reportService.findNegativeMargin(queryData);
	}

	@Override
	public List<ExceptInventory> findSingularItem(InventoryExceptQuery inventoryExceptQuery) {
		return reportService.findSingularItem(inventoryExceptQuery);
	}

	@Override
	public List<SingularPrice> findSingularPrice(InventoryExceptQuery inventoryExceptQuery) {
		return reportService.findSingularPrice(inventoryExceptQuery);
	}

	@Override
	public List<BranchBizSummary> findBizAndMoney(String systemBookCode, Integer branchNum, String queryBy, String dateType, Date dateFrom, Date dateTo) {
		List<Object[]> objects = reportService.findBizAndMoney(systemBookCode,branchNum,queryBy,dateType,dateFrom,dateTo);
		int size = objects.size();
		List<BranchBizSummary> list = new ArrayList<BranchBizSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		Object[] object = null;
		for(int i = 0;i < size;i++){
			object = objects.get(i);
			BranchBizSummary branchBizSummary = new BranchBizSummary();
			branchBizSummary.setBranchNum((Integer) object[0]);
			branchBizSummary.setBiz((String)object[1]);
			if(object[2] instanceof  BigDecimal){
				branchBizSummary.setMoney(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]);
			
			} else if(object[2] instanceof  Integer){
				branchBizSummary.setCount(object[2] == null?0:(Integer) object[2]);
				
			}
			list.add(branchBizSummary);
		}
		return list;
	}

	@Override
	public List<UnsalablePosItem> findInventoryOverStock(UnsalableQuery unsalableQuery) {
		return reportService.findInventoryOverStock(unsalableQuery);
	}

	@Override
	public List<RetailDetail> findRetailDetails(String systemBookCode, RetailDetailQueryData retailDetailQueryData) {
		return reportService.findRetailDetails(retailDetailQueryData, false);
	}

    @Override
    public List<RetailDetail> findMerchantRetailDetails(String systemBookCode, RetailDetailQueryData retailDetailQueryData) {
        return reportService.findRetailDetails(retailDetailQueryData, true);
    }

    @Override
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData retailDetailQueryData) {
		return reportService.findRetailDetails(retailDetailQueryData, false);
	}
	
	@Override
	public List<PurchaseOrderCollect> findPurchaseDetail(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		return reportService.findPurchaseDetail(purchaseOrderCollectQuery);
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseItem(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		return reportService.findPurchaseItem(purchaseOrderCollectQuery);
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseSupplier(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		return reportService.findPurchaseSupplier(purchaseOrderCollectQuery);
	}

	@Override
	public List<PurchaseOrderCollect> findPurchaseBranchCategory(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
		return reportService.findPurchaseBranchCategory(purchaseOrderCollectQuery);
	}

    @Override
    public List<PurchaseOrderCollect> findPurchaseBranchSupplier(PurchaseOrderCollectQuery purchaseOrderCollectQuery) {
        return reportService.findPurchaseBranchSupplier(purchaseOrderCollectQuery);
    }

    @Override
	public List<BranchBizSummary> findProfitAnalysisDays(ProfitAnalysisQueryData profitAnalysisQueryData) {
		List<Object[]> objects = reportService.findProfitAnalysisDays(profitAnalysisQueryData);
		int size = objects.size();
		List<BranchBizSummary> list = new ArrayList<BranchBizSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			BranchBizSummary branchBizSummary = new BranchBizSummary();
			branchBizSummary.setBranchNum((Integer) object[0]);
			branchBizSummary.setBiz((String) object[1]);
			branchBizSummary.setProfit((BigDecimal) object[2]);
			branchBizSummary.setMoney((BigDecimal) object[3]);
			branchBizSummary.setCost((BigDecimal) object[4]);
			list.add(branchBizSummary);
		}
		return list;
	}

	@Override
	public List<ProfitByClientAndItemSummary> findProfitAnalysisByClientAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {

		List<Object[]> objects = reportService.findProfitAnalysisByClientAndItem(profitAnalysisQueryData);
		int size = objects.size();
		List<ProfitByClientAndItemSummary> list = new ArrayList<ProfitByClientAndItemSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			ProfitByClientAndItemSummary profitByClientAndItemSummary = new ProfitByClientAndItemSummary();
			profitByClientAndItemSummary.setClientFid((String) object[0]);
			profitByClientAndItemSummary.setItemNum((Integer) object[1]);
			profitByClientAndItemSummary.setMatrixNum((int)object[2]);
			profitByClientAndItemSummary.setProfit((BigDecimal) object[3]);
			profitByClientAndItemSummary.setAmount((BigDecimal) object[4]);
			profitByClientAndItemSummary.setMoney((BigDecimal) object[5]);
			profitByClientAndItemSummary.setCost((BigDecimal) object[6]);
			list.add(profitByClientAndItemSummary);
		}
		return list;
	}

	@Override
	public List<ProfitByBranchAndItemSummary> findProfitAnalysisByBranchAndItem(ProfitAnalysisQueryData profitAnalysisQueryData) {


		List<Object[]> objects = reportService.findProfitAnalysisByBranchAndItem(profitAnalysisQueryData);
		int size = objects.size();
		List<ProfitByBranchAndItemSummary> list = new ArrayList<ProfitByBranchAndItemSummary>(size);
		if(objects.isEmpty()){
			return list;
		}

		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			ProfitByBranchAndItemSummary profitByBranchAndItemSummary = new ProfitByBranchAndItemSummary();
			profitByBranchAndItemSummary.setBranchNum((Integer) object[0]);
			profitByBranchAndItemSummary.setItemNum((Integer) object[1]);
			profitByBranchAndItemSummary.setMatrixNum(object[2] == null ? 0 : (int) object[2]);
			profitByBranchAndItemSummary.setProfit(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			profitByBranchAndItemSummary.setAmount(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			profitByBranchAndItemSummary.setMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			profitByBranchAndItemSummary.setCost(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			list.add(profitByBranchAndItemSummary);
		}
		return list;
	}

	@Override
	public List<ProfitAnalysisByItemSummary> findProfitAnalysisByItem(ProfitAnalysisQueryData profitAnalysisQueryData) {

		List<Object[]> objects = reportService.findProfitAnalysisByItem(profitAnalysisQueryData);
		int size = objects.size();
		List<ProfitAnalysisByItemSummary> list = new ArrayList<>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0 ;i<size; i++){
			Object[] object = objects.get(i);
			ProfitAnalysisByItemSummary profitAnalysisByItemSummary = new ProfitAnalysisByItemSummary();
			profitAnalysisByItemSummary.setItemNum((Integer) object[0]);
			profitAnalysisByItemSummary.setProfit((BigDecimal) object[1]);
			profitAnalysisByItemSummary.setPayment((BigDecimal) object[2]);
			profitAnalysisByItemSummary.setCost((BigDecimal) object[3]);
			profitAnalysisByItemSummary.setAmount((BigDecimal) object[4]);
			list.add(profitAnalysisByItemSummary);
		}
		return list;
	}

	@Override
	public List<SalerCommissionBrand> findSalerCommissionBrands(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames) {
		return reportService.findSalerCommissionBrands(systemBookCode,dateFrom,dateTo,branchNums,salerNames);
	}

	@Override
	public List<SalerCommissionCard> findSalerCommissionCards(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames) {
		return reportService.findSalerCommissionCards(systemBookCode,dateFrom,dateTo,branchNums,salerNames);
	}

	@Override
	public List<SalerCommission> findSalerCommissions(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames, BigDecimal interval) {
		return reportService.findSalerCommissions(systemBookCode,dateFrom,dateTo,branchNums,salerNames,interval);
	}

	@Override
	public SalerSummary findSalerSummary(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames) {

		Object[] object = reportService.findSalerSummary(systemBookCode, dateFrom, dateTo, branchNums, salerNames);
		SalerSummary salerSummary = new SalerSummary();
		salerSummary.setAmount((BigDecimal) object[0]);
		salerSummary.setMoney((BigDecimal) object[1]);
		salerSummary.setCommission((BigDecimal) object[2]);
		return salerSummary;
	}

	@Override
	public List<SalerCommissionDetail> findSalerCommissionDetails(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, List<String> salerNames) {
		return reportService.findSalerCommissionDetails(systemBookCode,dateFrom,dateTo,branchNums,salerNames);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<CustomerAnalysisHistory> findCustomerAnalysisHistorys(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType) {
		return reportService.findCustomerAnalysisHistorys(systemBookCode,dateFrom,dateTo,branchNums,saleType);
	}

	@Override
	public List<CustomerAnalysisRange> findCustomerAnalysisRanges(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, Integer rangeFrom, Integer rangeTo, Integer space, String saleType) {
		return reportService.findCustomerAnalysisRanges(systemBookCode,dateFrom,dateTo,branchNums,rangeFrom,rangeTo,space,saleType);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<CustomerAnalysisDay> findCustomerAnalysisDays(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType) {
		return reportService.findCustomerAnalysisDays(systemBookCode,dateFrom,dateTo,branchNums,saleType);
	}

	@Override
	public List<CustomerAnalysisDay> findCustomerAnalysisShiftTables(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String appUserName, String saleType) {
		return reportService.findCustomerAnalysisShiftTables(systemBookCode,dateFrom,dateTo,branchNums,appUserName,saleType);
	}

	@Override
	public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, Integer space, List<Integer> itemNums, String saleType) {
		return reportService.findCustomerAnalysisTimePeriods(systemBookCode,dateFrom,dateTo,branchNums,space,itemNums,saleType);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public CustomerSummary sumCustomerAnalysis(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, String branchType) {
		Object[] object = reportService.sumCustomerAnalysis(systemBookCode, dtFrom, dtTo, branchNums, branchType);
		CustomerSummary customerSummary = new CustomerSummary();
		customerSummary.setMoney((BigDecimal) object[0]);
		customerSummary.setOrderNo((Long) object[1]);
		customerSummary.setProfit((BigDecimal) object[2]);
		customerSummary.setShiftCount((Long) object[3]);
		return customerSummary;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByPosItems' + #p0.getKey()")
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByPosItems(SaleAnalysisQueryData queryData) {
		return reportService.findSaleAnalysisByPosItems(queryData);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByMerchantPosItems' + #p0.getKey()")
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByMerchantPosItems(SaleAnalysisQueryData queryData) {
		return reportService.findSaleAnalysisByMerchantPosItems(queryData);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByBranchs' + #p0.getKey()")
	public List<SaleByBranchSummary> findSaleAnalysisByBranchs(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByBranchs(queryData);
		int size = objects.size();
		List<SaleByBranchSummary> list = new ArrayList<SaleByBranchSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			SaleByBranchSummary saleByBranchSummary = new SaleByBranchSummary();
			saleByBranchSummary.setBranchNum((Integer) object[0]);
			saleByBranchSummary.setStateCode((int)object[1]);
			saleByBranchSummary.setAmount((BigDecimal)object[2]);
			saleByBranchSummary.setMoney((BigDecimal) object[3]);
			saleByBranchSummary.setAssistAmount((BigDecimal) object[4]);
			saleByBranchSummary.setItemNum((Integer) object[5]);
			list.add(saleByBranchSummary);
		}
		return list;
	}

	@Override
	@Deprecated
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByCategorys' + #p0.getKey()")
	public List<SaleByCategorySummary> findSaleAnalysisByCategorys(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByCategorys(queryData);
		int size = objects.size();
		List<SaleByCategorySummary> list = new ArrayList<SaleByCategorySummary>(size);
		if(objects.isEmpty()){
			return list;
		}

		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			SaleByCategorySummary saleByCategorySummary = new SaleByCategorySummary();
			saleByCategorySummary.setItemNum((Integer) object[0]);
			saleByCategorySummary.setStateCode((int)object[1]);
			saleByCategorySummary.setAmount((BigDecimal) object[2]);
			saleByCategorySummary.setPaymentMoney((BigDecimal) object[3]);
			saleByCategorySummary.setAssistAmount((BigDecimal) object[4]);
			saleByCategorySummary.setItemCount((Integer) object[5]);
			if(object[6] instanceof  Double){
				saleByCategorySummary.setDiscount(new BigDecimal((Double) object[6]));

			} else {
				saleByCategorySummary.setDiscount(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

			}
			list.add(saleByCategorySummary);
		}
		return list;
	}

	@Override
	@Deprecated
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByMerchantCategorys' + #p0.getKey()")
	public List<SaleByCategorySummary> findSaleAnalysisByMerchantCategorys(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByMerchantCategorys(queryData);
		int size = objects.size();
		List<SaleByCategorySummary> list = new ArrayList<SaleByCategorySummary>(size);
		if(objects.isEmpty()){
			return list;
		}

		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			SaleByCategorySummary saleByCategorySummary = new SaleByCategorySummary();
			saleByCategorySummary.setItemNum((Integer) object[0]);
			saleByCategorySummary.setStateCode((int)object[1]);
			saleByCategorySummary.setAmount((BigDecimal) object[2]);
			saleByCategorySummary.setPaymentMoney((BigDecimal) object[3]);
			saleByCategorySummary.setAssistAmount((BigDecimal) object[4]);
			saleByCategorySummary.setItemCount((Integer) object[5]);
			if(object[6] instanceof  Double){
				saleByCategorySummary.setDiscount(new BigDecimal((Double) object[6]));

			} else {
				saleByCategorySummary.setDiscount(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

			}
			saleByCategorySummary.setMerchantNum((Integer) object[7]);
			saleByCategorySummary.setStallNum((Integer)object[8]);
			list.add(saleByCategorySummary);
		}
		return list;
	}


	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByCategoryBranchs' + #p0.getKey()")
	public List<SaleByCategoryBranchSummary> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByCategoryBranchs(queryData);
		int size = objects.size();
		List<SaleByCategoryBranchSummary> list = new ArrayList<SaleByCategoryBranchSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i < size ; i++) {
			Object[] object = objects.get(i);
			SaleByCategoryBranchSummary saleByCategoryBranchSummary = new SaleByCategoryBranchSummary();
			saleByCategoryBranchSummary.setBranchNum((Integer) object[0]);
			saleByCategoryBranchSummary.setCategory((String) object[1]);
			saleByCategoryBranchSummary.setCategoryCode((String) object[2]);
			saleByCategoryBranchSummary.setStateCode((Integer)object[3]);
			saleByCategoryBranchSummary.setAmount((BigDecimal) object[4]);
			saleByCategoryBranchSummary.setPaymentMoney((BigDecimal) object[5]);
			saleByCategoryBranchSummary.setAssistAmount((BigDecimal) object[6]);
			saleByCategoryBranchSummary.setItemCount((Integer) object[7]);
			list.add(saleByCategoryBranchSummary);
		}

		return list;
	}

	@Override
	@Deprecated
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByDepartments' + #p0.getKey()")
	public List<SaleByDepartmentSummary> findSaleAnalysisByDepartments(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByDepartments(queryData);
		int size = objects.size();
		List<SaleByDepartmentSummary> list = new ArrayList<SaleByDepartmentSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i<size; i++){
			Object[] object = objects.get(i);
			SaleByDepartmentSummary saleByDepartmentSummary = new SaleByDepartmentSummary();
			saleByDepartmentSummary.setItemNum((Integer) object[0]);
			saleByDepartmentSummary.setStateCode((int)object[1]);
			saleByDepartmentSummary.setAmount((BigDecimal) object[2]);
			saleByDepartmentSummary.setPaymentMoney((BigDecimal) object[3]);
			saleByDepartmentSummary.setAssistAmount((BigDecimal) object[4]);
			saleByDepartmentSummary.setItemCount((Integer) object[5]);
			if(object[6] instanceof  Double){
				saleByDepartmentSummary.setDiscount(new BigDecimal((Double) object[6]));

			} else {
				saleByDepartmentSummary.setDiscount(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

			}
			list.add(saleByDepartmentSummary);
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByItems' + #p0.getKey()")
	public List<SaleAnalysisByItemDTO> findSaleAnalysisByItems(SaleAnalysisQueryData queryData) {
		List<Object[]> objects = reportService.findSaleAnalysisByCategorys(queryData);
		int size = objects.size();
		List<SaleAnalysisByItemDTO> list = new ArrayList<SaleAnalysisByItemDTO>(size);
		if(objects.isEmpty()){
			return list;
		}

		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			SaleAnalysisByItemDTO saleAnalysisByItemDTO = new SaleAnalysisByItemDTO();
			saleAnalysisByItemDTO.setItemNum((Integer) object[0]);
			saleAnalysisByItemDTO.setStateCode((int)object[1]);
			saleAnalysisByItemDTO.setAmount((BigDecimal) object[2]);
			saleAnalysisByItemDTO.setPaymentMoney((BigDecimal) object[3]);
			saleAnalysisByItemDTO.setAssistAmount((BigDecimal) object[4]);
			saleAnalysisByItemDTO.setItemCount((Integer) object[5]);
			if(object[6] instanceof  Double){
				saleAnalysisByItemDTO.setDiscount(new BigDecimal((Double) object[6]));

			} else {
				saleAnalysisByItemDTO.setDiscount(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

			}
			list.add(saleAnalysisByItemDTO);
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByBrands' + #p0.getKey()")
	public List<SaleByBrandSummary> findSaleAnalysisByBrands(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByBrands(queryData);
		int size = objects.size();
		List<SaleByBrandSummary> list = new ArrayList<SaleByBrandSummary>(size);
		if(objects.isEmpty()) {
			return list;
		}
		for (int i = 0; i < size ; i++) {
			Object[] object = objects.get(i);
			SaleByBrandSummary saleByBrandSummary = new SaleByBrandSummary();
			saleByBrandSummary.setItemNum((Integer) object[0]);
			saleByBrandSummary.setStateCode((int)object[1]);
			saleByBrandSummary.setAmount((BigDecimal) object[2]);
			saleByBrandSummary.setPaymentMoney((BigDecimal) object[3]);
			saleByBrandSummary.setAssistAmount((BigDecimal) object[4]);
			saleByBrandSummary.setItemCount((Integer) object[5]);
			if(object[6] instanceof  Double){
				saleByBrandSummary.setDiscount(new BigDecimal((Double) object[6]));

			} else {
				saleByBrandSummary.setDiscount(object[6] == null?BigDecimal.ZERO:(BigDecimal)object[6]);

			}
			list.add(saleByBrandSummary);
		}
		return list;
	}

	@Override
	public List<WholesaleProfitByPosItem> findWholesaleProfitByCategory(WholesaleProfitQuery wholesaleProfitQuery) {
		return reportService.findWholesaleProfitByCategory(wholesaleProfitQuery);
	}

	@Override
	public List<WholesaleProfitByPosItemDetail> findWholesaleProfitByClientAndItem(WholesaleProfitQuery wholesaleProfitQuery) {
		return reportService.findWholesaleProfitByClientAndItem(wholesaleProfitQuery);
	}

	@Override
	public List<ItemRebatesSummary> findItemRebates(PolicyAllowPriftQuery policyAllowPriftQuery) {

		List<Object[]> objects = reportService.findItemRebates(policyAllowPriftQuery);
		int size = objects.size();
		List<ItemRebatesSummary> list = new ArrayList<ItemRebatesSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			ItemRebatesSummary itemRebatesSummary = new ItemRebatesSummary();
			itemRebatesSummary.setBranchNum((Integer) object[0]);
			itemRebatesSummary.setItemNum((Integer) object[1]);
			itemRebatesSummary.setMoney((BigDecimal) object[2]);
			itemRebatesSummary.setDiscount((BigDecimal) object[3]);
			itemRebatesSummary.setAmount((BigDecimal)object[4]);
			list.add(itemRebatesSummary);
		}
		return list;
	}

	@Override
	public List<RebatesDetailSummary> findRebatesDetail(PolicyAllowPriftQuery policyAllowPriftQuery) {

		List<Object[]> objects = reportService.findRebatesDetail(policyAllowPriftQuery);
		int size = objects.size();
		List<RebatesDetailSummary> list = new ArrayList<RebatesDetailSummary>(size);
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			RebatesDetailSummary rebatesDetailSummary = new RebatesDetailSummary();
			rebatesDetailSummary.setOrderDetailBranchNum((Integer) object[0]);
			rebatesDetailSummary.setOrderDetailBizday((String) object[1]);
			rebatesDetailSummary.setOrderNo((String) object[2]);
			rebatesDetailSummary.setItemNum((Integer) object[3]);
			rebatesDetailSummary.setOrderDetailStdPrice((BigDecimal) object[4]);
			rebatesDetailSummary.setOrderDetailPrice((BigDecimal) object[5]);
			rebatesDetailSummary.setOrderDetailAmount((BigDecimal) object[6]);
			rebatesDetailSummary.setOrderDetailPaymentMoney((BigDecimal) object[7]);
			rebatesDetailSummary.setOrderDetailDiscount((BigDecimal) object[8]);
			rebatesDetailSummary.setOrderDetailPromotionType((int) object[9]);
			rebatesDetailSummary.setOrderDetailStateCode((int) object[10]);
			list.add(rebatesDetailSummary);
		}

		return list;
	}

	@Override
	public RebatesSumSummary findRebatesSum(PolicyAllowPriftQuery policyAllowPriftQuery) {

		Object[] object = reportService.findRebatesSum(policyAllowPriftQuery);
		RebatesSumSummary rebatesSumSummary = new RebatesSumSummary();
		rebatesSumSummary.setMoney((BigDecimal) object[0]);
		rebatesSumSummary.setDiscount((BigDecimal) object[1]);
		rebatesSumSummary.setAmount((BigDecimal) object[2]);
		return rebatesSumSummary;
	}

	@Override
	public List<OutOrderCountAndMoneySummary> findOutOrderCountAndMoneyByDate(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findOutOrderCountAndMoneyByDate(systemBookCode, outBranchNum, branchNum, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<OutOrderCountAndMoneySummary> list = new ArrayList<OutOrderCountAndMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i<size;i++){
			Object[] object = objects.get(i);
			OutOrderCountAndMoneySummary outOrderCountAndMoneySummary = new OutOrderCountAndMoneySummary();
			outOrderCountAndMoneySummary.setAuditTime((String) object[0]);
			outOrderCountAndMoneySummary.setAmount((Integer) object[1]);
			outOrderCountAndMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(outOrderCountAndMoneySummary);
		}
		return list;
	}

	@Override
	public List<ReturnCountAndMoneySummary> findReturnCountAndMoneyByDate(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findReturnCountAndMoneyByDate(systemBookCode, outBranchNum, branchNum, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<ReturnCountAndMoneySummary> list = new ArrayList<ReturnCountAndMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i < size; i++){
			Object[] object = objects.get(i);
			ReturnCountAndMoneySummary returnCountAndMoneySummary = new ReturnCountAndMoneySummary();
			returnCountAndMoneySummary.setAuditTime((String) object[0]);
			returnCountAndMoneySummary.setAmount((Integer) object[1]);
			returnCountAndMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(returnCountAndMoneySummary);
		}
		return list;
	}

	@Override
	public List<WholesaleOrderCountAndMoneySummary> findWholesaleOrderCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findWholesaleOrderCountAndMoneyByDate(systemBookCode, branchNum, clientFid, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<WholesaleOrderCountAndMoneySummary> list = new ArrayList<WholesaleOrderCountAndMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0;i < size; i++){
			Object[] object = objects.get(i);
			WholesaleOrderCountAndMoneySummary wholesaleOrderCountAndMoneySummary = new WholesaleOrderCountAndMoneySummary();
			wholesaleOrderCountAndMoneySummary.setAuditTime((String) object[0]);
			wholesaleOrderCountAndMoneySummary.setAmount((Integer) object[1]);
			wholesaleOrderCountAndMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(wholesaleOrderCountAndMoneySummary);
		}
		return list;
	}

	@Override
	public List<WholesaleReturnCountAndMoneySummary> findWholesaleReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findWholesaleReturnCountAndMoneyByDate(systemBookCode, branchNum, clientFid, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<WholesaleReturnCountAndMoneySummary> list = new ArrayList<WholesaleReturnCountAndMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			WholesaleReturnCountAndMoneySummary wholesaleReturnCountAndMoneySummary = new WholesaleReturnCountAndMoneySummary();
			wholesaleReturnCountAndMoneySummary.setAuditTime((String) object[0]);
			wholesaleReturnCountAndMoneySummary.setAmount((Integer) object[1]);
			wholesaleReturnCountAndMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(wholesaleReturnCountAndMoneySummary);
		}
		return list;
	}

	@Override
	public List<PosOrderMoneyByBizDaySummary> findPosOrderMoneyByBizDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findPosOrderMoneyByBizDay(systemBookCode, branchNums, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<PosOrderMoneyByBizDaySummary> list = new ArrayList<PosOrderMoneyByBizDaySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PosOrderMoneyByBizDaySummary posOrderMoneyByBizDaySummary = new PosOrderMoneyByBizDaySummary();
			posOrderMoneyByBizDaySummary.setBizday((String) object[0]);
			posOrderMoneyByBizDaySummary.setMoney((BigDecimal) object[1]);
			posOrderMoneyByBizDaySummary.setAmount((Integer) object[2]);
			list.add(posOrderMoneyByBizDaySummary);
		}

		return list;
	}

	@Override
	public List<PurchaseReceiveCountMoneySummary> findPurchaseReceiveCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {
		List<Object[]> objects = reportService.findPurchaseReceiveCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<PurchaseReceiveCountMoneySummary> list = new ArrayList<PurchaseReceiveCountMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i<size;i++){
			Object[] object = objects.get(i);
			PurchaseReceiveCountMoneySummary purchaseReceiveCountMoneySummary = new PurchaseReceiveCountMoneySummary();
			purchaseReceiveCountMoneySummary.setAuditTime((String) object[0]);
			purchaseReceiveCountMoneySummary.setAmount((Integer) object[1]);
			purchaseReceiveCountMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(purchaseReceiveCountMoneySummary);
		}
		return list;
	}

	@Override
	public List<PurchaseReturnCountMoneySummary> findPurchaseReturnCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findPurchaseReturnCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<PurchaseReturnCountMoneySummary> list = new ArrayList<PurchaseReturnCountMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PurchaseReturnCountMoneySummary purchaseReturnCountMoneySummary = new PurchaseReturnCountMoneySummary();
			purchaseReturnCountMoneySummary.setAuditTime((String) object[0]);
			purchaseReturnCountMoneySummary.setAmount((Integer) object[1]);
			purchaseReturnCountMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(purchaseReturnCountMoneySummary);

		}
		return list;
	}

	@Override
	public List<PurchaseCountMoneySummary> findPurchaseCountAndMoneyByDate(String systemBookCode, Integer branchNum, Integer supplierNum, Date dateFrom, Date dateTo, String dateType) {

		List<Object[]> objects = reportService.findPurchaseCountAndMoneyByDate(systemBookCode, branchNum, supplierNum, dateFrom, dateTo, dateType);
		int size = objects.size();
		List<PurchaseCountMoneySummary> list = new ArrayList<PurchaseCountMoneySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PurchaseCountMoneySummary purchaseCountMoneySummary = new PurchaseCountMoneySummary();
			purchaseCountMoneySummary.setAuditTime((String) object[0]);
			purchaseCountMoneySummary.setAmount((Integer) object[1]);
			purchaseCountMoneySummary.setMoney((BigDecimal) object[2]);
			list.add(purchaseCountMoneySummary);
		}

		return list;
	}

	@Override
	public List<CardConsumeAnalysis> findCardConsumeAnalysis(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {

		List<Object[]> objects = reportService.findCardConsumeAnalysis(cardConsuemAnalysisQuery);
		BigDecimal posMoneyTotal = posOrderService.sumPosMoneyByCardConsuemAnalysisQuery(cardConsuemAnalysisQuery);//营业额
		BigDecimal consumeMoneyTotal = BigDecimal.ZERO;
		Map<String,CardConsumeAnalysis> map = new HashMap<>(16);

		for (int i = 0,len = objects.size(); i < len; i++) {
			Object[] object = objects.get(i);
			CardConsumeAnalysis card = new CardConsumeAnalysis();
			card.setRang((String) object[0]);
			if(card.getRang() == null ){
				continue;
			}
			card.setConsumeMoney(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1] );
			card.setCardUserNum((Integer) object[2]);

			if(posMoneyTotal.compareTo(BigDecimal.ZERO) == 0){
				card.setBusiRate(BigDecimal.ZERO);//营业占比
			}else{
				card.setBusiRate(card.getConsumeMoney().divide(posMoneyTotal, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100).setScale(2)));
			}
			consumeMoneyTotal = consumeMoneyTotal.add(card.getConsumeMoney());
			map.put(card.getRang(),card);
		}

		List<String> ranges = new ArrayList<>();
		BigDecimal count = BigDecimal.ZERO;
		BigDecimal moneySpace = cardConsuemAnalysisQuery.getMoneySpace();
		if(moneySpace == null || moneySpace.compareTo(BigDecimal.ZERO) == 0){
			moneySpace = BigDecimal.ONE;
		}
		int size = BigDecimal.valueOf(1000).divide(moneySpace, 0, BigDecimal.ROUND_UP).intValue() + 1;
		for (int i = 0; i <size ; i++) {
			if(i == size-1){
				ranges.add("1000");
				continue;
			}
			ranges.add(String.valueOf(count));
			count = count.add(moneySpace);
		}

		List<CardConsumeAnalysis> list = new ArrayList<>();
		for (int i = 0; i <ranges.size() ; i++) {
			String range = ranges.get(i);

			String strRange = null;
			BigDecimal intRange = new BigDecimal(range);
			StringBuilder sb = new StringBuilder();

			if(BigDecimal.valueOf(1000).subtract(intRange).compareTo(moneySpace) < 0 && intRange.compareTo(BigDecimal.valueOf(1000)) < 0) {
				strRange = sb.append(range).append("-").append(BigDecimal.valueOf(1000)).toString();
			} else if (intRange.compareTo(BigDecimal.valueOf(1000)) < 0){
				strRange = sb.append(range).append("-").append(intRange.add(moneySpace)).toString();
			}else {
				strRange = "1000-以上";
			}


			CardConsumeAnalysis data = map.get(range);
			if(data == null){
				CardConsumeAnalysis cardConsumeAnalysis = new CardConsumeAnalysis(0,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
				cardConsumeAnalysis.setRang(strRange);
				list.add(cardConsumeAnalysis);
			}else{
				data.setRang(strRange);
				if(consumeMoneyTotal.compareTo(BigDecimal.ZERO) == 0){
					data.setConsumeRate(BigDecimal.ZERO);
				}else{
					data.setConsumeRate(data.getConsumeMoney().divide(consumeMoneyTotal,4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100).setScale(2)));
				}
			}
		}
		list.addAll(map.values());
		for (int i = 0,len = list.size(); i < len ; i++) {

			CardConsumeAnalysis cardConsumeAnalysis = list.get(i);
			String rang = cardConsumeAnalysis.getRang();
			String[] split = rang.split("-");
			BigDecimal moneyFrom = new BigDecimal(split[0]);
			if(BigDecimal.valueOf(1000).compareTo(moneyFrom) == 0){
				cardConsumeAnalysis.setMoneyFrom(moneyFrom);

			}else{
				BigDecimal moneyTo = new BigDecimal(split[1]);
				cardConsumeAnalysis.setMoneyFrom(moneyFrom);
				cardConsumeAnalysis.setMoneyTo(moneyTo);
			}
		}

		if(cardConsuemAnalysisQuery.getMoneyFrom() != null){
			for (int i = list.size()-1; i >= 0 ; i--) {
				CardConsumeAnalysis cardConsumeAnalysis = list.get(i);
				//去除小于消费金额起的数据
				if(cardConsumeAnalysis.getMoneyTo() != null){//1000-以上 moneyTo 为null
					if(cardConsuemAnalysisQuery.getMoneyFrom().compareTo(cardConsumeAnalysis.getMoneyTo()) >= 0){
						list.remove(i);
					}
				}
			}
		}

		if(cardConsuemAnalysisQuery.getMoneyTo() != null){
			for (int i = list.size()-1; i >= 0 ; i--) {
				CardConsumeAnalysis cardConsumeAnalysis = list.get(i);
				//去除消费金额小于moneyTo的数据
				if(cardConsumeAnalysis.getMoneyFrom().compareTo(cardConsuemAnalysisQuery.getMoneyTo()) >= 0){
					list.remove(i);
				}
			}
		}
		Comparator<CardConsumeAnalysis> comparing = Comparator.comparing(CardConsumeAnalysis::getMoneyFrom);
		list.sort(comparing);
		return list;
	}

	@Override
	public List<CardConsumeDetailSummary> findCardConsumeAnalysisDetails(CardConsuemAnalysisQuery cardConsuemAnalysisQuery) {

		List<Object[]> objects = reportService.findCardConsumeAnalysisDetails(cardConsuemAnalysisQuery);
		int size = objects.size();
		List<CardConsumeDetailSummary> list = new ArrayList<CardConsumeDetailSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			CardConsumeDetailSummary cardConsumeDetailSummary = new CardConsumeDetailSummary();
			cardConsumeDetailSummary.setCardUserNum((Integer) object[0]);
			cardConsumeDetailSummary.setCardUserPrintedNum((String) object[1]);
			cardConsumeDetailSummary.setCardUserCustdName((String) object[2]);
			cardConsumeDetailSummary.setPaymentMoney((BigDecimal) object[3]);
			list.add(cardConsumeDetailSummary);
		}
		return list;
	}

	@Override
	public BookSummaryReport getBookSummaryReport(String systemBookCode, Date dateFrom, Date dateTo) {
		return reportService.getBookSummaryReport(systemBookCode,dateFrom,dateTo);
	}

	@Override
	public List<ExceptInventory> findExceptInventories(InventoryExceptQuery inventoryExceptQuery, boolean highExceptFlag) {
		return reportService.findExceptInventories(inventoryExceptQuery,highExceptFlag);
	}

	@Override
	public List<BranchBusinessSummary> findBranchBusinessSummaries(String systemBookCode, Date dateFrom, Date dateTo) {
		return reportService.findBranchBusinessSummaries(systemBookCode,dateFrom,dateTo);
	}

	@Override
	public BigDecimal sumTransferMoney(String systemBookCode, Integer branchNum, List<Integer> transferBranchNums, Date dateFrom, Date dateTo) {
		return reportService.sumTransferMoney(systemBookCode,branchNum,transferBranchNums,dateFrom,dateTo);
	}

	@Override
	public InventoryUnsaleSummary findInventoryUnsale(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {

		Object[] object = reportService.findInventoryUnsale(systemBookCode, branchNum, dateFrom, dateTo);
		InventoryUnsaleSummary inventoryUnsaleSummary = new InventoryUnsaleSummary();
		inventoryUnsaleSummary.setInventoryAmount((BigDecimal) object[0]);
		inventoryUnsaleSummary.setInventoryMoney((BigDecimal) object[1]);
		inventoryUnsaleSummary.setUnSaleAmount((BigDecimal) object[2]);
		inventoryUnsaleSummary.setUnSaleMoney((BigDecimal) object[3]);
		return inventoryUnsaleSummary;
	}

	@Override
	public List<PosGroupBranchRegionTypeSummary> findPosGroupByBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {

		List<Object[]> objects = reportService.findPosGroupByBranchRegionType(systemBookCode, branchNum, dateFrom, dateTo);
		int size = objects.size();
		List<PosGroupBranchRegionTypeSummary> list = new ArrayList<PosGroupBranchRegionTypeSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PosGroupBranchRegionTypeSummary posGroupBranchRegionTypeSummary = new PosGroupBranchRegionTypeSummary();
			posGroupBranchRegionTypeSummary.setRegionType((String) object[0]);
			posGroupBranchRegionTypeSummary.setMoney((BigDecimal) object[0]);
			posGroupBranchRegionTypeSummary.setAmount((Integer) object[0]);
			list.add(posGroupBranchRegionTypeSummary);
		}

		return list;
	}

	@Override
	public List<PosGroupHourAndBranchRegionTypeSummary> findPosGroupByHourAndBranchRegionType(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		List<Object[]> objects = reportService.findPosGroupByHourAndBranchRegionType(systemBookCode, branchNum, dateFrom, dateTo);
		int size = objects.size();
		List<PosGroupHourAndBranchRegionTypeSummary> list = new ArrayList<PosGroupHourAndBranchRegionTypeSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size; i++) {
			Object[] object = objects.get(i);
			PosGroupHourAndBranchRegionTypeSummary summary = new PosGroupHourAndBranchRegionTypeSummary();
			summary.setHour((int)object[0]);
			summary.setBranchType((String) object[1]);
			summary.setOrderMoney((BigDecimal) object[2]);
			summary.setOrderCount((Integer) object[3]);
			list.add(summary);
		}
		return list;
	}

	@Override
	public List<PosGroupHourSummary> findPosGroupByHour(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		List<Object[]> objects = reportService.findPosGroupByHour(systemBookCode, branchNum, dateFrom, dateTo);
		int size = objects.size();
		List<PosGroupHourSummary> list = new ArrayList<PosGroupHourSummary>(size);
		if(objects.isEmpty()){
			return null;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PosGroupHourSummary posGroupHourSummary = new PosGroupHourSummary();
			posGroupHourSummary.setOrderTime((Integer) object[0]);
			posGroupHourSummary.setMoney(object[1] == null?BigDecimal.ZERO:(BigDecimal)object[1]);
			posGroupHourSummary.setAmount(object[2] == null?0:(Integer) object[2]);
			list.add(posGroupHourSummary);
		}
		return list;
	}

	@Override
	public List<GroupByItemSummary> findSummaryGroupByItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, boolean kitFlag) {
		List<Object[]> objects = reportService.findSummaryGroupByItem(systemBookCode, branchNums, dateFrom, dateTo, itemNums, kitFlag);
		int size = objects.size();
		List<GroupByItemSummary> list = new ArrayList<GroupByItemSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			GroupByItemSummary groupByItemSummary = new GroupByItemSummary();
			groupByItemSummary.setItemNum((Integer) object[0]);
			groupByItemSummary.setMatrixNum((Integer) object[1]);
			groupByItemSummary.setGrossProfit((BigDecimal) object[2]);
			groupByItemSummary.setAmount((BigDecimal) object[3]);
			groupByItemSummary.setPaymentMoney((BigDecimal) object[4]);
			groupByItemSummary.setCost((BigDecimal) object[5]);
			list.add(groupByItemSummary);
		}
		return list;
	}

	@Override
	public List<ItemSaleQtySummary> findItemSaleQty(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, boolean includePos, boolean includeTranferOut, boolean includeWholesale) {
		List<Object[]> objects = reportService.findItemSaleQty(systemBookCode, branchNum, dateFrom, dateTo, includePos, includeTranferOut, includeWholesale);
		int size = objects.size();
		List<ItemSaleQtySummary> list = new ArrayList<ItemSaleQtySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			ItemSaleQtySummary itemSaleQtySummary  = new ItemSaleQtySummary();
			itemSaleQtySummary.setItemNum((Integer) object[0]);
			itemSaleQtySummary.setMatrixNum((Integer) object[1]);
			itemSaleQtySummary.setAmount((BigDecimal) object[2]);
			list.add(itemSaleQtySummary);
		}
		return list;
	}

	@Override
	public List<WholesaleOrderLostSummary> findWholesaleOrderLostByClientAndItem(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> clientFids, List<Integer> itemNums) {

		List<Object[]> objects = reportService.findWholesaleOrderLostByClientAndItem(systemBookCode, branchNum, dateFrom, dateTo, clientFids, itemNums);
		int size = objects.size();
		List<WholesaleOrderLostSummary> list = new ArrayList<WholesaleOrderLostSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i<size; i++){
			Object[] object = objects.get(i);
			WholesaleOrderLostSummary wholesaleOrderLostSummary = new WholesaleOrderLostSummary();
			wholesaleOrderLostSummary.setClientFid((String) object[0]);
			wholesaleOrderLostSummary.setItemNum((Integer) object[1]);
			wholesaleOrderLostSummary.setFidCount((Integer) object[2]);
			wholesaleOrderLostSummary.setMaxAuditTime((Date) object[3]);
			list.add(wholesaleOrderLostSummary);
		}
		return list;

	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleUnsalableItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType) {
		return reportService.findWholeSaleUnsalableItem(systemBookCode,branchNum,clientFid,wholesaleRateFrom,wholesaleRateTo,unWholesaleDaysFrom,unWholesaleDaysTo,unitType);
	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleProfitItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType) {
		return reportService.findWholeSaleProfitItem(systemBookCode,branchNum,clientFid,wholesaleRateFrom,wholesaleRateTo,unWholesaleDaysFrom,unWholesaleDaysTo,unitType);
	}

	@Override
	public List<WholesaleAnalysisDTO> findWholeSaleHotItem(String systemBookCode, Integer branchNum, String clientFid, Integer wholesaleRateFrom, Integer wholesaleRateTo, Integer unWholesaleDaysFrom, Integer unWholesaleDaysTo, String unitType) {
		return reportService.findWholeSaleHotItem(systemBookCode,branchNum,clientFid,wholesaleRateFrom,wholesaleRateTo,unWholesaleDaysFrom,unWholesaleDaysTo,unitType);
	}

	@Override
	public List<BranchSaleQtySummary> findBranchSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		List<Object[]> objects = reportService.findBranchSaleQty(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BranchSaleQtySummary> list = new ArrayList<BranchSaleQtySummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0 ;i<size; i++){
			Object[] object = objects.get(i);
			BranchSaleQtySummary branchSaleQtySummary = new BranchSaleQtySummary();
			branchSaleQtySummary.setBranchNum((Integer) object[0]);
			branchSaleQtySummary.setPaymentMoney((BigDecimal) object[1]);
			branchSaleQtySummary.setAmount((BigDecimal) object[2]);
			list.add(branchSaleQtySummary);
		}
		return list;
	}

	@Override
	public List<BranchItemSummary> findBranchItemSaleQty(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		List<Object[]> objects = reportService.findBranchItemSaleQty(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
		int size = objects.size();
		List<BranchItemSummary> list = new ArrayList<BranchItemSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0 ;i<size;i++){
			Object[] object = objects.get(i);
			BranchItemSummary branchItemSummary = new BranchItemSummary();
			branchItemSummary.setBranchNum((Integer) object[0]);
			branchItemSummary.setItemNum((Integer) object[1]);
			branchItemSummary.setMatrixNum((Integer) object[2]);
			branchItemSummary.setPaymentMoney((BigDecimal) object[3]);
			branchItemSummary.setAmount((BigDecimal) object[4]);
			list.add(branchItemSummary);
		}
		return list;
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<CustomerAnalysisDay> findCusotmerAnalysisBranchs(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> branchNums, String saleType) {
		return reportService.findCusotmerAnalysisBranchs(systemBookCode,dateFrom,dateTo,branchNums,saleType);
	}

	@Override
	public List<WholesaleAnalysisDTO> findClientUnSaleItems(String systemBookCode, Integer branchNum, String clientFid) {
		return reportService.findClientUnSaleItems(systemBookCode,branchNum,clientFid);
	}

	@Override
	public List<NeedSaleItemDTO> findNeedSaleItemDatas(String systemBookCode, Integer branchNum, Integer storehouseNum, List<String> categoryCodes, List<Integer> itemNums, String unitType) {
		return reportService.findNeedSaleItemDatas(systemBookCode,branchNum,storehouseNum,categoryCodes,itemNums,unitType);
	}

	@Override
	@Cacheable(value = "serviceCache" )
	public List<AlipaySumDTO> findAlipaySumDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String payType) {
		return reportService.findAlipaySumDTOs(systemBookCode,branchNums,dateFrom,dateTo,payType);
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String type, String paymentType, Boolean queryAll) {

		if(dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0){
			return new ArrayList<AlipayDetailDTO>();
		}
		List<AlipayDetailDTO> list = reportService.findAlipayDetailDTOs(systemBookCode,branchNums,dateFrom,dateTo,type,paymentType,queryAll);
		return list;
	}

	@Override
	public List<CardAnalysisDTO> findCardAnalysisDTOs(CardUserQuery cardUserQuery) {
		return reportService.findCardAnalysisDTOs(cardUserQuery);
	}

	@Override
	public List<BranchProfitSummary> findProfitAnalysisBranchs(ProfitAnalysisQueryData profitAnalysisQueryData) {
        List<Object[]> objects = reportService.findProfitAnalysisBranchs(profitAnalysisQueryData);
		int size = objects.size();
		List<BranchProfitSummary> list = new ArrayList<BranchProfitSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
        for(int i = 0; i<size;i++){
            Object[] object = objects.get(i);
            BranchProfitSummary branchProfitSummary = new BranchProfitSummary();
            branchProfitSummary.setBranchNum((Integer) object[0]);
            branchProfitSummary.setProfit((BigDecimal) object[1]);
            branchProfitSummary.setPaymentMoney((BigDecimal) object[2]);
            branchProfitSummary.setCost((BigDecimal) object[3]);
            list.add(branchProfitSummary);
        }
        return list;
	}

	@Override
	public List<OrderDetailCompare> findOrderDetailCompareDatasByBranch(String systemBookCode, List<Integer> branchNums, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo) {
		return reportService.findOrderDetailCompareDatasByBranch(systemBookCode,branchNums,lastDateFrom,lastDateTo,thisDateFrom,thisDateTo);
	}

	@Override
	public List<OrderDetailCompare> findOrderDetailCompareDatasByBranchItem(String systemBookCode, List<Integer> branchNums, Date lastDateFrom, Date lastDateTo, Date thisDateFrom, Date thisDateTo, List<Integer> itemNums, List<String> categoryCodes) {
		ReportUtil<OrderDetailCompare> reportUtil = new ReportUtil<>(OrderDetailCompare.class);
		List<Object[]> objects = reportService.findCustomerAnalysisBranchItem(systemBookCode, thisDateFrom, thisDateTo,
				branchNums, itemNums);

		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			OrderDetailCompare data = reportUtil.getInstance();
			data.setBranchNum((Integer) object[0]);
			data.setItemNum((Integer) object[1]);
			data.setItemMatrixNum((Integer) object[2]);
			data.setThisSellMoney(AppUtil.getValue(object[4], BigDecimal.class));
			data.setThisSellNum(AppUtil.getValue(object[3], BigDecimal.class));
			data.setThisSaleProfit(AppUtil.getValue(object[5], BigDecimal.class));
			if (data.getThisSellNum().compareTo(BigDecimal.ZERO) > 0) {
				data.setThisAvgPrice(data.getThisSellMoney().divide(data.getThisSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
			reportUtil.add(data);
		}
		objects = reportService.findCustomerAnalysisBranchItem(systemBookCode, lastDateFrom, lastDateTo, branchNums, itemNums);

		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);

			OrderDetailCompare data = reportUtil.getInstance();
			data.setBranchNum((Integer) object[0]);
			data.setItemNum((Integer) object[1]);
			data.setItemMatrixNum((Integer) object[2]);
			data.setLastSellMoney(AppUtil.getValue(object[4], BigDecimal.class));
			data.setLastSellNum(AppUtil.getValue(object[3], BigDecimal.class));
			data.setLastSaleProfit(AppUtil.getValue(object[5], BigDecimal.class));
			if (data.getLastSellNum().compareTo(BigDecimal.ZERO) > 0) {
				data.setLastAvgPrice(data.getLastSellMoney().divide(data.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP));
			}
			reportUtil.add(data);
		}

		List<AdjustmentReason> adjustmentReasons = bookResourceRpc.findAdjustmentReasons(systemBookCode);
		List<String> reasons = new ArrayList<String>();
		for (int i = 0; i < adjustmentReasons.size(); i++) {
			AdjustmentReason adjustmentReason = adjustmentReasons.get(i);
			if (org.apache.commons.lang.StringUtils.equals(adjustmentReason.getAdjustmentInoutCategory(),
					AppConstants.ADJUSTMENT_REASON_TYPE_LOSS)) {
				reasons.add(adjustmentReason.getAdjustmentReasonName());
			}
		}
		if (reasons.size() > 0) {
			objects = adjustmentOrderService.findBranchItemSummary(systemBookCode, branchNums, thisDateFrom, thisDateTo, itemNums,
					reasons);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branchNum = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				Integer itemMatrixNum = (Integer) object[2];
				BigDecimal money = AppUtil.getValue(object[3], BigDecimal.class);
				OrderDetailCompare data = reportUtil.getInstance();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setBranchNum(branchNum);
				data.setThisAdjustMoney(money);
				reportUtil.add(data);
			}

			objects = adjustmentOrderService.findBranchItemSummary(systemBookCode, branchNums, lastDateFrom, lastDateTo, itemNums,
					reasons);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer branchNum = (Integer) object[0];
				Integer itemNum = (Integer) object[1];
				Integer itemMatrixNum = (Integer) object[2];
				BigDecimal money = AppUtil.getValue(object[3], BigDecimal.class);
				OrderDetailCompare data = reportUtil.getInstance();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setBranchNum(branchNum);
				data.setLastAdjustMoney(money);
				reportUtil.add(data);
			}

		}

		List<Object[]> outThisObjects = transferOutOrderService.findBranchItemMatrixSummary(systemBookCode,
				AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM, branchNums, thisDateFrom, thisDateTo, itemNums);
		List<Object[]> outLastObjects = transferOutOrderService.findBranchItemMatrixSummary(systemBookCode,
				AppConstants.REQUEST_ORDER_OUT_BRANCH_NUM, branchNums, lastDateFrom, lastDateTo, itemNums);
		for (int i = 0; i < outThisObjects.size(); i++) {
			Object[] object = outThisObjects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer itemMatrixNum = (Integer) object[2];
			BigDecimal money = AppUtil.getValue(object[4], BigDecimal.class);
			OrderDetailCompare data = reportUtil.getInstance();
			data.setBranchNum(branchNum);
			data.setItemNum(itemNum);
			data.setItemMatrixNum(itemMatrixNum);
			data.setThisInMoney(money);
			reportUtil.add(data);
		}

		for (int i = 0; i < outLastObjects.size(); i++) {
			Object[] object = outLastObjects.get(i);
			Integer branchNum = (Integer) object[0];
			Integer itemNum = (Integer) object[1];
			Integer itemMatrixNum = (Integer) object[2];
			BigDecimal money = AppUtil.getValue(object[4], BigDecimal.class);
			OrderDetailCompare data = reportUtil.getInstance();
			data.setBranchNum(branchNum);
			data.setItemNum(itemNum);
			data.setItemMatrixNum(itemMatrixNum);
			data.setLastInMoney(money);
			reportUtil.add(data);
		}
		List<OrderDetailCompare> list = reportUtil.toList();
		if(list.isEmpty()){
			return list;
		}

		BigDecimal hundred = BigDecimal.valueOf(100);
		List<Branch> branchs = branchService.findInCache(systemBookCode);
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
		for (int i = list.size()-1; i >= 0; i--) {
			OrderDetailCompare orderDetailCompare = list.get(i);
			PosItem posItem = AppUtil.getPosItem(orderDetailCompare.getItemNum(), posItems);
			if(posItem == null) {
				list.remove(i);
				continue;
			}
			orderDetailCompare.setCategoryCode(posItem.getItemCategoryCode());
			orderDetailCompare.setCategoryName(posItem.getItemCategory());
			orderDetailCompare.setOrderCode(posItem.getItemCode());
			orderDetailCompare.setOrderName(posItem.getItemName());
			orderDetailCompare.setSpec(posItem.getItemSpec());
			orderDetailCompare.setUnit(posItem.getItemUnit());
			if(categoryCodes != null && categoryCodes.size() > 0) {
				if(!categoryCodes.contains(orderDetailCompare.getCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			Branch branch = AppUtil.getBranch(branchs, orderDetailCompare.getBranchNum());
			if (branch != null) {
				orderDetailCompare.setBranchName(branch.getBranchName());
				orderDetailCompare.setBranchRegionNum(branch.getBranchRegionNum());
			}
			if (orderDetailCompare.getItemMatrixNum() != 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(),
						orderDetailCompare.getItemNum(), orderDetailCompare.getItemMatrixNum());
				if (itemMatrix != null) {
					orderDetailCompare.setOrderName(orderDetailCompare.getOrderName().concat(
							AppUtil.getMatrixName(itemMatrix)));
				}
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
				orderDetailCompare.setProfitMoneyGrowthRate(orderDetailCompare.getProfitMoneyGrowthRateValue() + "%");
			}
			orderDetailCompare.setProfitGrowthRateValue(orderDetailCompare.getThisProfitRate()
					.subtract(orderDetailCompare.getLastProfitRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
			orderDetailCompare.setProfitGrowthRate(orderDetailCompare.getProfitGrowthRateValue() + "%");

			orderDetailCompare.setSaleProfitGrowthRate("100%");
			orderDetailCompare.setSaleProfitGrowthRateValue(hundred);
			BigDecimal subSaleProfit = orderDetailCompare.getThisSaleProfit().subtract(
					orderDetailCompare.getLastSaleProfit());
			if (orderDetailCompare.getLastSaleProfit().compareTo(BigDecimal.ZERO) > 0) {
				orderDetailCompare.setSaleProfitGrowthRateValue(subSaleProfit
						.divide(orderDetailCompare.getLastSaleProfit(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setSaleProfitGrowthRate(orderDetailCompare.getSaleProfitGrowthRateValue()
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
						.divide(orderDetailCompare.getLastSellNum(), 4, BigDecimal.ROUND_HALF_UP).multiply(hundred)
						.setScale(2));
				orderDetailCompare.setNumGrowthRate(orderDetailCompare.getNumGrowthRateValue()
						+ "%");

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
	public List<TransferGoal> findTransferSaleGoalByDate(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return reportService.findTransferSaleGoalByDate(systemBookCode,centerBranchNum,branchNums,dateFrom,dateTo,dateType);
	}

	@Override
	public List<ElecTicketDTO> findElecTicketDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {
		return reportService.findElecTicketDTOs(elecTicketQueryDTO);
	}

	@Override
	public List<WholesaleProfitByClient> findWholesaleProfitBySupplier(WholesaleProfitQuery wholesaleProfitQuery) {
		return reportService.findWholesaleProfitBySupplier(wholesaleProfitQuery);
	}

	@Override
	public List<TicketUseAnalysisDTO> findTicketUseAnalysisDTOs(ElecTicketQueryDTO elecTicketQueryDTO) {
		return reportService.findTicketUseAnalysisDTOs(elecTicketQueryDTO);
	}

	@Override
	public List<PackageSumDTO> findPackageSum(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findPackageSum(systemBookCode,centerBranchNum,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<IntChart> findItemRelatedItemRanks(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer itemNum, Integer selectCount) {
		List<IntChart> list = reportService.findItemRelatedItemRanks(systemBookCode,branchNums,dateFrom,dateTo,itemNum,selectCount);
		if(list.isEmpty()){
			return list;
		}
		List<Integer> itemNums = new ArrayList<Integer>();
		for(IntChart intChart : list){
			itemNums.add(intChart.getItemNum());
		}
		
		PosItemQuery posItemQuery = new PosItemQuery();
		posItemQuery.setSystemBookCode(systemBookCode);
		posItemQuery.setQueryAll(true);
		posItemQuery.setItemNums(itemNums);
		posItemQuery.setPaging(false);
		posItemQuery.setQueryProperties(new ArrayList<String>(2));
		posItemQuery.getQueryProperties().add("itemNum");
		posItemQuery.getQueryProperties().add("itemName");
		
		List<PosItem> posItems = posItemService.findByPosItemQuery(posItemQuery, null, null, 0, 0);
		for(IntChart intChart : list){
			PosItem posItem = AppUtil.getPosItem(intChart.getItemNum(), posItems);
			intChart.setName(posItem.getItemName());
		}
		return list;
	}

	@Override
	public List<CarriageCostDTO> findCarriageCosts(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> transferLineNums) {
		return reportService.findCarriageCosts(systemBookCode,branchNum,dateFrom,dateTo,transferLineNums);
	}

	@Override
	public List<CenterBoxReportDTO> findCenterBoxReportDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		return reportService.findCenterBoxReportDTOs(systemBookCode,branchNum,dateFrom,dateTo);
	}

	@Override
	public Integer countByClientOverDue(String systemBookCode, Integer branchNum) {
		return reportService.countByClientOverDue(systemBookCode,branchNum);
	}

	@Override
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByBranchBizday' + #p0.getKey()")
	public List<BranchBizSaleSummary> findSaleAnalysisByBranchBizday(SaleAnalysisQueryData saleAnalysisQueryData) {
		List<Object[]> objects = reportService.findSaleAnalysisByBranchBizday(saleAnalysisQueryData);
		int size = objects.size();
		List<BranchBizSaleSummary> list = new ArrayList<BranchBizSaleSummary>(size);
		if (objects.isEmpty()) {
			return list;
		}
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			BranchBizSaleSummary branchBizSaleSummary = new BranchBizSaleSummary();
			branchBizSaleSummary.setBranchNum((Integer) object[0]);
			branchBizSaleSummary.setBizday((String) object[1]);
			branchBizSaleSummary.setStateCode((int) object[2]);
			branchBizSaleSummary.setAmount((BigDecimal) object[3]);
			branchBizSaleSummary.setPaymentMoney((BigDecimal) object[4]);
			branchBizSaleSummary.setAssistAmount((BigDecimal) object[5]);
			branchBizSaleSummary.setItemNumAmount((Integer) object[6]);
			list.add(branchBizSaleSummary);
		}
		return list;
	}

	@Override
	public List<CardReportDTO> findCardReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return reportService.findCardReportByBranch(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<CardReportDTO> findCardReportByDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return reportService.findCardReportByDay(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<BranchCategoryTransSaleAnalyseDTO> findBranchCategoryTransSaleAnalyseDTOs(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date saleDate, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return reportService.findBranchCategoryTransSaleAnalyseDTOs(systemBookCode,centerBranchNum,branchNums,saleDate,dateFrom,dateTo,categoryCodes,itemNums);
	}

	@Override
	public List<BranchCategoryAnalyseDTO> findBranchCategoryAnalyseDTOs(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo,List<String> categoryCodes) {
		return reportService.findBranchCategoryAnalyseDTOs(systemBookCode,centerBranchNum,branchNums,dateFrom,dateTo,categoryCodes);
	}

	@Override
	public List<Object[]> findNeedSaleItemRecords(String bookCode, Integer branchNum) {
		return reportService.findNeedSaleItemRecords(bookCode,branchNum);
	}

	@Override
	public List<PosItemPriceBandDTO> findPosItemPriceBandDTOs(PosItemPriceBandQuery posItemPriceBandQuery) {
		return reportService.findPosItemPriceBandDTOs(posItemPriceBandQuery);
	}

	@Override
	public List<PickerPerformanceDTO> findPickerPerformanceDTOs(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> pickers) {
		return reportService.findPickerPerformanceDTOs(systemBookCode,branchNums,dateFrom,dateTo,pickers);
	}

	@Override
	public List<PackageSumDTO> findPackageSumByBranch(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findPackageSumByBranch(systemBookCode,centerBranchNum,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<PackageSumDTO> findPackageSumByPrice(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findPackageSumByPrice(systemBookCode,centerBranchNum,branchNums,dateFrom,dateTo);
	}

	@Override
	public CardAnalysisSummaryDTO getCardAnalysisSummaryDTO(CardUserQuery cardUserQuery) {
		CardAnalysisSummaryDTO cardAnalysisSummaryDTO = reportService.getCardAnalysisSummaryDTO(cardUserQuery);
		Gson gson = new Gson();
		String json = gson.toJson(cardAnalysisSummaryDTO);
		logger.info("会员消费指标分析 :  "+json);
		return cardAnalysisSummaryDTO;
	}

	@Override
	public List<BranchCustomerSummary> findCustomerAnalysisBranch(String systemBookCode, Date dtFrom, Date dtTo, List<Integer> branchNums, String saleType) {
		List<Object[]> objects = reportService.findCustomerAnalysisBranch(systemBookCode, dtFrom, dtTo, branchNums, saleType);
		int size = objects.size();
		List<BranchCustomerSummary> list = new ArrayList<BranchCustomerSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for(int i = 0; i<size;i++){
			Object[] object = objects.get(i);
			BranchCustomerSummary branchCustomerSummary = new BranchCustomerSummary();
			branchCustomerSummary.setBranchNum((Integer) object[0]);
			branchCustomerSummary.setPaymentMoney((BigDecimal) object[1]);
			Long orderCount = object[2] == null ? 0L : (Long) object[2];
			branchCustomerSummary.setOrderNoCount(orderCount.intValue());
			branchCustomerSummary.setConponMoney((BigDecimal) object[3]);
			branchCustomerSummary.setMgrDiscount((BigDecimal) object[4]);
			branchCustomerSummary.setGrossProfit((BigDecimal) object[5]);
			BigDecimal itemCount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			branchCustomerSummary.setItemCount(itemCount.intValue());
			branchCustomerSummary.setUserAmount((Integer) object[7]);
			branchCustomerSummary.setUserMoney((BigDecimal) object[8]);
			BigDecimal number = object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9];
			branchCustomerSummary.setValidOrderNo(number.intValue());
			list.add(branchCustomerSummary);
		}
		return list;
	}

	@Override
	public List<ShipOrderBranchDTO> findShipOrderBranch(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, Date itemDateFrom, Date itemDateTo, List<String> categoryCodes, List<Integer> exceptItemNums, BigDecimal minMoney) {
		return reportService.findShipOrderBranch(systemBookCode,outBranchNum,branchNums,dateFrom,dateTo,itemDateFrom,itemDateTo,categoryCodes,exceptItemNums,minMoney);
	}

	@Override
	public List<ShipOrderBranchDetailDTO> findShipOrderBranchDetail(String systemBookCode, Integer outBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes) {
		return reportService.findShipOrderBranchDetail(systemBookCode,outBranchNum,branchNum,dateFrom,dateTo,itemNums,categoryCodes);
	}

	@Override
	public List<ShipOrderBranchDetailDTO> findShipOrderBranchNewItem(String systemBookCode, Integer outBranchNum, Date itemDateFrom, Date itemDateTo, List<String> categoryCodes, List<Integer> exceptItemNums) {
		return reportService.findShipOrderBranchNewItem(systemBookCode,outBranchNum,itemDateFrom,itemDateTo,categoryCodes,exceptItemNums);
	}

	@Override
	public List<PurchaseAndTransferDTO> findPurchaseAndTransferDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums, String categoryCodes, String unitType) {
		return reportService.findPurchaseAndTransferDTOs(systemBookCode,branchNum,dateFrom,dateTo,employees,itemNums,categoryCodes,unitType);
	}

	@Override
	public List<PurchaseAndTransferDetailDTO> findPurchaseAndTransferDetailDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<String> employees, List<Integer> itemNums, String categoryCodes, String unitType) {
		return reportService.findPurchaseAndTransferDetailDTOs(systemBookCode,branchNum,dateFrom,dateTo,employees,itemNums,categoryCodes,unitType);
	}

	@Override
	public List<CardReportDTO> findCardReportByBranchCardType(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return reportService.findCardReportByBranchCardType(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<LnItemSummaryDTO> findLnItemSummaryDTOs(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Integer itemNum, String itemLotNumber, String itemUnit) {
		return reportService.findLnItemSummaryDTOs(systemBookCode,branchNum,dateFrom,dateTo,itemNum,itemLotNumber,itemUnit);
	}
	
	@Override
	public Object excuteSql(String systemBookCode, String sql) {
		Object object = reportService.excuteSql(systemBookCode, sql);
		
		return object;
	}

	@Override
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchPosItems(String systemBookCode, SaleAnalysisQueryData saleAnalysisQueryData) {
		return reportService.findSaleAnalysisByBranchPosItems(systemBookCode,saleAnalysisQueryData);
	}

	@Override
	public List<SaleAnalysisByPosItemDTO> findSaleAnalysisByBranchPosItems(SaleAnalysisQueryData saleAnalysisQueryData) {
		return reportService.findSaleAnalysisByBranchPosItems(saleAnalysisQueryData.getSystemBookCode() ,saleAnalysisQueryData);
	}

	private BusinessCollectionIncome getBusinessCollectionIncome(
			List<BusinessCollectionIncome> businessCollectionIncomes, String name) {
		for (int i = 0,len = businessCollectionIncomes.size(); i < len; i++) {
			BusinessCollectionIncome businessCollectionIncome = businessCollectionIncomes.get(i);
			if (businessCollectionIncome.getName().equals(name)) {
				return businessCollectionIncome;
			}
		}
		return null;
	}



	@Override
	public List<InventoryLostDTO> findInventoryLostAnalysis(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums,String unitType,
															List<String> itemDepartments, List<String> itemCategoryCodes,Boolean saleCrease,Boolean stockCrease) {

		//控制时间范围
		Calendar calendar = Calendar.getInstance();
		Date now = DateUtil.getMinOfDate(calendar.getTime());
		Date from;
		Boolean flag;
		if (dateTo.compareTo(now) >= 0) {
			dateTo = now;
			//按商品汇总数据的时间范围
			calendar.setTime(dateTo);
			from = calendar.getTime();
			flag = false;
		} else {
			calendar.setTime(dateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			from = calendar.getTime();//dateTo+1
			flag = true;
		}

		List<Integer> branchNums = new ArrayList<>();
		branchNums.add(branchNum);
		//查询商品信息
		PosItemQuery query = new PosItemQuery();
		query.setSystemBookCode(systemBookCode);
		query.setBranchNum(branchNum);
		query.setCategoryCodes(itemCategoryCodes);
		query.setItemNums(itemNums);
		query.setItemDepartments(itemDepartments);
		query.setFilterType(AppConstants.ITEM_TYPE_PURCHASE);
		if(saleCrease != null && saleCrease){//过滤(不显示)停售
			query.setSaleCrease(false);
		}
		if(stockCrease != null && stockCrease){//过滤停购
			query.setStockCrease(false);
		}
		List<PosItem> posItems = posItemService.findByPosItemQuery(query, null, null, 0, 0);


		//配送仓库库存
		List<InventoryDTO> inventory = inventoryRpc.findCenterStore(systemBookCode, branchNum, itemNums);
		//收货数量
		List<ReceiveOrderInfoDTO> receiveSummary = receiveOrderRpc.findItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
		//要货数量
		List<RequestOrderDetailDTO> requestSummary = requestOrderRpc.findItemSummary(systemBookCode, branchNum, null, dateFrom, dateTo, itemNums);
		//要货调出数量
		List<TransterOutDTO> transterOutSummary = transferOutOrderRpc.findItemSummary(systemBookCode, branchNums, null, dateFrom, dateTo, itemNums);
		//最近收货日期
		List<String> types = new ArrayList<>();
		types.add(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER);
		List<BranchItemRecoredDTO> itemAuditDate = branchItemRecoredRpc.findItemAuditDate(systemBookCode, branchNum, null, itemNums, types);


		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setSystemBookCode(systemBookCode);
		queryCondition.setBranchNum(branchNum);
		queryCondition.setDateStart(dateFrom);
		queryCondition.setDateEnd(dateTo);
		queryCondition.setItemNums(itemNums);

		//断货天数			时间范围内库存为0的天数
		List<PosItemLogSummaryDTO> itemBizFlagSummary = posItemLogRpc.findItemBizFlagSummary(queryCondition);


		List<PosItemLogSummaryDTO> dateList = new ArrayList<>();
		int diff = DateUtil.diffDay(dateFrom, dateTo);

		for (int i = 1; i < diff ; i++) {
			calendar.setTime(dateFrom);
			calendar.add(Calendar.DAY_OF_MONTH, i);
			Date tempDate = calendar.getTime();
			String tempDateStr = DateUtil.getDateShortStr(tempDate);
			for (int j = 0,len = posItems.size(); j <len ; j++) {
				PosItem posItem = posItems.get(j);
				Integer num = posItem.getItemNum();
				PosItemLogSummaryDTO dto = new PosItemLogSummaryDTO();
				dto.setItemNum(num);
				dto.setBizday(tempDateStr);
				dto.setQty(BigDecimal.ZERO);
				dto.setInoutFlag(true);//为了防止空指针
				dateList.add(dto);
			}

		}
		for (int i = 0,len = itemBizFlagSummary.size(); i <len ; i++) {
			PosItemLogSummaryDTO dto = itemBizFlagSummary.get(i);
			String bizday = dto.getBizday();
			Integer itemNum = dto.getItemNum();

			for (int j = dateList.size() - 1; j >= 0 ; j--) {
				PosItemLogSummaryDTO posItemDTO = dateList.get(j);
				if(itemNum != null && bizday != null){
					if(itemNum.equals(posItemDTO.getItemNum()) && bizday.equals(posItemDTO.getBizday())){
						dateList.remove(posItemDTO);
					}
				}
			}
		}
		itemBizFlagSummary.addAll(dateList);

		//dateTo = now  就不用按商品汇总数据了
		List<PosItemLogSummaryDTO> itemFlagSummary = new ArrayList<>();
		if (flag) {
			queryCondition.setDateStart(from);
			queryCondition.setDateEnd(now);
			itemFlagSummary = posItemLogRpc.findItemFlagSummary(queryCondition);//计算dateto到现在的商品库存出入
		}

		int size = posItems.size();
		List<InventoryLostDTO> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			PosItem posItem = posItems.get(i);
			Integer itemNum = posItem.getItemNum();

			InventoryLostDTO inventoryLostDTO = new InventoryLostDTO();
			inventoryLostDTO.setItemNum(itemNum);

			//配送仓库库存
			for (int j = 0, len = inventory.size(); j < len; j++) {
				InventoryDTO dto = inventory.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					inventoryLostDTO.setInventoryAmount(dto.getInventoryAmount() == null ? BigDecimal.ZERO : dto.getInventoryAmount());//当前库存
				}
			}
			//收货数量
			for (int j = 0, len = receiveSummary.size(); j < len; j++) {
				ReceiveOrderInfoDTO dto = receiveSummary.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					inventoryLostDTO.setReceiveAmount(dto.getReceiveQty());
				}
			}
			//要货数量
			for (int j = 0, len = requestSummary.size(); j < len; j++) {
				RequestOrderDetailDTO dto = requestSummary.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					inventoryLostDTO.setRequestAmount(dto.getRequestOrderDetailQty());
				}
			}
			//要货调出数量
			for (int j = 0, len = transterOutSummary.size(); j < len; j++) {
				TransterOutDTO dto = transterOutSummary.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					inventoryLostDTO.setRequestOutAmount(dto.getQty());
				}
			}
			//最近收货日期
			for (int j = 0, len = itemAuditDate.size(); j < len; j++) {
				BranchItemRecoredDTO dto = itemAuditDate.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					inventoryLostDTO.setMaxReceiveDay(dto.getAuditDate());
				}
			}

			BigDecimal currentAmount = inventoryLostDTO.getInventoryAmount();//当前库存   (循环迭代完毕后。currentAmount为dateTo的库存量)
			if (currentAmount == null) {
				currentAmount = BigDecimal.ZERO;
			}
			if (flag) {//dateTo = now  就不用按商品汇总数据了
				for (int j = 0, len = itemFlagSummary.size(); j < len; j++) {
					PosItemLogSummaryDTO dto = itemFlagSummary.get(j);
					if (itemNum.equals(dto.getItemNum())) {
						//得到当前库存
						if (dto.getInoutFlag()) {
							currentAmount = currentAmount.subtract(dto.getQty() == null ? BigDecimal.ZERO : dto.getQty());
						} else {
							currentAmount = currentAmount.add(dto.getQty() == null ? BigDecimal.ZERO : dto.getQty());
						}
					}
				}
			}



			itemBizFlagSummary= itemBizFlagSummary.stream() .filter(a -> a.getBizday() != null) .collect(Collectors.toList());
			Comparator<PosItemLogSummaryDTO> comparing = Comparator.comparing(PosItemLogSummaryDTO::getBizday);
			itemBizFlagSummary.sort(comparing.reversed());


			//此时的currentAmount是dateTo号的库存量
			//计算断货天数
			Map<String, InventoryLostDTO.InventoryLostDetailDTO> map = new HashMap<>();
			InventoryLostDTO.InventoryLostDetailDTO nowInventory = new InventoryLostDTO.InventoryLostDetailDTO();
			nowInventory.setBizday(DateUtil.getDateShortStr(dateTo));
			nowInventory.setInventoryAmount(currentAmount);
			map.put(nowInventory.getBizday(), nowInventory);//dateTo的库存量

			for (int j = 0, len = itemBizFlagSummary.size(); j < len; j++) {        ///进出标记
				PosItemLogSummaryDTO dto = itemBizFlagSummary.get(j);
				String bizday = dto.getBizday();
				Integer num = dto.getItemNum();

				if (itemNum.equals(num)) {
					StringBuilder sb = new StringBuilder();
					bizday = DateUtil.addDayStr(bizday, -1);//当前日期减一
					String key = sb.append(bizday).append(num).toString();
					InventoryLostDTO.InventoryLostDetailDTO detail = map.get(key);
					if (detail == null) {
						detail = new InventoryLostDTO.InventoryLostDetailDTO();
						detail.setBizday(bizday);
						map.put(key, detail);
					}
					if (dto.getInoutFlag()) {//调入
						currentAmount = currentAmount.subtract(dto.getQty() == null ? BigDecimal.ZERO : dto.getQty());
					} else {//调出
						currentAmount = currentAmount.add(dto.getQty() == null ? BigDecimal.ZERO : dto.getQty());
					}
					detail.setInventoryAmount(currentAmount);
					map.put(key, detail);
				}
			}
			inventoryLostDTO.getInventoryDetails().addAll(map.values());//时间范围内的库存量封装到集合中

			//商品基信息   （通过转换率计算件数）

			inventoryLostDTO.setItemName(posItem.getItemName());
			inventoryLostDTO.setItemSpec(posItem.getItemSpec());
			BigDecimal rate = BigDecimal.ZERO;
			if (unitType.equals(AppConstants.UNIT_BASIC)) {
				rate = BigDecimal.ONE;
				inventoryLostDTO.setItemUnit(posItem.getItemUnit());
			}
			if (unitType.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
				inventoryLostDTO.setItemUnit(posItem.getItemInventoryUnit());

			}
			if (unitType.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
				inventoryLostDTO.setItemUnit(posItem.getItemTransferUnit());

			}
			if (unitType.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
				inventoryLostDTO.setItemUnit(posItem.getItemPurchaseUnit());

			}
			if (unitType.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
				inventoryLostDTO.setItemUnit(posItem.getItemWholesaleUnit());
			}
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				inventoryLostDTO.setInventoryAmount(inventoryLostDTO.getInventoryAmount().divide(rate, 4,
						BigDecimal.ROUND_HALF_UP));
				//收货数量
				inventoryLostDTO.setReceiveAmount(inventoryLostDTO.getReceiveAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				//要货数量
				inventoryLostDTO.setRequestAmount(inventoryLostDTO.getRequestAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				//要货调出数量
				inventoryLostDTO.setRequestOutAmount(inventoryLostDTO.getRequestOutAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				// 计算detail里面的库存件数
				List<InventoryLostDTO.InventoryLostDetailDTO> dtos = inventoryLostDTO.getInventoryDetails();
				for (int j = 0; j < dtos.size(); j++) {
					InventoryLostDTO.InventoryLostDetailDTO dto = dtos.get(j);
					dto.setInventoryAmount(dto.getInventoryAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				}
			}

			//遍历集合(统计 缺货天数)
			List<InventoryLostDTO.InventoryLostDetailDTO> inventoryDetails = inventoryLostDTO.getInventoryDetails();
			if (inventoryDetails != null && inventoryDetails.size() > 0) {
				//排序（默认是升序）
				Comparator<InventoryLostDTO.InventoryLostDetailDTO> sort = Comparator.comparing(InventoryLostDTO.InventoryLostDetailDTO::getBizday);
				inventoryDetails.sort(sort);
			}

			int lostDay = 0;
			int lostCount = 0;
			BigDecimal inventoryAmount = BigDecimal.ZERO;
			for (int j = 0, len = inventoryDetails.size(); j < len; j++) {
				InventoryLostDTO.InventoryLostDetailDTO dto = inventoryDetails.get(j);
				if (dto.getInventoryAmount().compareTo(BigDecimal.ZERO) <= 0) {
					++lostDay;
					if(j > 0){
						if (inventoryAmount.compareTo(BigDecimal.ZERO) > 0) {
							++lostCount;
						}
					}
				}
				inventoryAmount = dto.getInventoryAmount();
			}
			inventoryLostDTO.setLostDay(lostDay);//缺货天数
			inventoryLostDTO.setLostCount(lostCount);//缺货次数
			list.add(inventoryLostDTO);
		}
		return list;
	}

	@Override
	public List<BranchSaleAnalysisSummary> findDaySaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {
		List<Object[]> objects = reportService.findDaySaleAnalysis(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BranchSaleAnalysisSummary> list = new ArrayList<>(size);
		////0营业额          1日均客单量      2客单价         3毛利           4毛利率
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizDay = (String) object[1];
			BigDecimal value = null;
			BigDecimal memberValue = null;
			//type = 4 时需要返回 销售额 和毛利
			BigDecimal moneyValue = null;
			BigDecimal profitValue = null;
			BigDecimal memberMoneyValue = null;
			BigDecimal memberProfitValue = null;

			if(type == 0 ){			//营业额
				value = (BigDecimal) object[2];
				memberValue = (BigDecimal) object[6];
			}else if(type == 1){	//日均客单量
				value = BigDecimal.valueOf((Integer)object[3]);
				memberValue = BigDecimal.valueOf((Integer) object[7]);
			}else if(type == 2){	//客单价
				BigDecimal money  = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal amount = object[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)object[3]);
				if(amount.compareTo(BigDecimal.ZERO) == 0){
					value = BigDecimal.ZERO;
				}else {
					value = money.divide(amount, 2, BigDecimal.ROUND_HALF_UP);
				}

				BigDecimal memberMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
				BigDecimal memberAmount = object[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[7]);

				if(memberAmount.compareTo(BigDecimal.ZERO) == 0){
					memberValue  = BigDecimal.ZERO;
				}else {
					memberValue = memberMoney.divide(memberAmount, 2, BigDecimal.ROUND_HALF_UP);
				}

			}else if(type == 3){	//毛利
				value = (BigDecimal) object[4];
				memberValue = (BigDecimal) object[8];

			}else if(type == 4){	//毛利率

				moneyValue  = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				profitValue = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				if(moneyValue.compareTo(BigDecimal.ZERO) == 0){
					value = BigDecimal.ZERO;
				}else {
					value = profitValue.divide(moneyValue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}

				memberMoneyValue = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
				memberProfitValue = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
				if(memberMoneyValue.compareTo(BigDecimal.ZERO) == 0){
					memberValue = BigDecimal.ZERO;
				}else {
					memberValue = memberProfitValue.divide(memberMoneyValue, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}
			}
			BranchSaleAnalysisSummary summary = new BranchSaleAnalysisSummary();
			summary.setBranchNum(branchNum);
			summary.setBizDate(bizDay);
			summary.setData(value);
			summary.setMemberData(memberValue);
			if(type == 4){
				summary.setSaleMoney(moneyValue);
				summary.setProfit(profitValue);
				summary.setMemberSaleMoney(memberMoneyValue);
				summary.setMemberProfit(memberProfitValue);
			}
			list.add(summary);
		}

		return list;
	}

	@Override
	public List<BranchSaleAnalysisSummary> findMonthSaleAnalysis(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type) {

		List<Object[]> objects = reportService.findMonthSaleAnalysis(systemBookCode, branchNums, dateFrom, dateTo);
		int size = objects.size();
		List<BranchSaleAnalysisSummary> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer branchNum = (Integer) object[0];
			String bizMonth = (String) object[1];
			BigDecimal value = null;
			BigDecimal memberValue = null;
			if(type == 0 ){			//营业额
				value = (BigDecimal) object[2];
				memberValue = (BigDecimal) object[6];
			}else if(type == 1){	//日均客单量
				value = BigDecimal.valueOf((Integer)object[3]);
				memberValue = BigDecimal.valueOf((Integer) object[7]);
			}else if(type == 2){	//客单价
				BigDecimal money  = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal amount = object[3] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)object[3]);
				if(amount.compareTo(BigDecimal.ZERO) == 0){
					value = BigDecimal.ZERO;
				}else {
					value = money.divide(amount, 2, BigDecimal.ROUND_HALF_UP);
				}

				BigDecimal memberMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
				BigDecimal memberAmount = object[7] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer) object[7]);

				if(memberAmount.compareTo(BigDecimal.ZERO) == 0){
					memberValue  = BigDecimal.ZERO;
				}else {
					memberValue = memberMoney.divide(memberAmount, 2, BigDecimal.ROUND_HALF_UP);
				}

			}else if(type == 3){	//毛利
				value = (BigDecimal) object[4];
				memberValue = (BigDecimal) object[8];

			}else if(type == 4){	//毛利率
				BigDecimal money  = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
				BigDecimal profit = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				if(money.compareTo(BigDecimal.ZERO) == 0){
					value = BigDecimal.ZERO;
				}else {
					value = profit.divide(money, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}

				BigDecimal memberMoney = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
				BigDecimal memberProfit = object[8] == null ? BigDecimal.ZERO : (BigDecimal) object[8];
				if(memberMoney.compareTo(BigDecimal.ZERO) == 0){
					memberValue = BigDecimal.ZERO;
				}else {
					memberValue = memberProfit.divide(memberMoney, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				}
			}
			BranchSaleAnalysisSummary summary = new BranchSaleAnalysisSummary();
			summary.setBranchNum(branchNum);
			summary.setBizDate(bizMonth);
			summary.setData(value);
			summary.setMemberData(memberValue);
			list.add(summary);
		}
		return list;

	}

	@Override
	public CustomerAnalysisHistoryPageDTO findCustomerAnalysisHistorysByPage(SaleAnalysisQueryData saleAnalysisQueryData) {

		boolean page = saleAnalysisQueryData.isPage();

		int days = DateUtil.diffDay(saleAnalysisQueryData.getDtFrom(), saleAnalysisQueryData.getDtTo());
		int branchSize = saleAnalysisQueryData.getBranchNums().size();
		Object[] object = null;
		if(days * branchSize > 1000 && page){
			object = reportService.findCustomerAnalysisHistorysCount(saleAnalysisQueryData);
		}else{
			saleAnalysisQueryData.setPage(false);
		}

		List<CustomerAnalysisHistory> result = reportService.findCustomerAnalysisHistorysByPage(saleAnalysisQueryData);
		CustomerAnalysisHistoryPageDTO pageDTO = new CustomerAnalysisHistoryPageDTO();
		pageDTO.setData(result);

		BigDecimal totalMoneySum = BigDecimal.ZERO;
		BigDecimal customerSum = BigDecimal.ZERO;
		if(saleAnalysisQueryData.isPage()){
			if(object != null){
				pageDTO.setCount((Integer) object[0]);
				pageDTO.setTotalMoneySum(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
				pageDTO.setCustomerSum(object[2] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Integer)object[2]));
			}
		}else{
			int size = result.size();	//不分页  汇总查询回来的数据
			pageDTO.setCount(size);
			for (int i = 0; i < size ; i++) {
				CustomerAnalysisHistory customerAnalysisHistory = result.get(i);
				customerSum = customerSum.add(customerAnalysisHistory.getCustomerNums() == null ?
						BigDecimal.ZERO : customerAnalysisHistory.getCustomerNums());
				totalMoneySum = totalMoneySum.add(customerAnalysisHistory.getTotalMoney() == null ?
						BigDecimal.ZERO : customerAnalysisHistory.getTotalMoney());
			}
			pageDTO.setCustomerSum(customerSum);
			pageDTO.setTotalMoneySum(totalMoneySum);

			if(page){
				List<CustomerAnalysisHistory> data = pageDTO.getData();
				int dataSize = data.size();
				List<CustomerAnalysisHistory> subData = null;
				int pageSum  = saleAnalysisQueryData.getOffset() + saleAnalysisQueryData.getLimit();
				if (dataSize >= pageSum) {
					subData = data.subList(saleAnalysisQueryData.getOffset(), pageSum);
				} else {
					subData = data.subList(saleAnalysisQueryData.getOffset(), dataSize);
				}
				pageDTO.setData(subData);
			}

		}

		if(pageDTO.getCustomerSum().compareTo(BigDecimal.ZERO) == 0){
			pageDTO.setCustomerAvgPriceSum(BigDecimal.ZERO);
		}else{
			pageDTO.setCustomerAvgPriceSum(pageDTO.getTotalMoneySum().divide(pageDTO.getCustomerSum(),2,BigDecimal.ROUND_HALF_UP));
		}

		return pageDTO;

	}

	@Override
	public ProfitByBranchAndItemSummaryPageDTOV2 findProfitAnalysisByBranchAndItemByPageV2(ProfitAnalysisQueryData profitAnalysisQueryData) {


		if(profitAnalysisQueryData.getSortField() != null){

			boolean flag = false;
			switch (profitAnalysisQueryData.getSortField()){
				case "branchName":
					flag = true;
					break;
				case "categoryCode":
					flag = true;
					break;
				case "categoryName":
					flag = true;
					break;
				case "itemCode":
					flag = true;
					break;
				case "itemName":
					flag = true;
					break;
				case "spec":
					flag = true;
					break;
				case "unit":
					flag = true;
					break;
			}
			if(flag){
				List<PosItem> posItems = posItemService.findShortItems(profitAnalysisQueryData.getSystemBookCode());
				List<ProfitByBranchAndItemSummary> profitAnalysis = findProfitAnalysisByBranchAndItem(profitAnalysisQueryData);

				BigDecimal profitSum = BigDecimal.ZERO;
				BigDecimal costSum = BigDecimal.ZERO;
				BigDecimal moneySum = BigDecimal.ZERO;
				BigDecimal amountSum = BigDecimal.ZERO;
				List<ProfitByBranchAndItemDTO> data  = new ArrayList<>();
				for (int i = 0,len = profitAnalysis.size(); i < len ; i++) {
					ProfitByBranchAndItemSummary summary = profitAnalysis.get(i);
					PosItem posItem = AppUtil.getPosItem(summary.getItemNum(),posItems);
					if(posItem != null){
						ProfitByBranchAndItemDTO dto = new ProfitByBranchAndItemDTO();
						dto.setCategoryCode(posItem.getItemCategoryCode());
						dto.setCategoryName(posItem.getItemCategory());
						dto.setItemCode(posItem.getItemCode());
						dto.setItemName(posItem.getItemName());
						dto.setSpec(posItem.getItemSpec());
						dto.setUnit(posItem.getItemUnit());
						dto.setItemType(posItem.getItemType());
						dto.setBranchNum(summary.getBranchNum());
						dto.setItemNum(summary.getItemNum());
						dto.setMatrixNum(summary.getMatrixNum());
						dto.setProfit(summary.getProfit());
						dto.setSaleCost(summary.getCost());
						dto.setSaleNums(summary.getAmount());
						dto.setSaleMoney(summary.getMoney());
						data.add(dto);
						profitSum = profitSum.add(dto.getProfit());
						costSum = costSum.add(dto.getSaleCost());
						moneySum = moneySum.add(dto.getSaleMoney());
						amountSum = amountSum.add(dto.getSaleNums());
					}
				}

				ComparatorBaseModelData<ProfitByBranchAndItemDTO> comparator = new ComparatorBaseModelData<>(profitAnalysisQueryData.getSortField(), profitAnalysisQueryData.getSortType(),ProfitByBranchAndItemDTO.class);
				Collections.sort(data, comparator);

				ProfitByBranchAndItemSummaryPageDTOV2 result = new ProfitByBranchAndItemSummaryPageDTOV2();
				int dataSize = data.size();
				result.setCount(dataSize);
				result.setData(data);
				result.setProfitSum(profitSum);
				result.setCostSum(costSum);
				result.setMoneySum(moneySum);
				result.setAmountSum(amountSum);
				if(result.getMoneySum().compareTo(BigDecimal.ZERO) == 0){
					result.setProfitRateSum(BigDecimal.ZERO);
				}else{
					result.setProfitRateSum(result.getProfitSum().divide(result.getMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
				}
				if(profitAnalysisQueryData.isPage()){
					List<ProfitByBranchAndItemDTO> subData = null;
					int pageSum =  profitAnalysisQueryData.getOffset() + profitAnalysisQueryData.getLimit();
					if(dataSize >= pageSum){
						subData = data.subList(profitAnalysisQueryData.getOffset(), pageSum);
					}else{
						subData = data.subList(profitAnalysisQueryData.getOffset(), dataSize);
					}
					result.setData(subData);
				}
				return result;
			}
		}

		boolean page = profitAnalysisQueryData.isPage();
		int days = DateUtil.diffDay(profitAnalysisQueryData.getShiftTableFrom(), profitAnalysisQueryData.getShiftTableTo());
		int branchSize = profitAnalysisQueryData.getBranchNums().size();

		Object[] pageCount = null;
		if(days * branchSize > 1000 && page){
			pageCount = reportService.findProfitAnalysisByBranchAndItemCount(profitAnalysisQueryData);
		}else{
			profitAnalysisQueryData.setPage(false);
		}
		List<Object[]> objects = reportService.findProfitAnalysisByBranchAndItemByPage(profitAnalysisQueryData);//500-1000

		int size = objects.size();
		List<ProfitByBranchAndItemDTO> list = new ArrayList<ProfitByBranchAndItemDTO>(size);
		BigDecimal profitSum = BigDecimal.ZERO;
		BigDecimal costSum = BigDecimal.ZERO;
		BigDecimal moneySum = BigDecimal.ZERO;
		BigDecimal amountSum = BigDecimal.ZERO;
		List<PosItem> posItems = posItemService.findShortItems(profitAnalysisQueryData.getSystemBookCode());
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[1];
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if(posItem != null){
				ProfitByBranchAndItemDTO profitByBranchAndItemSummary = new ProfitByBranchAndItemDTO();
				profitByBranchAndItemSummary.setBranchNum((Integer) object[0]);
				profitByBranchAndItemSummary.setItemNum(itemNum);
				profitByBranchAndItemSummary.setMatrixNum(object[2] == null ? 0 : (int) object[2]);
				profitByBranchAndItemSummary.setProfit(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
				profitByBranchAndItemSummary.setSaleNums(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
				profitByBranchAndItemSummary.setSaleMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
				profitByBranchAndItemSummary.setSaleCost(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);

				profitByBranchAndItemSummary.setCategoryCode(posItem.getItemCategoryCode());
				profitByBranchAndItemSummary.setCategoryName(posItem.getItemCategory());
				profitByBranchAndItemSummary.setItemCode(posItem.getItemCode());
				profitByBranchAndItemSummary.setItemName(posItem.getItemName());
				profitByBranchAndItemSummary.setSpec(posItem.getItemSpec());
				profitByBranchAndItemSummary.setUnit(posItem.getItemUnit());
				profitByBranchAndItemSummary.setItemType(posItem.getItemType());
				list.add(profitByBranchAndItemSummary);
				if(!profitAnalysisQueryData.isPage()){
					profitSum = profitSum.add(profitByBranchAndItemSummary.getProfit());
					amountSum = amountSum.add(profitByBranchAndItemSummary.getSaleNums());
					moneySum = moneySum.add(profitByBranchAndItemSummary.getSaleMoney());
					costSum = costSum.add(profitByBranchAndItemSummary.getSaleCost());
				}
			}

		}

		ProfitByBranchAndItemSummaryPageDTOV2 result = new ProfitByBranchAndItemSummaryPageDTOV2();
		result.setData(list);

		if(profitAnalysisQueryData.isPage()){
			if(pageCount != null ){
				result.setCount((Integer) pageCount[0]);
				result.setProfitSum(pageCount[1] == null ? BigDecimal.ZERO : (BigDecimal) pageCount[1]);
				result.setAmountSum((BigDecimal) pageCount[2] );
				result.setMoneySum(pageCount [3] == null ? BigDecimal.ZERO : (BigDecimal) pageCount [3]);
				result.setCostSum((BigDecimal) pageCount [4]);
			}
		}else{
			result.setCount(list.size());
			result.setProfitSum(profitSum);
			result.setAmountSum(amountSum);
			result.setMoneySum(moneySum);
			result.setCostSum(costSum);

			if(page){
				List<ProfitByBranchAndItemDTO> data = result.getData();
				int dataSize = data.size();
				List<ProfitByBranchAndItemDTO> subData = null;
				int pageSum =  profitAnalysisQueryData.getOffset() + profitAnalysisQueryData.getLimit();
				if(dataSize >= pageSum){
					subData = data.subList(profitAnalysisQueryData.getOffset(), pageSum);
				}else{
					subData = data.subList(profitAnalysisQueryData.getOffset(), dataSize);
				}
				result.setData(subData);
			}

		}
		if(result.getMoneySum().compareTo(BigDecimal.ZERO) == 0){
			result.setProfitRateSum(BigDecimal.ZERO);
		}else{
			result.setProfitRateSum(result.getProfitSum().divide(result.getMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		}

		return result;
	}

	@Override
	public ProfitByBranchAndItemSummaryPageDTO findProfitAnalysisByBranchAndItemByPage(ProfitAnalysisQueryData profitAnalysisQueryData) {

		if(profitAnalysisQueryData.getSortField() != null){

			switch (profitAnalysisQueryData.getSortField()){
				case "cost":
					profitAnalysisQueryData.setSortField("saleCost");
					break;
				case "amount":
					profitAnalysisQueryData.setSortField("saleNums");
					break;
				case "money":
					profitAnalysisQueryData.setSortField("saleMoney");
					break;
			}
		}

		boolean page = profitAnalysisQueryData.isPage();

		int days = DateUtil.diffDay(profitAnalysisQueryData.getShiftTableFrom(), profitAnalysisQueryData.getShiftTableTo());
		int branchSize = profitAnalysisQueryData.getBranchNums().size();

		Object[] pageCount = null;
		if(days * branchSize > 1000 && page){
			pageCount = reportService.findProfitAnalysisByBranchAndItemCount(profitAnalysisQueryData);
		}else{
			profitAnalysisQueryData.setPage(false);
		}
		List<Object[]> objects = reportService.findProfitAnalysisByBranchAndItemByPage(profitAnalysisQueryData);

		int size = objects.size();
		List<ProfitByBranchAndItemSummary> list = new ArrayList<ProfitByBranchAndItemSummary>(size);
		BigDecimal profitSum = BigDecimal.ZERO;
		BigDecimal costSum = BigDecimal.ZERO;
		BigDecimal moneySum = BigDecimal.ZERO;
		BigDecimal amountSum = BigDecimal.ZERO;
		for (int i = 0; i < size; i++) {
			Object[] object = objects.get(i);
			ProfitByBranchAndItemSummary profitByBranchAndItemSummary = new ProfitByBranchAndItemSummary();
			profitByBranchAndItemSummary.setBranchNum((Integer) object[0]);
			profitByBranchAndItemSummary.setItemNum((Integer) object[1]);
			profitByBranchAndItemSummary.setMatrixNum((object[2] == null ? 0 :(int)object[2]));
			profitByBranchAndItemSummary.setProfit(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			profitByBranchAndItemSummary.setAmount(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			profitByBranchAndItemSummary.setMoney(object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5]);
			profitByBranchAndItemSummary.setCost(object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6]);
			list.add(profitByBranchAndItemSummary);
			if(!profitAnalysisQueryData.isPage()){
				profitSum = profitSum.add(profitByBranchAndItemSummary.getProfit());
				amountSum = amountSum.add(profitByBranchAndItemSummary.getAmount());
				moneySum = moneySum.add(profitByBranchAndItemSummary.getMoney());
				costSum = costSum.add(profitByBranchAndItemSummary.getCost());
			}
		}

		ProfitByBranchAndItemSummaryPageDTO result = new ProfitByBranchAndItemSummaryPageDTO();
		result.setData(list);

		if(profitAnalysisQueryData.isPage()){
			if(pageCount != null ){
				result.setCount((Integer) pageCount[0]);
				result.setProfitSum(pageCount[1] == null ? BigDecimal.ZERO : (BigDecimal) pageCount[1]);
				result.setAmountSum((BigDecimal) pageCount[2] );
				result.setMoneySum(pageCount [3] == null ? BigDecimal.ZERO : (BigDecimal) pageCount [3]);
				result.setCostSum((BigDecimal) pageCount [4]);
			}
		}else{
			result.setCount(size);
			result.setProfitSum(profitSum);
			result.setAmountSum(amountSum);
			result.setMoneySum(moneySum);
			result.setCostSum(costSum);

			if(page){
				List<ProfitByBranchAndItemSummary> data = result.getData();
				int dataSize = data.size();
				List<ProfitByBranchAndItemSummary> subData = null;
				int pageSum =  profitAnalysisQueryData.getOffset() + profitAnalysisQueryData.getLimit();
				if(dataSize >= pageSum){
					subData = data.subList(profitAnalysisQueryData.getOffset(), pageSum);
				}else{
					subData = data.subList(profitAnalysisQueryData.getOffset(), dataSize);
				}
				result.setData(subData);
			}

		}

		if(result.getMoneySum().compareTo(BigDecimal.ZERO) == 0){
			result.setProfitRateSum(BigDecimal.ZERO);
		}else{
			result.setProfitRateSum(result.getProfitSum().divide(result.getMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		}

		return result;
	}

	@Override
	public BranchBizSummaryPageDTO findProfitAnalysisDaysByPage(ProfitAnalysisQueryData profitAnalysisQueryData) {

		if ((profitAnalysisQueryData.getBrandCodes() != null && profitAnalysisQueryData.getBrandCodes().size() > 0)
				|| (profitAnalysisQueryData.getPosItemTypeCodes() != null && profitAnalysisQueryData
				.getPosItemTypeCodes().size() > 0)) {
			profitAnalysisQueryData.setQueryPosItem(true);
		}
		if(profitAnalysisQueryData.getIsQueryCF() == null){
			profitAnalysisQueryData.setIsQueryCF(false);
		}

		boolean page = profitAnalysisQueryData.isPage();

		int days = DateUtil.diffDay(profitAnalysisQueryData.getShiftTableFrom(), profitAnalysisQueryData.getShiftTableTo());
		int branchSize = profitAnalysisQueryData.getBranchNums().size();
		Object[] pageCount = null;
		if(days * branchSize > 1000 && page){
			pageCount = reportService.findProfitAnalysisDaysCount(profitAnalysisQueryData);
		}else{
			profitAnalysisQueryData.setPage(false);
		}

		List<Object[]> objects = reportService.findProfitAnalysisDaysByPage(profitAnalysisQueryData);
		int size = objects.size();
		List<BranchBizSummary> list = new ArrayList<BranchBizSummary>(size);
		BigDecimal costSum = BigDecimal.ZERO;
		BigDecimal profitSum = BigDecimal.ZERO;
		BigDecimal moneySum = BigDecimal.ZERO;

		for (int i = 0; i < size ; i++) {
			Object[] object = objects.get(i);
			BranchBizSummary branchBizSummary = new BranchBizSummary();
			branchBizSummary.setBranchNum((Integer) object[0]);
			branchBizSummary.setBiz((String) object[1]);
			branchBizSummary.setProfit(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
			branchBizSummary.setMoney(object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3]);
			branchBizSummary.setCost(object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4]);
			list.add(branchBizSummary);
			if(!profitAnalysisQueryData.isPage()){
				profitSum = profitSum.add(branchBizSummary.getProfit());
				moneySum = moneySum.add(branchBizSummary.getMoney());
				costSum = costSum.add(branchBizSummary.getCost());
			}
		}

		BranchBizSummaryPageDTO result = new BranchBizSummaryPageDTO();
		result.setData(list);

		if(profitAnalysisQueryData.isPage()){
			if(pageCount != null){
				result.setCount((Integer) pageCount[0]);
				result.setProfitSum(pageCount[1] == null ? BigDecimal.ZERO : (BigDecimal)pageCount[1]);
				result.setMoneySum(pageCount[2] == null ? BigDecimal.ZERO : (BigDecimal)pageCount[2]);
				result.setCostSum((BigDecimal) pageCount[3]);
			}
		}else{
			result.setCount(size);
			result.setProfitSum(profitSum);
			result.setMoneySum(moneySum);
			result.setCostSum(costSum);

			if(page){
				List<BranchBizSummary> data = result.getData();
				int dataSize = data.size();
				List<BranchBizSummary> subData = null;
				int pageSum = profitAnalysisQueryData.getOffset() + profitAnalysisQueryData.getLimit();
				if(dataSize >= pageSum){
					subData = data.subList(profitAnalysisQueryData.getOffset(),pageSum);
				}else{
					subData = data.subList(profitAnalysisQueryData.getOffset(), dataSize);
				}
				result.setData(subData);
			}
		}

		if(result.getMoneySum().compareTo(BigDecimal.ZERO) == 0){
			result.setProfitRateSum(BigDecimal.ZERO);
		}else{
			result.setProfitRateSum(result.getProfitSum().divide(result.getMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		}

		return result;
	}

	@Override
	public SaleAnalysisBranchItemPageSummary findSaleAnalysisByBranchPosItemsByPage(SaleAnalysisQueryData queryData) {

		if(queryData.getSortField() != null){
			boolean flag = false;
			switch (queryData.getSortField()){
				case "categoryName":
					flag = true;
					break;
				case "categoryCode":
					flag = true;
					break;
				case "itemName":
					flag = true;
					break;
				case "itemCode":
					flag = true;
					break;
				case "spec":
					flag = true;
					break;
				case "unit":
					flag = true;
					break;
			}
			if (flag) {
				List<SaleAnalysisByPosItemDTO> data = reportService.findSaleAnalysisByBranchPosItems(queryData.getSystemBookCode(), queryData);
				ComparatorBaseModelData<SaleAnalysisByPosItemDTO> comparator = new ComparatorBaseModelData(queryData.getSortField(), queryData.getSortType(),SaleAnalysisByPosItemDTO.class);
				Collections.sort(data, comparator);

				SaleAnalysisBranchItemPageSummary result = new SaleAnalysisBranchItemPageSummary();
				int dataSize = data.size();
				result.setData(data);
				result.setCount(dataSize);

				BigDecimal totalNumSum = BigDecimal.ZERO;
				BigDecimal returnNumSum = BigDecimal.ZERO;
				BigDecimal presentNumSum = BigDecimal.ZERO;
				BigDecimal saleNumSum = BigDecimal.ZERO;
				BigDecimal totalMoneySum = BigDecimal.ZERO;
				BigDecimal returnMoneySum = BigDecimal.ZERO;
				BigDecimal presentMoneySum = BigDecimal.ZERO;
				BigDecimal saleMoneySum = BigDecimal.ZERO;
				BigDecimal countTotalSum = BigDecimal.ZERO;
				BigDecimal saleAssistSum = BigDecimal.ZERO;
				BigDecimal returnAssistSum = BigDecimal.ZERO;
				BigDecimal presentAssistSum = BigDecimal.ZERO;
				BigDecimal itemDiscount = BigDecimal.ZERO;

				for (int i = 0;i < dataSize ; i++) {
					SaleAnalysisByPosItemDTO dto = data.get(i);
					totalNumSum = totalNumSum.add(dto.getTotalNum());
					returnNumSum = returnNumSum.add(dto.getReturnNum());
					presentNumSum = presentNumSum.add(dto.getPresentNum());
					saleNumSum = saleNumSum.add(dto.getSaleNum());
					totalMoneySum = totalMoneySum.add(dto.getTotalMoney());
					returnMoneySum = returnMoneySum.add(dto.getReturnMoney());
					presentMoneySum = presentMoneySum.add(dto.getPresentMoney());
					saleMoneySum = saleMoneySum.add(dto.getSaleMoney());
					countTotalSum = countTotalSum.add(dto.getCountTotal());
					saleAssistSum = saleAssistSum.add(dto.getSaleAssist());
					returnAssistSum = returnAssistSum.add(dto.getReturnAssist());
					presentAssistSum = presentAssistSum.add(dto.getPresentAssist());
					itemDiscount = itemDiscount.add(dto.getItemDiscount());
				}

				result.setTotalNumSum(totalNumSum);
				result.setReturnNumSum(returnNumSum);
				result.setPresentNumSum(presentNumSum);
				result.setSaleNumSum(saleNumSum);
				result.setTotalMoneySum(totalMoneySum);
				result.setReturnMoneySum(returnMoneySum);
				result.setPresentMoneySum(presentMoneySum);
				result.setSaleMoneySum(saleMoneySum);
				result.setCountTotalSum(countTotalSum);
				result.setSaleAssistSum(saleAssistSum);
				result.setReturnAssistSum(returnAssistSum);
				result.setPresentAssistSum(presentAssistSum);
				result.setItemDiscount(itemDiscount);

				if (queryData.isPage()) {
					List<SaleAnalysisByPosItemDTO> subData = null;
					int pageSum = queryData.getOffset() + queryData.getLimit();
					if (dataSize >= pageSum) {
						subData = data.subList(queryData.getOffset(), pageSum);
					} else {
						subData = data.subList(queryData.getOffset(), dataSize);
					}
					result.setData(subData);
				}
				return result;
			}
		}


		List<SaleAnalysisByPosItemDTO> data = reportService.findSaleAnalysisByBranchPosItemsByPage(queryData);
		SaleAnalysisBranchItemPageSummary result = new SaleAnalysisBranchItemPageSummary();
		result.setData(data);

		Object[] count = reportService.findSaleAnalysisByBranchPosItemsCount(queryData);
		if(count != null){
			result.setCount((Integer) count[0]);

			result.setTotalNumSum((BigDecimal) count[1]);
			result.setReturnNumSum((BigDecimal) count[2]);
			result.setPresentNumSum((BigDecimal) count[3]);
			result.setSaleNumSum((BigDecimal) count[4]);

			result.setTotalMoneySum((BigDecimal) count[5]);
			result.setReturnMoneySum((BigDecimal) count[6]);
			result.setPresentMoneySum((BigDecimal) count[7]);
			result.setSaleMoneySum((BigDecimal) count[8]);

			result.setCountTotalSum(BigDecimal.valueOf((Integer) count[9]));

			result.setSaleAssistSum((BigDecimal) count[10]);
			result.setReturnAssistSum((BigDecimal) count[10]);
			result.setPresentAssistSum((BigDecimal) count[10]);

			BigDecimal discount;
			if (count[11] instanceof BigDecimal) {
				discount = count[11] == null ? BigDecimal.ZERO : (BigDecimal) count[11];

			} else if (count[11] instanceof Double) {
				discount = count[11] == null ? BigDecimal.ZERO : BigDecimal.valueOf((Double) count[11]);
			} else {
				discount = BigDecimal.ZERO;
			}
			result.setItemDiscount(discount);
		}

		return result;
	}

	@Override
	public RetailDetailPageSummary findRetailDetailsByPage(RetailDetailQueryData retailDetailQueryData) {


		if(retailDetailQueryData.getSortField() != null){
			boolean flag = false;
			switch (retailDetailQueryData.getSortField()){
				case "posItemCode":
					flag = true;
					break;
				case "posItemName":
					flag = true;
					break;
				case "posItemSpec":
					flag = true;
					break;
				case "itemBarCode":
					flag = true;
					break;
				case "itemUnit":
					flag = true;
					break;
			}
			if(flag){
				List<RetailDetail> data = reportService.findRetailDetails(retailDetailQueryData,false);
				ComparatorBaseModelData<RetailDetail> comparator = new ComparatorBaseModelData(retailDetailQueryData.getSortField(), retailDetailQueryData.getSortType(),RetailDetail.class);
				Collections.sort(data,comparator);

				RetailDetailPageSummary result = new RetailDetailPageSummary();
				int dataSize = data.size();
				result.setCount(dataSize);
				result.setData(data);
				BigDecimal amountSum = BigDecimal.ZERO;
				BigDecimal saleMoneySum = BigDecimal.ZERO;
				BigDecimal saleCostSum = BigDecimal.ZERO;
				BigDecimal saleProfitSum = BigDecimal.ZERO;
				BigDecimal discountMoneySum = BigDecimal.ZERO;
				BigDecimal saleCommissionSum = BigDecimal.ZERO;
				for (int i = 0; i < dataSize ; i++) {
					RetailDetail retailDetail = data.get(i);
					amountSum = amountSum.add(retailDetail.getAmount() == null ? BigDecimal.ZERO : retailDetail.getAmount());
					saleMoneySum = saleMoneySum.add(retailDetail.getSaleMoney() == null ? BigDecimal.ZERO : retailDetail.getSaleMoney());
					saleCostSum = saleCostSum.add(retailDetail.getSaleCost() == null ? BigDecimal.ZERO : retailDetail.getSaleCost());
					saleProfitSum = saleProfitSum.add(retailDetail.getSaleProfit() == null ? BigDecimal.ZERO : retailDetail.getSaleProfit());
					discountMoneySum = discountMoneySum.add(retailDetail.getDiscountMoney() == null ? BigDecimal.ZERO : retailDetail.getDiscountMoney());
					saleCommissionSum =saleCommissionSum.add(retailDetail.getSaleCommission() == null ? BigDecimal.ZERO : retailDetail.getSaleCommission());
				}
				result.setAmountSum(amountSum);
				result.setSaleMoneySum(saleMoneySum);
				result.setSaleCostSum(saleCostSum);
				result.setSaleProfitSum(saleProfitSum);
				result.setDiscountMoneySum(discountMoneySum);
				result.setSaleCommissionSum(saleCommissionSum);
				if(result.getSaleMoneySum().compareTo(BigDecimal.ZERO) == 0){
					result.setSaleProfitRateSum(BigDecimal.ZERO);
				}else{
					result.setSaleProfitRateSum(result.getSaleProfitSum().divide(result.getSaleMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
				}

				if(retailDetailQueryData.isPage()){
					List<RetailDetail> subData = null;
					int pageSum = retailDetailQueryData.getLimit() + retailDetailQueryData.getOffset();
					if (dataSize >= pageSum) {
						subData = data.subList(retailDetailQueryData.getOffset(), pageSum);
					} else {
						subData = data.subList(retailDetailQueryData.getOffset(), dataSize);
					}
					result.setData(subData);
				}
				return result;
			}
		}

		List<RetailDetail> data = reportService.findRetailDetailsByPage(retailDetailQueryData, false);
		Object[] count = reportService.findRetailDetailsCount(retailDetailQueryData, false);
		RetailDetailPageSummary result = new RetailDetailPageSummary();
		result.setData(data);
		if(count != null){
			result.setCount((Integer) count[0]);
			result.setAmountSum((BigDecimal) count[1]);
			result.setSaleMoneySum(count[2] == null ? BigDecimal.ZERO : (BigDecimal)count[2]);
			result.setDiscountMoneySum((BigDecimal) count[3]);
			result.setSaleCommissionSum((BigDecimal) count[4]);
			result.setSaleProfitSum(count[5] == null ? BigDecimal.ZERO : (BigDecimal) count[5]);
			result.setSaleCostSum((BigDecimal) count[6]);
			if(result.getSaleMoneySum().compareTo(BigDecimal.ZERO) == 0){
				result.setSaleProfitRateSum(BigDecimal.ZERO);
			}else{
				result.setSaleProfitRateSum(result.getSaleProfitSum().divide(result.getSaleMoneySum(),4,BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}

		}
		return result;
	}

	@Override
	public List<PurchaseCycleSummary> findPurchaseCycleByBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums) {


		//出入库数量和当前库存总数取配送单位的数量
		//基本数量  除以   商品档案的转换率    就是配送单位的数量

		// 入库金额   入库数量    采购员
		List<BizPurchaseDTO> purchaseByBiz = receiveOrderRpc.findPurchaseByBiz(systemBookCode, dateFrom, dateTo, itemNums);
		//调出单
		List<TransferOutMoneyAndAmountDTO> transferOutSum = transferOutOrderRpc.findMoneyAndAmountByBiz(systemBookCode, dateFrom, dateTo,itemNums);

		//批发数量
		List<WholesaleAmountAndMoneyDTO> wholesaleSum = wholesaleOrderRpc.findAmountAndMoneyByBiz(systemBookCode, dateFrom, dateTo, itemNums);
		//库存  当前库存
        List<Inventory> inventories = inventoryService.findByItemAndBranch(systemBookCode, null, itemNums, null);
		PosItemQuery query = new PosItemQuery();
		query.setSystemBookCode(systemBookCode);
		query.setItemNums(itemNums);
		query.setPaging(false);
		List<PosItemDTO> posItemDTOS = posItemRpc.findByPosItemQuery(query,null,null,0,0);




		BigDecimal inventoryAmount = BigDecimal.ZERO;
        BigDecimal inventoryMoney = BigDecimal.ZERO;
        for (int i = 0,len = inventories.size(); i < len ; i++) {
            Inventory inventory = inventories.get(i);
			for (int j = 0,size = posItemDTOS.size(); j < size; j++) {
				PosItemDTO posItemDTO = posItemDTOS.get(j);
				if(inventory.getItemNum().equals(posItemDTO.getItemNum())){
					BigDecimal basicAmount = inventory.getInventoryAmount() == null ? BigDecimal.ZERO : inventory.getInventoryAmount();
					BigDecimal itemTransferRate = posItemDTO.getItemTransferRate();
					if(itemTransferRate != null && itemTransferRate.compareTo(BigDecimal.ZERO) != 0 ){
						BigDecimal transferQty = basicAmount.divide(itemTransferRate,4,BigDecimal.ROUND_HALF_UP);
						inventoryAmount = inventoryAmount.add(transferQty);
						break;
					}
				}

			}
			inventoryMoney = inventoryMoney.add(inventory.getInventoryMoney() == null ? BigDecimal.ZERO :inventory.getInventoryMoney());
        }

        List<PurchaseCycleSummary> result = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int diffDay = DateUtil.diffDayV2(dateFrom, dateTo)+1;
		for (int i = 0; i < diffDay; i++) {
            calendar.setTime(dateFrom);
            calendar.add(Calendar.DAY_OF_MONTH,i);
            PurchaseCycleSummary summary = new PurchaseCycleSummary();
            summary.setBizDay(DateUtil.getDateShortStr(calendar.getTime()));
            result.add(summary);
        }

        for (int i = 0,len = result.size(); i < len ; i++) {
            PurchaseCycleSummary summary = result.get(i);
            String bizDay = summary.getBizDay();
			summary.setCurrentInventoryQty(inventoryAmount);
			summary.setCurrentInventoryMoney(inventoryMoney);

            for (int j = 0,size = purchaseByBiz.size(); j <size ; j++) {
                BizPurchaseDTO purchaseDTO = purchaseByBiz.get(j);
                if(bizDay.equals(purchaseDTO.getBizday())){
                    summary.setActualInMoney(purchaseDTO.getTotalMoney());
                    summary.setInQty(purchaseDTO.getQty());
                }
            }

            for (int j = 0,size = transferOutSum.size(); j < size ; j++) {
                TransferOutMoneyAndAmountDTO transferOut = transferOutSum.get(j);
                if(bizDay.equals(transferOut.getBiz())){
                    summary.setOutTotalMoney(transferOut.getOutMoney());
                    summary.setOutQty(transferOut.getOutQty());
                }
            }

            for (int j = 0,size = wholesaleSum.size(); j < size ; j++) {
				WholesaleAmountAndMoneyDTO dto = wholesaleSum.get(j);
				if(bizDay.equals(dto.getBiz())){
					summary.setOutTotalMoney(summary.getOutTotalMoney().add(dto.getMoney()));
				}
            }
        }

        return result;
	}

	@Override
	public List<TransferItemDetailSummary> findTransferItemTop(String systemBookCode, Integer centerBranchNum ,List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> itemCategoryCodes,String sortField) {

		List<PosItem> posItems = posItemService.find(systemBookCode, itemCategoryCodes, null, null);
		int size = posItems.size();
		List<Integer> itemNums = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			PosItem posItem = posItems.get(i);
			itemNums.add(posItem.getItemNum());
		}

		//查询总仓
		List<Storehouse> storehouses = storehouseService.findByBranch(systemBookCode, centerBranchNum);
		int stores = storehouses.size();
		List<Integer> storehouseNums = new ArrayList<>(stores);
		for (int i = 0; i < stores; i++) {
			Storehouse storehouse = storehouses.get(i);
			if(storehouse.getStorehouseCenterTag()){
				storehouseNums.add(storehouse.getStorehouseNum());//总仓
			}
		}

		//配送数量  配送金额  (总仓)
		TransferQueryDTO transferQuery = new TransferQueryDTO();
		transferQuery.setSystemBookCode(systemBookCode);
		transferQuery.setCenterBranchNum(centerBranchNum);
		transferQuery.setStorehouseNums(storehouseNums);
		transferQuery.setDateFrom(dateFrom);
		transferQuery.setDateTo(dateTo);
		transferQuery.setItemNums(itemNums);
		transferQuery.setSortField(sortField);
		List<TransterOutDTO> transterOutDTOS = transferOutOrderRpc.findMoneyAndAmountByItemNum(transferQuery);

		//到货数量统计管理中心调出单
		transferQuery.setStorehouseNums(null);
		List<TransterOutDTO> receiveSummary = transferOutOrderRpc.findMoneyAndAmountByItemNum(transferQuery);
		//要货数量
		RequestOrderQuery requestOrderQuery = new RequestOrderQuery();
		BeanUtils.copyProperties(transferQuery,requestOrderQuery);
		List<RequestOrderDetailDTO> requestSummary = requestOrderRpc.findItemSummary(requestOrderQuery);
		List<TransferItemDetailSummary> list = new ArrayList<>(size);

		for (int i = 0; i <size ; i++) {
			PosItem posItem = posItems.get(i);
			Integer itemNum = posItem.getItemNum();
			TransferItemDetailSummary summary = new TransferItemDetailSummary();
			summary.setItemCode(posItem.getItemCode());
			summary.setItemNum(posItem.getItemNum());
			summary.setItemName(posItem.getItemName());

			for (int j = 0, len = transterOutDTOS.size(); j < len; j++) {
				TransterOutDTO dto = transterOutDTOS.get(j);
				if (itemNum.equals(dto.getItemNum())) {
					summary.setTransferMoney(dto.getMoney());
					summary.setTransferQty(dto.getQty());
				}
			}

			for (int j = 0,len = receiveSummary.size(); j < len ; j++) {
				TransterOutDTO dto = receiveSummary.get(j);
				if(itemNum.equals(dto.getItemNum())){
					summary.setReceiveQty(dto.getQty());
				}
			}
			for (int j = 0,len = requestSummary.size(); j < len ; j++) {
				RequestOrderDetailDTO dto = requestSummary.get(j);
				if(itemNum.equals(dto.getItemNum())){
					summary.setRequestQty(dto.getRequestOrderDetailQty());
				}
			}
			list.add(summary);
		}

		return list;
	}

	@Override
	public List<ItemInventoryTrendSummary> findItemTrendInventory(ItemInventoryQueryDTO inventoryQuery) {

		PosItemQuery query = new PosItemQuery();

		List<Integer> itemTypes = new ArrayList<>();
		itemTypes.add(AppConstants.C_ITEM_TYPE_STANDARD);
		itemTypes.add(AppConstants.C_ITEM_TYPE_ELEMENT);
		query.setShowItemTypes(itemTypes);
		query.setSystemBookCode(inventoryQuery.getSystemBookCode());
		query.setCategoryCodes(inventoryQuery.getItemCategorys());
		query.setSaleCrease(inventoryQuery.getSaleCrease());
		query.setStockCrease(inventoryQuery.getStockCrease());
		query.setDromCrease(inventoryQuery.getDromCrease());
		query.setFilterType(AppConstants.C_ITEM_TYPE_NON_STOCK_NAME);

		//根据商品类别查询商品
		List<PosItemDTO> posItemDTOS = posItemRpc.findByPosItemQuery(query,null,null,0,0);
		int size = posItemDTOS.size();
		if(size == 0){
			return Collections.emptyList();
		}
		List<Integer> itemNums = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			PosItemDTO posItemDTO = posItemDTOS.get(i);
			itemNums.add(posItemDTO.getItemNum());
		}
		int count = 0;
		List<ItemInventoryDTO> itemInventory = inventoryRpc.findItemAmount(inventoryQuery.getSystemBookCode(), null, itemNums, null);
		Map<String,ItemInventoryTrendSummary> map = new HashMap<>();

		for (int i = 0,len = itemInventory.size(); i < len ; i++) {
			ItemInventoryDTO itemInventoryDTO = itemInventory.get(i);
			Integer itemNum = itemInventoryDTO.getItemNum();
			PosItemDTO posItemDTO = AppUtil.getPosItemDTO(itemNum, posItemDTOS);
			if(posItemDTO == null ){
				continue;
			}
			ItemInventoryTrendSummary data = map.get(posItemDTO.getItemCategoryCode());
			if(data == null){
				data = new ItemInventoryTrendSummary();
				data.setCategoryCode(posItemDTO.getItemCategoryCode());
				data.setCategoryName(posItemDTO.getItemCategory());
				map.put(posItemDTO.getItemCategoryCode(),data);
			}

			if(itemInventoryDTO.getAmount().compareTo(BigDecimal.ZERO) > 0){
				data.setInventoryAmount(data.getInventoryAmount() + 1);
			}else{
				data.setUnInventoryAmount(data.getUnInventoryAmount() +1);
				List<PosItemDTO> itemDTOS = data.getPosItems();
				itemDTOS.add(posItemDTO);
				data.setPosItems(itemDTOS);
				System.out.println(++count);
			}

			data.setTotalAmount(data.getInventoryAmount()+data.getUnInventoryAmount());

		}

		return new ArrayList<>(map.values());
	}

	@Override
	public List<AlipayDetailDTO> findAlipayDetailDTOs(AlipayDetailQuery alipayDetailQuery) {
		if(alipayDetailQuery.getDateFrom() != null && alipayDetailQuery.getDateTo() != null
				&& alipayDetailQuery.getDateFrom().compareTo(alipayDetailQuery.getDateTo()) > 0){
			return new ArrayList<AlipayDetailDTO>();
		}
		List<AlipayDetailDTO> list = reportService.findAlipayDetailDTOs(alipayDetailQuery);
		return list;
	}

	@Override
	public List<PosItemRank> findPosItemRanks(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
		return reportService.findPosItemRanks(systemBookCode, branchNum, dateFrom, dateTo);
	}

	@Override
	public BranchProfitDataPageDTO findBranchAndItemProfit(BranchProfitQuery query){

			String systemBookCode = query.getSystemBookCode();
			Date dateFrom = query.getDateFrom();
			Date dateTo = query.getDateTo();
			Integer centerBranchNum = query.getBranchNum();
			List<Integer> branchNums = query.getBranchNums();
			List<String> categoryCodeList = query.getCategoryCodeList();
			List<Integer> posItemNums = query.getItemNums();
			Boolean queryKit = query.getQueryKit();
			Boolean isFilterDel = query.getFilterDel();
			String sortField = query.getSortField();
			String sortType = query.getSortType();
			int offset = query.getOffset();
			int limit = query.getLimit();

			Map<String, BranchProfitDataDTO> map = new HashMap<String, BranchProfitDataDTO>();

			List<Object[]> inventorObjects = inventoryService.findBranchItemSummary(systemBookCode, branchNums,null);
			Date now = Calendar.getInstance().getTime();
			Date nextDay = DateUtil.addDay(dateTo, 1);
			List<PosItemLogSummaryDTO> endList = new ArrayList<PosItemLogSummaryDTO>();

			StoreQueryCondition storeQueryCondition = new StoreQueryCondition();
			storeQueryCondition.setSystemBookCode(systemBookCode);
			storeQueryCondition.setBranchNums(branchNums);
			storeQueryCondition.setQuerySaleMoney(true);
			storeQueryCondition.setItemNums(posItemNums);
			if (DateUtil.getDateShortStr(nextDay).compareTo(DateUtil.getDateShortStr(now)) < 0) {
				logger.info(String.format("开始查询库存汇总 %s-%s", DateUtil.getDateShortStr(nextDay),
						DateUtil.getDateShortStr(now)));
				storeQueryCondition.setDateStart(nextDay);
				storeQueryCondition.setDateEnd(now);

				endList = posItemLogRpc.findBranchItemFlagSummary(storeQueryCondition);
				logger.info(String.format("完成查询库存汇总 %s-%s", DateUtil.getDateShortStr(nextDay),
						DateUtil.getDateShortStr(now)));
			} else {
				dateTo = now;
			}
			logger.info(String.format("开始查询库存汇总 %s-%s", DateUtil.getDateShortStr(dateFrom),
					DateUtil.getDateShortStr(dateTo)));
			storeQueryCondition.setDateStart(dateFrom);
			storeQueryCondition.setDateEnd(dateTo);
			List<PosItemLogSummaryDTO> beginList = posItemLogRpc.findBranchItemFlagSummary(storeQueryCondition);

			logger.info(String.format("完成查询库存汇总 %s-%s", DateUtil.getDateShortStr(dateFrom),
					DateUtil.getDateShortStr(dateTo)));

			// 当前库存金额
			for (int i = 0; i < inventorObjects.size(); i++) {
				Object[] objects = inventorObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[1];
				BigDecimal amount = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
				BigDecimal money = objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3];

				BranchProfitDataDTO data = new BranchProfitDataDTO();
				data.setItemNum(itemNum);
				data.setStartInventoryMoney(money);
				data.setEndInventoryMoney(money);
				data.setStartSaleMoney(amount);
				data.setEndSaleMoney(amount);
				data.setBranchNum(branchNum);
				map.put(branchNum + "|" + itemNum, data);
			}

			// 期末库存金额
			for (int i = 0; i < endList.size(); i++) {
				PosItemLogSummaryDTO dto = endList.get(i);
				Integer branchNum = dto.getBranchNum();
				Integer itemNum = dto.getItemNum();
				boolean flag = dto.getInoutFlag();
				BigDecimal money = dto.getMoney();
				BigDecimal amount = dto.getQty();

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				if (flag) {
					data.setEndInventoryMoney(data.getEndInventoryMoney().subtract(money));
					data.setEndSaleMoney(data.getEndSaleMoney().subtract(amount));
					data.setStartInventoryMoney(data.getEndInventoryMoney());
					data.setStartSaleMoney(data.getEndSaleMoney());
				} else {
					data.setEndInventoryMoney(data.getEndInventoryMoney().add(money));
					data.setEndSaleMoney(data.getEndSaleMoney().add(amount));
					data.setStartInventoryMoney(data.getEndInventoryMoney());
					data.setStartSaleMoney(data.getEndSaleMoney());
				}

			}

			// 起初库存金额
			for (int i = 0; i < beginList.size(); i++) {
				PosItemLogSummaryDTO dto = beginList.get(i);
				Integer branchNum = dto.getBranchNum();
				Integer itemNum = dto.getItemNum();
				boolean flag = dto.getInoutFlag();
				BigDecimal money = dto.getMoney();
				BigDecimal amount = dto.getQty();

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				if (flag) {
					data.setStartInventoryMoney(data.getStartInventoryMoney().subtract(money));
					data.setStartSaleMoney(data.getStartSaleMoney().subtract(amount));

				} else {
					data.setStartInventoryMoney(data.getStartInventoryMoney().add(money));
					data.setStartSaleMoney(data.getStartSaleMoney().add(amount));
				}

			}
			dateTo = query.getDateTo();
			// 营业额
			logger.info(String.format("开始查询门店营业分析 商品汇总 POS营业额"));
			List<Object[]> posOrderObjects = posOrderService.findItemSumByCategory(systemBookCode, branchNums,
					dateFrom, dateTo, null, queryKit,null);
			logger.info(String.format("完成查询门店营业分析 商品汇总 POS营业额"));
			for (int i = 0; i < posOrderObjects.size(); i++) {
				Object[] objects = posOrderObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[1];
				BigDecimal money = objects[2] == null ? BigDecimal.ZERO : (BigDecimal) objects[2];
				BigDecimal discount = objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5];
				BigDecimal costMoney = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setPosOrderMoney(money);
				data.setDiscountMoney(discount);
				data.setCostMoney(costMoney);
			}

			List<Integer> transferBranchNums = new ArrayList<Integer>();
			transferBranchNums.add(centerBranchNum);
			List<Object[]> inObjects = transferInOrderService.findProfitGroupByBranchAndItem(systemBookCode, transferBranchNums, branchNums, dateFrom, dateTo, null, posItemNums);
			for (int i = 0; i < inObjects.size(); i++) {
				Object[] objects = inObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[2];
				BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
				BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setTransferInMoney(data.getTransferInMoney().add(money));
				data.setTransferInSaleMoney(data.getTransferInSaleMoney().add(saleMoney));
			}

			List<Object[]> outObjects = transferOutOrderService.findProfitGroupByBranchAndItem(systemBookCode, transferBranchNums, branchNums, dateFrom, dateTo, null, posItemNums,false);
			for (int i = 0; i < outObjects.size(); i++) {
				Object[] objects = outObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[2];
				BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
				BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setTransferOutMoney(data.getTransferOutMoney().add(money));
				data.setTransferOutSaleMoney(data.getTransferOutSaleMoney().add(saleMoney));
			}

			List<Object[]> wholesaleObjects = wholesaleOrderService.findMoneyGroupByBranchItem(systemBookCode, branchNums, dateFrom, dateTo, posItemNums, null, null);
			for (int i = 0; i < wholesaleObjects.size(); i++) {
				Object[] objects = wholesaleObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[1];
				BigDecimal money = objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4];
				BigDecimal saleMoney = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setWholesaleOrderMoney(data.getWholesaleOrderMoney().add(money));
				data.setWholesaleOrderSaleMoney(data.getWholesaleOrderSaleMoney().add(saleMoney));
			}

			List<Object[]> receiveObjects = receiveOrderService.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, posItemNums);
			for (int i = 0; i < receiveObjects.size(); i++) {
				Object[] objects = receiveObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[1];
				BigDecimal money = objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3];

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setReceiveMoney(data.getReceiveMoney().add(money));
			}

			List<Object[]> returnObjects = returnOrderService.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, posItemNums);
			for (int i = 0; i < returnObjects.size(); i++) {
				Object[] objects = returnObjects.get(i);
				Integer branchNum = (Integer) objects[0];
				Integer itemNum = (Integer) objects[1];
				BigDecimal money = objects[3] == null ? BigDecimal.ZERO : (BigDecimal) objects[3];

				BranchProfitDataDTO data = map.get(branchNum + "|" + itemNum);
				if (data == null) {
					data = new BranchProfitDataDTO();
					data.setBranchNum(branchNum);
					data.setItemNum(itemNum);
					map.put(branchNum + "|" + itemNum, data);
				}
				data.setReturnMoney(data.getReturnMoney().add(money));
			}

			BigDecimal posOrderMoneySum = BigDecimal.ZERO;
			BigDecimal startInventoryMoneySum = BigDecimal.ZERO;
			BigDecimal endInventoryMoneySum = BigDecimal.ZERO;
			BigDecimal transferOutMoneySum = BigDecimal.ZERO;
			BigDecimal wholesaleOrderMoneySum = BigDecimal.ZERO;
			BigDecimal wholesaleOrderSaleMoneySum = BigDecimal.ZERO;
			BigDecimal transferInMoneySum = BigDecimal.ZERO;
			BigDecimal profitMoneySum = BigDecimal.ZERO;
			BigDecimal startSaleMoneySum = BigDecimal.ZERO;
			BigDecimal endSaleMoneySum = BigDecimal.ZERO;
			BigDecimal posDifferenceSum = BigDecimal.ZERO;
			BigDecimal lossMoneySum = BigDecimal.ZERO;
			BigDecimal discountMoneySum = BigDecimal.ZERO;
			BigDecimal receiveMoneySum = BigDecimal.ZERO;
			BigDecimal returnMoneySum = BigDecimal.ZERO;
			BigDecimal costMoneySum = BigDecimal.ZERO;

			List<BranchProfitDataDTO> list = new ArrayList<BranchProfitDataDTO>(map.values());
			List<Branch> branchs = branchService.findInCache(systemBookCode);
			List<PosItem> posItems = posItemService.findShortItems(systemBookCode);
			for (int i = list.size() - 1; i >= 0; i--) {
				BranchProfitDataDTO data = list.get(i);
				Branch branch = AppUtil.getBranch(branchs, data.getBranchNum());
				if (branch != null) {
					data.setBranch(data.getBranchNum() + "|" + branch.getBranchName());
					data.setBranchName(branch.getBranchName());
					data.setBranchCode(branch.getBranchCode());
				}
				if (posItemNums != null && posItemNums.size() > 0) {
					if (!posItemNums.contains(data.getItemNum())) {
						list.remove(i);
						continue;
					}
				}

				PosItem posItem = AppUtil.getPosItem(data.getItemNum(), posItems);
				if (posItem == null) {
					list.remove(i);
					continue;
				}
				if (isFilterDel && posItem.getItemDelTag() != null && posItem.getItemDelTag()) {
					list.remove(i);
					continue;
				}
				data.setPosItemCatagoryCode(posItem.getItemCategoryCode());
				data.setPosItemCatagoryName(posItem.getItemCategory());
				data.setPosItemCode(posItem.getItemCode());
				data.setPosItemName(posItem.getItemName());

				if (categoryCodeList != null && categoryCodeList.size() > 0) {
					if (!categoryCodeList.contains(data.getPosItemCatagoryCode())) {
						list.remove(i);
						continue;
					}
				}
				data.setPosDifference(data.getPosOrderMoney().add(data.getWholesaleOrderMoney()).subtract(data.getTransferOutMoney().subtract(data.getTransferInMoney())));
				data.setProfitMoney(data.getPosOrderMoney().add(data.getWholesaleOrderMoney()).add(data.getTransferInMoney()).subtract(data.getTransferOutMoney())
						.subtract(data.getStartInventoryMoney()).add(data.getEndInventoryMoney()).subtract(data.getReceiveMoney()).add(data.getReturnMoney()));
				data.setStartSaleMoney(data.getStartSaleMoney().multiply(posItem.getItemRegularPrice()));
				data.setEndSaleMoney(data.getEndSaleMoney().multiply(posItem.getItemRegularPrice()));
				if ((data.getPosOrderMoney().compareTo(BigDecimal.ZERO) > 0 || data.getWholesaleOrderMoney().compareTo(BigDecimal.ZERO) > 0)
						&& (data.getPosOrderMoney().add(data.getWholesaleOrderMoney())).compareTo(BigDecimal.ZERO) != 0) {
					data.setProfitRate(data.getProfitMoney().divide(data.getPosOrderMoney().add(data.getWholesaleOrderMoney()), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(BigDecimal.valueOf(100).setScale(2)));

					data.setPosRate((data.getTransferOutMoney().subtract(data.getTransferInMoney())).divide(data.getPosOrderMoney().add(data.getWholesaleOrderMoney()), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(BigDecimal.valueOf(100).setScale(2)));
				}
				//损耗=期初零售金额+调出零售金额-调入零售金额-期末零售金额-POS收入-批发销售商品预估零售额-折扣合计
				data.setLossMoney(data.getStartSaleMoney().add(data.getTransferOutSaleMoney()).subtract(data.getTransferInSaleMoney())
						.subtract(data.getEndSaleMoney()).subtract(data.getDiscountMoney()).subtract(data.getPosOrderMoney().add(data.getWholesaleOrderSaleMoney())));

				posOrderMoneySum = posOrderMoneySum.add(data.getPosOrderMoney());
				startInventoryMoneySum = startInventoryMoneySum.add(data.getStartInventoryMoney());
				endInventoryMoneySum = endInventoryMoneySum.add(data.getEndInventoryMoney());
				transferOutMoneySum = transferOutMoneySum.add(data.getTransferOutMoney());
				wholesaleOrderMoneySum = wholesaleOrderMoneySum.add(data.getWholesaleOrderMoney());
				wholesaleOrderSaleMoneySum = wholesaleOrderSaleMoneySum.add(data.getWholesaleOrderSaleMoney());
				transferInMoneySum = transferInMoneySum.add(data.getTransferInMoney());
				profitMoneySum = profitMoneySum.add(data.getProfitMoney());
				startSaleMoneySum = startSaleMoneySum.add(data.getStartSaleMoney());
				endSaleMoneySum = endSaleMoneySum.add(data.getEndSaleMoney());
				posDifferenceSum = posDifferenceSum.add(data.getPosDifference());
				lossMoneySum = lossMoneySum.add(data.getLossMoney());
				discountMoneySum = discountMoneySum.add(data.getDiscountMoney());
				receiveMoneySum = receiveMoneySum.add(data.getReceiveMoney());
				returnMoneySum = returnMoneySum.add(data.getReturnMoney());
				costMoneySum = costMoneySum.add(data.getCostMoney());
			}

			if (sortField == null) {
				sortField = "posItemCode";
				sortType = "ASC";
			}

		ComparatorGroupModelData<BranchProfitDataDTO> comparator = new ComparatorGroupModelData("branchNum", sortField, sortType,BranchProfitDataDTO.class);
		Collections.sort(list, comparator);


		for (int i = 0; i < list.size(); i++) {
				BranchProfitDataDTO data = list.get(i);
				data.setId(i);
				data.setBranch(data.getBranchNum() + "|" + data.getBranchName());
				data.setItemCatagory(data.getPosItemCatagoryCode() + "|" + data.getPosItemCatagoryName());
			}

			int dataSize = list.size();
			BranchProfitDataPageDTO resultDTO = new BranchProfitDataPageDTO();
			resultDTO.setCount(dataSize);
			resultDTO.setData(list);

			resultDTO.setPosOrderMoneySum(posOrderMoneySum);
			resultDTO.setStartInventoryMoneySum(startInventoryMoneySum);
			resultDTO.setEndInventoryMoneySum(endInventoryMoneySum);
			resultDTO.setTransferOutMoneySum(transferOutMoneySum);
			resultDTO.setWholesaleOrderMoneySum(wholesaleOrderMoneySum);
			resultDTO.setTransferInMoneySum(transferInMoneySum);
			resultDTO.setProfitMoneySum(profitMoneySum);
			resultDTO.setStartSaleMoneySum(startSaleMoneySum);
			resultDTO.setEndSaleMoneySum(endSaleMoneySum);
			resultDTO.setPosDifferenceSum(posDifferenceSum);
			resultDTO.setLossMoneySum(lossMoneySum);
			resultDTO.setDiscountMoneySum(discountMoneySum);
			resultDTO.setReceiveMoneySum(receiveMoneySum);
			resultDTO.setReturnMoneySum(returnMoneySum);
			resultDTO.setCostMoneySum(costMoneySum);

			if(posOrderMoneySum.compareTo(BigDecimal.ZERO) == 0){
				resultDTO.setProfitRateSum(BigDecimal.ZERO);
			}else{
				resultDTO.setProfitRateSum(profitMoneySum.divide(posOrderMoneySum, 8, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}
			if(posOrderMoneySum.compareTo(BigDecimal.ZERO) == 0){
				resultDTO.setPosRateSum(BigDecimal.ZERO);
			}else{
				resultDTO.setPosRateSum((transferOutMoneySum.subtract(transferInMoneySum)).divide(posOrderMoneySum, 8, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
			}

			if(query.isPage()){
				int pageSum = offset + limit;
				List<BranchProfitDataDTO> subList = null;
				if(dataSize >= pageSum){
					subList = list.subList(offset, pageSum);
				}else{
					subList = list.subList(offset, dataSize);
				}
				resultDTO.setData(subList);
			}

			return resultDTO;
	}


	@Override
	public TransferProfitByPosItemPageDTO findTransferProfitByPosItemBranch(TransferProfitQuery queryData){

		int offset = queryData.getOffset();
		int limit = queryData.getLimit();
		String sortField = queryData.getSortField();
		String sortType = queryData.getSortType();
		queryData.setCategoryCodes(queryData.getItemTypeNums());
		String unit = queryData.getUnitType();
		if (unit == null) {
			unit = AppConstants.UNIT_TRANFER;
			queryData.setUnitType(unit);
		}
		List<Integer> itemNums = queryData.getItemNums();
		List<String> categoryCodes = queryData.getCategoryCodes();
		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();


		if (queryData.getItemFlagNum() != null && queryData.getItemFlagNum() > 0) {
			List<ItemFlagDetailDTO> itemDetails = posItemFlagRpc.findDetails(queryData.getSystemBookCode(), queryData.getItemFlagNum());
			for (int i = 0; i < itemDetails.size(); i++) {
				posItems.add(itemDetails.get(i).getPosItem());
			}
			if ((itemNums != null && itemNums.size() > 0)
					|| (categoryCodes != null && categoryCodes.size() > 0)) {
				for (int i = 0; i < posItems.size(); i++) {
					if (itemNums != null && itemNums.size() > 0) {
						if (!itemNums.contains(posItems.get(i).getItemNum())) {
							posItems.remove(i);
							i--;
							continue;
						}
					}
					if (categoryCodes != null && categoryCodes.size() > 0) {
						if (!categoryCodes.contains(posItems.get(i).getItemCategoryCode())) {
							posItems.remove(i);
							i--;
							continue;
						}
					}
				}
			}
			if (posItems.size() == 0) {
				return new TransferProfitByPosItemPageDTO();
			} else {
				itemNums = new ArrayList<Integer>();
				for (int i = 0; i < posItems.size(); i++) {
					itemNums.add(posItems.get(i).getItemNum());
				}
				queryData.setItemNums(itemNums);
			}
		}

		List<PosItem> posItemDatas = posItemService.findShortItems(queryData.getSystemBookCode());
		List<TransferProfitByPosItemDTO> list = new ArrayList<TransferProfitByPosItemDTO>();

		List<Object[]> outObjects = transferOutOrderService.findProfitGroupByBranchAndItem(queryData);

		for (int i = 0; i < outObjects.size(); i++) {
			Object[] objects = outObjects.get(i);
			Integer branchNum = (Integer) objects[0];
			Integer outBranchNum = (Integer) objects[1];
			Integer itemNum = (Integer) objects[2];
			Integer itemMatrixNum = objects[3] == null ? 0 : (Integer) objects[3];
			BigDecimal amount = objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4];
			BigDecimal cost = objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5];
			BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
			BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];
			BigDecimal useAmount = objects[8] == null ? BigDecimal.ZERO : (BigDecimal) objects[8];
			BigDecimal amountPr = objects[9] == null ? BigDecimal.ZERO : (BigDecimal) objects[9];
			BigDecimal useAmountPr = objects[10] == null ? BigDecimal.ZERO : (BigDecimal) objects[10];
			BigDecimal moneyTranPr = objects[11] == null ? BigDecimal.ZERO : (BigDecimal) objects[11];
			BigDecimal moneyCostPr = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];
			BigDecimal receiveTare = objects[13] == null ? BigDecimal.ZERO : (BigDecimal) objects[13];

			TransferProfitByPosItemDTO data = new TransferProfitByPosItemDTO();
			data.setTranferBranchNum(outBranchNum);
			data.setBranchNum(branchNum);
			data.setItemNum(itemNum);
			data.setItemMatrixNum(itemMatrixNum);
			data.setBasicQty(amount);
			data.setOutAmount(useAmount);
			data.setBasicQtyPr(amountPr);
			data.setOutAmountPr(useAmountPr);
			data.setOutAmountPrTranferMoney(moneyTranPr.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrCostMoney(moneyCostPr.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutCost(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutMoney(money.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setSaleMoney(saleMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setReceiveTare(receiveTare);
			data.setOutsAmount(amount);
			data.setOutsMoney(money.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutUseAmount(useAmount);
			list.add(data);
		}

		List<Object[]> inObjects = transferInOrderService.findProfitGroupByBranchAndItem(queryData);
		for (int i = 0; i < inObjects.size(); i++) {
			Object[] objects = inObjects.get(i);
			Integer branchNum = (Integer) objects[0];
			Integer inBranchNum = (Integer) objects[1];
			Integer itemNum = (Integer) objects[2];
			Integer itemMatrixNum = objects[3] == null ? 0 : (Integer) objects[3];
			BigDecimal amount = objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4];
			BigDecimal cost = objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5];
			BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
			BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];
			BigDecimal useAmount = objects[8] == null ? BigDecimal.ZERO : (BigDecimal) objects[8];
			BigDecimal amountPr = objects[9] == null ? BigDecimal.ZERO : (BigDecimal) objects[9];
			BigDecimal useAmountPr = objects[10] == null ? BigDecimal.ZERO : (BigDecimal) objects[10];
			BigDecimal moneyTranPr = objects[11] == null ? BigDecimal.ZERO : (BigDecimal) objects[11];
			BigDecimal moneyCostPr = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];
			BigDecimal receiveTare = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];
			TransferProfitByPosItemDTO data = getTransferProfitByPosItemData(list, inBranchNum, branchNum, itemNum, itemMatrixNum);
			if (data == null) {
				data = new TransferProfitByPosItemDTO();
				data.setTranferBranchNum(inBranchNum);
				data.setBranchNum(branchNum);
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				list.add(data);
			}
			data.setBasicQty(data.getBasicQty().subtract(amount));
			data.setOutAmount(data.getOutAmount().subtract(useAmount));
			data.setBasicQtyPr(data.getBasicQtyPr().subtract(amountPr));
			data.setOutAmountPr(data.getOutAmountPr().subtract(useAmountPr));
			data.setOutCost((data.getOutCost().subtract(cost)).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutMoney((data.getOutMoney().subtract(money)).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setSaleMoney((data.getSaleMoney().subtract(saleMoney)).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrTranferMoney(data.getOutAmountPrTranferMoney().subtract(moneyTranPr).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrCostMoney(data.getOutAmountPrCostMoney().subtract(moneyCostPr).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setReceiveTare(data.getReceiveTare().subtract(receiveTare));
			data.setInMoney(money.setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setInAmount(amount);
			data.setInUseAmount(useAmount);
		}
		List<Branch> branchs = branchService.findInCache(queryData.getSystemBookCode());

		BigDecimal basicQtySum = BigDecimal.ZERO;
		BigDecimal basicQtyPrSum = BigDecimal.ZERO;
		for (int i = 0; i < list.size(); i++) {
			TransferProfitByPosItemDTO data = list.get(i);
			data.setId(AppUtil.getUUID());
			basicQtySum = basicQtySum.add(data.getBasicQty());
			basicQtyPrSum = basicQtyPrSum.add(data.getBasicQtyPr());
			Branch branch = AppUtil.getBranch(branchs, data.getTranferBranchNum());
			if (branch != null) {
				data.setTranferBranchNum(branch.getId().getBranchNum());
				data.setTranferBranchName(branch.getBranchName());
			}

			branch = AppUtil.getBranch(branchs, data.getBranchNum());
			if (branch != null) {
				data.setBranchNum(branch.getId().getBranchNum());
				data.setBranchName(branch.getBranchName());
			}

			Integer itemNum = data.getItemNum();
			PosItem posItem = AppUtil.getPosItem(itemNum, posItemDatas);
			if (posItem == null) {
				continue;
			}
			//data.setPosItemData(PosItemConverter.createModelData(posItem, false));
			data.setPosItemTypeCode(posItem.getItemCategoryCode());
			data.setPosItemTypeName(posItem.getItemCategory());
			data.setSpec(posItem.getItemSpec());
			if (queryData.isEnableAssist()) {
				if (StringUtils.isNotEmpty(posItem.getItemAssistUnit()) && posItem.getItemAssistRate() != null
						&& posItem.getItemAssistRate().compareTo(BigDecimal.ZERO) > 0) {
					data.setUnit(posItem.getItemAssistUnit());
					data.setInAmount(data.getInUseAmount());
					data.setOutsAmount(data.getOutUseAmount());
				} else {
					data.setUnit(posItem.getItemTransferUnit());
					if (unit.equals(AppConstants.UNIT_TRANFER)) {
						data.setUnit(posItem.getItemTransferUnit());
					} else if (unit.equals(AppConstants.UNIT_SOTRE)) {
						data.setUnit(posItem.getItemInventoryUnit());
					} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
						data.setUnit(posItem.getItemPurchaseUnit());
					} else if (unit.equals(AppConstants.UNIT_BASIC)) {
						data.setUnit(posItem.getItemUnit());
					} else if (unit.equals(AppConstants.UNIT_PIFA)) {
						data.setUnit(posItem.getItemWholesaleUnit());
					}
				}
			} else {
				data.setUnit(posItem.getItemTransferUnit());
				if (unit.equals(AppConstants.UNIT_TRANFER)) {
					data.setUnit(posItem.getItemTransferUnit());
				} else if (unit.equals(AppConstants.UNIT_SOTRE)) {
					data.setUnit(posItem.getItemInventoryUnit());
				} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
					data.setUnit(posItem.getItemPurchaseUnit());
				} else if (unit.equals(AppConstants.UNIT_BASIC)) {
					data.setUnit(posItem.getItemUnit());
				} else if (unit.equals(AppConstants.UNIT_PIFA)) {
					data.setUnit(posItem.getItemWholesaleUnit());
				}
			}
			data.setBasicUnit(posItem.getItemUnit());
			data.setPosItemCode(posItem.getItemCode());
			data.setPosItemName(posItem.getItemName());

			BigDecimal rate = BigDecimal.ZERO;
			if (unit.equals(AppConstants.UNIT_TRANFER)) {
				rate = posItem.getItemTransferRate();
			} else if (unit.equals(AppConstants.UNIT_SOTRE)) {
				rate = posItem.getItemInventoryRate();
			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				rate = posItem.getItemPurchaseRate();
			} else if (unit.equals(AppConstants.UNIT_BASIC)) {
				rate = BigDecimal.ONE;
			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				rate = posItem.getItemWholesaleRate();
			}
			if (rate.compareTo(BigDecimal.ZERO) > 0) {
				data.setOutAmount(data.getBasicQty().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				data.setOutAmountPr(data.getBasicQtyPr().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				data.setOutsAmount(data.getOutsAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
				data.setInAmount(data.getInAmount().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
			}
			if (data.getItemMatrixNum() > 0) {
				ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), itemNum, data.getItemMatrixNum());
				if (itemMatrix != null) {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}
			data.setOutProfit((data.getOutMoney().subtract(data.getOutCost())).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setSaleProfit((data.getSaleMoney().subtract(data.getOutMoney())).setScale(2, BigDecimal.ROUND_HALF_UP));

			if (data.getOutMoney().compareTo(BigDecimal.ZERO) > 0) {
				data.setOutProfitRate(data.getOutProfit().divide(data.getOutMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2));

			} else {
				data.setOutProfitRate(BigDecimal.ZERO);
			}
			if (data.getSaleMoney().compareTo(BigDecimal.ZERO) != 0) {
				data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2));

			} else {
				data.setSaleProfitRate(BigDecimal.ZERO);
			}
			if (data.getOutMoney() == null || data.getBasicQty() == null || data.getBasicQty().compareTo(BigDecimal.ZERO) == 0) {
				data.setBasicPrice(BigDecimal.ZERO);
			} else {
				data.setBasicPrice(data.getOutMoney().divide(data.getBasicQty(), 4, BigDecimal.ROUND_HALF_UP));
			}

		}
		if (sortField == null) {
			sortField = "branchNum";
			sortType = "ASC";
		}

		ComparatorGroupModelData<TransferProfitByPosItemDTO> comparator = new ComparatorGroupModelData("tranferBranchNum", sortField, sortType,TransferProfitByPosItemDTO.class);
		Collections.sort(list, comparator);

		// 总合计
		BigDecimal outAmountSum = BigDecimal.ZERO;
		BigDecimal outAmountPrSum = BigDecimal.ZERO;
		BigDecimal outMoneySum = BigDecimal.ZERO;
		BigDecimal saleMoneySum = BigDecimal.ZERO;
		BigDecimal outCostSum = BigDecimal.ZERO;
		BigDecimal outProfitSum = BigDecimal.ZERO;
		BigDecimal saleProfitSum = BigDecimal.ZERO;
		BigDecimal outAmountPrTranferMoneySum = BigDecimal.ZERO;
		BigDecimal outAmountPrCostMoneySum = BigDecimal.ZERO;
		BigDecimal receiveTareSum = BigDecimal.ZERO;
		BigDecimal totalAmountSum = BigDecimal.ZERO;
		BigDecimal totalMoneySum = BigDecimal.ZERO;
		BigDecimal inAmountSum = BigDecimal.ZERO;
		BigDecimal inMoneySum = BigDecimal.ZERO;
		BigDecimal outsAmountSum = BigDecimal.ZERO;
		BigDecimal outsMoneySum = BigDecimal.ZERO;
		for (int i = list.size() - 1; i >= 0; i--) {
			TransferProfitByPosItemDTO data = list.get(i);
			data.setTotalAmount(data.getOutAmount().subtract(data.getInAmount()).add(data.getOutAmountPr()));
			data.setTotalMoney(data.getOutMoney().subtract(data.getInMoney()).add(data.getOutAmountPrTranferMoney()));

			outAmountSum = outAmountSum.add(data.getOutAmount());
			outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
			outCostSum = outCostSum.add(data.getOutCost());
			outMoneySum = outMoneySum.add(data.getOutMoney());
			saleMoneySum = saleMoneySum.add(data.getSaleMoney());
			outProfitSum = outProfitSum.add(data.getOutProfit());
			saleProfitSum = saleProfitSum.add(data.getSaleProfit());
			outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
			outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
			receiveTareSum = receiveTareSum.add(data.getReceiveTare());
			totalAmountSum = totalAmountSum.add(data.getTotalAmount());
			totalMoneySum = totalMoneySum.add(data.getTotalMoney());
			inAmountSum = inAmountSum.add(data.getInAmount());
			inMoneySum = inMoneySum.add(data.getInMoney());
			outsAmountSum = outsAmountSum.add(data.getOutsAmount());
			outsMoneySum = outsMoneySum.add(data.getOutsMoney());

		}

		int dataSize = list.size();
		TransferProfitByPosItemPageDTO result = new TransferProfitByPosItemPageDTO();
		result.setData(list);
		result.setCount(dataSize);

		result.setSaleMoneySum(saleMoneySum);
		result.setOutMoneySum(outMoneySum);
		result.setOutCostSum(outCostSum);
		result.setOutProfitSum(outProfitSum);
		result.setSaleProfitSum(saleProfitSum);
		result.setOutAmountSum(outAmountSum);
		result.setOutAmountPrSum(outAmountPrSum);
		result.setBasicQtySum(basicQtySum);
		result.setBasicQtyPrSum(basicQtyPrSum);
		result.setOutAmountPrTranferMoneySum(outAmountPrTranferMoneySum);
		result.setOutAmountPrCostMoneySum(outAmountPrCostMoneySum);
		result.setReceiveTareSum(receiveTareSum);
		result.setTotalMoneySum(totalMoneySum);
		result.setTotalAmountSum(totalAmountSum);
		result.setInAmountSum(inAmountSum);
		result.setInMoneySum(inMoneySum);
		result.setOutsAmountSum(outsAmountSum);
		result.setOutsMoneySum(outsMoneySum);

		if (outMoneySum.compareTo(BigDecimal.ZERO) == 0) {
			result.setOutProfitRateSum(BigDecimal.ZERO);
		} else {
			result.setOutProfitRateSum(outProfitSum.divide(outMoneySum, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		}

		if (saleMoneySum.compareTo(BigDecimal.ZERO) == 0) {
			result.setSaleProfitRateSum(BigDecimal.ZERO);
		} else {
			result.setSaleProfitRateSum(saleProfitSum.divide(saleMoneySum, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)));
		}

		int pageSum = offset + limit;
		if(queryData.isPage()){
			List<TransferProfitByPosItemDTO> subList = null;
			if(dataSize >= pageSum){
				subList = list.subList(offset,pageSum);
			}else{
				subList = list.subList(offset,dataSize);
			}
			result.setData(subList);

		}

		return result;
	}



	@Override
	public TransferProfitByPosItemDetailPageDTO findTransferProfitByPosItemDetail(TransferProfitQuery queryData) {

		int offset = queryData.getOffset();
		int limit = queryData.getLimit();
		String sortField = queryData.getSortField();
		String sortType = queryData.getSortType();
		Integer itemFlagNum = queryData.getItemFlagNum();

		List<Integer> itemNums = queryData.getItemNums();
		List<String> categoryCodes = queryData.getItemTypeNums();
		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
		if (itemFlagNum != null && itemFlagNum > 0) {
			List<ItemFlagDetailDTO> itemDetails = posItemFlagRpc.findDetails(queryData.getSystemBookCode(), itemFlagNum);
			for (int i = 0; i < itemDetails.size(); i++) {
				posItems.add(itemDetails.get(i).getPosItem());
			}
			if ((itemNums != null && itemNums.size() > 0)
					|| (categoryCodes != null && categoryCodes.size() > 0)) {
				for (int i = 0; i < posItems.size(); i++) {
					if (itemNums != null && itemNums.size() > 0) {
						if (!itemNums.contains(posItems.get(i).getItemNum())) {
							posItems.remove(i);
							i--;
							continue;
						}
					}
					if (categoryCodes != null && categoryCodes.size() > 0) {
						if (!categoryCodes.contains(posItems.get(i).getItemCategoryCode())) {
							posItems.remove(i);
							i--;
							continue;
						}
					}
				}
			}
			if (posItems.size() == 0) {
				return new TransferProfitByPosItemDetailPageDTO();
			} else {
				itemNums = new ArrayList<Integer>();
				for (int i = 0; i < posItems.size(); i++) {
					itemNums.add(posItems.get(i).getItemNum());
				}
				queryData.setItemNums(itemNums);
			}
		}


		List<Branch> branchs = branchService.findInCache(queryData.getSystemBookCode());
		List<TransferProfitByPosItemDetailDTO> list = new ArrayList<TransferProfitByPosItemDetailDTO>();

		//诚信志远 不统计特价商品
		queryData.setCategoryCodes(categoryCodes);
		List<Object[]> outObjects = transferOutOrderService.findDetails(queryData);

		List<ItemMatrix> itemMatrixs = new ArrayList<ItemMatrix>();

		// 总合计
		BigDecimal baseAmountSum = BigDecimal.ZERO;
		BigDecimal outAmountSum = BigDecimal.ZERO;
		BigDecimal baseAmountPrSum = BigDecimal.ZERO;
		BigDecimal outAmountPrSum = BigDecimal.ZERO;
		BigDecimal outAmountPrTranferMoneySum = BigDecimal.ZERO;
		BigDecimal outAmountPrCostMoneySum = BigDecimal.ZERO;
		BigDecimal outMoneySum = BigDecimal.ZERO;
		BigDecimal costUnitPriceSum = BigDecimal.ZERO;
		BigDecimal profitMoneySum = BigDecimal.ZERO;
		List<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < outObjects.size(); i++) {
			Object[] objects = outObjects.get(i);

			TransferProfitByPosItemDetailDTO data = new TransferProfitByPosItemDetailDTO();
			data.setPosOrderNum((String) objects[0]);
			data.setPosOrderType("调出单");
			data.setSaleTime((Date) objects[1]);
			data.setOrderSeller((String) objects[2]);
			data.setOrderMaker((String) objects[3]);
			data.setOrderAuditor((String) objects[4]);
			data.setResponseBranchNum((Integer) objects[5]);
			data.setPosItemCode((String) objects[6]);
			data.setPosItemName((String) objects[7]);
			data.setSpec((String) objects[8]);
			data.setOutUnit((String) objects[9]);
			data.setOutAmount((BigDecimal) objects[10]);
			data.setOutUnitPrice((BigDecimal) objects[11]);
			data.setOutMoney((BigDecimal) objects[12]);
			data.setCostUnitPrice((BigDecimal) objects[13]);
			data.setProfitMoney(data.getOutMoney().subtract(data.getCostUnitPrice()));
			data.setRemark((String) objects[14]);
			data.setDistributionBranchNum((Integer) objects[15]);
			data.setBaseUnit((String) objects[16]);
			Date sendDate = (Date) objects[17];
			data.setBaseAmount(((BigDecimal) objects[18]).setScale(2, BigDecimal.ROUND_HALF_UP));
			if (data.getBaseAmount() == null || data.getBaseAmount().compareTo(BigDecimal.ZERO) == 0 || data.getOutMoney() == null) {
			} else {
				data.setBasePrice(data.getOutMoney().divide(data.getBaseAmount(), 4, BigDecimal.ROUND_HALF_UP));
			}
			Integer itemMatrixNum = (Integer) objects[19];
			Integer itemNum = (Integer) objects[20];
			if (!nums.contains(itemNum)) {
				nums.add(itemNum);
			}
			data.setItemNum(itemNum);
			data.setOutUnitPr((String) objects[22]);
			data.setBaseAmountPr(objects[23] == null ? BigDecimal.ZERO : (BigDecimal) objects[23]);
			data.setOutAmountPr((objects[24] == null ? BigDecimal.ZERO : (BigDecimal) objects[24]).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrTranferMoney((objects[25] == null ? BigDecimal.ZERO : (BigDecimal) objects[25]).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrCostMoney((objects[26] == null ? BigDecimal.ZERO : (BigDecimal) objects[26]).setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setProductDate(objects[27] == null ? null : (Date) objects[27]);
			if (itemMatrixNum != null && itemMatrixNum > 0) {

				ItemMatrix itemMatrix = AppUtil.getItemMatrix(itemMatrixs, itemNum, itemMatrixNum);
				if (itemMatrix == null) {
					itemMatrix = itemMatrixService.read(itemNum, itemMatrixNum);
					if (itemMatrix != null) {
						data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
						itemMatrixs.add(itemMatrix);
					}
				} else {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				}
			}

			if (sendDate == null) {
				data.setState("未配货");
			} else {
				data.setState("已配货");
			}
			Branch branch = AppUtil.getBranch(branchs, data.getDistributionBranchNum());
			if (branch == null) {
				continue;
			}
			data.setDistributionBranchNum(branch.getId().getBranchNum());
			data.setDistributionBranchName(branch.getBranchName());

			branch = AppUtil.getBranch(branchs, data.getResponseBranchNum());
			if (branch == null) {
				continue;
			}
			data.setResponseBranchNum(branch.getId().getBranchNum());
			data.setResponseBranchName(branch.getBranchName());
			list.add(data);

			baseAmountSum = baseAmountSum.add(data.getBaseAmount());
			outAmountSum = outAmountSum.add(data.getOutAmount());
			baseAmountPrSum = baseAmountPrSum.add(data.getBaseAmountPr());
			outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
			outMoneySum = outMoneySum.add(data.getOutMoney());
			costUnitPriceSum = costUnitPriceSum.add(data.getCostUnitPrice());
			profitMoneySum = profitMoneySum.add(data.getProfitMoney());
			outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
			outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
		}

		List<Object[]> inObjects = transferInOrderService.findDetails(queryData);

		for (int i = 0; i < inObjects.size(); i++) {
			Object[] objects = inObjects.get(i);

			TransferProfitByPosItemDetailDTO data = new TransferProfitByPosItemDetailDTO();
			data.setPosOrderNum((String) objects[0]);
			data.setPosOrderType("调入单");
			data.setSaleTime((Date) objects[1]);
			data.setOrderSeller((String) objects[2]);
			data.setOrderMaker((String) objects[3]);
			data.setOrderAuditor((String) objects[4]);
			data.setResponseBranchNum((Integer) objects[5]);
			data.setPosItemCode((String) objects[6]);
			data.setPosItemName((String) objects[7]);
			data.setSpec((String) objects[8]);
			data.setOutUnit((String) objects[9]);
			data.setOutAmount(((BigDecimal) objects[10]).negate());
			data.setOutUnitPrice((BigDecimal) objects[11]);
			data.setOutMoney(((BigDecimal) objects[12]).negate());
			data.setCostUnitPrice(((BigDecimal) objects[13]).negate());
			data.setProfitMoney(data.getOutMoney().subtract(data.getCostUnitPrice()));
			data.setRemark((String) objects[14]);
			data.setDistributionBranchNum((Integer) objects[15]);
			data.setBaseUnit((String) objects[16]);
			data.setBaseAmount(((BigDecimal) objects[17]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
			Integer itemMatrixNum = (Integer) objects[18];
			Integer itemNum = (Integer) objects[19];
			if (!nums.contains(itemNum)) {
				nums.add(itemNum);
			}
			data.setItemNum(itemNum);
			data.setOutUnitPr((String) objects[20]);
			data.setOutAmountPr((objects[21] == null ? BigDecimal.ZERO : (BigDecimal) objects[21]).negate());
			data.setBaseAmountPr((objects[22] == null ? BigDecimal.ZERO : (BigDecimal) objects[22]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrTranferMoney((objects[23] == null ? BigDecimal.ZERO : (BigDecimal) objects[23]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setOutAmountPrCostMoney((objects[24] == null ? BigDecimal.ZERO : (BigDecimal) objects[24]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setProductDate(objects[25] == null ? null : (Date) objects[25]);
			if (itemMatrixNum != null && itemMatrixNum > 0) {

				ItemMatrix itemMatrix = AppUtil.getItemMatrix(itemMatrixs, itemNum, itemMatrixNum);
				if (itemMatrix == null) {
					itemMatrix = itemMatrixService.read(itemNum, itemMatrixNum);
					if (itemMatrix != null) {
						data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
						itemMatrixs.add(itemMatrix);
					}
				} else {
					data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));

				}
			}
			data.setState("未配货");

			Branch branch = AppUtil.getBranch(branchs, data.getDistributionBranchNum());
			if (branch == null) {
				continue;
			}
			data.setDistributionBranchNum(branch.getId().getBranchNum());
			data.setDistributionBranchName(branch.getBranchName());

			branch = AppUtil.getBranch(branchs, data.getResponseBranchNum());
			if (branch == null) {
				continue;
			}
			data.setResponseBranchNum(branch.getId().getBranchNum());
			data.setResponseBranchName(branch.getBranchName());
			list.add(data);

			baseAmountSum = baseAmountSum.add(data.getBaseAmount());
			outAmountSum = outAmountSum.add(data.getOutAmount());
			baseAmountPrSum = baseAmountPrSum.add(data.getBaseAmountPr());
			outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
			outMoneySum = outMoneySum.add(data.getOutMoney());
			costUnitPriceSum = costUnitPriceSum.add(data.getCostUnitPrice());
			profitMoneySum = profitMoneySum.add(data.getProfitMoney());
			outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
			outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
		}
		List<PosItem> items = posItemService.findByItemNumsInCache(queryData.getSystemBookCode(), nums);
		for (int i = 0; i < list.size(); i++) {
			TransferProfitByPosItemDetailDTO detailData = list.get(i);
			detailData.setId(AppUtil.getUUID());
			PosItem posItem = AppUtil.getPosItem(detailData.getItemNum(), items);
			if (posItem != null) {
				detailData.setItemValidPeriod(posItem.getItemValidPeriod());
				if (detailData.getProductDate() != null && posItem.getItemValidPeriod() != null && posItem.getItemValidPeriod() >= 0) {
					detailData.setProductPassDate(DateUtil.addDay(detailData.getProductDate(), posItem.getItemValidPeriod()));
				}
				detailData.setDepartment(posItem.getItemDepartment());
			}
		}
		if (sortField == null) {
			sortField = "posOrderNum";
			sortType = "ASC";
		}

		ComparatorBaseModelData<TransferProfitByPosItemDetailDTO> comparator = new ComparatorBaseModelData<>(sortField, sortType, TransferProfitByPosItemDetailDTO.class);
		Collections.sort(list, comparator);


		int dataSize = list.size();
		TransferProfitByPosItemDetailPageDTO result = new TransferProfitByPosItemDetailPageDTO();
		result.setCount(dataSize);
		result.setData(list);
		result.setOutMoneySum(outMoneySum);
		result.setCostUnitPriceSum(costUnitPriceSum);
		result.setProfitMoneySum(profitMoneySum);
		result.setOutAmountSum(outAmountSum);
		result.setBaseAmountSum(baseAmountSum);
		result.setOutAmountPrSum(outAmountPrSum);
		result.setBaseAmountPrSum(baseAmountPrSum);
		result.setOutAmountPrCostMoneySum(outAmountPrCostMoneySum);
		result.setOutAmountPrTranferMoneySum(outAmountPrTranferMoneySum);

		int pageSum = offset + limit;
		if (queryData.isPage()) {
			List<TransferProfitByPosItemDetailDTO> subList = null;
			if (dataSize >= pageSum ) {
				subList = list.subList(offset, pageSum);
			} else {
				subList = list.subList(offset, dataSize);
			}
			result.setData(subList);
		}
		return result;
	}




	@Override
	public InventoryProfitPageDTO findInventoryProfit(InventoryProfitQuery queryData){

		int offset = queryData.getOffset();
		int limit = queryData.getLimit();
		String sortField = queryData.getSortField();
		String sortType = queryData.getSortType();

		String systemBookCode = queryData.getSystemBookCode();
		List<Integer> branchNums = queryData.getBranchNums();
		Integer storehouseNum = queryData.getStoreNum();
		Date dateFrom = queryData.getDateFrom();
		Date dateTo = queryData.getDateTo();
		List<String> categoryCodes = queryData.getCategoryCodes();
		List<Integer> itemNums = queryData.getItemNums();
		String checkType = queryData.getCheckType();
		Boolean isChechUp = queryData.getIsChechUp();
		List<String> reasons = queryData.getReasons();
		String unit = queryData.getUnit();


		if (StringUtils.isEmpty(unit)) {
			unit = AppConstants.UNIT_BASIC;
		}

		Map<String, InventoryProfitDTO> map = new HashMap<String, InventoryProfitDTO>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		if (StringUtils.isEmpty(checkType)) {
			checkType = AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER + "," + AppConstants.POS_ITEM_LOG_CHECKORDER
					+ "," + AppConstants.POS_ITEM_LOG_COST_ADJUST;
		}

		List<Object[]> objects = posItemLogService.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo,
				checkType, itemNums, storehouseNum, reasons);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
			boolean flag = (Boolean) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			}
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			BigDecimal saleMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			String itemUnit = object[8] == null ? "" : (String) object[8];
            BigDecimal adjustMoney = object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9];
            if(flag){
                money = money.subtract(adjustMoney);
            }
			if (isChechUp != null) {
				if (isChechUp != null) {
					if (isChechUp) {
						if (!flag) {
							continue;
						}
					} else {
						if (flag) {
							continue;
						}
					}
				}
			}

			InventoryProfitDTO data = map.get(itemNum + "|" + itemMatrixNum);
			if (data == null) {
				data = new InventoryProfitDTO();
				data.setItemNum(itemNum);
				data.setItemMatrixNum(itemMatrixNum);
				data.setItemUnit(itemUnit);
				map.put(itemNum + "|" + itemMatrixNum, data);
			}

			if (!flag) {
				amount = amount.negate();
				money = money.negate();
				saleMoney = saleMoney.negate();
				assistAmount = assistAmount.negate();
                adjustMoney = adjustMoney.negate();
			}
			data.setProfitAssitQty(data.getProfitAssitQty().add(assistAmount));
			data.setProfitQty(data.getProfitQty().add(amount));
			data.setProfitMoney(data.getProfitMoney().add(money));
			data.setItemSaleMoney(data.getItemSaleMoney().add(saleMoney));
            data.setAdjustMoney(data.getAdjustMoney().add(adjustMoney));

		}

		if (!unit.equals(AppConstants.UNIT_USE)) {
			checkType = AppConstants.POS_ITEM_LOG_POS + "," + AppConstants.POS_ITEM_LOG_CONSUME_POINT + ","
					+ AppConstants.POS_ITEM_LOG_ANTI_POS;
			objects = posItemLogService.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo, checkType,
					itemNums, storehouseNum, null);


			BigDecimal qty = BigDecimal.ZERO;
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				Integer itemMatrixNum = object[1] == null ? 0 : (Integer) object[1];
				boolean flag = (Boolean) object[2];
				BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];

				InventoryProfitDTO data = map.get(itemNum + "|" + itemMatrixNum);
				if (data == null) {
					data = new InventoryProfitDTO();
					data.setItemNum(itemNum);
					data.setItemMatrixNum(itemMatrixNum);
					map.put(itemNum + "|" + itemMatrixNum, data);
				}
				if (flag) {
					amount = amount.negate();
					money = money.negate();
					assistAmount = assistAmount.negate();
				}
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(money));
				data.setSaleAssitQty(data.getSaleAssitQty().add(assistAmount));
				qty = qty.add(amount);
			}
		}
		List<InventoryProfitDTO> list = new ArrayList<InventoryProfitDTO>(map.values());
		BigDecimal profitQtySum = BigDecimal.ZERO;
		BigDecimal profitAssitQtySum = BigDecimal.ZERO;
		BigDecimal profitMoneySum = BigDecimal.ZERO;
		BigDecimal saleQtySum = BigDecimal.ZERO;
		BigDecimal saleAssitQtySum = BigDecimal.ZERO;
		BigDecimal saleMoneySum = BigDecimal.ZERO;
		BigDecimal itemSaleMoneySum = BigDecimal.ZERO;
		BigDecimal adjustMoneySum = BigDecimal.ZERO;
		for (int i = list.size() - 1; i >= 0; i--) {
			if (queryData.getIsChechZero()) {
				if (list.get(i).getProfitQty().doubleValue() == 0.00) {
					list.remove(i);
				}
			} else {
				if (list.get(i).getProfitQty().doubleValue() == 0.00 && list.get(i).getProfitMoney().doubleValue() == 0.00
						&& list.get(i).getSaleQty().doubleValue() == 0.00) {
					list.remove(i);
				}
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			InventoryProfitDTO data = list.get(i);
			Integer itemNum = data.getItemNum();
			if (data.getProfitQty().compareTo(BigDecimal.ZERO) == 0
					&& data.getProfitMoney().compareTo(BigDecimal.ZERO) == 0) {
				if (data.getSaleQty().compareTo(BigDecimal.ZERO) == 0 && data.getSaleAssitQty().compareTo(BigDecimal.ZERO) == 0
						&& data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
					list.remove(i);
					continue;
				}
			}
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (data.getSaleQty().compareTo(BigDecimal.ZERO) > 0) {
				data.setProfitRate(data.getProfitQty().divide(data.getSaleQty(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2));
			} else {
				data.setProfitRate(BigDecimal.valueOf(9999.99));
			}
			data.setItemCategoryCode(posItem.getItemCategoryCode());
			data.setItemCategoryName(posItem.getItemCategory());
			data.setItemCode(posItem.getItemCode());
			data.setItemName(posItem.getItemName());
			data.setItemSpec(posItem.getItemSpec());
			if (!unit.equals(AppConstants.UNIT_USE)) {
				data.setItemUnit(posItem.getItemUnit());
			}
			ItemMatrix itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), itemNum,
					data.getItemMatrixNum());
			if (itemMatrix != null) {
				data.setItemName(data.getItemName().concat(AppUtil.getMatrixName(itemMatrix)));
				data.setItemMatrixName(AppUtil.getMatrixName(itemMatrix));
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				data.setItemUnit(posItem.getItemInventoryUnit());
				if(posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				data.setItemUnit(posItem.getItemPurchaseUnit());
				if(posItem.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_TRANFER)) {
				data.setItemUnit(posItem.getItemTransferUnit());
				if(posItem.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				data.setItemUnit(posItem.getItemWholesaleUnit());
				if(posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			}
			profitQtySum = profitQtySum.add(data.getProfitQty());
			profitAssitQtySum = profitAssitQtySum.add(data.getProfitAssitQty());
			profitMoneySum = profitMoneySum.add(data.getProfitMoney());
			saleQtySum = saleQtySum.add(data.getSaleQty());
			saleAssitQtySum = saleAssitQtySum.add(data.getSaleAssitQty());
			saleMoneySum = saleMoneySum.add(data.getSaleMoney());
			itemSaleMoneySum = itemSaleMoneySum.add(data.getItemSaleMoney());
            adjustMoneySum = adjustMoneySum.add(data.getAdjustMoney());
		}
		if (sortField == null) {
			sortField = "itemCode";
			sortType = "ASC";
		}

		ComparatorBaseModelData<InventoryProfitDTO> comparator = new ComparatorBaseModelData<InventoryProfitDTO>(sortField, sortType, InventoryProfitDTO.class);
		Collections.sort(list, comparator);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(i);
		}
		int dataSize = list.size();
		InventoryProfitPageDTO result = new InventoryProfitPageDTO();
		result.setCount(dataSize);
		result.setData(list);


        result.setAdjustMoneySum(adjustMoneySum);
		result.setProfitQtySum(profitQtySum);
		result.setProfitAssitQtySum(profitAssitQtySum);
		result.setProfitMoneySum(profitMoneySum);
		result.setSaleQtySum(saleQtySum);
		result.setSaleAssitQtySum(saleAssitQtySum);
		result.setSaleMoneySum(saleMoneySum);
		result.setItemSaleMoneySum(itemSaleMoneySum);
		if (saleQtySum.compareTo(BigDecimal.ZERO) > 0) {
			result.setProfitRateSum(profitQtySum.divide(saleQtySum, 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(100)).setScale(2));
		}
		int pageSum = offset + limit;
		if (queryData.isPage()) {
			List<InventoryProfitDTO> subList = null;
			if (dataSize >= pageSum) {
				subList = list.subList(offset, pageSum);
			} else {
				subList = list.subList(offset, dataSize);
			}
			result.setData(subList);
		}

		return result;

	}



	@Override
	public InventoryProfitPageDTO findInventoryProfitSum(InventoryProfitQuery queryData){

		int offset = queryData.getOffset();
		int limit = queryData.getLimit();


		String sortField = queryData.getSortField();
		String sortType = queryData.getSortType();

		String systemBookCode = queryData.getSystemBookCode();
		List<Integer> branchNums = queryData.getBranchNums();
		Integer storehouseNum = queryData.getStoreNum();
		Date dateFrom = queryData.getDateFrom();
		Date dateTo = queryData.getDateTo();
		List<String> categoryCodes = queryData.getCategoryCodes();
		List<Integer> itemNums = queryData.getItemNums();
		String checkType = queryData.getCheckType();
		Boolean isChechUp = queryData.getIsChechUp();
		List<String> reasons = queryData.getReasons();
		String unit = queryData.getUnit();

		if (StringUtils.isEmpty(unit)) {
			unit = AppConstants.UNIT_BASIC;
		}

		Map<Integer, InventoryProfitDTO> map = new HashMap<Integer, InventoryProfitDTO>();
		List<PosItem> posItems = posItemService.findShortItems(systemBookCode);

		if (StringUtils.isEmpty(checkType)) {
			checkType = AppConstants.POS_ITEM_LOG_ADJUSTMENTORDER + "," + AppConstants.POS_ITEM_LOG_CHECKORDER
					+ "," + AppConstants.POS_ITEM_LOG_COST_ADJUST;
		}

		List<PosItemTypeParam> posItemTypeParams = null;
		if (queryData.isUseTopCategory()) {
			posItemTypeParams = bookResourceService.findPosItemTypeParams(systemBookCode);
		}
		List<Object[]> objects = posItemLogService.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo,
				checkType, itemNums, storehouseNum, reasons);
		for (int i = 0; i < objects.size(); i++) {
			Object[] object = objects.get(i);
			Integer itemNum = (Integer) object[0];
			boolean flag = (Boolean) object[2];
			BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
			if (unit.equals(AppConstants.UNIT_USE)) {
				amount = object[6] == null ? BigDecimal.ZERO : (BigDecimal) object[6];
			}
			BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
			BigDecimal saleMoney = object[7] == null ? BigDecimal.ZERO : (BigDecimal) object[7];
			BigDecimal assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];
			String itemUnit = object[8] == null ? "" : (String) object[8];
            BigDecimal adjustMoney = object[9] == null ? BigDecimal.ZERO : (BigDecimal) object[9];
            if(flag){
                money = money.subtract(adjustMoney);
            }

			if (isChechUp != null) {
				if (isChechUp != null) {
					if (isChechUp) {
						if (!flag) {
							continue;
						}
					} else {
						if (flag) {
							continue;
						}
					}
				}
			}

			InventoryProfitDTO data = map.get(itemNum);
			if (data == null) {
				data = new InventoryProfitDTO();
				data.setItemNum(itemNum);
				data.setItemUnit(itemUnit);
				map.put(itemNum, data);
			}
			if (!flag) {
				amount = amount.negate();
				money = money.negate();
				saleMoney = saleMoney.negate();
				assistAmount = assistAmount.negate();
                adjustMoney = adjustMoney.negate();
			}
			data.setProfitAssitQty(data.getProfitAssitQty().add(assistAmount));
			data.setProfitQty(data.getProfitQty().add(amount));
			data.setProfitMoney(data.getProfitMoney().add(money));
			data.setItemSaleMoney(data.getItemSaleMoney().add(saleMoney));
            data.setAdjustMoney(data.getAdjustMoney().add(adjustMoney));
		}
		if (!unit.equals(AppConstants.UNIT_USE)) {
			checkType = AppConstants.POS_ITEM_LOG_POS + "," + AppConstants.POS_ITEM_LOG_CONSUME_POINT + ","
					+ AppConstants.POS_ITEM_LOG_ANTI_POS;
			objects = posItemLogService.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo, checkType,
					itemNums, storehouseNum, null);
			for (int i = 0; i < objects.size(); i++) {
				Object[] object = objects.get(i);
				Integer itemNum = (Integer) object[0];
				boolean flag = (Boolean) object[2];
				BigDecimal amount = object[3] == null ? BigDecimal.ZERO : (BigDecimal) object[3];
				BigDecimal money = object[4] == null ? BigDecimal.ZERO : (BigDecimal) object[4];
				BigDecimal assistAmount = object[5] == null ? BigDecimal.ZERO : (BigDecimal) object[5];

				InventoryProfitDTO data = map.get(itemNum);
				if (data == null) {
					data = new InventoryProfitDTO();
					data.setItemNum(itemNum);
					map.put(itemNum, data);
				}
				if (flag) {
					amount = amount.negate();
					money = money.negate();
					assistAmount = assistAmount.negate();
				}
				data.setSaleQty(data.getSaleQty().add(amount));
				data.setSaleMoney(data.getSaleMoney().add(money));
				data.setSaleAssitQty(data.getSaleAssitQty().add(assistAmount));
			}
		}
		Map<String, InventoryProfitDTO> categoryMap = new HashMap<String, InventoryProfitDTO>();
		List<InventoryProfitDTO> list = new ArrayList<InventoryProfitDTO>(map.values());
		BigDecimal profitQtySum = BigDecimal.ZERO;
		BigDecimal profitAssitQtySum = BigDecimal.ZERO;
		BigDecimal profitMoneySum = BigDecimal.ZERO;
		BigDecimal saleQtySum = BigDecimal.ZERO;
		BigDecimal saleAssitQtySum = BigDecimal.ZERO;
		BigDecimal saleMoneySum = BigDecimal.ZERO;
		BigDecimal itemSaleMoneySum = BigDecimal.ZERO;
        BigDecimal adjustMoneySum = BigDecimal.ZERO;
		for (int i = list.size() - 1; i >= 0; i--) {
			InventoryProfitDTO data = list.get(i);
			Integer itemNum = data.getItemNum();
			PosItem posItem = AppUtil.getPosItem(itemNum, posItems);
			if (posItem == null) {
				list.remove(i);
				continue;
			}
			if (categoryCodes != null && categoryCodes.size() > 0) {
				if (!categoryCodes.contains(posItem.getItemCategoryCode())) {
					list.remove(i);
					continue;
				}
			}
			if (data.getProfitQty().compareTo(BigDecimal.ZERO) == 0
					&& data.getProfitMoney().compareTo(BigDecimal.ZERO) == 0) {
				if (data.getSaleQty().compareTo(BigDecimal.ZERO) == 0 && data.getSaleAssitQty().compareTo(BigDecimal.ZERO) == 0
						&& data.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
					list.remove(i);
					continue;
				}
			}
			if (unit.equals(AppConstants.UNIT_SOTRE)) {
				data.setItemUnit(posItem.getItemInventoryUnit());
				if(posItem.getItemInventoryRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemInventoryRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
				data.setItemUnit(posItem.getItemPurchaseUnit());
				if(posItem.getItemPurchaseRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemPurchaseRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_TRANFER)) {
				data.setItemUnit(posItem.getItemTransferUnit());
				if(posItem.getItemTransferRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemTransferRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			} else if (unit.equals(AppConstants.UNIT_PIFA)) {
				data.setItemUnit(posItem.getItemWholesaleUnit());
				if(posItem.getItemWholesaleRate().compareTo(BigDecimal.ZERO) != 0){
					data.setProfitQty(data.getProfitQty().divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP));
					data.setSaleQty(data.getSaleQty().divide(posItem.getItemWholesaleRate(), 4, BigDecimal.ROUND_HALF_UP));
				}
			}
			String code = posItem.getItemCategoryCode();
			String name = posItem.getItemCategory();
			if (posItemTypeParams != null) {
				code = AppUtil.getTopCategoryCode(posItemTypeParams, posItem.getItemCategoryCode());
				name = AppUtil.getPosItemTypeName(posItemTypeParams,code);
			}
			InventoryProfitDTO categoryData = categoryMap.get(code + "|"
					+ name);
			if (categoryData == null) {
				categoryData = new InventoryProfitDTO();
				if (posItemTypeParams != null) {
					categoryData.setItemCategoryCode(code);
					categoryData.setItemCategoryName(name);
					categoryMap.put(code + "|" + name, categoryData);
				} else {
					categoryData.setItemCategoryCode(posItem.getItemCategoryCode());
					categoryData.setItemCategoryName(posItem.getItemCategory());
					categoryMap.put(posItem.getItemCategoryCode() + "|" + posItem.getItemCategory(), categoryData);
				}
			}
			categoryData.setProfitAssitQty(categoryData.getProfitAssitQty().add(data.getProfitAssitQty()));
			categoryData.setProfitMoney(categoryData.getProfitMoney().add(data.getProfitMoney()));
			categoryData.setProfitQty(categoryData.getProfitQty().add(data.getProfitQty()));
			categoryData.setSaleAssitQty(categoryData.getSaleAssitQty().add(data.getSaleAssitQty()));
			categoryData.setSaleMoney(categoryData.getSaleMoney().add(data.getSaleMoney()));
			categoryData.setSaleQty(categoryData.getSaleQty().add(data.getSaleQty()));
			categoryData.setItemSaleMoney(categoryData.getItemSaleMoney().add(data.getItemSaleMoney()));
            categoryData.setAdjustMoney(categoryData.getAdjustMoney().add(data.getAdjustMoney()));

		}
		list = new ArrayList<InventoryProfitDTO>(categoryMap.values());
		for (int i = list.size() - 1; i >= 0; i--) {
			if (queryData.getIsChechZero()) {
				if (list.get(i).getProfitQty().doubleValue() == 0.00) {
					list.remove(i);
				}
			} else {
				if (list.get(i).getProfitQty().doubleValue() == 0.00 && list.get(i).getProfitMoney().doubleValue() == 0.00
						&& list.get(i).getSaleQty().doubleValue() == 0.00) {
					list.remove(i);
				}
			}
		}
		for (int i = list.size() - 1; i >= 0; i--) {

			InventoryProfitDTO data = list.get(i);

			if (data.getSaleQty().compareTo(BigDecimal.ZERO) > 0) {
				data.setProfitRate(data.getProfitQty().divide(data.getSaleQty(), 4, BigDecimal.ROUND_HALF_UP)
						.multiply(BigDecimal.valueOf(100)).setScale(2));
			} else {
				data.setProfitRate(BigDecimal.valueOf(9999.99));
			}

			profitQtySum = profitQtySum.add(data.getProfitQty());
			profitAssitQtySum = profitAssitQtySum.add(data.getProfitAssitQty());
			profitMoneySum = profitMoneySum.add(data.getProfitMoney());
			saleQtySum = saleQtySum.add(data.getSaleQty());
			saleAssitQtySum = saleAssitQtySum.add(data.getSaleAssitQty());
			saleMoneySum = saleMoneySum.add(data.getSaleMoney());
			itemSaleMoneySum = itemSaleMoneySum.add(data.getItemSaleMoney());
			adjustMoneySum = adjustMoneySum.add(data.getAdjustMoney());
		}
		if (sortField == null) {
			sortField = "itemCategoryCode";
			sortType = "ASC";
		}
		ComparatorBaseModelData<InventoryProfitDTO> comparator = new ComparatorBaseModelData<>(sortField, sortType, InventoryProfitDTO.class);
		Collections.sort(list, comparator);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(i);
		}
		int dataSize = list.size();
		InventoryProfitPageDTO result = new InventoryProfitPageDTO();
		result.setCount(dataSize);
		result.setData(list);

        result.setAdjustMoneySum(adjustMoneySum);
		result.setProfitQtySum(profitQtySum);
		result.setProfitAssitQtySum(profitAssitQtySum);
		result.setProfitMoneySum(profitMoneySum);
		result.setSaleQtySum(saleQtySum);
		result.setSaleAssitQtySum(saleAssitQtySum);
		result.setSaleMoneySum(saleMoneySum);
		result.setItemSaleMoneySum(itemSaleMoneySum);
		if (saleQtySum.compareTo(BigDecimal.ZERO) > 0) {
			result.setProfitRateSum(profitQtySum.divide(saleQtySum, 4, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(100)).setScale(2));
		}

		int pageSum = offset + limit;
		if(queryData.isPage()){
			List<InventoryProfitDTO> subList = null;
			if(dataSize >= pageSum){
				subList = list.subList(offset,pageSum);
			}else{
				subList = list.subList(offset,dataSize);
			}
			result.setData(subList);
		}


		return result;

	}

	private TransferProfitByPosItemDTO getTransferProfitByPosItemData(List<TransferProfitByPosItemDTO> list ,Integer inBranchNum,Integer branchNum, Integer itemNum, Integer itemMatrixNum){
		if(list == null ){
			return null;
		}
		for (int i = 0,len = list.size(); i < len; i++) {
			TransferProfitByPosItemDTO data = list.get(i);
			if(inBranchNum.equals(data.getTranferBranchNum()) && branchNum.equals(data.getBranchNum())
					&& itemNum.equals(data.getItemNum()) && itemMatrixNum.equals(data.getItemMatrixNum())){
				return data;
			}
		}
		return null;
	}


	@Override
	public AllOperatePageDTO findCardUserOperate(CardReportQuery cardReportQuery){

			boolean page  = cardReportQuery.isPaging();
			int offset = cardReportQuery.getOffset();
			int limit = cardReportQuery.getLimit();
			String sortField = cardReportQuery.getSortField();
			String sortType = cardReportQuery.getSortType();
		    cardReportQuery.setSortField(null);
			cardReportQuery.setPaging(false);
			List<AllOperateSummary> list = new ArrayList<AllOperateSummary>();


			if (cardReportQuery.getBranchNums() == null || cardReportQuery.getBranchNums().size() == 0) {
				List<BranchDTO> branchs = branchRpc.findInCache(cardReportQuery.getSystemBookCode());
				List<Integer> branchNums = new ArrayList<Integer>();
				for(int i = 0;i < branchs.size();i++){
					BranchDTO branch = branchs.get(i);
					branchNums.add(branch.getBranchNum());
				}
				cardReportQuery.setBranchNums(branchNums);
			}

			List<CardUserDTO> cardUsers = cardUserRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);

			List<Branch> branchs = branchService.findInCache(cardReportQuery.getSystemBookCode());
			List<CardUserType> cardUserTypes = bookResourceService.findCardUserTypesInCache(cardReportQuery.getSystemBookCode());
			for(int i = 0;i < cardUsers.size();i++){
				CardUserDTO cardUser = cardUsers.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("发卡");
				data.setReportDate(cardUser.getCardUserDate());
				data.setReportOperator(cardUser.getCardUserOperator());
				data.setReportCustName(cardUser.getCardUserCustName());
				data.setReportPrintedNum(cardUser.getCardUserPrintedNum());
				data.setReportMemo("表面卡号："+cardUser.getCardUserPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(cardUser.getCardUserCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
					data.setReportMemo(data.getReportMemo() + " 会员卡类型;"+cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				Branch branch = AppUtil.getBranch(branchs, cardUser.getCardUserEnrollShop());
				if(branch != null){
					data.setReportOperateBranch(branch.getBranchName());
				}
				list.add(data);
			}

			List<CardLossDTO> cardLosses = cardLossRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < cardLosses.size();i++){
				CardLossDTO cardLoss = cardLosses.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType(cardLoss.getCardLossOperateName());
				data.setReportDate(cardLoss.getCardLossOperateTime());
				data.setReportOperator(cardLoss.getCardLossOperator());
				data.setReportCustName(cardLoss.getCardLossCustName());
				data.setReportPrintedNum(cardLoss.getCardLossPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(cardLoss.getCardLossCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(cardLoss.getCardLossBranchName());
				data.setReportMemo("挂失：表面卡号 = "+cardLoss.getCardLossPrintedNum());
				list.add(data);
			}

			List<ReplaceCardDTO> replaceCards = replaceCardRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < replaceCards.size();i++){
				ReplaceCardDTO replaceCard = replaceCards.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("换卡");
				data.setReportDate(replaceCard.getReplaceCardOperateTime());
				data.setReportOperator(replaceCard.getReplaceCardOperator());
				data.setReportCustName(replaceCard.getRepalceCardCustName());
				data.setReportPrintedNum(replaceCard.getReplaceCardOldPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(replaceCard.getReplaceCardCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(replaceCard.getReplaceCardBranchName());
				data.setReportMemo("原卡号 = "+replaceCard.getReplaceCardOldPrintedNum()+
						";新卡号"+replaceCard.getReplaceCardNewPrintedNum());
				list.add(data);
			}

			List<RelatCardDTO> relatCards = relatCardRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < relatCards.size();i++){
				RelatCardDTO relatCard = relatCards.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("续卡");
				data.setReportDate(relatCard.getRelatCardOperateTime());
				data.setReportOperator(relatCard.getRelatCardOperator());
				data.setReportCustName(relatCard.getRelatCardCustName());
				data.setReportPrintedNum(relatCard.getRelatCardPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(relatCard.getRelatCardCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(relatCard.getRelatCardBranchName());
				data.setReportMemo("原有效期= "+DateUtil.getDateStr(relatCard.getRelatCardOldDeadline())+
						";新有效期"+DateUtil.getDateStr(relatCard.getRelatCardNewDeadline()));
				list.add(data);
			}

			List<CardConsumeDTO> cardConsumes = cardConsumeRpc.findByCardReportQuery(cardReportQuery, offset, limit);
			for(int i = 0;i < cardConsumes.size();i++){
				CardConsumeDTO cardConsume = cardConsumes.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("消费");
				data.setReportDate(cardConsume.getConsumeDate());
				data.setReportOperator(cardConsume.getConsumeOperator());
				data.setReportCustName(cardConsume.getConsumeCustName());
				data.setReportPrintedNum(cardConsume.getConsumePrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(cardConsume.getConsumeCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(cardConsume.getConsumeBranchName());
				data.setReportMemo("消费前余额= "+cardConsume.getConsumeBalance().setScale(2, RoundingMode.HALF_UP)+
						";消费金额"+cardConsume.getConsumeMoney().setScale(2, RoundingMode.HALF_UP));
				list.add(data);
			}

			List<CardDepositDTO> cardDeposits = cardDepositRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < cardDeposits.size();i++){
				CardDepositDTO cardDeposit = cardDeposits.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("存款");
				data.setReportDate(cardDeposit.getDepositDate());
				data.setReportOperator(cardDeposit.getDepositOperator());
				data.setReportCustName(cardDeposit.getDepositCustName());
				data.setReportPrintedNum(cardDeposit.getDepositPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(cardDeposit.getDepositCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(cardDeposit.getDepositBranchName());
				data.setReportMemo("存款前余额= "+cardDeposit.getDepositBalance().setScale(2, RoundingMode.HALF_UP)+
						";实存金额"+cardDeposit.getDepositCash().setScale(2, RoundingMode.HALF_UP)+"存款金额："+cardDeposit.getDepositMoney().setScale(2, RoundingMode.HALF_UP)+
						"付款方式="+cardDeposit.getDepositPaymentTypeName());
				list.add(data);
			}

			List<ConsumePointDTO> consumePoints = consumePointRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < consumePoints.size();i++){
				ConsumePointDTO consumePoint = consumePoints.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType("积分兑换");
				data.setReportDate(consumePoint.getConsumePointDate());
				data.setReportOperator(consumePoint.getConsumePointOperator());
				data.setReportCustName(consumePoint.getConsumePointCustName());
				data.setReportPrintedNum(consumePoint.getConsumePointPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(consumePoint.getConsumePointCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(consumePoint.getConsumePointBranchName());
				data.setReportMemo(consumePoint.getConsumePointMemo());
				list.add(data);
			}

			List<CardUserLogDTO> cardUserLogs = cardUserRegisterRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < cardUserLogs.size();i++){
				CardUserLogDTO consumePoint = cardUserLogs.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType(consumePoint.getCardUserLogType());
				data.setReportDate(consumePoint.getCardUserLogTime());
				data.setReportOperator(consumePoint.getCardUserLogOperator());
				data.setReportCustName(consumePoint.getCardUserCustName());
				data.setReportPrintedNum(consumePoint.getCardUserPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(consumePoint.getCardUserCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(consumePoint.getCardUserLogBranchName());
				data.setReportMemo(consumePoint.getCardUserLogMemo());
				list.add(data);
			}


			cardUserLogs = cardUserLogRpc.findByCardReportQuery(cardReportQuery.getSystemBookCode(), cardReportQuery, offset, limit);
			for(int i = 0;i < cardUserLogs.size();i++){
				CardUserLogDTO consumePoint = cardUserLogs.get(i);
				AllOperateSummary data = new AllOperateSummary();
				data.setReportType(consumePoint.getCardUserLogType());
				data.setReportDate(consumePoint.getCardUserLogTime());
				data.setReportOperator(consumePoint.getCardUserLogOperator());
				data.setReportCustName(consumePoint.getCardUserCustName());
				data.setReportPrintedNum(consumePoint.getCardUserPrintedNum());
				CardUserType cardUserType = AppUtil.readCardType(consumePoint.getCardUserCardType(), cardUserTypes);
				if(cardUserType != null){
					data.setCardUserTypeName(cardUserType.getTypeName());
				}else {
					data.setCardUserTypeName("");
				}
				data.setReportOperateBranch(consumePoint.getCardUserLogBranchName());
				data.setReportMemo(consumePoint.getCardUserLogMemo());
				list.add(data);
			}
			ComparatorBaseModelData<AllOperateSummary> comparator = new ComparatorBaseModelData<>(sortField, sortType, AllOperateSummary.class);
			Collections.sort(list, comparator);


			int dataSize = list.size();
		    AllOperatePageDTO result = new AllOperatePageDTO();
		    result.setCount(dataSize);
			result.setData(list);

			int pageSum = offset + limit;
			if(page){
				List<AllOperateSummary> subList = null;
				if(dataSize >= pageSum){
					subList = list.subList(offset,pageSum);
				}else{
					subList = list.subList(offset,dataSize);
				}
				result.setData(subList);
			}
			return result;
		}

    @Override
    public List<InventoryAnalysisDTO> findInventoryAnalysiss(InventoryAnalysisQuery inventoryAnalysisQuery, ChainDeliveryParam chainDeliveryParam) {

		String systemBookCode = inventoryAnalysisQuery.getSystemBookCode();
		Integer branchNum = inventoryAnalysisQuery.getBranchNum();
		Date now = DateUtil.getMinOfDate(Calendar.getInstance().getTime());
		Date yesterday = DateUtil.addDay(now, -1);
		if (inventoryAnalysisQuery.getSuggestionType() == null) {
			inventoryAnalysisQuery.setSuggestionType(1);
		}
		List<Integer> branchNums = new ArrayList<Integer>();
		branchNums.add(branchNum);
		BranchDTO branch = branchRpc.readInCache(systemBookCode, branchNum);
		List<InventoryAnalysisDTO> list = new ArrayList<InventoryAnalysisDTO>();
		List<Inventory> inventories = null;
		List<Integer> itemNums = inventoryAnalysisQuery.getItemNums();

		if(inventoryAnalysisQuery.getSupplierNum() != null){
			inventoryAnalysisQuery.setSupplierNums(new ArrayList<>());
			inventoryAnalysisQuery.getSupplierNums().add(inventoryAnalysisQuery.getSupplierNum());
		}


		//手工添加补货的商品忽略规则
		List<Integer> ignoreRuleItemNums = null;
		if(itemNums != null && !itemNums.isEmpty()){
			ignoreRuleItemNums = new ArrayList<>(itemNums.size());
			ignoreRuleItemNums.addAll(itemNums);
		}

		if (inventoryAnalysisQuery.getStorehouseNum() == null) {
			inventories = inventoryService.findByItemAndBranch(systemBookCode, inventoryAnalysisQuery.getBranchNum(), itemNums,
					true);
		} else {
			inventories = inventoryService.findByStorehouseNum(inventoryAnalysisQuery.getStorehouseNum(), itemNums);
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

		List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
		if (inventoryAnalysisQuery.isQueryAssemble()) {
			if(itemNums != null && itemNums.size() > 0){
				List<PosItemDTO> tempPosItems = posItemRpc.findByItemNums(systemBookCode,itemNums);
				List<Integer> manufactureItemNums = new ArrayList<Integer>();
				for (int i = 0; i < tempPosItems.size(); i++) {
					PosItemDTO posItem = tempPosItems.get(i);
					posItem.setPosItemKits(new ArrayList<PosItemKitDTO>());

					if(posItem.getItemManufactureFlag() != null && posItem.getItemManufactureFlag()){
						manufactureItemNums.add(posItem.getItemNum());
					}
				}
				if(manufactureItemNums.size() > 0 ){
					List<PosItemKitDTO> posItemKits = posItemRpc.findPosItemKitsWithDetails(systemBookCode,manufactureItemNums);
					for (int i = 0; i < tempPosItems.size(); i++) {
						PosItemDTO posItem = tempPosItems.get(i);
						posItem.setPosItemKits(PosItemKitDTO.find(posItemKits, posItem.getItemNum()));

					}
				}
				posItems.addAll(tempPosItems);

			} else {
				posItems.addAll(posItemRpc.findShortItems(systemBookCode));

			}
			if(ignoreRuleItemNums == null){

				for (int i = posItems.size() - 1; i >= 0; i--) {
					PosItemDTO posItem = posItems.get(i);
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

					if (org.apache.commons.lang.StringUtils.equals(posItem.getItemMethod(), AppConstants.BUSINESS_TYPE_SHOP)) {
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
					if (org.apache.commons.lang.StringUtils.isNotEmpty(inventoryAnalysisQuery.getPosItemDepartmentCode())) {
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

				itemNums.addAll(inventoryAnalysisQuery.getStoreItemNums());
				posItemQuery.setItemNums(itemNums);
			}

			posItemQuery.setPaging(false);
			posItemQuery.setOrderType(AppConstants.POS_ITEM_LOG_PURCHASE_ORDER);
			posItemQuery.setFilterType(AppConstants.ITEM_TYPE_PURCHASE);
			posItemQuery.setIsFindNoStock(false);
			posItemQuery.setRdc(branch.isRdc());
			posItems = posItemRpc.findByPosItemQuery(posItemQuery, null, null, 0, 0);
		}

		List<Integer> matrixItemNums = new ArrayList<Integer>();
		for (int i = 0; i < posItems.size(); i++) {
			PosItemDTO posItem = posItems.get(i);

			List<ItemMatrixDTO> itemMatrixs = posItem.getItemMatrixs();
			InventoryAnalysisDTO dto = new InventoryAnalysisDTO();
			dto.setPosItem(posItem);
			dto.setItemNum(posItem.getItemNum());
			dto.setItemMatrixNum(0);
			dto.getPosItem().getItemMatrixs().clear();
			dto.setItemMinQuantity(posItem.getItemMinQuantity());


			boolean ignoreRule = false;
			if(ignoreRuleItemNums != null && ignoreRuleItemNums.contains(posItem.getItemNum())){
				ignoreRule = true;
			}
			if (posItem.getItemType() == AppConstants.C_ITEM_TYPE_MATRIX) {

				for (int j = 0; j < itemMatrixs.size(); j++) {
					ItemMatrixDTO itemMatrix = itemMatrixs.get(j);
					InventoryAnalysisDTO matrixDTO = new InventoryAnalysisDTO();
					Object[] objects = AppUtil.getInventoryAmount(inventories, posItem, itemMatrix.getItemMatrixNum(), null, branch);
					if (!(Boolean) objects[4] &&  !ignoreRule) {
						continue;
					}
					matrixDTO.setInventoryQty((BigDecimal) objects[0]);
					matrixDTO.setPosItem(posItem);
					matrixDTO.setItemNum(posItem.getItemNum());
					matrixDTO.setItemMatrixNum(itemMatrix.getItemMatrixNum());
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
			List<Object[]> purchaseObjects = purchaseOrderService.findUnReceivedItemAmount(systemBookCode, branchNum, itemNums);
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
		Date dateFrom = DateUtil.addDay(yesterday, -(inventoryAnalysisQuery.getLastDays() - 1));
		List<Object[]> objects = reportService.findItemSaleQty(systemBookCode, branchNum, dateFrom, yesterday,
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
		if (!inventoryAnalysisQuery.isFindCount()) {

			if (inventoryAnalysisQuery.isFindAdjustOut()) {

				objects = adjustmentOrderService.findItemSummary(systemBookCode, branchNums, dateFrom, yesterday, null,
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

			List<Object[]> requestObjects = requestOrderService.findCenterRequestMatrixAmount(systemBookCode,
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

			List<Object[]> receiveObjects = receiveOrderService.findItemMatrixMaxProducingDates(systemBookCode, branchNum,
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

		List<StoreMatrix> storeMatrixs = storeMatrixService.findByBranch(systemBookCode, branchNum, itemNums);

		List<StoreMatrixDetail> storeMatrixDetails = new ArrayList<StoreMatrixDetail>();
		if (matrixItemNums.size() > 0) {
			storeMatrixDetails = storeMatrixService.findDetails(systemBookCode, branchNum, matrixItemNums);
		}
		List<StoreItemSupplier> storeItemSuppliers = new ArrayList<StoreItemSupplier>();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		if (!inventoryAnalysisQuery.isQueryAssemble()) {
			storeItemSuppliers = storeItemSupplierService.find(systemBookCode, branchNum, null, false, null);
			suppliers = supplierService.findInCache(systemBookCode);
		}

		// 查询今日制单的加工入库数量
		if (inventoryAnalysisQuery.isQueryAssemble()) {

			List<String> reasons = new ArrayList<String>();
			reasons.add("加工入库单");
			List<Object[]> todayAdjustmentObjects = adjustmentOrderService.findItemSummary(systemBookCode, branchNums, now,
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

			boolean ignoreRule = false;
			if(ignoreRuleItemNums != null && ignoreRuleItemNums.contains(itemNum)){
				ignoreRule = true;
			}

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
					if(!ignoreRule && (itemStoreItemSupplier.getStoreItemSupplierDefault() == null || !itemStoreItemSupplier.getStoreItemSupplierDefault())){
						continue;
					}
				}
				if (inventoryAnalysisQuery.getSupplierNums() != null && !inventoryAnalysisQuery.getSupplierNums().isEmpty()) {
					if (!ignoreRule && !inventoryAnalysisQuery.getSupplierNums().contains(itemStoreItemSupplier.getId().getSupplierNum())) {
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
						supplier.setSupplierPurchaseDate(purchaseOrderService.getLastDate(supplier.getSystemBookCode(),
								supplier.getBranchNum(), itemStoreItemSupplier.getId().getSupplierNum()));

					}
					SupplierDTO itemSupplier = new SupplierDTO();
					BeanUtils.copyProperties(supplier, itemSupplier);
					itemSupplier.setStoreItemReturnType(itemStoreItemSupplier.getStoreItemReturnType());
					data.getSuppliers().add(itemSupplier);
				}
			}
			if (inventoryAnalysisQuery.getSupplierNums() != null && !inventoryAnalysisQuery.getSupplierNums().isEmpty()) {
				if (!ignoreRule && data.getSuppliers().isEmpty()) {
					list.remove(i);
					continue;
				}

			}
			if (inventoryAnalysisQuery.getSuggestionType() == 1) {
				if (data.getSuppliers().size() > 0) {
					SupplierDTO supplier = data.getSuppliers().get(0);
					if (supplier.getSupplierPurchasePeriod() != null) {
						if (org.apache.commons.lang.StringUtils.isEmpty(supplier.getSupplierPurchasePeriodUnit())) {
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
					&& data.getAvgSaleQty().abs().compareTo(BigDecimal.valueOf(0.001)) < 0
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


}
