package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.query.*;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

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
	private PosOrderRpc posOrderRpc;


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
		for (int i = 0; i < wholesaleOrders.size(); i++) {
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
						.divide(multipleProfitReportDTO.getWholesaleMoney(), 2, BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}

			multipleProfitReportDTO.setTranferProfit(multipleProfitReportDTO.getTranferMoney().subtract(multipleProfitReportDTO.getTranferCost()));
			if (multipleProfitReportDTO.getTranferMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setTranferProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setTranferProfitRate(multipleProfitReportDTO.getTranferProfit().divide(
						multipleProfitReportDTO.getTranferMoney(), 2, BigDecimal.ROUND_HALF_UP).multiply(hundred));
			}
			multipleProfitReportDTO.setTotalMoney(multipleProfitReportDTO.getPosSaleMoney()
					.add(multipleProfitReportDTO.getTranferMoney()).add(multipleProfitReportDTO.getWholesaleMoney()));
			multipleProfitReportDTO.setTotalProfit(multipleProfitReportDTO.getPosSaleProfit().add(
					multipleProfitReportDTO.getTranferProfit().add(multipleProfitReportDTO.getWholesaleProfit())));
			if (multipleProfitReportDTO.getTotalMoney().compareTo(BigDecimal.ZERO) == 0) {
				multipleProfitReportDTO.setTotalProfitRate(BigDecimal.ZERO);
			} else {
				multipleProfitReportDTO.setTotalProfitRate(multipleProfitReportDTO.getTotalProfit().divide(
						multipleProfitReportDTO.getTotalMoney(), 2,BigDecimal.ROUND_HALF_UP).multiply(hundred));
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
		List<Object[]> objects = inventoryService.findItemAmount(systemBookCode, branchNums, null);
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
			}
			data.setSaleQty(data.getSaleQty().add(amount));
			data.setSaleMoney(data.getSaleMoney().add(saleMoney));
			data.setSaleCost(data.getSaleCost().add(saleMoney.subtract(profit)));
			data.setSaleProfit(data.getSaleProfit().add(profit));
		}
		List<SupplierComplexReportDTO> list = new ArrayList<SupplierComplexReportDTO>(map.values());
		objects = inventoryService.findBranchItemSummary(systemBookCode, supplierSaleQuery.getBranchNums());
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
		for (int i = 0; i < objects.size(); i++) {
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

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}

		List<Object[]> posList = posOrderService.findBranchDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = posList.size(); i < len ; i++) {
			Object[] object = posList.get(i);
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

		return new ArrayList<BusinessCollection>(map.values());
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

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
		}

		List<Object[]> postList = posOrderService.findBranchBizdayDiscountSummary(systemBookCode, branchNums, dateFrom, dateTo);
		for (int i = 0,len = postList.size(); i < len; i++) {
			Object[] object = postList.get(i);
			Integer branchNum = (Integer) object[0];
			String shiftTableBizday = (String) object[1];
			BigDecimal money = object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2];
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
			BusinessCollection data = map.get(branchNum + shiftTableBizday + machineName);
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
			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
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

			detail = getBusinessCollectionIncome(data.getPosIncomes(), AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
			if (detail == null) {
				detail = new BusinessCollectionIncome();
				detail.setName(AppConstants.POS_ORDER_DETAIL_TYPE_COUPON);
				detail.setMoney(BigDecimal.ZERO);
				data.getPosIncomes().add(detail);
			}
			detail.setMoney(detail.getMoney().add(money));
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
		return reportService.findRetailDetails(retailDetailQueryData);
	}
	
	@Override
	public List<RetailDetail> findRetailDetails(RetailDetailQueryData retailDetailQueryData) {
		return reportService.findRetailDetails(retailDetailQueryData);
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
			profitByBranchAndItemSummary.setMatrixNum((int)object[2]);
			profitByBranchAndItemSummary.setProfit((BigDecimal) object[3]);
			profitByBranchAndItemSummary.setAmount((BigDecimal) object[4]);
			profitByBranchAndItemSummary.setMoney((BigDecimal) object[5]);
			profitByBranchAndItemSummary.setCost((BigDecimal) object[6]);
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
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisByCategoryBranchs' + #p0.getKey()")
	public List<SaleByCategoryBranchSummary> findSaleAnalysisByCategoryBranchs(SaleAnalysisQueryData queryData) {

		List<Object[]> objects = reportService.findSaleAnalysisByCategoryBranchs(queryData);
		int size = objects.size();
		List<SaleByCategoryBranchSummary> list = new ArrayList<SaleByCategoryBranchSummary>(size);
		if(objects.isEmpty()){
			return list;
		}
		for (int i = 0; i <size ; i++) {
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
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisCommon' + #p0.getKey()")
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
	@Cacheable(value = "serviceCache", key = "'AMA_findSaleAnalysisCommon' + #p0.getKey()")
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
			itemRebatesSummary.setMoney((BigDecimal) object[0]);
			itemRebatesSummary.setDiscount((BigDecimal) object[1]);
			itemRebatesSummary.setAmount((BigDecimal)object[2]);
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
		return reportService.findCardConsumeAnalysis(cardConsuemAnalysisQuery);
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
			cardConsumeDetailSummary.setCardUserPrintedNum((Integer) object[1]);
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
			PosGroupHourAndBranchRegionTypeSummary posGroupHourAndBranchRegionTypeSummary = new PosGroupHourAndBranchRegionTypeSummary();
			posGroupHourAndBranchRegionTypeSummary.setOrderTimeChar((int)object[0]);
			posGroupHourAndBranchRegionTypeSummary.setMoney((BigDecimal) object[1]);
			posGroupHourAndBranchRegionTypeSummary.setAmount((Integer) object[2]);
			list.add(posGroupHourAndBranchRegionTypeSummary);
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
			posGroupHourSummary.setMoney((BigDecimal) object[1]);
			posGroupHourSummary.setAmount((Integer) object[2]);
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
			groupByItemSummary.setAssistAmount((BigDecimal) object[6]);
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
		return null;
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
		ReportUtil<OrderDetailCompare> reportUtil = new ReportUtil<OrderDetailCompare>(OrderDetailCompare.class);
		List<Object[]> objects = reportService.findCustomerAnalysisBranchItem(systemBookCode, thisDateFrom, thisDateTo,
				branchNums, itemNums);

		for (int i = 0,len = objects.size(); i < len; i++) {
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

		for (int i = 0,len = objects.size(); i < len; i++) {
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
		for (int i = 0,len = adjustmentReasons.size(); i < len; i++) {
			AdjustmentReason adjustmentReason = adjustmentReasons.get(i);
			if (StringUtils.equals(adjustmentReason.getAdjustmentInoutCategory(),
					AppConstants.ADJUSTMENT_REASON_TYPE_LOSS)) {
				reasons.add(adjustmentReason.getAdjustmentReasonName());
			}
		}
		if (reasons.size() > 0) {
			objects = adjustmentOrderService.findBranchItemSummary(systemBookCode, branchNums, thisDateFrom, thisDateTo, itemNums,
					reasons);
			for (int i = 0,len = objects.size(); i < len; i++) {
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
			for (int i = 0,len = objects.size(); i < len; i++) {
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
		for (int i = 0,len = outThisObjects.size(); i < len; i++) {
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

		for (int i = 0,len = outLastObjects.size(); i < len; i++) {
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
		return reportService.getCardAnalysisSummaryDTO(cardUserQuery);
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
			branchCustomerSummary.setOrderNoCount((Integer) object[2]);
			branchCustomerSummary.setConponMoney((BigDecimal) object[3]);
			branchCustomerSummary.setMgrDiscount((BigDecimal) object[4]);
			branchCustomerSummary.setGrossProfit((BigDecimal) object[5]);
			branchCustomerSummary.setItemCount((Integer)object[6]);
			branchCustomerSummary.setUserAmount((Integer) object[7]);
			branchCustomerSummary.setUserMoney((BigDecimal) object[8]);
			branchCustomerSummary.setValidOrderNo((Integer) object[9]);
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

	@Override
	public List<CardDailyDTO> findCardDailys(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

		//新增会员数
		List<BranchBizdayCardCountSummary> cardCounts = cardUserRpc.findCardCountByBranchBizday(systemBookCode, branchNums, dateFrom, dateTo, null);
		//退卡数
		List<BranchBizdayCardReturnSummary> revokeCards= cardUserRpc.findRevokeCardCountByBranchBizday(systemBookCode, branchNums, dateFrom, dateTo, null);
		//付款金额，存款金额
		List<BranchBizdayDepositSummary> depositSummarys = cardDepositRpc.findSumByBizdayBranch(systemBookCode, branchNums, dateFrom, dateTo);
		//存款目标
		List<DepositGoalsDTO> depositGoals = branchTransferGoalsRpc.findDepositGoals(systemBookCode, branchNums, dateFrom, dateTo);
		//发卡目标
		List<NewCardGoalsDTO> newCardGoals= branchTransferGoalsRpc.findNewCardGoals(systemBookCode, branchNums, dateFrom, dateTo);
		//卡消费
		List<BranchBizdayConsumeSummary> consumeSummarys = cardConsumeRpc.findBranchBizdaySum(systemBookCode, branchNums, dateFrom, dateTo, null);

		List<CardDailyDTO> returnList = new ArrayList<CardDailyDTO>();
		//查询所有分店
		List<BranchDTO> branchs = branchRpc.findInCache(systemBookCode);
		String biz = DateUtil.getDateShortStr(dateFrom);


		for (int i = 0,size = branchs.size(); i <size ; i++) {
			BranchDTO branchDTO = branchs.get(i);
			CardDailyDTO cardDailyDTO = new CardDailyDTO();
			cardDailyDTO.setSystemBookCode(systemBookCode);
			cardDailyDTO.setBranchNum(branchDTO.getBranchNum());
			cardDailyDTO.setShiftTableBizday(biz);
			cardDailyDTO.setShiftTableDate(dateFrom);
			Integer branchNum = cardDailyDTO.getBranchNum();
			//新增会员数(发卡数)
			for (int j = 0,len = cardCounts.size(); j <len ; j++) {
				BranchBizdayCardCountSummary countSummary = cardCounts.get(j);
				if(branchNum.equals(countSummary.getBranchNum()) && biz.equals(countSummary.getBizday())){
					cardDailyDTO.setCardDeliverCount(countSummary.getCount().intValue());
				}
			}
			//退卡数
			for (int j = 0,len = revokeCards.size(); j <len; j++) {
				BranchBizdayCardReturnSummary returnSummary = revokeCards.get(j);
				if(branchNum.equals(returnSummary.getBranchNum()) && biz.equals(returnSummary.getBizday())){
					cardDailyDTO.setCardReturnCount(returnSummary.getReturnCount().intValue());
				}
			}
			//付款金额，存款金额
			for (int j = 0,len = depositSummarys.size(); j <len; j++) {
				BranchBizdayDepositSummary depositSummary = depositSummarys.get(j);
				if(branchNum.equals(depositSummary.getBranchNum()) && biz.equals(depositSummary.getBizday())){
					cardDailyDTO.setCardDepositCash(depositSummary.getDepositCash());
					cardDailyDTO.setCardDepositMoney(depositSummary.getDeposit());
				}
			}
			//存款目标
			for (int j = 0,len = depositGoals.size(); j <len; j++) {
				DepositGoalsDTO depositGoalsDTO = depositGoals.get(j);
				if(branchNum.equals(depositGoalsDTO.getBranchNum()) && biz.equals(depositGoalsDTO.getBizday())){
					cardDailyDTO.setCardDepositTarget(depositGoalsDTO.getDepositGoals());
				}
			}
			//发卡目标
			for (int j = 0,len = newCardGoals.size(); j <len; j++) {
				NewCardGoalsDTO newCardGoalsDTO = newCardGoals.get(j);
				if(branchNum.equals(newCardGoalsDTO.getBranchNum()) && biz.equals(newCardGoalsDTO.getBizday())){
					cardDailyDTO.setCardDeliverTarget(newCardGoalsDTO.getNewCard());
				}
			}
			//卡消费
			for (int j = 0,len = consumeSummarys.size(); j <len; j++) {
				BranchBizdayConsumeSummary consumeSummary = consumeSummarys.get(j);
				if(branchNum.equals(consumeSummary.getBranchNum()) && biz.equals(consumeSummary.getBizday())){
					cardDailyDTO.setCardConsumeMoney(consumeSummary.getMoney());
				}
			}
			returnList.add(cardDailyDTO);
		}
		return returnList;
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
}
