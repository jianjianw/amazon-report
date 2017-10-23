package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosReceiveDiffMoneySumDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4079820758026632042L;
	private String bizday;
	private String regionCode;// 区域代码（按门店汇总的时候用）
	private String regionName;// 区域名称（按门店汇总的时候用）
	private String branchCode;// 门店代码
	private String branchName;// 门店名称
	private Integer branchNum;
	private String casher;// 收银员（按收银员汇总的时候用）
	private BigDecimal totalSaleMoney;// 总销售
	private BigDecimal totalReceiveMoney;// 总缴款
	private BigDecimal totalCardDeposit;// 储值卡充值总额（按门店汇总的时候用）
	private BigDecimal totalOtherMoney;// 其他收支金额
	private BigDecimal totalRelatMoney;// 续卡金额
	private BigDecimal totalReplaceMoney;// 换卡金额
	private BigDecimal totalReceiveDiff;// 总缴款差异=总缴款-总销售
	private String branchInputMemo;// 门店（收银员）添加备注
	private Integer shiftTableNum;
	private String shiftTableBizday;
	private List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs = new ArrayList<TypeAndTwoValuesDTO>();
	
	
	/**
	 * 总销售支付方式汇总
	 */
	private List<TypeAndTwoValuesDTO> totalSaleMoneyDetails = new ArrayList<TypeAndTwoValuesDTO>();
	
	/**
	 * 总存款支付方式汇总
	 */
	private List<TypeAndTwoValuesDTO> totalCardDepositDetails = new ArrayList<TypeAndTwoValuesDTO>();

	
	public PosReceiveDiffMoneySumDTO(){
		setTotalCardDeposit(BigDecimal.ZERO);
		setTotalReceiveDiff(BigDecimal.ZERO);
		setTotalReceiveMoney(BigDecimal.ZERO);
		setTotalSaleMoney(BigDecimal.ZERO);
		setTotalOtherMoney(BigDecimal.ZERO);
		setTotalRelatMoney(BigDecimal.ZERO);
		setTotalReplaceMoney(BigDecimal.ZERO);
	}

	public List<TypeAndTwoValuesDTO> getTotalSaleMoneyDetails() {
		return totalSaleMoneyDetails;
	}

	public void setTotalSaleMoneyDetails(List<TypeAndTwoValuesDTO> totalSaleMoneyDetails) {
		this.totalSaleMoneyDetails = totalSaleMoneyDetails;
	}

	public List<TypeAndTwoValuesDTO> getTotalCardDepositDetails() {
		return totalCardDepositDetails;
	}

	public void setTotalCardDepositDetails(List<TypeAndTwoValuesDTO> totalCardDepositDetails) {
		this.totalCardDepositDetails = totalCardDepositDetails;
	}

	public BigDecimal getTotalOtherMoney() {
		return totalOtherMoney;
	}

	public void setTotalOtherMoney(BigDecimal totalOtherMoney) {
		this.totalOtherMoney = totalOtherMoney;
	}

	public BigDecimal getTotalRelatMoney() {
		return totalRelatMoney;
	}

	public void setTotalRelatMoney(BigDecimal totalRelatMoney) {
		this.totalRelatMoney = totalRelatMoney;
	}

	public BigDecimal getTotalReplaceMoney() {
		return totalReplaceMoney;
	}

	public void setTotalReplaceMoney(BigDecimal totalReplaceMoney) {
		this.totalReplaceMoney = totalReplaceMoney;
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

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCasher() {
		return casher;
	}

	public void setCasher(String casher) {
		this.casher = casher;
	}

	public BigDecimal getTotalSaleMoney() {
		return totalSaleMoney;
	}

	public void setTotalSaleMoney(BigDecimal totalSaleMoney) {
		this.totalSaleMoney = totalSaleMoney;
	}

	public BigDecimal getTotalReceiveMoney() {
		return totalReceiveMoney;
	}

	public void setTotalReceiveMoney(BigDecimal totalReceiveMoney) {
		this.totalReceiveMoney = totalReceiveMoney;
	}

	public BigDecimal getTotalCardDeposit() {
		return totalCardDeposit;
	}

	public void setTotalCardDeposit(BigDecimal totalCardDeposit) {
		this.totalCardDeposit = totalCardDeposit;
	}

	public BigDecimal getTotalReceiveDiff() {
		return totalReceiveDiff;
	}

	public void setTotalReceiveDiff(BigDecimal totalReceiveDiff) {
		this.totalReceiveDiff = totalReceiveDiff;
	}

	public String getBranchInputMemo() {
		return branchInputMemo;
	}

	public void setBranchInputMemo(String branchInputMemo) {
		this.branchInputMemo = branchInputMemo;
	}

	public List<TypeAndTwoValuesDTO> getTypeAndTwoValuesDTOs() {
		return typeAndTwoValuesDTOs;
	}

	public void setTypeAndTwoValuesDTOs(List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs) {
		this.typeAndTwoValuesDTOs = typeAndTwoValuesDTOs;
	}
	
	public static PosReceiveDiffMoneySumDTO getByBranch(List<PosReceiveDiffMoneySumDTO> list, Integer branchNum){
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			if(dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
		
	}
	
	public static PosReceiveDiffMoneySumDTO getByBranchCashier(List<PosReceiveDiffMoneySumDTO> list, Integer branchNum, String cashier){
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			if(dto.getBranchNum().equals(branchNum) && dto.getCasher().equals(cashier)){
				return dto;
			}
		}
		return null;
		
	}
	
	public static PosReceiveDiffMoneySumDTO getByShift(List<PosReceiveDiffMoneySumDTO> list, Integer branchNum, String bizday, Integer biznum){
		for(int i = 0;i < list.size();i++){
			PosReceiveDiffMoneySumDTO dto = list.get(i);
			if(dto.getBranchNum().equals(branchNum) 
					&& dto.getShiftTableBizday().equals(bizday) && dto.getShiftTableNum().equals(biznum)){
				return dto;
			}
		}
		return null;
		
	}

}
