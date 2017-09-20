package com.nhsoft.report.model;


import com.nhsoft.report.shared.State;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CheckOrder generated by hbm2java
 */
public class CheckOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8463220116841213220L;
	private String checkOrderFid;
	private Integer storehouseNum;
	private String systemBookCode;
	private Date checkOrderDate;
	private String checkOrderScope;
	private String checkOrderItemCategory;
	private String checkOrderMemo;
	private State state;
	private String checkOrderCreator;
	private String checkOrderAuditor;
	private boolean checkOrderTransferFlag;
	private Boolean checkOrderCycle;
	private Integer checkOrderPeriod;
	private Boolean checkOrderForced;
	private Date checkOrderAuditTime;
	private boolean checkOrderSnapshot;
	private Date checkOrderSnapshotTime;
	private String checkOrderUnit;
	private BigDecimal checkOrderMoney;
	private Date checkOrderLastEditTime;
	private String checkOrderCycleType;
	private String checkOrderExtend;
	private List<CheckOrderDetail> checkOrderDetails = new ArrayList<CheckOrderDetail>(
			0);

	//不存入数据库
	private Integer branchNum;
	private Boolean saveAndAudit;
	
	private AppUser appUser;
	private String copyFid;
	
	public String getCopyFid() {
		return copyFid;
	}

	public void setCopyFid(String copyFid) {
		this.copyFid = copyFid;
	}
	
    public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getCheckOrderExtend() {
		return checkOrderExtend;
	}

	public void setCheckOrderExtend(String checkOrderExtend) {
		this.checkOrderExtend = checkOrderExtend;
	}

	public CheckOrder() {
	}
    
	public String getCheckOrderCycleType() {
		return checkOrderCycleType;
	}

	public void setCheckOrderCycleType(String checkOrderCycleType) {
		this.checkOrderCycleType = checkOrderCycleType;
	}

	public Boolean getSaveAndAudit() {
		return saveAndAudit;
	}

	public void setSaveAndAudit(Boolean saveAndAudit) {
		this.saveAndAudit = saveAndAudit;
	}

	public Date getCheckOrderLastEditTime() {
		return checkOrderLastEditTime;
	}

	public void setCheckOrderLastEditTime(Date checkOrderLastEditTime) {
		this.checkOrderLastEditTime = checkOrderLastEditTime;
	}

	public Integer getStorehouseNum() {
		return storehouseNum;
	}

	public void setStorehouseNum(Integer storehouseNum) {
		this.storehouseNum = storehouseNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public String getCheckOrderFid() {
		return this.checkOrderFid;
	}

	public void setCheckOrderFid(String checkOrderFid) {
		this.checkOrderFid = checkOrderFid;
	}

	public Date getCheckOrderDate() {
		return this.checkOrderDate;
	}

	public void setCheckOrderDate(Date checkOrderDate) {
		this.checkOrderDate = checkOrderDate;
	}

	public String getCheckOrderScope() {
		return this.checkOrderScope;
	}

	public void setCheckOrderScope(String checkOrderScope) {
		this.checkOrderScope = checkOrderScope;
	}

	public String getCheckOrderItemCategory() {
		return this.checkOrderItemCategory;
	}

	public void setCheckOrderItemCategory(String checkOrderItemCategory) {
		this.checkOrderItemCategory = checkOrderItemCategory;
	}

	public String getCheckOrderMemo() {
		return this.checkOrderMemo;
	}

	public void setCheckOrderMemo(String checkOrderMemo) {
		this.checkOrderMemo = checkOrderMemo;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getCheckOrderCreator() {
		return this.checkOrderCreator;
	}

	public void setCheckOrderCreator(String checkOrderCreator) {
		this.checkOrderCreator = checkOrderCreator;
	}

	public String getCheckOrderAuditor() {
		return this.checkOrderAuditor;
	}

	public void setCheckOrderAuditor(String checkOrderAuditor) {
		this.checkOrderAuditor = checkOrderAuditor;
	}

	public boolean isCheckOrderTransferFlag() {
		return this.checkOrderTransferFlag;
	}

	public void setCheckOrderTransferFlag(boolean checkOrderTransferFlag) {
		this.checkOrderTransferFlag = checkOrderTransferFlag;
	}

	public Boolean getCheckOrderCycle() {
		return this.checkOrderCycle;
	}

	public void setCheckOrderCycle(Boolean checkOrderCycle) {
		this.checkOrderCycle = checkOrderCycle;
	}

	public Integer getCheckOrderPeriod() {
		return this.checkOrderPeriod;
	}

	public void setCheckOrderPeriod(Integer checkOrderPeriod) {
		this.checkOrderPeriod = checkOrderPeriod;
	}

	public Boolean getCheckOrderForced() {
		return this.checkOrderForced;
	}

	public void setCheckOrderForced(Boolean checkOrderForced) {
		this.checkOrderForced = checkOrderForced;
	}

	public Date getCheckOrderAuditTime() {
		return this.checkOrderAuditTime;
	}

	public void setCheckOrderAuditTime(Date checkOrderAuditTime) {
		this.checkOrderAuditTime = checkOrderAuditTime;
	}

	public boolean isCheckOrderSnapshot() {
		return this.checkOrderSnapshot;
	}

	public void setCheckOrderSnapshot(boolean checkOrderSnapshot) {
		this.checkOrderSnapshot = checkOrderSnapshot;
	}

	public Date getCheckOrderSnapshotTime() {
		return this.checkOrderSnapshotTime;
	}

	public void setCheckOrderSnapshotTime(Date checkOrderSnapshotTime) {
		this.checkOrderSnapshotTime = checkOrderSnapshotTime;
	}

	public List<CheckOrderDetail> getCheckOrderDetails() {
		return checkOrderDetails;
	}

	public void setCheckOrderDetails(List<CheckOrderDetail> checkOrderDetails) {
		this.checkOrderDetails = checkOrderDetails;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getCheckOrderUnit() {
		return checkOrderUnit;
	}

	public void setCheckOrderUnit(String checkOrderUnit) {
		this.checkOrderUnit = checkOrderUnit;
	}

	public BigDecimal getCheckOrderMoney() {
		return checkOrderMoney;
	}

	public void setCheckOrderMoney(BigDecimal checkOrderMoney) {
		this.checkOrderMoney = checkOrderMoney;
	}
	
	public void calMoney(){
		checkOrderMoney = BigDecimal.ZERO;
		for(int i = 0;i < checkOrderDetails.size();i++){
			CheckOrderDetail checkOrderDetail = checkOrderDetails.get(i);
			checkOrderMoney = checkOrderMoney.add(checkOrderDetail.getCheckOrderDetailCost().multiply(checkOrderDetail.getCheckOrderDetailQty()).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
	}
	
	public static CheckOrderDetail getCheckOrderDetail(List<CheckOrderDetail> checkOrderDetails, Integer itemNum, Integer itemMatrxiNum, String lotNumber){
		if(lotNumber == null){
			lotNumber = "";
		}
		if(itemMatrxiNum == null){
			itemMatrxiNum = 0;
		}
		for(int i = 0;i < checkOrderDetails.size();i++){
			CheckOrderDetail checkOrderDetail = checkOrderDetails.get(i);
			if(checkOrderDetail.getCheckOrderDetailItemMatrixNum() == null){
				checkOrderDetail.setCheckOrderDetailItemMatrixNum(0);
			}
			if(checkOrderDetail.getCheckOrderDetailLotNumber() == null){
				checkOrderDetail.setCheckOrderDetailLotNumber("");
			}
			if(checkOrderDetail.getItemNum().equals(itemNum) 
					&& itemMatrxiNum.equals(checkOrderDetail.getCheckOrderDetailItemMatrixNum())
					&& lotNumber.equals(checkOrderDetail.getCheckOrderDetailLotNumber())){
				return checkOrderDetail;
			}
		}
		return null;
	}
	
	public static CheckOrder get(List<CheckOrder> checkOrders, String checkOrderFid){
	    for(int i=0;i<checkOrders.size();i++){
	        CheckOrder checkOrder = checkOrders.get(i);
	        if(checkOrder.getCheckOrderFid().equals(checkOrderFid)){
	            return checkOrder;
	        }
	    }
	    return null;
	}

	public boolean checkExistsDetail(Integer itemNum, String posItemLogLotNumber, Integer posItemLogItemMatrixNum) {
		if(posItemLogItemMatrixNum == null){
			posItemLogItemMatrixNum = 0;
		}
		for(int i = 0;i < checkOrderDetails.size();i++){
			CheckOrderDetail detail = checkOrderDetails.get(i);
			if(detail.getCheckOrderDetailItemMatrixNum() == null){
				detail.setCheckOrderDetailItemMatrixNum(0);
			}
			if(detail.getItemNum().equals(itemNum) 
					&& StringUtils.equals(detail.getCheckOrderDetailLotNumber(), posItemLogLotNumber)
					&& detail.getCheckOrderDetailItemMatrixNum().equals(posItemLogItemMatrixNum)){
				return true;
			}			
		}
		return false;
	}

	public String generateObjectId(String uuid) {
		return "temp_order_backup/"+systemBookCode+"/"+branchNum+"/checkOrder/"+uuid+".txt";
	}

}
