package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangqin on 2017/11/2.
 */
public class PosItemLogSummaryDTO implements Serializable {
	
	/**
	 * group by
	 */
	private Integer itemNum;
	private Integer itemMatrixNum;
	private Integer branchNum;
	private Boolean inoutFlag;
	private String summary;
	private String bizday;
	private String memo;
	private Date date;
	private BigDecimal price;
	private BigDecimal operatePrice;
	private Integer billNo;
	private BigDecimal balance;
	private Integer inStorehouseNum;
	private Integer outStorehouseNum;
	private String dateIndex;

	
	/**
	 * sum
	 */
	private BigDecimal qty;
	private BigDecimal money;
	private BigDecimal assistQty;
	private BigDecimal useQty;
	private BigDecimal saleMoney;
	private String useUnit;
	private BigDecimal adjustMoney;



	/**
	 * count
	 * */
	private Integer itemCount;

	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}
	
	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}
	
	public Integer getItemMatrixNum() {
		return itemMatrixNum;
	}
	
	public void setItemMatrixNum(Integer itemMatrixNum) {
		this.itemMatrixNum = itemMatrixNum;
	}
	
	public Integer getBranchNum() {
		return branchNum;
	}
	
	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}
	
	public Boolean getInoutFlag() {
		return inoutFlag;
	}
	
	public void setInoutFlag(Boolean inoutFlag) {
		this.inoutFlag = inoutFlag;
	}
	
	public String getBizday() {
		return bizday;
	}
	
	public void setBizday(String bizday) {
		this.bizday = bizday;
	}
	
	public BigDecimal getQty() {
		return qty;
	}
	
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public BigDecimal getAssistQty() {
		return assistQty;
	}
	
	public void setAssistQty(BigDecimal assistQty) {
		this.assistQty = assistQty;
	}
	
	public BigDecimal getUseQty() {
		return useQty;
	}
	
	public void setUseQty(BigDecimal useQty) {
		this.useQty = useQty;
	}
	
	public BigDecimal getSaleMoney() {
		return saleMoney;
	}
	
	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}
	
	public String getUseUnit() {
		return useUnit;
	}
	
	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAdjustMoney() {
		return adjustMoney;
	}

	public void setAdjustMoney(BigDecimal adjustMoney) {
		this.adjustMoney = adjustMoney;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOperatePrice() {
		return operatePrice;
	}

	public void setOperatePrice(BigDecimal operatePrice) {
		this.operatePrice = operatePrice;
	}

	public Integer getBillNo() {
		return billNo;
	}

	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getInStorehouseNum() {
		return inStorehouseNum;
	}

	public void setInStorehouseNum(Integer inStorehouseNum) {
		this.inStorehouseNum = inStorehouseNum;
	}

	public Integer getOutStorehouseNum() {
		return outStorehouseNum;
	}

	public void setOutStorehouseNum(Integer outStorehouseNum) {
		this.outStorehouseNum = outStorehouseNum;
	}

	public String getDateIndex() {
		return dateIndex;
	}

	public void setDateIndex(String dateIndex) {
		this.dateIndex = dateIndex;
	}
}
