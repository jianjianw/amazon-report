package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.InvoiceChangeDao;
import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.dto.TypeAndTwoValuesDTO;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.queryBuilder.PosOrderQuery;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PosOrderServiceImpl implements PosOrderService {
	private static final Logger logger = LoggerFactory.getLogger(PosOrderServiceImpl.class);
	@Autowired
	private PosOrderDao posOrderDao;
	@Autowired
	private InvoiceChangeDao invoiceChangeDao;

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

		ItemQueryDTO itemQueryDTO = new ItemQueryDTO();
		itemQueryDTO.setSystemBookCode(systemBookCode);
		itemQueryDTO.setBranchNums(branchNums);
		itemQueryDTO.setDateFrom(dateFrom);
		itemQueryDTO.setDateTo(dateTo);
		itemQueryDTO.setItemNums(itemNums);
		itemQueryDTO.setQueryKit(queryKit);
		return findItemSum(itemQueryDTO);
	}

	@Override
	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return posOrderDao.findCustomReportByBranch(systemBookCode, branchNums, dateFrom, dateTo, dateType);
	}

	@Override
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo,Boolean isMember) {

		List<Object[]> objects = null;
		if(queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)){
			objects = posOrderDao.findMoneyBranchSummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;

	}

	@Override
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

		List<Object[]> objects = null;
		if(queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)){
			objects = posOrderDao.findMoneyBizdaySummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;
	}

	@Override
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {


		List<Object[]> objects = null;
		if (queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)) {
			objects = posOrderDao.findMoneyBizmonthSummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;

	}

	@Override
	public List<PosOrder> findByShiftTableWithDetails(ShiftTable shiftTable) {
		List<PosOrder> posOrders = posOrderDao.findByShiftTable(shiftTable);
		if(posOrders.isEmpty()){
			return posOrders;
		}
		List<String> orderNos = new ArrayList<String>();
		for (int i = 0; i < posOrders.size(); i++) {
			PosOrder posOrder = posOrders.get(i);
			orderNos.add(posOrder.getOrderNo());

		}
		List<Payment> payments = posOrderDao.findPaymentsByOrderNos(orderNos);
		List<InvoiceChange> invoiceChanges = invoiceChangeDao.find(orderNos);
		List<PosOrderDetail> posOrderDetails = posOrderDao.findDetails(orderNos);
		for (int i = 0; i < posOrders.size(); i++) {
			PosOrder posOrder = posOrders.get(i);

			posOrder.setPayments(Payment.find(payments, posOrder.getOrderNo()));
			posOrder.setInvoiceChanges(InvoiceChange.find(invoiceChanges, posOrder.getOrderNo()));
			posOrder.setPosOrderDetails(PosOrderDetail.find(posOrderDetails, posOrder.getOrderNo()));

			List<PosOrderDetail> orderDetails = posOrder.getPosOrderDetails();
			for (int j = 0; j < orderDetails.size(); j++) {
				PosOrderDetail posOrderDetail = orderDetails.get(j);
				if(posOrderDetail.getOrderDetailHasKit() != null && posOrderDetail.getOrderDetailHasKit()){
					posOrderDetail.getPosOrderKitDetails().size();

				} else {
					posOrderDetail.setPosOrderKitDetails(new ArrayList<PosOrderKitDetail>());

				}
			}
		}
		return posOrders;
	}


	@Override
	public List<Object[]> findBranchCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findBranchCouponSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findBranchDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findBranchDiscountSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findMerchantDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		return posOrderDao.findMerchantDiscountSummary(systemBookCode,branchNum, merchantNum,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findStallDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Integer stallNum, Date dateFrom, Date dateTo) {
		return posOrderDao.findStallDiscountSummary(systemBookCode,branchNum, merchantNum, stallNum, dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findBranchBizdayCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findBranchBizdayCouponSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findBranchBizdayDiscountSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findBranchBizdayDiscountSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findMerchantBizdayDiscountSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo) {
		return posOrderDao.findMerchantBizdayDiscountSummary(systemBookCode,branchNum, merchantNum,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findCouponSummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		return posOrderDao.findBranchShiftTablePaymentSummary(systemBookCode,branchNums,dateFrom,dateTo,casher);
	}

	@Override
	public List<Object[]> findBranchShiftTablePaymentSummary(String systemBookCode, Integer branchNum, Integer merchantNum, Date dateFrom, Date dateTo, String casher) {
		return posOrderDao.findBranchShiftTablePaymentSummary(systemBookCode,branchNum, merchantNum, dateFrom,dateTo,casher);
	}

	@Override
	public List<Object[]> findBranchShiftTableCouponSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		return posOrderDao.findBranchShiftTableCouponSummary(systemBookCode,branchNums,dateFrom,dateTo,casher);
	}

	@Override
	public List<Object[]> findBranchOperatorPayByMoneySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findBranchOperatorPayByMoneySummary(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType) {
		return posOrderDao.findDetails(systemBookCode,branchNums,dateFrom,dateTo,categoryCodes,offset,limit,sortField,sortType);
	}


	@Override
	public List<PosOrder> findSettled(PosOrderQuery posOrderQuery,
									  int offset, int limit) {

		List<PosOrder> posOrders = posOrderDao.findSettled(posOrderQuery,
				offset, limit);
		if (posOrders.isEmpty()) {
			return posOrders;
		}
		List<Object[]> objects;
		if (!posOrderQuery.isPage()) {
			objects = posOrderDao.findOrderPaymentMoneys(posOrderQuery);
		} else {
			List<String> orderNos = new ArrayList<String>();
			for (int i = 0; i < posOrders.size(); i++) {
				PosOrder posOrder = posOrders.get(i);
				orderNos.add(posOrder.getOrderNo());
			}
			objects = posOrderDao.findOrderPaymentMoneys(orderNos);

		}
		for (int i = 0; i < posOrders.size(); i++) {
			PosOrder posOrder = posOrders.get(i);

			for (int j = 0; j < objects.size(); j++) {
				Object[] object = objects.get(j);
				if (posOrder.getOrderNo().equals(object[0])) {
					String payment = (String) object[1];
					if (posOrder.getPaymentTypes() == null) {
						posOrder.setPaymentTypes(payment);
					} else if(!posOrder.getPaymentTypes().contains(payment)){
						posOrder.setPaymentTypes(posOrder.getPaymentTypes() + "," + payment);
					}
					TypeAndTwoValuesDTO typeAndTwoValuesDTO = posOrder.getTypeAndTwoValuesDTO(payment);
					if(typeAndTwoValuesDTO == null){
						typeAndTwoValuesDTO = new TypeAndTwoValuesDTO();
						typeAndTwoValuesDTO.setType(payment);
						typeAndTwoValuesDTO.setMoney(BigDecimal.ZERO);
						posOrder.getTypeAndTwoValuesDTOs().add(typeAndTwoValuesDTO);
					}
					typeAndTwoValuesDTO.setMoney(typeAndTwoValuesDTO.getMoney().add(object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2]));

				}
			}
		}
		return posOrders;
	}

	@Override
	public Object[] sumSettled(PosOrderQuery posOrderQuery) {
		Object[] objects = posOrderDao.sumSettled(posOrderQuery);
		return objects;
	}

	@Override
	public List<Object[]> findSummaryByPrintNum(CardReportQuery cardReportQuery) {
		return posOrderDao.findSummaryByPrintNum(cardReportQuery);
	}

	@Override
	public Object[] findCountByPrintNum(CardReportQuery cardReportQuery) {
		return posOrderDao.findCountByPrintNum(cardReportQuery);
	}


}

