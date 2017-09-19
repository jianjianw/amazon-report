package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PosItemPriceBand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2731249298249622623L;
	private int band; // 0 : 廉价 1：便宜  2：大众化 3：适宜 4：较高 5 最高
	private BigDecimal num ;  //价格带项数
	private BigDecimal OutNum; //销售数量
	private BigDecimal outMoney; //销售金额
	
	private BigDecimal priceFrom;
	private BigDecimal priceTo;
	
	public PosItemPriceBand(){
		setNum(BigDecimal.ZERO);
		setOutNum(BigDecimal.ZERO);
		setOutMoney(BigDecimal.ZERO);
	}
	
	public PosItemPriceBand(int band, BigDecimal priceFrom, BigDecimal priceTo){
		setBand(band);
		setPriceFrom(priceFrom);
		setPriceTo(priceTo);
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

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}
	
	

}
