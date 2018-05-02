package com.nhsoft.module.report.dto;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ShiftTable generated by hbm2java
 */
public class ShiftTableDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1225341176889558728L;
	private String systemBookCode;
	private Integer branchNum;
	private Integer merchantNum;
	private Integer stallNum;
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private Date shiftTableBizDate;
	private Date shiftTableStart;
	private Date shiftTableEnd;
	private Integer shiftTableUserNum;
	private String shiftTableUserCode;
	private String shiftTableUserName;
	private boolean shiftTableClosed;
	private boolean shiftTableSynchronized;
	private boolean shiftTableNeedCarry;
	private boolean shiftTableCarried;
	private String shiftTableTerminalId;
	private Boolean shiftTableDpcSynchronized;
	private BigDecimal shiftTableActualMoney;
	private BigDecimal shiftTableActualBankMoney;
	private Integer shiftTableStatus;
	private String shiftTableMemo;
	private Boolean shiftTableCrmSynchronized;
	private Date shiftTableLastEditTime;
	
	
	//临时属性
	private List<ShiftTablePaymentDTO> shiftTablePayments = new ArrayList<ShiftTablePaymentDTO>();
	
	public Boolean getShiftTableCrmSynchronized() {
		return shiftTableCrmSynchronized;
	}
	
	public void setShiftTableCrmSynchronized(Boolean shiftTableCrmSynchronized) {
		this.shiftTableCrmSynchronized = shiftTableCrmSynchronized;
	}
	
	public Integer getShiftTableStatus() {
		return shiftTableStatus;
	}

	public void setShiftTableStatus(Integer shiftTableStatus) {
		this.shiftTableStatus = shiftTableStatus;
	}

	public String getShiftTableMemo() {
		return shiftTableMemo;
	}

	public void setShiftTableMemo(String shiftTableMemo) {
		this.shiftTableMemo = shiftTableMemo;
	}

	public BigDecimal getShiftTableActualBankMoney() {
		return shiftTableActualBankMoney;
	}

	public void setShiftTableActualBankMoney(BigDecimal shiftTableActualBankMoney) {
		this.shiftTableActualBankMoney = shiftTableActualBankMoney;
	}

	public Boolean getShiftTableDpcSynchronized() {
		return shiftTableDpcSynchronized;
	}

	public void setShiftTableDpcSynchronized(Boolean shiftTableDpcSynchronized) {
		this.shiftTableDpcSynchronized = shiftTableDpcSynchronized;
	}

	//临时属性
	private String branchName;

	public ShiftTableDTO() {
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public Integer getShiftTableNum() {
		return shiftTableNum;
	}

	public Integer getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(Integer merchantNum) {
		this.merchantNum = merchantNum;
	}

	public Integer getStallNum() {
		return stallNum;
	}

	public void setStallNum(Integer stallNum) {
		this.stallNum = stallNum;
	}

	public String getShiftTableBizday() {
		return shiftTableBizday;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public void setShiftTableNum(Integer shiftTableNum) {
		this.shiftTableNum = shiftTableNum;
	}

	public void setShiftTableBizday(String shiftTableBizday) {
		this.shiftTableBizday = shiftTableBizday;
	}

	public Date getShiftTableBizDate() {
		return this.shiftTableBizDate;
	}

	public void setShiftTableBizDate(Date shiftTableBizDate) {
		this.shiftTableBizDate = shiftTableBizDate;
	}

	public Date getShiftTableStart() {
		return this.shiftTableStart;
	}

	public void setShiftTableStart(Date shiftTableStart) {
		this.shiftTableStart = shiftTableStart;
	}

	public Date getShiftTableEnd() {
		return this.shiftTableEnd;
	}

	public void setShiftTableEnd(Date shiftTableEnd) {
		this.shiftTableEnd = shiftTableEnd;
	}

	public Integer getShiftTableUserNum() {
		return this.shiftTableUserNum;
	}

	public void setShiftTableUserNum(Integer shiftTableUserNum) {
		this.shiftTableUserNum = shiftTableUserNum;
	}

	public String getShiftTableUserCode() {
		return this.shiftTableUserCode;
	}

	public void setShiftTableUserCode(String shiftTableUserCode) {
		this.shiftTableUserCode = shiftTableUserCode;
	}

	public String getShiftTableUserName() {
		return this.shiftTableUserName;
	}

	public void setShiftTableUserName(String shiftTableUserName) {
		this.shiftTableUserName = shiftTableUserName;
	}

	public boolean isShiftTableClosed() {
		return this.shiftTableClosed;
	}

	public void setShiftTableClosed(boolean shiftTableClosed) {
		this.shiftTableClosed = shiftTableClosed;
	}

	public boolean isShiftTableSynchronized() {
		return this.shiftTableSynchronized;
	}

	public void setShiftTableSynchronized(boolean shiftTableSynchronized) {
		this.shiftTableSynchronized = shiftTableSynchronized;
	}

	public boolean isShiftTableNeedCarry() {
		return this.shiftTableNeedCarry;
	}

	public void setShiftTableNeedCarry(boolean shiftTableNeedCarry) {
		this.shiftTableNeedCarry = shiftTableNeedCarry;
	}

	public boolean isShiftTableCarried() {
		return this.shiftTableCarried;
	}

	public void setShiftTableCarried(boolean shiftTableCarried) {
		this.shiftTableCarried = shiftTableCarried;
	}

	public String getShiftTableTerminalId() {
		return this.shiftTableTerminalId;
	}

	public void setShiftTableTerminalId(String shiftTableTerminalId) {
		this.shiftTableTerminalId = shiftTableTerminalId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public BigDecimal getShiftTableActualMoney() {
		return shiftTableActualMoney;
	}

	public void setShiftTableActualMoney(BigDecimal shiftTableActualMoney) {
		this.shiftTableActualMoney = shiftTableActualMoney;
	}

	public List<ShiftTablePaymentDTO> getShiftTablePayments() {
		return shiftTablePayments;
	}

	public void setShiftTablePayments(List<ShiftTablePaymentDTO> shiftTablePayments) {
		this.shiftTablePayments = shiftTablePayments;
	}

	public static ShiftTableDTO getShiftTable(List<ShiftTableDTO> shiftTables, Integer branchNum, String shiftTableBizday,
			Integer shiftTableNum) {
		for(int i = 0;i < shiftTables.size();i++){
			ShiftTableDTO shiftTable = shiftTables.get(i);
			if(shiftTable.getBranchNum().equals(branchNum)
					&& shiftTable.getShiftTableBizday().equals(shiftTableBizday)
					&& shiftTable.getShiftTableNum().equals(shiftTableNum)){
				return shiftTable;
			}
		}
		return null;
	}

	public Date getShiftTableLastEditTime() {
		return shiftTableLastEditTime;
	}

	public void setShiftTableLastEditTime(Date shiftTableLastEditTime) {
		this.shiftTableLastEditTime = shiftTableLastEditTime;
	}
}
