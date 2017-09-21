package com.nhsoft.report.dao;


import com.nhsoft.report.model.PurchaseOrder;
import com.nhsoft.report.model.PurchaseOrderDetail;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;
import com.nhsoft.report.shared.queryBuilder.PurchaseOrderCollectQuery;
import com.nhsoft.report.shared.queryBuilder.PurchaseOrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PurchaseOrderDao {



	/**
	 * 未收货的商品数量 (在订量)
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findUnReceivedItemAmount(String systemBookCode, Integer branchNum, List<Integer> itemNums);

    /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
     * @param dateTo
     * @param dateFrom
     * @return
     */
    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 读最近一次采购时间
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @return
	 */
	public Date getLastDate(String systemBookCode, Integer branchNum, Integer supplierNum);

	public List<PurchaseOrderDetail> findDetails(List<String> purchaseOrderFids);


}