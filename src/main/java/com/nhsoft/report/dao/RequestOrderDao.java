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
	
	public RequestOrder saveAll(RequestOrder requestOrder);
	
	public RequestOrder updateAll(RequestOrder requestOrder);
	
	public RequestOrder read(String requestOrderFid);
	
	public RequestOrderDetail readDetail(RequestOrderDetailId id);
	
	public void updateDetail(RequestOrderDetail requestOrderDetail);
	
	public void remove(RequestOrder requestOrder);
	
	public List<RequestOrder> findCreatorByBranchNum(String systemBookCode, Integer branchNum);
	
	public List<RequestOrder> findByQuery(RequestOrderQuery posItemquery, int first, int count);
	
	public int countByQuery(RequestOrderQuery posItemquery);
	
	public List<RequestOrder> findByQuery(RequestOrderQuery posItemquery);
	
	public int findRequestOrderCount(String systemBookCode, Integer centerBranchNum,
                                     Integer branchNum, Date dateTo);

	public BigDecimal findUnSendAmount(String systemBookCode, Integer branchNum, Integer itemNum);

	public List<RequestOrder> findByOrderQuery(String systemBookCode, Integer branchNum, OrderQueryCondition conditionData, int offset, int limit);

	public int countByOrderQuery(String systemBookCode, Integer branchNum, OrderQueryCondition conditionData);

	/**
	 * 要货期内未调出审核的金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @return
	 */
	public BigDecimal readBranchUnOutMoney(String systemBookCode, Integer branchNum, Integer centerBranchNum);

	/**
	 * 时间范围内的要货量 按商品汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemAmount(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 时间范围内的要货量 按门店商品汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchItemAmount(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public int findByDeadline(String systemBookCode, Integer branchNum);

	/**
	 * 向中心要货未调出的商品数量 按商品汇总
	 * @return
	 */
	public List<Object[]> findCenterRequestAmount(String systemBookCode, Integer outBranchNum);

	/**
	 * 向中心要货未调出的商品数量 按商品和多特性汇总
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findCenterRequestMatrixAmount(String systemBookCode, Integer outBranchNum, List<Integer> itemNums);

	/**
	 * 查询门店1：待审核，2：已审核，3：已配货，4：已发货，5：已收货 的要货单
	 * @param requestOrderQuery
	 * @return
	 */
	public List<RequestOrder> findByState(RequestOrderQuery requestOrderQuery);

	/**
	 * 未过期的审核的要货单
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<RequestOrder> findUnExpired(String systemBookCode, Integer branchNum);

	/**
	 * 配送中心用
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param inBranchNums
	 * @param orderFid
	 * @param dateType
	 * @param dateFrom
	 * @param dateTo
	 * @param isValid
	 * @param queryByTime
	 * @param queryUnOut
	 * @return
	 */
	public List<RequestOrder> findAudit(String systemBookCode,
                                        Integer centerBranchNum, List<Integer> inBranchNums, String orderFid,
                                        String dateType, Date dateFrom, Date dateTo, Boolean isValid, boolean queryByTime, boolean queryUnOut);

	/**
	 * 向中心要货未调出的商品数量
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param itemNum
	 * @param itemMatrixNum
	 * @return
	 */
	public BigDecimal findCenterRequestAmount(String systemBookCode,
                                              Integer outBranchNum, Integer itemNum, Integer itemMatrixNum);

	public void flush();

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
     * 按日期删除单据
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     */
    public void deleteByDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


    /**
     * 取最小单据日期
     * @param systemBookCode
     * @param branchNum
     * @return
     */
    public Date getFirstDate(String systemBookCode, Integer branchNum);

    /**
     * 查询要货量 按商品汇总
     * @param systemBookCode
     * @param centerBranchNum
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findItemQty(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);


    /**
     * 按门店、商品汇总 要货数量 要货金额 配送数量 发货数量 收货数量
     * @param requestOrderQuery
     * @return
     */
    public List<Object[]> findBranchAndItemSummaryByRequestOrderQuery(RequestOrderQuery requestOrderQuery);

    /**
     * 按商品汇总 要货数量 要货金额 配送数量 发货数量 收货数量
     * @param requestOrderQuery
     * @return
     */
    public List<Object[]> findItemSummaryByRequestOrderQuery(RequestOrderQuery requestOrderQuery);

	public void update(RequestOrder requestOrder);

    /**
     * 查询需结算的单据
     * @param systemBookCode
     * @param centerBranchNum
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<RequestOrder> findDue(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo);

    public Object[] sumByOrderQueryCondition(String systemBookCode,
                                             Integer branchNum, OrderQueryCondition orderQueryCondition);

    /**
     * 查询未完全付款的单据
     * @param systemBookCode
     * @param branchNum
     * @param centerBranchNum
     * @param dtFrom
     * @param dtTo
     * @return
     */
    public List<RequestOrder> findUnPay(String systemBookCode,
                                        Integer branchNum, Integer centerBranchNum, Date dtFrom, Date dtTo);

    /**
     * 查询时间段内门店要货的配送数量 和 金额汇总
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public Object[] sumBranchRequestUseQtyAndMoney(String systemBookCode,
                                                   List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 查询未配送数量
     * @param systemBookCode
     * @param branchNum
     * @param itemNum
     * @return
     */
	public BigDecimal getRemainAmount(String systemBookCode, Integer branchNum, Integer itemNum);

    /**
     * 查询未上传的单据
     * @param systemBookCode
     * @param branchNum
     * @param dateTo
     * @param dateFrom
     * @param offset
     * @param limit
     * @return
     */
	public List<RequestOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset,
                                                int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param requestOrderFids
	 */
	public void updateTransferFlag(String systemBookCode, Integer branchNum, List<String> requestOrderFids);

	/**
	 * 查询商品在订量
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findItemRemainAmount(String systemBookCode, Integer branchNum);

	/**
     * 查询未上传单据数量
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public int countUnUploadAudit(String systemBookCode, Date dateFrom, Date dateTo);

	/**
	 * 批量删除
	 * @param systemBookCode
	 * @param branchNum
	 * @param limitDate
	 * @param batchSize
	 * @return
	 */
	public int batchDelete(String systemBookCode, Date limitDate, int batchSize);

	/**
	 * 查询最近orderLimit次订货商品  按数量降序
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNum
	 * @param orderLimit
	 * @return
	 */
	public List<Integer> findRecentlyItem(String systemBookCode, Integer outBranchNum, Integer branchNum, Integer orderLimit);

	/**
	 * 忽略之前要过货的商品
	 * @param requestOrderFid
	 * @param itemNums
	 * @param addDay
	 */
	public void ignoreBefore(String systemBookCode, Integer branchNum, String requestOrderFid, Date dateFrom, List<Integer> itemNums);

	/**
	 * 查询ConfirmRequestDetail
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param branchNums 要货分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param notExistsRequestOrderFids
	 * @return
	 */
	public List<TaskRequestDetail> findTaskRequestDetails(String systemBookCode, Integer outBranchNum,
														  List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> notExistsRequestOrderFids);

	/**
	 * 根据关联单据查询
	 * @param systemBookCode
	 * @param requestOrderRefBillNo
	 * @return
	 */
	public RequestOrder readByRefBillNo(String systemBookCode, String requestOrderRefBillNo);

	/**
	 * 查询需要作废的单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param cancelDate
	 * @return
	 */
	public List<RequestOrder> findToCancel(String systemBookCode, int branchNum, Date cancelDate);

	/**
	 * 查询审核要货单 带明细
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
     * @param branchNum 要货分店
     * @param dateFrom 审核时间起
     * @param dateTo 审核时间止
	 * @param isValid 是否有效期内
	 * @parma isOut 是否已调出
	 * @parma isPrePayment 是否预付款
	 * @return
	 */
	public List<RequestOrder> findAudit(String systemBookCode, Integer outBranchNum, Integer branchNum,
                                        Date dateFrom, Date dateTo, Boolean isValid, Boolean isOut, Boolean isPrePayment);

	/**
	 * 根据调出单号查询要货单号
	 * @param outOrderFid
	 * @return
	 */
	public List<String> findFidsByOutOrderFid(String outOrderFid);

	/**
	 * 根据主键查询
	 * @param requestOrderFids
	 * @return
	 */
	public List<RequestOrder> findByFids(List<String> requestOrderFids);

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
	 * 查询要货单部分属性
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<RequestOrderDTO> findDTOs(String systemBookCode, Integer centerBranchNum, Integer branchNum,
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
	
	/**
	 * 查询要货单对应的发货单货运方式
	 * @param requestOrderFids
	 * @return
	 */
	public List<Object[]> findFidShipOrderDeliver(List<String> requestOrderFids);

	/**
	 * 查询已作废的单据号
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<RequestOrder> findCancelFids(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 读取最后修改时间
	 * @param requestOrderFid
	 * @return
	 */
	public Date readCreateTime(String requestOrderFid);

	public List<Object[]> findRequestOrderByItem(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

	public List<RequestOrder> findTmallOrders(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<RequestOrder> findAuditByFids(String systemBookCode, List<String> requestOrderFids);

	public BigDecimal findValidRequestAmount(String systemBookCode, Integer branchNum, Integer itemNum, Integer itemMatrixNum);
	
	/**
	 * 按调出门店 调往门店 汇总 要货数量 要货金额 配送数量 发货数量 收货数量
	 * @param requestOrderQuery
	 * @return
	 */
	public List<Object[]> findTwoBranchSummaryByRequestOrderQuery(RequestOrderQuery requestOrderQuery);
	
	/**
	 * 查询要货分店ID列表
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Integer> findBranchNums(String systemBookCode, Integer outBranchNum, Date dateFrom, Date dateTo);
}
