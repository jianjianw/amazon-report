package com.nhsoft.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarketAnalyticsChart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7350974689439444204L;
	private String marketDate;
	private BigDecimal morningValue;
	private BigDecimal noonValue;
	private BigDecimal nightValue;
	private Integer morning;
	private Integer noon;
	private Integer night;

	public MarketAnalyticsChart() {
		morningValue = BigDecimal.ZERO;
		noonValue = BigDecimal.ZERO;
		nightValue = BigDecimal.ZERO;
		morning = 0;
		noon = 0;
		night = 0;
	}

	public String getMarketDate() {
		return marketDate;
	}

	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}

	public BigDecimal getMorningValue() {
		return morningValue;
	}

	public void setMorningValue(BigDecimal morningValue) {
		this.morningValue = morningValue;
	}

	public BigDecimal getNoonValue() {
		return noonValue;
	}

	public void setNoonValue(BigDecimal noonValue) {
		this.noonValue = noonValue;
	}

	public BigDecimal getNightValue() {
		return nightValue;
	}

	public void setNightValue(BigDecimal nightValue) {
		this.nightValue = nightValue;
	}

	public Integer getMorning() {
		return morning;
	}

	public void setMorning(Integer morning) {
		this.morning = morning;
	}

	public Integer getNoon() {
		return noon;
	}

	public void setNoon(Integer noon) {
		this.noon = noon;
	}

	public Integer getNight() {
		return night;
	}

	public void setNight(Integer night) {
		this.night = night;
	}

}
