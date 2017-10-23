package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferProfit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -860357460428836833L;
	private Integer tranferBranchNum; // 配送分店编号
	private String tranferBranchName; // 配送分店名称
	private Integer branchNum; // 分店编号
	private String branchName; // 分店名称
	private String posItemTypeCode; // 商品类别代码
	private String posItemTypeName; // 商品类别名称
	private String posItemCode; // 商品代码
	private String posItemName; // 商品名称
	private String spec; // 规格
	private String unit; // 单位
	private BigDecimal inNum; // 调入数量
	private BigDecimal inMoney; // 调入金额
	private BigDecimal inCost; // 调入成本
	private BigDecimal inProfit; // 调入毛利
	private String inProfitRate; // 调入毛利率
	private BigDecimal outNum; // 调出数量
	private BigDecimal outMoney; // 调出金额
	private BigDecimal outCost; // 调出成本
	private BigDecimal outProfit; // 调出毛利
	private String outProfitRate; // 调出毛利率
	private Integer itemNum;

	public TransferProfit() {
		setOutCost(BigDecimal.ZERO);
		setOutMoney(BigDecimal.ZERO);
		setOutNum(BigDecimal.ZERO);
		setOutProfit(BigDecimal.ZERO);
		setInCost(BigDecimal.ZERO);
		setInMoney(BigDecimal.ZERO);
		setInNum(BigDecimal.ZERO);
		setInProfit(BigDecimal.ZERO);
		setInProfitRate("0.00%");
		setOutProfitRate("0.00%");
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Integer getTranferBranchNum() {
		return tranferBranchNum;
	}

	public void setTranferBranchNum(Integer tranferBranchNum) {
		this.tranferBranchNum = tranferBranchNum;
	}

	public String getTranferBranchName() {
		return tranferBranchName;
	}

	public void setTranferBranchName(String tranferBranchName) {
		this.tranferBranchName = tranferBranchName;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getPosItemTypeCode() {
		return posItemTypeCode;
	}

	public void setPosItemTypeCode(String posItemTypeCode) {
		this.posItemTypeCode = posItemTypeCode;
	}

	public String getPosItemTypeName() {
		return posItemTypeName;
	}

	public void setPosItemTypeName(String posItemTypeName) {
		this.posItemTypeName = posItemTypeName;
	}

	public String getPosItemCode() {
		return posItemCode;
	}

	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}

	public String getPosItemName() {
		return posItemName;
	}

	public void setPosItemName(String posItemName) {
		this.posItemName = posItemName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getInNum() {
		return inNum;
	}

	public void setInNum(BigDecimal inNum) {
		this.inNum = inNum;
	}

	public BigDecimal getInMoney() {
		return inMoney;
	}

	public void setInMoney(BigDecimal inMoney) {
		this.inMoney = inMoney;
	}

	public BigDecimal getInCost() {
		return inCost;
	}

	public void setInCost(BigDecimal inCost) {
		this.inCost = inCost;
	}

	public BigDecimal getInProfit() {
		return inProfit;
	}

	public void setInProfit(BigDecimal inProfit) {
		this.inProfit = inProfit;
	}

	public String getInProfitRate() {
		return inProfitRate;
	}

	public void setInProfitRate(String inProfitRate) {
		this.inProfitRate = inProfitRate;
	}

	public BigDecimal getOutNum() {
		return outNum;
	}

	public void setOutNum(BigDecimal outNum) {
		this.outNum = outNum;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

	public BigDecimal getOutCost() {
		return outCost;
	}

	public void setOutCost(BigDecimal outCost) {
		this.outCost = outCost;
	}

	public BigDecimal getOutProfit() {
		return outProfit;
	}

	public void setOutProfit(BigDecimal outProfit) {
		this.outProfit = outProfit;
	}

	public String getOutProfitRate() {
		return outProfitRate;
	}

	public void setOutProfitRate(String outProfitRate) {
		this.outProfitRate = outProfitRate;
	}

}
