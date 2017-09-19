package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MobileBusinessPeriodDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 47473883623833937L;
	private BigDecimal businessMoney; // 营业额
	private Integer receiptCount; // 客单量
	private BigDecimal customerAvgPrice; // 客单价
	private List<NameAndValueDTO> businessMoneyList = new ArrayList<NameAndValueDTO>(0); // 营业额折线图
	private List<NameAndValueDTO> receiptCountList = new ArrayList<NameAndValueDTO>(0); // 客单量折线图
	
	public MobileBusinessPeriodDTO(){
		businessMoney = BigDecimal.ZERO;
		receiptCount = 0;
		NameAndValueDTO dto;
		for(int i = 0; i < 24;i++){
			dto = new NameAndValueDTO();
			dto.setName(i + "");
			dto.setValue(BigDecimal.ZERO);
			businessMoneyList.add(dto);
			
			dto = new NameAndValueDTO();
			dto.setName(i + "");
			dto.setValue(BigDecimal.ZERO);
			receiptCountList.add(dto);
		}
	}

	public BigDecimal getBusinessMoney() {
		return businessMoney;
	}

	public void setBusinessMoney(BigDecimal businessMoney) {
		this.businessMoney = businessMoney;
	}

	public Integer getReceiptCount() {
		return receiptCount;
	}

	public void setReceiptCount(Integer receiptCount) {
		this.receiptCount = receiptCount;
	}

	public BigDecimal getCustomerAvgPrice() {
		return customerAvgPrice;
	}

	public void setCustomerAvgPrice(BigDecimal customerAvgPrice) {
		this.customerAvgPrice = customerAvgPrice;
	}

	public List<NameAndValueDTO> getBusinessMoneyList() {
		return businessMoneyList;
	}

	public void setBusinessMoneyList(List<NameAndValueDTO> businessMoneyList) {
		this.businessMoneyList = businessMoneyList;
	}

	public List<NameAndValueDTO> getReceiptCountList() {
		return receiptCountList;
	}

	public void setReceiptCountList(List<NameAndValueDTO> receiptCountList) {
		this.receiptCountList = receiptCountList;
	}

}
