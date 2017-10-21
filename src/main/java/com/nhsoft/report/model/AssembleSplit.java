package com.nhsoft.report.model;


import com.nhsoft.module.report.query.State;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AssembleSplit generated by hbm2java
 */
public class AssembleSplit implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054139504048943255L;
	private String assembleSplitFid;
	private Integer itemNum;
	private Integer storehouseNum;
	private String systemBookCode;
	private String assembleSplitType;
	private Date assembleSplitDate;
	private BigDecimal assembleSplitAmount;
	private BigDecimal assembleSplitPrice;
	private String assembleSplitMemo;
	private String assembleSplitCreateor;
	private String assembleSplitAuditor;
	private State state;
	private boolean assembleSplitTransferFlag;
	private Date assembleSplitProducingDate;
	private String assembleSplitLotNumber;
	private BigDecimal assembleSplitAssistAmount;
	private Date assembleSplitAuditTime;
	private Date assembleSplitCreateTime;
	private Boolean assembleSplitAntiFlag;
	private List<AssembleSplitDetail> assembleSplitDetails = new ArrayList<AssembleSplitDetail>();

	private Integer branchNum;
	
	public AssembleSplit() {
	}

	public Boolean getAssembleSplitAntiFlag() {
		return assembleSplitAntiFlag;
	}

	public void setAssembleSplitAntiFlag(Boolean assembleSplitAntiFlag) {
		this.assembleSplitAntiFlag = assembleSplitAntiFlag;
	}

	public Date getAssembleSplitCreateTime() {
		return assembleSplitCreateTime;
	}

	public void setAssembleSplitCreateTime(Date assembleSplitCreateTime) {
		this.assembleSplitCreateTime = assembleSplitCreateTime;
	}

	public Date getAssembleSplitAuditTime() {
		return assembleSplitAuditTime;
	}

	public void setAssembleSplitAuditTime(Date assembleSplitAuditTime) {
		this.assembleSplitAuditTime = assembleSplitAuditTime;
	}

	public String getAssembleSplitFid() {
		return this.assembleSplitFid;
	}

	public void setAssembleSplitFid(String assembleSplitFid) {
		this.assembleSplitFid = assembleSplitFid;
	}

	public String getAssembleSplitType() {
		return this.assembleSplitType;
	}

	public void setAssembleSplitType(String assembleSplitType) {
		this.assembleSplitType = assembleSplitType;
	}

	public Date getAssembleSplitDate() {
		return this.assembleSplitDate;
	}

	public void setAssembleSplitDate(Date assembleSplitDate) {
		this.assembleSplitDate = assembleSplitDate;
	}

	public BigDecimal getAssembleSplitAmount() {
		return this.assembleSplitAmount;
	}

	public void setAssembleSplitAmount(BigDecimal assembleSplitAmount) {
		this.assembleSplitAmount = assembleSplitAmount;
	}

	public BigDecimal getAssembleSplitPrice() {
		return this.assembleSplitPrice;
	}

	public void setAssembleSplitPrice(BigDecimal assembleSplitPrice) {
		this.assembleSplitPrice = assembleSplitPrice;
	}

	public String getAssembleSplitMemo() {
		return this.assembleSplitMemo;
	}

	public void setAssembleSplitMemo(String assembleSplitMemo) {
		this.assembleSplitMemo = assembleSplitMemo;
	}

	public String getAssembleSplitCreateor() {
		return this.assembleSplitCreateor;
	}

	public void setAssembleSplitCreateor(String assembleSplitCreateor) {
		this.assembleSplitCreateor = assembleSplitCreateor;
	}

	public String getAssembleSplitAuditor() {
		return this.assembleSplitAuditor;
	}

	public void setAssembleSplitAuditor(String assembleSplitAuditor) {
		this.assembleSplitAuditor = assembleSplitAuditor;
	}

	public boolean isAssembleSplitTransferFlag() {
		return this.assembleSplitTransferFlag;
	}

	public void setAssembleSplitTransferFlag(boolean assembleSplitTransferFlag) {
		this.assembleSplitTransferFlag = assembleSplitTransferFlag;
	}

	public Date getAssembleSplitProducingDate() {
		return this.assembleSplitProducingDate;
	}

	public void setAssembleSplitProducingDate(Date assembleSplitProducingDate) {
		this.assembleSplitProducingDate = assembleSplitProducingDate;
	}

	public String getAssembleSplitLotNumber() {
		return this.assembleSplitLotNumber;
	}

	public void setAssembleSplitLotNumber(String assembleSplitLotNumber) {
		this.assembleSplitLotNumber = assembleSplitLotNumber;
	}

	public BigDecimal getAssembleSplitAssistAmount() {
		return this.assembleSplitAssistAmount;
	}

	public void setAssembleSplitAssistAmount(
			BigDecimal assembleSplitAssistAmount) {
		this.assembleSplitAssistAmount = assembleSplitAssistAmount;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<AssembleSplitDetail> getAssembleSplitDetails() {
		return assembleSplitDetails;
	}

	public void setAssembleSplitDetails(
			List<AssembleSplitDetail> assembleSplitDetails) {
		this.assembleSplitDetails = assembleSplitDetails;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public void removeZeroDetails(){
		for(int i = assembleSplitDetails.size() - 1;i >= 0;i--){
			AssembleSplitDetail assembleSplitDetail = assembleSplitDetails.get(i);
			if(assembleSplitDetail.getAssembleSplitDetailItemAmount().add(assembleSplitDetail.getAssembleSplitDetailItemAssistAmount()).compareTo(BigDecimal.ZERO) == 0){
				assembleSplitDetails.remove(i);
			}
		}
	}
	
	public static List<AssembleSplitDetail> findDetails(List<AssembleSplitDetail> details, String assembleSplitFid){
		List<AssembleSplitDetail> list = new ArrayList<AssembleSplitDetail>();
		for(AssembleSplitDetail detail : details){
			if(detail.getId().getAssembleSplitFid().equals(assembleSplitFid)){
				list.add(detail);
			}
		}
		return list;
	}

}
