package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarriageCostDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7749391995165409098L;
	private Integer transferLineNum;
	private String transferLineCode;
	private String transferLineName;

	private int branchCount;// 店数
	private BigDecimal carriageQty;// 配送数量
	private BigDecimal carriageMoney;// 配送金额
	private BigDecimal carriageMoneyRate;// 配送金额占比
	private BigDecimal carriageAvgRate;// 日均装载率---车辆统计的时候用
	private BigDecimal carriageCostTotal;// 运输费用累计
	private BigDecimal branchCarriageCostDay;// 单店日均运费
	private BigDecimal carriageRate;// 运输费用占比
	private BigDecimal lineCarriageRate;// 区域配送费率
	private BigDecimal totalCarriageRate;// 总费率占比
	private int carriageCount;//运送趟次
	
	private List<Integer> branchNums = new ArrayList<Integer>();
	
	public CarriageCostDTO(){
		setBranchCount(0);
		setCarriageQty(BigDecimal.ZERO);
		setCarriageMoney(BigDecimal.ZERO);
		setCarriageMoneyRate(BigDecimal.ZERO);
		setCarriageAvgRate(BigDecimal.ZERO);
		setCarriageCostTotal(BigDecimal.ZERO);
		setBranchCarriageCostDay(BigDecimal.ZERO);
		setCarriageRate(BigDecimal.ZERO);
		setLineCarriageRate(BigDecimal.ZERO);
		setTotalCarriageRate(BigDecimal.ZERO);
		setCarriageCount(0);
	}

	public List<Integer> getBranchNums() {
		return branchNums;
	}

	public void setBranchNums(List<Integer> branchNums) {
		this.branchNums = branchNums;
	}

	public int getCarriageCount() {
		return carriageCount;
	}

	public void setCarriageCount(int carriageCount) {
		this.carriageCount = carriageCount;
	}

	public Integer getTransferLineNum() {
		return transferLineNum;
	}

	public void setTransferLineNum(Integer transferLineNum) {
		this.transferLineNum = transferLineNum;
	}

	public String getTransferLineCode() {
		return transferLineCode;
	}

	public void setTransferLineCode(String transferLineCode) {
		this.transferLineCode = transferLineCode;
	}

	public String getTransferLineName() {
		return transferLineName;
	}

	public void setTransferLineName(String transferLineName) {
		this.transferLineName = transferLineName;
	}

	public int getBranchCount() {
		return branchCount;
	}

	public void setBranchCount(int branchCount) {
		this.branchCount = branchCount;
	}

	public BigDecimal getCarriageQty() {
		return carriageQty;
	}

	public void setCarriageQty(BigDecimal carriageQty) {
		this.carriageQty = carriageQty;
	}

	public BigDecimal getCarriageMoney() {
		return carriageMoney;
	}

	public void setCarriageMoney(BigDecimal carriageMoney) {
		this.carriageMoney = carriageMoney;
	}

	public BigDecimal getCarriageMoneyRate() {
		return carriageMoneyRate;
	}

	public void setCarriageMoneyRate(BigDecimal carriageMoneyRate) {
		this.carriageMoneyRate = carriageMoneyRate;
	}

	public BigDecimal getCarriageAvgRate() {
		return carriageAvgRate;
	}

	public void setCarriageAvgRate(BigDecimal carriageAvgRate) {
		this.carriageAvgRate = carriageAvgRate;
	}

	public BigDecimal getCarriageCostTotal() {
		return carriageCostTotal;
	}

	public void setCarriageCostTotal(BigDecimal carriageCostTotal) {
		this.carriageCostTotal = carriageCostTotal;
	}

	public BigDecimal getBranchCarriageCostDay() {
		return branchCarriageCostDay;
	}

	public void setBranchCarriageCostDay(BigDecimal branchCarriageCostDay) {
		this.branchCarriageCostDay = branchCarriageCostDay;
	}

	public BigDecimal getCarriageRate() {
		return carriageRate;
	}

	public void setCarriageRate(BigDecimal carriageRate) {
		this.carriageRate = carriageRate;
	}

	public BigDecimal getLineCarriageRate() {
		return lineCarriageRate;
	}

	public void setLineCarriageRate(BigDecimal lineCarriageRate) {
		this.lineCarriageRate = lineCarriageRate;
	}

	public BigDecimal getTotalCarriageRate() {
		return totalCarriageRate;
	}

	public void setTotalCarriageRate(BigDecimal totalCarriageRate) {
		this.totalCarriageRate = totalCarriageRate;
	}

}
