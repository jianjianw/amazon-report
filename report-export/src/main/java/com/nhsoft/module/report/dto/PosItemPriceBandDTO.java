package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemPriceBandDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1976321295229686833L;
	private int band; // 0 : 廉价 1：便宜 2：大众化 3：适宜 4：较高 5 最高
	private BigDecimal num; // 价格带项数
	private BigDecimal OutNum; // 销售数量
	private BigDecimal outMoney; // 销售金额
	
	public PosItemPriceBandDTO(){
		setNum(BigDecimal.ZERO);
		setOutNum(BigDecimal.ZERO);
		setOutMoney(BigDecimal.ZERO);
	}
	
	public PosItemPriceBandDTO(int band, int num){
		setBand(band);
		setNum(BigDecimal.valueOf(num));
		setOutNum(BigDecimal.ZERO);
		setOutMoney(BigDecimal.ZERO);
	}

	public int getBand() {
		return band;
	}

	public void setBand(int band) {
		this.band = band;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getOutNum() {
		return OutNum;
	}

	public void setOutNum(BigDecimal outNum) {
		OutNum = outNum;
	}

	public BigDecimal getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(BigDecimal outMoney) {
		this.outMoney = outMoney;
	}

}
