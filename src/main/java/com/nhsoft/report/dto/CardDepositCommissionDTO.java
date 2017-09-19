package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardDepositCommissionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6273891703046161237L;
	private String seller;
	private Integer branchNum;
	private BigDecimal money;
	private List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs = new ArrayList<TypeAndTwoValuesDTO>();

	
	//临时属性
	private Integer cardUserNum;
	private String bizday;

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Integer getBranchNum() {
		return branchNum;
	}

	public void setBranchNum(Integer branchNum) {
		this.branchNum = branchNum;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	
	public List<TypeAndTwoValuesDTO> getTypeAndTwoValuesDTOs() {
		return typeAndTwoValuesDTOs;
	}

	public void setTypeAndTwoValuesDTOs(List<TypeAndTwoValuesDTO> typeAndTwoValuesDTOs) {
		this.typeAndTwoValuesDTOs = typeAndTwoValuesDTOs;
	}

	public static CardDepositCommissionDTO get(List<CardDepositCommissionDTO> list, Integer branchNum, String bizday, Integer cardUserNum){
		
		for(int i = 0;i < list.size();i++){
			CardDepositCommissionDTO dto = list.get(i);
			
			if(dto.getBranchNum().equals(branchNum) 
					&& dto.getBizday().equals(bizday) 
					&& dto.getCardUserNum().equals(cardUserNum)){
				return dto;
			}
		}
		return null;
	}
	
	public static CardDepositCommissionDTO get(List<CardDepositCommissionDTO> list, String seller, Integer branchNum){
		
		for(int i = 0;i < list.size();i++){
			CardDepositCommissionDTO dto = list.get(i);
			
			if(dto.getBranchNum().equals(branchNum) 
					&& dto.getSeller().equals(seller)){
				return dto;
			}
		}
		return null;
	}
	
	public static CardDepositCommissionDTO get(List<CardDepositCommissionDTO> list, Integer branchNum){
		
		for(int i = 0;i < list.size();i++){
			CardDepositCommissionDTO dto = list.get(i);
			
			if(dto.getBranchNum().equals(branchNum)){
				return dto;
			}
		}
		return null;
	}

	public TypeAndTwoValuesDTO getDetail(String bizday) {
		for(int i = 0;i < typeAndTwoValuesDTOs.size();i++){
			TypeAndTwoValuesDTO dto = typeAndTwoValuesDTOs.get(i);
			if(dto.getType().equals(bizday)){
				return dto;
			}
		}
		return null;
	}

}
