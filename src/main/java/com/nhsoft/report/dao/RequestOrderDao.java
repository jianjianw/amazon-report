package com.nhsoft.report.dao;



import com.nhsoft.report.dto.RequestOrderDTO;
import com.nhsoft.report.model.RequestOrder;
import com.nhsoft.report.model.RequestOrderDetail;
import com.nhsoft.report.model.RequestOrderDetailId;
import com.nhsoft.report.model.TaskRequestDetail;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;
import com.nhsoft.report.shared.queryBuilder.RequestOrderQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RequestOrderDao {
	



	/**
	 * 向中心要货未调出的商品数量 按商品和多特性汇总
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findCenterRequestMatrixAmount(String systemBookCode, Integer outBranchNum, List<Integer> itemNums);


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
	 * 按门店汇总要货新品数
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param newItemNums
	 * @param categoryCodes
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchNewItemSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, List<Integer> newItemNums, List<String> categoryCodes,
                                                   Date dateFrom, Date dateTo);



	/**
	 * 查询明细
	 * @param requestOrderFid
	 * @return
	 */
	public List<RequestOrderDetail> findDetails(String requestOrderFid);

	/**
	 * 按商品汇总数据
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, Integer centerBranchNum, Integer branchNum,
                                          Date dateFrom, Date dateTo, List<Integer> itemNums);

	

}
