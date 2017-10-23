package com.nhsoft.module.report.model;


import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PosItemLog implements java.io.Serializable {

	private static final long serialVersionUID = 4017784879621340343L;
	private String posItemLogFid;
	private Integer itemNum;
	private Integer inStorehouseNum;
	private String systemBookCode;
	private Integer outStorehouseNum;	
	private String shiSystemBookCode;
	private Integer branchNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private String posItemLogItemCode;
	private String posItemLogItemName;
	private String posItemLogItemUnit;
	private Boolean posItemLogInoutFlag;
	private String posItemLogSummary;
	private Date posItemLogDate;
	private BigDecimal posItemLogItemPrice;
	private BigDecimal posItemLogItemBalance;
	private BigDecimal posItemLogItemAmount;
	private String posItemLogOperator;
	private Date posItemLogOperateTime;
	private String posItemLogMemo;
	private Integer posItemLogItemMatrixNum;
	private String posItemLogBillNo;
	private Integer posItemLogBillDetailNum;
	private Boolean posItemLogTransferFlag;
	private String posItemLogLotNumber;
	private Date posItemLogProducingDate;
	private String posItemLogSerialNumber;
	private BigDecimal posItemLogCostPrice;
	private String posItemLogItemAssistUnit;
	private BigDecimal posItemLogItemAssistBalance;
	private BigDecimal posItemLogItemAssistAmount;
	private BigDecimal posItemLogOperatePrice;
	private String posItemLogUseUnit;
	private BigDecimal posItemLogUseQty;
	private BigDecimal posItemLogUsePrice;
	private BigDecimal posItemLogUseRate;
	private String posItemLogItemSpec;
	private String posItemLogRef;
	private BigDecimal posItemLogMoney;
	private String posItemLogItemCategory;
	private String posItemLogDateIndex;
	private BigDecimal posItemLogAdjustMoney; 
	private BigDecimal posItemLogLotBalance; //调整后所有批次常用数量
	private BigDecimal posItemLogLnBalance; //调整前单批次基本数量
	
	//临时属性
	private Integer repeatCount;
	private String systemBookName;
	private String branchName;
	private String refBillNo;
	private Branch branch;
	private BigDecimal posItemLogOtherFee;
	private PosItem posItem;
	private String inventoryLnDetailMemo;
	private BigDecimal inventoryLnDetailTare;//件皮重
	private boolean useLotRate = false;//启用批次换算率
	private boolean useAssistQty = false; //启用辅助单位代替常用单位
	private boolean centerTransferInRefCost = false; //调入单按中心成本价调入
	private Integer supplierNum;
	private BigDecimal inventoryLnDetailTransferPrice; //批次锁定配送价
	private String posItemLogBillNoRef;//关联单据号
	private String antiBillNo;//冲红单据号
	private Boolean outWithOrderPrice; //按单据金额出库
	private Boolean changeOrderPrice; //修改单据单价
	private boolean enoughInventory = true; //库存足够
	private Boolean queryInventory = true; //inventoryDao.updateInventory中是否重读库存
	private Inventory inventory;
 
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Boolean getQueryInventory() {
		return queryInventory;
	}

	public void setQueryInventory(Boolean queryInventory) {
		this.queryInventory = queryInventory;
	}

	public Boolean getChangeOrderPrice() {
		return changeOrderPrice;
	}

	public void setChangeOrderPrice(Boolean changeOrderPrice) {
		this.changeOrderPrice = changeOrderPrice;
	}

	public Boolean getOutWithOrderPrice() {
		return outWithOrderPrice;
	}

	public void setOutWithOrderPrice(Boolean outWithOrderPrice) {
		this.outWithOrderPrice = outWithOrderPrice;
	}

	public String getAntiBillNo() {
		return antiBillNo;
	}

	public void setAntiBillNo(String antiBillNo) {
		this.antiBillNo = antiBillNo;
	}

	public String getPosItemLogBillNoRef() {
		return posItemLogBillNoRef;
	}

	public void setPosItemLogBillNoRef(String posItemLogBillNoRef) {
		this.posItemLogBillNoRef = posItemLogBillNoRef;
	}

	public boolean isCenterTransferInRefCost() {
		return centerTransferInRefCost;
	}

	public void setCenterTransferInRefCost(boolean centerTransferInRefCost) {
		this.centerTransferInRefCost = centerTransferInRefCost;
	}

	public BigDecimal getInventoryLnDetailTransferPrice() {
		return inventoryLnDetailTransferPrice;
	}

	public void setInventoryLnDetailTransferPrice(BigDecimal inventoryLnDetailTransferPrice) {
		this.inventoryLnDetailTransferPrice = inventoryLnDetailTransferPrice;
	}

	public boolean isUseAssistQty() {
		return useAssistQty;
	}

	public void setUseAssistQty(boolean useAssistQty) {
		this.useAssistQty = useAssistQty;
	}

	public Integer getSupplierNum() {
		return supplierNum;
	}

	public void setSupplierNum(Integer supplierNum) {
		this.supplierNum = supplierNum;
	}

	public BigDecimal getPosItemLogLnBalance() {
		return posItemLogLnBalance;
	}

	public void setPosItemLogLnBalance(BigDecimal posItemLogLnBalance) {
		this.posItemLogLnBalance = posItemLogLnBalance;
	}

	public BigDecimal getPosItemLogLotBalance() {
		return posItemLogLotBalance;
	}

	public void setPosItemLogLotBalance(BigDecimal posItemLogLotBalance) {
		this.posItemLogLotBalance = posItemLogLotBalance;
	}

	public BigDecimal getInventoryLnDetailTare() {
		return inventoryLnDetailTare;
	}

	public void setInventoryLnDetailTare(BigDecimal inventoryLnDetailTare) {
		this.inventoryLnDetailTare = inventoryLnDetailTare;
	}

	public BigDecimal getPosItemLogAdjustMoney() {
		return posItemLogAdjustMoney;
	}

	public void setPosItemLogAdjustMoney(BigDecimal posItemLogAdjustMoney) {
		this.posItemLogAdjustMoney = posItemLogAdjustMoney;
	}

	public boolean isUseLotRate() {
		return useLotRate;
	}

	public void setUseLotRate(boolean useLotRate) {
		this.useLotRate = useLotRate;
	}

	public String getPosItemLogDateIndex() {
		return posItemLogDateIndex;
	}

	public void setPosItemLogDateIndex(String posItemLogDateIndex) {
		this.posItemLogDateIndex = posItemLogDateIndex;
	}

	public String getInventoryLnDetailMemo() {
		return inventoryLnDetailMemo;
	}

	public void setInventoryLnDetailMemo(String inventoryLnDetailMemo) {
		this.inventoryLnDetailMemo = inventoryLnDetailMemo;
	}

	public PosItem getPosItem() {
		return posItem;
	}

	public void setPosItem(PosItem posItem) {
		this.posItem = posItem;
	}

	public BigDecimal getPosItemLogOtherFee() {
		return posItemLogOtherFee;
	}

	public void setPosItemLogOtherFee(BigDecimal posItemLogOtherFee) {
		this.posItemLogOtherFee = posItemLogOtherFee;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getRefBillNo() {
		return refBillNo;
	}

	public void setRefBillNo(String refBillNo) {
		this.refBillNo = refBillNo;
	}

	public String getPosItemLogFid() {
		return posItemLogFid;
	}

	public void setPosItemLogFid(String posItemLogFid) {
		this.posItemLogFid = posItemLogFid;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getInStorehouseNum() {
		return inStorehouseNum;
	}

	public void setInStorehouseNum(Integer inStorehouseNum) {
		this.inStorehouseNum = inStorehouseNum;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public Integer getOutStorehouseNum() {
		return outStorehouseNum;
	}

	public void setOutStorehouseNum(Integer outStorehouseNum) {
		this.outStorehouseNum = outStorehouseNum;
	}

	public String getShiSystemBookCode() {
		return shiSystemBookCode;
	}

	public void setShiSystemBookCode(String shiSystemBookCode) {
		this.shiSystemBookCode = shiSystemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public Integer getShiftTableNum() {
		return shiftTableNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public String getShiftTableBizday() {
		return shiftTableBizday;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public String getPosItemLogItemCode() {
		return posItemLogItemCode;
	}

	public void setPosItemLogItemCode(String posItemLogItemCode) {
		this.posItemLogItemCode = posItemLogItemCode;
	}

	public String getPosItemLogItemName() {
		return posItemLogItemName;
	}

	public void setPosItemLogItemName(String posItemLogItemName) {
		this.posItemLogItemName = posItemLogItemName;
	}

	public String getPosItemLogItemUnit() {
		return posItemLogItemUnit;
	}

	public void setPosItemLogItemUnit(String posItemLogItemUnit) {
		this.posItemLogItemUnit = posItemLogItemUnit;
	}

	public Boolean getPosItemLogInoutFlag() {
		return posItemLogInoutFlag;
	}

	public void setPosItemLogInoutFlag(Boolean posItemLogInoutFlag) {
		this.posItemLogInoutFlag = posItemLogInoutFlag;
	}

	public String getPosItemLogSummary() {
		return posItemLogSummary;
	}

	public void setPosItemLogSummary(String posItemLogSummary) {
		this.posItemLogSummary = posItemLogSummary;
	}

	public Date getPosItemLogDate() {
		return posItemLogDate;
	}

	public void setPosItemLogDate(Date posItemLogDate) {
		this.posItemLogDate = posItemLogDate;
		this.posItemLogDateIndex = DateUtil.getDateShortStr(posItemLogDate);
		this.shiftTableBizday = DateUtil.getDateShortStr(posItemLogDate);
	}

	public BigDecimal getPosItemLogItemPrice() {
		return posItemLogItemPrice;
	}

	public void setPosItemLogItemPrice(BigDecimal posItemLogItemPrice) {
		this.posItemLogItemPrice = posItemLogItemPrice;
	}

	public BigDecimal getPosItemLogItemBalance() {
		return posItemLogItemBalance;
	}

	public void setPosItemLogItemBalance(BigDecimal posItemLogItemBalance) {
		this.posItemLogItemBalance = posItemLogItemBalance;
	}

	public BigDecimal getPosItemLogItemAmount() {
		return posItemLogItemAmount;
	}

	public void setPosItemLogItemAmount(BigDecimal posItemLogItemAmount) {
		this.posItemLogItemAmount = posItemLogItemAmount;
	}

	public String getPosItemLogOperator() {
		return posItemLogOperator;
	}

	public void setPosItemLogOperator(String posItemLogOperator) {
		this.posItemLogOperator = posItemLogOperator;
	}

	public Date getPosItemLogOperateTime() {
		return posItemLogOperateTime;
	}

	public void setPosItemLogOperateTime(Date posItemLogOperateTime) {
		this.posItemLogOperateTime = posItemLogOperateTime;
	}

	public String getPosItemLogMemo() {
		return posItemLogMemo;
	}

	public void setPosItemLogMemo(String posItemLogMemo) {
		this.posItemLogMemo = posItemLogMemo;
	}

	public Integer getPosItemLogItemMatrixNum() {
		return posItemLogItemMatrixNum;
	}

	public void setPosItemLogItemMatrixNum(Integer posItemLogItemMatrixNum) {
		this.posItemLogItemMatrixNum = posItemLogItemMatrixNum;
	}

	public String getPosItemLogBillNo() {
		return posItemLogBillNo;
	}

	public void setPosItemLogBillNo(String posItemLogBillNo) {
		this.posItemLogBillNo = posItemLogBillNo;
	}

	public Integer getPosItemLogBillDetailNum() {
		return posItemLogBillDetailNum;
	}

	public void setPosItemLogBillDetailNum(Integer posItemLogBillDetailNum) {
		this.posItemLogBillDetailNum = posItemLogBillDetailNum;
	}

	public Boolean getPosItemLogTransferFlag() {
		return posItemLogTransferFlag;
	}

	public void setPosItemLogTransferFlag(Boolean posItemLogTransferFlag) {
		this.posItemLogTransferFlag = posItemLogTransferFlag;
	}

	public String getPosItemLogLotNumber() {
		return posItemLogLotNumber;
	}

	public void setPosItemLogLotNumber(String posItemLogLotNumber) {
		this.posItemLogLotNumber = posItemLogLotNumber;
	}

	public Date getPosItemLogProducingDate() {
		return posItemLogProducingDate;
	}

	public void setPosItemLogProducingDate(Date posItemLogProducingDate) {
		this.posItemLogProducingDate = posItemLogProducingDate;
	}

	public String getPosItemLogSerialNumber() {
		return posItemLogSerialNumber;
	}

	public void setPosItemLogSerialNumber(String posItemLogSerialNumber) {
		this.posItemLogSerialNumber = posItemLogSerialNumber;
	}

	public BigDecimal getPosItemLogCostPrice() {
		return posItemLogCostPrice;
	}

	public void setPosItemLogCostPrice(BigDecimal posItemLogCostPrice) {
		this.posItemLogCostPrice = posItemLogCostPrice;
	}

	public String getPosItemLogItemAssistUnit() {
		return posItemLogItemAssistUnit;
	}

	public void setPosItemLogItemAssistUnit(String posItemLogItemAssistUnit) {
		this.posItemLogItemAssistUnit = posItemLogItemAssistUnit;
	}

	public BigDecimal getPosItemLogItemAssistBalance() {
		return posItemLogItemAssistBalance;
	}

	public void setPosItemLogItemAssistBalance(
			BigDecimal posItemLogItemAssistBalance) {
		this.posItemLogItemAssistBalance = posItemLogItemAssistBalance;
	}

	public BigDecimal getPosItemLogItemAssistAmount() {
		return posItemLogItemAssistAmount;
	}

	public void setPosItemLogItemAssistAmount(
			BigDecimal posItemLogItemAssistAmount) {
		this.posItemLogItemAssistAmount = posItemLogItemAssistAmount;
	}

	public BigDecimal getPosItemLogOperatePrice() {
		return posItemLogOperatePrice;
	}

	public void setPosItemLogOperatePrice(BigDecimal posItemLogOperatePrice) {
		this.posItemLogOperatePrice = posItemLogOperatePrice;
	}

	public String getPosItemLogUseUnit() {
		return posItemLogUseUnit;
	}

	public void setPosItemLogUseUnit(String posItemLogUseUnit) {
		this.posItemLogUseUnit = posItemLogUseUnit;
	}

	public BigDecimal getPosItemLogUseQty() {
		return posItemLogUseQty;
	}

	public void setPosItemLogUseQty(BigDecimal posItemLogUseQty) {
		this.posItemLogUseQty = posItemLogUseQty;
	}

	public BigDecimal getPosItemLogUsePrice() {
		return posItemLogUsePrice;
	}

	public void setPosItemLogUsePrice(BigDecimal posItemLogUsePrice) {
		this.posItemLogUsePrice = posItemLogUsePrice;
	}

	public BigDecimal getPosItemLogUseRate() {
		return posItemLogUseRate;
	}

	public void setPosItemLogUseRate(BigDecimal posItemLogUseRate) {
		this.posItemLogUseRate = posItemLogUseRate;
	}

	public String getPosItemLogItemSpec() {
		return posItemLogItemSpec;
	}

	public void setPosItemLogItemSpec(String posItemLogItemSpec) {
		this.posItemLogItemSpec = posItemLogItemSpec;
	}

	public String getPosItemLogRef() {
		return posItemLogRef;
	}

	public void setPosItemLogRef(String posItemLogRef) {
		this.posItemLogRef = posItemLogRef;
	}

	public BigDecimal getPosItemLogMoney() {
		return posItemLogMoney;
	}

	public void setPosItemLogMoney(BigDecimal posItemLogMoney) {
		if(posItemLogMoney != null){
			posItemLogMoney = posItemLogMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		this.posItemLogMoney = posItemLogMoney;
	}

	public String getPosItemLogItemCategory() {
		return posItemLogItemCategory;
	}

	public void setPosItemLogItemCategory(String posItemLogItemCategory) {
		this.posItemLogItemCategory = posItemLogItemCategory;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getSystemBookName() {
		return systemBookName;
	}

	public void setSystemBookName(String systemBookName) {
		this.systemBookName = systemBookName;
	}

	public String getBranchName() {
		return branchName;
	}

	public boolean isEnoughInventory() {
		return enoughInventory;
	}

	public void setEnoughInventory(boolean enoughInventory) {
		this.enoughInventory = enoughInventory;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public static PosItemLog get(List<PosItemLog> posItemLogs, Integer itemNum, Integer posItemLogItemMatrixNum, String posItemLogLotNumber) {
		if(posItemLogItemMatrixNum == null){
			posItemLogItemMatrixNum = 0;
		}
		if(posItemLogLotNumber == null){
			posItemLogLotNumber = "";
		}
		for(int i = 0;i < posItemLogs.size();i++){
			PosItemLog posItemLog = posItemLogs.get(i);
			if(posItemLog.getItemNum().equals(itemNum) 
					&& posItemLog.getPosItemLogItemMatrixNum().equals(posItemLogItemMatrixNum)
					&& posItemLog.getPosItemLogLotNumber().equals(posItemLogLotNumber)){
				return posItemLog;
				
			}
		}
		return null;
	}
	
	/**
	 * 是否仓管中心收货
	 * @return
	 */
	public boolean isInventoryCenterReceive(){
		if(posItemLogBillNo.length() == 32 && posItemLogSummary.equals(AppConstants.POS_ITEM_LOG_RECEIVE_ORDER)){
			return true;
		}
		
		return false;
	}

}