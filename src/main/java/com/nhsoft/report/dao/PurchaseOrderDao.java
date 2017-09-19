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

	public PurchaseOrder read(String fid);
	
	public List<PurchaseOrder> findAuditBySupplier(String systemBookCode, Integer branchNum, Integer supplierNum,
												   Date dateFrom, Date dateTo, List<Integer> selectItemNums, String orderFid);

	public List<PurchaseOrder> findByQuery(PurchaseOrderQuery purchaseOrderQuery, int offset, int limit);

	public Object[] sumByQuery(PurchaseOrderQuery purchaseOrderQuery);

	/**
	 * 根据审核时间和供应商查询
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param supplierNum
	 * @return
	 */
	public List<PurchaseOrder> findByAuditAndSupplier(String systemBookCode, Date dateFrom, Date dateTo,
                                                      Integer supplierNum);

	public void update(PurchaseOrder purchaseOrder);

	public void delete(PurchaseOrder persisent);

	public void updateAll(PurchaseOrder purchaseOrder);

	public void saveAll(PurchaseOrder purchaseOrder);

	public List<PurchaseOrder> findByQuery(String systemBookCode, Integer branchNum,
										   OrderQueryCondition conditionData, int offset, int limit);

	public int countByQuery(String systemBookCode, Integer branchNum,
                            OrderQueryCondition conditionData);

	public BigDecimal sumTotalMoney(String systemBookCode, Integer branchNum,
                                    OrderQueryCondition conditionData);

	public List<PurchaseOrder> findAudit(String systemBookCode, Integer branchNum, String orderFid,
                                         Integer supplierNum, String dateType, Date dateFrom, Date dateTo,
                                         Integer storehouseNum, String orderState, Boolean isValid);

	public List<PurchaseOrder> findByPurchaseOrderCollectQuery(PurchaseOrderCollectQuery purchaseOrderCollectQuery);

	/**
	 * 未收货的商品数量 (在订量)
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findUnReceivedItemAmount(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	/**
	 * 单个商品未收货数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNum
	 * @param requestOrderFid
	 * @return
	 */
	public BigDecimal getUnReceiveItemQtyByFid(String systemBookCode, Integer branchNum,
                                               Integer itemNum, String requestOrderFid, Integer stateCode);

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
     * 查询未上传单据数量
     * @param systemBookCode
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public int countUnUploadAudit(String systemBookCode, Date dateFrom, Date dateTo);

	public List<PurchaseOrder> findUnUploadAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, int offset, int limit);

	/**
	 * 批量更新上传标记
	 * @param systemBookCode
	 * @param branchNum
	 * @param begin
	 * @param end
	 */
	public void updateTransferFlag(String systemBookCode, Integer branchNum, List<String> orderFids);

	/**
	 *  有效期内已采购未收货的数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNum
	 * @return
	 */
	public BigDecimal getUnReceivedAmount(String systemBookCode, Integer branchNum, Integer itemNum);

	/**
	 * 读最近一次采购时间
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNum
	 * @return
	 */
	public Date getLastDate(String systemBookCode, Integer branchNum, Integer supplierNum);

	/**
	 * 查询已制单未审核的单品采购量
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param storehouseNum
	 * @param itemNum
	 * @param itemMatrixNum
	 * @param requestOrderFid
	 * @return
	 */
	public BigDecimal getHasPurchaseQty(String systemBookCode, Integer outBranchNum, Integer storehouseNum,
                                        Integer itemNum, Integer itemMatrixNum, String requestOrderFid);
	
	/**
	 * 批量删除
	 * @param systemBookCode
	 * @param limitDate
	 * @param batchSize
	 * @return
	 */
	public Integer batchDelete(String systemBookCode, Date limitDate, int batchSize);
    
	public Integer countExpiredOrder(String systemBookCode, Integer branchNum);
	
	/**
	 * 查询未全部入库的单据数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param isValid
	 * @return
	 */
	public int countUnReceive(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean isValid);

	public List<PurchaseOrder> findAudit(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public List<PurchaseOrderDetail> findDetails(List<String> purchaseOrderFids);

	public void updateAllNotDelete(PurchaseOrder purchaseOrder);
	
	public void updateDetail(PurchaseOrderDetail purchaseOrderDetail);
}