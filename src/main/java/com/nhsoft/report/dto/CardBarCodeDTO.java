package com.nhsoft.report.dto;

import com.nhsoft.pos3.server.util.DateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardBarCodeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8624656620768141200L;
	private String systemBookCode;
	private Integer cardUserNum;
	private String barCode; // 条码
	private Date date;// 条码生成时间
	
	public CardBarCodeDTO(){
		
	}
	
	public CardBarCodeDTO(String systemBookCode, Integer cardUserNum){
		this.date = Calendar.getInstance().getTime();
		this.systemBookCode = systemBookCode;
		this.cardUserNum = cardUserNum;
	}

	public void setSystemBookCode(String systemBookCode) {
		this.systemBookCode = systemBookCode;
	}

	public void setCardUserNum(Integer cardUserNum) {
		this.cardUserNum = cardUserNum;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSystemBookCode() {
		return systemBookCode;
	}

	public Integer getCardUserNum() {
		return cardUserNum;
	}

	public String getBarCode() {
		return barCode;
	}

	public Date getDate() {
		return date;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardUserNum == null) ? 0 : cardUserNum.hashCode());
		result = prime * result + ((systemBookCode == null) ? 0 : systemBookCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardBarCodeDTO other = (CardBarCodeDTO) obj;
		if (cardUserNum == null) {
			if (other.cardUserNum != null)
				return false;
		} else if (!cardUserNum.equals(other.cardUserNum))
			return false;
		if (systemBookCode == null) {
			if (other.systemBookCode != null)
				return false;
		} else if (!systemBookCode.equals(other.systemBookCode))
			return false;
		return true;
	}

	public static CardBarCodeDTO get(List<CardBarCodeDTO> cardBarCodeDTOs, String systemBookCode, Integer cardUserNum, List<String> cardBarCodes){
		Date now = Calendar.getInstance().getTime();
		for(int i = 0;i < cardBarCodeDTOs.size();i++){
			CardBarCodeDTO cardBarCodeDTO = cardBarCodeDTOs.get(i);
			
			//超过2分钟的移除
			if(DateUtil.diffMinute(cardBarCodeDTO.getDate(), now) >= 3){
				cardBarCodes.remove(cardBarCodeDTO.getBarCode());
				cardBarCodeDTOs.remove(i);
				i--;
				continue;
				
			}
			if(cardBarCodeDTO.getSystemBookCode().equals(systemBookCode) &&  cardBarCodeDTO.getCardUserNum().equals(cardUserNum)){
				return cardBarCodeDTO;
			}
		}
		return null;
		
	}
	
	public static CardBarCodeDTO getByBarCode(List<CardBarCodeDTO> cardBarCodeDTOs, String systemBookCode, String cardBarCode, List<String> cardBarCodes){
		Date now = Calendar.getInstance().getTime();
		for(int i = 0;i < cardBarCodeDTOs.size();i++){
			CardBarCodeDTO cardBarCodeDTO = cardBarCodeDTOs.get(i);
			
			//超过2分钟的移除
			if(DateUtil.diffMinute(cardBarCodeDTO.getDate(), now) >= 3){
				cardBarCodes.remove(cardBarCodeDTO.getBarCode());
				cardBarCodeDTOs.remove(i);
				i--;
				continue;
				
			}
			if(cardBarCodeDTO.getSystemBookCode().equals(systemBookCode) &&  cardBarCodeDTO.getBarCode().equals(cardBarCode)){
				return cardBarCodeDTO;
			}
		}
		return null;
		
	}

}
