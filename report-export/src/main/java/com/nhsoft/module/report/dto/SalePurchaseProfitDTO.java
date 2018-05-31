package com.nhsoft.module.report.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SalePurchaseProfitDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5859721616951531388L;
	private Integer branchNum;
	private String branchCode;// 门店编码
	private String branchName;// 门店名称

	private String categoryCode;// 商品类别代码
	private String categoryName;// 商品类别名称

	private Integer itemNum;
	private String itemCode;// 商品代码
	private String itemName;// 商品名称
	private String spec;// 规格
	private String unit;// 计量单位
	
	private String bizday;//营业日

	private BigDecimal profit;// 毛利
	private BigDecimal profitRate;// 毛利率
	private BigDecimal saleCost;// 销售成本
	private BigDecimal saleAmount;// 销售数量
	private BigDecimal saleMoney;// 销售金额
	private Integer receiveOrderCount;
	private Integer saleOrderCount;

	
	public SalePurchaseProfitDTO(){
		setProfit(BigDecimal.ZERO);
		setSaleAmount(BigDecimal.ZERO);
		setSaleMoney(BigDecimal.ZERO);
		setSaleCost(BigDecimal.ZERO);
		setProfitRate(BigDecimal.ZERO);
		setReceiveOrderCount(0);
		setSaleOrderCount(0);
	}
	
	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public Integer getReceiveOrderCount() {
		return receiveOrderCount;
	}

	public void setReceiveOrderCount(Integer receiveOrderCount) {
		this.receiveOrderCount = receiveOrderCount;
	}

	public Integer getSaleOrderCount() {
		return saleOrderCount;
	}

	public void setSaleOrderCount(Integer saleOrderCount) {
		this.saleOrderCount = saleOrderCount;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public BigDecimal getSaleCost() {
		return saleCost;
	}

	public void setSaleCost(BigDecimal saleCost) {
		this.saleCost = saleCost;
	}

	public BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public BigDecimal getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(BigDecimal saleMoney) {
		this.saleMoney = saleMoney;
	}
	
	public static SalePurchaseProfitDTO getByBranch(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs, Integer branchNum){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}
	
	public static SalePurchaseProfitDTO getByItem(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs, Integer itemNum){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(dto.getItemNum().equals(itemNum)){
				return dto;
			}
		}
		return null;
	}
	
	public static SalePurchaseProfitDTO getByBranchItem(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs, 
			Integer branchNum, Integer itemNum){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(dto.getItemNum().equals(itemNum) && dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}
	
	public static SalePurchaseProfitDTO getByCategory(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs, String categoryCode){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(StringUtils.equals(dto.getCategoryCode(),categoryCode)){
				return dto;
			}
		}
		return null;
	}
	
	public static SalePurchaseProfitDTO getByBizday(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs, String bizday){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(dto.getBizday().equals(bizday)){
				return dto;
			}
		}
		return null;
	}
	
	public static SalePurchaseProfitDTO getByBranchCategory(List<SalePurchaseProfitDTO> salePurchaseProfitDTOs,
			Integer branchNum,
			String categoryCode){
		SalePurchaseProfitDTO dto;
		for(int i = 0;i < salePurchaseProfitDTOs.size();i++){
			dto = salePurchaseProfitDTOs.get(i);
			if(StringUtils.equals(dto.getCategoryCode(),categoryCode) && dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}


}
