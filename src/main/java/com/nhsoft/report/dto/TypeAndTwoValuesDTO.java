package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TypeAndTwoValuesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5098889964507096035L;
	private String type;
	private BigDecimal amount;
	private BigDecimal money;
	
	public TypeAndTwoValuesDTO(){
		setAmount(BigDecimal.ZERO);
		setMoney(BigDecimal.ZERO);
	}
	
	public TypeAndTwoValuesDTO(String type, BigDecimal money){
		this.type = type;
		this.money = money;
		setAmount(BigDecimal.ZERO);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public static TypeAndTwoValuesDTO get(List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs, String type) {
		for(int i = 0;i < typeAndTwoValuesDTOs.size();i++){
			TypeAndTwoValuesDTO dto = typeAndTwoValuesDTOs.get(i);
			if(dto.getType().equals(type)){
				return dto;
			}
		}
		return null;
	}

}
