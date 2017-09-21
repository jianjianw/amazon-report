package com.nhsoft.report.dto;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OtherInfoDTO implements Serializable {

	/**
	 * 
	 */
	public static class OtherInfoDetailDTO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8312452367295877811L;
		private String name;
		private Date date;
		private BigDecimal money;
		private String operator;
		private String bizday;
		private String orderNo;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public BigDecimal getMoney() {
			return money;
		}

		public void setMoney(BigDecimal money) {
			this.money = money;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getBizday() {
			return bizday;
		}

		public void setBizday(String bizday) {
			this.bizday = bizday;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

	}

	private static final long serialVersionUID = -6183118443472292447L;
	private String name;
	private Date date;
	private BigDecimal paymentMoney;
	private BigDecimal money;
	private String operator;
	private String bizday;
	private String orderNo;
	private List<OtherInfoDetailDTO> details = new ArrayList<OtherInfoDetailDTO>();

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<OtherInfoDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<OtherInfoDetailDTO> details) {
		this.details = details;
	}

	public String getBizday() {
		return bizday;
	}

	public void setBizday(String bizday) {
		this.bizday = bizday;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	
	public static OtherInfoDTO getByOrderNo(List<OtherInfoDTO> list, String orderNo) {
		for (int i = 0; i < list.size(); i++) {
			OtherInfoDTO dto = list.get(i);
			if (StringUtils.isEmpty(dto.getOrderNo())) {
				continue;
			}
			if (dto.getOrderNo().equals(orderNo)) {
				return dto;
			}
		}
		return null;
	}

}
