package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PickerPerformanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -2226902333494618739L;
	private String	          pickerName;	                                 // 人员名称
	private BigDecimal	      pickerCreateCount;	                         // 制单单品数
	private BigDecimal	      pickerCreateMoney;	                         // 制单单据金额
	private BigDecimal	      pickerCreateAmount;	                         // 制单单品数量
	private BigDecimal	      pickerPickCount;	                             // 配货单品数
	private BigDecimal	      pickerPickMoney;	                             // 配货单据金额
	private BigDecimal	      pickerPickAmount;	                             // 配货单品数量
	private BigDecimal	      pickerSendCount;	                             // 发货单品数
	private BigDecimal	      pickerSendMoney;	                             // 发货单据金额
	private BigDecimal	      pickerSendAmount;	                             // 发货单品数量
	
	public PickerPerformanceDTO() {
	    setPickerCreateCount(BigDecimal.ZERO);
	    setPickerCreateMoney(BigDecimal.ZERO);
	    setPickerPickCount(BigDecimal.ZERO);
	    setPickerPickMoney(BigDecimal.ZERO);
	    setPickerCreateAmount(BigDecimal.ZERO);
	    setPickerPickAmount(BigDecimal.ZERO);
	    setPickerSendAmount(BigDecimal.ZERO);
	    setPickerSendCount(BigDecimal.ZERO);
	    setPickerSendMoney(BigDecimal.ZERO);
    }

	public BigDecimal getPickerSendCount() {
		return pickerSendCount;
	}

	public void setPickerSendCount(BigDecimal pickerSendCount) {
		this.pickerSendCount = pickerSendCount;
	}

	public BigDecimal getPickerSendMoney() {
		return pickerSendMoney;
	}

	public void setPickerSendMoney(BigDecimal pickerSendMoney) {
		this.pickerSendMoney = pickerSendMoney;
	}

	public BigDecimal getPickerSendAmount() {
		return pickerSendAmount;
	}

	public void setPickerSendAmount(BigDecimal pickerSendAmount) {
		this.pickerSendAmount = pickerSendAmount;
	}

	public BigDecimal getPickerCreateAmount() {
		return pickerCreateAmount;
	}

	public void setPickerCreateAmount(BigDecimal pickerCreateAmount) {
		this.pickerCreateAmount = pickerCreateAmount;
	}

	public BigDecimal getPickerPickAmount() {
		return pickerPickAmount;
	}

	public void setPickerPickAmount(BigDecimal pickerPickAmount) {
		this.pickerPickAmount = pickerPickAmount;
	}

	public String getPickerName() {
		return pickerName;
	}

	public void setPickerName(String pickerName) {
		this.pickerName = pickerName;
	}

	public BigDecimal getPickerCreateCount() {
		return pickerCreateCount;
	}

	public void setPickerCreateCount(BigDecimal pickerCreateCount) {
		this.pickerCreateCount = pickerCreateCount;
	}

	public BigDecimal getPickerCreateMoney() {
		return pickerCreateMoney;
	}

	public void setPickerCreateMoney(BigDecimal pickerCreateMoney) {
		this.pickerCreateMoney = pickerCreateMoney;
	}

	public BigDecimal getPickerPickCount() {
		return pickerPickCount;
	}

	public void setPickerPickCount(BigDecimal pickerPickCount) {
		this.pickerPickCount = pickerPickCount;
	}

	public BigDecimal getPickerPickMoney() {
		return pickerPickMoney;
	}

	public void setPickerPickMoney(BigDecimal pickerPickMoney) {
		this.pickerPickMoney = pickerPickMoney;
	}
	
	public static PickerPerformanceDTO get(List<PickerPerformanceDTO> pickerPerformanceDTOs, String operator){
		for(int i = 0;i < pickerPerformanceDTOs.size();i++){
			PickerPerformanceDTO pickerPerformanceDTO = pickerPerformanceDTOs.get(i);
			if(pickerPerformanceDTO.getPickerName().equals(operator)){
				return pickerPerformanceDTO;
			}
		}
		return null;
	}

}
